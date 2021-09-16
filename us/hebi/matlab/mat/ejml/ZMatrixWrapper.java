/*    */ package us.hebi.matlab.mat.ejml;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.ejml.data.ZMatrix;
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
/*    */ class ZMatrixWrapper
/*    */   extends AbstractMatrixWrapper<ZMatrix>
/*    */ {
/*    */   protected int getMat5DataSize() {
/* 39 */     return 2 * Mat5Type.Double.computeSerializedSize(getNumElements());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeMat5Data(Sink sink) throws IOException {
/* 45 */     Mat5Type.Double.writeTag(getNumElements(), sink); int col;
/* 46 */     for (col = 0; col < this.matrix.getNumCols(); col++) {
/* 47 */       for (int row = 0; row < this.matrix.getNumRows(); row++) {
/* 48 */         sink.writeDouble(this.matrix.getReal(row, col));
/*    */       }
/*    */     } 
/* 51 */     Mat5Type.Double.writePadding(getNumElements(), sink);
/*    */ 
/*    */     
/* 54 */     Mat5Type.Double.writeTag(getNumElements(), sink);
/* 55 */     for (col = 0; col < this.matrix.getNumCols(); col++) {
/* 56 */       for (int row = 0; row < this.matrix.getNumRows(); row++) {
/* 57 */         sink.writeDouble(this.matrix.getImag(row, col));
/*    */       }
/*    */     } 
/* 60 */     Mat5Type.Double.writePadding(getNumElements(), sink);
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 65 */     return MatlabType.Double;
/*    */   }
/*    */   
/*    */   ZMatrixWrapper(ZMatrix matrix) {
/* 69 */     super(matrix);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isComplex() {
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/ejml/ZMatrixWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */