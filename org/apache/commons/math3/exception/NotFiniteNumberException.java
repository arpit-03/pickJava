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
/*    */ 
/*    */ 
/*    */ public class NotFiniteNumberException
/*    */   extends MathIllegalNumberException
/*    */ {
/*    */   private static final long serialVersionUID = -6100997100383932834L;
/*    */   
/*    */   public NotFiniteNumberException(Number wrong, Object... args) {
/* 39 */     this((Localizable)LocalizedFormats.NOT_FINITE_NUMBER, wrong, args);
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
/*    */   
/*    */   public NotFiniteNumberException(Localizable specific, Number wrong, Object... args) {
/* 52 */     super(specific, wrong, args);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NotFiniteNumberException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */