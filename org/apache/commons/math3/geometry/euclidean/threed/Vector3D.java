/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.NumberFormat;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathArrays;
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
/*     */ public class Vector3D
/*     */   implements Serializable, Vector<Euclidean3D>
/*     */ {
/*  41 */   public static final Vector3D ZERO = new Vector3D(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */   
/*  44 */   public static final Vector3D PLUS_I = new Vector3D(1.0D, 0.0D, 0.0D);
/*     */ 
/*     */   
/*  47 */   public static final Vector3D MINUS_I = new Vector3D(-1.0D, 0.0D, 0.0D);
/*     */ 
/*     */   
/*  50 */   public static final Vector3D PLUS_J = new Vector3D(0.0D, 1.0D, 0.0D);
/*     */ 
/*     */   
/*  53 */   public static final Vector3D MINUS_J = new Vector3D(0.0D, -1.0D, 0.0D);
/*     */ 
/*     */   
/*  56 */   public static final Vector3D PLUS_K = new Vector3D(0.0D, 0.0D, 1.0D);
/*     */ 
/*     */   
/*  59 */   public static final Vector3D MINUS_K = new Vector3D(0.0D, 0.0D, -1.0D);
/*     */ 
/*     */ 
/*     */   
/*  63 */   public static final Vector3D NaN = new Vector3D(Double.NaN, Double.NaN, Double.NaN);
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final Vector3D POSITIVE_INFINITY = new Vector3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final Vector3D NEGATIVE_INFINITY = new Vector3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 1313493323784566947L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double y;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double z;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D(double x, double y, double z) {
/*  96 */     this.x = x;
/*  97 */     this.y = y;
/*  98 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D(double[] v) throws DimensionMismatchException {
/* 108 */     if (v.length != 3) {
/* 109 */       throw new DimensionMismatchException(v.length, 3);
/*     */     }
/* 111 */     this.x = v[0];
/* 112 */     this.y = v[1];
/* 113 */     this.z = v[2];
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
/*     */   public Vector3D(double alpha, double delta) {
/* 125 */     double cosDelta = FastMath.cos(delta);
/* 126 */     this.x = FastMath.cos(alpha) * cosDelta;
/* 127 */     this.y = FastMath.sin(alpha) * cosDelta;
/* 128 */     this.z = FastMath.sin(delta);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D(double a, Vector3D u) {
/* 138 */     this.x = a * u.x;
/* 139 */     this.y = a * u.y;
/* 140 */     this.z = a * u.z;
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
/*     */   public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2) {
/* 152 */     this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x);
/* 153 */     this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y);
/* 154 */     this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z);
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
/*     */   public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3) {
/* 169 */     this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x, a3, u3.x);
/* 170 */     this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y, a3, u3.y);
/* 171 */     this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z, a3, u3.z);
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
/*     */   public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3, double a4, Vector3D u4) {
/* 188 */     this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x, a3, u3.x, a4, u4.x);
/* 189 */     this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y, a3, u3.y, a4, u4.y);
/* 190 */     this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z, a3, u3.z, a4, u4.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 198 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 206 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 214 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 222 */     return new double[] { this.x, this.y, this.z };
/*     */   }
/*     */ 
/*     */   
/*     */   public Space getSpace() {
/* 227 */     return Euclidean3D.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D getZero() {
/* 232 */     return ZERO;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNorm1() {
/* 237 */     return FastMath.abs(this.x) + FastMath.abs(this.y) + FastMath.abs(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 243 */     return FastMath.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNormSq() {
/* 249 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNormInf() {
/* 254 */     return FastMath.max(FastMath.max(FastMath.abs(this.x), FastMath.abs(this.y)), FastMath.abs(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAlpha() {
/* 262 */     return FastMath.atan2(this.y, this.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDelta() {
/* 270 */     return FastMath.asin(this.z / getNorm());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D add(Vector<Euclidean3D> v) {
/* 275 */     Vector3D v3 = (Vector3D)v;
/* 276 */     return new Vector3D(this.x + v3.x, this.y + v3.y, this.z + v3.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D add(double factor, Vector<Euclidean3D> v) {
/* 281 */     return new Vector3D(1.0D, this, factor, (Vector3D)v);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D subtract(Vector<Euclidean3D> v) {
/* 286 */     Vector3D v3 = (Vector3D)v;
/* 287 */     return new Vector3D(this.x - v3.x, this.y - v3.y, this.z - v3.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D subtract(double factor, Vector<Euclidean3D> v) {
/* 292 */     return new Vector3D(1.0D, this, -factor, (Vector3D)v);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D normalize() throws MathArithmeticException {
/* 297 */     double s = getNorm();
/* 298 */     if (s == 0.0D) {
/* 299 */       throw new MathArithmeticException(LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new Object[0]);
/*     */     }
/* 301 */     return scalarMultiply(1.0D / s);
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
/*     */ 
/*     */   
/*     */   public Vector3D orthogonal() throws MathArithmeticException {
/* 321 */     double threshold = 0.6D * getNorm();
/* 322 */     if (threshold == 0.0D) {
/* 323 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/*     */     
/* 326 */     if (FastMath.abs(this.x) <= threshold) {
/* 327 */       double d = 1.0D / FastMath.sqrt(this.y * this.y + this.z * this.z);
/* 328 */       return new Vector3D(0.0D, d * this.z, -d * this.y);
/* 329 */     }  if (FastMath.abs(this.y) <= threshold) {
/* 330 */       double d = 1.0D / FastMath.sqrt(this.x * this.x + this.z * this.z);
/* 331 */       return new Vector3D(-d * this.z, 0.0D, d * this.x);
/*     */     } 
/* 333 */     double inverse = 1.0D / FastMath.sqrt(this.x * this.x + this.y * this.y);
/* 334 */     return new Vector3D(inverse * this.y, -inverse * this.x, 0.0D);
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
/*     */   public static double angle(Vector3D v1, Vector3D v2) throws MathArithmeticException {
/* 351 */     double normProduct = v1.getNorm() * v2.getNorm();
/* 352 */     if (normProduct == 0.0D) {
/* 353 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/*     */     
/* 356 */     double dot = v1.dotProduct(v2);
/* 357 */     double threshold = normProduct * 0.9999D;
/* 358 */     if (dot < -threshold || dot > threshold) {
/*     */       
/* 360 */       Vector3D v3 = crossProduct(v1, v2);
/* 361 */       if (dot >= 0.0D) {
/* 362 */         return FastMath.asin(v3.getNorm() / normProduct);
/*     */       }
/* 364 */       return Math.PI - FastMath.asin(v3.getNorm() / normProduct);
/*     */     } 
/*     */ 
/*     */     
/* 368 */     return FastMath.acos(dot / normProduct);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D negate() {
/* 374 */     return new Vector3D(-this.x, -this.y, -this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector3D scalarMultiply(double a) {
/* 379 */     return new Vector3D(a * this.x, a * this.y, a * this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 384 */     return (Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isNaN(this.z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 389 */     return (!isNaN() && (Double.isInfinite(this.x) || Double.isInfinite(this.y) || Double.isInfinite(this.z)));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 414 */     if (this == other) {
/* 415 */       return true;
/*     */     }
/*     */     
/* 418 */     if (other instanceof Vector3D) {
/* 419 */       Vector3D rhs = (Vector3D)other;
/* 420 */       if (rhs.isNaN()) {
/* 421 */         return isNaN();
/*     */       }
/*     */       
/* 424 */       return (this.x == rhs.x && this.y == rhs.y && this.z == rhs.z);
/*     */     } 
/* 426 */     return false;
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
/*     */   public int hashCode() {
/* 438 */     if (isNaN()) {
/* 439 */       return 642;
/*     */     }
/* 441 */     return 643 * (164 * MathUtils.hash(this.x) + 3 * MathUtils.hash(this.y) + MathUtils.hash(this.z));
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
/*     */   public double dotProduct(Vector<Euclidean3D> v) {
/* 453 */     Vector3D v3 = (Vector3D)v;
/* 454 */     return MathArrays.linearCombination(this.x, v3.x, this.y, v3.y, this.z, v3.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D crossProduct(Vector<Euclidean3D> v) {
/* 462 */     Vector3D v3 = (Vector3D)v;
/* 463 */     return new Vector3D(MathArrays.linearCombination(this.y, v3.z, -this.z, v3.y), MathArrays.linearCombination(this.z, v3.x, -this.x, v3.z), MathArrays.linearCombination(this.x, v3.y, -this.y, v3.x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance1(Vector<Euclidean3D> v) {
/* 470 */     Vector3D v3 = (Vector3D)v;
/* 471 */     double dx = FastMath.abs(v3.x - this.x);
/* 472 */     double dy = FastMath.abs(v3.y - this.y);
/* 473 */     double dz = FastMath.abs(v3.z - this.z);
/* 474 */     return dx + dy + dz;
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(Vector<Euclidean3D> v) {
/* 479 */     return distance((Point<Euclidean3D>)v);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(Point<Euclidean3D> v) {
/* 484 */     Vector3D v3 = (Vector3D)v;
/* 485 */     double dx = v3.x - this.x;
/* 486 */     double dy = v3.y - this.y;
/* 487 */     double dz = v3.z - this.z;
/* 488 */     return FastMath.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceInf(Vector<Euclidean3D> v) {
/* 493 */     Vector3D v3 = (Vector3D)v;
/* 494 */     double dx = FastMath.abs(v3.x - this.x);
/* 495 */     double dy = FastMath.abs(v3.y - this.y);
/* 496 */     double dz = FastMath.abs(v3.z - this.z);
/* 497 */     return FastMath.max(FastMath.max(dx, dy), dz);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceSq(Vector<Euclidean3D> v) {
/* 502 */     Vector3D v3 = (Vector3D)v;
/* 503 */     double dx = v3.x - this.x;
/* 504 */     double dy = v3.y - this.y;
/* 505 */     double dz = v3.z - this.z;
/* 506 */     return dx * dx + dy * dy + dz * dz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double dotProduct(Vector3D v1, Vector3D v2) {
/* 515 */     return v1.dotProduct(v2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3D crossProduct(Vector3D v1, Vector3D v2) {
/* 524 */     return v1.crossProduct(v2);
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
/*     */   public static double distance1(Vector3D v1, Vector3D v2) {
/* 536 */     return v1.distance1(v2);
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
/*     */   public static double distance(Vector3D v1, Vector3D v2) {
/* 548 */     return v1.distance(v2);
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
/*     */   public static double distanceInf(Vector3D v1, Vector3D v2) {
/* 560 */     return v1.distanceInf(v2);
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
/*     */   public static double distanceSq(Vector3D v1, Vector3D v2) {
/* 572 */     return v1.distanceSq(v2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 580 */     return Vector3DFormat.getInstance().format(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(NumberFormat format) {
/* 585 */     return (new Vector3DFormat(format)).format(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/Vector3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */