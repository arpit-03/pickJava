/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import edu.mines.jtk.util.Monitor;
/*     */ import edu.mines.jtk.util.PartialMonitor;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GaussNewtonSolver
/*     */ {
/*     */   private static boolean s_expensiveDebug = false;
/*  32 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vect solve(VectConst data, VectConst referenceModel, VectConst perturbModel, Transform transform, boolean dampOnlyPerturbation, int conjugateGradIterations, int lineSearchIterations, int linearizationIterations, double lineSearchError, Monitor monitor) {
/* 107 */     if (s_expensiveDebug) {
/* 108 */       VectUtil.test(data);
/* 109 */       VectUtil.test(referenceModel);
/* 110 */       TransformQuadratic tq = new TransformQuadratic(data, referenceModel, perturbModel, transform, dampOnlyPerturbation);
/*     */       
/* 112 */       int precision = tq.getTransposePrecision();
/* 113 */       if (precision < 6) {
/* 114 */         throw new IllegalStateException("Bad transpose precision = " + precision);
/*     */       }
/* 116 */       tq.dispose();
/*     */     } 
/* 118 */     if (monitor == null) {
/* 119 */       monitor = Monitor.NULL_MONITOR;
/*     */     }
/* 121 */     monitor.report(0.0D);
/*     */     
/* 123 */     Vect m0 = referenceModel.clone();
/* 124 */     referenceModel = null;
/* 125 */     m0.constrain();
/* 126 */     if (linearizationIterations < 1) {
/* 127 */       linearizationIterations = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 132 */     for (int iter = 0; iter < linearizationIterations && !monitor.isCanceled(); iter++) {
/* 133 */       double frac = 3.0D * conjugateGradIterations / (3.0D * conjugateGradIterations + lineSearchIterations);
/*     */       
/* 135 */       double begin = iter / linearizationIterations;
/* 136 */       double mid = (iter + frac) / linearizationIterations;
/* 137 */       double end = (iter + 1.0D) / linearizationIterations;
/* 138 */       monitor.report(begin);
/*     */       
/* 140 */       TransformQuadratic transformQuadratic = new TransformQuadratic(data, m0, perturbModel, transform, dampOnlyPerturbation);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       QuadraticSolver quadraticSolver = new QuadraticSolver(transformQuadratic);
/*     */ 
/*     */       
/* 148 */       Vect perturbation = quadraticSolver.solve(conjugateGradIterations, (Monitor)new PartialMonitor(monitor, begin, mid));
/*     */ 
/*     */       
/* 151 */       double pp = perturbation.dot(perturbation);
/* 152 */       if (Almost.FLOAT.zero(pp)) {
/* 153 */         perturbation.dispose();
/* 154 */         transformQuadratic.dispose();
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 159 */       double scalar = 1.0D;
/* 160 */       if (lineSearchIterations > 0) {
/* 161 */         TransformFunction transformFunction = new TransformFunction(transform, data, m0, perturbation, dampOnlyPerturbation);
/*     */ 
/*     */         
/* 164 */         ScalarSolver scalarSolver = new ScalarSolver(transformFunction);
/*     */         
/* 166 */         double scalarMin = 0.0D;
/* 167 */         double scalarMax = 1.1D;
/* 168 */         double okError = lineSearchError;
/* 169 */         double okFraction = lineSearchError;
/*     */         
/* 171 */         scalar = scalarSolver.solve(0.0D, 1.1D, okError, okFraction, lineSearchIterations, (Monitor)new PartialMonitor(monitor, mid, end));
/*     */         
/* 173 */         transformFunction.dispose();
/*     */       } 
/*     */ 
/*     */       
/* 177 */       m0.project(1.0D, scalar, perturbation);
/*     */ 
/*     */       
/* 180 */       m0.constrain();
/*     */       
/* 182 */       perturbation.dispose();
/* 183 */       transformQuadratic.dispose();
/*     */       
/* 185 */       monitor.report(end);
/*     */     } 
/* 187 */     monitor.report(1.0D);
/* 188 */     return m0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TransformFunction
/*     */     implements ScalarSolver.Function
/*     */   {
/*     */     private final VectConst _referenceModel;
/*     */ 
/*     */     
/*     */     private final VectConst _perturbation;
/*     */ 
/*     */     
/*     */     private final Vect _model;
/*     */ 
/*     */     
/*     */     private final TransformQuadratic _transformQuadratic;
/*     */ 
/*     */ 
/*     */     
/*     */     private TransformFunction(Transform transform, VectConst data, VectConst referenceModel, VectConst perturbation, boolean dampOnlyPerturbation) {
/* 211 */       this._referenceModel = referenceModel;
/* 212 */       this._model = this._referenceModel.clone();
/* 213 */       this._perturbation = perturbation;
/* 214 */       this._transformQuadratic = new TransformQuadratic(data, referenceModel, null, transform, dampOnlyPerturbation);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double function(double scalar) {
/* 220 */       VectUtil.copy(this._model, this._referenceModel);
/* 221 */       this._model.project(1.0D, scalar, this._perturbation);
/* 222 */       return this._transformQuadratic.evalFullObjectiveFunction(this._model);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void dispose() {
/* 229 */       this._model.dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setExpensiveDebug(boolean debug) {
/* 240 */     s_expensiveDebug = debug;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/GaussNewtonSolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */