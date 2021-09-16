/*     */ package org.apache.commons.math3.ml.clustering.evaluation;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.ml.clustering.CentroidCluster;
/*     */ import org.apache.commons.math3.ml.clustering.Cluster;
/*     */ import org.apache.commons.math3.ml.clustering.Clusterable;
/*     */ import org.apache.commons.math3.ml.clustering.DoublePoint;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.distance.EuclideanDistance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ClusterEvaluator<T extends Clusterable>
/*     */ {
/*     */   private final DistanceMeasure measure;
/*     */   
/*     */   public ClusterEvaluator() {
/*  45 */     this((DistanceMeasure)new EuclideanDistance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClusterEvaluator(DistanceMeasure measure) {
/*  53 */     this.measure = measure;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double score(List<? extends Cluster<T>> paramList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBetterScore(double score1, double score2) {
/*  75 */     return (score1 < score2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double distance(Clusterable p1, Clusterable p2) {
/*  87 */     return this.measure.compute(p1.getPoint(), p2.getPoint());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Clusterable centroidOf(Cluster<T> cluster) {
/*  98 */     List<T> points = cluster.getPoints();
/*  99 */     if (points.isEmpty()) {
/* 100 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 104 */     if (cluster instanceof CentroidCluster) {
/* 105 */       return ((CentroidCluster)cluster).getCenter();
/*     */     }
/*     */     
/* 108 */     int dimension = (((Clusterable)points.get(0)).getPoint()).length;
/* 109 */     double[] centroid = new double[dimension];
/* 110 */     for (Clusterable clusterable : points) {
/* 111 */       double[] point = clusterable.getPoint();
/* 112 */       for (int j = 0; j < centroid.length; j++) {
/* 113 */         centroid[j] = centroid[j] + point[j];
/*     */       }
/*     */     } 
/* 116 */     for (int i = 0; i < centroid.length; i++) {
/* 117 */       centroid[i] = centroid[i] / points.size();
/*     */     }
/* 119 */     return (Clusterable)new DoublePoint(centroid);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/evaluation/ClusterEvaluator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */