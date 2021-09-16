/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.DataUtilities;
/*     */ import org.jfree.data.Values2D;
/*     */ import org.jfree.data.category.CategoryDataset;
/*     */ import org.jfree.util.ObjectUtilities;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCategoryItemLabelGenerator
/*     */   implements PublicCloneable, Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -7108591260223293197L;
/*     */   private String labelFormat;
/*     */   private String nullValueString;
/*     */   private NumberFormat numberFormat;
/*     */   private DateFormat dateFormat;
/*     */   private NumberFormat percentFormat;
/*     */   
/*     */   protected AbstractCategoryItemLabelGenerator(String labelFormat, NumberFormat formatter) {
/* 109 */     this(labelFormat, formatter, NumberFormat.getPercentInstance());
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
/*     */   protected AbstractCategoryItemLabelGenerator(String labelFormat, NumberFormat formatter, NumberFormat percentFormatter) {
/* 125 */     ParamChecks.nullNotPermitted(labelFormat, "labelFormat");
/* 126 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 127 */     ParamChecks.nullNotPermitted(percentFormatter, "percentFormatter");
/* 128 */     this.labelFormat = labelFormat;
/* 129 */     this.numberFormat = formatter;
/* 130 */     this.percentFormat = percentFormatter;
/* 131 */     this.dateFormat = null;
/* 132 */     this.nullValueString = "-";
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
/*     */   protected AbstractCategoryItemLabelGenerator(String labelFormat, DateFormat formatter) {
/* 144 */     ParamChecks.nullNotPermitted(labelFormat, "labelFormat");
/* 145 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 146 */     this.labelFormat = labelFormat;
/* 147 */     this.numberFormat = null;
/* 148 */     this.percentFormat = NumberFormat.getPercentInstance();
/* 149 */     this.dateFormat = formatter;
/* 150 */     this.nullValueString = "-";
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
/*     */   public String generateRowLabel(CategoryDataset dataset, int row) {
/* 162 */     return dataset.getRowKey(row).toString();
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
/*     */   public String generateColumnLabel(CategoryDataset dataset, int column) {
/* 174 */     return dataset.getColumnKey(column).toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabelFormat() {
/* 183 */     return this.labelFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getNumberFormat() {
/* 192 */     return this.numberFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormat getDateFormat() {
/* 201 */     return this.dateFormat;
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
/*     */   protected String generateLabelString(CategoryDataset dataset, int row, int column) {
/* 215 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*     */     
/* 217 */     Object[] items = createItemArray(dataset, row, column);
/* 218 */     String result = MessageFormat.format(this.labelFormat, items);
/* 219 */     return result;
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
/* 235 */     Object[] result = new Object[4];
/* 236 */     result[0] = dataset.getRowKey(row).toString();
/* 237 */     result[1] = dataset.getColumnKey(column).toString();
/* 238 */     Number value = dataset.getValue(row, column);
/* 239 */     if (value != null) {
/* 240 */       if (this.numberFormat != null) {
/* 241 */         result[2] = this.numberFormat.format(value);
/*     */       }
/* 243 */       else if (this.dateFormat != null) {
/* 244 */         result[2] = this.dateFormat.format(value);
/*     */       } 
/*     */     } else {
/*     */       
/* 248 */       result[2] = this.nullValueString;
/*     */     } 
/* 250 */     if (value != null) {
/* 251 */       double total = DataUtilities.calculateColumnTotal((Values2D)dataset, column);
/* 252 */       double percent = value.doubleValue() / total;
/* 253 */       result[3] = this.percentFormat.format(percent);
/*     */     } 
/*     */     
/* 256 */     return result;
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
/* 268 */     if (obj == this) {
/* 269 */       return true;
/*     */     }
/* 271 */     if (!(obj instanceof AbstractCategoryItemLabelGenerator)) {
/* 272 */       return false;
/*     */     }
/*     */     
/* 275 */     AbstractCategoryItemLabelGenerator that = (AbstractCategoryItemLabelGenerator)obj;
/*     */     
/* 277 */     if (!this.labelFormat.equals(that.labelFormat)) {
/* 278 */       return false;
/*     */     }
/* 280 */     if (!ObjectUtilities.equal(this.dateFormat, that.dateFormat)) {
/* 281 */       return false;
/*     */     }
/* 283 */     if (!ObjectUtilities.equal(this.numberFormat, that.numberFormat)) {
/* 284 */       return false;
/*     */     }
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 296 */     int result = 127;
/* 297 */     result = HashUtilities.hashCode(result, this.labelFormat);
/* 298 */     result = HashUtilities.hashCode(result, this.nullValueString);
/* 299 */     result = HashUtilities.hashCode(result, this.dateFormat);
/* 300 */     result = HashUtilities.hashCode(result, this.numberFormat);
/* 301 */     result = HashUtilities.hashCode(result, this.percentFormat);
/* 302 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 315 */     AbstractCategoryItemLabelGenerator clone = (AbstractCategoryItemLabelGenerator)super.clone();
/* 316 */     if (this.numberFormat != null) {
/* 317 */       clone.numberFormat = (NumberFormat)this.numberFormat.clone();
/*     */     }
/* 319 */     if (this.dateFormat != null) {
/* 320 */       clone.dateFormat = (DateFormat)this.dateFormat.clone();
/*     */     }
/* 322 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/AbstractCategoryItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */