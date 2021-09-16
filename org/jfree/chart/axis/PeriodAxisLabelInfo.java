/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Paint;
/*     */ import java.awt.Stroke;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.text.DateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.time.RegularTimePeriod;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PeriodAxisLabelInfo
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 5710451740920277357L;
/*  79 */   public static final RectangleInsets DEFAULT_INSETS = new RectangleInsets(2.0D, 2.0D, 2.0D, 2.0D);
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final Font DEFAULT_FONT = new Font("SansSerif", 0, 10);
/*     */ 
/*     */ 
/*     */   
/*  87 */   public static final Paint DEFAULT_LABEL_PAINT = Color.black;
/*     */ 
/*     */   
/*  90 */   public static final Stroke DEFAULT_DIVIDER_STROKE = new BasicStroke(0.5F);
/*     */ 
/*     */   
/*  93 */   public static final Paint DEFAULT_DIVIDER_PAINT = Color.gray;
/*     */ 
/*     */ 
/*     */   
/*     */   private Class periodClass;
/*     */ 
/*     */ 
/*     */   
/*     */   private RectangleInsets padding;
/*     */ 
/*     */ 
/*     */   
/*     */   private DateFormat dateFormat;
/*     */ 
/*     */ 
/*     */   
/*     */   private Font labelFont;
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Paint labelPaint;
/*     */ 
/*     */   
/*     */   private boolean drawDividers;
/*     */ 
/*     */   
/*     */   private transient Stroke dividerStroke;
/*     */ 
/*     */   
/*     */   private transient Paint dividerPaint;
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodAxisLabelInfo(Class periodClass, DateFormat dateFormat) {
/* 127 */     this(periodClass, dateFormat, DEFAULT_INSETS, DEFAULT_FONT, DEFAULT_LABEL_PAINT, true, DEFAULT_DIVIDER_STROKE, DEFAULT_DIVIDER_PAINT);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PeriodAxisLabelInfo(Class periodClass, DateFormat dateFormat, RectangleInsets padding, Font labelFont, Paint labelPaint, boolean drawDividers, Stroke dividerStroke, Paint dividerPaint) {
/* 151 */     ParamChecks.nullNotPermitted(periodClass, "periodClass");
/* 152 */     ParamChecks.nullNotPermitted(dateFormat, "dateFormat");
/* 153 */     ParamChecks.nullNotPermitted(padding, "padding");
/* 154 */     ParamChecks.nullNotPermitted(labelFont, "labelFont");
/* 155 */     ParamChecks.nullNotPermitted(labelPaint, "labelPaint");
/* 156 */     ParamChecks.nullNotPermitted(dividerStroke, "dividerStroke");
/* 157 */     ParamChecks.nullNotPermitted(dividerPaint, "dividerPaint");
/* 158 */     this.periodClass = periodClass;
/* 159 */     this.dateFormat = (DateFormat)dateFormat.clone();
/* 160 */     this.padding = padding;
/* 161 */     this.labelFont = labelFont;
/* 162 */     this.labelPaint = labelPaint;
/* 163 */     this.drawDividers = drawDividers;
/* 164 */     this.dividerStroke = dividerStroke;
/* 165 */     this.dividerPaint = dividerPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getPeriodClass() {
/* 175 */     return this.periodClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DateFormat getDateFormat() {
/* 184 */     return (DateFormat)this.dateFormat.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RectangleInsets getPadding() {
/* 193 */     return this.padding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Font getLabelFont() {
/* 202 */     return this.labelFont;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getLabelPaint() {
/* 211 */     return this.labelPaint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDrawDividers() {
/* 220 */     return this.drawDividers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Stroke getDividerStroke() {
/* 229 */     return this.dividerStroke;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Paint getDividerPaint() {
/* 238 */     return this.dividerPaint;
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
/*     */   public RegularTimePeriod createInstance(Date millisecond, TimeZone zone) {
/* 253 */     return createInstance(millisecond, zone, Locale.getDefault());
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
/*     */   public RegularTimePeriod createInstance(Date millisecond, TimeZone zone, Locale locale) {
/* 270 */     RegularTimePeriod result = null;
/*     */     try {
/* 272 */       Constructor<RegularTimePeriod> c = this.periodClass.getDeclaredConstructor(new Class[] { Date.class, TimeZone.class, Locale.class });
/*     */       
/* 274 */       result = c.newInstance(new Object[] { millisecond, zone, locale });
/*     */     
/*     */     }
/* 277 */     catch (Exception e) {}
/*     */ 
/*     */     
/* 280 */     return result;
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
/* 292 */     if (obj == this) {
/* 293 */       return true;
/*     */     }
/* 295 */     if (obj instanceof PeriodAxisLabelInfo) {
/* 296 */       PeriodAxisLabelInfo info = (PeriodAxisLabelInfo)obj;
/* 297 */       if (!info.periodClass.equals(this.periodClass)) {
/* 298 */         return false;
/*     */       }
/* 300 */       if (!info.dateFormat.equals(this.dateFormat)) {
/* 301 */         return false;
/*     */       }
/* 303 */       if (!info.padding.equals(this.padding)) {
/* 304 */         return false;
/*     */       }
/* 306 */       if (!info.labelFont.equals(this.labelFont)) {
/* 307 */         return false;
/*     */       }
/* 309 */       if (!info.labelPaint.equals(this.labelPaint)) {
/* 310 */         return false;
/*     */       }
/* 312 */       if (info.drawDividers != this.drawDividers) {
/* 313 */         return false;
/*     */       }
/* 315 */       if (!info.dividerStroke.equals(this.dividerStroke)) {
/* 316 */         return false;
/*     */       }
/* 318 */       if (!info.dividerPaint.equals(this.dividerPaint)) {
/* 319 */         return false;
/*     */       }
/* 321 */       return true;
/*     */     } 
/* 323 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 333 */     int result = 41;
/* 334 */     result += 37 * this.periodClass.hashCode();
/* 335 */     result += 37 * this.dateFormat.hashCode();
/* 336 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 348 */     PeriodAxisLabelInfo clone = (PeriodAxisLabelInfo)super.clone();
/* 349 */     return clone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 360 */     stream.defaultWriteObject();
/* 361 */     SerialUtilities.writePaint(this.labelPaint, stream);
/* 362 */     SerialUtilities.writeStroke(this.dividerStroke, stream);
/* 363 */     SerialUtilities.writePaint(this.dividerPaint, stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 376 */     stream.defaultReadObject();
/* 377 */     this.labelPaint = SerialUtilities.readPaint(stream);
/* 378 */     this.dividerStroke = SerialUtilities.readStroke(stream);
/* 379 */     this.dividerPaint = SerialUtilities.readPaint(stream);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/PeriodAxisLabelInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */