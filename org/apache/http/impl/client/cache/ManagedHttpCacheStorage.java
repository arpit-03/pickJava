/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheStorage;
/*     */ import org.apache.http.client.cache.HttpCacheUpdateCallback;
/*     */ import org.apache.http.client.cache.Resource;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Contract(threading = ThreadingBehavior.SAFE)
/*     */ public class ManagedHttpCacheStorage
/*     */   implements HttpCacheStorage, Closeable
/*     */ {
/*     */   private final CacheMap entries;
/*     */   private final ReferenceQueue<HttpCacheEntry> morque;
/*     */   private final Set<ResourceReference> resources;
/*     */   private final AtomicBoolean active;
/*     */   
/*     */   public ManagedHttpCacheStorage(CacheConfig config) {
/*  84 */     this.entries = new CacheMap(config.getMaxCacheEntries());
/*  85 */     this.morque = new ReferenceQueue<HttpCacheEntry>();
/*  86 */     this.resources = new HashSet<ResourceReference>();
/*  87 */     this.active = new AtomicBoolean(true);
/*     */   }
/*     */   
/*     */   private void ensureValidState() throws IllegalStateException {
/*  91 */     if (!this.active.get()) {
/*  92 */       throw new IllegalStateException("Cache has been shut down");
/*     */     }
/*     */   }
/*     */   
/*     */   private void keepResourceReference(HttpCacheEntry entry) {
/*  97 */     Resource resource = entry.getResource();
/*  98 */     if (resource != null) {
/*     */       
/* 100 */       ResourceReference ref = new ResourceReference(entry, this.morque);
/* 101 */       this.resources.add(ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void putEntry(String url, HttpCacheEntry entry) throws IOException {
/* 107 */     Args.notNull(url, "URL");
/* 108 */     Args.notNull(entry, "Cache entry");
/* 109 */     ensureValidState();
/* 110 */     synchronized (this) {
/* 111 */       this.entries.put(url, entry);
/* 112 */       keepResourceReference(entry);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpCacheEntry getEntry(String url) throws IOException {
/* 118 */     Args.notNull(url, "URL");
/* 119 */     ensureValidState();
/* 120 */     synchronized (this) {
/* 121 */       return this.entries.get(url);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntry(String url) throws IOException {
/* 127 */     Args.notNull(url, "URL");
/* 128 */     ensureValidState();
/* 129 */     synchronized (this) {
/*     */ 
/*     */       
/* 132 */       this.entries.remove(url);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateEntry(String url, HttpCacheUpdateCallback callback) throws IOException {
/* 140 */     Args.notNull(url, "URL");
/* 141 */     Args.notNull(callback, "Callback");
/* 142 */     ensureValidState();
/* 143 */     synchronized (this) {
/* 144 */       HttpCacheEntry existing = this.entries.get(url);
/* 145 */       HttpCacheEntry updated = callback.update(existing);
/* 146 */       this.entries.put(url, updated);
/* 147 */       if (existing != updated) {
/* 148 */         keepResourceReference(updated);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cleanResources() {
/* 154 */     if (this.active.get()) {
/*     */       ResourceReference ref;
/* 156 */       while ((ref = (ResourceReference)this.morque.poll()) != null) {
/* 157 */         synchronized (this) {
/* 158 */           this.resources.remove(ref);
/*     */         } 
/* 160 */         ref.getResource().dispose();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 166 */     if (this.active.compareAndSet(true, false)) {
/* 167 */       synchronized (this) {
/* 168 */         this.entries.clear();
/* 169 */         for (ResourceReference ref : this.resources) {
/* 170 */           ref.getResource().dispose();
/*     */         }
/* 172 */         this.resources.clear();
/* 173 */         while (this.morque.poll() != null);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 181 */     if (this.active.compareAndSet(true, false))
/* 182 */       synchronized (this) {
/*     */         ResourceReference ref;
/* 184 */         while ((ref = (ResourceReference)this.morque.poll()) != null) {
/* 185 */           this.resources.remove(ref);
/* 186 */           ref.getResource().dispose();
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/ManagedHttpCacheStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */