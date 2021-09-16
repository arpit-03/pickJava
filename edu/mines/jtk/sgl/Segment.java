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
/*     */ public class Segment
/*     */ {
/*     */   private static final double TINY = 2.220446049250313E-13D;
/*     */   private Point3 _a;
/*     */   private Point3 _b;
/*     */   private Vector3 _d;
/*     */   
/*     */   public Segment(Point3 a, Point3 b) {
/*  32 */     this._a = new Point3(a);
/*  33 */     this._b = new Point3(b);
/*  34 */     this._d = this._b.minus(this._a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Segment(Segment ls) {
/*  42 */     this(ls._a, ls._b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getA() {
/*  50 */     return new Point3(this._a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 getB() {
/*  58 */     return new Point3(this._b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/*  66 */     return this._a.distanceTo(this._b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void transform(Matrix44 m) {
/*  74 */     this._a = m.times(this._a);
/*  75 */     this._b = m.times(this._b);
/*  76 */     this._d = this._b.minus(this._a);
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
/*     */   public Point3 intersectWithTriangle(double xa, double ya, double za, double xb, double yb, double zb, double xc, double yc, double zc) {
/*  98 */     double xd = this._d.x;
/*  99 */     double yd = this._d.y;
/* 100 */     double zd = this._d.z;
/* 101 */     double xba = xb - xa;
/* 102 */     double yba = yb - ya;
/* 103 */     double zba = zb - za;
/* 104 */     double xca = xc - xa;
/* 105 */     double yca = yc - ya;
/* 106 */     double zca = zc - za;
/* 107 */     double xp = yd * zca - zd * yca;
/* 108 */     double yp = zd * xca - xd * zca;
/* 109 */     double zp = xd * yca - yd * xca;
/* 110 */     double a = xba * xp + yba * yp + zba * zp;
/* 111 */     if (-2.220446049250313E-13D < a && a < 2.220446049250313E-13D)
/* 112 */       return null; 
/* 113 */     double f = 1.0D / a;
/* 114 */     double xaa = this._a.x - xa;
/* 115 */     double yaa = this._a.y - ya;
/* 116 */     double zaa = this._a.z - za;
/* 117 */     double u = f * (xaa * xp + yaa * yp + zaa * zp);
/* 118 */     if (u < 0.0D || u > 1.0D)
/* 119 */       return null; 
/* 120 */     double xq = yaa * zba - zaa * yba;
/* 121 */     double yq = zaa * xba - xaa * zba;
/* 122 */     double zq = xaa * yba - yaa * xba;
/* 123 */     double v = f * (xd * xq + yd * yq + zd * zq);
/* 124 */     if (v < 0.0D || u + v > 1.0D)
/* 125 */       return null; 
/* 126 */     double t = f * (xca * xq + yca * yq + zca * zq);
/* 127 */     if (t < 0.0D || 1.0D < t)
/* 128 */       return null; 
/* 129 */     double w = 1.0D - u - v;
/* 130 */     double xi = w * xa + u * xb + v * xc;
/* 131 */     double yi = w * ya + u * yb + v * yc;
/* 132 */     double zi = w * za + u * zb + v * zc;
/* 133 */     return new Point3(xi, yi, zi);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Segment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */