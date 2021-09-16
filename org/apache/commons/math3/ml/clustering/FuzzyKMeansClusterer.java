/*     */ package org.apache.commons.math3.ml.clustering;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.MathIllegalStateException;
/*     */ import org.apache.commons.math3.exception.NumberIsTooSmallException;
/*     */ import org.apache.commons.math3.linear.MatrixUtils;
/*     */ import org.apache.commons.math3.linear.RealMatrix;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.distance.EuclideanDistance;
/*     */ import org.apache.commons.math3.random.JDKRandomGenerator;
/*     */ import org.apache.commons.math3.random.RandomGenerator;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ public class FuzzyKMeansClusterer<T extends Clusterable>
/*     */   extends Clusterer<T>
/*     */ {
/*     */   private static final double DEFAULT_EPSILON = 0.001D;
/*     */   private final int k;
/*     */   private final int maxIterations;
/*     */   private final double fuzziness;
/*     */   private final double epsilon;
/*     */   private final RandomGenerator random;
/*     */   private double[][] membershipMatrix;
/*     */   private List<T> points;
/*     */   private List<CentroidCluster<T>> clusters;
/*     */   
/*     */   public FuzzyKMeansClusterer(int k, double fuzziness) throws NumberIsTooSmallException {
/* 107 */     this(k, fuzziness, -1, (DistanceMeasure)new EuclideanDistance());
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
/*     */   public FuzzyKMeansClusterer(int k, double fuzziness, int maxIterations, DistanceMeasure measure) throws NumberIsTooSmallException {
/* 123 */     this(k, fuzziness, maxIterations, measure, 0.001D, (RandomGenerator)new JDKRandomGenerator());
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
/*     */   public FuzzyKMeansClusterer(int k, double fuzziness, int maxIterations, DistanceMeasure measure, double epsilon, RandomGenerator random) throws NumberIsTooSmallException {
/* 143 */     super(measure);
/*     */     
/* 145 */     if (fuzziness <= 1.0D) {
/* 146 */       throw new NumberIsTooSmallException(Double.valueOf(fuzziness), Double.valueOf(1.0D), false);
/*     */     }
/* 148 */     this.k = k;
/* 149 */     this.fuzziness = fuzziness;
/* 150 */     this.maxIterations = maxIterations;
/* 151 */     this.epsilon = epsilon;
/* 152 */     this.random = random;
/*     */     
/* 154 */     this.membershipMatrix = (double[][])null;
/* 155 */     this.points = null;
/* 156 */     this.clusters = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getK() {
/* 164 */     return this.k;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFuzziness() {
/* 172 */     return this.fuzziness;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIterations() {
/* 180 */     return this.maxIterations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEpsilon() {
/* 188 */     return this.epsilon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomGenerator getRandomGenerator() {
/* 196 */     return this.random;
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
/*     */   public RealMatrix getMembershipMatrix() {
/* 210 */     if (this.membershipMatrix == null) {
/* 211 */       throw new MathIllegalStateException();
/*     */     }
/* 213 */     return MatrixUtils.createRealMatrix(this.membershipMatrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> getDataPoints() {
/* 223 */     return this.points;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<CentroidCluster<T>> getClusters() {
/* 232 */     return this.clusters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getObjectiveFunctionValue() {
/* 241 */     if (this.points == null || this.clusters == null) {
/* 242 */       throw new MathIllegalStateException();
/*     */     }
/*     */     
/* 245 */     int i = 0;
/* 246 */     double objFunction = 0.0D;
/* 247 */     for (Clusterable clusterable : this.points) {
/* 248 */       int j = 0;
/* 249 */       for (CentroidCluster<T> cluster : this.clusters) {
/* 250 */         double dist = distance(clusterable, cluster.getCenter());
/* 251 */         objFunction += dist * dist * FastMath.pow(this.membershipMatrix[i][j], this.fuzziness);
/* 252 */         j++;
/*     */       } 
/* 254 */       i++;
/*     */     } 
/* 256 */     return objFunction;
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
/*     */   public List<CentroidCluster<T>> cluster(Collection<T> dataPoints) throws MathIllegalArgumentException {
/* 272 */     MathUtils.checkNotNull(dataPoints);
/*     */     
/* 274 */     int size = dataPoints.size();
/*     */ 
/*     */     
/* 277 */     if (size < this.k) {
/* 278 */       throw new NumberIsTooSmallException(Integer.valueOf(size), Integer.valueOf(this.k), false);
/*     */     }
/*     */ 
/*     */     
/* 282 */     this.points = Collections.unmodifiableList(new ArrayList<T>(dataPoints));
/* 283 */     this.clusters = new ArrayList<CentroidCluster<T>>();
/* 284 */     this.membershipMatrix = new double[size][this.k];
/* 285 */     double[][] oldMatrix = new double[size][this.k];
/*     */ 
/*     */     
/* 288 */     if (size == 0) {
/* 289 */       return this.clusters;
/*     */     }
/*     */     
/* 292 */     initializeMembershipMatrix();
/*     */ 
/*     */     
/* 295 */     int pointDimension = (((Clusterable)this.points.get(0)).getPoint()).length;
/* 296 */     for (int i = 0; i < this.k; i++) {
/* 297 */       this.clusters.add(new CentroidCluster<T>(new DoublePoint(new double[pointDimension])));
/*     */     }
/*     */     
/* 300 */     int iteration = 0;
/* 301 */     int max = (this.maxIterations < 0) ? Integer.MAX_VALUE : this.maxIterations;
/* 302 */     double difference = 0.0D;
/*     */     
/*     */     do {
/* 305 */       saveMembershipMatrix(oldMatrix);
/* 306 */       updateClusterCenters();
/* 307 */       updateMembershipMatrix();
/* 308 */       difference = calculateMaxMembershipChange(oldMatrix);
/* 309 */     } while (difference > this.epsilon && ++iteration < max);
/*     */     
/* 311 */     return this.clusters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateClusterCenters() {
/* 318 */     int j = 0;
/* 319 */     List<CentroidCluster<T>> newClusters = new ArrayList<CentroidCluster<T>>(this.k);
/* 320 */     for (CentroidCluster<T> cluster : this.clusters) {
/* 321 */       Clusterable center = cluster.getCenter();
/* 322 */       int i = 0;
/* 323 */       double[] arr = new double[(center.getPoint()).length];
/* 324 */       double sum = 0.0D;
/* 325 */       for (Clusterable clusterable : this.points) {
/* 326 */         double u = FastMath.pow(this.membershipMatrix[i][j], this.fuzziness);
/* 327 */         double[] pointArr = clusterable.getPoint();
/* 328 */         for (int idx = 0; idx < arr.length; idx++) {
/* 329 */           arr[idx] = arr[idx] + u * pointArr[idx];
/*     */         }
/* 331 */         sum += u;
/* 332 */         i++;
/*     */       } 
/* 334 */       MathArrays.scaleInPlace(1.0D / sum, arr);
/* 335 */       newClusters.add(new CentroidCluster<T>(new DoublePoint(arr)));
/* 336 */       j++;
/*     */     } 
/* 338 */     this.clusters.clear();
/* 339 */     this.clusters = newClusters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateMembershipMatrix() {
/* 347 */     for (int i = 0; i < this.points.size(); i++) {
/* 348 */       Clusterable clusterable = (Clusterable)this.points.get(i);
/* 349 */       double maxMembership = Double.MIN_VALUE;
/* 350 */       int newCluster = -1;
/* 351 */       for (int j = 0; j < this.clusters.size(); j++) {
/* 352 */         double membership, sum = 0.0D;
/* 353 */         double distA = FastMath.abs(distance(clusterable, ((CentroidCluster)this.clusters.get(j)).getCenter()));
/*     */         
/* 355 */         if (distA != 0.0D) {
/* 356 */           for (CentroidCluster<T> c : this.clusters) {
/* 357 */             double distB = FastMath.abs(distance(clusterable, c.getCenter()));
/* 358 */             if (distB == 0.0D) {
/* 359 */               sum = Double.POSITIVE_INFINITY;
/*     */               break;
/*     */             } 
/* 362 */             sum += FastMath.pow(distA / distB, 2.0D / (this.fuzziness - 1.0D));
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 367 */         if (sum == 0.0D) {
/* 368 */           membership = 1.0D;
/* 369 */         } else if (sum == Double.POSITIVE_INFINITY) {
/* 370 */           membership = 0.0D;
/*     */         } else {
/* 372 */           membership = 1.0D / sum;
/*     */         } 
/* 374 */         this.membershipMatrix[i][j] = membership;
/*     */         
/* 376 */         if (this.membershipMatrix[i][j] > maxMembership) {
/* 377 */           maxMembership = this.membershipMatrix[i][j];
/* 378 */           newCluster = j;
/*     */         } 
/*     */       } 
/* 381 */       ((CentroidCluster<Clusterable>)this.clusters.get(newCluster)).addPoint(clusterable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeMembershipMatrix() {
/* 389 */     for (int i = 0; i < this.points.size(); i++) {
/* 390 */       for (int j = 0; j < this.k; j++) {
/* 391 */         this.membershipMatrix[i][j] = this.random.nextDouble();
/*     */       }
/* 393 */       this.membershipMatrix[i] = MathArrays.normalizeArray(this.membershipMatrix[i], 1.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double calculateMaxMembershipChange(double[][] matrix) {
/* 405 */     double maxMembership = 0.0D;
/* 406 */     for (int i = 0; i < this.points.size(); i++) {
/* 407 */       for (int j = 0; j < this.clusters.size(); j++) {
/* 408 */         double v = FastMath.abs(this.membershipMatrix[i][j] - matrix[i][j]);
/* 409 */         maxMembership = FastMath.max(v, maxMembership);
/*     */       } 
/*     */     } 
/* 412 */     return maxMembership;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveMembershipMatrix(double[][] matrix) {
/* 421 */     for (int i = 0; i < this.points.size(); i++)
/* 422 */       System.arraycopy(this.membershipMatrix[i], 0, matrix[i], 0, this.clusters.size()); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/FuzzyKMeansClusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */