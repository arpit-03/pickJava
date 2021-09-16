/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.border.TitledBorder;
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
/*     */ public class InsetsChooserPanel
/*     */   extends JPanel
/*     */ {
/*     */   private JTextField topValueEditor;
/*     */   private JTextField leftValueEditor;
/*     */   private JTextField bottomValueEditor;
/*     */   private JTextField rightValueEditor;
/*  88 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsetsChooserPanel() {
/*  96 */     this(new Insets(0, 0, 0, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsetsChooserPanel(Insets current) {
/* 106 */     current = (current == null) ? new Insets(0, 0, 0, 0) : current;
/*     */     
/* 108 */     this.topValueEditor = new JTextField(new IntegerDocument(), "" + current.top, 0);
/*     */     
/* 110 */     this.leftValueEditor = new JTextField(new IntegerDocument(), "" + current.left, 0);
/*     */     
/* 112 */     this.bottomValueEditor = new JTextField(new IntegerDocument(), "" + current.bottom, 0);
/*     */     
/* 114 */     this.rightValueEditor = new JTextField(new IntegerDocument(), "" + current.right, 0);
/*     */ 
/*     */     
/* 117 */     JPanel panel = new JPanel(new GridBagLayout());
/* 118 */     panel.setBorder(new TitledBorder(localizationResources
/* 119 */           .getString("Insets")));
/*     */ 
/*     */     
/* 122 */     panel.add(new JLabel(localizationResources.getString("Top")), new GridBagConstraints(1, 0, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     panel.add(new JLabel(" "), new GridBagConstraints(1, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 12, 0, 12), 8, 0));
/*     */ 
/*     */     
/* 131 */     panel.add(this.topValueEditor, new GridBagConstraints(2, 1, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */     
/* 134 */     panel.add(new JLabel(" "), new GridBagConstraints(3, 1, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 12, 0, 11), 8, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 139 */     panel.add(new JLabel(localizationResources.getString("Left")), new GridBagConstraints(0, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 4, 0, 4), 0, 0));
/*     */ 
/*     */ 
/*     */     
/* 143 */     panel.add(this.leftValueEditor, new GridBagConstraints(1, 2, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */     
/* 146 */     panel.add(new JLabel(" "), new GridBagConstraints(2, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 12, 0, 12), 8, 0));
/*     */ 
/*     */     
/* 149 */     panel.add(this.rightValueEditor, new GridBagConstraints(3, 2, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */     
/* 152 */     panel.add(new JLabel(localizationResources.getString("Right")), new GridBagConstraints(4, 2, 1, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 4, 0, 4), 0, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     panel.add(this.bottomValueEditor, new GridBagConstraints(2, 3, 1, 1, 0.0D, 0.0D, 10, 2, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 163 */     panel.add(new JLabel(localizationResources.getString("Bottom")), new GridBagConstraints(1, 4, 3, 1, 0.0D, 0.0D, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
/*     */ 
/*     */ 
/*     */     
/* 167 */     setLayout(new BorderLayout());
/* 168 */     add(panel, "Center");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Insets getInsetsValue() {
/* 179 */     return new Insets(
/* 180 */         Math.abs(stringToInt(this.topValueEditor.getText())), 
/* 181 */         Math.abs(stringToInt(this.leftValueEditor.getText())), 
/* 182 */         Math.abs(stringToInt(this.bottomValueEditor.getText())), 
/* 183 */         Math.abs(stringToInt(this.rightValueEditor.getText())));
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
/*     */   protected int stringToInt(String value) {
/* 196 */     value = value.trim();
/* 197 */     if (value.length() == 0) {
/* 198 */       return 0;
/*     */     }
/*     */     
/*     */     try {
/* 202 */       return Integer.parseInt(value);
/*     */     }
/* 204 */     catch (NumberFormatException e) {
/* 205 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeNotify() {
/* 214 */     super.removeNotify();
/* 215 */     removeAll();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/InsetsChooserPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */