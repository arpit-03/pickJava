/*     */ package org.apache.commons.math3.stat.clustering;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.stat.descriptive.moment.Variance;
/*     */ import org.apache.commons.math3.util.MathUtils;
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
/*     */ @Deprecated
/*     */ public class KMeansPlusPlusClusterer<T extends Clusterable<T>>
/*     */ {
/*     */   private final Random random;
/*     */   private final EmptyClusterStrategy emptyStrategy;
/*     */   
/*     */   public enum EmptyClusterStrategy
/*     */   {
/*  48 */     LARGEST_VARIANCE,
/*     */ 
/*     */     
/*  51 */     LARGEST_POINTS_NUMBER,
/*     */ 
/*     */     
/*  54 */     FARTHEST_POINT,
/*     */ 
/*     */     
/*  57 */     ERROR;
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
/*     */   public KMeansPlusPlusClusterer(Random random) {
/*  75 */     this(random, EmptyClusterStrategy.LARGEST_VARIANCE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KMeansPlusPlusClusterer(Random random, EmptyClusterStrategy emptyStrategy) {
/*  85 */     this.random = random;
/*  86 */     this.emptyStrategy = emptyStrategy;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Cluster<T>> cluster(Collection<T> points, int k, int numTrials, int maxIterationsPerTrial) throws MathIllegalArgumentException, ConvergenceException {
/* 108 */     List<Cluster<T>> best = null;
/* 109 */     double bestVarianceSum = Double.POSITIVE_INFINITY;
/*     */ 
/*     */     
/* 112 */     for (int i = 0; i < numTrials; i++) {
/*     */ 
/*     */       
/* 115 */       List<Cluster<T>> clusters = cluster(points, k, maxIterationsPerTrial);
/*     */ 
/*     */       
/* 118 */       double varianceSum = 0.0D;
/* 119 */       for (Cluster<T> cluster : clusters) {
/* 120 */         if (!cluster.getPoints().isEmpty()) {
/*     */ 
/*     */           
/* 123 */           T center = cluster.getCenter();
/* 124 */           Variance stat = new Variance();
/* 125 */           for (Clusterable<T> clusterable : cluster.getPoints()) {
/* 126 */             stat.increment(clusterable.distanceFrom(center));
/*     */           }
/* 128 */           varianceSum += stat.getResult();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 133 */       if (varianceSum <= bestVarianceSum) {
/*     */         
/* 135 */         best = clusters;
/* 136 */         bestVarianceSum = varianceSum;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     return best;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Cluster<T>> cluster(Collection<T> points, int k, int maxIterations) throws MathIllegalArgumentException, ConvergenceException {
/* 164 */     MathUtils.checkNotNull(points);
/*     */ 
/*     */     
/* 167 */     if (points.size() < k) {
/* 168 */       throw new NumberIsTooSmallException(Integer.valueOf(points.size()), Integer.valueOf(k), false);
/*     */     }
/*     */ 
/*     */     
/* 172 */     List<Cluster<T>> clusters = chooseInitialCenters(points, k, this.random);
/*     */ 
/*     */ 
/*     */     
/* 176 */     int[] assignments = new int[points.size()];
/* 177 */     assignPointsToClusters(clusters, points, assignments);
/*     */ 
/*     */     
/* 180 */     int max = (maxIterations < 0) ? Integer.MAX_VALUE : maxIterations;
/* 181 */     for (int count = 0; count < max; count++) {
/* 182 */       boolean emptyCluster = false;
/* 183 */       List<Cluster<T>> newClusters = new ArrayList<Cluster<T>>();
/* 184 */       for (Cluster<T> cluster : clusters) {
/*     */         Clusterable clusterable;
/* 186 */         if (cluster.getPoints().isEmpty()) {
/* 187 */           T newCenter; switch (this.emptyStrategy) {
/*     */             case LARGEST_VARIANCE:
/* 189 */               newCenter = getPointFromLargestVarianceCluster(clusters);
/*     */               break;
/*     */             case LARGEST_POINTS_NUMBER:
/* 192 */               newCenter = getPointFromLargestNumberCluster(clusters);
/*     */               break;
/*     */             case FARTHEST_POINT:
/* 195 */               newCenter = getFarthestPoint(clusters);
/*     */               break;
/*     */             default:
/* 198 */               throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */           } 
/* 200 */           emptyCluster = true;
/*     */         } else {
/* 202 */           clusterable = cluster.getCenter().centroidOf(cluster.getPoints());
/*     */         } 
/* 204 */         newClusters.add(new Cluster<T>((T)clusterable));
/*     */       } 
/* 206 */       int changes = assignPointsToClusters(newClusters, points, assignments);
/* 207 */       clusters = newClusters;
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (changes == 0 && !emptyCluster) {
/* 212 */         return clusters;
/*     */       }
/*     */     } 
/* 215 */     return clusters;
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
/*     */   private static <T extends Clusterable<T>> int assignPointsToClusters(List<Cluster<T>> clusters, Collection<T> points, int[] assignments) {
/* 230 */     int assignedDifferently = 0;
/* 231 */     int pointIndex = 0;
/* 232 */     for (Clusterable clusterable : points) {
/* 233 */       int clusterIndex = getNearestCluster(clusters, (T)clusterable);
/* 234 */       if (clusterIndex != assignments[pointIndex]) {
/* 235 */         assignedDifferently++;
/*     */       }
/*     */       
/* 238 */       Cluster<T> cluster = clusters.get(clusterIndex);
/* 239 */       cluster.addPoint((T)clusterable);
/* 240 */       assignments[pointIndex++] = clusterIndex;
/*     */     } 
/*     */     
/* 243 */     return assignedDifferently;
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
/*     */   private static <T extends Clusterable<T>> List<Cluster<T>> chooseInitialCenters(Collection<T> points, int k, Random random) {
/* 260 */     List<T> pointList = Collections.unmodifiableList(new ArrayList<T>(points));
/*     */ 
/*     */     
/* 263 */     int numPoints = pointList.size();
/*     */ 
/*     */ 
/*     */     
/* 267 */     boolean[] taken = new boolean[numPoints];
/*     */ 
/*     */     
/* 270 */     List<Cluster<T>> resultSet = new ArrayList<Cluster<T>>();
/*     */ 
/*     */     
/* 273 */     int firstPointIndex = random.nextInt(numPoints);
/*     */     
/* 275 */     Clusterable clusterable = (Clusterable)pointList.get(firstPointIndex);
/*     */     
/* 277 */     resultSet.add(new Cluster<T>((T)clusterable));
/*     */ 
/*     */     
/* 280 */     taken[firstPointIndex] = true;
/*     */ 
/*     */ 
/*     */     
/* 284 */     double[] minDistSquared = new double[numPoints];
/*     */ 
/*     */ 
/*     */     
/* 288 */     for (int i = 0; i < numPoints; i++) {
/* 289 */       if (i != firstPointIndex) {
/* 290 */         double d = clusterable.distanceFrom(pointList.get(i));
/* 291 */         minDistSquared[i] = d * d;
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     while (resultSet.size() < k) {
/*     */ 
/*     */ 
/*     */       
/* 299 */       double distSqSum = 0.0D;
/*     */       
/* 301 */       for (int j = 0; j < numPoints; j++) {
/* 302 */         if (!taken[j]) {
/* 303 */           distSqSum += minDistSquared[j];
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 309 */       double r = random.nextDouble() * distSqSum;
/*     */ 
/*     */       
/* 312 */       int nextPointIndex = -1;
/*     */ 
/*     */ 
/*     */       
/* 316 */       double sum = 0.0D; int m;
/* 317 */       for (m = 0; m < numPoints; m++) {
/* 318 */         if (!taken[m]) {
/* 319 */           sum += minDistSquared[m];
/* 320 */           if (sum >= r) {
/* 321 */             nextPointIndex = m;
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 330 */       if (nextPointIndex == -1) {
/* 331 */         for (m = numPoints - 1; m >= 0; m--) {
/* 332 */           if (!taken[m]) {
/* 333 */             nextPointIndex = m;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 340 */       if (nextPointIndex >= 0) {
/*     */         
/* 342 */         Clusterable clusterable1 = (Clusterable)pointList.get(nextPointIndex);
/*     */         
/* 344 */         resultSet.add(new Cluster<T>((T)clusterable1));
/*     */ 
/*     */         
/* 347 */         taken[nextPointIndex] = true;
/*     */         
/* 349 */         if (resultSet.size() < k)
/*     */         {
/*     */           
/* 352 */           for (int n = 0; n < numPoints; n++) {
/*     */             
/* 354 */             if (!taken[n]) {
/* 355 */               double d = clusterable1.distanceFrom(pointList.get(n));
/* 356 */               double d2 = d * d;
/* 357 */               if (d2 < minDistSquared[n]) {
/* 358 */                 minDistSquared[n] = d2;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 372 */     return resultSet;
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
/*     */   private T getPointFromLargestVarianceCluster(Collection<Cluster<T>> clusters) throws ConvergenceException {
/* 385 */     double maxVariance = Double.NEGATIVE_INFINITY;
/* 386 */     Cluster<T> selected = null;
/* 387 */     for (Cluster<T> cluster : clusters) {
/* 388 */       if (!cluster.getPoints().isEmpty()) {
/*     */ 
/*     */         
/* 391 */         T center = cluster.getCenter();
/* 392 */         Variance stat = new Variance();
/* 393 */         for (Clusterable<T> clusterable : cluster.getPoints()) {
/* 394 */           stat.increment(clusterable.distanceFrom(center));
/*     */         }
/* 396 */         double variance = stat.getResult();
/*     */ 
/*     */         
/* 399 */         if (variance > maxVariance) {
/* 400 */           maxVariance = variance;
/* 401 */           selected = cluster;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 408 */     if (selected == null) {
/* 409 */       throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 413 */     List<T> selectedPoints = selected.getPoints();
/* 414 */     return selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
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
/*     */   private T getPointFromLargestNumberCluster(Collection<Cluster<T>> clusters) throws ConvergenceException {
/* 427 */     int maxNumber = 0;
/* 428 */     Cluster<T> selected = null;
/* 429 */     for (Cluster<T> cluster : clusters) {
/*     */ 
/*     */       
/* 432 */       int number = cluster.getPoints().size();
/*     */ 
/*     */       
/* 435 */       if (number > maxNumber) {
/* 436 */         maxNumber = number;
/* 437 */         selected = cluster;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 443 */     if (selected == null) {
/* 444 */       throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 448 */     List<T> selectedPoints = selected.getPoints();
/* 449 */     return selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
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
/*     */   private T getFarthestPoint(Collection<Cluster<T>> clusters) throws ConvergenceException {
/* 462 */     double maxDistance = Double.NEGATIVE_INFINITY;
/* 463 */     Cluster<T> selectedCluster = null;
/* 464 */     int selectedPoint = -1;
/* 465 */     for (Cluster<T> cluster : clusters) {
/*     */ 
/*     */       
/* 468 */       T center = cluster.getCenter();
/* 469 */       List<T> points = cluster.getPoints();
/* 470 */       for (int i = 0; i < points.size(); i++) {
/* 471 */         double distance = ((Clusterable<T>)points.get(i)).distanceFrom(center);
/* 472 */         if (distance > maxDistance) {
/* 473 */           maxDistance = distance;
/* 474 */           selectedCluster = cluster;
/* 475 */           selectedPoint = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 482 */     if (selectedCluster == null) {
/* 483 */       throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */     }
/*     */     
/* 486 */     return (T)selectedCluster.getPoints().remove(selectedPoint);
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
/*     */   private static <T extends Clusterable<T>> int getNearestCluster(Collection<Cluster<T>> clusters, T point) {
/* 500 */     double minDistance = Double.MAX_VALUE;
/* 501 */     int clusterIndex = 0;
/* 502 */     int minCluster = 0;
/* 503 */     for (Cluster<T> c : clusters) {
/* 504 */       double distance = point.distanceFrom(c.getCenter());
/* 505 */       if (distance < minDistance) {
/* 506 */         minDistance = distance;
/* 507 */         minCluster = clusterIndex;
/*     */       } 
/* 509 */       clusterIndex++;
/*     */     } 
/* 511 */     return minCluster;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/clustering/KMeansPlusPlusClusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */