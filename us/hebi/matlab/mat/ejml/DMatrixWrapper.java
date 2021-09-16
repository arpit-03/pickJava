/*    */ package us.hebi.matlab.mat.ejml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.ejml.data.DMatrix;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ class DMatrixWrapper
/*    */   extends AbstractMatrixWrapper<DMatrix>
/*    */ {
/*    */   protected int getMat5DataSize() {
/* 39 */     return Mat5Type.Double.computeSerializedSize(this.matrix.getNumElements());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeMat5Data(Sink sink) throws IOException {
/* 45 */     Mat5Type.Double.writeTag(this.matrix.getNumElements(), sink);
/* 46 */     for (int col = 0; col < this.matrix.getNumCols(); col++) {
/* 47 */       for (int row = 0; row < this.matrix.getNumRows(); row++) {
/* 48 */         sink.writeDouble(this.matrix.unsafe_get(row, col));
/*    */       }
/*    */     } 
/* 51 */     Mat5Type.Double.writePadding(this.matrix.getNumElements(), sink);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 56 */     return MatlabType.Double;
/*    */   }
/*    */   
/*    */   DMatrixWrapper(DMatrix matrix) {
/* 60 */     super(matrix);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/DMatrixWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */