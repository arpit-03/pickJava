/*    */ package org.apache.commons.math3.analysis.differentiation;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateVectorFunction;
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
/*    */ public class GradientFunction
/*    */   implements MultivariateVectorFunction
/*    */ {
/*    */   private final MultivariateDifferentiableFunction f;
/*    */   
/*    */   public GradientFunction(MultivariateDifferentiableFunction f) {
/* 37 */     this.f = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] value(double[] point) {
/* 44 */     DerivativeStructure[] dsX = new DerivativeStructure[point.length];
/* 45 */     for (int i = 0; i < point.length; i++) {
/* 46 */       dsX[i] = new DerivativeStructure(point.length, 1, i, point[i]);
/*    */     }
/*    */ 
/*    */     
/* 50 */     DerivativeStructure dsY = this.f.value(dsX);
/*    */ 
/*    */     
/* 53 */     double[] y = new double[point.length];
/* 54 */     int[] orders = new int[point.length];
/* 55 */     for (int j = 0; j < point.length; j++) {
/* 56 */       orders[j] = 1;
/* 57 */       y[j] = dsY.getPartialDerivative(orders);
/* 58 */       orders[j] = 0;
/*    */     } 
/*    */     
/* 61 */     return y;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/GradientFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */