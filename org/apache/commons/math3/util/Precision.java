/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Precision
/*     */ {
/*     */   public static final double EPSILON;
/*     */   public static final double SAFE_MIN;
/*     */   private static final long EXPONENT_OFFSET = 1023L;
/*     */   private static final long SGN_MASK = -9223372036854775808L;
/*     */   private static final int SGN_MASK_FLOAT = -2147483648;
/*     */   private static final double POSITIVE_ZERO = 0.0D;
/*  65 */   private static final long POSITIVE_ZERO_DOUBLE_BITS = Double.doubleToRawLongBits(0.0D);
/*     */   
/*  67 */   private static final long NEGATIVE_ZERO_DOUBLE_BITS = Double.doubleToRawLongBits(-0.0D);
/*     */   
/*  69 */   private static final int POSITIVE_ZERO_FLOAT_BITS = Float.floatToRawIntBits(0.0F);
/*     */   
/*  71 */   private static final int NEGATIVE_ZERO_FLOAT_BITS = Float.floatToRawIntBits(-0.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  79 */     EPSILON = Double.longBitsToDouble(4368491638549381120L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     SAFE_MIN = Double.longBitsToDouble(4503599627370496L);
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
/*     */   public static int compareTo(double x, double y, double eps) {
/* 106 */     if (equals(x, y, eps))
/* 107 */       return 0; 
/* 108 */     if (x < y) {
/* 109 */       return -1;
/*     */     }
/* 111 */     return 1;
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
/*     */   public static int compareTo(double x, double y, int maxUlps) {
/* 133 */     if (equals(x, y, maxUlps))
/* 134 */       return 0; 
/* 135 */     if (x < y) {
/* 136 */       return -1;
/*     */     }
/* 138 */     return 1;
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
/*     */   public static boolean equals(float x, float y) {
/* 150 */     return equals(x, y, 1);
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
/*     */   public static boolean equalsIncludingNaN(float x, float y) {
/* 163 */     return (x != x || y != y) ? (((((x != x) ? 1 : 0) ^ ((y != y) ? 1 : 0)) == 0)) : equals(x, y, 1);
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
/*     */   public static boolean equals(float x, float y, float eps) {
/* 178 */     return (equals(x, y, 1) || FastMath.abs(y - x) <= eps);
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
/*     */   public static boolean equalsIncludingNaN(float x, float y, float eps) {
/* 193 */     return (equalsIncludingNaN(x, y) || FastMath.abs(y - x) <= eps);
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
/*     */   public static boolean equals(float x, float y, int maxUlps) {
/*     */     boolean isEqual;
/* 216 */     int xInt = Float.floatToRawIntBits(x);
/* 217 */     int yInt = Float.floatToRawIntBits(y);
/*     */ 
/*     */     
/* 220 */     if (((xInt ^ yInt) & Integer.MIN_VALUE) == 0) {
/*     */       
/* 222 */       isEqual = (FastMath.abs(xInt - yInt) <= maxUlps);
/*     */     } else {
/*     */       int deltaPlus;
/*     */       
/*     */       int deltaMinus;
/* 227 */       if (xInt < yInt) {
/* 228 */         deltaPlus = yInt - POSITIVE_ZERO_FLOAT_BITS;
/* 229 */         deltaMinus = xInt - NEGATIVE_ZERO_FLOAT_BITS;
/*     */       } else {
/* 231 */         deltaPlus = xInt - POSITIVE_ZERO_FLOAT_BITS;
/* 232 */         deltaMinus = yInt - NEGATIVE_ZERO_FLOAT_BITS;
/*     */       } 
/*     */       
/* 235 */       if (deltaPlus > maxUlps) {
/* 236 */         isEqual = false;
/*     */       } else {
/* 238 */         isEqual = (deltaMinus <= maxUlps - deltaPlus);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 243 */     return (isEqual && !Float.isNaN(x) && !Float.isNaN(y));
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
/*     */   public static boolean equalsIncludingNaN(float x, float y, int maxUlps) {
/* 260 */     return (x != x || y != y) ? (((((x != x) ? 1 : 0) ^ ((y != y) ? 1 : 0)) == 0)) : equals(x, y, maxUlps);
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
/*     */   public static boolean equals(double x, double y) {
/* 272 */     return equals(x, y, 1);
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
/*     */   public static boolean equalsIncludingNaN(double x, double y) {
/* 285 */     return (x != x || y != y) ? (((((x != x) ? 1 : 0) ^ ((y != y) ? 1 : 0)) == 0)) : equals(x, y, 1);
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
/*     */   public static boolean equals(double x, double y, double eps) {
/* 301 */     return (equals(x, y, 1) || FastMath.abs(y - x) <= eps);
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
/*     */   public static boolean equalsWithRelativeTolerance(double x, double y, double eps) {
/* 318 */     if (equals(x, y, 1)) {
/* 319 */       return true;
/*     */     }
/*     */     
/* 322 */     double absoluteMax = FastMath.max(FastMath.abs(x), FastMath.abs(y));
/* 323 */     double relativeDifference = FastMath.abs((x - y) / absoluteMax);
/*     */     
/* 325 */     return (relativeDifference <= eps);
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
/*     */   public static boolean equalsIncludingNaN(double x, double y, double eps) {
/* 340 */     return (equalsIncludingNaN(x, y) || FastMath.abs(y - x) <= eps);
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
/*     */   public static boolean equals(double x, double y, int maxUlps) {
/*     */     boolean isEqual;
/* 366 */     long xInt = Double.doubleToRawLongBits(x);
/* 367 */     long yInt = Double.doubleToRawLongBits(y);
/*     */ 
/*     */     
/* 370 */     if (((xInt ^ yInt) & Long.MIN_VALUE) == 0L) {
/*     */       
/* 372 */       isEqual = (FastMath.abs(xInt - yInt) <= maxUlps);
/*     */     } else {
/*     */       long deltaPlus;
/*     */       
/*     */       long deltaMinus;
/* 377 */       if (xInt < yInt) {
/* 378 */         deltaPlus = yInt - POSITIVE_ZERO_DOUBLE_BITS;
/* 379 */         deltaMinus = xInt - NEGATIVE_ZERO_DOUBLE_BITS;
/*     */       } else {
/* 381 */         deltaPlus = xInt - POSITIVE_ZERO_DOUBLE_BITS;
/* 382 */         deltaMinus = yInt - NEGATIVE_ZERO_DOUBLE_BITS;
/*     */       } 
/*     */       
/* 385 */       if (deltaPlus > maxUlps) {
/* 386 */         isEqual = false;
/*     */       } else {
/* 388 */         isEqual = (deltaMinus <= maxUlps - deltaPlus);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 393 */     return (isEqual && !Double.isNaN(x) && !Double.isNaN(y));
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
/*     */   public static boolean equalsIncludingNaN(double x, double y, int maxUlps) {
/* 410 */     return (x != x || y != y) ? (((((x != x) ? 1 : 0) ^ ((y != y) ? 1 : 0)) == 0)) : equals(x, y, maxUlps);
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
/*     */   public static double round(double x, int scale) {
/* 423 */     return round(x, scale, 4);
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
/*     */   public static double round(double x, int scale, int roundingMethod) {
/*     */     try {
/* 445 */       double rounded = (new BigDecimal(Double.toString(x))).setScale(scale, roundingMethod).doubleValue();
/*     */ 
/*     */ 
/*     */       
/* 449 */       return (rounded == 0.0D) ? (0.0D * x) : rounded;
/* 450 */     } catch (NumberFormatException ex) {
/* 451 */       if (Double.isInfinite(x)) {
/* 452 */         return x;
/*     */       }
/* 454 */       return Double.NaN;
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
/*     */   public static float round(float x, int scale) {
/* 469 */     return round(x, scale, 4);
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
/*     */   public static float round(float x, int scale, int roundingMethod) throws MathArithmeticException, MathIllegalArgumentException {
/* 487 */     float sign = FastMath.copySign(1.0F, x);
/* 488 */     float factor = (float)FastMath.pow(10.0D, scale) * sign;
/* 489 */     return (float)roundUnscaled((x * factor), sign, roundingMethod) / factor;
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
/*     */   private static double roundUnscaled(double unscaled, double sign, int roundingMethod) throws MathArithmeticException, MathIllegalArgumentException {
/*     */     double fraction;
/* 509 */     switch (roundingMethod) {
/*     */       case 2:
/* 511 */         if (sign == -1.0D) {
/* 512 */           unscaled = FastMath.floor(FastMath.nextAfter(unscaled, Double.NEGATIVE_INFINITY));
/*     */         } else {
/* 514 */           unscaled = FastMath.ceil(FastMath.nextAfter(unscaled, Double.POSITIVE_INFINITY));
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 586 */         return unscaled;case 1: unscaled = FastMath.floor(FastMath.nextAfter(unscaled, Double.NEGATIVE_INFINITY)); return unscaled;case 3: if (sign == -1.0D) { unscaled = FastMath.ceil(FastMath.nextAfter(unscaled, Double.POSITIVE_INFINITY)); } else { unscaled = FastMath.floor(FastMath.nextAfter(unscaled, Double.NEGATIVE_INFINITY)); }  return unscaled;case 5: unscaled = FastMath.nextAfter(unscaled, Double.NEGATIVE_INFINITY); fraction = unscaled - FastMath.floor(unscaled); if (fraction > 0.5D) { unscaled = FastMath.ceil(unscaled); } else { unscaled = FastMath.floor(unscaled); }  return unscaled;case 6: fraction = unscaled - FastMath.floor(unscaled); if (fraction > 0.5D) { unscaled = FastMath.ceil(unscaled); } else if (fraction < 0.5D) { unscaled = FastMath.floor(unscaled); } else if (FastMath.floor(unscaled) / 2.0D == FastMath.floor(FastMath.floor(unscaled) / 2.0D)) { unscaled = FastMath.floor(unscaled); } else { unscaled = FastMath.ceil(unscaled); }  return unscaled;case 4: unscaled = FastMath.nextAfter(unscaled, Double.POSITIVE_INFINITY); fraction = unscaled - FastMath.floor(unscaled); if (fraction >= 0.5D) { unscaled = FastMath.ceil(unscaled); } else { unscaled = FastMath.floor(unscaled); }  return unscaled;case 7: if (unscaled != FastMath.floor(unscaled)) throw new MathArithmeticException();  return unscaled;case 0: if (unscaled != FastMath.floor(unscaled)) unscaled = FastMath.ceil(FastMath.nextAfter(unscaled, Double.POSITIVE_INFINITY));  return unscaled;
/*     */     } 
/*     */     throw new MathIllegalArgumentException(LocalizedFormats.INVALID_ROUNDING_METHOD, new Object[] { 
/*     */           Integer.valueOf(roundingMethod), "ROUND_CEILING", Integer.valueOf(2), "ROUND_DOWN", Integer.valueOf(1), "ROUND_FLOOR", Integer.valueOf(3), "ROUND_HALF_DOWN", Integer.valueOf(5), "ROUND_HALF_EVEN", 
/*     */           Integer.valueOf(6), "ROUND_HALF_UP", Integer.valueOf(4), "ROUND_UNNECESSARY", Integer.valueOf(7), "ROUND_UP", Integer.valueOf(0) });
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
/*     */   public static double representableDelta(double x, double originalDelta) {
/* 606 */     return x + originalDelta - x;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/Precision.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */