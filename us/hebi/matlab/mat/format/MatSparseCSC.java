/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import us.hebi.matlab.mat.types.AbstractSparse;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Sink;
/*     */ import us.hebi.matlab.mat.types.Sparse;
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
/*     */ class MatSparseCSC
/*     */   extends AbstractSparse
/*     */   implements Sparse, Mat5Serializable, Mat5Serializable.Mat5Attributes
/*     */ {
/*     */   private final NumberStore columnIndices;
/*     */   private final NumberStore rowIndices;
/*     */   private final NumberStore real;
/*     */   private final NumberStore imaginary;
/*     */   private final boolean logical;
/*     */   private final boolean complex;
/*     */   final int nzMax;
/*     */   
/*     */   MatSparseCSC(int[] dims, boolean logical, int nzMax, NumberStore real, NumberStore imaginary, NumberStore rowIndices, NumberStore columnIndices) {
/*  54 */     super(dims);
/*     */     
/*  56 */     if (columnIndices.getNumElements() != getNumCols() + 1)
/*  57 */       throw new IllegalArgumentException("Expected (numCols + 1) column indices"); 
/*  58 */     if (rowIndices.getNumElements() != nzMax)
/*  59 */       throw new IllegalArgumentException("Expected nzMax row indices"); 
/*  60 */     if (real.getNumElements() > nzMax) {
/*  61 */       throw new IllegalArgumentException("Expected data with fewer than " + nzMax + " elements");
/*     */     }
/*  63 */     this.real = (NumberStore)Preconditions.checkNotNull(real);
/*  64 */     this.imaginary = imaginary;
/*  65 */     this.nzMax = nzMax;
/*     */ 
/*     */     
/*  68 */     this.complex = (imaginary != null);
/*  69 */     this.logical = logical;
/*     */ 
/*     */     
/*  72 */     this.columnIndices = columnIndices;
/*     */ 
/*     */     
/*  75 */     this.rowIndices = rowIndices;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLogical() {
/*  81 */     return this.logical;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplex() {
/*  86 */     return this.complex;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/*  91 */     return orLogical((index < 0) ? getDefaultValue() : this.real.getDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDouble(int index, double value) {
/*  96 */     throw new IllegalStateException("This sparse matrix can't be modified.");
/*     */   }
/*     */ 
/*     */   
/*     */   public double getImaginaryDouble(int index) {
/* 101 */     if (!this.complex) return 0.0D; 
/* 102 */     return orLogical((index < 0) ? getDefaultValue() : this.imaginary.getDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryDouble(int index, double value) {
/* 107 */     throw new IllegalStateException("This sparse matrix can't be modified.");
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
/*     */   protected int getColumnMajorIndex(int row, int col) {
/* 125 */     int fromIndex = Casts.sint32(this.columnIndices.getLong(col));
/* 126 */     int toIndex = Casts.sint32(this.columnIndices.getLong(col + 1));
/* 127 */     return searchRowBinary(fromIndex, toIndex, row);
/*     */   }
/*     */   
/*     */   protected int searchRowLinear(int fromIndex, int toIndex, int row) {
/* 131 */     for (int i = fromIndex; i < toIndex; i++) {
/* 132 */       if (this.rowIndices.getLong(i) == row)
/* 133 */         return i; 
/*     */     } 
/* 135 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int searchRowBinary(int fromIndex, int toIndex, int row) {
/* 140 */     int low = fromIndex;
/* 141 */     int high = toIndex - 1;
/*     */     
/* 143 */     while (low <= high) {
/* 144 */       int mid = low + high >>> 1;
/* 145 */       int midVal = Casts.sint32(this.rowIndices.getLong(mid));
/*     */       
/* 147 */       if (midVal < row) {
/* 148 */         low = mid + 1; continue;
/* 149 */       }  if (midVal > row) {
/* 150 */         high = mid - 1; continue;
/*     */       } 
/* 152 */       return mid;
/*     */     } 
/* 154 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNzMax() {
/* 159 */     return this.nzMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumNonZero() {
/* 164 */     return this.real.getNumElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Sparse.SparseConsumer action) {
/* 169 */     int i0 = (int)this.columnIndices.getLong(0);
/* 170 */     for (int col = 0; col < getNumCols(); col++) {
/* 171 */       int i1 = (int)this.columnIndices.getLong(col + 1);
/*     */       
/* 173 */       for (int i = i0; i < i1; i++) {
/* 174 */         int row = (int)this.rowIndices.getLong(i);
/* 175 */         double imag = this.complex ? this.imaginary.getDouble(i) : 0.0D;
/* 176 */         action.accept(row, col, this.real.getDouble(i), imag);
/*     */       } 
/*     */       
/* 179 */       i0 = i1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 186 */     this.rowIndices.close();
/* 187 */     this.columnIndices.close();
/* 188 */     this.real.close();
/* 189 */     if (this.complex) this.imaginary.close();
/*     */   
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
/*     */   public int getMat5Size(String name) {
/* 202 */     return 8 + 
/* 203 */       Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this) + 
/* 204 */       this.rowIndices.getMat5Size() + 
/* 205 */       this.columnIndices.getMat5Size() + 
/* 206 */       this.real.getMat5Size() + (
/* 207 */       this.complex ? this.imaginary.getMat5Size() : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 212 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/* 213 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/* 214 */     this.rowIndices.writeMat5(sink);
/* 215 */     this.columnIndices.writeMat5(sink);
/* 216 */     this.real.writeMat5(sink);
/* 217 */     if (this.complex) this.imaginary.writeMat5(sink);
/*     */   
/*     */   }
/*     */   
/*     */   protected int subHashCode() {
/* 222 */     return Compat.hash(new Object[] { Integer.valueOf(this.nzMax), Boolean.valueOf(this.logical), Boolean.valueOf(this.complex), 
/* 223 */           Integer.valueOf(UniversalNumberStore.hashCodeForType(this.imaginary, this.logical, MatlabType.Double)), 
/* 224 */           Integer.valueOf(UniversalNumberStore.hashCodeForType(this.real, this.logical, MatlabType.Double)), 
/* 225 */           Integer.valueOf(UniversalNumberStore.hashCodeForType(this.rowIndices, this.logical, MatlabType.Int64)), 
/* 226 */           Integer.valueOf(UniversalNumberStore.hashCodeForType(this.columnIndices, this.logical, MatlabType.Int64)) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 231 */     MatSparseCSC other = (MatSparseCSC)otherGuaranteedSameClass;
/*     */     
/* 233 */     return (other.nzMax == this.nzMax && 
/* 234 */       other.logical == this.logical && 
/* 235 */       other.complex == this.complex && 
/* 236 */       UniversalNumberStore.equalForType(other.imaginary, this.imaginary, this.logical, MatlabType.Double) && 
/* 237 */       UniversalNumberStore.equalForType(other.real, this.real, this.logical, MatlabType.Double) && 
/* 238 */       UniversalNumberStore.equalForType(other.rowIndices, this.rowIndices, this.logical, MatlabType.Int64) && 
/* 239 */       UniversalNumberStore.equalForType(other.columnIndices, this.columnIndices, this.logical, MatlabType.Int64));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatSparseCSC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */