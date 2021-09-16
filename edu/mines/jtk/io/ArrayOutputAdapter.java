/*     */ package edu.mines.jtk.io;
/*     */ 
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.FileOutputStream;
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
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArrayOutputAdapter
/*     */   implements ArrayOutput
/*     */ {
/*     */   private byte[] _buffer;
/*     */   private WritableByteChannel _wbc;
/*     */   private DataOutput _do;
/*     */   private ByteOrder _bo;
/*     */   private ByteBuffer _bb;
/*     */   private CharBuffer _cb;
/*     */   private ShortBuffer _sb;
/*     */   private IntBuffer _ib;
/*     */   private LongBuffer _lb;
/*     */   private FloatBuffer _fb;
/*     */   private DoubleBuffer _db;
/*     */   
/*     */   public ArrayOutputAdapter(DataOutput output) {
/*  43 */     this(output, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputAdapter(RandomAccessFile file) {
/*  52 */     this(file, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputAdapter(FileOutputStream stream) {
/*  61 */     this(stream, ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputAdapter(DataOutput output, ByteOrder order) {
/*  70 */     this(null, output, order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputAdapter(RandomAccessFile file, ByteOrder order) {
/*  80 */     this(file.getChannel(), file, order);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayOutputAdapter(FileOutputStream stream, ByteOrder order) {
/*  90 */     this(stream.getChannel(), new DataOutputStream(stream), order);
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
/*     */   public ArrayOutputAdapter(WritableByteChannel channel, DataOutput output, ByteOrder order) {
/* 103 */     this._wbc = channel;
/* 104 */     this._do = output;
/* 105 */     this._bo = order;
/* 106 */     if (this._wbc != null) {
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
/*     */   
/*     */   public void write(int b) throws IOException {
/* 135 */     this._do.write(b);
/*     */   }
/*     */   public void write(byte[] b) throws IOException {
/* 138 */     this._do.write(b);
/*     */   }
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 141 */     this._do.write(b, off, len);
/*     */   }
/*     */   public void writeBoolean(boolean v) throws IOException {
/* 144 */     this._do.writeBoolean(v);
/*     */   }
/*     */   public void writeByte(int v) throws IOException {
/* 147 */     this._do.writeByte(v);
/*     */   }
/*     */   public void writeShort(int v) throws IOException {
/* 150 */     if (this._bo == ByteOrder.BIG_ENDIAN) {
/* 151 */       this._do.write(v >>> 8 & 0xFF);
/* 152 */       this._do.write(v & 0xFF);
/*     */     } else {
/* 154 */       this._do.write(v & 0xFF);
/* 155 */       this._do.write(v >>> 8 & 0xFF);
/*     */     } 
/*     */   }
/*     */   public void writeChar(int v) throws IOException {
/* 159 */     this._do.writeShort(v);
/*     */   }
/*     */   public void writeInt(int v) throws IOException {
/* 162 */     if (this._bo == ByteOrder.BIG_ENDIAN) {
/* 163 */       this._do.write(v >>> 24 & 0xFF);
/* 164 */       this._do.write(v >>> 16 & 0xFF);
/* 165 */       this._do.write(v >>> 8 & 0xFF);
/* 166 */       this._do.write(v & 0xFF);
/*     */     } else {
/* 168 */       this._do.write(v & 0xFF);
/* 169 */       this._do.write(v >>> 8 & 0xFF);
/* 170 */       this._do.write(v >>> 16 & 0xFF);
/* 171 */       this._do.write(v >>> 24 & 0xFF);
/*     */     } 
/*     */   }
/*     */   public void writeLong(long v) throws IOException {
/* 175 */     if (this._bo == ByteOrder.BIG_ENDIAN) {
/* 176 */       this._do.write((int)(v >>> 56L) & 0xFF);
/* 177 */       this._do.write((int)(v >>> 48L) & 0xFF);
/* 178 */       this._do.write((int)(v >>> 40L) & 0xFF);
/* 179 */       this._do.write((int)(v >>> 32L) & 0xFF);
/* 180 */       this._do.write((int)(v >>> 24L) & 0xFF);
/* 181 */       this._do.write((int)(v >>> 16L) & 0xFF);
/* 182 */       this._do.write((int)(v >>> 8L) & 0xFF);
/* 183 */       this._do.write((int)v & 0xFF);
/*     */     } else {
/* 185 */       this._do.write((int)v & 0xFF);
/* 186 */       this._do.write((int)(v >>> 8L) & 0xFF);
/* 187 */       this._do.write((int)(v >>> 16L) & 0xFF);
/* 188 */       this._do.write((int)(v >>> 24L) & 0xFF);
/* 189 */       this._do.write((int)(v >>> 32L) & 0xFF);
/* 190 */       this._do.write((int)(v >>> 40L) & 0xFF);
/* 191 */       this._do.write((int)(v >>> 48L) & 0xFF);
/* 192 */       this._do.write((int)(v >>> 56L) & 0xFF);
/*     */     } 
/*     */   }
/*     */   public void writeFloat(float v) throws IOException {
/* 196 */     writeInt(Float.floatToIntBits(v));
/*     */   }
/*     */   public void writeDouble(double v) throws IOException {
/* 199 */     writeLong(Double.doubleToLongBits(v));
/*     */   }
/*     */   public void writeBytes(String s) throws IOException {
/* 202 */     this._do.writeBytes(s);
/*     */   }
/*     */   public void writeChars(String s) throws IOException {
/* 205 */     int n = s.length();
/* 206 */     for (int i = 0; i < n; i++)
/* 207 */       writeChar(s.charAt(i)); 
/*     */   }
/*     */   
/*     */   public void writeUTF(String s) throws IOException {
/* 211 */     this._do.writeUTF(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] v, int k, int n) throws IOException {
/* 221 */     write(v, k, n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[] v) throws IOException {
/* 230 */     write(v);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[][] v) throws IOException {
/* 238 */     for (byte[] vi : v) {
/* 239 */       writeBytes(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeBytes(byte[][][] v) throws IOException {
/* 247 */     for (byte[][] vi : v) {
/* 248 */       writeBytes(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[] v, int k, int n) throws IOException {
/* 258 */     int m = this._cb.capacity(); int j;
/* 259 */     for (j = 0; j < n; j += m) {
/* 260 */       int l = Math.min(n - j, m);
/* 261 */       this._cb.position(0).limit(l);
/* 262 */       this._cb.put(v, k + j, l);
/* 263 */       if (this._wbc != null) {
/* 264 */         this._bb.position(0).limit(l * 2);
/* 265 */         this._wbc.write(this._bb);
/*     */       } else {
/* 267 */         this._do.write(this._buffer, 0, l * 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[] v) throws IOException {
/* 278 */     writeChars(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[][] v) throws IOException {
/* 286 */     for (char[] vi : v) {
/* 287 */       writeChars(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeChars(char[][][] v) throws IOException {
/* 295 */     for (char[][] vi : v) {
/* 296 */       writeChars(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] v, int k, int n) throws IOException {
/* 306 */     int m = this._sb.capacity(); int j;
/* 307 */     for (j = 0; j < n; j += m) {
/* 308 */       int l = Math.min(n - j, m);
/* 309 */       this._sb.position(0).limit(l);
/* 310 */       this._sb.put(v, k + j, l);
/* 311 */       if (this._wbc != null) {
/* 312 */         this._bb.position(0).limit(l * 2);
/* 313 */         this._wbc.write(this._bb);
/*     */       } else {
/* 315 */         this._do.write(this._buffer, 0, l * 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] v) throws IOException {
/* 326 */     writeShorts(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[][] v) throws IOException {
/* 334 */     for (short[] vi : v) {
/* 335 */       writeShorts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShorts(short[][][] v) throws IOException {
/* 343 */     for (short[][] vi : v) {
/* 344 */       writeShorts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[] v, int k, int n) throws IOException {
/* 354 */     int m = this._ib.capacity(); int j;
/* 355 */     for (j = 0; j < n; j += m) {
/* 356 */       int l = Math.min(n - j, m);
/* 357 */       this._ib.position(0).limit(l);
/* 358 */       this._ib.put(v, k + j, l);
/* 359 */       if (this._wbc != null) {
/* 360 */         this._bb.position(0).limit(l * 4);
/* 361 */         this._wbc.write(this._bb);
/*     */       } else {
/* 363 */         this._do.write(this._buffer, 0, l * 4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[] v) throws IOException {
/* 374 */     writeInts(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[][] v) throws IOException {
/* 382 */     for (int[] vi : v) {
/* 383 */       writeInts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInts(int[][][] v) throws IOException {
/* 391 */     for (int[][] vi : v) {
/* 392 */       writeInts(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] v, int k, int n) throws IOException {
/* 402 */     int m = this._lb.capacity(); int j;
/* 403 */     for (j = 0; j < n; j += m) {
/* 404 */       int l = Math.min(n - j, m);
/* 405 */       this._lb.position(0).limit(l);
/* 406 */       this._lb.put(v, k + j, l);
/* 407 */       if (this._wbc != null) {
/* 408 */         this._bb.position(0).limit(l * 8);
/* 409 */         this._wbc.write(this._bb);
/*     */       } else {
/* 411 */         this._do.write(this._buffer, 0, l * 8);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] v) throws IOException {
/* 422 */     writeLongs(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[][] v) throws IOException {
/* 430 */     for (long[] vi : v) {
/* 431 */       writeLongs(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeLongs(long[][][] v) throws IOException {
/* 439 */     for (long[][] vi : v) {
/* 440 */       writeLongs(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] v, int k, int n) throws IOException {
/* 450 */     int m = this._fb.capacity(); int j;
/* 451 */     for (j = 0; j < n; j += m) {
/* 452 */       int l = Math.min(n - j, m);
/* 453 */       this._fb.position(0).limit(l);
/* 454 */       this._fb.put(v, k + j, l);
/* 455 */       if (this._wbc != null) {
/* 456 */         this._bb.position(0).limit(l * 4);
/* 457 */         this._wbc.write(this._bb);
/*     */       } else {
/* 459 */         this._do.write(this._buffer, 0, l * 4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] v) throws IOException {
/* 470 */     writeFloats(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[][] v) throws IOException {
/* 478 */     for (float[] vi : v) {
/* 479 */       writeFloats(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeFloats(float[][][] v) throws IOException {
/* 487 */     for (float[][] vi : v) {
/* 488 */       writeFloats(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] v, int k, int n) throws IOException {
/* 498 */     int m = this._db.capacity(); int j;
/* 499 */     for (j = 0; j < n; j += m) {
/* 500 */       int l = Math.min(n - j, m);
/* 501 */       this._db.position(0).limit(l);
/* 502 */       this._db.put(v, k + j, l);
/* 503 */       if (this._wbc != null) {
/* 504 */         this._bb.position(0).limit(l * 8);
/* 505 */         this._wbc.write(this._bb);
/*     */       } else {
/* 507 */         this._do.write(this._buffer, 0, l * 8);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] v) throws IOException {
/* 518 */     writeDoubles(v, 0, v.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[][] v) throws IOException {
/* 526 */     for (double[] vi : v) {
/* 527 */       writeDoubles(vi);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[][][] v) throws IOException {
/* 535 */     for (double[][] vi : v)
/* 536 */       writeDoubles(vi); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/io/ArrayOutputAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */