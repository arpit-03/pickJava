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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractMatFileBase
/*    */   implements MatFile
/*    */ {
/*    */   public Matrix getMatrix(String name) {
/* 33 */     return getArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Sparse getSparse(String name) {
/* 38 */     return getArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Char getChar(String name) {
/* 43 */     return getArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Struct getStruct(String name) {
/* 48 */     return getArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectStruct getObject(String name) {
/* 53 */     return getArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Cell getCell(String name) {
/* 58 */     return getArray(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix getMatrix(int index) {
/* 63 */     return getArray(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public Sparse getSparse(int index) {
/* 68 */     return getArray(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public Char getChar(int index) {
/* 73 */     return getArray(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public Struct getStruct(int index) {
/* 78 */     return getArray(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectStruct getObject(int index) {
/* 83 */     return getArray(index);
/*    */   }
/*    */ 
/*    */   
/*    */   public Cell getCell(int index) {
/* 88 */     return getArray(index);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/types/AbstractMatFileBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */