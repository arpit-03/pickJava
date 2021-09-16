/*     */ package com.sun.jna.platform.mac;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Pointer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class XAttrUtil
/*     */ {
/*     */   public static List<String> listXAttr(String path) {
/*  37 */     long bufferLength = XAttr.INSTANCE.listxattr(path, null, 0L, 0);
/*     */     
/*  39 */     if (bufferLength < 0L) {
/*  40 */       return null;
/*     */     }
/*  42 */     if (bufferLength == 0L) {
/*  43 */       return new ArrayList<String>(0);
/*     */     }
/*  45 */     Memory valueBuffer = new Memory(bufferLength);
/*  46 */     long valueLength = XAttr.INSTANCE.listxattr(path, (Pointer)valueBuffer, bufferLength, 0);
/*     */     
/*  48 */     if (valueLength < 0L) {
/*  49 */       return null;
/*     */     }
/*  51 */     return decodeStringSequence(valueBuffer.getByteBuffer(0L, valueLength));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getXAttr(String path, String name) {
/*  56 */     long bufferLength = XAttr.INSTANCE.getxattr(path, name, null, 0L, 0, 0);
/*     */     
/*  58 */     if (bufferLength < 0L) {
/*  59 */       return null;
/*     */     }
/*  61 */     Memory valueBuffer = new Memory(bufferLength);
/*  62 */     long valueLength = XAttr.INSTANCE.getxattr(path, name, (Pointer)valueBuffer, bufferLength, 0, 0);
/*     */     
/*  64 */     if (valueLength < 0L) {
/*  65 */       return null;
/*     */     }
/*  67 */     return decodeString(valueBuffer.getByteBuffer(0L, valueLength - 1L));
/*     */   }
/*     */   
/*     */   public static int setXAttr(String path, String name, String value) {
/*  71 */     Memory valueBuffer = encodeString(value);
/*  72 */     return XAttr.INSTANCE.setxattr(path, name, (Pointer)valueBuffer, valueBuffer.size(), 0, 0);
/*     */   }
/*     */   
/*     */   public static int removeXAttr(String path, String name) {
/*  76 */     return XAttr.INSTANCE.removexattr(path, name, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Memory encodeString(String s) {
/*  81 */     byte[] bb = s.getBytes(Charset.forName("UTF-8"));
/*  82 */     Memory valueBuffer = new Memory((bb.length + 1));
/*  83 */     valueBuffer.write(0L, bb, 0, bb.length);
/*  84 */     valueBuffer.setByte(valueBuffer.size() - 1L, (byte)0);
/*  85 */     return valueBuffer;
/*     */   }
/*     */   
/*     */   protected static String decodeString(ByteBuffer bb) {
/*  89 */     return Charset.forName("UTF-8").decode(bb).toString();
/*     */   }
/*     */   
/*     */   protected static List<String> decodeStringSequence(ByteBuffer bb) {
/*  93 */     List<String> names = new ArrayList<String>();
/*     */     
/*  95 */     bb.mark();
/*  96 */     while (bb.hasRemaining()) {
/*  97 */       if (bb.get() == 0) {
/*  98 */         ByteBuffer nameBuffer = (ByteBuffer)bb.duplicate().limit(bb.position() - 1).reset();
/*  99 */         if (nameBuffer.hasRemaining()) {
/* 100 */           names.add(decodeString(nameBuffer));
/*     */         }
/* 102 */         bb.mark();
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return names;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/mac/XAttrUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */