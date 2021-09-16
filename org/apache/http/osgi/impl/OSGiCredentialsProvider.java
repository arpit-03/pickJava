/*     */ package org.apache.http.osgi.impl;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.http.auth.AuthScope;
/*     */ import org.apache.http.auth.Credentials;
/*     */ import org.apache.http.auth.NTCredentials;
/*     */ import org.apache.http.auth.UsernamePasswordCredentials;
/*     */ import org.apache.http.client.CredentialsProvider;
/*     */ import org.apache.http.osgi.services.ProxyConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OSGiCredentialsProvider
/*     */   implements CredentialsProvider
/*     */ {
/*  45 */   private static final Log log = LogFactory.getLog(OSGiCredentialsProvider.class);
/*     */   
/*     */   private static final int HOST_AND_PORT_MATCH = 12;
/*     */   
/*     */   private static final String BASIC_SCHEME_NAME = "BASIC";
/*     */   
/*     */   private static final String NTLM_SCHEME_NAME = "NTLM";
/*     */   
/*     */   private final List<ProxyConfiguration> proxyConfigurations;
/*     */   
/*     */   OSGiCredentialsProvider(List<ProxyConfiguration> proxyConfigurations) {
/*  56 */     this.proxyConfigurations = proxyConfigurations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCredentials(AuthScope authscope, Credentials credentials) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Credentials getCredentials(AuthScope authScope) {
/*  73 */     for (ProxyConfiguration config : this.proxyConfigurations) {
/*  74 */       if (config.isEnabled() && isSuitable(config, authScope)) {
/*  75 */         String scheme = authScope.getScheme();
/*  76 */         if ("BASIC".equals(scheme))
/*  77 */           return (Credentials)new UsernamePasswordCredentials(config.getUsername(), config.getPassword()); 
/*  78 */         if ("NTLM".equals(scheme)) {
/*  79 */           return createNTCredentials(config);
/*     */         }
/*  81 */         log.debug("credentials requested for unsupported authentication scheme " + scheme);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  86 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSuitable(ProxyConfiguration config, AuthScope authScope) {
/*  99 */     return (authScope.match(new AuthScope(config.getHostname(), config.getPort())) >= 12);
/*     */   }
/*     */   
/*     */   private static Credentials createNTCredentials(ProxyConfiguration config) {
/* 103 */     String username, domain, domainAndUsername = config.getUsername();
/*     */ 
/*     */     
/* 106 */     int index = domainAndUsername.indexOf("\\");
/* 107 */     if (index > -1) {
/* 108 */       username = domainAndUsername.substring(index + 1);
/* 109 */       domain = domainAndUsername.substring(0, index);
/*     */     } else {
/* 111 */       username = domainAndUsername;
/* 112 */       domain = null;
/*     */     } 
/* 114 */     return (Credentials)new NTCredentials(username, config.getPassword(), null, domain);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/OSGiCredentialsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */