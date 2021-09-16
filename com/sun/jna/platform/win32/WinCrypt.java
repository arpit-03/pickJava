/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.win32.W32APITypeMapper;
/*     */ import java.util.List;
/*     */ 
/*     */ public interface WinCrypt
/*     */ {
/*     */   public static final int CRYPTPROTECT_PROMPT_ON_UNPROTECT = 1;
/*     */   public static final int CRYPTPROTECT_PROMPT_ON_PROTECT = 2;
/*     */   public static final int CRYPTPROTECT_PROMPT_RESERVED = 4;
/*     */   public static final int CRYPTPROTECT_PROMPT_STRONG = 8;
/*     */   public static final int CRYPTPROTECT_PROMPT_REQUIRE_STRONG = 16;
/*     */   public static final int CRYPTPROTECT_UI_FORBIDDEN = 1;
/*     */   public static final int CRYPTPROTECT_LOCAL_MACHINE = 4;
/*     */   public static final int CRYPTPROTECT_CRED_SYNC = 8;
/*     */   public static final int CRYPTPROTECT_AUDIT = 16;
/*     */   public static final int CRYPTPROTECT_NO_RECOVERY = 32;
/*     */   public static final int CRYPTPROTECT_VERIFY_PROTECTION = 64;
/*     */   public static final int CRYPTPROTECT_CRED_REGENERATE = 128;
/*     */   public static final int CRYPT_E_ASN1_ERROR = -2146881280;
/*     */   public static final int CRYPT_E_ASN1_INTERNAL = -2146881279;
/*     */   public static final int CRYPT_E_ASN1_EOD = -2146881278;
/*     */   public static final int CRYPT_E_ASN1_CORRUPT = -2146881277;
/*     */   public static final int CRYPT_E_ASN1_LARGE = -2146881276;
/*     */   public static final int CRYPT_E_ASN1_CONSTRAINT = -2146881275;
/*     */   public static final int CRYPT_E_ASN1_MEMORY = -2146881274;
/*     */   public static final int CRYPT_E_ASN1_OVERFLOW = -2146881273;
/*     */   public static final int CRYPT_E_ASN1_BADPDU = -2146881272;
/*     */   public static final int CRYPT_E_ASN1_BADARGS = -2146881271;
/*     */   public static final int CRYPT_E_ASN1_BADREAL = -2146881270;
/*     */   public static final int CRYPT_E_ASN1_BADTAG = -2146881269;
/*     */   public static final int CRYPT_E_ASN1_CHOICE = -2146881268;
/*     */   public static final int CRYPT_E_ASN1_RULE = -2146881267;
/*     */   public static final int CRYPT_E_ASN1_UTF8 = -2146881266;
/*     */   public static final int CRYPT_E_ASN1_PDU_TYPE = -2146881229;
/*     */   public static final int CRYPT_E_ASN1_NYI = -2146881228;
/*     */   public static final int CRYPT_E_ASN1_EXTENDED = -2146881023;
/*     */   public static final int CRYPT_E_ASN1_NOEOD = -2146881022;
/*     */   
/*     */   public static class DATA_BLOB
/*     */     extends Structure {
/*  46 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cbData", "pbData" });
/*     */ 
/*     */     
/*     */     public int cbData;
/*     */ 
/*     */     
/*     */     public Pointer pbData;
/*     */ 
/*     */ 
/*     */     
/*     */     public DATA_BLOB() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public DATA_BLOB(Pointer memory) {
/*  61 */       super(memory);
/*  62 */       read();
/*     */     }
/*     */     
/*     */     public DATA_BLOB(byte[] data) {
/*  66 */       this.pbData = (Pointer)new Memory(data.length);
/*  67 */       this.pbData.write(0L, data, 0, data.length);
/*  68 */       this.cbData = data.length;
/*  69 */       allocateMemory();
/*     */     }
/*     */     
/*     */     public DATA_BLOB(String s) {
/*  73 */       this(Native.toByteArray(s));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  79 */       return FIELDS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getData() {
/*  87 */       return (this.pbData == null) ? null : this.pbData.getByteArray(0L, this.cbData);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CRYPTPROTECT_PROMPTSTRUCT
/*     */     extends Structure
/*     */   {
/*  97 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cbSize", "dwPromptFlags", "hwndApp", "szPrompt" });
/*     */ 
/*     */ 
/*     */     
/*     */     public int cbSize;
/*     */ 
/*     */ 
/*     */     
/*     */     public int dwPromptFlags;
/*     */ 
/*     */     
/*     */     public WinDef.HWND hwndApp;
/*     */ 
/*     */     
/*     */     public String szPrompt;
/*     */ 
/*     */ 
/*     */     
/*     */     public CRYPTPROTECT_PROMPTSTRUCT() {
/* 116 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */     
/*     */     public CRYPTPROTECT_PROMPTSTRUCT(Pointer memory) {
/* 120 */       super(memory, 0, W32APITypeMapper.DEFAULT);
/* 121 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 126 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinCrypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */