/*     */ package org.jfree.ui.about;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.Clipboard;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.KeyStroke;
/*     */ import javax.swing.ListSelectionModel;
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
/*     */ 
/*     */ public class SystemPropertiesPanel
/*     */   extends JPanel
/*     */ {
/*     */   private JTable table;
/*     */   private JPopupMenu copyPopupMenu;
/*     */   private JMenuItem copyMenuItem;
/*     */   private PopupListener copyPopupListener;
/*     */   
/*     */   public SystemPropertiesPanel() {
/*  97 */     String baseName = "org.jfree.ui.about.resources.AboutResources";
/*  98 */     ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
/*     */ 
/*     */     
/* 101 */     setLayout(new BorderLayout());
/* 102 */     this.table = (JTable)SystemProperties.createSystemPropertiesTable();
/* 103 */     add(new JScrollPane(this.table));
/*     */ 
/*     */     
/* 106 */     this.copyPopupMenu = new JPopupMenu();
/*     */     
/* 108 */     String label = resources.getString("system-properties-panel.popup-menu.copy");
/*     */     
/* 110 */     KeyStroke accelerator = (KeyStroke)resources.getObject("system-properties-panel.popup-menu.copy.accelerator");
/*     */     
/* 112 */     this.copyMenuItem = new JMenuItem(label);
/* 113 */     this.copyMenuItem.setAccelerator(accelerator);
/* 114 */     this.copyMenuItem.getAccessibleContext().setAccessibleDescription(label);
/*     */     
/* 116 */     this.copyMenuItem.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 118 */             SystemPropertiesPanel.this.copySystemPropertiesToClipboard();
/*     */           }
/*     */         });
/* 121 */     this.copyPopupMenu.add(this.copyMenuItem);
/*     */ 
/*     */     
/* 124 */     this.copyPopupListener = new PopupListener();
/* 125 */     this.table.addMouseListener(this.copyPopupListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copySystemPropertiesToClipboard() {
/* 135 */     StringBuffer buffer = new StringBuffer();
/* 136 */     ListSelectionModel selection = this.table.getSelectionModel();
/* 137 */     int firstRow = selection.getMinSelectionIndex();
/* 138 */     int lastRow = selection.getMaxSelectionIndex();
/* 139 */     if (firstRow != -1 && lastRow != -1) {
/* 140 */       for (int r = firstRow; r <= lastRow; r++) {
/* 141 */         for (int c = 0; c < this.table.getColumnCount(); c++) {
/* 142 */           buffer.append(this.table.getValueAt(r, c));
/* 143 */           if (c != 2) {
/* 144 */             buffer.append("\t");
/*     */           }
/*     */         } 
/* 147 */         buffer.append("\n");
/*     */       } 
/*     */     }
/* 150 */     StringSelection ss = new StringSelection(buffer.toString());
/* 151 */     Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
/* 152 */     cb.setContents(ss, ss);
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
/*     */   protected final JPopupMenu getCopyPopupMenu() {
/* 164 */     return this.copyPopupMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final JTable getTable() {
/* 173 */     return this.table;
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
/*     */   private class PopupListener
/*     */     extends MouseAdapter
/*     */   {
/*     */     public void mousePressed(MouseEvent e) {
/* 193 */       maybeShowPopup(e);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent e) {
/* 202 */       maybeShowPopup(e);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void maybeShowPopup(MouseEvent e) {
/* 211 */       if (e.isPopupTrigger())
/* 212 */         SystemPropertiesPanel.this.getCopyPopupMenu().show(SystemPropertiesPanel.this
/* 213 */             .getTable(), e.getX(), e.getY()); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/about/SystemPropertiesPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */