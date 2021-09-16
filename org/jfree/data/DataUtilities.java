/*     */ package org.jfree.data;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DataUtilities
/*     */ {
/*     */   public static boolean equal(double[][] a, double[][] b) {
/*  75 */     if (a == null) {
/*  76 */       return (b == null);
/*     */     }
/*  78 */     if (b == null) {
/*  79 */       return false;
/*     */     }
/*  81 */     if (a.length != b.length) {
/*  82 */       return false;
/*     */     }
/*  84 */     for (int i = 0; i < a.length; i++) {
/*  85 */       if (!Arrays.equals(a[i], b[i])) {
/*  86 */         return false;
/*     */       }
/*     */     } 
/*  89 */     return true;
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
/*     */   public static double[][] clone(double[][] source) {
/* 102 */     ParamChecks.nullNotPermitted(source, "source");
/* 103 */     double[][] clone = new double[source.length][];
/* 104 */     for (int i = 0; i < source.length; i++) {
/* 105 */       if (source[i] != null) {
/* 106 */         double[] row = new double[(source[i]).length];
/* 107 */         System.arraycopy(source[i], 0, row, 0, (source[i]).length);
/* 108 */         clone[i] = row;
/*     */       } 
/*     */     } 
/* 111 */     return clone;
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
/*     */   public static double calculateColumnTotal(Values2D data, int column) {
/* 124 */     ParamChecks.nullNotPermitted(data, "data");
/* 125 */     double total = 0.0D;
/* 126 */     int rowCount = data.getRowCount();
/* 127 */     for (int r = 0; r < rowCount; r++) {
/* 128 */       Number n = data.getValue(r, column);
/* 129 */       if (n != null) {
/* 130 */         total += n.doubleValue();
/*     */       }
/*     */     } 
/* 133 */     return total;
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
/*     */   public static double calculateColumnTotal(Values2D data, int column, int[] validRows) {
/* 150 */     ParamChecks.nullNotPermitted(data, "data");
/* 151 */     double total = 0.0D;
/* 152 */     int rowCount = data.getRowCount();
/* 153 */     for (int v = 0; v < validRows.length; v++) {
/* 154 */       int row = validRows[v];
/* 155 */       if (row < rowCount) {
/* 156 */         Number n = data.getValue(row, column);
/* 157 */         if (n != null) {
/* 158 */           total += n.doubleValue();
/*     */         }
/*     */       } 
/*     */     } 
/* 162 */     return total;
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
/*     */   public static double calculateRowTotal(Values2D data, int row) {
/* 175 */     ParamChecks.nullNotPermitted(data, "data");
/* 176 */     double total = 0.0D;
/* 177 */     int columnCount = data.getColumnCount();
/* 178 */     for (int c = 0; c < columnCount; c++) {
/* 179 */       Number n = data.getValue(row, c);
/* 180 */       if (n != null) {
/* 181 */         total += n.doubleValue();
/*     */       }
/*     */     } 
/* 184 */     return total;
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
/*     */   public static double calculateRowTotal(Values2D data, int row, int[] validCols) {
/* 201 */     ParamChecks.nullNotPermitted(data, "data");
/* 202 */     double total = 0.0D;
/* 203 */     int colCount = data.getColumnCount();
/* 204 */     for (int v = 0; v < validCols.length; v++) {
/* 205 */       int col = validCols[v];
/* 206 */       if (col < colCount) {
/* 207 */         Number n = data.getValue(row, col);
/* 208 */         if (n != null) {
/* 209 */           total += n.doubleValue();
/*     */         }
/*     */       } 
/*     */     } 
/* 213 */     return total;
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
/*     */   public static Number[] createNumberArray(double[] data) {
/* 225 */     ParamChecks.nullNotPermitted(data, "data");
/* 226 */     Number[] result = new Number[data.length];
/* 227 */     for (int i = 0; i < data.length; i++) {
/* 228 */       result[i] = new Double(data[i]);
/*     */     }
/* 230 */     return result;
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
/*     */   public static Number[][] createNumberArray2D(double[][] data) {
/* 242 */     ParamChecks.nullNotPermitted(data, "data");
/* 243 */     int l1 = data.length;
/* 244 */     Number[][] result = new Number[l1][];
/* 245 */     for (int i = 0; i < l1; i++) {
/* 246 */       result[i] = createNumberArray(data[i]);
/*     */     }
/* 248 */     return result;
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
/*     */   public static KeyedValues getCumulativePercentages(KeyedValues data) {
/* 262 */     ParamChecks.nullNotPermitted(data, "data");
/* 263 */     DefaultKeyedValues result = new DefaultKeyedValues();
/* 264 */     double total = 0.0D;
/* 265 */     for (int i = 0; i < data.getItemCount(); i++) {
/* 266 */       Number v = data.getValue(i);
/* 267 */       if (v != null) {
/* 268 */         total += v.doubleValue();
/*     */       }
/*     */     } 
/* 271 */     double runningTotal = 0.0D;
/* 272 */     for (int j = 0; j < data.getItemCount(); j++) {
/* 273 */       Number v = data.getValue(j);
/* 274 */       if (v != null) {
/* 275 */         runningTotal += v.doubleValue();
/*     */       }
/* 277 */       result.addValue(data.getKey(j), new Double(runningTotal / total));
/*     */     } 
/* 279 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/DataUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */