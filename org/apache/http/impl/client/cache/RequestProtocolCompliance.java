/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.StatusLine;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.entity.HttpEntityWrapper;
/*     */ import org.apache.http.message.BasicHeader;
/*     */ import org.apache.http.message.BasicHttpResponse;
/*     */ import org.apache.http.message.BasicStatusLine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ class RequestProtocolCompliance
/*     */ {
/*     */   private final boolean weakETagOnPutDeleteAllowed;
/*     */   
/*     */   public RequestProtocolCompliance() {
/*  64 */     this.weakETagOnPutDeleteAllowed = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestProtocolCompliance(boolean weakETagOnPutDeleteAllowed) {
/*  69 */     this.weakETagOnPutDeleteAllowed = weakETagOnPutDeleteAllowed;
/*     */   }
/*     */   
/*  72 */   private static final List<String> disallowedWithNoCache = Arrays.asList(new String[] { "min-fresh", "max-stale", "max-age" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<RequestProtocolError> requestIsFatallyNonCompliant(HttpRequest request) {
/*  83 */     List<RequestProtocolError> theErrors = new ArrayList<RequestProtocolError>();
/*     */     
/*  85 */     RequestProtocolError anError = requestHasWeakETagAndRange(request);
/*  86 */     if (anError != null) {
/*  87 */       theErrors.add(anError);
/*     */     }
/*     */     
/*  90 */     if (!this.weakETagOnPutDeleteAllowed) {
/*  91 */       anError = requestHasWeekETagForPUTOrDELETEIfMatch(request);
/*  92 */       if (anError != null) {
/*  93 */         theErrors.add(anError);
/*     */       }
/*     */     } 
/*     */     
/*  97 */     anError = requestContainsNoCacheDirectiveWithFieldName(request);
/*  98 */     if (anError != null) {
/*  99 */       theErrors.add(anError);
/*     */     }
/*     */     
/* 102 */     return theErrors;
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
/*     */   public void makeRequestCompliant(HttpRequestWrapper request) throws ClientProtocolException {
/* 115 */     if (requestMustNotHaveEntity((HttpRequest)request)) {
/* 116 */       ((HttpEntityEnclosingRequest)request).setEntity(null);
/*     */     }
/*     */     
/* 119 */     verifyRequestWithExpectContinueFlagHas100continueHeader((HttpRequest)request);
/* 120 */     verifyOPTIONSRequestWithBodyHasContentType((HttpRequest)request);
/* 121 */     decrementOPTIONSMaxForwardsIfGreaterThen0((HttpRequest)request);
/* 122 */     stripOtherFreshnessDirectivesWithNoCache((HttpRequest)request);
/*     */     
/* 124 */     if (requestVersionIsTooLow((HttpRequest)request) || requestMinorVersionIsTooHighMajorVersionsMatch((HttpRequest)request))
/*     */     {
/* 126 */       request.setProtocolVersion((ProtocolVersion)HttpVersion.HTTP_1_1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void stripOtherFreshnessDirectivesWithNoCache(HttpRequest request) {
/* 131 */     List<HeaderElement> outElts = new ArrayList<HeaderElement>();
/* 132 */     boolean shouldStrip = false;
/* 133 */     for (Header h : request.getHeaders("Cache-Control")) {
/* 134 */       for (HeaderElement elt : h.getElements()) {
/* 135 */         if (!disallowedWithNoCache.contains(elt.getName())) {
/* 136 */           outElts.add(elt);
/*     */         }
/* 138 */         if ("no-cache".equals(elt.getName())) {
/* 139 */           shouldStrip = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 143 */     if (!shouldStrip) {
/*     */       return;
/*     */     }
/* 146 */     request.removeHeaders("Cache-Control");
/* 147 */     request.setHeader("Cache-Control", buildHeaderFromElements(outElts));
/*     */   }
/*     */   
/*     */   private String buildHeaderFromElements(List<HeaderElement> outElts) {
/* 151 */     StringBuilder newHdr = new StringBuilder("");
/* 152 */     boolean first = true;
/* 153 */     for (HeaderElement elt : outElts) {
/* 154 */       if (!first) {
/* 155 */         newHdr.append(",");
/*     */       } else {
/* 157 */         first = false;
/*     */       } 
/* 159 */       newHdr.append(elt.toString());
/*     */     } 
/* 161 */     return newHdr.toString();
/*     */   }
/*     */   
/*     */   private boolean requestMustNotHaveEntity(HttpRequest request) {
/* 165 */     return ("TRACE".equals(request.getRequestLine().getMethod()) && request instanceof HttpEntityEnclosingRequest);
/*     */   }
/*     */ 
/*     */   
/*     */   private void decrementOPTIONSMaxForwardsIfGreaterThen0(HttpRequest request) {
/* 170 */     if (!"OPTIONS".equals(request.getRequestLine().getMethod())) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     Header maxForwards = request.getFirstHeader("Max-Forwards");
/* 175 */     if (maxForwards == null) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     request.removeHeaders("Max-Forwards");
/* 180 */     int currentMaxForwards = Integer.parseInt(maxForwards.getValue());
/*     */     
/* 182 */     request.setHeader("Max-Forwards", Integer.toString(currentMaxForwards - 1));
/*     */   }
/*     */   
/*     */   private void verifyOPTIONSRequestWithBodyHasContentType(HttpRequest request) {
/* 186 */     if (!"OPTIONS".equals(request.getRequestLine().getMethod())) {
/*     */       return;
/*     */     }
/*     */     
/* 190 */     if (!(request instanceof HttpEntityEnclosingRequest)) {
/*     */       return;
/*     */     }
/*     */     
/* 194 */     addContentTypeHeaderIfMissing((HttpEntityEnclosingRequest)request);
/*     */   }
/*     */   
/*     */   private void addContentTypeHeaderIfMissing(HttpEntityEnclosingRequest request) {
/* 198 */     HttpEntity entity = request.getEntity();
/* 199 */     if (entity != null && entity.getContentType() == null) {
/* 200 */       HttpEntityWrapper entityWrapper = new HttpEntityWrapper(entity)
/*     */         {
/*     */           public Header getContentType()
/*     */           {
/* 204 */             return (Header)new BasicHeader("Content-Type", ContentType.APPLICATION_OCTET_STREAM.getMimeType());
/*     */           }
/*     */         };
/*     */       
/* 208 */       request.setEntity((HttpEntity)entityWrapper);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void verifyRequestWithExpectContinueFlagHas100continueHeader(HttpRequest request) {
/* 213 */     if (request instanceof HttpEntityEnclosingRequest) {
/*     */       
/* 215 */       if (((HttpEntityEnclosingRequest)request).expectContinue() && ((HttpEntityEnclosingRequest)request).getEntity() != null) {
/*     */         
/* 217 */         add100ContinueHeaderIfMissing(request);
/*     */       } else {
/* 219 */         remove100ContinueHeaderIfExists(request);
/*     */       } 
/*     */     } else {
/* 222 */       remove100ContinueHeaderIfExists(request);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void remove100ContinueHeaderIfExists(HttpRequest request) {
/* 227 */     boolean hasHeader = false;
/*     */     
/* 229 */     Header[] expectHeaders = request.getHeaders("Expect");
/* 230 */     List<HeaderElement> expectElementsThatAreNot100Continue = new ArrayList<HeaderElement>();
/*     */     
/* 232 */     for (Header h : expectHeaders) {
/* 233 */       for (HeaderElement elt : h.getElements()) {
/* 234 */         if (!"100-continue".equalsIgnoreCase(elt.getName())) {
/* 235 */           expectElementsThatAreNot100Continue.add(elt);
/*     */         } else {
/* 237 */           hasHeader = true;
/*     */         } 
/*     */       } 
/*     */       
/* 241 */       if (hasHeader) {
/* 242 */         request.removeHeader(h);
/* 243 */         for (HeaderElement elt : expectElementsThatAreNot100Continue) {
/* 244 */           BasicHeader newHeader = new BasicHeader("Expect", elt.getName());
/* 245 */           request.addHeader((Header)newHeader);
/*     */         } 
/*     */         return;
/*     */       } 
/* 249 */       expectElementsThatAreNot100Continue = new ArrayList<HeaderElement>();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void add100ContinueHeaderIfMissing(HttpRequest request) {
/* 255 */     boolean hasHeader = false;
/*     */     
/* 257 */     for (Header h : request.getHeaders("Expect")) {
/* 258 */       for (HeaderElement elt : h.getElements()) {
/* 259 */         if ("100-continue".equalsIgnoreCase(elt.getName())) {
/* 260 */           hasHeader = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 265 */     if (!hasHeader) {
/* 266 */       request.addHeader("Expect", "100-continue");
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean requestMinorVersionIsTooHighMajorVersionsMatch(HttpRequest request) {
/* 271 */     ProtocolVersion requestProtocol = request.getProtocolVersion();
/* 272 */     if (requestProtocol.getMajor() != HttpVersion.HTTP_1_1.getMajor()) {
/* 273 */       return false;
/*     */     }
/*     */     
/* 276 */     if (requestProtocol.getMinor() > HttpVersion.HTTP_1_1.getMinor()) {
/* 277 */       return true;
/*     */     }
/*     */     
/* 280 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean requestVersionIsTooLow(HttpRequest request) {
/* 284 */     return (request.getProtocolVersion().compareToVersion((ProtocolVersion)HttpVersion.HTTP_1_1) < 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpResponse getErrorForRequest(RequestProtocolError errorCheck) {
/* 295 */     switch (errorCheck) {
/*     */       case BODY_BUT_NO_LENGTH_ERROR:
/* 297 */         return (HttpResponse)new BasicHttpResponse((StatusLine)new BasicStatusLine((ProtocolVersion)HttpVersion.HTTP_1_1, 411, ""));
/*     */ 
/*     */       
/*     */       case WEAK_ETAG_AND_RANGE_ERROR:
/* 301 */         return (HttpResponse)new BasicHttpResponse((StatusLine)new BasicStatusLine((ProtocolVersion)HttpVersion.HTTP_1_1, 400, "Weak eTag not compatible with byte range"));
/*     */ 
/*     */       
/*     */       case WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR:
/* 305 */         return (HttpResponse)new BasicHttpResponse((StatusLine)new BasicStatusLine((ProtocolVersion)HttpVersion.HTTP_1_1, 400, "Weak eTag not compatible with PUT or DELETE requests"));
/*     */ 
/*     */ 
/*     */       
/*     */       case NO_CACHE_DIRECTIVE_WITH_FIELD_NAME:
/* 310 */         return (HttpResponse)new BasicHttpResponse((StatusLine)new BasicStatusLine((ProtocolVersion)HttpVersion.HTTP_1_1, 400, "No-Cache directive MUST NOT include a field name"));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 315 */     throw new IllegalStateException("The request was compliant, therefore no error can be generated for it.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestProtocolError requestHasWeakETagAndRange(HttpRequest request) {
/* 323 */     String method = request.getRequestLine().getMethod();
/* 324 */     if (!"GET".equals(method)) {
/* 325 */       return null;
/*     */     }
/*     */     
/* 328 */     Header range = request.getFirstHeader("Range");
/* 329 */     if (range == null) {
/* 330 */       return null;
/*     */     }
/*     */     
/* 333 */     Header ifRange = request.getFirstHeader("If-Range");
/* 334 */     if (ifRange == null) {
/* 335 */       return null;
/*     */     }
/*     */     
/* 338 */     String val = ifRange.getValue();
/* 339 */     if (val.startsWith("W/")) {
/* 340 */       return RequestProtocolError.WEAK_ETAG_AND_RANGE_ERROR;
/*     */     }
/*     */     
/* 343 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestProtocolError requestHasWeekETagForPUTOrDELETEIfMatch(HttpRequest request) {
/* 349 */     String method = request.getRequestLine().getMethod();
/* 350 */     if (!"PUT".equals(method) && !"DELETE".equals(method))
/*     */     {
/* 352 */       return null;
/*     */     }
/*     */     
/* 355 */     Header ifMatch = request.getFirstHeader("If-Match");
/* 356 */     if (ifMatch != null) {
/* 357 */       String val = ifMatch.getValue();
/* 358 */       if (val.startsWith("W/")) {
/* 359 */         return RequestProtocolError.WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR;
/*     */       }
/*     */     } else {
/* 362 */       Header ifNoneMatch = request.getFirstHeader("If-None-Match");
/* 363 */       if (ifNoneMatch == null) {
/* 364 */         return null;
/*     */       }
/*     */       
/* 367 */       String val2 = ifNoneMatch.getValue();
/* 368 */       if (val2.startsWith("W/")) {
/* 369 */         return RequestProtocolError.WEAK_ETAG_ON_PUTDELETE_METHOD_ERROR;
/*     */       }
/*     */     } 
/*     */     
/* 373 */     return null;
/*     */   }
/*     */   
/*     */   private RequestProtocolError requestContainsNoCacheDirectiveWithFieldName(HttpRequest request) {
/* 377 */     for (Header h : request.getHeaders("Cache-Control")) {
/* 378 */       for (HeaderElement elt : h.getElements()) {
/* 379 */         if ("no-cache".equalsIgnoreCase(elt.getName()) && elt.getValue() != null)
/*     */         {
/* 381 */           return RequestProtocolError.NO_CACHE_DIRECTIVE_WITH_FIELD_NAME;
/*     */         }
/*     */       } 
/*     */     } 
/* 385 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/RequestProtocolCompliance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */