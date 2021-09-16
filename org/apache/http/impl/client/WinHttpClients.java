/*     */ package org.apache.http.impl.client;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import org.apache.http.auth.AuthSchemeProvider;
/*     */ import org.apache.http.client.CredentialsProvider;
/*     */ import org.apache.http.config.Lookup;
/*     */ import org.apache.http.config.Registry;
/*     */ import org.apache.http.config.RegistryBuilder;
/*     */ import org.apache.http.impl.auth.BasicSchemeFactory;
/*     */ import org.apache.http.impl.auth.DigestSchemeFactory;
/*     */ import org.apache.http.impl.auth.win.WindowsCredentialsProvider;
/*     */ import org.apache.http.impl.auth.win.WindowsNTLMSchemeFactory;
/*     */ import org.apache.http.impl.auth.win.WindowsNegotiateSchemeFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WinHttpClients
/*     */ {
/*     */   public static boolean isWinAuthAvailable() {
/*  57 */     String os = System.getProperty("os.name");
/*  58 */     os = (os != null) ? os.toLowerCase(Locale.ROOT) : null;
/*  59 */     if (os != null && os.contains("windows")) {
/*     */       try {
/*  61 */         return true;
/*  62 */       } catch (Exception ignore) {
/*  63 */         return false;
/*     */       } 
/*     */     }
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   private static HttpClientBuilder createBuilder() {
/*  70 */     if (isWinAuthAvailable()) {
/*  71 */       Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.create().register("Basic", new BasicSchemeFactory()).register("Digest", new DigestSchemeFactory()).register("NTLM", new WindowsNTLMSchemeFactory(null)).register("Negotiate", new WindowsNegotiateSchemeFactory(null)).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  77 */       WindowsCredentialsProvider windowsCredentialsProvider = new WindowsCredentialsProvider(new SystemDefaultCredentialsProvider());
/*  78 */       return HttpClientBuilder.create().setDefaultCredentialsProvider((CredentialsProvider)windowsCredentialsProvider).setDefaultAuthSchemeRegistry((Lookup<AuthSchemeProvider>)authSchemeRegistry);
/*     */     } 
/*     */ 
/*     */     
/*  82 */     return HttpClientBuilder.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpClientBuilder custom() {
/*  91 */     return createBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CloseableHttpClient createDefault() {
/*  99 */     return createBuilder().build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CloseableHttpClient createSystem() {
/* 107 */     return createBuilder().useSystemProperties().build();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/WinHttpClients.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */