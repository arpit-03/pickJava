/*     */ package org.apache.commons.math3.optimization.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.optimization.ConvergenceChecker;
/*     */ import org.apache.commons.math3.optimization.GoalType;
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
/*     */ @Deprecated
/*     */ public class BrentOptimizer
/*     */   extends BaseAbstractUnivariateOptimizer
/*     */ {
/*  49 */   private static final double GOLDEN_SECTION = 0.5D * (3.0D - FastMath.sqrt(5.0D));
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static final double MIN_RELATIVE_TOLERANCE = 2.0D * FastMath.ulp(1.0D);
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
/*  82 */     super(checker);
/*     */     
/*  84 */     if (rel < MIN_RELATIVE_TOLERANCE) {
/*  85 */       throw new NumberIsTooSmallException(Double.valueOf(rel), Double.valueOf(MIN_RELATIVE_TOLERANCE), true);
/*     */     }
/*  87 */     if (abs <= 0.0D) {
/*  88 */       throw new NotStrictlyPositiveException(Double.valueOf(abs));
/*     */     }
/*     */     
/*  91 */     this.relativeThreshold = rel;
/*  92 */     this.absoluteThreshold = abs;
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
/* 111 */     this(rel, abs, (ConvergenceChecker<UnivariatePointValuePair>)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected UnivariatePointValuePair doOptimize() {
/*     */     double a, b;
/* 117 */     boolean isMinim = (getGoalType() == GoalType.MINIMIZE);
/* 118 */     double lo = getMin();
/* 119 */     double mid = getStartValue();
/* 120 */     double hi = getMax();
/*     */ 
/*     */     
/* 123 */     ConvergenceChecker<UnivariatePointValuePair> checker = getConvergenceChecker();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     if (lo < hi) {
/* 129 */       a = lo;
/* 130 */       b = hi;
/*     */     } else {
/* 132 */       a = hi;
/* 133 */       b = lo;
/*     */     } 
/*     */     
/* 136 */     double x = mid;
/* 137 */     double v = x;
/* 138 */     double w = x;
/* 139 */     double d = 0.0D;
/* 140 */     double e = 0.0D;
/* 141 */     double fx = computeObjectiveValue(x);
/* 142 */     if (!isMinim) {
/* 143 */       fx = -fx;
/*     */     }
/* 145 */     double fv = fx;
/* 146 */     double fw = fx;
/*     */     
/* 148 */     UnivariatePointValuePair previous = null;
/* 149 */     UnivariatePointValuePair current = new UnivariatePointValuePair(x, isMinim ? fx : -fx);
/*     */ 
/*     */     
/* 152 */     UnivariatePointValuePair best = current;
/*     */     
/* 154 */     int iter = 0;
/*     */     while (true) {
/* 156 */       double m = 0.5D * (a + b);
/* 157 */       double tol1 = this.relativeThreshold * FastMath.abs(x) + this.absoluteThreshold;
/* 158 */       double tol2 = 2.0D * tol1;
/*     */ 
/*     */       
/* 161 */       boolean stop = (FastMath.abs(x - m) <= tol2 - 0.5D * (b - a));
/* 162 */       if (!stop) {
/* 163 */         double p = 0.0D;
/* 164 */         double q = 0.0D;
/* 165 */         double r = 0.0D;
/* 166 */         double u = 0.0D;
/*     */         
/* 168 */         if (FastMath.abs(e) > tol1) {
/* 169 */           r = (x - w) * (fx - fv);
/* 170 */           q = (x - v) * (fx - fw);
/* 171 */           p = (x - v) * q - (x - w) * r;
/* 172 */           q = 2.0D * (q - r);
/*     */           
/* 174 */           if (q > 0.0D) {
/* 175 */             p = -p;
/*     */           } else {
/* 177 */             q = -q;
/*     */           } 
/*     */           
/* 180 */           r = e;
/* 181 */           e = d;
/*     */           
/* 183 */           if (p > q * (a - x) && p < q * (b - x) && FastMath.abs(p) < FastMath.abs(0.5D * q * r)) {
/*     */ 
/*     */ 
/*     */             
/* 187 */             d = p / q;
/* 188 */             u = x + d;
/*     */ 
/*     */             
/* 191 */             if (u - a < tol2 || b - u < tol2) {
/* 192 */               if (x <= m) {
/* 193 */                 d = tol1;
/*     */               } else {
/* 195 */                 d = -tol1;
/*     */               } 
/*     */             }
/*     */           } else {
/*     */             
/* 200 */             if (x < m) {
/* 201 */               e = b - x;
/*     */             } else {
/* 203 */               e = a - x;
/*     */             } 
/* 205 */             d = GOLDEN_SECTION * e;
/*     */           } 
/*     */         } else {
/*     */           
/* 209 */           if (x < m) {
/* 210 */             e = b - x;
/*     */           } else {
/* 212 */             e = a - x;
/*     */           } 
/* 214 */           d = GOLDEN_SECTION * e;
/*     */         } 
/*     */ 
/*     */         
/* 218 */         if (FastMath.abs(d) < tol1) {
/* 219 */           if (d >= 0.0D) {
/* 220 */             u = x + tol1;
/*     */           } else {
/* 222 */             u = x - tol1;
/*     */           } 
/*     */         } else {
/* 225 */           u = x + d;
/*     */         } 
/*     */         
/* 228 */         double fu = computeObjectiveValue(u);
/* 229 */         if (!isMinim) {
/* 230 */           fu = -fu;
/*     */         }
/*     */ 
/*     */         
/* 234 */         previous = current;
/* 235 */         current = new UnivariatePointValuePair(u, isMinim ? fu : -fu);
/* 236 */         best = best(best, best(previous, current, isMinim), isMinim);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 242 */         if (checker != null && checker.converged(iter, previous, current)) {
/* 243 */           return best;
/*     */         }
/*     */ 
/*     */         
/* 247 */         if (fu <= fx) {
/* 248 */           if (u < x) {
/* 249 */             b = x;
/*     */           } else {
/* 251 */             a = x;
/*     */           } 
/* 253 */           v = w;
/* 254 */           fv = fw;
/* 255 */           w = x;
/* 256 */           fw = fx;
/* 257 */           x = u;
/* 258 */           fx = fu;
/*     */         } else {
/* 260 */           if (u < x) {
/* 261 */             a = u;
/*     */           } else {
/* 263 */             b = u;
/*     */           } 
/* 265 */           if (fu <= fw || Precision.equals(w, x)) {
/*     */             
/* 267 */             v = w;
/* 268 */             fv = fw;
/* 269 */             w = u;
/* 270 */             fw = fu;
/* 271 */           } else if (fu <= fv || Precision.equals(v, x) || Precision.equals(v, w)) {
/*     */ 
/*     */             
/* 274 */             v = u;
/* 275 */             fv = fu;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 279 */         return best(best, best(previous, current, isMinim), isMinim);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 285 */       iter++;
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
/* 303 */     if (a == null) {
/* 304 */       return b;
/*     */     }
/* 306 */     if (b == null) {
/* 307 */       return a;
/*     */     }
/*     */     
/* 310 */     if (isMinim) {
/* 311 */       return (a.getValue() <= b.getValue()) ? a : b;
/*     */     }
/* 313 */     return (a.getValue() >= b.getValue()) ? a : b;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/BrentOptimizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */