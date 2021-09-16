/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import us.hebi.matlab.mat.types.Array;
/*    */ import us.hebi.matlab.mat.types.MatlabType;
/*    */ import us.hebi.matlab.mat.types.ObjectStruct;
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
/*    */ class MatObjectStruct
/*    */   extends MatStruct
/*    */   implements ObjectStruct
/*    */ {
/*    */   private final String className;
/*    */   
/*    */   MatObjectStruct(int[] dims, String className, String[] names, Array[][] values) {
/* 34 */     super(dims, names, values);
/* 35 */     this.className = className;
/*    */   }
/*    */ 
/*    */   
/*    */   public MatlabType getType() {
/* 40 */     return MatlabType.Object;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPackageName() {
/* 45 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClassName() {
/* 50 */     return this.className;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int subHashCode() {
/* 57 */     return 31 * this.className.hashCode() + super.subHashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean subEqualsGuaranteedSameClass(Object otherGuaranteedSameClass) {
/* 62 */     MatObjectStruct other = (MatObjectStruct)otherGuaranteedSameClass;
/* 63 */     return (other.className.equals(this.className) && super.subEqualsGuaranteedSameClass(other));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/MatObjectStruct.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */