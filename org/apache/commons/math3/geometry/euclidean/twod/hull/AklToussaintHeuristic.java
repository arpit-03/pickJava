/*     */ package org.apache.commons.math3.geometry.euclidean.twod.hull;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AklToussaintHeuristic
/*     */ {
/*     */   public static Collection<Vector2D> reducePoints(Collection<Vector2D> points) {
/*  56 */     int size = 0;
/*  57 */     Vector2D minX = null;
/*  58 */     Vector2D maxX = null;
/*  59 */     Vector2D minY = null;
/*  60 */     Vector2D maxY = null;
/*  61 */     for (Vector2D p : points) {
/*  62 */       if (minX == null || p.getX() < minX.getX()) {
/*  63 */         minX = p;
/*     */       }
/*  65 */       if (maxX == null || p.getX() > maxX.getX()) {
/*  66 */         maxX = p;
/*     */       }
/*  68 */       if (minY == null || p.getY() < minY.getY()) {
/*  69 */         minY = p;
/*     */       }
/*  71 */       if (maxY == null || p.getY() > maxY.getY()) {
/*  72 */         maxY = p;
/*     */       }
/*  74 */       size++;
/*     */     } 
/*     */     
/*  77 */     if (size < 4) {
/*  78 */       return points;
/*     */     }
/*     */     
/*  81 */     List<Vector2D> quadrilateral = buildQuadrilateral(new Vector2D[] { minY, maxX, maxY, minX });
/*     */     
/*  83 */     if (quadrilateral.size() < 3) {
/*  84 */       return points;
/*     */     }
/*     */     
/*  87 */     List<Vector2D> reducedPoints = new ArrayList<Vector2D>(quadrilateral);
/*  88 */     for (Vector2D p : points) {
/*     */ 
/*     */       
/*  91 */       if (!insideQuadrilateral(p, quadrilateral)) {
/*  92 */         reducedPoints.add(p);
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return reducedPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Vector2D> buildQuadrilateral(Vector2D... points) {
/* 106 */     List<Vector2D> quadrilateral = new ArrayList<Vector2D>();
/* 107 */     for (Vector2D p : points) {
/* 108 */       if (!quadrilateral.contains(p)) {
/* 109 */         quadrilateral.add(p);
/*     */       }
/*     */     } 
/* 112 */     return quadrilateral;
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
/*     */   private static boolean insideQuadrilateral(Vector2D point, List<Vector2D> quadrilateralPoints) {
/* 124 */     Vector2D p1 = quadrilateralPoints.get(0);
/* 125 */     Vector2D p2 = quadrilateralPoints.get(1);
/*     */     
/* 127 */     if (point.equals(p1) || point.equals(p2)) {
/* 128 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 132 */     double last = point.crossProduct(p1, p2);
/* 133 */     int size = quadrilateralPoints.size();
/*     */     
/* 135 */     for (int i = 1; i < size; i++) {
/* 136 */       p1 = p2;
/* 137 */       p2 = quadrilateralPoints.get((i + 1 == size) ? 0 : (i + 1));
/*     */       
/* 139 */       if (point.equals(p1) || point.equals(p2)) {
/* 140 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 146 */       if (last * point.crossProduct(p1, p2) < 0.0D) {
/* 147 */         return false;
/*     */       }
/*     */     } 
/* 150 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/hull/AklToussaintHeuristic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */