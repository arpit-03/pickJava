/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ode.sampling.StepHandler;
/*     */ import org.apache.commons.math3.ode.sampling.StepInterpolator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContinuousOutputModel
/*     */   implements StepHandler, Serializable
/*     */ {
/* 114 */   private List<StepInterpolator> steps = new ArrayList<StepInterpolator>();
/* 115 */   private double initialTime = Double.NaN;
/* 116 */   private double finalTime = Double.NaN;
/*     */   private boolean forward = true;
/* 118 */   private int index = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = -1417964919405031606L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(ContinuousOutputModel model) throws MathIllegalArgumentException, MaxCountExceededException {
/* 132 */     if (model.steps.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 136 */     if (this.steps.size() == 0) {
/* 137 */       this.initialTime = model.initialTime;
/* 138 */       this.forward = model.forward;
/*     */     } else {
/*     */       
/* 141 */       if ((getInterpolatedState()).length != (model.getInterpolatedState()).length) {
/* 142 */         throw new DimensionMismatchException((model.getInterpolatedState()).length, (getInterpolatedState()).length);
/*     */       }
/*     */ 
/*     */       
/* 146 */       if ((this.forward ^ model.forward) != 0) {
/* 147 */         throw new MathIllegalArgumentException(LocalizedFormats.PROPAGATION_DIRECTION_MISMATCH, new Object[0]);
/*     */       }
/*     */       
/* 150 */       StepInterpolator lastInterpolator = this.steps.get(this.index);
/* 151 */       double current = lastInterpolator.getCurrentTime();
/* 152 */       double previous = lastInterpolator.getPreviousTime();
/* 153 */       double step = current - previous;
/* 154 */       double gap = model.getInitialTime() - current;
/* 155 */       if (FastMath.abs(gap) > 0.001D * FastMath.abs(step)) {
/* 156 */         throw new MathIllegalArgumentException(LocalizedFormats.HOLE_BETWEEN_MODELS_TIME_RANGES, new Object[] { Double.valueOf(FastMath.abs(gap)) });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 162 */     for (StepInterpolator interpolator : model.steps) {
/* 163 */       this.steps.add(interpolator.copy());
/*     */     }
/*     */     
/* 166 */     this.index = this.steps.size() - 1;
/* 167 */     this.finalTime = ((StepInterpolator)this.steps.get(this.index)).getCurrentTime();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(double t0, double[] y0, double t) {
/* 173 */     this.initialTime = Double.NaN;
/* 174 */     this.finalTime = Double.NaN;
/* 175 */     this.forward = true;
/* 176 */     this.index = 0;
/* 177 */     this.steps.clear();
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
/*     */   public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
/* 191 */     if (this.steps.size() == 0) {
/* 192 */       this.initialTime = interpolator.getPreviousTime();
/* 193 */       this.forward = interpolator.isForward();
/*     */     } 
/*     */     
/* 196 */     this.steps.add(interpolator.copy());
/*     */     
/* 198 */     if (isLast) {
/* 199 */       this.finalTime = interpolator.getCurrentTime();
/* 200 */       this.index = this.steps.size() - 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInitialTime() {
/* 210 */     return this.initialTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFinalTime() {
/* 218 */     return this.finalTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getInterpolatedTime() {
/* 228 */     return ((StepInterpolator)this.steps.get(this.index)).getInterpolatedTime();
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
/*     */   public void setInterpolatedTime(double time) {
/* 253 */     int iMin = 0;
/* 254 */     StepInterpolator sMin = this.steps.get(iMin);
/* 255 */     double tMin = 0.5D * (sMin.getPreviousTime() + sMin.getCurrentTime());
/*     */     
/* 257 */     int iMax = this.steps.size() - 1;
/* 258 */     StepInterpolator sMax = this.steps.get(iMax);
/* 259 */     double tMax = 0.5D * (sMax.getPreviousTime() + sMax.getCurrentTime());
/*     */ 
/*     */ 
/*     */     
/* 263 */     if (locatePoint(time, sMin) <= 0) {
/* 264 */       this.index = iMin;
/* 265 */       sMin.setInterpolatedTime(time);
/*     */       return;
/*     */     } 
/* 268 */     if (locatePoint(time, sMax) >= 0) {
/* 269 */       this.index = iMax;
/* 270 */       sMax.setInterpolatedTime(time);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 275 */     while (iMax - iMin > 5) {
/*     */ 
/*     */       
/* 278 */       StepInterpolator si = this.steps.get(this.index);
/* 279 */       int location = locatePoint(time, si);
/* 280 */       if (location < 0) {
/* 281 */         iMax = this.index;
/* 282 */         tMax = 0.5D * (si.getPreviousTime() + si.getCurrentTime());
/* 283 */       } else if (location > 0) {
/* 284 */         iMin = this.index;
/* 285 */         tMin = 0.5D * (si.getPreviousTime() + si.getCurrentTime());
/*     */       } else {
/*     */         
/* 288 */         si.setInterpolatedTime(time);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 293 */       int iMed = (iMin + iMax) / 2;
/* 294 */       StepInterpolator sMed = this.steps.get(iMed);
/* 295 */       double tMed = 0.5D * (sMed.getPreviousTime() + sMed.getCurrentTime());
/*     */       
/* 297 */       if (FastMath.abs(tMed - tMin) < 1.0E-6D || FastMath.abs(tMax - tMed) < 1.0E-6D) {
/*     */         
/* 299 */         this.index = iMed;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 304 */         double d12 = tMax - tMed;
/* 305 */         double d23 = tMed - tMin;
/* 306 */         double d13 = tMax - tMin;
/* 307 */         double dt1 = time - tMax;
/* 308 */         double dt2 = time - tMed;
/* 309 */         double dt3 = time - tMin;
/* 310 */         double iLagrange = (dt2 * dt3 * d23 * iMax - dt1 * dt3 * d13 * iMed + dt1 * dt2 * d12 * iMin) / d12 * d23 * d13;
/*     */ 
/*     */ 
/*     */         
/* 314 */         this.index = (int)FastMath.rint(iLagrange);
/*     */       } 
/*     */ 
/*     */       
/* 318 */       int low = FastMath.max(iMin + 1, (9 * iMin + iMax) / 10);
/* 319 */       int high = FastMath.min(iMax - 1, (iMin + 9 * iMax) / 10);
/* 320 */       if (this.index < low) {
/* 321 */         this.index = low; continue;
/* 322 */       }  if (this.index > high) {
/* 323 */         this.index = high;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 329 */     this.index = iMin;
/* 330 */     while (this.index <= iMax && locatePoint(time, this.steps.get(this.index)) > 0) {
/* 331 */       this.index++;
/*     */     }
/*     */     
/* 334 */     ((StepInterpolator)this.steps.get(this.index)).setInterpolatedTime(time);
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
/*     */   public double[] getInterpolatedState() throws MaxCountExceededException {
/* 352 */     return ((StepInterpolator)this.steps.get(this.index)).getInterpolatedState();
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
/*     */   public double[] getInterpolatedDerivatives() throws MaxCountExceededException {
/* 370 */     return ((StepInterpolator)this.steps.get(this.index)).getInterpolatedDerivatives();
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
/*     */   public double[] getInterpolatedSecondaryState(int secondaryStateIndex) throws MaxCountExceededException {
/* 392 */     return ((StepInterpolator)this.steps.get(this.index)).getInterpolatedSecondaryState(secondaryStateIndex);
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
/*     */   public double[] getInterpolatedSecondaryDerivatives(int secondaryStateIndex) throws MaxCountExceededException {
/* 414 */     return ((StepInterpolator)this.steps.get(this.index)).getInterpolatedSecondaryDerivatives(secondaryStateIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int locatePoint(double time, StepInterpolator interval) {
/* 425 */     if (this.forward) {
/* 426 */       if (time < interval.getPreviousTime())
/* 427 */         return -1; 
/* 428 */       if (time > interval.getCurrentTime()) {
/* 429 */         return 1;
/*     */       }
/* 431 */       return 0;
/*     */     } 
/*     */     
/* 434 */     if (time > interval.getPreviousTime())
/* 435 */       return -1; 
/* 436 */     if (time < interval.getCurrentTime()) {
/* 437 */       return 1;
/*     */     }
/* 439 */     return 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ContinuousOutputModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */