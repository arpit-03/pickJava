/*    */ package com.jmatio.io;
/*    */ 
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
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
/*    */ class OSArrayTag
/*    */   extends MatTag
/*    */ {
/*    */   private ByteBuffer data;
/*    */   
/*    */   public OSArrayTag(int type, byte[] data) {
/* 23 */     this(type, ByteBuffer.wrap(data));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OSArrayTag(int type, ByteBuffer data) {
/* 33 */     super(type, data.limit());
/* 34 */     this.data = data;
/* 35 */     data.rewind();
/*    */   }
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
/*    */   public void writeTo(DataOutputStream os) throws IOException {
/*    */     int padding;
/* 49 */     if (this.size <= 4 && this.size > 0) {
/*    */       
/* 51 */       os.writeShort(this.size);
/* 52 */       os.writeShort(this.type);
/* 53 */       padding = getPadding(this.data.limit(), true);
/*    */     } else {
/* 55 */       os.writeInt(this.type);
/* 56 */       os.writeInt(this.size);
/* 57 */       padding = getPadding(this.data.limit(), false);
/*    */     } 
/*    */     
/* 60 */     int maxBuffSize = 1024;
/* 61 */     int writeBuffSize = (this.data.remaining() < maxBuffSize) ? this.data.remaining() : maxBuffSize;
/* 62 */     byte[] tmp = new byte[writeBuffSize];
/* 63 */     while (this.data.remaining() > 0) {
/*    */       
/* 65 */       int length = (this.data.remaining() > tmp.length) ? tmp.length : this.data.remaining();
/* 66 */       this.data.get(tmp, 0, length);
/* 67 */       os.write(tmp, 0, length);
/*    */     } 
/*    */     
/* 70 */     if (padding > 0)
/*    */     {
/* 72 */       os.write(new byte[padding]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/OSArrayTag.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */