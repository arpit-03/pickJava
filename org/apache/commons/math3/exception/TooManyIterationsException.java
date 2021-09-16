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
/*    */ 
/*    */ 
/*    */ public class TooManyIterationsException
/*    */   extends MaxCountExceededException
/*    */ {
/*    */   private static final long serialVersionUID = 20121211L;
/*    */   
/*    */   public TooManyIterationsException(Number max) {
/* 36 */     super(max);
/* 37 */     getContext().addMessage((Localizable)LocalizedFormats.ITERATIONS, new Object[0]);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/TooManyIterationsException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */