/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ import us.hebi.matlab.mat.types.AbstractCell;
/*    */ import us.hebi.matlab.mat.types.Array;
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
/*    */ class MatCell
/*    */   extends AbstractCell
/*    */   implements Mat5Serializable
/*    */ {
/*    */   MatCell(int[] dims) {
/* 39 */     super(dims, new Array[getNumElements(dims)]);
/* 40 */     Arrays.fill((Object[])this.contents, getEmptyValue());
/*    */   }
/*    */   
/*    */   MatCell(int[] dims, Array[] contents) {
/* 44 */     super(dims, contents);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Array getEmptyValue() {
/* 49 */     return Mat5.EMPTY_MATRIX;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMat5Size(String name) {
/* 54 */     int size = 8;
/* 55 */     size += Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this);
/* 56 */     for (int i = 0; i < getNumElements(); i++) {
/* 57 */       size += Mat5WriteUtil.computeArraySize(get(i));
/*    */     }
/* 59 */     return size;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 64 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/* 65 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/* 66 */     for (int i = 0; i < getNumElements(); i++) {
/* 67 */       Mat5WriteUtil.writeNestedArray(get(i), sink);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected int subHashCode() {
/* 73 */     return Arrays.hashCode((Object[])this.contents);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 78 */     MatCell other = (MatCell)otherGuaranteedSameClass;
/* 79 */     return Arrays.equals((Object[])other.contents, (Object[])this.contents);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatCell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */