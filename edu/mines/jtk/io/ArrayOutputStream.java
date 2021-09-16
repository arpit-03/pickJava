/*     */ package edu.mines.jtk.io;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class ArrayOutputStream
/*     */   extends FilterOutputStream
/*     */   implements ArrayOutput
/*     */ {
/*     */   private DataOutputStream _dos;
/*     */   private ArrayOutput _ao;
/*     */   private ByteOrder _bo;
/*     */   
/*     */   public ArrayOutputStream(OutputStream os) {
/*  36 */     this(os, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputStream(FileOutputStream fos) {
/*  45 */     this(fos, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputStream(String name) throws FileNotFoundException {
/*  54 */     this(new FileOutputStream(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputStream(File file) throws FileNotFoundException {
/*  63 */     this(new FileOutputStream(file));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputStream(OutputStream os, ByteOrder bo) {
/*  72 */     super(os);
/*  73 */     this._dos = new DataOutputStream(new BufferedOutputStream(os));
/*  74 */     this._ao = new ArrayOutputAdapter(this._dos, bo);
/*  75 */     this._bo = bo;
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
/*     */   public ArrayOutputStream(String name, ByteOrder bo) throws FileNotFoundException {
/*  88 */     this(new FileOutputStream(name), bo);
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
/*     */   public ArrayOutputStream(File file, ByteOrder bo) throws FileNotFoundException {
/* 100 */     this(new FileOutputStream(file), bo);
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 104 */     this._dos.flush();
/* 105 */     super.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 112 */     flush();
/* 113 */     super.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder getByteOrder() {
/* 121 */     return this._bo;
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/* 125 */     this._ao.write(b);
/*     */   }
/*     */   public void write(byte[] b) throws IOException {
/* 128 */     this._ao.write(b);
/*     */   }
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 131 */     this._ao.write(b, off, len);
/*     */   }
/*     */   public void writeBoolean(boolean v) throws IOException {
/* 134 */     this._ao.writeBoolean(v);
/*     */   }
/*     */   public void writeByte(int v) throws IOException {
/* 137 */     this._ao.writeByte(v);
/*     */   }
/*     */   public void writeShort(int v) throws IOException {
/* 140 */     this._ao.writeShort(v);
/*     */   }
/*     */   public void writeChar(int v) throws IOException {
/* 143 */     this._ao.writeChar(v);
/*     */   }
/*     */   public void writeInt(int v) throws IOException {
/* 146 */     this._ao.writeInt(v);
/*     */   }
/*     */   public void writeLong(long v) throws IOException {
/* 149 */     this._ao.writeLong(v);
/*     */   }
/*     */   public void writeFloat(float v) throws IOException {
/* 152 */     this._ao.writeFloat(v);
/*     */   }
/*     */   public void writeDouble(double v) throws IOException {
/* 155 */     this._ao.writeDouble(v);
/*     */   }
/*     */   public void writeBytes(String s) throws IOException {
/* 158 */     this._ao.writeBytes(s);
/*     */   }
/*     */   public void writeChars(String s) throws IOException {
/* 161 */     this._ao.writeChars(s);
/*     */   }
/*     */   public void writeUTF(String s) throws IOException {
/* 164 */     this._ao.writeUTF(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] v, int k, int n) throws IOException {
/* 174 */     this._ao.writeBytes(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] v) throws IOException {
/* 183 */     this._ao.writeBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[][] v) throws IOException {
/* 191 */     this._ao.writeBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[][][] v) throws IOException {
/* 199 */     this._ao.writeBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[] v, int k, int n) throws IOException {
/* 209 */     this._ao.writeChars(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[] v) throws IOException {
/* 218 */     this._ao.writeChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[][] v) throws IOException {
/* 226 */     this._ao.writeChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[][][] v) throws IOException {
/* 234 */     this._ao.writeChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] v, int k, int n) throws IOException {
/* 244 */     this._ao.writeShorts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] v) throws IOException {
/* 253 */     this._ao.writeShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[][] v) throws IOException {
/* 261 */     this._ao.writeShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[][][] v) throws IOException {
/* 269 */     this._ao.writeShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[] v, int k, int n) throws IOException {
/* 279 */     this._ao.writeInts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[] v) throws IOException {
/* 288 */     this._ao.writeInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[][] v) throws IOException {
/* 296 */     this._ao.writeInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[][][] v) throws IOException {
/* 304 */     this._ao.writeInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] v, int k, int n) throws IOException {
/* 314 */     this._ao.writeLongs(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] v) throws IOException {
/* 323 */     this._ao.writeLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[][] v) throws IOException {
/* 331 */     this._ao.writeLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[][][] v) throws IOException {
/* 339 */     this._ao.writeLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] v, int k, int n) throws IOException {
/* 349 */     this._ao.writeFloats(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] v) throws IOException {
/* 358 */     this._ao.writeFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[][] v) throws IOException {
/* 366 */     this._ao.writeFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[][][] v) throws IOException {
/* 374 */     this._ao.writeFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] v, int k, int n) throws IOException {
/* 384 */     this._ao.writeDoubles(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] v) throws IOException {
/* 393 */     this._ao.writeDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[][] v) throws IOException {
/* 401 */     this._ao.writeDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[][][] v) throws IOException {
/* 409 */     this._ao.writeDoubles(v);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */