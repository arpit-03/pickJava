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
/*     */ class HeapByteConverter
/*     */   implements ByteConverter
/*     */ {
/*     */   public short getShort(ByteOrder order, byte[] bytes, int offset) {
/*     */     int value;
/*  35 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/*  36 */       value = bytes[offset + 0] & 0xFF | (
/*  37 */         bytes[offset + 1] & 0xFF) << 8;
/*     */     } else {
/*  39 */       value = (bytes[offset + 0] & 0xFF) << 8 | 
/*  40 */         bytes[offset + 1] & 0xFF;
/*     */     } 
/*  42 */     return (short)value;
/*     */   }
/*     */   
/*     */   public int getInt(ByteOrder order, byte[] bytes, int offset) {
/*     */     int value;
/*  47 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/*  48 */       value = bytes[offset + 0] & 0xFF | (
/*  49 */         bytes[offset + 1] & 0xFF) << 8 | (
/*  50 */         bytes[offset + 2] & 0xFF) << 16 | (
/*  51 */         bytes[offset + 3] & 0xFF) << 24;
/*     */     } else {
/*  53 */       value = (bytes[offset + 0] & 0xFF) << 24 | (
/*  54 */         bytes[offset + 1] & 0xFF) << 16 | (
/*  55 */         bytes[offset + 2] & 0xFF) << 8 | 
/*  56 */         bytes[offset + 3] & 0xFF;
/*     */     } 
/*  58 */     return value;
/*     */   }
/*     */   
/*     */   public long getLong(ByteOrder order, byte[] bytes, int offset) {
/*     */     long value;
/*  63 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/*  64 */       value = bytes[offset + 0] & 0xFFL | (
/*  65 */         bytes[offset + 1] & 0xFFL) << 8L | (
/*  66 */         bytes[offset + 2] & 0xFFL) << 16L | (
/*  67 */         bytes[offset + 3] & 0xFFL) << 24L | (
/*  68 */         bytes[offset + 4] & 0xFFL) << 32L | (
/*  69 */         bytes[offset + 5] & 0xFFL) << 40L | (
/*  70 */         bytes[offset + 6] & 0xFFL) << 48L | (
/*  71 */         bytes[offset + 7] & 0xFFL) << 56L;
/*     */     } else {
/*  73 */       value = (bytes[offset + 0] & 0xFFL) << 56L | (
/*  74 */         bytes[offset + 1] & 0xFFL) << 48L | (
/*  75 */         bytes[offset + 2] & 0xFFL) << 40L | (
/*  76 */         bytes[offset + 3] & 0xFFL) << 32L | (
/*  77 */         bytes[offset + 4] & 0xFFL) << 24L | (
/*  78 */         bytes[offset + 5] & 0xFFL) << 16L | (
/*  79 */         bytes[offset + 6] & 0xFFL) << 8L | 
/*  80 */         bytes[offset + 7] & 0xFFL;
/*     */     } 
/*  82 */     return value;
/*     */   }
/*     */   
/*     */   public float getFloat(ByteOrder order, byte[] bytes, int offset) {
/*  86 */     return Float.intBitsToFloat(getInt(order, bytes, offset));
/*     */   }
/*     */   
/*     */   public double getDouble(ByteOrder order, byte[] bytes, int offset) {
/*  90 */     return Double.longBitsToDouble(getLong(order, bytes, offset));
/*     */   }
/*     */ 
/*     */   
/*     */   public void putShort(short value, ByteOrder order, byte[] bytes, int offset) {
/*  95 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/*  96 */       bytes[offset + 0] = (byte)(0xFF & value);
/*  97 */       bytes[offset + 1] = (byte)(0xFF & value >>> 8);
/*     */     } else {
/*  99 */       bytes[offset + 0] = (byte)(0xFF & value >>> 8);
/* 100 */       bytes[offset + 1] = (byte)(0xFF & value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putInt(int value, ByteOrder order, byte[] bytes, int offset) {
/* 106 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/* 107 */       bytes[offset + 0] = (byte)(0xFF & value);
/* 108 */       bytes[offset + 1] = (byte)(0xFF & value >>> 8);
/* 109 */       bytes[offset + 2] = (byte)(0xFF & value >>> 16);
/* 110 */       bytes[offset + 3] = (byte)(0xFF & value >>> 24);
/*     */     } else {
/* 112 */       bytes[offset + 0] = (byte)(0xFF & value >>> 24);
/* 113 */       bytes[offset + 1] = (byte)(0xFF & value >>> 16);
/* 114 */       bytes[offset + 2] = (byte)(0xFF & value >>> 8);
/* 115 */       bytes[offset + 3] = (byte)(0xFF & value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putLong(long value, ByteOrder order, byte[] bytes, int offset) {
/* 121 */     if (order == ByteOrder.LITTLE_ENDIAN) {
/* 122 */       bytes[offset + 0] = (byte)(int)(0xFFL & value);
/* 123 */       bytes[offset + 1] = (byte)(int)(0xFFL & value >>> 8L);
/* 124 */       bytes[offset + 2] = (byte)(int)(0xFFL & value >>> 16L);
/* 125 */       bytes[offset + 3] = (byte)(int)(0xFFL & value >>> 24L);
/* 126 */       bytes[offset + 4] = (byte)(int)(0xFFL & value >>> 32L);
/* 127 */       bytes[offset + 5] = (byte)(int)(0xFFL & value >>> 40L);
/* 128 */       bytes[offset + 6] = (byte)(int)(0xFFL & value >>> 48L);
/* 129 */       bytes[offset + 7] = (byte)(int)(0xFFL & value >>> 56L);
/*     */     } else {
/* 131 */       bytes[offset + 0] = (byte)(int)(0xFFL & value >>> 56L);
/* 132 */       bytes[offset + 1] = (byte)(int)(0xFFL & value >>> 48L);
/* 133 */       bytes[offset + 2] = (byte)(int)(0xFFL & value >>> 40L);
/* 134 */       bytes[offset + 3] = (byte)(int)(0xFFL & value >>> 32L);
/* 135 */       bytes[offset + 4] = (byte)(int)(0xFFL & value >>> 24L);
/* 136 */       bytes[offset + 5] = (byte)(int)(0xFFL & value >>> 16L);
/* 137 */       bytes[offset + 6] = (byte)(int)(0xFFL & value >>> 8L);
/* 138 */       bytes[offset + 7] = (byte)(int)(0xFFL & value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putFloat(float value, ByteOrder order, byte[] bytes, int offset) {
/* 144 */     putInt(Float.floatToRawIntBits(value), order, bytes, offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putDouble(double value, ByteOrder order, byte[] bytes, int offset) {
/* 149 */     putLong(Double.doubleToRawLongBits(value), order, bytes, offset);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/HeapByteConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */