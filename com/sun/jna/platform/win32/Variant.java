/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.Union;
/*     */ import com.sun.jna.platform.win32.COM.Dispatch;
/*     */ import com.sun.jna.platform.win32.COM.IDispatch;
/*     */ import com.sun.jna.platform.win32.COM.Unknown;
/*     */ import com.sun.jna.ptr.ByteByReference;
/*     */ import com.sun.jna.ptr.DoubleByReference;
/*     */ import com.sun.jna.ptr.FloatByReference;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import com.sun.jna.ptr.ShortByReference;
/*     */ import java.util.Date;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Variant
/*     */ {
/*     */   public static final int VT_EMPTY = 0;
/*     */   public static final int VT_NULL = 1;
/*     */   public static final int VT_I2 = 2;
/*     */   public static final int VT_I4 = 3;
/*     */   public static final int VT_R4 = 4;
/*     */   public static final int VT_R8 = 5;
/*     */   public static final int VT_CY = 6;
/*     */   public static final int VT_DATE = 7;
/*     */   public static final int VT_BSTR = 8;
/*     */   public static final int VT_DISPATCH = 9;
/*     */   public static final int VT_ERROR = 10;
/*     */   public static final int VT_BOOL = 11;
/*     */   public static final int VT_VARIANT = 12;
/*     */   public static final int VT_UNKNOWN = 13;
/*     */   public static final int VT_DECIMAL = 14;
/*     */   public static final int VT_I1 = 16;
/*     */   public static final int VT_UI1 = 17;
/*     */   public static final int VT_UI2 = 18;
/*     */   public static final int VT_UI4 = 19;
/*     */   public static final int VT_I8 = 20;
/*     */   public static final int VT_UI8 = 21;
/*     */   public static final int VT_INT = 22;
/*     */   public static final int VT_UINT = 23;
/*     */   public static final int VT_VOID = 24;
/*     */   public static final int VT_HRESULT = 25;
/*     */   public static final int VT_PTR = 26;
/*     */   public static final int VT_SAFEARRAY = 27;
/*     */   public static final int VT_CARRAY = 28;
/*     */   public static final int VT_USERDEFINED = 29;
/*     */   public static final int VT_LPSTR = 30;
/*     */   public static final int VT_LPWSTR = 31;
/*     */   public static final int VT_RECORD = 36;
/*     */   public static final int VT_INT_PTR = 37;
/*     */   public static final int VT_UINT_PTR = 38;
/*     */   public static final int VT_FILETIME = 64;
/*     */   public static final int VT_BLOB = 65;
/*     */   public static final int VT_STREAM = 66;
/*     */   public static final int VT_STORAGE = 67;
/*     */   public static final int VT_STREAMED_OBJECT = 68;
/*     */   public static final int VT_STORED_OBJECT = 69;
/*     */   public static final int VT_BLOB_OBJECT = 70;
/*     */   public static final int VT_CF = 71;
/*     */   public static final int VT_CLSID = 72;
/*     */   public static final int VT_VERSIONED_STREAM = 73;
/*     */   public static final int VT_BSTR_BLOB = 4095;
/*     */   public static final int VT_VECTOR = 4096;
/*     */   public static final int VT_ARRAY = 8192;
/*     */   public static final int VT_BYREF = 16384;
/*     */   public static final int VT_RESERVED = 32768;
/*     */   public static final int VT_ILLEGAL = 65535;
/*     */   public static final int VT_ILLEGALMASKED = 4095;
/*     */   public static final int VT_TYPEMASK = 4095;
/* 126 */   public static final OaIdl.VARIANT_BOOL VARIANT_TRUE = new OaIdl.VARIANT_BOOL(65535L);
/* 127 */   public static final OaIdl.VARIANT_BOOL VARIANT_FALSE = new OaIdl.VARIANT_BOOL(0L);
/*     */   
/*     */   @Deprecated
/*     */   public static final long COM_DAYS_ADJUSTMENT = 25569L;
/*     */   @Deprecated
/*     */   public static final long MICRO_SECONDS_PER_DAY = 86400000L;
/*     */   
/*     */   public static class VARIANT
/*     */     extends Union
/*     */   {
/*     */     public static class ByReference
/*     */       extends VARIANT
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference(Variant.VARIANT variant) {
/* 142 */         setValue(variant.getVarType(), variant.getValue());
/*     */       }
/*     */       
/*     */       public ByReference(Pointer variant) {
/* 146 */         super(variant);
/*     */       }
/*     */       
/*     */       public ByReference() {}
/*     */     }
/*     */     
/*     */     public static class ByValue
/*     */       extends VARIANT
/*     */       implements Structure.ByValue
/*     */     {
/*     */       public ByValue(Variant.VARIANT variant) {
/* 157 */         setValue(variant.getVarType(), variant.getValue());
/*     */       }
/*     */       
/*     */       public ByValue(Pointer variant) {
/* 161 */         super(variant);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public ByValue() {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 172 */     public static final VARIANT VARIANT_MISSING = new VARIANT(); public _VARIANT _variant; public OaIdl.DECIMAL decVal; static {
/* 173 */       VARIANT_MISSING.setValue(10, new WinDef.SCODE(-2147352572L));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public VARIANT() {
/* 181 */       setType("_variant");
/* 182 */       read();
/*     */     }
/*     */     
/*     */     public VARIANT(Pointer pointer) {
/* 186 */       super(pointer);
/* 187 */       setType("_variant");
/* 188 */       read();
/*     */     }
/*     */     
/*     */     public VARIANT(WTypes.BSTR value) {
/* 192 */       this();
/* 193 */       setValue(8, value);
/*     */     }
/*     */     
/*     */     public VARIANT(WTypes.BSTRByReference value) {
/* 197 */       this();
/* 198 */       setValue(16392, value);
/*     */     }
/*     */     
/*     */     public VARIANT(OaIdl.VARIANT_BOOL value) {
/* 202 */       this();
/* 203 */       setValue(11, value);
/*     */     }
/*     */     
/*     */     public VARIANT(WinDef.BOOL value) {
/* 207 */       this(value.booleanValue());
/*     */     }
/*     */     
/*     */     public VARIANT(WinDef.LONG value) {
/* 211 */       this();
/* 212 */       setValue(3, value);
/*     */     }
/*     */     
/*     */     public VARIANT(WinDef.SHORT value) {
/* 216 */       this();
/* 217 */       setValue(2, value);
/*     */     }
/*     */     
/*     */     public VARIANT(OaIdl.DATE value) {
/* 221 */       this();
/* 222 */       setValue(7, value);
/*     */     }
/*     */     
/*     */     public VARIANT(byte value) {
/* 226 */       this(new WinDef.BYTE(value));
/*     */     }
/*     */     
/*     */     public VARIANT(WinDef.BYTE value) {
/* 230 */       this();
/* 231 */       setValue(17, value);
/*     */     }
/*     */     
/*     */     public VARIANT(char value) {
/* 235 */       this();
/* 236 */       setValue(18, new WinDef.USHORT(value));
/*     */     }
/*     */     
/*     */     public VARIANT(WinDef.CHAR value) {
/* 240 */       this();
/* 241 */       setValue(16, value);
/*     */     }
/*     */     
/*     */     public VARIANT(short value) {
/* 245 */       this();
/* 246 */       setValue(2, new WinDef.SHORT(value));
/*     */     }
/*     */     
/*     */     public VARIANT(int value) {
/* 250 */       this();
/* 251 */       setValue(3, new WinDef.LONG(value));
/*     */     }
/*     */     
/*     */     public VARIANT(long value) {
/* 255 */       this();
/* 256 */       setValue(20, new WinDef.LONGLONG(value));
/*     */     }
/*     */     
/*     */     public VARIANT(float value) {
/* 260 */       this();
/* 261 */       setValue(4, Float.valueOf(value));
/*     */     }
/*     */     
/*     */     public VARIANT(double value) {
/* 265 */       this();
/* 266 */       setValue(5, Double.valueOf(value));
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
/*     */     public VARIANT(String value) {
/* 280 */       this();
/* 281 */       WTypes.BSTR bstrValue = OleAuto.INSTANCE.SysAllocString(value);
/* 282 */       setValue(8, bstrValue);
/*     */     }
/*     */     
/*     */     public VARIANT(boolean value) {
/* 286 */       this();
/* 287 */       setValue(11, new OaIdl.VARIANT_BOOL(value));
/*     */     }
/*     */     
/*     */     public VARIANT(IDispatch value) {
/* 291 */       this();
/* 292 */       setValue(9, value);
/*     */     }
/*     */     
/*     */     public VARIANT(Date value) {
/* 296 */       this();
/* 297 */       OaIdl.DATE date = fromJavaDate(value);
/* 298 */       setValue(7, date);
/*     */     }
/*     */     
/*     */     public VARIANT(OaIdl.SAFEARRAY array) {
/* 302 */       this();
/* 303 */       setValue(array);
/*     */     }
/*     */     
/*     */     public WTypes.VARTYPE getVarType() {
/* 307 */       read();
/* 308 */       return this._variant.vt;
/*     */     }
/*     */     
/*     */     public void setVarType(short vt) {
/* 312 */       this._variant.vt = new WTypes.VARTYPE(vt);
/*     */     }
/*     */     
/*     */     public void setValue(int vt, Object value) {
/* 316 */       setValue(new WTypes.VARTYPE(vt), value);
/*     */     }
/*     */     
/*     */     public void setValue(OaIdl.SAFEARRAY array) {
/* 320 */       setValue(array.getVarType().intValue() | 0x2000, array);
/*     */     }
/*     */     
/*     */     public void setValue(WTypes.VARTYPE vt, Object value) {
/* 324 */       int varType = vt.intValue();
/* 325 */       switch (varType) {
/*     */         case 17:
/* 327 */           this._variant.__variant.writeField("bVal", value);
/*     */           break;
/*     */         case 2:
/* 330 */           this._variant.__variant.writeField("iVal", value);
/*     */           break;
/*     */         case 3:
/* 333 */           this._variant.__variant.writeField("lVal", value);
/*     */           break;
/*     */         case 20:
/* 336 */           this._variant.__variant.writeField("llVal", value);
/*     */           break;
/*     */         case 4:
/* 339 */           this._variant.__variant.writeField("fltVal", value);
/*     */           break;
/*     */         case 5:
/* 342 */           this._variant.__variant.writeField("dblVal", value);
/*     */           break;
/*     */         case 11:
/* 345 */           this._variant.__variant.writeField("boolVal", value);
/*     */           break;
/*     */         case 10:
/* 348 */           this._variant.__variant.writeField("scode", value);
/*     */           break;
/*     */         case 6:
/* 351 */           this._variant.__variant.writeField("cyVal", value);
/*     */           break;
/*     */         case 7:
/* 354 */           this._variant.__variant.writeField("date", value);
/*     */           break;
/*     */         case 8:
/* 357 */           this._variant.__variant.writeField("bstrVal", value);
/*     */           break;
/*     */         case 13:
/* 360 */           this._variant.__variant.writeField("punkVal", value);
/*     */           break;
/*     */         case 9:
/* 363 */           this._variant.__variant.writeField("pdispVal", value);
/*     */           break;
/*     */         case 16401:
/* 366 */           this._variant.__variant.writeField("pbVal", value);
/*     */           break;
/*     */         case 16386:
/* 369 */           this._variant.__variant.writeField("piVal", value);
/*     */           break;
/*     */         case 16387:
/* 372 */           this._variant.__variant.writeField("plVal", value);
/*     */           break;
/*     */         case 16404:
/* 375 */           this._variant.__variant.writeField("pllVal", value);
/*     */           break;
/*     */         case 16388:
/* 378 */           this._variant.__variant.writeField("pfltVal", value);
/*     */           break;
/*     */         case 16389:
/* 381 */           this._variant.__variant.writeField("pdblVal", value);
/*     */           break;
/*     */         case 16395:
/* 384 */           this._variant.__variant.writeField("pboolVal", value);
/*     */           break;
/*     */         case 16394:
/* 387 */           this._variant.__variant.writeField("pscode", value);
/*     */           break;
/*     */         case 16390:
/* 390 */           this._variant.__variant.writeField("pcyVal", value);
/*     */           break;
/*     */         case 16391:
/* 393 */           this._variant.__variant.writeField("pdate", value);
/*     */           break;
/*     */         case 16392:
/* 396 */           this._variant.__variant.writeField("pbstrVal", value);
/*     */           break;
/*     */         case 16397:
/* 399 */           this._variant.__variant.writeField("ppunkVal", value);
/*     */           break;
/*     */         case 16393:
/* 402 */           this._variant.__variant.writeField("ppdispVal", value);
/*     */           break;
/*     */         case 16396:
/* 405 */           this._variant.__variant.writeField("pvarVal", value);
/*     */           break;
/*     */         case 16384:
/* 408 */           this._variant.__variant.writeField("byref", value);
/*     */           break;
/*     */         case 16:
/* 411 */           this._variant.__variant.writeField("cVal", value);
/*     */           break;
/*     */         case 18:
/* 414 */           this._variant.__variant.writeField("uiVal", value);
/*     */           break;
/*     */         case 19:
/* 417 */           this._variant.__variant.writeField("ulVal", value);
/*     */           break;
/*     */         case 21:
/* 420 */           this._variant.__variant.writeField("ullVal", value);
/*     */           break;
/*     */         case 22:
/* 423 */           this._variant.__variant.writeField("intVal", value);
/*     */           break;
/*     */         case 23:
/* 426 */           this._variant.__variant.writeField("uintVal", value);
/*     */           break;
/*     */         case 16398:
/* 429 */           this._variant.__variant.writeField("pdecVal", value);
/*     */           break;
/*     */         case 16400:
/* 432 */           this._variant.__variant.writeField("pcVal", value);
/*     */           break;
/*     */         case 16402:
/* 435 */           this._variant.__variant.writeField("puiVal", value);
/*     */           break;
/*     */         case 16403:
/* 438 */           this._variant.__variant.writeField("pulVal", value);
/*     */           break;
/*     */         case 16405:
/* 441 */           this._variant.__variant.writeField("pullVal", value);
/*     */           break;
/*     */         case 16406:
/* 444 */           this._variant.__variant.writeField("pintVal", value);
/*     */           break;
/*     */         case 16407:
/* 447 */           this._variant.__variant.writeField("puintVal", value);
/*     */           break;
/*     */         case 36:
/* 450 */           this._variant.__variant.writeField("pvRecord", value);
/*     */           break;
/*     */         default:
/* 453 */           if ((varType & 0x2000) > 0) {
/* 454 */             if ((varType & 0x4000) > 0) {
/* 455 */               this._variant.__variant.writeField("pparray", value); break;
/*     */             } 
/* 457 */             this._variant.__variant.writeField("parray", value);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */       
/* 462 */       this._variant.writeField("vt", vt);
/* 463 */       write();
/*     */     }
/*     */     
/*     */     public Object getValue() {
/* 467 */       read();
/* 468 */       int varType = getVarType().intValue();
/* 469 */       switch (getVarType().intValue()) {
/*     */         case 17:
/* 471 */           return this._variant.__variant.readField("bVal");
/*     */         case 2:
/* 473 */           return this._variant.__variant.readField("iVal");
/*     */         case 3:
/* 475 */           return this._variant.__variant.readField("lVal");
/*     */         case 20:
/* 477 */           return this._variant.__variant.readField("llVal");
/*     */         case 4:
/* 479 */           return this._variant.__variant.readField("fltVal");
/*     */         case 5:
/* 481 */           return this._variant.__variant.readField("dblVal");
/*     */         case 11:
/* 483 */           return this._variant.__variant.readField("boolVal");
/*     */         case 10:
/* 485 */           return this._variant.__variant.readField("scode");
/*     */         case 6:
/* 487 */           return this._variant.__variant.readField("cyVal");
/*     */         case 7:
/* 489 */           return this._variant.__variant.readField("date");
/*     */         case 8:
/* 491 */           return this._variant.__variant.readField("bstrVal");
/*     */         case 13:
/* 493 */           return this._variant.__variant.readField("punkVal");
/*     */         case 9:
/* 495 */           return this._variant.__variant.readField("pdispVal");
/*     */         case 16401:
/* 497 */           return this._variant.__variant.readField("pbVal");
/*     */         case 16386:
/* 499 */           return this._variant.__variant.readField("piVal");
/*     */         case 16387:
/* 501 */           return this._variant.__variant.readField("plVal");
/*     */         case 16404:
/* 503 */           return this._variant.__variant.readField("pllVal");
/*     */         case 16388:
/* 505 */           return this._variant.__variant.readField("pfltVal");
/*     */         case 16389:
/* 507 */           return this._variant.__variant.readField("pdblVal");
/*     */         case 16395:
/* 509 */           return this._variant.__variant.readField("pboolVal");
/*     */         case 16394:
/* 511 */           return this._variant.__variant.readField("pscode");
/*     */         case 16390:
/* 513 */           return this._variant.__variant.readField("pcyVal");
/*     */         case 16391:
/* 515 */           return this._variant.__variant.readField("pdate");
/*     */         case 16392:
/* 517 */           return this._variant.__variant.readField("pbstrVal");
/*     */         case 16397:
/* 519 */           return this._variant.__variant.readField("ppunkVal");
/*     */         case 16393:
/* 521 */           return this._variant.__variant.readField("ppdispVal");
/*     */         case 16396:
/* 523 */           return this._variant.__variant.readField("pvarVal");
/*     */         case 16384:
/* 525 */           return this._variant.__variant.readField("byref");
/*     */         case 16:
/* 527 */           return this._variant.__variant.readField("cVal");
/*     */         case 18:
/* 529 */           return this._variant.__variant.readField("uiVal");
/*     */         case 19:
/* 531 */           return this._variant.__variant.readField("ulVal");
/*     */         case 21:
/* 533 */           return this._variant.__variant.readField("ullVal");
/*     */         case 22:
/* 535 */           return this._variant.__variant.readField("intVal");
/*     */         case 23:
/* 537 */           return this._variant.__variant.readField("uintVal");
/*     */         case 16398:
/* 539 */           return this._variant.__variant.readField("pdecVal");
/*     */         case 16400:
/* 541 */           return this._variant.__variant.readField("pcVal");
/*     */         case 16402:
/* 543 */           return this._variant.__variant.readField("puiVal");
/*     */         case 16403:
/* 545 */           return this._variant.__variant.readField("pulVal");
/*     */         case 16405:
/* 547 */           return this._variant.__variant.readField("pullVal");
/*     */         case 16406:
/* 549 */           return this._variant.__variant.readField("pintVal");
/*     */         case 16407:
/* 551 */           return this._variant.__variant.readField("puintVal");
/*     */         case 36:
/* 553 */           return this._variant.__variant.readField("pvRecord");
/*     */       } 
/* 555 */       if ((varType & 0x2000) > 0) {
/* 556 */         if ((varType & 0x4000) > 0) {
/* 557 */           return this._variant.__variant.readField("pparray");
/*     */         }
/* 559 */         return this._variant.__variant.readField("parray");
/*     */       } 
/*     */       
/* 562 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public byte byteValue() {
/* 567 */       return ((Number)getValue()).byteValue();
/*     */     }
/*     */     
/*     */     public short shortValue() {
/* 571 */       return ((Number)getValue()).shortValue();
/*     */     }
/*     */     
/*     */     public int intValue() {
/* 575 */       return ((Number)getValue()).intValue();
/*     */     }
/*     */     
/*     */     public long longValue() {
/* 579 */       return ((Number)getValue()).longValue();
/*     */     }
/*     */     
/*     */     public float floatValue() {
/* 583 */       return ((Number)getValue()).floatValue();
/*     */     }
/*     */     
/*     */     public double doubleValue() {
/* 587 */       return ((Number)getValue()).doubleValue();
/*     */     }
/*     */     
/*     */     public String stringValue() {
/* 591 */       WTypes.BSTR bstr = (WTypes.BSTR)getValue();
/* 592 */       if (bstr == null) {
/* 593 */         return null;
/*     */       }
/* 595 */       return bstr.getValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean booleanValue() {
/* 601 */       return ((OaIdl.VARIANT_BOOL)getValue()).booleanValue();
/*     */     }
/*     */     
/*     */     public Date dateValue() {
/* 605 */       OaIdl.DATE varDate = (OaIdl.DATE)getValue();
/* 606 */       if (varDate == null) {
/* 607 */         return null;
/*     */       }
/* 609 */       return varDate.getAsJavaDate();
/*     */     }
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     protected Date toJavaDate(OaIdl.DATE varDate) {
/* 615 */       return varDate.getAsJavaDate();
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     protected OaIdl.DATE fromJavaDate(Date javaDate) {
/* 620 */       return new OaIdl.DATE(javaDate);
/*     */     }
/*     */     
/*     */     public static class _VARIANT extends Structure {
/* 624 */       public static final List<String> FIELDS = createFieldsOrder(new String[] { "vt", "wReserved1", "wReserved2", "wReserved3", "__variant" }); public WTypes.VARTYPE vt; public short wReserved1; public short wReserved2; public short wReserved3; public __VARIANT __variant;
/*     */       public static class __VARIANT extends Union { public WinDef.LONGLONG llVal; public WinDef.LONG lVal; public WinDef.BYTE bVal; public WinDef.SHORT iVal; public Float fltVal; public Double dblVal; public OaIdl.VARIANT_BOOL boolVal; public WinDef.SCODE scode; public OaIdl.CURRENCY cyVal; public OaIdl.DATE date; public WTypes.BSTR bstrVal; public Unknown punkVal; public Dispatch pdispVal; public OaIdl.SAFEARRAY.ByReference parray; public ByteByReference pbVal; public ShortByReference piVal; public WinDef.LONGByReference plVal; public WinDef.LONGLONGByReference pllVal; public FloatByReference pfltVal; public DoubleByReference pdblVal; public OaIdl.VARIANT_BOOLByReference pboolVal; public OaIdl._VARIANT_BOOLByReference pbool; public WinDef.SCODEByReference pscode; public OaIdl.CURRENCY.ByReference pcyVal; public OaIdl.DATE.ByReference pdate; public WTypes.BSTR.ByReference pbstrVal; public Unknown.ByReference ppunkVal; public Dispatch.ByReference ppdispVal; public OaIdl.SAFEARRAY.ByReference pparray; public Variant.VARIANT.ByReference pvarVal; public WinDef.PVOID byref; public WinDef.CHAR cVal; public WinDef.USHORT uiVal; public WinDef.ULONG ulVal; public WinDef.ULONGLONG ullVal; public Integer intVal; public WinDef.UINT uintVal; public OaIdl.DECIMAL.ByReference pdecVal; public WinDef.CHARByReference pcVal; public WinDef.USHORTByReference puiVal; public WinDef.ULONGByReference pulVal;
/*     */         public WinDef.ULONGLONGByReference pullVal;
/*     */         public IntByReference pintVal;
/*     */         public WinDef.UINTByReference puintVal;
/*     */         public BRECORD pvRecord;
/*     */         
/*     */         public static class BRECORD extends Structure { public static class ByReference extends BRECORD implements Structure.ByReference {}
/*     */           
/* 633 */           public static final List<String> FIELDS = createFieldsOrder(new String[] { "pvRecord", "pRecInfo" });
/*     */           
/*     */           public WinDef.PVOID pvRecord;
/*     */           
/*     */           public Pointer pRecInfo;
/*     */ 
/*     */           
/*     */           public BRECORD() {}
/*     */           
/*     */           public BRECORD(Pointer pointer) {
/* 643 */             super(pointer);
/*     */           }
/*     */ 
/*     */           
/*     */           protected List<String> getFieldOrder() {
/* 648 */             return FIELDS;
/*     */           } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public __VARIANT() {
/* 745 */           read();
/*     */         }
/*     */         
/*     */         public __VARIANT(Pointer pointer) {
/* 749 */           super(pointer);
/* 750 */           read();
/*     */         } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public _VARIANT() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public _VARIANT(Pointer pointer) {
/* 765 */         super(pointer);
/* 766 */         read();
/*     */       }
/*     */ 
/*     */       
/*     */       protected List<String> getFieldOrder() {
/* 771 */         return FIELDS;
/*     */       }
/*     */     } public static class __VARIANT extends Union { public WinDef.LONGLONG llVal; public WinDef.LONG lVal; public WinDef.BYTE bVal; public WinDef.SHORT iVal; public Float fltVal; public Double dblVal; public OaIdl.VARIANT_BOOL boolVal; public WinDef.SCODE scode; public OaIdl.CURRENCY cyVal; public OaIdl.DATE date; public WTypes.BSTR bstrVal; public Unknown punkVal; public Dispatch pdispVal; public OaIdl.SAFEARRAY.ByReference parray; public ByteByReference pbVal; public ShortByReference piVal; public WinDef.LONGByReference plVal; public WinDef.LONGLONGByReference pllVal; public FloatByReference pfltVal; public DoubleByReference pdblVal; public OaIdl.VARIANT_BOOLByReference pboolVal; public OaIdl._VARIANT_BOOLByReference pbool; public WinDef.SCODEByReference pscode; public OaIdl.CURRENCY.ByReference pcyVal; public OaIdl.DATE.ByReference pdate; public WTypes.BSTR.ByReference pbstrVal; public Unknown.ByReference ppunkVal; public Dispatch.ByReference ppdispVal; public OaIdl.SAFEARRAY.ByReference pparray; public Variant.VARIANT.ByReference pvarVal; public WinDef.PVOID byref; public WinDef.CHAR cVal; public WinDef.USHORT uiVal; public WinDef.ULONG ulVal; public WinDef.ULONGLONG ullVal; public Integer intVal; public WinDef.UINT uintVal; public OaIdl.DECIMAL.ByReference pdecVal; public WinDef.CHARByReference pcVal; public WinDef.USHORTByReference puiVal; public WinDef.ULONGByReference pulVal; public WinDef.ULONGLONGByReference pullVal; public IntByReference pintVal; public WinDef.UINTByReference puintVal; public BRECORD pvRecord; public static class BRECORD extends Structure { public static class ByReference extends BRECORD implements Structure.ByReference {} public static final List<String> FIELDS = createFieldsOrder(new String[] { "pvRecord", "pRecInfo" }); public WinDef.PVOID pvRecord; public Pointer pRecInfo; public BRECORD() {} public BRECORD(Pointer pointer) {
/*     */           super(pointer);
/*     */         } protected List<String> getFieldOrder() {
/*     */           return FIELDS;
/*     */         } } public __VARIANT() {
/*     */         read();
/*     */       } public __VARIANT(Pointer pointer) {
/*     */         super(pointer);
/*     */         read();
/*     */       } }
/*     */   } public static class VariantArg extends Structure { public static class ByReference extends VariantArg implements Structure.ByReference { public ByReference(Variant.VARIANT[] variantArg) {
/* 784 */         this.variantArg = variantArg;
/*     */       }
/*     */       public ByReference() {} }
/*     */     
/* 788 */     public static final List<String> FIELDS = createFieldsOrder("variantArg");
/* 789 */     public Variant.VARIANT[] variantArg = new Variant.VARIANT[1];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public VariantArg() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public VariantArg(Pointer pointer) {
/* 800 */       super(pointer);
/*     */     }
/*     */     
/*     */     public VariantArg(Variant.VARIANT[] variantArg) {
/* 804 */       this.variantArg = variantArg;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 809 */       return FIELDS;
/*     */     }
/*     */     
/*     */     public void setArraySize(int size) {
/* 813 */       this.variantArg = new Variant.VARIANT[size];
/* 814 */       read();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Variant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */