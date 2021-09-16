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
/*    */ 
/*    */ public class InsufficientDataException
/*    */   extends MathIllegalArgumentException
/*    */ {
/*    */   private static final long serialVersionUID = -2629324471511903359L;
/*    */   
/*    */   public InsufficientDataException() {
/* 37 */     this((Localizable)LocalizedFormats.INSUFFICIENT_DATA, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InsufficientDataException(Localizable pattern, Object... arguments) {
/* 47 */     super(pattern, arguments);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/InsufficientDataException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */