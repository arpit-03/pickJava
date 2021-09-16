/*     */ package org.apache.http.impl.client.cache.memcached;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemcachedCacheEntryImpl
/*     */   implements MemcachedCacheEntry
/*     */ {
/*     */   private String key;
/*     */   private HttpCacheEntry httpCacheEntry;
/*     */   
/*     */   public MemcachedCacheEntryImpl(String key, HttpCacheEntry httpCacheEntry) {
/*  48 */     this.key = key;
/*  49 */     this.httpCacheEntry = httpCacheEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemcachedCacheEntryImpl() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized byte[] toByteArray() {
/*  60 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */     
/*     */     try {
/*  63 */       ObjectOutputStream oos = new ObjectOutputStream(bos);
/*  64 */       oos.writeObject(this.key);
/*  65 */       oos.writeObject(this.httpCacheEntry);
/*  66 */       oos.close();
/*  67 */     } catch (IOException ioe) {
/*  68 */       throw new MemcachedSerializationException(ioe);
/*     */     } 
/*  70 */     return bos.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String getStorageKey() {
/*  78 */     return this.key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized HttpCacheEntry getHttpCacheEntry() {
/*  86 */     return this.httpCacheEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void set(byte[] bytes) {
/*     */     String s;
/*     */     HttpCacheEntry entry;
/*  94 */     ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  99 */       ObjectInputStream ois = new ObjectInputStream(bis);
/* 100 */       s = (String)ois.readObject();
/* 101 */       entry = (HttpCacheEntry)ois.readObject();
/* 102 */       ois.close();
/* 103 */       bis.close();
/* 104 */     } catch (IOException ioe) {
/* 105 */       throw new MemcachedSerializationException(ioe);
/* 106 */     } catch (ClassNotFoundException cnfe) {
/* 107 */       throw new MemcachedSerializationException(cnfe);
/*     */     } 
/* 109 */     this.key = s;
/* 110 */     this.httpCacheEntry = entry;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/MemcachedCacheEntryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */