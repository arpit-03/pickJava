/*     */ package org.apache.commons.math3.ode.sampling;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StepNormalizer
/*     */   implements StepHandler
/*     */ {
/*     */   private double h;
/*     */   private final FixedStepHandler handler;
/*     */   private double firstTime;
/*     */   private double lastTime;
/*     */   private double[] lastState;
/*     */   private double[] lastDerivatives;
/*     */   private boolean forward;
/*     */   private final StepNormalizerBounds bounds;
/*     */   private final StepNormalizerMode mode;
/*     */   
/*     */   public StepNormalizer(double h, FixedStepHandler handler) {
/* 126 */     this(h, handler, StepNormalizerMode.INCREMENT, StepNormalizerBounds.FIRST);
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
/*     */   public StepNormalizer(double h, FixedStepHandler handler, StepNormalizerMode mode) {
/* 139 */     this(h, handler, mode, StepNormalizerBounds.FIRST);
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
/*     */   public StepNormalizer(double h, FixedStepHandler handler, StepNormalizerBounds bounds) {
/* 151 */     this(h, handler, StepNormalizerMode.INCREMENT, bounds);
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
/*     */   public StepNormalizer(double h, FixedStepHandler handler, StepNormalizerMode mode, StepNormalizerBounds bounds) {
/* 164 */     this.h = FastMath.abs(h);
/* 165 */     this.handler = handler;
/* 166 */     this.mode = mode;
/* 167 */     this.bounds = bounds;
/* 168 */     this.firstTime = Double.NaN;
/* 169 */     this.lastTime = Double.NaN;
/* 170 */     this.lastState = null;
/* 171 */     this.lastDerivatives = null;
/* 172 */     this.forward = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(double t0, double[] y0, double t) {
/* 178 */     this.firstTime = Double.NaN;
/* 179 */     this.lastTime = Double.NaN;
/* 180 */     this.lastState = null;
/* 181 */     this.lastDerivatives = null;
/* 182 */     this.forward = true;
/*     */ 
/*     */     
/* 185 */     this.handler.init(t0, y0, t);
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
/*     */   public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
/* 205 */     if (this.lastState == null) {
/* 206 */       this.firstTime = interpolator.getPreviousTime();
/* 207 */       this.lastTime = interpolator.getPreviousTime();
/* 208 */       interpolator.setInterpolatedTime(this.lastTime);
/* 209 */       this.lastState = (double[])interpolator.getInterpolatedState().clone();
/* 210 */       this.lastDerivatives = (double[])interpolator.getInterpolatedDerivatives().clone();
/*     */ 
/*     */       
/* 213 */       this.forward = (interpolator.getCurrentTime() >= this.lastTime);
/* 214 */       if (!this.forward) {
/* 215 */         this.h = -this.h;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 220 */     double nextTime = (this.mode == StepNormalizerMode.INCREMENT) ? (this.lastTime + this.h) : ((FastMath.floor(this.lastTime / this.h) + 1.0D) * this.h);
/*     */ 
/*     */     
/* 223 */     if (this.mode == StepNormalizerMode.MULTIPLES && Precision.equals(nextTime, this.lastTime, 1))
/*     */     {
/* 225 */       nextTime += this.h;
/*     */     }
/*     */ 
/*     */     
/* 229 */     boolean nextInStep = isNextInStep(nextTime, interpolator);
/* 230 */     while (nextInStep) {
/*     */       
/* 232 */       doNormalizedStep(false);
/*     */ 
/*     */       
/* 235 */       storeStep(interpolator, nextTime);
/*     */ 
/*     */       
/* 238 */       nextTime += this.h;
/* 239 */       nextInStep = isNextInStep(nextTime, interpolator);
/*     */     } 
/*     */     
/* 242 */     if (isLast) {
/*     */ 
/*     */ 
/*     */       
/* 246 */       boolean addLast = (this.bounds.lastIncluded() && this.lastTime != interpolator.getCurrentTime());
/*     */       
/* 248 */       doNormalizedStep(!addLast);
/* 249 */       if (addLast) {
/* 250 */         storeStep(interpolator, interpolator.getCurrentTime());
/* 251 */         doNormalizedStep(true);
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
/*     */   private boolean isNextInStep(double nextTime, StepInterpolator interpolator) {
/* 267 */     return this.forward ? ((nextTime <= interpolator.getCurrentTime())) : ((nextTime >= interpolator.getCurrentTime()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doNormalizedStep(boolean isLast) {
/* 277 */     if (!this.bounds.firstIncluded() && this.firstTime == this.lastTime) {
/*     */       return;
/*     */     }
/* 280 */     this.handler.handleStep(this.lastTime, this.lastState, this.lastDerivatives, isLast);
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
/*     */   private void storeStep(StepInterpolator interpolator, double t) throws MaxCountExceededException {
/* 293 */     this.lastTime = t;
/* 294 */     interpolator.setInterpolatedTime(this.lastTime);
/* 295 */     System.arraycopy(interpolator.getInterpolatedState(), 0, this.lastState, 0, this.lastState.length);
/*     */     
/* 297 */     System.arraycopy(interpolator.getInterpolatedDerivatives(), 0, this.lastDerivatives, 0, this.lastDerivatives.length);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/StepNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */