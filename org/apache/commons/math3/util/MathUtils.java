/*     */ package org.apache.commons.math3.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.math3.RealFieldElement;
/*     */ import org.apache.commons.math3.exception.MathArithmeticException;
/*     */ import org.apache.commons.math3.exception.NotFiniteNumberException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.Localizable;
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
/*     */ public final class MathUtils
/*     */ {
/*     */   public static final double TWO_PI = 6.283185307179586D;
/*     */   public static final double PI_SQUARED = 9.869604401089358D;
/*     */   
/*     */   public static int hash(double value) {
/*  64 */     return (new Double(value)).hashCode();
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
/*  76 */     return (new Double(x)).equals(new Double(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int hash(double[] value) {
/*  87 */     return Arrays.hashCode(value);
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
/*     */   public static double normalizeAngle(double a, double center) {
/* 110 */     return a - 6.283185307179586D * FastMath.floor((a + Math.PI - center) / 6.283185307179586D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends RealFieldElement<T>> T max(T e1, T e2) {
/* 121 */     return (((RealFieldElement)e1.subtract(e2)).getReal() >= 0.0D) ? e1 : e2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends RealFieldElement<T>> T min(T e1, T e2) {
/* 132 */     return (((RealFieldElement)e1.subtract(e2)).getReal() >= 0.0D) ? e2 : e1;
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
/*     */   public static double reduce(double a, double period, double offset) {
/* 154 */     double p = FastMath.abs(period);
/* 155 */     return a - p * FastMath.floor((a - offset) / p) - offset;
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
/*     */   public static byte copySign(byte magnitude, byte sign) throws MathArithmeticException {
/* 170 */     if ((magnitude >= 0 && sign >= 0) || (magnitude < 0 && sign < 0))
/*     */     {
/* 172 */       return magnitude; } 
/* 173 */     if (sign >= 0 && magnitude == Byte.MIN_VALUE)
/*     */     {
/* 175 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
/*     */     }
/* 177 */     return (byte)-magnitude;
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
/*     */   public static short copySign(short magnitude, short sign) throws MathArithmeticException {
/* 193 */     if ((magnitude >= 0 && sign >= 0) || (magnitude < 0 && sign < 0))
/*     */     {
/* 195 */       return magnitude; } 
/* 196 */     if (sign >= 0 && magnitude == Short.MIN_VALUE)
/*     */     {
/* 198 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
/*     */     }
/* 200 */     return (short)-magnitude;
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
/*     */   public static int copySign(int magnitude, int sign) throws MathArithmeticException {
/* 216 */     if ((magnitude >= 0 && sign >= 0) || (magnitude < 0 && sign < 0))
/*     */     {
/* 218 */       return magnitude; } 
/* 219 */     if (sign >= 0 && magnitude == Integer.MIN_VALUE)
/*     */     {
/* 221 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
/*     */     }
/* 223 */     return -magnitude;
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
/*     */   public static long copySign(long magnitude, long sign) throws MathArithmeticException {
/* 239 */     if ((magnitude >= 0L && sign >= 0L) || (magnitude < 0L && sign < 0L))
/*     */     {
/* 241 */       return magnitude; } 
/* 242 */     if (sign >= 0L && magnitude == Long.MIN_VALUE)
/*     */     {
/* 244 */       throw new MathArithmeticException(LocalizedFormats.OVERFLOW, new Object[0]);
/*     */     }
/* 246 */     return -magnitude;
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
/*     */   public static void checkFinite(double x) throws NotFiniteNumberException {
/* 258 */     if (Double.isInfinite(x) || Double.isNaN(x)) {
/* 259 */       throw new NotFiniteNumberException(Double.valueOf(x), new Object[0]);
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
/*     */   public static void checkFinite(double[] val) throws NotFiniteNumberException {
/* 272 */     for (int i = 0; i < val.length; i++) {
/* 273 */       double x = val[i];
/* 274 */       if (Double.isInfinite(x) || Double.isNaN(x)) {
/* 275 */         throw new NotFiniteNumberException(LocalizedFormats.ARRAY_ELEMENT, Double.valueOf(x), new Object[] { Integer.valueOf(i) });
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkNotNull(Object o, Localizable pattern, Object... args) throws NullArgumentException {
/* 292 */     if (o == null) {
/* 293 */       throw new NullArgumentException(pattern, args);
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
/*     */   public static void checkNotNull(Object o) throws NullArgumentException {
/* 305 */     if (o == null)
/* 306 */       throw new NullArgumentException(); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/MathUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */