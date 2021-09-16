/*    */ package org.jfree.base;
/*    */ 
/*    */ import java.net.URL;
/*    */ import java.util.Enumeration;
/*    */ import org.jfree.util.ObjectUtilities;
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
/*    */ public class ClassPathDebugger
/*    */ {
/*    */   public static void main(String[] args) {
/* 51 */     System.out.println("Listing the various classloaders:");
/* 52 */     System.out.println("Defined classloader source: " + ObjectUtilities.getClassLoaderSource());
/* 53 */     System.out.println("User classloader: " + ObjectUtilities.getClassLoader());
/* 54 */     System.out.println("Classloader for ObjectUtilities.class: " + ObjectUtilities.getClassLoader(ObjectUtilities.class));
/* 55 */     System.out.println("Classloader for String.class: " + ObjectUtilities.getClassLoader(String.class));
/* 56 */     System.out.println("Thread-Context Classloader: " + Thread.currentThread().getContextClassLoader());
/* 57 */     System.out.println("Defined System classloader: " + ClassLoader.getSystemClassLoader());
/* 58 */     System.out.println();
/*    */     
/*    */     try {
/* 61 */       System.out.println("Listing sources for '/jcommon.properties':");
/*    */       
/* 63 */       Enumeration<URL> resources = ObjectUtilities.getClassLoader(ObjectUtilities.class).getResources("jcommon.properties");
/* 64 */       while (resources.hasMoreElements())
/*    */       {
/* 66 */         System.out.println(" " + resources.nextElement());
/*    */       }
/* 68 */       System.out.println();
/* 69 */       System.out.println("Listing sources for 'org/jfree/JCommonInfo.class':");
/*    */       
/* 71 */       resources = ObjectUtilities.getClassLoader(ObjectUtilities.class).getResources("org/jfree/JCommonInfo.class");
/* 72 */       while (resources.hasMoreElements())
/*    */       {
/* 74 */         System.out.println(" " + resources.nextElement());
/*    */       }
/*    */     }
/* 77 */     catch (Exception e) {
/*    */       
/* 79 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/base/ClassPathDebugger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */