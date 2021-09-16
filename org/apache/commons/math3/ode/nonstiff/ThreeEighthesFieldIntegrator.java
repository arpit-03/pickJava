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
/*     */ public class ThreeEighthesFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   public ThreeEighthesFieldIntegrator(Field<T> field, T step) {
/*  60 */     super(field, "3/8", step);
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getC() {
/*  65 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 3);
/*  66 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 3);
/*  67 */     arrayOfRealFieldElement[1] = (RealFieldElement)arrayOfRealFieldElement[0].add(arrayOfRealFieldElement[0]);
/*  68 */     arrayOfRealFieldElement[2] = (RealFieldElement)getField().getOne();
/*  69 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/*  74 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 3, -1);
/*  75 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/*  76 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/*  78 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)fraction(1, 3);
/*  79 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)arrayOfRealFieldElement[0][0].negate();
/*  80 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)getField().getOne();
/*  81 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)getField().getOne();
/*  82 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)((RealFieldElement)getField().getOne()).negate();
/*  83 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)getField().getOne();
/*  84 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getB() {
/*  89 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 4);
/*  90 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 8);
/*  91 */     arrayOfRealFieldElement[1] = (RealFieldElement)fraction(3, 8);
/*  92 */     arrayOfRealFieldElement[2] = arrayOfRealFieldElement[1];
/*  93 */     arrayOfRealFieldElement[3] = arrayOfRealFieldElement[0];
/*  94 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThreeEighthesFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 104 */     return new ThreeEighthesFieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/ThreeEighthesFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */