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
/*     */ public class Quantiler
/*     */ {
/*     */   private float _q;
/*     */   private float _fnull;
/*     */   private double _m0;
/*     */   private double _m1;
/*     */   private double _m2;
/*     */   private double _m3;
/*     */   private double _m4;
/*     */   private double _q0;
/*     */   private double _q1;
/*     */   private double _q2;
/*     */   private double _q3;
/*     */   private double _q4;
/*     */   private double _f1;
/*     */   private double _f2;
/*     */   private double _f3;
/*     */   private double _d1;
/*     */   private double _d2;
/*     */   private double _d3;
/*     */   private boolean _ignoreNull;
/*     */   private boolean _inited;
/*     */   
/*     */   public Quantiler(float q) {
/*  45 */     Check.argument((0.0F <= q), "0.0f<=q");
/*  46 */     Check.argument((q <= 1.0F), "q<=1.0f");
/*  47 */     this._q = q;
/*  48 */     this._m0 = -1.0D;
/*  49 */     this._q2 = 0.0D;
/*  50 */     this._inited = (this._q == 0.0D || this._q == 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quantiler(float q, float fnull) {
/*  59 */     Check.argument((0.0F <= q), "0.0f<=q");
/*  60 */     Check.argument((q <= 1.0F), "q<=1.0f");
/*  61 */     this._q = q;
/*  62 */     this._fnull = fnull;
/*  63 */     this._ignoreNull = true;
/*  64 */     this._m0 = -1.0D;
/*  65 */     this._q2 = fnull;
/*  66 */     this._inited = (this._q == 0.0D || this._q == 1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float estimate() {
/*  74 */     return (float)this._q2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float update(float f) {
/*  83 */     if (!this._inited) {
/*  84 */       initOne(f);
/*     */     } else {
/*  86 */       updateOne(f);
/*     */     } 
/*  88 */     return estimate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float update(float[] f) {
/*  97 */     int n = f.length;
/*  98 */     int i = 0;
/*  99 */     for (; !this._inited && i < n; i++)
/* 100 */       initOne(f[i]); 
/* 101 */     for (; i < n; i++)
/* 102 */       updateOne(f[i]); 
/* 103 */     return estimate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float update(float[][] f) {
/* 112 */     int n = f.length;
/* 113 */     for (int i = 0; i < n; i++)
/* 114 */       update(f[i]); 
/* 115 */     return estimate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float update(float[][][] f) {
/* 124 */     int n = f.length;
/* 125 */     for (int i = 0; i < n; i++)
/* 126 */       update(f[i]); 
/* 127 */     return estimate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float estimate(float q, float[] f) {
/* 137 */     Quantiler qu = new Quantiler(q);
/* 138 */     return qu.update(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float estimate(float q, float[][] f) {
/* 148 */     Quantiler qu = new Quantiler(q);
/* 149 */     return qu.update(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float estimate(float q, float[][][] f) {
/* 159 */     Quantiler qu = new Quantiler(q);
/* 160 */     return qu.update(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float estimate(float q, float fnull, float[] f) {
/* 171 */     Quantiler qu = new Quantiler(q, fnull);
/* 172 */     return qu.update(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float estimate(float q, float fnull, float[][] f) {
/* 183 */     Quantiler qu = new Quantiler(q, fnull);
/* 184 */     return qu.update(f);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float estimate(float q, float fnull, float[][][] f) {
/* 195 */     Quantiler qu = new Quantiler(q, fnull);
/* 196 */     return qu.update(f);
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
/*     */   private void initOne(float f) {
/* 214 */     if (this._ignoreNull && f == this._fnull) {
/*     */       return;
/*     */     }
/*     */     
/* 218 */     if (this._m0 < 0.0D) {
/* 219 */       this._m0 = 0.0D;
/* 220 */       this._q0 = f;
/* 221 */     } else if (this._m1 == 0.0D) {
/* 222 */       this._m1 = 1.0D;
/* 223 */       this._q1 = f;
/* 224 */     } else if (this._m2 == 0.0D) {
/* 225 */       this._m2 = 2.0D;
/* 226 */       this._q2 = f;
/* 227 */     } else if (this._m3 == 0.0D) {
/* 228 */       this._m3 = 3.0D;
/* 229 */       this._q3 = f;
/* 230 */     } else if (this._m4 == 0.0D) {
/* 231 */       this._m4 = 4.0D;
/* 232 */       this._q4 = f;
/*     */     } 
/* 234 */     if (this._m4 == 0.0D) {
/*     */       return;
/*     */     }
/*     */     
/* 238 */     double[] y = { this._q0, this._q1, this._q2, this._q3, this._q4 };
/* 239 */     for (int i = 1; i < 5; i++) {
/* 240 */       for (int j = i; j > 0 && y[j - 1] > y[j]; j--) {
/* 241 */         double ytemp = y[j - 1];
/* 242 */         y[j - 1] = y[j];
/* 243 */         y[j] = ytemp;
/*     */       } 
/*     */     } 
/* 246 */     this._q0 = y[0];
/* 247 */     this._q1 = y[1];
/* 248 */     this._q2 = y[2];
/* 249 */     this._q3 = y[3];
/* 250 */     this._q4 = y[4];
/*     */ 
/*     */     
/* 253 */     this._f1 = 2.0D * this._q;
/* 254 */     this._f2 = 4.0D * this._q;
/* 255 */     this._f3 = 2.0D + 2.0D * this._q;
/*     */ 
/*     */     
/* 258 */     this._d1 = this._q / 2.0D;
/* 259 */     this._d2 = this._q;
/* 260 */     this._d3 = (1.0D + this._q) / 2.0D;
/*     */ 
/*     */     
/* 263 */     this._inited = true;
/*     */   }
/*     */   
/*     */   private void updateOne(float f) {
/* 267 */     assert this._inited : "quantiler is initialized";
/*     */ 
/*     */     
/* 270 */     if (this._ignoreNull && f == this._fnull) {
/*     */       return;
/*     */     }
/*     */     
/* 274 */     if (this._q == 0.0F) {
/* 275 */       if (f < this._q2)
/* 276 */         this._q2 = f; 
/* 277 */     } else if (this._q == 1.0F) {
/* 278 */       if (f > this._q2) {
/* 279 */         this._q2 = f;
/*     */       }
/*     */     } else {
/*     */       
/* 283 */       if (f < this._q0) {
/* 284 */         this._m1++;
/* 285 */         this._m2++;
/* 286 */         this._m3++;
/* 287 */         this._m4++;
/* 288 */         this._q0 = f;
/* 289 */       } else if (f < this._q1) {
/* 290 */         this._m1++;
/* 291 */         this._m2++;
/* 292 */         this._m3++;
/* 293 */         this._m4++;
/* 294 */       } else if (f < this._q2) {
/* 295 */         this._m2++;
/* 296 */         this._m3++;
/* 297 */         this._m4++;
/* 298 */       } else if (f < this._q3) {
/* 299 */         this._m3++;
/* 300 */         this._m4++;
/* 301 */       } else if (f < this._q4) {
/* 302 */         this._m4++;
/*     */       } else {
/* 304 */         this._m4++;
/* 305 */         this._q4 = f;
/*     */       } 
/*     */ 
/*     */       
/* 309 */       this._f1 += this._d1;
/* 310 */       this._f2 += this._d2;
/* 311 */       this._f3 += this._d3;
/*     */ 
/*     */ 
/*     */       
/* 315 */       double mm = this._m1 - 1.0D;
/* 316 */       double mp = this._m1 + 1.0D;
/* 317 */       if (this._f1 >= mp && this._m2 > mp) {
/* 318 */         this._q1 = qp(mp, this._m0, this._m1, this._m2, this._q0, this._q1, this._q2);
/* 319 */         this._m1 = mp;
/* 320 */       } else if (this._f1 <= mm && this._m0 < mm) {
/* 321 */         this._q1 = qm(mm, this._m0, this._m1, this._m2, this._q0, this._q1, this._q2);
/* 322 */         this._m1 = mm;
/*     */       } 
/* 324 */       mm = this._m2 - 1.0D;
/* 325 */       mp = this._m2 + 1.0D;
/* 326 */       if (this._f2 >= mp && this._m3 > mp) {
/* 327 */         this._q2 = qp(mp, this._m1, this._m2, this._m3, this._q1, this._q2, this._q3);
/* 328 */         this._m2 = mp;
/* 329 */       } else if (this._f2 <= mm && this._m1 < mm) {
/* 330 */         this._q2 = qm(mm, this._m1, this._m2, this._m3, this._q1, this._q2, this._q3);
/* 331 */         this._m2 = mm;
/*     */       } 
/* 333 */       mm = this._m3 - 1.0D;
/* 334 */       mp = this._m3 + 1.0D;
/* 335 */       if (this._f3 >= mp && this._m4 > mp) {
/* 336 */         this._q3 = qp(mp, this._m2, this._m3, this._m4, this._q2, this._q3, this._q4);
/* 337 */         this._m3 = mp;
/* 338 */       } else if (this._f3 <= mm && this._m2 < mm) {
/* 339 */         this._q3 = qm(mm, this._m2, this._m3, this._m4, this._q2, this._q3, this._q4);
/* 340 */         this._m3 = mm;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double qp(double mp, double m0, double m1, double m2, double q0, double q1, double q2) {
/* 350 */     double qt = q1 + ((mp - m0) * (q2 - q1) / (m2 - m1) + (m2 - mp) * (q1 - q0) / (m1 - m0)) / (m2 - m0);
/* 351 */     return (qt <= q2) ? qt : (q1 + (q2 - q1) / (m2 - m1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double qm(double mm, double m0, double m1, double m2, double q0, double q1, double q2) {
/* 359 */     double qt = q1 - ((mm - m0) * (q2 - q1) / (m2 - m1) + (m2 - mm) * (q1 - q0) / (m1 - m0)) / (m2 - m0);
/* 360 */     return (q0 <= qt) ? qt : (q1 + (q0 - q1) / (m0 - m1));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Quantiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */