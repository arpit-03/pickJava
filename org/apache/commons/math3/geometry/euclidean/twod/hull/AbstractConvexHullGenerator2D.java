/*     */ package org.apache.commons.math3.geometry.euclidean.twod.hull;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.math3.exception.ConvergenceException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.geometry.hull.ConvexHull;
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
/*     */ abstract class AbstractConvexHullGenerator2D
/*     */   implements ConvexHullGenerator2D
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   private final double tolerance;
/*     */   private final boolean includeCollinearPoints;
/*     */   
/*     */   protected AbstractConvexHullGenerator2D(boolean includeCollinearPoints) {
/*  55 */     this(includeCollinearPoints, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractConvexHullGenerator2D(boolean includeCollinearPoints, double tolerance) {
/*  66 */     this.includeCollinearPoints = includeCollinearPoints;
/*  67 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/*  75 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIncludeCollinearPoints() {
/*  84 */     return this.includeCollinearPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConvexHull2D generate(Collection<Vector2D> points) throws NullArgumentException, ConvergenceException {
/*  91 */     MathUtils.checkNotNull(points);
/*     */     
/*  93 */     Collection<Vector2D> hullVertices = null;
/*  94 */     if (points.size() < 2) {
/*  95 */       hullVertices = points;
/*     */     } else {
/*  97 */       hullVertices = findHullVertices(points);
/*     */     } 
/*     */     
/*     */     try {
/* 101 */       return new ConvexHull2D(hullVertices.<Vector2D>toArray(new Vector2D[hullVertices.size()]), this.tolerance);
/*     */     }
/* 103 */     catch (MathIllegalArgumentException e) {
/*     */       
/* 105 */       throw new ConvergenceException();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract Collection<Vector2D> findHullVertices(Collection<Vector2D> paramCollection);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/hull/AbstractConvexHullGenerator2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */