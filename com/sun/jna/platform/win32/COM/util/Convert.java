/*     */ package com.sun.jna.platform.win32.COM.util;
/*     */ 
/*     */ import com.sun.jna.platform.win32.COM.IDispatch;
/*     */ import com.sun.jna.platform.win32.COM.IUnknown;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.Variant;
/*     */ import com.sun.jna.platform.win32.WTypes;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Date;
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
/*     */ class Convert
/*     */ {
/*     */   public static Variant.VARIANT toVariant(Object value) {
/*  95 */     if (value instanceof Variant.VARIANT)
/*  96 */       return (Variant.VARIANT)value; 
/*  97 */     if (value instanceof WTypes.BSTR)
/*  98 */       return new Variant.VARIANT((WTypes.BSTR)value); 
/*  99 */     if (value instanceof OaIdl.VARIANT_BOOL)
/* 100 */       return new Variant.VARIANT((OaIdl.VARIANT_BOOL)value); 
/* 101 */     if (value instanceof WinDef.BOOL)
/* 102 */       return new Variant.VARIANT((WinDef.BOOL)value); 
/* 103 */     if (value instanceof WinDef.LONG)
/* 104 */       return new Variant.VARIANT((WinDef.LONG)value); 
/* 105 */     if (value instanceof WinDef.SHORT)
/* 106 */       return new Variant.VARIANT((WinDef.SHORT)value); 
/* 107 */     if (value instanceof OaIdl.DATE)
/* 108 */       return new Variant.VARIANT((OaIdl.DATE)value); 
/* 109 */     if (value instanceof WinDef.BYTE)
/* 110 */       return new Variant.VARIANT((WinDef.BYTE)value); 
/* 111 */     if (value instanceof Byte)
/* 112 */       return new Variant.VARIANT(((Byte)value).byteValue()); 
/* 113 */     if (value instanceof Character)
/* 114 */       return new Variant.VARIANT(((Character)value).charValue()); 
/* 115 */     if (value instanceof WinDef.CHAR)
/* 116 */       return new Variant.VARIANT((WinDef.CHAR)value); 
/* 117 */     if (value instanceof Short)
/* 118 */       return new Variant.VARIANT(((Short)value).shortValue()); 
/* 119 */     if (value instanceof Integer)
/* 120 */       return new Variant.VARIANT(((Integer)value).intValue()); 
/* 121 */     if (value instanceof Long)
/* 122 */       return new Variant.VARIANT(((Long)value).longValue()); 
/* 123 */     if (value instanceof Float)
/* 124 */       return new Variant.VARIANT(((Float)value).floatValue()); 
/* 125 */     if (value instanceof Double)
/* 126 */       return new Variant.VARIANT(((Double)value).doubleValue()); 
/* 127 */     if (value instanceof String)
/* 128 */       return new Variant.VARIANT((String)value); 
/* 129 */     if (value instanceof Boolean)
/* 130 */       return new Variant.VARIANT(((Boolean)value).booleanValue()); 
/* 131 */     if (value instanceof IDispatch)
/* 132 */       return new Variant.VARIANT((IDispatch)value); 
/* 133 */     if (value instanceof Date)
/* 134 */       return new Variant.VARIANT((Date)value); 
/* 135 */     if (value instanceof Proxy) {
/* 136 */       InvocationHandler ih = Proxy.getInvocationHandler(value);
/* 137 */       ProxyObject pobj = (ProxyObject)ih;
/* 138 */       return new Variant.VARIANT(pobj.getRawDispatch());
/* 139 */     }  if (value instanceof IComEnum) {
/* 140 */       IComEnum enm = (IComEnum)value;
/* 141 */       return new Variant.VARIANT(new WinDef.LONG(enm.getValue()));
/* 142 */     }  if (value instanceof OaIdl.SAFEARRAY) {
/* 143 */       return new Variant.VARIANT((OaIdl.SAFEARRAY)value);
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */   
/*     */   public static Object toJavaObject(Variant.VARIANT value, Class<?> targetClass, ObjectFactory factory, boolean addReference, boolean freeValue) {
/*     */     Object result;
/* 150 */     if (null == value || value
/* 151 */       .getVarType().intValue() == 0 || value
/* 152 */       .getVarType().intValue() == 1) {
/* 153 */       return null;
/*     */     }
/*     */     
/* 156 */     if (targetClass != null && !targetClass.isAssignableFrom(Object.class)) {
/* 157 */       if (targetClass.isAssignableFrom(value.getClass())) {
/* 158 */         return value;
/*     */       }
/*     */       
/* 161 */       Object vobj = value.getValue();
/* 162 */       if (vobj != null && targetClass.isAssignableFrom(vobj.getClass())) {
/* 163 */         return vobj;
/*     */       }
/*     */     } 
/*     */     
/* 167 */     Variant.VARIANT inputValue = value;
/*     */     
/* 169 */     if (value.getVarType().intValue() == 16396) {
/* 170 */       value = (Variant.VARIANT)value.getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 175 */     if (targetClass == null || targetClass.isAssignableFrom(Object.class)) {
/*     */       
/* 177 */       targetClass = null;
/*     */       
/* 179 */       int varType = value.getVarType().intValue();
/*     */       
/* 181 */       switch (value.getVarType().intValue()) {
/*     */         case 16:
/*     */         case 17:
/*     */         case 16400:
/*     */         case 16401:
/* 186 */           targetClass = Byte.class;
/*     */           break;
/*     */         case 2:
/*     */         case 16386:
/* 190 */           targetClass = Short.class;
/*     */           break;
/*     */         case 18:
/*     */         case 16402:
/* 194 */           targetClass = Character.class;
/*     */           break;
/*     */         case 3:
/*     */         case 19:
/*     */         case 22:
/*     */         case 23:
/*     */         case 16387:
/*     */         case 16403:
/*     */         case 16406:
/*     */         case 16407:
/* 204 */           targetClass = Integer.class;
/*     */           break;
/*     */         case 20:
/*     */         case 21:
/*     */         case 16404:
/*     */         case 16405:
/* 210 */           targetClass = Long.class;
/*     */           break;
/*     */         case 4:
/*     */         case 16388:
/* 214 */           targetClass = Float.class;
/*     */           break;
/*     */         case 5:
/*     */         case 16389:
/* 218 */           targetClass = Double.class;
/*     */           break;
/*     */         case 11:
/*     */         case 16395:
/* 222 */           targetClass = Boolean.class;
/*     */           break;
/*     */         case 10:
/*     */         case 16394:
/* 226 */           targetClass = WinDef.SCODE.class;
/*     */           break;
/*     */         case 6:
/*     */         case 16390:
/* 230 */           targetClass = OaIdl.CURRENCY.class;
/*     */           break;
/*     */         case 7:
/*     */         case 16391:
/* 234 */           targetClass = Date.class;
/*     */           break;
/*     */         case 8:
/*     */         case 16392:
/* 238 */           targetClass = String.class;
/*     */           break;
/*     */         case 13:
/*     */         case 16397:
/* 242 */           targetClass = IUnknown.class;
/*     */           break;
/*     */         case 9:
/*     */         case 16393:
/* 246 */           targetClass = IDispatch.class;
/*     */           break;
/*     */         case 16396:
/* 249 */           targetClass = Variant.class;
/*     */           break;
/*     */         case 16384:
/* 252 */           targetClass = WinDef.PVOID.class;
/*     */           break;
/*     */         case 16398:
/* 255 */           targetClass = OaIdl.DECIMAL.class;
/*     */           break;
/*     */         
/*     */         default:
/* 259 */           if ((varType & 0x2000) > 0) {
/* 260 */             targetClass = OaIdl.SAFEARRAY.class;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 266 */     if (Byte.class.equals(targetClass) || byte.class.equals(targetClass)) {
/* 267 */       result = Byte.valueOf(value.byteValue());
/* 268 */     } else if (Short.class.equals(targetClass) || short.class.equals(targetClass)) {
/* 269 */       result = Short.valueOf(value.shortValue());
/* 270 */     } else if (Character.class.equals(targetClass) || char.class.equals(targetClass)) {
/* 271 */       result = Character.valueOf((char)value.intValue());
/* 272 */     } else if (Integer.class.equals(targetClass) || int.class.equals(targetClass)) {
/* 273 */       result = Integer.valueOf(value.intValue());
/* 274 */     } else if (Long.class.equals(targetClass) || long.class.equals(targetClass) || IComEnum.class.isAssignableFrom(targetClass)) {
/* 275 */       result = Long.valueOf(value.longValue());
/* 276 */     } else if (Float.class.equals(targetClass) || float.class.equals(targetClass)) {
/* 277 */       result = Float.valueOf(value.floatValue());
/* 278 */     } else if (Double.class.equals(targetClass) || double.class.equals(targetClass)) {
/* 279 */       result = Double.valueOf(value.doubleValue());
/* 280 */     } else if (Boolean.class.equals(targetClass) || boolean.class.equals(targetClass)) {
/* 281 */       result = Boolean.valueOf(value.booleanValue());
/* 282 */     } else if (Date.class.equals(targetClass)) {
/* 283 */       result = value.dateValue();
/* 284 */     } else if (String.class.equals(targetClass)) {
/* 285 */       result = value.stringValue();
/* 286 */     } else if (value.getValue() instanceof IDispatch) {
/* 287 */       IDispatch d = (IDispatch)value.getValue();
/* 288 */       Object proxy = factory.createProxy(targetClass, d);
/*     */ 
/*     */       
/* 291 */       if (!addReference) {
/* 292 */         int i = d.Release();
/*     */       }
/* 294 */       result = proxy;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 305 */       result = value.getValue();
/*     */     } 
/*     */     
/* 308 */     if (IComEnum.class.isAssignableFrom(targetClass)) {
/* 309 */       result = targetClass.cast(toComEnum(targetClass, result));
/*     */     }
/*     */     
/* 312 */     if (freeValue) {
/* 313 */       free(inputValue, result);
/*     */     }
/*     */     
/* 316 */     return result;
/*     */   }
/*     */   
/*     */   public static <T extends IComEnum> T toComEnum(Class<T> enumType, Object value) {
/*     */     
/* 321 */     try { Method m = enumType.getMethod("values", new Class[0]);
/* 322 */       IComEnum[] arrayOfIComEnum = (IComEnum[])m.invoke(null, new Object[0]);
/* 323 */       for (IComEnum iComEnum : arrayOfIComEnum) {
/* 324 */         if (value.equals(Long.valueOf(iComEnum.getValue()))) {
/* 325 */           return (T)iComEnum;
/*     */         }
/*     */       }  }
/* 328 */     catch (NoSuchMethodException noSuchMethodException) {  }
/* 329 */     catch (IllegalAccessException illegalAccessException) {  }
/* 330 */     catch (IllegalArgumentException illegalArgumentException) {  }
/* 331 */     catch (InvocationTargetException invocationTargetException) {}
/*     */     
/* 333 */     return null;
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
/*     */   public static void free(Variant.VARIANT variant, Class<?> javaType) {
/* 346 */     if ((javaType == null || !WTypes.BSTR.class.isAssignableFrom(javaType)) && variant != null && variant
/*     */       
/* 348 */       .getVarType().intValue() == 8) {
/* 349 */       Object value = variant.getValue();
/* 350 */       if (value instanceof WTypes.BSTR) {
/* 351 */         OleAuto.INSTANCE.SysFreeString((WTypes.BSTR)value);
/*     */       }
/*     */     } 
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
/*     */   public static void free(Variant.VARIANT variant, Object value) {
/* 366 */     free(variant, (value == null) ? null : value.getClass());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/util/Convert.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */