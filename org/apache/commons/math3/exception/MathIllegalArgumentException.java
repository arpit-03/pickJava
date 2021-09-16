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
/*    */ public class MathIllegalArgumentException
/*    */   extends IllegalArgumentException
/*    */   implements ExceptionContextProvider
/*    */ {
/*    */   private static final long serialVersionUID = -6024911025449780478L;
/*    */   private final ExceptionContext context;
/*    */   
/*    */   public MathIllegalArgumentException(Localizable pattern, Object... args) {
/* 44 */     this.context = new ExceptionContext(this);
/* 45 */     this.context.addMessage(pattern, args);
/*    */   }
/*    */ 
/*    */   
/*    */   public ExceptionContext getContext() {
/* 50 */     return this.context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 56 */     return this.context.getMessage();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLocalizedMessage() {
/* 62 */     return this.context.getLocalizedMessage();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathIllegalArgumentException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */