/*    */ package us.hebi.matlab.mat.ejml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.ejml.data.BMatrixRMaj;
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
/*    */ class BMatrixRMajWrapper
/*    */   extends AbstractMatrixWrapper<BMatrixRMaj>
/*    */ {
/*    */   protected int getMat5DataSize() {
/* 39 */     return Mat5Type.UInt8.computeSerializedSize(this.matrix.getNumElements());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeMat5Data(Sink sink) throws IOException {
/* 44 */     Mat5Type.UInt8.writeTag(this.matrix.getNumElements(), sink);
/* 45 */     for (int col = 0; col < this.matrix.getNumCols(); col++) {
/* 46 */       for (int row = 0; row < this.matrix.getNumRows(); row++) {
/* 47 */         int value = this.matrix.unsafe_get(row, col) ? 1 : 0;
/* 48 */         sink.writeByte((byte)value);
/*    */       } 
/*    */     } 
/* 51 */     Mat5Type.UInt8.writePadding(this.matrix.getNumElements(), sink);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 56 */     return MatlabType.UInt8;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isLogical() {
/* 61 */     return true;
/*    */   }
/*    */   
/*    */   protected BMatrixRMajWrapper(BMatrixRMaj matrix) {
/* 65 */     super(matrix);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/BMatrixRMajWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */