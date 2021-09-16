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
/*     */ public class Cfloat
/*     */ {
/*  27 */   public static final Cfloat FLT_I = new Cfloat(0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float r;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float i;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat() {
/*  43 */     this(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat(float r) {
/*  51 */     this(r, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat(float r, float i) {
/*  60 */     this.r = r;
/*  61 */     this.i = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat(Cfloat x) {
/*  69 */     this(x.r, x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat plus(Cfloat x) {
/*  78 */     return (new Cfloat(this)).plusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat minus(Cfloat x) {
/*  87 */     return (new Cfloat(this)).minusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat times(Cfloat x) {
/*  96 */     return (new Cfloat(this)).timesEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat over(Cfloat x) {
/* 105 */     return (new Cfloat(this)).overEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat plus(float x) {
/* 114 */     return (new Cfloat(this)).plusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat minus(float x) {
/* 123 */     return (new Cfloat(this)).minusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat times(float x) {
/* 132 */     return (new Cfloat(this)).timesEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat over(float x) {
/* 141 */     return (new Cfloat(this)).overEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat plusEquals(Cfloat x) {
/* 150 */     this.r += x.r;
/* 151 */     this.i += x.i;
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat minusEquals(Cfloat x) {
/* 161 */     this.r -= x.r;
/* 162 */     this.i -= x.i;
/* 163 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat timesEquals(Cfloat x) {
/* 172 */     float tr = this.r;
/* 173 */     float ti = this.i;
/* 174 */     float xr = x.r;
/* 175 */     float xi = x.i;
/* 176 */     this.r = tr * xr - ti * xi;
/* 177 */     this.i = tr * xi + ti * xr;
/* 178 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat overEquals(Cfloat x) {
/* 187 */     float tr = this.r;
/* 188 */     float ti = this.i;
/* 189 */     float xr = x.r;
/* 190 */     float xi = x.i;
/* 191 */     float d = norm(x);
/* 192 */     this.r = (tr * xr + ti * xi) / d;
/* 193 */     this.i = (ti * xr - tr * xi) / d;
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat plusEquals(float x) {
/* 203 */     this.r += x;
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat minusEquals(float x) {
/* 213 */     this.r -= x;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat timesEquals(float x) {
/* 223 */     this.r *= x;
/* 224 */     this.i *= x;
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat overEquals(float x) {
/* 234 */     this.r /= x;
/* 235 */     this.i /= x;
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat conjEquals() {
/* 244 */     this.i = -this.i;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat invEquals() {
/* 253 */     float d = norm();
/* 254 */     this.r /= d;
/* 255 */     this.i = -this.i / d;
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat negEquals() {
/* 264 */     this.r = -this.r;
/* 265 */     this.i = -this.i;
/* 266 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReal() {
/* 274 */     return (this.i == 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImag() {
/* 282 */     return (this.r == 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat conj() {
/* 290 */     return new Cfloat(this.r, -this.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat inv() {
/* 298 */     float d = norm();
/* 299 */     return new Cfloat(this.r / d, -this.i / d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat neg() {
/* 307 */     return new Cfloat(-this.r, -this.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float abs() {
/* 315 */     return abs(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float arg() {
/* 323 */     return arg(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float norm() {
/* 332 */     return norm(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat sqrt() {
/* 340 */     return sqrt(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat exp() {
/* 348 */     return exp(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat log() {
/* 356 */     return log(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat log10() {
/* 364 */     return log10(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat pow(float y) {
/* 373 */     return pow(this, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat pow(Cfloat y) {
/* 382 */     return pow(this, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat sin() {
/* 390 */     return sin(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat cos() {
/* 398 */     return cos(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat tan() {
/* 406 */     return tan(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat sinh() {
/* 414 */     return sinh(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat cosh() {
/* 422 */     return cosh(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cfloat tanh() {
/* 430 */     return tanh(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isReal(Cfloat x) {
/* 439 */     return (x.i == 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isImag(Cfloat x) {
/* 448 */     return (x.r == 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat conj(Cfloat x) {
/* 457 */     return new Cfloat(x.r, -x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat inv(Cfloat x) {
/* 466 */     float d = x.norm();
/* 467 */     return new Cfloat(x.r / d, -x.i / d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat neg(Cfloat x) {
/* 476 */     return new Cfloat(-x.r, -x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat polar(float r, float a) {
/* 486 */     return new Cfloat(r * cos(a), r * sin(a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat add(Cfloat x, Cfloat y) {
/* 496 */     return x.plus(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat sub(Cfloat x, Cfloat y) {
/* 506 */     return x.minus(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat mul(Cfloat x, Cfloat y) {
/* 516 */     return x.times(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat div(Cfloat x, Cfloat y) {
/* 526 */     return x.over(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float abs(Cfloat x) {
/* 535 */     float ar = abs(x.r);
/* 536 */     float ai = abs(x.i);
/* 537 */     float s = max(abs(ar), abs(ai));
/* 538 */     if (s == 0.0F)
/* 539 */       return 0.0F; 
/* 540 */     ar /= s;
/* 541 */     ai /= s;
/* 542 */     return s * sqrt(ar * ar + ai * ai);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float arg(Cfloat x) {
/* 551 */     return atan2(x.i, x.r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float norm(Cfloat x) {
/* 561 */     return x.r * x.r + x.i * x.i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat sqrt(Cfloat x) {
/* 570 */     if (x.r == 0.0F) {
/* 571 */       float f = sqrt(0.5F * abs(x.i));
/* 572 */       return new Cfloat(f, (x.i < 0.0F) ? -f : f);
/*     */     } 
/* 574 */     float t = sqrt(2.0F * (abs(x) + abs(x.r)));
/* 575 */     float u = 0.5F * t;
/* 576 */     return (x.r > 0.0F) ? new Cfloat(u, x.i / t) : new Cfloat(
/*     */         
/* 578 */         abs(x.i) / t, (x.i < 0.0F) ? -u : u);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat exp(Cfloat x) {
/* 588 */     return polar(exp(x.r), x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat log(Cfloat x) {
/* 597 */     return new Cfloat(log(abs(x)), arg(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat log10(Cfloat x) {
/* 606 */     return log(x).overEquals(log(10.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat pow(Cfloat x, float y) {
/* 616 */     if (x.i == 0.0F)
/* 617 */       return new Cfloat(pow(x.r, y)); 
/* 618 */     Cfloat t = log(x);
/* 619 */     return polar(exp(y * t.r), y * t.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat pow(float x, Cfloat y) {
/* 629 */     if (x == 0.0F)
/* 630 */       return new Cfloat(); 
/* 631 */     return polar(pow(x, y.r), y.i * log(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat pow(Cfloat x, Cfloat y) {
/* 641 */     if (x.r == 0.0F && x.i == 0.0F)
/* 642 */       return new Cfloat(); 
/* 643 */     return exp(y.times(log(x)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat sin(Cfloat x) {
/* 652 */     return new Cfloat(sin(x.r) * cosh(x.i), cos(x.r) * sinh(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat cos(Cfloat x) {
/* 661 */     return new Cfloat(cos(x.r) * cosh(x.i), -sin(x.r) * sinh(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat tan(Cfloat x) {
/* 670 */     return sin(x).overEquals(cos(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat sinh(Cfloat x) {
/* 679 */     return new Cfloat(sinh(x.r) * cos(x.i), cosh(x.r) * sin(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat cosh(Cfloat x) {
/* 688 */     return new Cfloat(cosh(x.r) * cos(x.i), sinh(x.r) * sin(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cfloat tanh(Cfloat x) {
/* 697 */     return sinh(x).overEquals(cosh(x));
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 701 */     if (this == obj)
/* 702 */       return true; 
/* 703 */     if (obj == null || getClass() != obj.getClass())
/* 704 */       return false; 
/* 705 */     Cfloat that = (Cfloat)obj;
/* 706 */     return (this.r == that.r && this.i == that.i);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 710 */     return Float.floatToIntBits(this.r) ^ Float.floatToIntBits(this.i);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 714 */     if (this.i == 0.0F)
/* 715 */       return "(" + this.r + "+0.0i)"; 
/* 716 */     if (this.i > 0.0F) {
/* 717 */       return "(" + this.r + "+" + this.i + "i)";
/*     */     }
/* 719 */     return "(" + this.r + "-" + -this.i + "i)";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float max(float x, float y) {
/* 727 */     return (x >= y) ? x : y;
/*     */   }
/*     */   
/*     */   private static float abs(float x) {
/* 731 */     return (x >= 0.0F) ? x : -x;
/*     */   }
/*     */   
/*     */   private static float sqrt(float x) {
/* 735 */     return (float)Math.sqrt(x);
/*     */   }
/*     */   
/*     */   private static float sin(float x) {
/* 739 */     return (float)Math.sin(x);
/*     */   }
/*     */   
/*     */   private static float cos(float x) {
/* 743 */     return (float)Math.cos(x);
/*     */   }
/*     */   
/*     */   private static float sinh(float x) {
/* 747 */     return (float)Math.sinh(x);
/*     */   }
/*     */   
/*     */   private static float cosh(float x) {
/* 751 */     return (float)Math.cosh(x);
/*     */   }
/*     */   
/*     */   private static float exp(float x) {
/* 755 */     return (float)Math.exp(x);
/*     */   }
/*     */   
/*     */   private static float log(float x) {
/* 759 */     return (float)Math.log(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float pow(float x, float y) {
/* 769 */     return (float)Math.pow(x, y);
/*     */   }
/*     */   
/*     */   private static float atan2(float y, float x) {
/* 773 */     return (float)Math.atan2(y, x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Cfloat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */