/*    */ package org.jfree.util;
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
/*    */ public interface LogTarget
/*    */ {
/*    */   public static final int ERROR = 0;
/*    */   public static final int WARN = 1;
/*    */   public static final int INFO = 2;
/*    */   public static final int DEBUG = 3;
/* 75 */   public static final String[] LEVELS = new String[] { "ERROR: ", "WARN:  ", "INFO:  ", "DEBUG: " };
/*    */   
/*    */   void log(int paramInt, Object paramObject);
/*    */   
/*    */   void log(int paramInt, Object paramObject, Exception paramException);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/util/LogTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */