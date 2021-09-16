/*    */ package com.jmatio.types;
/*    */ 
/*    */ public class MLEmptyArray
/*    */   extends MLArray
/*    */ {
/*    */   public MLEmptyArray() {
/*  7 */     this(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLEmptyArray(String name) {
/* 12 */     this(name, new int[] { 0, 0 }, 6, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLEmptyArray(String name, int[] dims, int type, int attributes) {
/* 17 */     super(name, dims, type, attributes);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLEmptyArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */