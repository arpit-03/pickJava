/*     */ package org.apache.commons.math3.optim.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.optim.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
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
/*     */ public class BrentOptimizer
/*     */   extends UnivariateOptimizer
/*     */ {
/*  47 */   private static final double GOLDEN_SECTION = 0.5D * (3.0D - FastMath.sqrt(5.0D));
/*     */ 
/*     */ 
/*     */   
/*  51 */   private static final double MIN_RELATIVE_TOLERANCE = 2.0D * FastMath.ulp(1.0D);
/*     */ 
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
/*     */ 
/*     */   
/*     */   private final double absoluteThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BrentOptimizer(double rel, double abs, ConvergenceChecker<UnivariatePointValuePair> checker) {
/*  80 */     super(checker);
/*     */     
/*  82 */     if (rel < MIN_RELATIVE_TOLERANCE) {
/*  83 */       throw new NumberIsTooSmallException(Double.valueOf(rel), Double.valueOf(MIN_RELATIVE_TOLERANCE), true);
/*     */     }
/*  85 */     if (abs <= 0.0D) {
/*  86 */       throw new NotStrictlyPositiveException(Double.valueOf(abs));
/*     */     }
/*     */     
/*  89 */     this.relativeThreshold = rel;
/*  90 */     this.absoluteThreshold = abs;
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
/*     */   public BrentOptimizer(double rel, double abs) {
/* 109 */     this(rel, abs, (ConvergenceChecker<UnivariatePointValuePair>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected UnivariatePointValuePair doOptimize() {
/*     */     double a, b;
/* 115 */     boolean isMinim = (getGoalType() == GoalType.MINIMIZE);
/* 116 */     double lo = getMin();
/* 117 */     double mid = getStartValue();
/* 118 */     double hi = getMax();
/*     */ 
/*     */     
/* 121 */     ConvergenceChecker<UnivariatePointValuePair> checker = getConvergenceChecker();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (lo < hi) {
/* 127 */       a = lo;
/* 128 */       b = hi;
/*     */     } else {
/* 130 */       a = hi;
/* 131 */       b = lo;
/*     */     } 
/*     */     
/* 134 */     double x = mid;
/* 135 */     double v = x;
/* 136 */     double w = x;
/* 137 */     double d = 0.0D;
/* 138 */     double e = 0.0D;
/* 139 */     double fx = computeObjectiveValue(x);
/* 140 */     if (!isMinim) {
/* 141 */       fx = -fx;
/*     */     }
/* 143 */     double fv = fx;
/* 144 */     double fw = fx;
/*     */     
/* 146 */     UnivariatePointValuePair previous = null;
/* 147 */     UnivariatePointValuePair current = new UnivariatePointValuePair(x, isMinim ? fx : -fx);
/*     */ 
/*     */     
/* 150 */     UnivariatePointValuePair best = current;
/*     */     
/*     */     while (true) {
/* 153 */       double m = 0.5D * (a + b);
/* 154 */       double tol1 = this.relativeThreshold * FastMath.abs(x) + this.absoluteThreshold;
/* 155 */       double tol2 = 2.0D * tol1;
/*     */ 
/*     */       
/* 158 */       boolean stop = (FastMath.abs(x - m) <= tol2 - 0.5D * (b - a));
/* 159 */       if (!stop) {
/* 160 */         double p = 0.0D;
/* 161 */         double q = 0.0D;
/* 162 */         double r = 0.0D;
/* 163 */         double u = 0.0D;
/*     */         
/* 165 */         if (FastMath.abs(e) > tol1) {
/* 166 */           r = (x - w) * (fx - fv);
/* 167 */           q = (x - v) * (fx - fw);
/* 168 */           p = (x - v) * q - (x - w) * r;
/* 169 */           q = 2.0D * (q - r);
/*     */           
/* 171 */           if (q > 0.0D) {
/* 172 */             p = -p;
/*     */           } else {
/* 174 */             q = -q;
/*     */           } 
/*     */           
/* 177 */           r = e;
/* 178 */           e = d;
/*     */           
/* 180 */           if (p > q * (a - x) && p < q * (b - x) && FastMath.abs(p) < FastMath.abs(0.5D * q * r)) {
/*     */ 
/*     */ 
/*     */             
/* 184 */             d = p / q;
/* 185 */             u = x + d;
/*     */ 
/*     */             
/* 188 */             if (u - a < tol2 || b - u < tol2) {
/* 189 */               if (x <= m) {
/* 190 */                 d = tol1;
/*     */               } else {
/* 192 */                 d = -tol1;
/*     */               } 
/*     */             }
/*     */           } else {
/*     */             
/* 197 */             if (x < m) {
/* 198 */               e = b - x;
/*     */             } else {
/* 200 */               e = a - x;
/*     */             } 
/* 202 */             d = GOLDEN_SECTION * e;
/*     */           } 
/*     */         } else {
/*     */           
/* 206 */           if (x < m) {
/* 207 */             e = b - x;
/*     */           } else {
/* 209 */             e = a - x;
/*     */           } 
/* 211 */           d = GOLDEN_SECTION * e;
/*     */         } 
/*     */ 
/*     */         
/* 215 */         if (FastMath.abs(d) < tol1) {
/* 216 */           if (d >= 0.0D) {
/* 217 */             u = x + tol1;
/*     */           } else {
/* 219 */             u = x - tol1;
/*     */           } 
/*     */         } else {
/* 222 */           u = x + d;
/*     */         } 
/*     */         
/* 225 */         double fu = computeObjectiveValue(u);
/* 226 */         if (!isMinim) {
/* 227 */           fu = -fu;
/*     */         }
/*     */ 
/*     */         
/* 231 */         previous = current;
/* 232 */         current = new UnivariatePointValuePair(u, isMinim ? fu : -fu);
/* 233 */         best = best(best, best(previous, current, isMinim), isMinim);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 239 */         if (checker != null && checker.converged(getIterations(), previous, current)) {
/* 240 */           return best;
/*     */         }
/*     */ 
/*     */         
/* 244 */         if (fu <= fx) {
/* 245 */           if (u < x) {
/* 246 */             b = x;
/*     */           } else {
/* 248 */             a = x;
/*     */           } 
/* 250 */           v = w;
/* 251 */           fv = fw;
/* 252 */           w = x;
/* 253 */           fw = fx;
/* 254 */           x = u;
/* 255 */           fx = fu;
/*     */         } else {
/* 257 */           if (u < x) {
/* 258 */             a = u;
/*     */           } else {
/* 260 */             b = u;
/*     */           } 
/* 262 */           if (fu <= fw || Precision.equals(w, x)) {
/*     */             
/* 264 */             v = w;
/* 265 */             fv = fw;
/* 266 */             w = u;
/* 267 */             fw = fu;
/* 268 */           } else if (fu <= fv || Precision.equals(v, x) || Precision.equals(v, w)) {
/*     */ 
/*     */             
/* 271 */             v = u;
/* 272 */             fv = fu;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 276 */         return best(best, best(previous, current, isMinim), isMinim);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       incrementIterationCount();
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
/*     */   private UnivariatePointValuePair best(UnivariatePointValuePair a, UnivariatePointValuePair b, boolean isMinim) {
/* 301 */     if (a == null) {
/* 302 */       return b;
/*     */     }
/* 304 */     if (b == null) {
/* 305 */       return a;
/*     */     }
/*     */     
/* 308 */     if (isMinim) {
/* 309 */       return (a.getValue() <= b.getValue()) ? a : b;
/*     */     }
/* 311 */     return (a.getValue() >= b.getValue()) ? a : b;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/BrentOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */