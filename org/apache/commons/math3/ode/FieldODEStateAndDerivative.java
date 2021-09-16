/*    */ package org.apache.commons.math3.ode;
/*    */ 
/*    */ import org.apache.commons.math3.RealFieldElement;
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
/*    */ public class FieldODEStateAndDerivative<T extends RealFieldElement<T>>
/*    */   extends FieldODEState<T>
/*    */ {
/*    */   private final T[] derivative;
/*    */   private final T[][] secondaryDerivative;
/*    */   
/*    */   public FieldODEStateAndDerivative(T time, T[] state, T[] derivative) {
/* 49 */     this(time, state, derivative, (T[][])null, (T[][])null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldODEStateAndDerivative(T time, T[] state, T[] derivative, T[][] secondaryState, T[][] secondaryDerivative) {
/* 60 */     super(time, state, secondaryState);
/* 61 */     this.derivative = (T[])derivative.clone();
/* 62 */     this.secondaryDerivative = copy(time.getField(), secondaryDerivative);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T[] getDerivative() {
/* 69 */     return (T[])this.derivative.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T[] getSecondaryDerivative(int index) {
/* 79 */     return (index == 0) ? (T[])this.derivative.clone() : (T[])this.secondaryDerivative[index - 1].clone();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ode/FieldODEStateAndDerivative.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */