/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.IntervalCategoryDataset;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntervalCategoryItemLabelGenerator
/*     */   extends StandardCategoryItemLabelGenerator
/*     */   implements CategoryItemLabelGenerator, PublicCloneable, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5056909225610630529L;
/*     */   public static final String DEFAULT_LABEL_FORMAT_STRING = "({0}, {1}) = {3} - {4}";
/*     */   
/*     */   public IntervalCategoryItemLabelGenerator() {
/*  71 */     super("({0}, {1}) = {3} - {4}", NumberFormat.getInstance());
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
/*     */   public IntervalCategoryItemLabelGenerator(String labelFormat, NumberFormat formatter) {
/*  83 */     super(labelFormat, formatter);
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
/*     */   public IntervalCategoryItemLabelGenerator(String labelFormat, DateFormat formatter) {
/*  95 */     super(labelFormat, formatter);
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
/*     */   protected Object[] createItemArray(CategoryDataset dataset, int row, int column) {
/* 111 */     Object[] result = new Object[5];
/* 112 */     result[0] = dataset.getRowKey(row).toString();
/* 113 */     result[1] = dataset.getColumnKey(column).toString();
/* 114 */     Number value = dataset.getValue(row, column);
/* 115 */     if (getNumberFormat() != null) {
/* 116 */       result[2] = getNumberFormat().format(value);
/*     */     }
/* 118 */     else if (getDateFormat() != null) {
/* 119 */       result[2] = getDateFormat().format(value);
/*     */     } 
/*     */     
/* 122 */     if (dataset instanceof IntervalCategoryDataset) {
/* 123 */       IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
/* 124 */       Number start = icd.getStartValue(row, column);
/* 125 */       Number end = icd.getEndValue(row, column);
/* 126 */       if (getNumberFormat() != null) {
/* 127 */         result[3] = getNumberFormat().format(start);
/* 128 */         result[4] = getNumberFormat().format(end);
/*     */       }
/* 130 */       else if (getDateFormat() != null) {
/* 131 */         result[3] = getDateFormat().format(start);
/* 132 */         result[4] = getDateFormat().format(end);
/*     */       } 
/*     */     } 
/* 135 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/IntervalCategoryItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */