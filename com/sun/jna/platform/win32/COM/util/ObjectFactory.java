/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.platform.win32.COM.COMException;
/*     */ import com.sun.jna.platform.win32.COM.COMUtils;
/*     */ import com.sun.jna.platform.win32.COM.Dispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
/*     */ import com.sun.jna.platform.win32.COM.RunningObjectTable;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComObject;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.Kernel32;
/*     */ import com.sun.jna.platform.win32.Ole32;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectFactory
/*     */ {
/*     */   protected void finalize() throws Throwable {
/*     */     try {
/*  59 */       disposeAll();
/*     */     } finally {
/*  61 */       super.finalize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IRunningObjectTable getRunningObjectTable() {
/*  72 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*  74 */     PointerByReference rotPtr = new PointerByReference();
/*     */     
/*  76 */     WinNT.HRESULT hr = Ole32.INSTANCE.GetRunningObjectTable(new WinDef.DWORD(0L), rotPtr);
/*     */     
/*  78 */     COMUtils.checkRC(hr);
/*     */     
/*  80 */     RunningObjectTable raw = new RunningObjectTable(rotPtr.getValue());
/*  81 */     IRunningObjectTable rot = new RunningObjectTable(raw, this);
/*  82 */     return rot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T createProxy(Class<T> comInterface, IDispatch dispatch) {
/*  90 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*  92 */     ProxyObject jop = new ProxyObject(comInterface, dispatch, this);
/*  93 */     Object proxy = Proxy.newProxyInstance(comInterface.getClassLoader(), new Class[] { comInterface }, jop);
/*  94 */     T result = comInterface.cast(proxy);
/*  95 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T createObject(Class<T> comInterface) {
/* 103 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 105 */     ComObject comObectAnnotation = comInterface.<ComObject>getAnnotation(ComObject.class);
/* 106 */     if (null == comObectAnnotation) {
/* 107 */       throw new COMException("createObject: Interface must define a value for either clsId or progId via the ComInterface annotation");
/*     */     }
/*     */     
/* 110 */     Guid.GUID guid = discoverClsId(comObectAnnotation);
/*     */     
/* 112 */     PointerByReference ptrDisp = new PointerByReference();
/* 113 */     WinNT.HRESULT hr = Ole32.INSTANCE.CoCreateInstance(guid, null, 21, (Guid.GUID)IDispatch.IID_IDISPATCH, ptrDisp);
/*     */ 
/*     */     
/* 116 */     COMUtils.checkRC(hr);
/* 117 */     Dispatch d = new Dispatch(ptrDisp.getValue());
/* 118 */     T t = createProxy(comInterface, (IDispatch)d);
/*     */ 
/*     */     
/* 121 */     int n = d.Release();
/* 122 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T fetchObject(Class<T> comInterface) {
/* 130 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 132 */     ComObject comObectAnnotation = comInterface.<ComObject>getAnnotation(ComObject.class);
/* 133 */     if (null == comObectAnnotation) {
/* 134 */       throw new COMException("createObject: Interface must define a value for either clsId or progId via the ComInterface annotation");
/*     */     }
/*     */     
/* 137 */     Guid.GUID guid = discoverClsId(comObectAnnotation);
/*     */     
/* 139 */     PointerByReference ptrDisp = new PointerByReference();
/* 140 */     WinNT.HRESULT hr = OleAuto.INSTANCE.GetActiveObject(guid, null, ptrDisp);
/*     */     
/* 142 */     COMUtils.checkRC(hr);
/* 143 */     Dispatch d = new Dispatch(ptrDisp.getValue());
/* 144 */     T t = createProxy(comInterface, (IDispatch)d);
/*     */ 
/*     */     
/* 147 */     d.Release();
/*     */     
/* 149 */     return t;
/*     */   }
/*     */   
/*     */   Guid.GUID discoverClsId(ComObject annotation) {
/* 153 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 155 */     String clsIdStr = annotation.clsId();
/* 156 */     String progIdStr = annotation.progId();
/* 157 */     if (null != clsIdStr && !clsIdStr.isEmpty())
/* 158 */       return (Guid.GUID)new Guid.CLSID(clsIdStr); 
/* 159 */     if (null != progIdStr && !progIdStr.isEmpty()) {
/* 160 */       Guid.CLSID.ByReference rclsid = new Guid.CLSID.ByReference();
/*     */       
/* 162 */       WinNT.HRESULT hr = Ole32.INSTANCE.CLSIDFromProgID(progIdStr, rclsid);
/*     */       
/* 164 */       COMUtils.checkRC(hr);
/* 165 */       return (Guid.GUID)rclsid;
/*     */     } 
/* 167 */     throw new COMException("ComObject must define a value for either clsId or progId");
/*     */   }
/*     */ 
/*     */   
/*     */   IDispatchCallback createDispatchCallback(Class<?> comEventCallbackInterface, IComEventCallbackListener comEventCallbackListener) {
/* 172 */     return new CallbackProxy(this, comEventCallbackInterface, comEventCallbackListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   private final List<WeakReference<ProxyObject>> registeredObjects = new LinkedList<WeakReference<ProxyObject>>();
/*     */   public void register(ProxyObject proxyObject) {
/* 184 */     synchronized (this.registeredObjects) {
/* 185 */       this.registeredObjects.add(new WeakReference<ProxyObject>(proxyObject));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void unregister(ProxyObject proxyObject) {
/* 190 */     synchronized (this.registeredObjects) {
/* 191 */       Iterator<WeakReference<ProxyObject>> iterator = this.registeredObjects.iterator();
/* 192 */       while (iterator.hasNext()) {
/* 193 */         WeakReference<ProxyObject> weakRef = iterator.next();
/* 194 */         ProxyObject po = weakRef.get();
/* 195 */         if (po == null || po == proxyObject) {
/* 196 */           iterator.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void disposeAll() {
/* 203 */     synchronized (this.registeredObjects) {
/* 204 */       List<WeakReference<ProxyObject>> s = new ArrayList<WeakReference<ProxyObject>>(this.registeredObjects);
/* 205 */       for (WeakReference<ProxyObject> weakRef : s) {
/* 206 */         ProxyObject po = weakRef.get();
/* 207 */         if (po != null) {
/* 208 */           po.dispose();
/*     */         }
/*     */       } 
/* 211 */       this.registeredObjects.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   private static final WinDef.LCID LOCALE_USER_DEFAULT = Kernel32.INSTANCE.GetUserDefaultLCID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WinDef.LCID LCID;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WinDef.LCID getLCID() {
/* 229 */     if (this.LCID != null) {
/* 230 */       return this.LCID;
/*     */     }
/* 232 */     return LOCALE_USER_DEFAULT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLCID(WinDef.LCID value) {
/* 242 */     this.LCID = value;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/ObjectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */