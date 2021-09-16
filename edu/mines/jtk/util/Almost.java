/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Almost
/*     */   implements Serializable, Comparator<Number>
/*     */ {
/*     */   static final long serialVersionUID = 1L;
/*  38 */   private double _epsilon = 1.1920928955078125E-6D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  44 */   private double _minValue = 1.401298464324817E-43D;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   public static final Almost FLOAT = new Almost();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   public static final Almost DOUBLE = new Almost(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Almost(double epsilon, double minValue) {
/*  76 */     this._epsilon = Math.abs(epsilon);
/*  77 */     this._minValue = Math.abs(minValue);
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
/*     */   public Almost(double epsilon) {
/*  93 */     if (epsilon > 0.1D) {
/*  94 */       throw new IllegalArgumentException("Epsilon should be a small number: " + epsilon);
/*     */     }
/*     */     
/*  97 */     this._epsilon = Math.abs(epsilon);
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
/*     */   public Almost(int significantDigits) {
/* 109 */     if (significantDigits < 1) {
/* 110 */       throw new IllegalArgumentException("The number of significant digits should be positive.");
/*     */     }
/*     */     
/* 113 */     significantDigits = Math.abs(significantDigits);
/* 114 */     this._epsilon = 1.0D;
/* 115 */     while (significantDigits > 0) {
/* 116 */       this._epsilon *= 0.1D;
/* 117 */       significantDigits--;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Almost(boolean isDouble) {
/* 128 */     if (isDouble) {
/* 129 */       this._epsilon = 2.220446049250313E-15D;
/* 130 */       this._minValue = 4.94E-322D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getEpsilon() {
/* 141 */     return this._epsilon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinValue() {
/* 151 */     return this._minValue;
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
/*     */   public boolean between(double x, double x1, double x2) {
/* 163 */     return (cmp(x, x1) * cmp(x, x2) <= 0);
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
/*     */   public int outside(double x, double x1, double x2) {
/* 176 */     int i = 0;
/* 177 */     if (between(x, x1, x2)) {
/* 178 */       i = 0;
/* 179 */     } else if (between(x1, x, x2)) {
/* 180 */       i = -1;
/* 181 */     } else if (between(x2, x, x1)) {
/* 182 */       i = 1;
/*     */     } 
/* 184 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean zero(double r) {
/* 194 */     if (r < 0.0D) {
/* 195 */       r = -r;
/*     */     }
/* 197 */     return (r < this._minValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equal(double r1, double r2) {
/* 208 */     return (cmp(r1, r2) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean lt(double r1, double r2) {
/* 219 */     return (cmp(r1, r2) < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean le(double r1, double r2) {
/* 230 */     return (cmp(r1, r2) <= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean gt(double r1, double r2) {
/* 241 */     return (cmp(r1, r2) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean ge(double r1, double r2) {
/* 252 */     return (cmp(r1, r2) >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int cmp(double r1, double r2) {
/* 263 */     if (r1 == r2) {
/* 264 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 268 */     double ar1 = r1;
/* 269 */     double ar2 = r2;
/* 270 */     if (ar1 < 0.0D) {
/* 271 */       ar1 = -ar1;
/*     */     }
/* 273 */     if (ar2 < 0.0D) {
/* 274 */       ar2 = -ar2;
/*     */     }
/* 276 */     if (ar1 < this._minValue && ar2 < this._minValue) {
/* 277 */       return 0;
/*     */     }
/*     */     
/* 280 */     if (0.0D * r1 * r2 != 0.0D) {
/* 281 */       throw new IllegalArgumentException("Comparing a NaN");
/*     */     }
/*     */     
/* 284 */     double er1 = this._epsilon * ar1;
/* 285 */     if (this._minValue > er1) {
/* 286 */       er1 = this._minValue;
/*     */     }
/* 288 */     double er2 = this._epsilon * ar2;
/* 289 */     if (this._minValue > er2) {
/* 290 */       er2 = this._minValue;
/*     */     }
/* 292 */     if (r1 - er1 > r2 + er2)
/* 293 */       return 1; 
/* 294 */     if (r1 + er1 < r2 - er2) {
/* 295 */       return -1;
/*     */     }
/* 297 */     return 0;
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
/*     */   public int hashCodeOf(Number number, int significantDigits) {
/* 311 */     if (number instanceof Long || number instanceof Integer) {
/* 312 */       return number.intValue();
/*     */     }
/* 314 */     double value = number.doubleValue();
/*     */     
/* 316 */     if (zero(value)) {
/* 317 */       return 0;
/*     */     }
/*     */     
/* 320 */     boolean flipSign = (value < 0.0D);
/* 321 */     if (flipSign) {
/* 322 */       value = -value;
/*     */     }
/* 324 */     int pow = 0;
/*     */     
/* 326 */     while (value < 0.1D) {
/* 327 */       value *= 10.0D;
/* 328 */       pow++;
/*     */     } 
/* 330 */     while (value > 1.0D) {
/* 331 */       value *= 0.1D;
/* 332 */       pow--;
/*     */     } 
/*     */     
/* 335 */     if (equal(value, 1.0D) || equal(value, 0.1D)) {
/* 336 */       return 1;
/*     */     }
/*     */     
/* 339 */     for (int i = 0; i < Math.abs(significantDigits); i++) {
/* 340 */       value *= 10.0D;
/*     */     }
/* 342 */     int result = Long.valueOf((long)(value + 0.5D)).intValue();
/*     */     
/* 344 */     result = 100 * result + pow;
/* 345 */     if (flipSign) {
/* 346 */       result = -result;
/*     */     }
/* 348 */     return result;
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
/*     */   public int hashCodeOf(Number number) {
/* 360 */     double epsilon = this._epsilon;
/* 361 */     int digits = 0;
/* 362 */     while (epsilon < 0.99D) {
/* 363 */       epsilon *= 10.0D;
/* 364 */       digits++;
/*     */     } 
/* 366 */     return hashCodeOf(number, digits);
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
/*     */   public double divide(double top, double bottom, boolean limitIsOne) {
/* 380 */     return divide(top, bottom, limitIsOne ? 1.0D : 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double reciprocal(double value) {
/* 390 */     return divide(1.0D, value, 0.0D);
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
/*     */   public double divide(double top, double bottom, double limit) {
/* 405 */     double sign = 1.0D;
/* 406 */     if ((top > 0.0D && bottom < 0.0D) || (top < 0.0D && bottom > 0.0D)) {
/* 407 */       sign = -1.0D;
/*     */     }
/*     */     
/* 410 */     if (top < 0.0D) {
/* 411 */       top = -top;
/*     */     }
/* 413 */     if (bottom < 0.0D) {
/* 414 */       bottom = -bottom;
/*     */     }
/*     */     
/* 417 */     if (bottom >= 1.0D || top < bottom * 0.1D * 3.4028234663852886E38D)
/* 418 */       return sign * top / bottom; 
/* 419 */     if (equal(top, bottom)) {
/* 420 */       if (zero(top)) {
/* 421 */         return limit;
/*     */       }
/* 423 */       return sign;
/*     */     } 
/* 425 */     return sign * 0.01D * 3.4028234663852886E38D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compare(Number n1, Number n2) {
/* 432 */     return cmp(n1.doubleValue(), n2.doubleValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object object) {
/* 438 */     if (this == object)
/* 439 */       return true; 
/* 440 */     if (object == null || getClass() != object.getClass() || hashCode() != object.hashCode()) {
/* 441 */       return false;
/*     */     }
/* 443 */     Almost other = (Almost)object;
/* 444 */     return (this._epsilon == other._epsilon && this._minValue == other._minValue);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 450 */     return Long.valueOf(Double.doubleToLongBits(this._epsilon) ^ 
/* 451 */         Double.doubleToLongBits(this._minValue)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 457 */     return "Almost<eps=" + this._epsilon + ",min=" + this._minValue + ">";
/*     */   }
/*     */   
/*     */   public Almost() {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Almost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */