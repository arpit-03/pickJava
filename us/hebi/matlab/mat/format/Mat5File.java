/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import us.hebi.matlab.mat.types.AbstractMatFile;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatFile;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Source;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mat5File
/*     */   extends AbstractMatFile
/*     */ {
/*     */   private final String description;
/*     */   private final long subsysOffset;
/*     */   private final ByteOrder byteOrder;
/*     */   private final short version;
/*     */   private final boolean reduced;
/*     */   private static final String MAT5_IDENTIFIER = "MATLAB 5.0 MAT-file";
/*     */   
/*     */   public static Mat5File readFileHeader(Source source) throws IOException {
/*  77 */     return readFileHeader(source, false);
/*     */   }
/*     */   
/*     */   public static Mat5File readReducedFileHeader(Source source) throws IOException {
/*  81 */     return readFileHeader(source, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Mat5File readFileHeader(Source source, boolean reducedHeader) throws IOException {
/*  88 */     String description = "";
/*  89 */     long subsysOffset = 0L;
/*  90 */     if (!reducedHeader) {
/*     */       
/*  92 */       byte[] buffer = new byte[116];
/*  93 */       source.readBytes(buffer, 0, buffer.length);
/*  94 */       description = CharEncoding.parseAsciiString(buffer, 0, buffer.length);
/*  95 */       checkMat5Identifier(description);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       subsysOffset = source.readLong();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     short version = Short.reverseBytes(source.readShort());
/*     */ 
/*     */     
/* 111 */     ByteOrder order = source.order();
/* 112 */     short endianIndicator = source.readShort();
/* 113 */     switch (endianIndicator) {
/*     */       case 19785:
/*     */         break;
/*     */       case 18765:
/* 117 */         order = Bytes.reverseByteOrder(order);
/* 118 */         subsysOffset = Long.reverseBytes(subsysOffset);
/* 119 */         version = Short.reverseBytes(version);
/*     */         break;
/*     */       default:
/* 122 */         throw new IllegalArgumentException("Invalid endian indicator");
/*     */     } 
/*     */ 
/*     */     
/* 126 */     if (reducedHeader) {
/* 127 */       source.skip(4L);
/*     */     }
/*     */ 
/*     */     
/* 131 */     return new Mat5File(reducedHeader, description, subsysOffset, order, version);
/*     */   }
/*     */   
/*     */   public void writeFileHeader(Sink sink) throws IOException {
/* 135 */     if (!hasReducedHeader()) {
/*     */ 
/*     */       
/* 138 */       checkMat5Identifier(getDescription());
/* 139 */       byte[] bytes = getDescription().getBytes(Charsets.US_ASCII);
/* 140 */       int remaining = 116 - bytes.length;
/* 141 */       sink.writeBytes(bytes, 0, Math.min(116, bytes.length));
/*     */       
/* 143 */       if (remaining > 0) {
/*     */         
/* 145 */         byte[] buffer = new byte[remaining];
/* 146 */         Arrays.fill(buffer, (byte)0);
/* 147 */         sink.writeBytes(buffer, 0, buffer.length);
/* 148 */       } else if (remaining < 0) {
/* 149 */         String msg = "Warning: Description is " + -remaining + " bytes too long and will be concatenated.";
/* 150 */         System.err.println(msg);
/*     */       } 
/*     */ 
/*     */       
/* 154 */       sink.writeLong(getSubsysOffset());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     sink.writeShort(Short.reverseBytes(getVersion()));
/*     */ 
/*     */     
/* 163 */     sink.writeShort((short)19785);
/*     */ 
/*     */     
/* 166 */     if (hasReducedHeader()) {
/* 167 */       sink.writeInt(0);
/*     */     }
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
/*     */   static void updateSubsysOffset(long headerStart, long subsysStart, Sink sink) throws IOException {
/* 183 */     long currentPosition = sink.position();
/* 184 */     sink.position(headerStart + 116L);
/* 185 */     sink.writeLong(subsysStart - headerStart);
/* 186 */     sink.position(currentPosition);
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
/*     */   public static String getDefaultDescription() {
/* 199 */     return ", Platform: " + System.getProperty("os.name") + ", Created on: " + (new Date()).toString();
/*     */   }
/*     */   
/*     */   protected Mat5File() {
/* 203 */     this(getDefaultDescription());
/*     */   }
/*     */   
/*     */   protected Mat5File(String description) {
/* 207 */     this.description = "MATLAB 5.0 MAT-file" + description;
/* 208 */     this.subsysOffset = 0L;
/* 209 */     this.byteOrder = ByteOrder.nativeOrder();
/* 210 */     this.version = 1;
/* 211 */     this.reduced = false;
/*     */   }
/*     */   
/*     */   private Mat5File(boolean reduced, String description, long subsysOffset, ByteOrder byteOrder, short version) {
/* 215 */     this.reduced = reduced;
/* 216 */     this.description = description;
/* 217 */     this.subsysOffset = subsysOffset;
/* 218 */     this.byteOrder = byteOrder;
/* 219 */     this.version = version;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 223 */     return this.description;
/*     */   }
/*     */   
/*     */   public long getSubsysOffset() {
/* 227 */     return this.subsysOffset;
/*     */   }
/*     */   
/*     */   public ByteOrder getByteOrder() {
/* 231 */     return this.byteOrder;
/*     */   }
/*     */   
/*     */   public short getVersion() {
/* 235 */     return this.version;
/*     */   }
/*     */   
/*     */   public boolean hasReducedHeader() {
/* 239 */     return this.reduced;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 244 */     return "Mat5File{description='" + 
/* 245 */       this.description + '\'' + 
/* 246 */       ", subsysOffset=" + this.subsysOffset + 
/* 247 */       ", byteOrder=" + this.byteOrder + 
/* 248 */       ", version=" + this.version + 
/* 249 */       "}\n" + super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUncompressedSerializedSize() {
/* 254 */     long size = (this.reduced ? 8L : 128L);
/* 255 */     for (MatFile.Entry entry : this.entries) {
/* 256 */       size += Mat5WriteUtil.computeArraySize(entry.getName(), entry.getValue());
/*     */     }
/* 258 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public Mat5File writeTo(Sink sink) throws IOException {
/* 263 */     Mat5.newWriter(sink).writeMat((MatFile)this);
/* 264 */     return this;
/*     */   }
/*     */   
/*     */   public Mat5Subsystem getSubsystem() {
/* 268 */     Array last = getLast();
/* 269 */     return (last instanceof Mat5Subsystem) ? (Mat5Subsystem)last : null;
/*     */   }
/*     */   
/*     */   public Mat5File addEntry(MatFile.Entry entry) {
/* 273 */     if (this.reduced && getNumEntries() >= 2) {
/* 274 */       throw new IllegalStateException("Reduced MAT 5 files may not contain more than 2 entries");
/*     */     }
/*     */ 
/*     */     
/* 278 */     if (getLast() instanceof Mat5Subsystem) {
/* 279 */       this.entries.add(getNumEntries() - 1, entry);
/*     */     } else {
/* 281 */       this.entries.add(entry);
/*     */     } 
/* 283 */     this.lookup.put(entry.getName(), entry.getValue());
/* 284 */     return this;
/*     */   }
/*     */   
/*     */   private Array getLast() {
/* 288 */     return (getNumEntries() == 0) ? null : ((MatFile.Entry)this.entries.get(getNumEntries() - 1)).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkMat5Identifier(String description) {
/* 298 */     if (!description.startsWith("MATLAB 5.0 MAT-file")) {
/* 299 */       throw new IllegalArgumentException("This is not a valid MATLAB 5.0 MAT-file description.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int subHashCode() {
/* 307 */     return Compat.hash(new Object[] { this.description, Long.valueOf(this.subsysOffset), this.byteOrder, Short.valueOf(this.version), Boolean.valueOf(this.reduced) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 312 */     Mat5File other = (Mat5File)otherGuaranteedSameClass;
/* 313 */     return (Compat.equals(other.description, this.description) && 
/* 314 */       Compat.equals(Long.valueOf(other.subsysOffset), Long.valueOf(this.subsysOffset)) && 
/* 315 */       Compat.equals(other.byteOrder, this.byteOrder) && 
/* 316 */       Compat.equals(Short.valueOf(other.version), Short.valueOf(this.version)) && 
/* 317 */       Compat.equals(Boolean.valueOf(other.reduced), Boolean.valueOf(this.reduced)));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5File.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */