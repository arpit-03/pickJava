/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.methods.HttpRequestWrapper;
/*     */ import org.apache.http.client.utils.DateUtils;
/*     */ import org.apache.http.message.BasicHeader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ class ResponseProtocolCompliance
/*     */ {
/*     */   private static final String UNEXPECTED_100_CONTINUE = "The incoming request did not contain a 100-continue header, but the response was a Status 100, continue.";
/*     */   private static final String UNEXPECTED_PARTIAL_CONTENT = "partial content was returned for a request that did not ask for it";
/*     */   
/*     */   public void ensureProtocolCompliance(HttpRequestWrapper request, HttpResponse response) throws IOException {
/*  72 */     if (backendResponseMustNotHaveBody((HttpRequest)request, response)) {
/*  73 */       consumeBody(response);
/*  74 */       response.setEntity(null);
/*     */     } 
/*     */     
/*  77 */     requestDidNotExpect100ContinueButResponseIsOne(request, response);
/*     */     
/*  79 */     transferEncodingIsNotReturnedTo1_0Client(request, response);
/*     */     
/*  81 */     ensurePartialContentIsNotSentToAClientThatDidNotRequestIt((HttpRequest)request, response);
/*     */     
/*  83 */     ensure200ForOPTIONSRequestWithNoBodyHasContentLengthZero((HttpRequest)request, response);
/*     */     
/*  85 */     ensure206ContainsDateHeader(response);
/*     */     
/*  87 */     ensure304DoesNotContainExtraEntityHeaders(response);
/*     */     
/*  89 */     identityIsNotUsedInContentEncoding(response);
/*     */     
/*  91 */     warningsWithNonMatchingWarnDatesAreRemoved(response);
/*     */   }
/*     */   
/*     */   private void consumeBody(HttpResponse response) throws IOException {
/*  95 */     HttpEntity body = response.getEntity();
/*  96 */     if (body != null) {
/*  97 */       IOUtils.consume(body);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void warningsWithNonMatchingWarnDatesAreRemoved(HttpResponse response) {
/* 103 */     Date responseDate = DateUtils.parseDate(response.getFirstHeader("Date").getValue());
/* 104 */     if (responseDate == null) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     Header[] warningHeaders = response.getHeaders("Warning");
/*     */     
/* 110 */     if (warningHeaders == null || warningHeaders.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 114 */     List<Header> newWarningHeaders = new ArrayList<Header>();
/* 115 */     boolean modified = false;
/* 116 */     for (Header h : warningHeaders) {
/* 117 */       for (WarningValue wv : WarningValue.getWarningValues(h)) {
/* 118 */         Date warnDate = wv.getWarnDate();
/* 119 */         if (warnDate == null || warnDate.equals(responseDate)) {
/* 120 */           newWarningHeaders.add(new BasicHeader("Warning", wv.toString()));
/*     */         } else {
/* 122 */           modified = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 126 */     if (modified) {
/* 127 */       response.removeHeaders("Warning");
/* 128 */       for (Header h : newWarningHeaders) {
/* 129 */         response.addHeader(h);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void identityIsNotUsedInContentEncoding(HttpResponse response) {
/* 135 */     Header[] hdrs = response.getHeaders("Content-Encoding");
/* 136 */     if (hdrs == null || hdrs.length == 0) {
/*     */       return;
/*     */     }
/* 139 */     List<Header> newHeaders = new ArrayList<Header>();
/* 140 */     boolean modified = false;
/* 141 */     for (Header h : hdrs) {
/* 142 */       StringBuilder buf = new StringBuilder();
/* 143 */       boolean first = true;
/* 144 */       for (HeaderElement elt : h.getElements()) {
/* 145 */         if ("identity".equalsIgnoreCase(elt.getName())) {
/* 146 */           modified = true;
/*     */         } else {
/* 148 */           if (!first) {
/* 149 */             buf.append(",");
/*     */           }
/* 151 */           buf.append(elt.toString());
/* 152 */           first = false;
/*     */         } 
/*     */       } 
/* 155 */       String newHeaderValue = buf.toString();
/* 156 */       if (!"".equals(newHeaderValue)) {
/* 157 */         newHeaders.add(new BasicHeader("Content-Encoding", newHeaderValue));
/*     */       }
/*     */     } 
/* 160 */     if (!modified) {
/*     */       return;
/*     */     }
/* 163 */     response.removeHeaders("Content-Encoding");
/* 164 */     for (Header h : newHeaders) {
/* 165 */       response.addHeader(h);
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensure206ContainsDateHeader(HttpResponse response) {
/* 170 */     if (response.getFirstHeader("Date") == null) {
/* 171 */       response.addHeader("Date", DateUtils.formatDate(new Date()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensurePartialContentIsNotSentToAClientThatDidNotRequestIt(HttpRequest request, HttpResponse response) throws IOException {
/* 178 */     if (request.getFirstHeader("Range") != null || response.getStatusLine().getStatusCode() != 206) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 183 */     consumeBody(response);
/* 184 */     throw new ClientProtocolException("partial content was returned for a request that did not ask for it");
/*     */   }
/*     */ 
/*     */   
/*     */   private void ensure200ForOPTIONSRequestWithNoBodyHasContentLengthZero(HttpRequest request, HttpResponse response) {
/* 189 */     if (!request.getRequestLine().getMethod().equalsIgnoreCase("OPTIONS")) {
/*     */       return;
/*     */     }
/*     */     
/* 193 */     if (response.getStatusLine().getStatusCode() != 200) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     if (response.getFirstHeader("Content-Length") == null) {
/* 198 */       response.addHeader("Content-Length", "0");
/*     */     }
/*     */   }
/*     */   
/*     */   private void ensure304DoesNotContainExtraEntityHeaders(HttpResponse response) {
/* 203 */     String[] disallowedEntityHeaders = { "Allow", "Content-Encoding", "Content-Language", "Content-Length", "Content-MD5", "Content-Range", "Content-Type", "Last-Modified" };
/*     */ 
/*     */ 
/*     */     
/* 207 */     if (response.getStatusLine().getStatusCode() == 304) {
/* 208 */       for (String hdr : disallowedEntityHeaders) {
/* 209 */         response.removeHeaders(hdr);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean backendResponseMustNotHaveBody(HttpRequest request, HttpResponse backendResponse) {
/* 215 */     return ("HEAD".equals(request.getRequestLine().getMethod()) || backendResponse.getStatusLine().getStatusCode() == 204 || backendResponse.getStatusLine().getStatusCode() == 205 || backendResponse.getStatusLine().getStatusCode() == 304);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void requestDidNotExpect100ContinueButResponseIsOne(HttpRequestWrapper request, HttpResponse response) throws IOException {
/* 223 */     if (response.getStatusLine().getStatusCode() != 100) {
/*     */       return;
/*     */     }
/*     */     
/* 227 */     HttpRequest originalRequest = request.getOriginal();
/* 228 */     if (originalRequest instanceof HttpEntityEnclosingRequest && (
/* 229 */       (HttpEntityEnclosingRequest)originalRequest).expectContinue()) {
/*     */       return;
/*     */     }
/*     */     
/* 233 */     consumeBody(response);
/* 234 */     throw new ClientProtocolException("The incoming request did not contain a 100-continue header, but the response was a Status 100, continue.");
/*     */   }
/*     */ 
/*     */   
/*     */   private void transferEncodingIsNotReturnedTo1_0Client(HttpRequestWrapper request, HttpResponse response) {
/* 239 */     HttpRequest originalRequest = request.getOriginal();
/* 240 */     if (originalRequest.getProtocolVersion().compareToVersion((ProtocolVersion)HttpVersion.HTTP_1_1) >= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     removeResponseTransferEncoding(response);
/*     */   }
/*     */   
/*     */   private void removeResponseTransferEncoding(HttpResponse response) {
/* 248 */     response.removeHeaders("TE");
/* 249 */     response.removeHeaders("Transfer-Encoding");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ResponseProtocolCompliance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */