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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogFormat
/*     */   extends NumberFormat
/*     */ {
/*     */   private double base;
/*     */   private double baseLog;
/*     */   private String baseLabel;
/*     */   private String powerLabel;
/*     */   private boolean showBase;
/*  81 */   private NumberFormat formatter = new DecimalFormat("0.0#");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LogFormat() {
/*  89 */     this(10.0D, "10", true);
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
/*     */   public LogFormat(double base, String baseLabel, boolean showBase) {
/* 101 */     this(base, baseLabel, "^", showBase);
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
/*     */   public LogFormat(double base, String baseLabel, String powerLabel, boolean showBase) {
/* 117 */     ParamChecks.nullNotPermitted(baseLabel, "baseLabel");
/* 118 */     ParamChecks.nullNotPermitted(powerLabel, "powerLabel");
/* 119 */     this.base = base;
/* 120 */     this.baseLog = Math.log(this.base);
/* 121 */     this.baseLabel = baseLabel;
/* 122 */     this.showBase = showBase;
/* 123 */     this.powerLabel = powerLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NumberFormat getExponentFormat() {
/* 134 */     return (NumberFormat)this.formatter.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExponentFormat(NumberFormat format) {
/* 145 */     ParamChecks.nullNotPermitted(format, "format");
/* 146 */     this.formatter = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double calculateLog(double value) {
/* 157 */     return Math.log(value) / this.baseLog;
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
/*     */   public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
/* 172 */     StringBuffer result = new StringBuffer();
/* 173 */     if (this.showBase) {
/* 174 */       result.append(this.baseLabel);
/* 175 */       result.append(this.powerLabel);
/*     */     } 
/* 177 */     result.append(this.formatter.format(calculateLog(number)));
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
/*     */   public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
/* 194 */     StringBuffer result = new StringBuffer();
/* 195 */     if (this.showBase) {
/* 196 */       result.append(this.baseLabel);
/* 197 */       result.append(this.powerLabel);
/*     */     } 
/* 199 */     result.append(this.formatter.format(calculateLog(number)));
/* 200 */     return result;
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
/* 214 */     return null;
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
/* 226 */     if (obj == this) {
/* 227 */       return true;
/*     */     }
/* 229 */     if (!(obj instanceof LogFormat)) {
/* 230 */       return false;
/*     */     }
/* 232 */     LogFormat that = (LogFormat)obj;
/* 233 */     if (this.base != that.base) {
/* 234 */       return false;
/*     */     }
/* 236 */     if (!this.baseLabel.equals(that.baseLabel)) {
/* 237 */       return false;
/*     */     }
/* 239 */     if (this.baseLog != that.baseLog) {
/* 240 */       return false;
/*     */     }
/* 242 */     if (this.showBase != that.showBase) {
/* 243 */       return false;
/*     */     }
/* 245 */     if (!this.formatter.equals(that.formatter)) {
/* 246 */       return false;
/*     */     }
/* 248 */     return super.equals(obj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 258 */     LogFormat clone = (LogFormat)super.clone();
/* 259 */     clone.formatter = (NumberFormat)this.formatter.clone();
/* 260 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/LogFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */