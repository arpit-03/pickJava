/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.Kernel32;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.Ole32;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
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
/*     */ public class COMBindingBaseObject
/*     */   extends COMInvoker
/*     */ {
/*  57 */   public static final WinDef.LCID LOCALE_USER_DEFAULT = Kernel32.INSTANCE
/*  58 */     .GetUserDefaultLCID();
/*     */ 
/*     */   
/*  61 */   public static final WinDef.LCID LOCALE_SYSTEM_DEFAULT = Kernel32.INSTANCE
/*  62 */     .GetSystemDefaultLCID();
/*     */ 
/*     */   
/*     */   private IUnknown iUnknown;
/*     */ 
/*     */   
/*     */   private IDispatch iDispatch;
/*     */ 
/*     */   
/*  71 */   private PointerByReference pDispatch = new PointerByReference();
/*     */ 
/*     */   
/*  74 */   private PointerByReference pUnknown = new PointerByReference();
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(IDispatch dispatch) {
/*  78 */     this.iDispatch = dispatch;
/*     */   }
/*     */   
/*     */   public COMBindingBaseObject(Guid.CLSID clsid, boolean useActiveInstance) {
/*  82 */     this(clsid, useActiveInstance, 21);
/*     */   }
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(Guid.CLSID clsid, boolean useActiveInstance, int dwClsContext) {
/*  87 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*  89 */     init(useActiveInstance, (Guid.GUID)clsid, dwClsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(String progId, boolean useActiveInstance, int dwClsContext) throws COMException {
/*  94 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*  96 */     Guid.CLSID.ByReference clsid = new Guid.CLSID.ByReference();
/*  97 */     WinNT.HRESULT hr = Ole32.INSTANCE.CLSIDFromProgID(progId, clsid);
/*     */     
/*  99 */     COMUtils.checkRC(hr);
/*     */     
/* 101 */     init(useActiveInstance, (Guid.GUID)clsid, dwClsContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public COMBindingBaseObject(String progId, boolean useActiveInstance) throws COMException {
/* 106 */     this(progId, useActiveInstance, 21);
/*     */   }
/*     */   
/*     */   private void init(boolean useActiveInstance, Guid.GUID clsid, int dwClsContext) throws COMException {
/*     */     WinNT.HRESULT hr;
/* 111 */     if (useActiveInstance) {
/* 112 */       hr = OleAuto.INSTANCE.GetActiveObject(clsid, null, this.pUnknown);
/*     */       
/* 114 */       if (COMUtils.SUCCEEDED(hr)) {
/* 115 */         this.iUnknown = new Unknown(this.pUnknown.getValue());
/* 116 */         hr = this.iUnknown.QueryInterface(new Guid.REFIID(IDispatch.IID_IDISPATCH), this.pDispatch);
/*     */       } else {
/*     */         
/* 119 */         hr = Ole32.INSTANCE.CoCreateInstance(clsid, null, dwClsContext, (Guid.GUID)IDispatch.IID_IDISPATCH, this.pDispatch);
/*     */       } 
/*     */     } else {
/*     */       
/* 123 */       hr = Ole32.INSTANCE.CoCreateInstance(clsid, null, dwClsContext, (Guid.GUID)IDispatch.IID_IDISPATCH, this.pDispatch);
/*     */     } 
/*     */ 
/*     */     
/* 127 */     COMUtils.checkRC(hr);
/*     */     
/* 129 */     this.iDispatch = new Dispatch(this.pDispatch.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IDispatch getIDispatch() {
/* 138 */     return this.iDispatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointerByReference getIDispatchPointer() {
/* 147 */     return this.pDispatch;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IUnknown getIUnknown() {
/* 156 */     return this.iUnknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointerByReference getIUnknownPointer() {
/* 165 */     return this.pUnknown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void release() {
/* 172 */     if (this.iDispatch != null) {
/* 173 */       this.iDispatch.Release();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name, Variant.VARIANT[] pArgs) throws COMException {
/* 180 */     if (pDisp == null) {
/* 181 */       throw new COMException("pDisp (IDispatch) parameter is null!");
/*     */     }
/*     */     
/* 184 */     WString[] ptName = { new WString(name) };
/* 185 */     OaIdl.DISPIDByReference pdispID = new OaIdl.DISPIDByReference();
/*     */ 
/*     */     
/* 188 */     WinNT.HRESULT hr = pDisp.GetIDsOfNames(new Guid.REFIID(Guid.IID_NULL), ptName, 1, LOCALE_USER_DEFAULT, pdispID);
/*     */ 
/*     */     
/* 191 */     COMUtils.checkRC(hr);
/*     */     
/* 193 */     return 
/* 194 */       oleMethod(nType, pvResult, pDisp, pdispID.getValue(), pArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId, Variant.VARIANT[] pArgs) throws COMException {
/*     */     int finalNType;
/* 201 */     if (pDisp == null) {
/* 202 */       throw new COMException("pDisp (IDispatch) parameter is null!");
/*     */     }
/*     */     
/* 205 */     int _argsLen = 0;
/* 206 */     Variant.VARIANT[] _args = null;
/* 207 */     OleAuto.DISPPARAMS.ByReference dp = new OleAuto.DISPPARAMS.ByReference();
/* 208 */     OaIdl.EXCEPINFO.ByReference pExcepInfo = new OaIdl.EXCEPINFO.ByReference();
/* 209 */     IntByReference puArgErr = new IntByReference();
/*     */ 
/*     */     
/* 212 */     if (pArgs != null && pArgs.length > 0) {
/* 213 */       _argsLen = pArgs.length;
/* 214 */       _args = new Variant.VARIANT[_argsLen];
/*     */       
/* 216 */       int revCount = _argsLen;
/* 217 */       for (int i = 0; i < _argsLen; i++) {
/* 218 */         _args[i] = pArgs[--revCount];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 223 */     if (nType == 4) {
/* 224 */       dp.setRgdispidNamedArgs(new OaIdl.DISPID[] { OaIdl.DISPID_PROPERTYPUT });
/*     */     }
/*     */ 
/*     */     
/* 228 */     if (_argsLen > 0) {
/* 229 */       dp.setArgs(_args);
/*     */ 
/*     */       
/* 232 */       dp.write();
/*     */     } 
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
/* 260 */     if (nType == 1 || nType == 2) {
/* 261 */       finalNType = 3;
/*     */     } else {
/* 263 */       finalNType = nType;
/*     */     } 
/*     */ 
/*     */     
/* 267 */     WinNT.HRESULT hr = pDisp.Invoke(dispId, new Guid.REFIID(Guid.IID_NULL), LOCALE_SYSTEM_DEFAULT, new WinDef.WORD(finalNType), dp, pvResult, pExcepInfo, puArgErr);
/*     */ 
/*     */     
/* 270 */     COMUtils.checkRC(hr, (OaIdl.EXCEPINFO)pExcepInfo, puArgErr);
/* 271 */     return hr;
/*     */   }
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
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name, Variant.VARIANT pArg) throws COMException {
/* 294 */     return oleMethod(nType, pvResult, pDisp, name, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId, Variant.VARIANT pArg) throws COMException {
/* 301 */     return oleMethod(nType, pvResult, pDisp, dispId, new Variant.VARIANT[] { pArg });
/*     */   }
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
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name) throws COMException {
/* 323 */     return oleMethod(nType, pvResult, pDisp, name, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId) throws COMException {
/* 329 */     return oleMethod(nType, pvResult, pDisp, dispId, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkFailed(WinNT.HRESULT hr) {
/* 339 */     COMUtils.checkRC(hr, null, null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/COMBindingBaseObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */