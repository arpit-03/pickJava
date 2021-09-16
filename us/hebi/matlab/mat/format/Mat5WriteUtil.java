/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.zip.Deflater;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Opaque;
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
/*     */ public class Mat5WriteUtil
/*     */ {
/*     */   private static final int NAME_LENGTH_MAX = 63;
/*     */   private static final int DUMMY_SIZE = 0;
/*     */   
/*     */   public static void writeNestedArray(Array array, Sink sink) throws IOException {
/*  48 */     writeArray("", false, array, sink);
/*     */   }
/*     */   
/*     */   public static void writeArray(String name, boolean isGlobal, Array array, Sink sink) throws IOException {
/*  52 */     if (array instanceof Mat5Serializable) {
/*  53 */       ((Mat5Serializable)array).writeMat5(name, isGlobal, sink);
/*     */       return;
/*     */     } 
/*  56 */     throw new IllegalArgumentException("Array does not support the MAT5 format");
/*     */   }
/*     */   
/*     */   public static int computeArraySize(Array array) {
/*  60 */     return computeArraySize("", array);
/*     */   }
/*     */   
/*     */   public static int computeArraySize(String name, Array array) {
/*  64 */     if (array instanceof Mat5Serializable)
/*  65 */       return ((Mat5Serializable)array).getMat5Size(name); 
/*  66 */     throw new IllegalArgumentException("Array does not support the MAT5 format");
/*     */   }
/*     */   
/*     */   public static int computeArrayHeaderSize(String name, Array array) {
/*  70 */     int arrayFlags = Mat5Type.UInt32.computeSerializedSize(2);
/*  71 */     int dimensions = Mat5Type.Int32.computeSerializedSize(array.getNumDimensions());
/*  72 */     int nameLen = Mat5Type.Int8.computeSerializedSize(getLimitedNameLength(name));
/*  73 */     return arrayFlags + dimensions + nameLen;
/*     */   }
/*     */   
/*     */   public static void writeMatrixTag(String name, Mat5Serializable array, Sink sink) throws IOException {
/*  77 */     Mat5Type.Matrix.writeTag(array.getMat5Size(name) - 8, sink);
/*     */   }
/*     */   
/*     */   public static void writeArrayHeader(String name, boolean isGlobal, Array array, Sink sink) throws IOException {
/*  81 */     if (array.getType() == MatlabType.Opaque) {
/*  82 */       throw new IllegalArgumentException("Opaque types do not share the same format as other types");
/*     */     }
/*     */     
/*  85 */     Mat5Type.UInt32.writeIntsWithTag(Mat5ArrayFlags.forArray(isGlobal, array), sink);
/*     */ 
/*     */     
/*  88 */     Mat5Type.Int32.writeIntsWithTag(array.getDimensions(), sink);
/*     */ 
/*     */     
/*  91 */     Mat5Type.Int8.writeBytesWithTag(getLimitedName(name).getBytes(Charsets.US_ASCII), sink);
/*     */   }
/*     */   
/*     */   public static int computeOpaqueSize(String name, Opaque array) {
/*  95 */     return 8 + 
/*  96 */       Mat5Type.UInt32.computeSerializedSize(2) + 
/*  97 */       Mat5Type.Int8.computeSerializedSize(name.length()) + 
/*  98 */       Mat5Type.Int8.computeSerializedSize(array.getObjectType().length()) + 
/*  99 */       Mat5Type.Int8.computeSerializedSize(array.getClassName().length()) + 
/* 100 */       computeArraySize(array.getContent());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeOpaque(String name, boolean global, Opaque opaque, Sink sink) throws IOException {
/* 105 */     int numBytes = computeOpaqueSize(name, opaque) - 8;
/* 106 */     Mat5Type.Matrix.writeTag(numBytes, sink);
/*     */ 
/*     */     
/* 109 */     Mat5Type.UInt32.writeIntsWithTag(Mat5ArrayFlags.forOpaque(global, opaque), sink);
/*     */ 
/*     */     
/* 112 */     Mat5Type.Int8.writeBytesWithTag(name.getBytes(Charsets.US_ASCII), sink);
/*     */ 
/*     */     
/* 115 */     Mat5Type.Int8.writeBytesWithTag(opaque.getObjectType().getBytes(Charsets.US_ASCII), sink);
/*     */ 
/*     */     
/* 118 */     Mat5Type.Int8.writeBytesWithTag(opaque.getClassName().getBytes(Charsets.US_ASCII), sink);
/*     */ 
/*     */     
/* 121 */     writeNestedArray(opaque.getContent(), sink);
/*     */   }
/*     */   
/*     */   static int computeCharBufferSize(CharEncoding encoding, CharBuffer buffer) {
/* 125 */     Mat5Type tagType = Mat5Type.fromCharEncoding(encoding);
/* 126 */     int numElements = Casts.checkedDivide(encoding.getEncodedLength(buffer), tagType.bytes());
/* 127 */     return tagType.computeSerializedSize(numElements);
/*     */   }
/*     */   
/*     */   static void writeCharBufferWithTag(CharEncoding encoding, CharBuffer buffer, Sink sink) throws IOException {
/* 131 */     Mat5Type tagType = Mat5Type.fromCharEncoding(encoding);
/* 132 */     int numElements = Casts.checkedDivide(encoding.getEncodedLength(buffer), tagType.bytes());
/* 133 */     tagType.writeTag(numElements, sink);
/* 134 */     encoding.writeEncoded(buffer, sink);
/* 135 */     tagType.writePadding(numElements, sink);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void writeArrayDeflated(String name, boolean global, Array array, Sink sink, Deflater deflater) throws IOException {
/* 141 */     long tagPosition = sink.position();
/* 142 */     Mat5Type.Compressed.writeTag(0, false, sink);
/* 143 */     long start = sink.position();
/*     */ 
/*     */     
/* 146 */     Sink compressed = sink.writeDeflated(deflater);
/* 147 */     writeArray(name, global, array, compressed);
/* 148 */     compressed.close();
/*     */ 
/*     */     
/* 151 */     long end = sink.position();
/* 152 */     long compressedSize = end - start;
/*     */ 
/*     */     
/* 155 */     sink.position(tagPosition);
/* 156 */     Mat5Type.Compressed.writeTag(Casts.sint32(compressedSize), false, sink);
/*     */ 
/*     */ 
/*     */     
/* 160 */     sink.position(end);
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
/*     */   static String getLimitedName(String name) {
/* 177 */     if (name.length() <= 63) {
/* 178 */       return name;
/*     */     }
/*     */     
/* 181 */     String truncated = name.substring(0, 63);
/* 182 */     String warning = "Warning: '%s' exceeds MATLAB's maximum name length and will be truncated to '%s'.";
/* 183 */     System.err.println(String.format(warning, new Object[] { name, truncated }));
/* 184 */     return truncated;
/*     */   }
/*     */   
/*     */   static int getLimitedNameLength(String name) {
/* 188 */     if (name == null) return 0; 
/* 189 */     return Math.min(63, name.length());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5WriteUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */