/*    */ package org.apache.commons.math3.stat.clustering;
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
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class Cluster<T extends Clusterable<T>>
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -3442297081515880464L;
/*    */   private final List<T> points;
/*    */   private final T center;
/*    */   
/*    */   public Cluster(T center) {
/* 48 */     this.center = center;
/* 49 */     this.points = new ArrayList<T>();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPoint(T point) {
/* 57 */     this.points.add(point);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> getPoints() {
/* 65 */     return this.points;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T getCenter() {
/* 73 */     return this.center;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/clustering/Cluster.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */