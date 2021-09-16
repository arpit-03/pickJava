/*    */ package us.hebi.matlab.mat.ejml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.ejml.data.MatrixSparse;
/*    */ import us.hebi.matlab.mat.format.Mat5Type;
/*    */ import us.hebi.matlab.mat.types.MatlabType;
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
/*    */ abstract class AbstractSparseWrapper<M extends MatrixSparse>
/*    */   extends AbstractMatrixWrapper<M>
/*    */ {
/*    */   AbstractSparseWrapper(M matrix) {
/* 36 */     super(matrix);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getMat5DataSize() {
/* 41 */     return Mat5Type.Int32.computeSerializedSize(getNumRowIndices()) + 
/* 42 */       Mat5Type.Int32.computeSerializedSize(getNumColIndices()) + 
/* 43 */       getMat5SparseNonZeroDataSize();
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract int getMat5SparseNonZeroDataSize();
/*    */ 
/*    */   
/*    */   protected void writeMat5Data(Sink sink) throws IOException {
/* 51 */     Mat5Type.Int32.writeTag(getNumRowIndices(), sink);
/* 52 */     sink.writeInts(getRowIndices0(), 0, getNumRowIndices());
/* 53 */     Mat5Type.Int32.writePadding(getNumRowIndices(), sink);
/*    */ 
/*    */     
/* 56 */     Mat5Type.Int32.writeTag(getNumColIndices(), sink);
/* 57 */     sink.writeInts(getColIndices(), 0, getNumColIndices());
/* 58 */     Mat5Type.Int32.writePadding(getNumColIndices(), sink);
/*    */ 
/*    */     
/* 61 */     writeMat5SparseNonZeroData(sink);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void writeMat5SparseNonZeroData(Sink paramSink) throws IOException;
/*    */   
/*    */   public MatlabType getType() {
/* 68 */     return MatlabType.Sparse;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getNzMax() {
/* 73 */     return Math.max(1, ((MatrixSparse)this.matrix).getNonZeroLength());
/*    */   }
/*    */ 
/*    */   
/*    */   private int getNumRowIndices() {
/* 78 */     return getNzMax();
/*    */   }
/*    */ 
/*    */   
/*    */   private int[] getRowIndices0() {
/* 83 */     return (((MatrixSparse)this.matrix).getNonZeroLength() == 0) ? EMPTY_ROW_INDEX : getRowIndices();
/*    */   }
/*    */   
/*    */   private int getNumColIndices() {
/* 87 */     return ((MatrixSparse)this.matrix).getNumCols() + 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 94 */   private static final int[] EMPTY_ROW_INDEX = new int[1];
/*    */   
/*    */   protected abstract int[] getRowIndices();
/*    */   
/*    */   protected abstract int[] getColIndices();
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/AbstractSparseWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */