/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.Cdouble;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import edu.mines.jtk.util.MathPlus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecursiveCascadeFilter
/*     */ {
/*     */   private int _n1;
/*     */   private Recursive2ndOrderFilter[] _f1;
/*     */   
/*     */   public RecursiveCascadeFilter(Cdouble[] poles, Cdouble[] zeros, double gain) {
/*  50 */     init(poles, zeros, gain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyForward(float[] x, float[] y) {
/*  61 */     this._f1[0].applyForward(x, y);
/*  62 */     for (int i1 = 1; i1 < this._n1; i1++) {
/*  63 */       this._f1[i1].applyForward(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyReverse(float[] x, float[] y) {
/*  74 */     this._f1[0].applyReverse(x, y);
/*  75 */     for (int i1 = 1; i1 < this._n1; i1++) {
/*  76 */       this._f1[i1].applyReverse(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyForwardReverse(float[] x, float[] y) {
/*  87 */     applyForward(x, y);
/*  88 */     applyReverse(y, y);
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
/*     */   public void apply1Forward(float[][] x, float[][] y) {
/* 107 */     this._f1[0].apply1Forward(x, y);
/* 108 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 109 */       this._f1[i1].apply1Forward(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1Reverse(float[][] x, float[][] y) {
/* 120 */     this._f1[0].apply1Reverse(x, y);
/* 121 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 122 */       this._f1[i1].apply1Reverse(y, y);
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
/*     */   public void apply1ForwardReverse(float[][] x, float[][] y) {
/* 134 */     apply1Forward(x, y);
/* 135 */     apply1Reverse(y, y);
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
/*     */   public void apply2Forward(float[][] x, float[][] y) {
/* 154 */     this._f1[0].apply2Forward(x, y);
/* 155 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 156 */       this._f1[i1].apply2Forward(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2Reverse(float[][] x, float[][] y) {
/* 167 */     this._f1[0].apply2Reverse(x, y);
/* 168 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 169 */       this._f1[i1].apply2Reverse(y, y);
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
/*     */   public void apply2ForwardReverse(float[][] x, float[][] y) {
/* 181 */     apply2Forward(x, y);
/* 182 */     apply2Reverse(y, y);
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
/*     */   public void apply1Forward(float[][][] x, float[][][] y) {
/* 201 */     this._f1[0].apply1Forward(x, y);
/* 202 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 203 */       this._f1[i1].apply1Forward(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply1Reverse(float[][][] x, float[][][] y) {
/* 214 */     this._f1[0].apply1Reverse(x, y);
/* 215 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 216 */       this._f1[i1].apply1Reverse(y, y);
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
/*     */   public void apply1ForwardReverse(float[][][] x, float[][][] y) {
/* 228 */     apply1Forward(x, y);
/* 229 */     apply1Reverse(y, y);
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
/*     */   public void apply2Forward(float[][][] x, float[][][] y) {
/* 248 */     this._f1[0].apply2Forward(x, y);
/* 249 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 250 */       this._f1[i1].apply2Forward(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply2Reverse(float[][][] x, float[][][] y) {
/* 261 */     this._f1[0].apply2Reverse(x, y);
/* 262 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 263 */       this._f1[i1].apply2Reverse(y, y);
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
/*     */   public void apply2ForwardReverse(float[][][] x, float[][][] y) {
/* 275 */     apply2Forward(x, y);
/* 276 */     apply2Reverse(y, y);
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
/*     */   public void apply3Forward(float[][][] x, float[][][] y) {
/* 295 */     this._f1[0].apply3Forward(x, y);
/* 296 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 297 */       this._f1[i1].apply3Forward(y, y);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void apply3Reverse(float[][][] x, float[][][] y) {
/* 308 */     this._f1[0].apply3Reverse(x, y);
/* 309 */     for (int i1 = 1; i1 < this._n1; i1++) {
/* 310 */       this._f1[i1].apply3Reverse(y, y);
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
/*     */   public void apply3ForwardReverse(float[][][] x, float[][][] y) {
/* 322 */     apply3Forward(x, y);
/* 323 */     apply3Reverse(y, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RecursiveCascadeFilter() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(Cdouble[] poles, Cdouble[] zeros, double gain) {
/* 341 */     Check.argument((poles.length > 0 || zeros.length > 0), "at least one pole or zero is specified");
/*     */ 
/*     */ 
/*     */     
/* 345 */     poles = sortPolesOrZeros(poles);
/* 346 */     zeros = sortPolesOrZeros(zeros);
/*     */ 
/*     */     
/* 349 */     int np = poles.length;
/* 350 */     int nz = zeros.length;
/* 351 */     this._n1 = MathPlus.max((np + 1) / 2, (nz + 1) / 2);
/* 352 */     this._f1 = new Recursive2ndOrderFilter[this._n1];
/* 353 */     gain = MathPlus.pow(gain, 1.0D / this._n1);
/* 354 */     Cdouble c0 = new Cdouble(0.0D, 0.0D);
/* 355 */     for (int i1 = 0, ip = 0, iz = 0; i1 < this._n1; i1++) {
/* 356 */       Cdouble pole1 = (ip < np) ? poles[ip++] : c0;
/* 357 */       Cdouble pole2 = (ip < np) ? poles[ip++] : c0;
/* 358 */       Cdouble zero1 = (iz < nz) ? zeros[iz++] : c0;
/* 359 */       Cdouble zero2 = (iz < nz) ? zeros[iz++] : c0;
/* 360 */       this._f1[i1] = new Recursive2ndOrderFilter(pole1, pole2, zero1, zero2, gain);
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
/*     */   private static Cdouble[] sortPolesOrZeros(Cdouble[] c) {
/* 377 */     c = (Cdouble[])c.clone();
/* 378 */     int n = c.length;
/* 379 */     Cdouble[] cs = new Cdouble[n];
/* 380 */     int ns = 0; int i;
/* 381 */     for (i = 0; i < n; i++) {
/* 382 */       if (c[i] != null && !c[i].isReal()) {
/* 383 */         Cdouble cc = c[i].conj();
/* 384 */         int j = i + 1;
/* 385 */         while (j < n && !cc.equals(c[j]))
/* 386 */           j++; 
/* 387 */         Check.argument((j < n), "complex " + c[i] + " has a conjugate mate");
/* 388 */         cs[ns++] = c[i];
/* 389 */         cs[ns++] = c[j];
/* 390 */         c[i] = null;
/* 391 */         c[j] = null;
/*     */       } 
/*     */     } 
/* 394 */     for (i = 0; i < n; i++) {
/* 395 */       if (c[i] != null && c[i].isReal())
/* 396 */         cs[ns++] = c[i]; 
/*     */     } 
/* 398 */     return cs;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/RecursiveCascadeFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */