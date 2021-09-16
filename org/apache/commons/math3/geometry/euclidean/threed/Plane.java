/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Euclidean1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.oned.Vector1D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.PolygonsSet;
/*     */ import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
/*     */ import org.apache.commons.math3.geometry.partitioning.Embedding;
/*     */ import org.apache.commons.math3.geometry.partitioning.Hyperplane;
/*     */ import org.apache.commons.math3.geometry.partitioning.Region;
/*     */ import org.apache.commons.math3.geometry.partitioning.SubHyperplane;
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
/*     */ public class Plane
/*     */   implements Hyperplane<Euclidean3D>, Embedding<Euclidean3D, Euclidean2D>
/*     */ {
/*     */   private static final double DEFAULT_TOLERANCE = 1.0E-10D;
/*     */   private double originOffset;
/*     */   private Vector3D origin;
/*     */   private Vector3D u;
/*     */   private Vector3D v;
/*     */   private Vector3D w;
/*     */   private final double tolerance;
/*     */   
/*     */   public Plane(Vector3D normal, double tolerance) throws MathArithmeticException {
/*  66 */     setNormal(normal);
/*  67 */     this.tolerance = tolerance;
/*  68 */     this.originOffset = 0.0D;
/*  69 */     setFrame();
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
/*     */   public Plane(Vector3D p, Vector3D normal, double tolerance) throws MathArithmeticException {
/*  81 */     setNormal(normal);
/*  82 */     this.tolerance = tolerance;
/*  83 */     this.originOffset = -p.dotProduct(this.w);
/*  84 */     setFrame();
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
/*     */   public Plane(Vector3D p1, Vector3D p2, Vector3D p3, double tolerance) throws MathArithmeticException {
/*  99 */     this(p1, p2.subtract(p1).crossProduct(p3.subtract(p1)), tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Plane(Vector3D normal) throws MathArithmeticException {
/* 109 */     this(normal, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Plane(Vector3D p, Vector3D normal) throws MathArithmeticException {
/* 120 */     this(p, normal, 1.0E-10D);
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
/*     */   @Deprecated
/*     */   public Plane(Vector3D p1, Vector3D p2, Vector3D p3) throws MathArithmeticException {
/* 135 */     this(p1, p2, p3, 1.0E-10D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plane(Plane plane) {
/* 145 */     this.originOffset = plane.originOffset;
/* 146 */     this.origin = plane.origin;
/* 147 */     this.u = plane.u;
/* 148 */     this.v = plane.v;
/* 149 */     this.w = plane.w;
/* 150 */     this.tolerance = plane.tolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plane copySelf() {
/* 160 */     return new Plane(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(Vector3D p, Vector3D normal) throws MathArithmeticException {
/* 169 */     setNormal(normal);
/* 170 */     this.originOffset = -p.dotProduct(this.w);
/* 171 */     setFrame();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset(Plane original) {
/* 181 */     this.originOffset = original.originOffset;
/* 182 */     this.origin = original.origin;
/* 183 */     this.u = original.u;
/* 184 */     this.v = original.v;
/* 185 */     this.w = original.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNormal(Vector3D normal) throws MathArithmeticException {
/* 193 */     double norm = normal.getNorm();
/* 194 */     if (norm < 1.0E-10D) {
/* 195 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/* 197 */     this.w = new Vector3D(1.0D / norm, normal);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setFrame() {
/* 203 */     this.origin = new Vector3D(-this.originOffset, this.w);
/* 204 */     this.u = this.w.orthogonal();
/* 205 */     this.v = Vector3D.crossProduct(this.w, this.u);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getOrigin() {
/* 215 */     return this.origin;
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
/*     */   public Vector3D getNormal() {
/* 227 */     return this.w;
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
/*     */   public Vector3D getU() {
/* 239 */     return this.u;
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
/*     */   public Vector3D getV() {
/* 251 */     return this.v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point<Euclidean3D> project(Point<Euclidean3D> point) {
/* 258 */     return (Point<Euclidean3D>)toSpace((Vector<Euclidean2D>)toSubSpace(point));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTolerance() {
/* 265 */     return this.tolerance;
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
/*     */   public void revertSelf() {
/* 280 */     Vector3D tmp = this.u;
/* 281 */     this.u = this.v;
/* 282 */     this.v = tmp;
/* 283 */     this.w = this.w.negate();
/* 284 */     this.originOffset = -this.originOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D toSubSpace(Vector<Euclidean3D> vector) {
/* 293 */     return toSubSpace((Point<Euclidean3D>)vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D toSpace(Vector<Euclidean2D> vector) {
/* 302 */     return toSpace((Point<Euclidean2D>)vector);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D toSubSpace(Point<Euclidean3D> point) {
/* 313 */     Vector3D p3D = (Vector3D)point;
/* 314 */     return new Vector2D(p3D.dotProduct(this.u), p3D.dotProduct(this.v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D toSpace(Point<Euclidean2D> point) {
/* 324 */     Vector2D p2D = (Vector2D)point;
/* 325 */     return new Vector3D(p2D.getX(), this.u, p2D.getY(), this.v, -this.originOffset, this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getPointAt(Vector2D inPlane, double offset) {
/* 336 */     return new Vector3D(inPlane.getX(), this.u, inPlane.getY(), this.v, offset - this.originOffset, this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSimilarTo(Plane plane) {
/* 347 */     double angle = Vector3D.angle(this.w, plane.w);
/* 348 */     return ((angle < 1.0E-10D && FastMath.abs(this.originOffset - plane.originOffset) < this.tolerance) || (angle > 3.141592653489793D && FastMath.abs(this.originOffset + plane.originOffset) < this.tolerance));
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
/*     */   public Plane rotate(Vector3D center, Rotation rotation) {
/* 360 */     Vector3D delta = this.origin.subtract(center);
/* 361 */     Plane plane = new Plane(center.add(rotation.applyTo(delta)), rotation.applyTo(this.w), this.tolerance);
/*     */ 
/*     */ 
/*     */     
/* 365 */     plane.u = rotation.applyTo(this.u);
/* 366 */     plane.v = rotation.applyTo(this.v);
/*     */     
/* 368 */     return plane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plane translate(Vector3D translation) {
/* 379 */     Plane plane = new Plane(this.origin.add(translation), this.w, this.tolerance);
/*     */ 
/*     */     
/* 382 */     plane.u = this.u;
/* 383 */     plane.v = this.v;
/*     */     
/* 385 */     return plane;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D intersection(Line line) {
/* 395 */     Vector3D direction = line.getDirection();
/* 396 */     double dot = this.w.dotProduct(direction);
/* 397 */     if (FastMath.abs(dot) < 1.0E-10D) {
/* 398 */       return null;
/*     */     }
/* 400 */     Vector3D point = line.toSpace((Point<Euclidean1D>)Vector1D.ZERO);
/* 401 */     double k = -(this.originOffset + this.w.dotProduct(point)) / dot;
/* 402 */     return new Vector3D(1.0D, point, k, direction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Line intersection(Plane other) {
/* 411 */     Vector3D direction = Vector3D.crossProduct(this.w, other.w);
/* 412 */     if (direction.getNorm() < this.tolerance) {
/* 413 */       return null;
/*     */     }
/* 415 */     Vector3D point = intersection(this, other, new Plane(direction, this.tolerance));
/* 416 */     return new Line(point, point.add(direction), this.tolerance);
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
/*     */   public static Vector3D intersection(Plane plane1, Plane plane2, Plane plane3) {
/* 428 */     double a1 = plane1.w.getX();
/* 429 */     double b1 = plane1.w.getY();
/* 430 */     double c1 = plane1.w.getZ();
/* 431 */     double d1 = plane1.originOffset;
/*     */     
/* 433 */     double a2 = plane2.w.getX();
/* 434 */     double b2 = plane2.w.getY();
/* 435 */     double c2 = plane2.w.getZ();
/* 436 */     double d2 = plane2.originOffset;
/*     */     
/* 438 */     double a3 = plane3.w.getX();
/* 439 */     double b3 = plane3.w.getY();
/* 440 */     double c3 = plane3.w.getZ();
/* 441 */     double d3 = plane3.originOffset;
/*     */ 
/*     */ 
/*     */     
/* 445 */     double a23 = b2 * c3 - b3 * c2;
/* 446 */     double b23 = c2 * a3 - c3 * a2;
/* 447 */     double c23 = a2 * b3 - a3 * b2;
/* 448 */     double determinant = a1 * a23 + b1 * b23 + c1 * c23;
/* 449 */     if (FastMath.abs(determinant) < 1.0E-10D) {
/* 450 */       return null;
/*     */     }
/*     */     
/* 453 */     double r = 1.0D / determinant;
/* 454 */     return new Vector3D((-a23 * d1 - (c1 * b3 - c3 * b1) * d2 - (c2 * b1 - c1 * b2) * d3) * r, (-b23 * d1 - (c3 * a1 - c1 * a3) * d2 - (c1 * a2 - c2 * a1) * d3) * r, (-c23 * d1 - (b1 * a3 - b3 * a1) * d2 - (b2 * a1 - b1 * a2) * d3) * r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubPlane wholeHyperplane() {
/* 465 */     return new SubPlane(this, (Region<Euclidean2D>)new PolygonsSet(this.tolerance));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolyhedronsSet wholeSpace() {
/* 473 */     return new PolyhedronsSet(this.tolerance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(Vector3D p) {
/* 481 */     return (FastMath.abs(getOffset(p)) < this.tolerance);
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
/*     */   public double getOffset(Plane plane) {
/* 495 */     return this.originOffset + (sameOrientationAs(plane) ? -plane.originOffset : plane.originOffset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOffset(Vector<Euclidean3D> vector) {
/* 503 */     return getOffset((Point<Euclidean3D>)vector);
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
/*     */   public double getOffset(Point<Euclidean3D> point) {
/* 515 */     return ((Vector3D)point).dotProduct(this.w) + this.originOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sameOrientationAs(Hyperplane<Euclidean3D> other) {
/* 524 */     return (((Plane)other).w.dotProduct(this.w) > 0.0D);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/Plane.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */