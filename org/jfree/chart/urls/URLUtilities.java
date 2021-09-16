/*    */ package org.jfree.chart.urls;
/*    */ 
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URLEncoder;
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
/*    */ public class URLUtilities
/*    */ {
/* 55 */   private static final Class[] STRING_ARGS_2 = new Class[] { String.class, String.class };
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
/*    */   public static String encode(String s, String encoding) {
/* 71 */     Class<URLEncoder> c = URLEncoder.class;
/* 72 */     String result = null;
/*    */     try {
/* 74 */       Method m = c.getDeclaredMethod("encode", STRING_ARGS_2);
/*    */       try {
/* 76 */         result = (String)m.invoke(null, new Object[] { s, encoding });
/*    */       }
/* 78 */       catch (InvocationTargetException e) {
/* 79 */         e.printStackTrace();
/*    */       }
/* 81 */       catch (IllegalAccessException e) {
/* 82 */         e.printStackTrace();
/*    */       }
/*    */     
/* 85 */     } catch (NoSuchMethodException e) {
/*    */       
/* 87 */       result = URLEncoder.encode(s);
/*    */     } 
/* 89 */     return result;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/urls/URLUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */