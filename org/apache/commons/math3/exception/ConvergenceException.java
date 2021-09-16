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
/*    */ public class ConvergenceException
/*    */   extends MathIllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = 4330003017885151975L;
/*    */   
/*    */   public ConvergenceException() {
/* 36 */     this((Localizable)LocalizedFormats.CONVERGENCE_FAILED, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ConvergenceException(Localizable pattern, Object... args) {
/* 48 */     getContext().addMessage(pattern, args);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/ConvergenceException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */