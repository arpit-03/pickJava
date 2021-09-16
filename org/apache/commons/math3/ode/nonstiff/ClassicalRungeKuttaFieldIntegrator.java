/*     */ package org.apache.commons.math3.ode.nonstiff;
/*     */ 
/*     */ import org.apache.commons.math3.Field;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*     */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
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
/*     */ public class ClassicalRungeKuttaFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   public ClassicalRungeKuttaFieldIntegrator(Field<T> field, T step) {
/*  61 */     super(field, "classical Runge-Kutta", step);
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getC() {
/*  66 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 3);
/*  67 */     arrayOfRealFieldElement[0] = (RealFieldElement)((RealFieldElement)getField().getOne()).multiply(0.5D);
/*  68 */     arrayOfRealFieldElement[1] = arrayOfRealFieldElement[0];
/*  69 */     arrayOfRealFieldElement[2] = (RealFieldElement)getField().getOne();
/*  70 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/*  75 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 3, -1);
/*  76 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/*  77 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/*  79 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)fraction(1, 2);
/*  80 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)getField().getZero();
/*  81 */     arrayOfRealFieldElement[1][1] = arrayOfRealFieldElement[0][0];
/*  82 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)getField().getZero();
/*  83 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)getField().getZero();
/*  84 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)getField().getOne();
/*  85 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getB() {
/*  90 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 4);
/*  91 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 6);
/*  92 */     arrayOfRealFieldElement[1] = (RealFieldElement)fraction(1, 3);
/*  93 */     arrayOfRealFieldElement[2] = arrayOfRealFieldElement[1];
/*  94 */     arrayOfRealFieldElement[3] = arrayOfRealFieldElement[0];
/*  95 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ClassicalRungeKuttaFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 105 */     return new ClassicalRungeKuttaFieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/ClassicalRungeKuttaFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */