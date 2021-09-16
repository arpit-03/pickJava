/*     */ package org.jfree.data.time;
/*     */ 
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import org.jfree.data.general.SeriesChangeEvent;
/*     */ import org.jfree.data.general.SeriesChangeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimeSeriesTableModel
/*     */   extends AbstractTableModel
/*     */   implements SeriesChangeListener
/*     */ {
/*     */   private TimeSeries series;
/*     */   private boolean editable;
/*     */   private RegularTimePeriod newTimePeriod;
/*     */   private Number newValue;
/*     */   
/*     */   public TimeSeriesTableModel() {
/*  74 */     this(new TimeSeries("Untitled"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeSeriesTableModel(TimeSeries series) {
/*  83 */     this(series, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeSeriesTableModel(TimeSeries series, boolean editable) {
/*  93 */     this.series = series;
/*  94 */     this.series.addChangeListener(this);
/*  95 */     this.editable = editable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 106 */     return 2;
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
/*     */   public Class getColumnClass(int column) {
/* 118 */     if (column == 0) {
/* 119 */       return String.class;
/*     */     }
/*     */     
/* 122 */     if (column == 1) {
/* 123 */       return Double.class;
/*     */     }
/*     */     
/* 126 */     return null;
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
/*     */   public String getColumnName(int column) {
/* 141 */     if (column == 0) {
/* 142 */       return "Period:";
/*     */     }
/*     */     
/* 145 */     if (column == 1) {
/* 146 */       return "Value:";
/*     */     }
/*     */     
/* 149 */     return null;
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
/* 162 */     return this.series.getItemCount();
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
/*     */   public Object getValueAt(int row, int column) {
/* 176 */     if (row < this.series.getItemCount()) {
/* 177 */       if (column == 0) {
/* 178 */         return this.series.getTimePeriod(row);
/*     */       }
/*     */       
/* 181 */       if (column == 1) {
/* 182 */         return this.series.getValue(row);
/*     */       }
/*     */       
/* 185 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 190 */     if (column == 0) {
/* 191 */       return this.newTimePeriod;
/*     */     }
/*     */     
/* 194 */     if (column == 1) {
/* 195 */       return this.newValue;
/*     */     }
/*     */     
/* 198 */     return null;
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
/*     */   public boolean isCellEditable(int row, int column) {
/* 215 */     if (this.editable) {
/* 216 */       if (column == 0 || column == 1) {
/* 217 */         return true;
/*     */       }
/*     */       
/* 220 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     return false;
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
/*     */   public void setValueAt(Object value, int row, int column) {
/* 238 */     if (row < this.series.getItemCount()) {
/*     */ 
/*     */       
/* 241 */       if (column == 1) {
/*     */         try {
/* 243 */           Double v = Double.valueOf(value.toString());
/* 244 */           this.series.update(row, v);
/*     */         
/*     */         }
/* 247 */         catch (NumberFormatException nfe) {
/* 248 */           System.err.println("Number format exception");
/*     */         }
/*     */       
/*     */       }
/*     */     }
/* 253 */     else if (column == 0) {
/*     */       
/* 255 */       this.newTimePeriod = null;
/*     */     }
/* 257 */     else if (column == 1) {
/* 258 */       this.newValue = Double.valueOf(value.toString());
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
/*     */   public void seriesChanged(SeriesChangeEvent event) {
/* 271 */     fireTableDataChanged();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/time/TimeSeriesTableModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */