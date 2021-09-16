/*     */ package org.apache.commons.math3.geometry.euclidean.oned;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OrientedPoint
/*     */   implements Hyperplane<Euclidean1D>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   private Vector1D location;
/*     */   private boolean direct;
/*     */   private final double tolerance;
/*     */   
/*     */   public OrientedPoint(Vector1D location, boolean direct, double tolerance) {
/*  51 */     this.location = location;
/*  52 */     this.direct = direct;
/*  53 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public OrientedPoint(Vector1D location, boolean direct) {
/*  64 */     this(location, direct, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OrientedPoint copySelf() {
/*  73 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOffset(Vector<Euclidean1D> vector) {
/*  81 */     return getOffset((Point<Euclidean1D>)vector);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOffset(Point<Euclidean1D> point) {
/*  86 */     double delta = ((Vector1D)point).getX() - this.location.getX();
/*  87 */     return this.direct ? delta : -delta;
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
/*     */   public SubOrientedPoint wholeHyperplane() {
/* 102 */     return new SubOrientedPoint(this, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntervalsSet wholeSpace() {
/* 110 */     return new IntervalsSet(this.tolerance);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sameOrientationAs(Hyperplane<Euclidean1D> other) {
/* 115 */     return ((this.direct ^ ((OrientedPoint)other).direct) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point<Euclidean1D> project(Point<Euclidean1D> point) {
/* 122 */     return (Point<Euclidean1D>)this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 129 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D getLocation() {
/* 136 */     return this.location;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/* 144 */     return this.direct;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void revertSelf() {
/* 150 */     this.direct = !this.direct;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/OrientedPoint.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */