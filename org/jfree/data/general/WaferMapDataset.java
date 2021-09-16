/*     */ package org.jfree.data.general;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import org.jfree.data.DefaultKeyedValues2D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaferMapDataset
/*     */   extends AbstractDataset
/*     */ {
/*     */   private DefaultKeyedValues2D data;
/*     */   private int maxChipX;
/*     */   private int maxChipY;
/*     */   private double chipSpace;
/*     */   private Double maxValue;
/*     */   private Double minValue;
/*     */   private static final double DEFAULT_CHIP_SPACE = 1.0D;
/*     */   
/*     */   public WaferMapDataset(int maxChipX, int maxChipY) {
/*  88 */     this(maxChipX, maxChipY, null);
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
/*     */   public WaferMapDataset(int maxChipX, int maxChipY, Number chipSpace) {
/* 100 */     this.maxValue = new Double(Double.NEGATIVE_INFINITY);
/* 101 */     this.minValue = new Double(Double.POSITIVE_INFINITY);
/* 102 */     this.data = new DefaultKeyedValues2D();
/*     */     
/* 104 */     this.maxChipX = maxChipX;
/* 105 */     this.maxChipY = maxChipY;
/* 106 */     if (chipSpace == null) {
/* 107 */       this.chipSpace = 1.0D;
/*     */     } else {
/*     */       
/* 110 */       this.chipSpace = chipSpace.doubleValue();
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
/*     */   public void addValue(Number value, Comparable chipx, Comparable chipy) {
/* 123 */     setValue(value, chipx, chipy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addValue(int v, int x, int y) {
/* 134 */     setValue(new Double(v), new Integer(x), new Integer(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Number value, Comparable chipx, Comparable chipy) {
/* 145 */     this.data.setValue(value, chipx, chipy);
/* 146 */     if (isMaxValue(value)) {
/* 147 */       this.maxValue = (Double)value;
/*     */     }
/* 149 */     if (isMinValue(value)) {
/* 150 */       this.minValue = (Double)value;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUniqueValueCount() {
/* 160 */     return getUniqueValues().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set getUniqueValues() {
/* 169 */     Set<Number> unique = new TreeSet();
/*     */     
/* 171 */     for (int r = 0; r < this.data.getRowCount(); r++) {
/* 172 */       for (int c = 0; c < this.data.getColumnCount(); c++) {
/* 173 */         Number value = this.data.getValue(r, c);
/* 174 */         if (value != null) {
/* 175 */           unique.add(value);
/*     */         }
/*     */       } 
/*     */     } 
/* 179 */     return unique;
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
/*     */   public Number getChipValue(int chipx, int chipy) {
/* 191 */     return getChipValue(new Integer(chipx), new Integer(chipy));
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
/*     */   public Number getChipValue(Comparable chipx, Comparable chipy) {
/* 203 */     int rowIndex = this.data.getRowIndex(chipx);
/* 204 */     if (rowIndex < 0) {
/* 205 */       return null;
/*     */     }
/* 207 */     int colIndex = this.data.getColumnIndex(chipy);
/* 208 */     if (colIndex < 0) {
/* 209 */       return null;
/*     */     }
/* 211 */     return this.data.getValue(rowIndex, colIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMaxValue(Number check) {
/* 222 */     if (check.doubleValue() > this.maxValue.doubleValue()) {
/* 223 */       return true;
/*     */     }
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMinValue(Number check) {
/* 236 */     if (check.doubleValue() < this.minValue.doubleValue()) {
/* 237 */       return true;
/*     */     }
/* 239 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMaxValue() {
/* 248 */     return this.maxValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMinValue() {
/* 257 */     return this.minValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxChipX() {
/* 266 */     return this.maxChipX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxChipX(int maxChipX) {
/* 275 */     this.maxChipX = maxChipX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxChipY() {
/* 284 */     return this.maxChipY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxChipY(int maxChipY) {
/* 293 */     this.maxChipY = maxChipY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getChipSpace() {
/* 302 */     return this.chipSpace;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChipSpace(double space) {
/* 311 */     this.chipSpace = space;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/general/WaferMapDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */