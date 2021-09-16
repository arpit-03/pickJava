/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import org.jfree.data.contour.ContourDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardContourToolTipGenerator
/*     */   implements ContourToolTipGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1881659351247502711L;
/*  70 */   private DecimalFormat valueForm = new DecimalFormat("##.###");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String generateToolTip(ContourDataset data, int item) {
/*     */     String xString;
/*  83 */     double x = data.getXValue(0, item);
/*  84 */     double y = data.getYValue(0, item);
/*  85 */     double z = data.getZValue(0, item);
/*     */ 
/*     */     
/*  88 */     if (data.isDateAxis(0)) {
/*  89 */       SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
/*     */       
/*  91 */       StringBuffer strbuf = new StringBuffer();
/*  92 */       strbuf = formatter.format(new Date((long)x), strbuf, new FieldPosition(0));
/*     */ 
/*     */       
/*  95 */       xString = strbuf.toString();
/*     */     } else {
/*     */       
/*  98 */       xString = this.valueForm.format(x);
/*     */     } 
/* 100 */     if (!Double.isNaN(z)) {
/* 101 */       return "X: " + xString + ", Y: " + this.valueForm
/* 102 */         .format(y) + ", Z: " + this.valueForm
/* 103 */         .format(z);
/*     */     }
/*     */     
/* 106 */     return "X: " + xString + ", Y: " + this.valueForm
/* 107 */       .format(y) + ", Z: no data";
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
/*     */   public boolean equals(Object obj) {
/* 122 */     if (obj == this) {
/* 123 */       return true;
/*     */     }
/* 125 */     if (!(obj instanceof StandardContourToolTipGenerator)) {
/* 126 */       return false;
/*     */     }
/* 128 */     StandardContourToolTipGenerator that = (StandardContourToolTipGenerator)obj;
/*     */     
/* 130 */     if (this.valueForm != null) {
/* 131 */       return this.valueForm.equals(that.valueForm);
/*     */     }
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/StandardContourToolTipGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */