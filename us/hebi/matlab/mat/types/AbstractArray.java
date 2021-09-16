/*     */ package us.hebi.matlab.mat.types;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public abstract class AbstractArray
/*     */   implements Array
/*     */ {
/*     */   protected final int[] dims;
/*     */   private final int[] dimStrides;
/*     */   
/*     */   public int[] getDimensions() {
/*  35 */     return this.dims;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumDimensions() {
/*  40 */     return (getDimensions()).length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumRows() {
/*  45 */     return getDimensions()[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumCols() {
/*  50 */     return getDimensions()[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNumElements() {
/*  55 */     return getNumElements(getDimensions());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumElements(int[] dimensions) {
/*  62 */     int count = dimensions[0];
/*  63 */     for (int i = 1; i < dimensions.length; i++) {
/*  64 */       count *= dimensions[i];
/*     */     }
/*  66 */     return count;
/*     */   }
/*     */   
/*     */   protected static int[] calculateColMajorStrides(int[] dimensions) {
/*  70 */     int[] dimStrides = new int[dimensions.length];
/*  71 */     dimStrides[0] = 1;
/*  72 */     for (int i = 0; i < dimStrides.length - 1; i++) {
/*  73 */       dimStrides[i + 1] = dimensions[i] * dimStrides[i];
/*     */     }
/*  75 */     return dimStrides;
/*     */   }
/*     */   
/*     */   protected int getColumnMajorIndex(int row, int col) {
/*  79 */     checkNumDimensions(2);
/*  80 */     int ix0 = checkIndexBounds(row, 0);
/*  81 */     int ix1 = checkIndexBounds(col, 1);
/*  82 */     return ix0 * this.dimStrides[0] + ix1 * this.dimStrides[1];
/*     */   }
/*     */   
/*     */   protected int getColumnMajorIndex(int[] indices) {
/*  86 */     checkNumDimensions(indices.length);
/*  87 */     int index = 0;
/*  88 */     for (int i = 0; i < indices.length; i++) {
/*  89 */       index += this.dimStrides[i] * checkIndexBounds(indices[i], i);
/*     */     }
/*  91 */     return index;
/*     */   }
/*     */   
/*     */   protected void checkNumDimensions(int numDim) {
/*  95 */     if (numDim != this.dimStrides.length)
/*  96 */       throw new IllegalArgumentException("Expected " + this.dimStrides.length + " dimensions. Found: " + numDim); 
/*     */   }
/*     */   
/*     */   protected int checkIndexBounds(int index, int dim) {
/* 100 */     if (index >= 0 && index < this.dims[dim])
/* 101 */       return index; 
/* 102 */     String msg = String.format("Index exceeds matrix dimension %d. %d/%d", new Object[] { Integer.valueOf(dim), Integer.valueOf(index), Integer.valueOf(this.dims[dim] - 1) });
/* 103 */     throw new IllegalArgumentException(msg);
/*     */   }
/*     */   
/*     */   protected AbstractArray(int[] dims) {
/* 107 */     this.dims = (int[])Preconditions.checkNotNull(dims);
/* 108 */     Preconditions.checkArgument((dims.length >= 2), "Every array must have at least two dimensions");
/* 109 */     this.dimStrides = calculateColMajorStrides(dims);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 114 */     return StringHelper.toString(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int hashCode() {
/* 125 */     int prime = 31;
/* 126 */     int result = 1;
/* 127 */     result = 31 * result + Arrays.hashCode(this.dims);
/* 128 */     result = 31 * result + subHashCode();
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean equals(Object other) {
/* 134 */     if (other == this)
/* 135 */       return true; 
/* 136 */     if (other == null)
/* 137 */       return false; 
/* 138 */     if (other.getClass().equals(getClass())) {
/* 139 */       AbstractArray otherArray = (AbstractArray)other;
/* 140 */       return (Arrays.equals(otherArray.dims, this.dims) && 
/* 141 */         subEqualsGuaranteedSameClass(other));
/*     */     } 
/* 143 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract int subHashCode();
/*     */   
/*     */   protected abstract boolean subEqualsGuaranteedSameClass(Object paramObject);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */