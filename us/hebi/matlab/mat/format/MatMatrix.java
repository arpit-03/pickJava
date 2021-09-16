/*     */ package us.hebi.matlab.mat.format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import us.hebi.matlab.mat.types.AbstractMatrixBase;
/*     */ import us.hebi.matlab.mat.types.Array;
/*     */ import us.hebi.matlab.mat.types.MatlabType;
/*     */ import us.hebi.matlab.mat.types.Sink;
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
/*     */ class MatMatrix
/*     */   extends AbstractMatrixBase
/*     */   implements Mat5Serializable
/*     */ {
/*     */   private boolean logical;
/*     */   private final NumberStore real;
/*     */   private final NumberStore imaginary;
/*     */   private final boolean complex;
/*     */   private final MatlabType type;
/*     */   
/*     */   MatMatrix(int[] dims, MatlabType type, boolean logical, NumberStore real, NumberStore imaginary) {
/*  39 */     super(dims);
/*  40 */     this.type = (MatlabType)Preconditions.checkNotNull(type);
/*  41 */     this.logical = logical;
/*     */     
/*  43 */     this.real = (NumberStore)Preconditions.checkNotNull(real);
/*  44 */     if (real.getNumElements() != getNumElements()) {
/*  45 */       throw new IllegalArgumentException("Incorrect number of elements in real store");
/*     */     }
/*  47 */     this.imaginary = imaginary;
/*  48 */     this.complex = (imaginary != null);
/*  49 */     if (this.complex && imaginary.getNumElements() != getNumElements()) {
/*  50 */       throw new IllegalArgumentException("Incorrect number of elements in imaginary store");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public MatlabType getType() {
/*  56 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLogical() {
/*  61 */     return this.logical;
/*     */   }
/*     */   
/*     */   protected void setLogical(boolean value) {
/*  65 */     this.logical = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isComplex() {
/*  70 */     return this.complex;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/*  75 */     return orLogical(this.real.getLong(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLong(int index, long value) {
/*  80 */     this.real.setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/*  85 */     return orLogical(this.real.getDouble(index));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDouble(int index, double value) {
/*  90 */     this.real.setDouble(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getImaginaryLong(int index) {
/*  95 */     return orLogical(this.complex ? this.imaginary.getLong(index) : 0L);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryLong(int index, long value) {
/* 100 */     Preconditions.checkState(this.complex, "Matrix is not complex");
/* 101 */     this.imaginary.setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getImaginaryDouble(int index) {
/* 106 */     return orLogical(this.complex ? this.imaginary.getDouble(index) : 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setImaginaryDouble(int index, double value) {
/* 111 */     Preconditions.checkState(this.complex, "Matrix is not complex");
/* 112 */     this.imaginary.setDouble(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMat5Size(String name) {
/* 117 */     return 8 + 
/* 118 */       Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this) + 
/* 119 */       this.real.getMat5Size() + (
/* 120 */       this.complex ? this.imaginary.getMat5Size() : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 125 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/* 126 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/* 127 */     this.real.writeMat5(sink);
/* 128 */     if (this.complex) this.imaginary.writeMat5(sink);
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   NumberStore getRealStore() {
/* 135 */     return this.real;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 143 */     if (this == Mat5.EMPTY_MATRIX) {
/*     */       return;
/*     */     }
/* 146 */     this.real.close();
/* 147 */     if (this.imaginary != null) {
/* 148 */       this.imaginary.close();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int subHashCode() {
/* 159 */     return Compat.hash(new Object[] { Boolean.valueOf(this.logical), Boolean.valueOf(this.complex), this.type, 
/* 160 */           Integer.valueOf(UniversalNumberStore.hashCodeForType(this.real, this.logical, this.type)), 
/* 161 */           Integer.valueOf(UniversalNumberStore.hashCodeForType(this.imaginary, this.logical, this.type)) });
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 166 */     MatMatrix other = (MatMatrix)otherGuaranteedSameClass;
/*     */     
/* 168 */     return (other.logical == this.logical && 
/* 169 */       other.complex == this.complex && 
/* 170 */       other.type == this.type && 
/* 171 */       UniversalNumberStore.equalForType(other.real, this.real, this.logical, this.type) && 
/* 172 */       UniversalNumberStore.equalForType(other.imaginary, this.imaginary, this.logical, this.type));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */