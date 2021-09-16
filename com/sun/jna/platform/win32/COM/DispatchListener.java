/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DispatchListener
/*     */   extends Structure
/*     */ {
/*  46 */   public static final List<String> FIELDS = createFieldsOrder("vtbl");
/*     */   public DispatchListener(IDispatchCallback callback) {
/*  48 */     this.vtbl = constructVTable();
/*  49 */     initVTable(callback);
/*  50 */     write();
/*     */   }
/*     */   
/*     */   public DispatchVTable.ByReference vtbl;
/*     */   
/*     */   protected List<String> getFieldOrder() {
/*  56 */     return FIELDS;
/*     */   }
/*     */   
/*     */   protected DispatchVTable.ByReference constructVTable() {
/*  60 */     return new DispatchVTable.ByReference();
/*     */   }
/*     */   
/*     */   protected void initVTable(final IDispatchCallback callback) {
/*  64 */     this.vtbl.QueryInterfaceCallback = new DispatchVTable.QueryInterfaceCallback()
/*     */       {
/*     */         public WinNT.HRESULT invoke(Pointer thisPointer, Guid.REFIID refid, PointerByReference ppvObject) {
/*  67 */           return callback.QueryInterface(refid, ppvObject);
/*     */         }
/*     */       };
/*  70 */     this.vtbl.AddRefCallback = new DispatchVTable.AddRefCallback()
/*     */       {
/*     */         public int invoke(Pointer thisPointer) {
/*  73 */           return callback.AddRef();
/*     */         }
/*     */       };
/*  76 */     this.vtbl.ReleaseCallback = new DispatchVTable.ReleaseCallback()
/*     */       {
/*     */         public int invoke(Pointer thisPointer) {
/*  79 */           return callback.Release();
/*     */         }
/*     */       };
/*  82 */     this.vtbl.GetTypeInfoCountCallback = new DispatchVTable.GetTypeInfoCountCallback()
/*     */       {
/*     */         public WinNT.HRESULT invoke(Pointer thisPointer, WinDef.UINTByReference pctinfo) {
/*  85 */           return callback.GetTypeInfoCount(pctinfo);
/*     */         }
/*     */       };
/*  88 */     this.vtbl.GetTypeInfoCallback = new DispatchVTable.GetTypeInfoCallback()
/*     */       {
/*     */         public WinNT.HRESULT invoke(Pointer thisPointer, WinDef.UINT iTInfo, WinDef.LCID lcid, PointerByReference ppTInfo) {
/*  91 */           return callback.GetTypeInfo(iTInfo, lcid, ppTInfo);
/*     */         }
/*     */       };
/*  94 */     this.vtbl.GetIDsOfNamesCallback = new DispatchVTable.GetIDsOfNamesCallback()
/*     */       {
/*     */         public WinNT.HRESULT invoke(Pointer thisPointer, Guid.REFIID riid, WString[] rgszNames, int cNames, WinDef.LCID lcid, OaIdl.DISPIDByReference rgDispId)
/*     */         {
/*  98 */           return callback.GetIDsOfNames(riid, rgszNames, cNames, lcid, rgDispId);
/*     */         }
/*     */       };
/* 101 */     this.vtbl.InvokeCallback = new DispatchVTable.InvokeCallback()
/*     */       {
/*     */ 
/*     */         
/*     */         public WinNT.HRESULT invoke(Pointer thisPointer, OaIdl.DISPID dispIdMember, Guid.REFIID riid, WinDef.LCID lcid, WinDef.WORD wFlags, OleAuto.DISPPARAMS.ByReference pDispParams, Variant.VARIANT.ByReference pVarResult, OaIdl.EXCEPINFO.ByReference pExcepInfo, IntByReference puArgErr)
/*     */         {
/* 107 */           return callback.Invoke(dispIdMember, riid, lcid, wFlags, pDispParams, pVarResult, pExcepInfo, puArgErr);
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/DispatchListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */