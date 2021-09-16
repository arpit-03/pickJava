/*    */ package us.hebi.matlab.mat.ejml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.ejml.data.CMatrix;
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
/*    */ class CMatrixWrapper
/*    */   extends AbstractMatrixWrapper<CMatrix>
/*    */ {
/*    */   protected int getMat5DataSize() {
/* 39 */     return 2 * Mat5Type.Single.computeSerializedSize(getNumElements());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeMat5Data(Sink sink) throws IOException {
/* 45 */     Mat5Type.Single.writeTag(getNumElements(), sink); int col;
/* 46 */     for (col = 0; col < this.matrix.getNumCols(); col++) {
/* 47 */       for (int row = 0; row < this.matrix.getNumRows(); row++) {
/* 48 */         sink.writeFloat(this.matrix.getReal(row, col));
/*    */       }
/*    */     } 
/* 51 */     Mat5Type.Single.writePadding(getNumElements(), sink);
/*    */ 
/*    */     
/* 54 */     Mat5Type.Single.writeTag(getNumElements(), sink);
/* 55 */     for (col = 0; col < this.matrix.getNumCols(); col++) {
/* 56 */       for (int row = 0; row < this.matrix.getNumRows(); row++) {
/* 57 */         sink.writeFloat(this.matrix.getImag(row, col));
/*    */       }
/*    */     } 
/* 60 */     Mat5Type.Single.writePadding(getNumElements(), sink);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 65 */     return MatlabType.Double;
/*    */   }
/*    */   
/*    */   CMatrixWrapper(CMatrix matrix) {
/* 69 */     super(matrix);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isComplex() {
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/CMatrixWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */