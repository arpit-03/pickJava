/*    */ package com.boreholeseismic.gui;
/*    */ 
/*    */ import java.io.File;
/*    */ import javax.swing.JFileChooser;
/*    */ import javax.swing.UIManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FolderBrowser
/*    */ {
/* 12 */   private int returnVal = 0;
/* 13 */   private JFileChooser BrowseFile = new JFileChooser();
/*    */ 
/*    */ 
/*    */   
/*    */   public FolderBrowser() {
/*    */     
/* 19 */     try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
/* 20 */     catch (Exception e) { e.printStackTrace(); }
/*    */ 
/*    */     
/* 23 */     this.BrowseFile.setDialogTitle("Select Input Folder");
/*    */     
/* 25 */     this.BrowseFile.setFileSelectionMode(2);
/* 26 */     int returnValue = this.BrowseFile.showOpenDialog(null);
/*    */     
/* 28 */     if (returnValue == 0) {
/* 29 */       this.returnVal = 1;
/*    */     }
/*    */     else {
/*    */       
/* 33 */       this.returnVal = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getReturnValue() {
/* 41 */     return this.returnVal;
/*    */   }
/*    */ 
/*    */   
/*    */   public File getSelectedFile() {
/* 46 */     if (this.BrowseFile.getSelectedFile().isDirectory()) {
/* 47 */       return this.BrowseFile.getSelectedFile();
/*    */     }
/* 49 */     return this.BrowseFile.getSelectedFile().getParentFile();
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/FolderBrowser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */