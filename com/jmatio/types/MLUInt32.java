/*    */ package com.jmatio.types;
/*    */ 
/*    */ 
/*    */ public class MLUInt32
/*    */   extends MLInt32
/*    */ {
/*    */   public MLUInt32(String name, int[] dims, int type, int attributes) {
/*  8 */     super(name, dims, type, attributes);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLUInt32(String name, int[] vals, int m) {
/* 13 */     super(name, vals, m);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLUInt32(String name, int[] dims) {
/* 18 */     super(name, dims);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLUInt32(String name, int[][] vals) {
/* 23 */     super(name, vals);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLUInt32(String name, Integer[] vals, int m) {
/* 28 */     super(name, vals, m);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLUInt32.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */