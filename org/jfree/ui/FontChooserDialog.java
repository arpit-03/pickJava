/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import javax.swing.BorderFactory;
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
/*     */ public class FontChooserDialog
/*     */   extends StandardDialog
/*     */ {
/*     */   private FontChooserPanel fontChooserPanel;
/*     */   
/*     */   public FontChooserDialog(Dialog owner, String title, boolean modal, Font font) {
/*  72 */     super(owner, title, modal);
/*  73 */     setContentPane(createContent(font));
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
/*     */   public FontChooserDialog(Frame owner, String title, boolean modal, Font font) {
/*  85 */     super(owner, title, modal);
/*  86 */     setContentPane(createContent(font));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getSelectedFont() {
/*  95 */     return this.fontChooserPanel.getSelectedFont();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel createContent(Font font) {
/* 106 */     JPanel content = new JPanel(new BorderLayout());
/* 107 */     content.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/* 108 */     if (font == null) {
/* 109 */       font = new Font("Dialog", 10, 0);
/*     */     }
/* 111 */     this.fontChooserPanel = new FontChooserPanel(font);
/* 112 */     content.add(this.fontChooserPanel);
/*     */     
/* 114 */     JPanel buttons = createButtonPanel();
/* 115 */     buttons.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
/* 116 */     content.add(buttons, "South");
/*     */     
/* 118 */     return content;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/FontChooserDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */