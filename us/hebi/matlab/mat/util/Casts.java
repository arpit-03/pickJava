/*     */ package us.hebi.matlab.mat.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Casts
/*     */ {
/*     */   public static final short MASK_UINT8 = 255;
/*     */   public static final int MASK_UINT16 = 65535;
/*     */   public static final long MASK_UINT32 = 4294967295L;
/*     */   
/*     */   public static boolean logical(double value) {
/*  49 */     return (value != 0.0D);
/*     */   }
/*     */   
/*     */   public static byte int8(boolean value) {
/*  53 */     return (byte)(value ? 1 : 0);
/*     */   }
/*     */   
/*     */   public static byte int8(long value) {
/*  57 */     return (byte)(int)value;
/*     */   }
/*     */   
/*     */   public static short int16(long value) {
/*  61 */     return (short)(int)value;
/*     */   }
/*     */   
/*     */   public static int int32(long value) {
/*  65 */     return (int)value;
/*     */   }
/*     */   
/*     */   public static byte sint8(long value) {
/*  69 */     if (value > 127L)
/*  70 */       throw new IllegalArgumentException("Value is above signed int8 range"); 
/*  71 */     if (value < -128L)
/*  72 */       throw new IllegalArgumentException("Value is below signed int8 range"); 
/*  73 */     return (byte)(int)value;
/*     */   }
/*     */   
/*     */   public static short sint16(long value) {
/*  77 */     if (value > 32767L)
/*  78 */       throw new IllegalArgumentException("Value is above signed int16 range"); 
/*  79 */     if (value < -32768L)
/*  80 */       throw new IllegalArgumentException("Value is below signed int16 range"); 
/*  81 */     return (short)(int)value;
/*     */   }
/*     */   
/*     */   public static int sint32(long value) {
/*  85 */     if (value > 2147483647L)
/*  86 */       throw new IllegalArgumentException("Value is above signed int32 range"); 
/*  87 */     if (value < -2147483648L)
/*  88 */       throw new IllegalArgumentException("Value is below signed int32 range"); 
/*  89 */     return (int)value;
/*     */   }
/*     */   
/*     */   public static float single(double value) {
/*  93 */     return (float)value;
/*     */   }
/*     */   
/*     */   public static short uint8(byte value) {
/*  97 */     return (short)(value & 0xFF);
/*     */   }
/*     */   
/*     */   public static int uint16(short value) {
/* 101 */     return value & 0xFFFF;
/*     */   }
/*     */   
/*     */   public static long uint32(int value) {
/* 105 */     return value & 0xFFFFFFFFL;
/*     */   }
/*     */   
/*     */   public static boolean isInteger(double value) {
/* 109 */     return (Math.rint(value) == value && !Double.isNaN(value) && !Double.isInfinite(value));
/*     */   }
/*     */   
/*     */   public static boolean fitsByte(long value) {
/* 113 */     return !(value != (byte)(int)value && value >>> 8L != 0L);
/*     */   }
/*     */   
/*     */   public static boolean fitsShort(long value) {
/* 117 */     return !(value != (short)(int)value && value >>> 16L != 0L);
/*     */   }
/*     */   
/*     */   public static boolean fitsInt(long value) {
/* 121 */     return !(value != (int)value && value >>> 32L != 0L);
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
/*     */   public static int checkedDivide(int numerator, int denominator) {
/* 133 */     int remainder = numerator % denominator;
/* 134 */     if (remainder != 0) {
/* 135 */       String format = String.format("%d is not a multiple of %d", new Object[] { Integer.valueOf(numerator), Integer.valueOf(denominator) });
/* 136 */       throw new IllegalArgumentException(format);
/*     */     } 
/* 138 */     return numerator / denominator;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/Casts.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */