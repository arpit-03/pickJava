/*     */ package org.apache.commons.math3.stat;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.commons.math3.exception.MathIllegalArgumentException;
/*     */ import org.apache.commons.math3.exception.NullArgumentException;
/*     */ import org.apache.commons.math3.exception.util.Localizable;
/*     */ import org.apache.commons.math3.exception.util.LocalizedFormats;
/*     */ import org.apache.commons.math3.util.MathUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Frequency
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3845586908418844111L;
/*     */   private final SortedMap<Comparable<?>, Long> freqTable;
/*     */   
/*     */   public Frequency() {
/*  77 */     this.freqTable = new TreeMap<Comparable<?>, Long>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frequency(Comparator<?> comparator) {
/*  87 */     this.freqTable = (SortedMap)new TreeMap<Object, Long>(comparator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  97 */     NumberFormat nf = NumberFormat.getPercentInstance();
/*  98 */     StringBuilder outBuffer = new StringBuilder();
/*  99 */     outBuffer.append("Value \t Freq. \t Pct. \t Cum Pct. \n");
/* 100 */     Iterator<Comparable<?>> iter = this.freqTable.keySet().iterator();
/* 101 */     while (iter.hasNext()) {
/* 102 */       Comparable<?> value = iter.next();
/* 103 */       outBuffer.append(value);
/* 104 */       outBuffer.append('\t');
/* 105 */       outBuffer.append(getCount(value));
/* 106 */       outBuffer.append('\t');
/* 107 */       outBuffer.append(nf.format(getPct(value)));
/* 108 */       outBuffer.append('\t');
/* 109 */       outBuffer.append(nf.format(getCumPct(value)));
/* 110 */       outBuffer.append('\n');
/*     */     } 
/* 112 */     return outBuffer.toString();
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
/*     */   public void addValue(Comparable<?> v) throws MathIllegalArgumentException {
/* 126 */     incrementValue(v, 1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(int v) throws MathIllegalArgumentException {
/* 137 */     addValue(Long.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(long v) throws MathIllegalArgumentException {
/* 148 */     addValue(Long.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(char v) throws MathIllegalArgumentException {
/* 159 */     addValue(Character.valueOf(v));
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
/*     */   public void incrementValue(Comparable<?> v, long increment) throws MathIllegalArgumentException {
/* 175 */     Comparable<?> obj = v;
/* 176 */     if (v instanceof Integer) {
/* 177 */       obj = Long.valueOf(((Integer)v).longValue());
/*     */     }
/*     */     try {
/* 180 */       Long count = this.freqTable.get(obj);
/* 181 */       if (count == null) {
/* 182 */         this.freqTable.put(obj, Long.valueOf(increment));
/*     */       } else {
/* 184 */         this.freqTable.put(obj, Long.valueOf(count.longValue() + increment));
/*     */       } 
/* 186 */     } catch (ClassCastException ex) {
/*     */       
/* 188 */       throw new MathIllegalArgumentException(LocalizedFormats.INSTANCES_NOT_COMPARABLE_TO_EXISTING_VALUES, new Object[] { v.getClass().getName() });
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementValue(int v, long increment) throws MathIllegalArgumentException {
/* 208 */     incrementValue(Long.valueOf(v), increment);
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
/*     */   public void incrementValue(long v, long increment) throws MathIllegalArgumentException {
/* 225 */     incrementValue(Long.valueOf(v), increment);
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
/*     */   public void incrementValue(char v, long increment) throws MathIllegalArgumentException {
/* 242 */     incrementValue(Character.valueOf(v), increment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 247 */     this.freqTable.clear();
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
/*     */   public Iterator<Comparable<?>> valuesIterator() {
/* 260 */     return this.freqTable.keySet().iterator();
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
/*     */   public Iterator<Map.Entry<Comparable<?>, Long>> entrySetIterator() {
/* 277 */     return this.freqTable.entrySet().iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSumFreq() {
/* 288 */     long result = 0L;
/* 289 */     Iterator<Long> iterator = this.freqTable.values().iterator();
/* 290 */     while (iterator.hasNext()) {
/* 291 */       result += ((Long)iterator.next()).longValue();
/*     */     }
/* 293 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount(Comparable<?> v) {
/* 304 */     if (v instanceof Integer) {
/* 305 */       return getCount(((Integer)v).longValue());
/*     */     }
/* 307 */     long result = 0L;
/*     */     try {
/* 309 */       Long count = this.freqTable.get(v);
/* 310 */       if (count != null) {
/* 311 */         result = count.longValue();
/*     */       }
/* 313 */     } catch (ClassCastException classCastException) {}
/*     */ 
/*     */     
/* 316 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount(int v) {
/* 326 */     return getCount(Long.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount(long v) {
/* 336 */     return getCount(Long.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount(char v) {
/* 346 */     return getCount(Character.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUniqueCount() {
/* 356 */     return this.freqTable.keySet().size();
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
/*     */   public double getPct(Comparable<?> v) {
/* 371 */     long sumFreq = getSumFreq();
/* 372 */     if (sumFreq == 0L) {
/* 373 */       return Double.NaN;
/*     */     }
/* 375 */     return getCount(v) / sumFreq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPct(int v) {
/* 386 */     return getPct(Long.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPct(long v) {
/* 397 */     return getPct(Long.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getPct(char v) {
/* 408 */     return getPct(Character.valueOf(v));
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
/*     */   public long getCumFreq(Comparable<?> v) {
/* 423 */     if (getSumFreq() == 0L) {
/* 424 */       return 0L;
/*     */     }
/* 426 */     if (v instanceof Integer) {
/* 427 */       return getCumFreq(((Integer)v).longValue());
/*     */     }
/* 429 */     Comparator<Comparable<?>> c = (Comparator)this.freqTable.comparator();
/* 430 */     if (c == null) {
/* 431 */       c = new NaturalComparator();
/*     */     }
/* 433 */     long result = 0L;
/*     */     
/*     */     try {
/* 436 */       Long value = this.freqTable.get(v);
/* 437 */       if (value != null) {
/* 438 */         result = value.longValue();
/*     */       }
/* 440 */     } catch (ClassCastException ex) {
/* 441 */       return result;
/*     */     } 
/*     */     
/* 444 */     if (c.compare(v, this.freqTable.firstKey()) < 0) {
/* 445 */       return 0L;
/*     */     }
/*     */     
/* 448 */     if (c.compare(v, this.freqTable.lastKey()) >= 0) {
/* 449 */       return getSumFreq();
/*     */     }
/*     */     
/* 452 */     Iterator<Comparable<?>> values = valuesIterator();
/* 453 */     while (values.hasNext()) {
/* 454 */       Comparable<?> nextValue = values.next();
/* 455 */       if (c.compare(v, nextValue) > 0) {
/* 456 */         result += getCount(nextValue); continue;
/*     */       } 
/* 458 */       return result;
/*     */     } 
/*     */     
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
/*     */   public long getCumFreq(int v) {
/* 473 */     return getCumFreq(Long.valueOf(v));
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
/*     */   public long getCumFreq(long v) {
/* 485 */     return getCumFreq(Long.valueOf(v));
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
/*     */   public long getCumFreq(char v) {
/* 497 */     return getCumFreq(Character.valueOf(v));
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
/*     */   public double getCumPct(Comparable<?> v) {
/* 514 */     long sumFreq = getSumFreq();
/* 515 */     if (sumFreq == 0L) {
/* 516 */       return Double.NaN;
/*     */     }
/* 518 */     return getCumFreq(v) / sumFreq;
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
/*     */   public double getCumPct(int v) {
/* 531 */     return getCumPct(Long.valueOf(v));
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
/*     */   public double getCumPct(long v) {
/* 544 */     return getCumPct(Long.valueOf(v));
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
/*     */   public double getCumPct(char v) {
/* 557 */     return getCumPct(Character.valueOf(v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Comparable<?>> getMode() {
/* 567 */     long mostPopular = 0L;
/*     */ 
/*     */     
/* 570 */     for (Long l : this.freqTable.values()) {
/* 571 */       long frequency = l.longValue();
/* 572 */       if (frequency > mostPopular) {
/* 573 */         mostPopular = frequency;
/*     */       }
/*     */     } 
/*     */     
/* 577 */     List<Comparable<?>> modeList = new ArrayList<Comparable<?>>();
/* 578 */     for (Map.Entry<Comparable<?>, Long> ent : this.freqTable.entrySet()) {
/* 579 */       long frequency = ((Long)ent.getValue()).longValue();
/* 580 */       if (frequency == mostPopular) {
/* 581 */         modeList.add(ent.getKey());
/*     */       }
/*     */     } 
/* 584 */     return modeList;
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
/*     */   public void merge(Frequency other) throws NullArgumentException {
/* 599 */     MathUtils.checkNotNull(other, (Localizable)LocalizedFormats.NULL_NOT_ALLOWED, new Object[0]);
/*     */     
/* 601 */     Iterator<Map.Entry<Comparable<?>, Long>> iter = other.entrySetIterator();
/* 602 */     while (iter.hasNext()) {
/* 603 */       Map.Entry<Comparable<?>, Long> entry = iter.next();
/* 604 */       incrementValue(entry.getKey(), ((Long)entry.getValue()).longValue());
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
/*     */   public void merge(Collection<Frequency> others) throws NullArgumentException {
/* 618 */     MathUtils.checkNotNull(others, (Localizable)LocalizedFormats.NULL_NOT_ALLOWED, new Object[0]);
/*     */     
/* 620 */     for (Frequency freq : others) {
/* 621 */       merge(freq);
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
/*     */   private static class NaturalComparator<T extends Comparable<T>>
/*     */     implements Comparator<Comparable<T>>, Serializable
/*     */   {
/*     */     private static final long serialVersionUID = -3852193713161395148L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private NaturalComparator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(Comparable<T> o1, Comparable<T> o2) {
/* 652 */       return o1.compareTo((T)o2);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 659 */     int prime = 31;
/* 660 */     int result = 1;
/* 661 */     result = 31 * result + ((this.freqTable == null) ? 0 : this.freqTable.hashCode());
/*     */     
/* 663 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 669 */     if (this == obj) {
/* 670 */       return true;
/*     */     }
/* 672 */     if (!(obj instanceof Frequency)) {
/* 673 */       return false;
/*     */     }
/* 675 */     Frequency other = (Frequency)obj;
/* 676 */     if (this.freqTable == null) {
/* 677 */       if (other.freqTable != null) {
/* 678 */         return false;
/*     */       }
/* 680 */     } else if (!this.freqTable.equals(other.freqTable)) {
/* 681 */       return false;
/*     */     } 
/* 683 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/commons/math3/stat/Frequency.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */