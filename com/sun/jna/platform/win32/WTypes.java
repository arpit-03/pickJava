/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.PointerType;
/*     */ import com.sun.jna.Structure;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface WTypes
/*     */ {
/*     */   public static final int CLSCTX_INPROC_SERVER = 1;
/*     */   public static final int CLSCTX_INPROC_HANDLER = 2;
/*     */   public static final int CLSCTX_LOCAL_SERVER = 4;
/*     */   public static final int CLSCTX_INPROC_SERVER16 = 8;
/*     */   public static final int CLSCTX_REMOTE_SERVER = 16;
/*     */   public static final int CLSCTX_INPROC_HANDLER16 = 32;
/*     */   public static final int CLSCTX_RESERVED1 = 64;
/*     */   public static final int CLSCTX_RESERVED2 = 128;
/*     */   public static final int CLSCTX_RESERVED3 = 256;
/*     */   public static final int CLSCTX_RESERVED4 = 512;
/*     */   public static final int CLSCTX_NO_CODE_DOWNLOAD = 1024;
/*     */   public static final int CLSCTX_RESERVED5 = 2048;
/*     */   public static final int CLSCTX_NO_CUSTOM_MARSHAL = 4096;
/*     */   public static final int CLSCTX_ENABLE_CODE_DOWNLOAD = 8192;
/*     */   public static final int CLSCTX_NO_FAILURE_LOG = 16384;
/*     */   public static final int CLSCTX_DISABLE_AAA = 32768;
/*     */   public static final int CLSCTX_ENABLE_AAA = 65536;
/*     */   public static final int CLSCTX_FROM_DEFAULT_CONTEXT = 131072;
/*     */   public static final int CLSCTX_ACTIVATE_32_BIT_SERVER = 262144;
/*     */   public static final int CLSCTX_ACTIVATE_64_BIT_SERVER = 524288;
/*     */   public static final int CLSCTX_ENABLE_CLOAKING = 1048576;
/*     */   public static final int CLSCTX_APPCONTAINER = 4194304;
/*     */   public static final int CLSCTX_ACTIVATE_AAA_AS_IU = 8388608;
/*     */   public static final int CLSCTX_PS_DLL = -2147483648;
/*     */   public static final int CLSCTX_SERVER = 21;
/*     */   public static final int CLSCTX_ALL = 7;
/*     */   
/*     */   public static class BSTR
/*     */     extends PointerType
/*     */   {
/*     */     public static class ByReference
/*     */       extends BSTR
/*     */       implements Structure.ByReference {}
/*     */     
/*     */     public BSTR() {
/* 109 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public BSTR(Pointer pointer) {
/* 113 */       super(pointer);
/*     */     }
/*     */ 
/*     */     
/*     */     public BSTR(String value) {
/* 118 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 122 */       if (value == null) {
/* 123 */         value = "";
/*     */       }
/*     */       try {
/* 126 */         byte[] encodedValue = value.getBytes("UTF-16LE");
/*     */ 
/*     */         
/* 129 */         Memory mem = new Memory((4 + encodedValue.length + 2));
/* 130 */         mem.clear();
/* 131 */         mem.setInt(0L, encodedValue.length);
/* 132 */         mem.write(4L, encodedValue, 0, encodedValue.length);
/* 133 */         setPointer(mem.share(4L));
/* 134 */       } catch (UnsupportedEncodingException ex) {
/* 135 */         throw new RuntimeException("UTF-16LE charset is not supported", ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getValue() {
/*     */       try {
/* 141 */         Pointer pointer = getPointer();
/* 142 */         if (pointer == null) {
/* 143 */           return "";
/*     */         }
/* 145 */         int stringLength = pointer.getInt(-4L);
/* 146 */         return new String(pointer.getByteArray(0L, stringLength), "UTF-16LE");
/* 147 */       } catch (UnsupportedEncodingException ex) {
/* 148 */         throw new RuntimeException("UTF-16LE charset is not supported", ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 154 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BSTRByReference extends com.sun.jna.ptr.ByReference {
/*     */     public BSTRByReference() {
/* 160 */       super(Pointer.SIZE);
/*     */     }
/*     */     
/*     */     public BSTRByReference(WTypes.BSTR value) {
/* 164 */       this();
/* 165 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(WTypes.BSTR value) {
/* 169 */       getPointer().setPointer(0L, value.getPointer());
/*     */     }
/*     */     
/*     */     public WTypes.BSTR getValue() {
/* 173 */       return new WTypes.BSTR(getPointer().getPointer(0L));
/*     */     }
/*     */     
/*     */     public String getString() {
/* 177 */       return getValue().getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LPSTR
/*     */     extends PointerType {
/*     */     public static class ByReference
/*     */       extends WTypes.BSTR implements Structure.ByReference {}
/*     */     
/*     */     public LPSTR() {
/* 187 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public LPSTR(Pointer pointer) {
/* 191 */       super(pointer);
/*     */     }
/*     */     
/*     */     public LPSTR(String value) {
/* 195 */       this((Pointer)new Memory((value.length() + 1L) * Native.WCHAR_SIZE));
/* 196 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 200 */       getPointer().setWideString(0L, value);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 204 */       Pointer pointer = getPointer();
/* 205 */       String str = null;
/* 206 */       if (pointer != null) {
/* 207 */         str = pointer.getWideString(0L);
/*     */       }
/* 209 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 214 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LPWSTR
/*     */     extends PointerType {
/*     */     public static class ByReference
/*     */       extends WTypes.BSTR implements Structure.ByReference {}
/*     */     
/*     */     public LPWSTR() {
/* 224 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public LPWSTR(Pointer pointer) {
/* 228 */       super(pointer);
/*     */     }
/*     */     
/*     */     public LPWSTR(String value) {
/* 232 */       this((Pointer)new Memory((value.length() + 1L) * Native.WCHAR_SIZE));
/* 233 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 237 */       getPointer().setWideString(0L, value);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 241 */       Pointer pointer = getPointer();
/* 242 */       String str = null;
/* 243 */       if (pointer != null) {
/* 244 */         str = pointer.getWideString(0L);
/*     */       }
/* 246 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 251 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class LPOLESTR
/*     */     extends PointerType {
/*     */     public static class ByReference
/*     */       extends LPOLESTR implements Structure.ByReference {}
/*     */     
/*     */     public LPOLESTR() {
/* 261 */       super(Pointer.NULL);
/*     */     }
/*     */     
/*     */     public LPOLESTR(Pointer pointer) {
/* 265 */       super(pointer);
/*     */     }
/*     */     
/*     */     public LPOLESTR(String value) {
/* 269 */       super((Pointer)new Memory((value.length() + 1L) * Native.WCHAR_SIZE));
/* 270 */       setValue(value);
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 274 */       getPointer().setWideString(0L, value);
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 278 */       Pointer pointer = getPointer();
/* 279 */       String str = null;
/* 280 */       if (pointer != null) {
/* 281 */         str = pointer.getWideString(0L);
/*     */       }
/* 283 */       return str;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 288 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class VARTYPE extends WinDef.USHORT {
/*     */     private static final long serialVersionUID = 1L;
/*     */     
/*     */     public VARTYPE() {
/* 296 */       this(0);
/*     */     }
/*     */     
/*     */     public VARTYPE(int value) {
/* 300 */       super(value);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class VARTYPEByReference extends com.sun.jna.ptr.ByReference {
/*     */     public VARTYPEByReference() {
/* 306 */       super(2);
/*     */     }
/*     */     
/*     */     public VARTYPEByReference(WTypes.VARTYPE type) {
/* 310 */       super(2);
/* 311 */       setValue(type);
/*     */     }
/*     */     
/*     */     public VARTYPEByReference(short type) {
/* 315 */       super(2);
/* 316 */       getPointer().setShort(0L, type);
/*     */     }
/*     */     
/*     */     public void setValue(WTypes.VARTYPE value) {
/* 320 */       getPointer().setShort(0L, value.shortValue());
/*     */     }
/*     */     
/*     */     public WTypes.VARTYPE getValue() {
/* 324 */       return new WTypes.VARTYPE(getPointer().getShort(0L));
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WTypes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */