/*    */ package org.apache.commons.math3.exception.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class ArgUtils
/*    */ {
/*    */   public static Object[] flatten(Object[] array) {
/* 41 */     List<Object> list = new ArrayList();
/* 42 */     if (array != null) {
/* 43 */       for (Object o : array) {
/* 44 */         if (o instanceof Object[]) {
/* 45 */           for (Object oR : flatten((Object[])o)) {
/* 46 */             list.add(oR);
/*    */           }
/*    */         } else {
/* 49 */           list.add(o);
/*    */         } 
/*    */       } 
/*    */     }
/* 53 */     return list.toArray();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/util/ArgUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */