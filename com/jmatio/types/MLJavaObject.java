/*    */ package com.jmatio.types;
/*    */ 
/*    */ public class MLJavaObject
/*    */   extends MLArray
/*    */ {
/*    */   private final Object o;
/*    */   private final String className;
/*    */   
/*    */   public MLJavaObject(String name, String className, Object o) {
/* 10 */     super(name, new int[] { 1, 1 }, 17, 0);
/* 11 */     this.o = o;
/* 12 */     this.className = className;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getClassName() {
/* 17 */     return this.className;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getObject() {
/* 22 */     return this.o;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLJavaObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */