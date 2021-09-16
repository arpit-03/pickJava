/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.FileChannel;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
/*     */ import us.hebi.matlab.mat.util.Unsafe9R;
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
/*     */ public class Sources
/*     */ {
/*     */   public static Source openFile(String file) throws IOException {
/*  43 */     return openFile(new File(checkInputNotNull(file)));
/*     */   }
/*     */   
/*     */   public static Source openFile(File file) throws IOException {
/*  47 */     checkFileExists(file);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     if (file.length() > 2147483647L) {
/*  53 */       return openStreamingFile(file);
/*     */     }
/*     */ 
/*     */     
/*  57 */     final FileChannel channel = (new RandomAccessFile(file, "r")).getChannel();
/*  58 */     ByteBuffer buffer = channel
/*  59 */       .map(FileChannel.MapMode.READ_ONLY, 0L, (int)channel.size())
/*  60 */       .load();
/*  61 */     buffer.order(ByteOrder.nativeOrder());
/*     */ 
/*     */     
/*  64 */     return new ByteBufferSource(buffer, 512)
/*     */       {
/*     */         public void close() throws IOException {
/*  67 */           super.close();
/*  68 */           Unsafe9R.invokeCleaner(this.buffer);
/*  69 */           channel.close();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Source openStreamingFile(File file) throws IOException {
/*  76 */     return new BufferedFileSource(checkFileExists(file));
/*     */   }
/*     */   
/*     */   public static Source wrap(byte[] bytes) {
/*  80 */     return wrap(ByteBuffer.wrap(checkInputNotNull(bytes)));
/*     */   }
/*     */   
/*     */   public static Source wrap(ByteBuffer buffer) {
/*  84 */     return new ByteBufferSource(checkInputNotNull(buffer), 128, null, null);
/*     */   }
/*     */   
/*     */   public static Source wrapInputStream(InputStream inputStream) {
/*  88 */     return wrapInputStream(inputStream, 512);
/*     */   }
/*     */   
/*     */   public static Source wrapInputStream(InputStream inputStream, int bufferSize) {
/*  92 */     return new InputStreamSource(checkInputNotNull(inputStream), bufferSize);
/*     */   }
/*     */   
/*     */   private static <T> T checkInputNotNull(T input) {
/*  96 */     if (input == null)
/*  97 */       throw new NullPointerException("input must not be null"); 
/*  98 */     return input;
/*     */   }
/*     */   
/*     */   private static File checkFileExists(File file) {
/* 102 */     if (file == null)
/* 103 */       throw new NullPointerException("input file must not be null"); 
/* 104 */     if (!file.exists())
/* 105 */       throw new IllegalArgumentException("input file does not exist: " + file.getPath()); 
/* 106 */     if (!file.isFile())
/* 107 */       throw new IllegalArgumentException("input path is not a valid file: " + file.getPath()); 
/* 108 */     return file;
/*     */   } private static class BufferedFileSource extends AbstractSource
/*     */   {
/*     */     private final ByteBuffer directBuffer; private final FileChannel fileChannel; private long fileChannelPosition; protected InputStream readBytesAsStream(long numBytes) throws IOException { return new Sources.SourceInputStream(this, numBytes, null); } public long getPosition() { return this.fileChannelPosition + this.directBuffer.position(); } public void readByteBuffer(ByteBuffer buffer) throws IOException { if (buffer.hasArray()) { super.readByteBuffer(buffer); return; }  if (this.directBuffer.remaining() > 0) { int oldLimit = this.directBuffer.limit(); int limit = Math.min(oldLimit, this.directBuffer.position() + buffer.remaining()); this.directBuffer.limit(limit); buffer.put(this.directBuffer); this.directBuffer.limit(oldLimit); }  if (buffer.hasRemaining()) { Preconditions.checkState(!this.directBuffer.hasRemaining(), "Read buffer was not fully emptied"); int numBytes = this.fileChannel.read(buffer); this.fileChannelPosition += Math.max(0, numBytes); if (buffer.hasRemaining())
/*     */           throw new EOFException();  }
/* 113 */        } protected BufferedFileSource(File file) throws IOException { super(512);
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
/* 194 */       this.directBuffer = ByteBuffer.allocateDirect(32768);
/*     */       
/* 196 */       this.fileChannelPosition = 0L; this.fileChannel = (new RandomAccessFile(file, "rw")).getChannel(); this.directBuffer.limit(0); } public void readBytes(byte[] buffer, int offset, int length) throws IOException { while (length > 0) { if (this.directBuffer.remaining() == 0) {
/*     */           this.fileChannelPosition += this.directBuffer.position(); this.directBuffer.clear(); if (this.fileChannel.read(this.directBuffer) == -1)
/*     */             throw new EOFException();  this.directBuffer.flip();
/*     */         }  int n = Math.min(this.directBuffer.remaining(), length); this.directBuffer.get(buffer, offset, n); offset += n; length -= n; }
/*     */        } public boolean isMutatedByChildren() { return true; } public void close() throws IOException { Unsafe9R.invokeCleaner(this.directBuffer); this.fileChannel.close(); }
/*     */   } private static class ByteBufferSource extends AbstractSource
/*     */   {
/* 203 */     private ByteBufferSource(ByteBuffer buffer, int bufferSize) { super(bufferSize);
/* 204 */       this.buffer = buffer; }
/*     */     
/*     */     final ByteBuffer buffer;
/*     */     
/*     */     public AbstractSource order(ByteOrder byteOrder) {
/* 209 */       super.order(byteOrder);
/* 210 */       this.buffer.order(byteOrder);
/* 211 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public ByteOrder order() {
/* 216 */       return this.buffer.order();
/*     */     }
/*     */ 
/*     */     
/*     */     public byte readByte() throws IOException {
/*     */       try {
/* 222 */         return this.buffer.get();
/* 223 */       } catch (BufferUnderflowException underflow) {
/* 224 */         throw new EOFException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public short readShort() throws IOException {
/*     */       try {
/* 231 */         return this.buffer.getShort();
/* 232 */       } catch (BufferUnderflowException underflow) {
/* 233 */         throw new EOFException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int readInt() throws IOException {
/*     */       try {
/* 240 */         return this.buffer.getInt();
/* 241 */       } catch (BufferUnderflowException underflow) {
/* 242 */         throw new EOFException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public long readLong() throws IOException {
/*     */       try {
/* 249 */         return this.buffer.getLong();
/* 250 */       } catch (BufferUnderflowException underflow) {
/* 251 */         throw new EOFException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public float readFloat() throws IOException {
/*     */       try {
/* 258 */         return this.buffer.getFloat();
/* 259 */       } catch (BufferUnderflowException underflow) {
/* 260 */         throw new EOFException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public double readDouble() throws IOException {
/*     */       try {
/* 267 */         return this.buffer.getDouble();
/* 268 */       } catch (BufferUnderflowException underflow) {
/* 269 */         throw new EOFException();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream readBytesAsStream(long numBytes) throws IOException {
/* 275 */       if (numBytes > this.buffer.remaining()) {
/* 276 */         throw new EOFException();
/*     */       }
/*     */       
/* 279 */       ByteBuffer slice = this.buffer.asReadOnlyBuffer();
/* 280 */       slice.limit(this.buffer.position() + (int)numBytes);
/*     */ 
/*     */       
/* 283 */       this.buffer.position(this.buffer.position() + (int)numBytes);
/* 284 */       return new Sources.ByteBufferInputStream(slice, null);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */ 
/*     */     
/*     */     public boolean isMutatedByChildren() {
/* 293 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void readByteBuffer(ByteBuffer dst) throws IOException {
/* 298 */       if (dst.remaining() > this.buffer.remaining()) {
/* 299 */         throw new EOFException();
/*     */       }
/*     */       
/* 302 */       int limit = this.buffer.limit();
/*     */       try {
/* 304 */         this.buffer.limit(this.buffer.position() + dst.remaining());
/* 305 */         dst.put(this.buffer);
/*     */       } finally {
/* 307 */         this.buffer.limit(limit);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void readBytes(byte[] bytes, int offset, int length) throws IOException {
/* 313 */       if (length > this.buffer.remaining())
/* 314 */         throw new EOFException(); 
/* 315 */       this.buffer.get(bytes, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public long getPosition() {
/* 320 */       return this.buffer.position();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ByteBufferInputStream
/*     */     extends InputStream {
/*     */     private final ByteBuffer buffer;
/*     */     
/*     */     private ByteBufferInputStream(ByteBuffer buffer) {
/* 329 */       this.buffer = buffer;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() {
/* 334 */       if (this.buffer.remaining() > 0)
/* 335 */         return this.buffer.get() & 0xFF; 
/* 336 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int len) {
/* 341 */       len = Math.min(len, this.buffer.remaining());
/* 342 */       if (len > 0) {
/* 343 */         this.buffer.get(b, off, len);
/* 344 */         return len;
/*     */       } 
/* 346 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 351 */       super.close();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class InputStreamSource extends AbstractSource {
/*     */     long position;
/*     */     final InputStream input;
/*     */     
/*     */     InputStreamSource(InputStream input, int bufferSize) {
/* 360 */       super(bufferSize);
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
/* 398 */       this.position = 0L;
/*     */       this.input = input;
/*     */     }
/*     */     public long getPosition() {
/*     */       return this.position;
/*     */     }
/*     */     public void readBytes(byte[] bytes, int offset, int length) throws IOException {
/*     */       int n = 0;
/*     */       while (n < length) {
/*     */         int count = this.input.read(bytes, offset + n, length - n);
/*     */         if (count < 0) {
/*     */           String format = "Reached end of stream after reading %d bytes. Expected %d bytes.";
/*     */           throw new EOFException(String.format(format, new Object[] { Integer.valueOf(n), Integer.valueOf(length) }));
/*     */         } 
/*     */         n += count;
/*     */       } 
/*     */       this.position += length;
/*     */     }
/*     */     public boolean isMutatedByChildren() {
/*     */       return true;
/*     */     }
/*     */     protected InputStream readBytesAsStream(long numBytes) {
/*     */       return new Sources.SourceInputStream(this, numBytes, null);
/*     */     }
/*     */     public void close() throws IOException {
/*     */       this.input.close();
/*     */     } }
/*     */   private static class SourceInputStream extends InputStream { long position; final Source matInput; final long maxLength;
/*     */     private SourceInputStream(Source matInput, long maxLength) {
/* 427 */       this.position = 0L;
/*     */       this.matInput = matInput;
/*     */       this.maxLength = maxLength;
/*     */     }
/*     */     
/*     */     public int read(byte[] b, int off, int len) throws IOException {
/*     */       int remaining = (int)(this.maxLength - this.position);
/*     */       if (remaining == 0)
/*     */         return -1; 
/*     */       len = Math.min(len, remaining);
/*     */       this.matInput.readBytes(b, off, len);
/*     */       this.position += len;
/*     */       return len;
/*     */     }
/*     */     
/*     */     public int read() {
/*     */       throw new IllegalStateException("not implemented");
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Sources.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */