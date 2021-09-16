/*    */ package org.jfree.ui;
/*    */ 
/*    */ import java.awt.event.WindowEvent;
/*    */ import java.awt.event.WindowListener;
/*    */ import javax.swing.JFrame;
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
/*    */ public class ApplicationFrame
/*    */   extends JFrame
/*    */   implements WindowListener
/*    */ {
/*    */   public ApplicationFrame(String title) {
/* 65 */     super(title);
/* 66 */     addWindowListener(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void windowClosing(WindowEvent event) {
/* 75 */     if (event.getWindow() == this) {
/* 76 */       dispose();
/* 77 */       System.exit(0);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void windowClosed(WindowEvent event) {}
/*    */   
/*    */   public void windowActivated(WindowEvent event) {}
/*    */   
/*    */   public void windowDeactivated(WindowEvent event) {}
/*    */   
/*    */   public void windowDeiconified(WindowEvent event) {}
/*    */   
/*    */   public void windowIconified(WindowEvent event) {}
/*    */   
/*    */   public void windowOpened(WindowEvent event) {}
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/ApplicationFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */