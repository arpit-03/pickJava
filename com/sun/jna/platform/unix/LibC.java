/*    */ package com.sun.jna.platform.unix;
/*    */ 
/*    */ import com.sun.jna.Library;
/*    */ import com.sun.jna.Native;
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
/*    */ public interface LibC
/*    */   extends LibCAPI, Library
/*    */ {
/*    */   public static final String NAME = "c";
/* 35 */   public static final LibC INSTANCE = (LibC)Native.loadLibrary("c", LibC.class);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/unix/LibC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */