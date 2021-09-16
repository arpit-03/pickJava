/*     */ package org.apache.commons.math3.ml.clustering;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.ml.clustering.evaluation.ClusterEvaluator;
/*     */ import org.apache.commons.math3.ml.clustering.evaluation.SumOfClusterVariances;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultiKMeansPlusPlusClusterer<T extends Clusterable>
/*     */   extends Clusterer<T>
/*     */ {
/*     */   private final KMeansPlusPlusClusterer<T> clusterer;
/*     */   private final int numTrials;
/*     */   private final ClusterEvaluator<T> evaluator;
/*     */   
/*     */   public MultiKMeansPlusPlusClusterer(KMeansPlusPlusClusterer<T> clusterer, int numTrials) {
/*  51 */     this(clusterer, numTrials, (ClusterEvaluator<T>)new SumOfClusterVariances(clusterer.getDistanceMeasure()));
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
/*     */   public MultiKMeansPlusPlusClusterer(KMeansPlusPlusClusterer<T> clusterer, int numTrials, ClusterEvaluator<T> evaluator) {
/*  63 */     super(clusterer.getDistanceMeasure());
/*  64 */     this.clusterer = clusterer;
/*  65 */     this.numTrials = numTrials;
/*  66 */     this.evaluator = evaluator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KMeansPlusPlusClusterer<T> getClusterer() {
/*  74 */     return this.clusterer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumTrials() {
/*  82 */     return this.numTrials;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClusterEvaluator<T> getClusterEvaluator() {
/*  91 */     return this.evaluator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CentroidCluster<T>> cluster(Collection<T> points) throws MathIllegalArgumentException, ConvergenceException {
/* 110 */     List<CentroidCluster<T>> best = null;
/* 111 */     double bestVarianceSum = Double.POSITIVE_INFINITY;
/*     */ 
/*     */     
/* 114 */     for (int i = 0; i < this.numTrials; i++) {
/*     */ 
/*     */       
/* 117 */       List<CentroidCluster<T>> clusters = this.clusterer.cluster(points);
/*     */ 
/*     */       
/* 120 */       double varianceSum = this.evaluator.score(clusters);
/*     */       
/* 122 */       if (this.evaluator.isBetterScore(varianceSum, bestVarianceSum)) {
/*     */         
/* 124 */         best = clusters;
/* 125 */         bestVarianceSum = varianceSum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 131 */     return best;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/MultiKMeansPlusPlusClusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */