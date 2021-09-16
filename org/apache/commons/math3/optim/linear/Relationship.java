/*    */ package org.apache.commons.math3.optim.linear;
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
/*    */ public enum Relationship
/*    */ {
/* 26 */   EQ("="),
/*    */   
/* 28 */   LEQ("<="),
/*    */   
/* 30 */   GEQ(">=");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final String stringValue;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Relationship(String stringValue) {
/* 41 */     this.stringValue = stringValue;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return this.stringValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Relationship oppositeRelationship() {
/* 56 */     switch (this) {
/*    */       case LEQ:
/* 58 */         return GEQ;
/*    */       case GEQ:
/* 60 */         return LEQ;
/*    */     } 
/* 62 */     return EQ;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/optim/linear/Relationship.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */