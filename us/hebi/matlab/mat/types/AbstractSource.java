/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.zip.Inflater;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ import us.hebi.matlab.mat.util.ByteConverter;
/*     */ import us.hebi.matlab.mat.util.ByteConverters;
/*     */ import us.hebi.matlab.mat.util.Bytes;
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
/*     */ public abstract class AbstractSource
/*     */   implements Source
/*     */ {
/*     */   public AbstractSource order(ByteOrder byteOrder) {
/*  44 */     this.byteOrder = byteOrder;
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/*  50 */     if (this.byteOrder == null) {
/*  51 */       throw new IllegalStateException("Byte order has not been initialized");
/*     */     }
/*  53 */     return this.byteOrder;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() throws IOException {
/*  58 */     readBytes(this.bytes, 0, 1);
/*  59 */     return this.bytes[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() throws IOException {
/*  64 */     readBytes(this.bytes, 0, 2);
/*  65 */     return byteConverter.getShort(order(), this.bytes, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() throws IOException {
/*  70 */     readBytes(this.bytes, 0, 4);
/*  71 */     return byteConverter.getInt(order(), this.bytes, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() throws IOException {
/*  76 */     readBytes(this.bytes, 0, 8);
/*  77 */     return byteConverter.getLong(order(), this.bytes, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() throws IOException {
/*  82 */     readBytes(this.bytes, 0, 4);
/*  83 */     return byteConverter.getFloat(order(), this.bytes, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() throws IOException {
/*  88 */     readBytes(this.bytes, 0, 8);
/*  89 */     return byteConverter.getDouble(order(), this.bytes, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readByteBuffer(ByteBuffer buffer) throws IOException {
/*  94 */     if (buffer.hasArray()) {
/*     */       
/*  96 */       int offset = buffer.arrayOffset() + buffer.position();
/*  97 */       int length = buffer.remaining();
/*  98 */       readBytes(buffer.array(), offset, length);
/*  99 */       buffer.position(buffer.limit());
/*     */     } else {
/*     */       
/* 102 */       while (buffer.hasRemaining()) {
/* 103 */         int length = Math.min(buffer.remaining(), this.bytes.length);
/* 104 */         readBytes(this.bytes, 0, length);
/* 105 */         buffer.put(this.bytes, 0, length);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readShorts(short[] buffer, int offset, int length) throws IOException {
/* 112 */     for (int i = 0; i < length; ) {
/* 113 */       int n = Math.min((length - i) * 2, this.bytes.length);
/* 114 */       readBytes(this.bytes, 0, n);
/* 115 */       for (int j = 0; j < n; j += 2, i++) {
/* 116 */         buffer[offset + i] = byteConverter.getShort(order(), this.bytes, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readInts(int[] buffer, int offset, int length) throws IOException {
/* 123 */     for (int i = 0; i < length; ) {
/* 124 */       int n = Math.min((length - i) * 4, this.bytes.length);
/* 125 */       readBytes(this.bytes, 0, n);
/* 126 */       for (int j = 0; j < n; j += 4, i++) {
/* 127 */         buffer[offset + i] = byteConverter.getInt(order(), this.bytes, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readLongs(long[] buffer, int offset, int length) throws IOException {
/* 134 */     for (int i = 0; i < length; ) {
/* 135 */       int n = Math.min((length - i) * 8, this.bytes.length);
/* 136 */       readBytes(this.bytes, 0, n);
/* 137 */       for (int j = 0; j < n; j += 8, i++) {
/* 138 */         buffer[offset + i] = byteConverter.getLong(order(), this.bytes, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFloats(float[] buffer, int offset, int length) throws IOException {
/* 145 */     for (int i = 0; i < length; ) {
/* 146 */       int n = Math.min((length - i) * 4, this.bytes.length);
/* 147 */       readBytes(this.bytes, 0, n);
/* 148 */       for (int j = 0; j < n; j += 4, i++) {
/* 149 */         buffer[offset + i] = byteConverter.getFloat(order(), this.bytes, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readDoubles(double[] buffer, int offset, int length) throws IOException {
/* 156 */     for (int i = 0; i < length; ) {
/* 157 */       int n = Math.min((length - i) * 8, this.bytes.length);
/* 158 */       readBytes(this.bytes, 0, n);
/* 159 */       for (int j = 0; j < n; j += 8, i++) {
/* 160 */         buffer[offset + i] = byteConverter.getDouble(order(), this.bytes, j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void skip(long numBytes) throws IOException {
/* 167 */     long n = 0L;
/* 168 */     while (n < numBytes) {
/* 169 */       long count = Math.min(numBytes - n, this.bytes.length);
/* 170 */       readBytes(this.bytes, 0, (int)count);
/* 171 */       n += count;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Source readInflated(int numBytes, int inflateBufferSize) throws IOException {
/* 177 */     InputStream subInputStream = readBytesAsStream(numBytes);
/* 178 */     InputStream inflaterInput = new InflaterInputStream(subInputStream, new Inflater(), inflateBufferSize);
/* 179 */     return Sources.wrapInputStream(inflaterInput, this.bytes.length).order(order());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractSource(int bufferSize) {
/* 189 */     int size = Math.max(Bytes.nextPowerOfTwo(bufferSize), 128);
/* 190 */     this.bytes = new byte[size];
/*     */   }
/*     */   
/* 193 */   private ByteOrder byteOrder = null;
/*     */   private final byte[] bytes;
/* 195 */   private static final ByteConverter byteConverter = ByteConverters.getFastest();
/*     */   
/*     */   protected abstract InputStream readBytesAsStream(long paramLong) throws IOException;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */