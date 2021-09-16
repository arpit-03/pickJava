/*    */ package org.apache.commons.math3.ode.nonstiff;
/*    */ 
/*    */ import org.apache.commons.math3.ode.sampling.StepInterpolator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class EulerStepInterpolator
/*    */   extends RungeKuttaStepInterpolator
/*    */ {
/*    */   private static final long serialVersionUID = 20111120L;
/*    */   
/*    */   public EulerStepInterpolator() {}
/*    */   
/*    */   EulerStepInterpolator(EulerStepInterpolator interpolator) {
/* 74 */     super(interpolator);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected StepInterpolator doCopy() {
/* 80 */     return (StepInterpolator)new EulerStepInterpolator(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/* 88 */     if (this.previousState != null && theta <= 0.5D) {
/* 89 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 90 */         this.interpolatedState[i] = this.previousState[i] + theta * this.h * this.yDotK[0][i];
/*    */       }
/* 92 */       System.arraycopy(this.yDotK[0], 0, this.interpolatedDerivatives, 0, this.interpolatedDerivatives.length);
/*    */     } else {
/* 94 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 95 */         this.interpolatedState[i] = this.currentState[i] - oneMinusThetaH * this.yDotK[0][i];
/*    */       }
/* 97 */       System.arraycopy(this.yDotK[0], 0, this.interpolatedDerivatives, 0, this.interpolatedDerivatives.length);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/EulerStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */