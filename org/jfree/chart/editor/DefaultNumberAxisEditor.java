/*     */ package org.jfree.chart.editor;
/*     */ 
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.FocusEvent;
/*     */ import java.awt.event.FocusListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import org.jfree.chart.axis.Axis;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.NumberTickUnit;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.layout.LCBLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultNumberAxisEditor
/*     */   extends DefaultValueAxisEditor
/*     */   implements FocusListener
/*     */ {
/*     */   private double manualTickUnitValue;
/*     */   private JTextField manualTickUnit;
/*     */   
/*     */   public DefaultNumberAxisEditor(NumberAxis axis) {
/*  79 */     super((ValueAxis)axis);
/*     */     
/*  81 */     this.manualTickUnitValue = axis.getTickUnit().getSize();
/*  82 */     validateTickUnit();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected JPanel createTickUnitPanel() {
/*  88 */     JPanel tickUnitPanel = new JPanel((LayoutManager)new LCBLayout(3));
/*  89 */     tickUnitPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
/*     */     
/*  91 */     tickUnitPanel.add(new JPanel());
/*     */ 
/*     */     
/*  94 */     JCheckBox autoTickUnitSelectionCheckBox = new JCheckBox(localizationResources.getString("Auto-TickUnit_Selection"), isAutoTickUnitSelection());
/*  95 */     autoTickUnitSelectionCheckBox.setActionCommand("AutoTickOnOff");
/*  96 */     autoTickUnitSelectionCheckBox.addActionListener(this);
/*  97 */     setAutoTickUnitSelectionCheckBox(autoTickUnitSelectionCheckBox);
/*  98 */     tickUnitPanel.add(getAutoTickUnitSelectionCheckBox());
/*  99 */     tickUnitPanel.add(new JPanel());
/*     */     
/* 101 */     tickUnitPanel.add(new JLabel(localizationResources.getString("Manual_TickUnit_value")));
/*     */     
/* 103 */     this.manualTickUnit = new JTextField(Double.toString(this.manualTickUnitValue));
/*     */     
/* 105 */     this.manualTickUnit.setEnabled(!isAutoTickUnitSelection());
/* 106 */     this.manualTickUnit.setActionCommand("TickUnitValue");
/* 107 */     this.manualTickUnit.addActionListener(this);
/* 108 */     this.manualTickUnit.addFocusListener(this);
/* 109 */     tickUnitPanel.add(this.manualTickUnit);
/* 110 */     tickUnitPanel.add(new JPanel());
/*     */     
/* 112 */     return tickUnitPanel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 121 */     String command = event.getActionCommand();
/* 122 */     if (command.equals("TickUnitValue")) {
/* 123 */       validateTickUnit();
/*     */     }
/*     */     else {
/*     */       
/* 127 */       super.actionPerformed(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void focusLost(FocusEvent event) {
/* 133 */     super.focusLost(event);
/* 134 */     if (event.getSource() == this.manualTickUnit) {
/* 135 */       validateTickUnit();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggleAutoTick() {
/* 141 */     super.toggleAutoTick();
/* 142 */     if (isAutoTickUnitSelection()) {
/* 143 */       this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
/* 144 */       this.manualTickUnit.setEnabled(false);
/*     */     } else {
/*     */       
/* 147 */       this.manualTickUnit.setEnabled(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void validateTickUnit() {
/*     */     double newTickUnit;
/*     */     try {
/* 154 */       newTickUnit = Double.parseDouble(this.manualTickUnit.getText());
/*     */     }
/* 156 */     catch (NumberFormatException e) {
/* 157 */       newTickUnit = this.manualTickUnitValue;
/*     */     } 
/*     */     
/* 160 */     if (newTickUnit > 0.0D) {
/* 161 */       this.manualTickUnitValue = newTickUnit;
/*     */     }
/* 163 */     this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
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
/* 174 */     super.setAxisProperties(axis);
/* 175 */     NumberAxis numberAxis = (NumberAxis)axis;
/* 176 */     if (!isAutoTickUnitSelection())
/* 177 */       numberAxis.setTickUnit(new NumberTickUnit(this.manualTickUnitValue)); 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/editor/DefaultNumberAxisEditor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */