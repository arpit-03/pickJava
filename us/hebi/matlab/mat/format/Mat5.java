/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.CharBuffer;
/*     */ import us.hebi.matlab.mat.types.AbstractArray;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.Cell;
/*     */ import us.hebi.matlab.mat.types.Char;
/*     */ import us.hebi.matlab.mat.types.MatFile;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Sinks;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ import us.hebi.matlab.mat.types.Sources;
/*     */ import us.hebi.matlab.mat.types.Struct;
/*     */ import us.hebi.matlab.mat.util.Casts;
/*     */ import us.hebi.matlab.mat.util.Preconditions;
/*     */ import us.hebi.matlab.mat.util.Unsafe9R;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Mat5
/*     */ {
/*     */   public static Mat5File readFromFile(String fileName) throws IOException {
/*  46 */     return readFromFile(new File((String)Preconditions.checkNotNull(fileName, "File can't be empty")));
/*     */   }
/*     */   
/*     */   public static Mat5File readFromFile(File file) throws IOException {
/*  50 */     Preconditions.checkNotNull(file, "Input file can't be empty");
/*  51 */     Source source = Sources.openFile(file);
/*     */     try {
/*  53 */       return newReader(source).readMat();
/*     */     } finally {
/*  55 */       source.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static File writeToFile(MatFile mat, String fileName) throws IOException {
/*  60 */     return writeToFile(mat, new File((String)Preconditions.checkNotNull(fileName, "File can't be empty")));
/*     */   }
/*     */   public static File writeToFile(MatFile mat, File file) throws IOException {
/*     */     Sink sink;
/*  64 */     Preconditions.checkNotNull(mat, "MatFile can't be empty");
/*  65 */     Preconditions.checkNotNull(file, "Output file can't be empty");
/*     */ 
/*     */ 
/*     */     
/*  69 */     long maxExpectedSize = mat.getUncompressedSerializedSize();
/*  70 */     long minMappingSize = 131072L;
/*  71 */     if (maxExpectedSize >= minMappingSize && maxExpectedSize <= 2147483647L) {
/*  72 */       sink = Sinks.newMappedFile(file, Casts.sint32(maxExpectedSize));
/*     */     } else {
/*  74 */       sink = Sinks.newStreamingFile(file);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  79 */       mat.writeTo(sink);
/*  80 */       return file;
/*     */     } finally {
/*  82 */       sink.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Mat5File newMatFile() {
/*  88 */     return new Mat5File();
/*     */   }
/*     */   
/*     */   public static Mat5Reader newReader(Source source) {
/*  92 */     return new Mat5Reader(source);
/*     */   }
/*     */   
/*     */   public static Mat5Writer newWriter(Sink sink) {
/*  96 */     return new Mat5Writer(sink);
/*     */   }
/*     */   
/*     */   public static int[] index(int rows, int cols) {
/* 100 */     return new int[] { rows, cols };
/*     */   }
/*     */   
/*     */   public static int[] index(int rows, int cols, int... other) {
/* 104 */     int[] dims = new int[other.length + 2];
/* 105 */     dims[0] = rows;
/* 106 */     dims[1] = cols;
/* 107 */     System.arraycopy(other, 0, dims, 2, other.length);
/* 108 */     return dims;
/*     */   }
/*     */   
/*     */   public static int[] dims(int rows, int cols) {
/* 112 */     return index(rows, cols);
/*     */   }
/*     */   
/*     */   public static int[] dims(int rows, int cols, int... other) {
/* 116 */     return index(rows, cols, other);
/*     */   }
/*     */   
/*     */   public static Cell newCell(int rows, int cols) {
/* 120 */     return newCell(dims(rows, cols));
/*     */   }
/*     */   
/*     */   public static Cell newCell(int[] dims) {
/* 124 */     return (Cell)new MatCell(dims);
/*     */   }
/*     */   
/*     */   public static Struct newStruct() {
/* 128 */     return newStruct(1, 1);
/*     */   }
/*     */   
/*     */   public static Struct newStruct(int rows, int cols) {
/* 132 */     return newStruct(dims(rows, cols));
/*     */   }
/*     */   
/*     */   public static Struct newStruct(int[] dims) {
/* 136 */     return (Struct)new MatStruct(dims);
/*     */   }
/*     */   
/*     */   public static Char newChar(int rows, int cols) {
/* 140 */     return newChar(dims(rows, cols));
/*     */   }
/*     */   
/*     */   public static Char newChar(int rows, int cols, CharEncoding encoding) {
/* 144 */     return (Char)new MatChar(dims(rows, cols), encoding);
/*     */   }
/*     */   
/*     */   public static Char newChar(int[] dims) {
/* 148 */     return newChar(dims, CharEncoding.Utf8);
/*     */   }
/*     */   
/*     */   public static Char newChar(int[] dims, CharEncoding encoding) {
/* 152 */     return (Char)new MatChar(dims, encoding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Char newString(String value) {
/* 163 */     return newString(value, CharEncoding.Utf8);
/*     */   }
/*     */   
/*     */   public static Char newString(String value, CharEncoding encoding) {
/* 167 */     return (Char)new MatChar(new int[] { 1, value.length() }, encoding, CharBuffer.wrap(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Matrix newLogicalScalar(boolean value) {
/* 172 */     Matrix logical = newLogical(1, 1);
/* 173 */     logical.setBoolean(0, value);
/* 174 */     return logical;
/*     */   }
/*     */   
/*     */   public static Matrix newScalar(double value) {
/* 178 */     Matrix matrix = newMatrix(1, 1);
/* 179 */     matrix.setDouble(0, 0, value);
/* 180 */     return matrix;
/*     */   }
/*     */   
/*     */   public static Matrix newComplexScalar(double real, double imaginary) {
/* 184 */     Matrix complex = newComplex(1, 1);
/* 185 */     complex.setDouble(0, real);
/* 186 */     complex.setImaginaryDouble(0, imaginary);
/* 187 */     return complex;
/*     */   }
/*     */   
/*     */   public static Matrix newLogical(int rows, int cols) {
/* 191 */     return newLogical(dims(rows, cols));
/*     */   }
/*     */   
/*     */   public static Matrix newLogical(int[] dims) {
/* 195 */     return newNumerical(dims, MatlabType.Int8, true, false);
/*     */   }
/*     */   
/*     */   public static Matrix newMatrix(int rows, int cols) {
/* 199 */     return newMatrix(dims(rows, cols));
/*     */   }
/*     */   
/*     */   public static Matrix newMatrix(int[] dims) {
/* 203 */     return newMatrix(dims, MatlabType.Double);
/*     */   }
/*     */   
/*     */   public static Matrix newMatrix(int rows, int cols, MatlabType type) {
/* 207 */     return newMatrix(dims(rows, cols), type);
/*     */   }
/*     */   
/*     */   public static Matrix newMatrix(int[] dims, MatlabType type) {
/* 211 */     return newNumerical(dims, type, false, false);
/*     */   }
/*     */   
/*     */   public static Matrix newComplex(int rows, int cols) {
/* 215 */     return newComplex(dims(rows, cols));
/*     */   }
/*     */   
/*     */   public static Matrix newComplex(int rows, int cols, MatlabType type) {
/* 219 */     return newComplex(dims(rows, cols), type);
/*     */   }
/*     */   
/*     */   public static Matrix newComplex(int[] dims) {
/* 223 */     return newComplex(dims, MatlabType.Double);
/*     */   }
/*     */   
/*     */   public static Matrix newComplex(int[] dims, MatlabType type) {
/* 227 */     return newNumerical(dims, type, false, true);
/*     */   }
/*     */   
/*     */   public static int getSerializedSize(String name, Array array) {
/* 231 */     if (array instanceof Mat5Serializable) {
/* 232 */       return ((Mat5Serializable)array).getMat5Size(name);
/*     */     }
/* 234 */     throw new IllegalArgumentException("Array does not support the MAT5 format");
/*     */   }
/*     */   
/*     */   private static Matrix newNumerical(int[] dims, MatlabType type, boolean logical, boolean complex) {
/* 238 */     return newNumerical(dims, type, logical, complex, getDefaultBufferAllocator());
/*     */   }
/*     */   
/*     */   public static Matrix newNumerical(int[] dims, MatlabType type, boolean logical, boolean complex, BufferAllocator allocator) {
/* 242 */     return (Matrix)new MatMatrix(dims, type, logical, 
/* 243 */         createStore(type, dims, allocator), complex ? createStore(type, dims, allocator) : null);
/*     */   }
/*     */   
/*     */   private static NumberStore createStore(MatlabType type, int[] dims, BufferAllocator bufferAllocator) {
/* 247 */     Mat5Type tagType = Mat5Type.fromNumericalType(type);
/* 248 */     int numBytes = AbstractArray.getNumElements(dims) * tagType.bytes();
/* 249 */     ByteBuffer buffer = bufferAllocator.allocate(numBytes);
/* 250 */     return new UniversalNumberStore(tagType, buffer, bufferAllocator);
/*     */   }
/*     */   
/*     */   static BufferAllocator getDefaultBufferAllocator() {
/* 254 */     return DEFAULT_BUFFER_ALLOCATOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 262 */   private static final BufferAllocator DEFAULT_BUFFER_ALLOCATOR = new BufferAllocator()
/*     */     {
/*     */       public ByteBuffer allocate(int numBytes) {
/* 265 */         ByteBuffer buffer = (numBytes <= 4096) ? 
/* 266 */           ByteBuffer.allocate(numBytes) : ByteBuffer.allocateDirect(numBytes);
/* 267 */         buffer.order(Mat5.DEFAULT_ORDER);
/* 268 */         return buffer;
/*     */       }
/*     */ 
/*     */       
/*     */       public void release(ByteBuffer buffer) {
/* 273 */         if (buffer.isDirect()) {
/* 274 */           Unsafe9R.invokeCleaner(buffer);
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteBuffer exportBytes(Matrix matrix) {
/* 292 */     if (matrix instanceof MatMatrix) {
/* 293 */       MatMatrix matMatrix = (MatMatrix)matrix;
/* 294 */       if (matMatrix.getRealStore() instanceof UniversalNumberStore)
/* 295 */         return ((UniversalNumberStore)matMatrix.getRealStore()).getByteBuffer(); 
/*     */     } 
/* 297 */     throw new IllegalStateException("Not implemented for input type");
/*     */   }
/*     */   
/* 300 */   public static final Array EMPTY_MATRIX = (Array)newMatrix(0, 0);
/* 301 */   public static final ByteOrder DEFAULT_ORDER = ByteOrder.nativeOrder();
/*     */   public static final int MATRIX_TAG_SIZE = 8;
/*     */   public static final int FILE_HEADER_SIZE = 128;
/*     */   public static final int REDUCED_FILE_HEADER_SIZE = 8;
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */