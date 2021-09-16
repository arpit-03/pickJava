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
/*    */ public class MathInternalError
/*    */   extends MathIllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = -6276776513966934846L;
/*    */   private static final String REPORT_URL = "https://issues.apache.org/jira/browse/MATH";
/*    */   
/*    */   public MathInternalError() {
/* 37 */     getContext().addMessage((Localizable)LocalizedFormats.INTERNAL_ERROR, new Object[] { "https://issues.apache.org/jira/browse/MATH" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MathInternalError(Throwable cause) {
/* 45 */     super(cause, (Localizable)LocalizedFormats.INTERNAL_ERROR, new Object[] { "https://issues.apache.org/jira/browse/MATH" });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MathInternalError(Localizable pattern, Object... args) {
/* 55 */     super(pattern, args);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathInternalError.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */