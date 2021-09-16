/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.http.Consts;
/*     */ import org.apache.http.Header;
/*     */ import org.apache.http.HeaderElement;
/*     */ import org.apache.http.HttpHost;
/*     */ import org.apache.http.HttpRequest;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.client.utils.URIBuilder;
/*     */ import org.apache.http.client.utils.URIUtils;
/*     */ import org.apache.http.client.utils.URLEncodedUtils;
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
/*     */ @Contract(threading = ThreadingBehavior.IMMUTABLE)
/*     */ class CacheKeyGenerator
/*     */ {
/*  58 */   private static final URI BASE_URI = URI.create("http://example.com/");
/*     */   
/*     */   static URIBuilder getRequestUriBuilder(HttpRequest request) throws URISyntaxException {
/*  61 */     if (request instanceof HttpUriRequest) {
/*  62 */       URI uri = ((HttpUriRequest)request).getURI();
/*  63 */       if (uri != null) {
/*  64 */         return new URIBuilder(uri);
/*     */       }
/*     */     } 
/*  67 */     return new URIBuilder(request.getRequestLine().getUri());
/*     */   }
/*     */   
/*     */   static URI getRequestUri(HttpRequest request, HttpHost target) throws URISyntaxException {
/*  71 */     Args.notNull(request, "HTTP request");
/*  72 */     Args.notNull(target, "Target");
/*  73 */     URIBuilder uriBuilder = getRequestUriBuilder(request);
/*     */ 
/*     */     
/*  76 */     String path = uriBuilder.getPath();
/*  77 */     if (path != null) {
/*  78 */       uriBuilder.setPathSegments(URLEncodedUtils.parsePathSegments(path));
/*     */     }
/*     */     
/*  81 */     if (!uriBuilder.isAbsolute()) {
/*  82 */       uriBuilder.setScheme(target.getSchemeName());
/*  83 */       uriBuilder.setHost(target.getHostName());
/*  84 */       uriBuilder.setPort(target.getPort());
/*     */     } 
/*  86 */     return uriBuilder.build();
/*     */   }
/*     */   
/*     */   static URI normalize(URI requestUri) throws URISyntaxException {
/*  90 */     Args.notNull(requestUri, "URI");
/*  91 */     URIBuilder builder = new URIBuilder(requestUri.isAbsolute() ? URIUtils.resolve(BASE_URI, requestUri) : requestUri);
/*  92 */     if (builder.getHost() != null) {
/*  93 */       if (builder.getScheme() == null) {
/*  94 */         builder.setScheme("http");
/*     */       }
/*  96 */       if (builder.getPort() <= -1) {
/*  97 */         if ("http".equalsIgnoreCase(builder.getScheme())) {
/*  98 */           builder.setPort(80);
/*  99 */         } else if ("https".equalsIgnoreCase(builder.getScheme())) {
/* 100 */           builder.setPort(443);
/*     */         } 
/*     */       }
/*     */     } 
/* 104 */     builder.setFragment(null);
/* 105 */     return builder.build();
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
/*     */   public String getURI(HttpHost host, HttpRequest req) {
/*     */     try {
/* 118 */       URI uri = normalize(getRequestUri(req, host));
/* 119 */       return uri.toASCIIString();
/* 120 */     } catch (URISyntaxException ex) {
/* 121 */       return req.getRequestLine().getUri();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String canonicalizeUri(String uri) {
/*     */     try {
/* 127 */       URI normalized = normalize(URIUtils.resolve(BASE_URI, uri));
/* 128 */       return normalized.toASCIIString();
/* 129 */     } catch (URISyntaxException ex) {
/* 130 */       return uri;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getFullHeaderValue(Header[] headers) {
/* 135 */     if (headers == null) {
/* 136 */       return "";
/*     */     }
/*     */     
/* 139 */     StringBuilder buf = new StringBuilder("");
/* 140 */     boolean first = true;
/* 141 */     for (Header hdr : headers) {
/* 142 */       if (!first) {
/* 143 */         buf.append(", ");
/*     */       }
/* 145 */       buf.append(hdr.getValue().trim());
/* 146 */       first = false;
/*     */     } 
/*     */     
/* 149 */     return buf.toString();
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
/*     */   public String getVariantURI(HttpHost host, HttpRequest req, HttpCacheEntry entry) {
/* 163 */     if (!entry.hasVariants()) {
/* 164 */       return getURI(host, req);
/*     */     }
/* 166 */     return getVariantKey(req, entry) + getURI(host, req);
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
/*     */   public String getVariantKey(HttpRequest req, HttpCacheEntry entry) {
/*     */     StringBuilder buf;
/* 179 */     List<String> variantHeaderNames = new ArrayList<String>();
/* 180 */     for (Header varyHdr : entry.getHeaders("Vary")) {
/* 181 */       for (HeaderElement elt : varyHdr.getElements()) {
/* 182 */         variantHeaderNames.add(elt.getName());
/*     */       }
/*     */     } 
/* 185 */     Collections.sort(variantHeaderNames);
/*     */ 
/*     */     
/*     */     try {
/* 189 */       buf = new StringBuilder("{");
/* 190 */       boolean first = true;
/* 191 */       for (String headerName : variantHeaderNames) {
/* 192 */         if (!first) {
/* 193 */           buf.append("&");
/*     */         }
/* 195 */         buf.append(URLEncoder.encode(headerName, Consts.UTF_8.name()));
/* 196 */         buf.append("=");
/* 197 */         buf.append(URLEncoder.encode(getFullHeaderValue(req.getHeaders(headerName)), Consts.UTF_8.name()));
/*     */         
/* 199 */         first = false;
/*     */       } 
/* 201 */       buf.append("}");
/* 202 */     } catch (UnsupportedEncodingException uee) {
/* 203 */       throw new RuntimeException("couldn't encode to UTF-8", uee);
/*     */     } 
/* 205 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/CacheKeyGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */