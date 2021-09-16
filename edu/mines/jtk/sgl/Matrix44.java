/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Matrix44
/*     */ {
/*  36 */   public double[] m = new double[16];
/*     */   
/*     */   private static final double D2R = 0.017453292519943295D;
/*     */ 
/*     */   
/*     */   public Matrix44() {
/*  42 */     this.m[15] = 1.0D; this.m[10] = 1.0D; this.m[5] = 1.0D; this.m[0] = 1.0D;
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
/*     */   public Matrix44(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
/*  69 */     set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
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
/*     */   public Matrix44(double[] m) {
/*  83 */     this.m = m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44(Matrix44 m) {
/*  91 */     set(m.m[0], m.m[4], m.m[8], m.m[12], m.m[1], m.m[5], m.m[9], m.m[13], m.m[2], m.m[6], m.m[10], m.m[14], m.m[3], m.m[7], m.m[11], m.m[15]);
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
/*     */ 
/*     */   
/*     */   public void set(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
/* 121 */     this.m[0] = m00; this.m[4] = m01; this.m[8] = m02; this.m[12] = m03;
/* 122 */     this.m[1] = m10; this.m[5] = m11; this.m[9] = m12; this.m[13] = m13;
/* 123 */     this.m[2] = m20; this.m[6] = m21; this.m[10] = m22; this.m[14] = m23;
/* 124 */     this.m[3] = m30; this.m[7] = m31; this.m[11] = m32; this.m[15] = m33;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 inverse() {
/* 132 */     return new Matrix44(invert(this.m, new double[16]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 inverseEquals() {
/* 140 */     invert(this.m, this.m);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 transpose() {
/* 149 */     double[] t = { this.m[0], this.m[4], this.m[8], this.m[12], this.m[1], this.m[5], this.m[9], this.m[13], this.m[2], this.m[6], this.m[10], this.m[14], this.m[3], this.m[7], this.m[11], this.m[15] };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     return new Matrix44(t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 transposeEquals() {
/* 163 */     double t00 = this.m[0], t01 = this.m[1], t02 = this.m[2], t03 = this.m[3];
/* 164 */     double t10 = this.m[4], t11 = this.m[5], t12 = this.m[6], t13 = this.m[7];
/* 165 */     double t20 = this.m[8], t21 = this.m[9], t22 = this.m[10], t23 = this.m[11];
/* 166 */     double t30 = this.m[12], t31 = this.m[13], t32 = this.m[14], t33 = this.m[15];
/* 167 */     this.m[0] = t00; this.m[4] = t01; this.m[8] = t02; this.m[12] = t03;
/* 168 */     this.m[1] = t10; this.m[5] = t11; this.m[9] = t12; this.m[13] = t13;
/* 169 */     this.m[2] = t20; this.m[6] = t21; this.m[10] = t22; this.m[14] = t23;
/* 170 */     this.m[3] = t30; this.m[7] = t31; this.m[11] = t32; this.m[15] = t33;
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 times(Matrix44 a) {
/* 180 */     return new Matrix44(mul(this.m, a.m, new double[16]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 timesEquals(Matrix44 a) {
/* 189 */     mul(this.m, a.m, this.m);
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 timesTranspose(Matrix44 a) {
/* 199 */     return new Matrix44(mult(this.m, a.m, new double[16]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 timesTransposeEquals(Matrix44 a) {
/* 208 */     mult(this.m, a.m, this.m);
/* 209 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 transposeTimes(Matrix44 a) {
/* 218 */     return new Matrix44(tmul(this.m, a.m, new double[16]));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 transposeTimesEquals(Matrix44 a) {
/* 227 */     tmul(this.m, a.m, this.m);
/* 228 */     return this;
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
/*     */   public Point3 times(Point3 p) {
/* 240 */     double px = p.x;
/* 241 */     double py = p.y;
/* 242 */     double pz = p.z;
/* 243 */     double qx = this.m[0] * px + this.m[4] * py + this.m[8] * pz + this.m[12];
/* 244 */     double qy = this.m[1] * px + this.m[5] * py + this.m[9] * pz + this.m[13];
/* 245 */     double qz = this.m[2] * px + this.m[6] * py + this.m[10] * pz + this.m[14];
/* 246 */     double qw = this.m[3] * px + this.m[7] * py + this.m[11] * pz + this.m[15];
/* 247 */     if (qw != 1.0D) {
/* 248 */       double s = 1.0D / qw;
/* 249 */       qx *= s;
/* 250 */       qy *= s;
/* 251 */       qz *= s;
/*     */     } 
/* 253 */     return new Point3(qx, qy, qz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 times(Point4 p) {
/* 262 */     double px = p.x;
/* 263 */     double py = p.y;
/* 264 */     double pz = p.z;
/* 265 */     double pw = p.w;
/* 266 */     double qx = this.m[0] * px + this.m[4] * py + this.m[8] * pz + this.m[12] * pw;
/* 267 */     double qy = this.m[1] * px + this.m[5] * py + this.m[9] * pz + this.m[13] * pw;
/* 268 */     double qz = this.m[2] * px + this.m[6] * py + this.m[10] * pz + this.m[14] * pw;
/* 269 */     double qw = this.m[3] * px + this.m[7] * py + this.m[11] * pz + this.m[15] * pw;
/* 270 */     return new Point4(qx, qy, qz, qw);
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
/*     */   public Point3 transposeTimes(Point3 p) {
/* 282 */     double px = p.x;
/* 283 */     double py = p.y;
/* 284 */     double pz = p.z;
/* 285 */     double qx = this.m[0] * px + this.m[1] * py + this.m[2] * pz + this.m[3];
/* 286 */     double qy = this.m[4] * px + this.m[5] * py + this.m[6] * pz + this.m[7];
/* 287 */     double qz = this.m[8] * px + this.m[9] * py + this.m[10] * pz + this.m[11];
/* 288 */     double qw = this.m[12] * px + this.m[13] * py + this.m[14] * pz + this.m[15];
/* 289 */     if (qw != 1.0D) {
/* 290 */       double s = 1.0D / qw;
/* 291 */       qx *= s;
/* 292 */       qy *= s;
/* 293 */       qz *= s;
/*     */     } 
/* 295 */     return new Point3(qx, qy, qz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point4 transposeTimes(Point4 p) {
/* 304 */     double px = p.x;
/* 305 */     double py = p.y;
/* 306 */     double pz = p.z;
/* 307 */     double pw = p.w;
/* 308 */     double qx = this.m[0] * px + this.m[1] * py + this.m[2] * pz + this.m[3] * pw;
/* 309 */     double qy = this.m[4] * px + this.m[5] * py + this.m[6] * pz + this.m[7] * pw;
/* 310 */     double qz = this.m[8] * px + this.m[9] * py + this.m[10] * pz + this.m[11] * pw;
/* 311 */     double qw = this.m[12] * px + this.m[13] * py + this.m[14] * pz + this.m[15] * pw;
/* 312 */     return new Point4(qx, qy, qz, qw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 times(Vector3 v) {
/* 322 */     double vx = v.x;
/* 323 */     double vy = v.y;
/* 324 */     double vz = v.z;
/* 325 */     double ux = this.m[0] * vx + this.m[4] * vy + this.m[8] * vz;
/* 326 */     double uy = this.m[1] * vx + this.m[5] * vy + this.m[9] * vz;
/* 327 */     double uz = this.m[2] * vx + this.m[6] * vy + this.m[10] * vz;
/* 328 */     return new Vector3(ux, uy, uz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 transposeTimes(Vector3 v) {
/* 338 */     double vx = v.x;
/* 339 */     double vy = v.y;
/* 340 */     double vz = v.z;
/* 341 */     double ux = this.m[0] * vx + this.m[1] * vy + this.m[2] * vz;
/* 342 */     double uy = this.m[4] * vx + this.m[5] * vy + this.m[6] * vz;
/* 343 */     double uz = this.m[8] * vx + this.m[9] * vy + this.m[10] * vz;
/* 344 */     return new Vector3(ux, uy, uz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 identity() {
/* 352 */     return new Matrix44();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 translate(double tx, double ty, double tz) {
/* 363 */     return (new Matrix44()).setTranslate(tx, ty, tz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 translate(Vector3 tv) {
/* 372 */     return (new Matrix44()).setTranslate(tv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 scale(double sx, double sy, double sz) {
/* 383 */     return (new Matrix44()).setScale(sx, sy, sz);
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
/*     */   public static Matrix44 rotate(double ra, double rx, double ry, double rz) {
/* 396 */     return (new Matrix44()).setRotate(ra, rx, ry, rz);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 rotate(double ra, Vector3 rv) {
/* 407 */     return (new Matrix44()).setRotate(ra, rv);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 rotateX(double ra) {
/* 417 */     return (new Matrix44()).setRotateX(ra);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 rotateY(double ra) {
/* 427 */     return (new Matrix44()).setRotateY(ra);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix44 rotateZ(double ra) {
/* 437 */     return (new Matrix44()).setRotateZ(ra);
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
/*     */   public static Matrix44 ortho(double left, double right, double bottom, double top, double znear, double zfar) {
/* 456 */     return (new Matrix44()).setOrtho(left, right, bottom, top, znear, zfar);
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
/*     */   public static Matrix44 frustum(double left, double right, double bottom, double top, double znear, double zfar) {
/* 475 */     return (new Matrix44()).setFrustum(left, right, bottom, top, znear, zfar);
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
/*     */   public static Matrix44 perspective(double fovy, double aspect, double znear, double zfar) {
/* 490 */     return (new Matrix44()).setPerspective(fovy, aspect, znear, zfar);
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
/*     */   public Matrix44 setIdentity() {
/* 502 */     set(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 506 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setTranslate(double tx, double ty, double tz) {
/* 517 */     set(1.0D, 0.0D, 0.0D, tx, 0.0D, 1.0D, 0.0D, ty, 0.0D, 0.0D, 1.0D, tz, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 521 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setTranslate(Vector3 tv) {
/* 530 */     return setTranslate(tv.x, tv.y, tv.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setScale(double sx, double sy, double sz) {
/* 541 */     set(sx, 0.0D, 0.0D, 0.0D, 0.0D, sy, 0.0D, 0.0D, 0.0D, 0.0D, sz, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 545 */     return this;
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
/*     */   public Matrix44 setRotate(double ra, double rx, double ry, double rz) {
/* 558 */     double rs = 1.0D / Math.sqrt(rx * rx + ry * ry + rz * rz);
/* 559 */     rx *= rs;
/* 560 */     ry *= rs;
/* 561 */     rz *= rs;
/* 562 */     double ca = Math.cos(ra * 0.017453292519943295D);
/* 563 */     double sa = Math.sin(ra * 0.017453292519943295D);
/* 564 */     double xx = rx * rx;
/* 565 */     double xy = rx * ry;
/* 566 */     double xz = rx * rz;
/* 567 */     double yx = xy;
/* 568 */     double yy = ry * ry;
/* 569 */     double yz = ry * rz;
/* 570 */     double zx = xz;
/* 571 */     double zy = yz;
/* 572 */     double zz = rz * rz;
/* 573 */     double m00 = xx + ca * (1.0D - xx) + sa * 0.0D;
/* 574 */     double m01 = xy + ca * (0.0D - xy) + sa * -rz;
/* 575 */     double m02 = xz + ca * (0.0D - xz) + sa * ry;
/* 576 */     double m10 = yx + ca * (0.0D - yx) + sa * rz;
/* 577 */     double m11 = yy + ca * (1.0D - yy) + sa * 0.0D;
/* 578 */     double m12 = yz + ca * (0.0D - yz) + sa * -rx;
/* 579 */     double m20 = zx + ca * (0.0D - zx) + sa * -ry;
/* 580 */     double m21 = zy + ca * (0.0D - zy) + sa * rx;
/* 581 */     double m22 = zz + ca * (1.0D - zz) + sa * 0.0D;
/* 582 */     set(m00, m01, m02, 0.0D, m10, m11, m12, 0.0D, m20, m21, m22, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 586 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setRotate(double ra, Vector3 rv) {
/* 597 */     return setRotate(ra, rv.x, rv.y, rv.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setRotateX(double ra) {
/* 607 */     double ca = Math.cos(ra * 0.017453292519943295D);
/* 608 */     double sa = Math.sin(ra * 0.017453292519943295D);
/* 609 */     set(1.0D, 0.0D, 0.0D, 0.0D, 0.0D, ca, -sa, 0.0D, 0.0D, sa, ca, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 613 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setRotateY(double ra) {
/* 623 */     double ca = Math.cos(ra * 0.017453292519943295D);
/* 624 */     double sa = Math.sin(ra * 0.017453292519943295D);
/* 625 */     set(ca, 0.0D, sa, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, -sa, 0.0D, ca, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 629 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix44 setRotateZ(double ra) {
/* 639 */     double ca = Math.cos(ra * 0.017453292519943295D);
/* 640 */     double sa = Math.sin(ra * 0.017453292519943295D);
/* 641 */     set(ca, -sa, 0.0D, 0.0D, sa, ca, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 645 */     return this;
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
/*     */   public Matrix44 setOrtho(double left, double right, double bottom, double top, double znear, double zfar) {
/* 664 */     Check.argument((left != right), "left!=right");
/* 665 */     Check.argument((bottom != top), "bottom!=top");
/* 666 */     Check.argument((znear != zfar), "znear!=zfar");
/* 667 */     double tx = -(right + left) / (right - left);
/* 668 */     double ty = -(top + bottom) / (top - bottom);
/* 669 */     double tz = -(zfar + znear) / (zfar - znear);
/* 670 */     double sx = 2.0D / (right - left);
/* 671 */     double sy = 2.0D / (top - bottom);
/* 672 */     double sz = -2.0D / (zfar - znear);
/* 673 */     set(sx, 0.0D, 0.0D, tx, 0.0D, sy, 0.0D, ty, 0.0D, 0.0D, sz, tz, 0.0D, 0.0D, 0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */     
/* 677 */     return this;
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
/*     */   public Matrix44 setFrustum(double left, double right, double bottom, double top, double znear, double zfar) {
/* 696 */     Check.argument((left != right), "left!=right");
/* 697 */     Check.argument((bottom != top), "bottom!=top");
/* 698 */     Check.argument((znear != zfar), "znear!=zfar");
/* 699 */     double sx = 2.0D * znear / (right - left);
/* 700 */     double sy = 2.0D * znear / (top - bottom);
/* 701 */     double a = (right + left) / (right - left);
/* 702 */     double b = (top + bottom) / (top - bottom);
/* 703 */     double c = (znear + zfar) / (znear - zfar);
/* 704 */     double d = 2.0D * zfar * znear / (znear - zfar);
/* 705 */     set(sx, 0.0D, a, 0.0D, 0.0D, sy, b, 0.0D, 0.0D, 0.0D, c, d, 0.0D, 0.0D, -1.0D, 0.0D);
/*     */ 
/*     */ 
/*     */     
/* 709 */     return this;
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
/*     */   public Matrix44 setPerspective(double fovy, double aspect, double znear, double zfar) {
/* 724 */     double t = Math.tan(0.5D * fovy * 0.017453292519943295D);
/* 725 */     double right = t * aspect * znear;
/* 726 */     double left = -right;
/* 727 */     double top = t * znear;
/* 728 */     double bottom = -top;
/* 729 */     return setFrustum(left, right, bottom, top, znear, zfar);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 733 */     StringBuilder sb = new StringBuilder();
/* 734 */     for (int i = 0; i < 4; i++) {
/* 735 */       sb.append("| ");
/* 736 */       for (int j = 0; j < 4; j++) {
/* 737 */         sb.append(String.format("% 12.5e ", new Object[] { Double.valueOf(this.m[i + j * 4]) }));
/* 738 */       }  sb.append("|\n");
/*     */     } 
/* 740 */     return sb.toString();
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
/*     */   private static double[] mul(double[] a, double[] b, double[] c) {
/* 754 */     double a00 = a[0], a01 = a[4], a02 = a[8], a03 = a[12];
/* 755 */     double a10 = a[1], a11 = a[5], a12 = a[9], a13 = a[13];
/* 756 */     double a20 = a[2], a21 = a[6], a22 = a[10], a23 = a[14];
/* 757 */     double a30 = a[3], a31 = a[7], a32 = a[11], a33 = a[15];
/* 758 */     double b00 = b[0], b01 = b[4], b02 = b[8], b03 = b[12];
/* 759 */     double b10 = b[1], b11 = b[5], b12 = b[9], b13 = b[13];
/* 760 */     double b20 = b[2], b21 = b[6], b22 = b[10], b23 = b[14];
/* 761 */     double b30 = b[3], b31 = b[7], b32 = b[11], b33 = b[15];
/* 762 */     c[0] = a00 * b00 + a01 * b10 + a02 * b20 + a03 * b30;
/* 763 */     c[1] = a10 * b00 + a11 * b10 + a12 * b20 + a13 * b30;
/* 764 */     c[2] = a20 * b00 + a21 * b10 + a22 * b20 + a23 * b30;
/* 765 */     c[3] = a30 * b00 + a31 * b10 + a32 * b20 + a33 * b30;
/* 766 */     c[4] = a00 * b01 + a01 * b11 + a02 * b21 + a03 * b31;
/* 767 */     c[5] = a10 * b01 + a11 * b11 + a12 * b21 + a13 * b31;
/* 768 */     c[6] = a20 * b01 + a21 * b11 + a22 * b21 + a23 * b31;
/* 769 */     c[7] = a30 * b01 + a31 * b11 + a32 * b21 + a33 * b31;
/* 770 */     c[8] = a00 * b02 + a01 * b12 + a02 * b22 + a03 * b32;
/* 771 */     c[9] = a10 * b02 + a11 * b12 + a12 * b22 + a13 * b32;
/* 772 */     c[10] = a20 * b02 + a21 * b12 + a22 * b22 + a23 * b32;
/* 773 */     c[11] = a30 * b02 + a31 * b12 + a32 * b22 + a33 * b32;
/* 774 */     c[12] = a00 * b03 + a01 * b13 + a02 * b23 + a03 * b33;
/* 775 */     c[13] = a10 * b03 + a11 * b13 + a12 * b23 + a13 * b33;
/* 776 */     c[14] = a20 * b03 + a21 * b13 + a22 * b23 + a23 * b33;
/* 777 */     c[15] = a30 * b03 + a31 * b13 + a32 * b23 + a33 * b33;
/* 778 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] mult(double[] a, double[] b, double[] c) {
/* 787 */     double a00 = a[0], a01 = a[4], a02 = a[8], a03 = a[12];
/* 788 */     double a10 = a[1], a11 = a[5], a12 = a[9], a13 = a[13];
/* 789 */     double a20 = a[2], a21 = a[6], a22 = a[10], a23 = a[14];
/* 790 */     double a30 = a[3], a31 = a[7], a32 = a[11], a33 = a[15];
/* 791 */     double b00 = b[0], b01 = b[4], b02 = b[8], b03 = b[12];
/* 792 */     double b10 = b[1], b11 = b[5], b12 = b[9], b13 = b[13];
/* 793 */     double b20 = b[2], b21 = b[6], b22 = b[10], b23 = b[14];
/* 794 */     double b30 = b[3], b31 = b[7], b32 = b[11], b33 = b[15];
/* 795 */     c[0] = a00 * b00 + a01 * b01 + a02 * b02 + a03 * b03;
/* 796 */     c[1] = a10 * b00 + a11 * b01 + a12 * b02 + a13 * b03;
/* 797 */     c[2] = a20 * b00 + a21 * b01 + a22 * b02 + a23 * b03;
/* 798 */     c[3] = a30 * b00 + a31 * b01 + a32 * b02 + a33 * b03;
/* 799 */     c[4] = a00 * b10 + a01 * b11 + a02 * b12 + a03 * b13;
/* 800 */     c[5] = a10 * b10 + a11 * b11 + a12 * b12 + a13 * b13;
/* 801 */     c[6] = a20 * b10 + a21 * b11 + a22 * b12 + a23 * b13;
/* 802 */     c[7] = a30 * b10 + a31 * b11 + a32 * b12 + a33 * b13;
/* 803 */     c[8] = a00 * b20 + a01 * b21 + a02 * b22 + a03 * b23;
/* 804 */     c[9] = a10 * b20 + a11 * b21 + a12 * b22 + a13 * b23;
/* 805 */     c[10] = a20 * b20 + a21 * b21 + a22 * b22 + a23 * b23;
/* 806 */     c[11] = a30 * b20 + a31 * b21 + a32 * b22 + a33 * b23;
/* 807 */     c[12] = a00 * b30 + a01 * b31 + a02 * b32 + a03 * b33;
/* 808 */     c[13] = a10 * b30 + a11 * b31 + a12 * b32 + a13 * b33;
/* 809 */     c[14] = a20 * b30 + a21 * b31 + a22 * b32 + a23 * b33;
/* 810 */     c[15] = a30 * b30 + a31 * b31 + a32 * b32 + a33 * b33;
/* 811 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double[] tmul(double[] a, double[] b, double[] c) {
/* 820 */     double a00 = a[0], a01 = a[4], a02 = a[8], a03 = a[12];
/* 821 */     double a10 = a[1], a11 = a[5], a12 = a[9], a13 = a[13];
/* 822 */     double a20 = a[2], a21 = a[6], a22 = a[10], a23 = a[14];
/* 823 */     double a30 = a[3], a31 = a[7], a32 = a[11], a33 = a[15];
/* 824 */     double b00 = b[0], b01 = b[4], b02 = b[8], b03 = b[12];
/* 825 */     double b10 = b[1], b11 = b[5], b12 = b[9], b13 = b[13];
/* 826 */     double b20 = b[2], b21 = b[6], b22 = b[10], b23 = b[14];
/* 827 */     double b30 = b[3], b31 = b[7], b32 = b[11], b33 = b[15];
/* 828 */     c[0] = a00 * b00 + a10 * b10 + a20 * b20 + a30 * b30;
/* 829 */     c[1] = a01 * b00 + a11 * b10 + a21 * b20 + a31 * b30;
/* 830 */     c[2] = a02 * b00 + a12 * b10 + a22 * b20 + a32 * b30;
/* 831 */     c[3] = a03 * b00 + a13 * b10 + a23 * b20 + a33 * b30;
/* 832 */     c[4] = a00 * b01 + a10 * b11 + a20 * b21 + a30 * b31;
/* 833 */     c[5] = a01 * b01 + a11 * b11 + a21 * b21 + a31 * b31;
/* 834 */     c[6] = a02 * b01 + a12 * b11 + a22 * b21 + a32 * b31;
/* 835 */     c[7] = a03 * b01 + a13 * b11 + a23 * b21 + a33 * b31;
/* 836 */     c[8] = a00 * b02 + a10 * b12 + a20 * b22 + a30 * b32;
/* 837 */     c[9] = a01 * b02 + a11 * b12 + a21 * b22 + a31 * b32;
/* 838 */     c[10] = a02 * b02 + a12 * b12 + a22 * b22 + a32 * b32;
/* 839 */     c[11] = a03 * b02 + a13 * b12 + a23 * b22 + a33 * b32;
/* 840 */     c[12] = a00 * b03 + a10 * b13 + a20 * b23 + a30 * b33;
/* 841 */     c[13] = a01 * b03 + a11 * b13 + a21 * b23 + a31 * b33;
/* 842 */     c[14] = a02 * b03 + a12 * b13 + a22 * b23 + a32 * b33;
/* 843 */     c[15] = a03 * b03 + a13 * b13 + a23 * b23 + a33 * b33;
/* 844 */     return c;
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
/*     */   private static double[] invert(double[] a, double[] b) {
/* 860 */     double t00 = a[0];
/* 861 */     double t01 = a[4];
/* 862 */     double t02 = a[8];
/* 863 */     double t03 = a[12];
/* 864 */     double t04 = a[1];
/* 865 */     double t08 = a[2];
/* 866 */     double t12 = a[3];
/* 867 */     double t05 = a[5];
/* 868 */     double t09 = a[6];
/* 869 */     double t13 = a[7];
/* 870 */     double t06 = a[9];
/* 871 */     double t10 = a[10];
/* 872 */     double t14 = a[11];
/* 873 */     double t07 = a[13];
/* 874 */     double t11 = a[14];
/* 875 */     double t15 = a[15];
/*     */ 
/*     */     
/* 878 */     double u00 = t10 * t15;
/* 879 */     double u01 = t11 * t14;
/* 880 */     double u02 = t09 * t15;
/* 881 */     double u03 = t11 * t13;
/* 882 */     double u04 = t09 * t14;
/* 883 */     double u05 = t10 * t13;
/* 884 */     double u06 = t08 * t15;
/* 885 */     double u07 = t11 * t12;
/* 886 */     double u08 = t08 * t14;
/* 887 */     double u09 = t10 * t12;
/* 888 */     double u10 = t08 * t13;
/* 889 */     double u11 = t09 * t12;
/*     */ 
/*     */     
/* 892 */     b[0] = u00 * t05 + u03 * t06 + u04 * t07 - u01 * t05 - u02 * t06 - u05 * t07;
/* 893 */     b[1] = u01 * t04 + u06 * t06 + u09 * t07 - u00 * t04 - u07 * t06 - u08 * t07;
/* 894 */     b[2] = u02 * t04 + u07 * t05 + u10 * t07 - u03 * t04 - u06 * t05 - u11 * t07;
/* 895 */     b[3] = u05 * t04 + u08 * t05 + u11 * t06 - u04 * t04 - u09 * t05 - u10 * t06;
/* 896 */     b[4] = u01 * t01 + u02 * t02 + u05 * t03 - u00 * t01 - u03 * t02 - u04 * t03;
/* 897 */     b[5] = u00 * t00 + u07 * t02 + u08 * t03 - u01 * t00 - u06 * t02 - u09 * t03;
/* 898 */     b[6] = u03 * t00 + u06 * t01 + u11 * t03 - u02 * t00 - u07 * t01 - u10 * t03;
/* 899 */     b[7] = u04 * t00 + u09 * t01 + u10 * t02 - u05 * t00 - u08 * t01 - u11 * t02;
/*     */ 
/*     */     
/* 902 */     u00 = t02 * t07;
/* 903 */     u01 = t03 * t06;
/* 904 */     u02 = t01 * t07;
/* 905 */     u03 = t03 * t05;
/* 906 */     u04 = t01 * t06;
/* 907 */     u05 = t02 * t05;
/* 908 */     u06 = t00 * t07;
/* 909 */     u07 = t03 * t04;
/* 910 */     u08 = t00 * t06;
/* 911 */     u09 = t02 * t04;
/* 912 */     u10 = t00 * t05;
/* 913 */     u11 = t01 * t04;
/*     */ 
/*     */     
/* 916 */     b[8] = u00 * t13 + u03 * t14 + u04 * t15 - u01 * t13 - u02 * t14 - u05 * t15;
/* 917 */     b[9] = u01 * t12 + u06 * t14 + u09 * t15 - u00 * t12 - u07 * t14 - u08 * t15;
/* 918 */     b[10] = u02 * t12 + u07 * t13 + u10 * t15 - u03 * t12 - u06 * t13 - u11 * t15;
/* 919 */     b[11] = u05 * t12 + u08 * t13 + u11 * t14 - u04 * t12 - u09 * t13 - u10 * t14;
/* 920 */     b[12] = u02 * t10 + u05 * t11 + u01 * t09 - u04 * t11 - u00 * t09 - u03 * t10;
/* 921 */     b[13] = u08 * t11 + u00 * t08 + u07 * t10 - u06 * t10 - u09 * t11 - u01 * t08;
/* 922 */     b[14] = u06 * t09 + u11 * t11 + u03 * t08 - u10 * t11 - u02 * t08 - u07 * t09;
/* 923 */     b[15] = u10 * t10 + u04 * t08 + u09 * t09 - u08 * t09 - u11 * t10 - u05 * t08;
/*     */ 
/*     */     
/* 926 */     double d = t00 * b[0] + t01 * b[1] + t02 * b[2] + t03 * b[3];
/*     */ 
/*     */     
/* 929 */     d = 1.0D / d;
/* 930 */     b[0] = b[0] * d; b[1] = b[1] * d; b[2] = b[2] * d; b[3] = b[3] * d;
/* 931 */     b[4] = b[4] * d; b[5] = b[5] * d; b[6] = b[6] * d; b[7] = b[7] * d;
/* 932 */     b[8] = b[8] * d; b[9] = b[9] * d; b[10] = b[10] * d; b[11] = b[11] * d;
/* 933 */     b[12] = b[12] * d; b[13] = b[13] * d; b[14] = b[14] * d; b[15] = b[15] * d;
/* 934 */     return b;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Matrix44.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */