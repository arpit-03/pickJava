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
/*    */ public class Sin
/*    */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*    */ {
/*    */   public double value(double x) {
/* 33 */     return FastMath.sin(x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public DifferentiableUnivariateFunction derivative() {
/* 41 */     return new Cos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DerivativeStructure value(DerivativeStructure t) {
/* 48 */     return t.sin();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Sin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */