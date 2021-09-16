/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimePeriodValue
/*     */   implements Cloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3390443360845711275L;
/*     */   private TimePeriod period;
/*     */   private Number value;
/*     */   
/*     */   public TimePeriodValue(TimePeriod period, Number value) {
/*  73 */     ParamChecks.nullNotPermitted(period, "period");
/*  74 */     this.period = period;
/*  75 */     this.value = value;
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
/*     */   public TimePeriodValue(TimePeriod period, double value) {
/*  88 */     this(period, new Double(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimePeriod getPeriod() {
/*  97 */     return this.period;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getValue() {
/* 108 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Number value) {
/* 119 */     this.value = value;
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
/* 131 */     if (this == obj) {
/* 132 */       return true;
/*     */     }
/* 134 */     if (!(obj instanceof TimePeriodValue)) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     TimePeriodValue timePeriodValue = (TimePeriodValue)obj;
/*     */     
/* 140 */     if ((this.period != null) ? !this.period.equals(timePeriodValue.period) : (timePeriodValue.period != null))
/*     */     {
/* 142 */       return false;
/*     */     }
/* 144 */     if ((this.value != null) ? !this.value.equals(timePeriodValue.value) : (timePeriodValue.value != null))
/*     */     {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 160 */     int result = (this.period != null) ? this.period.hashCode() : 0;
/* 161 */     result = 29 * result + ((this.value != null) ? this.value.hashCode() : 0);
/* 162 */     return result;
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
/*     */   public Object clone() {
/* 175 */     Object clone = null;
/*     */     try {
/* 177 */       clone = super.clone();
/*     */     }
/* 179 */     catch (CloneNotSupportedException e) {
/* 180 */       throw new RuntimeException(e);
/*     */     } 
/* 182 */     return clone;
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
/* 193 */     return "TimePeriodValue[" + getPeriod() + "," + getValue() + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/TimePeriodValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */