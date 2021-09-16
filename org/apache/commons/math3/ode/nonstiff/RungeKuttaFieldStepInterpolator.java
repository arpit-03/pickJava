/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
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
/*     */ abstract class RungeKuttaFieldStepInterpolator<T extends RealFieldElement<T>>
/*     */   extends AbstractFieldStepInterpolator<T>
/*     */ {
/*     */   private final Field<T> field;
/*     */   private final T[][] yDotK;
/*     */   
/*     */   protected RungeKuttaFieldStepInterpolator(Field<T> field, boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldODEStateAndDerivative<T> softPreviousState, FieldODEStateAndDerivative<T> softCurrentState, FieldEquationsMapper<T> mapper) {
/*  63 */     super(forward, globalPreviousState, globalCurrentState, softPreviousState, softCurrentState, mapper);
/*  64 */     this.field = field;
/*  65 */     this.yDotK = (T[][])MathArrays.buildArray(field, yDotK.length, -1);
/*  66 */     for (int i = 0; i < yDotK.length; i++) {
/*  67 */       this.yDotK[i] = (T[])yDotK[i].clone();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RungeKuttaFieldStepInterpolator<T> create(boolean newForward, FieldODEStateAndDerivative<T> newGlobalPreviousState, FieldODEStateAndDerivative<T> newGlobalCurrentState, FieldODEStateAndDerivative<T> newSoftPreviousState, FieldODEStateAndDerivative<T> newSoftCurrentState, FieldEquationsMapper<T> newMapper) {
/*  79 */     return create(this.field, newForward, this.yDotK, newGlobalPreviousState, newGlobalCurrentState, newSoftPreviousState, newSoftCurrentState, newMapper);
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
/*     */   protected final T[] previousStateLinearCombination(T... coefficients) {
/* 108 */     return combine((T[])getPreviousState().getState(), coefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T[] currentStateLinearCombination(T... coefficients) {
/* 117 */     return combine((T[])getCurrentState().getState(), coefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T[] derivativeLinearCombination(T... coefficients) {
/* 126 */     return combine((T[])MathArrays.buildArray(this.field, (this.yDotK[0]).length), coefficients);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private T[] combine(T[] a, T... coefficients) {
/* 135 */     for (int i = 0; i < a.length; i++) {
/* 136 */       for (int k = 0; k < coefficients.length; k++) {
/* 137 */         a[i] = (T)a[i].add(coefficients[k].multiply(this.yDotK[k][i]));
/*     */       }
/*     */     } 
/* 140 */     return a;
/*     */   }
/*     */   
/*     */   protected abstract RungeKuttaFieldStepInterpolator<T> create(Field<T> paramField, boolean paramBoolean, T[][] paramArrayOfT, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative1, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative2, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative3, FieldODEStateAndDerivative<T> paramFieldODEStateAndDerivative4, FieldEquationsMapper<T> paramFieldEquationsMapper);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/RungeKuttaFieldStepInterpolator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */