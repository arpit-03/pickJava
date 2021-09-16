/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.ode.sampling.StepInterpolator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MidpointStepInterpolator
/*     */   extends RungeKuttaStepInterpolator
/*     */ {
/*     */   private static final long serialVersionUID = 20111120L;
/*     */   
/*     */   public MidpointStepInterpolator() {}
/*     */   
/*     */   MidpointStepInterpolator(MidpointStepInterpolator interpolator) {
/*  76 */     super(interpolator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/*  82 */     return (StepInterpolator)new MidpointStepInterpolator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/*  91 */     double coeffDot2 = 2.0D * theta;
/*  92 */     double coeffDot1 = 1.0D - coeffDot2;
/*     */     
/*  94 */     if (this.previousState != null && theta <= 0.5D) {
/*  95 */       double coeff1 = theta * oneMinusThetaH;
/*  96 */       double coeff2 = theta * theta * this.h;
/*  97 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/*  98 */         double yDot1 = this.yDotK[0][i];
/*  99 */         double yDot2 = this.yDotK[1][i];
/* 100 */         this.interpolatedState[i] = this.previousState[i] + coeff1 * yDot1 + coeff2 * yDot2;
/* 101 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2;
/*     */       } 
/*     */     } else {
/* 104 */       double coeff1 = oneMinusThetaH * theta;
/* 105 */       double coeff2 = oneMinusThetaH * (1.0D + theta);
/* 106 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 107 */         double yDot1 = this.yDotK[0][i];
/* 108 */         double yDot2 = this.yDotK[1][i];
/* 109 */         this.interpolatedState[i] = this.currentState[i] + coeff1 * yDot1 - coeff2 * yDot2;
/* 110 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/MidpointStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */