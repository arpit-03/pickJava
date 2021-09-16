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
/*     */ class HighamHall54FieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   HighamHall54FieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  55 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
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
/*     */   protected HighamHall54FieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/*  68 */     return new HighamHall54FieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/*     */   protected FieldODEStateAndDerivative<T> computeInterpolatedStateAndDerivatives(FieldEquationsMapper<T> mapper, T time, T theta, T thetaH, T oneMinusThetaH) {
/*     */     T[] interpolatedState, interpolatedDerivatives;
/*  81 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-10.0D)).add(16.0D))).add(-7.5D))).add(1.0D);
/*  82 */     RealFieldElement realFieldElement2 = (RealFieldElement)time.getField().getZero();
/*  83 */     RealFieldElement realFieldElement3 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(67.5D)).add(-91.125D))).add(28.6875D));
/*  84 */     RealFieldElement realFieldElement4 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-120.0D)).add(152.0D))).add(-44.0D));
/*  85 */     RealFieldElement realFieldElement5 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(62.5D)).add(-78.125D))).add(23.4375D));
/*  86 */     RealFieldElement realFieldElement6 = (RealFieldElement)((RealFieldElement)theta.multiply(0.625D)).multiply(((RealFieldElement)theta.multiply(2)).subtract(1.0D));
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/*  91 */       RealFieldElement realFieldElement7 = (RealFieldElement)thetaH.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-2.5D)).add(5.333333333333333D))).add(-3.75D))).add(1.0D));
/*  92 */       RealFieldElement realFieldElement8 = (RealFieldElement)time.getField().getZero();
/*  93 */       RealFieldElement realFieldElement9 = (RealFieldElement)thetaH.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(16.875D)).add(-30.375D))).add(14.34375D)));
/*  94 */       RealFieldElement realFieldElement10 = (RealFieldElement)thetaH.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-30.0D)).add(50.666666666666664D))).add(-22.0D)));
/*  95 */       RealFieldElement realFieldElement11 = (RealFieldElement)thetaH.multiply(theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(15.625D)).add(-26.041666666666668D))).add(11.71875D)));
/*  96 */       RealFieldElement realFieldElement12 = (RealFieldElement)thetaH.multiply(theta.multiply(((RealFieldElement)theta.multiply(0.4166666666666667D)).add(-0.3125D)));
/*  97 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement7, realFieldElement8, realFieldElement9, realFieldElement10, realFieldElement11, realFieldElement12 });
/*  98 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement1, realFieldElement2, realFieldElement3, realFieldElement4, realFieldElement5, realFieldElement6 });
/*     */     } else {
/* 100 */       RealFieldElement realFieldElement7 = (RealFieldElement)theta.multiply(theta);
/* 101 */       RealFieldElement realFieldElement8 = (RealFieldElement)thetaH.divide(theta);
/* 102 */       RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-2.5D)).add(5.333333333333333D))).add(-3.75D))).add(1.0D))).add(-0.08333333333333333D));
/* 103 */       RealFieldElement realFieldElement10 = (RealFieldElement)time.getField().getZero();
/* 104 */       RealFieldElement realFieldElement11 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)realFieldElement7.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(16.875D)).add(-30.375D))).add(14.34375D))).add(-0.84375D));
/* 105 */       RealFieldElement realFieldElement12 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)realFieldElement7.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-30.0D)).add(50.666666666666664D))).add(-22.0D))).add(1.3333333333333333D));
/* 106 */       RealFieldElement realFieldElement13 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)realFieldElement7.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(15.625D)).add(-26.041666666666668D))).add(11.71875D))).add(-1.3020833333333333D));
/* 107 */       RealFieldElement realFieldElement14 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)realFieldElement7.multiply(((RealFieldElement)theta.multiply(0.4166666666666667D)).add(-0.3125D))).add(-0.10416666666666667D));
/* 108 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement9, realFieldElement10, realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14 });
/* 109 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement1, realFieldElement2, realFieldElement3, realFieldElement4, realFieldElement5, realFieldElement6 });
/*     */     } 
/*     */     
/* 112 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/HighamHall54FieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */