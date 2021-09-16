/*     */ package com.jmatio.io;
/*     */ 
/*     */ import com.jmatio.types.MLArray;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.FileChannel;
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
/*     */ class FileBufferedDataOutputStream
/*     */   extends OutputStream
/*     */   implements DataOutputStream
/*     */ {
/*     */   private static final int BUFFER_SIZE = 1024;
/*     */   private ByteBuffer buf;
/*     */   private final FileChannel rwChannel;
/*     */   private final RandomAccessFile raFile;
/*     */   private final File file;
/*     */   
/*     */   public FileBufferedDataOutputStream() throws IOException {
/*  33 */     this.file = File.createTempFile("jmatio-", null);
/*  34 */     this.file.deleteOnExit();
/*  35 */     this.raFile = new RandomAccessFile(this.file, "rw");
/*  36 */     this.rwChannel = this.raFile.getChannel();
/*  37 */     this.buf = ByteBuffer.allocate(1024);
/*     */   }
/*     */ 
/*     */   
/*     */   public FileBufferedDataOutputStream(MLArray array) throws IOException {
/*  42 */     this.file = File.createTempFile("jmatio-" + array.getName() + "-", null);
/*  43 */     this.file.deleteOnExit();
/*  44 */     this.raFile = new RandomAccessFile(this.file, "rw");
/*  45 */     this.rwChannel = this.raFile.getChannel();
/*  46 */     this.buf = ByteBuffer.allocate(1024);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  52 */     if (this.buf.position() >= this.buf.capacity())
/*     */     {
/*  54 */       flush();
/*     */     }
/*     */     
/*  57 */     this.buf.put((byte)(b & 0xFF));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  66 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  75 */     int wbytes = len;
/*  76 */     int offset = off;
/*     */     
/*  78 */     while (wbytes > 0) {
/*     */       
/*  80 */       if (this.buf.position() >= this.buf.capacity())
/*     */       {
/*  82 */         flush();
/*     */       }
/*     */       
/*  85 */       int length = Math.min(wbytes, this.buf.limit() - this.buf.position());
/*     */       
/*  87 */       this.buf.put(b, offset, length);
/*     */       
/*  89 */       offset += length;
/*  90 */       wbytes -= length;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 100 */     flush();
/*     */     
/* 102 */     this.buf = null;
/*     */     
/* 104 */     if (this.rwChannel.isOpen())
/*     */     {
/*     */       
/* 107 */       this.rwChannel.close();
/*     */     }
/*     */     
/* 110 */     this.raFile.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 119 */     if (this.buf != null && this.buf.position() > 0) {
/*     */       
/* 121 */       this.buf.flip();
/* 122 */       this.rwChannel.write(this.buf);
/* 123 */       this.buf.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() throws IOException {
/* 132 */     flush();
/*     */     
/* 134 */     return (int)this.file.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer getByteBuffer() throws IOException {
/* 142 */     return this.rwChannel.map(FileChannel.MapMode.READ_ONLY, 0L, this.file.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(ByteBuffer byteBuffer) throws IOException {
/* 150 */     byte[] tmp = new byte[1024];
/*     */     
/* 152 */     while (byteBuffer.hasRemaining()) {
/*     */       
/* 154 */       int length = Math.min(byteBuffer.remaining(), tmp.length);
/* 155 */       byteBuffer.get(tmp, 0, length);
/* 156 */       write(tmp, 0, length);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/FileBufferedDataOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */