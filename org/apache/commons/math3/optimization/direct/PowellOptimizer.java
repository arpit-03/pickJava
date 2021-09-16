/*     */ package org.apache.commons.math3.optimization.direct;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.MultivariateFunction;
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.optimization.MultivariateOptimizer;
/*     */ import org.apache.commons.math3.optimization.PointValuePair;
/*     */ import org.apache.commons.math3.optimization.univariate.BracketFinder;
/*     */ import org.apache.commons.math3.optimization.univariate.BrentOptimizer;
/*     */ import org.apache.commons.math3.optimization.univariate.SimpleUnivariateValueChecker;
/*     */ import org.apache.commons.math3.optimization.univariate.UnivariatePointValuePair;
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
/*     */ @Deprecated
/*     */ public class PowellOptimizer
/*     */   extends BaseAbstractMultivariateOptimizer<MultivariateFunction>
/*     */   implements MultivariateOptimizer
/*     */ {
/*  59 */   private static final double MIN_RELATIVE_TOLERANCE = 2.0D * FastMath.ulp(1.0D);
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
/*  90 */     this(rel, abs, FastMath.sqrt(rel), FastMath.sqrt(abs), checker);
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
/* 111 */     super(checker);
/*     */     
/* 113 */     if (rel < MIN_RELATIVE_TOLERANCE) {
/* 114 */       throw new NumberIsTooSmallException(Double.valueOf(rel), Double.valueOf(MIN_RELATIVE_TOLERANCE), true);
/*     */     }
/* 116 */     if (abs <= 0.0D) {
/* 117 */       throw new NotStrictlyPositiveException(Double.valueOf(abs));
/*     */     }
/* 119 */     this.relativeThreshold = rel;
/* 120 */     this.absoluteThreshold = abs;
/*     */ 
/*     */     
/* 123 */     this.line = new LineSearch(lineRel, lineAbs);
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
/*     */   public PowellOptimizer(double rel, double abs) {
/* 140 */     this(rel, abs, (ConvergenceChecker<PointValuePair>)null);
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
/*     */   public PowellOptimizer(double rel, double abs, double lineRel, double lineAbs) {
/* 158 */     this(rel, abs, lineRel, lineAbs, (ConvergenceChecker<PointValuePair>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointValuePair doOptimize() {
/* 164 */     GoalType goal = getGoalType();
/* 165 */     double[] guess = getStartPoint();
/* 166 */     int n = guess.length;
/*     */     
/* 168 */     double[][] direc = new double[n][n];
/* 169 */     for (int i = 0; i < n; i++) {
/* 170 */       direc[i][i] = 1.0D;
/*     */     }
/*     */     
/* 173 */     ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
/*     */ 
/*     */     
/* 176 */     double[] x = guess;
/* 177 */     double fVal = computeObjectiveValue(x);
/* 178 */     double[] x1 = (double[])x.clone();
/* 179 */     int iter = 0;
/*     */     while (true) {
/* 181 */       iter++;
/*     */       
/* 183 */       double fX = fVal;
/* 184 */       double fX2 = 0.0D;
/* 185 */       double delta = 0.0D;
/* 186 */       int bigInd = 0;
/* 187 */       double alphaMin = 0.0D;
/*     */       
/* 189 */       for (int j = 0; j < n; j++) {
/* 190 */         double[] arrayOfDouble = MathArrays.copyOf(direc[j]);
/*     */         
/* 192 */         fX2 = fVal;
/*     */         
/* 194 */         UnivariatePointValuePair optimum = this.line.search(x, arrayOfDouble);
/* 195 */         fVal = optimum.getValue();
/* 196 */         alphaMin = optimum.getPoint();
/* 197 */         double[][] result = newPointAndDirection(x, arrayOfDouble, alphaMin);
/* 198 */         x = result[0];
/*     */         
/* 200 */         if (fX2 - fVal > delta) {
/* 201 */           delta = fX2 - fVal;
/* 202 */           bigInd = j;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 207 */       boolean stop = (2.0D * (fX - fVal) <= this.relativeThreshold * (FastMath.abs(fX) + FastMath.abs(fVal)) + this.absoluteThreshold);
/*     */ 
/*     */ 
/*     */       
/* 211 */       PointValuePair previous = new PointValuePair(x1, fX);
/* 212 */       PointValuePair current = new PointValuePair(x, fVal);
/* 213 */       if (!stop && checker != null) {
/* 214 */         stop = checker.converged(iter, previous, current);
/*     */       }
/* 216 */       if (stop) {
/* 217 */         if (goal == GoalType.MINIMIZE) {
/* 218 */           return (fVal < fX) ? current : previous;
/*     */         }
/* 220 */         return (fVal > fX) ? current : previous;
/*     */       } 
/*     */ 
/*     */       
/* 224 */       double[] d = new double[n];
/* 225 */       double[] x2 = new double[n];
/* 226 */       for (int k = 0; k < n; k++) {
/* 227 */         d[k] = x[k] - x1[k];
/* 228 */         x2[k] = 2.0D * x[k] - x1[k];
/*     */       } 
/*     */       
/* 231 */       x1 = (double[])x.clone();
/* 232 */       fX2 = computeObjectiveValue(x2);
/*     */       
/* 234 */       if (fX > fX2) {
/* 235 */         double t = 2.0D * (fX + fX2 - 2.0D * fVal);
/* 236 */         double temp = fX - fVal - delta;
/* 237 */         t *= temp * temp;
/* 238 */         temp = fX - fX2;
/* 239 */         t -= delta * temp * temp;
/*     */         
/* 241 */         if (t < 0.0D) {
/* 242 */           UnivariatePointValuePair optimum = this.line.search(x, d);
/* 243 */           fVal = optimum.getValue();
/* 244 */           alphaMin = optimum.getPoint();
/* 245 */           double[][] result = newPointAndDirection(x, d, alphaMin);
/* 246 */           x = result[0];
/*     */           
/* 248 */           int lastInd = n - 1;
/* 249 */           direc[bigInd] = direc[lastInd];
/* 250 */           direc[lastInd] = result[1];
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
/* 269 */     int n = p.length;
/* 270 */     double[] nP = new double[n];
/* 271 */     double[] nD = new double[n];
/* 272 */     for (int i = 0; i < n; i++) {
/* 273 */       nD[i] = d[i] * optimum;
/* 274 */       nP[i] = p[i] + nD[i];
/*     */     } 
/*     */     
/* 277 */     double[][] result = new double[2][];
/* 278 */     result[0] = nP;
/* 279 */     result[1] = nD;
/*     */     
/* 281 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class LineSearch
/*     */     extends BrentOptimizer
/*     */   {
/*     */     private static final double REL_TOL_UNUSED = 1.0E-15D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final double ABS_TOL_UNUSED = 4.9E-324D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     private final BracketFinder bracket = new BracketFinder();
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
/*     */     LineSearch(double rel, double abs) {
/* 316 */       super(1.0E-15D, Double.MIN_VALUE, (ConvergenceChecker)new SimpleUnivariateValueChecker(rel, abs));
/*     */     }
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
/*     */     public UnivariatePointValuePair search(final double[] p, final double[] d) {
/* 331 */       final int n = p.length;
/* 332 */       UnivariateFunction f = new UnivariateFunction()
/*     */         {
/*     */           public double value(double alpha) {
/* 335 */             double[] x = new double[n];
/* 336 */             for (int i = 0; i < n; i++) {
/* 337 */               x[i] = p[i] + alpha * d[i];
/*     */             }
/* 339 */             double obj = PowellOptimizer.this.computeObjectiveValue(x);
/* 340 */             return obj;
/*     */           }
/*     */         };
/*     */       
/* 344 */       GoalType goal = PowellOptimizer.this.getGoalType();
/* 345 */       this.bracket.search(f, goal, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */       
/* 349 */       return optimize(2147483647, f, goal, this.bracket.getLo(), this.bracket.getHi(), this.bracket.getMid());
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/direct/PowellOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */