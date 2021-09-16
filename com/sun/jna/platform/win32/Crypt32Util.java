/*     */ package com.sun.jna.platform.win32;
/*     */ 
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
/*     */ public abstract class Crypt32Util
/*     */ {
/*     */   public static byte[] cryptProtectData(byte[] data) {
/*  44 */     return cryptProtectData(data, 0);
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
/*     */   public static byte[] cryptProtectData(byte[] data, int flags) {
/*  57 */     return cryptProtectData(data, null, flags, "", null);
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
/*     */   public static byte[] cryptProtectData(byte[] data, byte[] entropy, int flags, String description, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT prompt) {
/*  77 */     WinCrypt.DATA_BLOB pDataIn = new WinCrypt.DATA_BLOB(data);
/*  78 */     WinCrypt.DATA_BLOB pDataProtected = new WinCrypt.DATA_BLOB();
/*  79 */     WinCrypt.DATA_BLOB pEntropy = (entropy == null) ? null : new WinCrypt.DATA_BLOB(entropy);
/*     */     try {
/*  81 */       if (!Crypt32.INSTANCE.CryptProtectData(pDataIn, description, pEntropy, null, prompt, flags, pDataProtected))
/*     */       {
/*  83 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       }
/*  85 */       return pDataProtected.getData();
/*     */     } finally {
/*  87 */       if (pDataProtected.pbData != null) {
/*  88 */         Kernel32Util.freeLocalMemory(pDataProtected.pbData);
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
/*     */   public static byte[] cryptUnprotectData(byte[] data) {
/* 101 */     return cryptUnprotectData(data, 0);
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
/*     */   public static byte[] cryptUnprotectData(byte[] data, int flags) {
/* 114 */     return cryptUnprotectData(data, null, flags, null);
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
/*     */   public static byte[] cryptUnprotectData(byte[] data, byte[] entropy, int flags, WinCrypt.CRYPTPROTECT_PROMPTSTRUCT prompt) {
/* 132 */     WinCrypt.DATA_BLOB pDataIn = new WinCrypt.DATA_BLOB(data);
/* 133 */     WinCrypt.DATA_BLOB pDataUnprotected = new WinCrypt.DATA_BLOB();
/* 134 */     WinCrypt.DATA_BLOB pEntropy = (entropy == null) ? null : new WinCrypt.DATA_BLOB(entropy);
/* 135 */     PointerByReference pDescription = new PointerByReference();
/* 136 */     Win32Exception err = null;
/* 137 */     byte[] unProtectedData = null;
/*     */     try {
/* 139 */       if (!Crypt32.INSTANCE.CryptUnprotectData(pDataIn, pDescription, pEntropy, null, prompt, flags, pDataUnprotected)) {
/*     */         
/* 141 */         err = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       } else {
/* 143 */         unProtectedData = pDataUnprotected.getData();
/*     */       } 
/*     */     } finally {
/* 146 */       if (pDataUnprotected.pbData != null) {
/*     */         try {
/* 148 */           Kernel32Util.freeLocalMemory(pDataUnprotected.pbData);
/* 149 */         } catch (Win32Exception e) {
/* 150 */           if (err == null) {
/* 151 */             err = e;
/*     */           } else {
/* 153 */             err.addSuppressedReflected((Throwable)e);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 158 */       if (pDescription.getValue() != null) {
/*     */         try {
/* 160 */           Kernel32Util.freeLocalMemory(pDescription.getValue());
/* 161 */         } catch (Win32Exception e) {
/* 162 */           if (err == null) {
/* 163 */             err = e;
/*     */           } else {
/* 165 */             err.addSuppressedReflected((Throwable)e);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 171 */     if (err != null) {
/* 172 */       throw err;
/*     */     }
/*     */     
/* 175 */     return unProtectedData;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Crypt32Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */