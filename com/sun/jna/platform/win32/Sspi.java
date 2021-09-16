/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import com.sun.jna.win32.W32APITypeMapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Sspi
/*     */ {
/*     */   public static final int MAX_TOKEN_SIZE = 12288;
/*     */   public static final int SECPKG_CRED_INBOUND = 1;
/*     */   public static final int SECPKG_CRED_OUTBOUND = 2;
/*     */   public static final int SECURITY_NATIVE_DREP = 16;
/*     */   public static final int ISC_REQ_ALLOCATE_MEMORY = 256;
/*     */   public static final int ISC_REQ_CONFIDENTIALITY = 16;
/*     */   public static final int ISC_REQ_CONNECTION = 2048;
/*     */   public static final int ISC_REQ_DELEGATE = 1;
/*     */   public static final int ISC_REQ_EXTENDED_ERROR = 16384;
/*     */   public static final int ISC_REQ_INTEGRITY = 65536;
/*     */   public static final int ISC_REQ_MUTUAL_AUTH = 2;
/*     */   public static final int ISC_REQ_REPLAY_DETECT = 4;
/*     */   public static final int ISC_REQ_SEQUENCE_DETECT = 8;
/*     */   public static final int ISC_REQ_STREAM = 32768;
/*     */   public static final int SECBUFFER_VERSION = 0;
/*     */   public static final int SECBUFFER_EMPTY = 0;
/*     */   public static final int SECBUFFER_DATA = 1;
/*     */   public static final int SECBUFFER_TOKEN = 2;
/*     */   public static final int SECPKG_ATTR_PACKAGE_INFO = 10;
/*     */   public static final int SECPKG_FLAG_INTEGRITY = 1;
/*     */   public static final int SECPKG_FLAG_PRIVACY = 2;
/*     */   public static final int SECPKG_FLAG_TOKEN_ONLY = 4;
/*     */   public static final int SECPKG_FLAG_DATAGRAM = 8;
/*     */   public static final int SECPKG_FLAG_CONNECTION = 16;
/*     */   public static final int SECPKG_FLAG_MULTI_REQUIRED = 32;
/*     */   public static final int SECPKG_FLAG_CLIENT_ONLY = 64;
/*     */   public static final int SECPKG_FLAG_EXTENDED_ERROR = 128;
/*     */   public static final int SECPKG_FLAG_IMPERSONATION = 256;
/*     */   public static final int SECPKG_FLAG_ACCEPT_WIN32_NAME = 512;
/*     */   public static final int SECPKG_FLAG_STREAM = 1024;
/*     */   public static final int SECPKG_FLAG_NEGOTIABLE = 2048;
/*     */   public static final int SECPKG_FLAG_GSS_COMPATIBLE = 4096;
/*     */   public static final int SECPKG_FLAG_LOGON = 8192;
/*     */   public static final int SECPKG_FLAG_ASCII_BUFFERS = 16384;
/*     */   public static final int SECPKG_FLAG_FRAGMENT = 32768;
/*     */   public static final int SECPKG_FLAG_MUTUAL_AUTH = 65536;
/*     */   public static final int SECPKG_FLAG_DELEGATION = 131072;
/*     */   public static final int SECPKG_FLAG_RESTRICTED_TOKENS = 524288;
/*     */   public static final int SECPKG_FLAG_NEGO_EXTENDER = 1048576;
/*     */   public static final int SECPKG_FLAG_NEGOTIABLE2 = 2097152;
/*     */   public static final int SECPKG_FLAG_APPCONTAINER_PASSTHROUGH = 4194304;
/*     */   public static final int SECPKG_FLAG_APPCONTAINER_CHECKS = 8388608;
/*     */   
/*     */   public static class SecHandle
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SecHandle
/*     */       implements Structure.ByReference {}
/*     */     
/* 263 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwLower", "dwUpper" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer dwLower;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer dwUpper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isNull() {
/* 281 */       return (this.dwLower == null && this.dwUpper == null);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 286 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class PSecHandle
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends PSecHandle
/*     */       implements Structure.ByReference {}
/*     */     
/* 298 */     public static final List<String> FIELDS = createFieldsOrder("secHandle");
/*     */ 
/*     */     
/*     */     public Sspi.SecHandle.ByReference secHandle;
/*     */ 
/*     */ 
/*     */     
/*     */     public PSecHandle() {}
/*     */ 
/*     */     
/*     */     public PSecHandle(Sspi.SecHandle h) {
/* 309 */       super(h.getPointer());
/* 310 */       read();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 315 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CredHandle
/*     */     extends SecHandle {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CtxtHandle
/*     */     extends SecHandle {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SecBuffer
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SecBuffer
/*     */       implements Structure.ByReference
/*     */     {
/*     */       public ByReference() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public ByReference(int type, int size) {
/* 355 */         super(type, size);
/*     */       }
/*     */       
/*     */       public ByReference(int type, byte[] token) {
/* 359 */         super(type, token);
/*     */       }
/*     */     }
/*     */     
/* 363 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "cbBuffer", "BufferType", "pvBuffer" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int cbBuffer;
/*     */ 
/*     */ 
/*     */     
/* 372 */     public int BufferType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pointer pvBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecBuffer() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecBuffer(int type, int size) {
/* 393 */       this.cbBuffer = size;
/* 394 */       this.pvBuffer = (Pointer)new Memory(size);
/* 395 */       this.BufferType = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecBuffer(int type, byte[] token) {
/* 406 */       this.cbBuffer = token.length;
/* 407 */       this.pvBuffer = (Pointer)new Memory(token.length);
/* 408 */       this.pvBuffer.write(0L, token, 0, token.length);
/* 409 */       this.BufferType = type;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getBytes() {
/* 418 */       return (this.pvBuffer == null) ? null : this.pvBuffer.getByteArray(0L, this.cbBuffer);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 423 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SecBufferDesc extends Structure {
/* 428 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "ulVersion", "cBuffers", "pBuffers" });
/*     */ 
/*     */ 
/*     */     
/* 432 */     public int ulVersion = 0;
/*     */ 
/*     */ 
/*     */     
/* 436 */     public int cBuffers = 1;
/*     */ 
/*     */ 
/*     */     
/* 440 */     public Sspi.SecBuffer.ByReference[] pBuffers = new Sspi.SecBuffer.ByReference[] { new Sspi.SecBuffer.ByReference() };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecBufferDesc() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecBufferDesc(int type, byte[] token) {
/* 457 */       this.pBuffers[0] = new Sspi.SecBuffer.ByReference(type, token);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SecBufferDesc(int type, int tokenSize) {
/* 466 */       this.pBuffers[0] = new Sspi.SecBuffer.ByReference(type, tokenSize);
/*     */     }
/*     */     
/*     */     public byte[] getBytes() {
/* 470 */       return this.pBuffers[0].getBytes();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 475 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class SECURITY_INTEGER
/*     */     extends Structure
/*     */   {
/* 483 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwLower", "dwUpper" });
/*     */     
/*     */     public int dwLower;
/*     */     public int dwUpper;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 489 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class TimeStamp
/*     */     extends SECURITY_INTEGER {}
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PSecPkgInfo
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends PSecPkgInfo
/*     */       implements Structure.ByReference {}
/*     */ 
/*     */     
/* 508 */     public static final List<String> FIELDS = createFieldsOrder("pPkgInfo");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Sspi.SecPkgInfo.ByReference pPkgInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 521 */       return FIELDS;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Sspi.SecPkgInfo.ByReference[] toArray(int size) {
/* 529 */       return (Sspi.SecPkgInfo.ByReference[])this.pPkgInfo.toArray(size);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SecPkgInfo
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SecPkgInfo
/*     */       implements Structure.ByReference {}
/*     */ 
/*     */ 
/*     */     
/* 545 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "fCapabilities", "wVersion", "wRPCID", "cbMaxToken", "Name", "Comment" });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int fCapabilities;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 555 */     public short wVersion = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     public short wRPCID;
/*     */ 
/*     */ 
/*     */     
/*     */     public int cbMaxToken;
/*     */ 
/*     */ 
/*     */     
/*     */     public String Name;
/*     */ 
/*     */ 
/*     */     
/*     */     public String Comment;
/*     */ 
/*     */ 
/*     */     
/*     */     public SecPkgInfo() {
/* 576 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 581 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SecPkgContext_PackageInfo
/*     */     extends Structure
/*     */   {
/*     */     public static class ByReference
/*     */       extends SecPkgContext_PackageInfo
/*     */       implements Structure.ByReference {}
/*     */ 
/*     */     
/* 595 */     public static final List<String> FIELDS = createFieldsOrder("PackageInfo");
/*     */ 
/*     */ 
/*     */     
/*     */     public Sspi.SecPkgInfo.ByReference PackageInfo;
/*     */ 
/*     */ 
/*     */     
/*     */     public SecPkgContext_PackageInfo() {
/* 604 */       super(W32APITypeMapper.DEFAULT);
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 609 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/Sspi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */