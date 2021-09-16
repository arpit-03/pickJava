/*      */ package com.sun.jna.platform.win32;
/*      */ 
/*      */ import com.sun.jna.IntegerType;
/*      */ import com.sun.jna.Memory;
/*      */ import com.sun.jna.NativeLong;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.platform.win32.COM.COMUtils;
/*      */ import com.sun.jna.platform.win32.COM.Dispatch;
/*      */ import com.sun.jna.platform.win32.COM.TypeComp;
/*      */ import com.sun.jna.platform.win32.COM.Unknown;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import java.io.Closeable;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface OaIdl
/*      */ {
/*   90 */   public static final long DATE_OFFSET = (new Date(-1, 11, 30, 0, 0, 0)).getTime();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class EXCEPINFO
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends EXCEPINFO
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */     
/*  104 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "wCode", "wReserved", "bstrSource", "bstrDescription", "bstrHelpFile", "dwHelpContext", "pvReserved", "pfnDeferredFillIn", "scode" });
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wCode;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.WORD wReserved;
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.BSTR bstrSource;
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.BSTR bstrDescription;
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.BSTR bstrHelpFile;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.DWORD dwHelpContext;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.PVOID pvReserved;
/*      */ 
/*      */ 
/*      */     
/*      */     public ByReference pfnDeferredFillIn;
/*      */ 
/*      */ 
/*      */     
/*      */     public WinDef.SCODE scode;
/*      */ 
/*      */ 
/*      */     
/*      */     public EXCEPINFO() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public EXCEPINFO(Pointer p) {
/*  149 */       super(p);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  154 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class VARIANT_BOOL extends IntegerType {
/*      */     private static final long serialVersionUID = 1L;
/*      */     public static final int SIZE = 2;
/*      */     
/*      */     public VARIANT_BOOL() {
/*  163 */       this(0L);
/*      */     }
/*      */     
/*      */     public VARIANT_BOOL(long value) {
/*  167 */       super(2, value);
/*      */     }
/*      */     
/*      */     public VARIANT_BOOL(boolean value) {
/*  171 */       this(value ? 65535L : 0L);
/*      */     }
/*      */     
/*      */     public boolean booleanValue() {
/*  175 */       return (shortValue() != 0);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class _VARIANT_BOOL extends VARIANT_BOOL {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public _VARIANT_BOOL() {
/*  183 */       this(0L);
/*      */     }
/*      */     
/*      */     public _VARIANT_BOOL(long value) {
/*  187 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class VARIANT_BOOLByReference extends com.sun.jna.ptr.ByReference {
/*      */     public VARIANT_BOOLByReference() {
/*  193 */       this(new OaIdl.VARIANT_BOOL(0L));
/*      */     }
/*      */     
/*      */     public VARIANT_BOOLByReference(OaIdl.VARIANT_BOOL value) {
/*  197 */       super(2);
/*  198 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.VARIANT_BOOL value) {
/*  202 */       getPointer().setShort(0L, value.shortValue());
/*      */     }
/*      */     
/*      */     public OaIdl.VARIANT_BOOL getValue() {
/*  206 */       return new OaIdl.VARIANT_BOOL(getPointer().getShort(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   public static class _VARIANT_BOOLByReference extends com.sun.jna.ptr.ByReference {
/*      */     public _VARIANT_BOOLByReference() {
/*  212 */       this(new OaIdl.VARIANT_BOOL(0L));
/*      */     }
/*      */     
/*      */     public _VARIANT_BOOLByReference(OaIdl.VARIANT_BOOL value) {
/*  216 */       super(2);
/*  217 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.VARIANT_BOOL value) {
/*  221 */       getPointer().setShort(0L, value.shortValue());
/*      */     }
/*      */     
/*      */     public OaIdl.VARIANT_BOOL getValue() {
/*  225 */       return new OaIdl.VARIANT_BOOL(getPointer().getShort(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DATE
/*      */     extends Structure {
/*      */     private static final long MICRO_SECONDS_PER_DAY = 86400000L;
/*      */     
/*      */     public static class ByReference
/*      */       extends DATE implements Structure.ByReference {}
/*      */     
/*  236 */     public static final List<String> FIELDS = createFieldsOrder("date");
/*      */     
/*      */     public double date;
/*      */ 
/*      */     
/*      */     public DATE() {}
/*      */     
/*      */     public DATE(double date) {
/*  244 */       this.date = date;
/*      */     }
/*      */     
/*      */     public DATE(Date javaDate) {
/*  248 */       setFromJavaDate(javaDate);
/*      */     }
/*      */     
/*      */     public Date getAsJavaDate() {
/*  252 */       long days = (long)this.date * 86400000L + OaIdl.DATE_OFFSET;
/*  253 */       double timePart = 24.0D * Math.abs(this.date - (long)this.date);
/*  254 */       int hours = (int)timePart;
/*  255 */       timePart = 60.0D * (timePart - (int)timePart);
/*  256 */       int minutes = (int)timePart;
/*  257 */       timePart = 60.0D * (timePart - (int)timePart);
/*  258 */       int seconds = (int)timePart;
/*  259 */       timePart = 1000.0D * (timePart - (int)timePart);
/*  260 */       int milliseconds = (int)timePart;
/*      */       
/*  262 */       Date baseDate = new Date(days);
/*  263 */       baseDate.setHours(hours);
/*  264 */       baseDate.setMinutes(minutes);
/*  265 */       baseDate.setSeconds(seconds);
/*  266 */       baseDate.setTime(baseDate.getTime() + milliseconds);
/*  267 */       return baseDate;
/*      */     }
/*      */     
/*      */     public void setFromJavaDate(Date javaDate) {
/*  271 */       double msSinceOrigin = (javaDate.getTime() - OaIdl.DATE_OFFSET);
/*  272 */       double daysAsFract = msSinceOrigin / 8.64E7D;
/*      */       
/*  274 */       Date dayDate = new Date(javaDate.getTime());
/*  275 */       dayDate.setHours(0);
/*  276 */       dayDate.setMinutes(0);
/*  277 */       dayDate.setSeconds(0);
/*  278 */       dayDate.setTime(dayDate.getTime() / 1000L * 1000L);
/*      */       
/*  280 */       double integralPart = Math.floor(daysAsFract);
/*  281 */       double fractionalPart = Math.signum(daysAsFract) * (javaDate.getTime() - dayDate.getTime()) / 8.64E7D;
/*      */       
/*  283 */       this.date = integralPart + fractionalPart;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  288 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class DISPID
/*      */     extends WinDef.LONG
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public DISPID() {
/*  299 */       this(0);
/*      */     }
/*      */     
/*      */     public DISPID(int value) {
/*  303 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DISPIDByReference extends com.sun.jna.ptr.ByReference {
/*      */     public DISPIDByReference() {
/*  309 */       this(new OaIdl.DISPID(0));
/*      */     }
/*      */     
/*      */     public DISPIDByReference(OaIdl.DISPID value) {
/*  313 */       super(OaIdl.DISPID.SIZE);
/*  314 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.DISPID value) {
/*  318 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */     
/*      */     public OaIdl.DISPID getValue() {
/*  322 */       return new OaIdl.DISPID(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MEMBERID extends DISPID {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public MEMBERID() {
/*  330 */       this(0);
/*      */     }
/*      */     
/*      */     public MEMBERID(int value) {
/*  334 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class MEMBERIDByReference extends com.sun.jna.ptr.ByReference {
/*      */     public MEMBERIDByReference() {
/*  340 */       this(new OaIdl.MEMBERID(0));
/*      */     }
/*      */     
/*      */     public MEMBERIDByReference(OaIdl.MEMBERID value) {
/*  344 */       super(OaIdl.MEMBERID.SIZE);
/*  345 */       setValue(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.MEMBERID value) {
/*  349 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */     
/*      */     public OaIdl.MEMBERID getValue() {
/*  353 */       return new OaIdl.MEMBERID(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  360 */   public static final DISPID DISPID_COLLECT = new DISPID(-8);
/*      */ 
/*      */ 
/*      */   
/*  364 */   public static final DISPID DISPID_CONSTRUCTOR = new DISPID(-6);
/*      */ 
/*      */ 
/*      */   
/*  368 */   public static final DISPID DISPID_DESTRUCTOR = new DISPID(-7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  374 */   public static final DISPID DISPID_EVALUATE = new DISPID(-5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  380 */   public static final DISPID DISPID_NEWENUM = new DISPID(-4);
/*      */ 
/*      */ 
/*      */   
/*  384 */   public static final DISPID DISPID_PROPERTYPUT = new DISPID(-3);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  389 */   public static final DISPID DISPID_UNKNOWN = new DISPID(-1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  395 */   public static final DISPID DISPID_VALUE = new DISPID(0);
/*      */   
/*  397 */   public static final MEMBERID MEMBERID_NIL = new MEMBERID(DISPID_UNKNOWN
/*  398 */       .intValue());
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FADF_AUTO = 1;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FADF_STATIC = 2;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FADF_EMBEDDED = 4;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int FADF_FIXEDSIZE = 16;
/*      */ 
/*      */   
/*      */   public static final int FADF_RECORD = 32;
/*      */ 
/*      */   
/*      */   public static final int FADF_HAVEIID = 64;
/*      */ 
/*      */   
/*      */   public static final int FADF_HAVEVARTYPE = 128;
/*      */ 
/*      */   
/*      */   public static final int FADF_BSTR = 256;
/*      */ 
/*      */   
/*      */   public static final int FADF_UNKNOWN = 512;
/*      */ 
/*      */   
/*      */   public static final int FADF_DISPATCH = 1024;
/*      */ 
/*      */   
/*      */   public static final int FADF_VARIANT = 2048;
/*      */ 
/*      */   
/*      */   public static final int FADF_RESERVED = 61448;
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TYPEKIND
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends TYPEKIND
/*      */       implements Structure.ByReference
/*      */     {
/*      */       public ByReference() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public ByReference(int value) {
/*  454 */         super(value);
/*      */       }
/*      */       
/*      */       public ByReference(OaIdl.TYPEKIND typekind) {
/*  458 */         super(typekind.getPointer());
/*  459 */         this.value = typekind.value;
/*      */       }
/*      */     }
/*      */     
/*  463 */     public static final List<String> FIELDS = createFieldsOrder("value"); public int value; public static final int TKIND_ENUM = 0; public static final int TKIND_RECORD = 1; public static final int TKIND_MODULE = 2; public static final int TKIND_INTERFACE = 3; public static final int TKIND_DISPATCH = 4; public static final int TKIND_COCLASS = 5;
/*      */     public static final int TKIND_ALIAS = 6;
/*      */     public static final int TKIND_UNION = 7;
/*      */     public static final int TKIND_MAX = 8;
/*      */     
/*      */     public TYPEKIND() {}
/*      */     
/*      */     public TYPEKIND(int value) {
/*  471 */       this.value = value;
/*      */     }
/*      */     
/*      */     public TYPEKIND(Pointer pointer) {
/*  475 */       super(pointer);
/*  476 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  500 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DESCKIND
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends DESCKIND implements Structure.ByReference {}
/*      */     
/*  509 */     public static final List<String> FIELDS = createFieldsOrder("value"); public int value; public static final int DESCKIND_NONE = 0; public static final int DESCKIND_FUNCDESC = 1;
/*      */     public static final int DESCKIND_VARDESC = 2;
/*      */     public static final int DESCKIND_TYPECOMP = 3;
/*      */     public static final int DESCKIND_IMPLICITAPPOBJ = 4;
/*      */     public static final int DESCKIND_MAX = 5;
/*      */     
/*      */     public DESCKIND() {}
/*      */     
/*      */     public DESCKIND(int value) {
/*  518 */       this.value = value;
/*      */     }
/*      */     
/*      */     public DESCKIND(Pointer pointer) {
/*  522 */       super(pointer);
/*  523 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  541 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class SAFEARRAY
/*      */     extends Structure
/*      */     implements Closeable
/*      */   {
/*      */     public static class ByReference
/*      */       extends SAFEARRAY
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  586 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cDims", "fFeatures", "cbElements", "cLocks", "pvData", "rgsabound" });
/*      */     
/*      */     public WinDef.USHORT cDims;
/*      */     
/*      */     public WinDef.USHORT fFeatures;
/*      */     
/*      */     public WinDef.ULONG cbElements;
/*      */     
/*      */     public WinDef.ULONG cLocks;
/*      */     public WinDef.PVOID pvData;
/*  596 */     public OaIdl.SAFEARRAYBOUND[] rgsabound = new OaIdl.SAFEARRAYBOUND[] { new OaIdl.SAFEARRAYBOUND() };
/*      */ 
/*      */     
/*      */     public SAFEARRAY() {}
/*      */ 
/*      */     
/*      */     public SAFEARRAY(Pointer pointer) {
/*  603 */       super(pointer);
/*  604 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public void read() {
/*  609 */       super.read();
/*  610 */       if (this.cDims.intValue() > 0) {
/*  611 */         this.rgsabound = (OaIdl.SAFEARRAYBOUND[])this.rgsabound[0].toArray(this.cDims.intValue());
/*      */       } else {
/*  613 */         this.rgsabound = new OaIdl.SAFEARRAYBOUND[] { new OaIdl.SAFEARRAYBOUND() };
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  619 */       return FIELDS;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static SAFEARRAY createSafeArray(int... size) {
/*  635 */       return createSafeArray(new WTypes.VARTYPE(12), size);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static SAFEARRAY createSafeArray(WTypes.VARTYPE vartype, int... size) {
/*  651 */       OaIdl.SAFEARRAYBOUND[] rgsabound = (OaIdl.SAFEARRAYBOUND[])(new OaIdl.SAFEARRAYBOUND()).toArray(size.length);
/*  652 */       for (int i = 0; i < size.length; i++) {
/*  653 */         (rgsabound[i]).lLbound = new WinDef.LONG(0L);
/*  654 */         (rgsabound[i]).cElements = new WinDef.ULONG(size[size.length - i - 1]);
/*      */       } 
/*  656 */       ByReference data = OleAuto.INSTANCE.SafeArrayCreate(vartype, new WinDef.UINT(size.length), rgsabound);
/*  657 */       return data;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void putElement(Object arg, int... indices) {
/*      */       WinNT.HRESULT hr;
/*      */       Memory mem;
/*  671 */       WinDef.LONG[] paramIndices = new WinDef.LONG[indices.length];
/*  672 */       for (int i = 0; i < indices.length; i++) {
/*  673 */         paramIndices[i] = new WinDef.LONG(indices[indices.length - i - 1]);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  678 */       switch (getVarType().intValue()) {
/*      */         case 11:
/*  680 */           mem = new Memory(2L);
/*  681 */           if (arg instanceof Boolean) {
/*  682 */             mem.setShort(0L, (short)(((Boolean)arg).booleanValue() ? 65535 : 0));
/*      */           } else {
/*  684 */             mem.setShort(0L, (short)((((Number)arg).intValue() > 0) ? 65535 : 0));
/*      */           } 
/*  686 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  687 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 16:
/*      */         case 17:
/*  691 */           mem = new Memory(1L);
/*  692 */           mem.setByte(0L, ((Number)arg).byteValue());
/*  693 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  694 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 2:
/*      */         case 18:
/*  698 */           mem = new Memory(2L);
/*  699 */           mem.setShort(0L, ((Number)arg).shortValue());
/*  700 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  701 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 3:
/*      */         case 19:
/*      */         case 22:
/*      */         case 23:
/*  707 */           mem = new Memory(4L);
/*  708 */           mem.setInt(0L, ((Number)arg).intValue());
/*  709 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  710 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 10:
/*  713 */           mem = new Memory(4L);
/*  714 */           mem.setInt(0L, ((Number)arg).intValue());
/*  715 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  716 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 4:
/*  719 */           mem = new Memory(4L);
/*  720 */           mem.setFloat(0L, ((Number)arg).floatValue());
/*  721 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  722 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 5:
/*  725 */           mem = new Memory(8L);
/*  726 */           mem.setDouble(0L, ((Number)arg).doubleValue());
/*  727 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  728 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 7:
/*  731 */           mem = new Memory(8L);
/*  732 */           mem.setDouble(0L, ((OaIdl.DATE)arg).date);
/*  733 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, (Pointer)mem);
/*  734 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 8:
/*  737 */           if (arg instanceof String) {
/*  738 */             WTypes.BSTR bstr = OleAuto.INSTANCE.SysAllocString((String)arg);
/*  739 */             hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, bstr.getPointer());
/*  740 */             OleAuto.INSTANCE.SysFreeString(bstr);
/*  741 */             COMUtils.checkRC(hr);
/*      */           } else {
/*  743 */             hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((WTypes.BSTR)arg).getPointer());
/*  744 */             COMUtils.checkRC(hr);
/*      */           } 
/*      */           return;
/*      */         case 12:
/*  748 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((Variant.VARIANT)arg).getPointer());
/*  749 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 13:
/*  752 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((Unknown)arg).getPointer());
/*  753 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 9:
/*  756 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((Dispatch)arg).getPointer());
/*  757 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 6:
/*  760 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((OaIdl.CURRENCY)arg).getPointer());
/*  761 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */         case 14:
/*  764 */           hr = OleAuto.INSTANCE.SafeArrayPutElement(this, paramIndices, ((OaIdl.DECIMAL)arg).getPointer());
/*  765 */           COMUtils.checkRC(hr);
/*      */           return;
/*      */       } 
/*      */       
/*  769 */       throw new IllegalStateException("Can't parse array content - type not supported: " + getVarType().intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getElement(int... indices) {
/*      */       Object result;
/*      */       WinNT.HRESULT hr;
/*      */       Memory mem;
/*      */       PointerByReference pbr;
/*      */       WTypes.BSTR bstr;
/*      */       Variant.VARIANT holder;
/*      */       OaIdl.CURRENCY currency;
/*      */       OaIdl.DECIMAL decimal;
/*  783 */       WinDef.LONG[] paramIndices = new WinDef.LONG[indices.length];
/*  784 */       for (int i = 0; i < indices.length; i++) {
/*  785 */         paramIndices[i] = new WinDef.LONG(indices[indices.length - i - 1]);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  792 */       switch (getVarType().intValue()) {
/*      */         case 11:
/*  794 */           mem = new Memory(2L);
/*  795 */           hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem);
/*  796 */           COMUtils.checkRC(hr);
/*  797 */           result = Boolean.valueOf((mem.getShort(0L) != 0));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  889 */           return result;case 16: case 17: mem = new Memory(1L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Byte.valueOf(mem.getByte(0L)); return result;case 2: case 18: mem = new Memory(2L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Short.valueOf(mem.getShort(0L)); return result;case 3: case 19: case 22: case 23: mem = new Memory(4L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Integer.valueOf(mem.getInt(0L)); return result;case 10: mem = new Memory(4L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = new WinDef.SCODE(mem.getInt(0L)); return result;case 4: mem = new Memory(4L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Float.valueOf(mem.getFloat(0L)); return result;case 5: mem = new Memory(8L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = Double.valueOf(mem.getDouble(0L)); return result;case 7: mem = new Memory(8L); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, (Pointer)mem); COMUtils.checkRC(hr); result = new OaIdl.DATE(mem.getDouble(0L)); return result;case 8: pbr = new PointerByReference(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, pbr.getPointer()); COMUtils.checkRC(hr); bstr = new WTypes.BSTR(pbr.getValue()); result = bstr.getValue(); OleAuto.INSTANCE.SysFreeString(bstr); return result;case 12: holder = new Variant.VARIANT(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, holder.getPointer()); COMUtils.checkRC(hr); result = holder; return result;case 13: pbr = new PointerByReference(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, pbr.getPointer()); COMUtils.checkRC(hr); result = new Unknown(pbr.getValue()); return result;case 9: pbr = new PointerByReference(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, pbr.getPointer()); COMUtils.checkRC(hr); result = new Dispatch(pbr.getValue()); return result;case 6: currency = new OaIdl.CURRENCY(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, currency.getPointer()); COMUtils.checkRC(hr); result = currency; return result;case 14: decimal = new OaIdl.DECIMAL(); hr = OleAuto.INSTANCE.SafeArrayGetElement(this, paramIndices, decimal.getPointer()); COMUtils.checkRC(hr); result = decimal; return result;
/*      */       } 
/*      */       throw new IllegalStateException("Can't parse array content - type not supported: " + getVarType().intValue());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer ptrOfIndex(int... indices) {
/*  905 */       WinDef.LONG[] paramIndices = new WinDef.LONG[indices.length];
/*  906 */       for (int i = 0; i < indices.length; i++) {
/*  907 */         paramIndices[i] = new WinDef.LONG(indices[indices.length - i - 1]);
/*      */       }
/*  909 */       PointerByReference pbr = new PointerByReference();
/*  910 */       WinNT.HRESULT hr = OleAuto.INSTANCE.SafeArrayPtrOfIndex(this, paramIndices, pbr);
/*  911 */       COMUtils.checkRC(hr);
/*  912 */       return pbr.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void destroy() {
/*  919 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayDestroy(this);
/*  920 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {
/*  927 */       destroy();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getLBound(int dimension) {
/*  940 */       int targetDimension = getDimensionCount() - dimension;
/*  941 */       WinDef.LONGByReference bound = new WinDef.LONGByReference();
/*  942 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayGetLBound(this, new WinDef.UINT(targetDimension), bound);
/*  943 */       COMUtils.checkRC(res);
/*  944 */       return bound.getValue().intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getUBound(int dimension) {
/*  957 */       int targetDimension = getDimensionCount() - dimension;
/*  958 */       WinDef.LONGByReference bound = new WinDef.LONGByReference();
/*  959 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayGetUBound(this, new WinDef.UINT(targetDimension), bound);
/*  960 */       COMUtils.checkRC(res);
/*  961 */       return bound.getValue().intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getDimensionCount() {
/*  970 */       return OleAuto.INSTANCE.SafeArrayGetDim(this).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer accessData() {
/*  979 */       PointerByReference pbr = new PointerByReference();
/*  980 */       WinNT.HRESULT hr = OleAuto.INSTANCE.SafeArrayAccessData(this, pbr);
/*  981 */       COMUtils.checkRC(hr);
/*  982 */       return pbr.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unaccessData() {
/*  990 */       WinNT.HRESULT hr = OleAuto.INSTANCE.SafeArrayUnaccessData(this);
/*  991 */       COMUtils.checkRC(hr);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void lock() {
/*  999 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayLock(this);
/* 1000 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unlock() {
/* 1007 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayUnlock(this);
/* 1008 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void redim(int cElements, int lLbound) {
/* 1019 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayRedim(this, new OaIdl.SAFEARRAYBOUND(cElements, lLbound));
/* 1020 */       COMUtils.checkRC(res);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public WTypes.VARTYPE getVarType() {
/* 1029 */       WTypes.VARTYPEByReference resultHolder = new WTypes.VARTYPEByReference();
/* 1030 */       WinNT.HRESULT res = OleAuto.INSTANCE.SafeArrayGetVartype(this, resultHolder);
/* 1031 */       COMUtils.checkRC(res);
/* 1032 */       return resultHolder.getValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getElemsize() {
/* 1041 */       return OleAuto.INSTANCE.SafeArrayGetElemsize(this).longValue();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SAFEARRAYBOUND
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends SAFEARRAYBOUND implements Structure.ByReference {}
/*      */     
/* 1050 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cElements", "lLbound" });
/*      */     
/*      */     public WinDef.ULONG cElements;
/*      */     
/*      */     public WinDef.LONG lLbound;
/*      */ 
/*      */     
/*      */     public SAFEARRAYBOUND() {}
/*      */     
/*      */     public SAFEARRAYBOUND(Pointer pointer) {
/* 1060 */       super(pointer);
/* 1061 */       read();
/*      */     }
/*      */     
/*      */     public SAFEARRAYBOUND(int cElements, int lLbound) {
/* 1065 */       this.cElements = new WinDef.ULONG(cElements);
/* 1066 */       this.lLbound = new WinDef.LONG(lLbound);
/* 1067 */       write();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1072 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class CURRENCY
/*      */     extends Union
/*      */   {
/*      */     public _CURRENCY currency;
/*      */     public WinDef.LONGLONG int64;
/*      */     
/*      */     public static class ByReference
/*      */       extends CURRENCY
/*      */       implements Structure.ByReference {}
/*      */     
/*      */     public CURRENCY() {}
/*      */     
/*      */     public CURRENCY(Pointer pointer) {
/* 1090 */       super(pointer);
/* 1091 */       read();
/*      */     }
/*      */     
/*      */     public static class _CURRENCY extends Structure {
/* 1095 */       public static final List<String> FIELDS = createFieldsOrder(new String[] { "Lo", "Hi" });
/*      */       
/*      */       public WinDef.ULONG Lo;
/*      */       
/*      */       public WinDef.LONG Hi;
/*      */ 
/*      */       
/*      */       public _CURRENCY() {}
/*      */       
/*      */       public _CURRENCY(Pointer pointer) {
/* 1105 */         super(pointer);
/* 1106 */         read();
/*      */       }
/*      */ 
/*      */       
/*      */       protected List<String> getFieldOrder() {
/* 1111 */         return FIELDS;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static class DECIMAL extends Structure {
/* 1117 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "wReserved", "decimal1", "Hi32", "decimal2" });
/*      */     public short wReserved;
/*      */     public _DECIMAL1 decimal1;
/*      */     public NativeLong Hi32;
/*      */     public _DECIMAL2 decimal2;
/*      */     
/*      */     public static class ByReference extends DECIMAL implements Structure.ByReference {}
/*      */     
/*      */     public static class _DECIMAL1 extends Union {
/*      */       public WinDef.USHORT signscale;
/*      */       public _DECIMAL1_DECIMAL decimal1_DECIMAL;
/*      */       
/*      */       public _DECIMAL1() {
/* 1130 */         setType("signscale");
/*      */       }
/*      */       
/*      */       public _DECIMAL1(Pointer pointer) {
/* 1134 */         super(pointer);
/* 1135 */         setType("signscale");
/* 1136 */         read();
/*      */       }
/*      */       
/*      */       public static class _DECIMAL1_DECIMAL extends Structure {
/* 1140 */         public static final List<String> FIELDS = createFieldsOrder(new String[] { "scale", "sign" });
/*      */         
/*      */         public WinDef.BYTE scale;
/*      */         
/*      */         public WinDef.BYTE sign;
/*      */         
/*      */         public _DECIMAL1_DECIMAL() {}
/*      */         
/*      */         public _DECIMAL1_DECIMAL(Pointer pointer) {
/* 1149 */           super(pointer);
/*      */         }
/*      */ 
/*      */         
/*      */         protected List<String> getFieldOrder() {
/* 1154 */           return FIELDS;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     public static class _DECIMAL2 extends Union {
/*      */       public WinDef.ULONGLONG Lo64;
/*      */       public _DECIMAL2_DECIMAL decimal2_DECIMAL;
/*      */       
/*      */       public _DECIMAL2() {
/* 1164 */         setType("Lo64");
/*      */       }
/*      */       
/*      */       public _DECIMAL2(Pointer pointer) {
/* 1168 */         super(pointer);
/* 1169 */         setType("Lo64");
/* 1170 */         read();
/*      */       }
/*      */       
/*      */       public static class _DECIMAL2_DECIMAL extends Structure {
/* 1174 */         public static final List<String> FIELDS = createFieldsOrder(new String[] { "Lo32", "Mid32" });
/*      */         
/*      */         public WinDef.BYTE Lo32;
/*      */         
/*      */         public WinDef.BYTE Mid32;
/*      */ 
/*      */         
/*      */         public _DECIMAL2_DECIMAL() {}
/*      */         
/*      */         public _DECIMAL2_DECIMAL(Pointer pointer) {
/* 1184 */           super(pointer);
/*      */         }
/*      */ 
/*      */         
/*      */         protected List<String> getFieldOrder() {
/* 1189 */           return FIELDS;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DECIMAL() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public DECIMAL(Pointer pointer) {
/* 1204 */       super(pointer);
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1209 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class SYSKIND
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends SYSKIND implements Structure.ByReference {}
/*      */     
/* 1218 */     public static final List<String> FIELDS = createFieldsOrder("value"); public int value; public static final int SYS_WIN16 = 0;
/*      */     public static final int SYS_WIN32 = 1;
/*      */     public static final int SYS_MAC = 2;
/*      */     public static final int SYS_WIN64 = 3;
/*      */     
/*      */     public SYSKIND() {}
/*      */     
/*      */     public SYSKIND(int value) {
/* 1226 */       this.value = value;
/*      */     }
/*      */     
/*      */     public SYSKIND(Pointer pointer) {
/* 1230 */       super(pointer);
/* 1231 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1241 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class LIBFLAGS
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends LIBFLAGS implements Structure.ByReference {}
/*      */     
/* 1250 */     public static final List<String> FIELDS = createFieldsOrder("value"); public int value; public static final int LIBFLAG_FRESTRICTED = 1;
/*      */     public static final int LIBFLAG_FCONTROL = 2;
/*      */     public static final int LIBFLAG_FHIDDEN = 4;
/*      */     public static final int LIBFLAG_FHASDISKIMAGE = 8;
/*      */     
/*      */     public LIBFLAGS() {}
/*      */     
/*      */     public LIBFLAGS(int value) {
/* 1258 */       this.value = value;
/*      */     }
/*      */     
/*      */     public LIBFLAGS(Pointer pointer) {
/* 1262 */       super(pointer);
/* 1263 */       read();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1273 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TLIBATTR
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends TLIBATTR
/*      */       implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(Pointer pointer) {
/* 1286 */         super(pointer);
/* 1287 */         read();
/*      */       }
/*      */     }
/*      */     
/* 1291 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "guid", "lcid", "syskind", "wMajorVerNum", "wMinorVerNum", "wLibFlags" });
/*      */     
/*      */     public Guid.GUID guid;
/*      */     
/*      */     public WinDef.LCID lcid;
/*      */     
/*      */     public OaIdl.SYSKIND syskind;
/*      */     
/*      */     public WinDef.WORD wMajorVerNum;
/*      */     public WinDef.WORD wMinorVerNum;
/*      */     public WinDef.WORD wLibFlags;
/*      */     
/*      */     public TLIBATTR() {}
/*      */     
/*      */     public TLIBATTR(Pointer pointer) {
/* 1306 */       super(pointer);
/* 1307 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1312 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class BINDPTR
/*      */     extends Union
/*      */   {
/*      */     public OaIdl.FUNCDESC lpfuncdesc;
/*      */     
/*      */     public OaIdl.VARDESC lpvardesc;
/*      */     
/*      */     public TypeComp lptcomp;
/*      */ 
/*      */     
/*      */     public static class ByReference
/*      */       extends BINDPTR
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */     
/*      */     public BINDPTR() {}
/*      */     
/*      */     public BINDPTR(OaIdl.VARDESC lpvardesc) {
/* 1335 */       this.lpvardesc = lpvardesc;
/* 1336 */       setType(OaIdl.VARDESC.class);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BINDPTR(TypeComp lptcomp) {
/* 1342 */       this.lptcomp = lptcomp;
/* 1343 */       setType(TypeComp.class);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public BINDPTR(OaIdl.FUNCDESC lpfuncdesc) {
/* 1349 */       this.lpfuncdesc = lpfuncdesc;
/* 1350 */       setType(OaIdl.FUNCDESC.class);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class FUNCDESC
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends FUNCDESC implements Structure.ByReference {}
/*      */     
/* 1359 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "memid", "lprgscode", "lprgelemdescParam", "funckind", "invkind", "callconv", "cParams", "cParamsOpt", "oVft", "cScodes", "elemdescFunc", "wFuncFlags" });
/*      */     
/*      */     public OaIdl.MEMBERID memid;
/*      */     
/*      */     public OaIdl.ScodeArg.ByReference lprgscode;
/*      */     
/*      */     public OaIdl.ElemDescArg.ByReference lprgelemdescParam;
/*      */     
/*      */     public OaIdl.FUNCKIND funckind;
/*      */     
/*      */     public OaIdl.INVOKEKIND invkind;
/*      */     
/*      */     public OaIdl.CALLCONV callconv;
/*      */     public WinDef.SHORT cParams;
/*      */     public WinDef.SHORT cParamsOpt;
/*      */     public WinDef.SHORT oVft;
/*      */     public WinDef.SHORT cScodes;
/*      */     public OaIdl.ELEMDESC elemdescFunc;
/*      */     public WinDef.WORD wFuncFlags;
/*      */     
/*      */     public FUNCDESC() {}
/*      */     
/*      */     public FUNCDESC(Pointer pointer) {
/* 1382 */       super(pointer);
/* 1383 */       read();
/*      */       
/* 1385 */       if (this.cParams.shortValue() > 1) {
/* 1386 */         this.lprgelemdescParam
/* 1387 */           .elemDescArg = new OaIdl.ELEMDESC[this.cParams.shortValue()];
/* 1388 */         this.lprgelemdescParam.read();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1394 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class ElemDescArg
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends ElemDescArg implements Structure.ByReference {}
/*      */     
/* 1403 */     public static final List<String> FIELDS = createFieldsOrder("elemDescArg");
/*      */     
/* 1405 */     public OaIdl.ELEMDESC[] elemDescArg = new OaIdl.ELEMDESC[] { new OaIdl.ELEMDESC() };
/*      */ 
/*      */     
/*      */     public ElemDescArg() {}
/*      */ 
/*      */     
/*      */     public ElemDescArg(Pointer pointer) {
/* 1412 */       super(pointer);
/* 1413 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1418 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class ScodeArg
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends ScodeArg implements Structure.ByReference {}
/*      */     
/* 1427 */     public static final List<String> FIELDS = createFieldsOrder("scodeArg");
/*      */     
/* 1429 */     public WinDef.SCODE[] scodeArg = new WinDef.SCODE[] { new WinDef.SCODE() };
/*      */ 
/*      */     
/*      */     public ScodeArg() {}
/*      */ 
/*      */     
/*      */     public ScodeArg(Pointer pointer) {
/* 1436 */       super(pointer);
/* 1437 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1442 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class VARDESC
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends VARDESC implements Structure.ByReference {}
/*      */     
/* 1451 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "memid", "lpstrSchema", "_vardesc", "elemdescVar", "wVarFlags", "varkind" });
/*      */ 
/*      */     
/*      */     public OaIdl.MEMBERID memid;
/*      */ 
/*      */     
/*      */     public WTypes.LPOLESTR lpstrSchema;
/*      */ 
/*      */     
/*      */     public _VARDESC _vardesc;
/*      */ 
/*      */     
/*      */     public OaIdl.ELEMDESC elemdescVar;
/*      */ 
/*      */     
/*      */     public WinDef.WORD wVarFlags;
/*      */     
/*      */     public OaIdl.VARKIND varkind;
/*      */ 
/*      */     
/*      */     public static class _VARDESC
/*      */       extends Union
/*      */     {
/*      */       public NativeLong oInst;
/*      */       
/*      */       public Variant.VARIANT.ByReference lpvarValue;
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends _VARDESC
/*      */         implements Structure.ByReference {}
/*      */ 
/*      */       
/*      */       public _VARDESC() {
/* 1485 */         setType("lpvarValue");
/* 1486 */         read();
/*      */       }
/*      */       
/*      */       public _VARDESC(Pointer pointer) {
/* 1490 */         super(pointer);
/* 1491 */         setType("lpvarValue");
/* 1492 */         read();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public _VARDESC(Variant.VARIANT.ByReference lpvarValue) {
/* 1501 */         this.lpvarValue = lpvarValue;
/* 1502 */         setType("lpvarValue");
/*      */       }
/*      */ 
/*      */       
/*      */       public _VARDESC(NativeLong oInst) {
/* 1507 */         this.oInst = oInst;
/* 1508 */         setType("oInst");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public VARDESC() {}
/*      */ 
/*      */     
/*      */     public VARDESC(Pointer pointer) {
/* 1517 */       super(pointer);
/* 1518 */       this._vardesc.setType("lpvarValue");
/* 1519 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1524 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class ELEMDESC
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends ELEMDESC implements Structure.ByReference {}
/*      */     
/* 1533 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "tdesc", "_elemdesc" });
/*      */ 
/*      */     
/*      */     public OaIdl.TYPEDESC tdesc;
/*      */ 
/*      */     
/*      */     public _ELEMDESC _elemdesc;
/*      */ 
/*      */ 
/*      */     
/*      */     public static class _ELEMDESC
/*      */       extends Union
/*      */     {
/*      */       public OaIdl.IDLDESC idldesc;
/*      */ 
/*      */       
/*      */       public OaIdl.PARAMDESC paramdesc;
/*      */ 
/*      */ 
/*      */       
/*      */       public static class ByReference
/*      */         extends _ELEMDESC
/*      */         implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */       
/*      */       public _ELEMDESC() {}
/*      */ 
/*      */ 
/*      */       
/*      */       public _ELEMDESC(Pointer pointer) {
/* 1564 */         super(pointer);
/* 1565 */         setType("paramdesc");
/* 1566 */         read();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public _ELEMDESC(OaIdl.PARAMDESC paramdesc) {
/* 1575 */         this.paramdesc = paramdesc;
/* 1576 */         setType("paramdesc");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public _ELEMDESC(OaIdl.IDLDESC idldesc) {
/* 1585 */         this.idldesc = idldesc;
/* 1586 */         setType("idldesc");
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public ELEMDESC() {}
/*      */ 
/*      */     
/*      */     public ELEMDESC(Pointer pointer) {
/* 1595 */       super(pointer);
/* 1596 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1601 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class FUNCKIND
/*      */     extends Structure
/*      */   {
/*      */     public static final int FUNC_VIRTUAL = 0;
/*      */     
/*      */     public static final int FUNC_PUREVIRTUAL = 1;
/*      */     
/*      */     public static final int FUNC_NONVIRTUAL = 2;
/*      */     public static final int FUNC_STATIC = 3;
/*      */     public static final int FUNC_DISPATCH = 4;
/*      */     
/*      */     public static class ByReference
/*      */       extends FUNCKIND
/*      */       implements Structure.ByReference {}
/*      */     
/* 1621 */     public static final List<String> FIELDS = createFieldsOrder("value");
/*      */     
/*      */     public int value;
/*      */ 
/*      */     
/*      */     public FUNCKIND() {}
/*      */ 
/*      */     
/*      */     public FUNCKIND(int value) {
/* 1630 */       this.value = value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1636 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class INVOKEKIND
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends INVOKEKIND implements Structure.ByReference {}
/*      */     
/* 1645 */     public static final List<String> FIELDS = createFieldsOrder("value");
/*      */ 
/*      */     
/* 1648 */     public static final INVOKEKIND INVOKE_FUNC = new INVOKEKIND(1);
/*      */     
/* 1650 */     public static final INVOKEKIND INVOKE_PROPERTYGET = new INVOKEKIND(2);
/*      */     
/* 1652 */     public static final INVOKEKIND INVOKE_PROPERTYPUT = new INVOKEKIND(4);
/*      */     
/* 1654 */     public static final INVOKEKIND INVOKE_PROPERTYPUTREF = new INVOKEKIND(8);
/*      */     
/*      */     public int value;
/*      */ 
/*      */     
/*      */     public INVOKEKIND() {}
/*      */ 
/*      */     
/*      */     public INVOKEKIND(int value) {
/* 1663 */       this.value = value;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1669 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class CALLCONV
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends CALLCONV implements Structure.ByReference {}
/*      */     
/* 1678 */     public static final List<String> FIELDS = createFieldsOrder("value");
/*      */     
/*      */     public static final int CC_FASTCALL = 0;
/*      */     
/*      */     public static final int CC_CDECL = 1;
/*      */     
/*      */     public static final int CC_MSCPASCAL = 2;
/*      */     
/*      */     public static final int CC_PASCAL = 2;
/*      */     
/*      */     public static final int CC_MACPASCAL = 3;
/*      */     
/*      */     public static final int CC_STDCALL = 4;
/*      */     
/*      */     public static final int CC_FPFASTCALL = 5;
/*      */     
/*      */     public static final int CC_SYSCALL = 6;
/*      */     
/*      */     public static final int CC_MPWCDECL = 7;
/*      */     
/*      */     public static final int CC_MPWPASCAL = 8;
/*      */     
/*      */     public static final int CC_MAX = 9;
/*      */     
/*      */     public int value;
/*      */ 
/*      */     
/*      */     public CALLCONV() {}
/*      */ 
/*      */     
/*      */     public CALLCONV(int value) {
/* 1709 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1714 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class VARKIND
/*      */     extends Structure
/*      */   {
/*      */     public static final int VAR_PERINSTANCE = 0;
/*      */     
/*      */     public static final int VAR_STATIC = 1;
/*      */     public static final int VAR_CONST = 2;
/*      */     public static final int VAR_DISPATCH = 3;
/*      */     
/*      */     public static class ByReference
/*      */       extends VARKIND
/*      */       implements Structure.ByReference {}
/*      */     
/* 1732 */     public static final List<String> FIELDS = createFieldsOrder("value");
/*      */     
/*      */     public int value;
/*      */ 
/*      */     
/*      */     public VARKIND() {}
/*      */ 
/*      */     
/*      */     public VARKIND(int value) {
/* 1741 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1746 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class TYPEDESC
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends TYPEDESC
/*      */       implements Structure.ByReference {}
/*      */ 
/*      */ 
/*      */     
/*      */     public static class _TYPEDESC
/*      */       extends Union
/*      */     {
/*      */       public OaIdl.TYPEDESC.ByReference lptdesc;
/*      */ 
/*      */       
/*      */       public OaIdl.ARRAYDESC.ByReference lpadesc;
/*      */ 
/*      */       
/*      */       public OaIdl.HREFTYPE hreftype;
/*      */ 
/*      */       
/*      */       public _TYPEDESC() {
/* 1774 */         setType("hreftype");
/* 1775 */         read();
/*      */       }
/*      */       
/*      */       public _TYPEDESC(Pointer pointer) {
/* 1779 */         super(pointer);
/* 1780 */         setType("hreftype");
/* 1781 */         read();
/*      */       }
/*      */       
/*      */       public OaIdl.TYPEDESC.ByReference getLptdesc() {
/* 1785 */         setType("lptdesc");
/* 1786 */         read();
/* 1787 */         return this.lptdesc;
/*      */       }
/*      */       
/*      */       public OaIdl.ARRAYDESC.ByReference getLpadesc() {
/* 1791 */         setType("lpadesc");
/* 1792 */         read();
/* 1793 */         return this.lpadesc;
/*      */       }
/*      */       
/*      */       public OaIdl.HREFTYPE getHreftype() {
/* 1797 */         setType("hreftype");
/* 1798 */         read();
/* 1799 */         return this.hreftype;
/*      */       }
/*      */     }
/*      */     
/* 1803 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "_typedesc", "vt" });
/*      */     public _TYPEDESC _typedesc;
/*      */     public WTypes.VARTYPE vt;
/*      */     
/*      */     public TYPEDESC() {
/* 1808 */       read();
/*      */     }
/*      */     
/*      */     public TYPEDESC(Pointer pointer) {
/* 1812 */       super(pointer);
/* 1813 */       read();
/*      */     }
/*      */     
/*      */     public TYPEDESC(_TYPEDESC _typedesc, WTypes.VARTYPE vt) {
/* 1817 */       this._typedesc = _typedesc;
/* 1818 */       this.vt = vt;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1823 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class IDLDESC
/*      */     extends Structure
/*      */   {
/*      */     public static class ByReference
/*      */       extends IDLDESC
/*      */       implements Structure.ByReference {
/*      */       public ByReference() {}
/*      */       
/*      */       public ByReference(OaIdl.IDLDESC idldesc) {
/* 1836 */         super(idldesc.dwReserved, idldesc.wIDLFlags);
/*      */       }
/*      */     }
/*      */     
/* 1840 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwReserved", "wIDLFlags" });
/*      */     
/*      */     public BaseTSD.ULONG_PTR dwReserved;
/*      */     
/*      */     public WinDef.USHORT wIDLFlags;
/*      */ 
/*      */     
/*      */     public IDLDESC() {}
/*      */ 
/*      */     
/*      */     public IDLDESC(Pointer pointer) {
/* 1851 */       super(pointer);
/* 1852 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     public IDLDESC(BaseTSD.ULONG_PTR dwReserved, WinDef.USHORT wIDLFlags) {
/* 1857 */       this.dwReserved = dwReserved;
/* 1858 */       this.wIDLFlags = wIDLFlags;
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1863 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class ARRAYDESC extends Structure {
/* 1868 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "tdescElem", "cDims", "rgbounds" });
/*      */ 
/*      */     
/*      */     public OaIdl.TYPEDESC tdescElem;
/*      */ 
/*      */     
/*      */     public short cDims;
/*      */     
/* 1876 */     public OaIdl.SAFEARRAYBOUND[] rgbounds = new OaIdl.SAFEARRAYBOUND[] { new OaIdl.SAFEARRAYBOUND() };
/*      */ 
/*      */     
/*      */     public ARRAYDESC() {}
/*      */ 
/*      */     
/*      */     public ARRAYDESC(Pointer pointer) {
/* 1883 */       super(pointer);
/* 1884 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1889 */       return FIELDS;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ARRAYDESC(OaIdl.TYPEDESC tdescElem, short cDims, OaIdl.SAFEARRAYBOUND[] rgbounds) {
/* 1901 */       this.tdescElem = tdescElem;
/* 1902 */       this.cDims = cDims;
/* 1903 */       if (rgbounds.length != this.rgbounds.length)
/* 1904 */         throw new IllegalArgumentException("Wrong array size !"); 
/* 1905 */       this.rgbounds = rgbounds;
/*      */     }
/*      */     
/*      */     public static class ByReference
/*      */       extends ARRAYDESC
/*      */       implements Structure.ByReference {}
/*      */   }
/*      */   
/*      */   public static class PARAMDESC
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends PARAMDESC
/*      */       implements Structure.ByReference {}
/*      */     
/* 1919 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "pparamdescex", "wParamFlags" });
/*      */ 
/*      */     
/*      */     public Pointer pparamdescex;
/*      */     
/*      */     public WinDef.USHORT wParamFlags;
/*      */ 
/*      */     
/*      */     public PARAMDESC() {}
/*      */ 
/*      */     
/*      */     public PARAMDESC(Pointer pointer) {
/* 1931 */       super(pointer);
/* 1932 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1937 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class PARAMDESCEX
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends PARAMDESCEX implements Structure.ByReference {}
/*      */     
/* 1946 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cBytes", "varDefaultValue" });
/*      */     
/*      */     public WinDef.ULONG cBytes;
/*      */     
/*      */     public Variant.VariantArg varDefaultValue;
/*      */ 
/*      */     
/*      */     public PARAMDESCEX() {}
/*      */     
/*      */     public PARAMDESCEX(Pointer pointer) {
/* 1956 */       super(pointer);
/* 1957 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1962 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*      */   public static class HREFTYPE
/*      */     extends WinDef.DWORD
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */     
/*      */     public HREFTYPE() {}
/*      */     
/*      */     public HREFTYPE(long value) {
/* 1974 */       super(value);
/*      */     }
/*      */   }
/*      */   
/*      */   public static class HREFTYPEByReference extends WinDef.DWORDByReference {
/*      */     public HREFTYPEByReference() {
/* 1980 */       this(new OaIdl.HREFTYPE(0L));
/*      */     }
/*      */     
/*      */     public HREFTYPEByReference(WinDef.DWORD value) {
/* 1984 */       super(value);
/*      */     }
/*      */     
/*      */     public void setValue(OaIdl.HREFTYPE value) {
/* 1988 */       getPointer().setInt(0L, value.intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public OaIdl.HREFTYPE getValue() {
/* 1993 */       return new OaIdl.HREFTYPE(getPointer().getInt(0L));
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TYPEATTR
/*      */     extends Structure {
/*      */     public static class ByReference
/*      */       extends TYPEATTR implements Structure.ByReference {}
/*      */     
/* 2002 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "guid", "lcid", "dwReserved", "memidConstructor", "memidDestructor", "lpstrSchema", "cbSizeInstance", "typekind", "cFuncs", "cVars", "cImplTypes", "cbSizeVft", "cbAlignment", "wTypeFlags", "wMajorVerNum", "wMinorVerNum", "tdescAlias", "idldescType" });
/*      */     
/*      */     public Guid.GUID guid;
/*      */     
/*      */     public WinDef.LCID lcid;
/*      */     public WinDef.DWORD dwReserved;
/*      */     public OaIdl.MEMBERID memidConstructor;
/*      */     public OaIdl.MEMBERID memidDestructor;
/*      */     public WTypes.LPOLESTR lpstrSchema;
/*      */     public WinDef.ULONG cbSizeInstance;
/*      */     public OaIdl.TYPEKIND typekind;
/*      */     public WinDef.WORD cFuncs;
/*      */     public WinDef.WORD cVars;
/*      */     public WinDef.WORD cImplTypes;
/*      */     public WinDef.WORD cbSizeVft;
/*      */     public WinDef.WORD cbAlignment;
/*      */     public WinDef.WORD wTypeFlags;
/*      */     public WinDef.WORD wMajorVerNum;
/*      */     public WinDef.WORD wMinorVerNum;
/*      */     public OaIdl.TYPEDESC tdescAlias;
/*      */     public OaIdl.IDLDESC idldescType;
/*      */     public static final int TYPEFLAGS_FAPPOBJECT = 1;
/*      */     public static final int TYPEFLAGS_FCANCREATE = 2;
/*      */     public static final int TYPEFLAGS_FLICENSED = 4;
/*      */     public static final int TYPEFLAGS_FPREDECLID = 8;
/*      */     public static final int TYPEFLAGS_FHIDDEN = 16;
/*      */     public static final int TYPEFLAGS_FCONTROL = 32;
/*      */     public static final int TYPEFLAGS_FDUAL = 64;
/*      */     public static final int TYPEFLAGS_FNONEXTENSIBLE = 128;
/*      */     public static final int TYPEFLAGS_FOLEAUTOMATION = 256;
/*      */     public static final int TYPEFLAGS_FRESTRICTED = 512;
/*      */     public static final int TYPEFLAGS_FAGGREGATABLE = 1024;
/*      */     public static final int TYPEFLAGS_FREPLACEABLE = 2048;
/*      */     public static final int TYPEFLAGS_FDISPATCHABLE = 4096;
/*      */     public static final int TYPEFLAGS_FREVERSEBIND = 8192;
/*      */     public static final int TYPEFLAGS_FPROXY = 16384;
/*      */     
/*      */     public TYPEATTR() {}
/*      */     
/*      */     public TYPEATTR(Pointer pointer) {
/* 2042 */       super(pointer);
/* 2043 */       read();
/*      */     }
/*      */ 
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 2048 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/OaIdl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */