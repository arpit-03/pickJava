/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.text.NumberFormat;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NumberCellRenderer
/*     */   extends DefaultTableCellRenderer
/*     */ {
/*     */   public NumberCellRenderer() {
/*  65 */     setHorizontalAlignment(4);
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
/*     */   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
/*  86 */     setFont(null);
/*  87 */     NumberFormat nf = NumberFormat.getNumberInstance();
/*  88 */     if (value != null) {
/*  89 */       setText(nf.format(value));
/*     */     } else {
/*     */       
/*  92 */       setText("");
/*     */     } 
/*  94 */     if (isSelected) {
/*  95 */       setBackground(table.getSelectionBackground());
/*     */     } else {
/*     */       
/*  98 */       setBackground((Color)null);
/*     */     } 
/* 100 */     return this;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/NumberCellRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */