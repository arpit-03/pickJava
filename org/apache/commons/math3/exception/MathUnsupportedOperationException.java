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
/*    */ public class MathUnsupportedOperationException
/*    */   extends UnsupportedOperationException
/*    */   implements ExceptionContextProvider
/*    */ {
/*    */   private static final long serialVersionUID = -6024911025449780478L;
/*    */   private final ExceptionContext context;
/*    */   
/*    */   public MathUnsupportedOperationException() {
/* 43 */     this((Localizable)LocalizedFormats.UNSUPPORTED_OPERATION, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MathUnsupportedOperationException(Localizable pattern, Object... args) {
/* 52 */     this.context = new ExceptionContext(this);
/* 53 */     this.context.addMessage(pattern, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExceptionContext getContext() {
/* 58 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 64 */     return this.context.getMessage();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalizedMessage() {
/* 70 */     return this.context.getLocalizedMessage();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathUnsupportedOperationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */