/*    */ package org.apache.http.osgi.impl;
/*    */ 
/*    */ import org.apache.http.impl.client.CloseableHttpClient;
/*    */ import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
/*    */ import org.apache.http.osgi.services.CachingHttpClientBuilderFactory;
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
/*    */ final class OSGiCachingHttpClientBuilderFactory
/*    */   implements CachingHttpClientBuilderFactory
/*    */ {
/*    */   private final HttpClientBuilderConfigurator configurator;
/*    */   private final HttpProxyConfigurationActivator.HttpClientTracker httpClientTracker;
/*    */   
/*    */   OSGiCachingHttpClientBuilderFactory(HttpClientBuilderConfigurator configurator, HttpProxyConfigurationActivator.HttpClientTracker httpClientTracker) {
/* 42 */     this.configurator = configurator;
/* 43 */     this.httpClientTracker = httpClientTracker;
/*    */   }
/*    */ 
/*    */   
/*    */   public CachingHttpClientBuilder newBuilder() {
/* 48 */     return this.configurator.<CachingHttpClientBuilder>configure(new CachingHttpClientBuilder()
/*    */         {
/*    */           public CloseableHttpClient build() {
/* 51 */             CloseableHttpClient client = super.build();
/* 52 */             OSGiCachingHttpClientBuilderFactory.this.httpClientTracker.track(client);
/* 53 */             return client;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/OSGiCachingHttpClientBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */