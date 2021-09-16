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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ClassicalRungeKuttaStepInterpolator
/*     */   extends RungeKuttaStepInterpolator
/*     */ {
/*     */   private static final long serialVersionUID = 20111120L;
/*     */   
/*     */   public ClassicalRungeKuttaStepInterpolator() {}
/*     */   
/*     */   ClassicalRungeKuttaStepInterpolator(ClassicalRungeKuttaStepInterpolator interpolator) {
/*  83 */     super(interpolator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/*  89 */     return (StepInterpolator)new ClassicalRungeKuttaStepInterpolator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/*  97 */     double oneMinusTheta = 1.0D - theta;
/*  98 */     double oneMinus2Theta = 1.0D - 2.0D * theta;
/*  99 */     double coeffDot1 = oneMinusTheta * oneMinus2Theta;
/* 100 */     double coeffDot23 = 2.0D * theta * oneMinusTheta;
/* 101 */     double coeffDot4 = -theta * oneMinus2Theta;
/* 102 */     if (this.previousState != null && theta <= 0.5D) {
/* 103 */       double fourTheta2 = 4.0D * theta * theta;
/* 104 */       double s = theta * this.h / 6.0D;
/* 105 */       double coeff1 = s * (6.0D - 9.0D * theta + fourTheta2);
/* 106 */       double coeff23 = s * (6.0D * theta - fourTheta2);
/* 107 */       double coeff4 = s * (-3.0D * theta + fourTheta2);
/* 108 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 109 */         double yDot1 = this.yDotK[0][i];
/* 110 */         double yDot23 = this.yDotK[1][i] + this.yDotK[2][i];
/* 111 */         double yDot4 = this.yDotK[3][i];
/* 112 */         this.interpolatedState[i] = this.previousState[i] + coeff1 * yDot1 + coeff23 * yDot23 + coeff4 * yDot4;
/*     */         
/* 114 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot23 * yDot23 + coeffDot4 * yDot4;
/*     */       } 
/*     */     } else {
/*     */       
/* 118 */       double fourTheta = 4.0D * theta;
/* 119 */       double s = oneMinusThetaH / 6.0D;
/* 120 */       double coeff1 = s * ((-fourTheta + 5.0D) * theta - 1.0D);
/* 121 */       double coeff23 = s * ((fourTheta - 2.0D) * theta - 2.0D);
/* 122 */       double coeff4 = s * ((-fourTheta - 1.0D) * theta - 1.0D);
/* 123 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 124 */         double yDot1 = this.yDotK[0][i];
/* 125 */         double yDot23 = this.yDotK[1][i] + this.yDotK[2][i];
/* 126 */         double yDot4 = this.yDotK[3][i];
/* 127 */         this.interpolatedState[i] = this.currentState[i] + coeff1 * yDot1 + coeff23 * yDot23 + coeff4 * yDot4;
/*     */         
/* 129 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot23 * yDot23 + coeffDot4 * yDot4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/ClassicalRungeKuttaStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */