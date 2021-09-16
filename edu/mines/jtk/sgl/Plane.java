/*     */ package edu.mines.jtk.sgl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Plane
/*     */ {
/*     */   private double _a;
/*     */   private double _b;
/*     */   private double _c;
/*     */   private double _d;
/*     */   
/*     */   public Plane(double a, double b, double c, double d) {
/*  39 */     set(a, b, c, d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plane(Point3 p, Vector3 n) {
/*  50 */     set(n.x, n.y, n.z, -(n.x * p.x + n.y * p.y + n.z * p.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Plane(Plane p) {
/*  58 */     this._a = p._a;
/*  59 */     this._b = p._b;
/*  60 */     this._c = p._c;
/*  61 */     this._d = p._d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(double a, double b, double c, double d) {
/*  72 */     this._a = a;
/*  73 */     this._b = b;
/*  74 */     this._c = c;
/*  75 */     this._d = d;
/*  76 */     normalize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getA() {
/*  84 */     return this._a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getB() {
/*  92 */     return this._b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getC() {
/* 100 */     return this._c;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getD() {
/* 108 */     return this._d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 getNormal() {
/* 117 */     return new Vector3(this._a, this._b, this._c);
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
/*     */   public double distanceTo(double x, double y, double z) {
/* 130 */     return this._a * x + this._b * y + this._c * z + this._d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(Point3 p) {
/* 141 */     return distanceTo(p.x, p.y, p.z);
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
/*     */   public void transform(Matrix44 m) {
/* 159 */     transformWithInverse(m.inverse());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transformWithInverse(Matrix44 mi) {
/* 170 */     double[] m = mi.m;
/* 171 */     double a = m[0] * this._a + m[1] * this._b + m[2] * this._c + m[3] * this._d;
/* 172 */     double b = m[4] * this._a + m[5] * this._b + m[6] * this._c + m[7] * this._d;
/* 173 */     double c = m[8] * this._a + m[9] * this._b + m[10] * this._c + m[11] * this._d;
/* 174 */     double d = m[12] * this._a + m[13] * this._b + m[14] * this._c + m[15] * this._d;
/* 175 */     set(a, b, c, d);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 179 */     return "(" + this._a + "," + this._b + "," + this._c + "," + this._d + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void normalize() {
/* 188 */     double s = 1.0D / Math.sqrt(this._a * this._a + this._b * this._b + this._c * this._c);
/* 189 */     this._a *= s;
/* 190 */     this._b *= s;
/* 191 */     this._c *= s;
/* 192 */     this._d *= s;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Plane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */