/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Future;
/*     */ import us.hebi.matlab.mat.types.AbstractArray;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.Cell;
/*     */ import us.hebi.matlab.mat.types.Char;
/*     */ import us.hebi.matlab.mat.types.MatFile;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.ObjectStruct;
/*     */ import us.hebi.matlab.mat.types.Opaque;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ import us.hebi.matlab.mat.types.Sparse;
/*     */ import us.hebi.matlab.mat.types.Struct;
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
/*     */ public final class Mat5Reader
/*     */ {
/*     */   private final Source source;
/*     */   private int numEntries;
/*     */   private long subsysPosition;
/*     */   private boolean nextIsSubsys;
/*     */   private boolean mayFilterNext;
/*     */   private boolean reducedHeader;
/*     */   private EntryFilter filter;
/*     */   private ExecutorService executorService;
/*     */   private boolean processSubsystem;
/*     */   private int maxInflateBufferSize;
/*     */   private McosRegistry mcos;
/*     */   private BufferAllocator bufferAllocator;
/*     */   
/*     */   public static class EntryHeader
/*     */   {
/*     */     final int[] arrayFlags;
/*     */     final MatlabType type;
/*     */     final int[] dimensions;
/*     */     final String name;
/*     */     
/*     */     public int getNumElements() {
/*  62 */       return AbstractArray.getNumElements(this.dimensions);
/*     */     }
/*     */     
/*     */     public MatlabType getType() {
/*  66 */       return this.type;
/*     */     }
/*     */     
/*     */     public int[] getDimensions() {
/*  70 */       return this.dimensions;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  74 */       return this.name;
/*     */     }
/*     */     
/*     */     public boolean isLogical() {
/*  78 */       return Mat5ArrayFlags.isLogical(this.arrayFlags);
/*     */     }
/*     */     
/*     */     public boolean isComplex() {
/*  82 */       return Mat5ArrayFlags.isComplex(this.arrayFlags);
/*     */     }
/*     */     
/*     */     public boolean isGlobal() {
/*  86 */       return Mat5ArrayFlags.isGlobal(this.arrayFlags);
/*     */     }
/*     */     
/*     */     public int getNzMax() {
/*  90 */       return Mat5ArrayFlags.getNzMax(this.arrayFlags);
/*     */     }
/*     */     
/*     */     private EntryHeader(int[] arrayFlags, MatlabType type, int[] dimensions, String name) {
/*  94 */       this.arrayFlags = arrayFlags;
/*  95 */       this.type = type;
/*  96 */       this.dimensions = dimensions;
/*  97 */       this.name = name;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mat5Reader setEntryFilter(EntryFilter filter) {
/* 121 */     this.filter = (EntryFilter)Preconditions.checkNotNull(filter);
/* 122 */     return this;
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
/*     */   public Mat5Reader enableConcurrentDecompression(ExecutorService executorService) {
/* 138 */     this.executorService = (ExecutorService)Preconditions.checkNotNull(executorService);
/* 139 */     return this;
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
/*     */   public Mat5Reader setBufferAllocator(BufferAllocator bufferAllocator) {
/* 152 */     this.bufferAllocator = bufferAllocator;
/* 153 */     return this;
/*     */   }
/*     */   
/*     */   public Mat5Reader setReducedHeader(boolean reducedHeader) {
/* 157 */     this.reducedHeader = reducedHeader;
/* 158 */     return this;
/*     */   }
/*     */   
/*     */   public Mat5Reader setMaxInflateBufferSize(int maxInflateBufferSize) {
/* 162 */     this.maxInflateBufferSize = maxInflateBufferSize;
/* 163 */     return this;
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
/*     */   public Mat5Reader disableSubsystemProcessing() {
/* 176 */     this.processSubsystem = false;
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   Mat5Reader setMcosRegistry(McosRegistry registry) {
/* 181 */     this.mcos = (McosRegistry)Preconditions.checkNotNull(registry);
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Mat5File readMat() throws IOException {
/*     */     try {
/* 189 */       long start = this.source.getPosition();
/* 190 */       Mat5File matFile = readMatHeader();
/* 191 */       this.subsysPosition = start + matFile.getSubsysOffset();
/*     */ 
/*     */       
/* 194 */       for (Future<MatFile.Entry> task : readMatContent()) {
/* 195 */         MatFile.Entry entry = task.get();
/* 196 */         if (entry != null) {
/* 197 */           matFile.addEntry(entry);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 202 */       if (matFile.getSubsystem() != null && this.processSubsystem) {
/* 203 */         matFile.getSubsystem().processReferences(this.mcos);
/*     */       }
/* 205 */       return matFile;
/*     */     }
/* 207 */     catch (Exception e) {
/* 208 */       throw new IOException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Mat5File readMatHeader() throws IOException {
/*     */     Mat5File matFile;
/* 214 */     this.source.order(ByteOrder.nativeOrder());
/*     */     
/* 216 */     if (this.reducedHeader) {
/* 217 */       matFile = Mat5File.readReducedFileHeader(this.source);
/*     */     } else {
/* 219 */       matFile = Mat5File.readFileHeader(this.source);
/* 220 */     }  this.source.order(matFile.getByteOrder());
/* 221 */     return matFile;
/*     */   }
/*     */   
/*     */   private List<Future<MatFile.Entry>> readMatContent() throws IOException {
/* 225 */     List<Future<MatFile.Entry>> content = new ArrayList<>();
/* 226 */     Mat5Tag tag = Mat5Tag.readTagOrNull(this.source);
/* 227 */     while (tag != null) {
/* 228 */       content.add(readEntry(tag));
/* 229 */       tag = Mat5Tag.readTagOrNull(this.source);
/*     */     } 
/* 231 */     return content;
/*     */   }
/*     */   
/*     */   private Mat5Tag readTag() throws IOException {
/* 235 */     return Mat5Tag.readTag(this.source);
/*     */   }
/*     */   private Future<MatFile.Entry> readEntry(Mat5Tag tag) throws IOException {
/*     */     final boolean atSubsys;
/* 239 */     Preconditions.checkArgument((tag.getNumBytes() != 0), "Root element contains no data");
/* 240 */     long expectedEnd = this.source.getPosition() + tag.getNumBytes() + tag.getPadding();
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (!this.reducedHeader) {
/*     */       
/* 246 */       atSubsys = (this.subsysPosition == this.source.getPosition() - 8L);
/*     */     } else {
/*     */       
/* 249 */       this.numEntries++;
/* 250 */       atSubsys = (this.numEntries == 2);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 258 */       if (tag.getType() == Mat5Type.Matrix) {
/* 259 */         return Tasks.wrapAsFuture(atRoot(atSubsys).readEntryWithoutTag(tag));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 266 */       if (tag.getType() == Mat5Type.Compressed) {
/*     */ 
/*     */         
/* 269 */         int bufferSize = Math.min(tag.getNumBytes() * 2, this.maxInflateBufferSize);
/* 270 */         final Source inflated = this.source.readInflated(tag.getNumBytes(), bufferSize);
/*     */ 
/*     */         
/* 273 */         Tasks.IoTask<MatFile.Entry> task = new Tasks.IoTask<MatFile.Entry>()
/*     */           {
/*     */             public MatFile.Entry call() throws IOException {
/*     */               try {
/* 277 */                 return Mat5Reader.this.createChildReader(inflated)
/* 278 */                   .atRoot(atSubsys)
/* 279 */                   .readEntry();
/*     */               } finally {
/* 281 */                 inflated.close();
/*     */               } 
/*     */             }
/*     */           };
/*     */ 
/*     */         
/* 287 */         boolean runAsync = (!this.source.isMutatedByChildren() && this.executorService != null);
/* 288 */         return runAsync ? this.executorService.<MatFile.Entry>submit((Callable<MatFile.Entry>)task) : Tasks.wrapAsFuture(task.call());
/*     */       } 
/*     */ 
/*     */       
/* 292 */       throw readError("Expected 'Compressed' or 'Matrix' tag. Found: %s", new Object[] { tag.getType() });
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 298 */       long remaining = expectedEnd - this.source.getPosition();
/* 299 */       if (remaining > 0L) {
/* 300 */         this.source.skip(remaining);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface EntryFilter
/*     */   {
/*     */     boolean isAccepted(Mat5Reader.EntryHeader param1EntryHeader);
/*     */   }
/*     */   
/*     */   private Mat5Reader atRoot(boolean atSubsys) {
/* 312 */     this.nextIsSubsys = atSubsys;
/* 313 */     this.mayFilterNext = true;
/* 314 */     return this;
/*     */   }
/*     */   
/*     */   private boolean isAccepted(EntryHeader header) {
/*     */     try {
/* 319 */       if (this.filter == null || !this.mayFilterNext || this.nextIsSubsys)
/* 320 */         return true; 
/* 321 */       return this.filter.isAccepted(header);
/*     */     } finally {
/* 323 */       this.mayFilterNext = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private Array readNestedArray() throws IOException {
/* 328 */     return readEntry().getValue();
/*     */   }
/*     */   
/*     */   private MatFile.Entry readEntry() throws IOException {
/* 332 */     Mat5Tag tag = readTagWithExpectedType(Mat5Type.Matrix);
/*     */ 
/*     */     
/* 335 */     if (tag.getNumBytes() == 0)
/* 336 */       return new MatFile.Entry("", false, Mat5.EMPTY_MATRIX); 
/* 337 */     return readEntryWithoutTag(tag);
/*     */   }
/*     */   
/*     */   private MatFile.Entry readEntryWithoutTag(Mat5Tag tag) throws IOException {
/* 341 */     long start = this.source.getPosition();
/* 342 */     MatFile.Entry value = readEntryWithoutTag();
/* 343 */     long numBytes = this.source.getPosition() - start;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     if (tag.getNumBytes() == numBytes || value == null) {
/* 350 */       return value;
/*     */     }
/* 352 */     throw readError("Specified matrix tag does not match content size. Tag: %d, Content: %d", new Object[] { Integer.valueOf(tag.getNumBytes()), Long.valueOf(numBytes) });
/*     */   }
/*     */   
/*     */   private MatFile.Entry readEntryWithoutTag() throws IOException {
/*     */     Array array;
/* 357 */     int[] arrayFlags = readTagWithExpectedType(Mat5Type.UInt32).readAsInts();
/* 358 */     if (arrayFlags.length != 2)
/* 359 */       throw readError("Unexpected size of array flags. Expected %d, Found %d", new Object[] { Integer.valueOf(2), Integer.valueOf(arrayFlags.length) }); 
/* 360 */     MatlabType type = Mat5ArrayFlags.getType(arrayFlags);
/*     */ 
/*     */     
/* 363 */     if (type == MatlabType.Opaque) {
/* 364 */       this.mayFilterNext = false;
/* 365 */       return readOpaque(arrayFlags);
/*     */     } 
/*     */ 
/*     */     
/* 369 */     int[] dimensions = readTagWithExpectedType(Mat5Type.Int32).readAsInts();
/* 370 */     if (dimensions.length < 2) {
/* 371 */       throw readError("Expected at least 2 dimensions. Found %d", new Object[] { Integer.valueOf(dimensions.length) });
/*     */     }
/*     */ 
/*     */     
/* 375 */     String name = readAsAscii(readTagWithExpectedType(Mat5Type.Int8));
/* 376 */     EntryHeader header = new EntryHeader(arrayFlags, type, dimensions, name, null);
/*     */ 
/*     */     
/* 379 */     if (!isAccepted(header)) {
/* 380 */       return null;
/*     */     }
/*     */     
/* 383 */     if (this.nextIsSubsys) {
/*     */       try {
/* 385 */         return new MatFile.Entry(name, header.isGlobal(), readSubsystem(header));
/*     */       } finally {
/* 387 */         this.nextIsSubsys = false;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 393 */     switch (header.getType()) {
/*     */       case Double:
/*     */       case Single:
/*     */       case Int8:
/*     */       case UInt8:
/*     */       case Int16:
/*     */       case UInt16:
/*     */       case Int32:
/*     */       case UInt32:
/*     */       case Int64:
/*     */       case UInt64:
/* 404 */         array = readNumerical(header);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 438 */         return new MatFile.Entry(name, header.isGlobal(), array);case Sparse: array = readSparse(header); return new MatFile.Entry(name, header.isGlobal(), array);case Character: array = readChar(header); return new MatFile.Entry(name, header.isGlobal(), array);case null: array = readCell(header); return new MatFile.Entry(name, header.isGlobal(), array);case Structure: array = readStruct(header); return new MatFile.Entry(name, header.isGlobal(), array);case Object: array = readObject(header); return new MatFile.Entry(name, header.isGlobal(), array);case Function: array = readFunctionHandle(header); return new MatFile.Entry(name, header.isGlobal(), array);
/*     */       case Opaque:
/*     */         throw new AssertionError("Should not get here");
/*     */     } 
/* 442 */     throw readError("Found unsupported type: %s", new Object[] { type }); } private Array readSubsystem(EntryHeader header) throws IOException { if (header.isComplex())
/* 443 */       throw readError("Subsystem can't be complex", new Object[0]); 
/* 444 */     if (header.getType() != MatlabType.UInt8) {
/* 445 */       throw readError("Unexpected Subsystem class type. Expected: %s, Found %s", new Object[] { Mat5Type.UInt8, header.getType() });
/*     */     }
/*     */     
/* 448 */     ByteBuffer buffer = readAsByteBuffer(readTagWithExpectedType(Mat5Type.UInt8));
/* 449 */     return (Array)new Mat5Subsystem(header.getDimensions(), buffer, this.bufferAllocator); }
/*     */ 
/*     */ 
/*     */   
/*     */   private Array readNumerical(EntryHeader header) throws IOException {
/* 454 */     NumberStore real = readAsNumberStore(readTag());
/*     */ 
/*     */     
/* 457 */     NumberStore imaginary = null;
/* 458 */     if (header.isComplex()) {
/* 459 */       imaginary = readAsNumberStore(readTag());
/*     */     }
/* 461 */     return (Array)createMatrix(header.getDimensions(), header.getType(), header.isLogical(), 
/* 462 */         real, imaginary);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Array readSparse(EntryHeader header) throws IOException {
/* 469 */     NumberStore rowIndices = readAsNumberStore(readTagWithExpectedType(Mat5Type.Int32));
/* 470 */     if (header.getNzMax() == 1 && rowIndices.getNumElements() == 0)
/*     */     {
/*     */       
/* 473 */       rowIndices = new UniversalNumberStore(Mat5Type.Int32, this.bufferAllocator.allocate(4), this.bufferAllocator);
/*     */     }
/*     */ 
/*     */     
/* 477 */     NumberStore colIndices = readAsNumberStore(readTagWithExpectedType(Mat5Type.Int32));
/*     */ 
/*     */     
/* 480 */     NumberStore real = readAsNumberStore(readTag());
/*     */ 
/*     */     
/* 483 */     NumberStore imaginary = null;
/* 484 */     if (header.isComplex()) {
/* 485 */       imaginary = readAsNumberStore(readTag());
/*     */     }
/*     */     
/* 488 */     return (Array)createSparse(header.getDimensions(), header.isLogical(), 
/* 489 */         header.getNzMax(), real, imaginary, rowIndices, colIndices);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Array readChar(EntryHeader header) throws IOException {
/* 495 */     Mat5Tag tag = readTag();
/* 496 */     CharEncoding encoding = tag.getType().getCharEncoding();
/* 497 */     CharBuffer buffer = encoding.readCharBuffer(this.source, tag.getNumBytes());
/* 498 */     this.source.skip(tag.getPadding());
/* 499 */     return (Array)createChar(header.getDimensions(), encoding, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Array readCell(EntryHeader header) throws IOException {
/* 506 */     Array[] contents = new Array[header.getNumElements()];
/* 507 */     for (int i = 0; i < contents.length; i++) {
/* 508 */       contents[i] = readNestedArray();
/*     */     }
/*     */     
/* 511 */     return (Array)createCell(header.getDimensions(), contents);
/*     */   }
/*     */ 
/*     */   
/*     */   private Array readStruct(EntryHeader header) throws IOException {
/* 516 */     return readStructOrObject(header, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private Array readObject(EntryHeader header) throws IOException {
/* 521 */     String className = readAsAscii(readTagWithExpectedType(Mat5Type.Int8));
/* 522 */     return readStructOrObject(header, className);
/*     */   }
/*     */ 
/*     */   
/*     */   private Array readStructOrObject(EntryHeader header, String objectClassName) throws IOException {
/* 527 */     int[] result = readTagWithExpectedType(Mat5Type.Int32).readAsInts();
/* 528 */     Preconditions.checkArgument((result.length == 1), "Incorrect number of values for max field name length");
/* 529 */     int maxLength = result[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 534 */     byte[] buffer = readTagWithExpectedType(Mat5Type.Int8).readAsBytes();
/* 535 */     int numFields = (maxLength == 0) ? 0 : (buffer.length / maxLength);
/* 536 */     String[] names = new String[numFields];
/* 537 */     for (int i = 0; i < numFields; i++) {
/* 538 */       names[i] = CharEncoding.parseAsciiString(buffer, i * maxLength, maxLength);
/*     */     }
/*     */ 
/*     */     
/* 542 */     int numElements = header.getNumElements();
/* 543 */     Array[][] values = new Array[numFields][numElements];
/* 544 */     for (int j = 0; j < numElements; j++) {
/* 545 */       for (int field = 0; field < numFields; field++) {
/* 546 */         values[field][j] = readNestedArray();
/*     */       }
/*     */     } 
/*     */     
/* 550 */     if (objectClassName == null)
/* 551 */       return (Array)createStruct(header.getDimensions(), names, values); 
/* 552 */     return (Array)createObject(header.getDimensions(), objectClassName, names, values);
/*     */   }
/*     */   
/*     */   private Array readFunctionHandle(EntryHeader header) throws IOException {
/* 556 */     Struct content = (Struct)readNestedArray();
/* 557 */     return (Array)new MatFunction(content);
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
/*     */   private MatFile.Entry readOpaque(int[] arrayFlags) throws IOException {
/* 569 */     boolean isGlobal = Mat5ArrayFlags.isGlobal(arrayFlags);
/*     */ 
/*     */     
/* 572 */     String name = readAsAscii(readTagWithExpectedType(Mat5Type.Int8));
/*     */ 
/*     */     
/* 575 */     String objectType = readAsAscii(readTagWithExpectedType(Mat5Type.Int8));
/*     */ 
/*     */     
/* 578 */     String className = readAsAscii(readTagWithExpectedType(Mat5Type.Int8));
/*     */ 
/*     */     
/* 581 */     Array content = readNestedArray();
/*     */     
/* 583 */     return new MatFile.Entry(name, isGlobal, (Array)createOpaque(objectType, className, content));
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readAsAscii(Mat5Tag tag) throws IOException {
/* 588 */     return CharEncoding.parseAsciiString(tag.readAsBytes());
/*     */   }
/*     */   
/*     */   private NumberStore readAsNumberStore(Mat5Tag tag) throws IOException {
/* 592 */     return new UniversalNumberStore(tag.getType(), readAsByteBuffer(tag), this.bufferAllocator);
/*     */   }
/*     */   
/*     */   private ByteBuffer readAsByteBuffer(Mat5Tag tag) throws IOException {
/* 596 */     ByteBuffer buffer = this.bufferAllocator.allocate(tag.getNumBytes());
/* 597 */     buffer.order(this.source.order());
/* 598 */     this.source.readByteBuffer(buffer);
/* 599 */     this.source.skip(tag.getPadding());
/* 600 */     buffer.rewind();
/* 601 */     return buffer;
/*     */   }
/*     */   
/*     */   private Mat5Tag readTagWithExpectedType(Mat5Type expected) throws IOException {
/* 605 */     Mat5Tag tag = readTag();
/* 606 */     if (tag.getType() != expected)
/* 607 */       throw readError("Encountered unexpected tag. Expected %s, Found %s", new Object[] { expected, tag.getType() }); 
/* 608 */     return tag;
/*     */   }
/*     */   
/*     */   static IOException readError(String format, Object... args) {
/* 612 */     return new IOException(String.format(format, args));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Mat5Reader createChildReader(Source source) {
/* 618 */     Mat5Reader reader = new Mat5Reader(source);
/* 619 */     reader.filter = this.filter;
/* 620 */     reader.mcos = this.mcos;
/* 621 */     reader.bufferAllocator = this.bufferAllocator;
/* 622 */     return reader;
/*     */   }
/*     */   
/*     */   private Matrix createMatrix(int[] dimensions, MatlabType type, boolean logical, NumberStore real, NumberStore imaginary) {
/* 626 */     return (Matrix)new MatMatrix(dimensions, type, logical, real, imaginary);
/*     */   }
/*     */   
/*     */   private Sparse createSparse(int[] dimensions, boolean logical, int nzMax, NumberStore real, NumberStore imaginary, NumberStore rowIndices, NumberStore colIndices) {
/* 630 */     return new MatSparseCSC(dimensions, logical, nzMax, real, imaginary, rowIndices, colIndices);
/*     */   }
/*     */   
/*     */   private Char createChar(int[] dims, CharEncoding encoding, CharBuffer buffer) {
/* 634 */     return (Char)new MatChar(dims, encoding, buffer);
/*     */   }
/*     */   
/*     */   private Cell createCell(int[] dims, Array[] contents) {
/* 638 */     return (Cell)new MatCell(dims, contents);
/*     */   }
/*     */   
/*     */   private Struct createStruct(int[] dims, String[] names, Array[][] values) {
/* 642 */     return (Struct)new MatStruct(dims, names, values);
/*     */   }
/*     */   
/*     */   private ObjectStruct createObject(int[] dims, String className, String[] names, Array[][] values) {
/* 646 */     return new MatObjectStruct(dims, className, names, values);
/*     */   }
/*     */ 
/*     */   
/*     */   private Opaque createOpaque(String objectType, String className, Array content) {
/* 651 */     if ("java".equals(objectType)) {
/* 652 */       return new MatJavaObject(className, content);
/*     */     }
/*     */     
/* 655 */     if ("MCOS".equals(objectType)) {
/* 656 */       if ("FileWrapper__".equals(className))
/*     */       {
/* 658 */         return new McosFileWrapper(objectType, className, content, this.source.order());
/*     */       }
/*     */       
/* 661 */       return this.mcos.register(McosReference.parseOpaque(objectType, className, content));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 666 */     return new MatOpaque(objectType, className, content);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Mat5Reader(Source source) {
/* 675 */     this.numEntries = 0;
/* 676 */     this.subsysPosition = Long.MIN_VALUE;
/* 677 */     this.nextIsSubsys = false;
/* 678 */     this.mayFilterNext = false;
/* 679 */     this.reducedHeader = false;
/* 680 */     this.filter = null;
/* 681 */     this.executorService = null;
/* 682 */     this.processSubsystem = true;
/* 683 */     this.maxInflateBufferSize = 2048;
/* 684 */     this.mcos = new McosRegistry();
/* 685 */     this.bufferAllocator = Mat5.getDefaultBufferAllocator();
/*     */     this.source = (Source)Preconditions.checkNotNull(source, "Source can't be empty");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5Reader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */