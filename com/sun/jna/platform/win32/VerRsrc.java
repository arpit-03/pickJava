/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
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
/*     */ public interface VerRsrc
/*     */ {
/*     */   public static class VS_FIXEDFILEINFO
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends VS_FIXEDFILEINFO
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/*  44 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/*  48 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwSignature", "dwStrucVersion", "dwFileVersionMS", "dwFileVersionLS", "dwProductVersionMS", "dwProductVersionLS", "dwFileFlagsMask", "dwFileFlags", "dwFileOS", "dwFileType", "dwFileSubtype", "dwFileDateMS", "dwFileDateLS" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwSignature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwStrucVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileVersionMS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileVersionLS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwProductVersionMS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwProductVersionLS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileFlagsMask;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileFlags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileOS;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileSubtype;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileDateMS;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFileDateLS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public VS_FIXEDFILEINFO() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public VS_FIXEDFILEINFO(Pointer memory) {
/* 134 */       super(memory);
/* 135 */       read();
/*     */     }
/*     */     
/*     */     public int getFileVersionMajor() {
/* 139 */       return this.dwFileVersionMS.intValue() >>> 16;
/*     */     }
/*     */     
/*     */     public int getFileVersionMinor() {
/* 143 */       return this.dwFileVersionMS.intValue() & 0xFFFF;
/*     */     }
/*     */     
/*     */     public int getFileVersionRevision() {
/* 147 */       return this.dwFileVersionLS.intValue() >>> 16;
/*     */     }
/*     */     
/*     */     public int getFileVersionBuild() {
/* 151 */       return this.dwFileVersionLS.intValue() & 0xFFFF;
/*     */     }
/*     */     
/*     */     public int getProductVersionMajor() {
/* 155 */       return this.dwProductVersionMS.intValue() >>> 16;
/*     */     }
/*     */     
/*     */     public int getProductVersionMinor() {
/* 159 */       return this.dwProductVersionMS.intValue() & 0xFFFF;
/*     */     }
/*     */     
/*     */     public int getProductVersionRevision() {
/* 163 */       return this.dwProductVersionLS.intValue() >>> 16;
/*     */     }
/*     */     
/*     */     public int getProductVersionBuild() {
/* 167 */       return this.dwProductVersionLS.intValue() & 0xFFFF;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 172 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/VerRsrc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */