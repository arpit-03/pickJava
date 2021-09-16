/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.DatasetUtilities;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AbstractPieItemLabelGenerator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7347703325267846275L;
/*     */   private String labelFormat;
/*     */   private NumberFormat numberFormat;
/*     */   private NumberFormat percentFormat;
/*     */   
/*     */   protected AbstractPieItemLabelGenerator(String labelFormat, NumberFormat numberFormat, NumberFormat percentFormat) {
/*  86 */     ParamChecks.nullNotPermitted(labelFormat, "labelFormat");
/*  87 */     ParamChecks.nullNotPermitted(numberFormat, "numberFormat");
/*  88 */     ParamChecks.nullNotPermitted(percentFormat, "percentFormat");
/*  89 */     this.labelFormat = labelFormat;
/*  90 */     this.numberFormat = numberFormat;
/*  91 */     this.percentFormat = percentFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabelFormat() {
/* 100 */     return this.labelFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getNumberFormat() {
/* 109 */     return this.numberFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getPercentFormat() {
/* 118 */     return this.percentFormat;
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
/*     */   protected Object[] createItemArray(PieDataset dataset, Comparable key) {
/* 138 */     Object[] result = new Object[4];
/* 139 */     double total = DatasetUtilities.calculatePieDatasetTotal(dataset);
/* 140 */     result[0] = key.toString();
/* 141 */     Number value = dataset.getValue(key);
/* 142 */     if (value != null) {
/* 143 */       result[1] = this.numberFormat.format(value);
/*     */     } else {
/*     */       
/* 146 */       result[1] = "null";
/*     */     } 
/* 148 */     double percent = 0.0D;
/* 149 */     if (value != null) {
/* 150 */       double v = value.doubleValue();
/* 151 */       if (v > 0.0D) {
/* 152 */         percent = v / total;
/*     */       }
/*     */     } 
/* 155 */     result[2] = this.percentFormat.format(percent);
/* 156 */     result[3] = this.numberFormat.format(total);
/* 157 */     return result;
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
/*     */   protected String generateSectionLabel(PieDataset dataset, Comparable key) {
/* 169 */     String result = null;
/* 170 */     if (dataset != null) {
/* 171 */       Object[] items = createItemArray(dataset, key);
/* 172 */       result = MessageFormat.format(this.labelFormat, items);
/*     */     } 
/* 174 */     return result;
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
/* 186 */     if (obj == this) {
/* 187 */       return true;
/*     */     }
/* 189 */     if (!(obj instanceof AbstractPieItemLabelGenerator)) {
/* 190 */       return false;
/*     */     }
/*     */     
/* 193 */     AbstractPieItemLabelGenerator that = (AbstractPieItemLabelGenerator)obj;
/*     */     
/* 195 */     if (!this.labelFormat.equals(that.labelFormat)) {
/* 196 */       return false;
/*     */     }
/* 198 */     if (!this.numberFormat.equals(that.numberFormat)) {
/* 199 */       return false;
/*     */     }
/* 201 */     if (!this.percentFormat.equals(that.percentFormat)) {
/* 202 */       return false;
/*     */     }
/* 204 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 215 */     int result = 127;
/* 216 */     result = HashUtilities.hashCode(result, this.labelFormat);
/* 217 */     result = HashUtilities.hashCode(result, this.numberFormat);
/* 218 */     result = HashUtilities.hashCode(result, this.percentFormat);
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 232 */     AbstractPieItemLabelGenerator clone = (AbstractPieItemLabelGenerator)super.clone();
/* 233 */     if (this.numberFormat != null) {
/* 234 */       clone.numberFormat = (NumberFormat)this.numberFormat.clone();
/*     */     }
/* 236 */     if (this.percentFormat != null) {
/* 237 */       clone.percentFormat = (NumberFormat)this.percentFormat.clone();
/*     */     }
/* 239 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/AbstractPieItemLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */