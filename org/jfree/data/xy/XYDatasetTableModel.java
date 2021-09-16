/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import org.jfree.data.general.DatasetChangeEvent;
/*     */ import org.jfree.data.general.DatasetChangeListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XYDatasetTableModel
/*     */   extends AbstractTableModel
/*     */   implements TableModel, DatasetChangeListener
/*     */ {
/*  69 */   TableXYDataset model = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYDatasetTableModel() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYDatasetTableModel(TableXYDataset dataset) {
/*  84 */     this();
/*  85 */     this.model = dataset;
/*  86 */     this.model.addChangeListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModel(TableXYDataset dataset) {
/*  95 */     this.model = dataset;
/*  96 */     this.model.addChangeListener(this);
/*  97 */     fireTableDataChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 107 */     if (this.model == null) {
/* 108 */       return 0;
/*     */     }
/* 110 */     return this.model.getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 120 */     if (this.model == null) {
/* 121 */       return 0;
/*     */     }
/* 123 */     return this.model.getSeriesCount() + 1;
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
/*     */   public String getColumnName(int column) {
/* 135 */     if (this.model == null) {
/* 136 */       return super.getColumnName(column);
/*     */     }
/* 138 */     if (column < 1) {
/* 139 */       return "X Value";
/*     */     }
/*     */     
/* 142 */     return this.model.getSeriesKey(column - 1).toString();
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
/*     */   public Object getValueAt(int row, int column) {
/* 157 */     if (this.model == null) {
/* 158 */       return null;
/*     */     }
/* 160 */     if (column < 1) {
/* 161 */       return this.model.getX(0, row);
/*     */     }
/*     */     
/* 164 */     return this.model.getY(column - 1, row);
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
/*     */   public void datasetChanged(DatasetChangeEvent event) {
/* 177 */     fireTableDataChanged();
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
/*     */   public boolean isCellEditable(int row, int column) {
/* 190 */     return false;
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
/*     */   public void setValueAt(Object value, int row, int column) {
/* 202 */     if (isCellEditable(row, column));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XYDatasetTableModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */