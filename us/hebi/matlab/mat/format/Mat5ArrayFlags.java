/*    */ package us.hebi.matlab.mat.format;
/*    */ 
/*    */ import us.hebi.matlab.mat.types.Array;
/*    */ import us.hebi.matlab.mat.types.MatlabType;
/*    */ import us.hebi.matlab.mat.types.Matrix;
/*    */ import us.hebi.matlab.mat.types.Opaque;
/*    */ import us.hebi.matlab.mat.util.Preconditions;
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
/*    */ class Mat5ArrayFlags
/*    */ {
/*    */   private static final int FLAG_MASK_TYPE_ID = 255;
/*    */   private static final int FLAG_BIT_LOGICAL = 512;
/*    */   private static final int FLAG_BIT_GLOBAL = 1024;
/*    */   private static final int FLAG_BIT_COMPLEX = 2048;
/*    */   
/*    */   static int[] forArray(boolean global, Array array) {
/* 38 */     if (array instanceof Mat5Serializable.Mat5Attributes) {
/* 39 */       Mat5Serializable.Mat5Attributes attr = (Mat5Serializable.Mat5Attributes)array;
/* 40 */       return create(array.getType(), global, attr.isLogical(), attr.isComplex(), attr.getNzMax());
/*    */     } 
/* 42 */     Preconditions.checkArgument(!(array instanceof us.hebi.matlab.mat.types.Sparse), "Sparse matrices must implement Mat5Attributes");
/*    */     
/* 44 */     boolean logical = (array instanceof Matrix && ((Matrix)array).isLogical());
/* 45 */     boolean complex = (array instanceof Matrix && ((Matrix)array).isComplex());
/* 46 */     return create(array.getType(), global, logical, complex, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static int[] forOpaque(boolean global, Opaque opaque) {
/* 54 */     return create(MatlabType.Opaque, false, false, false, 0);
/*    */   }
/*    */   
/*    */   private static int[] create(MatlabType type, boolean global, boolean logical, boolean complex, int nzMax) {
/* 58 */     int attributes = type.id() & 0xFF;
/* 59 */     if (logical) attributes |= 0x200; 
/* 60 */     if (global) attributes |= 0x400; 
/* 61 */     if (complex) attributes |= 0x800; 
/* 62 */     return new int[] { attributes, nzMax };
/*    */   }
/*    */   
/*    */   static MatlabType getType(int[] arrayFlags) {
/* 66 */     return MatlabType.fromId(arrayFlags[0] & 0xFF);
/*    */   }
/*    */   
/*    */   static boolean isComplex(int[] arrayFlags) {
/* 70 */     return ((arrayFlags[0] & 0x800) != 0);
/*    */   }
/*    */   
/*    */   static boolean isGlobal(int[] arrayFlags) {
/* 74 */     return ((arrayFlags[0] & 0x400) != 0);
/*    */   }
/*    */   
/*    */   static boolean isLogical(int[] arrayFlags) {
/* 78 */     return ((arrayFlags[0] & 0x200) != 0);
/*    */   }
/*    */   
/*    */   static int getNzMax(int[] arrayFlags) {
/* 82 */     return arrayFlags[1];
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/format/Mat5ArrayFlags.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */