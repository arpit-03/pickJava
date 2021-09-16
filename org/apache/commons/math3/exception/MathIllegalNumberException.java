/*    */ package org.apache.commons.math3.exception;
/*    */ 
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
/*    */ public class MathIllegalNumberException
/*    */   extends MathIllegalArgumentException
/*    */ {
/* 32 */   protected static final Integer INTEGER_ZERO = Integer.valueOf(0);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final long serialVersionUID = -7447085893598031110L;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Number argument;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MathIllegalNumberException(Localizable pattern, Number wrong, Object... arguments) {
/* 50 */     super(pattern, new Object[] { wrong, arguments });
/* 51 */     this.argument = wrong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Number getArgument() {
/* 58 */     return this.argument;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/MathIllegalNumberException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */