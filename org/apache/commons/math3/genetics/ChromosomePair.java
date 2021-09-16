/*    */ package org.apache.commons.math3.genetics;
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
/*    */ public class ChromosomePair
/*    */ {
/*    */   private final Chromosome first;
/*    */   private final Chromosome second;
/*    */   
/*    */   public ChromosomePair(Chromosome c1, Chromosome c2) {
/* 39 */     this.first = c1;
/* 40 */     this.second = c2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Chromosome getFirst() {
/* 49 */     return this.first;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Chromosome getSecond() {
/* 58 */     return this.second;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     return String.format("(%s,%s)", new Object[] { getFirst(), getSecond() });
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/genetics/ChromosomePair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */