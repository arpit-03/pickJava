/*    */ package org.apache.commons.math3.analysis.function;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
/*    */ import org.apache.commons.math3.analysis.FunctionUtils;
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
/*    */ 
/*    */ public class Sqrt
/*    */   implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction
/*    */ {
/*    */   public double value(double x) {
/* 35 */     return FastMath.sqrt(x);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public UnivariateFunction derivative() {
/* 43 */     return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DerivativeStructure value(DerivativeStructure t) {
/* 50 */     return t.sqrt();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/function/Sqrt.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */