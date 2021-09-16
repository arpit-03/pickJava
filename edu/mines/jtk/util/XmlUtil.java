/*    */ package edu.mines.jtk.util;
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
/*    */ class XmlUtil
/*    */ {
/*    */   public static String quoteAttributeValue(String s) {
/* 32 */     if (s == null) return null; 
/* 33 */     s = replaceAll("&", "&amp;", s);
/* 34 */     s = replaceAll("<", "&lt;", s);
/* 35 */     s = replaceAll("\r", "&#13;", s);
/* 36 */     s = replaceAll("\n", "&#10;", s);
/* 37 */     s = replaceAll("\t", "&#9;", s);
/* 38 */     String quote = "\"";
/* 39 */     if (s.contains("\"") && !s.contains("'")) {
/* 40 */       quote = "'";
/*    */     } else {
/* 42 */       s = replaceAll("\"", "&quot;", s);
/*    */     } 
/* 44 */     return quote + s + quote;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String quoteCharacterData(String s) {
/* 55 */     char space = ' ';
/* 56 */     if (s == null) return null; 
/* 57 */     s = replaceAll("&", "&amp;", s);
/* 58 */     s = replaceAll("<", "&lt;", s);
/* 59 */     s = replaceAll("\\", "\\\\", s);
/* 60 */     s = replaceAll("\r", "\\r", s);
/* 61 */     s = replaceAll("\n", "\\n", s);
/* 62 */     s = replaceAll("\t", "\\t", s);
/* 63 */     s = replaceAll("\"", "\\\"", s);
/* 64 */     s = replaceAll("'", "\\'", s);
/* 65 */     String quote = "";
/* 66 */     if (s.length() == 0) {
/* 67 */       quote = "\"";
/*    */     } else {
/* 69 */       for (int i = 0; i < s.length(); i++) {
/* 70 */         if (s.charAt(i) <= ' ') {
/* 71 */           quote = "\"";
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/* 76 */     return quote + s + quote;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String replaceAll(String x, String y, String s) {
/* 87 */     if (s == null) return null; 
/* 88 */     int from = 0;
/* 89 */     int to = s.indexOf(x, from);
/* 90 */     if (to < 0) return s; 
/* 91 */     StringBuilder d = new StringBuilder(s.length() + 32);
/* 92 */     while (to >= 0) {
/* 93 */       d.append(s.substring(from, to));
/* 94 */       d.append(y);
/* 95 */       from = to + x.length();
/* 96 */       to = s.indexOf(x, from);
/*    */     } 
/* 98 */     return d.append(s.substring(from)).toString();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/XmlUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */