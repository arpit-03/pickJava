/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardCategoryToolTipGenerator
/*     */   extends AbstractCategoryItemLabelGenerator
/*     */   implements CategoryToolTipGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6768806592218710764L;
/*     */   public static final String DEFAULT_TOOL_TIP_FORMAT_STRING = "({0}, {1}) = {2}";
/*     */   
/*     */   public StandardCategoryToolTipGenerator() {
/*  70 */     super("({0}, {1}) = {2}", NumberFormat.getInstance());
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
/*     */   public StandardCategoryToolTipGenerator(String labelFormat, NumberFormat formatter) {
/*  82 */     super(labelFormat, formatter);
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
/*     */   public StandardCategoryToolTipGenerator(String labelFormat, DateFormat formatter) {
/*  94 */     super(labelFormat, formatter);
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
/*     */   public String generateToolTip(CategoryDataset dataset, int row, int column) {
/* 111 */     return generateLabelString(dataset, row, column);
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
/*     */   public boolean equals(Object obj) {
/* 123 */     if (obj == this) {
/* 124 */       return true;
/*     */     }
/* 126 */     if (!(obj instanceof StandardCategoryToolTipGenerator)) {
/* 127 */       return false;
/*     */     }
/* 129 */     return super.equals(obj);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/StandardCategoryToolTipGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */