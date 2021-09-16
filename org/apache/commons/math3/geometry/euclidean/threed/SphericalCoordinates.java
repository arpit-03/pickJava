/*     */ package org.apache.commons.math3.geometry.euclidean.threed;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SphericalCoordinates
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 20130206L;
/*     */   private final Vector3D v;
/*     */   private final double r;
/*     */   private final double theta;
/*     */   private final double phi;
/*     */   private double[][] jacobian;
/*     */   private double[][] rHessian;
/*     */   private double[][] thetaHessian;
/*     */   private double[][] phiHessian;
/*     */   
/*     */   public SphericalCoordinates(Vector3D v) {
/*  87 */     this.v = v;
/*     */ 
/*     */     
/*  90 */     this.r = v.getNorm();
/*  91 */     this.theta = v.getAlpha();
/*  92 */     this.phi = FastMath.acos(v.getZ() / this.r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SphericalCoordinates(double r, double theta, double phi) {
/* 103 */     double cosTheta = FastMath.cos(theta);
/* 104 */     double sinTheta = FastMath.sin(theta);
/* 105 */     double cosPhi = FastMath.cos(phi);
/* 106 */     double sinPhi = FastMath.sin(phi);
/*     */ 
/*     */     
/* 109 */     this.r = r;
/* 110 */     this.theta = theta;
/* 111 */     this.phi = phi;
/*     */ 
/*     */     
/* 114 */     this.v = new Vector3D(r * cosTheta * sinPhi, r * sinTheta * sinPhi, r * cosPhi);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3D getCartesian() {
/* 124 */     return this.v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getR() {
/* 133 */     return this.r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTheta() {
/* 142 */     return this.theta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPhi() {
/* 151 */     return this.phi;
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
/*     */   public double[] toCartesianGradient(double[] sGradient) {
/* 164 */     computeJacobian();
/*     */ 
/*     */ 
/*     */     
/* 168 */     return new double[] { sGradient[0] * this.jacobian[0][0] + sGradient[1] * this.jacobian[1][0] + sGradient[2] * this.jacobian[2][0], sGradient[0] * this.jacobian[0][1] + sGradient[1] * this.jacobian[1][1] + sGradient[2] * this.jacobian[2][1], sGradient[0] * this.jacobian[0][2] + sGradient[2] * this.jacobian[2][2] };
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
/*     */ 
/*     */ 
/*     */   
/*     */   public double[][] toCartesianHessian(double[][] sHessian, double[] sGradient) {
/* 196 */     computeJacobian();
/* 197 */     computeHessians();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     double[][] hj = new double[3][3];
/* 203 */     double[][] cHessian = new double[3][3];
/*     */ 
/*     */ 
/*     */     
/* 207 */     hj[0][0] = sHessian[0][0] * this.jacobian[0][0] + sHessian[1][0] * this.jacobian[1][0] + sHessian[2][0] * this.jacobian[2][0];
/* 208 */     hj[0][1] = sHessian[0][0] * this.jacobian[0][1] + sHessian[1][0] * this.jacobian[1][1] + sHessian[2][0] * this.jacobian[2][1];
/* 209 */     hj[0][2] = sHessian[0][0] * this.jacobian[0][2] + sHessian[2][0] * this.jacobian[2][2];
/* 210 */     hj[1][0] = sHessian[1][0] * this.jacobian[0][0] + sHessian[1][1] * this.jacobian[1][0] + sHessian[2][1] * this.jacobian[2][0];
/* 211 */     hj[1][1] = sHessian[1][0] * this.jacobian[0][1] + sHessian[1][1] * this.jacobian[1][1] + sHessian[2][1] * this.jacobian[2][1];
/*     */     
/* 213 */     hj[2][0] = sHessian[2][0] * this.jacobian[0][0] + sHessian[2][1] * this.jacobian[1][0] + sHessian[2][2] * this.jacobian[2][0];
/* 214 */     hj[2][1] = sHessian[2][0] * this.jacobian[0][1] + sHessian[2][1] * this.jacobian[1][1] + sHessian[2][2] * this.jacobian[2][1];
/* 215 */     hj[2][2] = sHessian[2][0] * this.jacobian[0][2] + sHessian[2][2] * this.jacobian[2][2];
/*     */ 
/*     */     
/* 218 */     cHessian[0][0] = this.jacobian[0][0] * hj[0][0] + this.jacobian[1][0] * hj[1][0] + this.jacobian[2][0] * hj[2][0];
/* 219 */     cHessian[1][0] = this.jacobian[0][1] * hj[0][0] + this.jacobian[1][1] * hj[1][0] + this.jacobian[2][1] * hj[2][0];
/* 220 */     cHessian[2][0] = this.jacobian[0][2] * hj[0][0] + this.jacobian[2][2] * hj[2][0];
/* 221 */     cHessian[1][1] = this.jacobian[0][1] * hj[0][1] + this.jacobian[1][1] * hj[1][1] + this.jacobian[2][1] * hj[2][1];
/* 222 */     cHessian[2][1] = this.jacobian[0][2] * hj[0][1] + this.jacobian[2][2] * hj[2][1];
/* 223 */     cHessian[2][2] = this.jacobian[0][2] * hj[0][2] + this.jacobian[2][2] * hj[2][2];
/*     */ 
/*     */     
/* 226 */     cHessian[0][0] = cHessian[0][0] + sGradient[0] * this.rHessian[0][0] + sGradient[1] * this.thetaHessian[0][0] + sGradient[2] * this.phiHessian[0][0];
/* 227 */     cHessian[1][0] = cHessian[1][0] + sGradient[0] * this.rHessian[1][0] + sGradient[1] * this.thetaHessian[1][0] + sGradient[2] * this.phiHessian[1][0];
/* 228 */     cHessian[2][0] = cHessian[2][0] + sGradient[0] * this.rHessian[2][0] + sGradient[2] * this.phiHessian[2][0];
/* 229 */     cHessian[1][1] = cHessian[1][1] + sGradient[0] * this.rHessian[1][1] + sGradient[1] * this.thetaHessian[1][1] + sGradient[2] * this.phiHessian[1][1];
/* 230 */     cHessian[2][1] = cHessian[2][1] + sGradient[0] * this.rHessian[2][1] + sGradient[2] * this.phiHessian[2][1];
/* 231 */     cHessian[2][2] = cHessian[2][2] + sGradient[0] * this.rHessian[2][2] + sGradient[2] * this.phiHessian[2][2];
/*     */ 
/*     */     
/* 234 */     cHessian[0][1] = cHessian[1][0];
/* 235 */     cHessian[0][2] = cHessian[2][0];
/* 236 */     cHessian[1][2] = cHessian[2][1];
/*     */     
/* 238 */     return cHessian;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeJacobian() {
/* 245 */     if (this.jacobian == null) {
/*     */ 
/*     */       
/* 248 */       double x = this.v.getX();
/* 249 */       double y = this.v.getY();
/* 250 */       double z = this.v.getZ();
/* 251 */       double rho2 = x * x + y * y;
/* 252 */       double rho = FastMath.sqrt(rho2);
/* 253 */       double r2 = rho2 + z * z;
/*     */       
/* 255 */       this.jacobian = new double[3][3];
/*     */ 
/*     */       
/* 258 */       this.jacobian[0][0] = x / this.r;
/* 259 */       this.jacobian[0][1] = y / this.r;
/* 260 */       this.jacobian[0][2] = z / this.r;
/*     */ 
/*     */       
/* 263 */       this.jacobian[1][0] = -y / rho2;
/* 264 */       this.jacobian[1][1] = x / rho2;
/*     */ 
/*     */ 
/*     */       
/* 268 */       this.jacobian[2][0] = x * z / rho * r2;
/* 269 */       this.jacobian[2][1] = y * z / rho * r2;
/* 270 */       this.jacobian[2][2] = -rho / r2;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void computeHessians() {
/* 279 */     if (this.rHessian == null) {
/*     */ 
/*     */       
/* 282 */       double x = this.v.getX();
/* 283 */       double y = this.v.getY();
/* 284 */       double z = this.v.getZ();
/* 285 */       double x2 = x * x;
/* 286 */       double y2 = y * y;
/* 287 */       double z2 = z * z;
/* 288 */       double rho2 = x2 + y2;
/* 289 */       double rho = FastMath.sqrt(rho2);
/* 290 */       double r2 = rho2 + z2;
/* 291 */       double xOr = x / this.r;
/* 292 */       double yOr = y / this.r;
/* 293 */       double zOr = z / this.r;
/* 294 */       double xOrho2 = x / rho2;
/* 295 */       double yOrho2 = y / rho2;
/* 296 */       double xOr3 = xOr / r2;
/* 297 */       double yOr3 = yOr / r2;
/* 298 */       double zOr3 = zOr / r2;
/*     */ 
/*     */       
/* 301 */       this.rHessian = new double[3][3];
/* 302 */       this.rHessian[0][0] = y * yOr3 + z * zOr3;
/* 303 */       this.rHessian[1][0] = -x * yOr3;
/* 304 */       this.rHessian[2][0] = -z * xOr3;
/* 305 */       this.rHessian[1][1] = x * xOr3 + z * zOr3;
/* 306 */       this.rHessian[2][1] = -y * zOr3;
/* 307 */       this.rHessian[2][2] = x * xOr3 + y * yOr3;
/*     */ 
/*     */       
/* 310 */       this.rHessian[0][1] = this.rHessian[1][0];
/* 311 */       this.rHessian[0][2] = this.rHessian[2][0];
/* 312 */       this.rHessian[1][2] = this.rHessian[2][1];
/*     */ 
/*     */       
/* 315 */       this.thetaHessian = new double[2][2];
/* 316 */       this.thetaHessian[0][0] = 2.0D * xOrho2 * yOrho2;
/* 317 */       this.thetaHessian[1][0] = yOrho2 * yOrho2 - xOrho2 * xOrho2;
/* 318 */       this.thetaHessian[1][1] = -2.0D * xOrho2 * yOrho2;
/*     */ 
/*     */       
/* 321 */       this.thetaHessian[0][1] = this.thetaHessian[1][0];
/*     */ 
/*     */       
/* 324 */       double rhor2 = rho * r2;
/* 325 */       double rho2r2 = rho * rhor2;
/* 326 */       double rhor4 = rhor2 * r2;
/* 327 */       double rho3r4 = rhor4 * rho2;
/* 328 */       double r2P2rho2 = 3.0D * rho2 + z2;
/* 329 */       this.phiHessian = new double[3][3];
/* 330 */       this.phiHessian[0][0] = z * (rho2r2 - x2 * r2P2rho2) / rho3r4;
/* 331 */       this.phiHessian[1][0] = -x * y * z * r2P2rho2 / rho3r4;
/* 332 */       this.phiHessian[2][0] = x * (rho2 - z2) / rhor4;
/* 333 */       this.phiHessian[1][1] = z * (rho2r2 - y2 * r2P2rho2) / rho3r4;
/* 334 */       this.phiHessian[2][1] = y * (rho2 - z2) / rhor4;
/* 335 */       this.phiHessian[2][2] = 2.0D * rho * zOr3 / this.r;
/*     */ 
/*     */       
/* 338 */       this.phiHessian[0][1] = this.phiHessian[1][0];
/* 339 */       this.phiHessian[0][2] = this.phiHessian[2][0];
/* 340 */       this.phiHessian[1][2] = this.phiHessian[2][1];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 351 */     return new DataTransferObject(this.v.getX(), this.v.getY(), this.v.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class DataTransferObject
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 20130206L;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double x;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final double y;
/*     */ 
/*     */ 
/*     */     
/*     */     private final double z;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DataTransferObject(double x, double y, double z) {
/* 381 */       this.x = x;
/* 382 */       this.y = y;
/* 383 */       this.z = z;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 390 */       return new SphericalCoordinates(new Vector3D(this.x, this.y, this.z));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/geometry/euclidean/threed/SphericalCoordinates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */