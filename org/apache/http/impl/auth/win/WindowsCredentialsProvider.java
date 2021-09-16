/*    */ package org.apache.http.impl.auth.win;
/*    */ 
/*    */ import org.apache.http.annotation.Contract;
/*    */ import org.apache.http.annotation.ThreadingBehavior;
/*    */ import org.apache.http.auth.AuthScope;
/*    */ import org.apache.http.auth.Credentials;
/*    */ import org.apache.http.client.CredentialsProvider;
/*    */ import org.apache.http.util.Args;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Contract(threading = ThreadingBehavior.SAFE)
/*    */ public class WindowsCredentialsProvider
/*    */   implements CredentialsProvider
/*    */ {
/*    */   private final CredentialsProvider provider;
/*    */   
/*    */   public WindowsCredentialsProvider(CredentialsProvider provider) {
/* 53 */     this.provider = (CredentialsProvider)Args.notNull(provider, "Credentials provider");
/*    */   }
/*    */ 
/*    */   
/*    */   public Credentials getCredentials(AuthScope authscope) {
/* 58 */     String scheme = authscope.getScheme();
/* 59 */     if ("NTLM".equalsIgnoreCase(scheme) || "Negotiate".equalsIgnoreCase(scheme)) {
/* 60 */       return CurrentWindowsCredentials.INSTANCE;
/*    */     }
/* 62 */     return this.provider.getCredentials(authscope);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCredentials(AuthScope authscope, Credentials credentials) {
/* 68 */     this.provider.setCredentials(authscope, credentials);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 73 */     this.provider.clear();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/auth/win/WindowsCredentialsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */