/*     */ package org.jfree.ui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dialog;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.Window;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RefineryUtilities
/*     */ {
/*     */   public static Point getCenterPoint() {
/* 107 */     GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */     
/*     */     try {
/* 110 */       Method method = GraphicsEnvironment.class.getMethod("getCenterPoint", (Class[])null);
/* 111 */       return (Point)method.invoke(localGraphicsEnvironment, (Object[])null);
/*     */     }
/* 113 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
/* 119 */       return new Point(s.width / 2, s.height / 2);
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
/*     */   public static Rectangle getMaximumWindowBounds() {
/* 131 */     GraphicsEnvironment localGraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
/*     */     
/*     */     try {
/* 134 */       Method method = GraphicsEnvironment.class.getMethod("getMaximumWindowBounds", (Class[])null);
/* 135 */       return (Rectangle)method.invoke(localGraphicsEnvironment, (Object[])null);
/*     */     }
/* 137 */     catch (Exception e) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
/* 143 */       return new Rectangle(0, 0, s.width, s.height);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void centerFrameOnScreen(Window frame) {
/* 152 */     positionFrameOnScreen(frame, 0.5D, 0.5D);
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
/*     */   public static void positionFrameOnScreen(Window frame, double horizontalPercent, double verticalPercent) {
/* 169 */     Rectangle s = frame.getGraphicsConfiguration().getBounds();
/* 170 */     Dimension f = frame.getSize();
/* 171 */     int w = Math.max(s.width - f.width, 0);
/* 172 */     int h = Math.max(s.height - f.height, 0);
/* 173 */     int x = (int)(horizontalPercent * w) + s.x;
/* 174 */     int y = (int)(verticalPercent * h) + s.y;
/* 175 */     frame.setBounds(x, y, f.width, f.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void positionFrameRandomly(Window frame) {
/* 186 */     positionFrameOnScreen(frame, Math.random(), Math.random());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void centerDialogInParent(Dialog dialog) {
/* 195 */     positionDialogRelativeToParent(dialog, 0.5D, 0.5D);
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
/*     */   public static void positionDialogRelativeToParent(Dialog dialog, double horizontalPercent, double verticalPercent) {
/* 208 */     Container parent = dialog.getParent();
/* 209 */     if (parent == null) {
/*     */       
/* 211 */       centerFrameOnScreen(dialog);
/*     */       
/*     */       return;
/*     */     } 
/* 215 */     Dimension d = dialog.getSize();
/* 216 */     Dimension p = parent.getSize();
/*     */     
/* 218 */     int baseX = parent.getX();
/* 219 */     int baseY = parent.getY();
/*     */     
/* 221 */     int x = baseX + (int)(horizontalPercent * p.width);
/* 222 */     int y = baseY + (int)(verticalPercent * p.height);
/*     */ 
/*     */     
/* 225 */     Rectangle s = parent.getGraphicsConfiguration().getBounds();
/* 226 */     Rectangle r = new Rectangle(x, y, d.width, d.height);
/* 227 */     dialog.setBounds(r.intersection(s));
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
/*     */   public static JPanel createTablePanel(TableModel model) {
/* 239 */     JPanel panel = new JPanel(new BorderLayout());
/* 240 */     JTable table = new JTable(model);
/* 241 */     for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
/* 242 */       TableColumn column = table.getColumnModel().getColumn(columnIndex);
/* 243 */       Class<?> c = model.getColumnClass(columnIndex);
/* 244 */       if (c.equals(Number.class)) {
/* 245 */         column.setCellRenderer(new NumberCellRenderer());
/*     */       }
/*     */     } 
/* 248 */     panel.add(new JScrollPane(table));
/* 249 */     return panel;
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
/*     */   public static JLabel createJLabel(String text, Font font) {
/* 263 */     JLabel result = new JLabel(text);
/* 264 */     result.setFont(font);
/* 265 */     return result;
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
/*     */   public static JLabel createJLabel(String text, Font font, Color color) {
/* 280 */     JLabel result = new JLabel(text);
/* 281 */     result.setFont(font);
/* 282 */     result.setForeground(color);
/* 283 */     return result;
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
/*     */   public static JButton createJButton(String label, Font font) {
/* 297 */     JButton result = new JButton(label);
/* 298 */     result.setFont(font);
/* 299 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/RefineryUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */