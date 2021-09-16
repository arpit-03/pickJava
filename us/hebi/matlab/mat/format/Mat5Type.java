/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.util.Casts;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Mat5Type
/*     */ {
/*  42 */   Int8(1),
/*  43 */   UInt8(2),
/*  44 */   Int16(3),
/*  45 */   UInt16(4),
/*  46 */   Int32(5),
/*  47 */   UInt32(6),
/*  48 */   Single(7),
/*  49 */   Double(
/*  50 */     9),
/*  51 */   Int64(
/*     */     
/*  53 */     12),
/*  54 */   UInt64(13),
/*  55 */   Matrix(14),
/*  56 */   Compressed(15),
/*  57 */   Utf8(16),
/*  58 */   Utf16(17),
/*  59 */   Utf32(18); private final int id;
/*     */   
/*     */   void writeByteBufferWithTag(ByteBuffer buffer, Sink sink) throws IOException {
/*  62 */     if (bytes() > 1 && buffer.order() != sink.order()) {
/*  63 */       throw new IllegalArgumentException("Buffer order does not match sink order");
/*     */     }
/*  65 */     int position = buffer.position();
/*  66 */     int numElements = buffer.remaining() / bytes();
/*  67 */     writeTag(numElements, sink);
/*  68 */     sink.writeByteBuffer(buffer);
/*  69 */     writePadding(numElements, sink);
/*  70 */     buffer.position(position);
/*     */   }
/*     */   private static final Mat5Type[] lookup; private static final byte[] paddingBuffer;
/*     */   void writeBytesWithTag(byte[] values, Sink sink) throws IOException {
/*  74 */     if (bytes() != 1) {
/*  75 */       throw new IllegalArgumentException("Not a byte tag type");
/*     */     }
/*  77 */     writeTag(values.length, sink);
/*  78 */     sink.writeBytes(values, 0, values.length);
/*  79 */     writePadding(values.length, sink);
/*     */   }
/*     */   
/*     */   void writeIntsWithTag(int[] values, Sink sink) throws IOException {
/*  83 */     if (this != Int32 && this != UInt32) {
/*  84 */       throw new IllegalArgumentException("Not an integer tag type");
/*     */     }
/*  86 */     writeTag(values.length, sink);
/*  87 */     sink.writeInts(values, 0, values.length);
/*  88 */     writePadding(values.length, sink);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int computeSerializedSize(int numElements) {
/*  95 */     int numBytes = getNumBytes(numElements);
/*  96 */     boolean packed = isPackable(numBytes);
/*  97 */     int tagSize = packed ? 4 : 8;
/*  98 */     int padding = getPadding(numBytes, packed);
/*  99 */     return tagSize + numBytes + padding;
/*     */   }
/*     */   
/*     */   public void writeTag(int numElements, boolean allowPacking, Sink sink) throws IOException {
/* 103 */     int numBytes = getNumBytes(numElements);
/* 104 */     if (allowPacking && isPackable(numBytes)) {
/* 105 */       sink.writeInt(numBytes << 16 | id());
/*     */     } else {
/* 107 */       sink.writeInt(id());
/* 108 */       sink.writeInt(numBytes);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTag(int numElements, Sink sink) throws IOException {
/* 113 */     writeTag(numElements, true, sink);
/*     */   }
/*     */   
/*     */   public void writePadding(int numElements, Sink sink) throws IOException {
/* 117 */     int numBytes = getNumBytes(numElements);
/* 118 */     int padding = getPadding(numBytes, isPackable(numBytes));
/* 119 */     if (padding == 0)
/* 120 */       return;  sink.writeBytes(paddingBuffer, 0, padding);
/*     */   }
/*     */   
/*     */   int getPadding(int numBytes, boolean packed) {
/* 124 */     switch (this) {
/*     */       case Matrix:
/*     */       case null:
/* 127 */         return 0;
/*     */     } 
/* 129 */     int tagSize = packed ? 4 : 8;
/* 130 */     int padding = (tagSize + numBytes) % 8;
/* 131 */     return (padding == 0) ? 0 : (8 - padding);
/*     */   }
/*     */ 
/*     */   
/*     */   private int getNumBytes(long numElements) {
/* 136 */     return Casts.sint32(bytes() * numElements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPackable(int numBytes) {
/* 145 */     return (numBytes <= 4);
/*     */   }
/*     */   
/*     */   public int bytes() {
/* 149 */     switch (this) {
/*     */       case Int8:
/*     */       case UInt8:
/* 152 */         return 1;
/*     */       case Int16:
/*     */       case UInt16:
/* 155 */         return 2;
/*     */       case Int32:
/*     */       case UInt32:
/*     */       case Single:
/* 159 */         return 4;
/*     */       case Double:
/*     */       case Int64:
/*     */       case UInt64:
/* 163 */         return 8;
/*     */     } 
/* 165 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mat5Type fromNumericalType(MatlabType type) {
/* 170 */     switch (type) {
/*     */       case Int8:
/* 172 */         return Int8;
/*     */       case Int16:
/* 174 */         return Int16;
/*     */       case Int32:
/* 176 */         return Int32;
/*     */       case Int64:
/* 178 */         return Int64;
/*     */       case UInt8:
/* 180 */         return UInt8;
/*     */       case UInt16:
/* 182 */         return UInt16;
/*     */       case UInt32:
/* 184 */         return UInt32;
/*     */       case UInt64:
/* 186 */         return UInt64;
/*     */       case Single:
/* 188 */         return Single;
/*     */       case Double:
/* 190 */         return Double;
/*     */     } 
/* 192 */     throw new IllegalArgumentException("Not a numerical type: " + type);
/*     */   }
/*     */ 
/*     */   
/*     */   public CharEncoding getCharEncoding() {
/* 197 */     switch (this) {
/*     */       case Utf8:
/* 199 */         return CharEncoding.Utf8;
/*     */       case Utf16:
/* 201 */         return CharEncoding.Utf16;
/*     */       case Utf32:
/* 203 */         return CharEncoding.Utf32;
/*     */       case UInt16:
/* 205 */         return CharEncoding.UInt16;
/*     */     } 
/* 207 */     throw new IllegalArgumentException("Not a character type: " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mat5Type fromCharEncoding(CharEncoding type) {
/* 212 */     switch (type) {
/*     */       case Utf8:
/* 214 */         return Utf8;
/*     */       case Utf16:
/* 216 */         return Utf16;
/*     */       case Utf32:
/* 218 */         return Utf32;
/*     */       case null:
/* 220 */         return UInt16;
/*     */     } 
/* 222 */     throw new IllegalArgumentException("Unknown char type " + type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mat5Type fromId(int id) {
/* 227 */     if (id > 0 && id < lookup.length) {
/* 228 */       Mat5Type type = lookup[id];
/* 229 */       if (type != null)
/* 230 */         return type; 
/*     */     } 
/* 232 */     throw new IllegalArgumentException("Unknown tag type for id: " + id);
/*     */   }
/*     */   
/*     */   public int id() {
/* 236 */     return this.id;
/*     */   }
/*     */   
/*     */   Mat5Type(int id) {
/* 240 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 250 */     int highestId = 0; byte b; int i; Mat5Type[] arrayOfMat5Type;
/* 251 */     for (i = (arrayOfMat5Type = values()).length, b = 0; b < i; ) { Mat5Type type = arrayOfMat5Type[b];
/* 252 */       highestId = Math.max(highestId, type.id);
/*     */       
/*     */       b++; }
/*     */     
/* 256 */     lookup = new Mat5Type[highestId + 1];
/* 257 */     for (i = (arrayOfMat5Type = values()).length, b = 0; b < i; ) { Mat5Type type = arrayOfMat5Type[b];
/* 258 */       lookup[type.id()] = type;
/*     */       
/*     */       b++; }
/*     */     
/* 262 */     paddingBuffer = new byte[8];
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */