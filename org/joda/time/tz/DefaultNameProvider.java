/*     */ package org.joda.time.tz;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.joda.time.DateTimeUtils;
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
/*     */ public class DefaultNameProvider
/*     */   implements NameProvider
/*     */ {
/*  37 */   private HashMap<Locale, Map<String, Map<String, Object>>> iByLocaleCache = createCache();
/*  38 */   private HashMap<Locale, Map<String, Map<Boolean, Object>>> iByLocaleCache2 = createCache();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortName(Locale paramLocale, String paramString1, String paramString2) {
/*  47 */     String[] arrayOfString = getNameSet(paramLocale, paramString1, paramString2);
/*  48 */     return (arrayOfString == null) ? null : arrayOfString[0];
/*     */   }
/*     */   
/*     */   public String getName(Locale paramLocale, String paramString1, String paramString2) {
/*  52 */     String[] arrayOfString = getNameSet(paramLocale, paramString1, paramString2);
/*  53 */     return (arrayOfString == null) ? null : arrayOfString[1];
/*     */   }
/*     */   
/*     */   private synchronized String[] getNameSet(Locale paramLocale, String paramString1, String paramString2) {
/*  57 */     if (paramLocale == null || paramString1 == null || paramString2 == null) {
/*  58 */       return null;
/*     */     }
/*     */     
/*  61 */     Map<String, Map<String, Object>> map = this.iByLocaleCache.get(paramLocale);
/*  62 */     if (map == null) {
/*  63 */       this.iByLocaleCache.put(paramLocale, map = createCache());
/*     */     }
/*     */     
/*  66 */     Map<String, Object> map1 = map.get(paramString1);
/*  67 */     if (map1 == null) {
/*  68 */       map.put(paramString1, map1 = createCache());
/*     */       
/*  70 */       String[][] arrayOfString1 = DateTimeUtils.getDateFormatSymbols(Locale.ENGLISH).getZoneStrings();
/*  71 */       String[] arrayOfString2 = null;
/*  72 */       for (String[] arrayOfString : arrayOfString1) {
/*  73 */         if (arrayOfString != null && arrayOfString.length >= 5 && paramString1.equals(arrayOfString[0])) {
/*  74 */           arrayOfString2 = arrayOfString;
/*     */           break;
/*     */         } 
/*     */       } 
/*  78 */       String[][] arrayOfString3 = DateTimeUtils.getDateFormatSymbols(paramLocale).getZoneStrings();
/*  79 */       String[] arrayOfString4 = null;
/*  80 */       for (String[] arrayOfString : arrayOfString3) {
/*  81 */         if (arrayOfString != null && arrayOfString.length >= 5 && paramString1.equals(arrayOfString[0])) {
/*  82 */           arrayOfString4 = arrayOfString;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  87 */       if (arrayOfString2 != null && arrayOfString4 != null) {
/*  88 */         map1.put(arrayOfString2[2], new String[] { arrayOfString4[2], arrayOfString4[1] });
/*     */ 
/*     */ 
/*     */         
/*  92 */         if (arrayOfString2[2].equals(arrayOfString2[4])) {
/*  93 */           map1.put(arrayOfString2[4] + "-Summer", new String[] { arrayOfString4[4], arrayOfString4[3] });
/*     */         } else {
/*  95 */           map1.put(arrayOfString2[4], new String[] { arrayOfString4[4], arrayOfString4[3] });
/*     */         } 
/*     */       } 
/*     */     } 
/*  99 */     return (String[])map1.get(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortName(Locale paramLocale, String paramString1, String paramString2, boolean paramBoolean) {
/* 106 */     String[] arrayOfString = getNameSet(paramLocale, paramString1, paramString2, paramBoolean);
/* 107 */     return (arrayOfString == null) ? null : arrayOfString[0];
/*     */   }
/*     */   
/*     */   public String getName(Locale paramLocale, String paramString1, String paramString2, boolean paramBoolean) {
/* 111 */     String[] arrayOfString = getNameSet(paramLocale, paramString1, paramString2, paramBoolean);
/* 112 */     return (arrayOfString == null) ? null : arrayOfString[1];
/*     */   }
/*     */   
/*     */   private synchronized String[] getNameSet(Locale paramLocale, String paramString1, String paramString2, boolean paramBoolean) {
/* 116 */     if (paramLocale == null || paramString1 == null || paramString2 == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     if (paramString1.startsWith("Etc/")) {
/* 120 */       paramString1 = paramString1.substring(4);
/*     */     }
/*     */     
/* 123 */     Map<String, Map<Boolean, Object>> map = this.iByLocaleCache2.get(paramLocale);
/* 124 */     if (map == null) {
/* 125 */       this.iByLocaleCache2.put(paramLocale, map = createCache());
/*     */     }
/*     */     
/* 128 */     Map<Boolean, Object> map1 = map.get(paramString1);
/* 129 */     if (map1 == null) {
/* 130 */       map.put(paramString1, map1 = createCache());
/*     */       
/* 132 */       String[][] arrayOfString1 = DateTimeUtils.getDateFormatSymbols(Locale.ENGLISH).getZoneStrings();
/* 133 */       String[] arrayOfString2 = null;
/* 134 */       for (String[] arrayOfString : arrayOfString1) {
/* 135 */         if (arrayOfString != null && arrayOfString.length >= 5 && paramString1.equals(arrayOfString[0])) {
/* 136 */           arrayOfString2 = arrayOfString;
/*     */           break;
/*     */         } 
/*     */       } 
/* 140 */       String[][] arrayOfString3 = DateTimeUtils.getDateFormatSymbols(paramLocale).getZoneStrings();
/* 141 */       String[] arrayOfString4 = null;
/* 142 */       for (String[] arrayOfString : arrayOfString3) {
/* 143 */         if (arrayOfString != null && arrayOfString.length >= 5 && paramString1.equals(arrayOfString[0])) {
/* 144 */           arrayOfString4 = arrayOfString;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 149 */       if (arrayOfString2 != null && arrayOfString4 != null) {
/* 150 */         map1.put(Boolean.TRUE, new String[] { arrayOfString4[2], arrayOfString4[1] });
/* 151 */         map1.put(Boolean.FALSE, new String[] { arrayOfString4[4], arrayOfString4[3] });
/*     */       } 
/*     */     } 
/* 154 */     return (String[])map1.get(Boolean.valueOf(paramBoolean));
/*     */   }
/*     */ 
/*     */   
/*     */   private HashMap createCache() {
/* 159 */     return new HashMap<Object, Object>(7);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/tz/DefaultNameProvider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */