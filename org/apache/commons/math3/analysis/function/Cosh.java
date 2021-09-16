/*    */ package org.apache.commons.math3.analysis.function;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.UnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
/*    */ import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
/*    */ import org.apache.commons.math3.util.FastMath;
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
/*    */ public class Cosh
/*    */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*    */ {
/*    */   public double value(double x) {
/* 33 */     return FastMath.cosh(x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public DifferentiableUnivariateFunction derivative() {
/* 41 */     return new Sinh();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DerivativeStructure value(DerivativeStructure t) {
/* 48 */     return t.cosh();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Cosh.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */