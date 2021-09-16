/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MathInternalError;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
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
/*     */ public class BracketingNthOrderBrentSolver
/*     */   extends AbstractUnivariateSolver
/*     */   implements BracketedUnivariateSolver<UnivariateFunction>
/*     */ {
/*     */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   private static final int DEFAULT_MAXIMAL_ORDER = 5;
/*     */   private static final int MAXIMAL_AGING = 2;
/*     */   private static final double REDUCTION_FACTOR = 0.0625D;
/*     */   private final int maximalOrder;
/*     */   private AllowedSolution allowed;
/*     */   
/*     */   public BracketingNthOrderBrentSolver() {
/*  69 */     this(1.0E-6D, 5);
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
/*     */   public BracketingNthOrderBrentSolver(double absoluteAccuracy, int maximalOrder) throws NumberIsTooSmallException {
/*  82 */     super(absoluteAccuracy);
/*  83 */     if (maximalOrder < 2) {
/*  84 */       throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(2), true);
/*     */     }
/*  86 */     this.maximalOrder = maximalOrder;
/*  87 */     this.allowed = AllowedSolution.ANY_SIDE;
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
/*     */   public BracketingNthOrderBrentSolver(double relativeAccuracy, double absoluteAccuracy, int maximalOrder) throws NumberIsTooSmallException {
/* 102 */     super(relativeAccuracy, absoluteAccuracy);
/* 103 */     if (maximalOrder < 2) {
/* 104 */       throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(2), true);
/*     */     }
/* 106 */     this.maximalOrder = maximalOrder;
/* 107 */     this.allowed = AllowedSolution.ANY_SIDE;
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
/*     */   public BracketingNthOrderBrentSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy, int maximalOrder) throws NumberIsTooSmallException {
/* 124 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
/* 125 */     if (maximalOrder < 2) {
/* 126 */       throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(2), true);
/*     */     }
/* 128 */     this.maximalOrder = maximalOrder;
/* 129 */     this.allowed = AllowedSolution.ANY_SIDE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaximalOrder() {
/* 136 */     return this.maximalOrder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doSolve() throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
/*     */     int nbPoints, signChangeIndex;
/* 148 */     double[] x = new double[this.maximalOrder + 1];
/* 149 */     double[] y = new double[this.maximalOrder + 1];
/* 150 */     x[0] = getMin();
/* 151 */     x[1] = getStartValue();
/* 152 */     x[2] = getMax();
/* 153 */     verifySequence(x[0], x[1], x[2]);
/*     */ 
/*     */     
/* 156 */     y[1] = computeObjectiveValue(x[1]);
/* 157 */     if (Precision.equals(y[1], 0.0D, 1))
/*     */     {
/* 159 */       return x[1];
/*     */     }
/*     */ 
/*     */     
/* 163 */     y[0] = computeObjectiveValue(x[0]);
/* 164 */     if (Precision.equals(y[0], 0.0D, 1))
/*     */     {
/* 166 */       return x[0];
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 171 */     if (y[0] * y[1] < 0.0D) {
/*     */ 
/*     */       
/* 174 */       nbPoints = 2;
/* 175 */       signChangeIndex = 1;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 180 */       y[2] = computeObjectiveValue(x[2]);
/* 181 */       if (Precision.equals(y[2], 0.0D, 1))
/*     */       {
/* 183 */         return x[2];
/*     */       }
/*     */       
/* 186 */       if (y[1] * y[2] < 0.0D) {
/*     */         
/* 188 */         nbPoints = 3;
/* 189 */         signChangeIndex = 2;
/*     */       } else {
/* 191 */         throw new NoBracketingException(x[0], x[2], y[0], y[2]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 197 */     double[] tmpX = new double[x.length];
/*     */ 
/*     */     
/* 200 */     double xA = x[signChangeIndex - 1];
/* 201 */     double yA = y[signChangeIndex - 1];
/* 202 */     double absYA = FastMath.abs(yA);
/* 203 */     int agingA = 0;
/* 204 */     double xB = x[signChangeIndex];
/* 205 */     double yB = y[signChangeIndex];
/* 206 */     double absYB = FastMath.abs(yB);
/* 207 */     int agingB = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 213 */       double targetY, nextX, xTol = getAbsoluteAccuracy() + getRelativeAccuracy() * FastMath.max(FastMath.abs(xA), FastMath.abs(xB));
/*     */       
/* 215 */       if (xB - xA <= xTol || FastMath.max(absYA, absYB) < getFunctionValueAccuracy()) {
/* 216 */         switch (this.allowed) {
/*     */           case ANY_SIDE:
/* 218 */             return (absYA < absYB) ? xA : xB;
/*     */           case LEFT_SIDE:
/* 220 */             return xA;
/*     */           case RIGHT_SIDE:
/* 222 */             return xB;
/*     */           case BELOW_SIDE:
/* 224 */             return (yA <= 0.0D) ? xA : xB;
/*     */           case ABOVE_SIDE:
/* 226 */             return (yA < 0.0D) ? xB : xA;
/*     */         } 
/*     */         
/* 229 */         throw new MathInternalError();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 235 */       if (agingA >= 2) {
/*     */         
/* 237 */         int p = agingA - 2;
/* 238 */         double weightA = ((1 << p) - 1);
/* 239 */         double weightB = (p + 1);
/* 240 */         targetY = (weightA * yA - weightB * 0.0625D * yB) / (weightA + weightB);
/* 241 */       } else if (agingB >= 2) {
/*     */         
/* 243 */         int p = agingB - 2;
/* 244 */         double weightA = (p + 1);
/* 245 */         double weightB = ((1 << p) - 1);
/* 246 */         targetY = (weightB * yB - weightA * 0.0625D * yA) / (weightA + weightB);
/*     */       } else {
/*     */         
/* 249 */         targetY = 0.0D;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 254 */       int start = 0;
/* 255 */       int end = nbPoints;
/*     */ 
/*     */       
/*     */       do {
/* 259 */         System.arraycopy(x, start, tmpX, start, end - start);
/* 260 */         nextX = guessX(targetY, tmpX, y, start, end);
/*     */         
/* 262 */         if (nextX > xA && nextX < xB) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 268 */         if (signChangeIndex - start >= end - signChangeIndex) {
/*     */           
/* 270 */           start++;
/*     */         } else {
/*     */           
/* 273 */           end--;
/*     */         } 
/*     */ 
/*     */         
/* 277 */         nextX = Double.NaN;
/*     */ 
/*     */       
/*     */       }
/* 281 */       while (Double.isNaN(nextX) && end - start > 1);
/*     */       
/* 283 */       if (Double.isNaN(nextX)) {
/*     */         
/* 285 */         nextX = xA + 0.5D * (xB - xA);
/* 286 */         start = signChangeIndex - 1;
/* 287 */         end = signChangeIndex;
/*     */       } 
/*     */ 
/*     */       
/* 291 */       double nextY = computeObjectiveValue(nextX);
/* 292 */       if (Precision.equals(nextY, 0.0D, 1))
/*     */       {
/*     */         
/* 295 */         return nextX;
/*     */       }
/*     */       
/* 298 */       if (nbPoints > 2 && end - start != nbPoints) {
/*     */ 
/*     */ 
/*     */         
/* 302 */         nbPoints = end - start;
/* 303 */         System.arraycopy(x, start, x, 0, nbPoints);
/* 304 */         System.arraycopy(y, start, y, 0, nbPoints);
/* 305 */         signChangeIndex -= start;
/*     */       }
/* 307 */       else if (nbPoints == x.length) {
/*     */ 
/*     */         
/* 310 */         nbPoints--;
/*     */ 
/*     */         
/* 313 */         if (signChangeIndex >= (x.length + 1) / 2) {
/*     */           
/* 315 */           System.arraycopy(x, 1, x, 0, nbPoints);
/* 316 */           System.arraycopy(y, 1, y, 0, nbPoints);
/* 317 */           signChangeIndex--;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 324 */       System.arraycopy(x, signChangeIndex, x, signChangeIndex + 1, nbPoints - signChangeIndex);
/* 325 */       x[signChangeIndex] = nextX;
/* 326 */       System.arraycopy(y, signChangeIndex, y, signChangeIndex + 1, nbPoints - signChangeIndex);
/* 327 */       y[signChangeIndex] = nextY;
/* 328 */       nbPoints++;
/*     */ 
/*     */       
/* 331 */       if (nextY * yA <= 0.0D) {
/*     */         
/* 333 */         xB = nextX;
/* 334 */         yB = nextY;
/* 335 */         absYB = FastMath.abs(yB);
/* 336 */         agingA++;
/* 337 */         agingB = 0;
/*     */         continue;
/*     */       } 
/* 340 */       xA = nextX;
/* 341 */       yA = nextY;
/* 342 */       absYA = FastMath.abs(yA);
/* 343 */       agingA = 0;
/* 344 */       agingB++;
/*     */ 
/*     */       
/* 347 */       signChangeIndex++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double guessX(double targetY, double[] x, double[] y, int start, int end) {
/* 373 */     for (int i = start; i < end - 1; i++) {
/* 374 */       int delta = i + 1 - start;
/* 375 */       for (int k = end - 1; k > i; k--) {
/* 376 */         x[k] = (x[k] - x[k - 1]) / (y[k] - y[k - delta]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 381 */     double x0 = 0.0D;
/* 382 */     for (int j = end - 1; j >= start; j--) {
/* 383 */       x0 = x[j] + x0 * (targetY - y[j]);
/*     */     }
/*     */     
/* 386 */     return x0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, UnivariateFunction f, double min, double max, AllowedSolution allowedSolution) throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
/* 396 */     this.allowed = allowedSolution;
/* 397 */     return solve(maxEval, f, min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue, AllowedSolution allowedSolution) throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
/* 407 */     this.allowed = allowedSolution;
/* 408 */     return solve(maxEval, f, min, max, startValue);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BracketingNthOrderBrentSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */