/*    */ package org.junit.internal;
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
/*    */ public class Classes
/*    */ {
/*    */   public static Class<?> getClass(String className) throws ClassNotFoundException {
/* 16 */     return Class.forName(className, true, Thread.currentThread().getContextClassLoader());
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/internal/Classes.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */