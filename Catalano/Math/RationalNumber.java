/*     */ package Catalano.Math;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RationalNumber
/*     */ {
/*     */   private BigInteger num;
/*     */   private BigInteger den;
/*     */   boolean alwaysFactorize = true;
/*     */   
/*     */   public BigInteger getNumerator() {
/*  42 */     return this.num;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNumerator(BigInteger numerator) {
/*  50 */     this.num = numerator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BigInteger getDenominator() {
/*  58 */     return this.den;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDenominator(BigInteger denominator) {
/*  66 */     this.den = denominator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlwaysFactorize() {
/*  74 */     return this.alwaysFactorize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlwaysFactorize(boolean alwaysFactorize) {
/*  82 */     this.alwaysFactorize = alwaysFactorize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RationalNumber(int number) {
/*  90 */     this.num = new BigInteger(String.valueOf(number));
/*  91 */     this.den = new BigInteger("1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RationalNumber(int numerator, int denominator) {
/* 100 */     this(new BigInteger(String.valueOf(numerator)), new BigInteger(String.valueOf(denominator)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RationalNumber(BigInteger number) {
/* 108 */     this.num = number;
/* 109 */     this.den = new BigInteger("1");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RationalNumber(BigInteger numerator, BigInteger denominator) {
/* 118 */     this.num = numerator;
/* 119 */     this.den = denominator;
/*     */     
/* 121 */     if (denominator.compareTo(new BigInteger("0")) == 0) {
/* 122 */       throw new IllegalArgumentException("The denominator must be different from zero.");
/*     */     }
/* 124 */     if (denominator.compareTo(new BigInteger("0")) < 0) {
/* 125 */       this.num = this.num.multiply(new BigInteger("-1"));
/* 126 */       this.den = this.den.multiply(new BigInteger("-1"));
/*     */     } 
/*     */     
/* 129 */     if (this.alwaysFactorize) {
/* 130 */       Factorize();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RationalNumber Abs(RationalNumber rn) {
/* 140 */     return new RationalNumber(rn.getNumerator().abs(), rn.getDenominator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RationalNumber Add(RationalNumber rn1, RationalNumber rn2) {
/* 151 */     RationalNumber temp = new RationalNumber(rn1.getNumerator(), rn1.getDenominator());
/* 152 */     temp.Add(rn2);
/* 153 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Abs() {
/* 160 */     this.num = this.num.abs();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(int number) {
/* 168 */     Add(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(int numerator, int denominator) {
/* 177 */     Add(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(BigInteger number) {
/* 185 */     Add(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(BigInteger numerator, BigInteger denominator) {
/* 194 */     Add(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Add(RationalNumber rn) {
/* 203 */     BigInteger denFinal = this.den.multiply(rn.getDenominator());
/* 204 */     BigInteger numerator1 = this.num.multiply(rn.getDenominator());
/* 205 */     BigInteger numerator2 = rn.getNumerator().multiply(this.den);
/*     */     
/* 207 */     this.num = numerator1.add(numerator2);
/* 208 */     this.den = denFinal;
/*     */     
/* 210 */     if (this.alwaysFactorize) {
/* 211 */       Factorize();
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
/*     */   public static RationalNumber Divide(RationalNumber rn1, RationalNumber rn2) {
/* 223 */     RationalNumber temp = new RationalNumber(rn1.getNumerator(), rn1.getDenominator());
/* 224 */     temp.Divide(rn2);
/* 225 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(int number) {
/* 233 */     Divide(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(int numerator, int denominator) {
/* 242 */     Divide(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(BigInteger number) {
/* 250 */     Divide(new RationalNumber(number, new BigInteger("1")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(BigInteger numerator, BigInteger denominator) {
/* 259 */     Divide(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Divide(RationalNumber rn) {
/* 267 */     Multiply(rn.den, rn.num);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RationalNumber Multiply(RationalNumber rn1, RationalNumber rn2) {
/* 278 */     RationalNumber temp = new RationalNumber(rn1.getNumerator(), rn1.getDenominator());
/* 279 */     temp.Multiply(rn2);
/* 280 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(int number) {
/* 288 */     Multiply(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(int numerator, int denominator) {
/* 297 */     Multiply(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(BigInteger number) {
/* 305 */     Multiply(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(BigInteger numerator, BigInteger denominator) {
/* 314 */     Multiply(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Multiply(RationalNumber rn) {
/* 322 */     this.num = this.num.multiply(rn.num);
/* 323 */     this.den = this.den.multiply(rn.den);
/*     */     
/* 325 */     if (this.den.compareTo(new BigInteger("0")) < 0) {
/* 326 */       this.num = this.num.multiply(new BigInteger("-1"));
/* 327 */       this.den = this.den.multiply(new BigInteger("-1"));
/*     */     } 
/*     */     
/* 330 */     if (this.alwaysFactorize) {
/* 331 */       Factorize();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RationalNumber Pow(RationalNumber rn, int power) {
/* 342 */     RationalNumber r = new RationalNumber(rn.getNumerator(), rn.getDenominator());
/* 343 */     for (int i = 1; i < power; i++) {
/* 344 */       r = Multiply(r, rn);
/*     */     }
/*     */     
/* 347 */     return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void pow(int power) {
/* 357 */     RationalNumber rn = new RationalNumber(this.num, this.den);
/* 358 */     for (int i = 1; i < power; i++) {
/* 359 */       this.num = this.num.multiply(rn.num);
/* 360 */       this.den = this.den.multiply(rn.den);
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
/*     */   public static RationalNumber Subtract(RationalNumber rn1, RationalNumber rn2) {
/* 373 */     RationalNumber temp = new RationalNumber(rn1.getNumerator(), rn1.getDenominator());
/* 374 */     temp.Subtract(rn2);
/* 375 */     return temp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(int number) {
/* 383 */     Subtract(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(int numerator, int denominator) {
/* 392 */     Subtract(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(BigInteger number) {
/* 400 */     Subtract(new RationalNumber(number));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(BigInteger numerator, BigInteger denominator) {
/* 409 */     Subtract(new RationalNumber(numerator, denominator));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Subtract(RationalNumber rn) {
/* 418 */     BigInteger denFinal = this.den.multiply(rn.getDenominator());
/* 419 */     BigInteger numerator1 = this.num.multiply(rn.getDenominator());
/* 420 */     BigInteger numerator2 = rn.getNumerator().multiply(this.den);
/*     */     
/* 422 */     this.num = numerator1.subtract(numerator2);
/* 423 */     this.den = denFinal;
/*     */     
/* 425 */     if (this.alwaysFactorize) {
/* 426 */       Factorize();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void Factorize() {
/* 433 */     BigInteger gcd = this.num.gcd(this.den);
/* 434 */     this.num = this.num.divide(gcd);
/* 435 */     this.den = this.den.divide(gcd);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void Swap() {
/* 442 */     BigInteger x = this.num;
/* 443 */     this.num = this.den;
/* 444 */     this.den = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/* 452 */     return this.num.doubleValue() / this.den.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 462 */     return String.valueOf(this.num.toString()) + " / " + this.den.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/RationalNumber.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */