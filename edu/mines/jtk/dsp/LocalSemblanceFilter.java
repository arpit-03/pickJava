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
/*     */ public class LocalSemblanceFilter
/*     */ {
/*     */   private LaplacianSmoother _smoother1;
/*     */   private LaplacianSmoother _smoother2;
/*     */   
/*     */   public enum Direction2
/*     */   {
/*  40 */     U, V, UV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Direction3
/*     */   {
/*  49 */     U, V, W, UV, UW, VW, UVW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalSemblanceFilter(int halfWidth1, int halfWidth2) {
/*  58 */     this._smoother1 = new LaplacianSmoother(halfWidth1);
/*  59 */     this._smoother2 = new LaplacianSmoother(halfWidth2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void semblance(float[] f, float[] s) {
/*  68 */     int n1 = f.length;
/*     */     
/*  70 */     float[] sn = smooth1(f);
/*  71 */     sn = ArrayMath.mul(sn, sn);
/*  72 */     sn = smooth2(sn);
/*  73 */     float[] sd = ArrayMath.mul(f, f);
/*  74 */     sd = smooth1(sd);
/*  75 */     sd = smooth2(sd);
/*  76 */     for (int i1 = 0; i1 < n1; i1++) {
/*  77 */       float sni = sn[i1];
/*  78 */       float sdi = sd[i1];
/*  79 */       if (sdi <= 0.0F || sni < 0.0F) {
/*  80 */         s[i1] = 0.0F;
/*  81 */       } else if (sdi < sni) {
/*  82 */         s[i1] = 1.0F;
/*     */       } else {
/*  84 */         s[i1] = sni / sdi;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] semblance(float[] f) {
/*  95 */     float[] s = like(f);
/*  96 */     semblance(f, s);
/*  97 */     return s;
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
/*     */   public void semblance(Direction2 d, EigenTensors2 t, float[][] f, float[][] s) {
/* 110 */     int n1 = (f[0]).length;
/* 111 */     int n2 = f.length;
/*     */     
/* 113 */     float[][] sn = smooth1(d, t, f);
/* 114 */     sn = ArrayMath.mul(sn, sn);
/* 115 */     sn = smooth2(d, t, sn);
/* 116 */     float[][] sd = ArrayMath.mul(f, f);
/* 117 */     sd = smooth1(d, t, sd);
/* 118 */     sd = smooth2(d, t, sd);
/* 119 */     for (int i2 = 0; i2 < n2; i2++) {
/* 120 */       for (int i1 = 0; i1 < n1; i1++) {
/* 121 */         float sni = sn[i2][i1];
/* 122 */         float sdi = sd[i2][i1];
/* 123 */         if (sdi <= 0.0F || sni < 0.0F) {
/* 124 */           s[i2][i1] = 0.0F;
/* 125 */         } else if (sdi < sni) {
/* 126 */           s[i2][i1] = 1.0F;
/*     */         } else {
/* 128 */           s[i2][i1] = sni / sdi;
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
/*     */   public float[][] semblance(Direction2 d, EigenTensors2 t, float[][] f) {
/* 142 */     float[][] s = like(f);
/* 143 */     semblance(d, t, f, s);
/* 144 */     return s;
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
/*     */   public void semblance(Direction3 d, EigenTensors3 t, float[][][] f, float[][][] s) {
/* 157 */     int n1 = (f[0][0]).length;
/* 158 */     int n2 = (f[0]).length;
/* 159 */     int n3 = f.length;
/*     */     
/* 161 */     float[][][] sn = smooth1(d, t, f);
/* 162 */     sn = ArrayMath.mul(sn, sn);
/* 163 */     sn = smooth2(d, t, sn);
/* 164 */     float[][][] sd = ArrayMath.mul(f, f);
/* 165 */     sd = smooth1(d, t, sd);
/* 166 */     sd = smooth2(d, t, sd);
/* 167 */     for (int i3 = 0; i3 < n3; i3++) {
/* 168 */       for (int i2 = 0; i2 < n2; i2++) {
/* 169 */         for (int i1 = 0; i1 < n1; i1++) {
/* 170 */           float sni = sn[i3][i2][i1];
/* 171 */           float sdi = sd[i3][i2][i1];
/* 172 */           if (sdi <= 0.0F || sni < 0.0F) {
/* 173 */             s[i3][i2][i1] = 0.0F;
/* 174 */           } else if (sdi < sni) {
/* 175 */             s[i3][i2][i1] = 1.0F;
/*     */           } else {
/* 177 */             s[i3][i2][i1] = sni / sdi;
/*     */           } 
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
/*     */   public float[][][] semblance(Direction3 d, EigenTensors3 t, float[][][] f) {
/* 192 */     float[][][] s = like(f);
/* 193 */     semblance(d, t, f, s);
/* 194 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void smooth1(float[] f, float[] g) {
/* 203 */     this._smoother1.apply(f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] smooth1(float[] f) {
/* 212 */     float[] g = like(f);
/* 213 */     smooth1(f, g);
/* 214 */     return g;
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
/*     */   public void smooth1(Direction2 d, EigenTensors2 t, float[][] f, float[][] g) {
/* 227 */     this._smoother1.apply(d, t, f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] smooth1(Direction2 d, EigenTensors2 t, float[][] f) {
/* 238 */     float[][] g = like(f);
/* 239 */     smooth1(d, t, f, g);
/* 240 */     return g;
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
/*     */   public void smooth1(Direction3 d, EigenTensors3 t, float[][][] f, float[][][] g) {
/* 253 */     this._smoother1.apply(d, t, f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] smooth1(Direction3 d, EigenTensors3 t, float[][][] f) {
/* 264 */     float[][][] g = like(f);
/* 265 */     smooth1(d, t, f, g);
/* 266 */     return g;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void smooth2(float[] f, float[] g) {
/* 275 */     this._smoother2.apply(f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[] smooth2(float[] f) {
/* 284 */     float[] g = like(f);
/* 285 */     smooth2(f, g);
/* 286 */     return g;
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
/*     */   public void smooth2(Direction2 d, EigenTensors2 t, float[][] f, float[][] g) {
/* 299 */     this._smoother2.apply(d, t, f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][] smooth2(Direction2 d, EigenTensors2 t, float[][] f) {
/* 310 */     float[][] g = like(f);
/* 311 */     smooth2(orthogonal(d), t, f, g);
/* 312 */     return g;
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
/*     */   public void smooth2(Direction3 d, EigenTensors3 t, float[][][] f, float[][][] g) {
/* 325 */     this._smoother2.apply(d, t, f, g);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float[][][] smooth2(Direction3 d, EigenTensors3 t, float[][][] f) {
/* 336 */     float[][][] g = like(f);
/* 337 */     smooth2(orthogonal(d), t, f, g);
/* 338 */     return g;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class LaplacianSmoother
/*     */   {
/*     */     private float _scale;
/*     */     
/*     */     private static final double _small = 0.001D;
/*     */     
/*     */     private static final int _niter = 1000;
/*     */     private final LocalDiffusionKernel _ldk;
/*     */     private final LocalSmoothingFilter _lsf;
/*     */     private static final double _kmax = 0.35D;
/*     */     
/*     */     public void apply(float[] f, float[] g) {
/*     */       if (this._scale == 0.0F) {
/*     */         ArrayMath.copy(f, g);
/*     */       } else {
/*     */         this._lsf.apply(this._scale, f, g);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void apply(LocalSemblanceFilter.Direction2 d, EigenTensors2 t, float[][] f, float[][] g) {
/*     */       if (this._scale == 0.0F) {
/*     */         ArrayMath.copy(f, g);
/*     */       } else {
/*     */         int n1 = (f[0]).length;
/*     */         int n2 = f.length;
/*     */         float[][] au = new float[n2][n1];
/*     */         float[][] av = new float[n2][n1];
/*     */         float[][] sf = new float[n2][n1];
/*     */         t.getEigenvalues(au, av);
/*     */         LocalSemblanceFilter.setEigenvalues(d, t);
/*     */         this._lsf.applySmoothL(0.35D, f, sf);
/*     */         this._lsf.apply(t, this._scale, sf, g);
/*     */         t.setEigenvalues(au, av);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void apply(LocalSemblanceFilter.Direction3 d, EigenTensors3 t, float[][][] f, float[][][] g) {
/*     */       if (this._scale == 0.0F) {
/*     */         ArrayMath.copy(f, g);
/*     */       } else {
/*     */         int n1 = (f[0][0]).length;
/*     */         int n2 = (f[0]).length;
/*     */         int n3 = f.length;
/*     */         float[][][] au = new float[n3][n2][n1];
/*     */         float[][][] av = new float[n3][n2][n1];
/*     */         float[][][] aw = new float[n3][n2][n1];
/*     */         float[][][] sf = new float[n3][n2][n1];
/*     */         t.getEigenvalues(au, av, aw);
/*     */         LocalSemblanceFilter.setEigenvalues(d, t);
/*     */         this._lsf.applySmoothL(0.35D, f, sf);
/*     */         this._lsf.apply(t, this._scale, sf, g);
/*     */         t.setEigenvalues(au, av, aw);
/*     */       } 
/*     */     }
/*     */     
/*     */     LaplacianSmoother(int halfWidth) {
/* 398 */       this._ldk = new LocalDiffusionKernel(LocalDiffusionKernel.Stencil.D71);
/*     */ 
/*     */       
/* 401 */       this._lsf = new LocalSmoothingFilter(0.001D, 1000, this._ldk);
/*     */       this._scale = (halfWidth * (halfWidth + 1)) / 6.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setEigenvalues(Direction2 d, EigenTensors2 t) {
/* 409 */     float au = 0.0F;
/* 410 */     float av = 0.0F;
/* 411 */     if (d == Direction2.U || d == Direction2.UV)
/* 412 */       au = 1.0F; 
/* 413 */     if (d == Direction2.V || d == Direction2.UV)
/* 414 */       av = 1.0F; 
/* 415 */     t.setEigenvalues(au, av);
/*     */   }
/*     */   private static void setEigenvalues(Direction3 d, EigenTensors3 t) {
/* 418 */     float au = 0.0F;
/* 419 */     float av = 0.0F;
/* 420 */     float aw = 0.0F;
/* 421 */     if (d == Direction3.U || d == Direction3.UV || d == Direction3.UW || d == Direction3.UVW)
/*     */     {
/*     */ 
/*     */       
/* 425 */       au = 1.0F; } 
/* 426 */     if (d == Direction3.V || d == Direction3.UV || d == Direction3.VW || d == Direction3.UVW)
/*     */     {
/*     */ 
/*     */       
/* 430 */       av = 1.0F; } 
/* 431 */     if (d == Direction3.W || d == Direction3.UW || d == Direction3.VW || d == Direction3.UVW)
/*     */     {
/*     */ 
/*     */       
/* 435 */       aw = 1.0F; } 
/* 436 */     t.setEigenvalues(au, av, aw);
/*     */   }
/*     */   
/*     */   private static float[] like(float[] f) {
/* 440 */     return new float[f.length];
/*     */   }
/*     */   private static float[][] like(float[][] f) {
/* 443 */     return new float[f.length][(f[0]).length];
/*     */   }
/*     */   private static float[][][] like(float[][][] f) {
/* 446 */     return new float[f.length][(f[0]).length][(f[0][0]).length];
/*     */   }
/*     */   
/*     */   private static Direction2 orthogonal(Direction2 d) {
/* 450 */     if (d == Direction2.U) {
/* 451 */       return Direction2.V;
/*     */     }
/* 453 */     return Direction2.U;
/*     */   }
/*     */   private static Direction3 orthogonal(Direction3 d) {
/* 456 */     if (d == Direction3.U)
/* 457 */       return Direction3.VW; 
/* 458 */     if (d == Direction3.V)
/* 459 */       return Direction3.UW; 
/* 460 */     if (d == Direction3.W)
/* 461 */       return Direction3.UV; 
/* 462 */     if (d == Direction3.UV)
/* 463 */       return Direction3.W; 
/* 464 */     if (d == Direction3.UW) {
/* 465 */       return Direction3.V;
/*     */     }
/* 467 */     return Direction3.U;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalSemblanceFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */