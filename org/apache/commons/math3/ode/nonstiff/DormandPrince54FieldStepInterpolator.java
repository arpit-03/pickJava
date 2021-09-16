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
/*     */ class DormandPrince54FieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldStepInterpolator<T>
/*     */ {
/*     */   private final T a70;
/*     */   private final T a72;
/*     */   private final T a73;
/*     */   private final T a74;
/*     */   private final T a75;
/*     */   private final T d0;
/*     */   private final T d2;
/*     */   private final T d3;
/*     */   private final T d4;
/*     */   private final T d5;
/*     */   private final T d6;
/*     */   
/*     */   DormandPrince54FieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  92 */     super(field, forward, yDotK, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
/*     */ 
/*     */     
/*  95 */     RealFieldElement realFieldElement = (RealFieldElement)field.getOne();
/*  96 */     this.a70 = (T)((RealFieldElement)realFieldElement.multiply(35.0D)).divide(384.0D);
/*  97 */     this.a72 = (T)((RealFieldElement)realFieldElement.multiply(500.0D)).divide(1113.0D);
/*  98 */     this.a73 = (T)((RealFieldElement)realFieldElement.multiply(125.0D)).divide(192.0D);
/*  99 */     this.a74 = (T)((RealFieldElement)realFieldElement.multiply(-2187.0D)).divide(6784.0D);
/* 100 */     this.a75 = (T)((RealFieldElement)realFieldElement.multiply(11.0D)).divide(84.0D);
/* 101 */     this.d0 = (T)((RealFieldElement)realFieldElement.multiply(-1.2715105075E10D)).divide(1.1282082432E10D);
/* 102 */     this.d2 = (T)((RealFieldElement)realFieldElement.multiply(8.74874797E10D)).divide(3.2700410799E10D);
/* 103 */     this.d3 = (T)((RealFieldElement)realFieldElement.multiply(-1.0690763975E10D)).divide(1.880347072E9D);
/* 104 */     this.d4 = (T)((RealFieldElement)realFieldElement.multiply(7.01980252875E11D)).divide(1.99316789632E11D);
/* 105 */     this.d5 = (T)((RealFieldElement)realFieldElement.multiply(-1.453857185E9D)).divide(8.22651844E8D);
/* 106 */     this.d6 = (T)((RealFieldElement)realFieldElement.multiply(6.9997945E7D)).divide(2.9380423E7D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DormandPrince54FieldStepInterpolator<T> create(Field<T> newField, boolean newForward, T[][] newYDotK, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/* 117 */     return new DormandPrince54FieldStepInterpolator(newField, newForward, newYDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/* 130 */     RealFieldElement realFieldElement1 = (RealFieldElement)time.getField().getOne();
/* 131 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.subtract(theta);
/* 132 */     RealFieldElement realFieldElement3 = (RealFieldElement)theta.multiply(2);
/* 133 */     RealFieldElement realFieldElement4 = (RealFieldElement)realFieldElement1.subtract(realFieldElement3);
/* 134 */     RealFieldElement realFieldElement5 = (RealFieldElement)theta.multiply(((RealFieldElement)theta.multiply(-3)).add(2.0D));
/* 135 */     RealFieldElement realFieldElement6 = (RealFieldElement)realFieldElement3.multiply(((RealFieldElement)theta.multiply(realFieldElement3.subtract(3.0D))).add(1.0D));
/*     */ 
/*     */     
/* 138 */     if (getGlobalPreviousState() != null && theta.getReal() <= 0.5D) {
/* 139 */       T f1 = thetaH;
/* 140 */       RealFieldElement realFieldElement7 = (RealFieldElement)f1.multiply(realFieldElement2);
/* 141 */       RealFieldElement realFieldElement8 = (RealFieldElement)realFieldElement7.multiply(theta);
/* 142 */       RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement8.multiply(realFieldElement2);
/* 143 */       RealFieldElement realFieldElement10 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)f1.multiply(this.a70)).subtract(realFieldElement7.multiply(this.a70.subtract(1.0D)))).add(realFieldElement8.multiply(((RealFieldElement)this.a70.multiply(2)).subtract(1.0D)))).add(realFieldElement9.multiply(this.d0));
/*     */ 
/*     */ 
/*     */       
/* 147 */       RealFieldElement realFieldElement11 = (RealFieldElement)time.getField().getZero();
/* 148 */       RealFieldElement realFieldElement12 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)f1.multiply(this.a72)).subtract(realFieldElement7.multiply(this.a72))).add(realFieldElement8.multiply(this.a72.multiply(2)))).add(realFieldElement9.multiply(this.d2));
/*     */ 
/*     */ 
/*     */       
/* 152 */       RealFieldElement realFieldElement13 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)f1.multiply(this.a73)).subtract(realFieldElement7.multiply(this.a73))).add(realFieldElement8.multiply(this.a73.multiply(2)))).add(realFieldElement9.multiply(this.d3));
/*     */ 
/*     */ 
/*     */       
/* 156 */       RealFieldElement realFieldElement14 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)f1.multiply(this.a74)).subtract(realFieldElement7.multiply(this.a74))).add(realFieldElement8.multiply(this.a74.multiply(2)))).add(realFieldElement9.multiply(this.d4));
/*     */ 
/*     */ 
/*     */       
/* 160 */       RealFieldElement realFieldElement15 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)f1.multiply(this.a75)).subtract(realFieldElement7.multiply(this.a75))).add(realFieldElement8.multiply(this.a75.multiply(2)))).add(realFieldElement9.multiply(this.d5));
/*     */ 
/*     */ 
/*     */       
/* 164 */       RealFieldElement realFieldElement16 = (RealFieldElement)((RealFieldElement)realFieldElement9.multiply(this.d6)).subtract(realFieldElement8);
/* 165 */       RealFieldElement realFieldElement17 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a70.subtract(realFieldElement4.multiply(this.a70.subtract(1.0D)))).add(realFieldElement5.multiply(((RealFieldElement)this.a70.multiply(2)).subtract(1.0D)))).add(realFieldElement6.multiply(this.d0));
/*     */ 
/*     */ 
/*     */       
/* 169 */       RealFieldElement realFieldElement18 = (RealFieldElement)time.getField().getZero();
/* 170 */       RealFieldElement realFieldElement19 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a72.subtract(realFieldElement4.multiply(this.a72))).add(realFieldElement5.multiply(this.a72.multiply(2)))).add(realFieldElement6.multiply(this.d2));
/*     */ 
/*     */ 
/*     */       
/* 174 */       RealFieldElement realFieldElement20 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a73.subtract(realFieldElement4.multiply(this.a73))).add(realFieldElement5.multiply(this.a73.multiply(2)))).add(realFieldElement6.multiply(this.d3));
/*     */ 
/*     */ 
/*     */       
/* 178 */       RealFieldElement realFieldElement21 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a74.subtract(realFieldElement4.multiply(this.a74))).add(realFieldElement5.multiply(this.a74.multiply(2)))).add(realFieldElement6.multiply(this.d4));
/*     */ 
/*     */ 
/*     */       
/* 182 */       RealFieldElement realFieldElement22 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a75.subtract(realFieldElement4.multiply(this.a75))).add(realFieldElement5.multiply(this.a75.multiply(2)))).add(realFieldElement6.multiply(this.d5));
/*     */ 
/*     */ 
/*     */       
/* 186 */       RealFieldElement realFieldElement23 = (RealFieldElement)((RealFieldElement)realFieldElement6.multiply(this.d6)).subtract(realFieldElement5);
/* 187 */       interpolatedState = previousStateLinearCombination((T[])new RealFieldElement[] { realFieldElement10, realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14, realFieldElement15, realFieldElement16 });
/*     */       
/* 189 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement17, realFieldElement18, realFieldElement19, realFieldElement20, realFieldElement21, realFieldElement22, realFieldElement23 });
/*     */     } else {
/*     */       
/* 192 */       RealFieldElement realFieldElement7 = (RealFieldElement)oneMinusThetaH.negate();
/* 193 */       RealFieldElement realFieldElement8 = (RealFieldElement)oneMinusThetaH.multiply(theta);
/* 194 */       RealFieldElement realFieldElement9 = (RealFieldElement)realFieldElement8.multiply(theta);
/* 195 */       RealFieldElement realFieldElement10 = (RealFieldElement)realFieldElement9.multiply(realFieldElement2);
/* 196 */       RealFieldElement realFieldElement11 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement7.multiply(this.a70)).subtract(realFieldElement8.multiply(this.a70.subtract(1.0D)))).add(realFieldElement9.multiply(((RealFieldElement)this.a70.multiply(2)).subtract(1.0D)))).add(realFieldElement10.multiply(this.d0));
/*     */ 
/*     */ 
/*     */       
/* 200 */       RealFieldElement realFieldElement12 = (RealFieldElement)time.getField().getZero();
/* 201 */       RealFieldElement realFieldElement13 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement7.multiply(this.a72)).subtract(realFieldElement8.multiply(this.a72))).add(realFieldElement9.multiply(this.a72.multiply(2)))).add(realFieldElement10.multiply(this.d2));
/*     */ 
/*     */ 
/*     */       
/* 205 */       RealFieldElement realFieldElement14 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement7.multiply(this.a73)).subtract(realFieldElement8.multiply(this.a73))).add(realFieldElement9.multiply(this.a73.multiply(2)))).add(realFieldElement10.multiply(this.d3));
/*     */ 
/*     */ 
/*     */       
/* 209 */       RealFieldElement realFieldElement15 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement7.multiply(this.a74)).subtract(realFieldElement8.multiply(this.a74))).add(realFieldElement9.multiply(this.a74.multiply(2)))).add(realFieldElement10.multiply(this.d4));
/*     */ 
/*     */ 
/*     */       
/* 213 */       RealFieldElement realFieldElement16 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement7.multiply(this.a75)).subtract(realFieldElement8.multiply(this.a75))).add(realFieldElement9.multiply(this.a75.multiply(2)))).add(realFieldElement10.multiply(this.d5));
/*     */ 
/*     */ 
/*     */       
/* 217 */       RealFieldElement realFieldElement17 = (RealFieldElement)((RealFieldElement)realFieldElement10.multiply(this.d6)).subtract(realFieldElement9);
/* 218 */       RealFieldElement realFieldElement18 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a70.subtract(realFieldElement4.multiply(this.a70.subtract(1.0D)))).add(realFieldElement5.multiply(((RealFieldElement)this.a70.multiply(2)).subtract(1.0D)))).add(realFieldElement6.multiply(this.d0));
/*     */ 
/*     */ 
/*     */       
/* 222 */       RealFieldElement realFieldElement19 = (RealFieldElement)time.getField().getZero();
/* 223 */       RealFieldElement realFieldElement20 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a72.subtract(realFieldElement4.multiply(this.a72))).add(realFieldElement5.multiply(this.a72.multiply(2)))).add(realFieldElement6.multiply(this.d2));
/*     */ 
/*     */ 
/*     */       
/* 227 */       RealFieldElement realFieldElement21 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a73.subtract(realFieldElement4.multiply(this.a73))).add(realFieldElement5.multiply(this.a73.multiply(2)))).add(realFieldElement6.multiply(this.d3));
/*     */ 
/*     */ 
/*     */       
/* 231 */       RealFieldElement realFieldElement22 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a74.subtract(realFieldElement4.multiply(this.a74))).add(realFieldElement5.multiply(this.a74.multiply(2)))).add(realFieldElement6.multiply(this.d4));
/*     */ 
/*     */ 
/*     */       
/* 235 */       RealFieldElement realFieldElement23 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.a75.subtract(realFieldElement4.multiply(this.a75))).add(realFieldElement5.multiply(this.a75.multiply(2)))).add(realFieldElement6.multiply(this.d5));
/*     */ 
/*     */ 
/*     */       
/* 239 */       RealFieldElement realFieldElement24 = (RealFieldElement)((RealFieldElement)realFieldElement6.multiply(this.d6)).subtract(realFieldElement5);
/* 240 */       interpolatedState = currentStateLinearCombination((T[])new RealFieldElement[] { realFieldElement11, realFieldElement12, realFieldElement13, realFieldElement14, realFieldElement15, realFieldElement16, realFieldElement17 });
/*     */       
/* 242 */       interpolatedDerivatives = derivativeLinearCombination((T[])new RealFieldElement[] { realFieldElement18, realFieldElement19, realFieldElement20, realFieldElement21, realFieldElement22, realFieldElement23, realFieldElement24 });
/*     */     } 
/*     */     
/* 245 */     return new FieldODEStateAndDerivative((RealFieldElement)time, (RealFieldElement[])interpolatedState, (RealFieldElement[])interpolatedDerivatives);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/DormandPrince54FieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */