/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.WString;
/*     */ import com.sun.jna.platform.win32.Guid;
/*     */ import com.sun.jna.platform.win32.Kernel32;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.Ole32;
/*     */ import com.sun.jna.platform.win32.OleAuto;
/*     */ import com.sun.jna.platform.win32.WTypes;
/*     */ import com.sun.jna.platform.win32.WinDef;
/*     */ import com.sun.jna.platform.win32.WinNT;
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
/*     */ public class TypeLibUtil
/*     */ {
/*  57 */   public static final OleAuto OLEAUTO = OleAuto.INSTANCE;
/*     */ 
/*     */   
/*     */   private ITypeLib typelib;
/*     */ 
/*     */   
/*  63 */   private WinDef.LCID lcid = Kernel32.INSTANCE.GetUserDefaultLCID();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String docString;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int helpContext;
/*     */ 
/*     */ 
/*     */   
/*     */   private String helpFile;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeLibUtil(String clsidStr, int wVerMajor, int wVerMinor) {
/*  88 */     Guid.CLSID.ByReference clsid = new Guid.CLSID.ByReference();
/*     */     
/*  90 */     WinNT.HRESULT hr = Ole32.INSTANCE.CLSIDFromString(new WString(clsidStr), clsid);
/*     */     
/*  92 */     COMUtils.checkRC(hr);
/*     */ 
/*     */     
/*  95 */     PointerByReference pTypeLib = new PointerByReference();
/*  96 */     hr = OleAuto.INSTANCE.LoadRegTypeLib((Guid.GUID)clsid, wVerMajor, wVerMinor, this.lcid, pTypeLib);
/*     */     
/*  98 */     COMUtils.checkRC(hr);
/*     */ 
/*     */     
/* 101 */     this.typelib = new TypeLib(pTypeLib.getValue());
/*     */     
/* 103 */     initTypeLibInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public TypeLibUtil(String file) {
/* 108 */     PointerByReference pTypeLib = new PointerByReference();
/* 109 */     WinNT.HRESULT hr = OleAuto.INSTANCE.LoadTypeLib(new WString(file), pTypeLib);
/* 110 */     COMUtils.checkRC(hr);
/*     */ 
/*     */     
/* 113 */     this.typelib = new TypeLib(pTypeLib.getValue());
/*     */     
/* 115 */     initTypeLibInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initTypeLibInfo() {
/* 122 */     TypeLibDoc documentation = getDocumentation(-1);
/* 123 */     this.name = documentation.getName();
/* 124 */     this.docString = documentation.getDocString();
/* 125 */     this.helpContext = documentation.getHelpContext();
/* 126 */     this.helpFile = documentation.getHelpFile();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTypeInfoCount() {
/* 135 */     return this.typelib.GetTypeInfoCount().intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OaIdl.TYPEKIND getTypeInfoType(int index) {
/* 146 */     OaIdl.TYPEKIND.ByReference typekind = new OaIdl.TYPEKIND.ByReference();
/* 147 */     WinNT.HRESULT hr = this.typelib.GetTypeInfoType(new WinDef.UINT(index), typekind);
/* 148 */     COMUtils.checkRC(hr);
/* 149 */     return (OaIdl.TYPEKIND)typekind;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITypeInfo getTypeInfo(int index) {
/* 160 */     PointerByReference ppTInfo = new PointerByReference();
/* 161 */     WinNT.HRESULT hr = this.typelib.GetTypeInfo(new WinDef.UINT(index), ppTInfo);
/* 162 */     COMUtils.checkRC(hr);
/* 163 */     return new TypeInfo(ppTInfo.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeInfoUtil getTypeInfoUtil(int index) {
/* 174 */     return new TypeInfoUtil(getTypeInfo(index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OaIdl.TLIBATTR getLibAttr() {
/* 183 */     PointerByReference ppTLibAttr = new PointerByReference();
/* 184 */     WinNT.HRESULT hr = this.typelib.GetLibAttr(ppTLibAttr);
/* 185 */     COMUtils.checkRC(hr);
/*     */     
/* 187 */     return new OaIdl.TLIBATTR(ppTLibAttr.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeComp GetTypeComp() {
/* 196 */     PointerByReference ppTComp = new PointerByReference();
/* 197 */     WinNT.HRESULT hr = this.typelib.GetTypeComp(ppTComp);
/* 198 */     COMUtils.checkRC(hr);
/*     */     
/* 200 */     return new TypeComp(ppTComp.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeLibDoc getDocumentation(int index) {
/* 211 */     WTypes.BSTRByReference pBstrName = new WTypes.BSTRByReference();
/* 212 */     WTypes.BSTRByReference pBstrDocString = new WTypes.BSTRByReference();
/* 213 */     WinDef.DWORDByReference pdwHelpContext = new WinDef.DWORDByReference();
/* 214 */     WTypes.BSTRByReference pBstrHelpFile = new WTypes.BSTRByReference();
/*     */     
/* 216 */     WinNT.HRESULT hr = this.typelib.GetDocumentation(index, pBstrName, pBstrDocString, pdwHelpContext, pBstrHelpFile);
/*     */     
/* 218 */     COMUtils.checkRC(hr);
/*     */ 
/*     */ 
/*     */     
/* 222 */     TypeLibDoc typeLibDoc = new TypeLibDoc(pBstrName.getString(), pBstrDocString.getString(), pdwHelpContext.getValue().intValue(), pBstrHelpFile.getString());
/*     */     
/* 224 */     OLEAUTO.SysFreeString(pBstrName.getValue());
/* 225 */     OLEAUTO.SysFreeString(pBstrDocString.getValue());
/* 226 */     OLEAUTO.SysFreeString(pBstrHelpFile.getValue());
/*     */     
/* 228 */     return typeLibDoc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TypeLibDoc
/*     */   {
/*     */     private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String docString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int helpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String helpFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TypeLibDoc(String name, String docString, int helpContext, String helpFile) {
/* 264 */       this.name = name;
/* 265 */       this.docString = docString;
/* 266 */       this.helpContext = helpContext;
/* 267 */       this.helpFile = helpFile;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/* 276 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDocString() {
/* 285 */       return this.docString;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getHelpContext() {
/* 294 */       return this.helpContext;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getHelpFile() {
/* 303 */       return this.helpFile;
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
/*     */   
/*     */   public IsName IsName(String nameBuf, int hashVal) {
/* 318 */     WTypes.LPOLESTR szNameBuf = new WTypes.LPOLESTR(nameBuf);
/* 319 */     WinDef.ULONG lHashVal = new WinDef.ULONG(hashVal);
/* 320 */     WinDef.BOOLByReference pfName = new WinDef.BOOLByReference();
/*     */     
/* 322 */     WinNT.HRESULT hr = this.typelib.IsName(szNameBuf, lHashVal, pfName);
/* 323 */     COMUtils.checkRC(hr);
/*     */     
/* 325 */     return new IsName(szNameBuf.getValue(), pfName.getValue()
/* 326 */         .booleanValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class IsName
/*     */   {
/*     */     private String nameBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public IsName(String nameBuf, boolean name) {
/* 351 */       this.nameBuf = nameBuf;
/* 352 */       this.name = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNameBuf() {
/* 361 */       return this.nameBuf;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isName() {
/* 370 */       return this.name;
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
/*     */ 
/*     */   
/*     */   public FindName FindName(String name, int hashVal, short maxResult) {
/* 386 */     Pointer p = Ole32.INSTANCE.CoTaskMemAlloc((name.length() + 1L) * Native.WCHAR_SIZE);
/* 387 */     WTypes.LPOLESTR olestr = new WTypes.LPOLESTR(p);
/* 388 */     olestr.setValue(name);
/*     */     
/* 390 */     WinDef.ULONG lHashVal = new WinDef.ULONG(hashVal);
/* 391 */     WinDef.USHORTByReference pcFound = new WinDef.USHORTByReference(maxResult);
/*     */     
/* 393 */     Pointer[] ppTInfo = new Pointer[maxResult];
/* 394 */     OaIdl.MEMBERID[] rgMemId = new OaIdl.MEMBERID[maxResult];
/* 395 */     WinNT.HRESULT hr = this.typelib.FindName(olestr, lHashVal, ppTInfo, rgMemId, pcFound);
/*     */     
/* 397 */     COMUtils.checkRC(hr);
/*     */ 
/*     */     
/* 400 */     FindName findName = new FindName(olestr.getValue(), ppTInfo, rgMemId, pcFound.getValue().shortValue());
/*     */     
/* 402 */     Ole32.INSTANCE.CoTaskMemFree(p);
/*     */     
/* 404 */     return findName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FindName
/*     */   {
/*     */     private String nameBuf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Pointer[] pTInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private OaIdl.MEMBERID[] rgMemId;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private short pcFound;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     FindName(String nameBuf, Pointer[] pTInfo, OaIdl.MEMBERID[] rgMemId, short pcFound) {
/* 438 */       this.nameBuf = nameBuf;
/* 439 */       this.pTInfo = new Pointer[pcFound];
/* 440 */       this.rgMemId = new OaIdl.MEMBERID[pcFound];
/* 441 */       this.pcFound = pcFound;
/* 442 */       System.arraycopy(pTInfo, 0, this.pTInfo, 0, pcFound);
/* 443 */       System.arraycopy(rgMemId, 0, this.rgMemId, 0, pcFound);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getNameBuf() {
/* 452 */       return this.nameBuf;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ITypeInfo[] getTInfo() {
/* 461 */       ITypeInfo[] values = new ITypeInfo[this.pcFound];
/* 462 */       for (int i = 0; i < this.pcFound; i++)
/*     */       {
/* 464 */         values[i] = new TypeInfo(this.pTInfo[i]);
/*     */       }
/* 466 */       return values;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public OaIdl.MEMBERID[] getMemId() {
/* 475 */       return this.rgMemId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public short getFound() {
/* 484 */       return this.pcFound;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ReleaseTLibAttr(OaIdl.TLIBATTR pTLibAttr) {
/* 495 */     this.typelib.ReleaseTLibAttr(pTLibAttr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WinDef.LCID getLcid() {
/* 504 */     return this.lcid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITypeLib getTypelib() {
/* 513 */     return this.typelib;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 522 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDocString() {
/* 531 */     return this.docString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHelpContext() {
/* 540 */     return this.helpContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHelpFile() {
/* 549 */     return this.helpFile;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/TypeLibUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */