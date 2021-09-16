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
/*     */ public class BandPassFilter
/*     */ {
/*     */   private double _klower;
/*     */   private double _kupper;
/*     */   private double _kwidth;
/*     */   private double _aerror;
/*     */   private FftFilter _ff1;
/*     */   private FftFilter _ff2;
/*     */   private FftFilter _ff3;
/*     */   private float[] _h1;
/*     */   private float[][] _h2;
/*     */   private float[][][] _h3;
/*     */   private Extrapolation _extrapolation;
/*     */   private boolean _filterCaching;
/*     */   
/*     */   public enum Extrapolation
/*     */   {
/*  48 */     ZERO_VALUE,
/*     */ 
/*     */ 
/*     */     
/*  52 */     ZERO_SLOPE;
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
/*     */   public void setExtrapolation(Extrapolation extrapolation) {
/*     */     if (this._extrapolation != extrapolation) {
/*     */       this._extrapolation = extrapolation;
/*     */       this._ff1 = this._ff2 = this._ff3 = null;
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
/*     */   public void setFilterCaching(boolean filterCaching) {
/*     */     if (this._filterCaching != filterCaching) {
/*     */       this._filterCaching = filterCaching;
/*     */       this._ff1 = this._ff2 = this._ff3 = null;
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
/*     */   public float[] getCoefficients1() {
/*     */     updateFilter1();
/*     */     return ArrayMath.copy(this._h1);
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
/*     */   public BandPassFilter(double klower, double kupper, double kwidth, double aerror) {
/* 179 */     this._extrapolation = Extrapolation.ZERO_VALUE; Check.argument((0.0D <= klower), "0<=klower"); Check.argument((klower < kupper), "klower<kupper"); Check.argument((kupper <= 0.5D), "kupper<0.5"); Check.argument((0.0D <= kwidth), "0<=kwidth"); Check.argument((kwidth <= kupper - klower), "kwidth<=kupper-klower"); Check.argument((0.0D < aerror), "0<aerror"); Check.argument((aerror < 1.0D), "aerror<1"); this._klower = klower; this._kupper = kupper;
/*     */     this._kwidth = kwidth;
/*     */     this._aerror = aerror;
/*     */   } public float[][] getCoefficients2() { updateFilter2();
/* 183 */     return ArrayMath.copy(this._h2); } private FftFilter.Extrapolation ffExtrap(Extrapolation e) { if (e == Extrapolation.ZERO_VALUE)
/* 184 */       return FftFilter.Extrapolation.ZERO_VALUE; 
/* 185 */     if (e == Extrapolation.ZERO_SLOPE) {
/* 186 */       return FftFilter.Extrapolation.ZERO_SLOPE;
/*     */     }
/* 188 */     return null; }
/*     */   public float[][][] getCoefficients3() { updateFilter3();
/*     */     return ArrayMath.copy(this._h3); }
/*     */   public void apply(float[] x, float[] y) { updateFilter1();
/*     */     this._ff1.apply(x, y); }
/* 193 */   private void updateFilter1() { if (this._ff1 == null) {
/* 194 */       KaiserWindow kw = KaiserWindow.fromErrorAndWidth(this._aerror, this._kwidth);
/* 195 */       int nh = ((int)kw.getLength() + 1) / 2 * 2 + 1;
/* 196 */       int nh1 = nh;
/* 197 */       int kh1 = (nh1 - 1) / 2;
/* 198 */       this._h1 = new float[nh1];
/* 199 */       double kus = 2.0D * this._kupper;
/* 200 */       double kls = 2.0D * this._klower;
/* 201 */       for (int i1 = 0; i1 < nh1; i1++) {
/* 202 */         double x1 = (i1 - kh1);
/* 203 */         double w1 = kw.evaluate(x1);
/* 204 */         double r = x1;
/* 205 */         double kur = 2.0D * this._kupper * r;
/* 206 */         double klr = 2.0D * this._klower * r;
/* 207 */         this._h1[i1] = (float)(w1 * (kus * h1(kur) - kls * h1(klr)));
/*     */       } 
/* 209 */       this._ff1 = new FftFilter(this._h1);
/* 210 */       this._ff1.setExtrapolation(ffExtrap(this._extrapolation));
/* 211 */       this._ff1.setFilterCaching(this._filterCaching);
/*     */     }  }
/*     */   public void apply(float[][] x, float[][] y) { updateFilter2();
/*     */     this._ff2.apply(x, y); }
/*     */   public void apply(float[][][] x, float[][][] y) { updateFilter3();
/* 216 */     this._ff3.apply(x, y); } private void updateFilter2() { if (this._ff2 == null) {
/* 217 */       KaiserWindow kw = KaiserWindow.fromErrorAndWidth(this._aerror, this._kwidth);
/* 218 */       int nh = ((int)kw.getLength() + 1) / 2 * 2 + 1;
/* 219 */       int nh1 = nh;
/* 220 */       int nh2 = nh;
/* 221 */       int kh1 = (nh1 - 1) / 2;
/* 222 */       int kh2 = (nh2 - 1) / 2;
/* 223 */       this._h2 = new float[nh2][nh1];
/* 224 */       double kus = 4.0D * this._kupper * this._kupper;
/* 225 */       double kls = 4.0D * this._klower * this._klower;
/* 226 */       for (int i2 = 0; i2 < nh2; i2++) {
/* 227 */         double x2 = (i2 - kh2);
/* 228 */         double w2 = kw.evaluate(x2);
/* 229 */         for (int i1 = 0; i1 < nh1; i1++) {
/* 230 */           double x1 = (i1 - kh1);
/* 231 */           double w1 = kw.evaluate(x1);
/* 232 */           double r = ArrayMath.sqrt(x1 * x1 + x2 * x2);
/* 233 */           double kur = 2.0D * this._kupper * r;
/* 234 */           double klr = 2.0D * this._klower * r;
/* 235 */           this._h2[i2][i1] = (float)(w1 * w2 * (kus * h2(kur) - kls * h2(klr)));
/*     */         } 
/*     */       } 
/* 238 */       this._ff2 = new FftFilter(this._h2);
/* 239 */       this._ff2.setExtrapolation(ffExtrap(this._extrapolation));
/* 240 */       this._ff2.setFilterCaching(this._filterCaching);
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void updateFilter3() {
/* 245 */     if (this._ff3 == null) {
/* 246 */       KaiserWindow kw = KaiserWindow.fromErrorAndWidth(this._aerror, this._kwidth);
/* 247 */       int nh = ((int)kw.getLength() + 1) / 2 * 2 + 1;
/* 248 */       int nh1 = nh;
/* 249 */       int nh2 = nh;
/* 250 */       int nh3 = nh;
/* 251 */       int kh1 = (nh1 - 1) / 2;
/* 252 */       int kh2 = (nh2 - 1) / 2;
/* 253 */       int kh3 = (nh3 - 1) / 2;
/* 254 */       this._h3 = new float[nh3][nh2][nh1];
/* 255 */       double kus = 8.0D * this._kupper * this._kupper * this._kupper;
/* 256 */       double kls = 8.0D * this._klower * this._klower * this._klower;
/* 257 */       for (int i3 = 0; i3 < nh3; i3++) {
/* 258 */         double x3 = (i3 - kh3);
/* 259 */         double w3 = kw.evaluate(x3);
/* 260 */         for (int i2 = 0; i2 < nh2; i2++) {
/* 261 */           double x2 = (i2 - kh2);
/* 262 */           double w2 = kw.evaluate(x2);
/* 263 */           for (int i1 = 0; i1 < nh1; i1++) {
/* 264 */             double x1 = (i1 - kh1);
/* 265 */             double w1 = kw.evaluate(x1);
/* 266 */             double r = ArrayMath.sqrt(x1 * x1 + x2 * x2 + x3 * x3);
/* 267 */             double kur = 2.0D * this._kupper * r;
/* 268 */             double klr = 2.0D * this._klower * r;
/* 269 */             this._h3[i3][i2][i1] = (float)(w1 * w2 * w3 * (kus * h3(kur) - kls * h3(klr)));
/*     */           } 
/*     */         } 
/*     */       } 
/* 273 */       this._ff3 = new FftFilter(this._h3);
/* 274 */       this._ff3.setExtrapolation(ffExtrap(this._extrapolation));
/* 275 */       this._ff3.setFilterCaching(this._filterCaching);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static double h1(double r) {
/* 280 */     return (r == 0.0D) ? 1.0D : (ArrayMath.sin(Math.PI * r) / Math.PI * r);
/*     */   }
/*     */   
/* 283 */   private static double PIO4 = 0.7853981633974483D;
/*     */   private static double h2(double r) {
/* 285 */     return (r == 0.0D) ? PIO4 : (besselJ1(Math.PI * r) / 2.0D * r);
/*     */   }
/*     */   private static double besselJ1(double x) {
/* 288 */     double ax = ArrayMath.abs(x);
/* 289 */     if (ax < 8.0D) {
/* 290 */       double xx = x * x;
/* 291 */       double num = x * (7.2362614232E10D + xx * (-7.895059235E9D + xx * (2.423968531E8D + xx * (-2972611.439D + xx * (15704.4826D + xx * -30.16036606D)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 297 */       double den = 1.44725228442E11D + xx * (2.300535178E9D + xx * (1.858330474E7D + xx * (99447.43394D + xx * (376.9991397D + xx))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 303 */       return num / den;
/*     */     } 
/* 305 */     double z = 8.0D / ax;
/* 306 */     double zz = z * z;
/* 307 */     double t1 = 1.0D + zz * (0.00183105D + zz * (-3.516396496E-5D + zz * (2.457520174E-6D + zz * -2.40337019E-7D)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     double t2 = 0.04687499995D + zz * (-2.002690873E-4D + zz * (8.449199096E-6D + zz * (-8.8228987E-7D + zz * 1.05787412E-7D)));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 317 */     double am = ax - 2.356194491D;
/* 318 */     double y = ArrayMath.sqrt(0.636619772D / ax) * (ArrayMath.cos(am) * t1 - z * ArrayMath.sin(am) * t2);
/* 319 */     return (x < 0.0D) ? -y : y;
/*     */   }
/*     */ 
/*     */   
/* 323 */   private static double PIO6 = 0.5235987755982988D;
/*     */   private static double h3(double r) {
/* 325 */     if (r == 0.0D) {
/* 326 */       return PIO6;
/*     */     }
/* 328 */     double pir = Math.PI * r;
/* 329 */     return 1.5707963267948966D * (ArrayMath.sin(pir) - pir * ArrayMath.cos(pir)) / pir * pir * pir;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/dsp/BandPassFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */