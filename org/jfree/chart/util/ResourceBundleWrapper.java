/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.ResourceBundle;
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
/*     */ public class ResourceBundleWrapper
/*     */ {
/*     */   private static URLClassLoader noCodeBaseClassLoader;
/*     */   
/*     */   public static void removeCodeBase(URL codeBase, URLClassLoader urlClassLoader) {
/*  98 */     List<URL> urlsNoBase = new ArrayList();
/*     */     
/* 100 */     URL[] urls = urlClassLoader.getURLs();
/* 101 */     for (int i = 0; i < urls.length; i++) {
/* 102 */       if (!urls[i].sameFile(codeBase)) {
/* 103 */         urlsNoBase.add(urls[i]);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     URL[] urlsNoBaseArray = urlsNoBase.<URL>toArray(new URL[0]);
/* 108 */     noCodeBaseClassLoader = URLClassLoader.newInstance(urlsNoBaseArray);
/*     */   }
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
/*     */   public static ResourceBundle getBundle(String baseName) {
/* 122 */     if (noCodeBaseClassLoader != null) {
/* 123 */       return ResourceBundle.getBundle(baseName, Locale.getDefault(), noCodeBaseClassLoader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 128 */     return ResourceBundle.getBundle(baseName);
/*     */   }
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
/*     */   public static ResourceBundle getBundle(String baseName, Locale locale) {
/* 145 */     if (noCodeBaseClassLoader != null) {
/* 146 */       return ResourceBundle.getBundle(baseName, locale, noCodeBaseClassLoader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 151 */     return ResourceBundle.getBundle(baseName, locale);
/*     */   }
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
/*     */   public static ResourceBundle getBundle(String baseName, Locale locale, ClassLoader loader) {
/* 167 */     return ResourceBundle.getBundle(baseName, locale, loader);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/ResourceBundleWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */