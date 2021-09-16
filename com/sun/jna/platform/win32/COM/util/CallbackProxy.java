/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.COM.COMException;
/*     */ import com.sun.jna.platform.win32.COM.COMUtils;
/*     */ import com.sun.jna.platform.win32.COM.Dispatch;
/*     */ import com.sun.jna.platform.win32.COM.DispatchListener;
/*     */ import com.sun.jna.platform.win32.COM.IDispatchCallback;
/*     */ import com.sun.jna.platform.win32.COM.Unknown;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComEventCallback;
/*     */ import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinError;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class CallbackProxy
/*     */   implements IDispatchCallback
/*     */ {
/*     */   private static boolean DEFAULT_BOOLEAN;
/*     */   private static byte DEFAULT_BYTE;
/*     */   private static short DEFAULT_SHORT;
/*     */   private static int DEFAULT_INT;
/*     */   private static long DEFAULT_LONG;
/*     */   private static float DEFAULT_FLOAT;
/*     */   private static double DEFAULT_DOUBLE;
/*     */   ObjectFactory factory;
/*     */   Class<?> comEventCallbackInterface;
/*     */   IComEventCallbackListener comEventCallbackListener;
/*     */   Guid.REFIID listenedToRiid;
/*     */   public DispatchListener dispatchListener;
/*     */   Map<OaIdl.DISPID, Method> dsipIdMap;
/*     */   
/*     */   public CallbackProxy(ObjectFactory factory, Class<?> comEventCallbackInterface, IComEventCallbackListener comEventCallbackListener) {
/*  71 */     this.factory = factory;
/*  72 */     this.comEventCallbackInterface = comEventCallbackInterface;
/*  73 */     this.comEventCallbackListener = comEventCallbackListener;
/*  74 */     this.listenedToRiid = createRIID(comEventCallbackInterface);
/*  75 */     this.dsipIdMap = createDispIdMap(comEventCallbackInterface);
/*  76 */     this.dispatchListener = new DispatchListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Guid.REFIID createRIID(Class<?> comEventCallbackInterface) {
/*  87 */     ComInterface comInterfaceAnnotation = comEventCallbackInterface.<ComInterface>getAnnotation(ComInterface.class);
/*  88 */     if (null == comInterfaceAnnotation) {
/*  89 */       throw new COMException("advise: Interface must define a value for either iid via the ComInterface annotation");
/*     */     }
/*     */     
/*  92 */     String iidStr = comInterfaceAnnotation.iid();
/*  93 */     if (null == iidStr || iidStr.isEmpty()) {
/*  94 */       throw new COMException("ComInterface must define a value for iid");
/*     */     }
/*  96 */     return new Guid.REFIID((new Guid.IID(iidStr)).getPointer());
/*     */   }
/*     */   
/*     */   Map<OaIdl.DISPID, Method> createDispIdMap(Class<?> comEventCallbackInterface) {
/* 100 */     Map<OaIdl.DISPID, Method> map = new HashMap<OaIdl.DISPID, Method>();
/*     */     
/* 102 */     for (Method meth : comEventCallbackInterface.getMethods()) {
/* 103 */       ComEventCallback annotation = meth.<ComEventCallback>getAnnotation(ComEventCallback.class);
/* 104 */       if (null != annotation) {
/* 105 */         int dispId = annotation.dispid();
/* 106 */         if (-1 == dispId) {
/* 107 */           dispId = fetchDispIdFromName(annotation);
/*     */         }
/* 109 */         if (dispId == -1) {
/* 110 */           this.comEventCallbackListener.errorReceivingCallbackEvent("DISPID for " + meth
/* 111 */               .getName() + " not found", null);
/*     */         }
/*     */         
/* 114 */         map.put(new OaIdl.DISPID(dispId), meth);
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   int fetchDispIdFromName(ComEventCallback annotation) {
/* 123 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void invokeOnThread(OaIdl.DISPID dispIdMember, Guid.REFIID riid, WinDef.LCID lcid, WinDef.WORD wFlags, OleAuto.DISPPARAMS.ByReference pDispParams) {
/* 129 */     Variant.VARIANT[] arguments = pDispParams.getArgs();
/*     */     
/* 131 */     Method eventMethod = this.dsipIdMap.get(dispIdMember);
/* 132 */     if (eventMethod == null) {
/* 133 */       this.comEventCallbackListener.errorReceivingCallbackEvent("No method found with dispId = " + dispIdMember, null);
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
/*     */       return;
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
/* 174 */     OaIdl.DISPID[] positionMap = pDispParams.getRgdispidNamedArgs();
/*     */     
/* 176 */     Class<?>[] paramTypes = eventMethod.getParameterTypes();
/* 177 */     Object[] params = new Object[paramTypes.length];
/*     */     
/*     */     int i;
/* 180 */     for (i = 0; i < params.length && arguments.length - positionMap.length - i > 0; i++) {
/* 181 */       Class<?> targetClass = paramTypes[i];
/* 182 */       Variant.VARIANT varg = arguments[arguments.length - i - 1];
/* 183 */       params[i] = Convert.toJavaObject(varg, targetClass, this.factory, true, false);
/*     */     } 
/*     */     
/* 186 */     for (i = 0; i < positionMap.length; i++) {
/* 187 */       int targetPosition = positionMap[i].intValue();
/* 188 */       if (targetPosition < params.length) {
/*     */ 
/*     */ 
/*     */         
/* 192 */         Class<?> targetClass = paramTypes[targetPosition];
/* 193 */         Variant.VARIANT varg = arguments[i];
/* 194 */         params[targetPosition] = Convert.toJavaObject(varg, targetClass, this.factory, true, false);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     for (i = 0; i < params.length; i++) {
/* 202 */       if (params[i] == null && paramTypes[i].isPrimitive()) {
/* 203 */         if (paramTypes[i].equals(boolean.class)) {
/* 204 */           params[i] = Boolean.valueOf(DEFAULT_BOOLEAN);
/* 205 */         } else if (paramTypes[i].equals(byte.class)) {
/* 206 */           params[i] = Byte.valueOf(DEFAULT_BYTE);
/* 207 */         } else if (paramTypes[i].equals(short.class)) {
/* 208 */           params[i] = Short.valueOf(DEFAULT_SHORT);
/* 209 */         } else if (paramTypes[i].equals(int.class)) {
/* 210 */           params[i] = Integer.valueOf(DEFAULT_INT);
/* 211 */         } else if (paramTypes[i].equals(long.class)) {
/* 212 */           params[i] = Long.valueOf(DEFAULT_LONG);
/* 213 */         } else if (paramTypes[i].equals(float.class)) {
/* 214 */           params[i] = Float.valueOf(DEFAULT_FLOAT);
/* 215 */         } else if (paramTypes[i].equals(double.class)) {
/* 216 */           params[i] = Double.valueOf(DEFAULT_DOUBLE);
/*     */         } else {
/* 218 */           throw new IllegalArgumentException("Class type " + paramTypes[i].getName() + " not mapped to primitive default value.");
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 224 */       eventMethod.invoke(this.comEventCallbackListener, params);
/* 225 */     } catch (Exception e) {
/* 226 */       List<String> decodedClassNames = new ArrayList<String>(params.length);
/* 227 */       for (Object o : params) {
/* 228 */         if (o == null) {
/* 229 */           decodedClassNames.add("NULL");
/*     */         } else {
/* 231 */           decodedClassNames.add(o.getClass().getName());
/*     */         } 
/*     */       } 
/* 234 */       this.comEventCallbackListener.errorReceivingCallbackEvent("Exception invoking method " + eventMethod + " supplied: " + decodedClassNames
/* 235 */           .toString(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Pointer getPointer() {
/* 241 */     return this.dispatchListener.getPointer();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT GetTypeInfoCount(WinDef.UINTByReference pctinfo) {
/* 247 */     return new WinNT.HRESULT(-2147467263);
/*     */   }
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT GetTypeInfo(WinDef.UINT iTInfo, WinDef.LCID lcid, PointerByReference ppTInfo) {
/* 252 */     return new WinNT.HRESULT(-2147467263);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT GetIDsOfNames(Guid.REFIID riid, WString[] rgszNames, int cNames, WinDef.LCID lcid, OaIdl.DISPIDByReference rgDispId) {
/* 258 */     return new WinNT.HRESULT(-2147467263);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT Invoke(OaIdl.DISPID dispIdMember, Guid.REFIID riid, WinDef.LCID lcid, WinDef.WORD wFlags, OleAuto.DISPPARAMS.ByReference pDispParams, Variant.VARIANT.ByReference pVarResult, OaIdl.EXCEPINFO.ByReference pExcepInfo, IntByReference puArgErr) {
/* 266 */     assert COMUtils.comIsInitialized() : "Assumption about COM threading broken.";
/*     */     
/* 268 */     invokeOnThread(dispIdMember, riid, lcid, wFlags, pDispParams);
/*     */     
/* 270 */     return WinError.S_OK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WinNT.HRESULT QueryInterface(Guid.REFIID refid, PointerByReference ppvObject) {
/* 276 */     if (null == ppvObject)
/* 277 */       return new WinNT.HRESULT(-2147467261); 
/* 278 */     if (refid.equals(this.listenedToRiid)) {
/* 279 */       ppvObject.setValue(getPointer());
/* 280 */       return WinError.S_OK;
/* 281 */     }  if (refid.getValue().equals(Unknown.IID_IUNKNOWN)) {
/* 282 */       ppvObject.setValue(getPointer());
/* 283 */       return WinError.S_OK;
/* 284 */     }  if (refid.getValue().equals(Dispatch.IID_IDISPATCH)) {
/* 285 */       ppvObject.setValue(getPointer());
/* 286 */       return WinError.S_OK;
/*     */     } 
/*     */     
/* 289 */     return new WinNT.HRESULT(-2147467262);
/*     */   }
/*     */   
/*     */   public int AddRef() {
/* 293 */     return 0;
/*     */   }
/*     */   
/*     */   public int Release() {
/* 297 */     return 0;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/CallbackProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */