/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.FileChannel;
/*     */ import us.hebi.matlab.mat.util.Casts;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sinks
/*     */ {
/*     */   static final int DISK_IO_BUFFER_SIZE = 32768;
/*     */   private static final int defaultCopyBufferSize = 4096;
/*     */   
/*     */   public static Sink newStreamingFile(File file) throws IOException {
/*  53 */     return newStreamingFile(file, false);
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
/*     */   public static Sink newStreamingFile(File file, boolean append) throws IOException {
/*  66 */     checkOutputNotNull(file);
/*     */ 
/*     */     
/*  69 */     if (!append)
/*  70 */       deleteFileIfExists(file); 
/*  71 */     createParentDirs(file);
/*     */ 
/*     */     
/*  74 */     final FileChannel channel = (new RandomAccessFile(file, "rw")).getChannel();
/*  75 */     final ByteBuffer directBuffer = ByteBuffer.allocateDirect(32768);
/*  76 */     if (append) {
/*  77 */       channel.position(file.length());
/*     */     }
/*     */     
/*  80 */     return new AbstractSink(4096)
/*     */       {
/*     */         public long position() throws IOException {
/*  83 */           return channel.position() + directBuffer.position();
/*     */         }
/*     */ 
/*     */         
/*     */         public void position(long position) throws IOException {
/*  88 */           flush();
/*  89 */           channel.position(position);
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeByteBuffer(ByteBuffer buffer) throws IOException {
/*  94 */           flush();
/*  95 */           channel.write(buffer);
/*     */         }
/*     */ 
/*     */         
/*     */         public void writeBytes(byte[] buffer, int offset, int length) throws IOException {
/* 100 */           while (length > 0) {
/*     */ 
/*     */             
/* 103 */             int n = Math.min(length, directBuffer.remaining());
/* 104 */             directBuffer.put(buffer, offset, n);
/* 105 */             offset += n;
/* 106 */             length -= n;
/*     */ 
/*     */             
/* 109 */             if (directBuffer.remaining() == 0) {
/* 110 */               flush();
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/*     */         private void flush() throws IOException {
/* 116 */           if (directBuffer.position() == 0)
/*     */             return; 
/* 118 */           directBuffer.flip();
/* 119 */           channel.write(directBuffer);
/* 120 */           directBuffer.clear();
/*     */         }
/*     */ 
/*     */         
/*     */         public void close() throws IOException {
/* 125 */           flush();
/* 126 */           channel.close();
/* 127 */           Unsafe9R.invokeCleaner(directBuffer);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Sink newStreamingFile(String file) throws IOException {
/* 134 */     return newStreamingFile(new File(checkOutputNotNull(file)));
/*     */   }
/*     */   
/*     */   public static Sink newStreamingFile(String file, boolean append) throws IOException {
/* 138 */     return newStreamingFile(new File(checkOutputNotNull(file)), append);
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
/*     */   public static Sink newMappedFile(File file, int maxExpectedSize) throws IOException {
/* 152 */     checkOutputNotNull(file);
/* 153 */     deleteFileIfExists(file);
/* 154 */     createParentDirs(file);
/*     */ 
/*     */     
/* 157 */     final FileChannel channel = (new RandomAccessFile(file, "rw")).getChannel();
/* 158 */     final ByteBuffer buffer = channel
/* 159 */       .map(FileChannel.MapMode.READ_WRITE, 0L, maxExpectedSize)
/* 160 */       .load();
/*     */ 
/*     */     
/* 163 */     return new BufferSink(buffer, 4096)
/*     */       {
/*     */         public void close() throws IOException {
/* 166 */           super.close();
/* 167 */           long finalSize = buffer.position();
/* 168 */           Unsafe9R.invokeCleaner(buffer);
/* 169 */           channel.truncate(finalSize);
/* 170 */           channel.close();
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public static Sink wrap(ByteBuffer buffer) {
/* 177 */     return new BufferSink(buffer, Math.min(buffer.remaining(), 4096), null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Sink wrapNonSeeking(OutputStream outputStream) {
/* 188 */     return wrapNonSeeking(outputStream, 4096);
/*     */   }
/*     */   
/*     */   public static Sink wrapNonSeeking(OutputStream outputStream, int copyBufferSize) {
/* 192 */     return new OutputStreamSink(checkOutputNotNull(outputStream), copyBufferSize);
/*     */   }
/*     */   
/*     */   private static <T> T checkOutputNotNull(T output) {
/* 196 */     if (output == null)
/* 197 */       throw new NullPointerException("output must not be null"); 
/* 198 */     return output;
/*     */   }
/*     */   
/*     */   private static void deleteFileIfExists(File file) throws IOException {
/* 202 */     if (((File)Preconditions.checkNotNull(file)).exists() && !file.delete())
/* 203 */       throw new IOException("Failed to overwrite " + file.getAbsolutePath()); 
/*     */   }
/*     */   
/*     */   private static void createParentDirs(File file) throws IOException {
/* 207 */     File parent = file.getCanonicalFile().getParentFile();
/* 208 */     if (parent != null && !parent.mkdirs() && !parent.isDirectory())
/* 209 */       throw new IOException("Unable to create parent directories of " + file); 
/*     */   }
/*     */   
/*     */   private static class OutputStreamSink extends AbstractSink { private long position;
/*     */     final OutputStream output;
/*     */     
/*     */     public long position() throws IOException {
/* 216 */       return this.position;
/*     */     }
/*     */     
/*     */     public void position(long position) throws IOException {
/* 220 */       throw new IllegalStateException("Sink does not implement position seeking");
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeBytes(byte[] buffer, int offset, int length) throws IOException {
/* 225 */       this.output.write(buffer, offset, length);
/* 226 */       this.position += length;
/*     */     } public void close() throws IOException {
/*     */       this.output.close();
/*     */     } protected OutputStreamSink(OutputStream output, int bufferSize) {
/* 230 */       super(bufferSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 239 */       this.position = 0L;
/*     */       this.output = output;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static class BufferSink
/*     */     extends AbstractSink {
/*     */     private BufferSink(ByteBuffer out, int writeBufferSize) {
/* 247 */       super(writeBufferSize);
/* 248 */       this.out = out;
/*     */     }
/*     */     final ByteBuffer out;
/*     */     
/*     */     public ByteOrder order() {
/* 253 */       return this.out.order();
/*     */     }
/*     */ 
/*     */     
/*     */     public AbstractSink order(ByteOrder order) {
/* 258 */       this.out.order(order);
/* 259 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public long position() {
/* 264 */       return this.out.position();
/*     */     }
/*     */ 
/*     */     
/*     */     public void position(long position) {
/* 269 */       this.out.position(Casts.sint32(position));
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeByteBuffer(ByteBuffer src) {
/* 274 */       this.out.put(src);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeBytes(byte[] buffer, int offset, int length) {
/* 279 */       this.out.put(buffer, offset, length);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeByte(byte value) throws IOException {
/* 284 */       this.out.put(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeShort(short value) throws IOException {
/* 289 */       this.out.putShort(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeInt(int value) throws IOException {
/* 294 */       this.out.putInt(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeLong(long value) throws IOException {
/* 299 */       this.out.putLong(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeFloat(float value) throws IOException {
/* 304 */       this.out.putFloat(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeDouble(double value) throws IOException {
/* 309 */       this.out.putDouble(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {}
/*     */   }
/*     */ 
/*     */   
/*     */   static class SinkOutputStream
/*     */     extends OutputStream
/*     */   {
/*     */     private final Sink sink;
/*     */ 
/*     */     
/*     */     public void write(int b) throws IOException {
/* 324 */       this.sink.writeByte((byte)(b & 0xFF));
/*     */     }
/*     */     
/*     */     public void write(byte[] b, int off, int len) throws IOException {
/* 328 */       this.sink.writeBytes(b, off, len);
/*     */     }
/*     */     
/*     */     SinkOutputStream(Sink sink) {
/* 332 */       this.sink = (Sink)Preconditions.checkNotNull(sink);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/Sinks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */