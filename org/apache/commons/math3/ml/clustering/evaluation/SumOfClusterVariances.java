/*    */ package org.apache.commons.math3.ml.clustering.evaluation;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.commons.math3.ml.clustering.Cluster;
/*    */ import org.apache.commons.math3.ml.clustering.Clusterable;
/*    */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*    */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
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
/*    */ public class SumOfClusterVariances<T extends Clusterable>
/*    */   extends ClusterEvaluator<T>
/*    */ {
/*    */   public SumOfClusterVariances(DistanceMeasure measure) {
/* 45 */     super(measure);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public double score(List<? extends Cluster<T>> clusters) {
/* 51 */     double varianceSum = 0.0D;
/* 52 */     for (Cluster<T> cluster : clusters) {
/* 53 */       if (!cluster.getPoints().isEmpty()) {
/*    */         
/* 55 */         Clusterable center = centroidOf(cluster);
/*    */ 
/*    */         
/* 58 */         Variance stat = new Variance();
/* 59 */         for (Clusterable clusterable : cluster.getPoints()) {
/* 60 */           stat.increment(distance(clusterable, center));
/*    */         }
/* 62 */         varianceSum += stat.getResult();
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     return varianceSum;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/evaluation/SumOfClusterVariances.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */