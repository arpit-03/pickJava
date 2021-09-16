/*    */ package org.jfree.ui.about;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.util.List;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTable;
/*    */ import javax.swing.table.TableModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContributorsPanel
/*    */   extends JPanel
/*    */ {
/*    */   private JTable table;
/*    */   private TableModel model;
/*    */   
/*    */   public ContributorsPanel(List contributors) {
/* 77 */     setLayout(new BorderLayout());
/* 78 */     this.model = new ContributorsTableModel(contributors);
/* 79 */     this.table = new JTable(this.model);
/* 80 */     add(new JScrollPane(this.table));
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/about/ContributorsPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */