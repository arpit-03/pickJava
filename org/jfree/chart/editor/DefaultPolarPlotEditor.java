/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.axis.NumberTickUnit;
/*     */ import org.jfree.chart.axis.TickUnit;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PolarPlot;
/*     */ import org.jfree.layout.LCBLayout;
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
/*     */ public class DefaultPolarPlotEditor
/*     */   extends DefaultPlotEditor
/*     */   implements FocusListener
/*     */ {
/*     */   private JTextField manualTickUnit;
/*     */   private JTextField angleOffset;
/*     */   private double manualTickUnitValue;
/*     */   private double angleOffsetValue;
/*     */   
/*     */   public DefaultPolarPlotEditor(PolarPlot plot) {
/*  85 */     super((Plot)plot);
/*  86 */     this.angleOffsetValue = plot.getAngleOffset();
/*  87 */     this.angleOffset.setText(Double.toString(this.angleOffsetValue));
/*  88 */     this.manualTickUnitValue = plot.getAngleTickUnit().getSize();
/*  89 */     this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
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
/*     */   protected JTabbedPane createPlotTabs(Plot plot) {
/* 101 */     JTabbedPane tabs = super.createPlotTabs(plot);
/*     */     
/* 103 */     tabs.insertTab(localizationResources.getString("General1"), null, 
/* 104 */         createPlotPanel(), null, 0);
/* 105 */     tabs.setSelectedIndex(0);
/* 106 */     return tabs;
/*     */   }
/*     */   
/*     */   private JPanel createPlotPanel() {
/* 110 */     JPanel plotPanel = new JPanel((LayoutManager)new LCBLayout(3));
/* 111 */     plotPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*     */     
/* 113 */     plotPanel.add(new JLabel(localizationResources.getString("AngleOffset")));
/*     */     
/* 115 */     this.angleOffset = new JTextField(Double.toString(this.angleOffsetValue));
/*     */     
/* 117 */     this.angleOffset.setActionCommand("AngleOffsetValue");
/* 118 */     this.angleOffset.addActionListener(this);
/* 119 */     this.angleOffset.addFocusListener(this);
/* 120 */     plotPanel.add(this.angleOffset);
/* 121 */     plotPanel.add(new JPanel());
/*     */     
/* 123 */     plotPanel.add(new JLabel(localizationResources.getString("Manual_TickUnit_value")));
/*     */     
/* 125 */     this.manualTickUnit = new JTextField(Double.toString(this.manualTickUnitValue));
/*     */     
/* 127 */     this.manualTickUnit.setActionCommand("TickUnitValue");
/* 128 */     this.manualTickUnit.addActionListener(this);
/* 129 */     this.manualTickUnit.addFocusListener(this);
/* 130 */     plotPanel.add(this.manualTickUnit);
/* 131 */     plotPanel.add(new JPanel());
/*     */     
/* 133 */     return plotPanel;
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
/* 153 */     if (event.getSource() == this.angleOffset) {
/* 154 */       validateAngleOffset();
/*     */     }
/* 156 */     else if (event.getSource() == this.manualTickUnit) {
/* 157 */       validateTickUnit();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 167 */     String command = event.getActionCommand();
/* 168 */     if (command.equals("AngleOffsetValue")) {
/* 169 */       validateAngleOffset();
/*     */     }
/* 171 */     else if (command.equals("TickUnitValue")) {
/* 172 */       validateTickUnit();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateAngleOffset() {
/*     */     double newOffset;
/*     */     try {
/* 182 */       newOffset = Double.parseDouble(this.angleOffset.getText());
/*     */     }
/* 184 */     catch (NumberFormatException e) {
/* 185 */       newOffset = this.angleOffsetValue;
/*     */     } 
/* 187 */     this.angleOffsetValue = newOffset;
/* 188 */     this.angleOffset.setText(Double.toString(this.angleOffsetValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateTickUnit() {
/*     */     double newTickUnit;
/*     */     try {
/* 197 */       newTickUnit = Double.parseDouble(this.manualTickUnit.getText());
/*     */     }
/* 199 */     catch (NumberFormatException e) {
/* 200 */       newTickUnit = this.manualTickUnitValue;
/*     */     } 
/*     */     
/* 203 */     if (newTickUnit > 0.0D && newTickUnit < 360.0D) {
/* 204 */       this.manualTickUnitValue = newTickUnit;
/*     */     }
/* 206 */     this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlotProperties(Plot plot) {
/* 211 */     super.updatePlotProperties(plot);
/* 212 */     PolarPlot pp = (PolarPlot)plot;
/* 213 */     pp.setAngleTickUnit((TickUnit)new NumberTickUnit(this.manualTickUnitValue));
/* 214 */     pp.setAngleOffset(this.angleOffsetValue);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultPolarPlotEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */