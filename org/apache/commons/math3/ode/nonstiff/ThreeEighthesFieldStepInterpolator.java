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
/*     */ class ThreeEighthesFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   ThreeEighthesFieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  81 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
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
/*     */   protected ThreeEighthesFieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/*  94 */     return new ThreeEighthesFieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/* 107 */     RealFieldElement realFieldElement1 = (RealFieldElement)theta.multiply(0.75D);
/* 108 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)realFieldElement1.multiply(((RealFieldElement)theta.multiply(4)).subtract(5.0D))).add(1.0D);
/* 109 */     RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement1.multiply(((RealFieldElement)theta.multiply(-6)).add(5.0D));
/* 110 */     RealFieldElement realFieldElement4 = (RealFieldElement)realFieldElement1.multiply(((RealFieldElement)theta.multiply(2)).subtract(1.0D));
/*     */ 
/*     */ 
/*     */     
/* 114 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/* 115 */       RealFieldElement realFieldElement5 = (RealFieldElement)thetaH.divide(8.0D);
/* 116 */       RealFieldElement realFieldElement6 = (RealFieldElement)((RealFieldElement)theta.multiply(theta)).multiply(4);
/* 117 */       RealFieldElement realFieldElement7 = (RealFieldElement)realFieldElement5.multiply(((RealFieldElement)((RealFieldElement)realFieldElement6.multiply(2)).subtract(theta.multiply(15))).add(8.0D));
/* 118 */       RealFieldElement realFieldElement8 = (RealFieldElement)((RealFieldElement)realFieldElement5.multiply(((RealFieldElement)theta.multiply(5)).subtract(realFieldElement6))).multiply(3);
/* 119 */       RealFieldElement realFieldElement9 = (RealFieldElement)((RealFieldElement)realFieldElement5.multiply(theta)).multiply(3);
/* 120 */       RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement5.multiply(realFieldElement6.subtract(theta.multiply(3)));
/* 121 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement7, realFieldElement8, realFieldElement9, realFieldElement10 });
/* 122 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement2, realFieldElement3, realFieldElement1, realFieldElement4 });
/*     */     } else {
/* 124 */       RealFieldElement realFieldElement5 = (RealFieldElement)oneMinusThetaH.divide(-8.0D);
/* 125 */       RealFieldElement realFieldElement6 = (RealFieldElement)((RealFieldElement)theta.multiply(theta)).multiply(4);
/* 126 */       RealFieldElement realFieldElement7 = (RealFieldElement)theta.add(1.0D);
/* 127 */       RealFieldElement realFieldElement8 = (RealFieldElement)realFieldElement5.multiply(((RealFieldElement)((RealFieldElement)realFieldElement6.multiply(2)).subtract(theta.multiply(7))).add(1.0D));
/* 128 */       RealFieldElement realFieldElement9 = (RealFieldElement)((RealFieldElement)realFieldElement5.multiply(realFieldElement7.subtract(realFieldElement6))).multiply(3);
/* 129 */       RealFieldElement realFieldElement10 = (RealFieldElement)((RealFieldElement)realFieldElement5.multiply(realFieldElement7)).multiply(3);
/* 130 */       RealFieldElement realFieldElement11 = (RealFieldElement)realFieldElement5.multiply(realFieldElement7.add(realFieldElement6));
/* 131 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement8, realFieldElement9, realFieldElement10, realFieldElement11 });
/* 132 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement2, realFieldElement3, realFieldElement1, realFieldElement4 });
/*     */     } 
/*     */     
/* 135 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/ThreeEighthesFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */