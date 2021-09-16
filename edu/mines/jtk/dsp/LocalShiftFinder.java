/*     */ package edu.mines.jtk.dsp;
/*     */ 
/*     */ import edu.mines.jtk.util.ArrayMath;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalShiftFinder
/*     */ {
/*     */   private LocalCorrelationFilter _lcfSimple;
/*     */   private SincInterpolator _si;
/*     */   private boolean _interpolateDisplacements;
/*     */   
/*     */   public LocalShiftFinder(double sigma) {
/*  71 */     this(sigma, sigma, sigma);
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
/*     */   public LocalShiftFinder(double sigma1, double sigma2) {
/*  85 */     this(sigma1, sigma2, sigma2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalShiftFinder(double sigma1, double sigma2, double sigma3) {
/* 667 */     this._interpolateDisplacements = true; Check.argument((sigma1 >= 1.0D), "sigma1>=1.0"); Check.argument((sigma2 >= 1.0D), "sigma2>=1.0"); Check.argument((sigma3 >= 1.0D), "sigma3>=1.0"); this._lcfSimple = new LocalCorrelationFilter(LocalCorrelationFilter.Type.SIMPLE, LocalCorrelationFilter.Window.GAUSSIAN, sigma1, sigma2, sigma3); this._si = new SincInterpolator(); this._si.setExtrapolation(SincInterpolator.Extrapolation.CONSTANT);
/*     */   } public void setInterpolateDisplacements(boolean enable) { this._interpolateDisplacements = enable; } public void find1(int min1, int max1, float[] f, float[] g, float[] u) { findShifts(min1, max1, f, g, u, null, null); } public void find1(int min1, int max1, float[] f, float[] g, float[] u, float[] c, float[] d) { findShifts(min1, max1, f, g, u, c, d); } public void find1(int min1, int max1, float[][] f, float[][] g, float[][] u) { findShifts(1, min1, max1, f, g, u); } public void find2(int min2, int max2, float[][] f, float[][] g, float[][] u) { findShifts(2, min2, max2, f, g, u); } public void find1(int min1, int max1, float[][][] f, float[][][] g, float[][][] u) { findShifts(1, min1, max1, f, g, u); }
/*     */   public void find2(int min2, int max2, float[][][] f, float[][][] g, float[][][] u) { findShifts(2, min2, max2, f, g, u); }
/*     */   public void find3(int min3, int max3, float[][][] f, float[][][] g, float[][][] u) { findShifts(3, min3, max3, f, g, u); }
/*     */   public void shift1(float[] du, float[] u1, float[] h) { int n1 = h.length; float[] xu1 = new float[n1]; float[] u1a = u1; float[] u1b = new float[n1]; float[] ha = h; float[] hb = new float[n1]; for (int i1 = 0; i1 < n1; i1++) xu1[i1] = i1 + du[i1];  this._si.interpolate(n1, 1.0D, 0.0D, ha, n1, xu1, hb); ArrayMath.copy(hb, h); if (this._interpolateDisplacements) { this._si.interpolate(n1, 1.0D, 0.0D, u1a, n1, xu1, u1b); ArrayMath.copy(u1b, u1); }  }
/* 672 */   private void findShifts(int min, int max, float[] f, float[] g, float[] u, float[] c, float[] d) { int n1 = f.length;
/*     */ 
/*     */     
/* 675 */     ArrayMath.zero(u);
/* 676 */     if (c != null) {
/* 677 */       ArrayMath.zero(c);
/*     */     } else {
/* 679 */       c = ArrayMath.zerofloat(n1);
/* 680 */     }  if (d != null) {
/* 681 */       ArrayMath.zero(d);
/*     */     }
/*     */     
/* 684 */     float[][] c3 = new float[3][n1];
/*     */ 
/*     */     
/* 687 */     LocalCorrelationFilter lcf = this._lcfSimple;
/* 688 */     lcf.setInputs(f, g);
/* 689 */     int lag1 = min;
/* 690 */     lcf.correlate(lag1, c3[1]);
/* 691 */     lcf.normalize(lag1, c3[1]);
/*     */ 
/*     */     
/* 694 */     for (int lag = min; lag <= max; lag++)
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 702 */       int i = lag - min;
/* 703 */       float[] ca = (lag > min) ? c3[i % 3] : c3[(i + 2) % 3];
/* 704 */       float[] cb = c3[(i + 1) % 3];
/* 705 */       float[] cc = (lag < max) ? c3[(i + 2) % 3] : c3[i % 3];
/*     */ 
/*     */       
/* 708 */       if (lag < max) {
/* 709 */         lag1 = lag + 1;
/* 710 */         lcf.correlate(lag1, cc);
/* 711 */         lcf.normalize(lag1, cc);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 717 */       for (int i1 = 0; i1 < n1; i1++)
/* 718 */       { float ai = ca[i1];
/* 719 */         float bi = cb[i1];
/* 720 */         float ci = cc[i1];
/* 721 */         if (bi >= ai && bi >= ci)
/* 722 */         { double c0 = bi;
/* 723 */           double c1 = 0.5D * (ci - ai);
/* 724 */           double c2 = 0.5D * (ci + ai) - bi;
/* 725 */           double up = (c2 < 0.0D) ? (-0.5D * c1 / c2) : 0.0D;
/* 726 */           double cp = c0 + up * (c1 + up * c2);
/* 727 */           if (cp > c[i1])
/* 728 */           { if (d != null) d[i1] = (float)cp - c[i1]; 
/* 729 */             c[i1] = (float)cp;
/* 730 */             u[i1] = (float)(lag + up); }  }  }  }  }
/*     */   public void shift1(float[][] du, float[][] u1, float[][] u2, float[][] h) { int n1 = (h[0]).length; int n2 = h.length; float[] xu1 = new float[n1]; float[] u1b = new float[n1]; float[] u2b = new float[n1]; float[] hb = new float[n1]; for (int i2 = 0; i2 < n2; i2++) { float[] ha = h[i2]; float[] u1a = u1[i2]; float[] u2a = u2[i2]; float[] du1 = du[i2]; int i1; for (i1 = 0; i1 < n1; i1++)
/*     */         xu1[i1] = i1 + du1[i1];  this._si.interpolate(n1, 1.0D, 0.0D, ha, n1, xu1, hb); if (this._interpolateDisplacements) { this._si.interpolate(n1, 1.0D, 0.0D, u1a, n1, xu1, u1b); this._si.interpolate(n1, 1.0D, 0.0D, u2a, n1, xu1, u2b); } else { ArrayMath.copy(u1a, u1b); ArrayMath.copy(u2a, u2b); }  for (i1 = 0; i1 < n1; i1++) { h[i2][i1] = hb[i1]; u1[i2][i1] = u1b[i1] + du1[i1]; u2[i2][i1] = u2b[i1]; }
/*     */        }
/*     */      }
/*     */   public void shift2(float[][] du, float[][] u1, float[][] u2, float[][] h) { int n1 = (h[0]).length; int n2 = h.length; float[] du2 = new float[n2]; float[] xu2 = new float[n2]; float[] u1a = new float[n2]; float[] u1b = new float[n2]; float[] u2a = new float[n2]; float[] u2b = new float[n2]; float[] ha = new float[n2]; float[] hb = new float[n2]; for (int i1 = 0; i1 < n1; i1++) { int i2; for (i2 = 0; i2 < n2; i2++) { ha[i2] = h[i2][i1]; u1a[i2] = u1[i2][i1]; u2a[i2] = u2[i2][i1]; du2[i2] = du[i2][i1]; xu2[i2] = i2 + du2[i2]; }
/*     */        this._si.interpolate(n2, 1.0D, 0.0D, ha, n2, xu2, hb); if (this._interpolateDisplacements) { this._si.interpolate(n2, 1.0D, 0.0D, u1a, n2, xu2, u1b); this._si.interpolate(n2, 1.0D, 0.0D, u2a, n2, xu2, u2b); }
/*     */       else { ArrayMath.copy(u1a, u1b); ArrayMath.copy(u2a, u2b); }
/*     */        for (i2 = 0; i2 < n2; i2++) { h[i2][i1] = hb[i2]; u1[i2][i1] = u1b[i2]; u2[i2][i1] = u2b[i2] + du2[i2]; }
/*     */        }
/* 740 */      } private void findShifts(int min, int max, float[] f, float[] g, float[] u) { int n1 = f.length;
/*     */ 
/*     */     
/* 743 */     ArrayMath.zero(u);
/*     */ 
/*     */     
/* 746 */     float[][] c = new float[3][n1];
/*     */ 
/*     */     
/* 749 */     float[] cmax = new float[n1];
/*     */ 
/*     */     
/* 752 */     LocalCorrelationFilter lcf = this._lcfSimple;
/* 753 */     lcf.setInputs(f, g);
/* 754 */     int lag1 = min;
/* 755 */     lcf.correlate(lag1, c[1]);
/* 756 */     lcf.normalize(lag1, c[1]);
/*     */ 
/*     */     
/* 759 */     for (int lag = min; lag <= max; lag++)
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 767 */       int i = lag - min;
/* 768 */       float[] ca = (lag > min) ? c[i % 3] : c[(i + 2) % 3];
/* 769 */       float[] cb = c[(i + 1) % 3];
/* 770 */       float[] cc = (lag < max) ? c[(i + 2) % 3] : c[i % 3];
/*     */ 
/*     */       
/* 773 */       if (lag < max) {
/* 774 */         lag1 = lag + 1;
/* 775 */         lcf.correlate(lag1, cc);
/* 776 */         lcf.normalize(lag1, cc);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 782 */       for (int i1 = 0; i1 < n1; i1++)
/* 783 */       { float ai = ca[i1];
/* 784 */         float bi = cb[i1];
/* 785 */         float ci = cc[i1];
/* 786 */         if (bi >= ai && bi >= ci)
/* 787 */         { double c0 = bi;
/* 788 */           double c1 = 0.5D * (ci - ai);
/* 789 */           double c2 = 0.5D * (ci + ai) - bi;
/* 790 */           double up = (c2 < 0.0D) ? (-0.5D * c1 / c2) : 0.0D;
/* 791 */           double cp = c0 + up * (c1 + up * c2);
/* 792 */           if (cp > cmax[i1])
/* 793 */           { cmax[i1] = (float)cp;
/* 794 */             u[i1] = (float)(lag + up); }  }  }  }  }
/*     */   public void shift1(float[][][] du, float[][][] u1, float[][][] u2, float[][][] u3, float[][][] h) { int n1 = (h[0][0]).length; int n2 = (h[0]).length; int n3 = h.length; float[] xu1 = new float[n1]; float[] u1b = new float[n1]; float[] u2b = new float[n1]; float[] u3b = new float[n1]; float[] hb = new float[n1]; for (int i3 = 0; i3 < n3; i3++) { for (int i2 = 0; i2 < n2; i2++) { float[] ha = h[i3][i2]; float[] u1a = u1[i3][i2]; float[] u2a = u2[i3][i2]; float[] u3a = u3[i3][i2]; float[] du1 = du[i3][i2]; int i1; for (i1 = 0; i1 < n1; i1++)
/*     */           xu1[i1] = i1 + du1[i1];  this._si.interpolate(n1, 1.0D, 0.0D, ha, n1, xu1, hb); if (this._interpolateDisplacements) { this._si.interpolate(n1, 1.0D, 0.0D, u1a, n1, xu1, u1b); this._si.interpolate(n1, 1.0D, 0.0D, u2a, n1, xu1, u2b); this._si.interpolate(n1, 1.0D, 0.0D, u3a, n1, xu1, u3b); } else { ArrayMath.copy(u1a, u1b); ArrayMath.copy(u2a, u2b); ArrayMath.copy(u3a, u3b); }  for (i1 = 0; i1 < n1; i1++) { h[i3][i2][i1] = hb[i1]; u1[i3][i2][i1] = u1b[i1] + du1[i1]; u2[i3][i2][i1] = u2b[i1]; u3[i3][i2][i1] = u3b[i1]; }  }  }  }
/*     */   public void shift2(float[][][] du, float[][][] u1, float[][][] u2, float[][][] u3, float[][][] h) { int n1 = (h[0][0]).length; int n2 = (h[0]).length; int n3 = h.length; float[] du2 = new float[n2]; float[] xu2 = new float[n2]; float[] u1a = new float[n2]; float[] u1b = new float[n2]; float[] u2a = new float[n2]; float[] u2b = new float[n2]; float[] u3a = new float[n2]; float[] u3b = new float[n2]; float[] ha = new float[n2]; float[] hb = new float[n2]; for (int i3 = 0; i3 < n3; i3++) { for (int i1 = 0; i1 < n1; i1++) { int i2; for (i2 = 0; i2 < n2; i2++) { ha[i2] = h[i3][i2][i1]; u1a[i2] = u1[i3][i2][i1]; u2a[i2] = u2[i3][i2][i1]; u3a[i2] = u3[i3][i2][i1]; du2[i2] = du[i3][i2][i1]; xu2[i2] = i2 + du2[i2]; }  this._si.interpolate(n2, 1.0D, 0.0D, ha, n2, xu2, hb); if (this._interpolateDisplacements) { this._si.interpolate(n2, 1.0D, 0.0D, u1a, n2, xu2, u1b); this._si.interpolate(n2, 1.0D, 0.0D, u2a, n2, xu2, u2b); this._si.interpolate(n2, 1.0D, 0.0D, u3a, n2, xu2, u3b); } else { ArrayMath.copy(u1a, u1b); ArrayMath.copy(u2a, u2b); ArrayMath.copy(u3a, u3b); }  for (i2 = 0; i2 < n2; i2++) { h[i3][i2][i1] = hb[i2]; u1[i3][i2][i1] = u1b[i2]; u2[i3][i2][i1] = u2b[i2] + du2[i2]; u3[i3][i2][i1] = u3b[i2]; }  }  }  }
/*     */   public void shift3(float[][][] du, float[][][] u1, float[][][] u2, float[][][] u3, float[][][] h) { int n1 = (h[0][0]).length; int n2 = (h[0]).length; int n3 = h.length; float[] du3 = new float[n3]; float[] xu3 = new float[n3]; float[] u1a = new float[n3]; float[] u1b = new float[n3]; float[] u2a = new float[n3]; float[] u2b = new float[n3]; float[] u3a = new float[n3]; float[] u3b = new float[n3]; float[] ha = new float[n3]; float[] hb = new float[n3]; for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++) { int i3; for (i3 = 0; i3 < n3; i3++) { ha[i3] = h[i3][i2][i1]; u1a[i3] = u1[i3][i2][i1]; u2a[i3] = u2[i3][i2][i1]; u3a[i3] = u3[i3][i2][i1]; du3[i3] = du[i3][i2][i1]; xu3[i3] = i3 + du3[i3]; }  this._si.interpolate(n3, 1.0D, 0.0D, ha, n3, xu3, hb); if (this._interpolateDisplacements) { this._si.interpolate(n3, 1.0D, 0.0D, u1a, n3, xu3, u1b); this._si.interpolate(n3, 1.0D, 0.0D, u2a, n3, xu3, u2b); this._si.interpolate(n3, 1.0D, 0.0D, u3a, n3, xu3, u3b); }
/*     */         else { ArrayMath.copy(u1a, u1b); ArrayMath.copy(u2a, u2b); ArrayMath.copy(u3a, u3b); }
/*     */          for (i3 = 0; i3 < n3; i3++) { h[i3][i2][i1] = hb[i3]; u1[i3][i2][i1] = u1b[i3]; u2[i3][i2][i1] = u2b[i3]; u3[i3][i2][i1] = u3b[i3] + du3[i3]; }
/*     */          }
/*     */        }
/*     */      }
/* 804 */   public void whiten(float[][] f, float[][] g) { whiten(1.0D, f, g); } private void findShifts(int dim, int min, int max, float[][] f, float[][] g, float[][] u) { int n1 = (f[0]).length;
/* 805 */     int n2 = f.length;
/*     */ 
/*     */     
/* 808 */     ArrayMath.zero(u);
/*     */ 
/*     */     
/* 811 */     float[][][] c = new float[3][n2][n1];
/*     */ 
/*     */     
/* 814 */     float[][] cmax = new float[n2][n1];
/*     */ 
/*     */     
/* 817 */     LocalCorrelationFilter lcf = this._lcfSimple;
/* 818 */     lcf.setInputs(f, g);
/* 819 */     int lag1 = (dim == 1) ? min : 0;
/* 820 */     int lag2 = (dim == 2) ? min : 0;
/* 821 */     lcf.correlate(lag1, lag2, c[1]);
/* 822 */     lcf.normalize(lag1, lag2, c[1]);
/*     */ 
/*     */     
/* 825 */     for (int lag = min; lag <= max; lag++)
/*     */     
/*     */     { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 833 */       int i = lag - min;
/* 834 */       float[][] ca = (lag > min) ? c[i % 3] : c[(i + 2) % 3];
/* 835 */       float[][] cb = c[(i + 1) % 3];
/* 836 */       float[][] cc = (lag < max) ? c[(i + 2) % 3] : c[i % 3];
/*     */ 
/*     */       
/* 839 */       if (lag < max) {
/* 840 */         lag1 = (dim == 1) ? (lag + 1) : 0;
/* 841 */         lag2 = (dim == 2) ? (lag + 1) : 0;
/* 842 */         lcf.correlate(lag1, lag2, cc);
/* 843 */         lcf.normalize(lag1, lag2, cc);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 849 */       for (int i2 = 0; i2 < n2; i2++)
/* 850 */       { float[] ca2 = ca[i2];
/* 851 */         float[] cb2 = cb[i2];
/* 852 */         float[] cc2 = cc[i2];
/* 853 */         for (int i1 = 0; i1 < n1; i1++)
/* 854 */         { float ai = ca2[i1];
/* 855 */           float bi = cb2[i1];
/* 856 */           float ci = cc2[i1];
/* 857 */           if (bi >= ai && bi >= ci)
/* 858 */           { double c0 = bi;
/* 859 */             double c1 = 0.5D * (ci - ai);
/* 860 */             double c2 = 0.5D * (ci + ai) - bi;
/* 861 */             double up = (c2 < 0.0D) ? (-0.5D * c1 / c2) : 0.0D;
/* 862 */             double cp = c0 + up * (c1 + up * c2);
/* 863 */             if (cp > cmax[i2][i1])
/* 864 */             { cmax[i2][i1] = (float)cp;
/* 865 */               u[i2][i1] = (float)(lag + up); }  }  }  }  }  } public void whiten(double sigma, float[][] f, float[][] g) { int n1 = (f[0]).length; int n2 = f.length; float[][] r00 = new float[n2][n1]; float[][] rpm = new float[n2][n1]; float[][] rm0 = new float[n2][n1]; float[][] r0m = new float[n2][n1]; this._lcfSimple.setInputs(f, f); this._lcfSimple.correlate(0, 0, r00); this._lcfSimple.correlate(1, -1, rpm); this._lcfSimple.correlate(-1, 0, rm0); this._lcfSimple.correlate(0, -1, r0m); float[][] s = rm0; float[][] t = r0m; for (int i = 0; i < n2; i++)
/*     */       s[i][0] = 0.0F;  for (int i1 = 0; i1 < n1; i1++)
/*     */       s[0][i1] = 0.0F;  for (int i2 = 1; i2 < n2; i2++) { for (int j = 1; j < n1; j++) { double b1 = rm0[i2][j]; double b2 = r0m[i2][j]; double a11 = r00[i2][j - 1]; double a21 = rpm[i2][j - 1]; double a22 = r00[i2 - 1][j]; double l11 = ArrayMath.sqrt(a11); double l21 = a21 / l11; double d22 = a22 - l21 * l21; double x1 = 0.0D; double x2 = 0.0D; if (d22 > 0.0D) { double l22 = ArrayMath.sqrt(d22); double v1 = b1 / l11; double v2 = (b2 - l21 * v1) / l22; x2 = v2 / l22; x1 = (v1 - l21 * x2) / l11; }  float a1 = (float)x1; float a2 = (float)x2; s[i2][j] = f[i2][j] - a1 * f[i2][j - 1] - a2 * f[i2 - 1][j]; }  }  if (sigma >= 1.0D) { RecursiveGaussianFilter rgf = new RecursiveGaussianFilter(sigma); rgf.apply0X(s, t); rgf.applyX0(t, g); } else { ArrayMath.copy(s, g); }  }
/*     */   public void whiten(float[][][] f, float[][][] g) { whiten(1.0D, f, g); }
/*     */   public void whiten(double sigma, float[][][] f, float[][][] g) { int n1 = (f[0][0]).length; int n2 = (f[0]).length; int n3 = f.length; float[][][] r000 = new float[n3][n2][n1]; float[][][] rpm0 = new float[n3][n2][n1]; float[][][] rp0m = new float[n3][n2][n1]; float[][][] r0pm = new float[n3][n2][n1]; float[][][] rm00 = new float[n3][n2][n1]; float[][][] r0m0 = new float[n3][n2][n1]; float[][][] r00m = new float[n3][n2][n1]; float[][][] s = rm00; float[][][] t = r0m0; this._lcfSimple.setInputs(f, f); this._lcfSimple.correlate(0, 0, 0, r000); this._lcfSimple.correlate(1, -1, 0, rpm0); this._lcfSimple.correlate(1, 0, -1, rp0m); this._lcfSimple.correlate(0, 1, -1, r0pm); this._lcfSimple.correlate(-1, 0, 0, rm00); this._lcfSimple.correlate(0, -1, 0, r0m0); this._lcfSimple.correlate(0, 0, -1, r00m); int i; for (i = 0; i < n3; i++) { for (int j = 0; j < n2; j++)
/*     */         s[i][j][0] = 0.0F;  }  for (i = 0; i < n3; i++) { for (int i1 = 0; i1 < n1; i1++)
/*     */         s[i][0][i1] = 0.0F;  }  for (int i2 = 0; i2 < n2; i2++) { for (int i1 = 0; i1 < n1; i1++)
/*     */         s[0][i2][i1] = 0.0F;  }  for (int i3 = 1; i3 < n3; i3++) { for (int j = 1; j < n2; j++) { for (int i1 = 1; i1 < n1; i1++) { double b1 = rm00[i3][j][i1]; double b2 = r0m0[i3][j][i1]; double b3 = r00m[i3][j][i1]; double a11 = r000[i3][j][i1 - 1]; double a21 = rpm0[i3][j][i1 - 1]; double a22 = r000[i3][j - 1][i1]; double a31 = rp0m[i3][j][i1 - 1]; double a32 = r0pm[i3][j - 1][i1]; double a33 = r000[i3 - 1][j][i1]; double x1 = 0.0D; double x2 = 0.0D; double x3 = 0.0D; double l11 = ArrayMath.sqrt(a11); double l21 = a21 / l11; double l31 = a31 / l11; double d22 = a22 - l21 * l21; if (d22 > 0.0D) { double l22 = ArrayMath.sqrt(d22); double l32 = (a32 - l31 * l21) / l22; double d33 = a33 - l31 * l31 - l32 * l32; if (d33 > 0.0D) { double l33 = ArrayMath.sqrt(d33); double v1 = b1 / l11; double v2 = (b2 - l21 * v1) / l22; double v3 = (b3 - l31 * v1 - l32 * v2) / l33; x3 = v3 / l33; x2 = (v2 - l32 * x3) / l22; x1 = (v1 - l21 * x2 - l31 * x3) / l11; }  }  float a1 = (float)x1; float a2 = (float)x2; float a3 = (float)x3; s[i3][j][i1] = f[i3][j][i1] - a1 * f[i3][j][i1 - 1] - a2 * f[i3][j - 1][i1] - a3 * f[i3 - 1][j][i1]; }  }  }
/*     */      if (sigma >= 1.0D) { RecursiveGaussianFilter rgf = new RecursiveGaussianFilter(sigma); rgf.apply0XX(s, t); rgf.applyX0X(t, s); rgf.applyXX0(s, g); }
/*     */     else { ArrayMath.copy(s, g); }
/*     */      }
/* 876 */   private void findShifts(int dim, int min, int max, float[][][] f, float[][][] g, float[][][] u) { int n1 = (f[0][0]).length;
/* 877 */     int n2 = (f[0]).length;
/* 878 */     int n3 = f.length;
/*     */ 
/*     */     
/* 881 */     ArrayMath.zero(u);
/*     */ 
/*     */     
/* 884 */     float[][][][] c = new float[3][n3][n2][n1];
/*     */ 
/*     */     
/* 887 */     float[][][] cmax = new float[n3][n2][n1];
/*     */ 
/*     */     
/* 890 */     LocalCorrelationFilter lcf = this._lcfSimple;
/* 891 */     lcf.setInputs(f, g);
/* 892 */     int lag1 = (dim == 1) ? min : 0;
/* 893 */     int lag2 = (dim == 2) ? min : 0;
/* 894 */     int lag3 = (dim == 3) ? min : 0;
/* 895 */     lcf.correlate(lag1, lag2, lag3, c[1]);
/* 896 */     lcf.normalize(lag1, lag2, lag3, c[1]);
/*     */ 
/*     */     
/* 899 */     for (int lag = min; lag <= max; lag++) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 907 */       int i = lag - min;
/* 908 */       float[][][] ca = (lag > min) ? c[i % 3] : c[(i + 2) % 3];
/* 909 */       float[][][] cb = c[(i + 1) % 3];
/* 910 */       float[][][] cc = (lag < max) ? c[(i + 2) % 3] : c[i % 3];
/*     */ 
/*     */       
/* 913 */       if (lag < max) {
/* 914 */         lag1 = (dim == 1) ? (lag + 1) : 0;
/* 915 */         lag2 = (dim == 2) ? (lag + 1) : 0;
/* 916 */         lag3 = (dim == 3) ? (lag + 1) : 0;
/* 917 */         lcf.correlate(lag1, lag2, lag3, cc);
/* 918 */         lcf.normalize(lag1, lag2, lag3, cc);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 924 */       for (int i3 = 0; i3 < n3; i3++) {
/* 925 */         for (int i2 = 0; i2 < n2; i2++) {
/* 926 */           float[] ca32 = ca[i3][i2];
/* 927 */           float[] cb32 = cb[i3][i2];
/* 928 */           float[] cc32 = cc[i3][i2];
/* 929 */           for (int i1 = 0; i1 < n1; i1++) {
/* 930 */             float ai = ca32[i1];
/* 931 */             float bi = cb32[i1];
/* 932 */             float ci = cc32[i1];
/* 933 */             if (bi >= ai && bi >= ci) {
/* 934 */               double c0 = bi;
/* 935 */               double c1 = 0.5D * (ci - ai);
/* 936 */               double c2 = 0.5D * (ci + ai) - bi;
/* 937 */               double up = (c2 < 0.0D) ? (-0.5D * c1 / c2) : 0.0D;
/* 938 */               double cp = c0 + up * (c1 + up * c2);
/* 939 */               if (cp > cmax[i3][i2][i1]) {
/* 940 */                 cmax[i3][i2][i1] = (float)cp;
/* 941 */                 u[i3][i2][i1] = (float)(lag + up);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/LocalShiftFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */