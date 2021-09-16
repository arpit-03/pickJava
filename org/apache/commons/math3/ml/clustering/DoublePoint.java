/*    */ package org.apache.commons.math3.ml.clustering;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class DoublePoint
/*    */   implements Clusterable, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3946024775784901369L;
/*    */   private final double[] point;
/*    */   
/*    */   public DoublePoint(double[] point) {
/* 43 */     this.point = point;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DoublePoint(int[] point) {
/* 54 */     this.point = new double[point.length];
/* 55 */     for (int i = 0; i < point.length; i++) {
/* 56 */       this.point[i] = point[i];
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] getPoint() {
/* 62 */     return this.point;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 68 */     if (!(other instanceof DoublePoint)) {
/* 69 */       return false;
/*    */     }
/* 71 */     return Arrays.equals(this.point, ((DoublePoint)other).point);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     return Arrays.hashCode(this.point);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 83 */     return Arrays.toString(this.point);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/DoublePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */