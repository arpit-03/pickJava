/*     */ package org.apache.http.osgi.impl;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.http.HttpException;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.impl.conn.DefaultRoutePlanner;
/*     */ import org.apache.http.osgi.services.ProxyConfiguration;
/*     */ import org.apache.http.protocol.HttpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OSGiHttpRoutePlanner
/*     */   extends DefaultRoutePlanner
/*     */ {
/*     */   private static final String DOT = ".";
/*  51 */   public static final Pattern IP_MASK_PATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*     */ 
/*     */   
/*     */   private final List<ProxyConfiguration> proxyConfigurations;
/*     */ 
/*     */ 
/*     */   
/*     */   public OSGiHttpRoutePlanner(List<ProxyConfiguration> proxyConfigurations) {
/*  59 */     super(null);
/*  60 */     this.proxyConfigurations = proxyConfigurations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HttpHost determineProxy(HttpHost target, HttpRequest request, HttpContext context) throws HttpException {
/*  68 */     HttpHost proxyHost = null;
/*  69 */     for (ProxyConfiguration proxyConfiguration : this.proxyConfigurations) {
/*  70 */       if (proxyConfiguration.isEnabled()) {
/*  71 */         for (String exception : proxyConfiguration.getProxyExceptions()) {
/*  72 */           if (createMatcher(exception).matches(target.getHostName())) {
/*  73 */             return null;
/*     */           }
/*     */         } 
/*  76 */         if (null == proxyHost) {
/*  77 */           proxyHost = new HttpHost(proxyConfiguration.getHostname(), proxyConfiguration.getPort());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     return proxyHost;
/*     */   }
/*     */   
/*     */   private static HostMatcher createMatcher(String name) {
/*  86 */     NetworkAddress na = NetworkAddress.parse(name);
/*  87 */     if (na != null) {
/*  88 */       return new IPAddressMatcher(na);
/*     */     }
/*     */     
/*  91 */     if (name.startsWith(".")) {
/*  92 */       return new DomainNameMatcher(name);
/*     */     }
/*     */     
/*  95 */     return new HostNameMatcher(name);
/*     */   }
/*     */   
/*     */   private static interface HostMatcher
/*     */   {
/*     */     boolean matches(String param1String);
/*     */   }
/*     */   
/*     */   private static class HostNameMatcher
/*     */     implements HostMatcher
/*     */   {
/*     */     private final String hostName;
/*     */     
/*     */     HostNameMatcher(String hostName) {
/* 109 */       this.hostName = hostName;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(String host) {
/* 114 */       return this.hostName.equalsIgnoreCase(host);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class DomainNameMatcher
/*     */     implements HostMatcher {
/*     */     private final String domainName;
/*     */     
/*     */     DomainNameMatcher(String domainName) {
/* 123 */       this.domainName = domainName.toLowerCase(Locale.ROOT);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(String host) {
/* 128 */       return host.toLowerCase(Locale.ROOT).endsWith(this.domainName);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class IPAddressMatcher
/*     */     implements HostMatcher {
/*     */     private final OSGiHttpRoutePlanner.NetworkAddress address;
/*     */     
/*     */     IPAddressMatcher(OSGiHttpRoutePlanner.NetworkAddress address) {
/* 137 */       this.address = address;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean matches(String host) {
/* 142 */       OSGiHttpRoutePlanner.NetworkAddress hostAddress = OSGiHttpRoutePlanner.NetworkAddress.parse(host);
/* 143 */       return (hostAddress != null && this.address.address == (hostAddress.address & this.address.mask));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class NetworkAddress
/*     */   {
/*     */     final int address;
/*     */     
/*     */     final int mask;
/*     */ 
/*     */     
/*     */     static NetworkAddress parse(String adrSpec) {
/* 156 */       if (null != adrSpec) {
/* 157 */         Matcher nameMatcher = OSGiHttpRoutePlanner.IP_MASK_PATTERN.matcher(adrSpec);
/* 158 */         if (nameMatcher.matches()) {
/*     */           try {
/* 160 */             int i1 = toInt(nameMatcher.group(1), 255);
/* 161 */             int i2 = toInt(nameMatcher.group(2), 255);
/* 162 */             int i3 = toInt(nameMatcher.group(3), 255);
/* 163 */             int i4 = toInt(nameMatcher.group(4), 255);
/* 164 */             int ip = i1 << 24 | i2 << 16 | i3 << 8 | i4;
/*     */             
/* 166 */             int mask = toInt(nameMatcher.group(4), 32);
/* 167 */             mask = (mask == 32) ? -1 : (-1 - (-1 >>> mask));
/*     */             
/* 169 */             return new NetworkAddress(ip, mask);
/* 170 */           } catch (NumberFormatException nfe) {}
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 176 */       return null;
/*     */     }
/*     */     
/*     */     private static int toInt(String value, int max) {
/* 180 */       if (value == null || value.isEmpty()) {
/* 181 */         return max;
/*     */       }
/*     */       
/* 184 */       int number = Integer.parseInt(value);
/* 185 */       if (number > max) {
/* 186 */         number = max;
/*     */       }
/* 188 */       return number;
/*     */     }
/*     */     
/*     */     NetworkAddress(int address, int mask) {
/* 192 */       this.address = address;
/* 193 */       this.mask = mask;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/OSGiHttpRoutePlanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */