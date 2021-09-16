/*     */ package edu.mines.jtk.interp;
/*     */ 
/*     */ import edu.mines.jtk.dsp.Sampling;
/*     */ import edu.mines.jtk.la.DMatrix;
/*     */ import edu.mines.jtk.la.DMatrixQrd;
/*     */ import edu.mines.jtk.util.Check;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolyTrend2
/*     */ {
/*     */   private int _order;
/*     */   private int _n;
/*     */   private float[] _f;
/*     */   private float[] _x1;
/*     */   private float[] _x2;
/*     */   private double _x1c;
/*     */   private double _x2c;
/*     */   private double _f0;
/*     */   private double _f1;
/*     */   private double _f2;
/*     */   private double _f11;
/*     */   private double _f12;
/*     */   private double _f22;
/*     */   private boolean _detrend;
/*     */   
/*     */   public PolyTrend2(int order, float[] f, float[] x1, float[] x2) {
/*  46 */     Check.argument((0 <= order), "0<=order");
/*  47 */     Check.argument((order <= 2), "order<=2");
/*  48 */     this._order = order;
/*  49 */     setSamples(f, x1, x2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSamples(float[] f, float[] x1, float[] x2) {
/*  60 */     this._n = f.length;
/*  61 */     this._f = f;
/*  62 */     this._x1 = x1;
/*  63 */     this._x2 = x2;
/*  64 */     if (this._order > 0)
/*  65 */       initCenter(); 
/*  66 */     if (this._order == 2 && this._n >= 6) {
/*  67 */       initOrder2();
/*  68 */     } else if (this._order == 1 && this._n >= 3) {
/*  69 */       initOrder1();
/*     */     } else {
/*  71 */       initOrder0();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detrend() {
/*  81 */     Check.state(!this._detrend, "trend not yet removed");
/*  82 */     detrend(this._f, this._x1, this._x2);
/*  83 */     this._detrend = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restore() {
/*  92 */     Check.state(this._detrend, "trend has been removed");
/*  93 */     restore(this._f, this._x1, this._x2);
/*  94 */     this._detrend = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float detrend(float f, float x1, float x2) {
/* 105 */     double fi = f - this._f0;
/* 106 */     if (this._order > 0) {
/* 107 */       double y1 = x1 - this._x1c;
/* 108 */       double y2 = x2 - this._x2c;
/* 109 */       fi -= this._f1 * y1 + this._f2 * y2;
/* 110 */       if (this._order > 1) {
/* 111 */         double y11 = y1 * y1;
/* 112 */         double y12 = y1 * y2;
/* 113 */         double y22 = y2 * y2;
/* 114 */         fi -= this._f11 * y11 + this._f12 * y12 + this._f22 * y22;
/*     */       } 
/*     */     } 
/* 117 */     return (float)fi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float restore(float f, float x1, float x2) {
/* 128 */     double fi = f + this._f0;
/* 129 */     if (this._order > 0) {
/* 130 */       double y1 = x1 - this._x1c;
/* 131 */       double y2 = x2 - this._x2c;
/* 132 */       fi += this._f1 * y1 + this._f2 * y2;
/* 133 */       if (this._order > 1) {
/* 134 */         double y11 = y1 * y1;
/* 135 */         double y12 = y1 * y2;
/* 136 */         double y22 = y2 * y2;
/* 137 */         fi += this._f11 * y11 + this._f12 * y12 + this._f22 * y22;
/*     */       } 
/*     */     } 
/* 140 */     return (float)fi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detrend(float[] f, float[] x1, float[] x2) {
/* 150 */     int n = f.length;
/* 151 */     for (int i = 0; i < n; i++) {
/* 152 */       f[i] = detrend(f[i], x1[i], x2[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restore(float[] f, float[] x1, float[] x2) {
/* 162 */     int n = f.length;
/* 163 */     for (int i = 0; i < n; i++) {
/* 164 */       f[i] = restore(f[i], x1[i], x2[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detrend(float[][] f, Sampling s1, Sampling s2) {
/* 174 */     int n2 = f.length;
/* 175 */     int n1 = (f[0]).length;
/* 176 */     for (int i2 = 0; i2 < n2; i2++) {
/* 177 */       float x2 = (float)s2.getValue(i2);
/* 178 */       for (int i1 = 0; i1 < n1; i1++) {
/* 179 */         float x1 = (float)s1.getValue(i1);
/* 180 */         f[i2][i1] = detrend(f[i2][i1], x1, x2);
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
/*     */   public void restore(float[][] f, Sampling s1, Sampling s2) {
/* 192 */     int n2 = f.length;
/* 193 */     int n1 = (f[0]).length;
/* 194 */     for (int i2 = 0; i2 < n2; i2++) {
/* 195 */       float x2 = (float)s2.getValue(i2);
/* 196 */       for (int i1 = 0; i1 < n1; i1++) {
/* 197 */         float x1 = (float)s1.getValue(i1);
/* 198 */         f[i2][i1] = restore(f[i2][i1], x1, x2);
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
/*     */   private void initCenter() {
/* 214 */     this._x1c = 0.0D;
/* 215 */     this._x2c = 0.0D;
/* 216 */     for (int i = 0; i < this._n; i++) {
/* 217 */       this._x1c += this._x1[i];
/* 218 */       this._x2c += this._x2[i];
/*     */     } 
/* 220 */     this._x1c /= this._n;
/* 221 */     this._x2c /= this._n;
/*     */   }
/*     */   private void initOrder0() {
/* 224 */     this._f0 = this._f1 = this._f2 = this._f11 = this._f12 = this._f22 = 0.0D;
/* 225 */     for (int i = 0; i < this._n; i++)
/* 226 */       this._f0 += this._f[i]; 
/* 227 */     this._f0 /= this._n;
/*     */   }
/*     */   private void initOrder1() {
/* 230 */     this._f11 = this._f12 = this._f22 = 0.0D;
/* 231 */     DMatrix a = new DMatrix(this._n, 3);
/* 232 */     DMatrix b = new DMatrix(this._n, 1);
/* 233 */     for (int i = 0; i < this._n; i++) {
/* 234 */       double x1 = this._x1[i] - this._x1c;
/* 235 */       double x2 = this._x2[i] - this._x2c;
/* 236 */       a.set(i, 0, 1.0D);
/* 237 */       a.set(i, 1, x1);
/* 238 */       a.set(i, 2, x2);
/* 239 */       b.set(i, 0, this._f[i]);
/*     */     } 
/* 241 */     DMatrixQrd qrd = new DMatrixQrd(a);
/* 242 */     if (qrd.isFullRank()) {
/* 243 */       DMatrix f = qrd.solve(b);
/* 244 */       this._f0 = f.get(0, 0);
/* 245 */       this._f1 = f.get(1, 0);
/* 246 */       this._f2 = f.get(2, 0);
/*     */     } else {
/* 248 */       initOrder0();
/*     */     } 
/*     */   }
/*     */   private void initOrder2() {
/* 252 */     DMatrix a = new DMatrix(this._n, 6);
/* 253 */     DMatrix b = new DMatrix(this._n, 1);
/* 254 */     for (int i = 0; i < this._n; i++) {
/* 255 */       double x1 = this._x1[i] - this._x1c;
/* 256 */       double x2 = this._x2[i] - this._x2c;
/* 257 */       a.set(i, 0, 1.0D);
/* 258 */       a.set(i, 1, x1);
/* 259 */       a.set(i, 2, x2);
/* 260 */       a.set(i, 3, x1 * x1);
/* 261 */       a.set(i, 4, x1 * x2);
/* 262 */       a.set(i, 5, x2 * x2);
/* 263 */       b.set(i, 0, this._f[i]);
/*     */     } 
/* 265 */     DMatrixQrd qrd = new DMatrixQrd(a);
/* 266 */     if (qrd.isFullRank()) {
/* 267 */       DMatrix f = qrd.solve(b);
/* 268 */       this._f0 = f.get(0, 0);
/* 269 */       this._f1 = f.get(1, 0);
/* 270 */       this._f2 = f.get(2, 0);
/* 271 */       this._f11 = f.get(3, 0);
/* 272 */       this._f12 = f.get(4, 0);
/* 273 */       this._f22 = f.get(5, 0);
/*     */     } else {
/* 275 */       initOrder1();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/interp/PolyTrend2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */