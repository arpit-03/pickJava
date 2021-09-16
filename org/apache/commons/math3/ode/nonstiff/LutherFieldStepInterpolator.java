/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LutherFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   private final T c5a;
/*     */   private final T c5b;
/*     */   private final T c5c;
/*     */   private final T c5d;
/*     */   private final T c6a;
/*     */   private final T c6b;
/*     */   private final T c6c;
/*     */   private final T c6d;
/*     */   private final T d5a;
/*     */   private final T d5b;
/*     */   private final T d5c;
/*     */   private final T d6a;
/*     */   private final T d6b;
/*     */   private final T d6c;
/*     */   
/*     */   LutherFieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/* 100 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
/*     */ 
/*     */     
/* 103 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)field.getZero()).add(21.0D)).sqrt();
/* 104 */     this.c5a = (T)((RealFieldElement)realFieldElement.multiply(-49)).add(-49.0D);
/* 105 */     this.c5b = (T)((RealFieldElement)realFieldElement.multiply(287)).add(392.0D);
/* 106 */     this.c5c = (T)((RealFieldElement)realFieldElement.multiply(-357)).add(-637.0D);
/* 107 */     this.c5d = (T)((RealFieldElement)realFieldElement.multiply(343)).add(833.0D);
/* 108 */     this.c6a = (T)((RealFieldElement)realFieldElement.multiply(49)).add(-49.0D);
/* 109 */     this.c6b = (T)((RealFieldElement)realFieldElement.multiply(-287)).add(392.0D);
/* 110 */     this.c6c = (T)((RealFieldElement)realFieldElement.multiply(357)).add(-637.0D);
/* 111 */     this.c6d = (T)((RealFieldElement)realFieldElement.multiply(-343)).add(833.0D);
/* 112 */     this.d5a = (T)((RealFieldElement)realFieldElement.multiply(49)).add(49.0D);
/* 113 */     this.d5b = (T)((RealFieldElement)realFieldElement.multiply(-847)).add(-1372.0D);
/* 114 */     this.d5c = (T)((RealFieldElement)realFieldElement.multiply(1029)).add(2254.0D);
/* 115 */     this.d6a = (T)((RealFieldElement)realFieldElement.multiply(-49)).add(49.0D);
/* 116 */     this.d6b = (T)((RealFieldElement)realFieldElement.multiply(847)).add(-1372.0D);
/* 117 */     this.d6c = (T)((RealFieldElement)realFieldElement.multiply(-1029)).add(2254.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LutherFieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/* 128 */     return new LutherFieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/*     */ 
/*     */ 
/*     */   
/*     */   protected FieldODEStateAndDerivative<T> computeInterpolatedStateAndDerivatives(FieldEquationsMapper<T> mapper, T time, T theta, T thetaH, T oneMinusThetaH) {
/*     */     T[] interpolatedState, interpolatedDerivatives;
/* 184 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(21)).add(-47.0D))).add(36.0D))).add(-10.8D))).add(1.0D);
/* 185 */     RealFieldElement realFieldElement2 = (RealFieldElement)time.getField().getZero();
/* 186 */     RealFieldElement realFieldElement3 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(112)).add(-202.66666666666666D))).add(106.66666666666667D))).add(-13.866666666666667D));
/* 187 */     RealFieldElement realFieldElement4 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-113.4D)).add(194.4D))).add(-97.2D))).add(12.96D));
/* 188 */     RealFieldElement realFieldElement5 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(this.c5a.divide(5.0D))).add(this.c5b.divide(15.0D)))).add(this.c5c.divide(30.0D)))).add(this.c5d.divide(150.0D)));
/* 189 */     RealFieldElement realFieldElement6 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(this.c6a.divide(5.0D))).add(this.c6b.divide(15.0D)))).add(this.c6c.divide(30.0D)))).add(this.c6d.divide(150.0D)));
/* 190 */     RealFieldElement realFieldElement7 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(3.0D)).add(-3.0D))).add(0.6D));
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/*     */       
/* 196 */       T s = thetaH;
/* 197 */       RealFieldElement realFieldElement8 = (RealFieldElement)s.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(4.2D)).add(-11.75D))).add(12.0D))).add(-5.4D))).add(1.0D));
/* 198 */       RealFieldElement realFieldElement9 = (RealFieldElement)time.getField().getZero();
/* 199 */       RealFieldElement realFieldElement10 = (RealFieldElement)s.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(22.4D)).add(-50.666666666666664D))).add(35.55555555555556D))).add(-6.933333333333334D)));
/* 200 */       RealFieldElement realFieldElement11 = (RealFieldElement)s.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-22.68D)).add(48.6D))).add(-32.4D))).add(6.48D)));
/* 201 */       RealFieldElement realFieldElement12 = (RealFieldElement)s.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(this.c5a.divide(25.0D))).add(this.c5b.divide(60.0D)))).add(this.c5c.divide(90.0D)))).add(this.c5d.divide(300.0D))));
/* 202 */       RealFieldElement realFieldElement13 = (RealFieldElement)s.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(this.c6a.divide(25.0D))).add(this.c6b.divide(60.0D)))).add(this.c6c.divide(90.0D)))).add(this.c6d.divide(300.0D))));
/* 203 */       RealFieldElement realFieldElement14 = (RealFieldElement)s.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(0.75D)).add(-1.0D))).add(0.3D)));
/* 204 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement8, realFieldElement9, realFieldElement10, realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14 });
/* 205 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement1, realFieldElement2, realFieldElement3, realFieldElement4, realFieldElement5, realFieldElement6, realFieldElement7 });
/*     */     } else {
/*     */       
/* 208 */       T s = oneMinusThetaH;
/* 209 */       RealFieldElement realFieldElement8 = (RealFieldElement)s.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-4.2D)).add(7.55D))).add(-4.45D))).add(0.95D))).add(-0.05D));
/* 210 */       RealFieldElement realFieldElement9 = (RealFieldElement)time.getField().getZero();
/* 211 */       RealFieldElement realFieldElement10 = (RealFieldElement)s.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-22.4D)).add(28.266666666666666D))).add(-7.288888888888889D))).add(-0.35555555555555557D))).add(-0.35555555555555557D));
/* 212 */       RealFieldElement realFieldElement11 = (RealFieldElement)s.multiply(theta.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(22.68D)).add(-25.92D))).add(6.48D))));
/* 213 */       RealFieldElement realFieldElement12 = (RealFieldElement)s.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(this.d5a.divide(25.0D))).add(this.d5b.divide(300.0D)))).add(this.d5c.divide(900.0D)))).add(-0.2722222222222222D))).add(-0.2722222222222222D));
/* 214 */       RealFieldElement realFieldElement13 = (RealFieldElement)s.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(this.d6a.divide(25.0D))).add(this.d6b.divide(300.0D)))).add(this.d6c.divide(900.0D)))).add(-0.2722222222222222D))).add(-0.2722222222222222D));
/* 215 */       RealFieldElement realFieldElement14 = (RealFieldElement)s.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-0.75D)).add(0.25D))).add(-0.05D))).add(-0.05D));
/* 216 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement8, realFieldElement9, realFieldElement10, realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14 });
/* 217 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement1, realFieldElement2, realFieldElement3, realFieldElement4, realFieldElement5, realFieldElement6, realFieldElement7 });
/*     */     } 
/*     */     
/* 220 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/LutherFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */