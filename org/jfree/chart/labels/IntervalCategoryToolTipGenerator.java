/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.data.category.IntervalCategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntervalCategoryToolTipGenerator
/*     */   extends StandardCategoryToolTipGenerator
/*     */ {
/*     */   private static final long serialVersionUID = -3853824986520333437L;
/*     */   public static final String DEFAULT_TOOL_TIP_FORMAT_STRING = "({0}, {1}) = {3} - {4}";
/*     */   
/*     */   public IntervalCategoryToolTipGenerator() {
/*  68 */     super("({0}, {1}) = {3} - {4}", NumberFormat.getInstance());
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
/*     */   public IntervalCategoryToolTipGenerator(String labelFormat, NumberFormat formatter) {
/*  80 */     super(labelFormat, formatter);
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
/*     */   public IntervalCategoryToolTipGenerator(String labelFormat, DateFormat formatter) {
/*  92 */     super(labelFormat, formatter);
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
/* 108 */     Object[] result = new Object[5];
/* 109 */     result[0] = dataset.getRowKey(row).toString();
/* 110 */     result[1] = dataset.getColumnKey(column).toString();
/* 111 */     Number value = dataset.getValue(row, column);
/* 112 */     if (getNumberFormat() != null) {
/* 113 */       result[2] = getNumberFormat().format(value);
/*     */     }
/* 115 */     else if (getDateFormat() != null) {
/* 116 */       result[2] = getDateFormat().format(value);
/*     */     } 
/*     */     
/* 119 */     if (dataset instanceof IntervalCategoryDataset) {
/* 120 */       IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
/* 121 */       Number start = icd.getStartValue(row, column);
/* 122 */       Number end = icd.getEndValue(row, column);
/* 123 */       if (getNumberFormat() != null) {
/* 124 */         result[3] = getNumberFormat().format(start);
/* 125 */         result[4] = getNumberFormat().format(end);
/*     */       }
/* 127 */       else if (getDateFormat() != null) {
/* 128 */         result[3] = getDateFormat().format(start);
/* 129 */         result[4] = getDateFormat().format(end);
/*     */       } 
/*     */     } 
/* 132 */     return result;
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
/*     */   public boolean equals(Object obj) {
/* 145 */     if (obj == this) {
/* 146 */       return true;
/*     */     }
/* 148 */     if (!(obj instanceof IntervalCategoryToolTipGenerator)) {
/* 149 */       return false;
/*     */     }
/*     */     
/* 152 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/IntervalCategoryToolTipGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */