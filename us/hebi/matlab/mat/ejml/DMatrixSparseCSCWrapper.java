/*    */ package us.hebi.matlab.mat.ejml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.ejml.data.DMatrixSparseCSC;
/*    */ import us.hebi.matlab.mat.format.Mat5Type;
/*    */ import us.hebi.matlab.mat.types.Sink;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class DMatrixSparseCSCWrapper
/*    */   extends AbstractSparseWrapper<DMatrixSparseCSC>
/*    */ {
/*    */   protected int getMat5SparseNonZeroDataSize() {
/* 38 */     return Mat5Type.Double.computeSerializedSize(this.matrix.getNonZeroLength());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeMat5SparseNonZeroData(Sink sink) throws IOException {
/* 44 */     Mat5Type.Double.writeTag(this.matrix.getNonZeroLength(), sink);
/* 45 */     sink.writeDoubles(this.matrix.nz_values, 0, this.matrix.getNonZeroLength());
/* 46 */     Mat5Type.Double.writePadding(this.matrix.getNonZeroLength(), sink);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int[] getRowIndices() {
/* 51 */     return this.matrix.nz_rows;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int[] getColIndices() {
/* 56 */     return this.matrix.col_idx;
/*    */   }
/*    */   
/*    */   DMatrixSparseCSCWrapper(DMatrixSparseCSC matrix) {
/* 60 */     super(matrix);
/* 61 */     if (!matrix.indicesSorted)
/* 62 */       matrix.sortIndices(null); 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/DMatrixSparseCSCWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */