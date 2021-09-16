/*     */ package org.apache.commons.math3.ml.clustering;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.distance.EuclideanDistance;
/*     */ import org.apache.commons.math3.random.JDKRandomGenerator;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
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
/*     */ public class KMeansPlusPlusClusterer<T extends Clusterable>
/*     */   extends Clusterer<T>
/*     */ {
/*     */   private final int k;
/*     */   private final int maxIterations;
/*     */   private final RandomGenerator random;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KMeansPlusPlusClusterer(int k) {
/*  83 */     this(k, -1);
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
/*     */   public KMeansPlusPlusClusterer(int k, int maxIterations) {
/*  98 */     this(k, maxIterations, (DistanceMeasure)new EuclideanDistance());
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
/*     */   public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure) {
/* 112 */     this(k, maxIterations, measure, (RandomGenerator)new JDKRandomGenerator());
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
/*     */   public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure, RandomGenerator random) {
/* 129 */     this(k, maxIterations, measure, random, EmptyClusterStrategy.LARGEST_VARIANCE);
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
/*     */   public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure, RandomGenerator random, EmptyClusterStrategy emptyStrategy) {
/* 146 */     super(measure);
/* 147 */     this.k = k;
/* 148 */     this.maxIterations = maxIterations;
/* 149 */     this.random = random;
/* 150 */     this.emptyStrategy = emptyStrategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getK() {
/* 158 */     return this.k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIterations() {
/* 166 */     return this.maxIterations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomGenerator getRandomGenerator() {
/* 174 */     return this.random;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EmptyClusterStrategy getEmptyClusterStrategy() {
/* 182 */     return this.emptyStrategy;
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
/*     */   public List<CentroidCluster<T>> cluster(Collection<T> points) throws MathIllegalArgumentException, ConvergenceException {
/* 200 */     MathUtils.checkNotNull(points);
/*     */ 
/*     */     
/* 203 */     if (points.size() < this.k) {
/* 204 */       throw new NumberIsTooSmallException(Integer.valueOf(points.size()), Integer.valueOf(this.k), false);
/*     */     }
/*     */ 
/*     */     
/* 208 */     List<CentroidCluster<T>> clusters = chooseInitialCenters(points);
/*     */ 
/*     */ 
/*     */     
/* 212 */     int[] assignments = new int[points.size()];
/* 213 */     assignPointsToClusters(clusters, points, assignments);
/*     */ 
/*     */     
/* 216 */     int max = (this.maxIterations < 0) ? Integer.MAX_VALUE : this.maxIterations;
/* 217 */     for (int count = 0; count < max; count++) {
/* 218 */       boolean emptyCluster = false;
/* 219 */       List<CentroidCluster<T>> newClusters = new ArrayList<CentroidCluster<T>>();
/* 220 */       for (CentroidCluster<T> cluster : clusters) {
/*     */         Clusterable newCenter;
/* 222 */         if (cluster.getPoints().isEmpty()) {
/* 223 */           switch (this.emptyStrategy) {
/*     */             case LARGEST_VARIANCE:
/* 225 */               newCenter = (Clusterable)getPointFromLargestVarianceCluster(clusters);
/*     */               break;
/*     */             case LARGEST_POINTS_NUMBER:
/* 228 */               newCenter = (Clusterable)getPointFromLargestNumberCluster((Collection)clusters);
/*     */               break;
/*     */             case FARTHEST_POINT:
/* 231 */               newCenter = (Clusterable)getFarthestPoint(clusters);
/*     */               break;
/*     */             default:
/* 234 */               throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */           } 
/* 236 */           emptyCluster = true;
/*     */         } else {
/* 238 */           newCenter = centroidOf(cluster.getPoints(), (cluster.getCenter().getPoint()).length);
/*     */         } 
/* 240 */         newClusters.add(new CentroidCluster<T>(newCenter));
/*     */       } 
/* 242 */       int changes = assignPointsToClusters(newClusters, points, assignments);
/* 243 */       clusters = newClusters;
/*     */ 
/*     */ 
/*     */       
/* 247 */       if (changes == 0 && !emptyCluster) {
/* 248 */         return clusters;
/*     */       }
/*     */     } 
/* 251 */     return clusters;
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
/*     */   private int assignPointsToClusters(List<CentroidCluster<T>> clusters, Collection<T> points, int[] assignments) {
/* 265 */     int assignedDifferently = 0;
/* 266 */     int pointIndex = 0;
/* 267 */     for (Clusterable clusterable : points) {
/* 268 */       int clusterIndex = getNearestCluster(clusters, (T)clusterable);
/* 269 */       if (clusterIndex != assignments[pointIndex]) {
/* 270 */         assignedDifferently++;
/*     */       }
/*     */       
/* 273 */       CentroidCluster<T> cluster = clusters.get(clusterIndex);
/* 274 */       cluster.addPoint((T)clusterable);
/* 275 */       assignments[pointIndex++] = clusterIndex;
/*     */     } 
/*     */     
/* 278 */     return assignedDifferently;
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
/*     */   private List<CentroidCluster<T>> chooseInitialCenters(Collection<T> points) {
/* 291 */     List<T> pointList = Collections.unmodifiableList(new ArrayList<T>(points));
/*     */ 
/*     */     
/* 294 */     int numPoints = pointList.size();
/*     */ 
/*     */ 
/*     */     
/* 298 */     boolean[] taken = new boolean[numPoints];
/*     */ 
/*     */     
/* 301 */     List<CentroidCluster<T>> resultSet = new ArrayList<CentroidCluster<T>>();
/*     */ 
/*     */     
/* 304 */     int firstPointIndex = this.random.nextInt(numPoints);
/*     */     
/* 306 */     Clusterable clusterable = (Clusterable)pointList.get(firstPointIndex);
/*     */     
/* 308 */     resultSet.add(new CentroidCluster<T>(clusterable));
/*     */ 
/*     */     
/* 311 */     taken[firstPointIndex] = true;
/*     */ 
/*     */ 
/*     */     
/* 315 */     double[] minDistSquared = new double[numPoints];
/*     */ 
/*     */ 
/*     */     
/* 319 */     for (int i = 0; i < numPoints; i++) {
/* 320 */       if (i != firstPointIndex) {
/* 321 */         double d = distance(clusterable, (Clusterable)pointList.get(i));
/* 322 */         minDistSquared[i] = d * d;
/*     */       } 
/*     */     } 
/*     */     
/* 326 */     while (resultSet.size() < this.k) {
/*     */ 
/*     */ 
/*     */       
/* 330 */       double distSqSum = 0.0D;
/*     */       
/* 332 */       for (int j = 0; j < numPoints; j++) {
/* 333 */         if (!taken[j]) {
/* 334 */           distSqSum += minDistSquared[j];
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 340 */       double r = this.random.nextDouble() * distSqSum;
/*     */ 
/*     */       
/* 343 */       int nextPointIndex = -1;
/*     */ 
/*     */ 
/*     */       
/* 347 */       double sum = 0.0D; int k;
/* 348 */       for (k = 0; k < numPoints; k++) {
/* 349 */         if (!taken[k]) {
/* 350 */           sum += minDistSquared[k];
/* 351 */           if (sum >= r) {
/* 352 */             nextPointIndex = k;
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 361 */       if (nextPointIndex == -1) {
/* 362 */         for (k = numPoints - 1; k >= 0; k--) {
/* 363 */           if (!taken[k]) {
/* 364 */             nextPointIndex = k;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 371 */       if (nextPointIndex >= 0) {
/*     */         
/* 373 */         Clusterable clusterable1 = (Clusterable)pointList.get(nextPointIndex);
/*     */         
/* 375 */         resultSet.add(new CentroidCluster<T>(clusterable1));
/*     */ 
/*     */         
/* 378 */         taken[nextPointIndex] = true;
/*     */         
/* 380 */         if (resultSet.size() < this.k)
/*     */         {
/*     */           
/* 383 */           for (int m = 0; m < numPoints; m++) {
/*     */             
/* 385 */             if (!taken[m]) {
/* 386 */               double d = distance(clusterable1, (Clusterable)pointList.get(m));
/* 387 */               double d2 = d * d;
/* 388 */               if (d2 < minDistSquared[m]) {
/* 389 */                 minDistSquared[m] = d2;
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
/* 403 */     return resultSet;
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
/*     */   private T getPointFromLargestVarianceCluster(Collection<CentroidCluster<T>> clusters) throws ConvergenceException {
/* 416 */     double maxVariance = Double.NEGATIVE_INFINITY;
/* 417 */     Cluster<T> selected = null;
/* 418 */     for (CentroidCluster<T> cluster : clusters) {
/* 419 */       if (!cluster.getPoints().isEmpty()) {
/*     */ 
/*     */         
/* 422 */         Clusterable center = cluster.getCenter();
/* 423 */         Variance stat = new Variance();
/* 424 */         for (Clusterable clusterable : cluster.getPoints()) {
/* 425 */           stat.increment(distance(clusterable, center));
/*     */         }
/* 427 */         double variance = stat.getResult();
/*     */ 
/*     */         
/* 430 */         if (variance > maxVariance) {
/* 431 */           maxVariance = variance;
/* 432 */           selected = cluster;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 439 */     if (selected == null) {
/* 440 */       throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 444 */     List<T> selectedPoints = selected.getPoints();
/* 445 */     return selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
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
/*     */   private T getPointFromLargestNumberCluster(Collection<? extends Cluster<T>> clusters) throws ConvergenceException {
/* 459 */     int maxNumber = 0;
/* 460 */     Cluster<T> selected = null;
/* 461 */     for (Cluster<T> cluster : clusters) {
/*     */ 
/*     */       
/* 464 */       int number = cluster.getPoints().size();
/*     */ 
/*     */       
/* 467 */       if (number > maxNumber) {
/* 468 */         maxNumber = number;
/* 469 */         selected = cluster;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 475 */     if (selected == null) {
/* 476 */       throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */     }
/*     */ 
/*     */     
/* 480 */     List<T> selectedPoints = selected.getPoints();
/* 481 */     return selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
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
/*     */   private T getFarthestPoint(Collection<CentroidCluster<T>> clusters) throws ConvergenceException {
/* 494 */     double maxDistance = Double.NEGATIVE_INFINITY;
/* 495 */     Cluster<T> selectedCluster = null;
/* 496 */     int selectedPoint = -1;
/* 497 */     for (CentroidCluster<T> cluster : clusters) {
/*     */ 
/*     */       
/* 500 */       Clusterable center = cluster.getCenter();
/* 501 */       List<T> points = cluster.getPoints();
/* 502 */       for (int i = 0; i < points.size(); i++) {
/* 503 */         double distance = distance((Clusterable)points.get(i), center);
/* 504 */         if (distance > maxDistance) {
/* 505 */           maxDistance = distance;
/* 506 */           selectedCluster = cluster;
/* 507 */           selectedPoint = i;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 514 */     if (selectedCluster == null) {
/* 515 */       throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
/*     */     }
/*     */     
/* 518 */     return (T)selectedCluster.getPoints().remove(selectedPoint);
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
/*     */   private int getNearestCluster(Collection<CentroidCluster<T>> clusters, T point) {
/* 530 */     double minDistance = Double.MAX_VALUE;
/* 531 */     int clusterIndex = 0;
/* 532 */     int minCluster = 0;
/* 533 */     for (CentroidCluster<T> c : clusters) {
/* 534 */       double distance = distance((Clusterable)point, c.getCenter());
/* 535 */       if (distance < minDistance) {
/* 536 */         minDistance = distance;
/* 537 */         minCluster = clusterIndex;
/*     */       } 
/* 539 */       clusterIndex++;
/*     */     } 
/* 541 */     return minCluster;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Clusterable centroidOf(Collection<T> points, int dimension) {
/* 552 */     double[] centroid = new double[dimension];
/* 553 */     for (Clusterable clusterable : points) {
/* 554 */       double[] point = clusterable.getPoint();
/* 555 */       for (int j = 0; j < centroid.length; j++) {
/* 556 */         centroid[j] = centroid[j] + point[j];
/*     */       }
/*     */     } 
/* 559 */     for (int i = 0; i < centroid.length; i++) {
/* 560 */       centroid[i] = centroid[i] / points.size();
/*     */     }
/* 562 */     return new DoublePoint(centroid);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/KMeansPlusPlusClusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */