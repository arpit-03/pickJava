/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class DefaultFailureCache
/*     */   implements FailureCache
/*     */ {
/*     */   static final int DEFAULT_MAX_SIZE = 1000;
/*     */   static final int MAX_UPDATE_TRIES = 10;
/*     */   private final int maxSize;
/*     */   private final ConcurrentMap<String, FailureCacheValue> storage;
/*     */   
/*     */   public DefaultFailureCache() {
/*  56 */     this(1000);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultFailureCache(int maxSize) {
/*  64 */     this.maxSize = maxSize;
/*  65 */     this.storage = new ConcurrentHashMap<String, FailureCacheValue>();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getErrorCount(String identifier) {
/*  70 */     if (identifier == null) {
/*  71 */       throw new IllegalArgumentException("identifier may not be null");
/*     */     }
/*  73 */     FailureCacheValue storedErrorCode = this.storage.get(identifier);
/*  74 */     return (storedErrorCode != null) ? storedErrorCode.getErrorCount() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetErrorCount(String identifier) {
/*  79 */     if (identifier == null) {
/*  80 */       throw new IllegalArgumentException("identifier may not be null");
/*     */     }
/*  82 */     this.storage.remove(identifier);
/*     */   }
/*     */ 
/*     */   
/*     */   public void increaseErrorCount(String identifier) {
/*  87 */     if (identifier == null) {
/*  88 */       throw new IllegalArgumentException("identifier may not be null");
/*     */     }
/*  90 */     updateValue(identifier);
/*  91 */     removeOldestEntryIfMapSizeExceeded();
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
/*     */   private void updateValue(String identifier) {
/* 104 */     for (int i = 0; i < 10; i++) {
/* 105 */       FailureCacheValue oldValue = this.storage.get(identifier);
/* 106 */       if (oldValue == null) {
/* 107 */         FailureCacheValue newValue = new FailureCacheValue(identifier, 1);
/* 108 */         if (this.storage.putIfAbsent(identifier, newValue) == null) {
/*     */           return;
/*     */         }
/*     */       } else {
/*     */         
/* 113 */         int errorCount = oldValue.getErrorCount();
/* 114 */         if (errorCount == Integer.MAX_VALUE) {
/*     */           return;
/*     */         }
/* 117 */         FailureCacheValue newValue = new FailureCacheValue(identifier, errorCount + 1);
/* 118 */         if (this.storage.replace(identifier, oldValue, newValue)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeOldestEntryIfMapSizeExceeded() {
/* 126 */     if (this.storage.size() > this.maxSize) {
/* 127 */       FailureCacheValue valueWithOldestTimestamp = findValueWithOldestTimestamp();
/* 128 */       if (valueWithOldestTimestamp != null) {
/* 129 */         this.storage.remove(valueWithOldestTimestamp.getKey(), valueWithOldestTimestamp);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private FailureCacheValue findValueWithOldestTimestamp() {
/* 135 */     long oldestTimestamp = Long.MAX_VALUE;
/* 136 */     FailureCacheValue oldestValue = null;
/* 137 */     for (Map.Entry<String, FailureCacheValue> storageEntry : this.storage.entrySet()) {
/* 138 */       FailureCacheValue value = storageEntry.getValue();
/* 139 */       long creationTimeInNanos = value.getCreationTimeInNanos();
/* 140 */       if (creationTimeInNanos < oldestTimestamp) {
/* 141 */         oldestTimestamp = creationTimeInNanos;
/* 142 */         oldestValue = storageEntry.getValue();
/*     */       } 
/*     */     } 
/* 145 */     return oldestValue;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/DefaultFailureCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */