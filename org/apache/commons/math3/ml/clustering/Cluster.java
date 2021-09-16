/*    */ package org.apache.commons.math3.ml.clustering;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class Cluster<T extends Clusterable>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3442297081515880464L;
/* 41 */   private final List<T> points = new ArrayList<T>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPoint(T point) {
/* 49 */     this.points.add(point);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> getPoints() {
/* 57 */     return this.points;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/Cluster.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */