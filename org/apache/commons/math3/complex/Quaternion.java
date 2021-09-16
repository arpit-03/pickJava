/*     */ package org.apache.commons.math3.complex;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*     */ import org.apache.commons.math3.exception.ZeroException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.FastMath;
/*     */ import org.apache.commons.math3.util.MathUtils;
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
/*     */ public final class Quaternion
/*     */   implements Serializable
/*     */ {
/*  38 */   public static final Quaternion IDENTITY = new Quaternion(1.0D, 0.0D, 0.0D, 0.0D);
/*     */   
/*  40 */   public static final Quaternion ZERO = new Quaternion(0.0D, 0.0D, 0.0D, 0.0D);
/*     */   
/*  42 */   public static final Quaternion I = new Quaternion(0.0D, 1.0D, 0.0D, 0.0D);
/*     */   
/*  44 */   public static final Quaternion J = new Quaternion(0.0D, 0.0D, 1.0D, 0.0D);
/*     */   
/*  46 */   public static final Quaternion K = new Quaternion(0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 20092012L;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double q0;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double q1;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double q2;
/*     */ 
/*     */ 
/*     */   
/*     */   private final double q3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion(double a, double b, double c, double d) {
/*  72 */     this.q0 = a;
/*  73 */     this.q1 = b;
/*  74 */     this.q2 = c;
/*  75 */     this.q3 = d;
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
/*     */   public Quaternion(double scalar, double[] v) throws DimensionMismatchException {
/*  89 */     if (v.length != 3) {
/*  90 */       throw new DimensionMismatchException(v.length, 3);
/*     */     }
/*  92 */     this.q0 = scalar;
/*  93 */     this.q1 = v[0];
/*  94 */     this.q2 = v[1];
/*  95 */     this.q3 = v[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion(double[] v) {
/* 105 */     this(0.0D, v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion getConjugate() {
/* 114 */     return new Quaternion(this.q0, -this.q1, -this.q2, -this.q3);
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
/*     */   public static Quaternion multiply(Quaternion q1, Quaternion q2) {
/* 126 */     double q1a = q1.getQ0();
/* 127 */     double q1b = q1.getQ1();
/* 128 */     double q1c = q1.getQ2();
/* 129 */     double q1d = q1.getQ3();
/*     */ 
/*     */     
/* 132 */     double q2a = q2.getQ0();
/* 133 */     double q2b = q2.getQ1();
/* 134 */     double q2c = q2.getQ2();
/* 135 */     double q2d = q2.getQ3();
/*     */ 
/*     */     
/* 138 */     double w = q1a * q2a - q1b * q2b - q1c * q2c - q1d * q2d;
/* 139 */     double x = q1a * q2b + q1b * q2a + q1c * q2d - q1d * q2c;
/* 140 */     double y = q1a * q2c - q1b * q2d + q1c * q2a + q1d * q2b;
/* 141 */     double z = q1a * q2d + q1b * q2c - q1c * q2b + q1d * q2a;
/*     */     
/* 143 */     return new Quaternion(w, x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion multiply(Quaternion q) {
/* 153 */     return multiply(this, q);
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
/*     */   public static Quaternion add(Quaternion q1, Quaternion q2) {
/* 165 */     return new Quaternion(q1.getQ0() + q2.getQ0(), q1.getQ1() + q2.getQ1(), q1.getQ2() + q2.getQ2(), q1.getQ3() + q2.getQ3());
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
/*     */   public Quaternion add(Quaternion q) {
/* 178 */     return add(this, q);
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
/*     */   public static Quaternion subtract(Quaternion q1, Quaternion q2) {
/* 190 */     return new Quaternion(q1.getQ0() - q2.getQ0(), q1.getQ1() - q2.getQ1(), q1.getQ2() - q2.getQ2(), q1.getQ3() - q2.getQ3());
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
/*     */   public Quaternion subtract(Quaternion q) {
/* 203 */     return subtract(this, q);
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
/*     */   public static double dotProduct(Quaternion q1, Quaternion q2) {
/* 215 */     return q1.getQ0() * q2.getQ0() + q1.getQ1() * q2.getQ1() + q1.getQ2() * q2.getQ2() + q1.getQ3() * q2.getQ3();
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
/*     */   public double dotProduct(Quaternion q) {
/* 228 */     return dotProduct(this, q);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getNorm() {
/* 237 */     return FastMath.sqrt(this.q0 * this.q0 + this.q1 * this.q1 + this.q2 * this.q2 + this.q3 * this.q3);
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
/*     */   public Quaternion normalize() {
/* 251 */     double norm = getNorm();
/*     */     
/* 253 */     if (norm < Precision.SAFE_MIN) {
/* 254 */       throw new ZeroException(LocalizedFormats.NORM, new Object[] { Double.valueOf(norm) });
/*     */     }
/*     */     
/* 257 */     return new Quaternion(this.q0 / norm, this.q1 / norm, this.q2 / norm, this.q3 / norm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 268 */     if (this == other) {
/* 269 */       return true;
/*     */     }
/* 271 */     if (other instanceof Quaternion) {
/* 272 */       Quaternion q = (Quaternion)other;
/* 273 */       return (this.q0 == q.getQ0() && this.q1 == q.getQ1() && this.q2 == q.getQ2() && this.q3 == q.getQ3());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 288 */     int result = 17;
/* 289 */     for (double comp : new double[] { this.q0, this.q1, this.q2, this.q3 }) {
/* 290 */       int c = MathUtils.hash(comp);
/* 291 */       result = 31 * result + c;
/*     */     } 
/* 293 */     return result;
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
/*     */   public boolean equals(Quaternion q, double eps) {
/* 307 */     return (Precision.equals(this.q0, q.getQ0(), eps) && Precision.equals(this.q1, q.getQ1(), eps) && Precision.equals(this.q2, q.getQ2(), eps) && Precision.equals(this.q3, q.getQ3(), eps));
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
/*     */   public boolean isUnitQuaternion(double eps) {
/* 322 */     return Precision.equals(getNorm(), 1.0D, eps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPureQuaternion(double eps) {
/* 333 */     return (FastMath.abs(getQ0()) <= eps);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion getPositivePolarForm() {
/* 342 */     if (getQ0() < 0.0D) {
/* 343 */       Quaternion unitQ = normalize();
/*     */ 
/*     */       
/* 346 */       return new Quaternion(-unitQ.getQ0(), -unitQ.getQ1(), -unitQ.getQ2(), -unitQ.getQ3());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 351 */     return normalize();
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
/*     */   public Quaternion getInverse() {
/* 363 */     double squareNorm = this.q0 * this.q0 + this.q1 * this.q1 + this.q2 * this.q2 + this.q3 * this.q3;
/* 364 */     if (squareNorm < Precision.SAFE_MIN) {
/* 365 */       throw new ZeroException(LocalizedFormats.NORM, new Object[] { Double.valueOf(squareNorm) });
/*     */     }
/*     */     
/* 368 */     return new Quaternion(this.q0 / squareNorm, -this.q1 / squareNorm, -this.q2 / squareNorm, -this.q3 / squareNorm);
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
/*     */   public double getQ0() {
/* 380 */     return this.q0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getQ1() {
/* 390 */     return this.q1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getQ2() {
/* 400 */     return this.q2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getQ3() {
/* 410 */     return this.q3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getScalarPart() {
/* 420 */     return getQ0();
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
/*     */   public double[] getVectorPart() {
/* 432 */     return new double[] { getQ1(), getQ2(), getQ3() };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quaternion multiply(double alpha) {
/* 442 */     return new Quaternion(alpha * this.q0, alpha * this.q1, alpha * this.q2, alpha * this.q3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 453 */     String sp = " ";
/* 454 */     StringBuilder s = new StringBuilder();
/* 455 */     s.append("[").append(this.q0).append(" ").append(this.q1).append(" ").append(this.q2).append(" ").append(this.q3).append("]");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     return s.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/complex/Quaternion.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */