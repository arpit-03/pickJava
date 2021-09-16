/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JColorChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ import org.jfree.ui.PaintSample;
/*     */ import org.jfree.ui.StrokeChooserPanel;
/*     */ import org.jfree.ui.StrokeSample;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultValueAxisEditor
/*     */   extends DefaultAxisEditor
/*     */   implements FocusListener
/*     */ {
/*     */   private boolean autoRange;
/*     */   private boolean autoTickUnitSelection;
/*     */   private double minimumValue;
/*     */   private double maximumValue;
/*     */   private JCheckBox autoRangeCheckBox;
/*     */   private JCheckBox autoTickUnitSelectionCheckBox;
/*     */   private JTextField minimumRangeValue;
/*     */   private JTextField maximumRangeValue;
/*     */   private PaintSample gridPaintSample;
/*     */   private StrokeSample gridStrokeSample;
/*     */   private StrokeSample[] availableStrokeSamples;
/* 115 */   protected static ResourceBundle localizationResources = ResourceBundleWrapper.getBundle("org.jfree.chart.editor.LocalizationBundle");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultValueAxisEditor(ValueAxis axis) {
/* 125 */     super((Axis)axis);
/*     */     
/* 127 */     this.autoRange = axis.isAutoRange();
/* 128 */     this.minimumValue = axis.getLowerBound();
/* 129 */     this.maximumValue = axis.getUpperBound();
/* 130 */     this.autoTickUnitSelection = axis.isAutoTickUnitSelection();
/*     */     
/* 132 */     this.gridPaintSample = new PaintSample(Color.blue);
/* 133 */     this.gridStrokeSample = new StrokeSample(new BasicStroke(1.0F));
/*     */     
/* 135 */     this.availableStrokeSamples = new StrokeSample[3];
/* 136 */     this.availableStrokeSamples[0] = new StrokeSample(new BasicStroke(1.0F));
/*     */     
/* 138 */     this.availableStrokeSamples[1] = new StrokeSample(new BasicStroke(2.0F));
/*     */     
/* 140 */     this.availableStrokeSamples[2] = new StrokeSample(new BasicStroke(3.0F));
/*     */ 
/*     */     
/* 143 */     JTabbedPane other = getOtherTabs();
/*     */     
/* 145 */     JPanel range = new JPanel((LayoutManager)new LCBLayout(3));
/* 146 */     range.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*     */     
/* 148 */     range.add(new JPanel());
/* 149 */     this.autoRangeCheckBox = new JCheckBox(localizationResources.getString("Auto-adjust_range"), this.autoRange);
/*     */     
/* 151 */     this.autoRangeCheckBox.setActionCommand("AutoRangeOnOff");
/* 152 */     this.autoRangeCheckBox.addActionListener(this);
/* 153 */     range.add(this.autoRangeCheckBox);
/* 154 */     range.add(new JPanel());
/*     */     
/* 156 */     range.add(new JLabel(localizationResources.getString("Minimum_range_value")));
/*     */     
/* 158 */     this.minimumRangeValue = new JTextField(Double.toString(this.minimumValue));
/*     */     
/* 160 */     this.minimumRangeValue.setEnabled(!this.autoRange);
/* 161 */     this.minimumRangeValue.setActionCommand("MinimumRange");
/* 162 */     this.minimumRangeValue.addActionListener(this);
/* 163 */     this.minimumRangeValue.addFocusListener(this);
/* 164 */     range.add(this.minimumRangeValue);
/* 165 */     range.add(new JPanel());
/*     */     
/* 167 */     range.add(new JLabel(localizationResources.getString("Maximum_range_value")));
/*     */     
/* 169 */     this.maximumRangeValue = new JTextField(Double.toString(this.maximumValue));
/*     */     
/* 171 */     this.maximumRangeValue.setEnabled(!this.autoRange);
/* 172 */     this.maximumRangeValue.setActionCommand("MaximumRange");
/* 173 */     this.maximumRangeValue.addActionListener(this);
/* 174 */     this.maximumRangeValue.addFocusListener(this);
/* 175 */     range.add(this.maximumRangeValue);
/* 176 */     range.add(new JPanel());
/*     */     
/* 178 */     other.add(localizationResources.getString("Range"), range);
/*     */     
/* 180 */     other.add(localizationResources.getString("TickUnit"), 
/* 181 */         createTickUnitPanel());
/*     */   }
/*     */   
/*     */   protected JPanel createTickUnitPanel() {
/* 185 */     JPanel tickUnitPanel = new JPanel((LayoutManager)new LCBLayout(3));
/* 186 */     tickUnitPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*     */     
/* 188 */     tickUnitPanel.add(new JPanel());
/* 189 */     this
/* 190 */       .autoTickUnitSelectionCheckBox = new JCheckBox(localizationResources.getString("Auto-TickUnit_Selection"), this.autoTickUnitSelection);
/*     */     
/* 192 */     this.autoTickUnitSelectionCheckBox.setActionCommand("AutoTickOnOff");
/* 193 */     this.autoTickUnitSelectionCheckBox.addActionListener(this);
/* 194 */     tickUnitPanel.add(this.autoTickUnitSelectionCheckBox);
/* 195 */     tickUnitPanel.add(new JPanel());
/*     */     
/* 197 */     return tickUnitPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAutoTickUnitSelection() {
/* 206 */     return this.autoTickUnitSelection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAutoTickUnitSelection(boolean autoTickUnitSelection) {
/* 214 */     this.autoTickUnitSelection = autoTickUnitSelection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JCheckBox getAutoTickUnitSelectionCheckBox() {
/* 223 */     return this.autoTickUnitSelectionCheckBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAutoTickUnitSelectionCheckBox(JCheckBox autoTickUnitSelectionCheckBox) {
/* 233 */     this.autoTickUnitSelectionCheckBox = autoTickUnitSelectionCheckBox;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAutoRange() {
/* 242 */     return this.autoRange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMinimumValue() {
/* 251 */     return this.minimumValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMaximumValue() {
/* 260 */     return this.maximumValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 269 */     String command = event.getActionCommand();
/* 270 */     if (command.equals("GridStroke")) {
/* 271 */       attemptGridStrokeSelection();
/*     */     }
/* 273 */     else if (command.equals("GridPaint")) {
/* 274 */       attemptGridPaintSelection();
/*     */     }
/* 276 */     else if (command.equals("AutoRangeOnOff")) {
/* 277 */       toggleAutoRange();
/*     */     }
/* 279 */     else if (command.equals("MinimumRange")) {
/* 280 */       validateMinimum();
/*     */     }
/* 282 */     else if (command.equals("MaximumRange")) {
/* 283 */       validateMaximum();
/*     */     }
/* 285 */     else if (command.equals("AutoTickOnOff")) {
/* 286 */       toggleAutoTick();
/*     */     }
/*     */     else {
/*     */       
/* 290 */       super.actionPerformed(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void attemptGridStrokeSelection() {
/* 298 */     StrokeChooserPanel panel = new StrokeChooserPanel(this.gridStrokeSample, this.availableStrokeSamples);
/*     */     
/* 300 */     int result = JOptionPane.showConfirmDialog(this, panel, localizationResources
/* 301 */         .getString("Stroke_Selection"), 2, -1);
/*     */ 
/*     */     
/* 304 */     if (result == 0) {
/* 305 */       this.gridStrokeSample.setStroke(panel.getSelectedStroke());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void attemptGridPaintSelection() {
/* 314 */     Color c = JColorChooser.showDialog(this, localizationResources.getString("Grid_Color"), Color.blue);
/*     */     
/* 316 */     if (c != null) {
/* 317 */       this.gridPaintSample.setPaint(c);
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
/*     */   public void focusGained(FocusEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent event) {
/* 338 */     if (event.getSource() == this.minimumRangeValue) {
/* 339 */       validateMinimum();
/*     */     }
/* 341 */     else if (event.getSource() == this.maximumRangeValue) {
/* 342 */       validateMaximum();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleAutoRange() {
/* 350 */     this.autoRange = this.autoRangeCheckBox.isSelected();
/* 351 */     if (this.autoRange) {
/* 352 */       this.minimumRangeValue.setText(Double.toString(this.minimumValue));
/* 353 */       this.minimumRangeValue.setEnabled(false);
/* 354 */       this.maximumRangeValue.setText(Double.toString(this.maximumValue));
/* 355 */       this.maximumRangeValue.setEnabled(false);
/*     */     } else {
/*     */       
/* 358 */       this.minimumRangeValue.setEnabled(true);
/* 359 */       this.maximumRangeValue.setEnabled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void toggleAutoTick() {
/* 364 */     this.autoTickUnitSelection = this.autoTickUnitSelectionCheckBox.isSelected();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateMinimum() {
/*     */     double newMin;
/*     */     try {
/* 373 */       newMin = Double.parseDouble(this.minimumRangeValue.getText());
/* 374 */       if (newMin >= this.maximumValue) {
/* 375 */         newMin = this.minimumValue;
/*     */       }
/*     */     }
/* 378 */     catch (NumberFormatException e) {
/* 379 */       newMin = this.minimumValue;
/*     */     } 
/*     */     
/* 382 */     this.minimumValue = newMin;
/* 383 */     this.minimumRangeValue.setText(Double.toString(this.minimumValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateMaximum() {
/*     */     double newMax;
/*     */     try {
/* 392 */       newMax = Double.parseDouble(this.maximumRangeValue.getText());
/* 393 */       if (newMax <= this.minimumValue) {
/* 394 */         newMax = this.maximumValue;
/*     */       }
/*     */     }
/* 397 */     catch (NumberFormatException e) {
/* 398 */       newMax = this.maximumValue;
/*     */     } 
/*     */     
/* 401 */     this.maximumValue = newMax;
/* 402 */     this.maximumRangeValue.setText(Double.toString(this.maximumValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAxisProperties(Axis axis) {
/* 413 */     super.setAxisProperties(axis);
/* 414 */     ValueAxis valueAxis = (ValueAxis)axis;
/* 415 */     valueAxis.setAutoRange(this.autoRange);
/* 416 */     if (!this.autoRange) {
/* 417 */       valueAxis.setRange(this.minimumValue, this.maximumValue);
/*     */     }
/* 419 */     valueAxis.setAutoTickUnitSelection(this.autoTickUnitSelection);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultValueAxisEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */