/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mat5Tag
/*     */ {
/*     */   private final Mat5Type type;
/*     */   private final int numBytes;
/*     */   private final boolean packed;
/*     */   private final Source source;
/*     */   
/*     */   public Mat5Type getType() {
/*  60 */     return this.type;
/*     */   }
/*     */   
/*     */   public int getNumBytes() {
/*  64 */     return this.numBytes;
/*     */   }
/*     */   
/*     */   public int getNumElements() {
/*  68 */     return this.numBytes / this.type.bytes();
/*     */   }
/*     */   
/*     */   public int getPadding() {
/*  72 */     return this.type.getPadding(this.numBytes, this.packed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mat5Tag readTagOrNull(Source source) throws IOException {
/*     */     try {
/*  80 */       return readTag(source);
/*  81 */     } catch (EOFException eof) {
/*  82 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Mat5Tag readTag(Source source) throws IOException {
/*     */     Mat5Type type;
/*  90 */     int numBytes, tmp = source.readInt();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     boolean packed = (tmp >> 16 != 0);
/*  96 */     if (!packed) {
/*     */       
/*  98 */       type = Mat5Type.fromId(tmp);
/*  99 */       numBytes = source.readInt();
/*     */     } else {
/*     */       
/* 102 */       numBytes = tmp >> 16;
/* 103 */       type = Mat5Type.fromId(tmp & 0xFFFF);
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if (numBytes % type.bytes() != 0)
/* 108 */       throw Mat5Reader.readError("Found invalid number of bytes for tag '%s'. Expected multiple of %d. Found %d", new Object[] {
/* 109 */             type, Integer.valueOf(type.bytes()), Integer.valueOf(numBytes)
/*     */           }); 
/* 111 */     return new Mat5Tag(type, numBytes, packed, source);
/*     */   }
/*     */   
/*     */   byte[] readAsBytes() throws IOException {
/* 115 */     byte[] buffer = new byte[getNumBytes()];
/* 116 */     this.source.readBytes(buffer, 0, buffer.length);
/* 117 */     this.source.skip(getPadding());
/* 118 */     return buffer;
/*     */   }
/*     */   
/*     */   short[] readAsShorts() throws IOException {
/* 122 */     checkMultipleOf(2, "short[]");
/* 123 */     short[] buffer = new short[getNumBytes() / 2];
/* 124 */     this.source.readShorts(buffer, 0, buffer.length);
/* 125 */     this.source.skip(getPadding());
/* 126 */     return buffer;
/*     */   }
/*     */   
/*     */   int[] readAsInts() throws IOException {
/* 130 */     checkMultipleOf(4, "int[]");
/* 131 */     int[] buffer = new int[getNumBytes() / 4];
/* 132 */     this.source.readInts(buffer, 0, buffer.length);
/* 133 */     this.source.skip(getPadding());
/* 134 */     return buffer;
/*     */   }
/*     */   
/*     */   long[] readAsLongs() throws IOException {
/* 138 */     checkMultipleOf(8, "long[]");
/* 139 */     long[] buffer = new long[getNumBytes() / 8];
/* 140 */     this.source.readLongs(buffer, 0, buffer.length);
/* 141 */     this.source.skip(getPadding());
/* 142 */     return buffer;
/*     */   }
/*     */   
/*     */   float[] readAsFloats() throws IOException {
/* 146 */     checkMultipleOf(4, "float[]");
/* 147 */     float[] buffer = new float[getNumBytes() / 4];
/* 148 */     this.source.readFloats(buffer, 0, buffer.length);
/* 149 */     this.source.skip(getPadding());
/* 150 */     return buffer;
/*     */   }
/*     */   
/*     */   double[] readAsDoubles() throws IOException {
/* 154 */     checkMultipleOf(8, "double[]");
/* 155 */     double[] buffer = new double[getNumBytes() / 8];
/* 156 */     this.source.readDoubles(buffer, 0, buffer.length);
/* 157 */     this.source.skip(getPadding());
/* 158 */     return buffer;
/*     */   }
/*     */   
/*     */   private void checkMultipleOf(int bytes, String target) throws IOException {
/* 162 */     if (getNumBytes() % bytes != 0) {
/* 163 */       throw Mat5Reader.readError("Tag with %d bytes cannot be read as %s", new Object[] { Integer.valueOf(bytes), target });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 170 */     return "Mat5Tag{type=" + 
/* 171 */       this.type + 
/* 172 */       ", numBytes=" + this.numBytes + (
/* 173 */       this.packed ? " (packed)" : "") + 
/* 174 */       '}';
/*     */   }
/*     */   
/*     */   private Mat5Tag(Mat5Type type, int numBytes, boolean packed, Source source) {
/* 178 */     this.type = type;
/* 179 */     this.numBytes = numBytes;
/* 180 */     this.packed = packed;
/* 181 */     this.source = source;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5Tag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */