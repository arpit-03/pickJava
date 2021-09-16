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
/*     */ public class GillFieldIntegrator<T extends RealFieldElement<T>>
/*     */   extends RungeKuttaFieldIntegrator<T>
/*     */ {
/*     */   public GillFieldIntegrator(Field<T> field, T step) {
/*  61 */     super(field, "Gill", step);
/*     */   }
/*     */ 
/*     */   
/*     */   public T[] getC() {
/*  66 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 3);
/*  67 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 2);
/*  68 */     arrayOfRealFieldElement[1] = arrayOfRealFieldElement[0];
/*  69 */     arrayOfRealFieldElement[2] = (RealFieldElement)getField().getOne();
/*  70 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[][] getA() {
/*  76 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)getField().getZero()).add(2.0D);
/*  77 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.sqrt();
/*     */     
/*  79 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 3, -1);
/*  80 */     for (int i = 0; i < arrayOfRealFieldElement.length; i++) {
/*  81 */       arrayOfRealFieldElement[i] = (RealFieldElement[])MathArrays.buildArray(getField(), i + 1);
/*     */     }
/*  83 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)fraction(1, 2);
/*  84 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)((RealFieldElement)realFieldElement2.subtract(1.0D)).multiply(0.5D);
/*  85 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)((RealFieldElement)realFieldElement2.subtract(2.0D)).multiply(-0.5D);
/*  86 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)getField().getZero();
/*  87 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)realFieldElement2.multiply(-0.5D);
/*  88 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)((RealFieldElement)realFieldElement2.add(2.0D)).multiply(0.5D);
/*  89 */     return (T[][])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public T[] getB() {
/*  95 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)getField().getZero()).add(2.0D);
/*  96 */     RealFieldElement realFieldElement2 = (RealFieldElement)realFieldElement1.sqrt();
/*     */     
/*  98 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 4);
/*  99 */     arrayOfRealFieldElement[0] = (RealFieldElement)fraction(1, 6);
/* 100 */     arrayOfRealFieldElement[1] = (RealFieldElement)((RealFieldElement)realFieldElement2.subtract(2.0D)).divide(-6.0D);
/* 101 */     arrayOfRealFieldElement[2] = (RealFieldElement)((RealFieldElement)realFieldElement2.add(2.0D)).divide(6.0D);
/* 102 */     arrayOfRealFieldElement[3] = arrayOfRealFieldElement[0];
/*     */     
/* 104 */     return (T[])arrayOfRealFieldElement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected GillFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 115 */     return new GillFieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/GillFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */