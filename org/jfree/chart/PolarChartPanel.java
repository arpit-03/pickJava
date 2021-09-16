/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PolarPlot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PolarChartPanel
/*     */   extends ChartPanel
/*     */ {
/*     */   private static final String POLAR_ZOOM_IN_ACTION_COMMAND = "Polar Zoom In";
/*     */   private static final String POLAR_ZOOM_OUT_ACTION_COMMAND = "Polar Zoom Out";
/*     */   private static final String POLAR_AUTO_RANGE_ACTION_COMMAND = "Polar Auto Range";
/*     */   
/*     */   public PolarChartPanel(JFreeChart chart) {
/* 101 */     this(chart, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolarChartPanel(JFreeChart chart, boolean useBuffer) {
/* 111 */     super(chart, useBuffer);
/* 112 */     checkChart(chart);
/* 113 */     setMinimumDrawWidth(200);
/* 114 */     setMinimumDrawHeight(200);
/* 115 */     setMaximumDrawWidth(2000);
/* 116 */     setMaximumDrawHeight(2000);
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
/*     */   public void setChart(JFreeChart chart) {
/* 129 */     checkChart(chart);
/* 130 */     super.setChart(chart);
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
/*     */   protected JPopupMenu createPopupMenu(boolean properties, boolean save, boolean print, boolean zoom) {
/* 147 */     JPopupMenu result = super.createPopupMenu(properties, save, print, zoom);
/* 148 */     int zoomInIndex = getPopupMenuItem(result, localizationResources
/* 149 */         .getString("Zoom_In"));
/* 150 */     int zoomOutIndex = getPopupMenuItem(result, localizationResources
/* 151 */         .getString("Zoom_Out"));
/* 152 */     int autoIndex = getPopupMenuItem(result, localizationResources
/* 153 */         .getString("Auto_Range"));
/* 154 */     if (zoom) {
/*     */       
/* 156 */       JMenuItem zoomIn = new JMenuItem(localizationResources.getString("Zoom_In"));
/* 157 */       zoomIn.setActionCommand("Polar Zoom In");
/* 158 */       zoomIn.addActionListener(this);
/*     */ 
/*     */       
/* 161 */       JMenuItem zoomOut = new JMenuItem(localizationResources.getString("Zoom_Out"));
/* 162 */       zoomOut.setActionCommand("Polar Zoom Out");
/* 163 */       zoomOut.addActionListener(this);
/*     */ 
/*     */       
/* 166 */       JMenuItem auto = new JMenuItem(localizationResources.getString("Auto_Range"));
/* 167 */       auto.setActionCommand("Polar Auto Range");
/* 168 */       auto.addActionListener(this);
/*     */       
/* 170 */       if (zoomInIndex != -1) {
/* 171 */         result.remove(zoomInIndex);
/*     */       } else {
/*     */         
/* 174 */         zoomInIndex = result.getComponentCount() - 1;
/*     */       } 
/* 176 */       result.add(zoomIn, zoomInIndex);
/* 177 */       if (zoomOutIndex != -1) {
/* 178 */         result.remove(zoomOutIndex);
/*     */       } else {
/*     */         
/* 181 */         zoomOutIndex = zoomInIndex + 1;
/*     */       } 
/* 183 */       result.add(zoomOut, zoomOutIndex);
/* 184 */       if (autoIndex != -1) {
/* 185 */         result.remove(autoIndex);
/*     */       } else {
/*     */         
/* 188 */         autoIndex = zoomOutIndex + 1;
/*     */       } 
/* 190 */       result.add(auto, autoIndex);
/*     */     } 
/* 192 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent event) {
/* 202 */     String command = event.getActionCommand();
/*     */     
/* 204 */     if (command.equals("Polar Zoom In")) {
/* 205 */       PolarPlot plot = (PolarPlot)getChart().getPlot();
/* 206 */       plot.zoom(0.5D);
/*     */     }
/* 208 */     else if (command.equals("Polar Zoom Out")) {
/* 209 */       PolarPlot plot = (PolarPlot)getChart().getPlot();
/* 210 */       plot.zoom(2.0D);
/*     */     }
/* 212 */     else if (command.equals("Polar Auto Range")) {
/* 213 */       PolarPlot plot = (PolarPlot)getChart().getPlot();
/* 214 */       plot.getAxis().setAutoRange(true);
/*     */     } else {
/*     */       
/* 217 */       super.actionPerformed(event);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkChart(JFreeChart chart) {
/* 235 */     Plot plot = chart.getPlot();
/* 236 */     if (!(plot instanceof PolarPlot)) {
/* 237 */       throw new IllegalArgumentException("plot is not a PolarPlot");
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
/*     */   private int getPopupMenuItem(JPopupMenu menu, String text) {
/* 250 */     int index = -1;
/* 251 */     for (int i = 0; index == -1 && i < menu.getComponentCount(); i++) {
/* 252 */       Component comp = menu.getComponent(i);
/* 253 */       if (comp instanceof JMenuItem) {
/* 254 */         JMenuItem item = (JMenuItem)comp;
/* 255 */         if (text.equals(item.getText())) {
/* 256 */           index = i;
/*     */         }
/*     */       } 
/*     */     } 
/* 260 */     return index;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/PolarChartPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */