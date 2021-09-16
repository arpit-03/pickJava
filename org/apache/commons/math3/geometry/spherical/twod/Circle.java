/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Rotation;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
/*     */ import org.apache.commons.math3.geometry.partitioning.Embedding;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Transform;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.Arc;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.ArcsSet;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.S1Point;
/*     */ import org.apache.commons.math3.geometry.spherical.oned.Sphere1D;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Circle
/*     */   implements Hyperplane<Sphere2D>, Embedding<Sphere2D, Sphere1D>
/*     */ {
/*     */   private Vector3D pole;
/*     */   private Vector3D x;
/*     */   private Vector3D y;
/*     */   private final double tolerance;
/*     */   
/*     */   public Circle(Vector3D pole, double tolerance) {
/*  65 */     reset(pole);
/*  66 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Circle(S2Point first, S2Point second, double tolerance) {
/*  76 */     reset(first.getVector().crossProduct((Vector)second.getVector()));
/*  77 */     this.tolerance = tolerance;
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
/*     */   private Circle(Vector3D pole, Vector3D x, Vector3D y, double tolerance) {
/*  89 */     this.pole = pole;
/*  90 */     this.x = x;
/*  91 */     this.y = y;
/*  92 */     this.tolerance = tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Circle(Circle circle) {
/* 101 */     this(circle.pole, circle.x, circle.y, circle.tolerance);
/*     */   }
/*     */ 
/*     */   
/*     */   public Circle copySelf() {
/* 106 */     return new Circle(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(Vector3D newPole) {
/* 114 */     this.pole = newPole.normalize();
/* 115 */     this.x = newPole.orthogonal();
/* 116 */     this.y = Vector3D.crossProduct(newPole, this.x).normalize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void revertSelf() {
/* 123 */     this.y = this.y.negate();
/* 124 */     this.pole = this.pole.negate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Circle getReverse() {
/* 133 */     return new Circle(this.pole.negate(), this.x, this.y.negate(), this.tolerance);
/*     */   }
/*     */ 
/*     */   
/*     */   public Point<Sphere2D> project(Point<Sphere2D> point) {
/* 138 */     return toSpace((Point<Sphere1D>)toSubSpace(point));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 143 */     return this.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S1Point toSubSpace(Point<Sphere2D> point) {
/* 150 */     return new S1Point(getPhase(((S2Point)point).getVector()));
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
/*     */   public double getPhase(Vector3D direction) {
/* 164 */     return Math.PI + FastMath.atan2(-direction.dotProduct((Vector)this.y), -direction.dotProduct((Vector)this.x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S2Point toSpace(Point<Sphere1D> point) {
/* 171 */     return new S2Point(getPointAt(((S1Point)point).getAlpha()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getPointAt(double alpha) {
/* 182 */     return new Vector3D(FastMath.cos(alpha), this.x, FastMath.sin(alpha), this.y);
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
/*     */   public Vector3D getXAxis() {
/* 197 */     return this.x;
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
/*     */   public Vector3D getYAxis() {
/* 212 */     return this.y;
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
/*     */   public Vector3D getPole() {
/* 225 */     return this.pole;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Arc getInsideArc(Circle other) {
/* 233 */     double alpha = getPhase(other.pole);
/* 234 */     double halfPi = 1.5707963267948966D;
/* 235 */     return new Arc(alpha - 1.5707963267948966D, alpha + 1.5707963267948966D, this.tolerance);
/*     */   }
/*     */ 
/*     */   
/*     */   public SubCircle wholeHyperplane() {
/* 240 */     return new SubCircle(this, (Region<Sphere1D>)new ArcsSet(this.tolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SphericalPolygonsSet wholeSpace() {
/* 248 */     return new SphericalPolygonsSet(this.tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOffset(Point<Sphere2D> point) {
/* 255 */     return getOffset(((S2Point)point).getVector());
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
/*     */   public double getOffset(Vector3D direction) {
/* 268 */     return Vector3D.angle(this.pole, direction) - 1.5707963267948966D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sameOrientationAs(Hyperplane<Sphere2D> other) {
/* 273 */     Circle otherC = (Circle)other;
/* 274 */     return (Vector3D.dotProduct(this.pole, otherC.pole) >= 0.0D);
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
/*     */   public static Transform<Sphere2D, Sphere1D> getTransform(Rotation rotation) {
/* 286 */     return new CircleTransform(rotation);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CircleTransform
/*     */     implements Transform<Sphere2D, Sphere1D>
/*     */   {
/*     */     private final Rotation rotation;
/*     */ 
/*     */ 
/*     */     
/*     */     CircleTransform(Rotation rotation) {
/* 299 */       this.rotation = rotation;
/*     */     }
/*     */ 
/*     */     
/*     */     public S2Point apply(Point<Sphere2D> point) {
/* 304 */       return new S2Point(this.rotation.applyTo(((S2Point)point).getVector()));
/*     */     }
/*     */ 
/*     */     
/*     */     public Circle apply(Hyperplane<Sphere2D> hyperplane) {
/* 309 */       Circle circle = (Circle)hyperplane;
/* 310 */       return new Circle(this.rotation.applyTo(circle.pole), this.rotation.applyTo(circle.x), this.rotation.applyTo(circle.y), circle.tolerance);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubHyperplane<Sphere1D> apply(SubHyperplane<Sphere1D> sub, Hyperplane<Sphere2D> original, Hyperplane<Sphere2D> transformed) {
/* 321 */       return sub;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/Circle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */