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
/*     */ class LutherStepInterpolator
/*     */   extends RungeKuttaStepInterpolator
/*     */ {
/*     */   private static final long serialVersionUID = 20140416L;
/*  41 */   private static final double Q = FastMath.sqrt(21.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LutherStepInterpolator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LutherStepInterpolator(LutherStepInterpolator interpolator) {
/*  66 */     super(interpolator);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StepInterpolator doCopy() {
/*  72 */     return (StepInterpolator)new LutherStepInterpolator(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
/* 124 */     double coeffDot1 = 1.0D + theta * (-10.8D + theta * (36.0D + theta * (-47.0D + theta * 21.0D)));
/* 125 */     double coeffDot2 = 0.0D;
/* 126 */     double coeffDot3 = theta * (-13.866666666666667D + theta * (106.66666666666667D + theta * (-202.66666666666666D + theta * 112.0D)));
/* 127 */     double coeffDot4 = theta * (12.96D + theta * (-97.2D + theta * (194.4D + theta * -567.0D / 5.0D)));
/* 128 */     double coeffDot5 = theta * ((833.0D + 343.0D * Q) / 150.0D + theta * ((-637.0D - 357.0D * Q) / 30.0D + theta * ((392.0D + 287.0D * Q) / 15.0D + theta * (-49.0D - 49.0D * Q) / 5.0D)));
/* 129 */     double coeffDot6 = theta * ((833.0D - 343.0D * Q) / 150.0D + theta * ((-637.0D + 357.0D * Q) / 30.0D + theta * ((392.0D - 287.0D * Q) / 15.0D + theta * (-49.0D + 49.0D * Q) / 5.0D)));
/* 130 */     double coeffDot7 = theta * (0.6D + theta * (-3.0D + theta * 3.0D));
/*     */     
/* 132 */     if (this.previousState != null && theta <= 0.5D) {
/*     */       
/* 134 */       double coeff1 = 1.0D + theta * (-5.4D + theta * (12.0D + theta * (-11.75D + theta * 21.0D / 5.0D)));
/* 135 */       double coeff2 = 0.0D;
/* 136 */       double coeff3 = theta * (-6.933333333333334D + theta * (35.55555555555556D + theta * (-50.666666666666664D + theta * 112.0D / 5.0D)));
/* 137 */       double coeff4 = theta * (6.48D + theta * (-32.4D + theta * (48.6D + theta * -567.0D / 25.0D)));
/* 138 */       double coeff5 = theta * ((833.0D + 343.0D * Q) / 300.0D + theta * ((-637.0D - 357.0D * Q) / 90.0D + theta * ((392.0D + 287.0D * Q) / 60.0D + theta * (-49.0D - 49.0D * Q) / 25.0D)));
/* 139 */       double coeff6 = theta * ((833.0D - 343.0D * Q) / 300.0D + theta * ((-637.0D + 357.0D * Q) / 90.0D + theta * ((392.0D - 287.0D * Q) / 60.0D + theta * (-49.0D + 49.0D * Q) / 25.0D)));
/* 140 */       double coeff7 = theta * (0.3D + theta * (-1.0D + theta * 0.75D));
/* 141 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 142 */         double yDot1 = this.yDotK[0][i];
/* 143 */         double yDot2 = this.yDotK[1][i];
/* 144 */         double yDot3 = this.yDotK[2][i];
/* 145 */         double yDot4 = this.yDotK[3][i];
/* 146 */         double yDot5 = this.yDotK[4][i];
/* 147 */         double yDot6 = this.yDotK[5][i];
/* 148 */         double yDot7 = this.yDotK[6][i];
/* 149 */         this.interpolatedState[i] = this.previousState[i] + theta * this.h * (coeff1 * yDot1 + 0.0D * yDot2 + coeff3 * yDot3 + coeff4 * yDot4 + coeff5 * yDot5 + coeff6 * yDot6 + coeff7 * yDot7);
/*     */ 
/*     */         
/* 152 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + 0.0D * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4 + coeffDot5 * yDot5 + coeffDot6 * yDot6 + coeffDot7 * yDot7;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 157 */       double coeff1 = -0.05D + theta * (0.95D + theta * (-4.45D + theta * (7.55D + theta * -21.0D / 5.0D)));
/* 158 */       double coeff2 = 0.0D;
/* 159 */       double coeff3 = -0.35555555555555557D + theta * (-0.35555555555555557D + theta * (-7.288888888888889D + theta * (28.266666666666666D + theta * -112.0D / 5.0D)));
/* 160 */       double coeff4 = theta * theta * (6.48D + theta * (-25.92D + theta * 567.0D / 25.0D));
/* 161 */       double coeff5 = -0.2722222222222222D + theta * (-0.2722222222222222D + theta * ((2254.0D + 1029.0D * Q) / 900.0D + theta * ((-1372.0D - 847.0D * Q) / 300.0D + theta * (49.0D + 49.0D * Q) / 25.0D)));
/* 162 */       double coeff6 = -0.2722222222222222D + theta * (-0.2722222222222222D + theta * ((2254.0D - 1029.0D * Q) / 900.0D + theta * ((-1372.0D + 847.0D * Q) / 300.0D + theta * (49.0D - 49.0D * Q) / 25.0D)));
/* 163 */       double coeff7 = -0.05D + theta * (-0.05D + theta * (0.25D + theta * -0.75D));
/* 164 */       for (int i = 0; i < this.interpolatedState.length; i++) {
/* 165 */         double yDot1 = this.yDotK[0][i];
/* 166 */         double yDot2 = this.yDotK[1][i];
/* 167 */         double yDot3 = this.yDotK[2][i];
/* 168 */         double yDot4 = this.yDotK[3][i];
/* 169 */         double yDot5 = this.yDotK[4][i];
/* 170 */         double yDot6 = this.yDotK[5][i];
/* 171 */         double yDot7 = this.yDotK[6][i];
/* 172 */         this.interpolatedState[i] = this.currentState[i] + oneMinusThetaH * (coeff1 * yDot1 + 0.0D * yDot2 + coeff3 * yDot3 + coeff4 * yDot4 + coeff5 * yDot5 + coeff6 * yDot6 + coeff7 * yDot7);
/*     */ 
/*     */         
/* 175 */         this.interpolatedDerivatives[i] = coeffDot1 * yDot1 + 0.0D * yDot2 + coeffDot3 * yDot3 + coeffDot4 * yDot4 + coeffDot5 * yDot5 + coeffDot6 * yDot6 + coeffDot7 * yDot7;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/LutherStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */