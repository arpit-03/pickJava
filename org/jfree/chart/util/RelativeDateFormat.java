/*     */ package org.jfree.chart.util;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RelativeDateFormat
/*     */   extends DateFormat
/*     */ {
/*     */   private long baseMillis;
/*     */   private boolean showZeroDays;
/*     */   private boolean showZeroHours;
/*     */   private NumberFormat dayFormatter;
/*     */   private String positivePrefix;
/*     */   private String daySuffix;
/*     */   private NumberFormat hourFormatter;
/*     */   private String hourSuffix;
/*     */   private NumberFormat minuteFormatter;
/*     */   private String minuteSuffix;
/*     */   private NumberFormat secondFormatter;
/*     */   private String secondSuffix;
/*     */   private static final long MILLISECONDS_IN_ONE_HOUR = 3600000L;
/*     */   private static final long MILLISECONDS_IN_ONE_DAY = 86400000L;
/*     */   
/*     */   public RelativeDateFormat() {
/* 150 */     this(0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RelativeDateFormat(Date time) {
/* 159 */     this(time.getTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RelativeDateFormat(long baseMillis) {
/* 169 */     this.baseMillis = baseMillis;
/* 170 */     this.showZeroDays = false;
/* 171 */     this.showZeroHours = true;
/* 172 */     this.positivePrefix = "";
/* 173 */     this.dayFormatter = NumberFormat.getNumberInstance();
/* 174 */     this.daySuffix = "d";
/* 175 */     this.hourFormatter = NumberFormat.getNumberInstance();
/* 176 */     this.hourSuffix = "h";
/* 177 */     this.minuteFormatter = NumberFormat.getNumberInstance();
/* 178 */     this.minuteSuffix = "m";
/* 179 */     this.secondFormatter = NumberFormat.getNumberInstance();
/* 180 */     this.secondFormatter.setMaximumFractionDigits(3);
/* 181 */     this.secondFormatter.setMinimumFractionDigits(3);
/* 182 */     this.secondSuffix = "s";
/*     */ 
/*     */ 
/*     */     
/* 186 */     this.calendar = new GregorianCalendar();
/* 187 */     this.numberFormat = new DecimalFormat("0");
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
/*     */   public long getBaseMillis() {
/* 199 */     return this.baseMillis;
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
/*     */   public void setBaseMillis(long baseMillis) {
/* 212 */     this.baseMillis = baseMillis;
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
/*     */   public boolean getShowZeroDays() {
/* 224 */     return this.showZeroDays;
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
/*     */   public void setShowZeroDays(boolean show) {
/* 236 */     this.showZeroDays = show;
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
/*     */   public boolean getShowZeroHours() {
/* 250 */     return this.showZeroHours;
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
/*     */   public void setShowZeroHours(boolean show) {
/* 264 */     this.showZeroHours = show;
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
/*     */   public String getPositivePrefix() {
/* 278 */     return this.positivePrefix;
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
/*     */   public void setPositivePrefix(String prefix) {
/* 292 */     ParamChecks.nullNotPermitted(prefix, "prefix");
/* 293 */     this.positivePrefix = prefix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDayFormatter(NumberFormat formatter) {
/* 304 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 305 */     this.dayFormatter = formatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDaySuffix() {
/* 316 */     return this.daySuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDaySuffix(String suffix) {
/* 327 */     ParamChecks.nullNotPermitted(suffix, "suffix");
/* 328 */     this.daySuffix = suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHourFormatter(NumberFormat formatter) {
/* 339 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 340 */     this.hourFormatter = formatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHourSuffix() {
/* 351 */     return this.hourSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHourSuffix(String suffix) {
/* 362 */     ParamChecks.nullNotPermitted(suffix, "suffix");
/* 363 */     this.hourSuffix = suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinuteFormatter(NumberFormat formatter) {
/* 374 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 375 */     this.minuteFormatter = formatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMinuteSuffix() {
/* 386 */     return this.minuteSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinuteSuffix(String suffix) {
/* 397 */     ParamChecks.nullNotPermitted(suffix, "suffix");
/* 398 */     this.minuteSuffix = suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSecondSuffix() {
/* 409 */     return this.secondSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecondSuffix(String suffix) {
/* 420 */     ParamChecks.nullNotPermitted(suffix, "suffix");
/* 421 */     this.secondSuffix = suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecondFormatter(NumberFormat formatter) {
/* 430 */     ParamChecks.nullNotPermitted(formatter, "formatter");
/* 431 */     this.secondFormatter = formatter;
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
/*     */   public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
/*     */     String signPrefix;
/* 447 */     long currentMillis = date.getTime();
/* 448 */     long elapsed = currentMillis - this.baseMillis;
/*     */     
/* 450 */     if (elapsed < 0L) {
/* 451 */       elapsed *= -1L;
/* 452 */       signPrefix = "-";
/*     */     } else {
/*     */       
/* 455 */       signPrefix = this.positivePrefix;
/*     */     } 
/*     */     
/* 458 */     long days = elapsed / 86400000L;
/* 459 */     elapsed -= days * 86400000L;
/* 460 */     long hours = elapsed / 3600000L;
/* 461 */     elapsed -= hours * 3600000L;
/* 462 */     long minutes = elapsed / 60000L;
/* 463 */     elapsed -= minutes * 60000L;
/* 464 */     double seconds = elapsed / 1000.0D;
/*     */     
/* 466 */     toAppendTo.append(signPrefix);
/* 467 */     if (days != 0L || this.showZeroDays) {
/* 468 */       toAppendTo.append(this.dayFormatter.format(days))
/* 469 */         .append(getDaySuffix());
/*     */     }
/* 471 */     if (hours != 0L || this.showZeroHours) {
/* 472 */       toAppendTo.append(this.hourFormatter.format(hours))
/* 473 */         .append(getHourSuffix());
/*     */     }
/* 475 */     toAppendTo.append(this.minuteFormatter.format(minutes))
/* 476 */       .append(getMinuteSuffix());
/* 477 */     toAppendTo.append(this.secondFormatter.format(seconds))
/* 478 */       .append(getSecondSuffix());
/* 479 */     return toAppendTo;
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
/*     */   public Date parse(String source, ParsePosition pos) {
/* 492 */     return null;
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
/* 504 */     if (obj == this) {
/* 505 */       return true;
/*     */     }
/* 507 */     if (!(obj instanceof RelativeDateFormat)) {
/* 508 */       return false;
/*     */     }
/* 510 */     if (!super.equals(obj)) {
/* 511 */       return false;
/*     */     }
/* 513 */     RelativeDateFormat that = (RelativeDateFormat)obj;
/* 514 */     if (this.baseMillis != that.baseMillis) {
/* 515 */       return false;
/*     */     }
/* 517 */     if (this.showZeroDays != that.showZeroDays) {
/* 518 */       return false;
/*     */     }
/* 520 */     if (this.showZeroHours != that.showZeroHours) {
/* 521 */       return false;
/*     */     }
/* 523 */     if (!this.positivePrefix.equals(that.positivePrefix)) {
/* 524 */       return false;
/*     */     }
/* 526 */     if (!this.daySuffix.equals(that.daySuffix)) {
/* 527 */       return false;
/*     */     }
/* 529 */     if (!this.hourSuffix.equals(that.hourSuffix)) {
/* 530 */       return false;
/*     */     }
/* 532 */     if (!this.minuteSuffix.equals(that.minuteSuffix)) {
/* 533 */       return false;
/*     */     }
/* 535 */     if (!this.secondSuffix.equals(that.secondSuffix)) {
/* 536 */       return false;
/*     */     }
/* 538 */     if (!this.dayFormatter.equals(that.dayFormatter)) {
/* 539 */       return false;
/*     */     }
/* 541 */     if (!this.hourFormatter.equals(that.hourFormatter)) {
/* 542 */       return false;
/*     */     }
/* 544 */     if (!this.minuteFormatter.equals(that.minuteFormatter)) {
/* 545 */       return false;
/*     */     }
/* 547 */     if (!this.secondFormatter.equals(that.secondFormatter)) {
/* 548 */       return false;
/*     */     }
/* 550 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 560 */     int result = 193;
/* 561 */     result = 37 * result + (int)(this.baseMillis ^ this.baseMillis >>> 32L);
/*     */     
/* 563 */     result = 37 * result + this.positivePrefix.hashCode();
/* 564 */     result = 37 * result + this.daySuffix.hashCode();
/* 565 */     result = 37 * result + this.hourSuffix.hashCode();
/* 566 */     result = 37 * result + this.minuteSuffix.hashCode();
/* 567 */     result = 37 * result + this.secondSuffix.hashCode();
/* 568 */     result = 37 * result + this.secondFormatter.hashCode();
/* 569 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 579 */     RelativeDateFormat clone = (RelativeDateFormat)super.clone();
/* 580 */     clone.dayFormatter = (NumberFormat)this.dayFormatter.clone();
/* 581 */     clone.secondFormatter = (NumberFormat)this.secondFormatter.clone();
/* 582 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/util/RelativeDateFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */