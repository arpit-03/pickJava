/*    */ package com.sun.jna.platform.win32.COM;
/*    */ 
/*    */ import com.sun.jna.Function;
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.PointerType;
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
/*    */ public abstract class COMInvoker
/*    */   extends PointerType
/*    */ {
/*    */   protected int _invokeNativeInt(int vtableId, Object[] args) {
/* 33 */     Pointer vptr = getPointer().getPointer(0L);
/*    */ 
/*    */     
/* 36 */     Function func = Function.getFunction(vptr.getPointer((vtableId * Pointer.SIZE)));
/*    */     
/* 38 */     return func.invokeInt(args);
/*    */   }
/*    */   
/*    */   protected Object _invokeNativeObject(int vtableId, Object[] args, Class<?> returnType) {
/* 42 */     Pointer vptr = getPointer().getPointer(0L);
/*    */ 
/*    */     
/* 45 */     Function func = Function.getFunction(vptr.getPointer((vtableId * Pointer.SIZE)));
/*    */     
/* 47 */     return func.invoke(returnType, args);
/*    */   }
/*    */   
/*    */   protected void _invokeNativeVoid(int vtableId, Object[] args) {
/* 51 */     Pointer vptr = getPointer().getPointer(0L);
/*    */ 
/*    */     
/* 54 */     Function func = Function.getFunction(vptr.getPointer((vtableId * Pointer.SIZE)));
/*    */     
/* 56 */     func.invokeVoid(args);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/COMInvoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */