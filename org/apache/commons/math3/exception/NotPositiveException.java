/*    */ package org.apache.commons.math3.exception;
/*    */ 
/*    */ import org.apache.commons.math3.exception.util.Localizable;
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
/*    */ public class NotPositiveException
/*    */   extends NumberIsTooSmallException
/*    */ {
/*    */   private static final long serialVersionUID = -2250556892093726375L;
/*    */   
/*    */   public NotPositiveException(Number value) {
/* 36 */     super(value, INTEGER_ZERO, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NotPositiveException(Localizable specific, Number value) {
/* 46 */     super(specific, value, INTEGER_ZERO, true);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NotPositiveException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */