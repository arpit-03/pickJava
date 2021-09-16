/*     */ package org.jfree.ui.tabbedui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TabbedFrame
/*     */   extends JFrame
/*     */ {
/*     */   private AbstractTabbedUI tabbedUI;
/*     */   
/*     */   private class MenuBarChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/*  82 */       if (evt.getPropertyName().equals("jMenuBar")) {
/*  83 */         TabbedFrame.this.setJMenuBar(TabbedFrame.this.getTabbedUI().getJMenuBar());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedFrame() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedFrame(String title) {
/* 100 */     super(title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractTabbedUI getTabbedUI() {
/* 110 */     return this.tabbedUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(AbstractTabbedUI tabbedUI) {
/* 120 */     this.tabbedUI = tabbedUI;
/* 121 */     this.tabbedUI.addPropertyChangeListener("jMenuBar", new MenuBarChangeListener());
/*     */ 
/*     */ 
/*     */     
/* 125 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent e) {
/* 127 */             TabbedFrame.this.getTabbedUI().getCloseAction()
/* 128 */               .actionPerformed(new ActionEvent(this, 1001, null, 0));
/*     */           }
/*     */         });
/*     */     
/* 132 */     JPanel panel = new JPanel();
/* 133 */     panel.setLayout(new BorderLayout());
/* 134 */     panel.add(tabbedUI, "Center");
/* 135 */     setContentPane(panel);
/* 136 */     setJMenuBar(tabbedUI.getJMenuBar());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/tabbedui/TabbedFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */