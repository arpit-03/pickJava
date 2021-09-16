/*     */ package us.hebi.matlab.mat.ejml;
/*     */ 
/*     */ import java.util.function.Supplier;
/*     */ import org.ejml.data.DMatrixSparseCSC;
/*     */ import org.ejml.data.FMatrixSparseCSC;
/*     */ import us.hebi.matlab.mat.types.Sparse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class SparseToCscConverter
/*     */   implements Sparse.SparseConsumer
/*     */ {
/*     */   static void convertToFMatrixSparseCSC(Sparse input, FMatrixSparseCSC output) {
/*  36 */     ((SparseToFCscConverter)fCscConverter.get()).convertToFSparseCSC(input, output);
/*     */   }
/*     */   
/*     */   static void convertToDMatrixSparseCSC(Sparse input, DMatrixSparseCSC output) {
/*  40 */     ((SparseToDCscConverter)dCscConverter.get()).convertToDSparseCSC(input, output);
/*     */   }
/*     */   
/*  43 */   private static final ThreadLocal<SparseToFCscConverter> fCscConverter = ThreadLocal.withInitial(new Supplier<SparseToFCscConverter>()
/*     */       {
/*     */         public SparseToCscConverter.SparseToFCscConverter get() {
/*  46 */           return new SparseToCscConverter.SparseToFCscConverter();
/*     */         }
/*     */       });
/*  49 */   private static final ThreadLocal<SparseToDCscConverter> dCscConverter = ThreadLocal.withInitial(new Supplier<SparseToDCscConverter>()
/*     */       {
/*     */         public SparseToCscConverter.SparseToDCscConverter get() {
/*  52 */           return new SparseToCscConverter.SparseToDCscConverter();
/*     */         }
/*     */       });
/*     */   float[] fValues; double[] dValues; private int[] col_idx; private int[] nz_rows; private int valueIx;
/*     */   private int lastColIx;
/*     */   
/*     */   static class SparseToFCscConverter extends SparseToCscConverter { void convertToFSparseCSC(Sparse input, FMatrixSparseCSC output) {
/*  59 */       output.reshape(input.getNumRows(), input.getNumCols(), input.getNumNonZero());
/*  60 */       initializeConversion(output.col_idx, output.nz_rows, output.nz_values, null);
/*  61 */       input.forEach(this);
/*  62 */       finishConversion(output.getNumCols());
/*  63 */       output.indicesSorted = true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setValue(int nzIndex, double value) {
/*  68 */       this.fValues[nzIndex] = (float)value;
/*     */     } }
/*     */ 
/*     */   
/*     */   static class SparseToDCscConverter
/*     */     extends SparseToCscConverter
/*     */   {
/*     */     void convertToDSparseCSC(Sparse input, DMatrixSparseCSC output) {
/*  76 */       output.reshape(input.getNumRows(), input.getNumCols(), input.getNumNonZero());
/*  77 */       initializeConversion(output.col_idx, output.nz_rows, null, output.nz_values);
/*  78 */       input.forEach(this);
/*  79 */       finishConversion(output.getNumCols());
/*  80 */       output.indicesSorted = true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setValue(int nzIndex, double value) {
/*  85 */       this.dValues[nzIndex] = value;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void initializeConversion(int[] col_idx, int[] nz_rows, float[] fValues, double[] dValues) {
/*  91 */     this.col_idx = col_idx;
/*  92 */     this.nz_rows = nz_rows;
/*  93 */     this.fValues = fValues;
/*  94 */     this.dValues = dValues;
/*  95 */     this.valueIx = 0;
/*  96 */     this.lastColIx = 0;
/*     */   }
/*     */   
/*     */   void finishConversion(int numCols) {
/* 100 */     setEmptyColumnsUntil(numCols);
/* 101 */     this.col_idx = null;
/* 102 */     this.nz_rows = null;
/* 103 */     this.fValues = null;
/* 104 */     this.dValues = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(int row, int col, double real, double imaginary) {
/* 110 */     this.nz_rows[this.valueIx] = row;
/* 111 */     setValue(this.valueIx, real);
/*     */ 
/*     */     
/* 114 */     setEmptyColumnsUntil(col);
/*     */ 
/*     */     
/* 117 */     this.col_idx[col + 1] = this.valueIx + 1;
/* 118 */     this.valueIx++;
/* 119 */     this.lastColIx = col;
/*     */   }
/*     */   
/*     */   protected abstract void setValue(int paramInt, double paramDouble);
/*     */   
/*     */   private void setEmptyColumnsUntil(int col) {
/* 125 */     while (this.lastColIx < col) {
/* 126 */       this.col_idx[this.lastColIx + 1] = this.valueIx;
/* 127 */       this.lastColIx++;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/SparseToCscConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */