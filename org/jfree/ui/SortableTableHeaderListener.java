/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.awt.event.MouseListener;
/*     */ import java.awt.event.MouseMotionListener;
/*     */ import javax.swing.table.JTableHeader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SortableTableHeaderListener
/*     */   implements MouseListener, MouseMotionListener
/*     */ {
/*     */   private SortableTableModel model;
/*     */   private SortButtonRenderer renderer;
/*     */   private int sortColumnIndex;
/*     */   
/*     */   public SortableTableHeaderListener(SortableTableModel model, SortButtonRenderer renderer) {
/*  76 */     this.model = model;
/*  77 */     this.renderer = renderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTableModel(SortableTableModel model) {
/*  86 */     this.model = model;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mousePressed(MouseEvent e) {
/*  97 */     JTableHeader header = (JTableHeader)e.getComponent();
/*     */     
/*  99 */     if (header.getResizingColumn() == null && 
/* 100 */       header.getDraggedDistance() < 1) {
/* 101 */       int columnIndex = header.columnAtPoint(e.getPoint());
/*     */       
/* 103 */       int modelColumnIndex = header.getTable().convertColumnIndexToModel(columnIndex);
/* 104 */       if (this.model.isSortable(modelColumnIndex)) {
/* 105 */         this.sortColumnIndex = header.getTable().convertColumnIndexToModel(columnIndex);
/* 106 */         this.renderer.setPressedColumn(this.sortColumnIndex);
/* 107 */         header.repaint();
/* 108 */         if (header.getTable().isEditing()) {
/* 109 */           header.getTable().getCellEditor().stopCellEditing();
/*     */         }
/*     */       } else {
/*     */         
/* 113 */         this.sortColumnIndex = -1;
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
/*     */   public void mouseDragged(MouseEvent e) {
/* 127 */     JTableHeader header = (JTableHeader)e.getComponent();
/*     */     
/* 129 */     if (header.getDraggedDistance() > 0 || header.getResizingColumn() != null) {
/* 130 */       this.renderer.setPressedColumn(-1);
/* 131 */       this.sortColumnIndex = -1;
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
/*     */   public void mouseEntered(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseMoved(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseExited(MouseEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(MouseEvent e) {
/* 178 */     JTableHeader header = (JTableHeader)e.getComponent();
/*     */     
/* 180 */     if (header.getResizingColumn() == null && 
/* 181 */       this.sortColumnIndex != -1) {
/* 182 */       SortableTableModel model = (SortableTableModel)header.getTable().getModel();
/* 183 */       boolean ascending = !model.isAscending();
/* 184 */       model.setAscending(ascending);
/* 185 */       model.sortByColumn(this.sortColumnIndex, ascending);
/*     */       
/* 187 */       this.renderer.setPressedColumn(-1);
/* 188 */       header.repaint();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/SortableTableHeaderListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */