/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormat;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.NumberFormat;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.data.xy.XYZDataset;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardXYZToolTipGenerator
/*     */   extends StandardXYToolTipGenerator
/*     */   implements XYZToolTipGenerator, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -2961577421889473503L;
/*     */   public static final String DEFAULT_TOOL_TIP_FORMAT = "{0}: ({1}, {2}, {3})";
/*     */   private NumberFormat zFormat;
/*     */   private DateFormat zDateFormat;
/*     */   
/*     */   public StandardXYZToolTipGenerator() {
/*  85 */     this("{0}: ({1}, {2}, {3})", 
/*     */         
/*  87 */         NumberFormat.getNumberInstance(), 
/*  88 */         NumberFormat.getNumberInstance(), 
/*  89 */         NumberFormat.getNumberInstance());
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
/*     */   public StandardXYZToolTipGenerator(String formatString, NumberFormat xFormat, NumberFormat yFormat, NumberFormat zFormat) {
/* 107 */     super(formatString, xFormat, yFormat);
/* 108 */     ParamChecks.nullNotPermitted(zFormat, "zFormat");
/* 109 */     this.zFormat = zFormat;
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
/*     */   public StandardXYZToolTipGenerator(String formatString, DateFormat xFormat, DateFormat yFormat, DateFormat zFormat) {
/* 125 */     super(formatString, xFormat, yFormat);
/* 126 */     ParamChecks.nullNotPermitted(zFormat, "zFormat");
/* 127 */     this.zDateFormat = zFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getZFormat() {
/* 138 */     return this.zFormat;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormat getZDateFormat() {
/* 147 */     return this.zDateFormat;
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
/*     */   public String generateToolTip(XYZDataset dataset, int series, int item) {
/* 161 */     return generateLabelString((XYDataset)dataset, series, item);
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
/*     */   public String generateLabelString(XYDataset dataset, int series, int item) {
/* 176 */     Object[] items = createItemArray((XYZDataset)dataset, series, item);
/* 177 */     String result = MessageFormat.format(getFormatString(), items);
/* 178 */     return result;
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
/*     */   protected Object[] createItemArray(XYZDataset dataset, int series, int item) {
/* 194 */     Object[] result = new Object[4];
/* 195 */     result[0] = dataset.getSeriesKey(series).toString();
/*     */     
/* 197 */     Number x = dataset.getX(series, item);
/* 198 */     DateFormat xf = getXDateFormat();
/* 199 */     if (xf != null) {
/* 200 */       result[1] = xf.format(x);
/*     */     } else {
/*     */       
/* 203 */       result[1] = getXFormat().format(x);
/*     */     } 
/*     */     
/* 206 */     Number y = dataset.getY(series, item);
/* 207 */     DateFormat yf = getYDateFormat();
/* 208 */     if (yf != null) {
/* 209 */       result[2] = yf.format(y);
/*     */     } else {
/*     */       
/* 212 */       result[2] = getYFormat().format(y);
/*     */     } 
/*     */     
/* 215 */     Number z = dataset.getZ(series, item);
/* 216 */     if (this.zDateFormat != null) {
/* 217 */       result[3] = this.zDateFormat.format(z);
/*     */     } else {
/*     */       
/* 220 */       result[3] = this.zFormat.format(z);
/*     */     } 
/*     */     
/* 223 */     return result;
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
/* 236 */     if (obj == this) {
/* 237 */       return true;
/*     */     }
/* 239 */     if (!(obj instanceof StandardXYZToolTipGenerator)) {
/* 240 */       return false;
/*     */     }
/* 242 */     if (!super.equals(obj)) {
/* 243 */       return false;
/*     */     }
/* 245 */     StandardXYZToolTipGenerator that = (StandardXYZToolTipGenerator)obj;
/* 246 */     if (!ObjectUtilities.equal(this.zFormat, that.zFormat)) {
/* 247 */       return false;
/*     */     }
/* 249 */     if (!ObjectUtilities.equal(this.zDateFormat, that.zDateFormat)) {
/* 250 */       return false;
/*     */     }
/* 252 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/StandardXYZToolTipGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */