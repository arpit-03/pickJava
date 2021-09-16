/*    */ package org.joda.time.tz;
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
/*    */ public class ZoneInfoLogger
/*    */ {
/* 23 */   static ThreadLocal<Boolean> cVerbose = new ThreadLocal<Boolean>() {
/*    */       protected Boolean initialValue() {
/* 25 */         return Boolean.FALSE;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean verbose() {
/* 34 */     return ((Boolean)cVerbose.get()).booleanValue();
/*    */   }
/*    */   
/*    */   public static void set(boolean paramBoolean) {
/* 38 */     cVerbose.set(Boolean.valueOf(paramBoolean));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/ZoneInfoLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */