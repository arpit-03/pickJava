/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JOptionPane;
/*    */ 
/*    */ public class SWSCheck
/*    */ {
/*  8 */   double VpVsRatio = 1.7D;
/*    */   
/*    */   public boolean repick = false;
/*    */   
/*    */   public SWSCheck(double[][] pickP, double[][] pickS, double[][] pickSS) {
/* 13 */     if ((((pickP.length != pickS.length) ? 1 : 0) | ((pickS.length != pickSS.length) ? 1 : 0)) != 0) {
/* 14 */       JOptionPane.showMessageDialog(new JFrame("Rot Mat Error"), "Unequal number of picks! Please contact IT.");
/*    */     }
/*    */ 
/*    */     
/* 18 */     String message = "SWS is > 0.3 for Receiver ";
/* 19 */     double swsValue = 0.0D; boolean showPrompt = false;
/*    */     
/* 21 */     for (int n = 0; n < pickP.length; n++) {
/*    */       
/* 23 */       if ((((pickP[n][1] == Double.NaN) ? 1 : 0) | ((pickS[n][1] == Double.NaN) ? 1 : 0) | ((pickSS[n][1] == Double.NaN) ? 1 : 0)) == 0) {
/*    */ 
/*    */ 
/*    */         
/* 27 */         swsValue = (pickS[n][1] - pickSS[n][1]) * (this.VpVsRatio - 1.0D) / (pickP[n][1] - pickS[n][1]) * this.VpVsRatio;
/*    */         
/* 29 */         if (swsValue > 0.3D) {
/* 30 */           showPrompt = true;
/* 31 */           message = String.valueOf(message) + "#" + String.valueOf(n + 1) + " ";
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 37 */     if (showPrompt) {
/*    */       
/* 39 */       message = String.valueOf(message) + "Please review your picks.";
/* 40 */       JOptionPane.showMessageDialog(new JFrame("SWS Warning"), message);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/SWSCheck.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */