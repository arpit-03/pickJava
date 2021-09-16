/*    */ package org.apache.commons.math3.analysis.differentiation;
/*    */ 
/*    */ import org.apache.commons.math3.analysis.MultivariateMatrixFunction;
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
/*    */ public class JacobianFunction
/*    */   implements MultivariateMatrixFunction
/*    */ {
/*    */   private final MultivariateDifferentiableVectorFunction f;
/*    */   
/*    */   public JacobianFunction(MultivariateDifferentiableVectorFunction f) {
/* 39 */     this.f = f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] value(double[] point) {
/* 46 */     DerivativeStructure[] dsX = new DerivativeStructure[point.length];
/* 47 */     for (int i = 0; i < point.length; i++) {
/* 48 */       dsX[i] = new DerivativeStructure(point.length, 1, i, point[i]);
/*    */     }
/*    */ 
/*    */     
/* 52 */     DerivativeStructure[] dsY = this.f.value(dsX);
/*    */ 
/*    */     
/* 55 */     double[][] y = new double[dsY.length][point.length];
/* 56 */     int[] orders = new int[point.length];
/* 57 */     for (int j = 0; j < dsY.length; j++) {
/* 58 */       for (int k = 0; k < point.length; k++) {
/* 59 */         orders[k] = 1;
/* 60 */         y[j][k] = dsY[j].getPartialDerivative(orders);
/* 61 */         orders[k] = 0;
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     return y;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/analysis/differentiation/JacobianFunction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */