/*    */ package com.boreholeseismic.gui;
/*    */ 
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import javax.swing.UIManager;
/*    */ import javax.swing.UnsupportedLookAndFeelException;
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
/*    */ public class PickingRebuild
/*    */ {
/*    */   public static void main(String[] args) throws FileNotFoundException, IOException {
/*    */     try {
/* 51 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/* 52 */     } catch (ClassNotFoundException e) {
/*    */       
/* 54 */       e.printStackTrace();
/* 55 */     } catch (InstantiationException e) {
/*    */       
/* 57 */       e.printStackTrace();
/* 58 */     } catch (IllegalAccessException e) {
/*    */       
/* 60 */       e.printStackTrace();
/* 61 */     } catch (UnsupportedLookAndFeelException e) {
/*    */       
/* 63 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 66 */     System.setProperty("apple.laf.useScreenMenuBar", "true");
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/PickingRebuild.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */