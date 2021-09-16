/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.zip.Deflater;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatFile;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Sinks;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
/*     */ import us.hebi.matlab.mat.util.Tasks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Mat5Writer
/*     */ {
/*     */   protected final Sink sink;
/*     */   protected int deflateLevel;
/*     */   private long headerStart;
/*     */   private long subsysLocation;
/*     */   private ExecutorService executorService;
/*     */   private BufferAllocator bufferAllocator;
/*     */   private final List<Future<FlushAction>> flushActions;
/*     */   private Deflater deflater;
/*     */   
/*     */   public Mat5Writer setDeflateLevel(int deflateLevel) {
/*  52 */     this.deflateLevel = deflateLevel;
/*  53 */     return this;
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
/*     */   public Mat5Writer enableConcurrentCompression(ExecutorService executorService) {
/*  69 */     return enableConcurrentCompression(executorService, Mat5.getDefaultBufferAllocator());
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
/*     */   public Mat5Writer enableConcurrentCompression(ExecutorService executorService, BufferAllocator bufferAllocator) {
/*  84 */     this.executorService = (ExecutorService)Preconditions.checkNotNull(executorService, "empty executor service");
/*  85 */     this.bufferAllocator = (BufferAllocator)Preconditions.checkNotNull(bufferAllocator, "empty buffer allocator");
/*  86 */     this.deflater = null;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public Mat5Writer writeMat(MatFile matFile) throws IOException {
/*  91 */     if (matFile instanceof Mat5File) {
/*  92 */       return writeMat((Mat5File)matFile);
/*     */     }
/*  94 */     throw new IllegalArgumentException("MatFile does not support the MAT5 format");
/*     */   }
/*     */   
/*     */   private Mat5Writer writeMat(Mat5File matFile) throws IOException {
/*  98 */     if (!matFile.hasReducedHeader())
/*  99 */       this.headerStart = this.sink.position(); 
/* 100 */     matFile.writeFileHeader(this.sink);
/* 101 */     for (MatFile.Entry entry : matFile.getEntries()) {
/* 102 */       writeEntry(entry);
/*     */     }
/* 104 */     flush();
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public Mat5Writer writeEntry(MatFile.Entry entry) throws IOException {
/* 109 */     return writeArray(entry.getName(), entry.isGlobal(), entry.getValue());
/*     */   }
/*     */   
/*     */   public Mat5Writer writeArray(String name, Array value) throws IOException {
/* 113 */     return writeArray(name, false, value);
/*     */   }
/*     */   
/*     */   public Mat5Writer writeArray(final String name, final boolean isGlobal, final Array array) throws IOException {
/* 117 */     if ((name == null || name.isEmpty()) && 
/* 118 */       !(array instanceof McosReference) && 
/* 119 */       !(array instanceof Mat5Subsystem))
/* 120 */       throw new IllegalArgumentException("Root Array can't have an empty name"); 
/* 121 */     final boolean isSubsystem = array instanceof Mat5Subsystem;
/*     */     
/* 123 */     if (this.deflateLevel == 0) {
/*     */ 
/*     */       
/* 126 */       if (this.flushActions.isEmpty()) {
/*     */ 
/*     */         
/* 129 */         if (isSubsystem) nextEntryIsSubsystem(); 
/* 130 */         Mat5WriteUtil.writeArray(name, isGlobal, array, this.sink);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 135 */         FlushAction action = new FlushAction() {
/*     */             public void run() throws IOException {
/* 137 */               if (isSubsystem) Mat5Writer.this.nextEntryIsSubsystem(); 
/* 138 */               Mat5WriteUtil.writeArray(name, isGlobal, array, Mat5Writer.this.sink);
/*     */             }
/*     */           };
/* 141 */         this.flushActions.add(Tasks.wrapAsFuture(action));
/*     */       } 
/*     */     } else {
/*     */       
/* 145 */       if (this.executorService == null) {
/*     */ 
/*     */ 
/*     */         
/* 149 */         Preconditions.checkState(this.flushActions.isEmpty(), "Expected flush actions to be empty when writing single threaded");
/* 150 */         if (isSubsystem) nextEntryIsSubsystem();
/*     */ 
/*     */         
/* 153 */         if (this.deflater == null) {
/* 154 */           this.deflater = new Deflater(this.deflateLevel);
/*     */         } else {
/* 156 */           this.deflater.setLevel(this.deflateLevel);
/* 157 */           this.deflater.reset();
/*     */         } 
/*     */         
/* 160 */         Mat5WriteUtil.writeArrayDeflated(name, isGlobal, array, this.sink, this.deflater);
/* 161 */         return this;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 166 */       final Deflater deflater = new Deflater(this.deflateLevel);
/* 167 */       final BufferAllocator bufferAllocator = this.bufferAllocator;
/* 168 */       this.flushActions.add(this.executorService.submit(new Callable<FlushAction>()
/*     */             {
/*     */               
/*     */               public Mat5Writer.FlushAction call() throws Exception
/*     */               {
/* 173 */                 int maxExpectedSize = Mat5WriteUtil.computeArraySize(name, array) + 256;
/* 174 */                 final ByteBuffer buffer = bufferAllocator.allocate(maxExpectedSize);
/* 175 */                 Sink tmpSink = Sinks.wrap(buffer).order(Mat5Writer.this.sink.order());
/*     */ 
/*     */                 
/* 178 */                 Mat5WriteUtil.writeArrayDeflated(name, isGlobal, array, tmpSink, deflater);
/* 179 */                 tmpSink.close();
/* 180 */                 buffer.flip();
/*     */ 
/*     */                 
/* 183 */                 return new Mat5Writer.FlushAction() {
/*     */                     public void run() throws IOException {
/*     */                       try {
/* 186 */                         if (isSubsystem) Mat5Writer.null.access$1(Mat5Writer.null.this).nextEntryIsSubsystem(); 
/* 187 */                         (Mat5Writer.null.access$1(Mat5Writer.null.this)).sink.writeByteBuffer(buffer);
/*     */                       } finally {
/* 189 */                         bufferAllocator.release(buffer);
/*     */                       } 
/*     */                     }
/*     */                   };
/*     */               }
/*     */             }));
/*     */     } 
/*     */ 
/*     */     
/* 198 */     return this;
/*     */   }
/*     */   
/*     */   private void nextEntryIsSubsystem() throws IOException {
/* 202 */     this.subsysLocation = this.sink.position();
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
/*     */   public Mat5Writer flush() throws IOException {
/* 215 */     for (Future<FlushAction> action : this.flushActions) {
/*     */       try {
/* 217 */         ((FlushAction)action.get()).run();
/* 218 */       } catch (Exception e) {
/* 219 */         throw new IOException(e);
/*     */       } 
/*     */     } 
/*     */     
/* 223 */     if (this.headerStart >= 0L && this.subsysLocation >= -1L) {
/* 224 */       Mat5File.updateSubsysOffset(this.headerStart, this.subsysLocation, this.sink);
/* 225 */       this.subsysLocation = -1L;
/*     */     } 
/* 227 */     return this;
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
/*     */   Mat5Writer(Sink sink) {
/* 239 */     this.deflateLevel = 1;
/* 240 */     this.headerStart = -1L;
/* 241 */     this.subsysLocation = -1L;
/* 242 */     this.executorService = null;
/* 243 */     this.bufferAllocator = Mat5.getDefaultBufferAllocator();
/* 244 */     this.flushActions = new ArrayList<>(16);
/* 245 */     this.deflater = null;
/*     */     this.sink = (Sink)Preconditions.checkNotNull(sink, "Sink can't be empty");
/*     */   }
/*     */   
/*     */   private static interface FlushAction {
/*     */     void run() throws IOException;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5Writer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */