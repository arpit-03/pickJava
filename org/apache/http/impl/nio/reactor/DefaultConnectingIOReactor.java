/*     */ package org.apache.http.impl.nio.reactor;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import org.apache.http.nio.reactor.ConnectingIOReactor;
/*     */ import org.apache.http.nio.reactor.IOReactorException;
/*     */ import org.apache.http.nio.reactor.IOReactorStatus;
/*     */ import org.apache.http.nio.reactor.SessionRequest;
/*     */ import org.apache.http.nio.reactor.SessionRequestCallback;
/*     */ import org.apache.http.params.HttpParams;
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
/*     */ public class DefaultConnectingIOReactor
/*     */   extends AbstractMultiworkerIOReactor
/*     */   implements ConnectingIOReactor
/*     */ {
/*     */   private final Queue<SessionRequestImpl> requestQueue;
/*     */   private long lastTimeoutCheck;
/*     */   
/*     */   public DefaultConnectingIOReactor(IOReactorConfig config, ThreadFactory threadFactory) throws IOReactorException {
/*  82 */     super(config, threadFactory);
/*  83 */     this.requestQueue = new ConcurrentLinkedQueue<SessionRequestImpl>();
/*  84 */     this.lastTimeoutCheck = System.currentTimeMillis();
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
/*     */   public DefaultConnectingIOReactor(IOReactorConfig config) throws IOReactorException {
/*  97 */     this(config, (ThreadFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultConnectingIOReactor() throws IOReactorException {
/* 108 */     this((IOReactorConfig)null, (ThreadFactory)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public DefaultConnectingIOReactor(int workerCount, ThreadFactory threadFactory, HttpParams params) throws IOReactorException {
/* 119 */     this(convert(workerCount, params), threadFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public DefaultConnectingIOReactor(int workerCount, HttpParams params) throws IOReactorException {
/* 129 */     this(convert(workerCount, params), (ThreadFactory)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void cancelRequests() throws IOReactorException {
/*     */     SessionRequestImpl request;
/* 135 */     while ((request = this.requestQueue.poll()) != null) {
/* 136 */       request.cancel();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void processEvents(int readyCount) throws IOReactorException {
/* 142 */     processSessionRequests();
/*     */     
/* 144 */     if (readyCount > 0) {
/* 145 */       Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
/* 146 */       for (SelectionKey key : selectedKeys)
/*     */       {
/* 148 */         processEvent(key);
/*     */       }
/*     */       
/* 151 */       selectedKeys.clear();
/*     */     } 
/*     */     
/* 154 */     long currentTime = System.currentTimeMillis();
/* 155 */     if (currentTime - this.lastTimeoutCheck >= this.selectTimeout) {
/* 156 */       this.lastTimeoutCheck = currentTime;
/* 157 */       Set<SelectionKey> keys = this.selector.keys();
/* 158 */       processTimeouts(keys);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processEvent(SelectionKey key) {
/*     */     try {
/* 165 */       if (key.isConnectable())
/*     */       {
/* 167 */         SocketChannel channel = (SocketChannel)key.channel();
/*     */         
/* 169 */         SessionRequestHandle requestHandle = (SessionRequestHandle)key.attachment();
/* 170 */         SessionRequestImpl sessionRequest = requestHandle.getSessionRequest();
/*     */ 
/*     */         
/*     */         try {
/* 174 */           channel.finishConnect();
/* 175 */         } catch (IOException ex) {
/* 176 */           sessionRequest.failed(ex);
/*     */         } 
/* 178 */         key.cancel();
/* 179 */         key.attach(null);
/* 180 */         if (!sessionRequest.isCompleted()) {
/* 181 */           addChannel(new ChannelEntry(channel, sessionRequest));
/*     */         } else {
/*     */           try {
/* 184 */             channel.close();
/* 185 */           } catch (IOException ignore) {}
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 190 */     } catch (CancelledKeyException ex) {
/* 191 */       SessionRequestHandle requestHandle = (SessionRequestHandle)key.attachment();
/* 192 */       key.attach(null);
/* 193 */       if (requestHandle != null) {
/* 194 */         SessionRequestImpl sessionRequest = requestHandle.getSessionRequest();
/* 195 */         if (sessionRequest != null) {
/* 196 */           sessionRequest.cancel();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processTimeouts(Set<SelectionKey> keys) {
/* 203 */     long now = System.currentTimeMillis();
/* 204 */     for (SelectionKey key : keys) {
/* 205 */       Object attachment = key.attachment();
/*     */       
/* 207 */       if (attachment instanceof SessionRequestHandle) {
/* 208 */         SessionRequestHandle handle = (SessionRequestHandle)key.attachment();
/* 209 */         SessionRequestImpl sessionRequest = handle.getSessionRequest();
/* 210 */         int timeout = sessionRequest.getConnectTimeout();
/* 211 */         if (timeout > 0 && 
/* 212 */           handle.getRequestTime() + timeout < now) {
/* 213 */           sessionRequest.timeout();
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
/*     */ 
/*     */   
/*     */   public SessionRequest connect(SocketAddress remoteAddress, SocketAddress localAddress, Object attachment, SessionRequestCallback callback) {
/* 227 */     Asserts.check((this.status.compareTo((Enum)IOReactorStatus.ACTIVE) <= 0), "I/O reactor has been shut down");
/*     */     
/* 229 */     SessionRequestImpl sessionRequest = new SessionRequestImpl(remoteAddress, localAddress, attachment, callback);
/*     */     
/* 231 */     sessionRequest.setConnectTimeout(this.config.getConnectTimeout());
/*     */     
/* 233 */     this.requestQueue.add(sessionRequest);
/* 234 */     this.selector.wakeup();
/*     */     
/* 236 */     return sessionRequest;
/*     */   }
/*     */   
/*     */   private void validateAddress(SocketAddress address) throws UnknownHostException {
/* 240 */     if (address == null) {
/*     */       return;
/*     */     }
/* 243 */     if (address instanceof InetSocketAddress) {
/* 244 */       InetSocketAddress endpoint = (InetSocketAddress)address;
/* 245 */       if (endpoint.isUnresolved()) {
/* 246 */         throw new UnknownHostException(endpoint.getHostName());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processSessionRequests() throws IOReactorException {
/*     */     SessionRequestImpl request;
/* 253 */     while ((request = this.requestQueue.poll()) != null) {
/* 254 */       final SocketChannel socketChannel; if (request.isCompleted()) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 259 */         socketChannel = SocketChannel.open();
/* 260 */       } catch (IOException ex) {
/* 261 */         request.failed(ex); return;
/*     */       } 
/*     */       try {
/*     */         boolean connected;
/* 265 */         validateAddress(request.getLocalAddress());
/* 266 */         validateAddress(request.getRemoteAddress());
/*     */         
/* 268 */         socketChannel.configureBlocking(false);
/* 269 */         prepareSocket(socketChannel.socket());
/*     */         
/* 271 */         if (request.getLocalAddress() != null) {
/* 272 */           Socket sock = socketChannel.socket();
/* 273 */           sock.setReuseAddress(this.config.isSoReuseAddress());
/* 274 */           sock.bind(request.getLocalAddress());
/*     */         } 
/*     */         
/* 277 */         final SocketAddress targetAddress = request.getRemoteAddress();
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 282 */           connected = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedExceptionAction<Boolean>()
/*     */               {
/*     */                 public Boolean run() throws IOException
/*     */                 {
/* 286 */                   return Boolean.valueOf(socketChannel.connect(targetAddress));
/*     */                 }
/*     */               })).booleanValue();
/* 289 */         } catch (PrivilegedActionException e) {
/* 290 */           Asserts.check(e.getCause() instanceof IOException, "method contract violation only checked exceptions are wrapped: " + e.getCause());
/*     */ 
/*     */           
/* 293 */           throw (IOException)e.getCause();
/*     */         } 
/* 295 */         if (connected) {
/* 296 */           ChannelEntry entry = new ChannelEntry(socketChannel, request);
/* 297 */           addChannel(entry);
/*     */           continue;
/*     */         } 
/* 300 */       } catch (IOException ex) {
/* 301 */         closeChannel(socketChannel);
/* 302 */         request.failed(ex);
/*     */         return;
/* 304 */       } catch (SecurityException ex) {
/* 305 */         closeChannel(socketChannel);
/* 306 */         request.failed(new IOException(ex));
/*     */         
/*     */         return;
/*     */       } 
/* 310 */       SessionRequestHandle requestHandle = new SessionRequestHandle(request);
/*     */       try {
/* 312 */         SelectionKey key = socketChannel.register(this.selector, 8, requestHandle);
/*     */         
/* 314 */         request.setKey(key);
/* 315 */       } catch (IOException ex) {
/* 316 */         closeChannel(socketChannel);
/* 317 */         throw new IOReactorException("Failure registering channel with the selector", ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/reactor/DefaultConnectingIOReactor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */