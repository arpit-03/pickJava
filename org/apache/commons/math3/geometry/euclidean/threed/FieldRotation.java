/*      */ package org.apache.commons.math3.geometry.euclidean.threed;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import org.apache.commons.math3.Field;
/*      */ import org.apache.commons.math3.RealFieldElement;
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
/*      */ public class FieldRotation<T extends RealFieldElement<T>>
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 20130224L;
/*      */   private final T q0;
/*      */   private final T q1;
/*      */   private final T q2;
/*      */   private final T q3;
/*      */   
/*      */   public FieldRotation(T q0, T q1, T q2, T q3, boolean needsNormalization) {
/*   77 */     if (needsNormalization) {
/*      */       
/*   79 */       RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)q0.multiply(q0)).add(q1.multiply(q1))).add(q2.multiply(q2))).add(q3.multiply(q3))).sqrt()).reciprocal();
/*      */       
/*   81 */       this.q0 = (T)realFieldElement.multiply(q0);
/*   82 */       this.q1 = (T)realFieldElement.multiply(q1);
/*   83 */       this.q2 = (T)realFieldElement.multiply(q2);
/*   84 */       this.q3 = (T)realFieldElement.multiply(q3);
/*      */     } else {
/*   86 */       this.q0 = q0;
/*   87 */       this.q1 = q1;
/*   88 */       this.q2 = q2;
/*   89 */       this.q3 = q3;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public FieldRotation(FieldVector3D<T> axis, T angle) throws MathIllegalArgumentException {
/*  120 */     this(axis, angle, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public FieldRotation(FieldVector3D<T> axis, T angle, RotationConvention convention) throws MathIllegalArgumentException {
/*  149 */     T norm = axis.getNorm();
/*  150 */     if (norm.getReal() == 0.0D) {
/*  151 */       throw new MathIllegalArgumentException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_AXIS, new Object[0]);
/*      */     }
/*      */     
/*  154 */     RealFieldElement realFieldElement1 = (RealFieldElement)angle.multiply((convention == RotationConvention.VECTOR_OPERATOR) ? -0.5D : 0.5D);
/*  155 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)realFieldElement1.sin()).divide(norm);
/*      */     
/*  157 */     this.q0 = (T)realFieldElement1.cos();
/*  158 */     this.q1 = (T)realFieldElement2.multiply(axis.getX());
/*  159 */     this.q2 = (T)realFieldElement2.multiply(axis.getY());
/*  160 */     this.q3 = (T)realFieldElement2.multiply(axis.getZ());
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
/*      */   public FieldRotation(T[][] m, double threshold) throws NotARotationMatrixException {
/*  198 */     if (m.length != 3 || (m[0]).length != 3 || (m[1]).length != 3 || (m[2]).length != 3)
/*      */     {
/*  200 */       throw new NotARotationMatrixException(LocalizedFormats.ROTATION_MATRIX_DIMENSIONS, new Object[] { Integer.valueOf(m.length), Integer.valueOf((m[0]).length) });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  206 */     T[][] ort = orthogonalizeMatrix(m, threshold);
/*      */ 
/*      */     
/*  209 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)ort[1][1].multiply(ort[2][2])).subtract(ort[2][1].multiply(ort[1][2]));
/*  210 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)ort[0][1].multiply(ort[2][2])).subtract(ort[2][1].multiply(ort[0][2]));
/*  211 */     RealFieldElement realFieldElement3 = (RealFieldElement)((RealFieldElement)ort[0][1].multiply(ort[1][2])).subtract(ort[1][1].multiply(ort[0][2]));
/*  212 */     RealFieldElement realFieldElement4 = (RealFieldElement)((RealFieldElement)((RealFieldElement)ort[0][0].multiply(realFieldElement1)).subtract(ort[1][0].multiply(realFieldElement2))).add(ort[2][0].multiply(realFieldElement3));
/*      */     
/*  214 */     if (realFieldElement4.getReal() < 0.0D) {
/*  215 */       throw new NotARotationMatrixException(LocalizedFormats.CLOSEST_ORTHOGONAL_MATRIX_HAS_NEGATIVE_DETERMINANT, new Object[] { realFieldElement4 });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  220 */     T[] quat = mat2quat(ort);
/*  221 */     this.q0 = quat[0];
/*  222 */     this.q1 = quat[1];
/*  223 */     this.q2 = quat[2];
/*  224 */     this.q3 = quat[3];
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
/*      */   public FieldRotation(FieldVector3D<T> u1, FieldVector3D<T> u2, FieldVector3D<T> v1, FieldVector3D<T> v2) throws MathArithmeticException {
/*  252 */     FieldVector3D<T> u3 = FieldVector3D.<T>crossProduct(u1, u2).normalize();
/*  253 */     u2 = FieldVector3D.<T>crossProduct(u3, u1).normalize();
/*  254 */     u1 = u1.normalize();
/*      */ 
/*      */ 
/*      */     
/*  258 */     FieldVector3D<T> v3 = FieldVector3D.<T>crossProduct(v1, v2).normalize();
/*  259 */     v2 = FieldVector3D.<T>crossProduct(v3, v1).normalize();
/*  260 */     v1 = v1.normalize();
/*      */ 
/*      */     
/*  263 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(u1.getX().getField(), 3, 3);
/*  264 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getX().multiply(v1.getX())).add(u2.getX().multiply(v2.getX()))).add(u3.getX().multiply(v3.getX()));
/*  265 */     arrayOfRealFieldElement[0][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getY().multiply(v1.getX())).add(u2.getY().multiply(v2.getX()))).add(u3.getY().multiply(v3.getX()));
/*  266 */     arrayOfRealFieldElement[0][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getZ().multiply(v1.getX())).add(u2.getZ().multiply(v2.getX()))).add(u3.getZ().multiply(v3.getX()));
/*  267 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getX().multiply(v1.getY())).add(u2.getX().multiply(v2.getY()))).add(u3.getX().multiply(v3.getY()));
/*  268 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getY().multiply(v1.getY())).add(u2.getY().multiply(v2.getY()))).add(u3.getY().multiply(v3.getY()));
/*  269 */     arrayOfRealFieldElement[1][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getZ().multiply(v1.getY())).add(u2.getZ().multiply(v2.getY()))).add(u3.getZ().multiply(v3.getY()));
/*  270 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getX().multiply(v1.getZ())).add(u2.getX().multiply(v2.getZ()))).add(u3.getX().multiply(v3.getZ()));
/*  271 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getY().multiply(v1.getZ())).add(u2.getY().multiply(v2.getZ()))).add(u3.getY().multiply(v3.getZ()));
/*  272 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)u1.getZ().multiply(v1.getZ())).add(u2.getZ().multiply(v2.getZ()))).add(u3.getZ().multiply(v3.getZ()));
/*      */     
/*  274 */     T[] quat = mat2quat((T[][])arrayOfRealFieldElement);
/*  275 */     this.q0 = quat[0];
/*  276 */     this.q1 = quat[1];
/*  277 */     this.q2 = quat[2];
/*  278 */     this.q3 = quat[3];
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
/*      */   public FieldRotation(FieldVector3D<T> u, FieldVector3D<T> v) throws MathArithmeticException {
/*  297 */     RealFieldElement realFieldElement = (RealFieldElement)u.getNorm().multiply(v.getNorm());
/*  298 */     if (realFieldElement.getReal() == 0.0D) {
/*  299 */       throw new MathArithmeticException(LocalizedFormats.ZERO_NORM_FOR_ROTATION_DEFINING_VECTOR, new Object[0]);
/*      */     }
/*      */     
/*  302 */     T dot = FieldVector3D.dotProduct(u, v);
/*      */     
/*  304 */     if (dot.getReal() < -0.999999999999998D * realFieldElement.getReal()) {
/*      */ 
/*      */       
/*  307 */       FieldVector3D<T> w = u.orthogonal();
/*  308 */       this.q0 = (T)realFieldElement.getField().getZero();
/*  309 */       this.q1 = (T)w.getX().negate();
/*  310 */       this.q2 = (T)w.getY().negate();
/*  311 */       this.q3 = (T)w.getZ().negate();
/*      */     }
/*      */     else {
/*      */       
/*  315 */       this.q0 = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)dot.divide(realFieldElement)).add(1.0D)).multiply(0.5D)).sqrt();
/*  316 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(realFieldElement)).multiply(2.0D)).reciprocal();
/*  317 */       FieldVector3D<T> q = FieldVector3D.crossProduct(v, u);
/*  318 */       this.q1 = (T)realFieldElement1.multiply(q.getX());
/*  319 */       this.q2 = (T)realFieldElement1.multiply(q.getY());
/*  320 */       this.q3 = (T)realFieldElement1.multiply(q.getZ());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public FieldRotation(RotationOrder order, T alpha1, T alpha2, T alpha3) {
/*  349 */     this(order, RotationConvention.VECTOR_OPERATOR, alpha1, alpha2, alpha3);
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
/*      */   public FieldRotation(RotationOrder order, RotationConvention convention, T alpha1, T alpha2, T alpha3) {
/*  376 */     RealFieldElement realFieldElement = (RealFieldElement)alpha1.getField().getOne();
/*  377 */     FieldRotation<T> r1 = new FieldRotation(new FieldVector3D<T>((T)realFieldElement, order.getA1()), alpha1, convention);
/*  378 */     FieldRotation<T> r2 = new FieldRotation(new FieldVector3D<T>((T)realFieldElement, order.getA2()), alpha2, convention);
/*  379 */     FieldRotation<T> r3 = new FieldRotation(new FieldVector3D<T>((T)realFieldElement, order.getA3()), alpha3, convention);
/*  380 */     FieldRotation<T> composed = r1.compose(r2.compose(r3, convention), convention);
/*  381 */     this.q0 = composed.q0;
/*  382 */     this.q1 = composed.q1;
/*  383 */     this.q2 = composed.q2;
/*  384 */     this.q3 = composed.q3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private T[] mat2quat(T[][] ort) {
/*  393 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(ort[0][0].getField(), 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  406 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)ort[0][0].add(ort[1][1])).add(ort[2][2]);
/*  407 */     if (realFieldElement.getReal() > -0.19D) {
/*      */       
/*  409 */       arrayOfRealFieldElement[0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.add(1.0D)).sqrt()).multiply(0.5D);
/*  410 */       RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)arrayOfRealFieldElement[0].reciprocal()).multiply(0.25D);
/*  411 */       arrayOfRealFieldElement[1] = (RealFieldElement)realFieldElement1.multiply(ort[1][2].subtract(ort[2][1]));
/*  412 */       arrayOfRealFieldElement[2] = (RealFieldElement)realFieldElement1.multiply(ort[2][0].subtract(ort[0][2]));
/*  413 */       arrayOfRealFieldElement[3] = (RealFieldElement)realFieldElement1.multiply(ort[0][1].subtract(ort[1][0]));
/*      */     } else {
/*  415 */       realFieldElement = (RealFieldElement)((RealFieldElement)ort[0][0].subtract(ort[1][1])).subtract(ort[2][2]);
/*  416 */       if (realFieldElement.getReal() > -0.19D) {
/*      */         
/*  418 */         arrayOfRealFieldElement[1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.add(1.0D)).sqrt()).multiply(0.5D);
/*  419 */         RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)arrayOfRealFieldElement[1].reciprocal()).multiply(0.25D);
/*  420 */         arrayOfRealFieldElement[0] = (RealFieldElement)realFieldElement1.multiply(ort[1][2].subtract(ort[2][1]));
/*  421 */         arrayOfRealFieldElement[2] = (RealFieldElement)realFieldElement1.multiply(ort[0][1].add(ort[1][0]));
/*  422 */         arrayOfRealFieldElement[3] = (RealFieldElement)realFieldElement1.multiply(ort[0][2].add(ort[2][0]));
/*      */       } else {
/*  424 */         realFieldElement = (RealFieldElement)((RealFieldElement)ort[1][1].subtract(ort[0][0])).subtract(ort[2][2]);
/*  425 */         if (realFieldElement.getReal() > -0.19D) {
/*      */           
/*  427 */           arrayOfRealFieldElement[2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.add(1.0D)).sqrt()).multiply(0.5D);
/*  428 */           RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)arrayOfRealFieldElement[2].reciprocal()).multiply(0.25D);
/*  429 */           arrayOfRealFieldElement[0] = (RealFieldElement)realFieldElement1.multiply(ort[2][0].subtract(ort[0][2]));
/*  430 */           arrayOfRealFieldElement[1] = (RealFieldElement)realFieldElement1.multiply(ort[0][1].add(ort[1][0]));
/*  431 */           arrayOfRealFieldElement[3] = (RealFieldElement)realFieldElement1.multiply(ort[2][1].add(ort[1][2]));
/*      */         } else {
/*      */           
/*  434 */           realFieldElement = (RealFieldElement)((RealFieldElement)ort[2][2].subtract(ort[0][0])).subtract(ort[1][1]);
/*  435 */           arrayOfRealFieldElement[3] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement.add(1.0D)).sqrt()).multiply(0.5D);
/*  436 */           RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)arrayOfRealFieldElement[3].reciprocal()).multiply(0.25D);
/*  437 */           arrayOfRealFieldElement[0] = (RealFieldElement)realFieldElement1.multiply(ort[0][1].subtract(ort[1][0]));
/*  438 */           arrayOfRealFieldElement[1] = (RealFieldElement)realFieldElement1.multiply(ort[0][2].add(ort[2][0]));
/*  439 */           arrayOfRealFieldElement[2] = (RealFieldElement)realFieldElement1.multiply(ort[2][1].add(ort[1][2]));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  444 */     return (T[])arrayOfRealFieldElement;
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
/*      */   public FieldRotation<T> revert() {
/*  456 */     return new FieldRotation((T)this.q0.negate(), this.q1, this.q2, this.q3, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getQ0() {
/*  463 */     return this.q0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getQ1() {
/*  470 */     return this.q1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getQ2() {
/*  477 */     return this.q2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getQ3() {
/*  484 */     return this.q3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public FieldVector3D<T> getAxis() {
/*  494 */     return getAxis(RotationConvention.VECTOR_OPERATOR);
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
/*      */   public FieldVector3D<T> getAxis(RotationConvention convention) {
/*  509 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(this.q1)).add(this.q2.multiply(this.q2))).add(this.q3.multiply(this.q3));
/*  510 */     if (realFieldElement1.getReal() == 0.0D) {
/*  511 */       Field<T> field = realFieldElement1.getField();
/*  512 */       return new FieldVector3D<T>((convention == RotationConvention.VECTOR_OPERATOR) ? (T)field.getOne() : (T)((RealFieldElement)field.getOne()).negate(), (T)field.getZero(), (T)field.getZero());
/*      */     } 
/*      */ 
/*      */     
/*  516 */     double sgn = (convention == RotationConvention.VECTOR_OPERATOR) ? 1.0D : -1.0D;
/*  517 */     if (this.q0.getReal() < 0.0D) {
/*  518 */       RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.sqrt()).reciprocal()).multiply(sgn);
/*  519 */       return new FieldVector3D<T>((T)this.q1.multiply(realFieldElement), (T)this.q2.multiply(realFieldElement), (T)this.q3.multiply(realFieldElement));
/*      */     } 
/*  521 */     RealFieldElement realFieldElement2 = (RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.sqrt()).reciprocal()).negate()).multiply(sgn);
/*  522 */     return new FieldVector3D<T>((T)this.q1.multiply(realFieldElement2), (T)this.q2.multiply(realFieldElement2), (T)this.q3.multiply(realFieldElement2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T getAngle() {
/*  531 */     if (this.q0.getReal() < -0.1D || this.q0.getReal() > 0.1D)
/*  532 */       return (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(this.q1)).add(this.q2.multiply(this.q2))).add(this.q3.multiply(this.q3))).sqrt()).asin()).multiply(2); 
/*  533 */     if (this.q0.getReal() < 0.0D) {
/*  534 */       return (T)((RealFieldElement)((RealFieldElement)this.q0.negate()).acos()).multiply(2);
/*      */     }
/*  536 */     return (T)((RealFieldElement)this.q0.acos()).multiply(2);
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
/*      */   @Deprecated
/*      */   public T[] getAngles(RotationOrder order) throws CardanEulerSingularityException {
/*  578 */     return getAngles(order, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public T[] getAngles(RotationOrder order, RotationConvention convention) throws CardanEulerSingularityException {
/*  621 */     if (convention == RotationConvention.VECTOR_OPERATOR) {
/*  622 */       if (order == RotationOrder.XYZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  629 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 0.0D, 1.0D));
/*  630 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(1.0D, 0.0D, 0.0D));
/*  631 */         if (fieldVector3D4.getZ().getReal() < -0.9999999999D || fieldVector3D4.getZ().getReal() > 0.9999999999D) {
/*  632 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  634 */         return buildArray((T)((RealFieldElement)fieldVector3D3.getY().negate()).atan2(fieldVector3D3.getZ()), (T)fieldVector3D4.getZ().asin(), (T)((RealFieldElement)fieldVector3D4.getY().negate()).atan2(fieldVector3D4.getX()));
/*      */       } 
/*      */ 
/*      */       
/*  638 */       if (order == RotationOrder.XZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  645 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 1.0D, 0.0D));
/*  646 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(1.0D, 0.0D, 0.0D));
/*  647 */         if (fieldVector3D4.getY().getReal() < -0.9999999999D || fieldVector3D4.getY().getReal() > 0.9999999999D) {
/*  648 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  650 */         return buildArray((T)fieldVector3D3.getZ().atan2(fieldVector3D3.getY()), (T)((RealFieldElement)fieldVector3D4.getY().asin()).negate(), (T)fieldVector3D4.getZ().atan2(fieldVector3D4.getX()));
/*      */       } 
/*      */ 
/*      */       
/*  654 */       if (order == RotationOrder.YXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  661 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 0.0D, 1.0D));
/*  662 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 1.0D, 0.0D));
/*  663 */         if (fieldVector3D4.getZ().getReal() < -0.9999999999D || fieldVector3D4.getZ().getReal() > 0.9999999999D) {
/*  664 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  666 */         return buildArray((T)fieldVector3D3.getX().atan2(fieldVector3D3.getZ()), (T)((RealFieldElement)fieldVector3D4.getZ().asin()).negate(), (T)fieldVector3D4.getX().atan2(fieldVector3D4.getY()));
/*      */       } 
/*      */ 
/*      */       
/*  670 */       if (order == RotationOrder.YZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  677 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(1.0D, 0.0D, 0.0D));
/*  678 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 1.0D, 0.0D));
/*  679 */         if (fieldVector3D4.getX().getReal() < -0.9999999999D || fieldVector3D4.getX().getReal() > 0.9999999999D) {
/*  680 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  682 */         return buildArray((T)((RealFieldElement)fieldVector3D3.getZ().negate()).atan2(fieldVector3D3.getX()), (T)fieldVector3D4.getX().asin(), (T)((RealFieldElement)fieldVector3D4.getZ().negate()).atan2(fieldVector3D4.getY()));
/*      */       } 
/*      */ 
/*      */       
/*  686 */       if (order == RotationOrder.ZXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  693 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 1.0D, 0.0D));
/*  694 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 0.0D, 1.0D));
/*  695 */         if (fieldVector3D4.getY().getReal() < -0.9999999999D || fieldVector3D4.getY().getReal() > 0.9999999999D) {
/*  696 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  698 */         return buildArray((T)((RealFieldElement)fieldVector3D3.getX().negate()).atan2(fieldVector3D3.getY()), (T)fieldVector3D4.getY().asin(), (T)((RealFieldElement)fieldVector3D4.getX().negate()).atan2(fieldVector3D4.getZ()));
/*      */       } 
/*      */ 
/*      */       
/*  702 */       if (order == RotationOrder.ZYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  709 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(1.0D, 0.0D, 0.0D));
/*  710 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 0.0D, 1.0D));
/*  711 */         if (fieldVector3D4.getX().getReal() < -0.9999999999D || fieldVector3D4.getX().getReal() > 0.9999999999D) {
/*  712 */           throw new CardanEulerSingularityException(true);
/*      */         }
/*  714 */         return buildArray((T)fieldVector3D3.getY().atan2(fieldVector3D3.getX()), (T)((RealFieldElement)fieldVector3D4.getX().asin()).negate(), (T)fieldVector3D4.getY().atan2(fieldVector3D4.getZ()));
/*      */       } 
/*      */ 
/*      */       
/*  718 */       if (order == RotationOrder.XYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  725 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(1.0D, 0.0D, 0.0D));
/*  726 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(1.0D, 0.0D, 0.0D));
/*  727 */         if (fieldVector3D4.getX().getReal() < -0.9999999999D || fieldVector3D4.getX().getReal() > 0.9999999999D) {
/*  728 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  730 */         return buildArray((T)fieldVector3D3.getY().atan2(fieldVector3D3.getZ().negate()), (T)fieldVector3D4.getX().acos(), (T)fieldVector3D4.getY().atan2(fieldVector3D4.getZ()));
/*      */       } 
/*      */ 
/*      */       
/*  734 */       if (order == RotationOrder.XZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  741 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(1.0D, 0.0D, 0.0D));
/*  742 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(1.0D, 0.0D, 0.0D));
/*  743 */         if (fieldVector3D4.getX().getReal() < -0.9999999999D || fieldVector3D4.getX().getReal() > 0.9999999999D) {
/*  744 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  746 */         return buildArray((T)fieldVector3D3.getZ().atan2(fieldVector3D3.getY()), (T)fieldVector3D4.getX().acos(), (T)fieldVector3D4.getZ().atan2(fieldVector3D4.getY().negate()));
/*      */       } 
/*      */ 
/*      */       
/*  750 */       if (order == RotationOrder.YXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  757 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 1.0D, 0.0D));
/*  758 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 1.0D, 0.0D));
/*  759 */         if (fieldVector3D4.getY().getReal() < -0.9999999999D || fieldVector3D4.getY().getReal() > 0.9999999999D) {
/*  760 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  762 */         return buildArray((T)fieldVector3D3.getX().atan2(fieldVector3D3.getZ()), (T)fieldVector3D4.getY().acos(), (T)fieldVector3D4.getX().atan2(fieldVector3D4.getZ().negate()));
/*      */       } 
/*      */ 
/*      */       
/*  766 */       if (order == RotationOrder.YZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  773 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 1.0D, 0.0D));
/*  774 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 1.0D, 0.0D));
/*  775 */         if (fieldVector3D4.getY().getReal() < -0.9999999999D || fieldVector3D4.getY().getReal() > 0.9999999999D) {
/*  776 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  778 */         return buildArray((T)fieldVector3D3.getZ().atan2(fieldVector3D3.getX().negate()), (T)fieldVector3D4.getY().acos(), (T)fieldVector3D4.getZ().atan2(fieldVector3D4.getX()));
/*      */       } 
/*      */ 
/*      */       
/*  782 */       if (order == RotationOrder.ZXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  789 */         FieldVector3D<T> fieldVector3D3 = applyTo(vector(0.0D, 0.0D, 1.0D));
/*  790 */         FieldVector3D<T> fieldVector3D4 = applyInverseTo(vector(0.0D, 0.0D, 1.0D));
/*  791 */         if (fieldVector3D4.getZ().getReal() < -0.9999999999D || fieldVector3D4.getZ().getReal() > 0.9999999999D) {
/*  792 */           throw new CardanEulerSingularityException(false);
/*      */         }
/*  794 */         return buildArray((T)fieldVector3D3.getX().atan2(fieldVector3D3.getY().negate()), (T)fieldVector3D4.getZ().acos(), (T)fieldVector3D4.getX().atan2(fieldVector3D4.getY()));
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
/*  805 */       FieldVector3D<T> fieldVector3D1 = applyTo(vector(0.0D, 0.0D, 1.0D));
/*  806 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(vector(0.0D, 0.0D, 1.0D));
/*  807 */       if (fieldVector3D2.getZ().getReal() < -0.9999999999D || fieldVector3D2.getZ().getReal() > 0.9999999999D) {
/*  808 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  810 */       return buildArray((T)fieldVector3D1.getY().atan2(fieldVector3D1.getX()), (T)fieldVector3D2.getZ().acos(), (T)fieldVector3D2.getY().atan2(fieldVector3D2.getX().negate()));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  816 */     if (order == RotationOrder.XYZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  823 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_I);
/*  824 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_K);
/*  825 */       if (fieldVector3D2.getX().getReal() < -0.9999999999D || fieldVector3D2.getX().getReal() > 0.9999999999D) {
/*  826 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  828 */       return buildArray((T)((RealFieldElement)fieldVector3D2.getY().negate()).atan2(fieldVector3D2.getZ()), (T)fieldVector3D2.getX().asin(), (T)((RealFieldElement)fieldVector3D1.getY().negate()).atan2(fieldVector3D1.getX()));
/*      */     } 
/*      */ 
/*      */     
/*  832 */     if (order == RotationOrder.XZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  839 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_I);
/*  840 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_J);
/*  841 */       if (fieldVector3D2.getX().getReal() < -0.9999999999D || fieldVector3D2.getX().getReal() > 0.9999999999D) {
/*  842 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  844 */       return buildArray((T)fieldVector3D2.getZ().atan2(fieldVector3D2.getY()), (T)((RealFieldElement)fieldVector3D2.getX().asin()).negate(), (T)fieldVector3D1.getZ().atan2(fieldVector3D1.getX()));
/*      */     } 
/*      */ 
/*      */     
/*  848 */     if (order == RotationOrder.YXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  855 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_J);
/*  856 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_K);
/*  857 */       if (fieldVector3D2.getY().getReal() < -0.9999999999D || fieldVector3D2.getY().getReal() > 0.9999999999D) {
/*  858 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  860 */       return buildArray((T)fieldVector3D2.getX().atan2(fieldVector3D2.getZ()), (T)((RealFieldElement)fieldVector3D2.getY().asin()).negate(), (T)fieldVector3D1.getX().atan2(fieldVector3D1.getY()));
/*      */     } 
/*      */ 
/*      */     
/*  864 */     if (order == RotationOrder.YZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  871 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_J);
/*  872 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  873 */       if (fieldVector3D2.getY().getReal() < -0.9999999999D || fieldVector3D2.getY().getReal() > 0.9999999999D) {
/*  874 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  876 */       return buildArray((T)((RealFieldElement)fieldVector3D2.getZ().negate()).atan2(fieldVector3D2.getX()), (T)fieldVector3D2.getY().asin(), (T)((RealFieldElement)fieldVector3D1.getZ().negate()).atan2(fieldVector3D1.getY()));
/*      */     } 
/*      */ 
/*      */     
/*  880 */     if (order == RotationOrder.ZXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  887 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_K);
/*  888 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_J);
/*  889 */       if (fieldVector3D2.getZ().getReal() < -0.9999999999D || fieldVector3D2.getZ().getReal() > 0.9999999999D) {
/*  890 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  892 */       return buildArray((T)((RealFieldElement)fieldVector3D2.getX().negate()).atan2(fieldVector3D2.getY()), (T)fieldVector3D2.getZ().asin(), (T)((RealFieldElement)fieldVector3D1.getX().negate()).atan2(fieldVector3D1.getZ()));
/*      */     } 
/*      */ 
/*      */     
/*  896 */     if (order == RotationOrder.ZYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  903 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_K);
/*  904 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  905 */       if (fieldVector3D2.getZ().getReal() < -0.9999999999D || fieldVector3D2.getZ().getReal() > 0.9999999999D) {
/*  906 */         throw new CardanEulerSingularityException(true);
/*      */       }
/*  908 */       return buildArray((T)fieldVector3D2.getY().atan2(fieldVector3D2.getX()), (T)((RealFieldElement)fieldVector3D2.getZ().asin()).negate(), (T)fieldVector3D1.getY().atan2(fieldVector3D1.getZ()));
/*      */     } 
/*      */ 
/*      */     
/*  912 */     if (order == RotationOrder.XYX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  919 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_I);
/*  920 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  921 */       if (fieldVector3D2.getX().getReal() < -0.9999999999D || fieldVector3D2.getX().getReal() > 0.9999999999D) {
/*  922 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  924 */       return buildArray((T)fieldVector3D2.getY().atan2(fieldVector3D2.getZ().negate()), (T)fieldVector3D2.getX().acos(), (T)fieldVector3D1.getY().atan2(fieldVector3D1.getZ()));
/*      */     } 
/*      */ 
/*      */     
/*  928 */     if (order == RotationOrder.XZX) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  935 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_I);
/*  936 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_I);
/*  937 */       if (fieldVector3D2.getX().getReal() < -0.9999999999D || fieldVector3D2.getX().getReal() > 0.9999999999D) {
/*  938 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  940 */       return buildArray((T)fieldVector3D2.getZ().atan2(fieldVector3D2.getY()), (T)fieldVector3D2.getX().acos(), (T)fieldVector3D1.getZ().atan2(fieldVector3D1.getY().negate()));
/*      */     } 
/*      */ 
/*      */     
/*  944 */     if (order == RotationOrder.YXY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  951 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_J);
/*  952 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_J);
/*  953 */       if (fieldVector3D2.getY().getReal() < -0.9999999999D || fieldVector3D2.getY().getReal() > 0.9999999999D) {
/*  954 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  956 */       return buildArray((T)fieldVector3D2.getX().atan2(fieldVector3D2.getZ()), (T)fieldVector3D2.getY().acos(), (T)fieldVector3D1.getX().atan2(fieldVector3D1.getZ().negate()));
/*      */     } 
/*      */ 
/*      */     
/*  960 */     if (order == RotationOrder.YZY) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  967 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_J);
/*  968 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_J);
/*  969 */       if (fieldVector3D2.getY().getReal() < -0.9999999999D || fieldVector3D2.getY().getReal() > 0.9999999999D) {
/*  970 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  972 */       return buildArray((T)fieldVector3D2.getZ().atan2(fieldVector3D2.getX().negate()), (T)fieldVector3D2.getY().acos(), (T)fieldVector3D1.getZ().atan2(fieldVector3D1.getX()));
/*      */     } 
/*      */ 
/*      */     
/*  976 */     if (order == RotationOrder.ZXZ) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  983 */       FieldVector3D<T> fieldVector3D1 = applyTo(Vector3D.PLUS_K);
/*  984 */       FieldVector3D<T> fieldVector3D2 = applyInverseTo(Vector3D.PLUS_K);
/*  985 */       if (fieldVector3D2.getZ().getReal() < -0.9999999999D || fieldVector3D2.getZ().getReal() > 0.9999999999D) {
/*  986 */         throw new CardanEulerSingularityException(false);
/*      */       }
/*  988 */       return buildArray((T)fieldVector3D2.getX().atan2(fieldVector3D2.getY().negate()), (T)fieldVector3D2.getZ().acos(), (T)fieldVector3D1.getX().atan2(fieldVector3D1.getY()));
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
/*  999 */     FieldVector3D<T> v1 = applyTo(Vector3D.PLUS_K);
/* 1000 */     FieldVector3D<T> v2 = applyInverseTo(Vector3D.PLUS_K);
/* 1001 */     if (v2.getZ().getReal() < -0.9999999999D || v2.getZ().getReal() > 0.9999999999D) {
/* 1002 */       throw new CardanEulerSingularityException(false);
/*      */     }
/* 1004 */     return buildArray((T)v2.getY().atan2(v2.getX()), (T)v2.getZ().acos(), (T)v1.getY().atan2(v1.getX().negate()));
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
/*      */   private T[] buildArray(T a0, T a1, T a2) {
/* 1020 */     RealFieldElement[] arrayOfRealFieldElement = (RealFieldElement[])MathArrays.buildArray(a0.getField(), 3);
/* 1021 */     arrayOfRealFieldElement[0] = (RealFieldElement)a0;
/* 1022 */     arrayOfRealFieldElement[1] = (RealFieldElement)a1;
/* 1023 */     arrayOfRealFieldElement[2] = (RealFieldElement)a2;
/* 1024 */     return (T[])arrayOfRealFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldVector3D<T> vector(double x, double y, double z) {
/* 1034 */     RealFieldElement realFieldElement = (RealFieldElement)this.q0.getField().getZero();
/* 1035 */     return new FieldVector3D<T>((T)realFieldElement.add(x), (T)realFieldElement.add(y), (T)realFieldElement.add(z));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public T[][] getMatrix() {
/* 1044 */     RealFieldElement realFieldElement1 = (RealFieldElement)this.q0.multiply(this.q0);
/* 1045 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.q0.multiply(this.q1);
/* 1046 */     RealFieldElement realFieldElement3 = (RealFieldElement)this.q0.multiply(this.q2);
/* 1047 */     RealFieldElement realFieldElement4 = (RealFieldElement)this.q0.multiply(this.q3);
/* 1048 */     RealFieldElement realFieldElement5 = (RealFieldElement)this.q1.multiply(this.q1);
/* 1049 */     RealFieldElement realFieldElement6 = (RealFieldElement)this.q1.multiply(this.q2);
/* 1050 */     RealFieldElement realFieldElement7 = (RealFieldElement)this.q1.multiply(this.q3);
/* 1051 */     RealFieldElement realFieldElement8 = (RealFieldElement)this.q2.multiply(this.q2);
/* 1052 */     RealFieldElement realFieldElement9 = (RealFieldElement)this.q2.multiply(this.q3);
/* 1053 */     RealFieldElement realFieldElement10 = (RealFieldElement)this.q3.multiply(this.q3);
/*      */ 
/*      */     
/* 1056 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(this.q0.getField(), 3, 3);
/*      */     
/* 1058 */     arrayOfRealFieldElement[0][0] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.add(realFieldElement5)).multiply(2)).subtract(1.0D);
/* 1059 */     arrayOfRealFieldElement[1][0] = (RealFieldElement)((RealFieldElement)realFieldElement6.subtract(realFieldElement4)).multiply(2);
/* 1060 */     arrayOfRealFieldElement[2][0] = (RealFieldElement)((RealFieldElement)realFieldElement7.add(realFieldElement3)).multiply(2);
/*      */     
/* 1062 */     arrayOfRealFieldElement[0][1] = (RealFieldElement)((RealFieldElement)realFieldElement6.add(realFieldElement4)).multiply(2);
/* 1063 */     arrayOfRealFieldElement[1][1] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.add(realFieldElement8)).multiply(2)).subtract(1.0D);
/* 1064 */     arrayOfRealFieldElement[2][1] = (RealFieldElement)((RealFieldElement)realFieldElement9.subtract(realFieldElement2)).multiply(2);
/*      */     
/* 1066 */     arrayOfRealFieldElement[0][2] = (RealFieldElement)((RealFieldElement)realFieldElement7.subtract(realFieldElement3)).multiply(2);
/* 1067 */     arrayOfRealFieldElement[1][2] = (RealFieldElement)((RealFieldElement)realFieldElement9.add(realFieldElement2)).multiply(2);
/* 1068 */     arrayOfRealFieldElement[2][2] = (RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement1.add(realFieldElement10)).multiply(2)).subtract(1.0D);
/*      */     
/* 1070 */     return (T[][])arrayOfRealFieldElement;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rotation toRotation() {
/* 1078 */     return new Rotation(this.q0.getReal(), this.q1.getReal(), this.q2.getReal(), this.q3.getReal(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldVector3D<T> applyTo(FieldVector3D<T> u) {
/* 1087 */     T x = u.getX();
/* 1088 */     T y = u.getY();
/* 1089 */     T z = u.getZ();
/*      */     
/* 1091 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/*      */     
/* 1093 */     return new FieldVector3D<T>((T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)x.multiply(this.q0)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement.multiply(this.q1))).multiply(2)).subtract(x), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)y.multiply(this.q0)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement.multiply(this.q2))).multiply(2)).subtract(y), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)z.multiply(this.q0)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement.multiply(this.q3))).multiply(2)).subtract(z));
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
/*      */   public FieldVector3D<T> applyTo(Vector3D u) {
/* 1105 */     double x = u.getX();
/* 1106 */     double y = u.getY();
/* 1107 */     double z = u.getZ();
/*      */     
/* 1109 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/*      */     
/* 1111 */     return new FieldVector3D<T>((T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement.multiply(this.q1))).multiply(2)).subtract(x), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement.multiply(this.q2))).multiply(2)).subtract(y), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement.multiply(this.q3))).multiply(2)).subtract(z));
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
/*      */   public void applyTo(T[] in, T[] out) {
/* 1124 */     T x = in[0];
/* 1125 */     T y = in[1];
/* 1126 */     T z = in[2];
/*      */     
/* 1128 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/*      */     
/* 1130 */     out[0] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)x.multiply(this.q0)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement.multiply(this.q1))).multiply(2)).subtract(x);
/* 1131 */     out[1] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)y.multiply(this.q0)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement.multiply(this.q2))).multiply(2)).subtract(y);
/* 1132 */     out[2] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)z.multiply(this.q0)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement.multiply(this.q3))).multiply(2)).subtract(z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyTo(double[] in, T[] out) {
/* 1142 */     double x = in[0];
/* 1143 */     double y = in[1];
/* 1144 */     double z = in[2];
/*      */     
/* 1146 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/*      */     
/* 1148 */     out[0] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement.multiply(this.q1))).multiply(2)).subtract(x);
/* 1149 */     out[1] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement.multiply(this.q2))).multiply(2)).subtract(y);
/* 1150 */     out[2] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)this.q0.multiply(((RealFieldElement)this.q0.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement.multiply(this.q3))).multiply(2)).subtract(z);
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
/*      */   public static <T extends RealFieldElement<T>> FieldVector3D<T> applyTo(Rotation r, FieldVector3D<T> u) {
/* 1162 */     T x = u.getX();
/* 1163 */     T y = u.getY();
/* 1164 */     T z = u.getZ();
/*      */     
/* 1166 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(r.getQ1())).add(y.multiply(r.getQ2()))).add(z.multiply(r.getQ3()));
/*      */     
/* 1168 */     return new FieldVector3D<T>((T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(r.getQ0())).subtract(((RealFieldElement)z.multiply(r.getQ2())).subtract(y.multiply(r.getQ3())))).multiply(r.getQ0())).add(realFieldElement.multiply(r.getQ1()))).multiply(2)).subtract(x), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)y.multiply(r.getQ0())).subtract(((RealFieldElement)x.multiply(r.getQ3())).subtract(z.multiply(r.getQ1())))).multiply(r.getQ0())).add(realFieldElement.multiply(r.getQ2()))).multiply(2)).subtract(y), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)z.multiply(r.getQ0())).subtract(((RealFieldElement)y.multiply(r.getQ1())).subtract(x.multiply(r.getQ2())))).multiply(r.getQ0())).add(realFieldElement.multiply(r.getQ3()))).multiply(2)).subtract(z));
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
/*      */   public FieldVector3D<T> applyInverseTo(FieldVector3D<T> u) {
/* 1180 */     T x = u.getX();
/* 1181 */     T y = u.getY();
/* 1182 */     T z = u.getZ();
/*      */     
/* 1184 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/* 1185 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.q0.negate();
/*      */     
/* 1187 */     return new FieldVector3D<T>((T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)x.multiply(realFieldElement2)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement1.multiply(this.q1))).multiply(2)).subtract(x), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)y.multiply(realFieldElement2)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement1.multiply(this.q2))).multiply(2)).subtract(y), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)z.multiply(realFieldElement2)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement1.multiply(this.q3))).multiply(2)).subtract(z));
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
/*      */   public FieldVector3D<T> applyInverseTo(Vector3D u) {
/* 1199 */     double x = u.getX();
/* 1200 */     double y = u.getY();
/* 1201 */     double z = u.getZ();
/*      */     
/* 1203 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/* 1204 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.q0.negate();
/*      */     
/* 1206 */     return new FieldVector3D<T>((T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)realFieldElement2.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement1.multiply(this.q1))).multiply(2)).subtract(x), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)realFieldElement2.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement1.multiply(this.q2))).multiply(2)).subtract(y), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)realFieldElement2.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement1.multiply(this.q3))).multiply(2)).subtract(z));
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
/*      */   public void applyInverseTo(T[] in, T[] out) {
/* 1219 */     T x = in[0];
/* 1220 */     T y = in[1];
/* 1221 */     T z = in[2];
/*      */     
/* 1223 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/* 1224 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.q0.negate();
/*      */     
/* 1226 */     out[0] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)x.multiply(realFieldElement2)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement1.multiply(this.q1))).multiply(2)).subtract(x);
/* 1227 */     out[1] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)y.multiply(realFieldElement2)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement1.multiply(this.q2))).multiply(2)).subtract(y);
/* 1228 */     out[2] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)z.multiply(realFieldElement2)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement1.multiply(this.q3))).multiply(2)).subtract(z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInverseTo(double[] in, T[] out) {
/* 1238 */     double x = in[0];
/* 1239 */     double y = in[1];
/* 1240 */     double z = in[2];
/*      */     
/* 1242 */     RealFieldElement realFieldElement1 = (RealFieldElement)((RealFieldElement)((RealFieldElement)this.q1.multiply(x)).add(this.q2.multiply(y))).add(this.q3.multiply(z));
/* 1243 */     RealFieldElement realFieldElement2 = (RealFieldElement)this.q0.negate();
/*      */     
/* 1245 */     out[0] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)realFieldElement2.multiply(x)).subtract(((RealFieldElement)this.q2.multiply(z)).subtract(this.q3.multiply(y))))).add(realFieldElement1.multiply(this.q1))).multiply(2)).subtract(x);
/* 1246 */     out[1] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)realFieldElement2.multiply(y)).subtract(((RealFieldElement)this.q3.multiply(x)).subtract(this.q1.multiply(z))))).add(realFieldElement1.multiply(this.q2))).multiply(2)).subtract(y);
/* 1247 */     out[2] = (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)realFieldElement2.multiply(((RealFieldElement)realFieldElement2.multiply(z)).subtract(((RealFieldElement)this.q1.multiply(y)).subtract(this.q2.multiply(x))))).add(realFieldElement1.multiply(this.q3))).multiply(2)).subtract(z);
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
/*      */   public static <T extends RealFieldElement<T>> FieldVector3D<T> applyInverseTo(Rotation r, FieldVector3D<T> u) {
/* 1259 */     T x = u.getX();
/* 1260 */     T y = u.getY();
/* 1261 */     T z = u.getZ();
/*      */     
/* 1263 */     RealFieldElement realFieldElement = (RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(r.getQ1())).add(y.multiply(r.getQ2()))).add(z.multiply(r.getQ3()));
/* 1264 */     double m0 = -r.getQ0();
/*      */     
/* 1266 */     return new FieldVector3D<T>((T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x.multiply(m0)).subtract(((RealFieldElement)z.multiply(r.getQ2())).subtract(y.multiply(r.getQ3())))).multiply(m0)).add(realFieldElement.multiply(r.getQ1()))).multiply(2)).subtract(x), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)y.multiply(m0)).subtract(((RealFieldElement)x.multiply(r.getQ3())).subtract(z.multiply(r.getQ1())))).multiply(m0)).add(realFieldElement.multiply(r.getQ2()))).multiply(2)).subtract(y), (T)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)z.multiply(m0)).subtract(((RealFieldElement)y.multiply(r.getQ1())).subtract(x.multiply(r.getQ2())))).multiply(m0)).add(realFieldElement.multiply(r.getQ3()))).multiply(2)).subtract(z));
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
/*      */   public FieldRotation<T> applyTo(FieldRotation<T> r) {
/* 1282 */     return compose(r, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public FieldRotation<T> compose(FieldRotation<T> r, RotationConvention convention) {
/* 1310 */     return (convention == RotationConvention.VECTOR_OPERATOR) ? composeInternal(r) : r.composeInternal(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldRotation<T> composeInternal(FieldRotation<T> r) {
/* 1320 */     return new FieldRotation((T)((RealFieldElement)r.q0.multiply(this.q0)).subtract(((RealFieldElement)((RealFieldElement)r.q1.multiply(this.q1)).add(r.q2.multiply(this.q2))).add(r.q3.multiply(this.q3))), (T)((RealFieldElement)((RealFieldElement)r.q1.multiply(this.q0)).add(r.q0.multiply(this.q1))).add(((RealFieldElement)r.q2.multiply(this.q3)).subtract(r.q3.multiply(this.q2))), (T)((RealFieldElement)((RealFieldElement)r.q2.multiply(this.q0)).add(r.q0.multiply(this.q2))).add(((RealFieldElement)r.q3.multiply(this.q1)).subtract(r.q1.multiply(this.q3))), (T)((RealFieldElement)((RealFieldElement)r.q3.multiply(this.q0)).add(r.q0.multiply(this.q3))).add(((RealFieldElement)r.q1.multiply(this.q2)).subtract(r.q2.multiply(this.q1))), false);
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
/*      */   public FieldRotation<T> applyTo(Rotation r) {
/* 1337 */     return compose(r, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public FieldRotation<T> compose(Rotation r, RotationConvention convention) {
/* 1365 */     return (convention == RotationConvention.VECTOR_OPERATOR) ? composeInternal(r) : applyTo(r, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldRotation<T> composeInternal(Rotation r) {
/* 1375 */     return new FieldRotation((T)((RealFieldElement)this.q0.multiply(r.getQ0())).subtract(((RealFieldElement)((RealFieldElement)this.q1.multiply(r.getQ1())).add(this.q2.multiply(r.getQ2()))).add(this.q3.multiply(r.getQ3()))), (T)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ1())).add(this.q1.multiply(r.getQ0()))).add(((RealFieldElement)this.q3.multiply(r.getQ2())).subtract(this.q2.multiply(r.getQ3()))), (T)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ2())).add(this.q2.multiply(r.getQ0()))).add(((RealFieldElement)this.q1.multiply(r.getQ3())).subtract(this.q3.multiply(r.getQ1()))), (T)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ3())).add(this.q3.multiply(r.getQ0()))).add(((RealFieldElement)this.q2.multiply(r.getQ1())).subtract(this.q1.multiply(r.getQ2()))), false);
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
/*      */   public static <T extends RealFieldElement<T>> FieldRotation<T> applyTo(Rotation r1, FieldRotation<T> rInner) {
/* 1394 */     return new FieldRotation<T>((T)((RealFieldElement)rInner.q0.multiply(r1.getQ0())).subtract(((RealFieldElement)((RealFieldElement)rInner.q1.multiply(r1.getQ1())).add(rInner.q2.multiply(r1.getQ2()))).add(rInner.q3.multiply(r1.getQ3()))), (T)((RealFieldElement)((RealFieldElement)rInner.q1.multiply(r1.getQ0())).add(rInner.q0.multiply(r1.getQ1()))).add(((RealFieldElement)rInner.q2.multiply(r1.getQ3())).subtract(rInner.q3.multiply(r1.getQ2()))), (T)((RealFieldElement)((RealFieldElement)rInner.q2.multiply(r1.getQ0())).add(rInner.q0.multiply(r1.getQ2()))).add(((RealFieldElement)rInner.q3.multiply(r1.getQ1())).subtract(rInner.q1.multiply(r1.getQ3()))), (T)((RealFieldElement)((RealFieldElement)rInner.q3.multiply(r1.getQ0())).add(rInner.q0.multiply(r1.getQ3()))).add(((RealFieldElement)rInner.q1.multiply(r1.getQ2())).subtract(rInner.q2.multiply(r1.getQ1()))), false);
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
/*      */   public FieldRotation<T> applyInverseTo(FieldRotation<T> r) {
/* 1412 */     return composeInverse(r, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public FieldRotation<T> composeInverse(FieldRotation<T> r, RotationConvention convention) {
/* 1442 */     return (convention == RotationConvention.VECTOR_OPERATOR) ? composeInverseInternal(r) : r.composeInternal(revert());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldRotation<T> composeInverseInternal(FieldRotation<T> r) {
/* 1453 */     return new FieldRotation((T)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q0)).add(((RealFieldElement)((RealFieldElement)r.q1.multiply(this.q1)).add(r.q2.multiply(this.q2))).add(r.q3.multiply(this.q3)))).negate(), (T)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q1)).add(((RealFieldElement)r.q2.multiply(this.q3)).subtract(r.q3.multiply(this.q2)))).subtract(r.q1.multiply(this.q0)), (T)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q2)).add(((RealFieldElement)r.q3.multiply(this.q1)).subtract(r.q1.multiply(this.q3)))).subtract(r.q2.multiply(this.q0)), (T)((RealFieldElement)((RealFieldElement)r.q0.multiply(this.q3)).add(((RealFieldElement)r.q1.multiply(this.q2)).subtract(r.q2.multiply(this.q1)))).subtract(r.q3.multiply(this.q0)), false);
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
/*      */   public FieldRotation<T> applyInverseTo(Rotation r) {
/* 1471 */     return composeInverse(r, RotationConvention.VECTOR_OPERATOR);
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
/*      */   public FieldRotation<T> composeInverse(Rotation r, RotationConvention convention) {
/* 1501 */     return (convention == RotationConvention.VECTOR_OPERATOR) ? composeInverseInternal(r) : applyTo(r, revert());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldRotation<T> composeInverseInternal(Rotation r) {
/* 1512 */     return new FieldRotation((T)((RealFieldElement)((RealFieldElement)this.q0.multiply(r.getQ0())).add(((RealFieldElement)((RealFieldElement)this.q1.multiply(r.getQ1())).add(this.q2.multiply(r.getQ2()))).add(this.q3.multiply(r.getQ3())))).negate(), (T)((RealFieldElement)((RealFieldElement)this.q1.multiply(r.getQ0())).add(((RealFieldElement)this.q3.multiply(r.getQ2())).subtract(this.q2.multiply(r.getQ3())))).subtract(this.q0.multiply(r.getQ1())), (T)((RealFieldElement)((RealFieldElement)this.q2.multiply(r.getQ0())).add(((RealFieldElement)this.q1.multiply(r.getQ3())).subtract(this.q3.multiply(r.getQ1())))).subtract(this.q0.multiply(r.getQ2())), (T)((RealFieldElement)((RealFieldElement)this.q3.multiply(r.getQ0())).add(((RealFieldElement)this.q2.multiply(r.getQ1())).subtract(this.q1.multiply(r.getQ2())))).subtract(this.q0.multiply(r.getQ3())), false);
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
/*      */   public static <T extends RealFieldElement<T>> FieldRotation<T> applyInverseTo(Rotation rOuter, FieldRotation<T> rInner) {
/* 1533 */     return new FieldRotation<T>((T)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ0())).add(((RealFieldElement)((RealFieldElement)rInner.q1.multiply(rOuter.getQ1())).add(rInner.q2.multiply(rOuter.getQ2()))).add(rInner.q3.multiply(rOuter.getQ3())))).negate(), (T)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ1())).add(((RealFieldElement)rInner.q2.multiply(rOuter.getQ3())).subtract(rInner.q3.multiply(rOuter.getQ2())))).subtract(rInner.q1.multiply(rOuter.getQ0())), (T)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ2())).add(((RealFieldElement)rInner.q3.multiply(rOuter.getQ1())).subtract(rInner.q1.multiply(rOuter.getQ3())))).subtract(rInner.q2.multiply(rOuter.getQ0())), (T)((RealFieldElement)((RealFieldElement)rInner.q0.multiply(rOuter.getQ3())).add(((RealFieldElement)rInner.q1.multiply(rOuter.getQ2())).subtract(rInner.q2.multiply(rOuter.getQ1())))).subtract(rInner.q3.multiply(rOuter.getQ0())), false);
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
/*      */   private T[][] orthogonalizeMatrix(T[][] m, double threshold) throws NotARotationMatrixException {
/* 1553 */     T x00 = m[0][0];
/* 1554 */     T x01 = m[0][1];
/* 1555 */     T x02 = m[0][2];
/* 1556 */     T x10 = m[1][0];
/* 1557 */     T x11 = m[1][1];
/* 1558 */     T x12 = m[1][2];
/* 1559 */     T x20 = m[2][0];
/* 1560 */     T x21 = m[2][1];
/* 1561 */     T x22 = m[2][2];
/* 1562 */     double fn = 0.0D;
/*      */ 
/*      */     
/* 1565 */     RealFieldElement[][] arrayOfRealFieldElement = (RealFieldElement[][])MathArrays.buildArray(m[0][0].getField(), 3, 3);
/*      */ 
/*      */     
/* 1568 */     int i = 0;
/* 1569 */     while (++i < 11) {
/*      */ 
/*      */       
/* 1572 */       RealFieldElement realFieldElement10 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][0].multiply(x00)).add(m[1][0].multiply(x10))).add(m[2][0].multiply(x20));
/* 1573 */       RealFieldElement realFieldElement11 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][1].multiply(x00)).add(m[1][1].multiply(x10))).add(m[2][1].multiply(x20));
/* 1574 */       RealFieldElement realFieldElement12 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][2].multiply(x00)).add(m[1][2].multiply(x10))).add(m[2][2].multiply(x20));
/* 1575 */       RealFieldElement realFieldElement13 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][0].multiply(x01)).add(m[1][0].multiply(x11))).add(m[2][0].multiply(x21));
/* 1576 */       RealFieldElement realFieldElement14 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][1].multiply(x01)).add(m[1][1].multiply(x11))).add(m[2][1].multiply(x21));
/* 1577 */       RealFieldElement realFieldElement15 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][2].multiply(x01)).add(m[1][2].multiply(x11))).add(m[2][2].multiply(x21));
/* 1578 */       RealFieldElement realFieldElement16 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][0].multiply(x02)).add(m[1][0].multiply(x12))).add(m[2][0].multiply(x22));
/* 1579 */       RealFieldElement realFieldElement17 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][1].multiply(x02)).add(m[1][1].multiply(x12))).add(m[2][1].multiply(x22));
/* 1580 */       RealFieldElement realFieldElement18 = (RealFieldElement)((RealFieldElement)((RealFieldElement)m[0][2].multiply(x02)).add(m[1][2].multiply(x12))).add(m[2][2].multiply(x22));
/*      */ 
/*      */       
/* 1583 */       arrayOfRealFieldElement[0][0] = (RealFieldElement)x00.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x00.multiply(realFieldElement10)).add(x01.multiply(realFieldElement11))).add(x02.multiply(realFieldElement12))).subtract(m[0][0])).multiply(0.5D));
/* 1584 */       arrayOfRealFieldElement[0][1] = (RealFieldElement)x01.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x00.multiply(realFieldElement13)).add(x01.multiply(realFieldElement14))).add(x02.multiply(realFieldElement15))).subtract(m[0][1])).multiply(0.5D));
/* 1585 */       arrayOfRealFieldElement[0][2] = (RealFieldElement)x02.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x00.multiply(realFieldElement16)).add(x01.multiply(realFieldElement17))).add(x02.multiply(realFieldElement18))).subtract(m[0][2])).multiply(0.5D));
/* 1586 */       arrayOfRealFieldElement[1][0] = (RealFieldElement)x10.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x10.multiply(realFieldElement10)).add(x11.multiply(realFieldElement11))).add(x12.multiply(realFieldElement12))).subtract(m[1][0])).multiply(0.5D));
/* 1587 */       arrayOfRealFieldElement[1][1] = (RealFieldElement)x11.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x10.multiply(realFieldElement13)).add(x11.multiply(realFieldElement14))).add(x12.multiply(realFieldElement15))).subtract(m[1][1])).multiply(0.5D));
/* 1588 */       arrayOfRealFieldElement[1][2] = (RealFieldElement)x12.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x10.multiply(realFieldElement16)).add(x11.multiply(realFieldElement17))).add(x12.multiply(realFieldElement18))).subtract(m[1][2])).multiply(0.5D));
/* 1589 */       arrayOfRealFieldElement[2][0] = (RealFieldElement)x20.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x20.multiply(realFieldElement10)).add(x21.multiply(realFieldElement11))).add(x22.multiply(realFieldElement12))).subtract(m[2][0])).multiply(0.5D));
/* 1590 */       arrayOfRealFieldElement[2][1] = (RealFieldElement)x21.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x20.multiply(realFieldElement13)).add(x21.multiply(realFieldElement14))).add(x22.multiply(realFieldElement15))).subtract(m[2][1])).multiply(0.5D));
/* 1591 */       arrayOfRealFieldElement[2][2] = (RealFieldElement)x22.subtract(((RealFieldElement)((RealFieldElement)((RealFieldElement)((RealFieldElement)x20.multiply(realFieldElement16)).add(x21.multiply(realFieldElement17))).add(x22.multiply(realFieldElement18))).subtract(m[2][2])).multiply(0.5D));
/*      */ 
/*      */       
/* 1594 */       double corr00 = arrayOfRealFieldElement[0][0].getReal() - m[0][0].getReal();
/* 1595 */       double corr01 = arrayOfRealFieldElement[0][1].getReal() - m[0][1].getReal();
/* 1596 */       double corr02 = arrayOfRealFieldElement[0][2].getReal() - m[0][2].getReal();
/* 1597 */       double corr10 = arrayOfRealFieldElement[1][0].getReal() - m[1][0].getReal();
/* 1598 */       double corr11 = arrayOfRealFieldElement[1][1].getReal() - m[1][1].getReal();
/* 1599 */       double corr12 = arrayOfRealFieldElement[1][2].getReal() - m[1][2].getReal();
/* 1600 */       double corr20 = arrayOfRealFieldElement[2][0].getReal() - m[2][0].getReal();
/* 1601 */       double corr21 = arrayOfRealFieldElement[2][1].getReal() - m[2][1].getReal();
/* 1602 */       double corr22 = arrayOfRealFieldElement[2][2].getReal() - m[2][2].getReal();
/*      */ 
/*      */       
/* 1605 */       double fn1 = corr00 * corr00 + corr01 * corr01 + corr02 * corr02 + corr10 * corr10 + corr11 * corr11 + corr12 * corr12 + corr20 * corr20 + corr21 * corr21 + corr22 * corr22;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1610 */       if (FastMath.abs(fn1 - fn) <= threshold) {
/* 1611 */         return (T[][])arrayOfRealFieldElement;
/*      */       }
/*      */ 
/*      */       
/* 1615 */       RealFieldElement realFieldElement1 = arrayOfRealFieldElement[0][0];
/* 1616 */       RealFieldElement realFieldElement2 = arrayOfRealFieldElement[0][1];
/* 1617 */       RealFieldElement realFieldElement3 = arrayOfRealFieldElement[0][2];
/* 1618 */       RealFieldElement realFieldElement4 = arrayOfRealFieldElement[1][0];
/* 1619 */       RealFieldElement realFieldElement5 = arrayOfRealFieldElement[1][1];
/* 1620 */       RealFieldElement realFieldElement6 = arrayOfRealFieldElement[1][2];
/* 1621 */       RealFieldElement realFieldElement7 = arrayOfRealFieldElement[2][0];
/* 1622 */       RealFieldElement realFieldElement8 = arrayOfRealFieldElement[2][1];
/* 1623 */       RealFieldElement realFieldElement9 = arrayOfRealFieldElement[2][2];
/* 1624 */       fn = fn1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1629 */     throw new NotARotationMatrixException(LocalizedFormats.UNABLE_TO_ORTHOGONOLIZE_MATRIX, new Object[] { Integer.valueOf(i - 1) });
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
/*      */   public static <T extends RealFieldElement<T>> T distance(FieldRotation<T> r1, FieldRotation<T> r2) {
/* 1660 */     return r1.composeInverseInternal(r2).getAngle();
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/FieldRotation.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */