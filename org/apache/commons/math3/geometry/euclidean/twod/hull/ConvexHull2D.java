/*     */ package org.apache.commons.math3.geometry.euclidean.twod.hull;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.InsufficientDataException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Line;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Segment;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.geometry.hull.ConvexHull;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.RegionFactory;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ 
/*     */ public class ConvexHull2D
/*     */   implements ConvexHull<Euclidean2D, Vector2D>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20140129L;
/*     */   private final Vector2D[] vertices;
/*     */   private final double tolerance;
/*     */   private transient Segment[] lineSegments;
/*     */   
/*     */   public ConvexHull2D(Vector2D[] vertices, double tolerance) throws MathIllegalArgumentException {
/*  66 */     this.tolerance = tolerance;
/*     */     
/*  68 */     if (!isConvex(vertices)) {
/*  69 */       throw new MathIllegalArgumentException(LocalizedFormats.NOT_CONVEX, new Object[0]);
/*     */     }
/*     */     
/*  72 */     this.vertices = (Vector2D[])vertices.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isConvex(Vector2D[] hullVertices) {
/*  81 */     if (hullVertices.length < 3) {
/*  82 */       return true;
/*     */     }
/*     */     
/*  85 */     int sign = 0;
/*  86 */     for (int i = 0; i < hullVertices.length; i++) {
/*  87 */       Vector2D p1 = hullVertices[(i == 0) ? (hullVertices.length - 1) : (i - 1)];
/*  88 */       Vector2D p2 = hullVertices[i];
/*  89 */       Vector2D p3 = hullVertices[(i == hullVertices.length - 1) ? 0 : (i + 1)];
/*     */       
/*  91 */       Vector2D d1 = p2.subtract((Vector)p1);
/*  92 */       Vector2D d2 = p3.subtract((Vector)p2);
/*     */       
/*  94 */       double crossProduct = MathArrays.linearCombination(d1.getX(), d2.getY(), -d1.getY(), d2.getX());
/*  95 */       int cmp = Precision.compareTo(crossProduct, 0.0D, this.tolerance);
/*     */       
/*  97 */       if (cmp != 0.0D) {
/*  98 */         if (sign != 0.0D && cmp != sign) {
/*  99 */           return false;
/*     */         }
/* 101 */         sign = cmp;
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D[] getVertices() {
/* 110 */     return (Vector2D[])this.vertices.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Segment[] getLineSegments() {
/* 118 */     return (Segment[])retrieveLineSegments().clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Segment[] retrieveLineSegments() {
/* 127 */     if (this.lineSegments == null) {
/*     */       
/* 129 */       int size = this.vertices.length;
/* 130 */       if (size <= 1) {
/* 131 */         this.lineSegments = new Segment[0];
/* 132 */       } else if (size == 2) {
/* 133 */         this.lineSegments = new Segment[1];
/* 134 */         Vector2D p1 = this.vertices[0];
/* 135 */         Vector2D p2 = this.vertices[1];
/* 136 */         this.lineSegments[0] = new Segment(p1, p2, new Line(p1, p2, this.tolerance));
/*     */       } else {
/* 138 */         this.lineSegments = new Segment[size];
/* 139 */         Vector2D firstPoint = null;
/* 140 */         Vector2D lastPoint = null;
/* 141 */         int index = 0;
/* 142 */         for (Vector2D point : this.vertices) {
/* 143 */           if (lastPoint == null) {
/* 144 */             firstPoint = point;
/* 145 */             lastPoint = point;
/*     */           } else {
/* 147 */             this.lineSegments[index++] = new Segment(lastPoint, point, new Line(lastPoint, point, this.tolerance));
/*     */             
/* 149 */             lastPoint = point;
/*     */           } 
/*     */         } 
/* 152 */         this.lineSegments[index] = new Segment(lastPoint, firstPoint, new Line(lastPoint, firstPoint, this.tolerance));
/*     */       } 
/*     */     } 
/*     */     
/* 156 */     return this.lineSegments;
/*     */   }
/*     */ 
/*     */   
/*     */   public Region<Euclidean2D> createRegion() throws InsufficientDataException {
/* 161 */     if (this.vertices.length < 3) {
/* 162 */       throw new InsufficientDataException();
/*     */     }
/* 164 */     RegionFactory<Euclidean2D> factory = new RegionFactory();
/* 165 */     Segment[] segments = retrieveLineSegments();
/* 166 */     Line[] lineArray = new Line[segments.length];
/* 167 */     for (int i = 0; i < segments.length; i++) {
/* 168 */       lineArray[i] = segments[i].getLine();
/*     */     }
/* 170 */     return factory.buildConvex((Hyperplane[])lineArray);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/hull/ConvexHull2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */