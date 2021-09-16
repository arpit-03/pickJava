/*     */ package Catalano.Math.Geometry;
/*     */ 
/*     */ import Catalano.Core.IntPoint;
/*     */ import Catalano.Math.Distances.Distance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RamerDouglasPeucker
/*     */   implements IShapeOptimizer
/*     */ {
/*     */   private double distanceThreshold;
/*     */   
/*     */   public double getDistanceThreshold() {
/*  49 */     return this.distanceThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDistanceThreshold(double distanceThreshold) {
/*  57 */     this.distanceThreshold = distanceThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RamerDouglasPeucker() {
/*  64 */     this(0.5D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RamerDouglasPeucker(double distanceThreshold) {
/*  72 */     this.distanceThreshold = distanceThreshold;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<IntPoint> OptimizeShape(List<IntPoint> shape) {
/*  77 */     List<IntPoint> simplifiedVertices = new ArrayList<>();
/*     */     
/*  79 */     Double maxDistance = null;
/*  80 */     int maxDistancePointIdx = 0;
/*     */     
/*  82 */     int lastPointIdx = shape.size() - 1;
/*     */     
/*  84 */     int currentIdx = 0;
/*  85 */     for (IntPoint aVertex : shape) {
/*     */       
/*  87 */       if (currentIdx != 0 && currentIdx != lastPointIdx) {
/*     */         
/*  89 */         Double distance = Double.valueOf(shortestDistanceToSegment(aVertex, shape.get(0), shape.get(lastPointIdx)));
/*     */         
/*  91 */         if (maxDistance == null || distance.doubleValue() > maxDistance.doubleValue()) {
/*  92 */           maxDistancePointIdx = currentIdx;
/*  93 */           maxDistance = distance;
/*     */         } 
/*     */       } 
/*     */       
/*  97 */       currentIdx++;
/*     */     } 
/*     */     
/* 100 */     if (maxDistance != null) {
/* 101 */       if (maxDistance.doubleValue() > this.distanceThreshold) {
/* 102 */         List<IntPoint> sub = OptimizeShape(shape.subList(0, maxDistancePointIdx + 1));
/* 103 */         List<IntPoint> sup = OptimizeShape(shape.subList(maxDistancePointIdx, lastPointIdx + 1));
/*     */         
/* 105 */         simplifiedVertices.addAll(sub);
/* 106 */         simplifiedVertices.addAll(sup);
/*     */       } else {
/*     */         
/* 109 */         simplifiedVertices.add(shape.get(0));
/* 110 */         simplifiedVertices.add(shape.get(lastPointIdx));
/*     */       } 
/*     */     }
/* 113 */     return simplifiedVertices;
/*     */   }
/*     */   
/*     */   private double shortestDistanceToSegment(IntPoint thePoint, IntPoint segmentPoint_A, IntPoint segmentPoint_B) {
/* 117 */     double area = calculateTriangleAreaGivenVertices(thePoint, segmentPoint_A, segmentPoint_B);
/* 118 */     double lengthSegment = Distance.Euclidean(segmentPoint_A, segmentPoint_B);
/* 119 */     return 2.0D * area / lengthSegment;
/*     */   }
/*     */ 
/*     */   
/*     */   private double calculateTriangleAreaGivenVertices(IntPoint a, IntPoint b, IntPoint c) {
/* 124 */     double area = Math.abs((a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y)) / 2);
/* 125 */     return area;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Geometry/RamerDouglasPeucker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */