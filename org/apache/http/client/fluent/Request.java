/*     */ package org.apache.http.client.fluent;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.nio.charset.Charset;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.http.Consts;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.HttpEntityEnclosingRequest;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpResponse;
/*     */ import org.apache.http.HttpVersion;
/*     */ import org.apache.http.NameValuePair;
/*     */ import org.apache.http.ProtocolVersion;
/*     */ import org.apache.http.client.ClientProtocolException;
/*     */ import org.apache.http.client.HttpClient;
/*     */ import org.apache.http.client.config.RequestConfig;
/*     */ import org.apache.http.client.methods.Configurable;
/*     */ import org.apache.http.client.utils.URLEncodedUtils;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.protocol.HttpContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Request
/*     */ {
/*     */   public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
/*  70 */   public static final Locale DATE_LOCALE = Locale.US;
/*  71 */   public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT");
/*     */   
/*     */   private final InternalHttpRequest request;
/*     */   
/*     */   private Boolean useExpectContinue;
/*     */   private Integer socketTmeout;
/*     */   private Integer connectTimeout;
/*     */   private HttpHost proxy;
/*     */   private SimpleDateFormat dateFormatter;
/*     */   
/*     */   public static Request Get(URI uri) {
/*  82 */     return new Request(new InternalHttpRequest("GET", uri));
/*     */   }
/*     */   
/*     */   public static Request Get(String uri) {
/*  86 */     return new Request(new InternalHttpRequest("GET", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Head(URI uri) {
/*  90 */     return new Request(new InternalHttpRequest("HEAD", uri));
/*     */   }
/*     */   
/*     */   public static Request Head(String uri) {
/*  94 */     return new Request(new InternalHttpRequest("HEAD", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Post(URI uri) {
/*  98 */     return new Request(new InternalEntityEnclosingHttpRequest("POST", uri));
/*     */   }
/*     */   
/*     */   public static Request Post(String uri) {
/* 102 */     return new Request(new InternalEntityEnclosingHttpRequest("POST", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Patch(URI uri) {
/* 106 */     return new Request(new InternalEntityEnclosingHttpRequest("PATCH", uri));
/*     */   }
/*     */   
/*     */   public static Request Patch(String uri) {
/* 110 */     return new Request(new InternalEntityEnclosingHttpRequest("PATCH", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Put(URI uri) {
/* 114 */     return new Request(new InternalEntityEnclosingHttpRequest("PUT", uri));
/*     */   }
/*     */   
/*     */   public static Request Put(String uri) {
/* 118 */     return new Request(new InternalEntityEnclosingHttpRequest("PUT", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Trace(URI uri) {
/* 122 */     return new Request(new InternalHttpRequest("TRACE", uri));
/*     */   }
/*     */   
/*     */   public static Request Trace(String uri) {
/* 126 */     return new Request(new InternalHttpRequest("TRACE", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Delete(URI uri) {
/* 130 */     return new Request(new InternalHttpRequest("DELETE", uri));
/*     */   }
/*     */   
/*     */   public static Request Delete(String uri) {
/* 134 */     return new Request(new InternalHttpRequest("DELETE", URI.create(uri)));
/*     */   }
/*     */   
/*     */   public static Request Options(URI uri) {
/* 138 */     return new Request(new InternalHttpRequest("OPTIONS", uri));
/*     */   }
/*     */   
/*     */   public static Request Options(String uri) {
/* 142 */     return new Request(new InternalHttpRequest("OPTIONS", URI.create(uri)));
/*     */   }
/*     */ 
/*     */   
/*     */   Request(InternalHttpRequest request) {
/* 147 */     this.request = request;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   HttpResponse internalExecute(HttpClient client, HttpContext localContext) throws ClientProtocolException, IOException {
/*     */     RequestConfig.Builder builder;
/* 154 */     if (client instanceof Configurable) {
/* 155 */       builder = RequestConfig.copy(((Configurable)client).getConfig());
/*     */     } else {
/* 157 */       builder = RequestConfig.custom();
/*     */     } 
/* 159 */     if (this.useExpectContinue != null) {
/* 160 */       builder.setExpectContinueEnabled(this.useExpectContinue.booleanValue());
/*     */     }
/* 162 */     if (this.socketTmeout != null) {
/* 163 */       builder.setSocketTimeout(this.socketTmeout.intValue());
/*     */     }
/* 165 */     if (this.connectTimeout != null) {
/* 166 */       builder.setConnectTimeout(this.connectTimeout.intValue());
/*     */     }
/* 168 */     if (this.proxy != null) {
/* 169 */       builder.setProxy(this.proxy);
/*     */     }
/* 171 */     RequestConfig config = builder.build();
/* 172 */     this.request.setConfig(config);
/* 173 */     return client.execute(this.request, localContext);
/*     */   }
/*     */   
/*     */   public Response execute() throws ClientProtocolException, IOException {
/* 177 */     return new Response(internalExecute(Executor.CLIENT, null));
/*     */   }
/*     */   
/*     */   public void abort() throws UnsupportedOperationException {
/* 181 */     this.request.abort();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Request addHeader(Header header) {
/* 187 */     this.request.addHeader(header);
/* 188 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request setHeader(Header header) {
/* 195 */     this.request.setHeader(header);
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public Request addHeader(String name, String value) {
/* 200 */     this.request.addHeader(name, value);
/* 201 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request setHeader(String name, String value) {
/* 208 */     this.request.setHeader(name, value);
/* 209 */     return this;
/*     */   }
/*     */   
/*     */   public Request removeHeader(Header header) {
/* 213 */     this.request.removeHeader(header);
/* 214 */     return this;
/*     */   }
/*     */   
/*     */   public Request removeHeaders(String name) {
/* 218 */     this.request.removeHeaders(name);
/* 219 */     return this;
/*     */   }
/*     */   
/*     */   public Request setHeaders(Header... headers) {
/* 223 */     this.request.setHeaders(headers);
/* 224 */     return this;
/*     */   }
/*     */   
/*     */   public Request setCacheControl(String cacheControl) {
/* 228 */     this.request.setHeader("Cache-Control", cacheControl);
/* 229 */     return this;
/*     */   }
/*     */   
/*     */   private SimpleDateFormat getDateFormat() {
/* 233 */     if (this.dateFormatter == null) {
/* 234 */       this.dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", DATE_LOCALE);
/* 235 */       this.dateFormatter.setTimeZone(TIME_ZONE);
/*     */     } 
/* 237 */     return this.dateFormatter;
/*     */   }
/*     */   
/*     */   public Request setDate(Date date) {
/* 241 */     this.request.setHeader("Date", getDateFormat().format(date));
/* 242 */     return this;
/*     */   }
/*     */   
/*     */   public Request setIfModifiedSince(Date date) {
/* 246 */     this.request.setHeader("If-Modified-Since", getDateFormat().format(date));
/* 247 */     return this;
/*     */   }
/*     */   
/*     */   public Request setIfUnmodifiedSince(Date date) {
/* 251 */     this.request.setHeader("If-Unmodified-Since", getDateFormat().format(date));
/* 252 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Request config(String param, Object object) {
/* 262 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Request removeConfig(String param) {
/* 272 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Request version(HttpVersion version) {
/* 278 */     this.request.setProtocolVersion((ProtocolVersion)version);
/* 279 */     return this;
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
/*     */   public Request elementCharset(String charset) {
/* 292 */     return this;
/*     */   }
/*     */   
/*     */   public Request useExpectContinue() {
/* 296 */     this.useExpectContinue = Boolean.TRUE;
/* 297 */     return this;
/*     */   }
/*     */   
/*     */   public Request userAgent(String agent) {
/* 301 */     this.request.setHeader("User-Agent", agent);
/* 302 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Request socketTimeout(int timeout) {
/* 308 */     this.socketTmeout = Integer.valueOf(timeout);
/* 309 */     return this;
/*     */   }
/*     */   
/*     */   public Request connectTimeout(int timeout) {
/* 313 */     this.connectTimeout = Integer.valueOf(timeout);
/* 314 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Request staleConnectionCheck(boolean b) {
/* 324 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Request viaProxy(HttpHost proxy) {
/* 330 */     this.proxy = proxy;
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request viaProxy(String proxy) {
/* 338 */     this.proxy = HttpHost.create(proxy);
/* 339 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Request body(HttpEntity entity) {
/* 345 */     if (this.request instanceof HttpEntityEnclosingRequest) {
/* 346 */       ((HttpEntityEnclosingRequest)this.request).setEntity(entity);
/*     */     } else {
/* 348 */       throw new IllegalStateException(this.request.getMethod() + " request cannot enclose an entity");
/*     */     } 
/*     */     
/* 351 */     return this;
/*     */   }
/*     */   
/*     */   public Request bodyForm(Iterable<? extends NameValuePair> formParams, Charset charset) {
/* 355 */     List<NameValuePair> paramList = new ArrayList<NameValuePair>();
/* 356 */     for (NameValuePair param : formParams) {
/* 357 */       paramList.add(param);
/*     */     }
/* 359 */     ContentType contentType = ContentType.create("application/x-www-form-urlencoded", charset);
/* 360 */     String s = URLEncodedUtils.format(paramList, charset);
/* 361 */     return bodyString(s, contentType);
/*     */   }
/*     */   
/*     */   public Request bodyForm(Iterable<? extends NameValuePair> formParams) {
/* 365 */     return bodyForm(formParams, Consts.ISO_8859_1);
/*     */   }
/*     */   
/*     */   public Request bodyForm(NameValuePair... formParams) {
/* 369 */     return bodyForm(Arrays.asList(formParams), Consts.ISO_8859_1);
/*     */   }
/*     */   
/*     */   public Request bodyString(String s, ContentType contentType) {
/* 373 */     Charset charset = (contentType != null) ? contentType.getCharset() : null;
/* 374 */     byte[] raw = (charset != null) ? s.getBytes(charset) : s.getBytes();
/* 375 */     return body((HttpEntity)new InternalByteArrayEntity(raw, contentType));
/*     */   }
/*     */   
/*     */   public Request bodyFile(File file, ContentType contentType) {
/* 379 */     return body((HttpEntity)new InternalFileEntity(file, contentType));
/*     */   }
/*     */   
/*     */   public Request bodyByteArray(byte[] b) {
/* 383 */     return body((HttpEntity)new InternalByteArrayEntity(b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request bodyByteArray(byte[] b, ContentType contentType) {
/* 390 */     return body((HttpEntity)new InternalByteArrayEntity(b, contentType));
/*     */   }
/*     */   
/*     */   public Request bodyByteArray(byte[] b, int off, int len) {
/* 394 */     return body((HttpEntity)new InternalByteArrayEntity(b, off, len));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Request bodyByteArray(byte[] b, int off, int len, ContentType contentType) {
/* 401 */     return body((HttpEntity)new InternalByteArrayEntity(b, off, len, contentType));
/*     */   }
/*     */   
/*     */   public Request bodyStream(InputStream inStream) {
/* 405 */     return body((HttpEntity)new InternalInputStreamEntity(inStream, -1L, null));
/*     */   }
/*     */   
/*     */   public Request bodyStream(InputStream inStream, ContentType contentType) {
/* 409 */     return body((HttpEntity)new InternalInputStreamEntity(inStream, -1L, contentType));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 414 */     return this.request.getRequestLine().toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/Request.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */