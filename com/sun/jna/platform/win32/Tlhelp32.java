/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Native;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Tlhelp32
/*     */ {
/*  42 */   public static final WinDef.DWORD TH32CS_SNAPHEAPLIST = new WinDef.DWORD(1L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final WinDef.DWORD TH32CS_SNAPPROCESS = new WinDef.DWORD(2L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final WinDef.DWORD TH32CS_SNAPTHREAD = new WinDef.DWORD(4L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final WinDef.DWORD TH32CS_SNAPMODULE = new WinDef.DWORD(8L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final WinDef.DWORD TH32CS_SNAPMODULE32 = new WinDef.DWORD(16L);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final WinDef.DWORD TH32CS_SNAPALL = new WinDef.DWORD((TH32CS_SNAPHEAPLIST.intValue() | TH32CS_SNAPPROCESS
/*  83 */       .intValue() | TH32CS_SNAPTHREAD.intValue() | TH32CS_SNAPMODULE.intValue()));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final WinDef.DWORD TH32CS_INHERIT = new WinDef.DWORD(-2147483648L);
/*     */   
/*     */   public static final int MAX_MODULE_NAME32 = 255;
/*     */   
/*     */   public static class PROCESSENTRY32
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends PROCESSENTRY32
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 102 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 106 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwSize", "cntUsage", "th32ProcessID", "th32DefaultHeapID", "th32ModuleID", "cntThreads", "th32ParentProcessID", "pcPriClassBase", "dwFlags", "szExeFile" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD cntUsage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD th32ProcessID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BaseTSD.ULONG_PTR th32DefaultHeapID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD th32ModuleID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD cntThreads;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD th32ParentProcessID;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.LONG pcPriClassBase;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwFlags;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     public char[] szExeFile = new char[260];
/*     */     
/*     */     public PROCESSENTRY32() {
/* 165 */       this.dwSize = new WinDef.DWORD(size());
/*     */     }
/*     */     
/*     */     public PROCESSENTRY32(Pointer memory) {
/* 169 */       super(memory);
/* 170 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 175 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MODULEENTRY32W
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends MODULEENTRY32W
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public ByReference(Pointer memory) {
/* 195 */         super(memory);
/*     */       }
/*     */     }
/*     */     
/* 199 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwSize", "th32ModuleID", "th32ProcessID", "GlblcntUsage", "ProccntUsage", "modBaseAddr", "modBaseSize", "hModule", "szModule", "szExePath" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD dwSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD th32ModuleID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD th32ProcessID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD GlblcntUsage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD ProccntUsage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer modBaseAddr;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.DWORD modBaseSize;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WinDef.HMODULE hModule;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     public char[] szModule = new char[256];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 255 */     public char[] szExePath = new char[260];
/*     */     
/*     */     public MODULEENTRY32W() {
/* 258 */       this.dwSize = new WinDef.DWORD(size());
/*     */     }
/*     */     
/*     */     public MODULEENTRY32W(Pointer memory) {
/* 262 */       super(memory);
/* 263 */       read();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String szModule() {
/* 270 */       return Native.toString(this.szModule);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String szExePath() {
/* 277 */       return Native.toString(this.szExePath);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 282 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Tlhelp32.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */