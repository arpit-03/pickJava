/*     */ package org.apache.commons.math3.analysis.solvers;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooLargeException;
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
/*     */ public class BrentSolver
/*     */   extends AbstractUnivariateSolver
/*     */ {
/*     */   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;
/*     */   
/*     */   public BrentSolver() {
/*  54 */     this(1.0E-6D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrentSolver(double absoluteAccuracy) {
/*  62 */     super(absoluteAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrentSolver(double relativeAccuracy, double absoluteAccuracy) {
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
/*     */ 
/*     */   
/*     */   public BrentSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
/*  86 */     super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double doSolve() throws NoBracketingException, TooManyEvaluationsException, NumberIsTooLargeException {
/*  97 */     double min = getMin();
/*  98 */     double max = getMax();
/*  99 */     double initial = getStartValue();
/* 100 */     double functionValueAccuracy = getFunctionValueAccuracy();
/*     */     
/* 102 */     verifySequence(min, initial, max);
/*     */ 
/*     */     
/* 105 */     double yInitial = computeObjectiveValue(initial);
/* 106 */     if (FastMath.abs(yInitial) <= functionValueAccuracy) {
/* 107 */       return initial;
/*     */     }
/*     */ 
/*     */     
/* 111 */     double yMin = computeObjectiveValue(min);
/* 112 */     if (FastMath.abs(yMin) <= functionValueAccuracy) {
/* 113 */       return min;
/*     */     }
/*     */ 
/*     */     
/* 117 */     if (yInitial * yMin < 0.0D) {
/* 118 */       return brent(min, initial, yMin, yInitial);
/*     */     }
/*     */ 
/*     */     
/* 122 */     double yMax = computeObjectiveValue(max);
/* 123 */     if (FastMath.abs(yMax) <= functionValueAccuracy) {
/* 124 */       return max;
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (yInitial * yMax < 0.0D) {
/* 129 */       return brent(initial, max, yInitial, yMax);
/*     */     }
/*     */     
/* 132 */     throw new NoBracketingException(min, max, yMin, yMax);
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
/*     */   private double brent(double lo, double hi, double fLo, double fHi) {
/* 153 */     double a = lo;
/* 154 */     double fa = fLo;
/* 155 */     double b = hi;
/* 156 */     double fb = fHi;
/* 157 */     double c = a;
/* 158 */     double fc = fa;
/* 159 */     double d = b - a;
/* 160 */     double e = d;
/*     */     
/* 162 */     double t = getAbsoluteAccuracy();
/* 163 */     double eps = getRelativeAccuracy();
/*     */     
/*     */     while (true) {
/* 166 */       if (FastMath.abs(fc) < FastMath.abs(fb)) {
/* 167 */         a = b;
/* 168 */         b = c;
/* 169 */         c = a;
/* 170 */         fa = fb;
/* 171 */         fb = fc;
/* 172 */         fc = fa;
/*     */       } 
/*     */       
/* 175 */       double tol = 2.0D * eps * FastMath.abs(b) + t;
/* 176 */       double m = 0.5D * (c - b);
/*     */       
/* 178 */       if (FastMath.abs(m) <= tol || Precision.equals(fb, 0.0D))
/*     */       {
/* 180 */         return b;
/*     */       }
/* 182 */       if (FastMath.abs(e) < tol || FastMath.abs(fa) <= FastMath.abs(fb)) {
/*     */ 
/*     */         
/* 185 */         d = m;
/* 186 */         e = d;
/*     */       } else {
/* 188 */         double p, q, s = fb / fa;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 194 */         if (a == c) {
/*     */           
/* 196 */           p = 2.0D * m * s;
/* 197 */           q = 1.0D - s;
/*     */         } else {
/*     */           
/* 200 */           q = fa / fc;
/* 201 */           double r = fb / fc;
/* 202 */           p = s * (2.0D * m * q * (q - r) - (b - a) * (r - 1.0D));
/* 203 */           q = (q - 1.0D) * (r - 1.0D) * (s - 1.0D);
/*     */         } 
/* 205 */         if (p > 0.0D) {
/* 206 */           q = -q;
/*     */         } else {
/* 208 */           p = -p;
/*     */         } 
/* 210 */         s = e;
/* 211 */         e = d;
/* 212 */         if (p >= 1.5D * m * q - FastMath.abs(tol * q) || p >= FastMath.abs(0.5D * s * q)) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 217 */           d = m;
/* 218 */           e = d;
/*     */         } else {
/* 220 */           d = p / q;
/*     */         } 
/*     */       } 
/* 223 */       a = b;
/* 224 */       fa = fb;
/*     */       
/* 226 */       if (FastMath.abs(d) > tol) {
/* 227 */         b += d;
/* 228 */       } else if (m > 0.0D) {
/* 229 */         b += tol;
/*     */       } else {
/* 231 */         b -= tol;
/*     */       } 
/* 233 */       fb = computeObjectiveValue(b);
/* 234 */       if ((fb > 0.0D && fc > 0.0D) || (fb <= 0.0D && fc <= 0.0D)) {
/*     */         
/* 236 */         c = a;
/* 237 */         fc = fa;
/* 238 */         d = b - a;
/* 239 */         e = d;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/solvers/BrentSolver.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */