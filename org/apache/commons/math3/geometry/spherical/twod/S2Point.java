/*     */ package org.apache.commons.math3.geometry.spherical.twod;
/*     */ 
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.OutOfRangeException;
/*     */ import org.apache.commons.math3.geometry.Point;
/*     */ import org.apache.commons.math3.geometry.Space;
/*     */ import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class S2Point
/*     */   implements Point<Sphere2D>
/*     */ {
/*  41 */   public static final S2Point PLUS_I = new S2Point(0.0D, 1.5707963267948966D, Vector3D.PLUS_I);
/*     */ 
/*     */   
/*  44 */   public static final S2Point PLUS_J = new S2Point(1.5707963267948966D, 1.5707963267948966D, Vector3D.PLUS_J);
/*     */ 
/*     */   
/*  47 */   public static final S2Point PLUS_K = new S2Point(0.0D, 0.0D, Vector3D.PLUS_K);
/*     */ 
/*     */   
/*  50 */   public static final S2Point MINUS_I = new S2Point(Math.PI, 1.5707963267948966D, Vector3D.MINUS_I);
/*     */ 
/*     */   
/*  53 */   public static final S2Point MINUS_J = new S2Point(4.71238898038469D, 1.5707963267948966D, Vector3D.MINUS_J);
/*     */ 
/*     */   
/*  56 */   public static final S2Point MINUS_K = new S2Point(0.0D, Math.PI, Vector3D.MINUS_K);
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final S2Point NaN = new S2Point(Double.NaN, Double.NaN, Vector3D.NaN);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long serialVersionUID = 20131218L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double theta;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final double phi;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Vector3D vector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S2Point(double theta, double phi) throws OutOfRangeException {
/*  85 */     this(theta, phi, vector(theta, phi));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S2Point(Vector3D vector) throws MathArithmeticException {
/*  94 */     this(FastMath.atan2(vector.getY(), vector.getX()), Vector3D.angle(Vector3D.PLUS_K, vector), vector.normalize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private S2Point(double theta, double phi, Vector3D vector) {
/* 104 */     this.theta = theta;
/* 105 */     this.phi = phi;
/* 106 */     this.vector = vector;
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
/*     */   private static Vector3D vector(double theta, double phi) throws OutOfRangeException {
/* 118 */     if (phi < 0.0D || phi > Math.PI) {
/* 119 */       throw new OutOfRangeException(Double.valueOf(phi), Integer.valueOf(0), Double.valueOf(Math.PI));
/*     */     }
/*     */     
/* 122 */     double cosTheta = FastMath.cos(theta);
/* 123 */     double sinTheta = FastMath.sin(theta);
/* 124 */     double cosPhi = FastMath.cos(phi);
/* 125 */     double sinPhi = FastMath.sin(phi);
/*     */     
/* 127 */     return new Vector3D(cosTheta * sinPhi, sinTheta * sinPhi, cosPhi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTheta() {
/* 136 */     return this.theta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPhi() {
/* 144 */     return this.phi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getVector() {
/* 151 */     return this.vector;
/*     */   }
/*     */ 
/*     */   
/*     */   public Space getSpace() {
/* 156 */     return Sphere2D.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNaN() {
/* 161 */     return (Double.isNaN(this.theta) || Double.isNaN(this.phi));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public S2Point negate() {
/* 168 */     return new S2Point(-this.theta, Math.PI - this.phi, this.vector.negate());
/*     */   }
/*     */ 
/*     */   
/*     */   public double distance(Point<Sphere2D> point) {
/* 173 */     return distance(this, (S2Point)point);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double distance(S2Point p1, S2Point p2) {
/* 182 */     return Vector3D.angle(p1.vector, p2.vector);
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
/* 207 */     if (this == other) {
/* 208 */       return true;
/*     */     }
/*     */     
/* 211 */     if (other instanceof S2Point) {
/* 212 */       S2Point rhs = (S2Point)other;
/* 213 */       if (rhs.isNaN()) {
/* 214 */         return isNaN();
/*     */       }
/*     */       
/* 217 */       return (this.theta == rhs.theta && this.phi == rhs.phi);
/*     */     } 
/* 219 */     return false;
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
/* 231 */     if (isNaN()) {
/* 232 */       return 542;
/*     */     }
/* 234 */     return 134 * (37 * MathUtils.hash(this.theta) + MathUtils.hash(this.phi));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/spherical/twod/S2Point.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */