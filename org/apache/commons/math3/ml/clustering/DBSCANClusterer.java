/*     */ package org.apache.commons.math3.ml.clustering;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.math3.exception.NotPositiveException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.ml.distance.DistanceMeasure;
/*     */ import org.apache.commons.math3.ml.distance.EuclideanDistance;
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
/*     */ public class DBSCANClusterer<T extends Clusterable>
/*     */   extends Clusterer<T>
/*     */ {
/*     */   private final double eps;
/*     */   private final int minPts;
/*     */   
/*     */   private enum PointStatus
/*     */   {
/*  69 */     NOISE,
/*     */     
/*  71 */     PART_OF_CLUSTER;
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
/*     */   public DBSCANClusterer(double eps, int minPts) throws NotPositiveException {
/*  85 */     this(eps, minPts, (DistanceMeasure)new EuclideanDistance());
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
/*     */   public DBSCANClusterer(double eps, int minPts, DistanceMeasure measure) throws NotPositiveException {
/*  98 */     super(measure);
/*     */     
/* 100 */     if (eps < 0.0D) {
/* 101 */       throw new NotPositiveException(Double.valueOf(eps));
/*     */     }
/* 103 */     if (minPts < 0) {
/* 104 */       throw new NotPositiveException(Integer.valueOf(minPts));
/*     */     }
/* 106 */     this.eps = eps;
/* 107 */     this.minPts = minPts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEps() {
/* 115 */     return this.eps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinPts() {
/* 123 */     return this.minPts;
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
/*     */   public List<Cluster<T>> cluster(Collection<T> points) throws NullArgumentException {
/* 137 */     MathUtils.checkNotNull(points);
/*     */     
/* 139 */     List<Cluster<T>> clusters = new ArrayList<Cluster<T>>();
/* 140 */     Map<Clusterable, PointStatus> visited = new HashMap<Clusterable, PointStatus>();
/*     */     
/* 142 */     for (Clusterable clusterable : points) {
/* 143 */       if (visited.get(clusterable) != null) {
/*     */         continue;
/*     */       }
/* 146 */       List<T> neighbors = getNeighbors((T)clusterable, points);
/* 147 */       if (neighbors.size() >= this.minPts) {
/*     */         
/* 149 */         Cluster<T> cluster = new Cluster<T>();
/* 150 */         clusters.add(expandCluster(cluster, (T)clusterable, neighbors, points, visited)); continue;
/*     */       } 
/* 152 */       visited.put(clusterable, PointStatus.NOISE);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     return clusters;
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
/*     */   private Cluster<T> expandCluster(Cluster<T> cluster, T point, List<T> neighbors, Collection<T> points, Map<Clusterable, PointStatus> visited) {
/* 174 */     cluster.addPoint(point);
/* 175 */     visited.put((Clusterable)point, PointStatus.PART_OF_CLUSTER);
/*     */     
/* 177 */     List<T> seeds = new ArrayList<T>(neighbors);
/* 178 */     int index = 0;
/* 179 */     while (index < seeds.size()) {
/* 180 */       Clusterable clusterable = (Clusterable)seeds.get(index);
/* 181 */       PointStatus pStatus = visited.get(clusterable);
/*     */       
/* 183 */       if (pStatus == null) {
/* 184 */         List<T> currentNeighbors = getNeighbors((T)clusterable, points);
/* 185 */         if (currentNeighbors.size() >= this.minPts) {
/* 186 */           seeds = merge(seeds, currentNeighbors);
/*     */         }
/*     */       } 
/*     */       
/* 190 */       if (pStatus != PointStatus.PART_OF_CLUSTER) {
/* 191 */         visited.put(clusterable, PointStatus.PART_OF_CLUSTER);
/* 192 */         cluster.addPoint((T)clusterable);
/*     */       } 
/*     */       
/* 195 */       index++;
/*     */     } 
/* 197 */     return cluster;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<T> getNeighbors(T point, Collection<T> points) {
/* 208 */     List<T> neighbors = new ArrayList<T>();
/* 209 */     for (Clusterable clusterable : points) {
/* 210 */       if (point != clusterable && distance(clusterable, (Clusterable)point) <= this.eps) {
/* 211 */         neighbors.add((T)clusterable);
/*     */       }
/*     */     } 
/* 214 */     return neighbors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<T> merge(List<T> one, List<T> two) {
/* 225 */     Set<T> oneSet = new HashSet<T>(one);
/* 226 */     for (Clusterable clusterable : two) {
/* 227 */       if (!oneSet.contains(clusterable)) {
/* 228 */         one.add((T)clusterable);
/*     */       }
/*     */     } 
/* 231 */     return one;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/ml/clustering/DBSCANClusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */