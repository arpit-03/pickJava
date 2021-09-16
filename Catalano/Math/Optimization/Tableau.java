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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tableau
/*    */ {
/*    */   private double[][] tableau;
/*    */   private double[] coefVars;
/*    */   private int[] basis;
/*    */   
/*    */   public int[] getBasis() {
/* 40 */     return this.basis;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[][] getTableau() {
/* 48 */     return this.tableau;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTableau(double[][] tableau) {
/* 56 */     this.tableau = tableau;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getCoefVars() {
/* 64 */     return this.coefVars;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCoefVars(double[] coefVars) {
/* 72 */     this.coefVars = coefVars;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Tableau(double[][] tableau, double[] coefVars, int[] basis) {
/* 82 */     this.tableau = tableau;
/* 83 */     this.coefVars = coefVars;
/* 84 */     this.basis = basis;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/Catalano/Math/Optimization/Tableau.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */