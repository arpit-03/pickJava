/*     */ package org.jfree.data.category;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.chart.util.ResourceBundleWrapper;
/*     */ import org.jfree.data.DataUtilities;
/*     */ import org.jfree.data.UnknownKeyException;
/*     */ import org.jfree.data.general.AbstractSeriesDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DefaultIntervalCategoryDataset
/*     */   extends AbstractSeriesDataset
/*     */   implements IntervalCategoryDataset
/*     */ {
/*     */   private Comparable[] seriesKeys;
/*     */   private Comparable[] categoryKeys;
/*     */   private Number[][] startData;
/*     */   private Number[][] endData;
/*     */   
/*     */   public DefaultIntervalCategoryDataset(double[][] starts, double[][] ends) {
/*  95 */     this(DataUtilities.createNumberArray2D(starts), 
/*  96 */         DataUtilities.createNumberArray2D(ends));
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
/*     */   public DefaultIntervalCategoryDataset(Number[][] starts, Number[][] ends) {
/* 111 */     this(null, null, starts, ends);
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
/*     */   public DefaultIntervalCategoryDataset(String[] seriesNames, Number[][] starts, Number[][] ends) {
/* 130 */     this((Comparable[])seriesNames, null, starts, ends);
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
/*     */   public DefaultIntervalCategoryDataset(Comparable[] seriesKeys, Comparable[] categoryKeys, Number[][] starts, Number[][] ends) {
/* 151 */     this.startData = starts;
/* 152 */     this.endData = ends;
/*     */     
/* 154 */     if (starts != null && ends != null) {
/*     */       
/* 156 */       String baseName = "org.jfree.data.resources.DataPackageResources";
/* 157 */       ResourceBundle resources = ResourceBundleWrapper.getBundle(baseName);
/*     */ 
/*     */       
/* 160 */       int seriesCount = starts.length;
/* 161 */       if (seriesCount != ends.length) {
/* 162 */         String errMsg = "DefaultIntervalCategoryDataset: the number of series in the start value dataset does not match the number of series in the end value dataset.";
/*     */ 
/*     */ 
/*     */         
/* 166 */         throw new IllegalArgumentException(errMsg);
/*     */       } 
/* 168 */       if (seriesCount > 0) {
/*     */ 
/*     */         
/* 171 */         if (seriesKeys != null) {
/*     */           
/* 173 */           if (seriesKeys.length != seriesCount) {
/* 174 */             throw new IllegalArgumentException("The number of series keys does not match the number of series in the data.");
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 179 */           this.seriesKeys = seriesKeys;
/*     */         } else {
/*     */           
/* 182 */           String prefix = resources.getString("series.default-prefix") + " ";
/*     */           
/* 184 */           this.seriesKeys = generateKeys(seriesCount, prefix);
/*     */         } 
/*     */ 
/*     */         
/* 188 */         int categoryCount = (starts[0]).length;
/* 189 */         if (categoryCount != (ends[0]).length) {
/* 190 */           String errMsg = "DefaultIntervalCategoryDataset: the number of categories in the start value dataset does not match the number of categories in the end value dataset.";
/*     */ 
/*     */ 
/*     */           
/* 194 */           throw new IllegalArgumentException(errMsg);
/*     */         } 
/* 196 */         if (categoryKeys != null) {
/* 197 */           if (categoryKeys.length != categoryCount) {
/* 198 */             throw new IllegalArgumentException("The number of category keys does not match the number of categories in the data.");
/*     */           }
/*     */ 
/*     */           
/* 202 */           this.categoryKeys = categoryKeys;
/*     */         } else {
/*     */           
/* 205 */           String prefix = resources.getString("categories.default-prefix") + " ";
/*     */           
/* 207 */           this.categoryKeys = generateKeys(categoryCount, prefix);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 212 */         this.seriesKeys = new Comparable[0];
/* 213 */         this.categoryKeys = new Comparable[0];
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
/*     */   
/*     */   public int getSeriesCount() {
/* 229 */     int result = 0;
/* 230 */     if (this.startData != null) {
/* 231 */       result = this.startData.length;
/*     */     }
/* 233 */     return result;
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
/*     */   public int getSeriesIndex(Comparable seriesKey) {
/* 247 */     int result = -1;
/* 248 */     for (int i = 0; i < this.seriesKeys.length; i++) {
/* 249 */       if (seriesKey.equals(this.seriesKeys[i])) {
/* 250 */         result = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 254 */     return result;
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
/*     */   public Comparable getSeriesKey(int series) {
/* 268 */     if (series >= getSeriesCount() || series < 0) {
/* 269 */       throw new IllegalArgumentException("No such series : " + series);
/*     */     }
/* 271 */     return this.seriesKeys[series];
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
/*     */   public void setSeriesKeys(Comparable[] seriesKeys) {
/* 284 */     ParamChecks.nullNotPermitted(seriesKeys, "seriesKeys");
/* 285 */     if (seriesKeys.length != getSeriesCount()) {
/* 286 */       throw new IllegalArgumentException("The number of series keys does not match the data.");
/*     */     }
/*     */     
/* 289 */     this.seriesKeys = seriesKeys;
/* 290 */     fireDatasetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCategoryCount() {
/* 301 */     int result = 0;
/* 302 */     if (this.startData != null && 
/* 303 */       getSeriesCount() > 0) {
/* 304 */       result = (this.startData[0]).length;
/*     */     }
/*     */     
/* 307 */     return result;
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
/*     */   public List getColumnKeys() {
/* 322 */     if (this.categoryKeys == null) {
/* 323 */       return new ArrayList();
/*     */     }
/*     */     
/* 326 */     return Collections.unmodifiableList(Arrays.asList((Object[])this.categoryKeys));
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
/*     */   public void setCategoryKeys(Comparable[] categoryKeys) {
/* 341 */     ParamChecks.nullNotPermitted(categoryKeys, "categoryKeys");
/* 342 */     if (categoryKeys.length != getCategoryCount()) {
/* 343 */       throw new IllegalArgumentException("The number of categories does not match the data.");
/*     */     }
/*     */     
/* 346 */     for (int i = 0; i < categoryKeys.length; i++) {
/* 347 */       if (categoryKeys[i] == null) {
/* 348 */         throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setCategoryKeys(): null category not permitted.");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 353 */     this.categoryKeys = categoryKeys;
/* 354 */     fireDatasetChanged();
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
/*     */   public Number getValue(Comparable series, Comparable category) {
/* 372 */     int seriesIndex = getSeriesIndex(series);
/* 373 */     if (seriesIndex < 0) {
/* 374 */       throw new UnknownKeyException("Unknown 'series' key.");
/*     */     }
/* 376 */     int itemIndex = getColumnIndex(category);
/* 377 */     if (itemIndex < 0) {
/* 378 */       throw new UnknownKeyException("Unknown 'category' key.");
/*     */     }
/* 380 */     return getValue(seriesIndex, itemIndex);
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
/*     */   public Number getValue(int series, int category) {
/* 398 */     return getEndValue(series, category);
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
/*     */   public Number getStartValue(Comparable series, Comparable category) {
/* 414 */     int seriesIndex = getSeriesIndex(series);
/* 415 */     if (seriesIndex < 0) {
/* 416 */       throw new UnknownKeyException("Unknown 'series' key.");
/*     */     }
/* 418 */     int itemIndex = getColumnIndex(category);
/* 419 */     if (itemIndex < 0) {
/* 420 */       throw new UnknownKeyException("Unknown 'category' key.");
/*     */     }
/* 422 */     return getStartValue(seriesIndex, itemIndex);
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
/*     */   public Number getStartValue(int series, int category) {
/* 440 */     if (series < 0 || series >= getSeriesCount()) {
/* 441 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): series index out of range.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 446 */     if (category < 0 || category >= getCategoryCount()) {
/* 447 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): category index out of range.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 453 */     return this.startData[series][category];
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
/*     */   public Number getEndValue(Comparable series, Comparable category) {
/* 469 */     int seriesIndex = getSeriesIndex(series);
/* 470 */     if (seriesIndex < 0) {
/* 471 */       throw new UnknownKeyException("Unknown 'series' key.");
/*     */     }
/* 473 */     int itemIndex = getColumnIndex(category);
/* 474 */     if (itemIndex < 0) {
/* 475 */       throw new UnknownKeyException("Unknown 'category' key.");
/*     */     }
/* 477 */     return getEndValue(seriesIndex, itemIndex);
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
/*     */   public Number getEndValue(int series, int category) {
/* 492 */     if (series < 0 || series >= getSeriesCount()) {
/* 493 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): series index out of range.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 498 */     if (category < 0 || category >= getCategoryCount()) {
/* 499 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.getValue(): category index out of range.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 504 */     return this.endData[series][category];
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
/*     */   public void setStartValue(int series, Comparable category, Number value) {
/* 520 */     if (series < 0 || series > getSeriesCount() - 1) {
/* 521 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: series outside valid range.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 527 */     int categoryIndex = getCategoryIndex(category);
/* 528 */     if (categoryIndex < 0) {
/* 529 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: unrecognised category.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 535 */     this.startData[series][categoryIndex] = value;
/* 536 */     fireDatasetChanged();
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
/*     */   public void setEndValue(int series, Comparable category, Number value) {
/* 553 */     if (series < 0 || series > getSeriesCount() - 1) {
/* 554 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: series outside valid range.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 560 */     int categoryIndex = getCategoryIndex(category);
/* 561 */     if (categoryIndex < 0) {
/* 562 */       throw new IllegalArgumentException("DefaultIntervalCategoryDataset.setValue: unrecognised category.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 568 */     this.endData[series][categoryIndex] = value;
/* 569 */     fireDatasetChanged();
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
/*     */   public int getCategoryIndex(Comparable category) {
/* 583 */     int result = -1;
/* 584 */     for (int i = 0; i < this.categoryKeys.length; i++) {
/* 585 */       if (category.equals(this.categoryKeys[i])) {
/* 586 */         result = i;
/*     */         break;
/*     */       } 
/*     */     } 
/* 590 */     return result;
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
/*     */   private Comparable[] generateKeys(int count, String prefix) {
/* 603 */     Comparable[] result = new Comparable[count];
/*     */     
/* 605 */     for (int i = 0; i < count; i++) {
/* 606 */       String name = prefix + (i + 1);
/* 607 */       result[i] = name;
/*     */     } 
/* 609 */     return result;
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
/* 623 */     return this.categoryKeys[column];
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
/*     */   public int getColumnIndex(Comparable columnKey) {
/* 637 */     ParamChecks.nullNotPermitted(columnKey, "columnKey");
/* 638 */     return getCategoryIndex(columnKey);
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
/*     */   public int getRowIndex(Comparable rowKey) {
/* 652 */     return getSeriesIndex(rowKey);
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
/*     */   public List getRowKeys() {
/* 667 */     if (this.seriesKeys == null) {
/* 668 */       return new ArrayList();
/*     */     }
/*     */     
/* 671 */     return Collections.unmodifiableList(Arrays.asList((Object[])this.seriesKeys));
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
/*     */   public Comparable getRowKey(int row) {
/* 686 */     if (row >= getRowCount() || row < 0) {
/* 687 */       throw new IllegalArgumentException("The 'row' argument is out of bounds.");
/*     */     }
/*     */     
/* 690 */     return this.seriesKeys[row];
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
/*     */   public int getColumnCount() {
/* 704 */     return this.categoryKeys.length;
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
/*     */   public int getRowCount() {
/* 717 */     return this.seriesKeys.length;
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
/* 729 */     if (obj == this) {
/* 730 */       return true;
/*     */     }
/* 732 */     if (!(obj instanceof DefaultIntervalCategoryDataset)) {
/* 733 */       return false;
/*     */     }
/* 735 */     DefaultIntervalCategoryDataset that = (DefaultIntervalCategoryDataset)obj;
/*     */     
/* 737 */     if (!Arrays.equals((Object[])this.seriesKeys, (Object[])that.seriesKeys)) {
/* 738 */       return false;
/*     */     }
/* 740 */     if (!Arrays.equals((Object[])this.categoryKeys, (Object[])that.categoryKeys)) {
/* 741 */       return false;
/*     */     }
/* 743 */     if (!equal(this.startData, that.startData)) {
/* 744 */       return false;
/*     */     }
/* 746 */     if (!equal(this.endData, that.endData)) {
/* 747 */       return false;
/*     */     }
/*     */     
/* 750 */     return true;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 764 */     DefaultIntervalCategoryDataset clone = (DefaultIntervalCategoryDataset)super.clone();
/* 765 */     clone.categoryKeys = (Comparable[])this.categoryKeys.clone();
/* 766 */     clone.seriesKeys = (Comparable[])this.seriesKeys.clone();
/* 767 */     clone.startData = clone(this.startData);
/* 768 */     clone.endData = clone(this.endData);
/* 769 */     return clone;
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
/*     */   private static boolean equal(Number[][] array1, Number[][] array2) {
/* 781 */     if (array1 == null) {
/* 782 */       return (array2 == null);
/*     */     }
/* 784 */     if (array2 == null) {
/* 785 */       return false;
/*     */     }
/* 787 */     if (array1.length != array2.length) {
/* 788 */       return false;
/*     */     }
/* 790 */     for (int i = 0; i < array1.length; i++) {
/* 791 */       if (!Arrays.equals((Object[])array1[i], (Object[])array2[i])) {
/* 792 */         return false;
/*     */       }
/*     */     } 
/* 795 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Number[][] clone(Number[][] array) {
/* 806 */     ParamChecks.nullNotPermitted(array, "array");
/* 807 */     Number[][] result = new Number[array.length][];
/* 808 */     for (int i = 0; i < array.length; i++) {
/* 809 */       Number[] child = array[i];
/* 810 */       Number[] copychild = new Number[child.length];
/* 811 */       System.arraycopy(child, 0, copychild, 0, child.length);
/* 812 */       result[i] = copychild;
/*     */     } 
/* 814 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getSeries() {
/* 825 */     if (this.seriesKeys == null) {
/* 826 */       return new ArrayList();
/*     */     }
/*     */     
/* 829 */     return Collections.unmodifiableList(Arrays.asList((Object[])this.seriesKeys));
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
/*     */   public List getCategories() {
/* 841 */     return getColumnKeys();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 852 */     return this.categoryKeys.length;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/category/DefaultIntervalCategoryDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */