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
/*     */ class ClassicalRungeKuttaFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   ClassicalRungeKuttaFieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  79 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
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
/*     */   protected ClassicalRungeKuttaFieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/*  92 */     return new ClassicalRungeKuttaFieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/* 105 */     RealFieldElement realFieldElement1 = (RealFieldElement)time.getField().getOne();
/* 106 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.subtract(theta);
/* 107 */     RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement1.subtract(theta.multiply(2));
/* 108 */     RealFieldElement realFieldElement4 = (RealFieldElement)realFieldElement2.multiply(realFieldElement3);
/* 109 */     RealFieldElement realFieldElement5 = (RealFieldElement)((RealFieldElement)theta.multiply(realFieldElement2)).multiply(2);
/* 110 */     RealFieldElement realFieldElement6 = (RealFieldElement)((RealFieldElement)theta.multiply(realFieldElement3)).negate();
/*     */ 
/*     */ 
/*     */     
/* 114 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/* 115 */       RealFieldElement realFieldElement7 = (RealFieldElement)((RealFieldElement)theta.multiply(theta)).multiply(4);
/* 116 */       RealFieldElement realFieldElement8 = (RealFieldElement)thetaH.divide(6.0D);
/* 117 */       RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)realFieldElement7.subtract(theta.multiply(9))).add(6.0D));
/* 118 */       RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)theta.multiply(6)).subtract(realFieldElement7));
/* 119 */       RealFieldElement realFieldElement11 = (RealFieldElement)realFieldElement8.multiply(realFieldElement7.subtract(theta.multiply(3)));
/* 120 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement9, realFieldElement10, realFieldElement10, realFieldElement11 });
/* 121 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement4, realFieldElement5, realFieldElement5, realFieldElement6 });
/*     */     } else {
/* 123 */       RealFieldElement realFieldElement7 = (RealFieldElement)theta.multiply(4);
/* 124 */       RealFieldElement realFieldElement8 = (RealFieldElement)oneMinusThetaH.divide(6.0D);
/* 125 */       RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)realFieldElement7.negate()).add(5.0D))).subtract(1.0D));
/* 126 */       RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)theta.multiply(realFieldElement7.subtract(2.0D))).subtract(2.0D));
/* 127 */       RealFieldElement realFieldElement11 = (RealFieldElement)realFieldElement8.multiply(((RealFieldElement)theta.multiply(((RealFieldElement)realFieldElement7.negate()).subtract(1.0D))).subtract(1.0D));
/* 128 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement9, realFieldElement10, realFieldElement10, realFieldElement11 });
/* 129 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement4, realFieldElement5, realFieldElement5, realFieldElement6 });
/*     */     } 
/*     */     
/* 132 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/ClassicalRungeKuttaFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */