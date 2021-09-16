/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.ptr.DoubleByReference;
/*     */ import com.sun.jna.ptr.PointerByReference;
/*     */ import com.sun.jna.win32.StdCallLibrary;
/*     */ import com.sun.jna.win32.W32APIOptions;
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
/*     */ public interface OleAuto
/*     */   extends StdCallLibrary
/*     */ {
/*  60 */   public static final OleAuto INSTANCE = (OleAuto)Native.loadLibrary("OleAut32", OleAuto.class, W32APIOptions.DEFAULT_OPTIONS);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DISPATCH_METHOD = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DISPATCH_PROPERTYGET = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DISPATCH_PROPERTYPUT = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DISPATCH_PROPERTYPUTREF = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_AUTO = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_STATIC = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_EMBEDDED = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_FIXEDSIZE = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_RECORD = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_HAVEIID = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_HAVEVARTYPE = 128;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_BSTR = 256;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_UNKNOWN = 512;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_DISPATCH = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_VARIANT = 2048;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int FADF_RESERVED = 61448;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_NOVALUEPROP = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_ALPHABOOL = 2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_NOUSEROVERRIDE = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_CALENDAR_HIJRI = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_LOCALBOOL = 16;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_CALENDAR_THAI = 32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_CALENDAR_GREGORIAN = 64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final short VARIANT_USE_NLS = 128;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WTypes.BSTR SysAllocString(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void SysFreeString(WTypes.BSTR paramBSTR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int SysStringByteLen(WTypes.BSTR paramBSTR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int SysStringLen(WTypes.BSTR paramBSTR);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void VariantInit(Variant.VARIANT.ByReference paramByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void VariantInit(Variant.VARIANT paramVARIANT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT VariantCopy(Pointer paramPointer, Variant.VARIANT paramVARIANT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT VariantClear(Variant.VARIANT paramVARIANT);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT VariantChangeType(Variant.VARIANT paramVARIANT1, Variant.VARIANT paramVARIANT2, short paramShort, WTypes.VARTYPE paramVARTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT VariantChangeType(Variant.VARIANT.ByReference paramByReference1, Variant.VARIANT.ByReference paramByReference2, short paramShort, WTypes.VARTYPE paramVARTYPE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   OaIdl.SAFEARRAY.ByReference SafeArrayCreate(WTypes.VARTYPE paramVARTYPE, WinDef.UINT paramUINT, OaIdl.SAFEARRAYBOUND[] paramArrayOfSAFEARRAYBOUND);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayPutElement(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.LONG[] paramArrayOfLONG, Pointer paramPointer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayGetUBound(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.UINT paramUINT, WinDef.LONGByReference paramLONGByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayGetLBound(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.UINT paramUINT, WinDef.LONGByReference paramLONGByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayGetElement(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.LONG[] paramArrayOfLONG, Pointer paramPointer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayPtrOfIndex(OaIdl.SAFEARRAY paramSAFEARRAY, WinDef.LONG[] paramArrayOfLONG, PointerByReference paramPointerByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayLock(OaIdl.SAFEARRAY paramSAFEARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayUnlock(OaIdl.SAFEARRAY paramSAFEARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayDestroy(OaIdl.SAFEARRAY paramSAFEARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayRedim(OaIdl.SAFEARRAY paramSAFEARRAY, OaIdl.SAFEARRAYBOUND paramSAFEARRAYBOUND);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayGetVartype(OaIdl.SAFEARRAY paramSAFEARRAY, WTypes.VARTYPEByReference paramVARTYPEByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinDef.UINT SafeArrayGetDim(OaIdl.SAFEARRAY paramSAFEARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayAccessData(OaIdl.SAFEARRAY paramSAFEARRAY, PointerByReference paramPointerByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT SafeArrayUnaccessData(OaIdl.SAFEARRAY paramSAFEARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinDef.UINT SafeArrayGetElemsize(OaIdl.SAFEARRAY paramSAFEARRAY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT GetActiveObject(Guid.GUID paramGUID, WinDef.PVOID paramPVOID, PointerByReference paramPointerByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT LoadRegTypeLib(Guid.GUID paramGUID, int paramInt1, int paramInt2, WinDef.LCID paramLCID, PointerByReference paramPointerByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WinNT.HRESULT LoadTypeLib(String paramString, PointerByReference paramPointerByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   WinNT.HRESULT LoadTypeLib(WString paramWString, PointerByReference paramPointerByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int SystemTimeToVariantTime(WinBase.SYSTEMTIME paramSYSTEMTIME, DoubleByReference paramDoubleByReference);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DISPPARAMS
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends DISPPARAMS
/*     */       implements Structure.ByReference {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 731 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "rgvarg", "rgdispidNamedArgs", "cArgs", "cNamedArgs" });
/*     */ 
/*     */     
/*     */     public Variant.VariantArg.ByReference rgvarg;
/*     */ 
/*     */     
/* 737 */     public Pointer rgdispidNamedArgs = Pointer.NULL;
/*     */ 
/*     */     
/* 740 */     public WinDef.UINT cArgs = new WinDef.UINT(0L);
/*     */ 
/*     */     
/* 743 */     public WinDef.UINT cNamedArgs = new WinDef.UINT(0L);
/*     */     
/*     */     public OaIdl.DISPID[] getRgdispidNamedArgs() {
/* 746 */       OaIdl.DISPID[] namedArgs = null;
/* 747 */       int count = this.cNamedArgs.intValue();
/* 748 */       if (this.rgdispidNamedArgs != null && count > 0) {
/* 749 */         int[] rawData = this.rgdispidNamedArgs.getIntArray(0L, count);
/* 750 */         namedArgs = new OaIdl.DISPID[count];
/* 751 */         for (int i = 0; i < count; i++) {
/* 752 */           namedArgs[i] = new OaIdl.DISPID(rawData[i]);
/*     */         }
/*     */       } else {
/* 755 */         namedArgs = new OaIdl.DISPID[0];
/*     */       } 
/* 757 */       return namedArgs;
/*     */     }
/*     */     
/*     */     public void setRgdispidNamedArgs(OaIdl.DISPID[] namedArgs) {
/* 761 */       if (namedArgs == null) {
/* 762 */         namedArgs = new OaIdl.DISPID[0];
/*     */       }
/* 764 */       this.cNamedArgs = new WinDef.UINT(namedArgs.length);
/* 765 */       this.rgdispidNamedArgs = (Pointer)new Memory((OaIdl.DISPID.SIZE * namedArgs.length));
/* 766 */       int[] rawData = new int[namedArgs.length];
/* 767 */       for (int i = 0; i < rawData.length; i++) {
/* 768 */         rawData[i] = namedArgs[i].intValue();
/*     */       }
/* 770 */       this.rgdispidNamedArgs.write(0L, rawData, 0, namedArgs.length);
/*     */     }
/*     */     
/*     */     public Variant.VARIANT[] getArgs() {
/* 774 */       if (this.rgvarg != null) {
/* 775 */         this.rgvarg.setArraySize(this.cArgs.intValue());
/* 776 */         return this.rgvarg.variantArg;
/*     */       } 
/* 778 */       return new Variant.VARIANT[0];
/*     */     }
/*     */ 
/*     */     
/*     */     public void setArgs(Variant.VARIANT[] arguments) {
/* 783 */       if (arguments == null) {
/* 784 */         arguments = new Variant.VARIANT[0];
/*     */       }
/*     */       
/* 787 */       this.rgvarg = new Variant.VariantArg.ByReference(arguments);
/* 788 */       this.cArgs = new WinDef.UINT(arguments.length);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DISPPARAMS() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DISPPARAMS(Pointer memory) {
/* 805 */       super(memory);
/* 806 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 811 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/OleAuto.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */