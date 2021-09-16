/*    */ package org.apache.commons.math3.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*    */ import org.apache.commons.math3.exception.NullArgumentException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultTransformer
/*    */   implements NumberTransformer, Serializable
/*    */ {
/*    */   private static final long serialVersionUID = 4019938025047800455L;
/*    */   
/*    */   public double transform(Object o) throws NullArgumentException, MathIllegalArgumentException {
/* 49 */     if (o == null) {
/* 50 */       throw new NullArgumentException(LocalizedFormats.OBJECT_TRANSFORMATION, new Object[0]);
/*    */     }
/*    */     
/* 53 */     if (o instanceof Number) {
/* 54 */       return ((Number)o).doubleValue();
/*    */     }
/*    */     
/*    */     try {
/* 58 */       return Double.parseDouble(o.toString());
/* 59 */     } catch (NumberFormatException e) {
/* 60 */       throw new MathIllegalArgumentException(LocalizedFormats.CANNOT_TRANSFORM_TO_DOUBLE, new Object[] { o.toString() });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object other) {
/* 68 */     if (this == other) {
/* 69 */       return true;
/*    */     }
/* 71 */     return other instanceof DefaultTransformer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 78 */     return 401993047;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/util/DefaultTransformer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */