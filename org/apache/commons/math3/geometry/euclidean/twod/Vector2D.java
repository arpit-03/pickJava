/*     */ package org.apache.commons.math3.geometry.euclidean.twod;
/*     */ 
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
/*     */ public class Vector2D
/*     */   implements Vector<Euclidean2D>
/*     */ {
/*  38 */   public static final Vector2D ZERO = new Vector2D(0.0D, 0.0D);
/*     */ 
/*     */ 
/*     */   
/*  42 */   public static final Vector2D NaN = new Vector2D(Double.NaN, Double.NaN);
/*     */ 
/*     */ 
/*     */   
/*  46 */   public static final Vector2D POSITIVE_INFINITY = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final Vector2D NEGATIVE_INFINITY = new Vector2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 266938651998679754L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double x;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double y;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D(double x, double y) {
/*  70 */     this.x = x;
/*  71 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D(double[] v) throws DimensionMismatchException {
/*  81 */     if (v.length != 2) {
/*  82 */       throw new DimensionMismatchException(v.length, 2);
/*     */     }
/*  84 */     this.x = v[0];
/*  85 */     this.y = v[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D(double a, Vector2D u) {
/*  95 */     this.x = a * u.x;
/*  96 */     this.y = a * u.y;
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
/*     */   public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2) {
/* 108 */     this.x = a1 * u1.x + a2 * u2.x;
/* 109 */     this.y = a1 * u1.y + a2 * u2.y;
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
/*     */   public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3) {
/* 124 */     this.x = a1 * u1.x + a2 * u2.x + a3 * u3.x;
/* 125 */     this.y = a1 * u1.y + a2 * u2.y + a3 * u3.y;
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
/*     */   public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3, double a4, Vector2D u4) {
/* 142 */     this.x = a1 * u1.x + a2 * u2.x + a3 * u3.x + a4 * u4.x;
/* 143 */     this.y = a1 * u1.y + a2 * u2.y + a3 * u3.y + a4 * u4.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 151 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getY() {
/* 159 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] toArray() {
/* 167 */     return new double[] { this.x, this.y };
/*     */   }
/*     */ 
/*     */   
/*     */   public Space getSpace() {
/* 172 */     return Euclidean2D.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D getZero() {
/* 177 */     return ZERO;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNorm1() {
/* 182 */     return FastMath.abs(this.x) + FastMath.abs(this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 187 */     return FastMath.sqrt(this.x * this.x + this.y * this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNormSq() {
/* 192 */     return this.x * this.x + this.y * this.y;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNormInf() {
/* 197 */     return FastMath.max(FastMath.abs(this.x), FastMath.abs(this.y));
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D add(Vector<Euclidean2D> v) {
/* 202 */     Vector2D v2 = (Vector2D)v;
/* 203 */     return new Vector2D(this.x + v2.getX(), this.y + v2.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D add(double factor, Vector<Euclidean2D> v) {
/* 208 */     Vector2D v2 = (Vector2D)v;
/* 209 */     return new Vector2D(this.x + factor * v2.getX(), this.y + factor * v2.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D subtract(Vector<Euclidean2D> p) {
/* 214 */     Vector2D p3 = (Vector2D)p;
/* 215 */     return new Vector2D(this.x - p3.x, this.y - p3.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D subtract(double factor, Vector<Euclidean2D> v) {
/* 220 */     Vector2D v2 = (Vector2D)v;
/* 221 */     return new Vector2D(this.x - factor * v2.getX(), this.y - factor * v2.getY());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D normalize() throws MathArithmeticException {
/* 226 */     double s = getNorm();
/* 227 */     if (s == 0.0D) {
/* 228 */       throw new MathArithmeticException(LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new Object[0]);
/*     */     }
/* 230 */     return scalarMultiply(1.0D / s);
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
/*     */   public static double angle(Vector2D v1, Vector2D v2) throws MathArithmeticException {
/* 246 */     double normProduct = v1.getNorm() * v2.getNorm();
/* 247 */     if (normProduct == 0.0D) {
/* 248 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*     */     }
/*     */     
/* 251 */     double dot = v1.dotProduct(v2);
/* 252 */     double threshold = normProduct * 0.9999D;
/* 253 */     if (dot < -threshold || dot > threshold) {
/*     */       
/* 255 */       double n = FastMath.abs(MathArrays.linearCombination(v1.x, v2.y, -v1.y, v2.x));
/* 256 */       if (dot >= 0.0D) {
/* 257 */         return FastMath.asin(n / normProduct);
/*     */       }
/* 259 */       return Math.PI - FastMath.asin(n / normProduct);
/*     */     } 
/*     */ 
/*     */     
/* 263 */     return FastMath.acos(dot / normProduct);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2D negate() {
/* 269 */     return new Vector2D(-this.x, -this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector2D scalarMultiply(double a) {
/* 274 */     return new Vector2D(a * this.x, a * this.y);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 279 */     return (Double.isNaN(this.x) || Double.isNaN(this.y));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 284 */     return (!isNaN() && (Double.isInfinite(this.x) || Double.isInfinite(this.y)));
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance1(Vector<Euclidean2D> p) {
/* 289 */     Vector2D p3 = (Vector2D)p;
/* 290 */     double dx = FastMath.abs(p3.x - this.x);
/* 291 */     double dy = FastMath.abs(p3.y - this.y);
/* 292 */     return dx + dy;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double distance(Vector<Euclidean2D> p) {
/* 298 */     return distance((Point<Euclidean2D>)p);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(Point<Euclidean2D> p) {
/* 303 */     Vector2D p3 = (Vector2D)p;
/* 304 */     double dx = p3.x - this.x;
/* 305 */     double dy = p3.y - this.y;
/* 306 */     return FastMath.sqrt(dx * dx + dy * dy);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceInf(Vector<Euclidean2D> p) {
/* 311 */     Vector2D p3 = (Vector2D)p;
/* 312 */     double dx = FastMath.abs(p3.x - this.x);
/* 313 */     double dy = FastMath.abs(p3.y - this.y);
/* 314 */     return FastMath.max(dx, dy);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceSq(Vector<Euclidean2D> p) {
/* 319 */     Vector2D p3 = (Vector2D)p;
/* 320 */     double dx = p3.x - this.x;
/* 321 */     double dy = p3.y - this.y;
/* 322 */     return dx * dx + dy * dy;
/*     */   }
/*     */ 
/*     */   
/*     */   public double dotProduct(Vector<Euclidean2D> v) {
/* 327 */     Vector2D v2 = (Vector2D)v;
/* 328 */     return MathArrays.linearCombination(this.x, v2.x, this.y, v2.y);
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
/*     */   public double crossProduct(Vector2D p1, Vector2D p2) {
/* 352 */     double x1 = p2.getX() - p1.getX();
/* 353 */     double y1 = getY() - p1.getY();
/* 354 */     double x2 = getX() - p1.getX();
/* 355 */     double y2 = p2.getY() - p1.getY();
/* 356 */     return MathArrays.linearCombination(x1, y1, -x2, y2);
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
/*     */   public static double distance(Vector2D p1, Vector2D p2) {
/* 368 */     return p1.distance(p2);
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
/*     */   public static double distanceInf(Vector2D p1, Vector2D p2) {
/* 380 */     return p1.distanceInf(p2);
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
/*     */   public static double distanceSq(Vector2D p1, Vector2D p2) {
/* 392 */     return p1.distanceSq(p2);
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
/* 417 */     if (this == other) {
/* 418 */       return true;
/*     */     }
/*     */     
/* 421 */     if (other instanceof Vector2D) {
/* 422 */       Vector2D rhs = (Vector2D)other;
/* 423 */       if (rhs.isNaN()) {
/* 424 */         return isNaN();
/*     */       }
/*     */       
/* 427 */       return (this.x == rhs.x && this.y == rhs.y);
/*     */     } 
/* 429 */     return false;
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
/* 441 */     if (isNaN()) {
/* 442 */       return 542;
/*     */     }
/* 444 */     return 122 * (76 * MathUtils.hash(this.x) + MathUtils.hash(this.y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 452 */     return Vector2DFormat.getInstance().format(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(NumberFormat format) {
/* 457 */     return (new Vector2DFormat(format)).format(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/twod/Vector2D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */