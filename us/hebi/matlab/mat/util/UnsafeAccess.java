/*    */ package us.hebi.matlab.mat.util;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.nio.ByteOrder;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ import sun.misc.Unsafe;
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
/*    */ class UnsafeAccess
/*    */ {
/*    */   static final ByteOrder NATIVE_ORDER;
/*    */   static final Unsafe UNSAFE;
/*    */   static final long BYTE_ARRAY_OFFSET;
/*    */   
/*    */   public static void requireUnsafe() {
/* 38 */     if (!isAvailable())
/* 39 */       throw new AssertionError("Unsafe is not available on this platform"); 
/*    */   }
/*    */   
/*    */   public static boolean isAvailable() {
/* 43 */     return (UNSAFE != null);
/*    */   }
/*    */   
/*    */   static {
/* 47 */     Unsafe unsafe = null;
/* 48 */     long baseOffset = 0L;
/*    */     try {
/* 50 */       PrivilegedExceptionAction<Unsafe> action = 
/* 51 */         new PrivilegedExceptionAction<Unsafe>()
/*    */         {
/*    */           public Unsafe run() throws Exception {
/* 54 */             Field f = Unsafe.class.getDeclaredField("theUnsafe");
/* 55 */             f.setAccessible(true);
/*    */             
/* 57 */             return (Unsafe)f.get(null);
/*    */           }
/*    */         };
/*    */       
/* 61 */       unsafe = AccessController.<Unsafe>doPrivileged(action);
/* 62 */       baseOffset = unsafe.arrayBaseOffset(byte[].class);
/* 63 */     } catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */     
/* 67 */     UNSAFE = unsafe;
/* 68 */     BYTE_ARRAY_OFFSET = baseOffset;
/*    */ 
/*    */     
/* 71 */     NATIVE_ORDER = ByteOrder.nativeOrder();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/UnsafeAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */