/*     */ package org.apache.commons.math3.stat.clustering;
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
/*     */ @Deprecated
/*     */ public class DBSCANClusterer<T extends Clusterable<T>>
/*     */ {
/*     */   private final double eps;
/*     */   private final int minPts;
/*     */   
/*     */   private enum PointStatus
/*     */   {
/*  74 */     NOISE,
/*     */     
/*  76 */     PART_OF_CLUSTER;
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
/*     */   public DBSCANClusterer(double eps, int minPts) throws NotPositiveException {
/*  88 */     if (eps < 0.0D) {
/*  89 */       throw new NotPositiveException(Double.valueOf(eps));
/*     */     }
/*  91 */     if (minPts < 0) {
/*  92 */       throw new NotPositiveException(Integer.valueOf(minPts));
/*     */     }
/*  94 */     this.eps = eps;
/*  95 */     this.minPts = minPts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEps() {
/* 104 */     return this.eps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinPts() {
/* 113 */     return this.minPts;
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
/*     */   public List<Cluster<T>> cluster(Collection<T> points) throws NullArgumentException {
/* 130 */     MathUtils.checkNotNull(points);
/*     */     
/* 132 */     List<Cluster<T>> clusters = new ArrayList<Cluster<T>>();
/* 133 */     Map<Clusterable<T>, PointStatus> visited = new HashMap<Clusterable<T>, PointStatus>();
/*     */     
/* 135 */     for (Clusterable<T> clusterable : points) {
/* 136 */       if (visited.get(clusterable) != null) {
/*     */         continue;
/*     */       }
/* 139 */       List<T> neighbors = getNeighbors((T)clusterable, points);
/* 140 */       if (neighbors.size() >= this.minPts) {
/*     */         
/* 142 */         Cluster<T> cluster = new Cluster<T>(null);
/* 143 */         clusters.add(expandCluster(cluster, (T)clusterable, neighbors, points, visited)); continue;
/*     */       } 
/* 145 */       visited.put(clusterable, PointStatus.NOISE);
/*     */     } 
/*     */ 
/*     */     
/* 149 */     return clusters;
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
/*     */   private Cluster<T> expandCluster(Cluster<T> cluster, T point, List<T> neighbors, Collection<T> points, Map<Clusterable<T>, PointStatus> visited) {
/* 167 */     cluster.addPoint(point);
/* 168 */     visited.put((Clusterable<T>)point, PointStatus.PART_OF_CLUSTER);
/*     */     
/* 170 */     List<T> seeds = new ArrayList<T>(neighbors);
/* 171 */     int index = 0;
/* 172 */     while (index < seeds.size()) {
/* 173 */       Clusterable<T> clusterable = (Clusterable)seeds.get(index);
/* 174 */       PointStatus pStatus = visited.get(clusterable);
/*     */       
/* 176 */       if (pStatus == null) {
/* 177 */         List<T> currentNeighbors = getNeighbors((T)clusterable, points);
/* 178 */         if (currentNeighbors.size() >= this.minPts) {
/* 179 */           seeds = merge(seeds, currentNeighbors);
/*     */         }
/*     */       } 
/*     */       
/* 183 */       if (pStatus != PointStatus.PART_OF_CLUSTER) {
/* 184 */         visited.put(clusterable, PointStatus.PART_OF_CLUSTER);
/* 185 */         cluster.addPoint((T)clusterable);
/*     */       } 
/*     */       
/* 188 */       index++;
/*     */     } 
/* 190 */     return cluster;
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
/* 201 */     List<T> neighbors = new ArrayList<T>();
/* 202 */     for (Clusterable<T> clusterable : points) {
/* 203 */       if (point != clusterable && clusterable.distanceFrom(point) <= this.eps) {
/* 204 */         neighbors.add((T)clusterable);
/*     */       }
/*     */     } 
/* 207 */     return neighbors;
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
/* 218 */     Set<T> oneSet = new HashSet<T>(one);
/* 219 */     for (Clusterable clusterable : two) {
/* 220 */       if (!oneSet.contains(clusterable)) {
/* 221 */         one.add((T)clusterable);
/*     */       }
/*     */     } 
/* 224 */     return one;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/clustering/DBSCANClusterer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */