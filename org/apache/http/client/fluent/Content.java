/*    */ package org.apache.http.client.fluent;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.InputStream;
/*    */ import java.nio.charset.Charset;
/*    */ import org.apache.http.Consts;
/*    */ import org.apache.http.entity.ContentType;
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
/*    */ public class Content
/*    */ {
/* 42 */   public static final Content NO_CONTENT = new Content(new byte[0], ContentType.DEFAULT_BINARY);
/*    */   
/*    */   private final byte[] raw;
/*    */   
/*    */   private final ContentType type;
/*    */   
/*    */   public Content(byte[] raw, ContentType type) {
/* 49 */     this.raw = raw;
/* 50 */     this.type = type;
/*    */   }
/*    */   
/*    */   public ContentType getType() {
/* 54 */     return this.type;
/*    */   }
/*    */   
/*    */   public byte[] asBytes() {
/* 58 */     return (byte[])this.raw.clone();
/*    */   }
/*    */   
/*    */   public String asString() {
/* 62 */     Charset charset = this.type.getCharset();
/* 63 */     if (charset == null) {
/* 64 */       charset = Consts.ISO_8859_1;
/*    */     }
/* 66 */     return asString(charset);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String asString(Charset charset) {
/* 73 */     return new String(this.raw, charset);
/*    */   }
/*    */   
/*    */   public InputStream asStream() {
/* 77 */     return new ByteArrayInputStream(this.raw);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 82 */     return asString();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/client/fluent/Content.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */