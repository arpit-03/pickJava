/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Stack;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ import us.hebi.matlab.mat.types.Sources;
/*     */ import us.hebi.matlab.mat.util.IndentingAppendable;
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
/*     */ class Mat5TagStreamer
/*     */ {
/*     */   private boolean reduced;
/*     */   private TagConsumer consumer;
/*     */   private final Source source;
/*     */   private boolean nextIsSubsys;
/*     */   
/*     */   Mat5TagStreamer(Source source) {
/*  53 */     this.reduced = false;
/*     */ 
/*     */     
/*  56 */     this.nextIsSubsys = false;
/*     */     this.source = (Source)Preconditions.checkNotNull(source);
/*     */   }
/*  59 */   void printTags() throws IOException { printTags(System.out); } Mat5TagStreamer setReducedHeader(boolean reduced) {
/*     */     this.reduced = reduced;
/*     */     return this;
/*     */   } void printTags(Appendable appendable) throws IOException {
/*  63 */     forEach(new TagPrinter(appendable));
/*     */   }
/*     */   
/*     */   void forEach(TagConsumer consumer) throws IOException {
/*  67 */     this.consumer = consumer;
/*     */ 
/*     */     
/*  70 */     this.source.order(ByteOrder.nativeOrder());
/*  71 */     Mat5File header = this.reduced ? Mat5File.readReducedFileHeader(this.source) : Mat5File.readFileHeader(this.source);
/*  72 */     this.source.order(header.getByteOrder());
/*  73 */     consumer.onFileStart(header);
/*     */ 
/*     */     
/*  76 */     int numEntries = 0;
/*  77 */     boolean eof = false;
/*  78 */     while (!eof) {
/*     */ 
/*     */       
/*  81 */       numEntries++;
/*  82 */       long position = this.source.getPosition();
/*  83 */       this.nextIsSubsys = this.reduced ? ((numEntries == 2)) : ((position == header.getSubsysOffset()));
/*     */ 
/*     */       
/*     */       try {
/*  87 */         handleTag(Mat5Tag.readTag(this.source), this.source, position);
/*  88 */       } catch (EOFException ex) {
/*  89 */         consumer.onFileEnd();
/*  90 */         eof = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleTag(Mat5Tag tag, Source source, long position) throws IOException {
/*  98 */     switch (tag.getType()) {
/*     */       case Matrix:
/* 100 */         handleMatrixTag(source, position, tag.getNumBytes());
/*     */         return;
/*     */       case null:
/* 103 */         handleCompressedTag(source, position, tag.getNumBytes());
/*     */         return;
/*     */     } 
/* 106 */     if (this.nextIsSubsys && tag.getType() == Mat5Type.UInt8) {
/*     */       
/* 108 */       if (this.consumer.onSubsystemBegin(position, tag.getNumBytes())) {
/* 109 */         Source subsys = Sources.wrap(tag.readAsBytes());
/* 110 */         (new Mat5TagStreamer(subsys))
/* 111 */           .setReducedHeader(true)
/* 112 */           .forEach(this.consumer);
/* 113 */         this.consumer.onSubsystemEnd();
/* 114 */         this.nextIsSubsys = false;
/*     */       } else {
/* 116 */         this.nextIsSubsys = false;
/* 117 */         handleTag(tag, source, position);
/*     */       }
/*     */     
/*     */     }
/* 121 */     else if (!this.consumer.onData(position, tag)) {
/* 122 */       source.skip((tag.getNumBytes() + tag.getPadding()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleCompressedTag(Source source, long position, int numBytes) throws IOException {
/* 128 */     this.consumer.onCompressedBegin(position, numBytes);
/* 129 */     Source inflated = source.readInflated(numBytes, 2048);
/*     */     try {
/* 131 */       handleTag(Mat5Tag.readTag(inflated), inflated, 0L);
/*     */     } finally {
/* 133 */       inflated.close();
/*     */     } 
/* 135 */     this.consumer.onCompressedEnd();
/*     */   }
/*     */   
/*     */   private void handleMatrixTag(Source source, long position, int numBytes) throws IOException {
/* 139 */     this.consumer.onMatrixBegin(position, numBytes);
/* 140 */     long end = source.getPosition() + numBytes;
/* 141 */     long currentPos = source.getPosition();
/* 142 */     while (currentPos < end) {
/* 143 */       handleTag(Mat5Tag.readTag(source), source, currentPos);
/* 144 */       currentPos = source.getPosition();
/*     */     } 
/* 146 */     this.consumer.onMatrixEnd();
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
/*     */   static interface TagConsumer
/*     */   {
/*     */     void onFileStart(Mat5File param1Mat5File) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onCompressedBegin(long param1Long, int param1Int) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onCompressedEnd() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onMatrixBegin(long param1Long, int param1Int) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onMatrixEnd() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onFileEnd() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean onSubsystemBegin(long param1Long, int param1Int) throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void onSubsystemEnd() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean onData(long param1Long, Mat5Tag param1Mat5Tag) throws IOException;
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
/*     */   static class TagPrinter
/*     */     implements TagConsumer
/*     */   {
/*     */     private boolean nextIsArrayFlags;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int count;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Stack<Integer> prevCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final IndentingAppendable out;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TagPrinter(Appendable out) {
/* 300 */       this.nextIsArrayFlags = false;
/* 301 */       this.count = 0;
/* 302 */       this.prevCount = new Stack<>();
/*     */       this.out = new IndentingAppendable(out);
/*     */     }
/*     */     
/*     */     public void onFileStart(Mat5File fileHeader) throws IOException {
/*     */       this.out.append(String.valueOf(fileHeader));
/*     */     }
/*     */     
/*     */     public void onCompressedBegin(long position, int numBytes) throws IOException {
/*     */       this.out.append("\n").append("[").append(Integer.toString(this.count)).append("] ").append(Mat5Type.Compressed.name()).append(" (").append(String.valueOf(numBytes)).append(" bytes,").append(" position = ").append(Long.toString(position)).append(")");
/*     */       indent();
/*     */     }
/*     */     
/*     */     public void onCompressedEnd() {
/*     */       unindent();
/*     */     }
/*     */     
/*     */     public void onMatrixBegin(long position, int numBytes) throws IOException {
/*     */       this.out.append("\n").append("[").append(Integer.toString(this.count)).append("] ").append(Mat5Type.Matrix.name()).append(" (").append(String.valueOf(numBytes)).append(" bytes,").append(" position = ").append(Long.toString(position)).append(")");
/*     */       indent();
/*     */       this.nextIsArrayFlags = true;
/*     */     }
/*     */     
/*     */     public void onMatrixEnd() {
/*     */       unindent();
/*     */       this.nextIsArrayFlags = false;
/*     */     }
/*     */     
/*     */     public boolean onSubsystemBegin(long position, int numBytes) throws IOException {
/*     */       this.out.append("\n--- Begin Subsystem ---").append("\n");
/*     */       indent();
/*     */       return true;
/*     */     }
/*     */     
/*     */     public void onSubsystemEnd() throws IOException {
/*     */       unindent();
/*     */     }
/*     */     
/*     */     public void onFileEnd() throws IOException {
/*     */       this.out.append("\n--- End of File ---");
/*     */     }
/*     */     
/*     */     private void indent() {
/*     */       this.out.indent();
/*     */       this.prevCount.push(Integer.valueOf(this.count));
/*     */       this.count = 0;
/*     */     }
/*     */     
/*     */     private void unindent() {
/*     */       this.out.unindent();
/*     */       this.count = ((Integer)this.prevCount.pop()).intValue() + 1;
/*     */     }
/*     */     
/*     */     public boolean onData(long position, Mat5Tag tag) throws IOException {
/*     */       int[] data;
/*     */       byte[] bytes;
/*     */       this.out.append("\n").append("[").append(Integer.toString(this.count)).append("] ").append(tag.getType().name()).append("[").append(Integer.toString(tag.getNumElements())).append("] = ");
/*     */       switch (tag.getType()) {
/*     */         case Int16:
/*     */         case UInt16:
/*     */           this.out.append(Arrays.toString(tag.readAsShorts()));
/*     */           this.nextIsArrayFlags = false;
/*     */           this.count++;
/*     */           return true;
/*     */         case Int32:
/*     */         case UInt32:
/*     */           data = tag.readAsInts();
/*     */           this.out.append(Arrays.toString(data));
/*     */           if (this.nextIsArrayFlags && data.length == 2)
/*     */             this.out.append(" // ").append(Mat5ArrayFlags.getType(data).name()); 
/*     */           this.nextIsArrayFlags = false;
/*     */           this.count++;
/*     */           return true;
/*     */         case Int64:
/*     */         case UInt64:
/*     */           this.out.append(Arrays.toString(tag.readAsLongs()));
/*     */           this.nextIsArrayFlags = false;
/*     */           this.count++;
/*     */           return true;
/*     */         case Single:
/*     */           this.out.append(Arrays.toString(tag.readAsFloats()));
/*     */           this.nextIsArrayFlags = false;
/*     */           this.count++;
/*     */           return true;
/*     */         case Double:
/*     */           this.out.append(Arrays.toString(tag.readAsDoubles()));
/*     */           this.nextIsArrayFlags = false;
/*     */           this.count++;
/*     */           return true;
/*     */         case Int8:
/*     */           bytes = tag.readAsBytes();
/*     */           this.out.append("['").append(CharEncoding.parseAsciiString(bytes)).append("']");
/*     */           this.nextIsArrayFlags = false;
/*     */           this.count++;
/*     */           return true;
/*     */       } 
/*     */       this.out.append(Arrays.toString(tag.readAsBytes()));
/*     */       this.nextIsArrayFlags = false;
/*     */       this.count++;
/*     */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5TagStreamer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */