/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.LastErrorException;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.platform.win32.Advapi32;
/*     */ import com.sun.jna.platform.win32.Advapi32Util;
/*     */ import com.sun.jna.platform.win32.Kernel32Util;
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.platform.win32.Ole32;
/*     */ import com.sun.jna.platform.win32.W32Errors;
/*     */ import com.sun.jna.platform.win32.WinNT;
/*     */ import com.sun.jna.platform.win32.WinReg;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class COMUtils
/*     */ {
/*     */   public static final int S_OK = 0;
/*     */   public static final int S_FALSE = 1;
/*     */   public static final int E_UNEXPECTED = -2147418113;
/*     */   
/*     */   public static boolean SUCCEEDED(WinNT.HRESULT hr) {
/*  65 */     return SUCCEEDED(hr.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean SUCCEEDED(int hr) {
/*  76 */     return (hr >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean FAILED(WinNT.HRESULT hr) {
/*  87 */     return FAILED(hr.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean FAILED(int hr) {
/*  98 */     return (hr < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void checkRC(WinNT.HRESULT hr) {
/* 108 */     checkRC(hr, null, null);
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
/*     */   public static void checkRC(WinNT.HRESULT hr, OaIdl.EXCEPINFO pExcepInfo, IntByReference puArgErr) {
/* 123 */     if (FAILED(hr)) {
/*     */       String formatMessage;
/*     */       try {
/* 126 */         formatMessage = Kernel32Util.formatMessage(hr) + "(HRESULT: " + Integer.toHexString(hr.intValue()) + ")";
/* 127 */       } catch (LastErrorException ex) {
/*     */         
/* 129 */         formatMessage = "(HRESULT: " + Integer.toHexString(hr.intValue()) + ")";
/*     */       } 
/* 131 */       throw new COMException(formatMessage, pExcepInfo, puArgErr);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<COMInfo> getAllCOMInfoOnSystem() {
/* 141 */     WinReg.HKEYByReference phkResult = new WinReg.HKEYByReference();
/* 142 */     WinReg.HKEYByReference phkResult2 = new WinReg.HKEYByReference();
/*     */     
/* 144 */     ArrayList<COMInfo> comInfos = new ArrayList<COMInfo>();
/*     */ 
/*     */     
/*     */     try {
/* 148 */       phkResult = Advapi32Util.registryGetKey(WinReg.HKEY_CLASSES_ROOT, "CLSID", 131097);
/*     */ 
/*     */       
/* 151 */       Advapi32Util.InfoKey infoKey = Advapi32Util.registryQueryInfoKey(phkResult
/* 152 */           .getValue(), 131097);
/*     */       
/* 154 */       for (int i = 0; i < infoKey.lpcSubKeys.getValue(); i++) {
/* 155 */         Advapi32Util.EnumKey enumKey = Advapi32Util.registryRegEnumKey(phkResult
/* 156 */             .getValue(), i);
/* 157 */         String subKey = Native.toString(enumKey.lpName);
/*     */         
/* 159 */         COMInfo comInfo = new COMInfo(subKey);
/*     */         
/* 161 */         phkResult2 = Advapi32Util.registryGetKey(phkResult.getValue(), subKey, 131097);
/*     */         
/* 163 */         Advapi32Util.InfoKey infoKey2 = Advapi32Util.registryQueryInfoKey(phkResult2
/* 164 */             .getValue(), 131097);
/*     */         
/* 166 */         for (int y = 0; y < infoKey2.lpcSubKeys.getValue(); y++) {
/* 167 */           Advapi32Util.EnumKey enumKey2 = Advapi32Util.registryRegEnumKey(phkResult2
/* 168 */               .getValue(), y);
/* 169 */           String subKey2 = Native.toString(enumKey2.lpName);
/*     */           
/* 171 */           if (subKey2.equals("InprocHandler32")) {
/* 172 */             comInfo
/* 173 */               .inprocHandler32 = (String)Advapi32Util.registryGetValue(phkResult2.getValue(), subKey2, null);
/*     */           }
/* 175 */           else if (subKey2.equals("InprocServer32")) {
/* 176 */             comInfo
/* 177 */               .inprocServer32 = (String)Advapi32Util.registryGetValue(phkResult2.getValue(), subKey2, null);
/*     */           }
/* 179 */           else if (subKey2.equals("LocalServer32")) {
/* 180 */             comInfo
/* 181 */               .localServer32 = (String)Advapi32Util.registryGetValue(phkResult2.getValue(), subKey2, null);
/*     */           }
/* 183 */           else if (subKey2.equals("ProgID")) {
/* 184 */             comInfo
/* 185 */               .progID = (String)Advapi32Util.registryGetValue(phkResult2.getValue(), subKey2, null);
/*     */           }
/* 187 */           else if (subKey2.equals("TypeLib")) {
/* 188 */             comInfo
/* 189 */               .typeLib = (String)Advapi32Util.registryGetValue(phkResult2.getValue(), subKey2, null);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 194 */         Advapi32.INSTANCE.RegCloseKey(phkResult2.getValue());
/* 195 */         comInfos.add(comInfo);
/*     */       } 
/*     */     } finally {
/* 198 */       Advapi32.INSTANCE.RegCloseKey(phkResult.getValue());
/* 199 */       Advapi32.INSTANCE.RegCloseKey(phkResult2.getValue());
/*     */     } 
/*     */     
/* 202 */     return comInfos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean comIsInitialized() {
/* 213 */     WinNT.HRESULT hr = Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, 0);
/* 214 */     if (hr.equals(W32Errors.S_OK)) {
/*     */       
/* 216 */       Ole32.INSTANCE.CoUninitialize();
/* 217 */       return false;
/* 218 */     }  if (hr.equals(W32Errors.S_FALSE)) {
/*     */ 
/*     */ 
/*     */       
/* 222 */       Ole32.INSTANCE.CoUninitialize();
/* 223 */       return true;
/* 224 */     }  if (hr.intValue() == -2147417850) {
/* 225 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 230 */     checkRC(hr);
/*     */     
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class COMInfo
/*     */   {
/*     */     public String clsid;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String inprocHandler32;
/*     */ 
/*     */ 
/*     */     
/*     */     public String inprocServer32;
/*     */ 
/*     */ 
/*     */     
/*     */     public String localServer32;
/*     */ 
/*     */ 
/*     */     
/*     */     public String progID;
/*     */ 
/*     */ 
/*     */     
/*     */     public String typeLib;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public COMInfo() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public COMInfo(String clsid) {
/* 273 */       this.clsid = clsid;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/COMUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */