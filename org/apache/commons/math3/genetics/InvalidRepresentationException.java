/*    */ package org.apache.commons.math3.genetics;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*    */ public class InvalidRepresentationException
/*    */   extends MathIllegalArgumentException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InvalidRepresentationException(Localizable pattern, Object... args) {
/* 39 */     super(pattern, args);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/InvalidRepresentationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */