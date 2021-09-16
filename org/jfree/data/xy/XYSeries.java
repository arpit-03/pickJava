/*      */ package org.jfree.data.xy;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.general.Series;
/*      */ import org.jfree.data.general.SeriesException;
/*      */ import org.jfree.util.ObjectUtilities;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class XYSeries
/*      */   extends Series
/*      */   implements Cloneable, Serializable
/*      */ {
/*      */   static final long serialVersionUID = -5908509288197150436L;
/*      */   protected List data;
/*  114 */   private int maximumItemCount = Integer.MAX_VALUE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoSort;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean allowDuplicateXValues;
/*      */ 
/*      */ 
/*      */   
/*      */   private double minX;
/*      */ 
/*      */ 
/*      */   
/*      */   private double maxX;
/*      */ 
/*      */ 
/*      */   
/*      */   private double minY;
/*      */ 
/*      */ 
/*      */   
/*      */   private double maxY;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeries(Comparable key) {
/*  145 */     this(key, true, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeries(Comparable key, boolean autoSort) {
/*  157 */     this(key, autoSort, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeries(Comparable key, boolean autoSort, boolean allowDuplicateXValues) {
/*  172 */     super(key);
/*  173 */     this.data = new ArrayList();
/*  174 */     this.autoSort = autoSort;
/*  175 */     this.allowDuplicateXValues = allowDuplicateXValues;
/*  176 */     this.minX = Double.NaN;
/*  177 */     this.maxX = Double.NaN;
/*  178 */     this.minY = Double.NaN;
/*  179 */     this.maxY = Double.NaN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMinX() {
/*  194 */     return this.minX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaxX() {
/*  209 */     return this.maxX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMinY() {
/*  224 */     return this.minY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaxY() {
/*  239 */     return this.maxY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBoundsForAddedItem(XYDataItem item) {
/*  250 */     double x = item.getXValue();
/*  251 */     this.minX = minIgnoreNaN(this.minX, x);
/*  252 */     this.maxX = maxIgnoreNaN(this.maxX, x);
/*  253 */     if (item.getY() != null) {
/*  254 */       double y = item.getYValue();
/*  255 */       this.minY = minIgnoreNaN(this.minY, y);
/*  256 */       this.maxY = maxIgnoreNaN(this.maxY, y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateBoundsForRemovedItem(XYDataItem item) {
/*  269 */     boolean itemContributesToXBounds = false;
/*  270 */     boolean itemContributesToYBounds = false;
/*  271 */     double x = item.getXValue();
/*  272 */     if (!Double.isNaN(x) && (
/*  273 */       x <= this.minX || x >= this.maxX)) {
/*  274 */       itemContributesToXBounds = true;
/*      */     }
/*      */     
/*  277 */     if (item.getY() != null) {
/*  278 */       double y = item.getYValue();
/*  279 */       if (!Double.isNaN(y) && (
/*  280 */         y <= this.minY || y >= this.maxY)) {
/*  281 */         itemContributesToYBounds = true;
/*      */       }
/*      */     } 
/*      */     
/*  285 */     if (itemContributesToYBounds) {
/*  286 */       findBoundsByIteration();
/*      */     }
/*  288 */     else if (itemContributesToXBounds) {
/*  289 */       if (getAutoSort()) {
/*  290 */         this.minX = getX(0).doubleValue();
/*  291 */         this.maxX = getX(getItemCount() - 1).doubleValue();
/*      */       } else {
/*      */         
/*  294 */         findBoundsByIteration();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void findBoundsByIteration() {
/*  306 */     this.minX = Double.NaN;
/*  307 */     this.maxX = Double.NaN;
/*  308 */     this.minY = Double.NaN;
/*  309 */     this.maxY = Double.NaN;
/*  310 */     Iterator<XYDataItem> iterator = this.data.iterator();
/*  311 */     while (iterator.hasNext()) {
/*  312 */       XYDataItem item = iterator.next();
/*  313 */       updateBoundsForAddedItem(item);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoSort() {
/*  325 */     return this.autoSort;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowDuplicateXValues() {
/*  335 */     return this.allowDuplicateXValues;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemCount() {
/*  347 */     return this.data.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List getItems() {
/*  357 */     return Collections.unmodifiableList(this.data);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumItemCount() {
/*  369 */     return this.maximumItemCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumItemCount(int maximum) {
/*  387 */     this.maximumItemCount = maximum;
/*  388 */     int remove = this.data.size() - maximum;
/*  389 */     if (remove > 0) {
/*  390 */       this.data.subList(0, remove).clear();
/*  391 */       findBoundsByIteration();
/*  392 */       fireSeriesChanged();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(XYDataItem item) {
/*  404 */     add(item, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(double x, double y) {
/*  415 */     add(new Double(x), new Double(y), true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(double x, double y, boolean notify) {
/*  429 */     add(new Double(x), new Double(y), notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(double x, Number y) {
/*  441 */     add(new Double(x), y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(double x, Number y, boolean notify) {
/*  456 */     add(new Double(x), y, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(Number x, Number y) {
/*  475 */     add(x, y, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(Number x, Number y, boolean notify) {
/*  493 */     XYDataItem item = new XYDataItem(x, y);
/*  494 */     add(item, notify);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void add(XYDataItem item, boolean notify) {
/*  507 */     ParamChecks.nullNotPermitted(item, "item");
/*  508 */     item = (XYDataItem)item.clone();
/*  509 */     if (this.autoSort) {
/*  510 */       int index = Collections.binarySearch(this.data, item);
/*  511 */       if (index < 0) {
/*  512 */         this.data.add(-index - 1, item);
/*      */       
/*      */       }
/*  515 */       else if (this.allowDuplicateXValues) {
/*      */         
/*  517 */         int size = this.data.size();
/*  518 */         while (index < size && item.compareTo(this.data
/*  519 */             .get(index)) == 0) {
/*  520 */           index++;
/*      */         }
/*  522 */         if (index < this.data.size()) {
/*  523 */           this.data.add(index, item);
/*      */         } else {
/*      */           
/*  526 */           this.data.add(item);
/*      */         } 
/*      */       } else {
/*      */         
/*  530 */         throw new SeriesException("X-value already exists.");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  535 */       if (!this.allowDuplicateXValues) {
/*      */ 
/*      */         
/*  538 */         int index = indexOf(item.getX());
/*  539 */         if (index >= 0) {
/*  540 */           throw new SeriesException("X-value already exists.");
/*      */         }
/*      */       } 
/*  543 */       this.data.add(item);
/*      */     } 
/*  545 */     updateBoundsForAddedItem(item);
/*  546 */     if (getItemCount() > this.maximumItemCount) {
/*  547 */       XYDataItem removed = this.data.remove(0);
/*  548 */       updateBoundsForRemovedItem(removed);
/*      */     } 
/*  550 */     if (notify) {
/*  551 */       fireSeriesChanged();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void delete(int start, int end) {
/*  563 */     this.data.subList(start, end + 1).clear();
/*  564 */     findBoundsByIteration();
/*  565 */     fireSeriesChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataItem remove(int index) {
/*  577 */     XYDataItem removed = this.data.remove(index);
/*  578 */     updateBoundsForRemovedItem(removed);
/*  579 */     fireSeriesChanged();
/*  580 */     return removed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataItem remove(Number x) {
/*  594 */     return remove(indexOf(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clear() {
/*  602 */     if (this.data.size() > 0) {
/*  603 */       this.data.clear();
/*  604 */       this.minX = Double.NaN;
/*  605 */       this.maxX = Double.NaN;
/*  606 */       this.minY = Double.NaN;
/*  607 */       this.maxY = Double.NaN;
/*  608 */       fireSeriesChanged();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataItem getDataItem(int index) {
/*  620 */     XYDataItem item = this.data.get(index);
/*  621 */     return (XYDataItem)item.clone();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   XYDataItem getRawDataItem(int index) {
/*  634 */     return this.data.get(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getX(int index) {
/*  645 */     return getRawDataItem(index).getX();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Number getY(int index) {
/*  656 */     return getRawDataItem(index).getY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(int index, Number y) {
/*  670 */     XYDataItem item = getRawDataItem(index);
/*      */ 
/*      */     
/*  673 */     boolean iterate = false;
/*  674 */     double oldY = item.getYValue();
/*  675 */     if (!Double.isNaN(oldY)) {
/*  676 */       iterate = (oldY <= this.minY || oldY >= this.maxY);
/*      */     }
/*  678 */     item.setY(y);
/*      */     
/*  680 */     if (iterate) {
/*  681 */       findBoundsByIteration();
/*      */     }
/*  683 */     else if (y != null) {
/*  684 */       double yy = y.doubleValue();
/*  685 */       this.minY = minIgnoreNaN(this.minY, yy);
/*  686 */       this.maxY = maxIgnoreNaN(this.maxY, yy);
/*      */     } 
/*  688 */     fireSeriesChanged();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double minIgnoreNaN(double a, double b) {
/*  701 */     if (Double.isNaN(a)) {
/*  702 */       return b;
/*      */     }
/*  704 */     if (Double.isNaN(b)) {
/*  705 */       return a;
/*      */     }
/*  707 */     return Math.min(a, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double maxIgnoreNaN(double a, double b) {
/*  720 */     if (Double.isNaN(a)) {
/*  721 */       return b;
/*      */     }
/*  723 */     if (Double.isNaN(b)) {
/*  724 */       return a;
/*      */     }
/*  726 */     return Math.max(a, b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateByIndex(int index, Number y) {
/*  739 */     update(index, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update(Number x, Number y) {
/*  752 */     int index = indexOf(x);
/*  753 */     if (index < 0) {
/*  754 */       throw new SeriesException("No observation for x = " + x);
/*      */     }
/*  756 */     updateByIndex(index, y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataItem addOrUpdate(double x, double y) {
/*  771 */     return addOrUpdate(new Double(x), new Double(y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataItem addOrUpdate(Number x, Number y) {
/*  786 */     return addOrUpdate(new XYDataItem(x, y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYDataItem addOrUpdate(XYDataItem item) {
/*  801 */     ParamChecks.nullNotPermitted(item, "item");
/*  802 */     if (this.allowDuplicateXValues) {
/*  803 */       add(item);
/*  804 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  808 */     XYDataItem overwritten = null;
/*  809 */     int index = indexOf(item.getX());
/*  810 */     if (index >= 0) {
/*  811 */       XYDataItem existing = this.data.get(index);
/*  812 */       overwritten = (XYDataItem)existing.clone();
/*      */       
/*  814 */       boolean iterate = false;
/*  815 */       double oldY = existing.getYValue();
/*  816 */       if (!Double.isNaN(oldY)) {
/*  817 */         iterate = (oldY <= this.minY || oldY >= this.maxY);
/*      */       }
/*  819 */       existing.setY(item.getY());
/*      */       
/*  821 */       if (iterate) {
/*  822 */         findBoundsByIteration();
/*      */       }
/*  824 */       else if (item.getY() != null) {
/*  825 */         double yy = item.getY().doubleValue();
/*  826 */         this.minY = minIgnoreNaN(this.minY, yy);
/*  827 */         this.maxY = maxIgnoreNaN(this.maxY, yy);
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  835 */       item = (XYDataItem)item.clone();
/*  836 */       if (this.autoSort) {
/*  837 */         this.data.add(-index - 1, item);
/*      */       } else {
/*      */         
/*  840 */         this.data.add(item);
/*      */       } 
/*  842 */       updateBoundsForAddedItem(item);
/*      */ 
/*      */       
/*  845 */       if (getItemCount() > this.maximumItemCount) {
/*  846 */         XYDataItem removed = this.data.remove(0);
/*  847 */         updateBoundsForRemovedItem(removed);
/*      */       } 
/*      */     } 
/*  850 */     fireSeriesChanged();
/*  851 */     return overwritten;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(Number x) {
/*  865 */     if (this.autoSort) {
/*  866 */       return Collections.binarySearch(this.data, new XYDataItem(x, null));
/*      */     }
/*      */     
/*  869 */     for (int i = 0; i < this.data.size(); i++) {
/*  870 */       XYDataItem item = this.data.get(i);
/*  871 */       if (item.getX().equals(x)) {
/*  872 */         return i;
/*      */       }
/*      */     } 
/*  875 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double[][] toArray() {
/*  887 */     int itemCount = getItemCount();
/*  888 */     double[][] result = new double[2][itemCount];
/*  889 */     for (int i = 0; i < itemCount; i++) {
/*  890 */       result[0][i] = getX(i).doubleValue();
/*  891 */       Number y = getY(i);
/*  892 */       if (y != null) {
/*  893 */         result[1][i] = y.doubleValue();
/*      */       } else {
/*      */         
/*  896 */         result[1][i] = Double.NaN;
/*      */       } 
/*      */     } 
/*  899 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() throws CloneNotSupportedException {
/*  911 */     XYSeries clone = (XYSeries)super.clone();
/*  912 */     clone.data = (List)ObjectUtilities.deepClone(this.data);
/*  913 */     return clone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XYSeries createCopy(int start, int end) throws CloneNotSupportedException {
/*  929 */     XYSeries copy = (XYSeries)super.clone();
/*  930 */     copy.data = new ArrayList();
/*  931 */     if (this.data.size() > 0) {
/*  932 */       for (int index = start; index <= end; index++) {
/*  933 */         XYDataItem item = this.data.get(index);
/*  934 */         XYDataItem clone = (XYDataItem)item.clone();
/*      */         try {
/*  936 */           copy.add(clone);
/*      */         }
/*  938 */         catch (SeriesException e) {
/*  939 */           throw new RuntimeException("Unable to add cloned data item.", e);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  944 */     return copy;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  958 */     if (obj == this) {
/*  959 */       return true;
/*      */     }
/*  961 */     if (!(obj instanceof XYSeries)) {
/*  962 */       return false;
/*      */     }
/*  964 */     if (!super.equals(obj)) {
/*  965 */       return false;
/*      */     }
/*  967 */     XYSeries that = (XYSeries)obj;
/*  968 */     if (this.maximumItemCount != that.maximumItemCount) {
/*  969 */       return false;
/*      */     }
/*  971 */     if (this.autoSort != that.autoSort) {
/*  972 */       return false;
/*      */     }
/*  974 */     if (this.allowDuplicateXValues != that.allowDuplicateXValues) {
/*  975 */       return false;
/*      */     }
/*  977 */     if (!ObjectUtilities.equal(this.data, that.data)) {
/*  978 */       return false;
/*      */     }
/*  980 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  990 */     int result = super.hashCode();
/*      */ 
/*      */     
/*  993 */     int count = getItemCount();
/*  994 */     if (count > 0) {
/*  995 */       XYDataItem item = getRawDataItem(0);
/*  996 */       result = 29 * result + item.hashCode();
/*      */     } 
/*  998 */     if (count > 1) {
/*  999 */       XYDataItem item = getRawDataItem(count - 1);
/* 1000 */       result = 29 * result + item.hashCode();
/*      */     } 
/* 1002 */     if (count > 2) {
/* 1003 */       XYDataItem item = getRawDataItem(count / 2);
/* 1004 */       result = 29 * result + item.hashCode();
/*      */     } 
/* 1006 */     result = 29 * result + this.maximumItemCount;
/* 1007 */     result = 29 * result + (this.autoSort ? 1 : 0);
/* 1008 */     result = 29 * result + (this.allowDuplicateXValues ? 1 : 0);
/* 1009 */     return result;
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XYSeries.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */