/*    */ package org.apache.commons.math3.optimization.linear;
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
/*    */ @Deprecated
/*    */ public enum Relationship
/*    */ {
/* 29 */   EQ("="),
/*    */ 
/*    */   
/* 32 */   LEQ("<="),
/*    */ 
/*    */   
/* 35 */   GEQ(">=");
/*    */ 
/*    */ 
/*    */   
/*    */   private final String stringValue;
/*    */ 
/*    */ 
/*    */   
/*    */   Relationship(String stringValue) {
/* 44 */     this.stringValue = stringValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return this.stringValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Relationship oppositeRelationship() {
/* 58 */     switch (this) {
/*    */       case LEQ:
/* 60 */         return GEQ;
/*    */       case GEQ:
/* 62 */         return LEQ;
/*    */     } 
/* 64 */     return EQ;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optimization/linear/Relationship.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */