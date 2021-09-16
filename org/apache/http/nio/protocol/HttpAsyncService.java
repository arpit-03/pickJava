/*      */ package org.apache.http.nio.protocol;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.net.SocketTimeoutException;
/*      */ import java.util.Queue;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import org.apache.http.ConnectionReuseStrategy;
/*      */ import org.apache.http.ExceptionLogger;
/*      */ import org.apache.http.HttpEntity;
/*      */ import org.apache.http.HttpEntityEnclosingRequest;
/*      */ import org.apache.http.HttpException;
/*      */ import org.apache.http.HttpRequest;
/*      */ import org.apache.http.HttpResponse;
/*      */ import org.apache.http.HttpResponseFactory;
/*      */ import org.apache.http.HttpVersion;
/*      */ import org.apache.http.ProtocolVersion;
/*      */ import org.apache.http.annotation.Contract;
/*      */ import org.apache.http.annotation.ThreadingBehavior;
/*      */ import org.apache.http.concurrent.Cancellable;
/*      */ import org.apache.http.entity.ContentType;
/*      */ import org.apache.http.impl.DefaultConnectionReuseStrategy;
/*      */ import org.apache.http.impl.DefaultHttpResponseFactory;
/*      */ import org.apache.http.nio.ContentDecoder;
/*      */ import org.apache.http.nio.ContentEncoder;
/*      */ import org.apache.http.nio.IOControl;
/*      */ import org.apache.http.nio.NHttpConnection;
/*      */ import org.apache.http.nio.NHttpServerConnection;
/*      */ import org.apache.http.nio.NHttpServerEventHandler;
/*      */ import org.apache.http.nio.entity.NStringEntity;
/*      */ import org.apache.http.nio.reactor.SessionBufferStatus;
/*      */ import org.apache.http.params.HttpParams;
/*      */ import org.apache.http.protocol.BasicHttpContext;
/*      */ import org.apache.http.protocol.HttpContext;
/*      */ import org.apache.http.protocol.HttpCoreContext;
/*      */ import org.apache.http.protocol.HttpProcessor;
/*      */ import org.apache.http.util.Args;
/*      */ import org.apache.http.util.Asserts;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Contract(threading = ThreadingBehavior.IMMUTABLE_CONDITIONAL)
/*      */ public class HttpAsyncService
/*      */   implements NHttpServerEventHandler
/*      */ {
/*      */   static final String HTTP_EXCHANGE_STATE = "http.nio.http-exchange-state";
/*      */   private final HttpProcessor httpProcessor;
/*      */   private final ConnectionReuseStrategy connectionStrategy;
/*      */   private final HttpResponseFactory responseFactory;
/*      */   private final HttpAsyncRequestHandlerMapper handlerMapper;
/*      */   private final HttpAsyncExpectationVerifier expectationVerifier;
/*      */   private final ExceptionLogger exceptionLogger;
/*      */   
/*      */   @Deprecated
/*      */   public HttpAsyncService(HttpProcessor httpProcessor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpAsyncRequestHandlerResolver handlerResolver, HttpAsyncExpectationVerifier expectationVerifier, HttpParams params) {
/*  137 */     this(httpProcessor, connStrategy, responseFactory, new HttpAsyncRequestHandlerResolverAdapter(handlerResolver), expectationVerifier);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public HttpAsyncService(HttpProcessor httpProcessor, ConnectionReuseStrategy connStrategy, HttpAsyncRequestHandlerResolver handlerResolver, HttpParams params) {
/*  162 */     this(httpProcessor, connStrategy, (HttpResponseFactory)DefaultHttpResponseFactory.INSTANCE, new HttpAsyncRequestHandlerResolverAdapter(handlerResolver), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpAsyncService(HttpProcessor httpProcessor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpAsyncRequestHandlerMapper handlerMapper, HttpAsyncExpectationVerifier expectationVerifier) {
/*  188 */     this(httpProcessor, connStrategy, responseFactory, handlerMapper, expectationVerifier, (ExceptionLogger)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpAsyncService(HttpProcessor httpProcessor, ConnectionReuseStrategy connStrategy, HttpResponseFactory responseFactory, HttpAsyncRequestHandlerMapper handlerMapper, HttpAsyncExpectationVerifier expectationVerifier, ExceptionLogger exceptionLogger) {
/*  216 */     this.httpProcessor = (HttpProcessor)Args.notNull(httpProcessor, "HTTP processor");
/*  217 */     this.connectionStrategy = (connStrategy != null) ? connStrategy : (ConnectionReuseStrategy)DefaultConnectionReuseStrategy.INSTANCE;
/*      */     
/*  219 */     this.responseFactory = (responseFactory != null) ? responseFactory : (HttpResponseFactory)DefaultHttpResponseFactory.INSTANCE;
/*      */     
/*  221 */     this.handlerMapper = handlerMapper;
/*  222 */     this.expectationVerifier = expectationVerifier;
/*  223 */     this.exceptionLogger = (exceptionLogger != null) ? exceptionLogger : ExceptionLogger.NO_OP;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpAsyncService(HttpProcessor httpProcessor, HttpAsyncRequestHandlerMapper handlerMapper) {
/*  237 */     this(httpProcessor, null, null, handlerMapper, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpAsyncService(HttpProcessor httpProcessor, HttpAsyncRequestHandlerMapper handlerMapper, ExceptionLogger exceptionLogger) {
/*  256 */     this(httpProcessor, (ConnectionReuseStrategy)null, (HttpResponseFactory)null, handlerMapper, (HttpAsyncExpectationVerifier)null, exceptionLogger);
/*      */   }
/*      */ 
/*      */   
/*      */   public void connected(NHttpServerConnection conn) {
/*  261 */     State state = new State();
/*  262 */     conn.getContext().setAttribute("http.nio.http-exchange-state", state);
/*      */   }
/*      */ 
/*      */   
/*      */   public void closed(NHttpServerConnection conn) {
/*  267 */     State state = (State)conn.getContext().removeAttribute("http.nio.http-exchange-state");
/*  268 */     if (state != null) {
/*  269 */       state.setTerminated();
/*  270 */       closeHandlers(state);
/*  271 */       Cancellable cancellable = state.getCancellable();
/*  272 */       if (cancellable != null) {
/*  273 */         cancellable.cancel();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void exception(NHttpServerConnection conn, Exception cause) {
/*  281 */     log(cause);
/*  282 */     State state = getState((NHttpConnection)conn);
/*  283 */     if (state == null) {
/*  284 */       shutdownConnection((NHttpConnection)conn);
/*      */       return;
/*      */     } 
/*  287 */     state.setTerminated();
/*  288 */     closeHandlers(state, cause);
/*      */     try {
/*  290 */       Cancellable cancellable = state.getCancellable();
/*  291 */       if (cancellable != null) {
/*  292 */         cancellable.cancel();
/*      */       }
/*  294 */       if (cause instanceof java.net.SocketException) {
/*      */         
/*  296 */         conn.shutdown();
/*      */         return;
/*      */       } 
/*  299 */       if (cause instanceof SocketTimeoutException) {
/*      */         
/*  301 */         conn.close();
/*      */         
/*      */         return;
/*      */       } 
/*  305 */       if (conn.isResponseSubmitted() || state.getResponseState().compareTo(MessageState.INIT) > 0) {
/*      */         
/*  307 */         conn.close();
/*      */         return;
/*      */       } 
/*  310 */       HttpRequest request = conn.getHttpRequest();
/*  311 */       if (request == null) {
/*  312 */         Incoming incoming = state.getIncoming();
/*  313 */         if (incoming != null) {
/*  314 */           request = incoming.getRequest();
/*      */         }
/*      */       } 
/*  317 */       if (request == null) {
/*  318 */         Queue<PipelineEntry> pipeline = state.getPipeline();
/*  319 */         PipelineEntry pipelineEntry = pipeline.poll();
/*  320 */         if (pipelineEntry != null) {
/*  321 */           request = pipelineEntry.getRequest();
/*      */         }
/*      */       } 
/*  324 */       if (request != null) {
/*  325 */         conn.resetInput();
/*  326 */         HttpCoreContext context = HttpCoreContext.create();
/*  327 */         HttpAsyncResponseProducer responseProducer = handleException(cause, (HttpContext)context);
/*  328 */         HttpResponse response = responseProducer.generateResponse();
/*  329 */         Outgoing outgoing = new Outgoing(request, response, responseProducer, (HttpContext)context);
/*  330 */         state.setResponseState(MessageState.INIT);
/*  331 */         state.setOutgoing(outgoing);
/*  332 */         commitFinalResponse(conn, state);
/*      */         return;
/*      */       } 
/*  335 */       conn.close();
/*  336 */     } catch (Exception ex) {
/*  337 */       shutdownConnection((NHttpConnection)conn);
/*  338 */       closeHandlers(state);
/*  339 */       if (ex instanceof RuntimeException) {
/*  340 */         throw (RuntimeException)ex;
/*      */       }
/*  342 */       log(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected HttpResponse createHttpResponse(int status, HttpContext context) {
/*  347 */     return this.responseFactory.newHttpResponse((ProtocolVersion)HttpVersion.HTTP_1_1, status, context);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void requestReceived(NHttpServerConnection conn) throws IOException, HttpException {
/*  353 */     State state = getState((NHttpConnection)conn);
/*  354 */     Asserts.notNull(state, "Connection state");
/*  355 */     Asserts.check((state.getRequestState() == MessageState.READY), "Unexpected request state %s", state.getRequestState());
/*      */ 
/*      */     
/*  358 */     HttpRequest request = conn.getHttpRequest();
/*  359 */     BasicHttpContext basicHttpContext = new BasicHttpContext();
/*      */     
/*  361 */     basicHttpContext.setAttribute("http.request", request);
/*  362 */     basicHttpContext.setAttribute("http.connection", conn);
/*  363 */     this.httpProcessor.process(request, (HttpContext)basicHttpContext);
/*      */     
/*  365 */     HttpAsyncRequestHandler<Object> requestHandler = getRequestHandler(request);
/*  366 */     HttpAsyncRequestConsumer<Object> consumer = requestHandler.processRequest(request, (HttpContext)basicHttpContext);
/*  367 */     consumer.requestReceived(request);
/*      */     
/*  369 */     Incoming incoming = new Incoming(request, requestHandler, consumer, (HttpContext)basicHttpContext);
/*  370 */     state.setIncoming(incoming);
/*      */     
/*  372 */     if (request instanceof HttpEntityEnclosingRequest) {
/*      */ 
/*      */ 
/*      */       
/*  376 */       if (((HttpEntityEnclosingRequest)request).expectContinue() && state.getResponseState() == MessageState.READY && state.getPipeline().isEmpty() && (!(conn instanceof SessionBufferStatus) || !((SessionBufferStatus)conn).hasBufferedInput())) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  381 */         state.setRequestState(MessageState.ACK_EXPECTED);
/*  382 */         HttpResponse ack = createHttpResponse(100, (HttpContext)basicHttpContext);
/*  383 */         if (this.expectationVerifier != null) {
/*  384 */           conn.suspendInput();
/*  385 */           conn.suspendOutput();
/*  386 */           HttpAsyncExchange httpAsyncExchange = new HttpAsyncExchangeImpl(request, ack, state, conn, (HttpContext)basicHttpContext);
/*      */           
/*  388 */           this.expectationVerifier.verify(httpAsyncExchange, (HttpContext)basicHttpContext);
/*      */         } else {
/*  390 */           conn.submitResponse(ack);
/*  391 */           state.setRequestState(MessageState.BODY_STREAM);
/*      */         } 
/*      */       } else {
/*  394 */         state.setRequestState(MessageState.BODY_STREAM);
/*      */       } 
/*      */     } else {
/*      */       
/*  398 */       completeRequest(incoming, conn, state);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void inputReady(NHttpServerConnection conn, ContentDecoder decoder) throws IOException, HttpException {
/*  406 */     State state = getState((NHttpConnection)conn);
/*  407 */     Asserts.notNull(state, "Connection state");
/*  408 */     Asserts.check((state.getRequestState() == MessageState.BODY_STREAM), "Unexpected request state %s", state.getRequestState());
/*      */ 
/*      */     
/*  411 */     Incoming incoming = state.getIncoming();
/*  412 */     Asserts.notNull(incoming, "Incoming request");
/*  413 */     HttpAsyncRequestConsumer<?> consumer = incoming.getConsumer();
/*  414 */     consumer.consumeContent(decoder, (IOControl)conn);
/*  415 */     if (decoder.isCompleted()) {
/*  416 */       completeRequest(incoming, conn, state);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void responseReady(NHttpServerConnection conn) throws IOException, HttpException {
/*  423 */     State state = getState((NHttpConnection)conn);
/*  424 */     Asserts.notNull(state, "Connection state");
/*  425 */     Asserts.check((state.getResponseState() == MessageState.READY || state.getResponseState() == MessageState.INIT), "Unexpected response state %s", state.getResponseState());
/*      */ 
/*      */ 
/*      */     
/*  429 */     if (state.getRequestState() == MessageState.ACK_EXPECTED) {
/*      */       Outgoing outgoing;
/*  431 */       synchronized (state) {
/*  432 */         outgoing = state.getOutgoing();
/*  433 */         if (outgoing == null) {
/*  434 */           conn.suspendOutput();
/*      */           return;
/*      */         } 
/*      */       } 
/*  438 */       HttpResponse response = outgoing.getResponse();
/*  439 */       int status = response.getStatusLine().getStatusCode();
/*  440 */       if (status == 100) {
/*  441 */         HttpContext context = outgoing.getContext();
/*  442 */         HttpAsyncResponseProducer responseProducer = outgoing.getProducer();
/*      */         
/*      */         try {
/*  445 */           response.setEntity(null);
/*  446 */           conn.requestInput();
/*  447 */           state.setRequestState(MessageState.BODY_STREAM);
/*  448 */           state.setOutgoing(null);
/*  449 */           conn.submitResponse(response);
/*  450 */           responseProducer.responseCompleted(context);
/*      */         } finally {
/*  452 */           responseProducer.close();
/*      */         } 
/*  454 */       } else if (status >= 400) {
/*  455 */         conn.resetInput();
/*  456 */         state.setRequestState(MessageState.READY);
/*  457 */         commitFinalResponse(conn, state);
/*      */       } else {
/*  459 */         throw new HttpException("Invalid response: " + response.getStatusLine());
/*      */       } 
/*      */     } else {
/*  462 */       if (state.getResponseState() == MessageState.READY) {
/*  463 */         Queue<PipelineEntry> pipeline = state.getPipeline();
/*  464 */         PipelineEntry pipelineEntry = pipeline.poll();
/*  465 */         if (pipelineEntry == null) {
/*  466 */           conn.suspendOutput();
/*      */           return;
/*      */         } 
/*  469 */         state.setResponseState(MessageState.INIT);
/*  470 */         Object result = pipelineEntry.getResult();
/*  471 */         HttpRequest request = pipelineEntry.getRequest();
/*  472 */         HttpContext context = pipelineEntry.getContext();
/*  473 */         HttpResponse response = createHttpResponse(200, context);
/*  474 */         HttpAsyncExchangeImpl httpExchange = new HttpAsyncExchangeImpl(request, response, state, conn, context);
/*      */         
/*  476 */         if (result != null) {
/*  477 */           HttpAsyncRequestHandler<Object> handler = pipelineEntry.getHandler();
/*  478 */           conn.suspendOutput();
/*      */           try {
/*  480 */             handler.handle(result, httpExchange, context);
/*  481 */           } catch (RuntimeException ex) {
/*  482 */             throw ex;
/*  483 */           } catch (Exception ex) {
/*  484 */             if (!httpExchange.isCompleted()) {
/*  485 */               httpExchange.submitResponse(handleException(ex, context));
/*      */             } else {
/*  487 */               log(ex);
/*  488 */               conn.close();
/*      */             } 
/*      */             return;
/*      */           } 
/*      */         } else {
/*  493 */           Exception exception = pipelineEntry.getException();
/*  494 */           HttpAsyncResponseProducer responseProducer = handleException((exception != null) ? exception : (Exception)new HttpException("Internal error processing request"), context);
/*      */ 
/*      */           
/*  497 */           httpExchange.submitResponse(responseProducer);
/*      */         } 
/*      */       } 
/*  500 */       if (state.getResponseState() == MessageState.INIT) {
/*      */         Outgoing outgoing;
/*  502 */         synchronized (state) {
/*  503 */           outgoing = state.getOutgoing();
/*  504 */           if (outgoing == null) {
/*  505 */             conn.suspendOutput();
/*      */             return;
/*      */           } 
/*      */         } 
/*  509 */         HttpResponse response = outgoing.getResponse();
/*  510 */         int status = response.getStatusLine().getStatusCode();
/*  511 */         if (status >= 200) {
/*  512 */           commitFinalResponse(conn, state);
/*      */         } else {
/*  514 */           throw new HttpException("Invalid response: " + response.getStatusLine());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void outputReady(NHttpServerConnection conn, ContentEncoder encoder) throws HttpException, IOException {
/*  524 */     State state = getState((NHttpConnection)conn);
/*  525 */     Asserts.notNull(state, "Connection state");
/*  526 */     Asserts.check((state.getResponseState() == MessageState.BODY_STREAM), "Unexpected response state %s", state.getResponseState());
/*      */ 
/*      */     
/*  529 */     Outgoing outgoing = state.getOutgoing();
/*  530 */     Asserts.notNull(outgoing, "Outgoing response");
/*  531 */     HttpAsyncResponseProducer responseProducer = outgoing.getProducer();
/*      */     
/*  533 */     responseProducer.produceContent(encoder, (IOControl)conn);
/*      */     
/*  535 */     if (encoder.isCompleted()) {
/*  536 */       completeResponse(outgoing, conn, state);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void endOfInput(NHttpServerConnection conn) throws IOException {
/*  546 */     if (conn.getSocketTimeout() <= 0) {
/*  547 */       conn.setSocketTimeout(1000);
/*      */     }
/*  549 */     conn.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public void timeout(NHttpServerConnection conn) throws IOException {
/*  554 */     State state = getState((NHttpConnection)conn);
/*  555 */     if (state != null) {
/*  556 */       closeHandlers(state, new SocketTimeoutException(String.format("%,d milliseconds timeout on connection %s", new Object[] { Integer.valueOf(conn.getSocketTimeout()), conn })));
/*      */     }
/*      */     
/*  559 */     if (conn.getStatus() == 0) {
/*  560 */       conn.close();
/*  561 */       if (conn.getStatus() == 1)
/*      */       {
/*      */         
/*  564 */         conn.setSocketTimeout(250);
/*      */       }
/*      */     } else {
/*  567 */       conn.shutdown();
/*      */     } 
/*      */   }
/*      */   
/*      */   private State getState(NHttpConnection conn) {
/*  572 */     return (State)conn.getContext().getAttribute("http.nio.http-exchange-state");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void log(Exception ex) {
/*  583 */     this.exceptionLogger.log(ex);
/*      */   }
/*      */   
/*      */   private void shutdownConnection(NHttpConnection conn) {
/*      */     try {
/*  588 */       conn.shutdown();
/*  589 */     } catch (IOException ex) {
/*  590 */       log(ex);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void closeHandlers(State state, Exception ex) {
/*  595 */     HttpAsyncRequestConsumer<Object> consumer = (state.getIncoming() != null) ? state.getIncoming().getConsumer() : null;
/*      */     
/*  597 */     if (consumer != null) {
/*      */       try {
/*  599 */         consumer.failed(ex);
/*      */       } finally {
/*      */         try {
/*  602 */           consumer.close();
/*  603 */         } catch (IOException ioex) {
/*  604 */           log(ioex);
/*      */         } 
/*      */       } 
/*      */     }
/*  608 */     HttpAsyncResponseProducer producer = (state.getOutgoing() != null) ? state.getOutgoing().getProducer() : null;
/*      */     
/*  610 */     if (producer != null) {
/*      */       try {
/*  612 */         producer.failed(ex);
/*      */       } finally {
/*      */         try {
/*  615 */           producer.close();
/*  616 */         } catch (IOException ioex) {
/*  617 */           log(ioex);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void closeHandlers(State state) {
/*  624 */     HttpAsyncRequestConsumer<Object> consumer = (state.getIncoming() != null) ? state.getIncoming().getConsumer() : null;
/*      */     
/*  626 */     if (consumer != null) {
/*      */       try {
/*  628 */         consumer.close();
/*  629 */       } catch (IOException ioex) {
/*  630 */         log(ioex);
/*      */       } 
/*      */     }
/*  633 */     HttpAsyncResponseProducer producer = (state.getOutgoing() != null) ? state.getOutgoing().getProducer() : null;
/*      */     
/*  635 */     if (producer != null) {
/*      */       try {
/*  637 */         producer.close();
/*  638 */       } catch (IOException ioex) {
/*  639 */         log(ioex);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected HttpAsyncResponseProducer handleException(Exception ex, HttpContext context) {
/*  646 */     String message = ex.getMessage();
/*  647 */     if (message == null) {
/*  648 */       message = ex.toString();
/*      */     }
/*  650 */     HttpResponse response = createHttpResponse(toStatusCode(ex, context), context);
/*  651 */     return new ErrorResponseProducer(response, (HttpEntity)new NStringEntity(message, ContentType.DEFAULT_TEXT), false);
/*      */   }
/*      */   
/*      */   protected int toStatusCode(Exception ex, HttpContext context) {
/*      */     int code;
/*  656 */     if (ex instanceof org.apache.http.MethodNotSupportedException) {
/*  657 */       code = 501;
/*  658 */     } else if (ex instanceof org.apache.http.UnsupportedHttpVersionException) {
/*  659 */       code = 505;
/*  660 */     } else if (ex instanceof org.apache.http.ProtocolException) {
/*  661 */       code = 400;
/*      */     } else {
/*  663 */       code = 500;
/*      */     } 
/*  665 */     return code;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleAlreadySubmittedResponse(Cancellable cancellable, HttpContext context) {
/*  679 */     throw new IllegalStateException("Response already submitted");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleAlreadySubmittedResponse(HttpAsyncResponseProducer responseProducer, HttpContext context) {
/*  693 */     throw new IllegalStateException("Response already submitted");
/*      */   }
/*      */   
/*      */   private boolean canResponseHaveBody(HttpRequest request, HttpResponse response) {
/*  697 */     if (request != null && "HEAD".equalsIgnoreCase(request.getRequestLine().getMethod())) {
/*  698 */       return false;
/*      */     }
/*  700 */     int status = response.getStatusLine().getStatusCode();
/*  701 */     return (status >= 200 && status != 204 && status != 304 && status != 205);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void completeRequest(Incoming incoming, NHttpServerConnection conn, State state) throws IOException {
/*      */     PipelineEntry pipelineEntry;
/*  711 */     state.setRequestState(MessageState.READY);
/*  712 */     state.setIncoming(null);
/*      */ 
/*      */     
/*  715 */     HttpAsyncRequestConsumer<?> consumer = incoming.getConsumer();
/*      */     try {
/*  717 */       HttpContext context = incoming.getContext();
/*  718 */       consumer.requestCompleted(context);
/*  719 */       pipelineEntry = new PipelineEntry(incoming.getRequest(), consumer.getResult(), consumer.getException(), incoming.getHandler(), context);
/*      */ 
/*      */     
/*      */     }
/*      */     finally {
/*      */ 
/*      */       
/*  726 */       consumer.close();
/*      */     } 
/*  728 */     Queue<PipelineEntry> pipeline = state.getPipeline();
/*  729 */     pipeline.add(pipelineEntry);
/*  730 */     if (state.getResponseState() == MessageState.READY) {
/*  731 */       conn.requestOutput();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void commitFinalResponse(NHttpServerConnection conn, State state) throws IOException, HttpException {
/*  738 */     Outgoing outgoing = state.getOutgoing();
/*  739 */     Asserts.notNull(outgoing, "Outgoing response");
/*  740 */     HttpRequest request = outgoing.getRequest();
/*  741 */     HttpResponse response = outgoing.getResponse();
/*  742 */     HttpContext context = outgoing.getContext();
/*      */     
/*  744 */     context.setAttribute("http.response", response);
/*  745 */     this.httpProcessor.process(response, context);
/*      */     
/*  747 */     HttpEntity entity = response.getEntity();
/*  748 */     if (entity != null && !canResponseHaveBody(request, response)) {
/*  749 */       response.setEntity(null);
/*  750 */       entity = null;
/*      */     } 
/*      */     
/*  753 */     conn.submitResponse(response);
/*      */     
/*  755 */     if (entity == null) {
/*  756 */       completeResponse(outgoing, conn, state);
/*      */     } else {
/*  758 */       state.setResponseState(MessageState.BODY_STREAM);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void completeResponse(Outgoing outgoing, NHttpServerConnection conn, State state) throws IOException {
/*  766 */     HttpContext context = outgoing.getContext();
/*  767 */     HttpResponse response = outgoing.getResponse();
/*  768 */     HttpAsyncResponseProducer responseProducer = outgoing.getProducer();
/*      */     try {
/*  770 */       responseProducer.responseCompleted(context);
/*  771 */       state.setOutgoing(null);
/*  772 */       state.setCancellable(null);
/*  773 */       state.setResponseState(MessageState.READY);
/*      */     } finally {
/*  775 */       responseProducer.close();
/*      */     } 
/*  777 */     if (!this.connectionStrategy.keepAlive(response, context)) {
/*  778 */       conn.close();
/*      */     } else {
/*  780 */       conn.requestInput();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private HttpAsyncRequestHandler<Object> getRequestHandler(HttpRequest request) {
/*  786 */     HttpAsyncRequestHandler<Object> handler = null;
/*  787 */     if (this.handlerMapper != null) {
/*  788 */       handler = (HttpAsyncRequestHandler)this.handlerMapper.lookup(request);
/*      */     }
/*  790 */     if (handler == null) {
/*  791 */       handler = NullRequestHandler.INSTANCE;
/*      */     }
/*  793 */     return handler;
/*      */   }
/*      */ 
/*      */   
/*      */   static class Incoming
/*      */   {
/*      */     private final HttpRequest request;
/*      */     
/*      */     private final HttpAsyncRequestHandler<Object> handler;
/*      */     
/*      */     private final HttpAsyncRequestConsumer<Object> consumer;
/*      */     
/*      */     private final HttpContext context;
/*      */     
/*      */     Incoming(HttpRequest request, HttpAsyncRequestHandler<Object> handler, HttpAsyncRequestConsumer<Object> consumer, HttpContext context) {
/*  808 */       this.request = request;
/*  809 */       this.handler = handler;
/*  810 */       this.consumer = consumer;
/*  811 */       this.context = context;
/*      */     }
/*      */     
/*      */     public HttpRequest getRequest() {
/*  815 */       return this.request;
/*      */     }
/*      */     
/*      */     public HttpAsyncRequestHandler<Object> getHandler() {
/*  819 */       return this.handler;
/*      */     }
/*      */     
/*      */     public HttpAsyncRequestConsumer<Object> getConsumer() {
/*  823 */       return this.consumer;
/*      */     }
/*      */     
/*      */     public HttpContext getContext() {
/*  827 */       return this.context;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class Outgoing
/*      */   {
/*      */     private final HttpRequest request;
/*      */     
/*      */     private final HttpResponse response;
/*      */     
/*      */     private final HttpAsyncResponseProducer producer;
/*      */     
/*      */     private final HttpContext context;
/*      */     
/*      */     Outgoing(HttpRequest request, HttpResponse response, HttpAsyncResponseProducer producer, HttpContext context) {
/*  843 */       this.request = request;
/*  844 */       this.response = response;
/*  845 */       this.producer = producer;
/*  846 */       this.context = context;
/*      */     }
/*      */     
/*      */     public HttpRequest getRequest() {
/*  850 */       return this.request;
/*      */     }
/*      */     
/*      */     public HttpResponse getResponse() {
/*  854 */       return this.response;
/*      */     }
/*      */     
/*      */     public HttpAsyncResponseProducer getProducer() {
/*  858 */       return this.producer;
/*      */     }
/*      */     
/*      */     public HttpContext getContext() {
/*  862 */       return this.context;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static class PipelineEntry
/*      */   {
/*      */     private final HttpRequest request;
/*      */     
/*      */     private final Object result;
/*      */     
/*      */     private final Exception exception;
/*      */     
/*      */     private final HttpAsyncRequestHandler<Object> handler;
/*      */     
/*      */     private final HttpContext context;
/*      */     
/*      */     PipelineEntry(HttpRequest request, Object result, Exception exception, HttpAsyncRequestHandler<Object> handler, HttpContext context) {
/*  880 */       this.request = request;
/*  881 */       this.result = result;
/*  882 */       this.exception = exception;
/*  883 */       this.handler = handler;
/*  884 */       this.context = context;
/*      */     }
/*      */     
/*      */     public HttpRequest getRequest() {
/*  888 */       return this.request;
/*      */     }
/*      */     
/*      */     public Object getResult() {
/*  892 */       return this.result;
/*      */     }
/*      */     
/*      */     public Exception getException() {
/*  896 */       return this.exception;
/*      */     }
/*      */     
/*      */     public HttpAsyncRequestHandler<Object> getHandler() {
/*  900 */       return this.handler;
/*      */     }
/*      */     
/*      */     public HttpContext getContext() {
/*  904 */       return this.context;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class State
/*      */   {
/*  921 */     private final Queue<HttpAsyncService.PipelineEntry> pipeline = new ConcurrentLinkedQueue<HttpAsyncService.PipelineEntry>(); private volatile boolean terminated;
/*  922 */     private volatile MessageState requestState = MessageState.READY;
/*  923 */     private volatile MessageState responseState = MessageState.READY;
/*      */     private volatile HttpAsyncService.Incoming incoming;
/*      */     
/*      */     public boolean isTerminated() {
/*  927 */       return this.terminated;
/*      */     }
/*      */     private volatile HttpAsyncService.Outgoing outgoing; private volatile Cancellable cancellable;
/*      */     public void setTerminated() {
/*  931 */       this.terminated = true;
/*      */     }
/*      */     
/*      */     public MessageState getRequestState() {
/*  935 */       return this.requestState;
/*      */     }
/*      */     
/*      */     public void setRequestState(MessageState state) {
/*  939 */       this.requestState = state;
/*      */     }
/*      */     
/*      */     public MessageState getResponseState() {
/*  943 */       return this.responseState;
/*      */     }
/*      */     
/*      */     public void setResponseState(MessageState state) {
/*  947 */       this.responseState = state;
/*      */     }
/*      */     
/*      */     public HttpAsyncService.Incoming getIncoming() {
/*  951 */       return this.incoming;
/*      */     }
/*      */     
/*      */     public void setIncoming(HttpAsyncService.Incoming incoming) {
/*  955 */       this.incoming = incoming;
/*      */     }
/*      */     
/*      */     public HttpAsyncService.Outgoing getOutgoing() {
/*  959 */       return this.outgoing;
/*      */     }
/*      */     
/*      */     public void setOutgoing(HttpAsyncService.Outgoing outgoing) {
/*  963 */       this.outgoing = outgoing;
/*      */     }
/*      */     
/*      */     public Cancellable getCancellable() {
/*  967 */       return this.cancellable;
/*      */     }
/*      */     
/*      */     public void setCancellable(Cancellable cancellable) {
/*  971 */       this.cancellable = cancellable;
/*      */     }
/*      */     
/*      */     public Queue<HttpAsyncService.PipelineEntry> getPipeline() {
/*  975 */       return this.pipeline;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  980 */       StringBuilder buf = new StringBuilder();
/*  981 */       buf.append("[incoming ");
/*  982 */       buf.append(this.requestState);
/*  983 */       if (this.incoming != null) {
/*  984 */         buf.append(" ");
/*  985 */         buf.append(this.incoming.getRequest().getRequestLine());
/*      */       } 
/*  987 */       buf.append("; outgoing ");
/*  988 */       buf.append(this.responseState);
/*  989 */       if (this.outgoing != null) {
/*  990 */         buf.append(" ");
/*  991 */         buf.append(this.outgoing.getResponse().getStatusLine());
/*      */       } 
/*  993 */       buf.append("]");
/*  994 */       return buf.toString();
/*      */     }
/*      */   }
/*      */   
/*      */   class HttpAsyncExchangeImpl
/*      */     implements HttpAsyncExchange
/*      */   {
/* 1001 */     private final AtomicBoolean completed = new AtomicBoolean();
/*      */     
/*      */     private final HttpRequest request;
/*      */     
/*      */     private final HttpResponse response;
/*      */     
/*      */     private final HttpAsyncService.State state;
/*      */     
/*      */     private final NHttpServerConnection conn;
/*      */     
/*      */     private final HttpContext context;
/*      */ 
/*      */     
/*      */     public HttpAsyncExchangeImpl(HttpRequest request, HttpResponse response, HttpAsyncService.State state, NHttpServerConnection conn, HttpContext context) {
/* 1015 */       this.request = request;
/* 1016 */       this.response = response;
/* 1017 */       this.state = state;
/* 1018 */       this.conn = conn;
/* 1019 */       this.context = context;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpRequest getRequest() {
/* 1024 */       return this.request;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpResponse getResponse() {
/* 1029 */       return this.response;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setCallback(Cancellable cancellable) {
/* 1034 */       if (this.completed.get()) {
/* 1035 */         HttpAsyncService.this.handleAlreadySubmittedResponse(cancellable, this.context);
/* 1036 */       } else if (this.state.isTerminated() && cancellable != null) {
/* 1037 */         cancellable.cancel();
/*      */       } else {
/* 1039 */         this.state.setCancellable(cancellable);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void submitResponse(HttpAsyncResponseProducer responseProducer) {
/* 1045 */       Args.notNull(responseProducer, "Response producer");
/* 1046 */       if (this.completed.getAndSet(true)) {
/* 1047 */         HttpAsyncService.this.handleAlreadySubmittedResponse(responseProducer, this.context);
/* 1048 */       } else if (!this.state.isTerminated()) {
/* 1049 */         HttpResponse response = responseProducer.generateResponse();
/* 1050 */         HttpAsyncService.Outgoing outgoing = new HttpAsyncService.Outgoing(this.request, response, responseProducer, this.context);
/*      */ 
/*      */         
/* 1053 */         synchronized (this.state) {
/* 1054 */           this.state.setOutgoing(outgoing);
/* 1055 */           this.state.setCancellable(null);
/* 1056 */           this.conn.requestOutput();
/*      */         } 
/*      */       } else {
/*      */         
/*      */         try {
/* 1061 */           responseProducer.close();
/* 1062 */         } catch (IOException ex) {
/* 1063 */           HttpAsyncService.this.log(ex);
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void submitResponse() {
/* 1070 */       submitResponse(new BasicAsyncResponseProducer(this.response));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isCompleted() {
/* 1075 */       return this.completed.get();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setTimeout(int timeout) {
/* 1080 */       this.conn.setSocketTimeout(timeout);
/*      */     }
/*      */ 
/*      */     
/*      */     public int getTimeout() {
/* 1085 */       return this.conn.getSocketTimeout();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   private static class HttpAsyncRequestHandlerResolverAdapter
/*      */     implements HttpAsyncRequestHandlerMapper
/*      */   {
/*      */     private final HttpAsyncRequestHandlerResolver resolver;
/*      */ 
/*      */ 
/*      */     
/*      */     public HttpAsyncRequestHandlerResolverAdapter(HttpAsyncRequestHandlerResolver resolver) {
/* 1101 */       this.resolver = resolver;
/*      */     }
/*      */ 
/*      */     
/*      */     public HttpAsyncRequestHandler<?> lookup(HttpRequest request) {
/* 1106 */       return this.resolver.lookup(request.getRequestLine().getUri());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpResponseFactory getResponseFactory() {
/* 1118 */     return this.responseFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpProcessor getHttpProcessor() {
/* 1128 */     return this.httpProcessor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ConnectionReuseStrategy getConnectionStrategy() {
/* 1138 */     return this.connectionStrategy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpAsyncRequestHandlerMapper getHandlerMapper() {
/* 1148 */     return this.handlerMapper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HttpAsyncExpectationVerifier getExpectationVerifier() {
/* 1158 */     return this.expectationVerifier;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExceptionLogger getExceptionLogger() {
/* 1168 */     return this.exceptionLogger;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/nio/protocol/HttpAsyncService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */