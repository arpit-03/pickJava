/*    */ package org.apache.commons.math3.exception;
/*    */ 
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
/*    */ 
/*    */ public class MathParseException
/*    */   extends MathIllegalStateException
/*    */   implements ExceptionContextProvider
/*    */ {
/*    */   private static final long serialVersionUID = -6024911025449780478L;
/*    */   
/*    */   public MathParseException(String wrong, int position, Class<?> type) {
/* 42 */     getContext().addMessage((Localizable)LocalizedFormats.CANNOT_PARSE_AS_TYPE, new Object[] { wrong, Integer.valueOf(position), type.getName() });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MathParseException(String wrong, int position) {
/* 53 */     getContext().addMessage((Localizable)LocalizedFormats.CANNOT_PARSE, new Object[] { wrong, Integer.valueOf(position) });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathParseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */