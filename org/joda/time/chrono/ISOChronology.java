/*     */ package org.joda.time.chrono;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.joda.time.Chronology;
/*     */ import org.joda.time.DateTimeField;
/*     */ import org.joda.time.DateTimeFieldType;
/*     */ import org.joda.time.DateTimeZone;
/*     */ import org.joda.time.field.DividedDateTimeField;
/*     */ import org.joda.time.field.RemainderDateTimeField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ISOChronology
/*     */   extends AssembledChronology
/*     */ {
/*     */   private static final long serialVersionUID = -6212696554273812441L;
/*     */   private static final ISOChronology INSTANCE_UTC;
/*  57 */   private static final ConcurrentHashMap<DateTimeZone, ISOChronology> cCache = new ConcurrentHashMap<DateTimeZone, ISOChronology>();
/*     */   static {
/*  59 */     INSTANCE_UTC = new ISOChronology(GregorianChronology.getInstanceUTC());
/*  60 */     cCache.put(DateTimeZone.UTC, INSTANCE_UTC);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ISOChronology getInstanceUTC() {
/*  70 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ISOChronology getInstance() {
/*  79 */     return getInstance(DateTimeZone.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ISOChronology getInstance(DateTimeZone paramDateTimeZone) {
/*  89 */     if (paramDateTimeZone == null) {
/*  90 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/*  92 */     ISOChronology iSOChronology = cCache.get(paramDateTimeZone);
/*  93 */     if (iSOChronology == null) {
/*  94 */       iSOChronology = new ISOChronology(ZonedChronology.getInstance(INSTANCE_UTC, paramDateTimeZone));
/*  95 */       ISOChronology iSOChronology1 = cCache.putIfAbsent(paramDateTimeZone, iSOChronology);
/*  96 */       if (iSOChronology1 != null) {
/*  97 */         iSOChronology = iSOChronology1;
/*     */       }
/*     */     } 
/* 100 */     return iSOChronology;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ISOChronology(Chronology paramChronology) {
/* 110 */     super(paramChronology, null);
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
/* 121 */     return INSTANCE_UTC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chronology withZone(DateTimeZone paramDateTimeZone) {
/* 131 */     if (paramDateTimeZone == null) {
/* 132 */       paramDateTimeZone = DateTimeZone.getDefault();
/*     */     }
/* 134 */     if (paramDateTimeZone == getZone()) {
/* 135 */       return this;
/*     */     }
/* 137 */     return getInstance(paramDateTimeZone);
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
/* 148 */     String str = "ISOChronology";
/* 149 */     DateTimeZone dateTimeZone = getZone();
/* 150 */     if (dateTimeZone != null) {
/* 151 */       str = str + '[' + dateTimeZone.getID() + ']';
/*     */     }
/* 153 */     return str;
/*     */   }
/*     */   
/*     */   protected void assemble(AssembledChronology.Fields paramFields) {
/* 157 */     if (getBase().getZone() == DateTimeZone.UTC) {
/*     */       
/* 159 */       paramFields.centuryOfEra = (DateTimeField)new DividedDateTimeField(ISOYearOfEraDateTimeField.INSTANCE, DateTimeFieldType.centuryOfEra(), 100);
/*     */       
/* 161 */       paramFields.centuries = paramFields.centuryOfEra.getDurationField();
/*     */       
/* 163 */       paramFields.yearOfCentury = (DateTimeField)new RemainderDateTimeField((DividedDateTimeField)paramFields.centuryOfEra, DateTimeFieldType.yearOfCentury());
/*     */       
/* 165 */       paramFields.weekyearOfCentury = (DateTimeField)new RemainderDateTimeField((DividedDateTimeField)paramFields.centuryOfEra, paramFields.weekyears, DateTimeFieldType.weekyearOfCentury());
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
/*     */   public boolean equals(Object paramObject) {
/* 179 */     if (this == paramObject) {
/* 180 */       return true;
/*     */     }
/* 182 */     if (paramObject instanceof ISOChronology) {
/* 183 */       ISOChronology iSOChronology = (ISOChronology)paramObject;
/* 184 */       return getZone().equals(iSOChronology.getZone());
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
/* 196 */     return "ISO".hashCode() * 11 + getZone().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() {
/* 205 */     return new Stub(getZone());
/*     */   }
/*     */   
/*     */   private static final class Stub
/*     */     implements Serializable {
/*     */     private static final long serialVersionUID = -6212696554273812441L;
/*     */     private transient DateTimeZone iZone;
/*     */     
/*     */     Stub(DateTimeZone param1DateTimeZone) {
/* 214 */       this.iZone = param1DateTimeZone;
/*     */     }
/*     */     
/*     */     private Object readResolve() {
/* 218 */       return ISOChronology.getInstance(this.iZone);
/*     */     }
/*     */     
/*     */     private void writeObject(ObjectOutputStream param1ObjectOutputStream) throws IOException {
/* 222 */       param1ObjectOutputStream.writeObject(this.iZone);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void readObject(ObjectInputStream param1ObjectInputStream) throws IOException, ClassNotFoundException {
/* 228 */       this.iZone = (DateTimeZone)param1ObjectInputStream.readObject();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/joda/time/chrono/ISOChronology.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */