/*    */ package edu.mines.jtk.lapack;
/*    */ 
/*    */ import org.netlib.util.intW;
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
/*    */ class LapackInfo
/*    */   extends intW
/*    */ {
/*    */   LapackInfo() {
/* 28 */     super(0);
/*    */   }
/*    */   
/*    */   void check(String name) {
/* 32 */     if (this.val < 0) {
/* 33 */       throw new IllegalArgumentException("LAPACK " + name + ": error in argument number " + -this.val);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   int get(String name) {
/* 39 */     check(name);
/* 40 */     return this.val;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/edu/mines/jtk/lapack/LapackInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */