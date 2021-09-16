/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class W32Errors
/*     */   implements WinError
/*     */ {
/*     */   public static final boolean SUCCEEDED(int hr) {
/*  41 */     return (hr >= 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean FAILED(int hr) {
/*  51 */     return (hr < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean SUCCEEDED(WinNT.HRESULT hr) {
/*  61 */     return (hr == null || SUCCEEDED(hr.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final boolean FAILED(WinNT.HRESULT hr) {
/*  71 */     return (hr != null && FAILED(hr.intValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int HRESULT_CODE(int hr) {
/*  81 */     return hr & 0xFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SCODE_CODE(int sc) {
/*  91 */     return sc & 0xFFFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int HRESULT_FACILITY(int hr) {
/* 101 */     return (hr >>= 16) & 0x1FFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int SCODE_FACILITY(short sc) {
/* 111 */     return (sc = (short)(sc >> 16)) & 0x1FFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short HRESULT_SEVERITY(int hr) {
/* 121 */     return (short)((hr >>= 31) & 0x1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static short SCODE_SEVERITY(short sc) {
/* 131 */     return (short)((sc = (short)(sc >> 31)) & 0x1);
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
/*     */   public static int MAKE_HRESULT(short sev, short fac, short code) {
/* 143 */     return sev << 31 | fac << 16 | code;
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
/*     */   public static final int MAKE_SCODE(short sev, short fac, short code) {
/* 155 */     return sev << 31 | fac << 16 | code;
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
/*     */   public static final WinNT.HRESULT HRESULT_FROM_WIN32(int x) {
/* 167 */     int f = 7;
/* 168 */     return new WinNT.HRESULT((x <= 0) ? x : (x & 0xFFFF | (f <<= 16) | Integer.MIN_VALUE));
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
/*     */   public static final int FILTER_HRESULT_FROM_FLT_NTSTATUS(int x) {
/* 181 */     int f = 31;
/* 182 */     return x & 0x8000FFFF | (f <<= 16);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/W32Errors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */