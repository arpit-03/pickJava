/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
/*     */ import org.apache.commons.math3.complex.Complex;
/*     */ import org.apache.commons.math3.complex.ComplexUtils;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NoDataException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
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
/*     */ public class LaguerreSolver
/*     */   extends AbstractPolynomialSolver
/*     */ {
/*     */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*  48 */   private final ComplexSolver complexSolver = new ComplexSolver();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LaguerreSolver() {
/*  54 */     this(1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LaguerreSolver(double absoluteAccuracy) {
/*  62 */     super(absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LaguerreSolver(double relativeAccuracy, double absoluteAccuracy) {
/*  72 */     super(relativeAccuracy, absoluteAccuracy);
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
/*     */   public LaguerreSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/*  84 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doSolve() throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
/*  95 */     double min = getMin();
/*  96 */     double max = getMax();
/*  97 */     double initial = getStartValue();
/*  98 */     double functionValueAccuracy = getFunctionValueAccuracy();
/*     */     
/* 100 */     verifySequence(min, initial, max);
/*     */ 
/*     */     
/* 103 */     double yInitial = computeObjectiveValue(initial);
/* 104 */     if (FastMath.abs(yInitial) <= functionValueAccuracy) {
/* 105 */       return initial;
/*     */     }
/*     */ 
/*     */     
/* 109 */     double yMin = computeObjectiveValue(min);
/* 110 */     if (FastMath.abs(yMin) <= functionValueAccuracy) {
/* 111 */       return min;
/*     */     }
/*     */ 
/*     */     
/* 115 */     if (yInitial * yMin < 0.0D) {
/* 116 */       return laguerre(min, initial, yMin, yInitial);
/*     */     }
/*     */ 
/*     */     
/* 120 */     double yMax = computeObjectiveValue(max);
/* 121 */     if (FastMath.abs(yMax) <= functionValueAccuracy) {
/* 122 */       return max;
/*     */     }
/*     */ 
/*     */     
/* 126 */     if (yInitial * yMax < 0.0D) {
/* 127 */       return laguerre(initial, max, yInitial, yMax);
/*     */     }
/*     */     
/* 130 */     throw new NoBracketingException(min, max, yMin, yMax);
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
/*     */   @Deprecated
/*     */   public double laguerre(double lo, double hi, double fLo, double fHi) {
/* 156 */     Complex[] c = ComplexUtils.convertToComplex(getCoefficients());
/*     */     
/* 158 */     Complex initial = new Complex(0.5D * (lo + hi), 0.0D);
/* 159 */     Complex z = this.complexSolver.solve(c, initial);
/* 160 */     if (this.complexSolver.isRoot(lo, hi, z)) {
/* 161 */       return z.getReal();
/*     */     }
/* 163 */     double r = Double.NaN;
/*     */     
/* 165 */     Complex[] root = this.complexSolver.solveAll(c, initial);
/* 166 */     for (int i = 0; i < root.length; i++) {
/* 167 */       if (this.complexSolver.isRoot(lo, hi, root[i])) {
/* 168 */         r = root[i].getReal();
/*     */         break;
/*     */       } 
/*     */     } 
/* 172 */     return r;
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
/*     */   public Complex[] solveAllComplex(double[] coefficients, double initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
/* 197 */     return solveAllComplex(coefficients, initial, 2147483647);
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
/*     */   public Complex[] solveAllComplex(double[] coefficients, double initial, int maxEval) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
/* 222 */     setup(maxEval, new PolynomialFunction(coefficients), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, initial);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 227 */     return this.complexSolver.solveAll(ComplexUtils.convertToComplex(coefficients), new Complex(initial, 0.0D));
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
/*     */   public Complex solveComplex(double[] coefficients, double initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
/* 252 */     return solveComplex(coefficients, initial, 2147483647);
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
/*     */   public Complex solveComplex(double[] coefficients, double initial, int maxEval) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
/* 277 */     setup(maxEval, new PolynomialFunction(coefficients), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, initial);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 282 */     return this.complexSolver.solve(ComplexUtils.convertToComplex(coefficients), new Complex(initial, 0.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ComplexSolver
/*     */   {
/*     */     private ComplexSolver() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isRoot(double min, double max, Complex z) {
/* 300 */       if (LaguerreSolver.this.isSequence(min, z.getReal(), max)) {
/* 301 */         double tolerance = FastMath.max(LaguerreSolver.this.getRelativeAccuracy() * z.abs(), LaguerreSolver.this.getAbsoluteAccuracy());
/* 302 */         return (FastMath.abs(z.getImaginary()) <= tolerance || z.abs() <= LaguerreSolver.this.getFunctionValueAccuracy());
/*     */       } 
/*     */       
/* 305 */       return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Complex[] solveAll(Complex[] coefficients, Complex initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
/* 325 */       if (coefficients == null) {
/* 326 */         throw new NullArgumentException();
/*     */       }
/* 328 */       int n = coefficients.length - 1;
/* 329 */       if (n == 0) {
/* 330 */         throw new NoDataException(LocalizedFormats.POLYNOMIAL);
/*     */       }
/*     */       
/* 333 */       Complex[] c = new Complex[n + 1];
/* 334 */       for (int i = 0; i <= n; i++) {
/* 335 */         c[i] = coefficients[i];
/*     */       }
/*     */ 
/*     */       
/* 339 */       Complex[] root = new Complex[n];
/* 340 */       for (int j = 0; j < n; j++) {
/* 341 */         Complex[] subarray = new Complex[n - j + 1];
/* 342 */         System.arraycopy(c, 0, subarray, 0, subarray.length);
/* 343 */         root[j] = solve(subarray, initial);
/*     */         
/* 345 */         Complex newc = c[n - j];
/* 346 */         Complex oldc = null;
/* 347 */         for (int k = n - j - 1; k >= 0; k--) {
/* 348 */           oldc = c[k];
/* 349 */           c[k] = newc;
/* 350 */           newc = oldc.add(newc.multiply(root[j]));
/*     */         } 
/*     */       } 
/*     */       
/* 354 */       return root;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Complex solve(Complex[] coefficients, Complex initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
/* 374 */       if (coefficients == null) {
/* 375 */         throw new NullArgumentException();
/*     */       }
/*     */       
/* 378 */       int n = coefficients.length - 1;
/* 379 */       if (n == 0) {
/* 380 */         throw new NoDataException(LocalizedFormats.POLYNOMIAL);
/*     */       }
/*     */       
/* 383 */       double absoluteAccuracy = LaguerreSolver.this.getAbsoluteAccuracy();
/* 384 */       double relativeAccuracy = LaguerreSolver.this.getRelativeAccuracy();
/* 385 */       double functionValueAccuracy = LaguerreSolver.this.getFunctionValueAccuracy();
/*     */       
/* 387 */       Complex nC = new Complex(n, 0.0D);
/* 388 */       Complex n1C = new Complex((n - 1), 0.0D);
/*     */       
/* 390 */       Complex z = initial;
/* 391 */       Complex oldz = new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 396 */         Complex pv = coefficients[n];
/* 397 */         Complex dv = Complex.ZERO;
/* 398 */         Complex d2v = Complex.ZERO;
/* 399 */         for (int j = n - 1; j >= 0; j--) {
/* 400 */           d2v = dv.add(z.multiply(d2v));
/* 401 */           dv = pv.add(z.multiply(dv));
/* 402 */           pv = coefficients[j].add(z.multiply(pv));
/*     */         } 
/* 404 */         d2v = d2v.multiply(new Complex(2.0D, 0.0D));
/*     */ 
/*     */         
/* 407 */         double tolerance = FastMath.max(relativeAccuracy * z.abs(), absoluteAccuracy);
/*     */         
/* 409 */         if (z.subtract(oldz).abs() <= tolerance) {
/* 410 */           return z;
/*     */         }
/* 412 */         if (pv.abs() <= functionValueAccuracy) {
/* 413 */           return z;
/*     */         }
/*     */ 
/*     */         
/* 417 */         Complex G = dv.divide(pv);
/* 418 */         Complex G2 = G.multiply(G);
/* 419 */         Complex H = G2.subtract(d2v.divide(pv));
/* 420 */         Complex delta = n1C.multiply(nC.multiply(H).subtract(G2));
/*     */         
/* 422 */         Complex deltaSqrt = delta.sqrt();
/* 423 */         Complex dplus = G.add(deltaSqrt);
/* 424 */         Complex dminus = G.subtract(deltaSqrt);
/* 425 */         Complex denominator = (dplus.abs() > dminus.abs()) ? dplus : dminus;
/*     */ 
/*     */         
/* 428 */         if (denominator.equals(new Complex(0.0D, 0.0D))) {
/* 429 */           z = z.add(new Complex(absoluteAccuracy, absoluteAccuracy));
/* 430 */           oldz = new Complex(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
/*     */         } else {
/*     */           
/* 433 */           oldz = z;
/* 434 */           z = z.subtract(nC.divide(denominator));
/*     */         } 
/* 436 */         LaguerreSolver.this.incrementEvaluationCount();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/LaguerreSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */