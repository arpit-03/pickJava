/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.util.Bytes;
/*     */ import us.hebi.matlab.mat.util.Casts;
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
/*     */ class UniversalNumberStore
/*     */   implements NumberStore
/*     */ {
/*     */   final Mat5Type type;
/*     */   private final int numElements;
/*     */   ByteBuffer buffer;
/*     */   private BufferAllocator bufferAllocator;
/*     */   
/*     */   UniversalNumberStore(Mat5Type type, ByteBuffer buffer, BufferAllocator bufferAllocator) {
/*  64 */     this.type = type;
/*  65 */     this.buffer = (ByteBuffer)Preconditions.checkNotNull(buffer);
/*  66 */     this.bufferAllocator = (BufferAllocator)Preconditions.checkNotNull(bufferAllocator);
/*  67 */     this.numElements = buffer.remaining() / type.bytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/*  72 */     return this.numElements;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/*  77 */     switch (this.type) {
/*     */       case Single:
/*  79 */         return this.buffer.getFloat(getOffset(index));
/*     */       case Double:
/*  81 */         return this.buffer.getDouble(getOffset(index));
/*     */     } 
/*  83 */     return getLong(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/*  89 */     switch (this.type) {
/*     */       case Int8:
/*  91 */         return this.buffer.get(getOffset(index));
/*     */       case Int16:
/*  93 */         return this.buffer.getShort(getOffset(index));
/*     */       case Int32:
/*  95 */         return this.buffer.getInt(getOffset(index));
/*     */       case Int64:
/*  97 */         return this.buffer.getLong(getOffset(index));
/*     */       case UInt8:
/*  99 */         return Casts.uint8(this.buffer.get(getOffset(index)));
/*     */       case UInt16:
/* 101 */         return Casts.uint16(this.buffer.getShort(getOffset(index)));
/*     */       case UInt32:
/* 103 */         return Casts.uint32(this.buffer.getInt(getOffset(index)));
/*     */       case UInt64:
/* 105 */         return this.buffer.getLong(getOffset(index));
/*     */       case Single:
/* 107 */         return (long)this.buffer.getFloat(getOffset(index));
/*     */       case Double:
/* 109 */         return (long)this.buffer.getDouble(getOffset(index));
/*     */     } 
/* 111 */     throw new IllegalArgumentException("Not a numerical type " + this.type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(int index, double value) {
/* 117 */     switch (this.type) {
/*     */       case Single:
/* 119 */         this.buffer.putFloat(getOffset(index), (float)value);
/*     */         return;
/*     */       case Double:
/* 122 */         this.buffer.putDouble(getOffset(index), value);
/*     */         return;
/*     */     } 
/* 125 */     checkInputRange(Casts.isInteger(value), value);
/* 126 */     setLong(index, (long)value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(int index, long value) {
/* 133 */     switch (this.type) {
/*     */       case Int8:
/*     */       case UInt8:
/* 136 */         checkInputRange(Casts.fitsByte(value), value);
/* 137 */         this.buffer.put(getOffset(index), (byte)(int)value);
/*     */         return;
/*     */       case Int16:
/*     */       case UInt16:
/* 141 */         checkInputRange(Casts.fitsShort(value), value);
/* 142 */         this.buffer.putShort(getOffset(index), (short)(int)value);
/*     */         return;
/*     */       case Int32:
/*     */       case UInt32:
/* 146 */         checkInputRange(Casts.fitsInt(value), value);
/* 147 */         this.buffer.putInt(getOffset(index), (int)value);
/*     */         return;
/*     */       
/*     */       case Int64:
/*     */       case UInt64:
/* 152 */         this.buffer.putLong(getOffset(index), value);
/*     */         return;
/*     */       case Single:
/* 155 */         this.buffer.putFloat(getOffset(index), (float)value);
/*     */         return;
/*     */       case Double:
/* 158 */         this.buffer.putDouble(getOffset(index), value);
/*     */         return;
/*     */     } 
/* 161 */     throw new IllegalArgumentException("Not a numerical type " + this.type);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkInputRange(boolean state, double value) {
/* 166 */     if (!state) {
/* 167 */       String format = "Internal store type '%s' can not hold input value %f";
/* 168 */       throw new IllegalArgumentException(String.format(format, new Object[] { this.type, Double.valueOf(value) }));
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getOffset(int index) {
/* 173 */     return index * this.type.bytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMat5Size() {
/* 178 */     return this.type.computeSerializedSize(this.numElements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMat5(Sink sink) throws IOException {
/* 188 */     if (this.buffer.order() != sink.order()) {
/* 189 */       Bytes.reverseByteOrder(this.buffer, this.type.bytes());
/*     */     }
/*     */ 
/*     */     
/* 193 */     this.type.writeByteBufferWithTag(this.buffer, sink);
/* 194 */     this.buffer.rewind();
/*     */   }
/*     */ 
/*     */   
/*     */   ByteBuffer getByteBuffer() {
/* 199 */     this.buffer.rewind();
/* 200 */     return this.buffer.slice();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 205 */     if (this.buffer == null) {
/* 206 */       System.err.println("already released!");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 211 */     this.bufferAllocator.release(this.buffer);
/* 212 */     this.buffer = null;
/* 213 */     this.bufferAllocator = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int hashCodeForType(NumberStore store, boolean logical, MatlabType type) {
/* 222 */     if (store == null) {
/* 223 */       return 0;
/*     */     }
/* 225 */     int hash = 1;
/* 226 */     if (logical) {
/* 227 */       for (int i = 0; i < store.getNumElements(); i++) {
/* 228 */         hash = 31 * hash + (Casts.logical(store.getDouble(i)) ? 1 : 0);
/*     */       }
/*     */     }
/* 231 */     else if (type == MatlabType.Single || type == MatlabType.Double) {
/* 232 */       for (int i = 0; i < store.getNumElements(); i++) {
/* 233 */         hash = 31 * hash + Double.hashCode(store.getDouble(i));
/*     */       }
/*     */     } else {
/* 236 */       for (int i = 0; i < store.getNumElements(); i++) {
/* 237 */         hash = 31 * hash + Long.hashCode(store.getLong(i));
/*     */       }
/*     */     } 
/*     */     
/* 241 */     return hash;
/*     */   }
/*     */   
/*     */   static boolean equalForType(NumberStore a, NumberStore b, boolean logical, MatlabType type) {
/* 245 */     if (((a == null) ? true : false) != ((b == null) ? true : false))
/*     */     {
/* 247 */       return false; } 
/* 248 */     if (a == null)
/*     */     {
/* 250 */       return true;
/*     */     }
/* 252 */     if (a instanceof UniversalNumberStore && b instanceof UniversalNumberStore) {
/* 253 */       UniversalNumberStore aCast = (UniversalNumberStore)a;
/* 254 */       UniversalNumberStore bCast = (UniversalNumberStore)b;
/* 255 */       if (aCast.type == bCast.type)
/*     */       {
/* 257 */         return aCast.buffer.equals(bCast.buffer);
/*     */       }
/*     */     } 
/* 260 */     if (logical) {
/*     */       
/* 262 */       for (int i = 0; i < a.getNumElements(); i++) {
/* 263 */         if (Casts.logical(a.getDouble(i)) != Casts.logical(b.getDouble(i))) {
/* 264 */           return false;
/*     */         
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 270 */     else if (type == MatlabType.Single || type == MatlabType.Double) {
/*     */       
/* 272 */       for (int i = 0; i < a.getNumElements(); i++) {
/* 273 */         if (a.getDouble(i) != b.getDouble(i)) {
/* 274 */           return false;
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 279 */       for (int i = 0; i < a.getNumElements(); i++) {
/* 280 */         if (a.getLong(i) != b.getLong(i)) {
/* 281 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 287 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/UniversalNumberStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */