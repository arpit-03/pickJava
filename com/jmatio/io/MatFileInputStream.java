/*     */ package com.jmatio.io;
/*     */ 
/*     */ import com.jmatio.common.MatDataTypes;
/*     */ import com.jmatio.types.ByteStorageSupport;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ class MatFileInputStream
/*     */ {
/*     */   private final int type;
/*     */   private final ByteBuffer buf;
/*     */   
/*     */   public MatFileInputStream(ByteBuffer buf, int type) {
/*  28 */     this.type = type;
/*  29 */     this.buf = buf;
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
/*     */   public int readInt() throws IOException {
/*  41 */     switch (this.type) {
/*     */       
/*     */       case 2:
/*  44 */         return this.buf.get() & 0xFF;
/*     */       case 1:
/*  46 */         return this.buf.get();
/*     */       case 4:
/*  48 */         return this.buf.getShort() & 0xFFFF;
/*     */       case 3:
/*  50 */         return this.buf.getShort();
/*     */       case 6:
/*  52 */         return this.buf.getInt() & 0xFFFFFFFF;
/*     */       case 5:
/*  54 */         return this.buf.getInt();
/*     */       case 13:
/*  56 */         return (int)this.buf.getLong();
/*     */       case 12:
/*  58 */         return (int)this.buf.getLong();
/*     */       case 9:
/*  60 */         return (int)this.buf.getDouble();
/*     */     } 
/*  62 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
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
/*     */   public char readChar() throws IOException {
/*  74 */     switch (this.type) {
/*     */       
/*     */       case 2:
/*  77 */         return (char)(this.buf.get() & 0xFF);
/*     */       case 1:
/*  79 */         return (char)this.buf.get();
/*     */       case 4:
/*  81 */         return (char)(this.buf.getShort() & 0xFFFF);
/*     */       case 3:
/*  83 */         return (char)this.buf.getShort();
/*     */       case 6:
/*  85 */         return (char)(this.buf.getInt() & 0xFFFFFFFF);
/*     */       case 5:
/*  87 */         return (char)this.buf.getInt();
/*     */       case 9:
/*  89 */         return (char)(int)this.buf.getDouble();
/*     */       case 16:
/*  91 */         return (char)this.buf.get();
/*     */     } 
/*  93 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
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
/*     */   public double readDouble() throws IOException {
/* 105 */     switch (this.type) {
/*     */       
/*     */       case 2:
/* 108 */         return (this.buf.get() & 0xFF);
/*     */       case 1:
/* 110 */         return this.buf.get();
/*     */       case 4:
/* 112 */         return (this.buf.getShort() & 0xFFFF);
/*     */       case 3:
/* 114 */         return this.buf.getShort();
/*     */       case 6:
/* 116 */         return (this.buf.getInt() & 0xFFFFFFFF);
/*     */       case 5:
/* 118 */         return this.buf.getInt();
/*     */       case 9:
/* 120 */         return this.buf.getDouble();
/*     */     } 
/* 122 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 128 */     switch (this.type) {
/*     */       
/*     */       case 2:
/* 131 */         return (byte)(this.buf.get() & 0xFF);
/*     */       case 1:
/* 133 */         return this.buf.get();
/*     */       case 4:
/* 135 */         return (byte)(this.buf.getShort() & 0xFFFF);
/*     */       case 3:
/* 137 */         return (byte)this.buf.getShort();
/*     */       case 6:
/* 139 */         return (byte)(this.buf.getInt() & 0xFFFFFFFF);
/*     */       case 5:
/* 141 */         return (byte)this.buf.getInt();
/*     */       case 9:
/* 143 */         return (byte)(int)this.buf.getDouble();
/*     */       case 16:
/* 145 */         return this.buf.get();
/*     */     } 
/* 147 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer readToByteBuffer(ByteBuffer dest, int elements, ByteStorageSupport<?> storage) throws IOException {
/* 170 */     int bytesAllocated = storage.getBytesAllocated();
/* 171 */     int size = elements * storage.getBytesAllocated();
/*     */ 
/*     */     
/* 174 */     if (MatDataTypes.sizeOf(this.type) == bytesAllocated && this.buf.order().equals(dest.order())) {
/*     */       
/* 176 */       int bufMaxSize = 1024;
/* 177 */       int bufSize = Math.min(this.buf.remaining(), bufMaxSize);
/* 178 */       int bufPos = this.buf.position();
/*     */       
/* 180 */       byte[] tmp = new byte[bufSize];
/*     */       
/* 182 */       while (dest.remaining() > 0) {
/*     */         
/* 184 */         int length = Math.min(dest.remaining(), tmp.length);
/* 185 */         this.buf.get(tmp, 0, length);
/* 186 */         dest.put(tmp, 0, length);
/*     */       } 
/* 188 */       this.buf.position(bufPos + size);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 194 */       Class<?> clazz = storage.getStorageClazz();
/* 195 */       while (dest.remaining() > 0) {
/*     */         
/* 197 */         if (clazz.equals(Double.class)) {
/*     */           
/* 199 */           dest.putDouble(readDouble()); continue;
/*     */         } 
/* 201 */         if (clazz.equals(Byte.class)) {
/*     */           
/* 203 */           dest.put(readByte()); continue;
/*     */         } 
/* 205 */         if (clazz.equals(Integer.class)) {
/*     */           
/* 207 */           dest.putInt(readInt()); continue;
/*     */         } 
/* 209 */         if (clazz.equals(Long.class)) {
/*     */           
/* 211 */           dest.putLong(readLong()); continue;
/*     */         } 
/* 213 */         if (clazz.equals(Float.class)) {
/*     */           
/* 215 */           dest.putFloat(readFloat()); continue;
/*     */         } 
/* 217 */         if (clazz.equals(Short.class)) {
/*     */           
/* 219 */           dest.putShort(readShort());
/*     */           
/*     */           continue;
/*     */         } 
/* 223 */         throw new RuntimeException("Not supported buffer reader for " + clazz);
/*     */       } 
/*     */     } 
/*     */     
/* 227 */     dest.rewind();
/* 228 */     return dest;
/*     */   }
/*     */ 
/*     */   
/*     */   private float readFloat() {
/* 233 */     switch (this.type) {
/*     */       
/*     */       case 2:
/* 236 */         return (this.buf.get() & 0xFF);
/*     */       case 1:
/* 238 */         return this.buf.get();
/*     */       case 4:
/* 240 */         return (this.buf.getShort() & 0xFFFF);
/*     */       case 3:
/* 242 */         return this.buf.getShort();
/*     */       case 6:
/* 244 */         return (this.buf.getInt() & 0xFFFFFFFF);
/*     */       case 5:
/* 246 */         return this.buf.getInt();
/*     */       case 7:
/* 248 */         return this.buf.getFloat();
/*     */       case 9:
/* 250 */         return (float)this.buf.getDouble();
/*     */     } 
/* 252 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   private short readShort() {
/* 257 */     switch (this.type) {
/*     */       
/*     */       case 2:
/* 260 */         return (short)(this.buf.get() & 0xFF);
/*     */       case 1:
/* 262 */         return (short)this.buf.get();
/*     */       case 4:
/* 264 */         return (short)(this.buf.getShort() & 0xFFFF);
/*     */       case 3:
/* 266 */         return this.buf.getShort();
/*     */       case 6:
/* 268 */         return (short)(this.buf.getInt() & 0xFFFFFFFF);
/*     */       case 5:
/* 270 */         return (short)this.buf.getInt();
/*     */       case 13:
/* 272 */         return (short)(int)this.buf.getLong();
/*     */       case 12:
/* 274 */         return (short)(int)this.buf.getLong();
/*     */       case 9:
/* 276 */         return (short)(int)this.buf.getDouble();
/*     */     } 
/* 278 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   private long readLong() {
/* 283 */     switch (this.type) {
/*     */       
/*     */       case 2:
/* 286 */         return (this.buf.get() & 0xFF);
/*     */       case 1:
/* 288 */         return this.buf.get();
/*     */       case 4:
/* 290 */         return (this.buf.getShort() & 0xFFFF);
/*     */       case 3:
/* 292 */         return this.buf.getShort();
/*     */       case 6:
/* 294 */         return (this.buf.getInt() & 0xFFFFFFFF);
/*     */       case 5:
/* 296 */         return this.buf.getInt();
/*     */       case 13:
/* 298 */         return this.buf.getLong();
/*     */       case 12:
/* 300 */         return this.buf.getLong();
/*     */       case 9:
/* 302 */         return (long)this.buf.getDouble();
/*     */     } 
/* 304 */     throw new IllegalArgumentException("Unknown data type: " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void skip(int padding) {
/* 310 */     this.buf.position(this.buf.position() + padding);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatFileInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */