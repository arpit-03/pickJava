/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
/*     */ import edu.mines.jtk.util.Check;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalPredictionFilter
/*     */ {
/*     */   private LocalCorrelationFilter _lcf;
/*     */   
/*     */   public LocalPredictionFilter(double sigma) {
/*  36 */     Check.argument((sigma >= 1.0D), "sigma>=1.0");
/*  37 */     this._lcf = new LocalCorrelationFilter(LocalCorrelationFilter.Type.SYMMETRIC, LocalCorrelationFilter.Window.GAUSSIAN, sigma);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] apply(int[] lag1, int[] lag2, float[][] f, float[][] g) {
/*  44 */     Check.argument((lag1.length == lag2.length), "lag1.length==lag2.length");
/*  45 */     Check.argument((f != g), "f!=g");
/*     */ 
/*     */     
/*  48 */     R2Cache rcache = new R2Cache(f);
/*  49 */     int m = lag1.length;
/*  50 */     float[][][][] rkj = new float[m][m][][];
/*  51 */     float[][][] rk0 = new float[m][][];
/*  52 */     for (int k = 0; k < m; k++) {
/*  53 */       int k1 = lag1[k];
/*  54 */       int k2 = lag2[k];
/*  55 */       for (int i = 0; i < m; i++) {
/*  56 */         int j1 = lag1[i];
/*  57 */         int j2 = lag2[i];
/*  58 */         rkj[k][i] = rcache.get(j1 - k1, j2 - k2);
/*     */       } 
/*  60 */       rk0[k] = rcache.get(k1, k2);
/*     */     } 
/*     */ 
/*     */     
/*  64 */     int n1 = (f[0]).length;
/*  65 */     int n2 = f.length;
/*  66 */     double[][] rkjt = new double[m][m];
/*  67 */     double[] rk0t = new double[m];
/*  68 */     double[] at = new double[m];
/*  69 */     float[][][] a = new float[m][n2][n1];
/*  70 */     CgSolver cgs = new CgSolver(m, 100);
/*     */     
/*  72 */     double niter = 0.0D;
/*  73 */     for (int i2 = 0; i2 < n2; i2++) {
/*  74 */       int i1b = (i2 % 2 == 0) ? 0 : (n1 - 1);
/*  75 */       int i1e = (i2 % 2 == 0) ? n1 : -1;
/*  76 */       int i1s = (i2 % 2 == 0) ? 1 : -1; int i1;
/*  77 */       for (i1 = i1b; i1 != i1e; i1 += i1s) {
/*  78 */         for (int n = 0; n < m; n++) {
/*  79 */           for (int i3 = 0; i3 < m; i3++)
/*  80 */             rkjt[n][i3] = rkj[n][i3][i2][i1]; 
/*  81 */           rk0t[n] = rk0[n][i2][i1];
/*     */         } 
/*  83 */         niter += cgs.solve(rkjt, rk0t, at);
/*     */ 
/*     */ 
/*     */         
/*  87 */         for (int i = 0; i < m; i++)
/*  88 */           a[i][i2][i1] = (float)at[i]; 
/*     */       } 
/*     */     } 
/*  91 */     niter /= n1;
/*  92 */     niter /= n2;
/*  93 */     System.out.println("Average number of CG iterations = " + niter);
/*     */ 
/*     */     
/*  96 */     ArrayMath.zero(g);
/*  97 */     for (int j = 0; j < m; j++) {
/*  98 */       int j1 = lag1[j];
/*  99 */       int j2 = lag2[j];
/* 100 */       float[][] aj = a[j];
/* 101 */       int i1min = ArrayMath.max(0, j1);
/* 102 */       int i1max = ArrayMath.min(n1, n1 + j1);
/* 103 */       int i2min = ArrayMath.max(0, j2);
/* 104 */       int i2max = ArrayMath.min(n2, n2 + j2);
/* 105 */       for (int i = i2min; i < i2max; i++) {
/* 106 */         for (int i1 = i1min; i1 < i1max; i1++) {
/* 107 */           g[i][i1] = g[i][i1] + aj[i][i1] * f[i - j2][i1 - j1];
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     return a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyPef(int[] lag1, int[] lag2, float[][] f, float[][] g) {
/* 121 */     Check.argument((lag1.length == lag2.length), "lag1.length==lag2.length");
/* 122 */     Check.argument((f != g), "f!=g");
/*     */ 
/*     */     
/* 125 */     R2Cache rcache = new R2Cache(f);
/* 126 */     int m = lag1.length;
/* 127 */     float[][][][] rkj = new float[m][m][][];
/* 128 */     float[][][] rk0 = new float[m][][];
/* 129 */     for (int k = 0; k < m; k++) {
/* 130 */       int k1 = lag1[k];
/* 131 */       int k2 = lag2[k];
/* 132 */       for (int i = 0; i < m; i++) {
/* 133 */         int j1 = lag1[i];
/* 134 */         int j2 = lag2[i];
/* 135 */         rkj[k][i] = rcache.get(j1 - k1, j2 - k2);
/*     */       } 
/* 137 */       rk0[k] = rcache.get(k1, k2);
/*     */     } 
/*     */ 
/*     */     
/* 141 */     int n1 = (f[0]).length;
/* 142 */     int n2 = f.length;
/* 143 */     double[][] rkjt = new double[m][m];
/* 144 */     double[] rk0t = new double[m];
/* 145 */     double[] at = new double[m];
/* 146 */     float[][][] a = new float[m][n2][n1];
/* 147 */     CgSolver cgs = new CgSolver(m, 100);
/* 148 */     double niter = 0.0D;
/* 149 */     for (int i2 = 0; i2 < n2; i2++) {
/* 150 */       int i1b = (i2 % 2 == 0) ? 0 : (n1 - 1);
/* 151 */       int i1e = (i2 % 2 == 0) ? n1 : -1;
/* 152 */       int i1s = (i2 % 2 == 0) ? 1 : -1; int i1;
/* 153 */       for (i1 = i1b; i1 != i1e; i1 += i1s) {
/* 154 */         for (int n = 0; n < m; n++) {
/* 155 */           for (int i3 = 0; i3 < m; i3++)
/* 156 */             rkjt[n][i3] = rkj[n][i3][i2][i1]; 
/* 157 */           rk0t[n] = rk0[n][i2][i1];
/*     */         } 
/* 159 */         niter += cgs.solve(rkjt, rk0t, at);
/* 160 */         for (int i = 0; i < m; i++)
/* 161 */           a[i][i2][i1] = (float)at[i]; 
/*     */       } 
/*     */     } 
/* 164 */     niter /= n1;
/* 165 */     niter /= n2;
/* 166 */     System.out.println("Average number of CG iterations = " + niter);
/*     */ 
/*     */     
/* 169 */     ArrayMath.copy(f, g);
/* 170 */     for (int j = 0; j < m; j++) {
/* 171 */       int j1 = lag1[j];
/* 172 */       int j2 = lag2[j];
/* 173 */       float[][] aj = a[j];
/* 174 */       int i1min = ArrayMath.max(0, j1);
/* 175 */       int i1max = ArrayMath.min(n1, n1 + j1);
/* 176 */       int i2min = ArrayMath.max(0, j2);
/* 177 */       int i2max = ArrayMath.min(n2, n2 + j2);
/* 178 */       for (int i = i2min; i < i2max; i++) {
/* 179 */         for (int i1 = i1min; i1 < i1max; i1++) {
/* 180 */           g[i][i1] = g[i][i1] - aj[i][i1] * f[i - j2][i1 - j1];
/*     */         }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CgSolver
/*     */   {
/*     */     CgSolver(int m, int maxiter) {
/* 231 */       this.m = m;
/* 232 */       this.maxiter = maxiter;
/* 233 */       this.p = new double[m];
/* 234 */       this.q = new double[m];
/* 235 */       this.r = new double[m];
/*     */     }
/*     */     int solve(double[][] a, double[] b, double[] x) {
/* 238 */       double rp = 0.0D;
/* 239 */       double rr = 0.0D;
/* 240 */       double bb = 0.0D;
/* 241 */       for (int i = 0; i < this.m; i++) {
/* 242 */         double[] ai = a[i];
/* 243 */         double ax = 0.0D;
/* 244 */         for (int j = 0; j < this.m; j++)
/* 245 */           ax += ai[j] * x[j]; 
/* 246 */         double bi = b[i];
/* 247 */         double ri = this.r[i] = bi - ax;
/* 248 */         bb += bi * bi;
/* 249 */         rr += ri * ri;
/*     */       } 
/* 251 */       double small = bb * TINY;
/*     */       int niter;
/* 253 */       for (niter = 0; niter < this.maxiter && rr > small; niter++) {
/* 254 */         if (niter == 0) {
/* 255 */           for (int m = 0; m < this.m; m++)
/* 256 */             this.p[m] = this.r[m]; 
/*     */         } else {
/* 258 */           double beta = rr / rp;
/* 259 */           for (int m = 0; m < this.m; m++)
/* 260 */             this.p[m] = this.r[m] + beta * this.p[m]; 
/*     */         } 
/* 262 */         double pq = 0.0D;
/* 263 */         for (int j = 0; j < this.m; j++) {
/* 264 */           double[] ai = a[j];
/* 265 */           double ap = 0.0D;
/* 266 */           for (int m = 0; m < this.m; m++)
/* 267 */             ap += ai[m] * this.p[m]; 
/* 268 */           this.q[j] = ap;
/* 269 */           pq += this.p[j] * this.q[j];
/*     */         } 
/* 271 */         double alpha = rr / pq;
/* 272 */         rp = rr;
/* 273 */         rr = 0.0D;
/* 274 */         for (int k = 0; k < this.m; k++) {
/* 275 */           x[k] = x[k] + alpha * this.p[k];
/* 276 */           this.r[k] = this.r[k] - alpha * this.q[k];
/* 277 */           rr += this.r[k] * this.r[k];
/*     */         } 
/*     */       } 
/* 280 */       if (rr > small) {
/* 281 */         System.out.println("CgSolver.solve: failed to converge");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       return niter;
/*     */     }
/* 301 */     private static final double TINY = ArrayMath.ulp(1.0F);
/*     */     private int m;
/*     */     private int maxiter;
/*     */     private double[] p;
/*     */     private double[] q;
/*     */     private double[] r; }
/*     */   
/*     */   private class R2 {
/*     */     R2(int l1, int l2, float[][] f) {
/* 310 */       int n1 = (f[0]).length;
/* 311 */       int n2 = f.length;
/* 312 */       this.l1 = l1;
/* 313 */       this.l2 = l2;
/* 314 */       this.r = new float[n2][n1];
/* 315 */       LocalPredictionFilter.this._lcf.setInputs(f, f);
/* 316 */       LocalPredictionFilter.this._lcf.correlate(l1, l2, this.r);
/* 317 */       if (l1 == 0 && l2 == 0) {
/* 318 */         for (int i2 = 0; i2 < n2; i2++) {
/* 319 */           for (int i1 = 0; i1 < n1; i1++) {
/* 320 */             this.r[i2][i1] = this.r[i2][i1] * 1.01F;
/*     */           }
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int l1;
/*     */ 
/*     */     
/*     */     int l2;
/*     */ 
/*     */     
/*     */     float[][] r;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class R2Cache
/*     */   {
/*     */     float[][] _f;
/*     */ 
/*     */     
/*     */     ArrayList<LocalPredictionFilter.R2> _rlist;
/*     */ 
/*     */ 
/*     */     
/*     */     R2Cache(float[][] f) {
/* 350 */       this._rlist = new ArrayList<>();
/*     */       this._f = f;
/*     */     }
/*     */     
/*     */     float[][][] get() {
/*     */       int n = this._rlist.size();
/*     */       float[][][] r = new float[n][][];
/*     */       int i = 0;
/*     */       for (LocalPredictionFilter.R2 r2 : this._rlist) {
/*     */         r[i] = r2.r;
/*     */         i++;
/*     */       } 
/*     */       return r;
/*     */     }
/*     */     
/*     */     float[][] get(int l1, int l2) {
/*     */       for (LocalPredictionFilter.R2 r21 : this._rlist) {
/*     */         if ((l1 == r21.l1 && l2 == r21.l2) || (-l1 == r21.l1 && -l2 == r21.l2))
/*     */           return r21.r; 
/*     */       } 
/*     */       LocalPredictionFilter.R2 r2 = new LocalPredictionFilter.R2(l1, l2, this._f);
/*     */       this._rlist.add(r2);
/*     */       return r2.r;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalPredictionFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */