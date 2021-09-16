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
/*     */ public class Cdouble
/*     */ {
/*  27 */   public static final Cdouble DBL_I = new Cdouble(0.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double r;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double i;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble() {
/*  43 */     this(0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble(double r) {
/*  51 */     this(r, 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble(double r, double i) {
/*  60 */     this.r = r;
/*  61 */     this.i = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble(Cdouble x) {
/*  69 */     this(x.r, x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble plus(Cdouble x) {
/*  78 */     return (new Cdouble(this)).plusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble minus(Cdouble x) {
/*  87 */     return (new Cdouble(this)).minusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble times(Cdouble x) {
/*  96 */     return (new Cdouble(this)).timesEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble over(Cdouble x) {
/* 105 */     return (new Cdouble(this)).overEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble plus(double x) {
/* 114 */     return (new Cdouble(this)).plusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble minus(double x) {
/* 123 */     return (new Cdouble(this)).minusEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble times(double x) {
/* 132 */     return (new Cdouble(this)).timesEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble over(double x) {
/* 141 */     return (new Cdouble(this)).overEquals(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble plusEquals(Cdouble x) {
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
/*     */   public Cdouble minusEquals(Cdouble x) {
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
/*     */   public Cdouble timesEquals(Cdouble x) {
/* 172 */     double tr = this.r;
/* 173 */     double ti = this.i;
/* 174 */     double xr = x.r;
/* 175 */     double xi = x.i;
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
/*     */   public Cdouble overEquals(Cdouble x) {
/* 187 */     double tr = this.r;
/* 188 */     double ti = this.i;
/* 189 */     double xr = x.r;
/* 190 */     double xi = x.i;
/* 191 */     double d = norm(x);
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
/*     */   public Cdouble plusEquals(double x) {
/* 203 */     this.r += x;
/* 204 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble minusEquals(double x) {
/* 213 */     this.r -= x;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble timesEquals(double x) {
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
/*     */   public Cdouble overEquals(double x) {
/* 234 */     this.r /= x;
/* 235 */     this.i /= x;
/* 236 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble conjEquals() {
/* 244 */     this.i = -this.i;
/* 245 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble invEquals() {
/* 253 */     double d = norm();
/* 254 */     this.r /= d;
/* 255 */     this.i = -this.i / d;
/* 256 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble negEquals() {
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
/* 274 */     return (this.i == 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImag() {
/* 282 */     return (this.r == 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble conj() {
/* 290 */     return new Cdouble(this.r, -this.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble inv() {
/* 298 */     double d = norm();
/* 299 */     return new Cdouble(this.r / d, -this.i / d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble neg() {
/* 307 */     return new Cdouble(-this.r, -this.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double abs() {
/* 315 */     return abs(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double arg() {
/* 323 */     return arg(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double norm() {
/* 332 */     return norm(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble sqrt() {
/* 340 */     return sqrt(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble exp() {
/* 348 */     return exp(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble log() {
/* 356 */     return log(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble log10() {
/* 364 */     return log10(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble pow(double y) {
/* 373 */     return pow(this, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble pow(Cdouble y) {
/* 382 */     return pow(this, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble sin() {
/* 390 */     return sin(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble cos() {
/* 398 */     return cos(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble tan() {
/* 406 */     return tan(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble sinh() {
/* 414 */     return sinh(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble cosh() {
/* 422 */     return cosh(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cdouble tanh() {
/* 430 */     return tanh(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isReal(Cdouble x) {
/* 439 */     return (x.i == 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isImag(Cdouble x) {
/* 448 */     return (x.r == 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble conj(Cdouble x) {
/* 457 */     return new Cdouble(x.r, -x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble inv(Cdouble x) {
/* 466 */     double d = x.norm();
/* 467 */     return new Cdouble(x.r / d, -x.i / d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble neg(Cdouble x) {
/* 476 */     return new Cdouble(-x.r, -x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble polar(double r, double a) {
/* 486 */     return new Cdouble(r * cos(a), r * sin(a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble add(Cdouble x, Cdouble y) {
/* 496 */     return x.plus(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble sub(Cdouble x, Cdouble y) {
/* 506 */     return x.minus(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble mul(Cdouble x, Cdouble y) {
/* 516 */     return x.times(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble div(Cdouble x, Cdouble y) {
/* 526 */     return x.over(y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double abs(Cdouble x) {
/* 535 */     double ar = abs(x.r);
/* 536 */     double ai = abs(x.i);
/* 537 */     double s = max(abs(ar), abs(ai));
/* 538 */     if (s == 0.0D)
/* 539 */       return 0.0D; 
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
/*     */   public static double arg(Cdouble x) {
/* 551 */     return atan2(x.i, x.r);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double norm(Cdouble x) {
/* 561 */     return x.r * x.r + x.i * x.i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble sqrt(Cdouble x) {
/* 570 */     if (x.r == 0.0D) {
/* 571 */       double d = sqrt(0.5D * abs(x.i));
/* 572 */       return new Cdouble(d, (x.i < 0.0D) ? -d : d);
/*     */     } 
/* 574 */     double t = sqrt(2.0D * (abs(x) + abs(x.r)));
/* 575 */     double u = 0.5D * t;
/* 576 */     return (x.r > 0.0D) ? new Cdouble(u, x.i / t) : new Cdouble(
/*     */         
/* 578 */         abs(x.i) / t, (x.i < 0.0D) ? -u : u);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble exp(Cdouble x) {
/* 588 */     return polar(exp(x.r), x.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble log(Cdouble x) {
/* 597 */     return new Cdouble(log(abs(x)), arg(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble log10(Cdouble x) {
/* 606 */     return log(x).overEquals(log(10.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble pow(Cdouble x, double y) {
/* 616 */     if (x.i == 0.0D)
/* 617 */       return new Cdouble(pow(x.r, y)); 
/* 618 */     Cdouble t = log(x);
/* 619 */     return polar(exp(y * t.r), y * t.i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble pow(double x, Cdouble y) {
/* 629 */     if (x == 0.0D)
/* 630 */       return new Cdouble(); 
/* 631 */     return polar(pow(x, y.r), y.i * log(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble pow(Cdouble x, Cdouble y) {
/* 641 */     if (x.r == 0.0D && x.i == 0.0D)
/* 642 */       return new Cdouble(); 
/* 643 */     return exp(y.times(log(x)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble sin(Cdouble x) {
/* 652 */     return new Cdouble(sin(x.r) * cosh(x.i), cos(x.r) * sinh(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble cos(Cdouble x) {
/* 661 */     return new Cdouble(cos(x.r) * cosh(x.i), -sin(x.r) * sinh(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble tan(Cdouble x) {
/* 670 */     return sin(x).overEquals(cos(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble sinh(Cdouble x) {
/* 679 */     return new Cdouble(sinh(x.r) * cos(x.i), cosh(x.r) * sin(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble cosh(Cdouble x) {
/* 688 */     return new Cdouble(cosh(x.r) * cos(x.i), sinh(x.r) * sin(x.i));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cdouble tanh(Cdouble x) {
/* 697 */     return sinh(x).overEquals(cosh(x));
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/* 701 */     if (this == obj)
/* 702 */       return true; 
/* 703 */     if (obj == null || getClass() != obj.getClass())
/* 704 */       return false; 
/* 705 */     Cdouble that = (Cdouble)obj;
/* 706 */     return (this.r == that.r && this.i == that.i);
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 710 */     long rbits = Double.doubleToLongBits(this.r);
/* 711 */     long ibits = Double.doubleToLongBits(this.i);
/* 712 */     return (int)(rbits ^ rbits >>> 32L ^ ibits ^ ibits >>> 32L);
/*     */   }
/*     */   
/*     */   public String toString() {
/* 716 */     if (this.i == 0.0D)
/* 717 */       return "(" + this.r + "+0.0i)"; 
/* 718 */     if (this.i > 0.0D) {
/* 719 */       return "(" + this.r + "+" + this.i + "i)";
/*     */     }
/* 721 */     return "(" + this.r + "-" + -this.i + "i)";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double max(double x, double y) {
/* 729 */     return (x >= y) ? x : y;
/*     */   }
/*     */   
/*     */   private static double abs(double x) {
/* 733 */     return (x >= 0.0D) ? x : -x;
/*     */   }
/*     */   
/*     */   private static double sqrt(double x) {
/* 737 */     return Math.sqrt(x);
/*     */   }
/*     */   
/*     */   private static double sin(double x) {
/* 741 */     return Math.sin(x);
/*     */   }
/*     */   
/*     */   private static double cos(double x) {
/* 745 */     return Math.cos(x);
/*     */   }
/*     */   
/*     */   private static double sinh(double x) {
/* 749 */     return Math.sinh(x);
/*     */   }
/*     */   
/*     */   private static double cosh(double x) {
/* 753 */     return Math.cosh(x);
/*     */   }
/*     */   
/*     */   private static double exp(double x) {
/* 757 */     return Math.exp(x);
/*     */   }
/*     */   
/*     */   private static double log(double x) {
/* 761 */     return Math.log(x);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static double pow(double x, double y) {
/* 771 */     return Math.pow(x, y);
/*     */   }
/*     */   
/*     */   private static double atan2(double y, double x) {
/* 775 */     return Math.atan2(y, x);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Cdouble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */