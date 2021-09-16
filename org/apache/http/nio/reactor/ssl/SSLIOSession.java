/*     */ package org.apache.http.nio.reactor.ssl;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ByteChannel;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.nio.reactor.IOSession;
/*     */ import org.apache.http.nio.reactor.SessionBufferStatus;
/*     */ import org.apache.http.nio.reactor.SocketAccessor;
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
/*     */ public class SSLIOSession
/*     */   implements IOSession, SessionBufferStatus, SocketAccessor
/*     */ {
/*     */   public static final String SESSION_KEY = "http.session.ssl";
/*  84 */   private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);
/*     */ 
/*     */   
/*     */   private final IOSession session;
/*     */ 
/*     */   
/*     */   private final SSLEngine sslEngine;
/*     */ 
/*     */   
/*     */   private final SSLBuffer inEncrypted;
/*     */ 
/*     */   
/*     */   private final SSLBuffer outEncrypted;
/*     */ 
/*     */   
/*     */   private final SSLBuffer inPlain;
/*     */   
/*     */   private final InternalByteChannel channel;
/*     */   
/*     */   private final SSLSetupHandler handler;
/*     */   
/*     */   private final AtomicInteger outboundClosedCount;
/*     */   
/*     */   private int appEventMask;
/*     */   
/*     */   private SessionBufferStatus appBufferStatus;
/*     */   
/*     */   private boolean endOfStream;
/*     */   
/*     */   private volatile SSLMode sslMode;
/*     */   
/*     */   private volatile int status;
/*     */   
/*     */   private volatile boolean initialized;
/*     */ 
/*     */   
/*     */   public SSLIOSession(IOSession session, SSLMode sslMode, HttpHost host, SSLContext sslContext, SSLSetupHandler handler) {
/* 121 */     this(session, sslMode, host, sslContext, handler, new PermanentSSLBufferManagementStrategy());
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
/*     */   public SSLIOSession(IOSession session, SSLMode sslMode, HttpHost host, SSLContext sslContext, SSLSetupHandler handler, SSLBufferManagementStrategy bufferManagementStrategy) {
/* 142 */     Args.notNull(session, "IO session");
/* 143 */     Args.notNull(sslContext, "SSL context");
/* 144 */     Args.notNull(bufferManagementStrategy, "Buffer management strategy");
/* 145 */     this.session = session;
/* 146 */     this.sslMode = sslMode;
/* 147 */     this.appEventMask = session.getEventMask();
/* 148 */     this.channel = new InternalByteChannel();
/* 149 */     this.handler = handler;
/*     */ 
/*     */     
/* 152 */     this.session.setBufferStatus(this);
/*     */     
/* 154 */     if (this.sslMode == SSLMode.CLIENT && host != null) {
/* 155 */       this.sslEngine = sslContext.createSSLEngine(host.getHostName(), host.getPort());
/*     */     } else {
/* 157 */       this.sslEngine = sslContext.createSSLEngine();
/*     */     } 
/*     */ 
/*     */     
/* 161 */     int netBuffersize = this.sslEngine.getSession().getPacketBufferSize();
/* 162 */     this.inEncrypted = bufferManagementStrategy.constructBuffer(netBuffersize);
/* 163 */     this.outEncrypted = bufferManagementStrategy.constructBuffer(netBuffersize);
/*     */ 
/*     */     
/* 166 */     int appBuffersize = this.sslEngine.getSession().getApplicationBufferSize();
/* 167 */     this.inPlain = bufferManagementStrategy.constructBuffer(appBuffersize);
/* 168 */     this.outboundClosedCount = new AtomicInteger(0);
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
/*     */   public SSLIOSession(IOSession session, SSLMode sslMode, SSLContext sslContext, SSLSetupHandler handler) {
/* 184 */     this(session, sslMode, null, sslContext, handler);
/*     */   }
/*     */   
/*     */   protected SSLSetupHandler getSSLSetupHandler() {
/* 188 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 196 */     return this.initialized;
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
/*     */   @Deprecated
/*     */   public synchronized void initialize(SSLMode sslMode) throws SSLException {
/* 209 */     this.sslMode = sslMode;
/* 210 */     initialize();
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
/*     */   public synchronized void initialize() throws SSLException {
/* 222 */     Asserts.check(!this.initialized, "SSL I/O session already initialized");
/* 223 */     if (this.status >= 1) {
/*     */       return;
/*     */     }
/* 226 */     switch (this.sslMode) {
/*     */       case NEED_WRAP:
/* 228 */         this.sslEngine.setUseClientMode(true);
/*     */         break;
/*     */       case NEED_UNWRAP:
/* 231 */         this.sslEngine.setUseClientMode(false);
/*     */         break;
/*     */     } 
/* 234 */     if (this.handler != null) {
/*     */       try {
/* 236 */         this.handler.initalize(this.sslEngine);
/* 237 */       } catch (RuntimeException ex) {
/* 238 */         throw convert(ex);
/*     */       } 
/*     */     }
/* 241 */     this.initialized = true;
/* 242 */     this.sslEngine.beginHandshake();
/*     */     
/* 244 */     this.inEncrypted.release();
/* 245 */     this.outEncrypted.release();
/* 246 */     this.inPlain.release();
/*     */     
/* 248 */     doHandshake();
/*     */   }
/*     */   
/*     */   public synchronized SSLSession getSSLSession() {
/* 252 */     return this.sslEngine.getSession();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SSLException convert(RuntimeException ex) {
/* 261 */     Throwable cause = ex.getCause();
/* 262 */     if (cause == null) {
/* 263 */       cause = ex;
/*     */     }
/* 265 */     return new SSLException(cause);
/*     */   }
/*     */   
/*     */   private SSLEngineResult doWrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
/*     */     try {
/* 270 */       return this.sslEngine.wrap(src, dst);
/* 271 */     } catch (RuntimeException ex) {
/* 272 */       throw convert(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private SSLEngineResult doUnwrap(ByteBuffer src, ByteBuffer dst) throws SSLException {
/*     */     try {
/* 278 */       return this.sslEngine.unwrap(src, dst);
/* 279 */     } catch (RuntimeException ex) {
/* 280 */       throw convert(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doRunTask() throws SSLException {
/*     */     try {
/* 286 */       Runnable r = this.sslEngine.getDelegatedTask();
/* 287 */       if (r != null) {
/* 288 */         r.run();
/*     */       }
/* 290 */     } catch (RuntimeException ex) {
/* 291 */       throw convert(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doHandshake() throws SSLException {
/* 296 */     boolean handshaking = true;
/*     */     
/* 298 */     SSLEngineResult result = null;
/* 299 */     while (handshaking) {
/* 300 */       ByteBuffer outEncryptedBuf, inEncryptedBuf, inPlainBuf; SSLEngineResult.HandshakeStatus handshakeStatus = this.sslEngine.getHandshakeStatus();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 305 */       if (handshakeStatus == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && this.outboundClosedCount.get() > 0) {
/* 306 */         handshakeStatus = SSLEngineResult.HandshakeStatus.NEED_WRAP;
/*     */       }
/* 308 */       switch (handshakeStatus) {
/*     */ 
/*     */ 
/*     */         
/*     */         case NEED_WRAP:
/* 313 */           outEncryptedBuf = this.outEncrypted.acquire();
/*     */ 
/*     */           
/* 316 */           result = doWrap(ByteBuffer.allocate(0), outEncryptedBuf);
/*     */           
/* 318 */           if (result.getStatus() != SSLEngineResult.Status.OK || result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_WRAP) {
/* 319 */             handshaking = false;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case NEED_UNWRAP:
/* 326 */           inEncryptedBuf = this.inEncrypted.acquire();
/* 327 */           inPlainBuf = this.inPlain.acquire();
/*     */ 
/*     */           
/* 330 */           inEncryptedBuf.flip();
/*     */           try {
/* 332 */             result = doUnwrap(inEncryptedBuf, inPlainBuf);
/*     */           } finally {
/* 334 */             inEncryptedBuf.compact();
/*     */           } 
/*     */           
/*     */           try {
/* 338 */             if (!inEncryptedBuf.hasRemaining() && result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
/* 339 */               throw new SSLException("Input buffer is full");
/*     */             }
/*     */           } finally {
/*     */             
/* 343 */             if (inEncryptedBuf.position() == 0) {
/* 344 */               this.inEncrypted.release();
/*     */             }
/*     */           } 
/*     */           
/* 348 */           if (this.status >= 1) {
/* 349 */             this.inPlain.release();
/*     */           }
/* 351 */           if (result.getStatus() != SSLEngineResult.Status.OK) {
/* 352 */             handshaking = false;
/*     */           }
/*     */         
/*     */         case NEED_TASK:
/* 356 */           doRunTask();
/*     */         
/*     */         case NOT_HANDSHAKING:
/* 359 */           handshaking = false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 369 */     if (result != null && result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.FINISHED && 
/* 370 */       this.handler != null) {
/* 371 */       this.handler.verify(this.session, this.sslEngine.getSession());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateEventMask() {
/* 378 */     if (this.status == 0 && (this.endOfStream || this.sslEngine.isInboundDone()))
/*     */     {
/* 380 */       this.status = 1;
/*     */     }
/* 382 */     if (this.status == 1 && !this.outEncrypted.hasData()) {
/* 383 */       this.sslEngine.closeOutbound();
/* 384 */       this.outboundClosedCount.incrementAndGet();
/*     */     } 
/* 386 */     if (this.status == 1 && this.sslEngine.isOutboundDone() && (this.endOfStream || this.sslEngine.isInboundDone()) && !this.inPlain.hasData() && this.appBufferStatus != null && !this.appBufferStatus.hasBufferedInput())
/*     */     {
/*     */ 
/*     */       
/* 390 */       this.status = Integer.MAX_VALUE;
/*     */     }
/*     */     
/* 393 */     if (this.status <= 1 && this.endOfStream && this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP)
/*     */     {
/* 395 */       this.status = Integer.MAX_VALUE;
/*     */     }
/* 397 */     if (this.status == Integer.MAX_VALUE) {
/* 398 */       this.session.close();
/*     */       
/*     */       return;
/*     */     } 
/* 402 */     int oldMask = this.session.getEventMask();
/* 403 */     int newMask = oldMask;
/* 404 */     switch (this.sslEngine.getHandshakeStatus()) {
/*     */       case NEED_WRAP:
/* 406 */         newMask = 5;
/*     */         break;
/*     */       case NEED_UNWRAP:
/* 409 */         newMask = 1;
/*     */         break;
/*     */       case NOT_HANDSHAKING:
/* 412 */         newMask = this.appEventMask;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 420 */     if (this.endOfStream && !this.inPlain.hasData() && (this.appBufferStatus == null || !this.appBufferStatus.hasBufferedInput()))
/*     */     {
/*     */       
/* 423 */       newMask &= 0xFFFFFFFE;
/*     */     }
/*     */ 
/*     */     
/* 427 */     if (this.outEncrypted.hasData()) {
/* 428 */       newMask |= 0x4;
/*     */     }
/*     */ 
/*     */     
/* 432 */     if (oldMask != newMask)
/* 433 */       this.session.setEventMask(newMask); 
/*     */   }
/*     */   
/*     */   private int sendEncryptedData() throws IOException {
/*     */     int bytesWritten;
/* 438 */     if (!this.outEncrypted.hasData())
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 443 */       return this.session.channel().write(EMPTY_BUFFER);
/*     */     }
/*     */ 
/*     */     
/* 447 */     ByteBuffer outEncryptedBuf = this.outEncrypted.acquire();
/*     */ 
/*     */ 
/*     */     
/* 451 */     outEncryptedBuf.flip();
/*     */     try {
/* 453 */       bytesWritten = this.session.channel().write(outEncryptedBuf);
/*     */     } finally {
/* 455 */       outEncryptedBuf.compact();
/*     */     } 
/*     */ 
/*     */     
/* 459 */     if (outEncryptedBuf.position() == 0) {
/* 460 */       this.outEncrypted.release();
/*     */     }
/* 462 */     return bytesWritten;
/*     */   }
/*     */   
/*     */   private int receiveEncryptedData() throws IOException {
/* 466 */     if (this.endOfStream) {
/* 467 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 471 */     ByteBuffer inEncryptedBuf = this.inEncrypted.acquire();
/*     */ 
/*     */     
/* 474 */     int bytesRead = this.session.channel().read(inEncryptedBuf);
/*     */ 
/*     */     
/* 477 */     if (inEncryptedBuf.position() == 0) {
/* 478 */       this.inEncrypted.release();
/*     */     }
/* 480 */     if (bytesRead == -1) {
/* 481 */       this.endOfStream = true;
/*     */     }
/* 483 */     return bytesRead;
/*     */   }
/*     */   
/*     */   private boolean decryptData() throws SSLException {
/* 487 */     boolean decrypted = false;
/* 488 */     while (this.inEncrypted.hasData()) {
/*     */       SSLEngineResult result;
/* 490 */       ByteBuffer inEncryptedBuf = this.inEncrypted.acquire();
/* 491 */       ByteBuffer inPlainBuf = this.inPlain.acquire();
/*     */ 
/*     */ 
/*     */       
/* 495 */       inEncryptedBuf.flip();
/*     */       try {
/* 497 */         result = doUnwrap(inEncryptedBuf, inPlainBuf);
/*     */       } finally {
/* 499 */         inEncryptedBuf.compact();
/*     */       } 
/*     */ 
/*     */       
/* 503 */       try { if (!inEncryptedBuf.hasRemaining() && result.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
/* 504 */           throw new SSLException("Unable to complete SSL handshake");
/*     */         }
/* 506 */         SSLEngineResult.Status status = result.getStatus();
/* 507 */         if (status == SSLEngineResult.Status.OK)
/* 508 */         { decrypted = true; }
/*     */         else
/* 510 */         { if (status == SSLEngineResult.Status.BUFFER_UNDERFLOW && this.endOfStream) {
/* 511 */             throw new SSLException("Unable to decrypt incoming data due to unexpected end of stream");
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 517 */           if (this.inEncrypted.acquire().position() == 0)
/* 518 */             this.inEncrypted.release();  break; }  } finally { if (this.inEncrypted.acquire().position() == 0) this.inEncrypted.release();
/*     */          }
/*     */     
/*     */     } 
/* 522 */     if (this.sslEngine.isInboundDone()) {
/* 523 */       this.endOfStream = true;
/*     */     }
/* 525 */     return decrypted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isAppInputReady() throws IOException {
/*     */     do {
/* 536 */       receiveEncryptedData();
/* 537 */       doHandshake();
/* 538 */       SSLEngineResult.HandshakeStatus status = this.sslEngine.getHandshakeStatus();
/* 539 */       if (status != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && status != SSLEngineResult.HandshakeStatus.FINISHED)
/* 540 */         continue;  decryptData();
/*     */     }
/* 542 */     while (this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK);
/*     */     
/* 544 */     return ((this.appEventMask & 0x1) > 0 && (this.inPlain.hasData() || (this.appBufferStatus != null && this.appBufferStatus.hasBufferedInput()) || (this.endOfStream && this.status == 0)));
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
/*     */   public synchronized boolean isAppOutputReady() throws IOException {
/* 557 */     return ((this.appEventMask & 0x4) > 0 && this.status == 0 && this.sslEngine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void inboundTransport() throws IOException {
/* 568 */     updateEventMask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void outboundTransport() throws IOException {
/* 577 */     sendEncryptedData();
/* 578 */     doHandshake();
/* 579 */     updateEventMask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isInboundDone() {
/* 586 */     return this.sslEngine.isInboundDone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isOutboundDone() {
/* 593 */     return this.sslEngine.isOutboundDone();
/*     */   }
/*     */   
/*     */   private synchronized int writePlain(ByteBuffer src) throws IOException {
/* 597 */     Args.notNull(src, "Byte buffer");
/* 598 */     if (this.status != 0) {
/* 599 */       throw new ClosedChannelException();
/*     */     }
/* 601 */     ByteBuffer outEncryptedBuf = this.outEncrypted.acquire();
/* 602 */     SSLEngineResult result = doWrap(src, outEncryptedBuf);
/* 603 */     if (result.getStatus() == SSLEngineResult.Status.CLOSED) {
/* 604 */       this.status = Integer.MAX_VALUE;
/*     */     }
/* 606 */     return result.bytesConsumed();
/*     */   }
/*     */   
/*     */   private synchronized int readPlain(ByteBuffer dst) {
/* 610 */     Args.notNull(dst, "Byte buffer");
/* 611 */     if (this.inPlain.hasData()) {
/*     */       
/* 613 */       ByteBuffer inPlainBuf = this.inPlain.acquire();
/*     */ 
/*     */       
/* 616 */       inPlainBuf.flip();
/* 617 */       int n = Math.min(inPlainBuf.remaining(), dst.remaining());
/* 618 */       for (int i = 0; i < n; i++) {
/* 619 */         dst.put(inPlainBuf.get());
/*     */       }
/* 621 */       inPlainBuf.compact();
/*     */ 
/*     */       
/* 624 */       if (inPlainBuf.position() == 0) {
/* 625 */         this.inPlain.release();
/*     */       }
/* 627 */       return n;
/*     */     } 
/* 629 */     return this.endOfStream ? -1 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 634 */     if (this.status >= 1) {
/*     */       return;
/*     */     }
/* 637 */     this.status = 1;
/* 638 */     if (this.session.getSocketTimeout() == 0) {
/* 639 */       this.session.setSocketTimeout(1000);
/*     */     }
/*     */     try {
/* 642 */       updateEventMask();
/* 643 */     } catch (CancelledKeyException ex) {
/* 644 */       shutdown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/* 650 */     if (this.status == Integer.MAX_VALUE) {
/*     */       return;
/*     */     }
/* 653 */     this.status = Integer.MAX_VALUE;
/* 654 */     this.session.shutdown();
/*     */     
/* 656 */     this.inEncrypted.release();
/* 657 */     this.outEncrypted.release();
/* 658 */     this.inPlain.release();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStatus() {
/* 664 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 669 */     return (this.status >= 1 || this.session.isClosed());
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteChannel channel() {
/* 674 */     return this.channel;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress getLocalAddress() {
/* 679 */     return this.session.getLocalAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketAddress getRemoteAddress() {
/* 684 */     return this.session.getRemoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized int getEventMask() {
/* 689 */     return this.appEventMask;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setEventMask(int ops) {
/* 694 */     this.appEventMask = ops;
/* 695 */     updateEventMask();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setEvent(int op) {
/* 700 */     this.appEventMask |= op;
/* 701 */     updateEventMask();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void clearEvent(int op) {
/* 706 */     this.appEventMask &= op ^ 0xFFFFFFFF;
/* 707 */     updateEventMask();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSocketTimeout() {
/* 712 */     return this.session.getSocketTimeout();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSocketTimeout(int timeout) {
/* 717 */     this.session.setSocketTimeout(timeout);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean hasBufferedInput() {
/* 722 */     return ((this.appBufferStatus != null && this.appBufferStatus.hasBufferedInput()) || this.inEncrypted.hasData() || this.inPlain.hasData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean hasBufferedOutput() {
/* 729 */     return ((this.appBufferStatus != null && this.appBufferStatus.hasBufferedOutput()) || this.outEncrypted.hasData());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setBufferStatus(SessionBufferStatus status) {
/* 735 */     this.appBufferStatus = status;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getAttribute(String name) {
/* 740 */     return this.session.getAttribute(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object removeAttribute(String name) {
/* 745 */     return this.session.removeAttribute(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttribute(String name, Object obj) {
/* 750 */     this.session.setAttribute(name, obj);
/*     */   }
/*     */   
/*     */   private static void formatOps(StringBuilder buffer, int ops) {
/* 754 */     if ((ops & 0x1) > 0) {
/* 755 */       buffer.append('r');
/*     */     }
/* 757 */     if ((ops & 0x4) > 0) {
/* 758 */       buffer.append('w');
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 764 */     StringBuilder buffer = new StringBuilder();
/* 765 */     buffer.append(this.session);
/* 766 */     buffer.append("[");
/* 767 */     switch (this.status) {
/*     */       case 0:
/* 769 */         buffer.append("ACTIVE");
/*     */         break;
/*     */       case 1:
/* 772 */         buffer.append("CLOSING");
/*     */         break;
/*     */       case 2147483647:
/* 775 */         buffer.append("CLOSED");
/*     */         break;
/*     */     } 
/* 778 */     buffer.append("][");
/* 779 */     formatOps(buffer, this.appEventMask);
/* 780 */     buffer.append("][");
/* 781 */     buffer.append(this.sslEngine.getHandshakeStatus());
/* 782 */     if (this.sslEngine.isInboundDone()) {
/* 783 */       buffer.append("][inbound done][");
/*     */     }
/* 785 */     if (this.sslEngine.isOutboundDone()) {
/* 786 */       buffer.append("][outbound done][");
/*     */     }
/* 788 */     if (this.endOfStream) {
/* 789 */       buffer.append("][EOF][");
/*     */     }
/* 791 */     buffer.append("][");
/* 792 */     buffer.append(!this.inEncrypted.hasData() ? 0 : this.inEncrypted.acquire().position());
/* 793 */     buffer.append("][");
/* 794 */     buffer.append(!this.inPlain.hasData() ? 0 : this.inPlain.acquire().position());
/* 795 */     buffer.append("][");
/* 796 */     buffer.append(!this.outEncrypted.hasData() ? 0 : this.outEncrypted.acquire().position());
/* 797 */     buffer.append("]");
/* 798 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public Socket getSocket() {
/* 803 */     return (this.session instanceof SocketAccessor) ? ((SocketAccessor)this.session).getSocket() : null;
/*     */   }
/*     */   
/*     */   private class InternalByteChannel implements ByteChannel {
/*     */     private InternalByteChannel() {}
/*     */     
/*     */     public int write(ByteBuffer src) throws IOException {
/* 810 */       return SSLIOSession.this.writePlain(src);
/*     */     }
/*     */ 
/*     */     
/*     */     public int read(ByteBuffer dst) throws IOException {
/* 815 */       return SSLIOSession.this.readPlain(dst);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 820 */       SSLIOSession.this.close();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOpen() {
/* 825 */       return !SSLIOSession.this.isClosed();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/reactor/ssl/SSLIOSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */