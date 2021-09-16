/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
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
/*     */ class GillStepInterpolator
/*     */   extends RungeKuttaStepInterpolator
/*     */ {
/*  59 */   private static final double ONE_MINUS_INV_SQRT_2 = 1.0D - FastMath.sqrt(0.5D);
/*     */ 
/*     */   
/*  62 */   private static final double ONE_PLUS_INV_SQRT_2 = 1.0D + FastMath.sqrt(0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 20111120L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GillStepInterpolator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   GillStepInterpolator(GillStepInterpolator interpolator) {
/*  90 */     super(interpolator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/*  96 */     return (StepInterpolator)new GillStepInterpolator(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/* 105 */     double twoTheta = 2.0D * theta;
/* 106 */     double fourTheta2 = twoTheta * twoTheta;
/* 107 */     double coeffDot1 = theta * (twoTheta - 3.0D) + 1.0D;
/* 108 */     double cDot23 = twoTheta * (1.0D - theta);
/* 109 */     double coeffDot2 = cDot23 * ONE_MINUS_INV_SQRT_2;
/* 110 */     double coeffDot3 = cDot23 * ONE_PLUS_INV_SQRT_2;
/* 111 */     double coeffDot4 = theta * (twoTheta - 1.0D);
/*     */     
/* 113 */     if (this.previousState != null && theta <= 0.5D) {
/* 114 */       double s = theta * this.h / 6.0D;
/* 115 */       double c23 = s * (6.0D * theta - fourTheta2);
/* 116 */       double coeff1 = s * (6.0D - 9.0D * theta + fourTheta2);
/* 117 */       double coeff2 = c23 * ONE_MINUS_INV_SQRT_2;
/* 118 */       double coeff3 = c23 * ONE_PLUS_INV_SQRT_2;
/* 119 */       double coeff4 = s * (-3.0D * theta + fourTheta2);
/* 120 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 121 */         double yDot1 = this.yDotK[0][i];
/* 122 */         double yDot2 = this.yDotK[1][i];
/* 123 */         double yDot3 = this.yDotK[2][i];
/* 124 */         double yDot4 = this.yDotK[3][i];
/* 125 */         this.interpolatedState[i] = this.previousState[i] + coeff1 * yDot1 + coeff2 * yDot2 + coeff3 * yDot3 + coeff4 * yDot4;
/*     */         
/* 127 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4;
/*     */       } 
/*     */     } else {
/*     */       
/* 131 */       double s = oneMinusThetaH / 6.0D;
/* 132 */       double c23 = s * (2.0D + twoTheta - fourTheta2);
/* 133 */       double coeff1 = s * (1.0D - 5.0D * theta + fourTheta2);
/* 134 */       double coeff2 = c23 * ONE_MINUS_INV_SQRT_2;
/* 135 */       double coeff3 = c23 * ONE_PLUS_INV_SQRT_2;
/* 136 */       double coeff4 = s * (1.0D + theta + fourTheta2);
/* 137 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 138 */         double yDot1 = this.yDotK[0][i];
/* 139 */         double yDot2 = this.yDotK[1][i];
/* 140 */         double yDot3 = this.yDotK[2][i];
/* 141 */         double yDot4 = this.yDotK[3][i];
/* 142 */         this.interpolatedState[i] = this.currentState[i] - coeff1 * yDot1 - coeff2 * yDot2 - coeff3 * yDot3 - coeff4 * yDot4;
/*     */         
/* 144 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + coeffDot2 * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/GillStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */