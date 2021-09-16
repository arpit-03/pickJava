/*    */ package us.hebi.matlab.mat.types;
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
/*    */ public abstract class AbstractSparse
/*    */   extends AbstractMatrixBase
/*    */   implements Sparse
/*    */ {
/*    */   protected double defaultValue;
/*    */   
/*    */   protected AbstractSparse(int[] dims) {
/* 30 */     super(dims);
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
/* 76 */     this.defaultValue = 0.0D;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getNzMax() {
/*    */     return getNumNonZero();
/*    */   }
/*    */   
/*    */   public MatlabType getType() {
/*    */     return MatlabType.Sparse;
/*    */   }
/*    */   
/*    */   public long getLong(int index) {
/*    */     return (long)getDouble(index);
/*    */   }
/*    */   
/*    */   public void setLong(int index, long value) {
/*    */     setDouble(index, value);
/*    */   }
/*    */   
/*    */   public long getImaginaryLong(int index) {
/*    */     return (long)getImaginaryDouble(index);
/*    */   }
/*    */   
/*    */   public void setImaginaryLong(int index, long value) {
/*    */     setImaginaryDouble(index, value);
/*    */   }
/*    */   
/*    */   public double getDefaultValue() {
/*    */     return this.defaultValue;
/*    */   }
/*    */   
/*    */   public void setDefaultValue(double value) {
/*    */     this.defaultValue = value;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractSparse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */