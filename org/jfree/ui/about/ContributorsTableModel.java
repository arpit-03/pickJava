/*     */ package org.jfree.ui.about;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import org.jfree.util.ResourceBundleWrapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContributorsTableModel
/*     */   extends AbstractTableModel
/*     */ {
/*     */   private List contributors;
/*     */   private String nameColumnLabel;
/*     */   private String contactColumnLabel;
/*     */   
/*     */   public ContributorsTableModel(List contributors) {
/*  82 */     this.contributors = contributors;
/*     */     
/*  84 */     String baseName = "org.jfree.ui.about.resources.AboutResources";
/*  85 */     ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
/*     */     
/*  87 */     this.nameColumnLabel = resources.getString("contributors-table.column.name");
/*     */     
/*  89 */     this.contactColumnLabel = resources.getString("contributors-table.column.contact");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRowCount() {
/* 100 */     return this.contributors.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getColumnCount() {
/* 110 */     return 2;
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
/* 122 */     String result = null;
/*     */     
/* 124 */     switch (column) {
/*     */       case 0:
/* 126 */         result = this.nameColumnLabel;
/*     */         break;
/*     */       case 1:
/* 129 */         result = this.contactColumnLabel;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return result;
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
/* 148 */     Object result = null;
/*     */     
/* 150 */     Contributor contributor = this.contributors.get(row);
/*     */     
/* 152 */     if (column == 0) {
/* 153 */       result = contributor.getName();
/*     */     }
/* 155 */     else if (column == 1) {
/* 156 */       result = contributor.getEmail();
/*     */     } 
/* 158 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/about/ContributorsTableModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */