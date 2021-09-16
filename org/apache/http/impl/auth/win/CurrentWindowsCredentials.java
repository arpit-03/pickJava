/*     */ package org.apache.http.impl.auth.win;
/*     */ 
/*     */ import com.sun.jna.platform.win32.Secur32Util;
/*     */ import java.io.Serializable;
/*     */ import java.security.Principal;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.auth.Credentials;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ public final class CurrentWindowsCredentials
/*     */   implements Credentials, Serializable, Principal
/*     */ {
/*     */   private static final long serialVersionUID = 4361166468529298169L;
/*  53 */   public static final CurrentWindowsCredentials INSTANCE = new CurrentWindowsCredentials();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getCurrentUsername() {
/*  61 */     return Secur32Util.getUserNameEx(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Principal getUserPrincipal() {
/*  69 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  74 */     return getClass().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  79 */     if (this == o) {
/*  80 */       return true;
/*     */     }
/*  82 */     if (o == null) {
/*  83 */       return false;
/*     */     }
/*  85 */     return getClass().equals(o.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  90 */     return getCurrentUsername();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/*  98 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 103 */     return getCurrentUsername();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/auth/win/CurrentWindowsCredentials.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */