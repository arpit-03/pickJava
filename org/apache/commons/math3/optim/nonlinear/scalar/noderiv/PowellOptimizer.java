/*     */ package org.apache.commons.math3.optim.nonlinear.scalar.noderiv;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathUnsupportedOperationException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.PointValuePair;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.LineSearch;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.MultivariateOptimizer;
/*     */ import org.apache.commons.math3.optim.univariate.UnivariatePointValuePair;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ public class PowellOptimizer
/*     */   extends MultivariateOptimizer
/*     */ {
/*  62 */   private static final double MIN_RELATIVE_TOLERANCE = 2.0D * FastMath.ulp(1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double relativeThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double absoluteThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final LineSearch line;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PowellOptimizer(double rel, double abs, ConvergenceChecker<PointValuePair> checker) {
/*  93 */     this(rel, abs, FastMath.sqrt(rel), FastMath.sqrt(abs), checker);
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
/*     */   public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs, ConvergenceChecker<PointValuePair> checker) {
/* 114 */     super(checker);
/*     */     
/* 116 */     if (rel < MIN_RELATIVE_TOLERANCE) {
/* 117 */       throw new NumberIsTooSmallException(Double.valueOf(rel), Double.valueOf(MIN_RELATIVE_TOLERANCE), true);
/*     */     }
/* 119 */     if (abs <= 0.0D) {
/* 120 */       throw new NotStrictlyPositiveException(Double.valueOf(abs));
/*     */     }
/* 122 */     this.relativeThreshold = rel;
/* 123 */     this.absoluteThreshold = abs;
/*     */ 
/*     */     
/* 126 */     this.line = new LineSearch(this, lineRel, lineAbs, 1.0D);
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
/*     */   public PowellOptimizer(double rel, double abs) {
/* 145 */     this(rel, abs, (ConvergenceChecker<PointValuePair>)null);
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
/*     */   public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs) {
/* 162 */     this(rel, abs, lineRel, lineAbs, (ConvergenceChecker<PointValuePair>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair doOptimize() {
/* 168 */     checkParameters();
/*     */     
/* 170 */     GoalType goal = getGoalType();
/* 171 */     double[] guess = getStartPoint();
/* 172 */     int n = guess.length;
/*     */     
/* 174 */     double[][] direc = new double[n][n];
/* 175 */     for (int i = 0; i < n; i++) {
/* 176 */       direc[i][i] = 1.0D;
/*     */     }
/*     */     
/* 179 */     ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
/*     */ 
/*     */     
/* 182 */     double[] x = guess;
/* 183 */     double fVal = computeObjectiveValue(x);
/* 184 */     double[] x1 = (double[])x.clone();
/*     */     while (true) {
/* 186 */       incrementIterationCount();
/*     */       
/* 188 */       double fX = fVal;
/* 189 */       double fX2 = 0.0D;
/* 190 */       double delta = 0.0D;
/* 191 */       int bigInd = 0;
/* 192 */       double alphaMin = 0.0D;
/*     */       
/* 194 */       for (int j = 0; j < n; j++) {
/* 195 */         double[] arrayOfDouble = MathArrays.copyOf(direc[j]);
/*     */         
/* 197 */         fX2 = fVal;
/*     */         
/* 199 */         UnivariatePointValuePair optimum = this.line.search(x, arrayOfDouble);
/* 200 */         fVal = optimum.getValue();
/* 201 */         alphaMin = optimum.getPoint();
/* 202 */         double[][] result = newPointAndDirection(x, arrayOfDouble, alphaMin);
/* 203 */         x = result[0];
/*     */         
/* 205 */         if (fX2 - fVal > delta) {
/* 206 */           delta = fX2 - fVal;
/* 207 */           bigInd = j;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 212 */       boolean stop = (2.0D * (fX - fVal) <= this.relativeThreshold * (FastMath.abs(fX) + FastMath.abs(fVal)) + this.absoluteThreshold);
/*     */ 
/*     */ 
/*     */       
/* 216 */       PointValuePair previous = new PointValuePair(x1, fX);
/* 217 */       PointValuePair current = new PointValuePair(x, fVal);
/* 218 */       if (!stop && checker != null) {
/* 219 */         stop = checker.converged(getIterations(), previous, current);
/*     */       }
/* 221 */       if (stop) {
/* 222 */         if (goal == GoalType.MINIMIZE) {
/* 223 */           return (fVal < fX) ? current : previous;
/*     */         }
/* 225 */         return (fVal > fX) ? current : previous;
/*     */       } 
/*     */ 
/*     */       
/* 229 */       double[] d = new double[n];
/* 230 */       double[] x2 = new double[n];
/* 231 */       for (int k = 0; k < n; k++) {
/* 232 */         d[k] = x[k] - x1[k];
/* 233 */         x2[k] = 2.0D * x[k] - x1[k];
/*     */       } 
/*     */       
/* 236 */       x1 = (double[])x.clone();
/* 237 */       fX2 = computeObjectiveValue(x2);
/*     */       
/* 239 */       if (fX > fX2) {
/* 240 */         double t = 2.0D * (fX + fX2 - 2.0D * fVal);
/* 241 */         double temp = fX - fVal - delta;
/* 242 */         t *= temp * temp;
/* 243 */         temp = fX - fX2;
/* 244 */         t -= delta * temp * temp;
/*     */         
/* 246 */         if (t < 0.0D) {
/* 247 */           UnivariatePointValuePair optimum = this.line.search(x, d);
/* 248 */           fVal = optimum.getValue();
/* 249 */           alphaMin = optimum.getPoint();
/* 250 */           double[][] result = newPointAndDirection(x, d, alphaMin);
/* 251 */           x = result[0];
/*     */           
/* 253 */           int lastInd = n - 1;
/* 254 */           direc[bigInd] = direc[lastInd];
/* 255 */           direc[lastInd] = result[1];
/*     */         } 
/*     */       } 
/*     */     } 
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
/*     */   private double[][] newPointAndDirection(double[] p, double[] d, double optimum) {
/* 274 */     int n = p.length;
/* 275 */     double[] nP = new double[n];
/* 276 */     double[] nD = new double[n];
/* 277 */     for (int i = 0; i < n; i++) {
/* 278 */       nD[i] = d[i] * optimum;
/* 279 */       nP[i] = p[i] + nD[i];
/*     */     } 
/*     */     
/* 282 */     double[][] result = new double[2][];
/* 283 */     result[0] = nP;
/* 284 */     result[1] = nD;
/*     */     
/* 286 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkParameters() {
/* 294 */     if (getLowerBound() != null || getUpperBound() != null)
/*     */     {
/* 296 */       throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/nonlinear/scalar/noderiv/PowellOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */