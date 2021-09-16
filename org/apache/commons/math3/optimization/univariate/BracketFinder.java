/*     */ package org.apache.commons.math3.optimization.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.optimization.GoalType;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Incrementor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class BracketFinder
/*     */ {
/*     */   private static final double EPS_MIN = 1.0E-21D;
/*     */   private static final double GOLD = 1.618034D;
/*     */   private final double growLimit;
/*  50 */   private final Incrementor evaluations = new Incrementor();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double lo;
/*     */ 
/*     */ 
/*     */   
/*     */   private double hi;
/*     */ 
/*     */ 
/*     */   
/*     */   private double mid;
/*     */ 
/*     */ 
/*     */   
/*     */   private double fLo;
/*     */ 
/*     */ 
/*     */   
/*     */   private double fHi;
/*     */ 
/*     */ 
/*     */   
/*     */   private double fMid;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BracketFinder() {
/*  81 */     this(100.0D, 50);
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
/*     */   public BracketFinder(double growLimit, int maxEvaluations) {
/*  93 */     if (growLimit <= 0.0D) {
/*  94 */       throw new NotStrictlyPositiveException(Double.valueOf(growLimit));
/*     */     }
/*  96 */     if (maxEvaluations <= 0) {
/*  97 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxEvaluations));
/*     */     }
/*     */     
/* 100 */     this.growLimit = growLimit;
/* 101 */     this.evaluations.setMaximalCount(maxEvaluations);
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
/*     */   public void search(UnivariateFunction func, GoalType goal, double xA, double xB) {
/* 115 */     this.evaluations.resetCount();
/* 116 */     boolean isMinim = (goal == GoalType.MINIMIZE);
/*     */     
/* 118 */     double fA = eval(func, xA);
/* 119 */     double fB = eval(func, xB);
/* 120 */     if (isMinim ? (fA < fB) : (fA > fB)) {
/*     */ 
/*     */ 
/*     */       
/* 124 */       double tmp = xA;
/* 125 */       xA = xB;
/* 126 */       xB = tmp;
/*     */       
/* 128 */       tmp = fA;
/* 129 */       fA = fB;
/* 130 */       fB = tmp;
/*     */     } 
/*     */     
/* 133 */     double xC = xB + 1.618034D * (xB - xA);
/* 134 */     double fC = eval(func, xC);
/*     */     
/* 136 */     while (isMinim ? (fC < fB) : (fC > fB)) {
/* 137 */       double fW, tmp1 = (xB - xA) * (fB - fC);
/* 138 */       double tmp2 = (xB - xC) * (fB - fA);
/*     */       
/* 140 */       double val = tmp2 - tmp1;
/* 141 */       double denom = (FastMath.abs(val) < 1.0E-21D) ? 2.0E-21D : (2.0D * val);
/*     */       
/* 143 */       double w = xB - ((xB - xC) * tmp2 - (xB - xA) * tmp1) / denom;
/* 144 */       double wLim = xB + this.growLimit * (xC - xB);
/*     */ 
/*     */       
/* 147 */       if ((w - xC) * (xB - w) > 0.0D) {
/* 148 */         fW = eval(func, w);
/* 149 */         if (isMinim ? (fW < fC) : (fW > fC)) {
/*     */ 
/*     */           
/* 152 */           xA = xB;
/* 153 */           xB = w;
/* 154 */           fA = fB;
/* 155 */           fB = fW; break;
/*     */         } 
/* 157 */         if (isMinim ? (fW > fB) : (fW < fB)) {
/*     */ 
/*     */           
/* 160 */           xC = w;
/* 161 */           fC = fW;
/*     */           break;
/*     */         } 
/* 164 */         w = xC + 1.618034D * (xC - xB);
/* 165 */         fW = eval(func, w);
/* 166 */       } else if ((w - wLim) * (wLim - xC) >= 0.0D) {
/* 167 */         w = wLim;
/* 168 */         fW = eval(func, w);
/* 169 */       } else if ((w - wLim) * (xC - w) > 0.0D) {
/* 170 */         fW = eval(func, w);
/* 171 */         if (isMinim ? (fW < fC) : (fW > fC)) {
/*     */ 
/*     */           
/* 174 */           xB = xC;
/* 175 */           xC = w;
/* 176 */           w = xC + 1.618034D * (xC - xB);
/* 177 */           fB = fC;
/* 178 */           fC = fW;
/* 179 */           fW = eval(func, w);
/*     */         } 
/*     */       } else {
/* 182 */         w = xC + 1.618034D * (xC - xB);
/* 183 */         fW = eval(func, w);
/*     */       } 
/*     */       
/* 186 */       xA = xB;
/* 187 */       fA = fB;
/* 188 */       xB = xC;
/* 189 */       fB = fC;
/* 190 */       xC = w;
/* 191 */       fC = fW;
/*     */     } 
/*     */     
/* 194 */     this.lo = xA;
/* 195 */     this.fLo = fA;
/* 196 */     this.mid = xB;
/* 197 */     this.fMid = fB;
/* 198 */     this.hi = xC;
/* 199 */     this.fHi = fC;
/*     */     
/* 201 */     if (this.lo > this.hi) {
/* 202 */       double tmp = this.lo;
/* 203 */       this.lo = this.hi;
/* 204 */       this.hi = tmp;
/*     */       
/* 206 */       tmp = this.fLo;
/* 207 */       this.fLo = this.fHi;
/* 208 */       this.fHi = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 216 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 223 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLo() {
/* 231 */     return this.lo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFLo() {
/* 239 */     return this.fLo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHi() {
/* 247 */     return this.hi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFHi() {
/* 255 */     return this.fHi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMid() {
/* 263 */     return this.mid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFMid() {
/* 271 */     return this.fMid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double eval(UnivariateFunction f, double x) {
/*     */     try {
/* 283 */       this.evaluations.incrementCount();
/* 284 */     } catch (MaxCountExceededException e) {
/* 285 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/* 287 */     return f.value(x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/univariate/BracketFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */