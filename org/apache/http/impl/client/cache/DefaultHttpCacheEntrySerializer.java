/*     */ package org.apache.http.impl.client.cache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.http.annotation.Contract;
/*     */ import org.apache.http.annotation.ThreadingBehavior;
/*     */ import org.apache.http.client.cache.HttpCacheEntry;
/*     */ import org.apache.http.client.cache.HttpCacheEntrySerializationException;
/*     */ import org.apache.http.client.cache.HttpCacheEntrySerializer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class DefaultHttpCacheEntrySerializer
/*     */   implements HttpCacheEntrySerializer
/*     */ {
/*  57 */   private static final List<Pattern> ALLOWED_CLASS_PATTERNS = Collections.unmodifiableList(Arrays.asList(new Pattern[] { Pattern.compile("^(\\[L)?org\\.apache\\.http\\.(.*)"), Pattern.compile("^(\\[L)?java\\.util\\.(.*)"), Pattern.compile("^(\\[L)?java\\.lang\\.(.*)$"), Pattern.compile("^\\[B$") }));
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Pattern> allowedClassPatterns;
/*     */ 
/*     */ 
/*     */   
/*     */   DefaultHttpCacheEntrySerializer(Pattern... allowedClassPatterns) {
/*  66 */     this.allowedClassPatterns = Collections.unmodifiableList(Arrays.asList(allowedClassPatterns));
/*     */   }
/*     */   
/*     */   public DefaultHttpCacheEntrySerializer() {
/*  70 */     this.allowedClassPatterns = ALLOWED_CLASS_PATTERNS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(HttpCacheEntry cacheEntry, OutputStream os) throws IOException {
/*  75 */     ObjectOutputStream oos = new ObjectOutputStream(os);
/*     */     try {
/*  77 */       oos.writeObject(cacheEntry);
/*     */     } finally {
/*  79 */       oos.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpCacheEntry readFrom(InputStream is) throws IOException {
/*  85 */     ObjectInputStream ois = new RestrictedObjectInputStream(is, this.allowedClassPatterns);
/*     */     try {
/*  87 */       return (HttpCacheEntry)ois.readObject();
/*  88 */     } catch (ClassNotFoundException ex) {
/*  89 */       throw new HttpCacheEntrySerializationException("Class not found: " + ex.getMessage(), ex);
/*     */     } finally {
/*  91 */       ois.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class RestrictedObjectInputStream
/*     */     extends ObjectInputStream {
/*     */     private final List<Pattern> allowedClassPatterns;
/*     */     
/*     */     private RestrictedObjectInputStream(InputStream in, List<Pattern> patterns) throws IOException {
/* 100 */       super(in);
/* 101 */       this.allowedClassPatterns = patterns;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/* 106 */       if (isProhibited(desc)) {
/* 107 */         throw new HttpCacheEntrySerializationException(String.format("Class %s is not allowed for deserialization", new Object[] { desc.getName() }));
/*     */       }
/*     */       
/* 110 */       return super.resolveClass(desc);
/*     */     }
/*     */     
/*     */     private boolean isProhibited(ObjectStreamClass desc) {
/* 114 */       for (Pattern pattern : this.allowedClassPatterns) {
/* 115 */         if (pattern.matcher(desc.getName()).matches()) {
/* 116 */           return false;
/*     */         }
/*     */       } 
/* 119 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/DefaultHttpCacheEntrySerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */