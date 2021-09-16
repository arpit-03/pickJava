/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.chart.plot.Crosshair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardCrosshairLabelGenerator
/*     */   implements CrosshairLabelGenerator, Serializable
/*     */ {
/*     */   private String labelTemplate;
/*     */   private NumberFormat numberFormat;
/*     */   
/*     */   public StandardCrosshairLabelGenerator() {
/*  66 */     this("{0}", NumberFormat.getNumberInstance());
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
/*     */   public StandardCrosshairLabelGenerator(String labelTemplate, NumberFormat numberFormat) {
/*  80 */     if (labelTemplate == null) {
/*  81 */       throw new IllegalArgumentException("Null 'labelTemplate' argument.");
/*     */     }
/*     */     
/*  84 */     if (numberFormat == null) {
/*  85 */       throw new IllegalArgumentException("Null 'numberFormat' argument.");
/*     */     }
/*     */     
/*  88 */     this.labelTemplate = labelTemplate;
/*  89 */     this.numberFormat = numberFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLabelTemplate() {
/*  98 */     return this.labelTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getNumberFormat() {
/* 107 */     return this.numberFormat;
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
/*     */   public String generateLabel(Crosshair crosshair) {
/* 119 */     Object[] v = { this.numberFormat.format(crosshair
/* 120 */           .getValue()) };
/* 121 */     String result = MessageFormat.format(this.labelTemplate, v);
/* 122 */     return result;
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
/* 134 */     if (obj == this) {
/* 135 */       return true;
/*     */     }
/* 137 */     if (!(obj instanceof StandardCrosshairLabelGenerator)) {
/* 138 */       return false;
/*     */     }
/* 140 */     StandardCrosshairLabelGenerator that = (StandardCrosshairLabelGenerator)obj;
/*     */     
/* 142 */     if (!this.labelTemplate.equals(that.labelTemplate)) {
/* 143 */       return false;
/*     */     }
/* 145 */     if (!this.numberFormat.equals(that.numberFormat)) {
/* 146 */       return false;
/*     */     }
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 158 */     return this.labelTemplate.hashCode();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/StandardCrosshairLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */