/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Font;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.title.Title;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ import org.jfree.ui.FontChooserPanel;
/*     */ import org.jfree.ui.FontDisplayField;
/*     */ import org.jfree.ui.PaintSample;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultTitleEditor
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*     */   private boolean showTitle;
/*     */   private JCheckBox showTitleCheckBox;
/*     */   private JTextField titleField;
/*     */   private Font titleFont;
/*     */   private JTextField fontfield;
/*     */   private JButton selectFontButton;
/*     */   private PaintSample titlePaint;
/*     */   private JButton selectPaintButton;
/* 103 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultTitleEditor(Title title) {
/* 115 */     TextTitle t = (title != null) ? (TextTitle)title : new TextTitle(localizationResources.getString("Title"));
/* 116 */     this.showTitle = (title != null);
/* 117 */     this.titleFont = t.getFont();
/* 118 */     this.titleField = new JTextField(t.getText());
/* 119 */     this.titlePaint = new PaintSample(t.getPaint());
/*     */     
/* 121 */     setLayout(new BorderLayout());
/*     */     
/* 123 */     JPanel general = new JPanel(new BorderLayout());
/* 124 */     general.setBorder(
/* 125 */         BorderFactory.createTitledBorder(
/* 126 */           BorderFactory.createEtchedBorder(), localizationResources
/* 127 */           .getString("General")));
/*     */ 
/*     */ 
/*     */     
/* 131 */     JPanel interior = new JPanel((LayoutManager)new LCBLayout(4));
/* 132 */     interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
/*     */     
/* 134 */     interior.add(new JLabel(localizationResources.getString("Show_Title")));
/* 135 */     this.showTitleCheckBox = new JCheckBox();
/* 136 */     this.showTitleCheckBox.setSelected(this.showTitle);
/* 137 */     this.showTitleCheckBox.setActionCommand("ShowTitle");
/* 138 */     this.showTitleCheckBox.addActionListener(this);
/* 139 */     interior.add(new JPanel());
/* 140 */     interior.add(this.showTitleCheckBox);
/*     */     
/* 142 */     JLabel titleLabel = new JLabel(localizationResources.getString("Text"));
/* 143 */     interior.add(titleLabel);
/* 144 */     interior.add(this.titleField);
/* 145 */     interior.add(new JPanel());
/*     */     
/* 147 */     JLabel fontLabel = new JLabel(localizationResources.getString("Font"));
/* 148 */     this.fontfield = (JTextField)new FontDisplayField(this.titleFont);
/* 149 */     this
/* 150 */       .selectFontButton = new JButton(localizationResources.getString("Select..."));
/*     */     
/* 152 */     this.selectFontButton.setActionCommand("SelectFont");
/* 153 */     this.selectFontButton.addActionListener(this);
/* 154 */     interior.add(fontLabel);
/* 155 */     interior.add(this.fontfield);
/* 156 */     interior.add(this.selectFontButton);
/*     */ 
/*     */     
/* 159 */     JLabel colorLabel = new JLabel(localizationResources.getString("Color"));
/*     */     
/* 161 */     this
/* 162 */       .selectPaintButton = new JButton(localizationResources.getString("Select..."));
/*     */     
/* 164 */     this.selectPaintButton.setActionCommand("SelectPaint");
/* 165 */     this.selectPaintButton.addActionListener(this);
/* 166 */     interior.add(colorLabel);
/* 167 */     interior.add((Component)this.titlePaint);
/* 168 */     interior.add(this.selectPaintButton);
/*     */     
/* 170 */     enableOrDisableControls();
/*     */     
/* 172 */     general.add(interior);
/* 173 */     add(general, "North");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitleText() {
/* 182 */     return this.titleField.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getTitleFont() {
/* 191 */     return this.titleFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getTitlePaint() {
/* 200 */     return this.titlePaint.getPaint();
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
/*     */   public void actionPerformed(ActionEvent event) {
/* 212 */     String command = event.getActionCommand();
/*     */     
/* 214 */     if (command.equals("SelectFont")) {
/* 215 */       attemptFontSelection();
/*     */     }
/* 217 */     else if (command.equals("SelectPaint")) {
/* 218 */       attemptPaintSelection();
/*     */     }
/* 220 */     else if (command.equals("ShowTitle")) {
/* 221 */       attemptModifyShowTitle();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attemptFontSelection() {
/* 230 */     FontChooserPanel panel = new FontChooserPanel(this.titleFont);
/*     */     
/* 232 */     int result = JOptionPane.showConfirmDialog(this, panel, localizationResources
/* 233 */         .getString("Font_Selection"), 2, -1);
/*     */ 
/*     */ 
/*     */     
/* 237 */     if (result == 0) {
/* 238 */       this.titleFont = panel.getSelectedFont();
/* 239 */       this.fontfield.setText(this.titleFont
/* 240 */           .getFontName() + " " + this.titleFont.getSize());
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
/*     */   public void attemptPaintSelection() {
/* 252 */     Paint p = this.titlePaint.getPaint();
/* 253 */     Color defaultColor = (p instanceof Color) ? (Color)p : Color.blue;
/* 254 */     Color c = JColorChooser.showDialog(this, localizationResources
/* 255 */         .getString("Title_Color"), defaultColor);
/*     */     
/* 257 */     if (c != null) {
/* 258 */       this.titlePaint.setPaint(c);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptModifyShowTitle() {
/* 267 */     this.showTitle = this.showTitleCheckBox.isSelected();
/* 268 */     enableOrDisableControls();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enableOrDisableControls() {
/* 276 */     boolean enabled = (this.showTitle == true);
/* 277 */     this.titleField.setEnabled(enabled);
/* 278 */     this.selectFontButton.setEnabled(enabled);
/* 279 */     this.selectPaintButton.setEnabled(enabled);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTitleProperties(JFreeChart chart) {
/* 289 */     if (this.showTitle) {
/* 290 */       TextTitle title = chart.getTitle();
/* 291 */       if (title == null) {
/* 292 */         title = new TextTitle();
/* 293 */         chart.setTitle(title);
/*     */       } 
/* 295 */       title.setText(getTitleText());
/* 296 */       title.setFont(getTitleFont());
/* 297 */       title.setPaint(getTitlePaint());
/*     */     } else {
/*     */       
/* 300 */       chart.setTitle((TextTitle)null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultTitleEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */