/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnivariateSolverUtils
/*     */ {
/*     */   public static double solve(UnivariateFunction function, double x0, double x1) throws NullArgumentException, NoBracketingException {
/*  52 */     if (function == null) {
/*  53 */       throw new NullArgumentException(LocalizedFormats.FUNCTION, new Object[0]);
/*     */     }
/*  55 */     UnivariateSolver solver = new BrentSolver();
/*  56 */     return solver.solve(2147483647, function, x0, x1);
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
/*     */   public static double solve(UnivariateFunction function, double x0, double x1, double absoluteAccuracy) throws NullArgumentException, NoBracketingException {
/*  77 */     if (function == null) {
/*  78 */       throw new NullArgumentException(LocalizedFormats.FUNCTION, new Object[0]);
/*     */     }
/*  80 */     UnivariateSolver solver = new BrentSolver(absoluteAccuracy);
/*  81 */     return solver.solve(2147483647, function, x0, x1);
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
/*     */   public static double forceSide(int maxEval, UnivariateFunction f, BracketedUnivariateSolver<UnivariateFunction> bracketing, double baseRoot, double min, double max, AllowedSolution allowedSolution) throws NoBracketingException {
/* 108 */     if (allowedSolution == AllowedSolution.ANY_SIDE)
/*     */     {
/* 110 */       return baseRoot;
/*     */     }
/*     */ 
/*     */     
/* 114 */     double step = FastMath.max(bracketing.getAbsoluteAccuracy(), FastMath.abs(baseRoot * bracketing.getRelativeAccuracy()));
/*     */     
/* 116 */     double xLo = FastMath.max(min, baseRoot - step);
/* 117 */     double fLo = f.value(xLo);
/* 118 */     double xHi = FastMath.min(max, baseRoot + step);
/* 119 */     double fHi = f.value(xHi);
/* 120 */     int remainingEval = maxEval - 2;
/* 121 */     while (remainingEval > 0) {
/*     */       
/* 123 */       if ((fLo >= 0.0D && fHi <= 0.0D) || (fLo <= 0.0D && fHi >= 0.0D))
/*     */       {
/* 125 */         return bracketing.solve(remainingEval, f, xLo, xHi, baseRoot, allowedSolution);
/*     */       }
/*     */ 
/*     */       
/* 129 */       boolean changeLo = false;
/* 130 */       boolean changeHi = false;
/* 131 */       if (fLo < fHi) {
/*     */         
/* 133 */         if (fLo >= 0.0D) {
/* 134 */           changeLo = true;
/*     */         } else {
/* 136 */           changeHi = true;
/*     */         } 
/* 138 */       } else if (fLo > fHi) {
/*     */         
/* 140 */         if (fLo <= 0.0D) {
/* 141 */           changeLo = true;
/*     */         } else {
/* 143 */           changeHi = true;
/*     */         } 
/*     */       } else {
/*     */         
/* 147 */         changeLo = true;
/* 148 */         changeHi = true;
/*     */       } 
/*     */ 
/*     */       
/* 152 */       if (changeLo) {
/* 153 */         xLo = FastMath.max(min, xLo - step);
/* 154 */         fLo = f.value(xLo);
/* 155 */         remainingEval--;
/*     */       } 
/*     */ 
/*     */       
/* 159 */       if (changeHi) {
/* 160 */         xHi = FastMath.min(max, xHi + step);
/* 161 */         fHi = f.value(xHi);
/* 162 */         remainingEval--;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 167 */     throw new NoBracketingException(LocalizedFormats.FAILED_BRACKETING, xLo, xHi, fLo, fHi, new Object[] { Integer.valueOf(maxEval - remainingEval), Integer.valueOf(maxEval), Double.valueOf(baseRoot), Double.valueOf(min), Double.valueOf(max) });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bracket(UnivariateFunction function, double initial, double lowerBound, double upperBound) throws NullArgumentException, NotStrictlyPositiveException, NoBracketingException {
/* 204 */     return bracket(function, initial, lowerBound, upperBound, 1.0D, 1.0D, 2147483647);
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
/*     */   public static double[] bracket(UnivariateFunction function, double initial, double lowerBound, double upperBound, int maximumIterations) throws NullArgumentException, NotStrictlyPositiveException, NoBracketingException {
/* 231 */     return bracket(function, initial, lowerBound, upperBound, 1.0D, 1.0D, maximumIterations);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double[] bracket(UnivariateFunction function, double initial, double lowerBound, double upperBound, double q, double r, int maximumIterations) throws NoBracketingException {
/* 300 */     if (function == null) {
/* 301 */       throw new NullArgumentException(LocalizedFormats.FUNCTION, new Object[0]);
/*     */     }
/* 303 */     if (q <= 0.0D) {
/* 304 */       throw new NotStrictlyPositiveException(Double.valueOf(q));
/*     */     }
/* 306 */     if (maximumIterations <= 0) {
/* 307 */       throw new NotStrictlyPositiveException(LocalizedFormats.INVALID_MAX_ITERATIONS, Integer.valueOf(maximumIterations));
/*     */     }
/* 309 */     verifySequence(lowerBound, initial, upperBound);
/*     */ 
/*     */     
/* 312 */     double a = initial;
/* 313 */     double b = initial;
/* 314 */     double fa = Double.NaN;
/* 315 */     double fb = Double.NaN;
/* 316 */     double delta = 0.0D;
/*     */     
/* 318 */     int numIterations = 0;
/* 319 */     for (; numIterations < maximumIterations && (a > lowerBound || b < upperBound); 
/* 320 */       numIterations++) {
/*     */       
/* 322 */       double previousA = a;
/* 323 */       double previousFa = fa;
/* 324 */       double previousB = b;
/* 325 */       double previousFb = fb;
/*     */       
/* 327 */       delta = r * delta + q;
/* 328 */       a = FastMath.max(initial - delta, lowerBound);
/* 329 */       b = FastMath.min(initial + delta, upperBound);
/* 330 */       fa = function.value(a);
/* 331 */       fb = function.value(b);
/*     */       
/* 333 */       if (numIterations == 0) {
/*     */ 
/*     */         
/* 336 */         if (fa * fb <= 0.0D)
/*     */         {
/* 338 */           return new double[] { a, b };
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 343 */         if (fa * previousFa <= 0.0D)
/*     */         {
/* 345 */           return new double[] { a, previousA }; } 
/* 346 */         if (fb * previousFb <= 0.0D)
/*     */         {
/* 348 */           return new double[] { previousB, b };
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 355 */     throw new NoBracketingException(a, b, fa, fb);
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
/*     */   public static double midpoint(double a, double b) {
/* 367 */     return (a + b) * 0.5D;
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
/*     */   public static boolean isBracketing(UnivariateFunction function, double lower, double upper) throws NullArgumentException {
/* 386 */     if (function == null) {
/* 387 */       throw new NullArgumentException(LocalizedFormats.FUNCTION, new Object[0]);
/*     */     }
/* 389 */     double fLo = function.value(lower);
/* 390 */     double fHi = function.value(upper);
/* 391 */     return ((fLo >= 0.0D && fHi <= 0.0D) || (fLo <= 0.0D && fHi >= 0.0D));
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
/*     */   public static boolean isSequence(double start, double mid, double end) {
/* 405 */     return (start < mid && mid < end);
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
/*     */   public static void verifyInterval(double lower, double upper) throws NumberIsTooLargeException {
/* 418 */     if (lower >= upper) {
/* 419 */       throw new NumberIsTooLargeException(LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, Double.valueOf(lower), Double.valueOf(upper), false);
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
/*     */   public static void verifySequence(double lower, double initial, double upper) throws NumberIsTooLargeException {
/* 437 */     verifyInterval(lower, initial);
/* 438 */     verifyInterval(initial, upper);
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
/*     */   public static void verifyBracketing(UnivariateFunction function, double lower, double upper) throws NullArgumentException, NoBracketingException {
/* 457 */     if (function == null) {
/* 458 */       throw new NullArgumentException(LocalizedFormats.FUNCTION, new Object[0]);
/*     */     }
/* 460 */     verifyInterval(lower, upper);
/* 461 */     if (!isBracketing(function, lower, upper))
/* 462 */       throw new NoBracketingException(lower, upper, function.value(lower), function.value(upper)); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/UnivariateSolverUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */