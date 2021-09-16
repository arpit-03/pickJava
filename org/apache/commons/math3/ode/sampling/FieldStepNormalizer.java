/*     */ package org.apache.commons.math3.ode.sampling;
/*     */ 
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.MaxCountExceededException;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
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
/*     */ public class FieldStepNormalizer<T extends RealFieldElement<T>>
/*     */   implements FieldStepHandler<T>
/*     */ {
/*     */   private double h;
/*     */   private final FieldFixedStepHandler<T> handler;
/*     */   private FieldODEStateAndDerivative<T> first;
/*     */   private FieldODEStateAndDerivative<T> last;
/*     */   private boolean forward;
/*     */   private final StepNormalizerBounds bounds;
/*     */   private final StepNormalizerMode mode;
/*     */   
/*     */   public FieldStepNormalizer(double h, FieldFixedStepHandler<T> handler) {
/* 124 */     this(h, handler, StepNormalizerMode.INCREMENT, StepNormalizerBounds.FIRST);
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
/*     */   public FieldStepNormalizer(double h, FieldFixedStepHandler<T> handler, StepNormalizerMode mode) {
/* 137 */     this(h, handler, mode, StepNormalizerBounds.FIRST);
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
/*     */   public FieldStepNormalizer(double h, FieldFixedStepHandler<T> handler, StepNormalizerBounds bounds) {
/* 149 */     this(h, handler, StepNormalizerMode.INCREMENT, bounds);
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
/*     */   public FieldStepNormalizer(double h, FieldFixedStepHandler<T> handler, StepNormalizerMode mode, StepNormalizerBounds bounds) {
/* 161 */     this.h = FastMath.abs(h);
/* 162 */     this.handler = handler;
/* 163 */     this.mode = mode;
/* 164 */     this.bounds = bounds;
/* 165 */     this.first = null;
/* 166 */     this.last = null;
/* 167 */     this.forward = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(FieldODEStateAndDerivative<T> initialState, T finalTime) {
/* 173 */     this.first = null;
/* 174 */     this.last = null;
/* 175 */     this.forward = true;
/*     */ 
/*     */     
/* 178 */     this.handler.init(initialState, finalTime);
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
/*     */   public void handleStep(FieldStepInterpolator<T> interpolator, boolean isLast) throws MaxCountExceededException {
/* 198 */     if (this.last == null) {
/*     */       
/* 200 */       this.first = interpolator.getPreviousState();
/* 201 */       this.last = this.first;
/*     */ 
/*     */       
/* 204 */       this.forward = interpolator.isForward();
/* 205 */       if (!this.forward) {
/* 206 */         this.h = -this.h;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 211 */     RealFieldElement realFieldElement = (this.mode == StepNormalizerMode.INCREMENT) ? (RealFieldElement)this.last.getTime().add(this.h) : (RealFieldElement)((RealFieldElement)this.last.getTime().getField().getZero()).add((FastMath.floor(this.last.getTime().getReal() / this.h) + 1.0D) * this.h);
/*     */ 
/*     */     
/* 214 */     if (this.mode == StepNormalizerMode.MULTIPLES && Precision.equals(realFieldElement.getReal(), this.last.getTime().getReal(), 1))
/*     */     {
/* 216 */       realFieldElement = (RealFieldElement)realFieldElement.add(this.h);
/*     */     }
/*     */ 
/*     */     
/* 220 */     boolean nextInStep = isNextInStep((T)realFieldElement, interpolator);
/* 221 */     while (nextInStep) {
/*     */       
/* 223 */       doNormalizedStep(false);
/*     */ 
/*     */       
/* 226 */       this.last = interpolator.getInterpolatedState((T)realFieldElement);
/*     */ 
/*     */       
/* 229 */       realFieldElement = (RealFieldElement)realFieldElement.add(this.h);
/* 230 */       nextInStep = isNextInStep((T)realFieldElement, interpolator);
/*     */     } 
/*     */     
/* 233 */     if (isLast) {
/*     */ 
/*     */ 
/*     */       
/* 237 */       boolean addLast = (this.bounds.lastIncluded() && this.last.getTime().getReal() != interpolator.getCurrentState().getTime().getReal());
/*     */       
/* 239 */       doNormalizedStep(!addLast);
/* 240 */       if (addLast) {
/* 241 */         this.last = interpolator.getCurrentState();
/* 242 */         doNormalizedStep(true);
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
/*     */   private boolean isNextInStep(T nextTime, FieldStepInterpolator<T> interpolator) {
/* 257 */     return this.forward ? ((nextTime.getReal() <= interpolator.getCurrentState().getTime().getReal())) : ((nextTime.getReal() >= interpolator.getCurrentState().getTime().getReal()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doNormalizedStep(boolean isLast) {
/* 267 */     if (!this.bounds.firstIncluded() && this.first.getTime().getReal() == this.last.getTime().getReal()) {
/*     */       return;
/*     */     }
/* 270 */     this.handler.handleStep(this.last, isLast);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/sampling/FieldStepNormalizer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */