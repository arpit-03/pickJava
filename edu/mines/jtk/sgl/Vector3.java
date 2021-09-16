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
/*     */ public class Vector3
/*     */   extends Tuple3
/*     */ {
/*     */   public Vector3() {}
/*     */   
/*     */   public Vector3(double x, double y, double z) {
/*  37 */     super(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3(Vector3 v) {
/*  45 */     super(v.x, v.y, v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double length() {
/*  53 */     return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double lengthSquared() {
/*  61 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 negate() {
/*  69 */     return new Vector3(-this.x, -this.y, -this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 negateEquals() {
/*  77 */     this.x = -this.x;
/*  78 */     this.y = -this.y;
/*  79 */     this.z = -this.z;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 normalize() {
/*  88 */     double d = length();
/*  89 */     double s = (d > 0.0D) ? (1.0D / d) : 1.0D;
/*  90 */     return new Vector3(this.x * s, this.y * s, this.z * s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 normalizeEquals() {
/*  98 */     double d = length();
/*  99 */     double s = (d > 0.0D) ? (1.0D / d) : 1.0D;
/* 100 */     this.x *= s;
/* 101 */     this.y *= s;
/* 102 */     this.z *= s;
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 plus(Vector3 v) {
/* 112 */     return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 plusEquals(Vector3 v) {
/* 121 */     this.x += v.x;
/* 122 */     this.y += v.y;
/* 123 */     this.z += v.z;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 minus(Vector3 v) {
/* 133 */     return new Vector3(this.x - v.x, this.y - v.y, this.z - v.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 minusEquals(Vector3 v) {
/* 142 */     this.x -= v.x;
/* 143 */     this.y -= v.y;
/* 144 */     this.z -= v.z;
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 times(double s) {
/* 154 */     return new Vector3(this.x * s, this.y * s, this.z * s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 timesEquals(double s) {
/* 163 */     this.x *= s;
/* 164 */     this.y *= s;
/* 165 */     this.z *= s;
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double dot(Vector3 v) {
/* 175 */     return this.x * v.x + this.y * v.y + this.z * v.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 cross(Vector3 v) {
/* 184 */     return new Vector3(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/sgl/Vector3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */