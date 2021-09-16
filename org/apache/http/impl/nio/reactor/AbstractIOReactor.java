/*     */ package org.apache.http.impl.nio.reactor;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.ClosedSelectorException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.apache.http.nio.reactor.IOReactor;
/*     */ import org.apache.http.nio.reactor.IOReactorException;
/*     */ import org.apache.http.nio.reactor.IOReactorStatus;
/*     */ import org.apache.http.nio.reactor.IOSession;
/*     */ import org.apache.http.util.Args;
/*     */ import org.apache.http.util.Asserts;
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
/*     */ 
/*     */ public abstract class AbstractIOReactor
/*     */   implements IOReactor
/*     */ {
/*     */   private volatile IOReactorStatus status;
/*     */   private final Object statusMutex;
/*     */   private final long selectTimeout;
/*     */   private final boolean interestOpsQueueing;
/*     */   private final Selector selector;
/*     */   private final Set<IOSession> sessions;
/*     */   private final Queue<InterestOpEntry> interestOpsQueue;
/*     */   private final Queue<IOSession> closedSessions;
/*     */   private final Queue<ChannelEntry> newChannels;
/*     */   
/*     */   public AbstractIOReactor(long selectTimeout) throws IOReactorException {
/*  78 */     this(selectTimeout, false);
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
/*     */   public AbstractIOReactor(long selectTimeout, boolean interestOpsQueueing) throws IOReactorException {
/*  93 */     Args.positive(selectTimeout, "Select timeout");
/*  94 */     this.selectTimeout = selectTimeout;
/*  95 */     this.interestOpsQueueing = interestOpsQueueing;
/*  96 */     this.sessions = Collections.synchronizedSet(new HashSet<IOSession>());
/*  97 */     this.interestOpsQueue = new ConcurrentLinkedQueue<InterestOpEntry>();
/*  98 */     this.closedSessions = new ConcurrentLinkedQueue<IOSession>();
/*  99 */     this.newChannels = new ConcurrentLinkedQueue<ChannelEntry>();
/*     */     try {
/* 101 */       this.selector = Selector.open();
/* 102 */     } catch (IOException ex) {
/* 103 */       throw new IOReactorException("Failure opening selector", ex);
/*     */     } 
/* 105 */     this.statusMutex = new Object();
/* 106 */     this.status = IOReactorStatus.INACTIVE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sessionCreated(SelectionKey key, IOSession session) {}
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
/*     */   protected void sessionClosed(IOSession session) {}
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
/*     */   protected void sessionTimedOut(IOSession session) {}
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
/*     */   protected IOSession getSession(SelectionKey key) {
/* 196 */     return (IOSession)key.attachment();
/*     */   }
/*     */ 
/*     */   
/*     */   public IOReactorStatus getStatus() {
/* 201 */     return this.status;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getInterestOpsQueueing() {
/* 210 */     return this.interestOpsQueueing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChannel(ChannelEntry channelEntry) {
/* 220 */     Args.notNull(channelEntry, "Channel entry");
/* 221 */     this.newChannels.add(channelEntry);
/* 222 */     this.selector.wakeup();
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
/*     */   protected void execute() throws InterruptedIOException, IOReactorException {
/* 248 */     this.status = IOReactorStatus.ACTIVE;
/*     */ 
/*     */     
/*     */     try { while (true) {
/*     */         int readyCount;
/*     */         
/*     */         try {
/* 255 */           readyCount = this.selector.select(this.selectTimeout);
/* 256 */         } catch (InterruptedIOException ex) {
/* 257 */           throw ex;
/* 258 */         } catch (IOException ex) {
/* 259 */           throw new IOReactorException("Unexpected selector failure", ex);
/*     */         } 
/*     */         
/* 262 */         if (this.status == IOReactorStatus.SHUT_DOWN) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 267 */         if (this.status == IOReactorStatus.SHUTTING_DOWN) {
/*     */ 
/*     */           
/* 270 */           closeSessions();
/* 271 */           closeNewChannels();
/*     */         } 
/*     */ 
/*     */         
/* 275 */         if (readyCount > 0) {
/* 276 */           processEvents(this.selector.selectedKeys());
/*     */         }
/*     */ 
/*     */         
/* 280 */         validate(this.selector.keys());
/*     */ 
/*     */         
/* 283 */         processClosedSessions();
/*     */ 
/*     */         
/* 286 */         if (this.status == IOReactorStatus.ACTIVE) {
/* 287 */           processNewChannels();
/*     */         }
/*     */ 
/*     */         
/* 291 */         if (this.status.compareTo((Enum)IOReactorStatus.ACTIVE) > 0 && this.sessions.isEmpty()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 296 */         if (this.interestOpsQueueing)
/*     */         {
/* 298 */           processPendingInterestOps();
/*     */         }
/*     */       }
/*     */        }
/*     */     
/* 303 */     catch (ClosedSelectorException ignore) {  }
/*     */     finally
/* 305 */     { hardShutdown();
/* 306 */       synchronized (this.statusMutex) {
/* 307 */         this.statusMutex.notifyAll();
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   private void processEvents(Set<SelectionKey> selectedKeys) {
/* 313 */     for (SelectionKey key : selectedKeys)
/*     */     {
/* 315 */       processEvent(key);
/*     */     }
/*     */     
/* 318 */     selectedKeys.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processEvent(SelectionKey key) {
/* 327 */     IOSessionImpl session = (IOSessionImpl)key.attachment();
/*     */     try {
/* 329 */       if (key.isAcceptable()) {
/* 330 */         acceptable(key);
/*     */       }
/* 332 */       if (key.isConnectable()) {
/* 333 */         connectable(key);
/*     */       }
/* 335 */       if (key.isReadable()) {
/* 336 */         session.resetLastRead();
/* 337 */         readable(key);
/*     */       } 
/* 339 */       if (key.isWritable()) {
/* 340 */         session.resetLastWrite();
/* 341 */         writable(key);
/*     */       } 
/* 343 */     } catch (CancelledKeyException ex) {
/* 344 */       queueClosedSession(session);
/* 345 */       key.attach(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void queueClosedSession(IOSession session) {
/* 355 */     if (session != null) {
/* 356 */       this.closedSessions.add(session);
/*     */     }
/*     */   }
/*     */   
/*     */   private void processNewChannels() throws IOReactorException {
/*     */     ChannelEntry entry;
/* 362 */     while ((entry = this.newChannels.poll()) != null) {
/*     */       SocketChannel channel;
/*     */       SelectionKey key;
/*     */       IOSession session;
/*     */       try {
/* 367 */         channel = entry.getChannel();
/* 368 */         channel.configureBlocking(false);
/* 369 */         key = channel.register(this.selector, 1);
/* 370 */       } catch (ClosedChannelException ex) {
/* 371 */         SessionRequestImpl sessionRequest = entry.getSessionRequest();
/* 372 */         if (sessionRequest != null) {
/* 373 */           sessionRequest.failed(ex);
/*     */         }
/*     */         
/*     */         return;
/* 377 */       } catch (IOException ex) {
/* 378 */         throw new IOReactorException("Failure registering channel with the selector", ex);
/*     */       } 
/*     */ 
/*     */       
/* 382 */       SessionClosedCallback sessionClosedCallback = new SessionClosedCallback()
/*     */         {
/*     */           public void sessionClosed(IOSession session)
/*     */           {
/* 386 */             AbstractIOReactor.this.queueClosedSession(session);
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 391 */       InterestOpsCallback interestOpsCallback = null;
/* 392 */       if (this.interestOpsQueueing) {
/* 393 */         interestOpsCallback = new InterestOpsCallback()
/*     */           {
/*     */             public void addInterestOps(InterestOpEntry entry)
/*     */             {
/* 397 */               AbstractIOReactor.this.queueInterestOps(entry);
/*     */             }
/*     */           };
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 405 */         session = new IOSessionImpl(key, interestOpsCallback, sessionClosedCallback);
/* 406 */         int timeout = 0;
/*     */         try {
/* 408 */           timeout = channel.socket().getSoTimeout();
/* 409 */         } catch (IOException ex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 415 */         session.setAttribute("http.session.attachment", entry.getAttachment());
/* 416 */         session.setSocketTimeout(timeout);
/* 417 */       } catch (CancelledKeyException ex) {
/*     */         continue;
/*     */       } 
/*     */       try {
/* 421 */         this.sessions.add(session);
/* 422 */         key.attach(session);
/* 423 */         SessionRequestImpl sessionRequest = entry.getSessionRequest();
/* 424 */         if (sessionRequest != null) {
/* 425 */           if (!sessionRequest.isTerminated()) {
/* 426 */             sessionRequest.completed(session);
/*     */           }
/* 428 */           if (!sessionRequest.isTerminated() && !session.isClosed()) {
/* 429 */             sessionCreated(key, session);
/*     */           }
/* 431 */           if (sessionRequest.isTerminated())
/* 432 */             throw new CancelledKeyException(); 
/*     */           continue;
/*     */         } 
/* 435 */         sessionCreated(key, session);
/*     */       }
/* 437 */       catch (CancelledKeyException ex) {
/* 438 */         session.close();
/* 439 */         key.attach(null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processClosedSessions() {
/*     */     IOSession session;
/* 446 */     while ((session = this.closedSessions.poll()) != null) {
/* 447 */       if (this.sessions.remove(session)) {
/*     */         try {
/* 449 */           sessionClosed(session);
/* 450 */         } catch (CancelledKeyException ex) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processPendingInterestOps() {
/* 459 */     if (!this.interestOpsQueueing) {
/*     */       return;
/*     */     }
/*     */     InterestOpEntry entry;
/* 463 */     while ((entry = this.interestOpsQueue.poll()) != null) {
/*     */       
/* 465 */       SelectionKey key = entry.getSelectionKey();
/* 466 */       int eventMask = entry.getEventMask();
/* 467 */       if (key.isValid()) {
/* 468 */         key.interestOps(eventMask);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean queueInterestOps(InterestOpEntry entry) {
/* 475 */     Asserts.check(this.interestOpsQueueing, "Interest ops queueing not enabled");
/* 476 */     if (entry == null) {
/* 477 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 481 */     this.interestOpsQueue.add(entry);
/*     */     
/* 483 */     return true;
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
/*     */   protected void timeoutCheck(SelectionKey key, long now) {
/* 496 */     IOSessionImpl session = (IOSessionImpl)key.attachment();
/* 497 */     if (session != null) {
/* 498 */       int timeout = session.getSocketTimeout();
/* 499 */       if (timeout > 0 && 
/* 500 */         session.getLastAccessTime() + timeout < now) {
/*     */         try {
/* 502 */           sessionTimedOut(session);
/* 503 */         } catch (CancelledKeyException ex) {
/* 504 */           session.close();
/* 505 */           key.attach(null);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeSessions() {
/* 516 */     synchronized (this.sessions) {
/* 517 */       for (IOSession session : this.sessions) {
/* 518 */         session.close();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeNewChannels() throws IOReactorException {
/*     */     ChannelEntry entry;
/* 530 */     while ((entry = this.newChannels.poll()) != null) {
/* 531 */       SessionRequestImpl sessionRequest = entry.getSessionRequest();
/* 532 */       if (sessionRequest != null) {
/* 533 */         sessionRequest.cancel();
/*     */       }
/* 535 */       SocketChannel channel = entry.getChannel();
/*     */       try {
/* 537 */         channel.close();
/* 538 */       } catch (IOException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeActiveChannels() throws IOReactorException {
/*     */     try {
/* 550 */       Set<SelectionKey> keys = this.selector.keys();
/* 551 */       for (SelectionKey key : keys) {
/* 552 */         IOSession session = getSession(key);
/* 553 */         if (session != null) {
/* 554 */           session.close();
/*     */         }
/*     */       } 
/* 557 */       this.selector.close();
/* 558 */     } catch (IOException ignore) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gracefulShutdown() {
/* 566 */     synchronized (this.statusMutex) {
/* 567 */       if (this.status != IOReactorStatus.ACTIVE) {
/*     */         return;
/*     */       }
/*     */       
/* 571 */       this.status = IOReactorStatus.SHUTTING_DOWN;
/*     */     } 
/* 573 */     this.selector.wakeup();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void hardShutdown() throws IOReactorException {
/* 580 */     synchronized (this.statusMutex) {
/* 581 */       if (this.status == IOReactorStatus.SHUT_DOWN) {
/*     */         return;
/*     */       }
/*     */       
/* 585 */       this.status = IOReactorStatus.SHUT_DOWN;
/*     */     } 
/*     */     
/* 588 */     closeNewChannels();
/* 589 */     closeActiveChannels();
/* 590 */     processClosedSessions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void awaitShutdown(long timeout) throws InterruptedException {
/* 601 */     synchronized (this.statusMutex) {
/* 602 */       long deadline = System.currentTimeMillis() + timeout;
/* 603 */       long remaining = timeout;
/* 604 */       while (this.status != IOReactorStatus.SHUT_DOWN) {
/* 605 */         this.statusMutex.wait(remaining);
/* 606 */         if (timeout > 0L) {
/* 607 */           remaining = deadline - System.currentTimeMillis();
/* 608 */           if (remaining <= 0L) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown(long gracePeriod) throws IOReactorException {
/* 618 */     if (this.status != IOReactorStatus.INACTIVE) {
/* 619 */       gracefulShutdown();
/*     */       try {
/* 621 */         awaitShutdown(gracePeriod);
/* 622 */       } catch (InterruptedException ignore) {}
/*     */     } 
/*     */     
/* 625 */     if (this.status != IOReactorStatus.SHUT_DOWN) {
/* 626 */       hardShutdown();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() throws IOReactorException {
/* 632 */     shutdown(1000L);
/*     */   }
/*     */   
/*     */   protected abstract void acceptable(SelectionKey paramSelectionKey);
/*     */   
/*     */   protected abstract void connectable(SelectionKey paramSelectionKey);
/*     */   
/*     */   protected abstract void readable(SelectionKey paramSelectionKey);
/*     */   
/*     */   protected abstract void writable(SelectionKey paramSelectionKey);
/*     */   
/*     */   protected abstract void validate(Set<SelectionKey> paramSet);
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/reactor/AbstractIOReactor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */