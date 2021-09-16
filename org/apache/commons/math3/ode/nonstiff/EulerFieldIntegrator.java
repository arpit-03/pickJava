/*    */ package org.apache.commons.math3.ode.nonstiff;
/*    */ 
/*    */ import org.apache.commons.math3.Field;
/*    */ import org.apache.commons.math3.RealFieldElement;
/*    */ import org.apache.commons.math3.ode.FieldEquationsMapper;
/*    */ import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
/*    */ import org.apache.commons.math3.util.MathArrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EulerFieldIntegrator<T extends RealFieldElement<T>>
/*    */   extends RungeKuttaFieldIntegrator<T>
/*    */ {
/*    */   public EulerFieldIntegrator(Field<T> field, T step) {
/* 63 */     super(field, "Euler", step);
/*    */   }
/*    */ 
/*    */   
/*    */   public T[] getC() {
/* 68 */     return (T[])MathArrays.buildArray(getField(), 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public T[][] getA() {
/* 73 */     return (T[][])MathArrays.buildArray(getField(), 0, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public T[] getB() {
/* 78 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 1);
/* 79 */     arrayOfRealFieldElement[0] = (RealFieldElement)getField().getOne();
/* 80 */     return (T[])arrayOfRealFieldElement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected EulerFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 90 */     return new EulerFieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/EulerFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */