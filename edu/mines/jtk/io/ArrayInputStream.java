/*     */ package edu.mines.jtk.io;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class ArrayInputStream
/*     */   extends FilterInputStream
/*     */   implements ArrayInput
/*     */ {
/*     */   private DataInputStream _dis;
/*     */   private ArrayInput _ai;
/*     */   private ByteOrder _bo;
/*     */   
/*     */   public ArrayInputStream(InputStream is) {
/*  36 */     this(is, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputStream(FileInputStream fis) {
/*  45 */     this(fis, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputStream(String name) throws FileNotFoundException {
/*  54 */     this(new FileInputStream(name));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputStream(File file) throws FileNotFoundException {
/*  63 */     this(new FileInputStream(file));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputStream(InputStream is, ByteOrder bo) {
/*  72 */     super(is);
/*  73 */     this._dis = new DataInputStream(new BufferedInputStream(is));
/*  74 */     this._ai = new ArrayInputAdapter(this._dis, bo);
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
/*     */   public ArrayInputStream(String name, ByteOrder bo) throws FileNotFoundException {
/*  88 */     this(new FileInputStream(name), bo);
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
/*     */   public ArrayInputStream(File file, ByteOrder bo) throws FileNotFoundException {
/* 100 */     this(new FileInputStream(file), bo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 108 */     super.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder getByteOrder() {
/* 116 */     return this._bo;
/*     */   }
/*     */   
/*     */   public void readFully(byte[] b) throws IOException {
/* 120 */     this._ai.readFully(b);
/*     */   }
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/* 123 */     this._ai.readFully(b, off, len);
/*     */   }
/*     */   public int skipBytes(int n) throws IOException {
/* 126 */     return this._ai.skipBytes(n);
/*     */   }
/*     */   public final boolean readBoolean() throws IOException {
/* 129 */     return this._ai.readBoolean();
/*     */   }
/*     */   public final byte readByte() throws IOException {
/* 132 */     return this._ai.readByte();
/*     */   }
/*     */   public final int readUnsignedByte() throws IOException {
/* 135 */     return this._ai.readUnsignedByte();
/*     */   }
/*     */   public final short readShort() throws IOException {
/* 138 */     return this._ai.readShort();
/*     */   }
/*     */   public final int readUnsignedShort() throws IOException {
/* 141 */     return this._ai.readUnsignedShort();
/*     */   }
/*     */   public final char readChar() throws IOException {
/* 144 */     return this._ai.readChar();
/*     */   }
/*     */   public final int readInt() throws IOException {
/* 147 */     return this._ai.readInt();
/*     */   }
/*     */   public final long readLong() throws IOException {
/* 150 */     return this._ai.readLong();
/*     */   }
/*     */   public final float readFloat() throws IOException {
/* 153 */     return this._ai.readFloat();
/*     */   }
/*     */   public final double readDouble() throws IOException {
/* 156 */     return this._ai.readDouble();
/*     */   }
/*     */   public final String readLine() throws IOException {
/* 159 */     return this._ai.readLine();
/*     */   }
/*     */   public final String readUTF() throws IOException {
/* 162 */     return this._ai.readUTF();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] v, int k, int n) throws IOException {
/* 172 */     this._ai.readBytes(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] v) throws IOException {
/* 181 */     this._ai.readBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[][] v) throws IOException {
/* 190 */     this._ai.readBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[][][] v) throws IOException {
/* 199 */     this._ai.readBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[] v, int k, int n) throws IOException {
/* 209 */     this._ai.readChars(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[] v) throws IOException {
/* 218 */     this._ai.readChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[][] v) throws IOException {
/* 227 */     this._ai.readChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[][][] v) throws IOException {
/* 236 */     this._ai.readChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[] v, int k, int n) throws IOException {
/* 246 */     this._ai.readShorts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[] v) throws IOException {
/* 255 */     this._ai.readShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[][] v) throws IOException {
/* 264 */     this._ai.readShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[][][] v) throws IOException {
/* 273 */     this._ai.readShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[] v, int k, int n) throws IOException {
/* 283 */     this._ai.readInts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[] v) throws IOException {
/* 292 */     this._ai.readInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[][] v) throws IOException {
/* 301 */     this._ai.readInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[][][] v) throws IOException {
/* 310 */     this._ai.readInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[] v, int k, int n) throws IOException {
/* 320 */     this._ai.readLongs(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[] v) throws IOException {
/* 329 */     this._ai.readLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[][] v) throws IOException {
/* 338 */     this._ai.readLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[][][] v) throws IOException {
/* 347 */     this._ai.readLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[] v, int k, int n) throws IOException {
/* 357 */     this._ai.readFloats(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[] v) throws IOException {
/* 366 */     this._ai.readFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[][] v) throws IOException {
/* 375 */     this._ai.readFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[][][] v) throws IOException {
/* 384 */     this._ai.readFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] v, int k, int n) throws IOException {
/* 394 */     this._ai.readDoubles(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] v) throws IOException {
/* 403 */     this._ai.readDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[][] v) throws IOException {
/* 412 */     this._ai.readDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[][][] v) throws IOException {
/* 421 */     this._ai.readDoubles(v);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */