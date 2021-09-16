/*     */ package us.hebi.matlab.mat.util;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class UnsafeByteConverter
/*     */   implements ByteConverter
/*     */ {
/*     */   public short getShort(ByteOrder order, byte[] bytes, int offset) {
/*  35 */     short value = UnsafeAccess.UNSAFE.getShort(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*  36 */     if (order == UnsafeAccess.NATIVE_ORDER)
/*  37 */       return value; 
/*  38 */     return Short.reverseBytes(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(ByteOrder order, byte[] bytes, int offset) {
/*  43 */     int value = UnsafeAccess.UNSAFE.getInt(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*  44 */     if (order == UnsafeAccess.NATIVE_ORDER)
/*  45 */       return value; 
/*  46 */     return Integer.reverseBytes(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(ByteOrder order, byte[] bytes, int offset) {
/*  51 */     long value = UnsafeAccess.UNSAFE.getLong(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*  52 */     if (order == UnsafeAccess.NATIVE_ORDER)
/*  53 */       return value; 
/*  54 */     return Long.reverseBytes(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(ByteOrder order, byte[] bytes, int offset) {
/*  59 */     if (order == UnsafeAccess.NATIVE_ORDER) {
/*  60 */       return UnsafeAccess.UNSAFE.getFloat(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*     */     }
/*  62 */     int bits = UnsafeAccess.UNSAFE.getInt(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*  63 */     return Float.intBitsToFloat(Integer.reverseBytes(bits));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(ByteOrder order, byte[] bytes, int offset) {
/*  69 */     if (order == UnsafeAccess.NATIVE_ORDER) {
/*  70 */       return UnsafeAccess.UNSAFE.getDouble(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*     */     }
/*  72 */     long bits = UnsafeAccess.UNSAFE.getLong(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset);
/*  73 */     return Double.longBitsToDouble(Long.reverseBytes(bits));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putShort(short value, ByteOrder order, byte[] bytes, int offset) {
/*  79 */     if (order != UnsafeAccess.NATIVE_ORDER)
/*  80 */       value = Short.reverseBytes(value); 
/*  81 */     UnsafeAccess.UNSAFE.putShort(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putInt(int value, ByteOrder order, byte[] bytes, int offset) {
/*  86 */     if (order != UnsafeAccess.NATIVE_ORDER)
/*  87 */       value = Integer.reverseBytes(value); 
/*  88 */     UnsafeAccess.UNSAFE.putInt(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putLong(long value, ByteOrder order, byte[] bytes, int offset) {
/*  93 */     if (order != UnsafeAccess.NATIVE_ORDER)
/*  94 */       value = Long.reverseBytes(value); 
/*  95 */     UnsafeAccess.UNSAFE.putLong(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putFloat(float value, ByteOrder order, byte[] bytes, int offset) {
/* 100 */     if (order == UnsafeAccess.NATIVE_ORDER) {
/* 101 */       UnsafeAccess.UNSAFE.putFloat(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, value);
/*     */     } else {
/* 103 */       int bits = Integer.reverseBytes(Float.floatToRawIntBits(value));
/* 104 */       UnsafeAccess.UNSAFE.putInt(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, bits);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putDouble(double value, ByteOrder order, byte[] bytes, int offset) {
/* 110 */     if (order == UnsafeAccess.NATIVE_ORDER) {
/* 111 */       UnsafeAccess.UNSAFE.putDouble(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, value);
/*     */     } else {
/* 113 */       long bits = Long.reverseBytes(Double.doubleToRawLongBits(value));
/* 114 */       UnsafeAccess.UNSAFE.putLong(bytes, UnsafeAccess.BYTE_ARRAY_OFFSET + offset, bits);
/*     */     } 
/*     */   }
/*     */   
/*     */   UnsafeByteConverter() {
/* 119 */     UnsafeAccess.requireUnsafe();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/UnsafeByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */