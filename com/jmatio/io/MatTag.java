/*    */ package com.jmatio.io;
/*    */ 
/*    */ import com.jmatio.common.MatDataTypes;
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
/*    */ class MatTag
/*    */ {
/*    */   protected int type;
/*    */   protected int size;
/*    */   
/*    */   public MatTag(int type, int size) {
/* 21 */     this.type = type;
/* 22 */     this.size = size;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getPadding(int size, boolean compressed) {
/*    */     int padding;
/* 32 */     if (!compressed) {
/*    */       int b;
/*    */       
/* 35 */       padding = ((b = size / sizeOf() % 8 / sizeOf() * sizeOf()) != 0) ? (8 - b) : 0;
/*    */     } else {
/*    */       int b;
/*    */ 
/*    */       
/* 40 */       padding = ((b = size / sizeOf() % 4 / sizeOf() * sizeOf()) != 0) ? (4 - b) : 0;
/*    */     } 
/* 42 */     return padding;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     String s = "[tag: " + MatDataTypes.typeToString(this.type) + " size: " + this.size + "]";
/*    */     
/* 53 */     return s;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int sizeOf() {
/* 62 */     return MatDataTypes.sizeOf(this.type);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */