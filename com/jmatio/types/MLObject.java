/*    */ package com.jmatio.types;
/*    */ 
/*    */ public class MLObject
/*    */   extends MLArray
/*    */ {
/*    */   private final MLStructure o;
/*    */   private final String className;
/*    */   
/*    */   public MLObject(String name, String className, MLStructure o) {
/* 10 */     super(name, new int[] { 1, 1 }, 3, 0);
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
/*    */   public MLStructure getObject() {
/* 22 */     return this.o;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */