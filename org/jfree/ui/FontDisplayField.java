/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Font;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FontDisplayField
/*     */   extends JTextField
/*     */ {
/*     */   private Font displayFont;
/*  71 */   protected static final ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontDisplayField(Font font) {
/*  80 */     super("");
/*  81 */     setDisplayFont(font);
/*  82 */     setEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getDisplayFont() {
/*  91 */     return this.displayFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisplayFont(Font font) {
/* 100 */     this.displayFont = font;
/* 101 */     setText(fontToString(this.displayFont));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String fontToString(Font font) {
/* 112 */     if (font != null) {
/* 113 */       return font.getFontName() + ", " + font.getSize();
/*     */     }
/*     */     
/* 116 */     return localizationResources.getString("No_Font_Selected");
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/FontDisplayField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */