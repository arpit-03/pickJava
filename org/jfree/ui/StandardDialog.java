/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Frame;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.util.ResourceBundleWrapper;
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
/*     */ public class StandardDialog
/*     */   extends JDialog
/*     */   implements ActionListener
/*     */ {
/*     */   private boolean cancelled;
/*  74 */   protected static final ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
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
/*     */   public StandardDialog(Frame owner, String title, boolean modal) {
/*  86 */     super(owner, title, modal);
/*  87 */     this.cancelled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardDialog(Dialog owner, String title, boolean modal) {
/*  99 */     super(owner, title, modal);
/* 100 */     this.cancelled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCancelled() {
/* 110 */     return this.cancelled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 119 */     String command = event.getActionCommand();
/* 120 */     if (!command.equals("helpButton"))
/*     */     {
/*     */       
/* 123 */       if (command.equals("okButton")) {
/* 124 */         this.cancelled = false;
/* 125 */         setVisible(false);
/*     */       }
/* 127 */       else if (command.equals("cancelButton")) {
/* 128 */         this.cancelled = true;
/* 129 */         setVisible(false);
/*     */       } 
/*     */     }
/*     */   }
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
/*     */   protected JPanel createButtonPanel() {
/* 144 */     L1R2ButtonPanel buttons = new L1R2ButtonPanel(localizationResources.getString("Help"), localizationResources.getString("OK"), localizationResources.getString("Cancel"));
/*     */     
/* 146 */     JButton helpButton = buttons.getLeftButton();
/* 147 */     helpButton.setActionCommand("helpButton");
/* 148 */     helpButton.addActionListener(this);
/*     */     
/* 150 */     JButton okButton = buttons.getRightButton1();
/* 151 */     okButton.setActionCommand("okButton");
/* 152 */     okButton.addActionListener(this);
/*     */     
/* 154 */     JButton cancelButton = buttons.getRightButton2();
/* 155 */     cancelButton.setActionCommand("cancelButton");
/* 156 */     cancelButton.addActionListener(this);
/*     */     
/* 158 */     buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
/* 159 */     return buttons;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/StandardDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */