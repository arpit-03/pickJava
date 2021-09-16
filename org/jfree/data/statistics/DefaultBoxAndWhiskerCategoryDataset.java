/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.jfree.data.KeyedObjects2D;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.RangeInfo;
/*     */ import org.jfree.data.general.AbstractDataset;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultBoxAndWhiskerCategoryDataset
/*     */   extends AbstractDataset
/*     */   implements BoxAndWhiskerCategoryDataset, RangeInfo, PublicCloneable
/*     */ {
/* 105 */   protected KeyedObjects2D data = new KeyedObjects2D();
/* 106 */   private double minimumRangeValue = Double.NaN;
/* 107 */   private int minimumRangeValueRow = -1;
/* 108 */   private int minimumRangeValueColumn = -1;
/* 109 */   private double maximumRangeValue = Double.NaN;
/* 110 */   private int maximumRangeValueRow = -1;
/* 111 */   private int maximumRangeValueColumn = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(List list, Comparable rowKey, Comparable columnKey) {
/* 127 */     BoxAndWhiskerItem item = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(list);
/* 128 */     add(item, rowKey, columnKey);
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
/*     */   public void add(BoxAndWhiskerItem item, Comparable rowKey, Comparable columnKey) {
/* 144 */     this.data.addObject(item, rowKey, columnKey);
/*     */ 
/*     */     
/* 147 */     int r = this.data.getRowIndex(rowKey);
/* 148 */     int c = this.data.getColumnIndex(columnKey);
/* 149 */     if ((this.maximumRangeValueRow == r && this.maximumRangeValueColumn == c) || (this.minimumRangeValueRow == r && this.minimumRangeValueColumn == c)) {
/*     */ 
/*     */       
/* 152 */       updateBounds();
/*     */     }
/*     */     else {
/*     */       
/* 156 */       double minval = Double.NaN;
/* 157 */       if (item.getMinOutlier() != null) {
/* 158 */         minval = item.getMinOutlier().doubleValue();
/*     */       }
/* 160 */       double maxval = Double.NaN;
/* 161 */       if (item.getMaxOutlier() != null) {
/* 162 */         maxval = item.getMaxOutlier().doubleValue();
/*     */       }
/*     */       
/* 165 */       if (Double.isNaN(this.maximumRangeValue)) {
/* 166 */         this.maximumRangeValue = maxval;
/* 167 */         this.maximumRangeValueRow = r;
/* 168 */         this.maximumRangeValueColumn = c;
/*     */       }
/* 170 */       else if (maxval > this.maximumRangeValue) {
/* 171 */         this.maximumRangeValue = maxval;
/* 172 */         this.maximumRangeValueRow = r;
/* 173 */         this.maximumRangeValueColumn = c;
/*     */       } 
/*     */       
/* 176 */       if (Double.isNaN(this.minimumRangeValue)) {
/* 177 */         this.minimumRangeValue = minval;
/* 178 */         this.minimumRangeValueRow = r;
/* 179 */         this.minimumRangeValueColumn = c;
/*     */       }
/* 181 */       else if (minval < this.minimumRangeValue) {
/* 182 */         this.minimumRangeValue = minval;
/* 183 */         this.minimumRangeValueRow = r;
/* 184 */         this.minimumRangeValueColumn = c;
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     fireDatasetChanged();
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
/*     */   public void remove(Comparable rowKey, Comparable columnKey) {
/* 205 */     int r = getRowIndex(rowKey);
/* 206 */     int c = getColumnIndex(columnKey);
/* 207 */     this.data.removeObject(rowKey, columnKey);
/*     */ 
/*     */ 
/*     */     
/* 211 */     if ((this.maximumRangeValueRow == r && this.maximumRangeValueColumn == c) || (this.minimumRangeValueRow == r && this.minimumRangeValueColumn == c))
/*     */     {
/*     */       
/* 214 */       updateBounds();
/*     */     }
/*     */     
/* 217 */     fireDatasetChanged();
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
/*     */   public void removeRow(int rowIndex) {
/* 231 */     this.data.removeRow(rowIndex);
/* 232 */     updateBounds();
/* 233 */     fireDatasetChanged();
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
/*     */   public void removeRow(Comparable rowKey) {
/* 247 */     this.data.removeRow(rowKey);
/* 248 */     updateBounds();
/* 249 */     fireDatasetChanged();
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
/*     */   public void removeColumn(int columnIndex) {
/* 263 */     this.data.removeColumn(columnIndex);
/* 264 */     updateBounds();
/* 265 */     fireDatasetChanged();
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
/*     */   public void removeColumn(Comparable columnKey) {
/* 279 */     this.data.removeColumn(columnKey);
/* 280 */     updateBounds();
/* 281 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 291 */     this.data.clear();
/* 292 */     updateBounds();
/* 293 */     fireDatasetChanged();
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
/*     */   public BoxAndWhiskerItem getItem(int row, int column) {
/* 305 */     return (BoxAndWhiskerItem)this.data.getObject(row, column);
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
/*     */   public Number getValue(int row, int column) {
/* 321 */     return getMedianValue(row, column);
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
/*     */   public Number getValue(Comparable rowKey, Comparable columnKey) {
/* 337 */     return getMedianValue(rowKey, columnKey);
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
/*     */   public Number getMeanValue(int row, int column) {
/* 353 */     Number result = null;
/* 354 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 356 */     if (item != null) {
/* 357 */       result = item.getMean();
/*     */     }
/* 359 */     return result;
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
/*     */   public Number getMeanValue(Comparable rowKey, Comparable columnKey) {
/* 375 */     Number result = null;
/* 376 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 378 */     if (item != null) {
/* 379 */       result = item.getMean();
/*     */     }
/* 381 */     return result;
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
/*     */   public Number getMedianValue(int row, int column) {
/* 396 */     Number result = null;
/* 397 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 399 */     if (item != null) {
/* 400 */       result = item.getMedian();
/*     */     }
/* 402 */     return result;
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
/*     */   public Number getMedianValue(Comparable rowKey, Comparable columnKey) {
/* 417 */     Number result = null;
/* 418 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 420 */     if (item != null) {
/* 421 */       result = item.getMedian();
/*     */     }
/* 423 */     return result;
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
/*     */   public Number getQ1Value(int row, int column) {
/* 438 */     Number result = null;
/* 439 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 441 */     if (item != null) {
/* 442 */       result = item.getQ1();
/*     */     }
/* 444 */     return result;
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
/*     */   public Number getQ1Value(Comparable rowKey, Comparable columnKey) {
/* 459 */     Number result = null;
/* 460 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 462 */     if (item != null) {
/* 463 */       result = item.getQ1();
/*     */     }
/* 465 */     return result;
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
/*     */   public Number getQ3Value(int row, int column) {
/* 480 */     Number result = null;
/* 481 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 483 */     if (item != null) {
/* 484 */       result = item.getQ3();
/*     */     }
/* 486 */     return result;
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
/*     */   public Number getQ3Value(Comparable rowKey, Comparable columnKey) {
/* 501 */     Number result = null;
/* 502 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 504 */     if (item != null) {
/* 505 */       result = item.getQ3();
/*     */     }
/* 507 */     return result;
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
/*     */   public int getColumnIndex(Comparable key) {
/* 521 */     return this.data.getColumnIndex(key);
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
/*     */   public Comparable getColumnKey(int column) {
/* 535 */     return this.data.getColumnKey(column);
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
/*     */   public List getColumnKeys() {
/* 547 */     return this.data.getColumnKeys();
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
/*     */   public int getRowIndex(Comparable key) {
/* 562 */     return this.data.getRowIndex(key);
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
/*     */   public Comparable getRowKey(int row) {
/* 576 */     return this.data.getRowKey(row);
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
/*     */   public List getRowKeys() {
/* 588 */     return this.data.getRowKeys();
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
/*     */   public int getRowCount() {
/* 600 */     return this.data.getRowCount();
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
/*     */   public int getColumnCount() {
/* 612 */     return this.data.getColumnCount();
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
/*     */   public double getRangeLowerBound(boolean includeInterval) {
/* 627 */     return this.minimumRangeValue;
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
/*     */   public double getRangeUpperBound(boolean includeInterval) {
/* 642 */     return this.maximumRangeValue;
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
/*     */   public Range getRangeBounds(boolean includeInterval) {
/* 655 */     return new Range(this.minimumRangeValue, this.maximumRangeValue);
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
/*     */   public Number getMinRegularValue(int row, int column) {
/* 670 */     Number result = null;
/* 671 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 673 */     if (item != null) {
/* 674 */       result = item.getMinRegularValue();
/*     */     }
/* 676 */     return result;
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
/*     */   public Number getMinRegularValue(Comparable rowKey, Comparable columnKey) {
/* 691 */     Number result = null;
/* 692 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 694 */     if (item != null) {
/* 695 */       result = item.getMinRegularValue();
/*     */     }
/* 697 */     return result;
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
/*     */   public Number getMaxRegularValue(int row, int column) {
/* 712 */     Number result = null;
/* 713 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 715 */     if (item != null) {
/* 716 */       result = item.getMaxRegularValue();
/*     */     }
/* 718 */     return result;
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
/*     */   public Number getMaxRegularValue(Comparable rowKey, Comparable columnKey) {
/* 733 */     Number result = null;
/* 734 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 736 */     if (item != null) {
/* 737 */       result = item.getMaxRegularValue();
/*     */     }
/* 739 */     return result;
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
/*     */   public Number getMinOutlier(int row, int column) {
/* 754 */     Number result = null;
/* 755 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 757 */     if (item != null) {
/* 758 */       result = item.getMinOutlier();
/*     */     }
/* 760 */     return result;
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
/*     */   public Number getMinOutlier(Comparable rowKey, Comparable columnKey) {
/* 775 */     Number result = null;
/* 776 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 778 */     if (item != null) {
/* 779 */       result = item.getMinOutlier();
/*     */     }
/* 781 */     return result;
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
/*     */   public Number getMaxOutlier(int row, int column) {
/* 796 */     Number result = null;
/* 797 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 799 */     if (item != null) {
/* 800 */       result = item.getMaxOutlier();
/*     */     }
/* 802 */     return result;
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
/*     */   public Number getMaxOutlier(Comparable rowKey, Comparable columnKey) {
/* 817 */     Number result = null;
/* 818 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 820 */     if (item != null) {
/* 821 */       result = item.getMaxOutlier();
/*     */     }
/* 823 */     return result;
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
/*     */   public List getOutliers(int row, int column) {
/* 838 */     List result = null;
/* 839 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(row, column);
/*     */     
/* 841 */     if (item != null) {
/* 842 */       result = item.getOutliers();
/*     */     }
/* 844 */     return result;
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
/*     */   public List getOutliers(Comparable rowKey, Comparable columnKey) {
/* 859 */     List result = null;
/* 860 */     BoxAndWhiskerItem item = (BoxAndWhiskerItem)this.data.getObject(rowKey, columnKey);
/*     */     
/* 862 */     if (item != null) {
/* 863 */       result = item.getOutliers();
/*     */     }
/* 865 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateBounds() {
/* 873 */     this.minimumRangeValue = Double.NaN;
/* 874 */     this.minimumRangeValueRow = -1;
/* 875 */     this.minimumRangeValueColumn = -1;
/* 876 */     this.maximumRangeValue = Double.NaN;
/* 877 */     this.maximumRangeValueRow = -1;
/* 878 */     this.maximumRangeValueColumn = -1;
/* 879 */     int rowCount = getRowCount();
/* 880 */     int columnCount = getColumnCount();
/* 881 */     for (int r = 0; r < rowCount; r++) {
/* 882 */       for (int c = 0; c < columnCount; c++) {
/* 883 */         BoxAndWhiskerItem item = getItem(r, c);
/* 884 */         if (item != null) {
/* 885 */           Number min = item.getMinOutlier();
/* 886 */           if (min != null) {
/* 887 */             double minv = min.doubleValue();
/* 888 */             if (!Double.isNaN(minv) && (
/* 889 */               minv < this.minimumRangeValue || Double.isNaN(this.minimumRangeValue))) {
/*     */               
/* 891 */               this.minimumRangeValue = minv;
/* 892 */               this.minimumRangeValueRow = r;
/* 893 */               this.minimumRangeValueColumn = c;
/*     */             } 
/*     */           } 
/*     */           
/* 897 */           Number max = item.getMaxOutlier();
/* 898 */           if (max != null) {
/* 899 */             double maxv = max.doubleValue();
/* 900 */             if (!Double.isNaN(maxv) && (
/* 901 */               maxv > this.maximumRangeValue || Double.isNaN(this.maximumRangeValue))) {
/*     */               
/* 903 */               this.maximumRangeValue = maxv;
/* 904 */               this.maximumRangeValueRow = r;
/* 905 */               this.maximumRangeValueColumn = c;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
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
/*     */   public boolean equals(Object obj) {
/* 923 */     if (obj == this) {
/* 924 */       return true;
/*     */     }
/* 926 */     if (obj instanceof DefaultBoxAndWhiskerCategoryDataset) {
/* 927 */       DefaultBoxAndWhiskerCategoryDataset dataset = (DefaultBoxAndWhiskerCategoryDataset)obj;
/*     */       
/* 929 */       return ObjectUtilities.equal(this.data, dataset.data);
/*     */     } 
/* 931 */     return false;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 944 */     DefaultBoxAndWhiskerCategoryDataset clone = (DefaultBoxAndWhiskerCategoryDataset)super.clone();
/* 945 */     clone.data = (KeyedObjects2D)this.data.clone();
/* 946 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/DefaultBoxAndWhiskerCategoryDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */