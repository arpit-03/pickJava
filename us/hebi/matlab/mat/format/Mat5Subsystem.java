/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import us.hebi.matlab.mat.types.AbstractArray;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Sources;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Mat5Subsystem
/*     */   extends AbstractArray
/*     */   implements Mat5Serializable
/*     */ {
/*     */   private ByteBuffer buffer;
/*     */   private BufferAllocator bufferAllocator;
/*     */   private Mat5File subFile;
/*     */   
/*     */   Mat5Subsystem(int[] dims, ByteBuffer buffer, BufferAllocator bufferAllocator) {
/*  46 */     super(dims);
/*  47 */     this.buffer = buffer;
/*  48 */     this.bufferAllocator = bufferAllocator;
/*     */   }
/*     */ 
/*     */   
/*     */   public MatlabType getType() {
/*  53 */     return MatlabType.UInt8;
/*     */   }
/*     */   
/*     */   public ByteBuffer getBuffer() {
/*  57 */     return this.buffer.slice();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMat5Size(String name) {
/*  62 */     return 8 + 
/*  63 */       Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this) + 
/*  64 */       Mat5Type.UInt8.computeSerializedSize(getNumElements());
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/*  69 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/*  70 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/*  71 */     Mat5Type.UInt8.writeByteBufferWithTag(this.buffer.slice(), sink);
/*     */   }
/*     */   
/*     */   void processReferences(McosRegistry mcosRegistry) throws IOException {
/*  75 */     if (mcosRegistry.getReferences().isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  79 */     this.subFile = Mat5.newReader(Sources.wrap(this.buffer.slice()))
/*  80 */       .setMcosRegistry(mcosRegistry)
/*  81 */       .setBufferAllocator(this.bufferAllocator)
/*  82 */       .disableSubsystemProcessing()
/*  83 */       .setReducedHeader(true)
/*  84 */       .readMat();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     McosFileWrapper fileWrapper = (McosFileWrapper)this.subFile.getStruct(0).get("MCOS");
/*  91 */     List<McosObject> objects = fileWrapper.parseObjects(mcosRegistry);
/*     */ 
/*     */     
/*  94 */     objects.add(0, null);
/*  95 */     for (McosReference reference : mcosRegistry.getReferences()) {
/*  96 */       reference.setReferences(objects);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 103 */     if (this.subFile != null)
/* 104 */       this.subFile.close(); 
/* 105 */     this.bufferAllocator.release(this.buffer);
/* 106 */     this.buffer = null;
/* 107 */     this.bufferAllocator = null;
/* 108 */     this.subFile = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int subHashCode() {
/* 117 */     return this.buffer.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 122 */     Mat5Subsystem other = (Mat5Subsystem)otherGuaranteedSameClass;
/* 123 */     return other.buffer.equals(this.buffer);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5Subsystem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */