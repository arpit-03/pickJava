/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.GridLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class L1R2ButtonPanel
/*     */   extends JPanel
/*     */ {
/*     */   private JButton left;
/*     */   private JButton right1;
/*     */   private JButton right2;
/*     */   
/*     */   public L1R2ButtonPanel(String label1, String label2, String label3) {
/*  78 */     setLayout(new BorderLayout());
/*     */ 
/*     */     
/*  81 */     this.left = new JButton(label1);
/*     */     
/*  83 */     JPanel rightButtonPanel = new JPanel(new GridLayout(1, 2));
/*  84 */     this.right1 = new JButton(label2);
/*  85 */     this.right2 = new JButton(label3);
/*  86 */     rightButtonPanel.add(this.right1);
/*  87 */     rightButtonPanel.add(this.right2);
/*     */ 
/*     */     
/*  90 */     add(this.left, "West");
/*  91 */     add(rightButtonPanel, "East");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getLeftButton() {
/* 101 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getRightButton1() {
/* 110 */     return this.right1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getRightButton2() {
/* 119 */     return this.right2;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/L1R2ButtonPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */