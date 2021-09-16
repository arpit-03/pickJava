/*     */ package org.apache.commons.math3.ode;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepHandler;
/*     */ import org.apache.commons.math3.ode.sampling.FieldStepInterpolator;
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
/*     */ public class ContinuousOutputFieldModel<T extends RealFieldElement<T>>
/*     */   implements FieldStepHandler<T>
/*     */ {
/* 103 */   private List<FieldStepInterpolator<T>> steps = new ArrayList<FieldStepInterpolator<T>>();
/* 104 */   private T initialTime = null;
/* 105 */   private T finalTime = null;
/*     */   private boolean forward = true;
/* 107 */   private int index = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(ContinuousOutputFieldModel<T> model) throws MathIllegalArgumentException, MaxCountExceededException {
/* 123 */     if (model.steps.size() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     if (this.steps.size() == 0) {
/* 128 */       this.initialTime = model.initialTime;
/* 129 */       this.forward = model.forward;
/*     */     }
/*     */     else {
/*     */       
/* 133 */       FieldODEStateAndDerivative<T> s1 = ((FieldStepInterpolator)this.steps.get(0)).getPreviousState();
/* 134 */       FieldODEStateAndDerivative<T> s2 = ((FieldStepInterpolator)model.steps.get(0)).getPreviousState();
/* 135 */       checkDimensionsEquality(s1.getStateDimension(), s2.getStateDimension());
/* 136 */       checkDimensionsEquality(s1.getNumberOfSecondaryStates(), s2.getNumberOfSecondaryStates());
/* 137 */       for (int i = 0; i < s1.getNumberOfSecondaryStates(); i++) {
/* 138 */         checkDimensionsEquality(s1.getSecondaryStateDimension(i), s2.getSecondaryStateDimension(i));
/*     */       }
/*     */       
/* 141 */       if ((this.forward ^ model.forward) != 0) {
/* 142 */         throw new MathIllegalArgumentException(LocalizedFormats.PROPAGATION_DIRECTION_MISMATCH, new Object[0]);
/*     */       }
/*     */       
/* 145 */       FieldStepInterpolator<T> lastInterpolator = this.steps.get(this.index);
/* 146 */       T current = lastInterpolator.getCurrentState().getTime();
/* 147 */       T previous = lastInterpolator.getPreviousState().getTime();
/* 148 */       RealFieldElement realFieldElement1 = (RealFieldElement)current.subtract(previous);
/* 149 */       RealFieldElement realFieldElement2 = (RealFieldElement)model.getInitialTime().subtract(current);
/* 150 */       if (((RealFieldElement)((RealFieldElement)realFieldElement2.abs()).subtract(((RealFieldElement)realFieldElement1.abs()).multiply(0.001D))).getReal() > 0.0D) {
/* 151 */         throw new MathIllegalArgumentException(LocalizedFormats.HOLE_BETWEEN_MODELS_TIME_RANGES, new Object[] { Double.valueOf(((RealFieldElement)realFieldElement2.abs()).getReal()) });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 157 */     for (FieldStepInterpolator<T> interpolator : model.steps) {
/* 158 */       this.steps.add(interpolator);
/*     */     }
/*     */     
/* 161 */     this.index = this.steps.size() - 1;
/* 162 */     this.finalTime = ((FieldStepInterpolator)this.steps.get(this.index)).getCurrentState().getTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkDimensionsEquality(int d1, int d2) throws DimensionMismatchException {
/* 173 */     if (d1 != d2) {
/* 174 */       throw new DimensionMismatchException(d2, d1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void init(FieldODEStateAndDerivative<T> initialState, T t) {
/* 180 */     this.initialTime = initialState.getTime();
/* 181 */     this.finalTime = t;
/* 182 */     this.forward = true;
/* 183 */     this.index = 0;
/* 184 */     this.steps.clear();
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
/*     */   public void handleStep(FieldStepInterpolator<T> interpolator, boolean isLast) throws MaxCountExceededException {
/* 198 */     if (this.steps.size() == 0) {
/* 199 */       this.initialTime = interpolator.getPreviousState().getTime();
/* 200 */       this.forward = interpolator.isForward();
/*     */     } 
/*     */     
/* 203 */     this.steps.add(interpolator);
/*     */     
/* 205 */     if (isLast) {
/* 206 */       this.finalTime = interpolator.getCurrentState().getTime();
/* 207 */       this.index = this.steps.size() - 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getInitialTime() {
/* 217 */     return this.initialTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T getFinalTime() {
/* 225 */     return this.finalTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FieldODEStateAndDerivative<T> getInterpolatedState(T time) {
/* 236 */     int iMin = 0;
/* 237 */     FieldStepInterpolator<T> sMin = this.steps.get(iMin);
/* 238 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)sMin.getPreviousState().getTime().add(sMin.getCurrentState().getTime())).multiply(0.5D);
/*     */     
/* 240 */     int iMax = this.steps.size() - 1;
/* 241 */     FieldStepInterpolator<T> sMax = this.steps.get(iMax);
/* 242 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)sMax.getPreviousState().getTime().add(sMax.getCurrentState().getTime())).multiply(0.5D);
/*     */ 
/*     */ 
/*     */     
/* 246 */     if (locatePoint(time, sMin) <= 0) {
/* 247 */       this.index = iMin;
/* 248 */       return sMin.getInterpolatedState((RealFieldElement)time);
/*     */     } 
/* 250 */     if (locatePoint(time, sMax) >= 0) {
/* 251 */       this.index = iMax;
/* 252 */       return sMax.getInterpolatedState((RealFieldElement)time);
/*     */     } 
/*     */ 
/*     */     
/* 256 */     while (iMax - iMin > 5) {
/*     */ 
/*     */       
/* 259 */       FieldStepInterpolator<T> si = this.steps.get(this.index);
/* 260 */       int location = locatePoint(time, si);
/* 261 */       if (location < 0) {
/* 262 */         iMax = this.index;
/* 263 */         realFieldElement2 = (RealFieldElement)((RealFieldElement)si.getPreviousState().getTime().add(si.getCurrentState().getTime())).multiply(0.5D);
/* 264 */       } else if (location > 0) {
/* 265 */         iMin = this.index;
/* 266 */         realFieldElement1 = (RealFieldElement)((RealFieldElement)si.getPreviousState().getTime().add(si.getCurrentState().getTime())).multiply(0.5D);
/*     */       } else {
/*     */         
/* 269 */         return si.getInterpolatedState((RealFieldElement)time);
/*     */       } 
/*     */ 
/*     */       
/* 273 */       int iMed = (iMin + iMax) / 2;
/* 274 */       FieldStepInterpolator<T> sMed = this.steps.get(iMed);
/* 275 */       RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)sMed.getPreviousState().getTime().add(sMed.getCurrentState().getTime())).multiply(0.5D);
/*     */       
/* 277 */       if (((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.subtract(realFieldElement1)).abs()).subtract(1.0E-6D)).getReal() < 0.0D || ((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.subtract(realFieldElement)).abs()).subtract(1.0E-6D)).getReal() < 0.0D) {
/*     */ 
/*     */         
/* 280 */         this.index = iMed;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 285 */         RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement2.subtract(realFieldElement);
/* 286 */         RealFieldElement realFieldElement4 = (RealFieldElement)realFieldElement.subtract(realFieldElement1);
/* 287 */         RealFieldElement realFieldElement5 = (RealFieldElement)realFieldElement2.subtract(realFieldElement1);
/* 288 */         RealFieldElement realFieldElement6 = (RealFieldElement)time.subtract(realFieldElement2);
/* 289 */         RealFieldElement realFieldElement7 = (RealFieldElement)time.subtract(realFieldElement);
/* 290 */         RealFieldElement realFieldElement8 = (RealFieldElement)time.subtract(realFieldElement1);
/* 291 */         RealFieldElement realFieldElement9 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement7.multiply(realFieldElement8)).multiply(realFieldElement4)).multiply(iMax)).subtract(((RealFieldElement)((RealFieldElement)realFieldElement6.multiply(realFieldElement8)).multiply(realFieldElement5)).multiply(iMed))).add(((RealFieldElement)((RealFieldElement)realFieldElement6.multiply(realFieldElement7)).multiply(realFieldElement3)).multiply(iMin))).divide(((RealFieldElement)realFieldElement3.multiply(realFieldElement4)).multiply(realFieldElement5));
/*     */ 
/*     */ 
/*     */         
/* 295 */         this.index = (int)FastMath.rint(realFieldElement9.getReal());
/*     */       } 
/*     */ 
/*     */       
/* 299 */       int low = FastMath.max(iMin + 1, (9 * iMin + iMax) / 10);
/* 300 */       int high = FastMath.min(iMax - 1, (iMin + 9 * iMax) / 10);
/* 301 */       if (this.index < low) {
/* 302 */         this.index = low; continue;
/* 303 */       }  if (this.index > high) {
/* 304 */         this.index = high;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 310 */     this.index = iMin;
/* 311 */     while (this.index <= iMax && locatePoint(time, this.steps.get(this.index)) > 0) {
/* 312 */       this.index++;
/*     */     }
/*     */     
/* 315 */     return ((FieldStepInterpolator)this.steps.get(this.index)).getInterpolatedState((RealFieldElement)time);
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
/*     */   private int locatePoint(T time, FieldStepInterpolator<T> interval) {
/* 327 */     if (this.forward) {
/* 328 */       if (((RealFieldElement)time.subtract(interval.getPreviousState().getTime())).getReal() < 0.0D)
/* 329 */         return -1; 
/* 330 */       if (((RealFieldElement)time.subtract(interval.getCurrentState().getTime())).getReal() > 0.0D) {
/* 331 */         return 1;
/*     */       }
/* 333 */       return 0;
/*     */     } 
/*     */     
/* 336 */     if (((RealFieldElement)time.subtract(interval.getPreviousState().getTime())).getReal() > 0.0D)
/* 337 */       return -1; 
/* 338 */     if (((RealFieldElement)time.subtract(interval.getCurrentState().getTime())).getReal() < 0.0D) {
/* 339 */       return 1;
/*     */     }
/* 341 */     return 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/ContinuousOutputFieldModel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */