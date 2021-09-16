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
/*    */ public class MidpointFieldIntegrator<T extends RealFieldElement<T>>
/*    */   extends RungeKuttaFieldIntegrator<T>
/*    */ {
/*    */   public MidpointFieldIntegrator(Field<T> field, T step) {
/* 58 */     super(field, "midpoint", step);
/*    */   }
/*    */ 
/*    */   
/*    */   public T[] getC() {
/* 63 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 1);
/* 64 */     arrayOfRealFieldElement[0] = (RealFieldElement)((RealFieldElement)getField().getOne()).multiply(0.5D);
/* 65 */     return (T[])arrayOfRealFieldElement;
/*    */   }
/*    */ 
/*    */   
/*    */   public T[][] getA() {
/* 70 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(getField(), 1, 1);
/* 71 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)fraction(1, 2);
/* 72 */     return (T[][])arrayOfRealFieldElement;
/*    */   }
/*    */ 
/*    */   
/*    */   public T[] getB() {
/* 77 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(getField(), 2);
/* 78 */     arrayOfRealFieldElement[0] = (RealFieldElement)getField().getZero();
/* 79 */     arrayOfRealFieldElement[1] = (RealFieldElement)getField().getOne();
/* 80 */     return (T[])arrayOfRealFieldElement;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MidpointFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
/* 90 */     return new MidpointFieldStepInterpolator<T>(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/nonstiff/MidpointFieldIntegrator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */