/*    */ package org.apache.commons.math3.linear;
/*    */ 
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
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
/*    */ public class NonPositiveDefiniteOperatorException
/*    */   extends MathIllegalArgumentException
/*    */ {
/*    */   private static final long serialVersionUID = 917034489420549847L;
/*    */   
/*    */   public NonPositiveDefiniteOperatorException() {
/* 41 */     super((Localizable)LocalizedFormats.NON_POSITIVE_DEFINITE_OPERATOR, new Object[0]);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/linear/NonPositiveDefiniteOperatorException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */