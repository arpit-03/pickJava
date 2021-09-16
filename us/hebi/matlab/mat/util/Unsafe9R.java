/*     */ package us.hebi.matlab.mat.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import sun.misc.Unsafe;
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
/*     */ public class Unsafe9R
/*     */ {
/*     */   private static final boolean useJava9;
/*     */   
/*     */   public static void invokeCleaner(ByteBuffer directBuffer) {
/*  66 */     if (!directBuffer.isDirect())
/*  67 */       throw new IllegalArgumentException("buffer is non-direct"); 
/*  68 */     if (useJava9) {
/*  69 */       Java9.invokeCleaner(directBuffer);
/*     */     } else {
/*  71 */       Java6.invokeCleaner(directBuffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  78 */     String version = System.getProperty("java.specification.version", "6");
/*  79 */     String majorVersion = version.startsWith("1.") ? version.substring(2) : version;
/*  80 */     useJava9 = (Integer.parseInt(majorVersion) >= 9);
/*     */   }
/*     */   
/*     */   private static class Java9
/*     */   {
/*     */     static final Unsafe UNSAFE;
/*     */     static final Method INVOKE_CLEANER;
/*     */     
/*     */     static void invokeCleaner(ByteBuffer buffer) {
/*     */       try {
/*  90 */         INVOKE_CLEANER.invoke(UNSAFE, new Object[] { buffer });
/*  91 */       } catch (Exception e) {
/*  92 */         throw new IllegalStateException("Java 9 Cleaner failed to free DirectBuffer", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       try {
/* 101 */         PrivilegedExceptionAction<Unsafe> action = 
/* 102 */           new PrivilegedExceptionAction<Unsafe>()
/*     */           {
/*     */             public Unsafe run() throws Exception {
/* 105 */               Field f = Unsafe.class.getDeclaredField("theUnsafe");
/* 106 */               f.setAccessible(true);
/* 107 */               return (Unsafe)f.get(null);
/*     */             }
/*     */           };
/* 110 */         UNSAFE = AccessController.<Unsafe>doPrivileged(action);
/* 111 */         INVOKE_CLEANER = UNSAFE.getClass().getMethod("invokeCleaner", new Class[] { ByteBuffer.class });
/*     */       }
/* 113 */       catch (Exception ex) {
/* 114 */         throw new IllegalStateException("Java 9 Cleaner not available", ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Java6 {
/*     */     static final Method GET_CLEANER;
/*     */     static final Method INVOKE_CLEANER;
/*     */     
/*     */     static void invokeCleaner(ByteBuffer buffer) {
/*     */       try {
/* 125 */         Object cleaner = GET_CLEANER.invoke(buffer, new Object[0]);
/* 126 */         if (cleaner != null)
/* 127 */           INVOKE_CLEANER.invoke(cleaner, new Object[0]); 
/* 128 */       } catch (Exception e) {
/* 129 */         throw new IllegalStateException("Java 6 Cleaner failed to free DirectBuffer", e);
/*     */       } 
/*     */     }
/*     */     
/*     */     static {
/*     */       try {
/* 135 */         GET_CLEANER = Class.forName("sun.nio.ch.DirectBuffer").getMethod("cleaner", new Class[0]);
/* 136 */         INVOKE_CLEANER = Class.forName("sun.misc.Cleaner").getMethod("clean", new Class[0]);
/* 137 */       } catch (Exception e) {
/* 138 */         throw new IllegalStateException("Java 6 Cleaner not available", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/us/hebi/matlab/mat/util/Unsafe9R.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */