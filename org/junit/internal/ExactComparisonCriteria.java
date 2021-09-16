/*   */ package org.junit.internal;
/*   */ 
/*   */ import org.junit.Assert;
/*   */ 
/*   */ public class ExactComparisonCriteria
/*   */   extends ComparisonCriteria {
/*   */   protected void assertElementsEqual(Object expected, Object actual) {
/* 8 */     Assert.assertEquals(expected, actual);
/*   */   }
/*   */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/junit/internal/ExactComparisonCriteria.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */