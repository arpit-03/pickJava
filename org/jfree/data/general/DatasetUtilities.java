/*      */ package org.jfree.data.general;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.util.ParamChecks;
/*      */ import org.jfree.data.DomainInfo;
/*      */ import org.jfree.data.DomainOrder;
/*      */ import org.jfree.data.KeyToGroupMap;
/*      */ import org.jfree.data.KeyedValues;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.RangeInfo;
/*      */ import org.jfree.data.category.CategoryDataset;
/*      */ import org.jfree.data.category.CategoryRangeInfo;
/*      */ import org.jfree.data.category.DefaultCategoryDataset;
/*      */ import org.jfree.data.category.IntervalCategoryDataset;
/*      */ import org.jfree.data.function.Function2D;
/*      */ import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
/*      */ import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
/*      */ import org.jfree.data.statistics.MultiValueCategoryDataset;
/*      */ import org.jfree.data.statistics.StatisticalCategoryDataset;
/*      */ import org.jfree.data.xy.IntervalXYDataset;
/*      */ import org.jfree.data.xy.OHLCDataset;
/*      */ import org.jfree.data.xy.TableXYDataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.data.xy.XYDomainInfo;
/*      */ import org.jfree.data.xy.XYRangeInfo;
/*      */ import org.jfree.data.xy.XYSeries;
/*      */ import org.jfree.data.xy.XYSeriesCollection;
/*      */ import org.jfree.data.xy.XYZDataset;
/*      */ import org.jfree.util.ArrayUtilities;
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
/*      */ public final class DatasetUtilities
/*      */ {
/*      */   public static double calculatePieDatasetTotal(PieDataset dataset) {
/*  184 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*  185 */     List keys = dataset.getKeys();
/*  186 */     double totalValue = 0.0D;
/*  187 */     Iterator<Comparable> iterator = keys.iterator();
/*  188 */     while (iterator.hasNext()) {
/*  189 */       Comparable current = iterator.next();
/*  190 */       if (current != null) {
/*  191 */         Number value = dataset.getValue(current);
/*  192 */         double v = 0.0D;
/*  193 */         if (value != null) {
/*  194 */           v = value.doubleValue();
/*      */         }
/*  196 */         if (v > 0.0D) {
/*  197 */           totalValue += v;
/*      */         }
/*      */       } 
/*      */     } 
/*  201 */     return totalValue;
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
/*      */   public static PieDataset createPieDatasetForRow(CategoryDataset dataset, Comparable rowKey) {
/*  215 */     int row = dataset.getRowIndex(rowKey);
/*  216 */     return createPieDatasetForRow(dataset, row);
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
/*      */   public static PieDataset createPieDatasetForRow(CategoryDataset dataset, int row) {
/*  230 */     DefaultPieDataset result = new DefaultPieDataset();
/*  231 */     int columnCount = dataset.getColumnCount();
/*  232 */     for (int current = 0; current < columnCount; current++) {
/*  233 */       Comparable columnKey = dataset.getColumnKey(current);
/*  234 */       result.setValue(columnKey, dataset.getValue(row, current));
/*      */     } 
/*  236 */     return result;
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
/*      */   public static PieDataset createPieDatasetForColumn(CategoryDataset dataset, Comparable columnKey) {
/*  250 */     int column = dataset.getColumnIndex(columnKey);
/*  251 */     return createPieDatasetForColumn(dataset, column);
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
/*      */   public static PieDataset createPieDatasetForColumn(CategoryDataset dataset, int column) {
/*  265 */     DefaultPieDataset result = new DefaultPieDataset();
/*  266 */     int rowCount = dataset.getRowCount();
/*  267 */     for (int i = 0; i < rowCount; i++) {
/*  268 */       Comparable rowKey = dataset.getRowKey(i);
/*  269 */       result.setValue(rowKey, dataset.getValue(i, column));
/*      */     } 
/*  271 */     return result;
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
/*      */   public static PieDataset createConsolidatedPieDataset(PieDataset source, Comparable key, double minimumPercent) {
/*  289 */     return createConsolidatedPieDataset(source, key, minimumPercent, 2);
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static PieDataset createConsolidatedPieDataset(PieDataset source, Comparable key, double minimumPercent, int minItems) {
/*  311 */     DefaultPieDataset result = new DefaultPieDataset();
/*  312 */     double total = calculatePieDatasetTotal(source);
/*      */ 
/*      */     
/*  315 */     List<Comparable> keys = source.getKeys();
/*  316 */     ArrayList<Comparable> otherKeys = new ArrayList();
/*  317 */     Iterator<Comparable> iterator = keys.iterator();
/*  318 */     while (iterator.hasNext()) {
/*  319 */       Comparable currentKey = iterator.next();
/*  320 */       Number dataValue = source.getValue(currentKey);
/*  321 */       if (dataValue != null) {
/*  322 */         double value = dataValue.doubleValue();
/*  323 */         if (value / total < minimumPercent) {
/*  324 */           otherKeys.add(currentKey);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  330 */     iterator = keys.iterator();
/*  331 */     double otherValue = 0.0D;
/*  332 */     while (iterator.hasNext()) {
/*  333 */       Comparable currentKey = iterator.next();
/*  334 */       Number dataValue = source.getValue(currentKey);
/*  335 */       if (dataValue != null) {
/*  336 */         if (otherKeys.contains(currentKey) && otherKeys
/*  337 */           .size() >= minItems) {
/*      */           
/*  339 */           otherValue += dataValue.doubleValue();
/*      */           
/*      */           continue;
/*      */         } 
/*  343 */         result.setValue(currentKey, dataValue);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  348 */     if (otherKeys.size() >= minItems) {
/*  349 */       result.setValue(key, otherValue);
/*      */     }
/*  351 */     return result;
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
/*      */   
/*      */   public static CategoryDataset createCategoryDataset(String rowKeyPrefix, String columnKeyPrefix, double[][] data) {
/*  371 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  372 */     for (int r = 0; r < data.length; r++) {
/*  373 */       String rowKey = rowKeyPrefix + (r + 1);
/*  374 */       for (int c = 0; c < (data[r]).length; c++) {
/*  375 */         String columnKey = columnKeyPrefix + (c + 1);
/*  376 */         result.addValue(new Double(data[r][c]), rowKey, columnKey);
/*      */       } 
/*      */     } 
/*  379 */     return (CategoryDataset)result;
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
/*      */   
/*      */   public static CategoryDataset createCategoryDataset(String rowKeyPrefix, String columnKeyPrefix, Number[][] data) {
/*  399 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  400 */     for (int r = 0; r < data.length; r++) {
/*  401 */       String rowKey = rowKeyPrefix + (r + 1);
/*  402 */       for (int c = 0; c < (data[r]).length; c++) {
/*  403 */         String columnKey = columnKeyPrefix + (c + 1);
/*  404 */         result.addValue(data[r][c], rowKey, columnKey);
/*      */       } 
/*      */     } 
/*  407 */     return (CategoryDataset)result;
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
/*      */   
/*      */   public static CategoryDataset createCategoryDataset(Comparable[] rowKeys, Comparable[] columnKeys, double[][] data) {
/*  427 */     ParamChecks.nullNotPermitted(rowKeys, "rowKeys");
/*  428 */     ParamChecks.nullNotPermitted(columnKeys, "columnKeys");
/*  429 */     if (ArrayUtilities.hasDuplicateItems((Object[])rowKeys)) {
/*  430 */       throw new IllegalArgumentException("Duplicate items in 'rowKeys'.");
/*      */     }
/*  432 */     if (ArrayUtilities.hasDuplicateItems((Object[])columnKeys)) {
/*  433 */       throw new IllegalArgumentException("Duplicate items in 'columnKeys'.");
/*      */     }
/*      */     
/*  436 */     if (rowKeys.length != data.length) {
/*  437 */       throw new IllegalArgumentException("The number of row keys does not match the number of rows in the data array.");
/*      */     }
/*      */ 
/*      */     
/*  441 */     int columnCount = 0;
/*  442 */     for (int r = 0; r < data.length; r++) {
/*  443 */       columnCount = Math.max(columnCount, (data[r]).length);
/*      */     }
/*  445 */     if (columnKeys.length != columnCount) {
/*  446 */       throw new IllegalArgumentException("The number of column keys does not match the number of columns in the data array.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  452 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  453 */     for (int i = 0; i < data.length; i++) {
/*  454 */       Comparable rowKey = rowKeys[i];
/*  455 */       for (int c = 0; c < (data[i]).length; c++) {
/*  456 */         Comparable columnKey = columnKeys[c];
/*  457 */         result.addValue(new Double(data[i][c]), rowKey, columnKey);
/*      */       } 
/*      */     } 
/*  460 */     return (CategoryDataset)result;
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
/*      */   public static CategoryDataset createCategoryDataset(Comparable rowKey, KeyedValues rowData) {
/*  476 */     ParamChecks.nullNotPermitted(rowKey, "rowKey");
/*  477 */     ParamChecks.nullNotPermitted(rowData, "rowData");
/*  478 */     DefaultCategoryDataset result = new DefaultCategoryDataset();
/*  479 */     for (int i = 0; i < rowData.getItemCount(); i++) {
/*  480 */       result.addValue(rowData.getValue(i), rowKey, rowData.getKey(i));
/*      */     }
/*  482 */     return (CategoryDataset)result;
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
/*      */ 
/*      */   
/*      */   public static XYDataset sampleFunction2D(Function2D f, double start, double end, int samples, Comparable seriesKey) {
/*  503 */     XYSeries series = sampleFunction2DToSeries(f, start, end, samples, seriesKey);
/*      */     
/*  505 */     XYSeriesCollection collection = new XYSeriesCollection(series);
/*  506 */     return (XYDataset)collection;
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
/*      */ 
/*      */   
/*      */   public static XYSeries sampleFunction2DToSeries(Function2D f, double start, double end, int samples, Comparable seriesKey) {
/*  527 */     ParamChecks.nullNotPermitted(f, "f");
/*  528 */     ParamChecks.nullNotPermitted(seriesKey, "seriesKey");
/*  529 */     if (start >= end) {
/*  530 */       throw new IllegalArgumentException("Requires 'start' < 'end'.");
/*      */     }
/*  532 */     if (samples < 2) {
/*  533 */       throw new IllegalArgumentException("Requires 'samples' > 1");
/*      */     }
/*      */     
/*  536 */     XYSeries series = new XYSeries(seriesKey);
/*  537 */     double step = (end - start) / (samples - 1);
/*  538 */     for (int i = 0; i < samples; i++) {
/*  539 */       double x = start + step * i;
/*  540 */       series.add(x, f.getValue(x));
/*      */     } 
/*  542 */     return series;
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
/*      */   public static boolean isEmptyOrNull(PieDataset dataset) {
/*  555 */     if (dataset == null) {
/*  556 */       return true;
/*      */     }
/*      */     
/*  559 */     int itemCount = dataset.getItemCount();
/*  560 */     if (itemCount == 0) {
/*  561 */       return true;
/*      */     }
/*      */     
/*  564 */     for (int item = 0; item < itemCount; item++) {
/*  565 */       Number y = dataset.getValue(item);
/*  566 */       if (y != null) {
/*  567 */         double yy = y.doubleValue();
/*  568 */         if (yy > 0.0D) {
/*  569 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  574 */     return true;
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
/*      */   public static boolean isEmptyOrNull(CategoryDataset dataset) {
/*  588 */     if (dataset == null) {
/*  589 */       return true;
/*      */     }
/*      */     
/*  592 */     int rowCount = dataset.getRowCount();
/*  593 */     int columnCount = dataset.getColumnCount();
/*  594 */     if (rowCount == 0 || columnCount == 0) {
/*  595 */       return true;
/*      */     }
/*      */     
/*  598 */     for (int r = 0; r < rowCount; r++) {
/*  599 */       for (int c = 0; c < columnCount; c++) {
/*  600 */         if (dataset.getValue(r, c) != null) {
/*  601 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  607 */     return true;
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
/*      */   public static boolean isEmptyOrNull(XYDataset dataset) {
/*  620 */     if (dataset != null) {
/*  621 */       for (int s = 0; s < dataset.getSeriesCount(); s++) {
/*  622 */         if (dataset.getItemCount(s) > 0) {
/*  623 */           return false;
/*      */         }
/*      */       } 
/*      */     }
/*  627 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Range findDomainBounds(XYDataset dataset) {
/*  638 */     return findDomainBounds(dataset, true);
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
/*      */   public static Range findDomainBounds(XYDataset dataset, boolean includeInterval) {
/*      */     Range result;
/*  654 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */ 
/*      */ 
/*      */     
/*  658 */     if (dataset instanceof DomainInfo) {
/*  659 */       DomainInfo info = (DomainInfo)dataset;
/*  660 */       result = info.getDomainBounds(includeInterval);
/*      */     } else {
/*      */       
/*  663 */       result = iterateDomainBounds(dataset, includeInterval);
/*      */     } 
/*  665 */     return result;
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
/*      */ 
/*      */   
/*      */   public static Range findDomainBounds(XYDataset dataset, List visibleSeriesKeys, boolean includeInterval) {
/*      */     Range result;
/*  687 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */     
/*  689 */     if (dataset instanceof XYDomainInfo) {
/*  690 */       XYDomainInfo info = (XYDomainInfo)dataset;
/*  691 */       result = info.getDomainBounds(visibleSeriesKeys, includeInterval);
/*      */     } else {
/*      */       
/*  694 */       result = iterateToFindDomainBounds(dataset, visibleSeriesKeys, includeInterval);
/*      */     } 
/*      */     
/*  697 */     return result;
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
/*      */   public static Range iterateDomainBounds(XYDataset dataset) {
/*  711 */     return iterateDomainBounds(dataset, true);
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
/*      */   public static Range iterateDomainBounds(XYDataset dataset, boolean includeInterval) {
/*  727 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*  728 */     double minimum = Double.POSITIVE_INFINITY;
/*  729 */     double maximum = Double.NEGATIVE_INFINITY;
/*  730 */     int seriesCount = dataset.getSeriesCount();
/*      */     
/*  732 */     if (includeInterval && dataset instanceof IntervalXYDataset) {
/*  733 */       IntervalXYDataset intervalXYData = (IntervalXYDataset)dataset;
/*  734 */       for (int series = 0; series < seriesCount; series++) {
/*  735 */         int itemCount = dataset.getItemCount(series);
/*  736 */         for (int item = 0; item < itemCount; item++) {
/*  737 */           double value = intervalXYData.getXValue(series, item);
/*  738 */           double lvalue = intervalXYData.getStartXValue(series, item);
/*  739 */           double uvalue = intervalXYData.getEndXValue(series, item);
/*  740 */           if (!Double.isNaN(value)) {
/*  741 */             minimum = Math.min(minimum, value);
/*  742 */             maximum = Math.max(maximum, value);
/*      */           } 
/*  744 */           if (!Double.isNaN(lvalue)) {
/*  745 */             minimum = Math.min(minimum, lvalue);
/*  746 */             maximum = Math.max(maximum, lvalue);
/*      */           } 
/*  748 */           if (!Double.isNaN(uvalue)) {
/*  749 */             minimum = Math.min(minimum, uvalue);
/*  750 */             maximum = Math.max(maximum, uvalue);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  756 */       for (int series = 0; series < seriesCount; series++) {
/*  757 */         int itemCount = dataset.getItemCount(series);
/*  758 */         for (int item = 0; item < itemCount; item++) {
/*  759 */           double lvalue = dataset.getXValue(series, item);
/*  760 */           double uvalue = lvalue;
/*  761 */           if (!Double.isNaN(lvalue)) {
/*  762 */             minimum = Math.min(minimum, lvalue);
/*  763 */             maximum = Math.max(maximum, uvalue);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  768 */     if (minimum > maximum) {
/*  769 */       return null;
/*      */     }
/*      */     
/*  772 */     return new Range(minimum, maximum);
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
/*      */   public static Range findRangeBounds(CategoryDataset dataset) {
/*  784 */     return findRangeBounds(dataset, true);
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
/*      */   public static Range findRangeBounds(CategoryDataset dataset, boolean includeInterval) {
/*      */     Range result;
/*  798 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */     
/*  800 */     if (dataset instanceof RangeInfo) {
/*  801 */       RangeInfo info = (RangeInfo)dataset;
/*  802 */       result = info.getRangeBounds(includeInterval);
/*      */     } else {
/*      */       
/*  805 */       result = iterateRangeBounds(dataset, includeInterval);
/*      */     } 
/*  807 */     return result;
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
/*      */   public static Range findRangeBounds(CategoryDataset dataset, List visibleSeriesKeys, boolean includeInterval) {
/*      */     Range result;
/*  826 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */     
/*  828 */     if (dataset instanceof CategoryRangeInfo) {
/*  829 */       CategoryRangeInfo info = (CategoryRangeInfo)dataset;
/*  830 */       result = info.getRangeBounds(visibleSeriesKeys, includeInterval);
/*      */     } else {
/*      */       
/*  833 */       result = iterateToFindRangeBounds(dataset, visibleSeriesKeys, includeInterval);
/*      */     } 
/*      */     
/*  836 */     return result;
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
/*      */   public static Range findRangeBounds(XYDataset dataset) {
/*  848 */     return findRangeBounds(dataset, true);
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
/*      */   public static Range findRangeBounds(XYDataset dataset, boolean includeInterval) {
/*      */     Range result;
/*  864 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */     
/*  866 */     if (dataset instanceof RangeInfo) {
/*  867 */       RangeInfo info = (RangeInfo)dataset;
/*  868 */       result = info.getRangeBounds(includeInterval);
/*      */     } else {
/*      */       
/*  871 */       result = iterateRangeBounds(dataset, includeInterval);
/*      */     } 
/*  873 */     return result;
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
/*      */   
/*      */   public static Range findRangeBounds(XYDataset dataset, List visibleSeriesKeys, Range xRange, boolean includeInterval) {
/*      */     Range result;
/*  894 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */     
/*  896 */     if (dataset instanceof XYRangeInfo) {
/*  897 */       XYRangeInfo info = (XYRangeInfo)dataset;
/*  898 */       result = info.getRangeBounds(visibleSeriesKeys, xRange, includeInterval);
/*      */     }
/*      */     else {
/*      */       
/*  902 */       result = iterateToFindRangeBounds(dataset, visibleSeriesKeys, xRange, includeInterval);
/*      */     } 
/*      */     
/*  905 */     return result;
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
/*      */   public static Range iterateCategoryRangeBounds(CategoryDataset dataset, boolean includeInterval) {
/*  923 */     return iterateRangeBounds(dataset, includeInterval);
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
/*      */   public static Range iterateRangeBounds(CategoryDataset dataset) {
/*  937 */     return iterateRangeBounds(dataset, true);
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
/*      */   public static Range iterateRangeBounds(CategoryDataset dataset, boolean includeInterval) {
/*  954 */     double minimum = Double.POSITIVE_INFINITY;
/*  955 */     double maximum = Double.NEGATIVE_INFINITY;
/*  956 */     int rowCount = dataset.getRowCount();
/*  957 */     int columnCount = dataset.getColumnCount();
/*  958 */     if (includeInterval && dataset instanceof IntervalCategoryDataset) {
/*      */ 
/*      */       
/*  961 */       IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
/*      */       
/*  963 */       for (int row = 0; row < rowCount; row++) {
/*  964 */         for (int column = 0; column < columnCount; column++) {
/*  965 */           Number value = icd.getValue(row, column);
/*      */           double v;
/*  967 */           if (value != null && 
/*  968 */             !Double.isNaN(v = value.doubleValue())) {
/*  969 */             minimum = Math.min(v, minimum);
/*  970 */             maximum = Math.max(v, maximum);
/*      */           } 
/*  972 */           Number lvalue = icd.getStartValue(row, column);
/*  973 */           if (lvalue != null && 
/*  974 */             !Double.isNaN(v = lvalue.doubleValue())) {
/*  975 */             minimum = Math.min(v, minimum);
/*  976 */             maximum = Math.max(v, maximum);
/*      */           } 
/*  978 */           Number uvalue = icd.getEndValue(row, column);
/*  979 */           if (uvalue != null && 
/*  980 */             !Double.isNaN(v = uvalue.doubleValue())) {
/*  981 */             minimum = Math.min(v, minimum);
/*  982 */             maximum = Math.max(v, maximum);
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  989 */       for (int row = 0; row < rowCount; row++) {
/*  990 */         for (int column = 0; column < columnCount; column++) {
/*  991 */           Number value = dataset.getValue(row, column);
/*  992 */           if (value != null) {
/*  993 */             double v = value.doubleValue();
/*  994 */             if (!Double.isNaN(v)) {
/*  995 */               minimum = Math.min(minimum, v);
/*  996 */               maximum = Math.max(maximum, v);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1002 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1003 */       return null;
/*      */     }
/*      */     
/* 1006 */     return new Range(minimum, maximum);
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
/*      */   
/*      */   public static Range iterateToFindRangeBounds(CategoryDataset dataset, List visibleSeriesKeys, boolean includeInterval) {
/* 1026 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1027 */     ParamChecks.nullNotPermitted(visibleSeriesKeys, "visibleSeriesKeys");
/*      */     
/* 1029 */     double minimum = Double.POSITIVE_INFINITY;
/* 1030 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1031 */     int columnCount = dataset.getColumnCount();
/* 1032 */     if (includeInterval && dataset instanceof BoxAndWhiskerCategoryDataset) {
/*      */ 
/*      */       
/* 1035 */       BoxAndWhiskerCategoryDataset bx = (BoxAndWhiskerCategoryDataset)dataset;
/*      */       
/* 1037 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1038 */       while (iterator.hasNext()) {
/* 1039 */         Comparable seriesKey = iterator.next();
/* 1040 */         int series = dataset.getRowIndex(seriesKey);
/* 1041 */         int itemCount = dataset.getColumnCount();
/* 1042 */         for (int item = 0; item < itemCount; item++) {
/* 1043 */           Number lvalue = bx.getMinRegularValue(series, item);
/* 1044 */           if (lvalue == null) {
/* 1045 */             lvalue = bx.getValue(series, item);
/*      */           }
/* 1047 */           Number uvalue = bx.getMaxRegularValue(series, item);
/* 1048 */           if (uvalue == null) {
/* 1049 */             uvalue = bx.getValue(series, item);
/*      */           }
/* 1051 */           if (lvalue != null) {
/* 1052 */             minimum = Math.min(minimum, lvalue.doubleValue());
/*      */           }
/* 1054 */           if (uvalue != null) {
/* 1055 */             maximum = Math.max(maximum, uvalue.doubleValue());
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/* 1060 */     } else if (includeInterval && dataset instanceof IntervalCategoryDataset) {
/*      */ 
/*      */ 
/*      */       
/* 1064 */       IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
/*      */       
/* 1066 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1067 */       while (iterator.hasNext()) {
/* 1068 */         Comparable seriesKey = iterator.next();
/* 1069 */         int series = dataset.getRowIndex(seriesKey);
/* 1070 */         for (int column = 0; column < columnCount; column++) {
/* 1071 */           Number lvalue = icd.getStartValue(series, column);
/* 1072 */           Number uvalue = icd.getEndValue(series, column);
/* 1073 */           if (lvalue != null && !Double.isNaN(lvalue.doubleValue())) {
/* 1074 */             minimum = Math.min(minimum, lvalue.doubleValue());
/*      */           }
/* 1076 */           if (uvalue != null && !Double.isNaN(uvalue.doubleValue())) {
/* 1077 */             maximum = Math.max(maximum, uvalue.doubleValue());
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/* 1082 */     } else if (includeInterval && dataset instanceof MultiValueCategoryDataset) {
/*      */ 
/*      */ 
/*      */       
/* 1086 */       MultiValueCategoryDataset mvcd = (MultiValueCategoryDataset)dataset;
/*      */       
/* 1088 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1089 */       while (iterator.hasNext()) {
/* 1090 */         Comparable seriesKey = iterator.next();
/* 1091 */         int series = dataset.getRowIndex(seriesKey);
/* 1092 */         for (int column = 0; column < columnCount; column++) {
/* 1093 */           List values = mvcd.getValues(series, column);
/* 1094 */           Iterator valueIterator = values.iterator();
/* 1095 */           while (valueIterator.hasNext()) {
/* 1096 */             Object o = valueIterator.next();
/* 1097 */             if (o instanceof Number) {
/* 1098 */               double v = ((Number)o).doubleValue();
/* 1099 */               if (!Double.isNaN(v)) {
/* 1100 */                 minimum = Math.min(minimum, v);
/* 1101 */                 maximum = Math.max(maximum, v);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1108 */     } else if (includeInterval && dataset instanceof StatisticalCategoryDataset) {
/*      */ 
/*      */ 
/*      */       
/* 1112 */       StatisticalCategoryDataset scd = (StatisticalCategoryDataset)dataset;
/*      */       
/* 1114 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1115 */       while (iterator.hasNext()) {
/* 1116 */         Comparable seriesKey = iterator.next();
/* 1117 */         int series = dataset.getRowIndex(seriesKey);
/* 1118 */         for (int column = 0; column < columnCount; column++) {
/* 1119 */           Number meanN = scd.getMeanValue(series, column);
/* 1120 */           if (meanN != null) {
/* 1121 */             double std = 0.0D;
/* 1122 */             Number stdN = scd.getStdDevValue(series, column);
/* 1123 */             if (stdN != null) {
/* 1124 */               std = stdN.doubleValue();
/* 1125 */               if (Double.isNaN(std)) {
/* 1126 */                 std = 0.0D;
/*      */               }
/*      */             } 
/* 1129 */             double mean = meanN.doubleValue();
/* 1130 */             if (!Double.isNaN(mean)) {
/* 1131 */               minimum = Math.min(minimum, mean - std);
/* 1132 */               maximum = Math.max(maximum, mean + std);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1140 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1141 */       while (iterator.hasNext()) {
/* 1142 */         Comparable seriesKey = iterator.next();
/* 1143 */         int series = dataset.getRowIndex(seriesKey);
/* 1144 */         for (int column = 0; column < columnCount; column++) {
/* 1145 */           Number value = dataset.getValue(series, column);
/* 1146 */           if (value != null) {
/* 1147 */             double v = value.doubleValue();
/* 1148 */             if (!Double.isNaN(v)) {
/* 1149 */               minimum = Math.min(minimum, v);
/* 1150 */               maximum = Math.max(maximum, v);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1156 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1157 */       return null;
/*      */     }
/*      */     
/* 1160 */     return new Range(minimum, maximum);
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
/*      */   public static Range iterateXYRangeBounds(XYDataset dataset) {
/* 1175 */     return iterateRangeBounds(dataset);
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
/*      */   public static Range iterateRangeBounds(XYDataset dataset) {
/* 1189 */     return iterateRangeBounds(dataset, true);
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
/*      */   public static Range iterateRangeBounds(XYDataset dataset, boolean includeInterval) {
/* 1207 */     double minimum = Double.POSITIVE_INFINITY;
/* 1208 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1209 */     int seriesCount = dataset.getSeriesCount();
/*      */ 
/*      */     
/* 1212 */     if (includeInterval && dataset instanceof IntervalXYDataset) {
/*      */       
/* 1214 */       IntervalXYDataset ixyd = (IntervalXYDataset)dataset;
/* 1215 */       for (int series = 0; series < seriesCount; series++) {
/* 1216 */         int itemCount = dataset.getItemCount(series);
/* 1217 */         for (int item = 0; item < itemCount; item++) {
/* 1218 */           double value = ixyd.getYValue(series, item);
/* 1219 */           double lvalue = ixyd.getStartYValue(series, item);
/* 1220 */           double uvalue = ixyd.getEndYValue(series, item);
/* 1221 */           if (!Double.isNaN(value)) {
/* 1222 */             minimum = Math.min(minimum, value);
/* 1223 */             maximum = Math.max(maximum, value);
/*      */           } 
/* 1225 */           if (!Double.isNaN(lvalue)) {
/* 1226 */             minimum = Math.min(minimum, lvalue);
/* 1227 */             maximum = Math.max(maximum, lvalue);
/*      */           } 
/* 1229 */           if (!Double.isNaN(uvalue)) {
/* 1230 */             minimum = Math.min(minimum, uvalue);
/* 1231 */             maximum = Math.max(maximum, uvalue);
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1236 */     } else if (includeInterval && dataset instanceof OHLCDataset) {
/*      */       
/* 1238 */       OHLCDataset ohlc = (OHLCDataset)dataset;
/* 1239 */       for (int series = 0; series < seriesCount; series++) {
/* 1240 */         int itemCount = dataset.getItemCount(series);
/* 1241 */         for (int item = 0; item < itemCount; item++) {
/* 1242 */           double lvalue = ohlc.getLowValue(series, item);
/* 1243 */           double uvalue = ohlc.getHighValue(series, item);
/* 1244 */           if (!Double.isNaN(lvalue)) {
/* 1245 */             minimum = Math.min(minimum, lvalue);
/*      */           }
/* 1247 */           if (!Double.isNaN(uvalue)) {
/* 1248 */             maximum = Math.max(maximum, uvalue);
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1255 */       for (int series = 0; series < seriesCount; series++) {
/* 1256 */         int itemCount = dataset.getItemCount(series);
/* 1257 */         for (int item = 0; item < itemCount; item++) {
/* 1258 */           double value = dataset.getYValue(series, item);
/* 1259 */           if (!Double.isNaN(value)) {
/* 1260 */             minimum = Math.min(minimum, value);
/* 1261 */             maximum = Math.max(maximum, value);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 1266 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1267 */       return null;
/*      */     }
/*      */     
/* 1270 */     return new Range(minimum, maximum);
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
/*      */   public static Range findZBounds(XYZDataset dataset) {
/* 1284 */     return findZBounds(dataset, true);
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
/*      */   public static Range findZBounds(XYZDataset dataset, boolean includeInterval) {
/* 1301 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1302 */     Range result = iterateZBounds(dataset, includeInterval);
/* 1303 */     return result;
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
/*      */   public static Range findZBounds(XYZDataset dataset, List visibleSeriesKeys, Range xRange, boolean includeInterval) {
/* 1322 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1323 */     Range result = iterateToFindZBounds(dataset, visibleSeriesKeys, xRange, includeInterval);
/*      */     
/* 1325 */     return result;
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
/*      */   public static Range iterateZBounds(XYZDataset dataset) {
/* 1337 */     return iterateZBounds(dataset, true);
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
/*      */   public static Range iterateZBounds(XYZDataset dataset, boolean includeInterval) {
/* 1352 */     double minimum = Double.POSITIVE_INFINITY;
/* 1353 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1354 */     int seriesCount = dataset.getSeriesCount();
/*      */     
/* 1356 */     for (int series = 0; series < seriesCount; series++) {
/* 1357 */       int itemCount = dataset.getItemCount(series);
/* 1358 */       for (int item = 0; item < itemCount; item++) {
/* 1359 */         double value = dataset.getZValue(series, item);
/* 1360 */         if (!Double.isNaN(value)) {
/* 1361 */           minimum = Math.min(minimum, value);
/* 1362 */           maximum = Math.max(maximum, value);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1367 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1368 */       return null;
/*      */     }
/*      */     
/* 1371 */     return new Range(minimum, maximum);
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
/*      */ 
/*      */   
/*      */   public static Range iterateToFindDomainBounds(XYDataset dataset, List visibleSeriesKeys, boolean includeInterval) {
/* 1392 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1393 */     ParamChecks.nullNotPermitted(visibleSeriesKeys, "visibleSeriesKeys");
/*      */     
/* 1395 */     double minimum = Double.POSITIVE_INFINITY;
/* 1396 */     double maximum = Double.NEGATIVE_INFINITY;
/*      */     
/* 1398 */     if (includeInterval && dataset instanceof IntervalXYDataset) {
/*      */       
/* 1400 */       IntervalXYDataset ixyd = (IntervalXYDataset)dataset;
/* 1401 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1402 */       while (iterator.hasNext()) {
/* 1403 */         Comparable seriesKey = iterator.next();
/* 1404 */         int series = dataset.indexOf(seriesKey);
/* 1405 */         int itemCount = dataset.getItemCount(series);
/* 1406 */         for (int item = 0; item < itemCount; item++) {
/* 1407 */           double lvalue = ixyd.getStartXValue(series, item);
/* 1408 */           double uvalue = ixyd.getEndXValue(series, item);
/* 1409 */           if (!Double.isNaN(lvalue)) {
/* 1410 */             minimum = Math.min(minimum, lvalue);
/*      */           }
/* 1412 */           if (!Double.isNaN(uvalue)) {
/* 1413 */             maximum = Math.max(maximum, uvalue);
/*      */           }
/*      */         }
/*      */       
/*      */       } 
/*      */     } else {
/*      */       
/* 1420 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1421 */       while (iterator.hasNext()) {
/* 1422 */         Comparable seriesKey = iterator.next();
/* 1423 */         int series = dataset.indexOf(seriesKey);
/* 1424 */         int itemCount = dataset.getItemCount(series);
/* 1425 */         for (int item = 0; item < itemCount; item++) {
/* 1426 */           double x = dataset.getXValue(series, item);
/* 1427 */           if (!Double.isNaN(x)) {
/* 1428 */             minimum = Math.min(minimum, x);
/* 1429 */             maximum = Math.max(maximum, x);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1435 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1436 */       return null;
/*      */     }
/*      */     
/* 1439 */     return new Range(minimum, maximum);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Range iterateToFindRangeBounds(XYDataset dataset, List visibleSeriesKeys, Range xRange, boolean includeInterval) {
/* 1463 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1464 */     ParamChecks.nullNotPermitted(visibleSeriesKeys, "visibleSeriesKeys");
/* 1465 */     ParamChecks.nullNotPermitted(xRange, "xRange");
/*      */     
/* 1467 */     double minimum = Double.POSITIVE_INFINITY;
/* 1468 */     double maximum = Double.NEGATIVE_INFINITY;
/*      */ 
/*      */     
/* 1471 */     if (includeInterval && dataset instanceof OHLCDataset) {
/*      */       
/* 1473 */       OHLCDataset ohlc = (OHLCDataset)dataset;
/* 1474 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1475 */       while (iterator.hasNext()) {
/* 1476 */         Comparable seriesKey = iterator.next();
/* 1477 */         int series = dataset.indexOf(seriesKey);
/* 1478 */         int itemCount = dataset.getItemCount(series);
/* 1479 */         for (int item = 0; item < itemCount; item++) {
/* 1480 */           double x = ohlc.getXValue(series, item);
/* 1481 */           if (xRange.contains(x)) {
/* 1482 */             double lvalue = ohlc.getLowValue(series, item);
/* 1483 */             double uvalue = ohlc.getHighValue(series, item);
/* 1484 */             if (!Double.isNaN(lvalue)) {
/* 1485 */               minimum = Math.min(minimum, lvalue);
/*      */             }
/* 1487 */             if (!Double.isNaN(uvalue)) {
/* 1488 */               maximum = Math.max(maximum, uvalue);
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1494 */     } else if (includeInterval && dataset instanceof BoxAndWhiskerXYDataset) {
/*      */       
/* 1496 */       BoxAndWhiskerXYDataset bx = (BoxAndWhiskerXYDataset)dataset;
/* 1497 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1498 */       while (iterator.hasNext()) {
/* 1499 */         Comparable seriesKey = iterator.next();
/* 1500 */         int series = dataset.indexOf(seriesKey);
/* 1501 */         int itemCount = dataset.getItemCount(series);
/* 1502 */         for (int item = 0; item < itemCount; item++) {
/* 1503 */           double x = bx.getXValue(series, item);
/* 1504 */           if (xRange.contains(x)) {
/* 1505 */             Number lvalue = bx.getMinRegularValue(series, item);
/* 1506 */             Number uvalue = bx.getMaxRegularValue(series, item);
/* 1507 */             if (lvalue != null) {
/* 1508 */               minimum = Math.min(minimum, lvalue.doubleValue());
/*      */             }
/* 1510 */             if (uvalue != null) {
/* 1511 */               maximum = Math.max(maximum, uvalue.doubleValue());
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1517 */     } else if (includeInterval && dataset instanceof IntervalXYDataset) {
/*      */       
/* 1519 */       IntervalXYDataset ixyd = (IntervalXYDataset)dataset;
/* 1520 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1521 */       while (iterator.hasNext()) {
/* 1522 */         Comparable seriesKey = iterator.next();
/* 1523 */         int series = dataset.indexOf(seriesKey);
/* 1524 */         int itemCount = dataset.getItemCount(series);
/* 1525 */         for (int item = 0; item < itemCount; item++) {
/* 1526 */           double x = ixyd.getXValue(series, item);
/* 1527 */           if (xRange.contains(x)) {
/* 1528 */             double lvalue = ixyd.getStartYValue(series, item);
/* 1529 */             double uvalue = ixyd.getEndYValue(series, item);
/* 1530 */             if (!Double.isNaN(lvalue)) {
/* 1531 */               minimum = Math.min(minimum, lvalue);
/*      */             }
/* 1533 */             if (!Double.isNaN(uvalue)) {
/* 1534 */               maximum = Math.max(maximum, uvalue);
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1542 */       Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1543 */       while (iterator.hasNext()) {
/* 1544 */         Comparable seriesKey = iterator.next();
/* 1545 */         int series = dataset.indexOf(seriesKey);
/* 1546 */         int itemCount = dataset.getItemCount(series);
/* 1547 */         for (int item = 0; item < itemCount; item++) {
/* 1548 */           double x = dataset.getXValue(series, item);
/* 1549 */           double y = dataset.getYValue(series, item);
/* 1550 */           if (xRange.contains(x) && 
/* 1551 */             !Double.isNaN(y)) {
/* 1552 */             minimum = Math.min(minimum, y);
/* 1553 */             maximum = Math.max(maximum, y);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1559 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1560 */       return null;
/*      */     }
/*      */     
/* 1563 */     return new Range(minimum, maximum);
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
/*      */ 
/*      */   
/*      */   public static Range iterateToFindZBounds(XYZDataset dataset, List visibleSeriesKeys, Range xRange, boolean includeInterval) {
/* 1584 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1585 */     ParamChecks.nullNotPermitted(visibleSeriesKeys, "visibleSeriesKeys");
/* 1586 */     ParamChecks.nullNotPermitted(xRange, "xRange");
/*      */     
/* 1588 */     double minimum = Double.POSITIVE_INFINITY;
/* 1589 */     double maximum = Double.NEGATIVE_INFINITY;
/*      */     
/* 1591 */     Iterator<Comparable> iterator = visibleSeriesKeys.iterator();
/* 1592 */     while (iterator.hasNext()) {
/* 1593 */       Comparable seriesKey = iterator.next();
/* 1594 */       int series = dataset.indexOf(seriesKey);
/* 1595 */       int itemCount = dataset.getItemCount(series);
/* 1596 */       for (int item = 0; item < itemCount; item++) {
/* 1597 */         double x = dataset.getXValue(series, item);
/* 1598 */         double z = dataset.getZValue(series, item);
/* 1599 */         if (xRange.contains(x) && 
/* 1600 */           !Double.isNaN(z)) {
/* 1601 */           minimum = Math.min(minimum, z);
/* 1602 */           maximum = Math.max(maximum, z);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1608 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1609 */       return null;
/*      */     }
/*      */     
/* 1612 */     return new Range(minimum, maximum);
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
/*      */   public static Number findMinimumDomainValue(XYDataset dataset) {
/*      */     Number result;
/* 1630 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */ 
/*      */     
/* 1633 */     if (dataset instanceof DomainInfo) {
/* 1634 */       DomainInfo info = (DomainInfo)dataset;
/* 1635 */       return new Double(info.getDomainLowerBound(true));
/*      */     } 
/*      */     
/* 1638 */     double minimum = Double.POSITIVE_INFINITY;
/* 1639 */     int seriesCount = dataset.getSeriesCount();
/* 1640 */     for (int series = 0; series < seriesCount; series++) {
/* 1641 */       int itemCount = dataset.getItemCount(series);
/* 1642 */       for (int item = 0; item < itemCount; item++) {
/*      */         double value;
/*      */         
/* 1645 */         if (dataset instanceof IntervalXYDataset) {
/* 1646 */           IntervalXYDataset intervalXYData = (IntervalXYDataset)dataset;
/*      */           
/* 1648 */           value = intervalXYData.getStartXValue(series, item);
/*      */         } else {
/*      */           
/* 1651 */           value = dataset.getXValue(series, item);
/*      */         } 
/* 1653 */         if (!Double.isNaN(value)) {
/* 1654 */           minimum = Math.min(minimum, value);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1659 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1660 */       result = null;
/*      */     } else {
/*      */       
/* 1663 */       result = new Double(minimum);
/*      */     } 
/*      */ 
/*      */     
/* 1667 */     return result;
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
/*      */   public static Number findMaximumDomainValue(XYDataset dataset) {
/*      */     Number result;
/* 1683 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */ 
/*      */     
/* 1686 */     if (dataset instanceof DomainInfo) {
/* 1687 */       DomainInfo info = (DomainInfo)dataset;
/* 1688 */       return new Double(info.getDomainUpperBound(true));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1693 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1694 */     int seriesCount = dataset.getSeriesCount();
/* 1695 */     for (int series = 0; series < seriesCount; series++) {
/* 1696 */       int itemCount = dataset.getItemCount(series);
/* 1697 */       for (int item = 0; item < itemCount; item++) {
/*      */         double value;
/*      */         
/* 1700 */         if (dataset instanceof IntervalXYDataset) {
/* 1701 */           IntervalXYDataset intervalXYData = (IntervalXYDataset)dataset;
/*      */           
/* 1703 */           value = intervalXYData.getEndXValue(series, item);
/*      */         } else {
/*      */           
/* 1706 */           value = dataset.getXValue(series, item);
/*      */         } 
/* 1708 */         if (!Double.isNaN(value)) {
/* 1709 */           maximum = Math.max(maximum, value);
/*      */         }
/*      */       } 
/*      */     } 
/* 1713 */     if (maximum == Double.NEGATIVE_INFINITY) {
/* 1714 */       result = null;
/*      */     } else {
/*      */       
/* 1717 */       result = new Double(maximum);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1722 */     return result;
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
/*      */   public static Number findMinimumRangeValue(CategoryDataset dataset) {
/* 1738 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1739 */     if (dataset instanceof RangeInfo) {
/* 1740 */       RangeInfo info = (RangeInfo)dataset;
/* 1741 */       return new Double(info.getRangeLowerBound(true));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1746 */     double minimum = Double.POSITIVE_INFINITY;
/* 1747 */     int seriesCount = dataset.getRowCount();
/* 1748 */     int itemCount = dataset.getColumnCount();
/* 1749 */     for (int series = 0; series < seriesCount; series++) {
/* 1750 */       for (int item = 0; item < itemCount; item++) {
/*      */         Number value;
/* 1752 */         if (dataset instanceof IntervalCategoryDataset) {
/* 1753 */           IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
/*      */           
/* 1755 */           value = icd.getStartValue(series, item);
/*      */         } else {
/*      */           
/* 1758 */           value = dataset.getValue(series, item);
/*      */         } 
/* 1760 */         if (value != null) {
/* 1761 */           minimum = Math.min(minimum, value.doubleValue());
/*      */         }
/*      */       } 
/*      */     } 
/* 1765 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1766 */       return null;
/*      */     }
/*      */     
/* 1769 */     return new Double(minimum);
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
/*      */   
/*      */   public static Number findMinimumRangeValue(XYDataset dataset) {
/* 1789 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */ 
/*      */     
/* 1792 */     if (dataset instanceof RangeInfo) {
/* 1793 */       RangeInfo info = (RangeInfo)dataset;
/* 1794 */       return new Double(info.getRangeLowerBound(true));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1799 */     double minimum = Double.POSITIVE_INFINITY;
/* 1800 */     int seriesCount = dataset.getSeriesCount();
/* 1801 */     for (int series = 0; series < seriesCount; series++) {
/* 1802 */       int itemCount = dataset.getItemCount(series);
/* 1803 */       for (int item = 0; item < itemCount; item++) {
/*      */         double value;
/*      */         
/* 1806 */         if (dataset instanceof IntervalXYDataset) {
/* 1807 */           IntervalXYDataset intervalXYData = (IntervalXYDataset)dataset;
/*      */           
/* 1809 */           value = intervalXYData.getStartYValue(series, item);
/*      */         }
/* 1811 */         else if (dataset instanceof OHLCDataset) {
/* 1812 */           OHLCDataset highLowData = (OHLCDataset)dataset;
/* 1813 */           value = highLowData.getLowValue(series, item);
/*      */         } else {
/*      */           
/* 1816 */           value = dataset.getYValue(series, item);
/*      */         } 
/* 1818 */         if (!Double.isNaN(value)) {
/* 1819 */           minimum = Math.min(minimum, value);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1824 */     if (minimum == Double.POSITIVE_INFINITY) {
/* 1825 */       return null;
/*      */     }
/*      */     
/* 1828 */     return new Double(minimum);
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
/*      */   
/*      */   public static Number findMaximumRangeValue(CategoryDataset dataset) {
/* 1848 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */ 
/*      */     
/* 1851 */     if (dataset instanceof RangeInfo) {
/* 1852 */       RangeInfo info = (RangeInfo)dataset;
/* 1853 */       return new Double(info.getRangeUpperBound(true));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1859 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1860 */     int seriesCount = dataset.getRowCount();
/* 1861 */     int itemCount = dataset.getColumnCount();
/* 1862 */     for (int series = 0; series < seriesCount; series++) {
/* 1863 */       for (int item = 0; item < itemCount; item++) {
/*      */         Number value;
/* 1865 */         if (dataset instanceof IntervalCategoryDataset) {
/* 1866 */           IntervalCategoryDataset icd = (IntervalCategoryDataset)dataset;
/*      */           
/* 1868 */           value = icd.getEndValue(series, item);
/*      */         } else {
/*      */           
/* 1871 */           value = dataset.getValue(series, item);
/*      */         } 
/* 1873 */         if (value != null) {
/* 1874 */           maximum = Math.max(maximum, value.doubleValue());
/*      */         }
/*      */       } 
/*      */     } 
/* 1878 */     if (maximum == Double.NEGATIVE_INFINITY) {
/* 1879 */       return null;
/*      */     }
/*      */     
/* 1882 */     return new Double(maximum);
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
/*      */   
/*      */   public static Number findMaximumRangeValue(XYDataset dataset) {
/* 1902 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/*      */ 
/*      */     
/* 1905 */     if (dataset instanceof RangeInfo) {
/* 1906 */       RangeInfo info = (RangeInfo)dataset;
/* 1907 */       return new Double(info.getRangeUpperBound(true));
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1913 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1914 */     int seriesCount = dataset.getSeriesCount();
/* 1915 */     for (int series = 0; series < seriesCount; series++) {
/* 1916 */       int itemCount = dataset.getItemCount(series);
/* 1917 */       for (int item = 0; item < itemCount; item++) {
/*      */         double value;
/* 1919 */         if (dataset instanceof IntervalXYDataset) {
/* 1920 */           IntervalXYDataset intervalXYData = (IntervalXYDataset)dataset;
/*      */           
/* 1922 */           value = intervalXYData.getEndYValue(series, item);
/*      */         }
/* 1924 */         else if (dataset instanceof OHLCDataset) {
/* 1925 */           OHLCDataset highLowData = (OHLCDataset)dataset;
/* 1926 */           value = highLowData.getHighValue(series, item);
/*      */         } else {
/*      */           
/* 1929 */           value = dataset.getYValue(series, item);
/*      */         } 
/* 1931 */         if (!Double.isNaN(value)) {
/* 1932 */           maximum = Math.max(maximum, value);
/*      */         }
/*      */       } 
/*      */     } 
/* 1936 */     if (maximum == Double.NEGATIVE_INFINITY) {
/* 1937 */       return null;
/*      */     }
/*      */     
/* 1940 */     return new Double(maximum);
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
/*      */   public static Range findStackedRangeBounds(CategoryDataset dataset) {
/* 1956 */     return findStackedRangeBounds(dataset, 0.0D);
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
/*      */   public static Range findStackedRangeBounds(CategoryDataset dataset, double base) {
/* 1970 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 1971 */     Range result = null;
/* 1972 */     double minimum = Double.POSITIVE_INFINITY;
/* 1973 */     double maximum = Double.NEGATIVE_INFINITY;
/* 1974 */     int categoryCount = dataset.getColumnCount();
/* 1975 */     for (int item = 0; item < categoryCount; item++) {
/* 1976 */       double positive = base;
/* 1977 */       double negative = base;
/* 1978 */       int seriesCount = dataset.getRowCount();
/* 1979 */       for (int series = 0; series < seriesCount; series++) {
/* 1980 */         Number number = dataset.getValue(series, item);
/* 1981 */         if (number != null) {
/* 1982 */           double value = number.doubleValue();
/* 1983 */           if (value > 0.0D) {
/* 1984 */             positive += value;
/*      */           }
/* 1986 */           if (value < 0.0D) {
/* 1987 */             negative += value;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1992 */       minimum = Math.min(minimum, negative);
/* 1993 */       maximum = Math.max(maximum, positive);
/*      */     } 
/* 1995 */     if (minimum <= maximum) {
/* 1996 */       result = new Range(minimum, maximum);
/*      */     }
/* 1998 */     return result;
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
/*      */   public static Range findStackedRangeBounds(CategoryDataset dataset, KeyToGroupMap map) {
/* 2014 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 2015 */     boolean hasValidData = false;
/* 2016 */     Range result = null;
/*      */ 
/*      */     
/* 2019 */     int[] groupIndex = new int[dataset.getRowCount()];
/* 2020 */     for (int i = 0; i < dataset.getRowCount(); i++) {
/* 2021 */       groupIndex[i] = map.getGroupIndex(map.getGroup(dataset
/* 2022 */             .getRowKey(i)));
/*      */     }
/*      */ 
/*      */     
/* 2026 */     int groupCount = map.getGroupCount();
/* 2027 */     double[] minimum = new double[groupCount];
/* 2028 */     double[] maximum = new double[groupCount];
/*      */     
/* 2030 */     int categoryCount = dataset.getColumnCount();
/* 2031 */     for (int item = 0; item < categoryCount; item++) {
/* 2032 */       double[] positive = new double[groupCount];
/* 2033 */       double[] negative = new double[groupCount];
/* 2034 */       int seriesCount = dataset.getRowCount();
/* 2035 */       for (int series = 0; series < seriesCount; series++) {
/* 2036 */         Number number = dataset.getValue(series, item);
/* 2037 */         if (number != null) {
/* 2038 */           hasValidData = true;
/* 2039 */           double value = number.doubleValue();
/* 2040 */           if (value > 0.0D) {
/* 2041 */             positive[groupIndex[series]] = positive[groupIndex[series]] + value;
/*      */           }
/*      */           
/* 2044 */           if (value < 0.0D) {
/* 2045 */             negative[groupIndex[series]] = negative[groupIndex[series]] + value;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2051 */       for (int g = 0; g < groupCount; g++) {
/* 2052 */         minimum[g] = Math.min(minimum[g], negative[g]);
/* 2053 */         maximum[g] = Math.max(maximum[g], positive[g]);
/*      */       } 
/*      */     } 
/* 2056 */     if (hasValidData) {
/* 2057 */       for (int j = 0; j < groupCount; j++) {
/* 2058 */         result = Range.combine(result, new Range(minimum[j], maximum[j]));
/*      */       }
/*      */     }
/*      */     
/* 2062 */     return result;
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
/*      */   public static Number findMinimumStackedRangeValue(CategoryDataset dataset) {
/* 2076 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 2077 */     Number result = null;
/* 2078 */     boolean hasValidData = false;
/* 2079 */     double minimum = 0.0D;
/* 2080 */     int categoryCount = dataset.getColumnCount();
/* 2081 */     for (int item = 0; item < categoryCount; item++) {
/* 2082 */       double total = 0.0D;
/* 2083 */       int seriesCount = dataset.getRowCount();
/* 2084 */       for (int series = 0; series < seriesCount; series++) {
/* 2085 */         Number number = dataset.getValue(series, item);
/* 2086 */         if (number != null) {
/* 2087 */           hasValidData = true;
/* 2088 */           double value = number.doubleValue();
/* 2089 */           if (value < 0.0D) {
/* 2090 */             total += value;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2095 */       minimum = Math.min(minimum, total);
/*      */     } 
/* 2097 */     if (hasValidData) {
/* 2098 */       result = new Double(minimum);
/*      */     }
/* 2100 */     return result;
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
/*      */   public static Number findMaximumStackedRangeValue(CategoryDataset dataset) {
/* 2114 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 2115 */     Number result = null;
/* 2116 */     boolean hasValidData = false;
/* 2117 */     double maximum = 0.0D;
/* 2118 */     int categoryCount = dataset.getColumnCount();
/* 2119 */     for (int item = 0; item < categoryCount; item++) {
/* 2120 */       double total = 0.0D;
/* 2121 */       int seriesCount = dataset.getRowCount();
/* 2122 */       for (int series = 0; series < seriesCount; series++) {
/* 2123 */         Number number = dataset.getValue(series, item);
/* 2124 */         if (number != null) {
/* 2125 */           hasValidData = true;
/* 2126 */           double value = number.doubleValue();
/* 2127 */           if (value > 0.0D) {
/* 2128 */             total += value;
/*      */           }
/*      */         } 
/*      */       } 
/* 2132 */       maximum = Math.max(maximum, total);
/*      */     } 
/* 2134 */     if (hasValidData) {
/* 2135 */       result = new Double(maximum);
/*      */     }
/* 2137 */     return result;
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
/*      */   public static Range findStackedRangeBounds(TableXYDataset dataset) {
/* 2149 */     return findStackedRangeBounds(dataset, 0.0D);
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
/*      */   public static Range findStackedRangeBounds(TableXYDataset dataset, double base) {
/* 2163 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 2164 */     double minimum = base;
/* 2165 */     double maximum = base;
/* 2166 */     for (int itemNo = 0; itemNo < dataset.getItemCount(); itemNo++) {
/* 2167 */       double positive = base;
/* 2168 */       double negative = base;
/* 2169 */       int seriesCount = dataset.getSeriesCount();
/* 2170 */       for (int seriesNo = 0; seriesNo < seriesCount; seriesNo++) {
/* 2171 */         double y = dataset.getYValue(seriesNo, itemNo);
/* 2172 */         if (!Double.isNaN(y)) {
/* 2173 */           if (y > 0.0D) {
/* 2174 */             positive += y;
/*      */           } else {
/*      */             
/* 2177 */             negative += y;
/*      */           } 
/*      */         }
/*      */       } 
/* 2181 */       if (positive > maximum) {
/* 2182 */         maximum = positive;
/*      */       }
/* 2184 */       if (negative < minimum) {
/* 2185 */         minimum = negative;
/*      */       }
/*      */     } 
/* 2188 */     if (minimum <= maximum) {
/* 2189 */       return new Range(minimum, maximum);
/*      */     }
/*      */     
/* 2192 */     return null;
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
/*      */   public static double calculateStackTotal(TableXYDataset dataset, int item) {
/* 2208 */     double total = 0.0D;
/* 2209 */     int seriesCount = dataset.getSeriesCount();
/* 2210 */     for (int s = 0; s < seriesCount; s++) {
/* 2211 */       double value = dataset.getYValue(s, item);
/* 2212 */       if (!Double.isNaN(value)) {
/* 2213 */         total += value;
/*      */       }
/*      */     } 
/* 2216 */     return total;
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
/*      */   public static Range findCumulativeRangeBounds(CategoryDataset dataset) {
/* 2230 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 2231 */     boolean allItemsNull = true;
/*      */     
/* 2233 */     double minimum = 0.0D;
/* 2234 */     double maximum = 0.0D;
/* 2235 */     for (int row = 0; row < dataset.getRowCount(); row++) {
/* 2236 */       double runningTotal = 0.0D;
/* 2237 */       for (int column = 0; column <= dataset.getColumnCount() - 1; 
/* 2238 */         column++) {
/* 2239 */         Number n = dataset.getValue(row, column);
/* 2240 */         if (n != null) {
/* 2241 */           allItemsNull = false;
/* 2242 */           double value = n.doubleValue();
/* 2243 */           if (!Double.isNaN(value)) {
/* 2244 */             runningTotal += value;
/* 2245 */             minimum = Math.min(minimum, runningTotal);
/* 2246 */             maximum = Math.max(maximum, runningTotal);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 2251 */     if (!allItemsNull) {
/* 2252 */       return new Range(minimum, maximum);
/*      */     }
/*      */     
/* 2255 */     return null;
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
/*      */   public static double findYValue(XYDataset dataset, int series, double x) {
/* 2274 */     int[] indices = findItemIndicesForX(dataset, series, x);
/* 2275 */     if (indices[0] == -1) {
/* 2276 */       return Double.NaN;
/*      */     }
/* 2278 */     if (indices[0] == indices[1]) {
/* 2279 */       return dataset.getYValue(series, indices[0]);
/*      */     }
/* 2281 */     double x0 = dataset.getXValue(series, indices[0]);
/* 2282 */     double x1 = dataset.getXValue(series, indices[1]);
/* 2283 */     double y0 = dataset.getYValue(series, indices[0]);
/* 2284 */     double y1 = dataset.getYValue(series, indices[1]);
/* 2285 */     return y0 + (y1 - y0) * (x - x0) / (x1 - x0);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] findItemIndicesForX(XYDataset dataset, int series, double x) {
/* 2311 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 2312 */     int itemCount = dataset.getItemCount(series);
/* 2313 */     if (itemCount == 0) {
/* 2314 */       return new int[] { -1, -1 };
/*      */     }
/* 2316 */     if (itemCount == 1) {
/* 2317 */       if (x == dataset.getXValue(series, 0)) {
/* 2318 */         return new int[] { 0, 0 };
/*      */       }
/* 2320 */       return new int[] { -1, -1 };
/*      */     } 
/*      */     
/* 2323 */     if (dataset.getDomainOrder() == DomainOrder.ASCENDING) {
/* 2324 */       int low = 0;
/* 2325 */       int high = itemCount - 1;
/* 2326 */       double lowValue = dataset.getXValue(series, low);
/* 2327 */       if (lowValue > x) {
/* 2328 */         return new int[] { -1, -1 };
/*      */       }
/* 2330 */       if (lowValue == x) {
/* 2331 */         return new int[] { low, low };
/*      */       }
/* 2333 */       double highValue = dataset.getXValue(series, high);
/* 2334 */       if (highValue < x) {
/* 2335 */         return new int[] { -1, -1 };
/*      */       }
/* 2337 */       if (highValue == x) {
/* 2338 */         return new int[] { high, high };
/*      */       }
/* 2340 */       int mid = (low + high) / 2;
/* 2341 */       while (high - low > 1) {
/* 2342 */         double midV = dataset.getXValue(series, mid);
/* 2343 */         if (x == midV) {
/* 2344 */           return new int[] { mid, mid };
/*      */         }
/* 2346 */         if (midV < x) {
/* 2347 */           low = mid;
/*      */         } else {
/*      */           
/* 2350 */           high = mid;
/*      */         } 
/* 2352 */         mid = (low + high) / 2;
/*      */       } 
/* 2354 */       return new int[] { low, high };
/*      */     } 
/* 2356 */     if (dataset.getDomainOrder() == DomainOrder.DESCENDING) {
/* 2357 */       int high = 0;
/* 2358 */       int low = itemCount - 1;
/* 2359 */       double lowValue = dataset.getXValue(series, low);
/* 2360 */       if (lowValue > x) {
/* 2361 */         return new int[] { -1, -1 };
/*      */       }
/* 2363 */       double highValue = dataset.getXValue(series, high);
/* 2364 */       if (highValue < x) {
/* 2365 */         return new int[] { -1, -1 };
/*      */       }
/* 2367 */       int mid = (low + high) / 2;
/* 2368 */       while (high - low > 1) {
/* 2369 */         double midV = dataset.getXValue(series, mid);
/* 2370 */         if (x == midV) {
/* 2371 */           return new int[] { mid, mid };
/*      */         }
/* 2373 */         if (midV < x) {
/* 2374 */           low = mid;
/*      */         } else {
/*      */           
/* 2377 */           high = mid;
/*      */         } 
/* 2379 */         mid = (low + high) / 2;
/*      */       } 
/* 2381 */       return new int[] { low, high };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2387 */     double prev = dataset.getXValue(series, 0);
/* 2388 */     if (x == prev) {
/* 2389 */       return new int[] { 0, 0 };
/*      */     }
/* 2391 */     for (int i = 1; i < itemCount; i++) {
/* 2392 */       double next = dataset.getXValue(series, i);
/* 2393 */       if (x == next) {
/* 2394 */         return new int[] { i, i };
/*      */       }
/* 2396 */       if ((x > prev && x < next) || (x < prev && x > next)) {
/* 2397 */         return new int[] { i - 1, i };
/*      */       }
/*      */     } 
/* 2400 */     return new int[] { -1, -1 };
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/DatasetUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */