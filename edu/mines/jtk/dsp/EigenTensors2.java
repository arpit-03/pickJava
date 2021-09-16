/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EigenTensors2
/*     */   implements Tensors2
/*     */ {
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private float[][] _au;
/*     */   private float[][] _av;
/*     */   private float[][] _u1;
/*     */   private float[][] _u2;
/*     */   
/*     */   public EigenTensors2(int n1, int n2) {
/*  51 */     this._n1 = n1;
/*  52 */     this._n2 = n2;
/*  53 */     this._au = new float[n2][n1];
/*  54 */     this._av = new float[n2][n1];
/*  55 */     this._u1 = new float[n2][n1];
/*  56 */     this._u2 = new float[n2][n1];
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
/*     */   public EigenTensors2(float[][] u1, float[][] u2, float[][] au, float[][] av) {
/*  70 */     this((u1[0]).length, u1.length);
/*  71 */     for (int i2 = 0; i2 < this._n2; i2++) {
/*  72 */       for (int i1 = 0; i1 < this._n1; i1++) {
/*  73 */         float aui = au[i2][i1];
/*  74 */         float avi = av[i2][i1];
/*  75 */         float u1i = u1[i2][i1];
/*  76 */         float u2i = u2[i2][i1];
/*  77 */         setEigenvalues(i1, i2, aui, avi);
/*  78 */         setEigenvectorU(i1, i2, u1i, u2i);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EigenTensors2(EigenTensors2 t) {
/*  88 */     this(t._u1, t._u2, t._au, t._av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN1() {
/*  96 */     return this._n1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN2() {
/* 104 */     return this._n2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getTensor(int i1, int i2, float[] a) {
/* 114 */     float au = this._au[i2][i1];
/* 115 */     float av = this._av[i2][i1];
/* 116 */     float u1 = this._u1[i2][i1];
/* 117 */     float u2 = this._u2[i2][i1];
/* 118 */     au -= av;
/* 119 */     a[0] = au * u1 * u1 + av;
/* 120 */     a[1] = au * u1 * u2;
/* 121 */     a[2] = au * u2 * u2 + av;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getTensor(int i1, int i2) {
/* 131 */     float[] a = new float[3];
/* 132 */     getTensor(i1, i2, a);
/* 133 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvalues(int i1, int i2, float[] a) {
/* 144 */     a[0] = this._au[i2][i1];
/* 145 */     a[1] = this._av[i2][i1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getEigenvalues(int i1, int i2) {
/* 155 */     float[] a = new float[2];
/* 156 */     getEigenvalues(i1, i2, a);
/* 157 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvalues(float[][] au, float[][] av) {
/* 166 */     ArrayMath.copy(this._au, au);
/* 167 */     ArrayMath.copy(this._av, av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvectorU(int i1, int i2, float[] u) {
/* 177 */     u[0] = this._u1[i2][i1];
/* 178 */     u[1] = this._u2[i2][i1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getEigenvectorU(int i1, int i2) {
/* 188 */     float[] u = new float[2];
/* 189 */     getEigenvectorU(i1, i2, u);
/* 190 */     return u;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getEigenvectorV(int i1, int i2, float[] v) {
/* 200 */     v[0] = this._u2[i2][i1];
/* 201 */     v[1] = -this._u1[i2][i1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] getEigenvectorV(int i1, int i2) {
/* 211 */     float[] v = new float[2];
/* 212 */     getEigenvectorV(i1, i2, v);
/* 213 */     return v;
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
/*     */   public void setTensor(int i1, int i2, float[] a) {
/* 226 */     setTensor(i1, i2, a[0], a[1], a[2]);
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
/*     */   public void setTensor(int i1, int i2, float a11, float a12, float a22) {
/* 241 */     float[][] aa = { { a11, a12 }, { a12, a22 } };
/*     */ 
/*     */ 
/*     */     
/* 245 */     float[][] vv = new float[2][2];
/* 246 */     float[] ev = new float[2];
/* 247 */     Eigen.solveSymmetric22(aa, vv, ev);
/* 248 */     float[] u = vv[0];
/* 249 */     float au = ev[0]; if (au < 0.0F) au = 0.0F; 
/* 250 */     float av = ev[1]; if (av < 0.0F) av = 0.0F; 
/* 251 */     setEigenvectorU(i1, i2, u);
/* 252 */     setEigenvalues(i1, i2, au, av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(float au, float av) {
/* 261 */     ArrayMath.fill(au, this._au);
/* 262 */     ArrayMath.fill(av, this._av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(int i1, int i2, float au, float av) {
/* 273 */     this._au[i2][i1] = au;
/* 274 */     this._av[i2][i1] = av;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(int i1, int i2, float[] a) {
/* 284 */     setEigenvalues(i1, i2, a[0], a[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvalues(float[][] au, float[][] av) {
/* 293 */     ArrayMath.copy(au, this._au);
/* 294 */     ArrayMath.copy(av, this._av);
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
/*     */   public void setEigenvectorU(int i1, int i2, float u1, float u2) {
/* 306 */     this._u1[i2][i1] = u1;
/* 307 */     this._u2[i2][i1] = u2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEigenvectorU(int i1, int i2, float[] u) {
/* 318 */     setEigenvectorU(i1, i2, u[0], u[1]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scale(float[][] s) {
/* 326 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 327 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 328 */         float si = s[i2][i1];
/* 329 */         this._au[i2][i1] = this._au[i2][i1] * si;
/* 330 */         this._av[i2][i1] = this._av[i2][i1] * si;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invert() {
/* 340 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 341 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 342 */         this._au[i2][i1] = 1.0F / this._au[i2][i1];
/* 343 */         this._av[i2][i1] = 1.0F / this._av[i2][i1];
/*     */       } 
/*     */     } 
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
/*     */   public void invertStructure(double p0, double p1) {
/* 375 */     float amax = 0.0F;
/* 376 */     float amin = Float.MAX_VALUE;
/* 377 */     for (int i2 = 0; i2 < this._n2; i2++) {
/* 378 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 379 */         float aui = this._au[i2][i1];
/* 380 */         float avi = this._av[i2][i1];
/* 381 */         if (avi < 0.0F) avi = 0.0F; 
/* 382 */         if (aui < avi) aui = avi; 
/* 383 */         if (avi < amin) amin = avi; 
/* 384 */         if (aui > amax) amax = aui; 
/* 385 */         this._au[i2][i1] = aui;
/* 386 */         this._av[i2][i1] = avi;
/*     */       } 
/*     */     } 
/*     */     
/* 390 */     float aeps = ArrayMath.max(1.4E-43F, 1.1920929E-7F * amax);
/* 391 */     amin += aeps;
/* 392 */     amax += aeps;
/* 393 */     float fp0 = (float)p0;
/* 394 */     float fp1 = (float)p1;
/* 395 */     for (int i = 0; i < this._n2; i++) {
/* 396 */       for (int i1 = 0; i1 < this._n1; i1++) {
/* 397 */         float aui = this._au[i][i1] + aeps;
/* 398 */         float avi = this._av[i][i1] + aeps;
/* 399 */         float a0i = ArrayMath.pow(amin / avi, fp0);
/* 400 */         float a1i = ArrayMath.pow(avi / aui, fp1);
/* 401 */         this._au[i][i1] = a0i * a1i;
/* 402 */         this._av[i][i1] = a0i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/EigenTensors2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */