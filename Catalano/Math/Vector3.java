/*     */ package Catalano.Math;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public Vector3() {}
/*     */   
/*     */   public Vector3(float x, float y, float z) {
/*  64 */     this.x = x;
/*  65 */     this.y = y;
/*  66 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3(float value) {
/*  74 */     this.x = this.y = this.z = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMax() {
/*  82 */     return (this.x > this.y) ? ((this.x > this.z) ? this.x : this.z) : ((this.y > this.z) ? this.y : this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMin() {
/*  90 */     return (this.x < this.y) ? ((this.x < this.z) ? this.x : this.z) : ((this.y < this.z) ? this.y : this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIndex() {
/*  98 */     return (this.x >= this.y) ? ((this.x >= this.z) ? 0 : 2) : ((this.y >= this.z) ? 1 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinIndex() {
/* 106 */     return (this.x <= this.y) ? ((this.x <= this.z) ? 0 : 2) : ((this.y <= this.z) ? 1 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Norm() {
/* 114 */     return (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Square() {
/* 122 */     return this.x * this.x + this.y * this.y + this.z * this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] toArray() {
/* 130 */     return new float[] { this.x, this.y, this.z };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Add(Vector3 vector1, Vector3 vector2) {
/* 140 */     return new Vector3(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Add(Vector3 vector, float value) {
/* 150 */     return new Vector3(vector.x + value, vector.y + value, vector.z + value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Subtract(Vector3 vector1, Vector3 vector2) {
/* 160 */     return new Vector3(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Subtract(Vector3 vector, float value) {
/* 170 */     return new Vector3(vector.x - value, vector.y - value, vector.z - value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Multiply(Vector3 vector1, Vector3 vector2) {
/* 180 */     return new Vector3(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Multiply(Vector3 vector, float factor) {
/* 190 */     return new Vector3(vector.x * factor, vector.y * factor, vector.z * factor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Divide(Vector3 vector1, Vector3 vector2) {
/* 200 */     return new Vector3(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Divide(Vector3 vector, float factor) {
/* 210 */     return new Vector3(vector.x / factor, vector.y / factor, vector.z / factor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Normalize() {
/* 218 */     float norm = (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z));
/* 219 */     float invNorm = 1.0F / norm;
/*     */     
/* 221 */     this.x *= invNorm;
/* 222 */     this.y *= invNorm;
/* 223 */     this.z *= invNorm;
/*     */     
/* 225 */     return norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 Inverse() {
/* 234 */     return new Vector3(
/* 235 */         (this.x == 0.0F) ? 0.0F : (1.0F / this.z), 
/* 236 */         (this.y == 0.0F) ? 0.0F : (1.0F / this.y), 
/* 237 */         (this.z == 0.0F) ? 0.0F : (1.0F / this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 Abs() {
/* 245 */     return new Vector3(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector3 Cross(Vector3 vector1, Vector3 vector2) {
/* 255 */     return new Vector3(
/* 256 */         vector1.y * vector2.z - vector1.z * vector2.y, 
/* 257 */         vector1.z * vector2.x - vector1.x * vector2.z, 
/* 258 */         vector1.x * vector2.y - vector1.y * vector2.x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Dot(Vector3 vector1, Vector3 vector2) {
/* 268 */     return vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z;
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
/*     */   public Vector4 toVector4() {
/* 280 */     return new Vector4(this.x, this.y, this.z, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 285 */     if (obj.getClass().isAssignableFrom(Vector3.class)) {
/* 286 */       Vector3 v = (Vector3)obj;
/* 287 */       if (this.x == v.x && this.y == v.y && this.z == v.z) {
/* 288 */         return true;
/*     */       }
/*     */     } 
/* 291 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Vector3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */