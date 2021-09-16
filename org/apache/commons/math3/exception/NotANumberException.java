/*    */ package org.apache.commons.math3.exception;
/*    */ 
/*    */ import org.apache.commons.math3.exception.util.Localizable;
/*    */ import org.apache.commons.math3.exception.util.LocalizedFormats;
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
/*    */ public class NotANumberException
/*    */   extends MathIllegalNumberException
/*    */ {
/*    */   private static final long serialVersionUID = 20120906L;
/*    */   
/*    */   public NotANumberException() {
/* 34 */     super((Localizable)LocalizedFormats.NAN_NOT_ALLOWED, Double.valueOf(Double.NaN), new Object[0]);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NotANumberException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */