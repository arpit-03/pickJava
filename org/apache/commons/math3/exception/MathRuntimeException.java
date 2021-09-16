/*    */ package org.apache.commons.math3.exception;
/*    */ 
/*    */ import org.apache.commons.math3.exception.util.ExceptionContext;
/*    */ import org.apache.commons.math3.exception.util.ExceptionContextProvider;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MathRuntimeException
/*    */   extends RuntimeException
/*    */   implements ExceptionContextProvider
/*    */ {
/*    */   private static final long serialVersionUID = 20120926L;
/*    */   private final ExceptionContext context;
/*    */   
/*    */   public MathRuntimeException(Localizable pattern, Object... args) {
/* 45 */     this.context = new ExceptionContext(this);
/* 46 */     this.context.addMessage(pattern, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExceptionContext getContext() {
/* 51 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 57 */     return this.context.getMessage();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalizedMessage() {
/* 63 */     return this.context.getLocalizedMessage();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathRuntimeException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */