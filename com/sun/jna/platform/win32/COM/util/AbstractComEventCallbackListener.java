/*    */ package com.sun.jna.platform.win32.COM.util;
/*    */ 
/*    */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
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
/*    */ public abstract class AbstractComEventCallbackListener
/*    */   implements IComEventCallbackListener
/*    */ {
/* 31 */   IDispatchCallback dispatchCallback = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDispatchCallbackListener(IDispatchCallback dispatchCallback) {
/* 36 */     this.dispatchCallback = dispatchCallback;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/AbstractComEventCallbackListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */