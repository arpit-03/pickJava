/*    */ package org.apache.commons.math3.analysis.function;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*    */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
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
/*    */ public class Minus
/*    */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*    */ {
/*    */   public double value(double x) {
/* 32 */     return -x;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public DifferentiableUnivariateFunction derivative() {
/* 40 */     return new Constant(-1.0D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DerivativeStructure value(DerivativeStructure t) {
/* 47 */     return t.negate();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Minus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */