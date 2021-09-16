/*    */ package org.apache.commons.math3.optim;
/*    */ 
/*    */ import java.util.Arrays;
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
/*    */ public class SimpleBounds
/*    */   implements OptimizationData
/*    */ {
/*    */   private final double[] lower;
/*    */   private final double[] upper;
/*    */   
/*    */   public SimpleBounds(double[] lB, double[] uB) {
/* 42 */     this.lower = (double[])lB.clone();
/* 43 */     this.upper = (double[])uB.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getLower() {
/* 52 */     return (double[])this.lower.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getUpper() {
/* 60 */     return (double[])this.upper.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static SimpleBounds unbounded(int dim) {
/* 72 */     double[] lB = new double[dim];
/* 73 */     Arrays.fill(lB, Double.NEGATIVE_INFINITY);
/* 74 */     double[] uB = new double[dim];
/* 75 */     Arrays.fill(uB, Double.POSITIVE_INFINITY);
/*    */     
/* 77 */     return new SimpleBounds(lB, uB);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/SimpleBounds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */