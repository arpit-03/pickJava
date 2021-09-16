/*     */ package org.apache.http.impl.nio.reactor;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.Channel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.nio.reactor.IOSession;
/*     */ import org.apache.http.nio.reactor.SessionRequest;
/*     */ import org.apache.http.nio.reactor.SessionRequestCallback;
/*     */ import org.apache.http.util.Args;
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
/*     */ @Contract(threading = ThreadingBehavior.SAFE_CONDITIONAL)
/*     */ public class SessionRequestImpl
/*     */   implements SessionRequest
/*     */ {
/*     */   private final SocketAddress remoteAddress;
/*     */   private final SocketAddress localAddress;
/*     */   private final Object attachment;
/*     */   private final SessionRequestCallback callback;
/*     */   private final AtomicReference<SessionRequestState> state;
/*     */   private volatile SelectionKey key;
/*     */   private volatile int connectTimeout;
/*     */   
/*     */   enum SessionRequestState
/*     */   {
/*  52 */     ACTIVE,
/*  53 */     SUCCESSFUL,
/*  54 */     TIMEDOUT,
/*  55 */     CANCELLED,
/*  56 */     FAILED;
/*     */   }
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
/*  68 */   private volatile IOSession session = null;
/*  69 */   private volatile IOException exception = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SessionRequestImpl(SocketAddress remoteAddress, SocketAddress localAddress, Object attachment, SessionRequestCallback callback) {
/*  77 */     Args.notNull(remoteAddress, "Remote address");
/*  78 */     this.remoteAddress = remoteAddress;
/*  79 */     this.localAddress = localAddress;
/*  80 */     this.attachment = attachment;
/*  81 */     this.callback = callback;
/*  82 */     this.state = new AtomicReference<SessionRequestState>(SessionRequestState.ACTIVE);
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/*  87 */     return this.remoteAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress getLocalAddress() {
/*  92 */     return this.localAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttachment() {
/*  97 */     return this.attachment;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/* 102 */     return (((SessionRequestState)this.state.get()).compareTo(SessionRequestState.ACTIVE) != 0);
/*     */   }
/*     */   
/*     */   boolean isTerminated() {
/* 106 */     return (((SessionRequestState)this.state.get()).compareTo(SessionRequestState.SUCCESSFUL) > 0);
/*     */   }
/*     */   
/*     */   protected void setKey(SelectionKey key) {
/* 110 */     this.key = key;
/* 111 */     if (isCompleted()) {
/* 112 */       key.cancel();
/* 113 */       Channel channel = key.channel();
/* 114 */       if (channel.isOpen()) {
/*     */         try {
/* 116 */           channel.close();
/* 117 */         } catch (IOException ignore) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void waitFor() throws InterruptedException {
/* 124 */     if (isCompleted()) {
/*     */       return;
/*     */     }
/* 127 */     synchronized (this) {
/* 128 */       while (!isCompleted()) {
/* 129 */         wait();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IOSession getSession() {
/* 136 */     synchronized (this) {
/* 137 */       return this.session;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IOException getException() {
/* 143 */     synchronized (this) {
/* 144 */       return this.exception;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void completed(IOSession session) {
/* 149 */     Args.notNull(session, "Session");
/* 150 */     if (this.state.compareAndSet(SessionRequestState.ACTIVE, SessionRequestState.SUCCESSFUL)) {
/* 151 */       synchronized (this) {
/* 152 */         this.session = session;
/* 153 */         if (this.callback != null) {
/* 154 */           this.callback.completed(this);
/*     */         }
/* 156 */         notifyAll();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void failed(IOException exception) {
/* 162 */     if (exception == null) {
/*     */       return;
/*     */     }
/* 165 */     if (this.state.compareAndSet(SessionRequestState.ACTIVE, SessionRequestState.FAILED)) {
/* 166 */       SelectionKey key = this.key;
/* 167 */       if (key != null) {
/* 168 */         key.cancel();
/* 169 */         Channel channel = key.channel();
/*     */         try {
/* 171 */           channel.close();
/* 172 */         } catch (IOException ignore) {}
/*     */       } 
/* 174 */       synchronized (this) {
/* 175 */         this.exception = exception;
/* 176 */         if (this.callback != null) {
/* 177 */           this.callback.failed(this);
/*     */         }
/* 179 */         notifyAll();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void timeout() {
/* 185 */     if (this.state.compareAndSet(SessionRequestState.ACTIVE, SessionRequestState.TIMEDOUT)) {
/* 186 */       SelectionKey key = this.key;
/* 187 */       if (key != null) {
/* 188 */         key.cancel();
/* 189 */         Channel channel = key.channel();
/* 190 */         if (channel.isOpen()) {
/*     */           try {
/* 192 */             channel.close();
/* 193 */           } catch (IOException ignore) {}
/*     */         }
/*     */       } 
/* 196 */       synchronized (this) {
/* 197 */         if (this.callback != null) {
/* 198 */           this.callback.timeout(this);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getConnectTimeout() {
/* 206 */     return this.connectTimeout;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setConnectTimeout(int timeout) {
/* 211 */     if (this.connectTimeout != timeout) {
/* 212 */       this.connectTimeout = timeout;
/* 213 */       SelectionKey key = this.key;
/* 214 */       if (key != null) {
/* 215 */         key.selector().wakeup();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 222 */     if (this.state.compareAndSet(SessionRequestState.ACTIVE, SessionRequestState.CANCELLED)) {
/* 223 */       SelectionKey key = this.key;
/* 224 */       if (key != null) {
/* 225 */         key.cancel();
/* 226 */         Channel channel = key.channel();
/* 227 */         if (channel.isOpen()) {
/*     */           try {
/* 229 */             channel.close();
/* 230 */           } catch (IOException ignore) {}
/*     */         }
/*     */       } 
/* 233 */       synchronized (this) {
/* 234 */         if (this.callback != null) {
/* 235 */           this.callback.cancelled(this);
/*     */         }
/* 237 */         notifyAll();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/reactor/SessionRequestImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */