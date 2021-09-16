/*     */ package com.sun.jna.platform.win32.COM;
/*     */ 
/*     */ import com.sun.jna.platform.win32.OaIdl;
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class COMException
/*     */   extends RuntimeException
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private OaIdl.EXCEPINFO pExcepInfo;
/*     */   private IntByReference puArgErr;
/*     */   private int uArgErr;
/*     */   
/*     */   public COMException() {}
/*     */   
/*     */   public COMException(String message, Throwable cause) {
/*  62 */     super(message, cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public COMException(String message) {
/*  72 */     super(message);
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
/*     */   public COMException(String message, OaIdl.EXCEPINFO pExcepInfo, IntByReference puArgErr) {
/*  87 */     super(message + " (puArgErr=" + ((null == puArgErr) ? "" : (String)Integer.valueOf(puArgErr.getValue())) + ")");
/*  88 */     this.pExcepInfo = pExcepInfo;
/*  89 */     this.puArgErr = puArgErr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public COMException(Throwable cause) {
/*  99 */     super(cause);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OaIdl.EXCEPINFO getExcepInfo() {
/* 108 */     return this.pExcepInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntByReference getArgErr() {
/* 117 */     return this.puArgErr;
/*     */   }
/*     */   
/*     */   public int getuArgErr() {
/* 121 */     return this.uArgErr;
/*     */   }
/*     */   
/*     */   public void setuArgErr(int uArgErr) {
/* 125 */     this.uArgErr = uArgErr;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/COM/COMException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */