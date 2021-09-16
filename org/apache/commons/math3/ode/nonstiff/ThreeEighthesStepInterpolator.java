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
/*     */ 
/*     */ 
/*     */ 
/*     */ class ThreeEighthesStepInterpolator
/*     */   extends RungeKuttaStepInterpolator
/*     */ {
/*     */   private static final long serialVersionUID = 20111120L;
/*     */   
/*     */   public ThreeEighthesStepInterpolator() {}
/*     */   
/*     */   ThreeEighthesStepInterpolator(ThreeEighthesStepInterpolator interpolator) {
/*  86 */     super(interpolator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/*  92 */     return (StepInterpolator)new ThreeEighthesStepInterpolator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/* 101 */     double coeffDot3 = 0.75D * theta;
/* 102 */     double coeffDot1 = coeffDot3 * (4.0D * theta - 5.0D) + 1.0D;
/* 103 */     double coeffDot2 = coeffDot3 * (5.0D - 6.0D * theta);
/* 104 */     double coeffDot4 = coeffDot3 * (2.0D * theta - 1.0D);
/*     */     
/* 106 */     if (this.previousState != null && theta <= 0.5D) {
/* 107 */       double s = theta * this.h / 8.0D;
/* 108 */       double fourTheta2 = 4.0D * theta * theta;
/* 109 */       double coeff1 = s * (8.0D - 15.0D * theta + 2.0D * fourTheta2);
/* 110 */       double coeff2 = 3.0D * s * (5.0D * theta - fourTheta2);
/* 111 */       double coeff3 = 3.0D * s * theta;
/* 112 */       double coeff4 = s * (-3.0D * theta + fourTheta2);
/* 113 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 114 */         double yDot1 = this.yDotK[0][i];
/* 115 */         double yDot2 = this.yDotK[1][i];
/* 116 */         double yDot3 = this.yDotK[2][i];
/* 117 */         double yDot4 = this.yDotK[3][i];
/* 118 */         this.interpolatedState[i] = this.previousState[i] + coeff1 * yDot1 + coeff2 * yDot2 + coeff3 * yDot3 + coeff4 * yDot4;
/*     */         
/* 120 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 125 */       double s = oneMinusThetaH / 8.0D;
/* 126 */       double fourTheta2 = 4.0D * theta * theta;
/* 127 */       double coeff1 = s * (1.0D - 7.0D * theta + 2.0D * fourTheta2);
/* 128 */       double coeff2 = 3.0D * s * (1.0D + theta - fourTheta2);
/* 129 */       double coeff3 = 3.0D * s * (1.0D + theta);
/* 130 */       double coeff4 = s * (1.0D + theta + fourTheta2);
/* 131 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 132 */         double yDot1 = this.yDotK[0][i];
/* 133 */         double yDot2 = this.yDotK[1][i];
/* 134 */         double yDot3 = this.yDotK[2][i];
/* 135 */         double yDot4 = this.yDotK[3][i];
/* 136 */         this.interpolatedState[i] = this.currentState[i] - coeff1 * yDot1 - coeff2 * yDot2 - coeff3 * yDot3 - coeff4 * yDot4;
/*     */         
/* 138 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/ThreeEighthesStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */