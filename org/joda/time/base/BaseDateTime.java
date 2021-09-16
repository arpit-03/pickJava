/*     */ package org.joda.time.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeUtils;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.ReadableDateTime;
/*     */ import org.joda.time.chrono.ISOChronology;
/*     */ import org.joda.time.convert.ConverterManager;
/*     */ import org.joda.time.convert.InstantConverter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseDateTime
/*     */   extends AbstractDateTime
/*     */   implements ReadableDateTime, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -6728882245981L;
/*     */   private volatile long iMillis;
/*     */   private volatile Chronology iChronology;
/*     */   
/*     */   public BaseDateTime() {
/*  61 */     this(DateTimeUtils.currentTimeMillis(), (Chronology)ISOChronology.getInstance());
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
/*     */   public BaseDateTime(DateTimeZone paramDateTimeZone) {
/*  73 */     this(DateTimeUtils.currentTimeMillis(), (Chronology)ISOChronology.getInstance(paramDateTimeZone));
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
/*     */   public BaseDateTime(Chronology paramChronology) {
/*  86 */     this(DateTimeUtils.currentTimeMillis(), paramChronology);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseDateTime(long paramLong) {
/*  97 */     this(paramLong, (Chronology)ISOChronology.getInstance());
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
/*     */   public BaseDateTime(long paramLong, DateTimeZone paramDateTimeZone) {
/* 110 */     this(paramLong, (Chronology)ISOChronology.getInstance(paramDateTimeZone));
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
/*     */   public BaseDateTime(long paramLong, Chronology paramChronology) {
/* 125 */     this.iChronology = checkChronology(paramChronology);
/* 126 */     this.iMillis = checkInstant(paramLong, this.iChronology);
/* 127 */     adjustForMinMax();
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
/*     */   public BaseDateTime(Object paramObject, DateTimeZone paramDateTimeZone) {
/* 148 */     InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(paramObject);
/* 149 */     Chronology chronology = checkChronology(instantConverter.getChronology(paramObject, paramDateTimeZone));
/* 150 */     this.iChronology = chronology;
/* 151 */     this.iMillis = checkInstant(instantConverter.getInstantMillis(paramObject, chronology), chronology);
/* 152 */     adjustForMinMax();
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
/*     */   public BaseDateTime(Object paramObject, Chronology paramChronology) {
/* 171 */     InstantConverter instantConverter = ConverterManager.getInstance().getInstantConverter(paramObject);
/* 172 */     this.iChronology = checkChronology(instantConverter.getChronology(paramObject, paramChronology));
/* 173 */     this.iMillis = checkInstant(instantConverter.getInstantMillis(paramObject, paramChronology), this.iChronology);
/* 174 */     adjustForMinMax();
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
/*     */   public BaseDateTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7) {
/* 198 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, (Chronology)ISOChronology.getInstance());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseDateTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, DateTimeZone paramDateTimeZone) {
/* 226 */     this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, (Chronology)ISOChronology.getInstance(paramDateTimeZone));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BaseDateTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, Chronology paramChronology) {
/* 256 */     this.iChronology = checkChronology(paramChronology);
/* 257 */     long l = this.iChronology.getDateTimeMillis(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */     
/* 259 */     this.iMillis = checkInstant(l, this.iChronology);
/* 260 */     adjustForMinMax();
/*     */   }
/*     */   
/*     */   private void adjustForMinMax() {
/* 264 */     if (this.iMillis == Long.MIN_VALUE || this.iMillis == Long.MAX_VALUE) {
/* 265 */       this.iChronology = this.iChronology.withUTC();
/*     */     }
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
/*     */   protected Chronology checkChronology(Chronology paramChronology) {
/* 280 */     return DateTimeUtils.getChronology(paramChronology);
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
/*     */   protected long checkInstant(long paramLong, Chronology paramChronology) {
/* 294 */     return paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getMillis() {
/* 305 */     return this.iMillis;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology getChronology() {
/* 314 */     return this.iChronology;
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
/*     */   protected void setMillis(long paramLong) {
/* 327 */     this.iMillis = checkInstant(paramLong, this.iChronology);
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
/*     */   protected void setChronology(Chronology paramChronology) {
/* 339 */     this.iChronology = checkChronology(paramChronology);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/base/BaseDateTime.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */