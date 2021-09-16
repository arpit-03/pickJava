/*     */ package org.jfree.chart.demo;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GradientPaint;
/*     */ import java.awt.Point;
/*     */ import java.awt.RadialGradientPaint;
/*     */ import java.awt.Window;
/*     */ import java.awt.geom.Point2D;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.ChartTheme;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.StandardChartTheme;
/*     */ import org.jfree.chart.plot.PiePlot;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.chart.title.Title;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ import org.jfree.data.general.PieDataset;
/*     */ import org.jfree.ui.ApplicationFrame;
/*     */ import org.jfree.ui.HorizontalAlignment;
/*     */ import org.jfree.ui.RectangleEdge;
/*     */ import org.jfree.ui.RectangleInsets;
/*     */ import org.jfree.ui.RefineryUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PieChartDemo1
/*     */   extends ApplicationFrame
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   static {
/*  79 */     ChartFactory.setChartTheme((ChartTheme)new StandardChartTheme("JFree/Shadow", true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PieChartDemo1(String title) {
/*  89 */     super(title);
/*  90 */     setContentPane(createDemoPanel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static PieDataset createDataset() {
/* 101 */     DefaultPieDataset dataset = new DefaultPieDataset();
/* 102 */     dataset.setValue("Samsung", new Double(27.8D));
/* 103 */     dataset.setValue("Others", new Double(55.3D));
/* 104 */     dataset.setValue("Nokia", new Double(16.8D));
/* 105 */     dataset.setValue("Apple", new Double(17.1D));
/* 106 */     return (PieDataset)dataset;
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
/*     */   private static JFreeChart createChart(PieDataset dataset) {
/* 118 */     JFreeChart chart = ChartFactory.createPieChart("Smart Phones Manufactured / Q3 2011", dataset, false, true, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     chart.setBackgroundPaint(new GradientPaint(new Point(0, 0), new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));
/*     */ 
/*     */ 
/*     */     
/* 131 */     TextTitle t = chart.getTitle();
/* 132 */     t.setHorizontalAlignment(HorizontalAlignment.LEFT);
/* 133 */     t.setPaint(new Color(240, 240, 240));
/* 134 */     t.setFont(new Font("Arial", 1, 26));
/*     */     
/* 136 */     PiePlot plot = (PiePlot)chart.getPlot();
/* 137 */     plot.setBackgroundPaint(null);
/* 138 */     plot.setInteriorGap(0.04D);
/* 139 */     plot.setOutlineVisible(false);
/*     */ 
/*     */     
/* 142 */     plot.setSectionPaint("Others", createGradientPaint(new Color(200, 200, 255), Color.BLUE));
/* 143 */     plot.setSectionPaint("Samsung", createGradientPaint(new Color(255, 200, 200), Color.RED));
/* 144 */     plot.setSectionPaint("Apple", createGradientPaint(new Color(200, 255, 200), Color.GREEN));
/* 145 */     plot.setSectionPaint("Nokia", createGradientPaint(new Color(200, 255, 200), Color.YELLOW));
/* 146 */     plot.setBaseSectionOutlinePaint(Color.WHITE);
/* 147 */     plot.setSectionOutlinesVisible(true);
/* 148 */     plot.setBaseSectionOutlineStroke(new BasicStroke(2.0F));
/*     */ 
/*     */     
/* 151 */     plot.setLabelFont(new Font("Courier New", 1, 20));
/* 152 */     plot.setLabelLinkPaint(Color.WHITE);
/* 153 */     plot.setLabelLinkStroke(new BasicStroke(2.0F));
/* 154 */     plot.setLabelOutlineStroke(null);
/* 155 */     plot.setLabelPaint(Color.WHITE);
/* 156 */     plot.setLabelBackgroundPaint(null);
/*     */ 
/*     */     
/* 159 */     TextTitle source = new TextTitle("Source: http://www.bbc.co.uk/news/business-15489523", new Font("Courier New", 0, 12));
/*     */     
/* 161 */     source.setPaint(Color.WHITE);
/* 162 */     source.setPosition(RectangleEdge.BOTTOM);
/* 163 */     source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
/* 164 */     chart.addSubtitle((Title)source);
/* 165 */     return chart;
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
/*     */   private static RadialGradientPaint createGradientPaint(Color c1, Color c2) {
/* 178 */     Point2D center = new Point2D.Float(0.0F, 0.0F);
/* 179 */     float radius = 200.0F;
/* 180 */     float[] dist = { 0.0F, 1.0F };
/* 181 */     return new RadialGradientPaint(center, radius, dist, new Color[] { c1, c2 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static JPanel createDemoPanel() {
/* 191 */     JFreeChart chart = createChart(createDataset());
/* 192 */     chart.setPadding(new RectangleInsets(4.0D, 8.0D, 2.0D, 2.0D));
/* 193 */     ChartPanel panel = new ChartPanel(chart);
/* 194 */     panel.setMouseWheelEnabled(true);
/* 195 */     panel.setPreferredSize(new Dimension(600, 300));
/* 196 */     return (JPanel)panel;
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
/*     */   
/*     */   public static void main(String[] args) {
/* 214 */     PieChartDemo1 demo = new PieChartDemo1("JFreeChart: Pie Chart Demo 1");
/* 215 */     demo.pack();
/* 216 */     RefineryUtilities.centerFrameOnScreen((Window)demo);
/* 217 */     demo.setVisible(true);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/demo/PieChartDemo1.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */