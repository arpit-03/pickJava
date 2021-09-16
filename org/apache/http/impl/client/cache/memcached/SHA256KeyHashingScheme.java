/*    */ package org.apache.http.impl.client.cache.memcached;
/*    */ 
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import org.apache.commons.codec.binary.Hex;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SHA256KeyHashingScheme
/*    */   implements KeyHashingScheme
/*    */ {
/* 45 */   private static final Log log = LogFactory.getLog(SHA256KeyHashingScheme.class);
/*    */ 
/*    */   
/*    */   public String hash(String key) {
/* 49 */     MessageDigest md = getDigest();
/* 50 */     md.update(key.getBytes());
/* 51 */     return Hex.encodeHexString(md.digest());
/*    */   }
/*    */   
/*    */   private MessageDigest getDigest() {
/*    */     try {
/* 56 */       return MessageDigest.getInstance("SHA-256");
/* 57 */     } catch (NoSuchAlgorithmException nsae) {
/* 58 */       log.error("can't find SHA-256 implementation for cache key hashing");
/* 59 */       throw new MemcachedKeyHashingException(nsae);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/impl/client/cache/memcached/SHA256KeyHashingScheme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */