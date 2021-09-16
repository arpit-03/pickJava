/*    */ package org.apache.commons.math3.stat.clustering;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import org.apache.commons.math3.util.MathArrays;
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
/*    */ public class EuclideanIntegerPoint
/*    */   implements Clusterable<EuclideanIntegerPoint>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 3946024775784901369L;
/*    */   private final int[] point;
/*    */   
/*    */   public EuclideanIntegerPoint(int[] point) {
/* 47 */     this.point = point;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getPoint() {
/* 55 */     return this.point;
/*    */   }
/*    */ 
/*    */   
/*    */   public double distanceFrom(EuclideanIntegerPoint p) {
/* 60 */     return MathArrays.distance(this.point, p.getPoint());
/*    */   }
/*    */ 
/*    */   
/*    */   public EuclideanIntegerPoint centroidOf(Collection<EuclideanIntegerPoint> points) {
/* 65 */     int[] centroid = new int[(getPoint()).length];
/* 66 */     for (EuclideanIntegerPoint p : points) {
/* 67 */       for (int j = 0; j < centroid.length; j++) {
/* 68 */         centroid[j] = centroid[j] + p.getPoint()[j];
/*    */       }
/*    */     } 
/* 71 */     for (int i = 0; i < centroid.length; i++) {
/* 72 */       centroid[i] = centroid[i] / points.size();
/*    */     }
/* 74 */     return new EuclideanIntegerPoint(centroid);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 80 */     if (!(other instanceof EuclideanIntegerPoint)) {
/* 81 */       return false;
/*    */     }
/* 83 */     return Arrays.equals(this.point, ((EuclideanIntegerPoint)other).point);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 89 */     return Arrays.hashCode(this.point);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 98 */     return Arrays.toString(this.point);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/clustering/EuclideanIntegerPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */