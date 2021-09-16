/*     */ package org.apache.http.client.fluent;
/*     */ 
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Future;
/*     */ import org.apache.http.client.ResponseHandler;
/*     */ import org.apache.http.concurrent.BasicFuture;
/*     */ import org.apache.http.concurrent.FutureCallback;
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
/*     */ public class Async
/*     */ {
/*     */   private Executor executor;
/*     */   private Executor concurrentExec;
/*     */   
/*     */   public static Async newInstance() {
/*  41 */     return new Async();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Async use(Executor executor) {
/*  49 */     this.executor = executor;
/*  50 */     return this;
/*     */   }
/*     */   
/*     */   public Async use(Executor concurrentExec) {
/*  54 */     this.concurrentExec = concurrentExec;
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   static class ExecRunnable<T>
/*     */     implements Runnable
/*     */   {
/*     */     private final BasicFuture<T> future;
/*     */     
/*     */     private final Request request;
/*     */     
/*     */     private final Executor executor;
/*     */     
/*     */     private final ResponseHandler<T> handler;
/*     */     
/*     */     ExecRunnable(BasicFuture<T> future, Request request, Executor executor, ResponseHandler<T> handler) {
/*  71 */       this.future = future;
/*  72 */       this.request = request;
/*  73 */       this.executor = executor;
/*  74 */       this.handler = handler;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/*  80 */         Response response = this.executor.execute(this.request);
/*  81 */         T result = response.handleResponse(this.handler);
/*  82 */         this.future.completed(result);
/*  83 */       } catch (Exception ex) {
/*  84 */         this.future.failed(ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Future<T> execute(Request request, ResponseHandler<T> handler, FutureCallback<T> callback) {
/*  92 */     BasicFuture<T> future = new BasicFuture(callback);
/*  93 */     ExecRunnable<T> runnable = new ExecRunnable<T>(future, request, (this.executor != null) ? this.executor : Executor.newInstance(), handler);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     if (this.concurrentExec != null) {
/*  99 */       this.concurrentExec.execute(runnable);
/*     */     } else {
/* 101 */       Thread t = new Thread(runnable);
/* 102 */       t.setDaemon(true);
/* 103 */       t.start();
/*     */     } 
/* 105 */     return (Future<T>)future;
/*     */   }
/*     */   
/*     */   public <T> Future<T> execute(Request request, ResponseHandler<T> handler) {
/* 109 */     return execute(request, handler, null);
/*     */   }
/*     */   
/*     */   public Future<Content> execute(Request request, FutureCallback<Content> callback) {
/* 113 */     return execute(request, (ResponseHandler<Content>)new ContentResponseHandler(), callback);
/*     */   }
/*     */   
/*     */   public Future<Content> execute(Request request) {
/* 117 */     return execute(request, (ResponseHandler<Content>)new ContentResponseHandler(), null);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/Async.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */