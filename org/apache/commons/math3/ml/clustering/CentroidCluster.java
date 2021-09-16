/*    */ package org.apache.commons.math3.ml.clustering;
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
/*    */ public class CentroidCluster<T extends Clusterable>
/*    */   extends Cluster<T>
/*    */ {
/*    */   private static final long serialVersionUID = -3075288519071812288L;
/*    */   private final Clusterable center;
/*    */   
/*    */   public CentroidCluster(Clusterable center) {
/* 42 */     this.center = center;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Clusterable getCenter() {
/* 50 */     return this.center;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/CentroidCluster.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */