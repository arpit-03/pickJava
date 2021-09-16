/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import us.hebi.matlab.mat.types.AbstractArray;
/*    */ import us.hebi.matlab.mat.types.Array;
/*    */ import us.hebi.matlab.mat.types.MatlabType;
/*    */ import us.hebi.matlab.mat.types.Opaque;
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
/*    */ class MatOpaque
/*    */   extends AbstractArray
/*    */   implements Opaque, Mat5Serializable
/*    */ {
/*    */   private final String objectType;
/*    */   private final String className;
/*    */   private final Array content;
/*    */   
/*    */   MatOpaque(String objectType, String className, Array content) {
/* 36 */     super(SINGLE_DIM);
/* 37 */     this.content = content;
/* 38 */     this.className = className;
/* 39 */     this.objectType = objectType;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 44 */     return MatlabType.Opaque;
/*    */   }
/*    */   
/*    */   public String getObjectType() {
/* 48 */     return this.objectType;
/*    */   }
/*    */   
/*    */   public String getClassName() {
/* 52 */     return this.className;
/*    */   }
/*    */   
/*    */   public Array getContent() {
/* 56 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMat5Size(String name) {
/* 61 */     return Mat5WriteUtil.computeOpaqueSize(name, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeMat5(String name, boolean isGlobal, Sink sink) throws IOException {
/* 66 */     Mat5WriteUtil.writeOpaque(name, isGlobal, this, sink);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() throws IOException {
/* 71 */     this.content.close();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   private static final int[] SINGLE_DIM = new int[] { 1, 1 };
/*    */ 
/*    */   
/*    */   protected int subHashCode() {
/* 81 */     return Compat.hash(new Object[] { this.objectType, this.className, this.content });
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 86 */     MatOpaque other = (MatOpaque)otherGuaranteedSameClass;
/* 87 */     return (other.objectType.equals(this.objectType) && 
/* 88 */       other.className.equals(this.className) && 
/* 89 */       other.content.equals(this.content));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatOpaque.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */