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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Matrix3x3
/*     */ {
/*     */   public float V00;
/*     */   public float V01;
/*     */   public float V02;
/*     */   public float V10;
/*     */   public float V11;
/*     */   public float V12;
/*     */   public float V20;
/*     */   public float V21;
/*     */   public float V22;
/*     */   
/*     */   public Matrix3x3() {
/*  86 */     this.V00 = this.V01 = this.V02 = 0.0F;
/*  87 */     this.V10 = this.V11 = this.V12 = 0.0F;
/*  88 */     this.V22 = this.V21 = this.V22 = 0.0F;
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
/*     */   public Matrix3x3(float V00, float V01, float V02, float V10, float V11, float V12, float V20, float V21, float V22) {
/* 104 */     this.V00 = V00;
/* 105 */     this.V01 = V01;
/* 106 */     this.V02 = V02;
/* 107 */     this.V10 = V10;
/* 108 */     this.V11 = V11;
/* 109 */     this.V12 = V12;
/* 110 */     this.V20 = V20;
/* 111 */     this.V21 = V21;
/* 112 */     this.V22 = V22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 Identity() {
/* 120 */     return new Matrix3x3(1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float Determinant() {
/* 128 */     return this.V00 * this.V11 * this.V22 + this.V01 * this.V12 * this.V20 + this.V02 * this.V10 * this.V21 - 
/* 129 */       this.V00 * this.V12 * this.V21 - this.V01 * this.V10 * this.V22 - this.V02 * this.V11 * this.V20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] toArray() {
/* 137 */     return new float[] { this.V00, this.V01, this.V02, this.V10, this.V11, this.V12, this.V20, this.V21, this.V22 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 CreateRotationY(float radians) {
/* 146 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 148 */     float cos = (float)Math.cos(radians);
/* 149 */     float sin = (float)Math.sin(radians);
/*     */     
/* 151 */     m.V00 = m.V22 = cos;
/* 152 */     m.V02 = sin;
/* 153 */     m.V20 = -sin;
/* 154 */     m.V11 = 1.0F;
/*     */     
/* 156 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 CreateRotationX(float radians) {
/* 165 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 167 */     float cos = (float)Math.cos(radians);
/* 168 */     float sin = (float)Math.sin(radians);
/*     */     
/* 170 */     m.V11 = m.V22 = cos;
/* 171 */     m.V12 = -sin;
/* 172 */     m.V21 = sin;
/* 173 */     m.V00 = 1.0F;
/*     */     
/* 175 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 CreateRotationZ(float radians) {
/* 184 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 186 */     float cos = (float)Math.cos(radians);
/* 187 */     float sin = (float)Math.sin(radians);
/*     */     
/* 189 */     m.V00 = m.V11 = cos;
/* 190 */     m.V01 = -sin;
/* 191 */     m.V10 = sin;
/* 192 */     m.V22 = 1.0F;
/*     */     
/* 194 */     return m;
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
/*     */   public static Matrix3x3 CreateFromYawPitchRoll(float yaw, float pitch, float roll) {
/* 211 */     Matrix3x3 r = Multiply(CreateRotationY(yaw), CreateRotationX(pitch));
/* 212 */     r.Multiply(CreateRotationZ(roll));
/*     */     
/* 214 */     return r;
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
/*     */   public float[] ExtractYawPitchRoll() {
/* 229 */     float[] v = new float[3];
/* 230 */     v[0] = (float)Math.atan2(this.V02, this.V22);
/* 231 */     v[1] = (float)Math.asin(-this.V12);
/* 232 */     v[2] = (float)Math.atan2(this.V10, this.V11);
/* 233 */     return v;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 CreateFromRows(Vector3 row0, Vector3 row1, Vector3 row2) {
/* 244 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 246 */     m.V00 = row0.x;
/* 247 */     m.V01 = row0.y;
/* 248 */     m.V02 = row0.z;
/*     */     
/* 250 */     m.V10 = row1.x;
/* 251 */     m.V11 = row1.y;
/* 252 */     m.V12 = row1.z;
/*     */     
/* 254 */     m.V20 = row2.x;
/* 255 */     m.V21 = row2.y;
/* 256 */     m.V22 = row2.z;
/*     */     
/* 258 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 CreateFromColumns(Vector3 column0, Vector3 column1, Vector3 column2) {
/* 269 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 271 */     m.V00 = column0.x;
/* 272 */     m.V10 = column0.y;
/* 273 */     m.V20 = column0.z;
/*     */     
/* 275 */     m.V01 = column1.x;
/* 276 */     m.V11 = column1.y;
/* 277 */     m.V21 = column1.z;
/*     */     
/* 279 */     m.V02 = column2.x;
/* 280 */     m.V12 = column2.y;
/* 281 */     m.V22 = column2.z;
/*     */     
/* 283 */     return m;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 CreateDiagonal(Vector3 vector) {
/* 292 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 294 */     m.V00 = vector.x;
/* 295 */     m.V11 = vector.y;
/* 296 */     m.V22 = vector.z;
/*     */     
/* 298 */     return m;
/*     */   }
/*     */   
/*     */   public void Multiply(Matrix3x3 matrix) {
/* 302 */     this.V00 = this.V00 * matrix.V00 + this.V01 * matrix.V10 + this.V02 * matrix.V20;
/* 303 */     this.V01 = this.V00 * matrix.V01 + this.V01 * matrix.V11 + this.V02 * matrix.V21;
/* 304 */     this.V02 = this.V00 * matrix.V02 + this.V01 * matrix.V12 + this.V02 * matrix.V22;
/*     */     
/* 306 */     this.V10 = this.V10 * matrix.V00 + this.V11 * matrix.V10 + this.V12 * matrix.V20;
/* 307 */     this.V11 = this.V10 * matrix.V01 + this.V11 * matrix.V11 + this.V12 * matrix.V21;
/* 308 */     this.V12 = this.V10 * matrix.V02 + this.V11 * matrix.V12 + this.V12 * matrix.V22;
/*     */     
/* 310 */     this.V20 = this.V20 * matrix.V00 + this.V21 * matrix.V10 + this.V22 * matrix.V20;
/* 311 */     this.V21 = this.V20 * matrix.V01 + this.V21 * matrix.V11 + this.V22 * matrix.V21;
/* 312 */     this.V22 = this.V20 * matrix.V02 + this.V21 * matrix.V12 + this.V22 * matrix.V22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 Multiply(Matrix3x3 matrix1, Matrix3x3 matrix2) {
/* 322 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 324 */     m.V00 = matrix1.V00 * matrix2.V00 + matrix1.V01 * matrix2.V10 + matrix1.V02 * matrix2.V20;
/* 325 */     m.V01 = matrix1.V00 * matrix2.V01 + matrix1.V01 * matrix2.V11 + matrix1.V02 * matrix2.V21;
/* 326 */     m.V02 = matrix1.V00 * matrix2.V02 + matrix1.V01 * matrix2.V12 + matrix1.V02 * matrix2.V22;
/*     */     
/* 328 */     m.V10 = matrix1.V10 * matrix2.V00 + matrix1.V11 * matrix2.V10 + matrix1.V12 * matrix2.V20;
/* 329 */     m.V11 = matrix1.V10 * matrix2.V01 + matrix1.V11 * matrix2.V11 + matrix1.V12 * matrix2.V21;
/* 330 */     m.V12 = matrix1.V10 * matrix2.V02 + matrix1.V11 * matrix2.V12 + matrix1.V12 * matrix2.V22;
/*     */     
/* 332 */     m.V20 = matrix1.V20 * matrix2.V00 + matrix1.V21 * matrix2.V10 + matrix1.V22 * matrix2.V20;
/* 333 */     m.V21 = matrix1.V20 * matrix2.V01 + matrix1.V21 * matrix2.V11 + matrix1.V22 * matrix2.V21;
/* 334 */     m.V22 = matrix1.V20 * matrix2.V02 + matrix1.V21 * matrix2.V12 + matrix1.V22 * matrix2.V22;
/*     */     
/* 336 */     return m;
/*     */   }
/*     */   
/*     */   public void Add(Matrix3x3 matrix) {
/* 340 */     this.V00 += matrix.V00;
/* 341 */     this.V01 += matrix.V01;
/* 342 */     this.V02 += matrix.V02;
/*     */     
/* 344 */     this.V10 += matrix.V10;
/* 345 */     this.V11 += matrix.V11;
/* 346 */     this.V12 += matrix.V12;
/*     */     
/* 348 */     this.V20 += matrix.V20;
/* 349 */     this.V21 += matrix.V21;
/* 350 */     this.V22 += matrix.V22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 Add(Matrix3x3 matrix1, Matrix3x3 matrix2) {
/* 360 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 362 */     matrix1.V00 += matrix2.V00;
/* 363 */     matrix1.V01 += matrix2.V01;
/* 364 */     matrix1.V02 += matrix2.V02;
/*     */     
/* 366 */     matrix1.V10 += matrix2.V10;
/* 367 */     matrix1.V11 += matrix2.V11;
/* 368 */     matrix1.V12 += matrix2.V12;
/*     */     
/* 370 */     matrix1.V20 += matrix2.V20;
/* 371 */     matrix1.V21 += matrix2.V21;
/* 372 */     matrix1.V22 += matrix2.V22;
/*     */     
/* 374 */     return m;
/*     */   }
/*     */   
/*     */   public void Subtract(Matrix3x3 matrix) {
/* 378 */     this.V00 -= matrix.V00;
/* 379 */     this.V01 -= matrix.V01;
/* 380 */     this.V02 -= matrix.V02;
/*     */     
/* 382 */     this.V10 -= matrix.V10;
/* 383 */     this.V11 -= matrix.V11;
/* 384 */     this.V12 -= matrix.V12;
/*     */     
/* 386 */     this.V20 -= matrix.V20;
/* 387 */     this.V21 -= matrix.V21;
/* 388 */     this.V22 -= matrix.V22;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Matrix3x3 Subtract(Matrix3x3 matrix1, Matrix3x3 matrix2) {
/* 398 */     Matrix3x3 m = new Matrix3x3();
/*     */     
/* 400 */     matrix1.V00 -= matrix2.V00;
/* 401 */     matrix1.V01 -= matrix2.V01;
/* 402 */     matrix1.V02 -= matrix2.V02;
/*     */     
/* 404 */     matrix1.V10 -= matrix2.V10;
/* 405 */     matrix1.V11 -= matrix2.V11;
/* 406 */     matrix1.V12 -= matrix2.V12;
/*     */     
/* 408 */     matrix1.V20 -= matrix2.V20;
/* 409 */     matrix1.V21 -= matrix2.V21;
/* 410 */     matrix1.V22 -= matrix2.V22;
/*     */     
/* 412 */     return m;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Matrix3x3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */