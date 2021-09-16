/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.general.Series;
/*     */ import org.jfree.data.general.SeriesException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimePeriodValues
/*     */   extends Series
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -2210593619794989709L;
/*     */   protected static final String DEFAULT_DOMAIN_DESCRIPTION = "Time";
/*     */   protected static final String DEFAULT_RANGE_DESCRIPTION = "Value";
/*     */   private String domain;
/*     */   private String range;
/*     */   private List data;
/*  90 */   private int minStartIndex = -1;
/*     */ 
/*     */   
/*  93 */   private int maxStartIndex = -1;
/*     */ 
/*     */   
/*  96 */   private int minMiddleIndex = -1;
/*     */ 
/*     */   
/*  99 */   private int maxMiddleIndex = -1;
/*     */ 
/*     */   
/* 102 */   private int minEndIndex = -1;
/*     */ 
/*     */   
/* 105 */   private int maxEndIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimePeriodValues(String name) {
/* 113 */     this(name, "Time", "Value");
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
/*     */   public TimePeriodValues(String name, String domain, String range) {
/* 128 */     super(name);
/* 129 */     this.domain = domain;
/* 130 */     this.range = range;
/* 131 */     this.data = new ArrayList();
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
/*     */   public String getDomainDescription() {
/* 143 */     return this.domain;
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
/*     */   public void setDomainDescription(String description) {
/* 155 */     String old = this.domain;
/* 156 */     this.domain = description;
/* 157 */     firePropertyChange("Domain", old, description);
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
/*     */   public String getRangeDescription() {
/* 169 */     return this.range;
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
/*     */   public void setRangeDescription(String description) {
/* 181 */     String old = this.range;
/* 182 */     this.range = description;
/* 183 */     firePropertyChange("Range", old, description);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 193 */     return this.data.size();
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
/*     */   public TimePeriodValue getDataItem(int index) {
/* 205 */     return this.data.get(index);
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
/*     */   public TimePeriod getTimePeriod(int index) {
/* 219 */     return getDataItem(index).getPeriod();
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
/*     */   public Number getValue(int index) {
/* 233 */     return getDataItem(index).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(TimePeriodValue item) {
/* 243 */     ParamChecks.nullNotPermitted(item, "item");
/* 244 */     this.data.add(item);
/* 245 */     updateBounds(item.getPeriod(), this.data.size() - 1);
/* 246 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateBounds(TimePeriod period, int index) {
/* 257 */     long start = period.getStart().getTime();
/* 258 */     long end = period.getEnd().getTime();
/* 259 */     long middle = start + (end - start) / 2L;
/*     */     
/* 261 */     if (this.minStartIndex >= 0) {
/*     */       
/* 263 */       long minStart = getDataItem(this.minStartIndex).getPeriod().getStart().getTime();
/* 264 */       if (start < minStart) {
/* 265 */         this.minStartIndex = index;
/*     */       }
/*     */     } else {
/*     */       
/* 269 */       this.minStartIndex = index;
/*     */     } 
/*     */     
/* 272 */     if (this.maxStartIndex >= 0) {
/*     */       
/* 274 */       long maxStart = getDataItem(this.maxStartIndex).getPeriod().getStart().getTime();
/* 275 */       if (start > maxStart) {
/* 276 */         this.maxStartIndex = index;
/*     */       }
/*     */     } else {
/*     */       
/* 280 */       this.maxStartIndex = index;
/*     */     } 
/*     */     
/* 283 */     if (this.minMiddleIndex >= 0) {
/*     */       
/* 285 */       long s = getDataItem(this.minMiddleIndex).getPeriod().getStart().getTime();
/*     */       
/* 287 */       long e = getDataItem(this.minMiddleIndex).getPeriod().getEnd().getTime();
/* 288 */       long minMiddle = s + (e - s) / 2L;
/* 289 */       if (middle < minMiddle) {
/* 290 */         this.minMiddleIndex = index;
/*     */       }
/*     */     } else {
/*     */       
/* 294 */       this.minMiddleIndex = index;
/*     */     } 
/*     */     
/* 297 */     if (this.maxMiddleIndex >= 0) {
/*     */       
/* 299 */       long s = getDataItem(this.maxMiddleIndex).getPeriod().getStart().getTime();
/*     */       
/* 301 */       long e = getDataItem(this.maxMiddleIndex).getPeriod().getEnd().getTime();
/* 302 */       long maxMiddle = s + (e - s) / 2L;
/* 303 */       if (middle > maxMiddle) {
/* 304 */         this.maxMiddleIndex = index;
/*     */       }
/*     */     } else {
/*     */       
/* 308 */       this.maxMiddleIndex = index;
/*     */     } 
/*     */     
/* 311 */     if (this.minEndIndex >= 0) {
/*     */       
/* 313 */       long minEnd = getDataItem(this.minEndIndex).getPeriod().getEnd().getTime();
/* 314 */       if (end < minEnd) {
/* 315 */         this.minEndIndex = index;
/*     */       }
/*     */     } else {
/*     */       
/* 319 */       this.minEndIndex = index;
/*     */     } 
/*     */     
/* 322 */     if (this.maxEndIndex >= 0) {
/*     */       
/* 324 */       long maxEnd = getDataItem(this.maxEndIndex).getPeriod().getEnd().getTime();
/* 325 */       if (end > maxEnd) {
/* 326 */         this.maxEndIndex = index;
/*     */       }
/*     */     } else {
/*     */       
/* 330 */       this.maxEndIndex = index;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void recalculateBounds() {
/* 339 */     this.minStartIndex = -1;
/* 340 */     this.minMiddleIndex = -1;
/* 341 */     this.minEndIndex = -1;
/* 342 */     this.maxStartIndex = -1;
/* 343 */     this.maxMiddleIndex = -1;
/* 344 */     this.maxEndIndex = -1;
/* 345 */     for (int i = 0; i < this.data.size(); i++) {
/* 346 */       TimePeriodValue tpv = this.data.get(i);
/* 347 */       updateBounds(tpv.getPeriod(), i);
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
/*     */   public void add(TimePeriod period, double value) {
/* 361 */     TimePeriodValue item = new TimePeriodValue(period, value);
/* 362 */     add(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(TimePeriod period, Number value) {
/* 373 */     TimePeriodValue item = new TimePeriodValue(period, value);
/* 374 */     add(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(int index, Number value) {
/* 385 */     TimePeriodValue item = getDataItem(index);
/* 386 */     item.setValue(value);
/* 387 */     fireSeriesChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(int start, int end) {
/* 398 */     for (int i = 0; i <= end - start; i++) {
/* 399 */       this.data.remove(start);
/*     */     }
/* 401 */     recalculateBounds();
/* 402 */     fireSeriesChanged();
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
/* 414 */     if (obj == this) {
/* 415 */       return true;
/*     */     }
/* 417 */     if (!(obj instanceof TimePeriodValues)) {
/* 418 */       return false;
/*     */     }
/* 420 */     if (!super.equals(obj)) {
/* 421 */       return false;
/*     */     }
/* 423 */     TimePeriodValues that = (TimePeriodValues)obj;
/* 424 */     if (!ObjectUtilities.equal(getDomainDescription(), that
/* 425 */         .getDomainDescription())) {
/* 426 */       return false;
/*     */     }
/* 428 */     if (!ObjectUtilities.equal(getRangeDescription(), that
/* 429 */         .getRangeDescription())) {
/* 430 */       return false;
/*     */     }
/* 432 */     int count = getItemCount();
/* 433 */     if (count != that.getItemCount()) {
/* 434 */       return false;
/*     */     }
/* 436 */     for (int i = 0; i < count; i++) {
/* 437 */       if (!getDataItem(i).equals(that.getDataItem(i))) {
/* 438 */         return false;
/*     */       }
/*     */     } 
/* 441 */     return true;
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
/* 452 */     int result = (this.domain != null) ? this.domain.hashCode() : 0;
/* 453 */     result = 29 * result + ((this.range != null) ? this.range.hashCode() : 0);
/* 454 */     result = 29 * result + this.data.hashCode();
/* 455 */     result = 29 * result + this.minStartIndex;
/* 456 */     result = 29 * result + this.maxStartIndex;
/* 457 */     result = 29 * result + this.minMiddleIndex;
/* 458 */     result = 29 * result + this.maxMiddleIndex;
/* 459 */     result = 29 * result + this.minEndIndex;
/* 460 */     result = 29 * result + this.maxEndIndex;
/* 461 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 481 */     Object clone = createCopy(0, getItemCount() - 1);
/* 482 */     return clone;
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
/*     */   public TimePeriodValues createCopy(int start, int end) throws CloneNotSupportedException {
/* 499 */     TimePeriodValues copy = (TimePeriodValues)super.clone();
/*     */     
/* 501 */     copy.data = new ArrayList();
/* 502 */     if (this.data.size() > 0) {
/* 503 */       for (int index = start; index <= end; index++) {
/* 504 */         TimePeriodValue item = this.data.get(index);
/* 505 */         TimePeriodValue clone = (TimePeriodValue)item.clone();
/*     */         try {
/* 507 */           copy.add(clone);
/*     */         }
/* 509 */         catch (SeriesException e) {
/* 510 */           System.err.println("Failed to add cloned item.");
/*     */         } 
/*     */       } 
/*     */     }
/* 514 */     return copy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinStartIndex() {
/* 524 */     return this.minStartIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStartIndex() {
/* 533 */     return this.maxStartIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinMiddleIndex() {
/* 543 */     return this.minMiddleIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxMiddleIndex() {
/* 553 */     return this.maxMiddleIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMinEndIndex() {
/* 562 */     return this.minEndIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxEndIndex() {
/* 571 */     return this.maxEndIndex;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/TimePeriodValues.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */