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
/*     */ public class Point3
/*     */   extends Tuple3
/*     */ {
/*     */   public Point3() {}
/*     */   
/*     */   public Point3(double x, double y, double z) {
/*  37 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3(Point4 p) {
/*  46 */     super(p.x / p.w, p.y / p.w, p.z / p.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3(Point3 p) {
/*  54 */     super(p.x, p.y, p.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 plus(Vector3 v) {
/*  63 */     return new Point3(this.x + v.x, this.y + v.y, this.z + v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 minus(Vector3 v) {
/*  72 */     return new Point3(this.x - v.x, this.y - v.y, this.z - v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 minus(Point3 q) {
/*  81 */     return new Vector3(this.x - q.x, this.y - q.y, this.z - q.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 plusEquals(Vector3 v) {
/*  90 */     this.x += v.x;
/*  91 */     this.y += v.y;
/*  92 */     this.z += v.z;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 minusEquals(Vector3 v) {
/* 102 */     this.x -= v.x;
/* 103 */     this.y -= v.y;
/* 104 */     this.z -= v.z;
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point3 affine(double a, Point3 q) {
/* 115 */     double b = 1.0D - a;
/* 116 */     Point3 p = this;
/* 117 */     return new Point3(b * p.x + a * q.x, b * p.y + a * q.y, b * p.z + a * q.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(Point3 q) {
/* 126 */     double dx = this.x - q.x;
/* 127 */     double dy = this.y - q.y;
/* 128 */     double dz = this.z - q.z;
/* 129 */     return Math.sqrt(dx * dx + dy * dy + dz * dz);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Point3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */