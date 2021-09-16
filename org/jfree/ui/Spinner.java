/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Spinner
/*     */   extends JPanel
/*     */   implements MouseListener
/*     */ {
/*     */   private int value;
/*     */   private JTextField textField;
/*     */   private JPanel buttonPanel;
/*     */   private ArrowPanel upButton;
/*     */   private ArrowPanel downButton;
/*     */   
/*     */   public Spinner(int value) {
/*  81 */     super(new BorderLayout());
/*  82 */     this.value = value;
/*  83 */     this.textField = new JTextField(Integer.toString(this.value));
/*  84 */     this.textField.setHorizontalAlignment(4);
/*  85 */     add(this.textField);
/*  86 */     this.buttonPanel = new JPanel(new GridLayout(2, 1, 0, 1));
/*  87 */     this.upButton = new ArrowPanel(0);
/*  88 */     this.upButton.addMouseListener(this);
/*  89 */     this.downButton = new ArrowPanel(1);
/*  90 */     this.downButton.addMouseListener(this);
/*  91 */     this.buttonPanel.add(this.upButton);
/*  92 */     this.buttonPanel.add(this.downButton);
/*  93 */     add(this.buttonPanel, "East");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValue() {
/* 102 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent e) {
/* 111 */     if (e.getSource() == this.upButton) {
/* 112 */       this.value++;
/* 113 */       this.textField.setText(Integer.toString(this.value));
/* 114 */       firePropertyChange("value", this.value - 1, this.value);
/*     */     }
/* 116 */     else if (e.getSource() == this.downButton) {
/* 117 */       this.value--;
/* 118 */       this.textField.setText(Integer.toString(this.value));
/* 119 */       firePropertyChange("value", this.value + 1, this.value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mouseEntered(MouseEvent e) {}
/*     */   
/*     */   public void mouseExited(MouseEvent e) {}
/*     */   
/*     */   public void mousePressed(MouseEvent e) {}
/*     */   
/*     */   public void mouseReleased(MouseEvent e) {}
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/Spinner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */