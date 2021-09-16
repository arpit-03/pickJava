/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.NoBracketingException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.ode.ExpandableStatefulODE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EmbeddedRungeKuttaIntegrator
/*     */   extends AdaptiveStepsizeIntegrator
/*     */ {
/*     */   private final boolean fsal;
/*     */   private final double[] c;
/*     */   private final double[][] a;
/*     */   private final double[] b;
/*     */   private final RungeKuttaStepInterpolator prototype;
/*     */   private final double exp;
/*     */   private double safety;
/*     */   private double minReduction;
/*     */   private double maxGrowth;
/*     */   
/*     */   protected EmbeddedRungeKuttaIntegrator(String name, boolean fsal, double[] c, double[][] a, double[] b, RungeKuttaStepInterpolator prototype, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
/* 116 */     super(name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
/*     */     
/* 118 */     this.fsal = fsal;
/* 119 */     this.c = c;
/* 120 */     this.a = a;
/* 121 */     this.b = b;
/* 122 */     this.prototype = prototype;
/*     */     
/* 124 */     this.exp = -1.0D / getOrder();
/*     */ 
/*     */     
/* 127 */     setSafety(0.9D);
/* 128 */     setMinReduction(0.2D);
/* 129 */     setMaxGrowth(10.0D);
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
/*     */   protected EmbeddedRungeKuttaIntegrator(String name, boolean fsal, double[] c, double[][] a, double[] b, RungeKuttaStepInterpolator prototype, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
/* 154 */     super(name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
/*     */     
/* 156 */     this.fsal = fsal;
/* 157 */     this.c = c;
/* 158 */     this.a = a;
/* 159 */     this.b = b;
/* 160 */     this.prototype = prototype;
/*     */     
/* 162 */     this.exp = -1.0D / getOrder();
/*     */ 
/*     */     
/* 165 */     setSafety(0.9D);
/* 166 */     setMinReduction(0.2D);
/* 167 */     setMaxGrowth(10.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getOrder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getSafety() {
/* 180 */     return this.safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSafety(double safety) {
/* 187 */     this.safety = safety;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
/* 196 */     sanityChecks(equations, t);
/* 197 */     setEquations(equations);
/* 198 */     boolean forward = (t > equations.getTime());
/*     */ 
/*     */     
/* 201 */     double[] y0 = equations.getCompleteState();
/* 202 */     double[] y = (double[])y0.clone();
/* 203 */     int stages = this.c.length + 1;
/* 204 */     double[][] yDotK = new double[stages][y.length];
/* 205 */     double[] yTmp = (double[])y0.clone();
/* 206 */     double[] yDotTmp = new double[y.length];
/*     */ 
/*     */     
/* 209 */     RungeKuttaStepInterpolator interpolator = (RungeKuttaStepInterpolator)this.prototype.copy();
/* 210 */     interpolator.reinitialize(this, yTmp, yDotK, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
/*     */     
/* 212 */     interpolator.storeTime(equations.getTime());
/*     */ 
/*     */     
/* 215 */     this.stepStart = equations.getTime();
/* 216 */     double hNew = 0.0D;
/* 217 */     boolean firstTime = true;
/* 218 */     initIntegration(equations.getTime(), y0, t);
/*     */ 
/*     */     
/* 221 */     this.isLastStep = false;
/*     */     
/*     */     do {
/* 224 */       interpolator.shift();
/*     */ 
/*     */       
/* 227 */       double error = 10.0D;
/* 228 */       while (error >= 1.0D) {
/*     */         
/* 230 */         if (firstTime || !this.fsal)
/*     */         {
/* 232 */           computeDerivatives(this.stepStart, y, yDotK[0]);
/*     */         }
/*     */         
/* 235 */         if (firstTime) {
/* 236 */           double[] scale = new double[this.mainSetDimension];
/* 237 */           if (this.vecAbsoluteTolerance == null) {
/* 238 */             for (int i = 0; i < scale.length; i++) {
/* 239 */               scale[i] = this.scalAbsoluteTolerance + this.scalRelativeTolerance * FastMath.abs(y[i]);
/*     */             }
/*     */           } else {
/* 242 */             for (int i = 0; i < scale.length; i++) {
/* 243 */               scale[i] = this.vecAbsoluteTolerance[i] + this.vecRelativeTolerance[i] * FastMath.abs(y[i]);
/*     */             }
/*     */           } 
/* 246 */           hNew = initializeStep(forward, getOrder(), scale, this.stepStart, y, yDotK[0], yTmp, yDotK[1]);
/*     */           
/* 248 */           firstTime = false;
/*     */         } 
/*     */         
/* 251 */         this.stepSize = hNew;
/* 252 */         if (forward) {
/* 253 */           if (this.stepStart + this.stepSize >= t) {
/* 254 */             this.stepSize = t - this.stepStart;
/*     */           }
/*     */         }
/* 257 */         else if (this.stepStart + this.stepSize <= t) {
/* 258 */           this.stepSize = t - this.stepStart;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 263 */         for (int k = 1; k < stages; k++) {
/*     */           
/* 265 */           for (int i = 0; i < y0.length; i++) {
/* 266 */             double sum = this.a[k - 1][0] * yDotK[0][i];
/* 267 */             for (int l = 1; l < k; l++) {
/* 268 */               sum += this.a[k - 1][l] * yDotK[l][i];
/*     */             }
/* 270 */             yTmp[i] = y[i] + this.stepSize * sum;
/*     */           } 
/*     */           
/* 273 */           computeDerivatives(this.stepStart + this.c[k - 1] * this.stepSize, yTmp, yDotK[k]);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 278 */         for (int j = 0; j < y0.length; j++) {
/* 279 */           double sum = this.b[0] * yDotK[0][j];
/* 280 */           for (int l = 1; l < stages; l++) {
/* 281 */             sum += this.b[l] * yDotK[l][j];
/*     */           }
/* 283 */           yTmp[j] = y[j] + this.stepSize * sum;
/*     */         } 
/*     */ 
/*     */         
/* 287 */         error = estimateError(yDotK, y, yTmp, this.stepSize);
/* 288 */         if (error >= 1.0D) {
/*     */           
/* 290 */           double d = FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp)));
/*     */ 
/*     */           
/* 293 */           hNew = filterStep(this.stepSize * d, forward, false);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 299 */       interpolator.storeTime(this.stepStart + this.stepSize);
/* 300 */       System.arraycopy(yTmp, 0, y, 0, y0.length);
/* 301 */       System.arraycopy(yDotK[stages - 1], 0, yDotTmp, 0, y0.length);
/* 302 */       this.stepStart = acceptStep(interpolator, y, yDotTmp, t);
/* 303 */       System.arraycopy(y, 0, yTmp, 0, y.length);
/*     */       
/* 305 */       if (this.isLastStep) {
/*     */         continue;
/*     */       }
/* 308 */       interpolator.storeTime(this.stepStart);
/*     */       
/* 310 */       if (this.fsal)
/*     */       {
/* 312 */         System.arraycopy(yDotTmp, 0, yDotK[0], 0, y0.length);
/*     */       }
/*     */ 
/*     */       
/* 316 */       double factor = FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp)));
/*     */       
/* 318 */       double scaledH = this.stepSize * factor;
/* 319 */       double nextT = this.stepStart + scaledH;
/* 320 */       boolean nextIsLast = forward ? ((nextT >= t)) : ((nextT <= t));
/* 321 */       hNew = filterStep(scaledH, forward, nextIsLast);
/*     */       
/* 323 */       double filteredNextT = this.stepStart + hNew;
/* 324 */       boolean filteredNextIsLast = forward ? ((filteredNextT >= t)) : ((filteredNextT <= t));
/* 325 */       if (!filteredNextIsLast)
/* 326 */         continue;  hNew = t - this.stepStart;
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 331 */     while (!this.isLastStep);
/*     */ 
/*     */     
/* 334 */     equations.setTime(this.stepStart);
/* 335 */     equations.setCompleteState(y);
/*     */     
/* 337 */     resetInternalState();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinReduction() {
/* 345 */     return this.minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinReduction(double minReduction) {
/* 352 */     this.minReduction = minReduction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaxGrowth() {
/* 359 */     return this.maxGrowth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxGrowth(double maxGrowth) {
/* 366 */     this.maxGrowth = maxGrowth;
/*     */   }
/*     */   
/*     */   protected abstract double estimateError(double[][] paramArrayOfdouble, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2, double paramDouble);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/EmbeddedRungeKuttaIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */