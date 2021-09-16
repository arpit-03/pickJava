/*    */ package org.la4j.iterator;
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
/*    */ abstract class JoinFunction
/*    */ {
/* 29 */   public static final JoinFunction ADD = new JoinFunction()
/*    */     {
/*    */       public double apply(double a, double b) {
/* 32 */         return a + b;
/*    */       }
/*    */     };
/*    */   
/* 36 */   public static final JoinFunction SUB = new JoinFunction()
/*    */     {
/*    */       public double apply(double a, double b) {
/* 39 */         return a - b;
/*    */       }
/*    */     };
/*    */   
/* 43 */   public static final JoinFunction MUL = new JoinFunction()
/*    */     {
/*    */       public double apply(double a, double b) {
/* 46 */         return a * b;
/*    */       }
/*    */     };
/*    */   
/* 50 */   public static final JoinFunction DIV = new JoinFunction()
/*    */     {
/*    */       public double apply(double a, double b) {
/* 53 */         return a / b;
/*    */       }
/*    */     };
/*    */   
/* 57 */   public static final JoinFunction MOD = new JoinFunction()
/*    */     {
/*    */       public double apply(double a, double b) {
/* 60 */         return a % b;
/*    */       }
/*    */     };
/*    */   
/*    */   public abstract double apply(double paramDouble1, double paramDouble2);
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/la4j/iterator/JoinFunction.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */