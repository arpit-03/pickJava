/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.axis.LogAxis;
/*     */ import org.jfree.chart.axis.NumberTickUnit;
/*     */ import org.jfree.chart.axis.ValueAxis;
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
/*     */ public class DefaultLogAxisEditor
/*     */   extends DefaultValueAxisEditor
/*     */ {
/*     */   private double manualTickUnitValue;
/*     */   private JTextField manualTickUnit;
/*     */   
/*     */   public DefaultLogAxisEditor(LogAxis axis) {
/*  69 */     super((ValueAxis)axis);
/*  70 */     this.manualTickUnitValue = axis.getTickUnit().getSize();
/*  71 */     this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected JPanel createTickUnitPanel() {
/*  81 */     JPanel tickUnitPanel = super.createTickUnitPanel();
/*     */     
/*  83 */     tickUnitPanel.add(new JLabel(localizationResources.getString("Manual_TickUnit_value")));
/*     */     
/*  85 */     this.manualTickUnit = new JTextField(Double.toString(this.manualTickUnitValue));
/*     */     
/*  87 */     this.manualTickUnit.setEnabled(!isAutoTickUnitSelection());
/*  88 */     this.manualTickUnit.setActionCommand("TickUnitValue");
/*  89 */     this.manualTickUnit.addActionListener(this);
/*  90 */     this.manualTickUnit.addFocusListener(this);
/*  91 */     tickUnitPanel.add(this.manualTickUnit);
/*  92 */     tickUnitPanel.add(new JPanel());
/*     */     
/*  94 */     return tickUnitPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 104 */     String command = event.getActionCommand();
/* 105 */     if (command.equals("TickUnitValue")) {
/* 106 */       validateTickUnit();
/*     */     }
/*     */     else {
/*     */       
/* 110 */       super.actionPerformed(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent event) {
/* 116 */     super.focusLost(event);
/* 117 */     if (event.getSource() == this.manualTickUnit) {
/* 118 */       validateTickUnit();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleAutoTick() {
/* 127 */     super.toggleAutoTick();
/* 128 */     if (isAutoTickUnitSelection()) {
/* 129 */       this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
/* 130 */       this.manualTickUnit.setEnabled(false);
/*     */     } else {
/*     */       
/* 133 */       this.manualTickUnit.setEnabled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validateTickUnit() {
/*     */     double newTickUnit;
/*     */     try {
/* 143 */       newTickUnit = Double.parseDouble(this.manualTickUnit.getText());
/*     */     }
/* 145 */     catch (NumberFormatException e) {
/* 146 */       newTickUnit = this.manualTickUnitValue;
/*     */     } 
/*     */     
/* 149 */     if (newTickUnit > 0.0D) {
/* 150 */       this.manualTickUnitValue = newTickUnit;
/*     */     }
/* 152 */     this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
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
/* 163 */     super.setAxisProperties(axis);
/* 164 */     LogAxis logAxis = (LogAxis)axis;
/* 165 */     if (!isAutoTickUnitSelection())
/* 166 */       logAxis.setTickUnit(new NumberTickUnit(this.manualTickUnitValue)); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultLogAxisEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */