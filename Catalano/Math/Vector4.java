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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Vector4
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float w;
/*     */   
/*     */   public Vector4() {}
/*     */   
/*     */   public Vector4(float x, float y, float z, float w) {
/*  70 */     this.x = x;
/*  71 */     this.y = y;
/*  72 */     this.z = z;
/*  73 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4(float value) {
/*  81 */     this.x = this.y = this.z = this.w = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMax() {
/*  89 */     float v1 = (this.x > this.y) ? this.x : this.y;
/*  90 */     float v2 = (this.z > this.w) ? this.z : this.w;
/*     */     
/*  92 */     return (v1 > v2) ? v1 : v2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMin() {
/* 100 */     float v1 = (this.x < this.y) ? this.x : this.y;
/* 101 */     float v2 = (this.z < this.w) ? this.z : this.w;
/*     */     
/* 103 */     return (v1 < v2) ? v1 : v2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxIndex() {
/* 111 */     float v1 = 0.0F;
/* 112 */     float v2 = 0.0F;
/* 113 */     int i1 = 0;
/* 114 */     int i2 = 0;
/*     */     
/* 116 */     if (this.x >= this.y) {
/*     */       
/* 118 */       v1 = this.x;
/* 119 */       i1 = 0;
/*     */     }
/*     */     else {
/*     */       
/* 123 */       v1 = this.y;
/* 124 */       i1 = 1;
/*     */     } 
/*     */     
/* 127 */     if (this.z >= this.w) {
/*     */       
/* 129 */       v2 = this.z;
/* 130 */       i2 = 2;
/*     */     }
/*     */     else {
/*     */       
/* 134 */       v2 = this.w;
/* 135 */       i2 = 3;
/*     */     } 
/*     */     
/* 138 */     return (v1 >= v2) ? i1 : i2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinIndex() {
/* 146 */     float v1 = 0.0F;
/* 147 */     float v2 = 0.0F;
/* 148 */     int i1 = 0;
/* 149 */     int i2 = 0;
/*     */     
/* 151 */     if (this.x <= this.y) {
/*     */       
/* 153 */       v1 = this.x;
/* 154 */       i1 = 0;
/*     */     }
/*     */     else {
/*     */       
/* 158 */       v1 = this.y;
/* 159 */       i1 = 1;
/*     */     } 
/*     */     
/* 162 */     if (this.z <= this.w) {
/*     */       
/* 164 */       v2 = this.z;
/* 165 */       i2 = 2;
/*     */     }
/*     */     else {
/*     */       
/* 169 */       v2 = this.w;
/* 170 */       i2 = 3;
/*     */     } 
/*     */     
/* 173 */     return (v1 <= v2) ? i1 : i2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Norm() {
/* 181 */     return (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Square() {
/* 189 */     return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] toArray() {
/* 197 */     return new float[] { this.x, this.y, this.z, this.w };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Add(Vector4 vector1, Vector4 vector2) {
/* 207 */     return new Vector4(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z, vector1.w + vector2.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Add(Vector4 vector1, float value) {
/* 217 */     return new Vector4(vector1.x + value, vector1.y + value, vector1.z + value, vector1.w + value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Subtract(Vector4 vector1, Vector4 vector2) {
/* 227 */     return new Vector4(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z, vector1.w - vector2.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Subtract(Vector4 vector1, float value) {
/* 237 */     return new Vector4(vector1.x - value, vector1.y - value, vector1.z - value, vector1.w - value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Multiply(Vector4 vector1, Vector4 vector2) {
/* 247 */     return new Vector4(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z, vector1.w * vector2.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Multiply(Vector4 vector, float factor) {
/* 257 */     return new Vector4(vector.x * factor, vector.y * factor, vector.z * factor, vector.w * factor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Divide(Vector4 vector1, Vector4 vector2) {
/* 267 */     return new Vector4(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z, vector1.w / vector2.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vector4 Divide(Vector4 vector, float factor) {
/* 277 */     return new Vector4(vector.x / factor, vector.y / factor, vector.z / factor, vector.w / factor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Normalize() {
/* 285 */     float norm = (float)Math.sqrt((this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w));
/* 286 */     float invNorm = 1.0F / norm;
/*     */     
/* 288 */     this.x *= invNorm;
/* 289 */     this.y *= invNorm;
/* 290 */     this.z *= invNorm;
/* 291 */     this.w *= invNorm;
/*     */     
/* 293 */     return norm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4 Inverse() {
/* 302 */     return new Vector4(
/* 303 */         (this.x == 0.0F) ? 0.0F : (1.0F / this.z), 
/* 304 */         (this.y == 0.0F) ? 0.0F : (1.0F / this.y), 
/* 305 */         (this.y == 0.0F) ? 0.0F : (1.0F / this.y), 
/* 306 */         (this.w == 0.0F) ? 0.0F : (1.0F / this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector4 Abs() {
/* 314 */     return new Vector4(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float Dot(Vector4 vector1, Vector4 vector2) {
/* 325 */     return vector1.x * vector2.x + vector1.y * vector2.y + 
/* 326 */       vector1.z * vector2.z + vector1.w * vector2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector3 toVector3() {
/* 334 */     return new Vector3(this.x / this.w, this.y / this.w, this.z / this.w);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 339 */     if (obj.getClass().isAssignableFrom(Vector3.class)) {
/* 340 */       Vector4 v = (Vector4)obj;
/* 341 */       if (this.x == v.x && this.y == v.y && this.z == v.z && this.w == v.w) {
/* 342 */         return true;
/*     */       }
/*     */     } 
/* 345 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Vector4.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */