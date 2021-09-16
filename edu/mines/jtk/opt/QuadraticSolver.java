/*     */ package edu.mines.jtk.opt;
/*     */ 
/*     */ import edu.mines.jtk.util.Almost;
/*     */ import edu.mines.jtk.util.Monitor;
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
/*     */ public class QuadraticSolver
/*     */ {
/*  58 */   private Quadratic _quadratic = null;
/*  59 */   private static final Logger LOG = Logger.getLogger("edu.mines.jtk.opt");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QuadraticSolver(Quadratic quadratic) {
/*  70 */     this._quadratic = quadratic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vect solve(int numberIterations, Monitor monitor) {
/*  82 */     if (monitor == null) {
/*  83 */       monitor = Monitor.NULL_MONITOR;
/*     */     }
/*  85 */     monitor.report(0.0D);
/*  86 */     VectConst b = this._quadratic.getB();
/*  87 */     monitor.report(1.0D / (numberIterations + 2.0D));
/*     */     
/*  89 */     double bb = b.dot(b);
/*  90 */     checkNaN(bb);
/*  91 */     if (Almost.FLOAT.zero(bb)) {
/*  92 */       LOG.fine("Gradient of quadratic is negligible.  Not solving");
/*  93 */       Vect result = VectUtil.cloneZero(b);
/*  94 */       monitor.report(1.0D);
/*  95 */       return result;
/*     */     } 
/*     */     
/*  98 */     Vect g = (Vect)b;
/*  99 */     b = null;
/* 100 */     Vect x = VectUtil.cloneZero(g);
/* 101 */     Vect p = x.clone();
/* 102 */     Vect u = x.clone();
/* 103 */     double pu = 0.0D;
/* 104 */     Vect qa = g.clone();
/*     */ 
/*     */     
/* 107 */     for (int iter = 0; iter < numberIterations && !monitor.isCanceled(); iter++) {
/* 108 */       double beta = 0.0D;
/*     */       
/* 110 */       Vect q = qa;
/* 111 */       VectUtil.copy(q, g);
/* 112 */       this._quadratic.inverseHessian(q);
/* 113 */       q.postCondition();
/* 114 */       this._quadratic.multiplyHessian(q);
/* 115 */       monitor.report((iter + 2.0D) / (numberIterations + 2.0D));
/* 116 */       if (iter > 0) {
/* 117 */         double pq = p.dot(q);
/* 118 */         checkNaN(pq);
/* 119 */         beta = Almost.FLOAT.divide(pq, pu, 0.0D);
/* 120 */         if (beta < -5.0D) {
/* 121 */           beta = -5.0D;
/*     */         }
/* 123 */         if (beta > 5.0D) {
/* 124 */           beta = 5.0D;
/*     */         }
/*     */       } 
/* 127 */       u.add(beta, -1.0D, q);
/*     */ 
/*     */       
/* 130 */       Vect a = qa;
/* 131 */       VectUtil.copy(a, g);
/* 132 */       this._quadratic.inverseHessian(a);
/* 133 */       a.postCondition();
/* 134 */       p.add(beta, -1.0D, a);
/*     */       
/* 136 */       double pg = p.dot(g);
/* 137 */       checkNaN(pg);
/* 138 */       pu = p.dot(u);
/* 139 */       checkNaN(pu);
/* 140 */       if (Almost.FLOAT.zero(pg) || Almost.FLOAT.zero(pu)) {
/*     */         break;
/*     */       }
/* 143 */       double scalar = -pg / pu;
/* 144 */       x.add(1.0D, scalar, p);
/* 145 */       if (iter == numberIterations - 1) {
/*     */         break;
/*     */       }
/* 148 */       g.add(1.0D, scalar, u);
/*     */     } 
/* 150 */     p.dispose();
/* 151 */     u.dispose();
/* 152 */     g.dispose();
/* 153 */     qa.dispose();
/* 154 */     monitor.report(1.0D);
/* 155 */     return x;
/*     */   }
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
/*     */   public static Vect solve(VectConst data, VectConst referenceModel, LinearTransform linearTransform, boolean dampOnlyPerturbation, int conjugateGradIterations, Monitor monitor) {
/* 187 */     Transform transform = new LinearTransformWrapper(linearTransform);
/* 188 */     VectConst perturbModel = null;
/* 189 */     TransformQuadratic transformQuadratic = new TransformQuadratic(data, referenceModel, perturbModel, transform, dampOnlyPerturbation);
/*     */ 
/*     */     
/* 192 */     QuadraticSolver quadraticSolver = new QuadraticSolver(transformQuadratic);
/* 193 */     Vect result = quadraticSolver.solve(conjugateGradIterations, monitor);
/* 194 */     transformQuadratic.dispose();
/* 195 */     result.add(1.0D, 1.0D, referenceModel);
/* 196 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNaN(double value) {
/* 205 */     if (value * 0.0D != 0.0D)
/* 206 */       throw new IllegalStateException("Value is a NaN"); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/opt/QuadraticSolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */