/*    */ package Catalano.Math.Optimization;
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
/*    */ public class Constraint
/*    */ {
/*    */   private Symbol symbol;
/*    */   private double[] leftSide;
/*    */   private double rightSide;
/*    */   
/*    */   public enum Symbol
/*    */   {
/* 36 */     LESS_THAN,
/* 37 */     EQUAL_TO,
/* 38 */     GREATER_THAN;
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
/*    */   public double[] getLeftSide() {
/* 51 */     return this.leftSide;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getRightSide() {
/* 59 */     return this.rightSide;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Symbol getSymbol() {
/* 67 */     return this.symbol;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Constraint(double[] leftSide, Symbol symbol, double rightSide) {
/* 77 */     this(leftSide, rightSide, symbol);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Constraint(double[] leftSide, double rightSide, Symbol symbol) {
/* 87 */     this.leftSide = leftSide;
/* 88 */     this.rightSide = rightSide;
/* 89 */     this.symbol = symbol;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Optimization/Constraint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */