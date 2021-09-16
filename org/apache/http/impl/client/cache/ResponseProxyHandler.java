/*    */ package org.apache.http.impl.client.cache;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import org.apache.http.HttpResponse;
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
/*    */ class ResponseProxyHandler
/*    */   implements InvocationHandler
/*    */ {
/*    */   private static final Method CLOSE_METHOD;
/*    */   private final HttpResponse original;
/*    */   
/*    */   static {
/*    */     try {
/* 50 */       CLOSE_METHOD = Closeable.class.getMethod("close", new Class[0]);
/* 51 */     } catch (NoSuchMethodException ex) {
/* 52 */       throw new Error(ex);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ResponseProxyHandler(HttpResponse original) {
/* 60 */     this.original = original;
/*    */   }
/*    */   
/*    */   public void close() throws IOException {
/* 64 */     IOUtils.consume(this.original.getEntity());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/* 70 */     if (method.equals(CLOSE_METHOD)) {
/* 71 */       close();
/* 72 */       return null;
/*    */     } 
/*    */     try {
/* 75 */       return method.invoke(this.original, args);
/* 76 */     } catch (InvocationTargetException ex) {
/* 77 */       Throwable cause = ex.getCause();
/* 78 */       if (cause != null) {
/* 79 */         throw cause;
/*    */       }
/* 81 */       throw ex;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ResponseProxyHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */