/*    */ package org.jfree.chart.plot;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ public class GreyPalette
/*    */   extends ColorPalette
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -2120941170159987395L;
/*    */   
/*    */   public GreyPalette() {
/* 68 */     initialize();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initialize() {
/* 77 */     setPaletteName("Grey");
/*    */     
/* 79 */     this.r = new int[256];
/* 80 */     this.g = new int[256];
/* 81 */     this.b = new int[256];
/*    */     
/* 83 */     this.r[0] = 255;
/* 84 */     this.g[0] = 255;
/* 85 */     this.b[0] = 255;
/* 86 */     this.r[1] = 0;
/* 87 */     this.g[1] = 0;
/* 88 */     this.b[1] = 0;
/*    */     
/* 90 */     for (int i = 2; i < 256; i++) {
/* 91 */       this.r[i] = i;
/* 92 */       this.g[i] = i;
/* 93 */       this.b[i] = i;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/GreyPalette.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */