/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Insets;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JTextField;
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
/*     */ public class InsetsTextField
/*     */   extends JTextField
/*     */ {
/*  65 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsetsTextField(Insets insets) {
/*  76 */     setInsets(insets);
/*  77 */     setEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String formatInsetsString(Insets insets) {
/*  88 */     insets = (insets == null) ? new Insets(0, 0, 0, 0) : insets;
/*     */     
/*  90 */     return localizationResources.getString("T") + insets.top + ", " + localizationResources
/*  91 */       .getString("L") + insets.left + ", " + localizationResources
/*  92 */       .getString("B") + insets.bottom + ", " + localizationResources
/*  93 */       .getString("R") + insets.right;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInsets(Insets insets) {
/* 104 */     setText(formatInsetsString(insets));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/InsetsTextField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */