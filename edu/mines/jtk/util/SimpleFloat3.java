/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleFloat3
/*     */   implements Float3
/*     */ {
/*     */   private int _n1;
/*     */   private int _n2;
/*     */   private int _n3;
/*     */   private float[][][] _a;
/*     */   
/*     */   public SimpleFloat3(int n1, int n2, int n3) {
/*  33 */     this._n1 = n1;
/*  34 */     this._n2 = n2;
/*  35 */     this._n3 = n3;
/*  36 */     this._a = new float[n3][n2][n1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleFloat3(float[][][] a) {
/*  45 */     this._n1 = (a[0][0]).length;
/*  46 */     this._n2 = (a[0]).length;
/*  47 */     this._n3 = a.length;
/*  48 */     this._a = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getN1() {
/*  55 */     return this._n1;
/*     */   }
/*     */   
/*     */   public int getN2() {
/*  59 */     return this._n2;
/*     */   }
/*     */   
/*     */   public int getN3() {
/*  63 */     return this._n3;
/*     */   }
/*     */   
/*     */   public void get1(int m1, int j1, int j2, int j3, float[] s) {
/*  67 */     float[] a32 = this._a[j3][j2];
/*  68 */     for (int i1 = 0; i1 < m1; i1++) {
/*  69 */       s[i1] = a32[i1 + j1];
/*     */     }
/*     */   }
/*     */   
/*     */   public void get2(int m2, int j1, int j2, int j3, float[] s) {
/*  74 */     float[][] a3 = this._a[j3];
/*  75 */     for (int i2 = 0; i2 < m2; i2++) {
/*  76 */       s[i2] = a3[i2 + j2][j1];
/*     */     }
/*     */   }
/*     */   
/*     */   public void get3(int m3, int j1, int j2, int j3, float[] s) {
/*  81 */     for (int i3 = 0; i3 < m3; i3++) {
/*  82 */       s[i3] = this._a[i3 + j3][j2][j1];
/*     */     }
/*     */   }
/*     */   
/*     */   public void get12(int m1, int m2, int j1, int j2, int j3, float[][] s) {
/*  87 */     for (int i2 = 0; i2 < m2; i2++) {
/*  88 */       float[] a32 = this._a[j3][i2 + j2];
/*  89 */       float[] s2 = s[i2];
/*  90 */       for (int i1 = 0; i1 < m1; i1++) {
/*  91 */         s2[i1] = a32[i1 + j1];
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void get13(int m1, int m3, int j1, int j2, int j3, float[][] s) {
/*  97 */     for (int i3 = 0; i3 < m3; i3++) {
/*  98 */       float[] a32 = this._a[i3 + j3][j2];
/*  99 */       float[] s3 = s[i3];
/* 100 */       for (int i1 = 0; i1 < m1; i1++) {
/* 101 */         s3[i1] = a32[i1 + j1];
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void get23(int m2, int m3, int j1, int j2, int j3, float[][] s) {
/* 107 */     for (int i3 = 0; i3 < m3; i3++) {
/* 108 */       float[][] a3 = this._a[i3 + j3];
/* 109 */       float[] s3 = s[i3];
/* 110 */       for (int i2 = 0; i2 < m2; i2++) {
/* 111 */         s3[i2] = a3[i2 + j2][j1];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void get123(int m1, int m2, int m3, int j1, int j2, int j3, float[][][] s) {
/* 121 */     for (int i3 = 0; i3 < m3; i3++) {
/* 122 */       float[][] a3 = this._a[i3 + j3];
/* 123 */       float[][] s3 = s[i3];
/* 124 */       for (int i2 = 0; i2 < m2; i2++) {
/* 125 */         float[] a32 = a3[i2 + j2];
/* 126 */         float[] s32 = s3[i2];
/* 127 */         for (int i1 = 0; i1 < m1; i1++) {
/* 128 */           s32[i1] = a32[i1 + j1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void get123(int m1, int m2, int m3, int j1, int j2, int j3, float[] s) {
/* 139 */     for (int i3 = 0, is = 0; i3 < m3; i3++) {
/* 140 */       float[][] a3 = this._a[i3 + j3];
/* 141 */       for (int i2 = 0; i2 < m2; i2++) {
/* 142 */         float[] a32 = a3[i2 + j2];
/* 143 */         for (int i1 = 0; i1 < m1; i1++) {
/* 144 */           s[is++] = a32[i1 + j1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set1(int m1, int j1, int j2, int j3, float[] s) {
/* 151 */     float[] a32 = this._a[j3][j2];
/* 152 */     for (int i1 = 0; i1 < m1; i1++) {
/* 153 */       a32[i1 + j1] = s[i1];
/*     */     }
/*     */   }
/*     */   
/*     */   public void set2(int m2, int j1, int j2, int j3, float[] s) {
/* 158 */     float[][] a3 = this._a[j3];
/* 159 */     for (int i2 = 0; i2 < m2; i2++) {
/* 160 */       a3[i2 + j2][j1] = s[i2];
/*     */     }
/*     */   }
/*     */   
/*     */   public void set3(int m3, int j1, int j2, int j3, float[] s) {
/* 165 */     for (int i3 = 0; i3 < m3; i3++) {
/* 166 */       this._a[i3 + j3][j2][j1] = s[i3];
/*     */     }
/*     */   }
/*     */   
/*     */   public void set12(int m1, int m2, int j1, int j2, int j3, float[][] s) {
/* 171 */     for (int i2 = 0; i2 < m2; i2++) {
/* 172 */       float[] a32 = this._a[j3][i2 + j2];
/* 173 */       float[] s2 = s[i2];
/* 174 */       for (int i1 = 0; i1 < m1; i1++) {
/* 175 */         a32[i1 + j1] = s2[i1];
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set13(int m1, int m3, int j1, int j2, int j3, float[][] s) {
/* 181 */     for (int i3 = 0; i3 < m3; i3++) {
/* 182 */       float[] a32 = this._a[i3 + j3][j2];
/* 183 */       float[] s3 = s[i3];
/* 184 */       for (int i1 = 0; i1 < m1; i1++) {
/* 185 */         a32[i1 + j1] = s3[i1];
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void set23(int m2, int m3, int j1, int j2, int j3, float[][] s) {
/* 191 */     for (int i3 = 0; i3 < m3; i3++) {
/* 192 */       float[][] a3 = this._a[i3 + j3];
/* 193 */       float[] s3 = s[i3];
/* 194 */       for (int i2 = 0; i2 < m2; i2++) {
/* 195 */         a3[i2 + j2][j1] = s3[i2];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set123(int m1, int m2, int m3, int j1, int j2, int j3, float[][][] s) {
/* 205 */     for (int i3 = 0; i3 < m3; i3++) {
/* 206 */       float[][] a3 = this._a[i3 + j3];
/* 207 */       float[][] s3 = s[i3];
/* 208 */       for (int i2 = 0; i2 < m2; i2++) {
/* 209 */         float[] a32 = a3[i2 + j2];
/* 210 */         float[] s32 = s3[i2];
/* 211 */         for (int i1 = 0; i1 < m1; i1++) {
/* 212 */           a32[i1 + j1] = s32[i1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set123(int m1, int m2, int m3, int j1, int j2, int j3, float[] s) {
/* 223 */     for (int i3 = 0, is = 0; i3 < m3; i3++) {
/* 224 */       float[][] a3 = this._a[i3 + j3];
/* 225 */       for (int i2 = 0; i2 < m2; i2++) {
/* 226 */         float[] a32 = a3[i2 + j2];
/* 227 */         for (int i1 = 0; i1 < m1; i1++)
/* 228 */           a32[i1 + j1] = s[is++]; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/SimpleFloat3.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */