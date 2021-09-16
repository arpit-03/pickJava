/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.GridLayout;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.ListModel;
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
/*     */ public class FontChooserPanel
/*     */   extends JPanel
/*     */ {
/*  74 */   public static final String[] SIZES = new String[] { "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "28", "36", "48", "72" };
/*     */ 
/*     */ 
/*     */   
/*     */   private JList fontlist;
/*     */ 
/*     */ 
/*     */   
/*     */   private JList sizelist;
/*     */ 
/*     */   
/*     */   private JCheckBox bold;
/*     */ 
/*     */   
/*     */   private JCheckBox italic;
/*     */ 
/*     */   
/*  91 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.ui.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FontChooserPanel(Font font) {
/* 102 */     GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
/* 103 */     String[] fonts = g.getAvailableFontFamilyNames();
/*     */     
/* 105 */     setLayout(new BorderLayout());
/* 106 */     JPanel right = new JPanel(new BorderLayout());
/*     */     
/* 108 */     JPanel fontPanel = new JPanel(new BorderLayout());
/* 109 */     fontPanel.setBorder(BorderFactory.createTitledBorder(
/* 110 */           BorderFactory.createEtchedBorder(), localizationResources
/* 111 */           .getString("Font")));
/* 112 */     this.fontlist = new JList<String>(fonts);
/* 113 */     JScrollPane fontpane = new JScrollPane(this.fontlist);
/* 114 */     fontpane.setBorder(BorderFactory.createEtchedBorder());
/* 115 */     fontPanel.add(fontpane);
/* 116 */     add(fontPanel);
/*     */     
/* 118 */     JPanel sizePanel = new JPanel(new BorderLayout());
/* 119 */     sizePanel.setBorder(BorderFactory.createTitledBorder(
/* 120 */           BorderFactory.createEtchedBorder(), localizationResources
/* 121 */           .getString("Size")));
/* 122 */     this.sizelist = new JList<String>(SIZES);
/* 123 */     JScrollPane sizepane = new JScrollPane(this.sizelist);
/* 124 */     sizepane.setBorder(BorderFactory.createEtchedBorder());
/* 125 */     sizePanel.add(sizepane);
/*     */     
/* 127 */     JPanel attributes = new JPanel(new GridLayout(1, 2));
/* 128 */     this.bold = new JCheckBox(localizationResources.getString("Bold"));
/* 129 */     this.italic = new JCheckBox(localizationResources.getString("Italic"));
/* 130 */     attributes.add(this.bold);
/* 131 */     attributes.add(this.italic);
/* 132 */     attributes.setBorder(BorderFactory.createTitledBorder(
/* 133 */           BorderFactory.createEtchedBorder(), localizationResources
/* 134 */           .getString("Attributes")));
/*     */     
/* 136 */     right.add(sizePanel, "Center");
/* 137 */     right.add(attributes, "South");
/*     */     
/* 139 */     add(right, "East");
/*     */     
/* 141 */     setSelectedFont(font);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getSelectedFont() {
/* 150 */     return new Font(getSelectedName(), getSelectedStyle(), 
/* 151 */         getSelectedSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedName() {
/* 160 */     return this.fontlist.getSelectedValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectedStyle() {
/* 169 */     if (this.bold.isSelected() && this.italic.isSelected()) {
/* 170 */       return 3;
/*     */     }
/* 172 */     if (this.bold.isSelected()) {
/* 173 */       return 1;
/*     */     }
/* 175 */     if (this.italic.isSelected()) {
/* 176 */       return 2;
/*     */     }
/*     */     
/* 179 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectedSize() {
/* 189 */     String selected = this.sizelist.getSelectedValue();
/* 190 */     if (selected != null) {
/* 191 */       return Integer.parseInt(selected);
/*     */     }
/*     */     
/* 194 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedFont(Font font) {
/* 205 */     if (font == null) {
/* 206 */       throw new NullPointerException();
/*     */     }
/* 208 */     this.bold.setSelected(font.isBold());
/* 209 */     this.italic.setSelected(font.isItalic());
/*     */     
/* 211 */     String fontName = font.getName();
/* 212 */     ListModel model = this.fontlist.getModel();
/* 213 */     this.fontlist.clearSelection();
/* 214 */     for (int i = 0; i < model.getSize(); i++) {
/* 215 */       if (fontName.equals(model.getElementAt(i))) {
/* 216 */         this.fontlist.setSelectedIndex(i);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 221 */     String fontSize = String.valueOf(font.getSize());
/* 222 */     model = this.sizelist.getModel();
/* 223 */     this.sizelist.clearSelection();
/* 224 */     for (int j = 0; j < model.getSize(); j++) {
/* 225 */       if (fontSize.equals(model.getElementAt(j))) {
/* 226 */         this.sizelist.setSelectedIndex(j);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/FontChooserPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */