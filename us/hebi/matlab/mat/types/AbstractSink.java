/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import us.hebi.matlab.mat.util.ByteConverter;
/*     */ import us.hebi.matlab.mat.util.ByteConverters;
/*     */ import us.hebi.matlab.mat.util.Bytes;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
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
/*     */ public abstract class AbstractSink
/*     */   implements Sink
/*     */ {
/*     */   public AbstractSink nativeOrder() {
/*  44 */     return order(ByteOrder.nativeOrder());
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractSink order(ByteOrder byteOrder) {
/*  49 */     this.byteOrder = (ByteOrder)Preconditions.checkNotNull(byteOrder);
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteOrder order() {
/*  55 */     return this.byteOrder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByte(byte value) throws IOException {
/*  60 */     this.bytes[0] = value;
/*  61 */     writeBytes(this.bytes, 0, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShort(short value) throws IOException {
/*  66 */     byteConverter.putShort(value, order(), this.bytes, 0);
/*  67 */     writeBytes(this.bytes, 0, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt(int value) throws IOException {
/*  72 */     byteConverter.putInt(value, order(), this.bytes, 0);
/*  73 */     writeBytes(this.bytes, 0, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long value) throws IOException {
/*  78 */     byteConverter.putLong(value, order(), this.bytes, 0);
/*  79 */     writeBytes(this.bytes, 0, 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloat(float value) throws IOException {
/*  84 */     byteConverter.putFloat(value, order(), this.bytes, 0);
/*  85 */     writeBytes(this.bytes, 0, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDouble(double value) throws IOException {
/*  90 */     byteConverter.putDouble(value, order(), this.bytes, 0);
/*  91 */     writeBytes(this.bytes, 0, 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByteBuffer(ByteBuffer buffer) throws IOException {
/*  96 */     if (buffer.hasArray()) {
/*     */       
/*  98 */       int offset = buffer.arrayOffset() + buffer.position();
/*  99 */       int length = buffer.remaining();
/* 100 */       writeBytes(buffer.array(), offset, length);
/* 101 */       buffer.position(buffer.limit());
/*     */     } else {
/*     */       
/* 104 */       while (buffer.hasRemaining()) {
/* 105 */         int length = Math.min(buffer.remaining(), this.bytes.length);
/* 106 */         buffer.get(this.bytes, 0, length);
/* 107 */         writeBytes(this.bytes, 0, length);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInputStream(InputStream input, long length) throws IOException {
/* 115 */     for (long i = 0L; i < length; ) {
/* 116 */       int maxN = (int)Math.min(length - i, this.bytes.length);
/* 117 */       int n = input.read(this.bytes, 0, maxN);
/* 118 */       if (n < 0) throw new EOFException(); 
/* 119 */       writeBytes(this.bytes, 0, n);
/* 120 */       i += n;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDataInput(DataInput input, long length) throws IOException {
/* 126 */     for (long i = 0L; i < length; ) {
/* 127 */       int n = (int)Math.min(length - i, this.bytes.length);
/* 128 */       input.readFully(this.bytes, 0, n);
/* 129 */       writeBytes(this.bytes, 0, n);
/* 130 */       i += n;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShorts(short[] buffer, int offset, int length) throws IOException {
/* 136 */     for (int i = 0; i < length; ) {
/* 137 */       int n = Math.min((length - i) * 2, this.bytes.length);
/* 138 */       for (int j = 0; j < n; j += 2, i++) {
/* 139 */         byteConverter.putShort(buffer[offset + i], order(), this.bytes, j);
/*     */       }
/* 141 */       writeBytes(this.bytes, 0, n);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInts(int[] buffer, int offset, int length) throws IOException {
/* 147 */     for (int i = 0; i < length; ) {
/* 148 */       int n = Math.min((length - i) * 4, this.bytes.length);
/* 149 */       for (int j = 0; j < n; j += 4, i++) {
/* 150 */         byteConverter.putInt(buffer[offset + i], order(), this.bytes, j);
/*     */       }
/* 152 */       writeBytes(this.bytes, 0, n);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLongs(long[] buffer, int offset, int length) throws IOException {
/* 158 */     for (int i = 0; i < length; ) {
/* 159 */       int n = Math.min((length - i) * 8, this.bytes.length);
/* 160 */       for (int j = 0; j < n; j += 8, i++) {
/* 161 */         byteConverter.putLong(buffer[offset + i], order(), this.bytes, j);
/*     */       }
/* 163 */       writeBytes(this.bytes, 0, n);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloats(float[] buffer, int offset, int length) throws IOException {
/* 169 */     for (int i = 0; i < length; ) {
/* 170 */       int n = Math.min((length - i) * 4, this.bytes.length);
/* 171 */       for (int j = 0; j < n; j += 4, i++) {
/* 172 */         byteConverter.putFloat(buffer[offset + i], order(), this.bytes, j);
/*     */       }
/* 174 */       writeBytes(this.bytes, 0, n);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDoubles(double[] buffer, int offset, int length) throws IOException {
/* 180 */     for (int i = 0; i < length; ) {
/* 181 */       int n = Math.min((length - i) * 8, this.bytes.length);
/* 182 */       for (int j = 0; j < n; j += 8, i++) {
/* 183 */         byteConverter.putDouble(buffer[offset + i], order(), this.bytes, j);
/*     */       }
/* 185 */       writeBytes(this.bytes, 0, n);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Sink writeDeflated(Deflater deflater) {
/* 191 */     DeflaterOutputStream deflateStream = new DeflaterOutputStream(this.streamWrapper, deflater, this.bytes.length);
/* 192 */     int deflateBufferSize = Math.max(1024, this.bytes.length);
/* 193 */     return Sinks.wrapNonSeeking(deflateStream, deflateBufferSize).order(order());
/*     */   }
/*     */   
/* 196 */   private OutputStream streamWrapper = new Sinks.SinkOutputStream(this);
/*     */   
/*     */   private ByteOrder byteOrder;
/*     */   
/*     */   private final byte[] bytes;
/*     */ 
/*     */   
/*     */   protected AbstractSink(int copyBufferSize) {
/* 204 */     this.byteOrder = ByteOrder.nativeOrder();
/*     */     int size = Math.max(Bytes.nextPowerOfTwo(copyBufferSize), 256);
/* 206 */     this.bytes = new byte[size]; } private static final ByteConverter byteConverter = ByteConverters.getFastest();
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractSink.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */