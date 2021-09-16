/*    */ package com.jmatio.types;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class MLCell
/*    */   extends MLArray
/*    */ {
/*    */   private ArrayList<MLArray> cells;
/*    */   
/*    */   public MLCell(String name, int[] dims) {
/* 11 */     this(name, dims, 1, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public MLCell(String name, int[] dims, int type, int attributes) {
/* 16 */     super(name, dims, type, attributes);
/*    */     
/* 18 */     this.cells = new ArrayList<MLArray>(getM() * getN());
/*    */     
/* 20 */     for (int i = 0; i < getM() * getN(); i++)
/*    */     {
/* 22 */       this.cells.add(new MLEmptyArray());
/*    */     }
/*    */   }
/*    */   
/*    */   public void set(MLArray value, int m, int n) {
/* 27 */     this.cells.set(getIndex(m, n), value);
/*    */   }
/*    */   
/*    */   public void set(MLArray value, int index) {
/* 31 */     this.cells.set(index, value);
/*    */   }
/*    */   
/*    */   public MLArray get(int m, int n) {
/* 35 */     return this.cells.get(getIndex(m, n));
/*    */   }
/*    */   
/*    */   public MLArray get(int index) {
/* 39 */     return this.cells.get(index);
/*    */   }
/*    */   
/*    */   public int getIndex(int m, int n) {
/* 43 */     return m + n * getM();
/*    */   }
/*    */   
/*    */   public ArrayList<MLArray> cells() {
/* 47 */     return this.cells;
/*    */   }
/*    */   
/*    */   public String contentToString() {
/* 51 */     StringBuffer sb = new StringBuffer();
/* 52 */     sb.append(this.name + " = \n");
/*    */     
/* 54 */     for (int m = 0; m < getM(); m++) {
/*    */       
/* 56 */       sb.append("\t");
/* 57 */       for (int n = 0; n < getN(); n++) {
/*    */         
/* 59 */         sb.append(get(m, n));
/* 60 */         sb.append("\t");
/*    */       } 
/* 62 */       sb.append("\n");
/*    */     } 
/* 64 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/types/MLCell.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */