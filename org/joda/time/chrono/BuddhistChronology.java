/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.DurationField;
/*     */ import org.joda.time.DurationFieldType;
/*     */ import org.joda.time.ReadableDateTime;
/*     */ import org.joda.time.field.DelegatedDateTimeField;
/*     */ import org.joda.time.field.DividedDateTimeField;
/*     */ import org.joda.time.field.OffsetDateTimeField;
/*     */ import org.joda.time.field.RemainderDateTimeField;
/*     */ import org.joda.time.field.SkipUndoDateTimeField;
/*     */ import org.joda.time.field.UnsupportedDurationField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class BuddhistChronology
/*     */   extends AssembledChronology
/*     */ {
/*     */   private static final long serialVersionUID = -3474595157769370126L;
/*     */   public static final int BE = 1;
/*  66 */   private static final DateTimeField ERA_FIELD = (DateTimeField)new BasicSingleEraDateTimeField("BE");
/*     */ 
/*     */   
/*     */   private static final int BUDDHIST_OFFSET = 543;
/*     */ 
/*     */   
/*  72 */   private static final ConcurrentHashMap<DateTimeZone, BuddhistChronology> cCache = new ConcurrentHashMap<DateTimeZone, BuddhistChronology>();
/*     */ 
/*     */   
/*  75 */   private static final BuddhistChronology INSTANCE_UTC = getInstance(DateTimeZone.UTC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BuddhistChronology getInstanceUTC() {
/*  85 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BuddhistChronology getInstance() {
/*  94 */     return getInstance(DateTimeZone.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BuddhistChronology getInstance(DateTimeZone paramDateTimeZone) {
/* 105 */     if (paramDateTimeZone == null) {
/* 106 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 108 */     BuddhistChronology buddhistChronology = cCache.get(paramDateTimeZone);
/* 109 */     if (buddhistChronology == null) {
/*     */       
/* 111 */       buddhistChronology = new BuddhistChronology(GJChronology.getInstance(paramDateTimeZone, null), null);
/*     */       
/* 113 */       DateTime dateTime = new DateTime(1, 1, 1, 0, 0, 0, 0, buddhistChronology);
/* 114 */       buddhistChronology = new BuddhistChronology(LimitChronology.getInstance(buddhistChronology, (ReadableDateTime)dateTime, null), "");
/* 115 */       BuddhistChronology buddhistChronology1 = cCache.putIfAbsent(paramDateTimeZone, buddhistChronology);
/* 116 */       if (buddhistChronology1 != null) {
/* 117 */         buddhistChronology = buddhistChronology1;
/*     */       }
/*     */     } 
/* 120 */     return buddhistChronology;
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
/*     */   private BuddhistChronology(Chronology paramChronology, Object paramObject) {
/* 132 */     super(paramChronology, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() {
/* 139 */     Chronology chronology = getBase();
/* 140 */     return (chronology == null) ? getInstanceUTC() : getInstance(chronology.getZone());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withUTC() {
/* 151 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 161 */     if (paramDateTimeZone == null) {
/* 162 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 164 */     if (paramDateTimeZone == getZone()) {
/* 165 */       return this;
/*     */     }
/* 167 */     return getInstance(paramDateTimeZone);
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
/*     */   public boolean equals(Object paramObject) {
/* 179 */     if (this == paramObject) {
/* 180 */       return true;
/*     */     }
/* 182 */     if (paramObject instanceof BuddhistChronology) {
/* 183 */       BuddhistChronology buddhistChronology = (BuddhistChronology)paramObject;
/* 184 */       return getZone().equals(buddhistChronology.getZone());
/*     */     } 
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 196 */     return "Buddhist".hashCode() * 11 + getZone().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 207 */     String str = "BuddhistChronology";
/* 208 */     DateTimeZone dateTimeZone = getZone();
/* 209 */     if (dateTimeZone != null) {
/* 210 */       str = str + '[' + dateTimeZone.getID() + ']';
/*     */     }
/* 212 */     return str;
/*     */   }
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 216 */     if (getParam() == null) {
/*     */       
/* 218 */       paramFields.eras = (DurationField)UnsupportedDurationField.getInstance(DurationFieldType.eras());
/*     */ 
/*     */       
/* 221 */       DateTimeField dateTimeField = paramFields.year;
/* 222 */       paramFields.year = (DateTimeField)new OffsetDateTimeField((DateTimeField)new SkipUndoDateTimeField(this, dateTimeField), 543);
/*     */ 
/*     */ 
/*     */       
/* 226 */       dateTimeField = paramFields.yearOfEra;
/* 227 */       paramFields.yearOfEra = (DateTimeField)new DelegatedDateTimeField(paramFields.year, paramFields.eras, DateTimeFieldType.yearOfEra());
/*     */ 
/*     */ 
/*     */       
/* 231 */       dateTimeField = paramFields.weekyear;
/* 232 */       paramFields.weekyear = (DateTimeField)new OffsetDateTimeField((DateTimeField)new SkipUndoDateTimeField(this, dateTimeField), 543);
/*     */ 
/*     */       
/* 235 */       OffsetDateTimeField offsetDateTimeField = new OffsetDateTimeField(paramFields.yearOfEra, 99);
/* 236 */       paramFields.centuryOfEra = (DateTimeField)new DividedDateTimeField((DateTimeField)offsetDateTimeField, paramFields.eras, DateTimeFieldType.centuryOfEra(), 100);
/*     */       
/* 238 */       paramFields.centuries = paramFields.centuryOfEra.getDurationField();
/*     */       
/* 240 */       RemainderDateTimeField remainderDateTimeField = new RemainderDateTimeField((DividedDateTimeField)paramFields.centuryOfEra);
/*     */       
/* 242 */       paramFields.yearOfCentury = (DateTimeField)new OffsetDateTimeField((DateTimeField)remainderDateTimeField, DateTimeFieldType.yearOfCentury(), 1);
/*     */ 
/*     */       
/* 245 */       remainderDateTimeField = new RemainderDateTimeField(paramFields.weekyear, paramFields.centuries, DateTimeFieldType.weekyearOfCentury(), 100);
/*     */       
/* 247 */       paramFields.weekyearOfCentury = (DateTimeField)new OffsetDateTimeField((DateTimeField)remainderDateTimeField, DateTimeFieldType.weekyearOfCentury(), 1);
/*     */ 
/*     */       
/* 250 */       paramFields.era = ERA_FIELD;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/BuddhistChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */