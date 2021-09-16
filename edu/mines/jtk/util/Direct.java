/*     */ package edu.mines.jtk.util;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.LongBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Direct
/*     */ {
/*     */   public static ByteBuffer newByteBuffer(int capacity) {
/*     */     ByteBuffer b;
/*     */     try {
/*  58 */       b = ByteBuffer.allocateDirect(capacity);
/*  59 */     } catch (OutOfMemoryError e1) {
/*  60 */       System.gc();
/*     */       try {
/*  62 */         b = ByteBuffer.allocateDirect(capacity);
/*  63 */       } catch (OutOfMemoryError e2) {
/*  64 */         throw new OutOfMemoryError("cannot allocate direct buffer");
/*     */       } 
/*     */     } 
/*  67 */     b.order(ByteOrder.nativeOrder());
/*  68 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ByteBuffer newByteBuffer(byte[] a) {
/*  79 */     ByteBuffer b = newByteBuffer(a.length);
/*  80 */     b.put(a);
/*  81 */     b.flip();
/*  82 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleBuffer newDoubleBuffer(int capacity) {
/*  91 */     return newByteBuffer(8 * capacity).asDoubleBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DoubleBuffer newDoubleBuffer(double[] a) {
/* 102 */     DoubleBuffer b = newDoubleBuffer(a.length);
/* 103 */     b.put(a);
/* 104 */     b.flip();
/* 105 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatBuffer newFloatBuffer(int capacity) {
/* 114 */     return newByteBuffer(4 * capacity).asFloatBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FloatBuffer newFloatBuffer(float[] a) {
/* 125 */     FloatBuffer b = newFloatBuffer(a.length);
/* 126 */     b.put(a);
/* 127 */     b.flip();
/* 128 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntBuffer newIntBuffer(int capacity) {
/* 137 */     return newByteBuffer(4 * capacity).asIntBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IntBuffer newIntBuffer(int[] a) {
/* 148 */     IntBuffer b = newIntBuffer(a.length);
/* 149 */     b.put(a);
/* 150 */     b.flip();
/* 151 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongBuffer newLongBuffer(int capacity) {
/* 160 */     return newByteBuffer(8 * capacity).asLongBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static LongBuffer newLongBuffer(long[] a) {
/* 171 */     LongBuffer b = newLongBuffer(a.length);
/* 172 */     b.put(a);
/* 173 */     b.flip();
/* 174 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortBuffer newShortBuffer(int capacity) {
/* 183 */     return newByteBuffer(2 * capacity).asShortBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShortBuffer newShortBuffer(short[] a) {
/* 194 */     ShortBuffer b = newShortBuffer(a.length);
/* 195 */     b.put(a);
/* 196 */     b.flip();
/* 197 */     return b;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/Direct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */