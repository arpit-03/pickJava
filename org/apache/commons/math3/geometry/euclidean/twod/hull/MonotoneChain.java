/*     */ package org.apache.commons.math3.geometry.euclidean.twod.hull;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Line;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.Precision;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MonotoneChain
/*     */   extends AbstractConvexHullGenerator2D
/*     */ {
/*     */   public MonotoneChain() {
/*  56 */     this(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MonotoneChain(boolean includeCollinearPoints) {
/*  64 */     super(includeCollinearPoints);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MonotoneChain(boolean includeCollinearPoints, double tolerance) {
/*  73 */     super(includeCollinearPoints, tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Vector2D> findHullVertices(Collection<Vector2D> points) {
/*  80 */     List<Vector2D> pointsSortedByXAxis = new ArrayList<Vector2D>(points);
/*     */ 
/*     */     
/*  83 */     Collections.sort(pointsSortedByXAxis, new Comparator<Vector2D>()
/*     */         {
/*     */           public int compare(Vector2D o1, Vector2D o2) {
/*  86 */             double tolerance = MonotoneChain.this.getTolerance();
/*     */ 
/*     */             
/*  89 */             int diff = Precision.compareTo(o1.getX(), o2.getX(), tolerance);
/*  90 */             if (diff == 0) {
/*  91 */               return Precision.compareTo(o1.getY(), o2.getY(), tolerance);
/*     */             }
/*  93 */             return diff;
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  99 */     List<Vector2D> lowerHull = new ArrayList<Vector2D>();
/* 100 */     for (Vector2D p : pointsSortedByXAxis) {
/* 101 */       updateHull(p, lowerHull);
/*     */     }
/*     */ 
/*     */     
/* 105 */     List<Vector2D> upperHull = new ArrayList<Vector2D>();
/* 106 */     for (int idx = pointsSortedByXAxis.size() - 1; idx >= 0; idx--) {
/* 107 */       Vector2D p = pointsSortedByXAxis.get(idx);
/* 108 */       updateHull(p, upperHull);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 113 */     List<Vector2D> hullVertices = new ArrayList<Vector2D>(lowerHull.size() + upperHull.size() - 2); int i;
/* 114 */     for (i = 0; i < lowerHull.size() - 1; i++) {
/* 115 */       hullVertices.add(lowerHull.get(i));
/*     */     }
/* 117 */     for (i = 0; i < upperHull.size() - 1; i++) {
/* 118 */       hullVertices.add(upperHull.get(i));
/*     */     }
/*     */ 
/*     */     
/* 122 */     if (hullVertices.isEmpty() && !lowerHull.isEmpty()) {
/* 123 */       hullVertices.add(lowerHull.get(0));
/*     */     }
/*     */     
/* 126 */     return hullVertices;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateHull(Vector2D point, List<Vector2D> hull) {
/* 136 */     double tolerance = getTolerance();
/*     */     
/* 138 */     if (hull.size() == 1) {
/*     */       
/* 140 */       Vector2D p1 = hull.get(0);
/* 141 */       if (p1.distance((Vector)point) < tolerance) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 146 */     while (hull.size() >= 2) {
/* 147 */       int size = hull.size();
/* 148 */       Vector2D p1 = hull.get(size - 2);
/* 149 */       Vector2D p2 = hull.get(size - 1);
/*     */       
/* 151 */       double offset = (new Line(p1, p2, tolerance)).getOffset((Vector)point);
/* 152 */       if (FastMath.abs(offset) < tolerance) {
/*     */ 
/*     */         
/* 155 */         double distanceToCurrent = p1.distance((Vector)point);
/* 156 */         if (distanceToCurrent < tolerance || p2.distance((Vector)point) < tolerance) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 161 */         double distanceToLast = p1.distance((Vector)p2);
/* 162 */         if (isIncludeCollinearPoints()) {
/* 163 */           int index = (distanceToCurrent < distanceToLast) ? (size - 1) : size;
/* 164 */           hull.add(index, point);
/*     */         }
/* 166 */         else if (distanceToCurrent > distanceToLast) {
/* 167 */           hull.remove(size - 1);
/* 168 */           hull.add(point);
/*     */         } 
/*     */         return;
/*     */       } 
/* 172 */       if (offset > 0.0D) {
/* 173 */         hull.remove(size - 1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 178 */     hull.add(point);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/hull/MonotoneChain.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */