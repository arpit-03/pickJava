/*     */ package org.apache.http.impl.nio.reactor;
/*     */ 
/*     */ import java.io.InterruptedIOException;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.apache.http.nio.reactor.IOEventDispatch;
/*     */ import org.apache.http.nio.reactor.IOReactorException;
/*     */ import org.apache.http.nio.reactor.IOReactorExceptionHandler;
/*     */ import org.apache.http.nio.reactor.IOSession;
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
/*     */ public class BaseIOReactor
/*     */   extends AbstractIOReactor
/*     */ {
/*     */   private final long timeoutCheckInterval;
/*     */   private final Set<IOSession> bufferingSessions;
/*     */   private long lastTimeoutCheck;
/*  60 */   private IOReactorExceptionHandler exceptionHandler = null;
/*  61 */   private IOEventDispatch eventDispatch = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseIOReactor(long selectTimeout) throws IOReactorException {
/*  70 */     this(selectTimeout, false);
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
/*     */ 
/*     */   
/*     */   public BaseIOReactor(long selectTimeout, boolean interestOpsQueueing) throws IOReactorException {
/*  85 */     super(selectTimeout, interestOpsQueueing);
/*  86 */     this.bufferingSessions = new HashSet<IOSession>();
/*  87 */     this.timeoutCheckInterval = selectTimeout;
/*  88 */     this.lastTimeoutCheck = System.currentTimeMillis();
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
/*     */   
/*     */   public void execute(IOEventDispatch eventDispatch) throws InterruptedIOException, IOReactorException {
/* 102 */     Args.notNull(eventDispatch, "Event dispatcher");
/* 103 */     this.eventDispatch = eventDispatch;
/* 104 */     execute();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExceptionHandler(IOReactorExceptionHandler exceptionHandler) {
/* 113 */     this.exceptionHandler = exceptionHandler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleRuntimeException(RuntimeException ex) {
/* 124 */     if (this.exceptionHandler == null || !this.exceptionHandler.handle(ex)) {
/* 125 */       throw ex;
/*     */     }
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
/*     */   protected void acceptable(SelectionKey key) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void connectable(SelectionKey key) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readable(SelectionKey key) {
/* 156 */     IOSession session = getSession(key);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 161 */       for (int i = 0; i < 5; i++) {
/* 162 */         this.eventDispatch.inputReady(session);
/* 163 */         if (!session.hasBufferedInput() || (session.getEventMask() & 0x1) == 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */       
/* 168 */       if (session.hasBufferedInput()) {
/* 169 */         this.bufferingSessions.add(session);
/*     */       }
/* 171 */     } catch (CancelledKeyException ex) {
/* 172 */       throw ex;
/* 173 */     } catch (RuntimeException ex) {
/* 174 */       handleRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writable(SelectionKey key) {
/* 185 */     IOSession session = getSession(key);
/*     */     try {
/* 187 */       this.eventDispatch.outputReady(session);
/* 188 */     } catch (CancelledKeyException ex) {
/* 189 */       throw ex;
/* 190 */     } catch (RuntimeException ex) {
/* 191 */       handleRuntimeException(ex);
/*     */     } 
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
/*     */   
/*     */   protected void validate(Set<SelectionKey> keys) {
/* 206 */     long currentTime = System.currentTimeMillis();
/* 207 */     if (currentTime - this.lastTimeoutCheck >= this.timeoutCheckInterval) {
/* 208 */       this.lastTimeoutCheck = currentTime;
/* 209 */       if (keys != null) {
/* 210 */         for (SelectionKey key : keys) {
/* 211 */           timeoutCheck(key, currentTime);
/*     */         }
/*     */       }
/*     */     } 
/* 215 */     if (!this.bufferingSessions.isEmpty()) {
/* 216 */       for (Iterator<IOSession> it = this.bufferingSessions.iterator(); it.hasNext(); ) {
/* 217 */         IOSession session = it.next();
/* 218 */         if (!session.hasBufferedInput()) {
/* 219 */           it.remove();
/*     */           continue;
/*     */         } 
/*     */         try {
/* 223 */           if ((session.getEventMask() & 0x1) > 0) {
/* 224 */             this.eventDispatch.inputReady(session);
/* 225 */             if (!session.hasBufferedInput()) {
/* 226 */               it.remove();
/*     */             }
/*     */           } 
/* 229 */         } catch (CancelledKeyException ex) {
/* 230 */           it.remove();
/* 231 */           session.close();
/* 232 */         } catch (RuntimeException ex) {
/* 233 */           handleRuntimeException(ex);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sessionCreated(SelectionKey key, IOSession session) {
/*     */     try {
/* 246 */       this.eventDispatch.connected(session);
/* 247 */     } catch (CancelledKeyException ex) {
/* 248 */       throw ex;
/* 249 */     } catch (RuntimeException ex) {
/* 250 */       handleRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sessionTimedOut(IOSession session) {
/*     */     try {
/* 261 */       this.eventDispatch.timeout(session);
/* 262 */     } catch (CancelledKeyException ex) {
/* 263 */       throw ex;
/* 264 */     } catch (RuntimeException ex) {
/* 265 */       handleRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sessionClosed(IOSession session) {
/*     */     try {
/* 277 */       this.eventDispatch.disconnected(session);
/* 278 */     } catch (CancelledKeyException ex) {
/*     */     
/* 280 */     } catch (RuntimeException ex) {
/* 281 */       handleRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/reactor/BaseIOReactor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */