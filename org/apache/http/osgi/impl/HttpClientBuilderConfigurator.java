/*    */ package org.apache.http.osgi.impl;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.http.conn.routing.HttpRoutePlanner;
/*    */ import org.apache.http.osgi.services.ProxyConfiguration;
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
/*    */ final class HttpClientBuilderConfigurator
/*    */ {
/*    */   private final OSGiCredentialsProvider credentialsProvider;
/*    */   private final OSGiHttpRoutePlanner routePlanner;
/*    */   
/*    */   HttpClientBuilderConfigurator(List<ProxyConfiguration> proxyConfigurations) {
/* 41 */     this.credentialsProvider = new OSGiCredentialsProvider(proxyConfigurations);
/* 42 */     this.routePlanner = new OSGiHttpRoutePlanner(proxyConfigurations);
/*    */   }
/*    */   
/*    */   <T extends org.apache.http.impl.client.HttpClientBuilder> T configure(T clientBuilder) {
/* 46 */     clientBuilder.setDefaultCredentialsProvider(this.credentialsProvider).setRoutePlanner((HttpRoutePlanner)this.routePlanner);
/*    */ 
/*    */     
/* 49 */     return clientBuilder;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/osgi/impl/HttpClientBuilderConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */