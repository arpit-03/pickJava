/*    */ package com.jmatio.types;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class MLSingle
/*    */   extends MLNumericArray<Float>
/*    */ {
/*    */   public MLSingle(String name, Float[] vals, int m) {
/* 10 */     super(name, 7, vals, m);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLSingle(String name, int[] dims, int type, int attributes) {
/* 15 */     super(name, dims, type, attributes);
/*    */   }
/*    */ 
/*    */   
/*    */   public Float[] createArray(int m, int n) {
/* 20 */     return new Float[m * n];
/*    */   }
/*    */ 
/*    */   
/*    */   public Float buldFromBytes(byte[] bytes) {
/* 25 */     if (bytes.length != getBytesAllocated())
/*    */     {
/* 27 */       throw new IllegalArgumentException("To build from byte array I need array of size: " + getBytesAllocated());
/*    */     }
/*    */ 
/*    */     
/* 31 */     return Float.valueOf(ByteBuffer.wrap(bytes).getFloat());
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getByteArray(Float value) {
/* 36 */     int byteAllocated = getBytesAllocated();
/* 37 */     ByteBuffer buff = ByteBuffer.allocate(byteAllocated);
/* 38 */     buff.putFloat(value.floatValue());
/* 39 */     return buff.array();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBytesAllocated() {
/* 44 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getStorageClazz() {
/* 49 */     return Float.class;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLSingle.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */