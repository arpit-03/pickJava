/*    */ package org.apache.commons.math3.exception;
/*    */ 
/*    */ import org.apache.commons.math3.exception.util.ExceptionContext;
/*    */ import org.apache.commons.math3.exception.util.ExceptionContextProvider;
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
/*    */ 
/*    */ 
/*    */ public class MathArithmeticException
/*    */   extends ArithmeticException
/*    */   implements ExceptionContextProvider
/*    */ {
/*    */   private static final long serialVersionUID = -6024911025449780478L;
/*    */   private final ExceptionContext context;
/*    */   
/*    */   public MathArithmeticException() {
/* 43 */     this.context = new ExceptionContext(this);
/* 44 */     this.context.addMessage((Localizable)LocalizedFormats.ARITHMETIC_EXCEPTION, new Object[0]);
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
/*    */   public MathArithmeticException(Localizable pattern, Object... args) {
/* 56 */     this.context = new ExceptionContext(this);
/* 57 */     this.context.addMessage(pattern, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExceptionContext getContext() {
/* 62 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 68 */     return this.context.getMessage();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalizedMessage() {
/* 74 */     return this.context.getLocalizedMessage();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathArithmeticException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */