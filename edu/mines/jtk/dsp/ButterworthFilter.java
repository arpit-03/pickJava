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
/*     */ public class ButterworthFilter
/*     */   extends RecursiveCascadeFilter
/*     */ {
/*     */   private Cdouble[] _poles;
/*     */   private Cdouble[] _zeros;
/*     */   private double _gain;
/*     */   
/*     */   public enum Type
/*     */   {
/*  31 */     LOW_PASS, HIGH_PASS;
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
/*     */   public ButterworthFilter(double fl, double al, double fh, double ah) {
/*  53 */     Check.argument((0.0D < fl), "0.0<fl");
/*  54 */     Check.argument((fl < fh), "fl<fh");
/*  55 */     Check.argument((fh < 0.5D), "fh<0.5");
/*  56 */     Check.argument((0.0D < al), "0.0<al");
/*  57 */     Check.argument((al < 1.0D), "al<1.0");
/*  58 */     Check.argument((al != ah), "al!=ah");
/*  59 */     Check.argument((0.0D < ah), "0.0<ah");
/*  60 */     Check.argument((ah < 1.0D), "ah<1.0");
/*  61 */     double wl = 6.283185307179586D * fl;
/*  62 */     double wh = 6.283185307179586D * fh;
/*  63 */     double xl = 2.0D * MathPlus.tan(wl / 2.0D);
/*  64 */     double xh = 2.0D * MathPlus.tan(wh / 2.0D);
/*  65 */     double pl = al * al;
/*  66 */     double ph = ah * ah;
/*  67 */     if (al >= ah) {
/*  68 */       int np = (int)MathPlus.ceil(0.5D * MathPlus.log(pl * (1.0D - ph) / ph * (1.0D - pl)) / MathPlus.log(xh / xl));
/*  69 */       double xc = xl * MathPlus.pow(pl / (1.0D - pl), 0.5D / np);
/*  70 */       double wc = 2.0D * MathPlus.atan(xc / 2.0D);
/*  71 */       double fc = 0.5D * wc / Math.PI;
/*  72 */       makePolesZerosGain(fc, np, Type.LOW_PASS);
/*     */     } else {
/*  74 */       int np = (int)MathPlus.ceil(0.5D * MathPlus.log(ph * (1.0D - pl) / pl * (1.0D - ph)) / MathPlus.log(xh / xl));
/*  75 */       double xc = xh * MathPlus.pow((1.0D - ph) / ph, 0.5D / np);
/*  76 */       double wc = 2.0D * MathPlus.atan(xc / 2.0D);
/*  77 */       double fc = 0.5D * wc / Math.PI;
/*  78 */       makePolesZerosGain(fc, np, Type.HIGH_PASS);
/*     */     } 
/*  80 */     init(this._poles, this._zeros, this._gain);
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
/*     */   public ButterworthFilter(double fc, int np, Type type) {
/*  92 */     Check.argument((0.0D < fc), "0.0<fc");
/*  93 */     Check.argument((fc < 0.5D), "fc<0.5");
/*  94 */     Check.argument((np > 0), "np>0");
/*  95 */     makePolesZerosGain(fc, np, type);
/*  96 */     init(this._poles, this._zeros, this._gain);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void makePolesZerosGain(double fc, int np, Type type) {
/* 107 */     boolean lowpass = (type == Type.LOW_PASS);
/* 108 */     double omegac = 2.0D * MathPlus.tan(Math.PI * fc);
/* 109 */     double dtheta = Math.PI / np;
/* 110 */     double ftheta = 0.5D * dtheta * (np + 1);
/* 111 */     this._poles = new Cdouble[np];
/* 112 */     this._zeros = new Cdouble[np];
/* 113 */     Cdouble c1 = new Cdouble(1.0D, 0.0D);
/* 114 */     Cdouble c2 = new Cdouble(2.0D, 0.0D);
/* 115 */     Cdouble zj = lowpass ? c1.neg() : c1;
/* 116 */     Cdouble gain = new Cdouble(c1);
/* 117 */     for (int j = 0, k = np - 1; j < np; j++, k--) {
/* 118 */       double theta = ftheta + j * dtheta;
/* 119 */       Cdouble sj = Cdouble.polar(omegac, theta);
/* 120 */       this._zeros[j] = zj;
/* 121 */       if (j == k) {
/* 122 */         this._poles[j] = c2.plus(sj).over(c2.minus(sj));
/* 123 */         (this._poles[j]).i = 0.0D;
/* 124 */       } else if (j < k) {
/* 125 */         this._poles[j] = c2.plus(sj).over(c2.minus(sj));
/* 126 */         this._poles[k] = this._poles[j].conj();
/*     */       } 
/* 128 */       if (lowpass) {
/* 129 */         gain.timesEquals(sj.over(sj.minus(c2)));
/*     */       } else {
/* 131 */         gain.timesEquals(c2.over(c2.minus(sj)));
/*     */       } 
/*     */     } 
/* 134 */     this._gain = gain.r;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/ButterworthFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */