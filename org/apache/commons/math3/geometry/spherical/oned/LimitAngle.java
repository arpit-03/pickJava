/*     */ package org.apache.commons.math3.geometry.spherical.oned;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Point;
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
/*     */ public class LimitAngle
/*     */   implements Hyperplane<Sphere1D>
/*     */ {
/*     */   private S1Point location;
/*     */   private boolean direct;
/*     */   private final double tolerance;
/*     */   
/*     */   public LimitAngle(S1Point location, boolean direct, double tolerance) {
/*  45 */     this.location = location;
/*  46 */     this.direct = direct;
/*  47 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LimitAngle copySelf() {
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOffset(Point<Sphere1D> point) {
/*  61 */     double delta = ((S1Point)point).getAlpha() - this.location.getAlpha();
/*  62 */     return this.direct ? delta : -delta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/*  70 */     return this.direct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LimitAngle getReverse() {
/*  79 */     return new LimitAngle(this.location, !this.direct, this.tolerance);
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
/*     */   public SubLimitAngle wholeHyperplane() {
/*  94 */     return new SubLimitAngle(this, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArcsSet wholeSpace() {
/* 102 */     return new ArcsSet(this.tolerance);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sameOrientationAs(Hyperplane<Sphere1D> other) {
/* 107 */     return ((this.direct ^ ((LimitAngle)other).direct) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S1Point getLocation() {
/* 114 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public Point<Sphere1D> project(Point<Sphere1D> point) {
/* 119 */     return this.location;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 124 */     return this.tolerance;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/oned/LimitAngle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */