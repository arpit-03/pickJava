/*     */ package org.jfree.ui.tabbedui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import javax.swing.JDialog;
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
/*     */ public class TabbedDialog
/*     */   extends JDialog
/*     */ {
/*     */   private AbstractTabbedUI tabbedUI;
/*     */   
/*     */   private class MenuBarChangeListener
/*     */     implements PropertyChangeListener
/*     */   {
/*     */     public void propertyChange(PropertyChangeEvent evt) {
/*  84 */       if (evt.getPropertyName().equals("jMenuBar")) {
/*  85 */         TabbedDialog.this.setJMenuBar(TabbedDialog.this.getTabbedUI().getJMenuBar());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Dialog owner) {
/* 101 */     super(owner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Dialog owner, boolean modal) {
/* 111 */     super(owner, modal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Dialog owner, String title) {
/* 121 */     super(owner, title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Dialog owner, String title, boolean modal) {
/* 132 */     super(owner, title, modal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Frame owner) {
/* 141 */     super(owner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Frame owner, boolean modal) {
/* 151 */     super(owner, modal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Frame owner, String title) {
/* 161 */     super(owner, title);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TabbedDialog(Frame owner, String title, boolean modal) {
/* 172 */     super(owner, title, modal);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final AbstractTabbedUI getTabbedUI() {
/* 183 */     return this.tabbedUI;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init(AbstractTabbedUI tabbedUI) {
/* 193 */     this.tabbedUI = tabbedUI;
/* 194 */     this.tabbedUI
/* 195 */       .addPropertyChangeListener("jMenuBar", new MenuBarChangeListener());
/*     */     
/* 197 */     addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent e) {
/* 199 */             TabbedDialog.this.getTabbedUI().getCloseAction()
/* 200 */               .actionPerformed(new ActionEvent(this, 1001, null, 0));
/*     */           }
/*     */         });
/*     */     
/* 204 */     JPanel panel = new JPanel();
/* 205 */     panel.setLayout(new BorderLayout());
/* 206 */     panel.add(tabbedUI, "Center");
/* 207 */     setContentPane(panel);
/* 208 */     setJMenuBar(tabbedUI.getJMenuBar());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/tabbedui/TabbedDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */