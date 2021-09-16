/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.COM.COMException;
/*     */ import com.sun.jna.platform.win32.COM.COMUtils;
/*     */ import com.sun.jna.platform.win32.COM.ConnectionPoint;
/*     */ import com.sun.jna.platform.win32.COM.ConnectionPointContainer;
/*     */ import com.sun.jna.platform.win32.COM.Dispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
/*     */ import com.sun.jna.platform.win32.COM.IUnknown;
/*     */ import com.sun.jna.platform.win32.COM.IUnknownCallback;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.Kernel32Util;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeoutException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProxyObject
/*     */   implements InvocationHandler, IDispatch, IRawDispatchHandle, IConnectionPoint
/*     */ {
/*     */   private long unknownId;
/*     */   private final Class<?> theInterface;
/*     */   private final ObjectFactory factory;
/*     */   private final IDispatch rawDispatch;
/*     */   
/*     */   public ProxyObject(Class<?> theInterface, IDispatch rawDispatch, ObjectFactory factory) {
/*  87 */     this.unknownId = -1L;
/*  88 */     this.rawDispatch = rawDispatch;
/*  89 */     this.theInterface = theInterface;
/*  90 */     this.factory = factory;
/*     */ 
/*     */     
/*  93 */     int n = this.rawDispatch.AddRef();
/*  94 */     getUnknownId();
/*  95 */     factory.register(this);
/*     */   }
/*     */   
/*     */   private long getUnknownId() {
/*  99 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 101 */     if (-1L == this.unknownId) {
/*     */       try {
/* 103 */         PointerByReference ppvObject = new PointerByReference();
/*     */         
/* 105 */         Thread current = Thread.currentThread();
/* 106 */         String tn = current.getName();
/*     */         
/* 108 */         Guid.IID iid = IUnknown.IID_IUNKNOWN;
/* 109 */         WinNT.HRESULT hr = getRawDispatch().QueryInterface(new Guid.REFIID(iid), ppvObject);
/*     */         
/* 111 */         if (WinNT.S_OK.equals(hr)) {
/* 112 */           Dispatch dispatch = new Dispatch(ppvObject.getValue());
/* 113 */           this.unknownId = Pointer.nativeValue(dispatch.getPointer());
/*     */ 
/*     */ 
/*     */           
/* 117 */           int i = dispatch.Release();
/*     */         } else {
/* 119 */           String formatMessageFromHR = Kernel32Util.formatMessage(hr);
/* 120 */           throw new COMException("getUnknownId: " + formatMessageFromHR);
/*     */         } 
/* 122 */       } catch (Exception e) {
/* 123 */         throw new COMException("Error occured when trying get Unknown Id ", e);
/*     */       } 
/*     */     }
/* 126 */     return this.unknownId;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 131 */     dispose();
/*     */   }
/*     */   
/*     */   public synchronized void dispose() {
/* 135 */     if (((Dispatch)this.rawDispatch).getPointer() != Pointer.NULL) {
/* 136 */       this.rawDispatch.Release();
/* 137 */       ((Dispatch)this.rawDispatch).setPointer(Pointer.NULL);
/* 138 */       this.factory.unregister(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IDispatch getRawDispatch() {
/* 144 */     return this.rawDispatch;
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
/*     */   public boolean equals(Object arg) {
/* 159 */     if (null == arg)
/* 160 */       return false; 
/* 161 */     if (arg instanceof ProxyObject) {
/* 162 */       ProxyObject other = (ProxyObject)arg;
/* 163 */       return (getUnknownId() == other.getUnknownId());
/* 164 */     }  if (Proxy.isProxyClass(arg.getClass())) {
/* 165 */       InvocationHandler handler = Proxy.getInvocationHandler(arg);
/* 166 */       if (handler instanceof ProxyObject) {
/*     */         try {
/* 168 */           ProxyObject other = (ProxyObject)handler;
/* 169 */           return (getUnknownId() == other.getUnknownId());
/* 170 */         } catch (Exception e) {
/*     */ 
/*     */           
/* 173 */           return false;
/*     */         } 
/*     */       }
/* 176 */       return false;
/*     */     } 
/*     */     
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 185 */     long id = getUnknownId();
/* 186 */     return (int)(id >>> 32L & 0xFFFFFFFFFFFFFFFFL) + (int)(id & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 191 */     return this.theInterface.getName() + "{unk=" + hashCode() + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 200 */     boolean declaredAsInterface = (method.getAnnotation(ComMethod.class) != null || method.getAnnotation(ComProperty.class) != null);
/*     */     
/* 202 */     if (!declaredAsInterface && (method.getDeclaringClass().equals(Object.class) || method
/* 203 */       .getDeclaringClass().equals(IRawDispatchHandle.class) || method
/* 204 */       .getDeclaringClass().equals(IUnknown.class) || method
/* 205 */       .getDeclaringClass().equals(IDispatch.class) || method
/* 206 */       .getDeclaringClass().equals(IConnectionPoint.class))) {
/*     */       
/*     */       try {
/* 209 */         return method.invoke(this, args);
/* 210 */       } catch (InvocationTargetException ex) {
/* 211 */         throw ex.getCause();
/*     */       } 
/*     */     }
/*     */     
/* 215 */     Class<?> returnType = method.getReturnType();
/* 216 */     boolean isVoid = void.class.equals(returnType);
/*     */     
/* 218 */     ComProperty prop = method.<ComProperty>getAnnotation(ComProperty.class);
/* 219 */     if (null != prop) {
/* 220 */       int dispId = prop.dispId();
/* 221 */       if (isVoid) {
/* 222 */         if (dispId != -1) {
/* 223 */           setProperty(new OaIdl.DISPID(dispId), args[0]);
/* 224 */           return null;
/*     */         } 
/* 226 */         String str = getMutatorName(method, prop);
/* 227 */         setProperty(str, args[0]);
/* 228 */         return null;
/*     */       } 
/*     */       
/* 231 */       if (dispId != -1) {
/* 232 */         return getProperty(returnType, new OaIdl.DISPID(dispId), args);
/*     */       }
/* 234 */       String propName = getAccessorName(method, prop);
/* 235 */       return getProperty(returnType, propName, args);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 240 */     ComMethod meth = method.<ComMethod>getAnnotation(ComMethod.class);
/* 241 */     if (null != meth) {
/* 242 */       Object[] fullLengthArgs = unfoldWhenVarargs(method, args);
/* 243 */       int dispId = meth.dispId();
/* 244 */       if (dispId != -1) {
/* 245 */         return invokeMethod(returnType, new OaIdl.DISPID(dispId), fullLengthArgs);
/*     */       }
/* 247 */       String methName = getMethodName(method, meth);
/* 248 */       return invokeMethod(returnType, methName, fullLengthArgs);
/*     */     } 
/*     */ 
/*     */     
/* 252 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private ConnectionPoint fetchRawConnectionPoint(Guid.IID iid) throws InterruptedException, ExecutionException, TimeoutException {
/* 257 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */ 
/*     */     
/* 260 */     IConnectionPointContainer cpc = queryInterface(IConnectionPointContainer.class);
/* 261 */     Dispatch rawCpcDispatch = (Dispatch)cpc.getRawDispatch();
/* 262 */     ConnectionPointContainer rawCpc = new ConnectionPointContainer(rawCpcDispatch.getPointer());
/*     */ 
/*     */     
/* 265 */     Guid.REFIID adviseRiid = new Guid.REFIID(iid.getPointer());
/* 266 */     PointerByReference ppCp = new PointerByReference();
/* 267 */     WinNT.HRESULT hr = rawCpc.FindConnectionPoint(adviseRiid, ppCp);
/* 268 */     COMUtils.checkRC(hr);
/* 269 */     ConnectionPoint rawCp = new ConnectionPoint(ppCp.getValue());
/* 270 */     return rawCp;
/*     */   }
/*     */ 
/*     */   
/*     */   public IComEventCallbackCookie advise(Class<?> comEventCallbackInterface, IComEventCallbackListener comEventCallbackListener) {
/* 275 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*     */     try {
/* 278 */       ComInterface comInterfaceAnnotation = comEventCallbackInterface.<ComInterface>getAnnotation(ComInterface.class);
/* 279 */       if (null == comInterfaceAnnotation) {
/* 280 */         throw new COMException("advise: Interface must define a value for either iid via the ComInterface annotation");
/*     */       }
/*     */       
/* 283 */       Guid.IID iid = getIID(comInterfaceAnnotation);
/*     */       
/* 285 */       ConnectionPoint rawCp = fetchRawConnectionPoint(iid);
/*     */ 
/*     */       
/* 288 */       IDispatchCallback rawListener = this.factory.createDispatchCallback(comEventCallbackInterface, comEventCallbackListener);
/*     */ 
/*     */       
/* 291 */       comEventCallbackListener.setDispatchCallbackListener(rawListener);
/*     */ 
/*     */       
/* 294 */       WinDef.DWORDByReference pdwCookie = new WinDef.DWORDByReference();
/* 295 */       WinNT.HRESULT hr = rawCp.Advise((IUnknownCallback)rawListener, pdwCookie);
/* 296 */       int n = rawCp.Release();
/*     */       
/* 298 */       COMUtils.checkRC(hr);
/*     */ 
/*     */       
/* 301 */       return new ComEventCallbackCookie(pdwCookie.getValue());
/*     */     }
/* 303 */     catch (Exception e) {
/* 304 */       throw new COMException("Error occured in advise when trying to connect the listener " + comEventCallbackListener, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unadvise(Class<?> comEventCallbackInterface, IComEventCallbackCookie cookie) {
/* 310 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*     */     try {
/* 313 */       ComInterface comInterfaceAnnotation = comEventCallbackInterface.<ComInterface>getAnnotation(ComInterface.class);
/* 314 */       if (null == comInterfaceAnnotation) {
/* 315 */         throw new COMException("unadvise: Interface must define a value for iid via the ComInterface annotation");
/*     */       }
/*     */       
/* 318 */       Guid.IID iid = getIID(comInterfaceAnnotation);
/*     */       
/* 320 */       ConnectionPoint rawCp = fetchRawConnectionPoint(iid);
/*     */       
/* 322 */       WinNT.HRESULT hr = rawCp.Unadvise(((ComEventCallbackCookie)cookie).getValue());
/*     */       
/* 324 */       rawCp.Release();
/* 325 */       COMUtils.checkRC(hr);
/*     */     }
/* 327 */     catch (Exception e) {
/* 328 */       throw new COMException("Error occured in unadvise when trying to disconnect the listener from " + this, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void setProperty(String name, T value) {
/* 335 */     OaIdl.DISPID dispID = resolveDispId(getRawDispatch(), name);
/* 336 */     setProperty(dispID, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void setProperty(OaIdl.DISPID dispId, T value) {
/* 341 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 343 */     Variant.VARIANT v = Convert.toVariant(value);
/* 344 */     WinNT.HRESULT hr = oleMethod(4, (Variant.VARIANT.ByReference)null, getRawDispatch(), dispId, v);
/* 345 */     Convert.free(v, value);
/* 346 */     COMUtils.checkRC(hr);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getProperty(Class<T> returnType, String name, Object... args) {
/* 351 */     OaIdl.DISPID dispID = resolveDispId(getRawDispatch(), name);
/* 352 */     return getProperty(returnType, dispID, args);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getProperty(Class<T> returnType, OaIdl.DISPID dispID, Object... args) {
/*     */     Variant.VARIANT[] vargs;
/* 358 */     if (null == args) {
/* 359 */       vargs = new Variant.VARIANT[0];
/*     */     } else {
/* 361 */       vargs = new Variant.VARIANT[args.length];
/*     */     } 
/* 363 */     for (int i = 0; i < vargs.length; i++) {
/* 364 */       vargs[i] = Convert.toVariant(args[i]);
/*     */     }
/* 366 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 367 */     WinNT.HRESULT hr = oleMethod(2, result, getRawDispatch(), dispID, vargs);
/*     */     
/* 369 */     for (int j = 0; j < vargs.length; j++)
/*     */     {
/* 371 */       Convert.free(vargs[j], args[j]);
/*     */     }
/*     */     
/* 374 */     COMUtils.checkRC(hr);
/*     */     
/* 376 */     return (T)Convert.toJavaObject((Variant.VARIANT)result, returnType, this.factory, false, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T invokeMethod(Class<T> returnType, String name, Object... args) {
/* 381 */     OaIdl.DISPID dispID = resolveDispId(getRawDispatch(), name);
/* 382 */     return invokeMethod(returnType, dispID, args);
/*     */   }
/*     */   
/*     */   public <T> T invokeMethod(Class<T> returnType, OaIdl.DISPID dispID, Object... args) {
/*     */     Variant.VARIANT[] vargs;
/* 387 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */ 
/*     */     
/* 390 */     if (null == args) {
/* 391 */       vargs = new Variant.VARIANT[0];
/*     */     } else {
/* 393 */       vargs = new Variant.VARIANT[args.length];
/*     */     } 
/* 395 */     for (int i = 0; i < vargs.length; i++) {
/* 396 */       vargs[i] = Convert.toVariant(args[i]);
/*     */     }
/* 398 */     Variant.VARIANT.ByReference result = new Variant.VARIANT.ByReference();
/* 399 */     WinNT.HRESULT hr = oleMethod(1, result, getRawDispatch(), dispID, vargs);
/*     */     
/* 401 */     for (int j = 0; j < vargs.length; j++)
/*     */     {
/* 403 */       Convert.free(vargs[j], args[j]);
/*     */     }
/*     */     
/* 406 */     COMUtils.checkRC(hr);
/*     */     
/* 408 */     return (T)Convert.toJavaObject((Variant.VARIANT)result, returnType, this.factory, false, true);
/*     */   }
/*     */   
/*     */   private Object[] unfoldWhenVarargs(Method method, Object[] argParams) {
/* 412 */     if (null == argParams) {
/* 413 */       return null;
/*     */     }
/* 415 */     if (argParams.length == 0 || !method.isVarArgs() || !(argParams[argParams.length - 1] instanceof Object[])) {
/* 416 */       return argParams;
/*     */     }
/*     */     
/* 419 */     Object[] varargs = (Object[])argParams[argParams.length - 1];
/* 420 */     Object[] args = new Object[argParams.length - 1 + varargs.length];
/* 421 */     System.arraycopy(argParams, 0, args, 0, argParams.length - 1);
/* 422 */     System.arraycopy(varargs, 0, args, argParams.length - 1, varargs.length);
/* 423 */     return args;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T queryInterface(Class<T> comInterface) throws COMException {
/* 428 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/*     */     try {
/* 431 */       ComInterface comInterfaceAnnotation = comInterface.<ComInterface>getAnnotation(ComInterface.class);
/* 432 */       if (null == comInterfaceAnnotation) {
/* 433 */         throw new COMException("queryInterface: Interface must define a value for iid via the ComInterface annotation");
/*     */       }
/*     */       
/* 436 */       Guid.IID iid = getIID(comInterfaceAnnotation);
/* 437 */       PointerByReference ppvObject = new PointerByReference();
/*     */       
/* 439 */       WinNT.HRESULT hr = getRawDispatch().QueryInterface(new Guid.REFIID(iid), ppvObject);
/*     */       
/* 441 */       if (WinNT.S_OK.equals(hr)) {
/* 442 */         Dispatch dispatch = new Dispatch(ppvObject.getValue());
/* 443 */         T t = this.factory.createProxy(comInterface, (IDispatch)dispatch);
/*     */ 
/*     */ 
/*     */         
/* 447 */         int n = dispatch.Release();
/* 448 */         return t;
/*     */       } 
/* 450 */       String formatMessageFromHR = Kernel32Util.formatMessage(hr);
/* 451 */       throw new COMException("queryInterface: " + formatMessageFromHR);
/*     */     }
/* 453 */     catch (Exception e) {
/* 454 */       throw new COMException("Error occured when trying to query for interface " + comInterface.getName(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Guid.IID getIID(ComInterface annotation) {
/* 459 */     String iidStr = annotation.iid();
/* 460 */     if (null != iidStr && !iidStr.isEmpty()) {
/* 461 */       return new Guid.IID(iidStr);
/*     */     }
/* 463 */     throw new COMException("ComInterface must define a value for iid");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getAccessorName(Method method, ComProperty prop) {
/* 470 */     if (prop.name().isEmpty()) {
/* 471 */       String methName = method.getName();
/* 472 */       if (methName.startsWith("get")) {
/* 473 */         return methName.replaceFirst("get", "");
/*     */       }
/* 475 */       throw new RuntimeException("Property Accessor name must start with 'get', or set the anotation 'name' value");
/*     */     } 
/*     */ 
/*     */     
/* 479 */     return prop.name();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getMutatorName(Method method, ComProperty prop) {
/* 484 */     if (prop.name().isEmpty()) {
/* 485 */       String methName = method.getName();
/* 486 */       if (methName.startsWith("set")) {
/* 487 */         return methName.replaceFirst("set", "");
/*     */       }
/* 489 */       throw new RuntimeException("Property Mutator name must start with 'set', or set the anotation 'name' value");
/*     */     } 
/*     */ 
/*     */     
/* 493 */     return prop.name();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getMethodName(Method method, ComMethod meth) {
/* 498 */     if (meth.name().isEmpty()) {
/* 499 */       String methName = method.getName();
/* 500 */       return methName;
/*     */     } 
/* 502 */     return meth.name();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name, Variant.VARIANT pArg) throws COMException {
/* 511 */     return oleMethod(nType, pvResult, pDisp, name, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId, Variant.VARIANT pArg) throws COMException {
/* 519 */     return oleMethod(nType, pvResult, pDisp, dispId, new Variant.VARIANT[] { pArg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name) throws COMException {
/* 527 */     return oleMethod(nType, pvResult, pDisp, name, (Variant.VARIANT[])null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId) throws COMException {
/* 536 */     return oleMethod(nType, pvResult, pDisp, dispId, (Variant.VARIANT[])null);
/*     */   }
/*     */   
/*     */   protected OaIdl.DISPID resolveDispId(IDispatch pDisp, String name) {
/* 540 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 542 */     if (pDisp == null) {
/* 543 */       throw new COMException("pDisp (IDispatch) parameter is null!");
/*     */     }
/*     */     
/* 546 */     WString[] ptName = { new WString(name) };
/* 547 */     OaIdl.DISPIDByReference pdispID = new OaIdl.DISPIDByReference();
/*     */ 
/*     */     
/* 550 */     WinNT.HRESULT hr = pDisp.GetIDsOfNames(new Guid.REFIID(Guid.IID_NULL), ptName, 1, this.factory
/*     */ 
/*     */ 
/*     */         
/* 554 */         .getLCID(), pdispID);
/*     */ 
/*     */     
/* 557 */     COMUtils.checkRC(hr);
/*     */     
/* 559 */     return pdispID.getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, String name, Variant.VARIANT[] pArgs) throws COMException {
/* 568 */     return oleMethod(nType, pvResult, pDisp, resolveDispId(pDisp, name), pArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WinNT.HRESULT oleMethod(int nType, Variant.VARIANT.ByReference pvResult, IDispatch pDisp, OaIdl.DISPID dispId, Variant.VARIANT[] pArgs) throws COMException {
/*     */     int finalNType;
/* 577 */     assert COMUtils.comIsInitialized() : "COM not initialized";
/*     */     
/* 579 */     if (pDisp == null) {
/* 580 */       throw new COMException("pDisp (IDispatch) parameter is null!");
/*     */     }
/*     */     
/* 583 */     int _argsLen = 0;
/* 584 */     Variant.VARIANT[] _args = null;
/* 585 */     OleAuto.DISPPARAMS.ByReference dp = new OleAuto.DISPPARAMS.ByReference();
/* 586 */     OaIdl.EXCEPINFO.ByReference pExcepInfo = new OaIdl.EXCEPINFO.ByReference();
/* 587 */     IntByReference puArgErr = new IntByReference();
/*     */ 
/*     */     
/* 590 */     if (pArgs != null && pArgs.length > 0) {
/* 591 */       _argsLen = pArgs.length;
/* 592 */       _args = new Variant.VARIANT[_argsLen];
/*     */       
/* 594 */       int revCount = _argsLen;
/* 595 */       for (int i = 0; i < _argsLen; i++) {
/* 596 */         _args[i] = pArgs[--revCount];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 601 */     if (nType == 4) {
/* 602 */       dp.setRgdispidNamedArgs(new OaIdl.DISPID[] { OaIdl.DISPID_PROPERTYPUT });
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
/* 629 */     if (nType == 1 || nType == 2) {
/* 630 */       finalNType = 3;
/*     */     } else {
/* 632 */       finalNType = nType;
/*     */     } 
/*     */ 
/*     */     
/* 636 */     if (_argsLen > 0) {
/* 637 */       dp.setArgs(_args);
/*     */ 
/*     */       
/* 640 */       dp.write();
/*     */     } 
/*     */ 
/*     */     
/* 644 */     WinNT.HRESULT hr = pDisp.Invoke(dispId, new Guid.REFIID(Guid.IID_NULL), this.factory
/*     */ 
/*     */         
/* 647 */         .getLCID(), new WinDef.WORD(finalNType), dp, pvResult, pExcepInfo, puArgErr);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 655 */     COMUtils.checkRC(hr, (OaIdl.EXCEPINFO)pExcepInfo, puArgErr);
/* 656 */     return hr;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/ProxyObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */