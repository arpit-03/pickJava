/*     */ package org.apache.commons.math3.optim.univariate;
/*     */ 
/*     */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NotStrictlyPositiveException;
/*     */ import org.apache.commons.math3.exception.TooManyEvaluationsException;
/*     */ import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.IntegerSequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BracketFinder
/*     */ {
/*     */   private static final double EPS_MIN = 1.0E-21D;
/*     */   private static final double GOLD = 1.618034D;
/*     */   private final double growLimit;
/*     */   private IntegerSequence.Incrementor evaluations;
/*     */   private double lo;
/*     */   private double hi;
/*     */   private double mid;
/*     */   private double fLo;
/*     */   private double fHi;
/*     */   private double fMid;
/*     */   
/*     */   public BracketFinder() {
/*  79 */     this(100.0D, 500);
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
/*  91 */     if (growLimit <= 0.0D) {
/*  92 */       throw new NotStrictlyPositiveException(Double.valueOf(growLimit));
/*     */     }
/*  94 */     if (maxEvaluations <= 0) {
/*  95 */       throw new NotStrictlyPositiveException(Integer.valueOf(maxEvaluations));
/*     */     }
/*     */     
/*  98 */     this.growLimit = growLimit;
/*  99 */     this.evaluations = IntegerSequence.Incrementor.create().withMaximalCount(maxEvaluations);
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
/*     */   public void search(UnivariateFunction func, GoalType goal, double xA, double xB) {
/* 116 */     this.evaluations = this.evaluations.withStart(0);
/* 117 */     boolean isMinim = (goal == GoalType.MINIMIZE);
/*     */     
/* 119 */     double fA = eval(func, xA);
/* 120 */     double fB = eval(func, xB);
/* 121 */     if (isMinim ? (fA < fB) : (fA > fB)) {
/*     */ 
/*     */ 
/*     */       
/* 125 */       double tmp = xA;
/* 126 */       xA = xB;
/* 127 */       xB = tmp;
/*     */       
/* 129 */       tmp = fA;
/* 130 */       fA = fB;
/* 131 */       fB = tmp;
/*     */     } 
/*     */     
/* 134 */     double xC = xB + 1.618034D * (xB - xA);
/* 135 */     double fC = eval(func, xC);
/*     */     
/* 137 */     while (isMinim ? (fC < fB) : (fC > fB)) {
/* 138 */       double fW, tmp1 = (xB - xA) * (fB - fC);
/* 139 */       double tmp2 = (xB - xC) * (fB - fA);
/*     */       
/* 141 */       double val = tmp2 - tmp1;
/* 142 */       double denom = (FastMath.abs(val) < 1.0E-21D) ? 2.0E-21D : (2.0D * val);
/*     */       
/* 144 */       double w = xB - ((xB - xC) * tmp2 - (xB - xA) * tmp1) / denom;
/* 145 */       double wLim = xB + this.growLimit * (xC - xB);
/*     */ 
/*     */       
/* 148 */       if ((w - xC) * (xB - w) > 0.0D) {
/* 149 */         fW = eval(func, w);
/* 150 */         if (isMinim ? (fW < fC) : (fW > fC)) {
/*     */ 
/*     */           
/* 153 */           xA = xB;
/* 154 */           xB = w;
/* 155 */           fA = fB;
/* 156 */           fB = fW; break;
/*     */         } 
/* 158 */         if (isMinim ? (fW > fB) : (fW < fB)) {
/*     */ 
/*     */           
/* 161 */           xC = w;
/* 162 */           fC = fW;
/*     */           break;
/*     */         } 
/* 165 */         w = xC + 1.618034D * (xC - xB);
/* 166 */         fW = eval(func, w);
/* 167 */       } else if ((w - wLim) * (wLim - xC) >= 0.0D) {
/* 168 */         w = wLim;
/* 169 */         fW = eval(func, w);
/* 170 */       } else if ((w - wLim) * (xC - w) > 0.0D) {
/* 171 */         fW = eval(func, w);
/* 172 */         if (isMinim ? (fW < fC) : (fW > fC)) {
/*     */ 
/*     */           
/* 175 */           xB = xC;
/* 176 */           xC = w;
/* 177 */           w = xC + 1.618034D * (xC - xB);
/* 178 */           fB = fC;
/* 179 */           fC = fW;
/* 180 */           fW = eval(func, w);
/*     */         } 
/*     */       } else {
/* 183 */         w = xC + 1.618034D * (xC - xB);
/* 184 */         fW = eval(func, w);
/*     */       } 
/*     */       
/* 187 */       xA = xB;
/* 188 */       fA = fB;
/* 189 */       xB = xC;
/* 190 */       fB = fC;
/* 191 */       xC = w;
/* 192 */       fC = fW;
/*     */     } 
/*     */     
/* 195 */     this.lo = xA;
/* 196 */     this.fLo = fA;
/* 197 */     this.mid = xB;
/* 198 */     this.fMid = fB;
/* 199 */     this.hi = xC;
/* 200 */     this.fHi = fC;
/*     */     
/* 202 */     if (this.lo > this.hi) {
/* 203 */       double tmp = this.lo;
/* 204 */       this.lo = this.hi;
/* 205 */       this.hi = tmp;
/*     */       
/* 207 */       tmp = this.fLo;
/* 208 */       this.fLo = this.fHi;
/* 209 */       this.fHi = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEvaluations() {
/* 217 */     return this.evaluations.getMaximalCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEvaluations() {
/* 224 */     return this.evaluations.getCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getLo() {
/* 232 */     return this.lo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFLo() {
/* 240 */     return this.fLo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getHi() {
/* 248 */     return this.hi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFHi() {
/* 256 */     return this.fHi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMid() {
/* 264 */     return this.mid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFMid() {
/* 272 */     return this.fMid;
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
/* 284 */       this.evaluations.increment();
/* 285 */     } catch (MaxCountExceededException e) {
/* 286 */       throw new TooManyEvaluationsException(e.getMax());
/*     */     } 
/* 288 */     return f.value(x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/univariate/BracketFinder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */