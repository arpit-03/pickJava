/*     */ package edu.mines.jtk.io;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
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
/*     */ public class ArrayFile
/*     */   implements ArrayInput, ArrayOutput, Closeable
/*     */ {
/*     */   private RandomAccessFile _raf;
/*     */   private ByteOrder _bor;
/*     */   private ByteOrder _bow;
/*     */   private ArrayInput _ai;
/*     */   private ArrayOutput _ao;
/*     */   
/*     */   public ArrayFile(String name, String mode) throws FileNotFoundException {
/*  45 */     this(name, mode, ByteOrder.BIG_ENDIAN, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayFile(File file, String mode) throws FileNotFoundException {
/*  54 */     this(file, mode, ByteOrder.BIG_ENDIAN, ByteOrder.BIG_ENDIAN);
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
/*     */   public ArrayFile(String name, String mode, ByteOrder bor, ByteOrder bow) throws FileNotFoundException {
/*  67 */     this((name != null) ? new File(name) : null, mode, bor, bow);
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
/*     */   public ArrayFile(File file, String mode, ByteOrder bor, ByteOrder bow) throws FileNotFoundException {
/*  80 */     this(new RandomAccessFile(file, mode), bor, bow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayFile(RandomAccessFile raf, ByteOrder bor, ByteOrder bow) {
/*  91 */     this._raf = raf;
/*  92 */     this._bor = bor;
/*  93 */     this._bow = bow;
/*  94 */     this._ai = new ArrayInputAdapter(raf, bor);
/*  95 */     this._ao = new ArrayOutputAdapter(raf, bow);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder getByteOrderRead() {
/* 103 */     return this._bor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder getByteOrderWrite() {
/* 111 */     return this._bow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/* 120 */     return this._raf.read();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 129 */     return this._raf.read(b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 140 */     return this._raf.read(b, off, len);
/*     */   }
/*     */   
/*     */   public void readFully(byte[] b) throws IOException {
/* 144 */     this._ai.readFully(b);
/*     */   }
/*     */   
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/* 148 */     this._ai.readFully(b, off, len);
/*     */   }
/*     */   
/*     */   public int skipBytes(int n) throws IOException {
/* 152 */     return this._ai.skipBytes(n);
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/* 156 */     this._ao.write(b);
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 160 */     this._ao.write(b);
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 164 */     this._ao.write(b, off, len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFilePointer() throws IOException {
/* 173 */     return this._raf.getFilePointer();
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
/*     */   public void seek(long off) throws IOException {
/* 187 */     this._raf.seek(off);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long length() throws IOException {
/* 195 */     return this._raf.length();
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
/*     */   public void setLength(long newLength) throws IOException {
/* 210 */     this._raf.setLength(newLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 217 */     this._raf.close();
/* 218 */     this._raf = null;
/* 219 */     this._ai = null;
/* 220 */     this._ao = null;
/*     */   }
/*     */   
/*     */   public final boolean readBoolean() throws IOException {
/* 224 */     return this._ai.readBoolean();
/*     */   }
/*     */   
/*     */   public final byte readByte() throws IOException {
/* 228 */     return this._ai.readByte();
/*     */   }
/*     */   
/*     */   public final int readUnsignedByte() throws IOException {
/* 232 */     return this._ai.readUnsignedByte();
/*     */   }
/*     */   
/*     */   public final short readShort() throws IOException {
/* 236 */     return this._ai.readShort();
/*     */   }
/*     */   
/*     */   public final int readUnsignedShort() throws IOException {
/* 240 */     return this._ai.readUnsignedShort();
/*     */   }
/*     */   
/*     */   public final char readChar() throws IOException {
/* 244 */     return this._ai.readChar();
/*     */   }
/*     */   
/*     */   public final int readInt() throws IOException {
/* 248 */     return this._ai.readInt();
/*     */   }
/*     */   
/*     */   public final long readLong() throws IOException {
/* 252 */     return this._ai.readLong();
/*     */   }
/*     */   
/*     */   public final float readFloat() throws IOException {
/* 256 */     return this._ai.readFloat();
/*     */   }
/*     */   
/*     */   public final double readDouble() throws IOException {
/* 260 */     return this._ai.readDouble();
/*     */   }
/*     */   
/*     */   public final String readLine() throws IOException {
/* 264 */     return this._ai.readLine();
/*     */   }
/*     */   
/*     */   public final String readUTF() throws IOException {
/* 268 */     return this._ai.readUTF();
/*     */   }
/*     */   
/*     */   public void writeBoolean(boolean v) throws IOException {
/* 272 */     this._ao.writeBoolean(v);
/*     */   }
/*     */   
/*     */   public void writeByte(int v) throws IOException {
/* 276 */     this._ao.writeByte(v);
/*     */   }
/*     */   
/*     */   public void writeShort(int v) throws IOException {
/* 280 */     this._ao.writeShort(v);
/*     */   }
/*     */   
/*     */   public void writeChar(int v) throws IOException {
/* 284 */     this._ao.writeChar(v);
/*     */   }
/*     */   
/*     */   public void writeInt(int v) throws IOException {
/* 288 */     this._ao.writeInt(v);
/*     */   }
/*     */   
/*     */   public void writeLong(long v) throws IOException {
/* 292 */     this._ao.writeLong(v);
/*     */   }
/*     */   
/*     */   public void writeFloat(float v) throws IOException {
/* 296 */     this._ao.writeFloat(v);
/*     */   }
/*     */   
/*     */   public void writeDouble(double v) throws IOException {
/* 300 */     this._ao.writeDouble(v);
/*     */   }
/*     */   
/*     */   public void writeBytes(String s) throws IOException {
/* 304 */     this._ao.writeBytes(s);
/*     */   }
/*     */   
/*     */   public void writeChars(String s) throws IOException {
/* 308 */     this._ao.writeChars(s);
/*     */   }
/*     */   
/*     */   public void writeUTF(String s) throws IOException {
/* 312 */     this._ao.writeUTF(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] v, int k, int n) throws IOException {
/* 322 */     this._ai.readBytes(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] v) throws IOException {
/* 331 */     this._ai.readBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[][] v) throws IOException {
/* 340 */     this._ai.readBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[][][] v) throws IOException {
/* 349 */     this._ai.readBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[] v, int k, int n) throws IOException {
/* 359 */     this._ai.readChars(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[] v) throws IOException {
/* 368 */     this._ai.readChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[][] v) throws IOException {
/* 377 */     this._ai.readChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[][][] v) throws IOException {
/* 386 */     this._ai.readChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[] v, int k, int n) throws IOException {
/* 396 */     this._ai.readShorts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[] v) throws IOException {
/* 405 */     this._ai.readShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[][] v) throws IOException {
/* 414 */     this._ai.readShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[][][] v) throws IOException {
/* 423 */     this._ai.readShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[] v, int k, int n) throws IOException {
/* 433 */     this._ai.readInts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[] v) throws IOException {
/* 442 */     this._ai.readInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[][] v) throws IOException {
/* 451 */     this._ai.readInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[][][] v) throws IOException {
/* 460 */     this._ai.readInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[] v, int k, int n) throws IOException {
/* 470 */     this._ai.readLongs(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[] v) throws IOException {
/* 479 */     this._ai.readLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[][] v) throws IOException {
/* 488 */     this._ai.readLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[][][] v) throws IOException {
/* 497 */     this._ai.readLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[] v, int k, int n) throws IOException {
/* 507 */     this._ai.readFloats(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[] v) throws IOException {
/* 516 */     this._ai.readFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[][] v) throws IOException {
/* 525 */     this._ai.readFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[][][] v) throws IOException {
/* 534 */     this._ai.readFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] v, int k, int n) throws IOException {
/* 544 */     this._ai.readDoubles(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] v) throws IOException {
/* 553 */     this._ai.readDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[][] v) throws IOException {
/* 562 */     this._ai.readDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[][][] v) throws IOException {
/* 571 */     this._ai.readDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] v, int k, int n) throws IOException {
/* 581 */     this._ao.writeBytes(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] v) throws IOException {
/* 590 */     this._ao.writeBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[][] v) throws IOException {
/* 598 */     this._ao.writeBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[][][] v) throws IOException {
/* 606 */     this._ao.writeBytes(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[] v, int k, int n) throws IOException {
/* 616 */     this._ao.writeChars(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[] v) throws IOException {
/* 625 */     this._ao.writeChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[][] v) throws IOException {
/* 633 */     this._ao.writeChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[][][] v) throws IOException {
/* 641 */     this._ao.writeChars(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] v, int k, int n) throws IOException {
/* 651 */     this._ao.writeShorts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] v) throws IOException {
/* 660 */     this._ao.writeShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[][] v) throws IOException {
/* 668 */     this._ao.writeShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[][][] v) throws IOException {
/* 676 */     this._ao.writeShorts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[] v, int k, int n) throws IOException {
/* 686 */     this._ao.writeInts(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[] v) throws IOException {
/* 695 */     this._ao.writeInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[][] v) throws IOException {
/* 703 */     this._ao.writeInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[][][] v) throws IOException {
/* 711 */     this._ao.writeInts(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] v, int k, int n) throws IOException {
/* 721 */     this._ao.writeLongs(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] v) throws IOException {
/* 730 */     this._ao.writeLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[][] v) throws IOException {
/* 738 */     this._ao.writeLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[][][] v) throws IOException {
/* 746 */     this._ao.writeLongs(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] v, int k, int n) throws IOException {
/* 756 */     this._ao.writeFloats(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] v) throws IOException {
/* 765 */     this._ao.writeFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[][] v) throws IOException {
/* 773 */     this._ao.writeFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[][][] v) throws IOException {
/* 781 */     this._ao.writeFloats(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] v, int k, int n) throws IOException {
/* 791 */     this._ao.writeDoubles(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] v) throws IOException {
/* 800 */     this._ao.writeDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[][] v) throws IOException {
/* 808 */     this._ao.writeDoubles(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[][][] v) throws IOException {
/* 816 */     this._ao.writeDoubles(v);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */