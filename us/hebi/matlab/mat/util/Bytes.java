/*    */ package us.hebi.matlab.mat.util;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.ByteOrder;
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
/*    */ public class Bytes
/*    */ {
/*    */   public static final int SIZEOF_BYTE = 1;
/*    */   public static final int SIZEOF_SHORT = 2;
/*    */   public static final int SIZEOF_INT = 4;
/*    */   public static final int SIZEOF_LONG = 8;
/*    */   public static final int SIZEOF_FLOAT = 4;
/*    */   public static final int SIZEOF_DOUBLE = 8;
/*    */   
/*    */   public static int nextPowerOfTwo(int value) {
/* 40 */     if (value == 0) {
/* 41 */       return 1;
/*    */     }
/* 43 */     int highestBit = Integer.highestOneBit(value);
/* 44 */     if (highestBit == value) {
/* 45 */       return value;
/*    */     }
/* 47 */     return highestBit << 1;
/*    */   }
/*    */   
/*    */   public static ByteOrder reverseByteOrder(ByteOrder order) {
/* 51 */     return (order == ByteOrder.LITTLE_ENDIAN) ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
/*    */   }
/*    */   
/*    */   public static void reverseByteOrder(ByteBuffer buffer, int bytesPerElement) {
/* 55 */     int i, offset = buffer.position();
/* 56 */     int limit = buffer.limit();
/*    */     
/* 58 */     switch (bytesPerElement) {
/*    */       case 1:
/*    */         break;
/*    */       case 2:
/* 62 */         for (i = offset; i < limit; i += 2)
/* 63 */           buffer.putShort(i, Short.reverseBytes(buffer.getShort(i))); 
/*    */         break;
/*    */       case 4:
/* 66 */         for (i = offset; i < limit; i += 4)
/* 67 */           buffer.putInt(i, Integer.reverseBytes(buffer.getInt(i))); 
/*    */         break;
/*    */       case 8:
/* 70 */         for (i = offset; i < limit; i += 8)
/* 71 */           buffer.putLong(i, Long.reverseBytes(buffer.getLong(i))); 
/*    */         break;
/*    */       default:
/* 74 */         throw new IllegalStateException("Unexpected number of bytes per element: " + bytesPerElement);
/*    */     } 
/*    */     
/* 77 */     buffer.order(reverseByteOrder(buffer.order()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int findFirst(byte[] bytes, int offset, int length, byte value, int defaultValue) {
/* 84 */     for (int i = 0; i < length; i++) {
/* 85 */       if (bytes[offset + i] == value)
/* 86 */         return i; 
/*    */     } 
/* 88 */     return defaultValue;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/Bytes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */