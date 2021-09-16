/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import us.hebi.matlab.mat.types.AbstractArray;
/*    */ import us.hebi.matlab.mat.types.Array;
/*    */ import us.hebi.matlab.mat.types.FunctionHandle;
/*    */ import us.hebi.matlab.mat.types.MatlabType;
/*    */ import us.hebi.matlab.mat.types.Sink;
/*    */ import us.hebi.matlab.mat.types.Struct;
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
/*    */ class MatFunction
/*    */   extends AbstractArray
/*    */   implements FunctionHandle, Mat5Serializable
/*    */ {
/*    */   final Struct content;
/*    */   
/*    */   MatFunction(Struct content) {
/* 36 */     super(new int[] { 1, 1 });
/* 37 */     this.content = content;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 42 */     return MatlabType.Function;
/*    */   }
/*    */ 
/*    */   
/*    */   public Struct getContent() {
/* 47 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMat5Size(String name) {
/* 52 */     return 8 + 
/* 53 */       Mat5WriteUtil.computeArrayHeaderSize(name, (Array)this) + 
/* 54 */       Mat5WriteUtil.computeArraySize((Array)getContent());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 59 */     Mat5WriteUtil.writeMatrixTag(name, this, sink);
/* 60 */     Mat5WriteUtil.writeArrayHeader(name, isGlobal, (Array)this, sink);
/* 61 */     Mat5WriteUtil.writeNestedArray((Array)this.content, sink);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 66 */     this.content.close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int subHashCode() {
/* 73 */     return this.content.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 78 */     MatFunction other = (MatFunction)otherGuaranteedSameClass;
/* 79 */     return other.content.equals(this.content);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatFunction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */