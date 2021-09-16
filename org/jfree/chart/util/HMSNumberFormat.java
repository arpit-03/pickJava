/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HMSNumberFormat
/*     */   extends NumberFormat
/*     */ {
/*  58 */   private NumberFormat formatter = new DecimalFormat("00");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
/*  80 */     return format((long)number, toAppendTo, pos);
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
/*     */   public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
/*  95 */     StringBuffer sb = new StringBuffer();
/*  96 */     long hours = number / 3600L;
/*  97 */     sb.append(this.formatter.format(hours)).append(":");
/*  98 */     long remaining = number - hours * 3600L;
/*  99 */     long minutes = remaining / 60L;
/* 100 */     sb.append(this.formatter.format(minutes)).append(":");
/* 101 */     long seconds = remaining - minutes * 60L;
/* 102 */     sb.append(this.formatter.format(seconds));
/* 103 */     return sb;
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
/*     */   public Number parse(String source, ParsePosition parsePosition) {
/* 117 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/HMSNumberFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */