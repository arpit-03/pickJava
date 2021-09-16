/*     */ package edu.mines.jtk.io;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.DoubleBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.nio.LongBuffer;
/*     */ import java.nio.ShortBuffer;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayInputAdapter
/*     */   implements ArrayInput
/*     */ {
/*     */   private byte[] _buffer;
/*     */   private ReadableByteChannel _rbc;
/*     */   private DataInput _di;
/*     */   private ByteOrder _bo;
/*     */   private ByteBuffer _bb;
/*     */   private CharBuffer _cb;
/*     */   private ShortBuffer _sb;
/*     */   private IntBuffer _ib;
/*     */   private LongBuffer _lb;
/*     */   private FloatBuffer _fb;
/*     */   private DoubleBuffer _db;
/*     */   
/*     */   public ArrayInputAdapter(DataInput input) {
/*  43 */     this(input, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputAdapter(RandomAccessFile file) {
/*  52 */     this(file, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputAdapter(FileInputStream stream) {
/*  61 */     this(stream, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputAdapter(DataInput input, ByteOrder order) {
/*  70 */     this(null, input, order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputAdapter(RandomAccessFile file, ByteOrder order) {
/*  80 */     this(file.getChannel(), file, order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayInputAdapter(FileInputStream stream, ByteOrder order) {
/*  90 */     this(stream.getChannel(), new DataInputStream(stream), order);
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
/*     */   public ArrayInputAdapter(ReadableByteChannel channel, DataInput input, ByteOrder order) {
/* 103 */     this._rbc = channel;
/* 104 */     this._di = input;
/* 105 */     this._bo = order;
/* 106 */     if (this._rbc != null) {
/* 107 */       this._bb = ByteBuffer.allocateDirect(4096);
/*     */     } else {
/* 109 */       this._buffer = new byte[4096];
/* 110 */       this._bb = ByteBuffer.wrap(this._buffer);
/*     */     } 
/* 112 */     if (order == ByteOrder.BIG_ENDIAN) {
/* 113 */       this._bb.order(ByteOrder.BIG_ENDIAN);
/*     */     } else {
/* 115 */       this._bb.order(ByteOrder.LITTLE_ENDIAN);
/*     */     } 
/* 117 */     this._cb = this._bb.asCharBuffer();
/* 118 */     this._sb = this._bb.asShortBuffer();
/* 119 */     this._ib = this._bb.asIntBuffer();
/* 120 */     this._lb = this._bb.asLongBuffer();
/* 121 */     this._fb = this._bb.asFloatBuffer();
/* 122 */     this._db = this._bb.asDoubleBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteOrder getByteOrder() {
/* 130 */     return this._bo;
/*     */   }
/*     */   
/*     */   public void readFully(byte[] b) throws IOException {
/* 134 */     this._di.readFully(b);
/*     */   }
/*     */   public void readFully(byte[] b, int off, int len) throws IOException {
/* 137 */     this._di.readFully(b, off, len);
/*     */   }
/*     */   public int skipBytes(int n) throws IOException {
/* 140 */     return this._di.skipBytes(n);
/*     */   }
/*     */   public final boolean readBoolean() throws IOException {
/* 143 */     return this._di.readBoolean();
/*     */   }
/*     */   public final byte readByte() throws IOException {
/* 146 */     return this._di.readByte();
/*     */   }
/*     */   public final int readUnsignedByte() throws IOException {
/* 149 */     return this._di.readUnsignedByte();
/*     */   }
/*     */   public final short readShort() throws IOException {
/* 152 */     int b1 = this._di.readUnsignedByte();
/* 153 */     int b2 = this._di.readUnsignedByte();
/* 154 */     if (this._bo == ByteOrder.BIG_ENDIAN) {
/* 155 */       return (short)((b1 << 8) + b2);
/*     */     }
/* 157 */     return (short)((b2 << 8) + b1);
/*     */   }
/*     */   
/*     */   public final int readUnsignedShort() throws IOException {
/* 161 */     return readShort() & 0xFFFF;
/*     */   }
/*     */   public final char readChar() throws IOException {
/* 164 */     return (char)readShort();
/*     */   }
/*     */   public final int readInt() throws IOException {
/* 167 */     int b1 = this._di.readUnsignedByte();
/* 168 */     int b2 = this._di.readUnsignedByte();
/* 169 */     int b3 = this._di.readUnsignedByte();
/* 170 */     int b4 = this._di.readUnsignedByte();
/* 171 */     if (this._bo == ByteOrder.BIG_ENDIAN) {
/* 172 */       return (b1 << 24) + (b2 << 16) + (b3 << 8) + b4;
/*     */     }
/* 174 */     return (b4 << 24) + (b3 << 16) + (b2 << 8) + b1;
/*     */   }
/*     */   
/*     */   public final long readLong() throws IOException {
/* 178 */     int i1 = readInt();
/* 179 */     int i2 = readInt();
/* 180 */     if (this._bo == ByteOrder.BIG_ENDIAN) {
/* 181 */       return (i1 << 32L) + (i2 & 0xFFFFFFFFL);
/*     */     }
/* 183 */     return (i2 << 32L) + (i1 & 0xFFFFFFFFL);
/*     */   }
/*     */   
/*     */   public final float readFloat() throws IOException {
/* 187 */     return Float.intBitsToFloat(readInt());
/*     */   }
/*     */   public final double readDouble() throws IOException {
/* 190 */     return Double.longBitsToDouble(readLong());
/*     */   }
/*     */   public final String readLine() throws IOException {
/* 193 */     return this._di.readLine();
/*     */   }
/*     */   public final String readUTF() throws IOException {
/* 196 */     return this._di.readUTF();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] v, int k, int n) throws IOException {
/* 206 */     readFully(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[] v) throws IOException {
/* 215 */     readFully(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[][] v) throws IOException {
/* 224 */     for (byte[] vi : v) {
/* 225 */       readBytes(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readBytes(byte[][][] v) throws IOException {
/* 234 */     for (byte[][] vi : v) {
/* 235 */       readBytes(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[] v, int k, int n) throws IOException {
/* 245 */     int m = this._cb.capacity(); int j;
/* 246 */     for (j = 0; j < n; j += m) {
/* 247 */       int l = Math.min(n - j, m);
/* 248 */       if (this._rbc != null) {
/* 249 */         this._bb.position(0).limit(l * 2);
/* 250 */         this._rbc.read(this._bb);
/*     */       } else {
/* 252 */         this._di.readFully(this._buffer, 0, l * 2);
/*     */       } 
/* 254 */       this._cb.position(0).limit(l);
/* 255 */       this._cb.get(v, k + j, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[] v) throws IOException {
/* 265 */     readChars(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[][] v) throws IOException {
/* 274 */     for (char[] vi : v) {
/* 275 */       readChars(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readChars(char[][][] v) throws IOException {
/* 284 */     for (char[][] vi : v) {
/* 285 */       readChars(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[] v, int k, int n) throws IOException {
/* 295 */     int m = this._sb.capacity(); int j;
/* 296 */     for (j = 0; j < n; j += m) {
/* 297 */       int l = Math.min(n - j, m);
/* 298 */       if (this._rbc != null) {
/* 299 */         this._bb.position(0).limit(l * 2);
/* 300 */         this._rbc.read(this._bb);
/*     */       } else {
/* 302 */         this._di.readFully(this._buffer, 0, l * 2);
/*     */       } 
/* 304 */       this._sb.position(0).limit(l);
/* 305 */       this._sb.get(v, k + j, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[] v) throws IOException {
/* 315 */     readShorts(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[][] v) throws IOException {
/* 324 */     for (short[] vi : v) {
/* 325 */       readShorts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readShorts(short[][][] v) throws IOException {
/* 334 */     for (short[][] vi : v) {
/* 335 */       readShorts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[] v, int k, int n) throws IOException {
/* 345 */     int m = this._ib.capacity(); int j;
/* 346 */     for (j = 0; j < n; j += m) {
/* 347 */       int l = Math.min(n - j, m);
/* 348 */       if (this._rbc != null) {
/* 349 */         this._bb.position(0).limit(l * 4);
/* 350 */         this._rbc.read(this._bb);
/*     */       } else {
/* 352 */         this._di.readFully(this._buffer, 0, l * 4);
/*     */       } 
/* 354 */       this._ib.position(0).limit(l);
/* 355 */       this._ib.get(v, k + j, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[] v) throws IOException {
/* 365 */     readInts(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[][] v) throws IOException {
/* 374 */     for (int[] vi : v) {
/* 375 */       readInts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readInts(int[][][] v) throws IOException {
/* 384 */     for (int[][] vi : v) {
/* 385 */       readInts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[] v, int k, int n) throws IOException {
/* 395 */     int m = this._lb.capacity(); int j;
/* 396 */     for (j = 0; j < n; j += m) {
/* 397 */       int l = Math.min(n - j, m);
/* 398 */       if (this._rbc != null) {
/* 399 */         this._bb.position(0).limit(l * 8);
/* 400 */         this._rbc.read(this._bb);
/*     */       } else {
/* 402 */         this._di.readFully(this._buffer, 0, l * 8);
/*     */       } 
/* 404 */       this._lb.position(0).limit(l);
/* 405 */       this._lb.get(v, k + j, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[] v) throws IOException {
/* 415 */     readLongs(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[][] v) throws IOException {
/* 424 */     for (long[] vi : v) {
/* 425 */       readLongs(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readLongs(long[][][] v) throws IOException {
/* 434 */     for (long[][] vi : v) {
/* 435 */       readLongs(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[] v, int k, int n) throws IOException {
/* 445 */     int m = this._fb.capacity(); int j;
/* 446 */     for (j = 0; j < n; j += m) {
/* 447 */       int l = Math.min(n - j, m);
/* 448 */       if (this._rbc != null) {
/* 449 */         this._bb.position(0).limit(l * 4);
/* 450 */         this._rbc.read(this._bb);
/*     */       } else {
/* 452 */         this._di.readFully(this._buffer, 0, l * 4);
/*     */       } 
/* 454 */       this._fb.position(0).limit(l);
/* 455 */       this._fb.get(v, k + j, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[] v) throws IOException {
/* 465 */     readFloats(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[][] v) throws IOException {
/* 474 */     for (float[] vi : v) {
/* 475 */       readFloats(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFloats(float[][][] v) throws IOException {
/* 484 */     for (float[][] vi : v) {
/* 485 */       readFloats(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] v, int k, int n) throws IOException {
/* 495 */     int m = this._db.capacity(); int j;
/* 496 */     for (j = 0; j < n; j += m) {
/* 497 */       int l = Math.min(n - j, m);
/* 498 */       if (this._rbc != null) {
/* 499 */         this._bb.position(0).limit(l * 8);
/* 500 */         this._rbc.read(this._bb);
/*     */       } else {
/* 502 */         this._di.readFully(this._buffer, 0, l * 8);
/*     */       } 
/* 504 */       this._db.position(0).limit(l);
/* 505 */       this._db.get(v, k + j, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] v) throws IOException {
/* 515 */     readDoubles(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[][] v) throws IOException {
/* 524 */     for (double[] vi : v) {
/* 525 */       readDoubles(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readDoubles(double[][][] v) throws IOException {
/* 534 */     for (double[][] vi : v)
/* 535 */       readDoubles(vi); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayInputAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */