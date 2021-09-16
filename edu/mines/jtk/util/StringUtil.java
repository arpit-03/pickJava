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
/*    */ public class StringUtil
/*    */ {
/*    */   public static String removeTrailingZeros(String s) {
/* 32 */     int len = s.length();
/* 33 */     int iend = s.indexOf('e');
/* 34 */     if (iend < 0)
/* 35 */       iend = s.indexOf('E'); 
/* 36 */     if (iend < 0)
/* 37 */       iend = len; 
/* 38 */     int ibeg = iend;
/* 39 */     if (s.indexOf('.') > 0) {
/* 40 */       while (ibeg > 0 && s.charAt(ibeg - 1) == '0')
/* 41 */         ibeg--; 
/* 42 */       if (ibeg > 0 && s.charAt(ibeg - 1) == '.')
/* 43 */         ibeg--; 
/*    */     } 
/* 45 */     if (ibeg < iend) {
/* 46 */       String sb = s.substring(0, ibeg);
/* 47 */       s = (iend < len) ? (sb + s.substring(iend, len)) : sb;
/*    */     } 
/* 49 */     return s;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/util/StringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */