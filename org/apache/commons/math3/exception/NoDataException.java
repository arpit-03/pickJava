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
/*    */ public class NoDataException
/*    */   extends MathIllegalArgumentException
/*    */ {
/*    */   private static final long serialVersionUID = -3629324471511904459L;
/*    */   
/*    */   public NoDataException() {
/* 36 */     this((Localizable)LocalizedFormats.NO_DATA);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NoDataException(Localizable specific) {
/* 44 */     super(specific, new Object[0]);
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/exception/NoDataException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */