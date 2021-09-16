/*    */ package com.sun.jna.platform.win32.COM;
/*    */ 
/*    */ import com.sun.jna.Pointer;
/*    */ import com.sun.jna.Structure;
/*    */ import com.sun.jna.platform.win32.Guid;
/*    */ import com.sun.jna.platform.win32.WinNT;
/*    */ import com.sun.jna.ptr.PointerByReference;
/*    */ import java.util.List;
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
/*    */ public class UnknownListener
/*    */   extends Structure
/*    */ {
/* 35 */   public static final List<String> FIELDS = createFieldsOrder("vtbl");
/*    */   public UnknownVTable.ByReference vtbl;
/*    */   
/*    */   public UnknownListener(IUnknownCallback callback) {
/* 39 */     this.vtbl = constructVTable();
/* 40 */     initVTable(callback);
/* 41 */     write();
/*    */   }
/*    */ 
/*    */   
/*    */   protected List<String> getFieldOrder() {
/* 46 */     return FIELDS;
/*    */   }
/*    */   
/*    */   protected UnknownVTable.ByReference constructVTable() {
/* 50 */     return new UnknownVTable.ByReference();
/*    */   }
/*    */   
/*    */   protected void initVTable(final IUnknownCallback callback) {
/* 54 */     this.vtbl.QueryInterfaceCallback = new UnknownVTable.QueryInterfaceCallback()
/*    */       {
/*    */         public WinNT.HRESULT invoke(Pointer thisPointer, Guid.REFIID refid, PointerByReference ppvObject) {
/* 57 */           return callback.QueryInterface(refid, ppvObject);
/*    */         }
/*    */       };
/* 60 */     this.vtbl.AddRefCallback = new UnknownVTable.AddRefCallback()
/*    */       {
/*    */         public int invoke(Pointer thisPointer) {
/* 63 */           return callback.AddRef();
/*    */         }
/*    */       };
/* 66 */     this.vtbl.ReleaseCallback = new UnknownVTable.ReleaseCallback()
/*    */       {
/*    */         public int invoke(Pointer thisPointer) {
/* 69 */           return callback.Release();
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/UnknownListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */