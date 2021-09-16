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
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.axis.LogAxis;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ import org.jfree.ui.FontChooserPanel;
/*     */ import org.jfree.ui.FontDisplayField;
/*     */ import org.jfree.ui.PaintSample;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultAxisEditor
/*     */   extends JPanel
/*     */   implements ActionListener
/*     */ {
/*     */   private JTextField label;
/*     */   private Font labelFont;
/*     */   private PaintSample labelPaintSample;
/*     */   private JTextField labelFontField;
/*     */   private Font tickLabelFont;
/*     */   private JTextField tickLabelFontField;
/*     */   private PaintSample tickLabelPaintSample;
/*     */   private JPanel slot1;
/*     */   private JPanel slot2;
/*     */   private JCheckBox showTickLabelsCheckBox;
/*     */   private JCheckBox showTickMarksCheckBox;
/*     */   private RectangleInsets tickLabelInsets;
/*     */   private RectangleInsets labelInsets;
/*     */   private JTabbedPane otherTabs;
/* 138 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DefaultAxisEditor getInstance(Axis axis) {
/* 152 */     if (axis != null) {
/*     */ 
/*     */       
/* 155 */       if (axis instanceof NumberAxis) {
/* 156 */         return new DefaultNumberAxisEditor((NumberAxis)axis);
/*     */       }
/* 158 */       if (axis instanceof LogAxis) {
/* 159 */         return new DefaultLogAxisEditor((LogAxis)axis);
/*     */       }
/*     */       
/* 162 */       return new DefaultAxisEditor(axis);
/*     */     } 
/*     */ 
/*     */     
/* 166 */     return null;
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
/*     */   
/*     */   public DefaultAxisEditor(Axis axis) {
/* 180 */     this.labelFont = axis.getLabelFont();
/* 181 */     this.labelPaintSample = new PaintSample(axis.getLabelPaint());
/* 182 */     this.tickLabelFont = axis.getTickLabelFont();
/* 183 */     this.tickLabelPaintSample = new PaintSample(axis.getTickLabelPaint());
/*     */ 
/*     */     
/* 186 */     this.tickLabelInsets = axis.getTickLabelInsets();
/* 187 */     this.labelInsets = axis.getLabelInsets();
/*     */     
/* 189 */     setLayout(new BorderLayout());
/*     */     
/* 191 */     JPanel general = new JPanel(new BorderLayout());
/* 192 */     general.setBorder(
/* 193 */         BorderFactory.createTitledBorder(
/* 194 */           BorderFactory.createEtchedBorder(), localizationResources
/* 195 */           .getString("General")));
/*     */ 
/*     */ 
/*     */     
/* 199 */     JPanel interior = new JPanel((LayoutManager)new LCBLayout(5));
/* 200 */     interior.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
/* 201 */     interior.add(new JLabel(localizationResources.getString("Label")));
/* 202 */     this.label = new JTextField(axis.getLabel());
/* 203 */     interior.add(this.label);
/* 204 */     interior.add(new JPanel());
/*     */     
/* 206 */     interior.add(new JLabel(localizationResources.getString("Font")));
/* 207 */     this.labelFontField = (JTextField)new FontDisplayField(this.labelFont);
/* 208 */     interior.add(this.labelFontField);
/* 209 */     JButton b = new JButton(localizationResources.getString("Select..."));
/* 210 */     b.setActionCommand("SelectLabelFont");
/* 211 */     b.addActionListener(this);
/* 212 */     interior.add(b);
/*     */     
/* 214 */     interior.add(new JLabel(localizationResources.getString("Paint")));
/* 215 */     interior.add((Component)this.labelPaintSample);
/* 216 */     b = new JButton(localizationResources.getString("Select..."));
/* 217 */     b.setActionCommand("SelectLabelPaint");
/* 218 */     b.addActionListener(this);
/* 219 */     interior.add(b);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     general.add(interior);
/*     */     
/* 244 */     add(general, "North");
/*     */     
/* 246 */     this.slot1 = new JPanel(new BorderLayout());
/*     */     
/* 248 */     JPanel other = new JPanel(new BorderLayout());
/* 249 */     other.setBorder(BorderFactory.createTitledBorder(
/* 250 */           BorderFactory.createEtchedBorder(), localizationResources
/* 251 */           .getString("Other")));
/*     */     
/* 253 */     this.otherTabs = new JTabbedPane();
/* 254 */     this.otherTabs.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
/*     */     
/* 256 */     JPanel ticks = new JPanel((LayoutManager)new LCBLayout(3));
/* 257 */     ticks.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*     */     
/* 259 */     this
/*     */       
/* 261 */       .showTickLabelsCheckBox = new JCheckBox(localizationResources.getString("Show_tick_labels"), axis.isTickLabelsVisible());
/*     */     
/* 263 */     ticks.add(this.showTickLabelsCheckBox);
/* 264 */     ticks.add(new JPanel());
/* 265 */     ticks.add(new JPanel());
/*     */     
/* 267 */     ticks.add(new JLabel(localizationResources
/* 268 */           .getString("Tick_label_font")));
/*     */     
/* 270 */     this.tickLabelFontField = (JTextField)new FontDisplayField(this.tickLabelFont);
/* 271 */     ticks.add(this.tickLabelFontField);
/* 272 */     b = new JButton(localizationResources.getString("Select..."));
/* 273 */     b.setActionCommand("SelectTickLabelFont");
/* 274 */     b.addActionListener(this);
/* 275 */     ticks.add(b);
/*     */     
/* 277 */     this
/*     */       
/* 279 */       .showTickMarksCheckBox = new JCheckBox(localizationResources.getString("Show_tick_marks"), axis.isTickMarksVisible());
/*     */     
/* 281 */     ticks.add(this.showTickMarksCheckBox);
/* 282 */     ticks.add(new JPanel());
/* 283 */     ticks.add(new JPanel());
/*     */     
/* 285 */     this.otherTabs.add(localizationResources.getString("Ticks"), ticks);
/*     */     
/* 287 */     other.add(this.otherTabs);
/*     */     
/* 289 */     this.slot1.add(other);
/*     */     
/* 291 */     this.slot2 = new JPanel(new BorderLayout());
/* 292 */     this.slot2.add(this.slot1, "North");
/* 293 */     add(this.slot2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabel() {
/* 303 */     return this.label.getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getLabelFont() {
/* 312 */     return this.labelFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLabelPaint() {
/* 321 */     return this.labelPaintSample.getPaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTickLabelsVisible() {
/* 330 */     return this.showTickLabelsCheckBox.isSelected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getTickLabelFont() {
/* 339 */     return this.tickLabelFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getTickLabelPaint() {
/* 348 */     return this.tickLabelPaintSample.getPaint();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTickMarksVisible() {
/* 358 */     return this.showTickMarksCheckBox.isSelected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getTickLabelInsets() {
/* 367 */     return (this.tickLabelInsets == null) ? new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D) : this.tickLabelInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getLabelInsets() {
/* 378 */     return (this.labelInsets == null) ? new RectangleInsets(0.0D, 0.0D, 0.0D, 0.0D) : this.labelInsets;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JTabbedPane getOtherTabs() {
/* 388 */     return this.otherTabs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 399 */     String command = event.getActionCommand();
/* 400 */     if (command.equals("SelectLabelFont")) {
/* 401 */       attemptLabelFontSelection();
/*     */     }
/* 403 */     else if (command.equals("SelectLabelPaint")) {
/* 404 */       attemptModifyLabelPaint();
/*     */     }
/* 406 */     else if (command.equals("SelectTickLabelFont")) {
/* 407 */       attemptTickLabelFontSelection();
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
/*     */   
/*     */   private void attemptLabelFontSelection() {
/* 422 */     FontChooserPanel panel = new FontChooserPanel(this.labelFont);
/* 423 */     int result = JOptionPane.showConfirmDialog(this, panel, localizationResources
/* 424 */         .getString("Font_Selection"), 2, -1);
/*     */ 
/*     */     
/* 427 */     if (result == 0) {
/* 428 */       this.labelFont = panel.getSelectedFont();
/* 429 */       this.labelFontField.setText(this.labelFont
/* 430 */           .getFontName() + " " + this.labelFont.getSize());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void attemptModifyLabelPaint() {
/* 441 */     Color c = JColorChooser.showDialog(this, localizationResources
/* 442 */         .getString("Label_Color"), Color.blue);
/*     */     
/* 444 */     if (c != null) {
/* 445 */       this.labelPaintSample.setPaint(c);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attemptTickLabelFontSelection() {
/* 454 */     FontChooserPanel panel = new FontChooserPanel(this.tickLabelFont);
/* 455 */     int result = JOptionPane.showConfirmDialog(this, panel, localizationResources
/* 456 */         .getString("Font_Selection"), 2, -1);
/*     */ 
/*     */     
/* 459 */     if (result == 0) {
/* 460 */       this.tickLabelFont = panel.getSelectedFont();
/* 461 */       this.tickLabelFontField.setText(this.tickLabelFont
/* 462 */           .getFontName() + " " + this.tickLabelFont
/* 463 */           .getSize());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAxisProperties(Axis axis) {
/* 513 */     axis.setLabel(getLabel());
/* 514 */     axis.setLabelFont(getLabelFont());
/* 515 */     axis.setLabelPaint(getLabelPaint());
/* 516 */     axis.setTickMarksVisible(isTickMarksVisible());
/*     */     
/* 518 */     axis.setTickLabelsVisible(isTickLabelsVisible());
/* 519 */     axis.setTickLabelFont(getTickLabelFont());
/* 520 */     axis.setTickLabelPaint(getTickLabelPaint());
/* 521 */     axis.setTickLabelInsets(getTickLabelInsets());
/* 522 */     axis.setLabelInsets(getLabelInsets());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultAxisEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */