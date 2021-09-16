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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomFloat
/*     */ {
/*     */   private static final float CS = 0.021602869F;
/*     */   private static final float CD = 0.45623308F;
/*     */   private static final float CM = 0.9999998F;
/*     */   private static final int NBITS = 24;
/*     */   
/*     */   public RandomFloat() {}
/*     */   
/*     */   public RandomFloat(int seed) {
/*  54 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float uniform() {
/*  65 */     float uni = this._u[this._i] - this._u[this._j];
/*  66 */     if (uni < 0.0D) uni = (float)(uni + 1.0D); 
/*  67 */     this._u[this._i] = uni;
/*  68 */     if (--this._i < 0) this._i = 16; 
/*  69 */     if (--this._j < 0) this._j = 16;
/*     */ 
/*     */     
/*  72 */     this._c -= 0.45623308F;
/*  73 */     if (this._c < 0.0D) this._c += 0.9999998F;
/*     */ 
/*     */     
/*  76 */     uni -= this._c;
/*  77 */     if (uni < 0.0D) uni++; 
/*  78 */     return uni;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float normal() {
/*  89 */     float uni = this._u[this._i] - this._u[this._j];
/*  90 */     if (uni < 0.0D) uni = (float)(uni + 1.0D); 
/*  91 */     this._u[this._i] = uni;
/*  92 */     if (--this._i < 0) this._i = 16; 
/*  93 */     if (--this._j < 0) this._j = 16;
/*     */ 
/*     */     
/*  96 */     float vni = uni + uni - 1.0F;
/*     */ 
/*     */     
/*  99 */     int k = (int)(this._u[this._i] * 128.0F) % 64;
/*     */ 
/*     */     
/* 102 */     float rnor = vni * _v[k + 1];
/* 103 */     if (rnor <= _v[k] && -rnor <= _v[k]) return rnor;
/*     */ 
/*     */     
/* 106 */     float x = (Math.abs(rnor) - _v[k]) / (_v[k + 1] - _v[k]);
/* 107 */     float y = fib();
/* 108 */     float s = x + y;
/* 109 */     if (s <= 1.301198F) {
/* 110 */       if (s <= 0.9689279F) return rnor; 
/* 111 */       float f = 0.4878992F - 0.4878992F * x;
/* 112 */       if (y <= 12.6770601272583D - 12.375860214233398D * Math.exp((-0.5F * f * f))) {
/* 113 */         if (Math.exp((-0.5F * _v[k + 1] * _v[k + 1])) + (y * 0.01958303F / _v[k + 1]) <= 
/* 114 */           Math.exp((-0.5F * rnor * rnor))) return rnor; 
/*     */         while (true) {
/* 116 */           y = fib();
/* 117 */           x = 0.3601016F * (float)Math.log(y);
/* 118 */           y = fib();
/* 119 */           if (-2.0D * Math.log(y) > (x * x))
/* 120 */           { float xnmx = 2.776994F - x;
/* 121 */             return (rnor >= 0.0F) ? Math.abs(xnmx) : -Math.abs(xnmx); } 
/*     */         } 
/*     */       } 
/* 124 */     }  float bmbx = 0.4878992F - 0.4878992F * x;
/* 125 */     return (rnor >= 0.0D) ? Math.abs(bmbx) : -Math.abs(bmbx);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/* 136 */     if (seed < 0) seed = -seed; 
/* 137 */     int i1 = seed % 177 + 1;
/* 138 */     int j1 = seed % 167 + 1;
/* 139 */     int k1 = seed % 157 + 1;
/* 140 */     int l1 = seed % 147 + 1;
/*     */ 
/*     */     
/* 143 */     for (int ii = 0; ii < 17; ii++) {
/* 144 */       float s = 0.0F;
/* 145 */       float t = 0.5F;
/*     */ 
/*     */       
/* 148 */       for (int jj = 0; jj < 24; jj++) {
/* 149 */         int m1 = i1 * j1 % 179 * k1 % 179;
/* 150 */         i1 = j1;
/* 151 */         j1 = k1;
/* 152 */         k1 = m1;
/* 153 */         l1 = (53 * l1 + 1) % 169;
/* 154 */         if (l1 * m1 % 64 >= 32) s += t; 
/* 155 */         t = (float)(t * 0.5D);
/*     */       } 
/* 157 */       this._u[ii] = s;
/*     */     } 
/*     */ 
/*     */     
/* 161 */     this._i = 16;
/* 162 */     this._j = 4;
/* 163 */     this._c = 0.021602869F;
/*     */   }
/*     */   
/*     */   private float fib() {
/* 167 */     float r = this._u[this._i] - this._u[this._j];
/* 168 */     if (r < 0.0F) r++; 
/* 169 */     this._u[this._i] = r;
/* 170 */     if (--this._i < 0) this._i = 16; 
/* 171 */     if (--this._j < 0) this._j = 16; 
/* 172 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 180 */   private int _i = 16;
/* 181 */   private int _j = 4;
/* 182 */   private float _c = 0.021602869F;
/* 183 */   private float[] _u = new float[] { 0.8668673F, 0.36979863F, 0.8008968F, 0.41738898F, 0.82545614F, 0.96409655F, 0.45086673F, 0.64513093F, 0.1645456F, 0.27879018F, 0.067615315F, 0.96632266F, 0.01963344F, 0.029473983F, 0.16362315F, 0.39763433F, 0.26310086F };
/*     */ 
/*     */   
/*     */   private static final float AA = 12.37586F;
/*     */   
/*     */   private static final float B = 0.4878992F;
/*     */   
/*     */   private static final float C = 12.67706F;
/*     */   
/*     */   private static final float C1 = 0.9689279F;
/*     */   
/*     */   private static final float C2 = 1.301198F;
/*     */   
/*     */   private static final float PC = 0.01958303F;
/*     */   
/*     */   private static final float XN = 2.776994F;
/*     */   
/*     */   private static final float OXN = 0.3601016F;
/*     */   
/* 202 */   private static float[] _v = new float[] { 0.340945F, 0.4573146F, 0.5397793F, 0.6062427F, 0.6631691F, 0.7136975F, 0.7596125F, 0.8020356F, 0.8417227F, 0.8792102F, 0.9148948F, 0.9490791F, 0.9820005F, 1.0138493F, 1.044781F, 1.0749254F, 1.1043917F, 1.1332738F, 1.161653F, 1.189601F, 1.2171814F, 1.2444516F, 1.2714635F, 1.298265F, 1.3249007F, 1.3514125F, 1.3778399F, 1.404221F, 1.4305929F, 1.4569916F, 1.4834526F, 1.5100121F, 1.5367061F, 1.5635712F, 1.5906454F, 1.617968F, 1.6455802F, 1.6735255F, 1.7018503F, 1.7306045F, 1.7598422F, 1.7896223F, 1.82001F, 1.851077F, 1.8829044F, 1.915583F, 1.9492166F, 1.9839239F, 2.019843F, 2.0571356F, 2.095993F, 2.136645F, 2.1793714F, 2.2245176F, 2.2725184F, 2.3239338F, 2.3795006F, 2.4402218F, 2.5075116F, 2.5834658F, 2.6713915F, 2.7769942F, 2.7769942F, 2.7769942F, 2.7769942F };
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/RandomFloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */