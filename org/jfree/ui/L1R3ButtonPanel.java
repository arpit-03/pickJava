/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class L1R3ButtonPanel
/*     */   extends JPanel
/*     */ {
/*     */   private JButton left;
/*     */   private JButton right1;
/*     */   private JButton right2;
/*     */   private JButton right3;
/*     */   
/*     */   public L1R3ButtonPanel(String label1, String label2, String label3, String label4) {
/*  81 */     setLayout(new BorderLayout());
/*     */ 
/*     */     
/*  84 */     JPanel panel = new JPanel(new BorderLayout());
/*  85 */     JPanel panel2 = new JPanel(new BorderLayout());
/*  86 */     this.left = new JButton(label1);
/*  87 */     this.right1 = new JButton(label2);
/*  88 */     this.right2 = new JButton(label3);
/*  89 */     this.right3 = new JButton(label4);
/*     */ 
/*     */     
/*  92 */     panel.add(this.left, "West");
/*  93 */     panel2.add(this.right1, "East");
/*  94 */     panel.add(panel2, "Center");
/*  95 */     panel.add(this.right2, "East");
/*  96 */     add(panel, "Center");
/*  97 */     add(this.right3, "East");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getLeftButton() {
/* 107 */     return this.left;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getRightButton1() {
/* 116 */     return this.right1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getRightButton2() {
/* 125 */     return this.right2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JButton getRightButton3() {
/* 134 */     return this.right3;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/L1R3ButtonPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */