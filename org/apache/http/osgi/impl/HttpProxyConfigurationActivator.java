/*     */ package org.apache.http.osgi.impl;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.Dictionary;
/*     */ import java.util.Hashtable;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.osgi.services.CachingHttpClientBuilderFactory;
/*     */ import org.apache.http.osgi.services.HttpClientBuilderFactory;
/*     */ import org.apache.http.osgi.services.ProxyConfiguration;
/*     */ import org.osgi.framework.BundleActivator;
/*     */ import org.osgi.framework.BundleContext;
/*     */ import org.osgi.framework.ServiceReference;
/*     */ import org.osgi.framework.ServiceRegistration;
/*     */ import org.osgi.service.cm.ConfigurationException;
/*     */ import org.osgi.service.cm.ManagedServiceFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpProxyConfigurationActivator
/*     */   implements BundleActivator, ManagedServiceFactory
/*     */ {
/*     */   private static final String PROXY_SERVICE_FACTORY_NAME = "Apache HTTP Client Proxy Configuration Factory";
/*     */   private static final String PROXY_SERVICE_PID = "org.apache.http.proxyconfigurator";
/*     */   private static final String BUILDER_FACTORY_SERVICE_NAME = "Apache HTTP Client Client Factory";
/*     */   private static final String BUILDER_FACTORY_SERVICE_PID = "org.apache.http.httpclientfactory";
/*     */   private static final String CACHEABLE_BUILDER_FACTORY_SERVICE_NAME = "Apache HTTP Client Caching Client Factory";
/*     */   private static final String CACHEABLE_BUILDER_FACTORY_SERVICE_PID = "org.apache.http.cachinghttpclientfactory";
/*     */   private ServiceRegistration configurator;
/*     */   private ServiceRegistration clientFactory;
/*     */   private ServiceRegistration cachingClientFactory;
/*     */   private BundleContext context;
/*  75 */   private final Map<String, ServiceRegistration> registeredConfigurations = new LinkedHashMap<String, ServiceRegistration>();
/*     */   
/*  77 */   private final List<ProxyConfiguration> proxyConfigurations = new CopyOnWriteArrayList<ProxyConfiguration>();
/*     */   
/*  79 */   private final HttpClientTracker httpClientTracker = new HttpClientTracker();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(BundleContext context) throws Exception {
/*  86 */     this.context = context;
/*     */ 
/*     */     
/*  89 */     Hashtable<String, Object> props = new Hashtable<String, Object>();
/*  90 */     props.put("service.pid", getName());
/*  91 */     props.put("service.vendor", context.getBundle().getHeaders().get("Bundle-Vendor"));
/*  92 */     props.put("service.description", "Apache HTTP Client Proxy Configuration Factory");
/*     */     
/*  94 */     this.configurator = context.registerService(ManagedServiceFactory.class.getName(), this, props);
/*     */     
/*  96 */     HttpClientBuilderConfigurator configurator = new HttpClientBuilderConfigurator(this.proxyConfigurations);
/*     */     
/*  98 */     props.clear();
/*  99 */     props.put("service.pid", "org.apache.http.httpclientfactory");
/* 100 */     props.put("service.vendor", context.getBundle().getHeaders().get("Bundle-Vendor"));
/* 101 */     props.put("service.description", "Apache HTTP Client Client Factory");
/* 102 */     this.clientFactory = context.registerService(HttpClientBuilderFactory.class.getName(), new OSGiHttpClientBuilderFactory(configurator, this.httpClientTracker), props);
/*     */ 
/*     */ 
/*     */     
/* 106 */     props.clear();
/* 107 */     props.put("service.pid", "org.apache.http.cachinghttpclientfactory");
/* 108 */     props.put("service.vendor", context.getBundle().getHeaders().get("Bundle-Vendor"));
/* 109 */     props.put("service.description", "Apache HTTP Client Caching Client Factory");
/* 110 */     this.cachingClientFactory = context.registerService(CachingHttpClientBuilderFactory.class.getName(), new OSGiCachingHttpClientBuilderFactory(configurator, this.httpClientTracker), props);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop(BundleContext context) throws Exception {
/* 121 */     for (ServiceRegistration registeredConfiguration : this.registeredConfigurations.values()) {
/* 122 */       registeredConfiguration.unregister();
/*     */     }
/*     */     
/* 125 */     this.registeredConfigurations.clear();
/*     */ 
/*     */     
/* 128 */     if (this.configurator != null) {
/* 129 */       this.configurator.unregister();
/*     */     }
/*     */     
/* 132 */     if (this.clientFactory != null) {
/* 133 */       this.clientFactory.unregister();
/*     */     }
/*     */     
/* 136 */     if (this.cachingClientFactory != null) {
/* 137 */       this.cachingClientFactory.unregister();
/*     */     }
/*     */ 
/*     */     
/* 141 */     this.httpClientTracker.closeAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 149 */     return "org.apache.http.proxyconfigurator";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updated(String pid, Dictionary<String, Object> config) throws ConfigurationException {
/*     */     OSGiProxyConfiguration proxyConfiguration;
/* 157 */     ServiceRegistration registration = this.registeredConfigurations.get(pid);
/*     */ 
/*     */     
/* 160 */     if (registration == null) {
/* 161 */       proxyConfiguration = new OSGiProxyConfiguration();
/* 162 */       ServiceRegistration configurationRegistration = this.context.registerService(ProxyConfiguration.class.getName(), proxyConfiguration, config);
/*     */ 
/*     */       
/* 165 */       this.registeredConfigurations.put(pid, configurationRegistration);
/* 166 */       this.proxyConfigurations.add(proxyConfiguration);
/*     */     } else {
/* 168 */       proxyConfiguration = (OSGiProxyConfiguration)this.context.getService(registration.getReference());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 173 */     Dictionary<String, Object> properties = config;
/* 174 */     proxyConfiguration.update(properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleted(String pid) {
/* 182 */     ServiceRegistration registration = this.registeredConfigurations.remove(pid);
/* 183 */     if (registration != null) {
/* 184 */       ServiceReference ref = registration.getReference();
/* 185 */       ProxyConfiguration config = (ProxyConfiguration)this.context.getService(ref);
/* 186 */       this.proxyConfigurations.remove(config);
/* 187 */       this.context.ungetService(ref);
/* 188 */       registration.unregister();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void closeQuietly(Closeable closeable) {
/* 193 */     if (closeable != null) {
/*     */       try {
/* 195 */         closeable.close();
/* 196 */       } catch (IOException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class HttpClientTracker
/*     */   {
/* 204 */     private final List<CloseableHttpClient> trackedHttpClients = new WeakList<CloseableHttpClient>();
/*     */     
/*     */     synchronized void track(CloseableHttpClient client) {
/* 207 */       this.trackedHttpClients.add(client);
/*     */     }
/*     */     
/*     */     synchronized void closeAll() {
/* 211 */       for (CloseableHttpClient client : this.trackedHttpClients) {
/* 212 */         HttpProxyConfigurationActivator.closeQuietly((Closeable)client);
/*     */       }
/* 214 */       this.trackedHttpClients.clear();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/HttpProxyConfigurationActivator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */