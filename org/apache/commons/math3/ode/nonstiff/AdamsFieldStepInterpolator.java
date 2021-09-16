/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.linear.Array2DRowFieldMatrix;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*     */ import org.apache.commons.math3.ode.sampling.AbstractFieldStepInterpolator;
/*     */ import org.apache.commons.math3.util.MathArrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AdamsFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends AbstractFieldStepInterpolator<T>
/*     */ {
/*     */   private T scalingH;
/*     */   private final FieldODEStateAndDerivative<T> reference;
/*     */   private final T[] scaled;
/*     */   private final Array2DRowFieldMatrix<T> nordsieck;
/*     */   
/*     */   AdamsFieldStepInterpolator(T stepSize, FieldODEStateAndDerivative<T> reference, T[] scaled, Array2DRowFieldMatrix<T> nordsieck, boolean isForward, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> equationsMapper) {
/*  76 */     this(stepSize, reference, scaled, nordsieck, isForward, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, equationsMapper);
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
/*     */   private AdamsFieldStepInterpolator(T stepSize, FieldODEStateAndDerivative<T> reference, T[] scaled, Array2DRowFieldMatrix<T> nordsieck, boolean isForward, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> equationsMapper) {
/* 101 */     super(isForward, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, equationsMapper);
/*     */     
/* 103 */     this.scalingH = stepSize;
/* 104 */     this.reference = reference;
/* 105 */     this.scaled = (T[])scaled.clone();
/* 106 */     this.nordsieck = new Array2DRowFieldMatrix(nordsieck.getData(), false);
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
/*     */   protected AdamsFieldStepInterpolator<T> create(boolean newForward, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/* 125 */     return new AdamsFieldStepInterpolator(this.scalingH, this.reference, this.scaled, this.nordsieck, newForward, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/*     */   protected FieldODEStateAndDerivative<T> computeInterpolatedStateAndDerivatives(FieldEquationsMapper<T> equationsMapper, T time, T theta, T thetaH, T oneMinusThetaH) {
/* 138 */     return taylor(this.reference, time, this.scalingH, this.scaled, this.nordsieck);
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
/*     */   public static <S extends RealFieldElement<S>> FieldODEStateAndDerivative<S> taylor(FieldODEStateAndDerivative<S> reference, S time, S stepSize, S[] scaled, Array2DRowFieldMatrix<S> nordsieck) {
/* 155 */     RealFieldElement realFieldElement1 = (RealFieldElement)time.subtract(reference.getTime());
/* 156 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.divide(stepSize);
/*     */     
/* 158 */     RealFieldElement[] arrayOfRealFieldElement1 = (RealFieldElement[])MathArrays.buildArray(time.getField(), scaled.length);
/* 159 */     Arrays.fill((Object[])arrayOfRealFieldElement1, time.getField().getZero());
/* 160 */     RealFieldElement[] arrayOfRealFieldElement2 = (RealFieldElement[])MathArrays.buildArray(time.getField(), scaled.length);
/* 161 */     Arrays.fill((Object[])arrayOfRealFieldElement2, time.getField().getZero());
/*     */ 
/*     */ 
/*     */     
/* 165 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])nordsieck.getDataRef();
/* 166 */     for (int i = arrayOfRealFieldElement.length - 1; i >= 0; i--) {
/* 167 */       int order = i + 2;
/* 168 */       RealFieldElement[] arrayOfRealFieldElement4 = arrayOfRealFieldElement[i];
/* 169 */       RealFieldElement realFieldElement = (RealFieldElement)realFieldElement2.pow(order);
/* 170 */       for (int k = 0; k < arrayOfRealFieldElement4.length; k++) {
/* 171 */         RealFieldElement realFieldElement3 = (RealFieldElement)arrayOfRealFieldElement4[k].multiply(realFieldElement);
/* 172 */         arrayOfRealFieldElement1[k] = (RealFieldElement)arrayOfRealFieldElement1[k].add(realFieldElement3);
/* 173 */         arrayOfRealFieldElement2[k] = (RealFieldElement)arrayOfRealFieldElement2[k].add(realFieldElement3.multiply(order));
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     RealFieldElement[] arrayOfRealFieldElement3 = reference.getState();
/* 178 */     for (int j = 0; j < arrayOfRealFieldElement1.length; j++) {
/* 179 */       arrayOfRealFieldElement1[j] = (RealFieldElement)arrayOfRealFieldElement1[j].add(scaled[j].multiply(realFieldElement2));
/* 180 */       arrayOfRealFieldElement3[j] = (RealFieldElement)arrayOfRealFieldElement3[j].add(arrayOfRealFieldElement1[j]);
/* 181 */       arrayOfRealFieldElement2[j] = (RealFieldElement)((RealFieldElement)arrayOfRealFieldElement2[j].add(scaled[j].multiply(realFieldElement2))).divide(realFieldElement1);
/*     */     } 
/*     */ 
/*     */     
/* 185 */     return new FieldODEStateAndDerivative((RealFieldElement)time, arrayOfRealFieldElement3, arrayOfRealFieldElement2);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/AdamsFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */