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
/*    */ 
/*    */ @Deprecated
/*    */ public class EuclideanDoublePoint
/*    */   implements Clusterable<EuclideanDoublePoint>, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 8026472786091227632L;
/*    */   private final double[] point;
/*    */   
/*    */   public EuclideanDoublePoint(double[] point) {
/* 48 */     this.point = point;
/*    */   }
/*    */ 
/*    */   
/*    */   public EuclideanDoublePoint centroidOf(Collection<EuclideanDoublePoint> points) {
/* 53 */     double[] centroid = new double[(getPoint()).length];
/* 54 */     for (EuclideanDoublePoint p : points) {
/* 55 */       for (int j = 0; j < centroid.length; j++) {
/* 56 */         centroid[j] = centroid[j] + p.getPoint()[j];
/*    */       }
/*    */     } 
/* 59 */     for (int i = 0; i < centroid.length; i++) {
/* 60 */       centroid[i] = centroid[i] / points.size();
/*    */     }
/* 62 */     return new EuclideanDoublePoint(centroid);
/*    */   }
/*    */ 
/*    */   
/*    */   public double distanceFrom(EuclideanDoublePoint p) {
/* 67 */     return MathArrays.distance(this.point, p.getPoint());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 73 */     if (!(other instanceof EuclideanDoublePoint)) {
/* 74 */       return false;
/*    */     }
/* 76 */     return Arrays.equals(this.point, ((EuclideanDoublePoint)other).point);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getPoint() {
/* 85 */     return this.point;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 91 */     return Arrays.hashCode(this.point);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 97 */     return Arrays.toString(this.point);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/clustering/EuclideanDoublePoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */