/*     */ package org.apache.commons.math3.geometry.euclidean.oned;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.geometry.Vector;
/*     */ import org.apache.commons.math3.util.FastMath;
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
/*     */ public class Vector1D
/*     */   implements Vector<Euclidean1D>
/*     */ {
/*  36 */   public static final Vector1D ZERO = new Vector1D(0.0D);
/*     */ 
/*     */   
/*  39 */   public static final Vector1D ONE = new Vector1D(1.0D);
/*     */ 
/*     */ 
/*     */   
/*  43 */   public static final Vector1D NaN = new Vector1D(Double.NaN);
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final Vector1D POSITIVE_INFINITY = new Vector1D(Double.POSITIVE_INFINITY);
/*     */ 
/*     */ 
/*     */   
/*  51 */   public static final Vector1D NEGATIVE_INFINITY = new Vector1D(Double.NEGATIVE_INFINITY);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 7556674948671647925L;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D(double x) {
/*  66 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector1D(double a, Vector1D u) {
/*  76 */     this.x = a * u.x;
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
/*     */   public Vector1D(double a1, Vector1D u1, double a2, Vector1D u2) {
/*  88 */     this.x = a1 * u1.x + a2 * u2.x;
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
/*     */   public Vector1D(double a1, Vector1D u1, double a2, Vector1D u2, double a3, Vector1D u3) {
/* 103 */     this.x = a1 * u1.x + a2 * u2.x + a3 * u3.x;
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
/*     */   public Vector1D(double a1, Vector1D u1, double a2, Vector1D u2, double a3, Vector1D u3, double a4, Vector1D u4) {
/* 120 */     this.x = a1 * u1.x + a2 * u2.x + a3 * u3.x + a4 * u4.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 128 */     return this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public Space getSpace() {
/* 133 */     return Euclidean1D.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D getZero() {
/* 138 */     return ZERO;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNorm1() {
/* 143 */     return FastMath.abs(this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 148 */     return FastMath.abs(this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNormSq() {
/* 153 */     return this.x * this.x;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNormInf() {
/* 158 */     return FastMath.abs(this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D add(Vector<Euclidean1D> v) {
/* 163 */     Vector1D v1 = (Vector1D)v;
/* 164 */     return new Vector1D(this.x + v1.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D add(double factor, Vector<Euclidean1D> v) {
/* 169 */     Vector1D v1 = (Vector1D)v;
/* 170 */     return new Vector1D(this.x + factor * v1.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D subtract(Vector<Euclidean1D> p) {
/* 175 */     Vector1D p3 = (Vector1D)p;
/* 176 */     return new Vector1D(this.x - p3.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D subtract(double factor, Vector<Euclidean1D> v) {
/* 181 */     Vector1D v1 = (Vector1D)v;
/* 182 */     return new Vector1D(this.x - factor * v1.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D normalize() throws MathArithmeticException {
/* 187 */     double s = getNorm();
/* 188 */     if (s == 0.0D) {
/* 189 */       throw new MathArithmeticException(LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new Object[0]);
/*     */     }
/* 191 */     return scalarMultiply(1.0D / s);
/*     */   }
/*     */   
/*     */   public Vector1D negate() {
/* 195 */     return new Vector1D(-this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector1D scalarMultiply(double a) {
/* 200 */     return new Vector1D(a * this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 205 */     return Double.isNaN(this.x);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInfinite() {
/* 210 */     return (!isNaN() && Double.isInfinite(this.x));
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance1(Vector<Euclidean1D> p) {
/* 215 */     Vector1D p3 = (Vector1D)p;
/* 216 */     double dx = FastMath.abs(p3.x - this.x);
/* 217 */     return dx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public double distance(Vector<Euclidean1D> p) {
/* 225 */     return distance((Point<Euclidean1D>)p);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(Point<Euclidean1D> p) {
/* 230 */     Vector1D p3 = (Vector1D)p;
/* 231 */     double dx = p3.x - this.x;
/* 232 */     return FastMath.abs(dx);
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceInf(Vector<Euclidean1D> p) {
/* 237 */     Vector1D p3 = (Vector1D)p;
/* 238 */     double dx = FastMath.abs(p3.x - this.x);
/* 239 */     return dx;
/*     */   }
/*     */ 
/*     */   
/*     */   public double distanceSq(Vector<Euclidean1D> p) {
/* 244 */     Vector1D p3 = (Vector1D)p;
/* 245 */     double dx = p3.x - this.x;
/* 246 */     return dx * dx;
/*     */   }
/*     */ 
/*     */   
/*     */   public double dotProduct(Vector<Euclidean1D> v) {
/* 251 */     Vector1D v1 = (Vector1D)v;
/* 252 */     return this.x * v1.x;
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
/*     */   public static double distance(Vector1D p1, Vector1D p2) {
/* 264 */     return p1.distance(p2);
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
/*     */   public static double distanceInf(Vector1D p1, Vector1D p2) {
/* 276 */     return p1.distanceInf(p2);
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
/*     */   public static double distanceSq(Vector1D p1, Vector1D p2) {
/* 288 */     return p1.distanceSq(p2);
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
/* 313 */     if (this == other) {
/* 314 */       return true;
/*     */     }
/*     */     
/* 317 */     if (other instanceof Vector1D) {
/* 318 */       Vector1D rhs = (Vector1D)other;
/* 319 */       if (rhs.isNaN()) {
/* 320 */         return isNaN();
/*     */       }
/*     */       
/* 323 */       return (this.x == rhs.x);
/*     */     } 
/* 325 */     return false;
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
/* 337 */     if (isNaN()) {
/* 338 */       return 7785;
/*     */     }
/* 340 */     return 997 * MathUtils.hash(this.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 348 */     return Vector1DFormat.getInstance().format(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(NumberFormat format) {
/* 353 */     return (new Vector1DFormat(format)).format(this);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/oned/Vector1D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */