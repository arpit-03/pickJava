/*    */ package org.apache.http.osgi.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.http.impl.client.CloseableHttpClient;
/*    */ import org.apache.http.impl.client.HttpClientBuilder;
/*    */ import org.apache.http.osgi.services.HttpClientBuilderFactory;
/*    */ import org.osgi.framework.BundleContext;
/*    */ import org.osgi.framework.ServiceRegistration;
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
/*    */ @Deprecated
/*    */ public final class OSGiClientBuilderFactory
/*    */   implements HttpClientBuilderFactory
/*    */ {
/*    */   private final BundleContext bundleContext;
/*    */   private final Map<String, ServiceRegistration> registeredConfigurations;
/*    */   private final List<CloseableHttpClient> trackedHttpClients;
/*    */   
/*    */   public OSGiClientBuilderFactory(BundleContext bundleContext, Map<String, ServiceRegistration> registeredConfigurations, List<CloseableHttpClient> trackedHttpClients) {
/* 56 */     this.bundleContext = bundleContext;
/* 57 */     this.registeredConfigurations = registeredConfigurations;
/* 58 */     this.trackedHttpClients = trackedHttpClients;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpClientBuilder newBuilder() {
/* 63 */     return new HttpClientBuilder()
/*    */       {
/*    */         public CloseableHttpClient build() {
/* 66 */           CloseableHttpClient httpClient = super.build();
/* 67 */           synchronized (OSGiClientBuilderFactory.this.trackedHttpClients) {
/* 68 */             OSGiClientBuilderFactory.this.trackedHttpClients.add(httpClient);
/*    */           } 
/* 70 */           return httpClient;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/OSGiClientBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */