/*    */ package us.hebi.matlab.mat.util;
/*    */ 
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
/*    */ class ArrayBoundsCheck
/*    */   implements ByteConverter
/*    */ {
/*    */   final ByteConverter impl;
/*    */   
/*    */   public short getShort(ByteOrder order, byte[] bytes, int offset) {
/* 35 */     checkBounds(bytes, offset, 2);
/* 36 */     return this.impl.getShort(order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt(ByteOrder order, byte[] bytes, int offset) {
/* 41 */     checkBounds(bytes, offset, 4);
/* 42 */     return this.impl.getInt(order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLong(ByteOrder order, byte[] bytes, int offset) {
/* 47 */     checkBounds(bytes, offset, 8);
/* 48 */     return this.impl.getLong(order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat(ByteOrder order, byte[] bytes, int offset) {
/* 53 */     checkBounds(bytes, offset, 4);
/* 54 */     return this.impl.getFloat(order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDouble(ByteOrder order, byte[] bytes, int offset) {
/* 59 */     checkBounds(bytes, offset, 8);
/* 60 */     return this.impl.getDouble(order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public void putShort(short value, ByteOrder order, byte[] bytes, int offset) {
/* 65 */     checkBounds(bytes, offset, 2);
/* 66 */     this.impl.putShort(value, order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public void putInt(int value, ByteOrder order, byte[] bytes, int offset) {
/* 71 */     checkBounds(bytes, offset, 4);
/* 72 */     this.impl.putInt(value, order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public void putLong(long value, ByteOrder order, byte[] bytes, int offset) {
/* 77 */     checkBounds(bytes, offset, 8);
/* 78 */     this.impl.putLong(value, order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public void putFloat(float value, ByteOrder order, byte[] bytes, int offset) {
/* 83 */     checkBounds(bytes, offset, 4);
/* 84 */     this.impl.putFloat(value, order, bytes, offset);
/*    */   }
/*    */ 
/*    */   
/*    */   public void putDouble(double value, ByteOrder order, byte[] bytes, int offset) {
/* 89 */     checkBounds(bytes, offset, 8);
/* 90 */     this.impl.putDouble(value, order, bytes, offset);
/*    */   }
/*    */   
/*    */   private void checkBounds(byte[] bytes, int offset, int length) {
/* 94 */     if (bytes == null || offset < 0 || offset + length > bytes.length)
/* 95 */       throw new ArrayIndexOutOfBoundsException(); 
/*    */   }
/*    */   
/*    */   ArrayBoundsCheck(ByteConverter impl) {
/* 99 */     this.impl = impl;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/ArrayBoundsCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */