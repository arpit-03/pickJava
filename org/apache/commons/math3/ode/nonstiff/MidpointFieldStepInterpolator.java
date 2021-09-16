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
/*     */ class MidpointFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   MidpointFieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  71 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
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
/*     */   protected MidpointFieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/*  84 */     return new MidpointFieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/*  97 */     RealFieldElement realFieldElement1 = (RealFieldElement)theta.multiply(2);
/*  98 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)time.getField().getOne()).subtract(realFieldElement1);
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/* 103 */       RealFieldElement realFieldElement3 = (RealFieldElement)theta.multiply(oneMinusThetaH);
/* 104 */       RealFieldElement realFieldElement4 = (RealFieldElement)theta.multiply(thetaH);
/* 105 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement3, realFieldElement4 });
/* 106 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement2, realFieldElement1 });
/*     */     } else {
/* 108 */       RealFieldElement realFieldElement3 = (RealFieldElement)oneMinusThetaH.multiply(theta);
/* 109 */       RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)oneMinusThetaH.multiply(theta.add(1.0D))).negate();
/* 110 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement3, realFieldElement4 });
/* 111 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement2, realFieldElement1 });
/*     */     } 
/*     */     
/* 114 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/MidpointFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */