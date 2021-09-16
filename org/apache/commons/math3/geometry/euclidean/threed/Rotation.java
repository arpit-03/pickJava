/*      */ package org.apache.commons.math3.geometry.euclidean.threed;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import org.apache.commons.math3.exception.MathArithmeticException;
/*      */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Rotation
/*      */   implements Serializable
/*      */ {
/*   99 */   public static final Rotation IDENTITY = new Rotation(1.0D, 0.0D, 0.0D, 0.0D, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = -2153622329907944313L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final double q0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final double q1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final double q2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final double q3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rotation(double q0, double q1, double q2, double q3, boolean needsNormalization) {
/*  137 */     if (needsNormalization) {
/*      */       
/*  139 */       double inv = 1.0D / FastMath.sqrt(q0 * q0 + q1 * q1 + q2 * q2 + q3 * q3);
/*  140 */       q0 *= inv;
/*  141 */       q1 *= inv;
/*  142 */       q2 *= inv;
/*  143 */       q3 *= inv;
/*      */     } 
/*      */     
/*  146 */     this.q0 = q0;
/*  147 */     this.q1 = q1;
/*  148 */     this.q2 = q2;
/*  149 */     this.q3 = q3;
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
/*      */   @Deprecated
/*      */   public Rotation(Vector3D axis, double angle) throws MathIllegalArgumentException {
/*  166 */     this(axis, angle, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public Rotation(Vector3D axis, double angle, RotationConvention convention) throws MathIllegalArgumentException {
/*  179 */     double norm = axis.getNorm();
/*  180 */     if (norm == 0.0D) {
/*  181 */       throw new MathIllegalArgumentException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_AXIS, new Object[0]);
/*      */     }
/*      */     
/*  184 */     double halfAngle = (convention == RotationConvention.VECTOR_OPERATOR) ? (-0.5D * angle) : (0.5D * angle);
/*  185 */     double coeff = FastMath.sin(halfAngle) / norm;
/*      */     
/*  187 */     this.q0 = FastMath.cos(halfAngle);
/*  188 */     this.q1 = coeff * axis.getX();
/*  189 */     this.q2 = coeff * axis.getY();
/*  190 */     this.q3 = coeff * axis.getZ();
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
/*      */   public Rotation(double[][] m, double threshold) throws NotARotationMatrixException {
/*  228 */     if (m.length != 3 || (m[0]).length != 3 || (m[1]).length != 3 || (m[2]).length != 3)
/*      */     {
/*  230 */       throw new NotARotationMatrixException(LocalizedFormats.ROTATION_MATRIX_DIMENSIONS, new Object[] { Integer.valueOf(m.length), Integer.valueOf((m[0]).length) });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  236 */     double[][] ort = orthogonalizeMatrix(m, threshold);
/*      */ 
/*      */     
/*  239 */     double det = ort[0][0] * (ort[1][1] * ort[2][2] - ort[2][1] * ort[1][2]) - ort[1][0] * (ort[0][1] * ort[2][2] - ort[2][1] * ort[0][2]) + ort[2][0] * (ort[0][1] * ort[1][2] - ort[1][1] * ort[0][2]);
/*      */ 
/*      */     
/*  242 */     if (det < 0.0D) {
/*  243 */       throw new NotARotationMatrixException(LocalizedFormats.CLOSEST_ORTHOGONAL_MATRIX_HAS_NEGATIVE_DETERMINANT, new Object[] { Double.valueOf(det) });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  248 */     double[] quat = mat2quat(ort);
/*  249 */     this.q0 = quat[0];
/*  250 */     this.q1 = quat[1];
/*  251 */     this.q2 = quat[2];
/*  252 */     this.q3 = quat[3];
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
/*      */ 
/*      */ 
/*      */   
/*      */   public Rotation(Vector3D u1, Vector3D u2, Vector3D v1, Vector3D v2) throws MathArithmeticException {
/*  280 */     Vector3D u3 = u1.crossProduct(u2).normalize();
/*  281 */     u2 = u3.crossProduct(u1).normalize();
/*  282 */     u1 = u1.normalize();
/*      */ 
/*      */ 
/*      */     
/*  286 */     Vector3D v3 = v1.crossProduct(v2).normalize();
/*  287 */     v2 = v3.crossProduct(v1).normalize();
/*  288 */     v1 = v1.normalize();
/*      */ 
/*      */     
/*  291 */     double[][] m = { { MathArrays.linearCombination(u1.getX(), v1.getX(), u2.getX(), v2.getX(), u3.getX(), v3.getX()), MathArrays.linearCombination(u1.getY(), v1.getX(), u2.getY(), v2.getX(), u3.getY(), v3.getX()), MathArrays.linearCombination(u1.getZ(), v1.getX(), u2.getZ(), v2.getX(), u3.getZ(), v3.getX()) }, { MathArrays.linearCombination(u1.getX(), v1.getY(), u2.getX(), v2.getY(), u3.getX(), v3.getY()), MathArrays.linearCombination(u1.getY(), v1.getY(), u2.getY(), v2.getY(), u3.getY(), v3.getY()), MathArrays.linearCombination(u1.getZ(), v1.getY(), u2.getZ(), v2.getY(), u3.getZ(), v3.getY()) }, { MathArrays.linearCombination(u1.getX(), v1.getZ(), u2.getX(), v2.getZ(), u3.getX(), v3.getZ()), MathArrays.linearCombination(u1.getY(), v1.getZ(), u2.getY(), v2.getZ(), u3.getY(), v3.getZ()), MathArrays.linearCombination(u1.getZ(), v1.getZ(), u2.getZ(), v2.getZ(), u3.getZ(), v3.getZ()) } };
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
/*  309 */     double[] quat = mat2quat(m);
/*  310 */     this.q0 = quat[0];
/*  311 */     this.q1 = quat[1];
/*  312 */     this.q2 = quat[2];
/*  313 */     this.q3 = quat[3];
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
/*      */   public Rotation(Vector3D u, Vector3D v) throws MathArithmeticException {
/*  332 */     double normProduct = u.getNorm() * v.getNorm();
/*  333 */     if (normProduct == 0.0D) {
/*  334 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR, new Object[0]);
/*      */     }
/*      */     
/*  337 */     double dot = u.dotProduct(v);
/*      */     
/*  339 */     if (dot < -0.999999999999998D * normProduct) {
/*      */ 
/*      */       
/*  342 */       Vector3D w = u.orthogonal();
/*  343 */       this.q0 = 0.0D;
/*  344 */       this.q1 = -w.getX();
/*  345 */       this.q2 = -w.getY();
/*  346 */       this.q3 = -w.getZ();
/*      */     }
/*      */     else {
/*      */       
/*  350 */       this.q0 = FastMath.sqrt(0.5D * (1.0D + dot / normProduct));
/*  351 */       double coeff = 1.0D / 2.0D * this.q0 * normProduct;
/*  352 */       Vector3D q = v.crossProduct(u);
/*  353 */       this.q1 = coeff * q.getX();
/*  354 */       this.q2 = coeff * q.getY();
/*  355 */       this.q3 = coeff * q.getZ();
/*      */     } 
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
/*      */   @Deprecated
/*      */   public Rotation(RotationOrder order, double alpha1, double alpha2, double alpha3) {
/*  378 */     this(order, RotationConvention.VECTOR_OPERATOR, alpha1, alpha2, alpha3);
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
/*      */ 
/*      */   
/*      */   public Rotation(RotationOrder order, RotationConvention convention, double alpha1, double alpha2, double alpha3) {
/*  405 */     Rotation r1 = new Rotation(order.getA1(), alpha1, convention);
/*  406 */     Rotation r2 = new Rotation(order.getA2(), alpha2, convention);
/*  407 */     Rotation r3 = new Rotation(order.getA3(), alpha3, convention);
/*  408 */     Rotation composed = r1.compose(r2.compose(r3, convention), convention);
/*  409 */     this.q0 = composed.q0;
/*  410 */     this.q1 = composed.q1;
/*  411 */     this.q2 = composed.q2;
/*  412 */     this.q3 = composed.q3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double[] mat2quat(double[][] ort) {
/*  421 */     double[] quat = new double[4];
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
/*  434 */     double s = ort[0][0] + ort[1][1] + ort[2][2];
/*  435 */     if (s > -0.19D) {
/*      */       
/*  437 */       quat[0] = 0.5D * FastMath.sqrt(s + 1.0D);
/*  438 */       double inv = 0.25D / quat[0];
/*  439 */       quat[1] = inv * (ort[1][2] - ort[2][1]);
/*  440 */       quat[2] = inv * (ort[2][0] - ort[0][2]);
/*  441 */       quat[3] = inv * (ort[0][1] - ort[1][0]);
/*      */     } else {
/*  443 */       s = ort[0][0] - ort[1][1] - ort[2][2];
/*  444 */       if (s > -0.19D) {
/*      */         
/*  446 */         quat[1] = 0.5D * FastMath.sqrt(s + 1.0D);
/*  447 */         double inv = 0.25D / quat[1];
/*  448 */         quat[0] = inv * (ort[1][2] - ort[2][1]);
/*  449 */         quat[2] = inv * (ort[0][1] + ort[1][0]);
/*  450 */         quat[3] = inv * (ort[0][2] + ort[2][0]);
/*      */       } else {
/*  452 */         s = ort[1][1] - ort[0][0] - ort[2][2];
/*  453 */         if (s > -0.19D) {
/*      */           
/*  455 */           quat[2] = 0.5D * FastMath.sqrt(s + 1.0D);
/*  456 */           double inv = 0.25D / quat[2];
/*  457 */           quat[0] = inv * (ort[2][0] - ort[0][2]);
/*  458 */           quat[1] = inv * (ort[0][1] + ort[1][0]);
/*  459 */           quat[3] = inv * (ort[2][1] + ort[1][2]);
/*      */         } else {
/*      */           
/*  462 */           s = ort[2][2] - ort[0][0] - ort[1][1];
/*  463 */           quat[3] = 0.5D * FastMath.sqrt(s + 1.0D);
/*  464 */           double inv = 0.25D / quat[3];
/*  465 */           quat[0] = inv * (ort[0][1] - ort[1][0]);
/*  466 */           quat[1] = inv * (ort[0][2] + ort[2][0]);
/*  467 */           quat[2] = inv * (ort[2][1] + ort[1][2]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  472 */     return quat;
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
/*      */   public Rotation revert() {
/*  484 */     return new Rotation(-this.q0, this.q1, this.q2, this.q3, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getQ0() {
/*  491 */     return this.q0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getQ1() {
/*  498 */     return this.q1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getQ2() {
/*  505 */     return this.q2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getQ3() {
/*  512 */     return this.q3;
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
/*      */   @Deprecated
/*      */   public Vector3D getAxis() {
/*  526 */     return getAxis(RotationConvention.VECTOR_OPERATOR);
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
/*      */   public Vector3D getAxis(RotationConvention convention) {
/*  541 */     double squaredSine = this.q1 * this.q1 + this.q2 * this.q2 + this.q3 * this.q3;
/*  542 */     if (squaredSine == 0.0D) {
/*  543 */       return (convention == RotationConvention.VECTOR_OPERATOR) ? Vector3D.PLUS_I : Vector3D.MINUS_I;
/*      */     }
/*  545 */     double sgn = (convention == RotationConvention.VECTOR_OPERATOR) ? 1.0D : -1.0D;
/*  546 */     if (this.q0 < 0.0D) {
/*  547 */       double d = sgn / FastMath.sqrt(squaredSine);
/*  548 */       return new Vector3D(this.q1 * d, this.q2 * d, this.q3 * d);
/*      */     } 
/*  550 */     double inverse = -sgn / FastMath.sqrt(squaredSine);
/*  551 */     return new Vector3D(this.q1 * inverse, this.q2 * inverse, this.q3 * inverse);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getAngle() {
/*  560 */     if (this.q0 < -0.1D || this.q0 > 0.1D)
/*  561 */       return 2.0D * FastMath.asin(FastMath.sqrt(this.q1 * this.q1 + this.q2 * this.q2 + this.q3 * this.q3)); 
/*  562 */     if (this.q0 < 0.0D) {
/*  563 */       return 2.0D * FastMath.acos(-this.q0);
/*      */     }
/*  565 */     return 2.0D * FastMath.acos(this.q0);
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
/*      */   @Deprecated
/*      */   public double[] getAngles(RotationOrder order) throws CardanEulerSingularityException {
/*  585 */     return getAngles(order, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public double[] getAngles(RotationOrder order, RotationConvention convention) throws CardanEulerSingularityException {
/*  628 */     if (convention == RotationConvention.VECTOR_OPERATOR) {
/*  629 */       if (order == RotationOrder.XYZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  636 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_K);
/*  637 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_I);
/*  638 */         if (vector3D4.getZ() < -0.9999999999D || vector3D4.getZ() > 0.9999999999D) {
/*  639 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  641 */         return new double[] { FastMath.atan2(-vector3D3.getY(), vector3D3.getZ()), FastMath.asin(vector3D4.getZ()), FastMath.atan2(-vector3D4.getY(), vector3D4.getX()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  647 */       if (order == RotationOrder.XZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  654 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_J);
/*  655 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_I);
/*  656 */         if (vector3D4.getY() < -0.9999999999D || vector3D4.getY() > 0.9999999999D) {
/*  657 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  659 */         return new double[] { FastMath.atan2(vector3D3.getZ(), vector3D3.getY()), -FastMath.asin(vector3D4.getY()), FastMath.atan2(vector3D4.getZ(), vector3D4.getX()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  665 */       if (order == RotationOrder.YXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  672 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_K);
/*  673 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_J);
/*  674 */         if (vector3D4.getZ() < -0.9999999999D || vector3D4.getZ() > 0.9999999999D) {
/*  675 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  677 */         return new double[] { FastMath.atan2(vector3D3.getX(), vector3D3.getZ()), -FastMath.asin(vector3D4.getZ()), FastMath.atan2(vector3D4.getX(), vector3D4.getY()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  683 */       if (order == RotationOrder.YZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  690 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_I);
/*  691 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_J);
/*  692 */         if (vector3D4.getX() < -0.9999999999D || vector3D4.getX() > 0.9999999999D) {
/*  693 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  695 */         return new double[] { FastMath.atan2(-vector3D3.getZ(), vector3D3.getX()), FastMath.asin(vector3D4.getX()), FastMath.atan2(-vector3D4.getZ(), vector3D4.getY()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  701 */       if (order == RotationOrder.ZXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  708 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_J);
/*  709 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_K);
/*  710 */         if (vector3D4.getY() < -0.9999999999D || vector3D4.getY() > 0.9999999999D) {
/*  711 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  713 */         return new double[] { FastMath.atan2(-vector3D3.getX(), vector3D3.getY()), FastMath.asin(vector3D4.getY()), FastMath.atan2(-vector3D4.getX(), vector3D4.getZ()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  719 */       if (order == RotationOrder.ZYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  726 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_I);
/*  727 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_K);
/*  728 */         if (vector3D4.getX() < -0.9999999999D || vector3D4.getX() > 0.9999999999D) {
/*  729 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  731 */         return new double[] { FastMath.atan2(vector3D3.getY(), vector3D3.getX()), -FastMath.asin(vector3D4.getX()), FastMath.atan2(vector3D4.getY(), vector3D4.getZ()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  737 */       if (order == RotationOrder.XYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  744 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_I);
/*  745 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_I);
/*  746 */         if (vector3D4.getX() < -0.9999999999D || vector3D4.getX() > 0.9999999999D) {
/*  747 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  749 */         return new double[] { FastMath.atan2(vector3D3.getY(), -vector3D3.getZ()), FastMath.acos(vector3D4.getX()), FastMath.atan2(vector3D4.getY(), vector3D4.getZ()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  755 */       if (order == RotationOrder.XZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  762 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_I);
/*  763 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_I);
/*  764 */         if (vector3D4.getX() < -0.9999999999D || vector3D4.getX() > 0.9999999999D) {
/*  765 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  767 */         return new double[] { FastMath.atan2(vector3D3.getZ(), vector3D3.getY()), FastMath.acos(vector3D4.getX()), FastMath.atan2(vector3D4.getZ(), -vector3D4.getY()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  773 */       if (order == RotationOrder.YXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  780 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_J);
/*  781 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_J);
/*  782 */         if (vector3D4.getY() < -0.9999999999D || vector3D4.getY() > 0.9999999999D) {
/*  783 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  785 */         return new double[] { FastMath.atan2(vector3D3.getX(), vector3D3.getZ()), FastMath.acos(vector3D4.getY()), FastMath.atan2(vector3D4.getX(), -vector3D4.getZ()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  791 */       if (order == RotationOrder.YZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  798 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_J);
/*  799 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_J);
/*  800 */         if (vector3D4.getY() < -0.9999999999D || vector3D4.getY() > 0.9999999999D) {
/*  801 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  803 */         return new double[] { FastMath.atan2(vector3D3.getZ(), -vector3D3.getX()), FastMath.acos(vector3D4.getY()), FastMath.atan2(vector3D4.getZ(), vector3D4.getX()) };
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  809 */       if (order == RotationOrder.ZXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  816 */         Vector3D vector3D3 = applyTo(Vector3D.PLUS_K);
/*  817 */         Vector3D vector3D4 = applyInverseTo(Vector3D.PLUS_K);
/*  818 */         if (vector3D4.getZ() < -0.9999999999D || vector3D4.getZ() > 0.9999999999D) {
/*  819 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  821 */         return new double[] { FastMath.atan2(vector3D3.getX(), -vector3D3.getY()), FastMath.acos(vector3D4.getZ()), FastMath.atan2(vector3D4.getX(), vector3D4.getY()) };
/*      */       } 
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
/*  834 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_K);
/*  835 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_K);
/*  836 */       if (vector3D2.getZ() < -0.9999999999D || vector3D2.getZ() > 0.9999999999D) {
/*  837 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  839 */       return new double[] { FastMath.atan2(vector3D1.getY(), vector3D1.getX()), FastMath.acos(vector3D2.getZ()), FastMath.atan2(vector3D2.getY(), -vector3D2.getX()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  847 */     if (order == RotationOrder.XYZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  854 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_I);
/*  855 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_K);
/*  856 */       if (vector3D2.getX() < -0.9999999999D || vector3D2.getX() > 0.9999999999D) {
/*  857 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  859 */       return new double[] { FastMath.atan2(-vector3D2.getY(), vector3D2.getZ()), FastMath.asin(vector3D2.getX()), FastMath.atan2(-vector3D1.getY(), vector3D1.getX()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  865 */     if (order == RotationOrder.XZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  872 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_I);
/*  873 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_J);
/*  874 */       if (vector3D2.getX() < -0.9999999999D || vector3D2.getX() > 0.9999999999D) {
/*  875 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  877 */       return new double[] { FastMath.atan2(vector3D2.getZ(), vector3D2.getY()), -FastMath.asin(vector3D2.getX()), FastMath.atan2(vector3D1.getZ(), vector3D1.getX()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  883 */     if (order == RotationOrder.YXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  890 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_J);
/*  891 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_K);
/*  892 */       if (vector3D2.getY() < -0.9999999999D || vector3D2.getY() > 0.9999999999D) {
/*  893 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  895 */       return new double[] { FastMath.atan2(vector3D2.getX(), vector3D2.getZ()), -FastMath.asin(vector3D2.getY()), FastMath.atan2(vector3D1.getX(), vector3D1.getY()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  901 */     if (order == RotationOrder.YZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  908 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_J);
/*  909 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  910 */       if (vector3D2.getY() < -0.9999999999D || vector3D2.getY() > 0.9999999999D) {
/*  911 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  913 */       return new double[] { FastMath.atan2(-vector3D2.getZ(), vector3D2.getX()), FastMath.asin(vector3D2.getY()), FastMath.atan2(-vector3D1.getZ(), vector3D1.getY()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  919 */     if (order == RotationOrder.ZXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  926 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_K);
/*  927 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_J);
/*  928 */       if (vector3D2.getZ() < -0.9999999999D || vector3D2.getZ() > 0.9999999999D) {
/*  929 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  931 */       return new double[] { FastMath.atan2(-vector3D2.getX(), vector3D2.getY()), FastMath.asin(vector3D2.getZ()), FastMath.atan2(-vector3D1.getX(), vector3D1.getZ()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  937 */     if (order == RotationOrder.ZYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  944 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_K);
/*  945 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  946 */       if (vector3D2.getZ() < -0.9999999999D || vector3D2.getZ() > 0.9999999999D) {
/*  947 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  949 */       return new double[] { FastMath.atan2(vector3D2.getY(), vector3D2.getX()), -FastMath.asin(vector3D2.getZ()), FastMath.atan2(vector3D1.getY(), vector3D1.getZ()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  955 */     if (order == RotationOrder.XYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  962 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_I);
/*  963 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  964 */       if (vector3D2.getX() < -0.9999999999D || vector3D2.getX() > 0.9999999999D) {
/*  965 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  967 */       return new double[] { FastMath.atan2(vector3D2.getY(), -vector3D2.getZ()), FastMath.acos(vector3D2.getX()), FastMath.atan2(vector3D1.getY(), vector3D1.getZ()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  973 */     if (order == RotationOrder.XZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  980 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_I);
/*  981 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  982 */       if (vector3D2.getX() < -0.9999999999D || vector3D2.getX() > 0.9999999999D) {
/*  983 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  985 */       return new double[] { FastMath.atan2(vector3D2.getZ(), vector3D2.getY()), FastMath.acos(vector3D2.getX()), FastMath.atan2(vector3D1.getZ(), -vector3D1.getY()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  991 */     if (order == RotationOrder.YXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  998 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_J);
/*  999 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_J);
/* 1000 */       if (vector3D2.getY() < -0.9999999999D || vector3D2.getY() > 0.9999999999D) {
/* 1001 */         throw new CardanEulerSingularityException(false);
/*      */       }
/* 1003 */       return new double[] { FastMath.atan2(vector3D2.getX(), vector3D2.getZ()), FastMath.acos(vector3D2.getY()), FastMath.atan2(vector3D1.getX(), -vector3D1.getZ()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1009 */     if (order == RotationOrder.YZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1016 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_J);
/* 1017 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_J);
/* 1018 */       if (vector3D2.getY() < -0.9999999999D || vector3D2.getY() > 0.9999999999D) {
/* 1019 */         throw new CardanEulerSingularityException(false);
/*      */       }
/* 1021 */       return new double[] { FastMath.atan2(vector3D2.getZ(), -vector3D2.getX()), FastMath.acos(vector3D2.getY()), FastMath.atan2(vector3D1.getZ(), vector3D1.getX()) };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1027 */     if (order == RotationOrder.ZXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1034 */       Vector3D vector3D1 = applyTo(Vector3D.PLUS_K);
/* 1035 */       Vector3D vector3D2 = applyInverseTo(Vector3D.PLUS_K);
/* 1036 */       if (vector3D2.getZ() < -0.9999999999D || vector3D2.getZ() > 0.9999999999D) {
/* 1037 */         throw new CardanEulerSingularityException(false);
/*      */       }
/* 1039 */       return new double[] { FastMath.atan2(vector3D2.getX(), -vector3D2.getY()), FastMath.acos(vector3D2.getZ()), FastMath.atan2(vector3D1.getX(), vector3D1.getY()) };
/*      */     } 
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
/* 1052 */     Vector3D v1 = applyTo(Vector3D.PLUS_K);
/* 1053 */     Vector3D v2 = applyInverseTo(Vector3D.PLUS_K);
/* 1054 */     if (v2.getZ() < -0.9999999999D || v2.getZ() > 0.9999999999D) {
/* 1055 */       throw new CardanEulerSingularityException(false);
/*      */     }
/* 1057 */     return new double[] { FastMath.atan2(v2.getY(), v2.getX()), FastMath.acos(v2.getZ()), FastMath.atan2(v1.getY(), -v1.getX()) };
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
/*      */   public double[][] getMatrix() {
/* 1074 */     double q0q0 = this.q0 * this.q0;
/* 1075 */     double q0q1 = this.q0 * this.q1;
/* 1076 */     double q0q2 = this.q0 * this.q2;
/* 1077 */     double q0q3 = this.q0 * this.q3;
/* 1078 */     double q1q1 = this.q1 * this.q1;
/* 1079 */     double q1q2 = this.q1 * this.q2;
/* 1080 */     double q1q3 = this.q1 * this.q3;
/* 1081 */     double q2q2 = this.q2 * this.q2;
/* 1082 */     double q2q3 = this.q2 * this.q3;
/* 1083 */     double q3q3 = this.q3 * this.q3;
/*      */ 
/*      */     
/* 1086 */     double[][] m = new double[3][];
/* 1087 */     m[0] = new double[3];
/* 1088 */     m[1] = new double[3];
/* 1089 */     m[2] = new double[3];
/*      */     
/* 1091 */     m[0][0] = 2.0D * (q0q0 + q1q1) - 1.0D;
/* 1092 */     m[1][0] = 2.0D * (q1q2 - q0q3);
/* 1093 */     m[2][0] = 2.0D * (q1q3 + q0q2);
/*      */     
/* 1095 */     m[0][1] = 2.0D * (q1q2 + q0q3);
/* 1096 */     m[1][1] = 2.0D * (q0q0 + q2q2) - 1.0D;
/* 1097 */     m[2][1] = 2.0D * (q2q3 - q0q1);
/*      */     
/* 1099 */     m[0][2] = 2.0D * (q1q3 - q0q2);
/* 1100 */     m[1][2] = 2.0D * (q2q3 + q0q1);
/* 1101 */     m[2][2] = 2.0D * (q0q0 + q3q3) - 1.0D;
/*      */     
/* 1103 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3D applyTo(Vector3D u) {
/* 1113 */     double x = u.getX();
/* 1114 */     double y = u.getY();
/* 1115 */     double z = u.getZ();
/*      */     
/* 1117 */     double s = this.q1 * x + this.q2 * y + this.q3 * z;
/*      */     
/* 1119 */     return new Vector3D(2.0D * (this.q0 * (x * this.q0 - this.q2 * z - this.q3 * y) + s * this.q1) - x, 2.0D * (this.q0 * (y * this.q0 - this.q3 * x - this.q1 * z) + s * this.q2) - y, 2.0D * (this.q0 * (z * this.q0 - this.q1 * y - this.q2 * x) + s * this.q3) - z);
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
/*      */   public void applyTo(double[] in, double[] out) {
/* 1132 */     double x = in[0];
/* 1133 */     double y = in[1];
/* 1134 */     double z = in[2];
/*      */     
/* 1136 */     double s = this.q1 * x + this.q2 * y + this.q3 * z;
/*      */     
/* 1138 */     out[0] = 2.0D * (this.q0 * (x * this.q0 - this.q2 * z - this.q3 * y) + s * this.q1) - x;
/* 1139 */     out[1] = 2.0D * (this.q0 * (y * this.q0 - this.q3 * x - this.q1 * z) + s * this.q2) - y;
/* 1140 */     out[2] = 2.0D * (this.q0 * (z * this.q0 - this.q1 * y - this.q2 * x) + s * this.q3) - z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vector3D applyInverseTo(Vector3D u) {
/* 1150 */     double x = u.getX();
/* 1151 */     double y = u.getY();
/* 1152 */     double z = u.getZ();
/*      */     
/* 1154 */     double s = this.q1 * x + this.q2 * y + this.q3 * z;
/* 1155 */     double m0 = -this.q0;
/*      */     
/* 1157 */     return new Vector3D(2.0D * (m0 * (x * m0 - this.q2 * z - this.q3 * y) + s * this.q1) - x, 2.0D * (m0 * (y * m0 - this.q3 * x - this.q1 * z) + s * this.q2) - y, 2.0D * (m0 * (z * m0 - this.q1 * y - this.q2 * x) + s * this.q3) - z);
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
/*      */   public void applyInverseTo(double[] in, double[] out) {
/* 1170 */     double x = in[0];
/* 1171 */     double y = in[1];
/* 1172 */     double z = in[2];
/*      */     
/* 1174 */     double s = this.q1 * x + this.q2 * y + this.q3 * z;
/* 1175 */     double m0 = -this.q0;
/*      */     
/* 1177 */     out[0] = 2.0D * (m0 * (x * m0 - this.q2 * z - this.q3 * y) + s * this.q1) - x;
/* 1178 */     out[1] = 2.0D * (m0 * (y * m0 - this.q3 * x - this.q1 * z) + s * this.q2) - y;
/* 1179 */     out[2] = 2.0D * (m0 * (z * m0 - this.q1 * y - this.q2 * x) + s * this.q3) - z;
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
/*      */   public Rotation applyTo(Rotation r) {
/* 1193 */     return compose(r, RotationConvention.VECTOR_OPERATOR);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public Rotation compose(Rotation r, RotationConvention convention) {
/* 1221 */     return (convention == RotationConvention.VECTOR_OPERATOR) ? composeInternal(r) : r.composeInternal(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rotation composeInternal(Rotation r) {
/* 1231 */     return new Rotation(r.q0 * this.q0 - r.q1 * this.q1 + r.q2 * this.q2 + r.q3 * this.q3, r.q1 * this.q0 + r.q0 * this.q1 + r.q2 * this.q3 - r.q3 * this.q2, r.q2 * this.q0 + r.q0 * this.q2 + r.q3 * this.q1 - r.q1 * this.q3, r.q3 * this.q0 + r.q0 * this.q3 + r.q1 * this.q2 - r.q2 * this.q1, false);
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
/*      */   public Rotation applyInverseTo(Rotation r) {
/* 1249 */     return composeInverse(r, RotationConvention.VECTOR_OPERATOR);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rotation composeInverse(Rotation r, RotationConvention convention) {
/* 1279 */     return (convention == RotationConvention.VECTOR_OPERATOR) ? composeInverseInternal(r) : r.composeInternal(revert());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rotation composeInverseInternal(Rotation r) {
/* 1290 */     return new Rotation(-r.q0 * this.q0 - r.q1 * this.q1 + r.q2 * this.q2 + r.q3 * this.q3, -r.q1 * this.q0 + r.q0 * this.q1 + r.q2 * this.q3 - r.q3 * this.q2, -r.q2 * this.q0 + r.q0 * this.q2 + r.q3 * this.q1 - r.q1 * this.q3, -r.q3 * this.q0 + r.q0 * this.q3 + r.q1 * this.q2 - r.q2 * this.q1, false);
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
/*      */   private double[][] orthogonalizeMatrix(double[][] m, double threshold) throws NotARotationMatrixException {
/* 1309 */     double[] m0 = m[0];
/* 1310 */     double[] m1 = m[1];
/* 1311 */     double[] m2 = m[2];
/* 1312 */     double x00 = m0[0];
/* 1313 */     double x01 = m0[1];
/* 1314 */     double x02 = m0[2];
/* 1315 */     double x10 = m1[0];
/* 1316 */     double x11 = m1[1];
/* 1317 */     double x12 = m1[2];
/* 1318 */     double x20 = m2[0];
/* 1319 */     double x21 = m2[1];
/* 1320 */     double x22 = m2[2];
/* 1321 */     double fn = 0.0D;
/*      */ 
/*      */     
/* 1324 */     double[][] o = new double[3][3];
/* 1325 */     double[] o0 = o[0];
/* 1326 */     double[] o1 = o[1];
/* 1327 */     double[] o2 = o[2];
/*      */ 
/*      */     
/* 1330 */     int i = 0;
/* 1331 */     while (++i < 11) {
/*      */ 
/*      */       
/* 1334 */       double mx00 = m0[0] * x00 + m1[0] * x10 + m2[0] * x20;
/* 1335 */       double mx10 = m0[1] * x00 + m1[1] * x10 + m2[1] * x20;
/* 1336 */       double mx20 = m0[2] * x00 + m1[2] * x10 + m2[2] * x20;
/* 1337 */       double mx01 = m0[0] * x01 + m1[0] * x11 + m2[0] * x21;
/* 1338 */       double mx11 = m0[1] * x01 + m1[1] * x11 + m2[1] * x21;
/* 1339 */       double mx21 = m0[2] * x01 + m1[2] * x11 + m2[2] * x21;
/* 1340 */       double mx02 = m0[0] * x02 + m1[0] * x12 + m2[0] * x22;
/* 1341 */       double mx12 = m0[1] * x02 + m1[1] * x12 + m2[1] * x22;
/* 1342 */       double mx22 = m0[2] * x02 + m1[2] * x12 + m2[2] * x22;
/*      */ 
/*      */       
/* 1345 */       o0[0] = x00 - 0.5D * (x00 * mx00 + x01 * mx10 + x02 * mx20 - m0[0]);
/* 1346 */       o0[1] = x01 - 0.5D * (x00 * mx01 + x01 * mx11 + x02 * mx21 - m0[1]);
/* 1347 */       o0[2] = x02 - 0.5D * (x00 * mx02 + x01 * mx12 + x02 * mx22 - m0[2]);
/* 1348 */       o1[0] = x10 - 0.5D * (x10 * mx00 + x11 * mx10 + x12 * mx20 - m1[0]);
/* 1349 */       o1[1] = x11 - 0.5D * (x10 * mx01 + x11 * mx11 + x12 * mx21 - m1[1]);
/* 1350 */       o1[2] = x12 - 0.5D * (x10 * mx02 + x11 * mx12 + x12 * mx22 - m1[2]);
/* 1351 */       o2[0] = x20 - 0.5D * (x20 * mx00 + x21 * mx10 + x22 * mx20 - m2[0]);
/* 1352 */       o2[1] = x21 - 0.5D * (x20 * mx01 + x21 * mx11 + x22 * mx21 - m2[1]);
/* 1353 */       o2[2] = x22 - 0.5D * (x20 * mx02 + x21 * mx12 + x22 * mx22 - m2[2]);
/*      */ 
/*      */       
/* 1356 */       double corr00 = o0[0] - m0[0];
/* 1357 */       double corr01 = o0[1] - m0[1];
/* 1358 */       double corr02 = o0[2] - m0[2];
/* 1359 */       double corr10 = o1[0] - m1[0];
/* 1360 */       double corr11 = o1[1] - m1[1];
/* 1361 */       double corr12 = o1[2] - m1[2];
/* 1362 */       double corr20 = o2[0] - m2[0];
/* 1363 */       double corr21 = o2[1] - m2[1];
/* 1364 */       double corr22 = o2[2] - m2[2];
/*      */ 
/*      */       
/* 1367 */       double fn1 = corr00 * corr00 + corr01 * corr01 + corr02 * corr02 + corr10 * corr10 + corr11 * corr11 + corr12 * corr12 + corr20 * corr20 + corr21 * corr21 + corr22 * corr22;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1372 */       if (FastMath.abs(fn1 - fn) <= threshold) {
/* 1373 */         return o;
/*      */       }
/*      */ 
/*      */       
/* 1377 */       x00 = o0[0];
/* 1378 */       x01 = o0[1];
/* 1379 */       x02 = o0[2];
/* 1380 */       x10 = o1[0];
/* 1381 */       x11 = o1[1];
/* 1382 */       x12 = o1[2];
/* 1383 */       x20 = o2[0];
/* 1384 */       x21 = o2[1];
/* 1385 */       x22 = o2[2];
/* 1386 */       fn = fn1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1391 */     throw new NotARotationMatrixException(LocalizedFormats.UNABLE_TO_ORTHOGONOLIZE_MATRIX, new Object[] { Integer.valueOf(i - 1) });
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double distance(Rotation r1, Rotation r2) {
/* 1421 */     return r1.composeInverseInternal(r2).getAngle();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/Rotation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */