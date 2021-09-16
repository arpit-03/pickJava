/*      */ package org.apache.commons.math3.geometry.euclidean.threed;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.text.NumberFormat;
/*      */ import org.apache.commons.math3.RealFieldElement;
/*      */ import org.apache.commons.math3.exception.DimensionMismatchException;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*      */ import org.apache.commons.math3.util.FastMath;
/*      */ import org.apache.commons.math3.util.MathArrays;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FieldVector3D<T extends RealFieldElement<T>>
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 20130224L;
/*      */   private final T x;
/*      */   private final T y;
/*      */   private final T z;
/*      */   
/*      */   public FieldVector3D(T x, T y, T z) {
/*   60 */     this.x = x;
/*   61 */     this.y = y;
/*   62 */     this.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T[] v) throws DimensionMismatchException {
/*   72 */     if (v.length != 3) {
/*   73 */       throw new DimensionMismatchException(v.length, 3);
/*      */     }
/*   75 */     this.x = v[0];
/*   76 */     this.y = v[1];
/*   77 */     this.z = v[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T alpha, T delta) {
/*   89 */     RealFieldElement realFieldElement = (RealFieldElement)delta.cos();
/*   90 */     this.x = (T)((RealFieldElement)alpha.cos()).multiply(realFieldElement);
/*   91 */     this.y = (T)((RealFieldElement)alpha.sin()).multiply(realFieldElement);
/*   92 */     this.z = (T)delta.sin();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a, FieldVector3D<T> u) {
/*  102 */     this.x = (T)a.multiply(u.x);
/*  103 */     this.y = (T)a.multiply(u.y);
/*  104 */     this.z = (T)a.multiply(u.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a, Vector3D u) {
/*  114 */     this.x = (T)a.multiply(u.getX());
/*  115 */     this.y = (T)a.multiply(u.getY());
/*  116 */     this.z = (T)a.multiply(u.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(double a, FieldVector3D<T> u) {
/*  126 */     this.x = (T)u.x.multiply(a);
/*  127 */     this.y = (T)u.y.multiply(a);
/*  128 */     this.z = (T)u.z.multiply(a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a1, FieldVector3D<T> u1, T a2, FieldVector3D<T> u2) {
/*  141 */     T prototype = a1;
/*  142 */     this.x = (T)prototype.linearCombination(a1, u1.getX(), a2, u2.getX());
/*  143 */     this.y = (T)prototype.linearCombination(a1, u1.getY(), a2, u2.getY());
/*  144 */     this.z = (T)prototype.linearCombination(a1, u1.getZ(), a2, u2.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a1, Vector3D u1, T a2, Vector3D u2) {
/*  157 */     T prototype = a1;
/*  158 */     this.x = (T)prototype.linearCombination(u1.getX(), a1, u2.getX(), a2);
/*  159 */     this.y = (T)prototype.linearCombination(u1.getY(), a1, u2.getY(), a2);
/*  160 */     this.z = (T)prototype.linearCombination(u1.getZ(), a1, u2.getZ(), a2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(double a1, FieldVector3D<T> u1, double a2, FieldVector3D<T> u2) {
/*  173 */     T prototype = u1.getX();
/*  174 */     this.x = (T)prototype.linearCombination(a1, u1.getX(), a2, u2.getX());
/*  175 */     this.y = (T)prototype.linearCombination(a1, u1.getY(), a2, u2.getY());
/*  176 */     this.z = (T)prototype.linearCombination(a1, u1.getZ(), a2, u2.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a1, FieldVector3D<T> u1, T a2, FieldVector3D<T> u2, T a3, FieldVector3D<T> u3) {
/*  192 */     T prototype = a1;
/*  193 */     this.x = (T)prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX());
/*  194 */     this.y = (T)prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY());
/*  195 */     this.z = (T)prototype.linearCombination(a1, u1.getZ(), a2, u2.getZ(), a3, u3.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a1, Vector3D u1, T a2, Vector3D u2, T a3, Vector3D u3) {
/*  211 */     T prototype = a1;
/*  212 */     this.x = (T)prototype.linearCombination(u1.getX(), a1, u2.getX(), a2, u3.getX(), a3);
/*  213 */     this.y = (T)prototype.linearCombination(u1.getY(), a1, u2.getY(), a2, u3.getY(), a3);
/*  214 */     this.z = (T)prototype.linearCombination(u1.getZ(), a1, u2.getZ(), a2, u3.getZ(), a3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(double a1, FieldVector3D<T> u1, double a2, FieldVector3D<T> u2, double a3, FieldVector3D<T> u3) {
/*  230 */     T prototype = u1.getX();
/*  231 */     this.x = (T)prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX());
/*  232 */     this.y = (T)prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY());
/*  233 */     this.z = (T)prototype.linearCombination(a1, u1.getZ(), a2, u2.getZ(), a3, u3.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a1, FieldVector3D<T> u1, T a2, FieldVector3D<T> u2, T a3, FieldVector3D<T> u3, T a4, FieldVector3D<T> u4) {
/*  252 */     T prototype = a1;
/*  253 */     this.x = (T)prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX(), a4, u4.getX());
/*  254 */     this.y = (T)prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY(), a4, u4.getY());
/*  255 */     this.z = (T)prototype.linearCombination(a1, u1.getZ(), a2, u2.getZ(), a3, u3.getZ(), a4, u4.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(T a1, Vector3D u1, T a2, Vector3D u2, T a3, Vector3D u3, T a4, Vector3D u4) {
/*  274 */     T prototype = a1;
/*  275 */     this.x = (T)prototype.linearCombination(u1.getX(), a1, u2.getX(), a2, u3.getX(), a3, u4.getX(), a4);
/*  276 */     this.y = (T)prototype.linearCombination(u1.getY(), a1, u2.getY(), a2, u3.getY(), a3, u4.getY(), a4);
/*  277 */     this.z = (T)prototype.linearCombination(u1.getZ(), a1, u2.getZ(), a2, u3.getZ(), a3, u4.getZ(), a4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D(double a1, FieldVector3D<T> u1, double a2, FieldVector3D<T> u2, double a3, FieldVector3D<T> u3, double a4, FieldVector3D<T> u4) {
/*  296 */     T prototype = u1.getX();
/*  297 */     this.x = (T)prototype.linearCombination(a1, u1.getX(), a2, u2.getX(), a3, u3.getX(), a4, u4.getX());
/*  298 */     this.y = (T)prototype.linearCombination(a1, u1.getY(), a2, u2.getY(), a3, u3.getY(), a4, u4.getY());
/*  299 */     this.z = (T)prototype.linearCombination(a1, u1.getZ(), a2, u2.getZ(), a3, u3.getZ(), a4, u4.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getX() {
/*  307 */     return this.x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getY() {
/*  315 */     return this.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getZ() {
/*  323 */     return this.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T[] toArray() {
/*  331 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(this.x.getField(), 3);
/*  332 */     arrayOfRealFieldElement[0] = (RealFieldElement)this.x;
/*  333 */     arrayOfRealFieldElement[1] = (RealFieldElement)this.y;
/*  334 */     arrayOfRealFieldElement[2] = (RealFieldElement)this.z;
/*  335 */     return (T[])arrayOfRealFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3D toVector3D() {
/*  342 */     return new Vector3D(this.x.getReal(), this.y.getReal(), this.z.getReal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getNorm1() {
/*  349 */     return (T)((RealFieldElement)((RealFieldElement)this.x.abs()).add(this.y.abs())).add(this.z.abs());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getNorm() {
/*  357 */     return (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.x.multiply(this.x)).add(this.y.multiply(this.y))).add(this.z.multiply(this.z))).sqrt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getNormSq() {
/*  365 */     return (T)((RealFieldElement)((RealFieldElement)this.x.multiply(this.x)).add(this.y.multiply(this.y))).add(this.z.multiply(this.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getNormInf() {
/*  372 */     RealFieldElement realFieldElement1 = (RealFieldElement)this.x.abs();
/*  373 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.y.abs();
/*  374 */     RealFieldElement realFieldElement3 = (RealFieldElement)this.z.abs();
/*  375 */     if (realFieldElement1.getReal() <= realFieldElement2.getReal()) {
/*  376 */       if (realFieldElement2.getReal() <= realFieldElement3.getReal()) {
/*  377 */         return (T)realFieldElement3;
/*      */       }
/*  379 */       return (T)realFieldElement2;
/*      */     } 
/*      */     
/*  382 */     if (realFieldElement1.getReal() <= realFieldElement3.getReal()) {
/*  383 */       return (T)realFieldElement3;
/*      */     }
/*  385 */     return (T)realFieldElement1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getAlpha() {
/*  395 */     return (T)this.y.atan2(this.x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getDelta() {
/*  403 */     return (T)((RealFieldElement)this.z.divide(getNorm())).asin();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> add(FieldVector3D<T> v) {
/*  411 */     return new FieldVector3D((T)this.x.add(v.x), (T)this.y.add(v.y), (T)this.z.add(v.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> add(Vector3D v) {
/*  419 */     return new FieldVector3D((T)this.x.add(v.getX()), (T)this.y.add(v.getY()), (T)this.z.add(v.getZ()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> add(T factor, FieldVector3D<T> v) {
/*  428 */     return new FieldVector3D((T)this.x.getField().getOne(), this, factor, v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> add(T factor, Vector3D v) {
/*  437 */     return new FieldVector3D((T)this.x.add(factor.multiply(v.getX())), (T)this.y.add(factor.multiply(v.getY())), (T)this.z.add(factor.multiply(v.getZ())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> add(double factor, FieldVector3D<T> v) {
/*  448 */     return new FieldVector3D(1.0D, this, factor, v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> add(double factor, Vector3D v) {
/*  457 */     return new FieldVector3D((T)this.x.add(factor * v.getX()), (T)this.y.add(factor * v.getY()), (T)this.z.add(factor * v.getZ()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> subtract(FieldVector3D<T> v) {
/*  467 */     return new FieldVector3D((T)this.x.subtract(v.x), (T)this.y.subtract(v.y), (T)this.z.subtract(v.z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> subtract(Vector3D v) {
/*  475 */     return new FieldVector3D((T)this.x.subtract(v.getX()), (T)this.y.subtract(v.getY()), (T)this.z.subtract(v.getZ()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> subtract(T factor, FieldVector3D<T> v) {
/*  484 */     return new FieldVector3D((T)this.x.getField().getOne(), this, (T)factor.negate(), v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> subtract(T factor, Vector3D v) {
/*  493 */     return new FieldVector3D((T)this.x.subtract(factor.multiply(v.getX())), (T)this.y.subtract(factor.multiply(v.getY())), (T)this.z.subtract(factor.multiply(v.getZ())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> subtract(double factor, FieldVector3D<T> v) {
/*  504 */     return new FieldVector3D(1.0D, this, -factor, v);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> subtract(double factor, Vector3D v) {
/*  513 */     return new FieldVector3D((T)this.x.subtract(factor * v.getX()), (T)this.y.subtract(factor * v.getY()), (T)this.z.subtract(factor * v.getZ()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> normalize() throws MathArithmeticException {
/*  523 */     T s = getNorm();
/*  524 */     if (s.getReal() == 0.0D) {
/*  525 */       throw new MathArithmeticException(LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new Object[0]);
/*      */     }
/*  527 */     return scalarMultiply((T)s.reciprocal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> orthogonal() throws MathArithmeticException {
/*  547 */     double threshold = 0.6D * getNorm().getReal();
/*  548 */     if (threshold == 0.0D) {
/*  549 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*      */     
/*  552 */     if (FastMath.abs(this.x.getReal()) <= threshold) {
/*  553 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.y.multiply(this.y)).add(this.z.multiply(this.z))).sqrt()).reciprocal();
/*  554 */       return new FieldVector3D((T)realFieldElement1.getField().getZero(), (T)realFieldElement1.multiply(this.z), (T)((RealFieldElement)realFieldElement1.multiply(this.y)).negate());
/*  555 */     }  if (FastMath.abs(this.y.getReal()) <= threshold) {
/*  556 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.x.multiply(this.x)).add(this.z.multiply(this.z))).sqrt()).reciprocal();
/*  557 */       return new FieldVector3D((T)((RealFieldElement)realFieldElement1.multiply(this.z)).negate(), (T)realFieldElement1.getField().getZero(), (T)realFieldElement1.multiply(this.x));
/*      */     } 
/*  559 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.x.multiply(this.x)).add(this.y.multiply(this.y))).sqrt()).reciprocal();
/*  560 */     return new FieldVector3D((T)realFieldElement.multiply(this.y), (T)((RealFieldElement)realFieldElement.multiply(this.x)).negate(), (T)realFieldElement.getField().getZero());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T angle(FieldVector3D<T> v1, FieldVector3D<T> v2) throws MathArithmeticException {
/*  580 */     RealFieldElement realFieldElement = (RealFieldElement)v1.getNorm().multiply(v2.getNorm());
/*  581 */     if (realFieldElement.getReal() == 0.0D) {
/*  582 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*      */     
/*  585 */     T dot = dotProduct(v1, v2);
/*  586 */     double threshold = realFieldElement.getReal() * 0.9999D;
/*  587 */     if (dot.getReal() < -threshold || dot.getReal() > threshold) {
/*      */       
/*  589 */       FieldVector3D<T> v3 = crossProduct(v1, v2);
/*  590 */       if (dot.getReal() >= 0.0D) {
/*  591 */         return (T)((RealFieldElement)v3.getNorm().divide(realFieldElement)).asin();
/*      */       }
/*  593 */       return (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)v3.getNorm().divide(realFieldElement)).asin()).subtract(Math.PI)).negate();
/*      */     } 
/*      */ 
/*      */     
/*  597 */     return (T)((RealFieldElement)dot.divide(realFieldElement)).acos();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T angle(FieldVector3D<T> v1, Vector3D v2) throws MathArithmeticException {
/*  616 */     RealFieldElement realFieldElement = (RealFieldElement)v1.getNorm().multiply(v2.getNorm());
/*  617 */     if (realFieldElement.getReal() == 0.0D) {
/*  618 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
/*      */     }
/*      */     
/*  621 */     T dot = dotProduct(v1, v2);
/*  622 */     double threshold = realFieldElement.getReal() * 0.9999D;
/*  623 */     if (dot.getReal() < -threshold || dot.getReal() > threshold) {
/*      */       
/*  625 */       FieldVector3D<T> v3 = crossProduct(v1, v2);
/*  626 */       if (dot.getReal() >= 0.0D) {
/*  627 */         return (T)((RealFieldElement)v3.getNorm().divide(realFieldElement)).asin();
/*      */       }
/*  629 */       return (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)v3.getNorm().divide(realFieldElement)).asin()).subtract(Math.PI)).negate();
/*      */     } 
/*      */ 
/*      */     
/*  633 */     return (T)((RealFieldElement)dot.divide(realFieldElement)).acos();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T angle(Vector3D v1, FieldVector3D<T> v2) throws MathArithmeticException {
/*  651 */     return angle(v2, v1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> negate() {
/*  658 */     return new FieldVector3D((T)this.x.negate(), (T)this.y.negate(), (T)this.z.negate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> scalarMultiply(T a) {
/*  666 */     return new FieldVector3D((T)this.x.multiply(a), (T)this.y.multiply(a), (T)this.z.multiply(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> scalarMultiply(double a) {
/*  674 */     return new FieldVector3D((T)this.x.multiply(a), (T)this.y.multiply(a), (T)this.z.multiply(a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNaN() {
/*  682 */     return (Double.isNaN(this.x.getReal()) || Double.isNaN(this.y.getReal()) || Double.isNaN(this.z.getReal()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInfinite() {
/*  692 */     return (!isNaN() && (Double.isInfinite(this.x.getReal()) || Double.isInfinite(this.y.getReal()) || Double.isInfinite(this.z.getReal())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/*  717 */     if (this == other) {
/*  718 */       return true;
/*      */     }
/*      */     
/*  721 */     if (other instanceof FieldVector3D) {
/*      */       
/*  723 */       FieldVector3D<T> rhs = (FieldVector3D<T>)other;
/*  724 */       if (rhs.isNaN()) {
/*  725 */         return isNaN();
/*      */       }
/*      */       
/*  728 */       return (this.x.equals(rhs.x) && this.y.equals(rhs.y) && this.z.equals(rhs.z));
/*      */     } 
/*      */     
/*  731 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  743 */     if (isNaN()) {
/*  744 */       return 409;
/*      */     }
/*  746 */     return 311 * (107 * this.x.hashCode() + 83 * this.y.hashCode() + this.z.hashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T dotProduct(FieldVector3D<T> v) {
/*  760 */     return (T)this.x.linearCombination(this.x, v.x, this.y, v.y, this.z, v.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T dotProduct(Vector3D v) {
/*  774 */     return (T)this.x.linearCombination(v.getX(), this.x, v.getY(), this.y, v.getZ(), this.z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> crossProduct(FieldVector3D<T> v) {
/*  782 */     return new FieldVector3D((T)this.x.linearCombination(this.y, v.z, this.z.negate(), v.y), (T)this.y.linearCombination(this.z, v.x, this.x.negate(), v.z), (T)this.z.linearCombination(this.x, v.y, this.y.negate(), v.x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> crossProduct(Vector3D v) {
/*  792 */     return new FieldVector3D((T)this.x.linearCombination(v.getZ(), this.y, -v.getY(), this.z), (T)this.y.linearCombination(v.getX(), this.z, -v.getZ(), this.x), (T)this.z.linearCombination(v.getY(), this.x, -v.getX(), this.y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distance1(FieldVector3D<T> v) {
/*  805 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)v.x.subtract(this.x)).abs();
/*  806 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)v.y.subtract(this.y)).abs();
/*  807 */     RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)v.z.subtract(this.z)).abs();
/*  808 */     return (T)((RealFieldElement)realFieldElement1.add(realFieldElement2)).add(realFieldElement3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distance1(Vector3D v) {
/*  819 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)this.x.subtract(v.getX())).abs();
/*  820 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)this.y.subtract(v.getY())).abs();
/*  821 */     RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)this.z.subtract(v.getZ())).abs();
/*  822 */     return (T)((RealFieldElement)realFieldElement1.add(realFieldElement2)).add(realFieldElement3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distance(FieldVector3D<T> v) {
/*  833 */     RealFieldElement realFieldElement1 = (RealFieldElement)v.x.subtract(this.x);
/*  834 */     RealFieldElement realFieldElement2 = (RealFieldElement)v.y.subtract(this.y);
/*  835 */     RealFieldElement realFieldElement3 = (RealFieldElement)v.z.subtract(this.z);
/*  836 */     return (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.multiply(realFieldElement1)).add(realFieldElement2.multiply(realFieldElement2))).add(realFieldElement3.multiply(realFieldElement3))).sqrt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distance(Vector3D v) {
/*  847 */     RealFieldElement realFieldElement1 = (RealFieldElement)this.x.subtract(v.getX());
/*  848 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.y.subtract(v.getY());
/*  849 */     RealFieldElement realFieldElement3 = (RealFieldElement)this.z.subtract(v.getZ());
/*  850 */     return (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.multiply(realFieldElement1)).add(realFieldElement2.multiply(realFieldElement2))).add(realFieldElement3.multiply(realFieldElement3))).sqrt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distanceInf(FieldVector3D<T> v) {
/*  861 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)v.x.subtract(this.x)).abs();
/*  862 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)v.y.subtract(this.y)).abs();
/*  863 */     RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)v.z.subtract(this.z)).abs();
/*  864 */     if (realFieldElement1.getReal() <= realFieldElement2.getReal()) {
/*  865 */       if (realFieldElement2.getReal() <= realFieldElement3.getReal()) {
/*  866 */         return (T)realFieldElement3;
/*      */       }
/*  868 */       return (T)realFieldElement2;
/*      */     } 
/*      */     
/*  871 */     if (realFieldElement1.getReal() <= realFieldElement3.getReal()) {
/*  872 */       return (T)realFieldElement3;
/*      */     }
/*  874 */     return (T)realFieldElement1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distanceInf(Vector3D v) {
/*  887 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)this.x.subtract(v.getX())).abs();
/*  888 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)this.y.subtract(v.getY())).abs();
/*  889 */     RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)this.z.subtract(v.getZ())).abs();
/*  890 */     if (realFieldElement1.getReal() <= realFieldElement2.getReal()) {
/*  891 */       if (realFieldElement2.getReal() <= realFieldElement3.getReal()) {
/*  892 */         return (T)realFieldElement3;
/*      */       }
/*  894 */       return (T)realFieldElement2;
/*      */     } 
/*      */     
/*  897 */     if (realFieldElement1.getReal() <= realFieldElement3.getReal()) {
/*  898 */       return (T)realFieldElement3;
/*      */     }
/*  900 */     return (T)realFieldElement1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distanceSq(FieldVector3D<T> v) {
/*  913 */     RealFieldElement realFieldElement1 = (RealFieldElement)v.x.subtract(this.x);
/*  914 */     RealFieldElement realFieldElement2 = (RealFieldElement)v.y.subtract(this.y);
/*  915 */     RealFieldElement realFieldElement3 = (RealFieldElement)v.z.subtract(this.z);
/*  916 */     return (T)((RealFieldElement)((RealFieldElement)realFieldElement1.multiply(realFieldElement1)).add(realFieldElement2.multiply(realFieldElement2))).add(realFieldElement3.multiply(realFieldElement3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T distanceSq(Vector3D v) {
/*  927 */     RealFieldElement realFieldElement1 = (RealFieldElement)this.x.subtract(v.getX());
/*  928 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.y.subtract(v.getY());
/*  929 */     RealFieldElement realFieldElement3 = (RealFieldElement)this.z.subtract(v.getZ());
/*  930 */     return (T)((RealFieldElement)((RealFieldElement)realFieldElement1.multiply(realFieldElement1)).add(realFieldElement2.multiply(realFieldElement2))).add(realFieldElement3.multiply(realFieldElement3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T dotProduct(FieldVector3D<T> v1, FieldVector3D<T> v2) {
/*  941 */     return v1.dotProduct(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T dotProduct(FieldVector3D<T> v1, Vector3D v2) {
/*  952 */     return v1.dotProduct(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T dotProduct(Vector3D v1, FieldVector3D<T> v2) {
/*  963 */     return v2.dotProduct(v1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> FieldVector3D<T> crossProduct(FieldVector3D<T> v1, FieldVector3D<T> v2) {
/*  974 */     return v1.crossProduct(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> FieldVector3D<T> crossProduct(FieldVector3D<T> v1, Vector3D v2) {
/*  985 */     return v1.crossProduct(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> FieldVector3D<T> crossProduct(Vector3D v1, FieldVector3D<T> v2) {
/*  996 */     return new FieldVector3D<T>((T)v2.x.linearCombination(v1.getY(), v2.z, -v1.getZ(), v2.y), (T)v2.y.linearCombination(v1.getZ(), v2.x, -v1.getX(), v2.z), (T)v2.z.linearCombination(v1.getX(), v2.y, -v1.getY(), v2.x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distance1(FieldVector3D<T> v1, FieldVector3D<T> v2) {
/* 1012 */     return v1.distance1(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distance1(FieldVector3D<T> v1, Vector3D v2) {
/* 1026 */     return v1.distance1(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distance1(Vector3D v1, FieldVector3D<T> v2) {
/* 1040 */     return v2.distance1(v1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distance(FieldVector3D<T> v1, FieldVector3D<T> v2) {
/* 1054 */     return v1.distance(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distance(FieldVector3D<T> v1, Vector3D v2) {
/* 1068 */     return v1.distance(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distance(Vector3D v1, FieldVector3D<T> v2) {
/* 1082 */     return v2.distance(v1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distanceInf(FieldVector3D<T> v1, FieldVector3D<T> v2) {
/* 1096 */     return v1.distanceInf(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distanceInf(FieldVector3D<T> v1, Vector3D v2) {
/* 1110 */     return v1.distanceInf(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distanceInf(Vector3D v1, FieldVector3D<T> v2) {
/* 1124 */     return v2.distanceInf(v1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distanceSq(FieldVector3D<T> v1, FieldVector3D<T> v2) {
/* 1138 */     return v1.distanceSq(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distanceSq(FieldVector3D<T> v1, Vector3D v2) {
/* 1152 */     return v1.distanceSq(v2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends RealFieldElement<T>> T distanceSq(Vector3D v1, FieldVector3D<T> v2) {
/* 1166 */     return v2.distanceSq(v1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1174 */     return Vector3DFormat.getInstance().format(toVector3D());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString(NumberFormat format) {
/* 1182 */     return (new Vector3DFormat(format)).format(toVector3D());
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/FieldVector3D.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */