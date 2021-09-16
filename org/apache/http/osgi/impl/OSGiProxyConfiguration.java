/*     */ package org.apache.http.osgi.impl;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Dictionary;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OSGiProxyConfiguration
/*     */   implements ProxyConfiguration
/*     */ {
/*     */   private static final String PROPERTYNAME_PROXY_ENABLED = "proxy.enabled";
/*  47 */   private static final Boolean PROPERTYDEFAULT_PROXY_ENABLED = Boolean.TRUE;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PROPERTYNAME_PROXY_HOSTNAME = "proxy.host";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PROPERTYDEFAULT_PROXY_HOSTNAME = "";
/*     */ 
/*     */   
/*     */   private static final String PROPERTYNAME_PROXY_PORT = "proxy.port";
/*     */ 
/*     */   
/*  61 */   private static final Integer PROPERTYDEFAULT_PROXY_PORT = Integer.valueOf(0);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PROPERTYNAME_PROXY_USERNAME = "proxy.user";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PROPERTYDEFAULT_PROXY_USERNAME = "";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PROPERTYNAME_PROXY_PASSWORD = "proxy.password";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String PROPERTYDEFAULT_PROXY_PASSWORD = "";
/*     */ 
/*     */   
/*     */   private static final String PROPERTYNAME_PROXY_EXCEPTIONS = "proxy.exceptions";
/*     */ 
/*     */   
/*  83 */   private static final String[] PROPERTYDEFAULT_PROXY_EXCEPTIONS = new String[] { "localhost", "127.0.0.1" };
/*     */   
/*  85 */   private Boolean enabled = Boolean.FALSE;
/*     */   
/*     */   private String hostname;
/*     */   
/*  89 */   private Integer port = Integer.valueOf(0);
/*     */   
/*     */   private String username;
/*     */   
/*     */   private String password;
/*     */   
/*     */   private String[] proxyExceptions;
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  99 */     return this.enabled.booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHostname() {
/* 104 */     return this.hostname;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 109 */     return this.port.intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 114 */     return this.username;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 119 */     return this.password;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getProxyExceptions() {
/* 124 */     return this.proxyExceptions;
/*     */   }
/*     */   
/*     */   public void update(Dictionary<String, Object> config) {
/* 128 */     this.enabled = PropertiesUtils.<Boolean>to(config.get("proxy.enabled"), (Class)boolean.class, PROPERTYDEFAULT_PROXY_ENABLED);
/* 129 */     this.hostname = PropertiesUtils.<String>to(config.get("proxy.host"), String.class, "");
/* 130 */     this.port = PropertiesUtils.<Integer>to(config.get("proxy.port"), (Class)int.class, PROPERTYDEFAULT_PROXY_PORT);
/* 131 */     this.username = PropertiesUtils.<String>to(config.get("proxy.user"), String.class, "");
/* 132 */     this.password = PropertiesUtils.<String>to(config.get("proxy.password"), String.class, "");
/* 133 */     this.proxyExceptions = PropertiesUtils.<String[]>to(config.get("proxy.exceptions"), (Class)String[].class, PROPERTYDEFAULT_PROXY_EXCEPTIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 138 */     return String.format("ProxyConfiguration [enabled=%s, hostname=%s, port=%s, username=%s, password=%s, proxyExceptions=%s]", new Object[] { this.enabled, this.hostname, this.port, this.username, this.password, Arrays.asList(this.proxyExceptions) });
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/OSGiProxyConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */