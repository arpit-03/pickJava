/*    */ package com.boreholeseismic.dsp;
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
/*    */ public class Sobel
/*    */ {
/* 32 */   private static final int[][] MASK_H = new int[][] { { 3, -3 }, { 10, -10 }, { 3, -3 } };
/* 33 */   private static final int[][] MASK_V = new int[][] { { 3, -3 }, { 10, -10 }, { 3, -3 } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int[][] Horizontal(int[][] raw) {
/* 44 */     int[][] out = null;
/* 45 */     int height = raw.length;
/* 46 */     int width = (raw[0]).length;
/*    */     
/* 48 */     if (height > 2 && width > 2) {
/* 49 */       out = new int[height - 2][width - 2];
/*    */       
/* 51 */       for (int r = 1; r < height - 1; r++) {
/* 52 */         for (int c = 1; c < width - 1; c++) {
/* 53 */           int sum = 0;
/*    */           
/* 55 */           for (int kr = -1; kr < 2; kr++) {
/* 56 */             for (int kc = -1; kc < 2; kc++) {
/* 57 */               sum += MASK_H[kr + 1][kc + 1] * raw[r + kr][c + kc];
/*    */             }
/*    */           } 
/*    */           
/* 61 */           out[r - 1][c - 1] = sum;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     return out;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int[][] Vertical(int[][] raw) {
/* 77 */     int[][] out = null;
/* 78 */     int height = raw.length;
/* 79 */     int width = (raw[0]).length;
/*    */     
/* 81 */     if (height > 2 || width > 2) {
/* 82 */       out = new int[height - 2][width - 2];
/*    */       
/* 84 */       for (int r = 1; r < height - 1; r++) {
/* 85 */         for (int c = 1; c < width - 1; c++) {
/* 86 */           int sum = 0;
/*    */           
/* 88 */           for (int kr = -1; kr < 2; kr++) {
/* 89 */             for (int kc = -1; kc < 2; kc++) {
/* 90 */               sum += MASK_V[kr + 1][kc + 1] * raw[r + kr][c + kc];
/*    */             }
/*    */           } 
/*    */           
/* 94 */           out[r - 1][c - 1] = sum;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 99 */     return out;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/Sobel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */