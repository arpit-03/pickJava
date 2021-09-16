/*     */ package org.apache.http.impl.nio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.apache.http.HttpException;
/*     */ import org.apache.http.HttpMessage;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpRequestFactory;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.config.MessageConstraints;
/*     */ import org.apache.http.entity.ContentLengthStrategy;
/*     */ import org.apache.http.impl.entity.DisallowIdentityContentLengthStrategy;
/*     */ import org.apache.http.impl.entity.LaxContentLengthStrategy;
/*     */ import org.apache.http.impl.entity.StrictContentLengthStrategy;
/*     */ import org.apache.http.impl.nio.codecs.DefaultHttpRequestParser;
/*     */ import org.apache.http.impl.nio.codecs.DefaultHttpRequestParserFactory;
/*     */ import org.apache.http.impl.nio.codecs.DefaultHttpResponseWriter;
/*     */ import org.apache.http.impl.nio.codecs.DefaultHttpResponseWriterFactory;
/*     */ import org.apache.http.nio.NHttpMessageParser;
/*     */ import org.apache.http.nio.NHttpMessageParserFactory;
/*     */ import org.apache.http.nio.NHttpMessageWriter;
/*     */ import org.apache.http.nio.NHttpMessageWriterFactory;
/*     */ import org.apache.http.nio.NHttpServerConnection;
/*     */ import org.apache.http.nio.NHttpServerEventHandler;
/*     */ import org.apache.http.nio.NHttpServerIOTarget;
/*     */ import org.apache.http.nio.NHttpServiceHandler;
/*     */ import org.apache.http.nio.reactor.IOSession;
/*     */ import org.apache.http.nio.reactor.SessionInputBuffer;
/*     */ import org.apache.http.nio.reactor.SessionOutputBuffer;
/*     */ import org.apache.http.nio.util.ByteBufferAllocator;
/*     */ import org.apache.http.params.HttpParamConfig;
/*     */ import org.apache.http.params.HttpParams;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultNHttpServerConnection
/*     */   extends NHttpConnectionBase
/*     */   implements NHttpServerIOTarget
/*     */ {
/*     */   protected final NHttpMessageParser<HttpRequest> requestParser;
/*     */   protected final NHttpMessageWriter<HttpResponse> responseWriter;
/*     */   
/*     */   @Deprecated
/*     */   public DefaultNHttpServerConnection(IOSession session, HttpRequestFactory requestFactory, ByteBufferAllocator allocator, HttpParams params) {
/*  98 */     super(session, allocator, params);
/*  99 */     Args.notNull(requestFactory, "Request factory");
/* 100 */     this.requestParser = createRequestParser((SessionInputBuffer)this.inbuf, requestFactory, params);
/* 101 */     this.responseWriter = createResponseWriter((SessionOutputBuffer)this.outbuf, params);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultNHttpServerConnection(IOSession session, int bufferSize, int fragmentSizeHint, ByteBufferAllocator allocator, CharsetDecoder charDecoder, CharsetEncoder charEncoder, MessageConstraints constraints, ContentLengthStrategy incomingContentStrategy, ContentLengthStrategy outgoingContentStrategy, NHttpMessageParserFactory<HttpRequest> requestParserFactory, NHttpMessageWriterFactory<HttpResponse> responseWriterFactory) {
/* 142 */     super(session, bufferSize, fragmentSizeHint, allocator, charDecoder, charEncoder, constraints, (incomingContentStrategy != null) ? incomingContentStrategy : (ContentLengthStrategy)DisallowIdentityContentLengthStrategy.INSTANCE, (outgoingContentStrategy != null) ? outgoingContentStrategy : (ContentLengthStrategy)StrictContentLengthStrategy.INSTANCE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     this.requestParser = ((requestParserFactory != null) ? requestParserFactory : DefaultHttpRequestParserFactory.INSTANCE).create((SessionInputBuffer)this.inbuf, constraints);
/*     */     
/* 150 */     this.responseWriter = ((responseWriterFactory != null) ? responseWriterFactory : DefaultHttpResponseWriterFactory.INSTANCE).create((SessionOutputBuffer)this.outbuf);
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
/*     */   public DefaultNHttpServerConnection(IOSession session, int bufferSize, CharsetDecoder charDecoder, CharsetEncoder charEncoder, MessageConstraints constraints) {
/* 163 */     this(session, bufferSize, bufferSize, (ByteBufferAllocator)null, charDecoder, charEncoder, constraints, (ContentLengthStrategy)null, (ContentLengthStrategy)null, (NHttpMessageParserFactory<HttpRequest>)null, (NHttpMessageWriterFactory<HttpResponse>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultNHttpServerConnection(IOSession session, int bufferSize) {
/* 171 */     this(session, bufferSize, bufferSize, (ByteBufferAllocator)null, (CharsetDecoder)null, (CharsetEncoder)null, (MessageConstraints)null, (ContentLengthStrategy)null, (ContentLengthStrategy)null, (NHttpMessageParserFactory<HttpRequest>)null, (NHttpMessageWriterFactory<HttpResponse>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   protected ContentLengthStrategy createIncomingContentStrategy() {
/* 180 */     return (ContentLengthStrategy)new DisallowIdentityContentLengthStrategy((ContentLengthStrategy)new LaxContentLengthStrategy(0));
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
/*     */   @Deprecated
/*     */   protected NHttpMessageParser<HttpRequest> createRequestParser(SessionInputBuffer buffer, HttpRequestFactory requestFactory, HttpParams params) {
/* 199 */     MessageConstraints constraints = HttpParamConfig.getMessageConstraints(params);
/* 200 */     return (NHttpMessageParser<HttpRequest>)new DefaultHttpRequestParser(buffer, null, requestFactory, constraints);
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
/*     */   @Deprecated
/*     */   protected NHttpMessageWriter<HttpResponse> createResponseWriter(SessionOutputBuffer buffer, HttpParams params) {
/* 220 */     return (NHttpMessageWriter<HttpResponse>)new DefaultHttpResponseWriter(buffer, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onRequestReceived(HttpRequest request) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onResponseSubmitted(HttpResponse response) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetInput() {
/* 237 */     this.request = null;
/* 238 */     this.contentDecoder = null;
/* 239 */     this.requestParser.reset();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetOutput() {
/* 244 */     this.response = null;
/* 245 */     this.contentEncoder = null;
/* 246 */     this.responseWriter.reset();
/*     */   }
/*     */   
/*     */   public void consumeInput(NHttpServerEventHandler handler) {
/* 250 */     if (this.status != 0) {
/* 251 */       this.session.clearEvent(1);
/*     */       return;
/*     */     } 
/*     */     try {
/* 255 */       if (this.request == null) {
/*     */         int bytesRead;
/*     */         do {
/* 258 */           bytesRead = this.requestParser.fillBuffer(this.session.channel());
/* 259 */           if (bytesRead > 0) {
/* 260 */             this.inTransportMetrics.incrementBytesTransferred(bytesRead);
/*     */           }
/* 262 */           this.request = (HttpRequest)this.requestParser.parse();
/* 263 */         } while (bytesRead > 0 && this.request == null);
/* 264 */         if (this.request != null) {
/* 265 */           if (this.request instanceof HttpEntityEnclosingRequest) {
/*     */             
/* 267 */             HttpEntity entity = prepareDecoder((HttpMessage)this.request);
/* 268 */             ((HttpEntityEnclosingRequest)this.request).setEntity(entity);
/*     */           } 
/* 270 */           this.connMetrics.incrementRequestCount();
/* 271 */           this.hasBufferedInput = this.inbuf.hasData();
/* 272 */           onRequestReceived(this.request);
/* 273 */           handler.requestReceived((NHttpServerConnection)this);
/* 274 */           if (this.contentDecoder == null)
/*     */           {
/*     */             
/* 277 */             resetInput();
/*     */           }
/*     */         } 
/* 280 */         if (bytesRead == -1 && !this.inbuf.hasData()) {
/* 281 */           handler.endOfInput((NHttpServerConnection)this);
/*     */         }
/*     */       } 
/* 284 */       if (this.contentDecoder != null && (this.session.getEventMask() & 0x1) > 0) {
/* 285 */         handler.inputReady((NHttpServerConnection)this, this.contentDecoder);
/* 286 */         if (this.contentDecoder.isCompleted())
/*     */         {
/*     */           
/* 289 */           resetInput();
/*     */         }
/*     */       } 
/* 292 */     } catch (HttpException ex) {
/* 293 */       handler.exception((NHttpServerConnection)this, (Exception)ex);
/* 294 */     } catch (Exception ex) {
/* 295 */       handler.exception((NHttpServerConnection)this, ex);
/*     */     } finally {
/*     */       
/* 298 */       this.hasBufferedInput = this.inbuf.hasData();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void produceOutput(NHttpServerEventHandler handler) {
/*     */     try {
/* 304 */       if (this.status == 0) {
/* 305 */         if (this.contentEncoder == null && !this.outbuf.hasData()) {
/* 306 */           handler.responseReady((NHttpServerConnection)this);
/*     */         }
/* 308 */         if (this.contentEncoder != null) {
/* 309 */           handler.outputReady((NHttpServerConnection)this, this.contentEncoder);
/* 310 */           if (this.contentEncoder.isCompleted()) {
/* 311 */             resetOutput();
/*     */           }
/*     */         } 
/*     */       } 
/* 315 */       if (this.outbuf.hasData()) {
/* 316 */         int bytesWritten = this.outbuf.flush(this.session.channel());
/* 317 */         if (bytesWritten > 0) {
/* 318 */           this.outTransportMetrics.incrementBytesTransferred(bytesWritten);
/*     */         }
/*     */       } 
/* 321 */       if (!this.outbuf.hasData() && 
/* 322 */         this.status == 1) {
/* 323 */         this.session.close();
/* 324 */         this.status = 2;
/* 325 */         resetOutput();
/*     */       }
/*     */     
/* 328 */     } catch (Exception ex) {
/* 329 */       handler.exception((NHttpServerConnection)this, ex);
/*     */     } finally {
/*     */       
/* 332 */       this.hasBufferedOutput = this.outbuf.hasData();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void submitResponse(HttpResponse response) throws IOException, HttpException {
/* 338 */     Args.notNull(response, "HTTP response");
/* 339 */     assertNotClosed();
/* 340 */     if (this.response != null) {
/* 341 */       throw new HttpException("Response already submitted");
/*     */     }
/* 343 */     onResponseSubmitted(response);
/* 344 */     this.responseWriter.write((HttpMessage)response);
/* 345 */     this.hasBufferedOutput = this.outbuf.hasData();
/*     */     
/* 347 */     if (response.getStatusLine().getStatusCode() >= 200) {
/* 348 */       this.connMetrics.incrementResponseCount();
/* 349 */       if (response.getEntity() != null) {
/* 350 */         this.response = response;
/* 351 */         prepareEncoder((HttpMessage)response);
/*     */       } 
/*     */     } 
/*     */     
/* 355 */     this.session.setEvent(4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isResponseSubmitted() {
/* 360 */     return (this.response != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void consumeInput(NHttpServiceHandler handler) {
/* 365 */     consumeInput(new NHttpServerEventHandlerAdaptor(handler));
/*     */   }
/*     */ 
/*     */   
/*     */   public void produceOutput(NHttpServiceHandler handler) {
/* 370 */     produceOutput(new NHttpServerEventHandlerAdaptor(handler));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/nio/DefaultNHttpServerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */