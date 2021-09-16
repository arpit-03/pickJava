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
/*     */ class GillFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   private final T one_minus_inv_sqrt_2;
/*     */   private final T one_plus_inv_sqrt_2;
/*     */   
/*     */   GillFieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  84 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
/*     */ 
/*     */     
/*  87 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)field.getZero()).add(0.5D)).sqrt();
/*  88 */     this.one_minus_inv_sqrt_2 = (T)((RealFieldElement)field.getOne()).subtract(realFieldElement);
/*  89 */     this.one_plus_inv_sqrt_2 = (T)((RealFieldElement)field.getOne()).add(realFieldElement);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GillFieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/* 100 */     return new GillFieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/* 113 */     RealFieldElement realFieldElement1 = (RealFieldElement)time.getField().getOne();
/* 114 */     RealFieldElement realFieldElement2 = (RealFieldElement)theta.multiply(2);
/* 115 */     RealFieldElement realFieldElement3 = (RealFieldElement)realFieldElement2.multiply(realFieldElement2);
/* 116 */     RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)theta.multiply(realFieldElement2.subtract(3.0D))).add(1.0D);
/* 117 */     RealFieldElement realFieldElement5 = (RealFieldElement)realFieldElement2.multiply(realFieldElement1.subtract(theta));
/* 118 */     RealFieldElement realFieldElement6 = (RealFieldElement)realFieldElement5.multiply(this.one_minus_inv_sqrt_2);
/* 119 */     RealFieldElement realFieldElement7 = (RealFieldElement)realFieldElement5.multiply(this.one_plus_inv_sqrt_2);
/* 120 */     RealFieldElement realFieldElement8 = (RealFieldElement)theta.multiply(realFieldElement2.subtract(1.0D));
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/* 125 */       RealFieldElement realFieldElement9 = (RealFieldElement)thetaH.divide(6.0D);
/* 126 */       RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement9.multiply(((RealFieldElement)theta.multiply(6)).subtract(realFieldElement3));
/* 127 */       RealFieldElement realFieldElement11 = (RealFieldElement)realFieldElement9.multiply(((RealFieldElement)realFieldElement3.subtract(theta.multiply(9))).add(6.0D));
/* 128 */       RealFieldElement realFieldElement12 = (RealFieldElement)realFieldElement10.multiply(this.one_minus_inv_sqrt_2);
/* 129 */       RealFieldElement realFieldElement13 = (RealFieldElement)realFieldElement10.multiply(this.one_plus_inv_sqrt_2);
/* 130 */       RealFieldElement realFieldElement14 = (RealFieldElement)realFieldElement9.multiply(realFieldElement3.subtract(theta.multiply(3)));
/* 131 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14 });
/* 132 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement4, realFieldElement6, realFieldElement7, realFieldElement8 });
/*     */     } else {
/* 134 */       RealFieldElement realFieldElement9 = (RealFieldElement)oneMinusThetaH.divide(-6.0D);
/* 135 */       RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement9.multiply(((RealFieldElement)realFieldElement2.add(2.0D)).subtract(realFieldElement3));
/* 136 */       RealFieldElement realFieldElement11 = (RealFieldElement)realFieldElement9.multiply(((RealFieldElement)realFieldElement3.subtract(theta.multiply(5))).add(1.0D));
/* 137 */       RealFieldElement realFieldElement12 = (RealFieldElement)realFieldElement10.multiply(this.one_minus_inv_sqrt_2);
/* 138 */       RealFieldElement realFieldElement13 = (RealFieldElement)realFieldElement10.multiply(this.one_plus_inv_sqrt_2);
/* 139 */       RealFieldElement realFieldElement14 = (RealFieldElement)realFieldElement9.multiply(((RealFieldElement)realFieldElement3.add(theta)).add(1.0D));
/* 140 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14 });
/* 141 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement4, realFieldElement6, realFieldElement7, realFieldElement8 });
/*     */     } 
/*     */     
/* 144 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/GillFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */