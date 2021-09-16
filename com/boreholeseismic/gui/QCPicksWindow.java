/*     */ package com.boreholeseismic.gui;
/*     */ 
/*     */ import com.boreholeseismic.dsp.BandPassTrace;
/*     */ import com.boreholeseismic.dsp.DiffTraces;
/*     */ import com.boreholeseismic.dsp.EnvelopeTraces;
/*     */ import com.boreholeseismic.dsp.FilterSettings;
/*     */ import com.boreholeseismic.dsp.MoveOutCorrect;
/*     */ import com.boreholeseismic.dsp.PhaseShiftTraces;
/*     */ import com.boreholeseismic.dsp.RemoveNaN;
/*     */ import com.boreholeseismic.dsp.integrateTrace;
/*     */ import com.boreholeseismic.dsp.normTrace3C;
/*     */ import com.boreholeseismic.io.readMAT;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.Container;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseWheelEvent;
/*     */ import java.awt.event.MouseWheelListener;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartMouseEvent;
/*     */ import org.jfree.chart.ChartMouseListener;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.annotations.XYAnnotation;
/*     */ import org.jfree.chart.annotations.XYTextAnnotation;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.data.xy.DefaultXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QCPicksWindow
/*     */   implements MouseWheelListener, ChartMouseListener
/*     */ {
/*     */   private JFrame parentFrame;
/*     */   private JDialog dialog;
/*     */   public boolean confirm = false;
/*     */   JPanel mainPanel;
/*     */   JPanel graphPanel;
/*     */   JPanel buttonsPanel;
/*     */   JButton b1;
/*     */   JButton b2;
/*     */   double[][] traces;
/*     */   double[][] pickP;
/*     */   double[][] pickS;
/*     */   double[][] pickSS;
/*     */   double[][] traceData;
/*     */   readMAT inputMAT;
/*     */   
/*     */   public QCPicksWindow(readMAT MATInfo, double[][] pickP, double[][] pickS, double[][] pickSS) {
/* 152 */     this.inputMAT = MATInfo;
/* 153 */     this.pickP = pickP;
/* 154 */     this.pickS = pickS;
/* 155 */     this.pickSS = pickSS;
/* 156 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/* 162 */     this.dialog = new JDialog(this.parentFrame, "QC Picks", true);
/* 163 */     this.dialog.setContentPane(createPane());
/*     */     
/* 165 */     Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();
/* 166 */     this.dialog.setSize((new Double(Size.getWidth() * 0.8D)).intValue(), (new Double(Size.getHeight() * 0.4D)).intValue());
/* 167 */     this.dialog.setLocation((new Double(Size.getWidth() * 0.1D)).intValue(), (new Double(Size.getHeight() * 0.1D)).intValue());
/* 168 */     this.dialog.setResizable(false);
/* 169 */     this.dialog.setDefaultCloseOperation(0);
/*     */ 
/*     */ 
/*     */     
/* 173 */     initData();
/*     */ 
/*     */     
/* 176 */     this.dialog.setVisible(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void initData() {
/* 182 */     RemoveNaN rm1 = new RemoveNaN(this.inputMAT.getTraceData());
/* 183 */     this.inputMAT.setTraceData(rm1.getTraceData());
/*     */     
/* 185 */     MatrixScalarMultiply mtScalar = new MatrixScalarMultiply(this.inputMAT.getTraceData(), Math.pow(10.0D, 15.0D));
/* 186 */     this.inputMAT.setTraceData(mtScalar.getTraceData());
/*     */ 
/*     */     
/* 189 */     integrateTrace intTrc = new integrateTrace(this.inputMAT.getTraceData(), this.inputMAT.timeSample * 0.001D);
/* 190 */     this.inputMAT.setTraceData(intTrc.getTraceData());
/*     */     
/* 192 */     FilterSettings filterSettings = new FilterSettings();
/*     */ 
/*     */     
/* 195 */     BandPassTrace filtTrc = new BandPassTrace(this.inputMAT.getTraceData(), 
/* 196 */         filterSettings.BPLow, 
/* 197 */         filterSettings.BPHigh, 
/* 198 */         filterSettings.BPEdge, 
/* 199 */         1.0E-5D, 
/* 200 */         this.inputMAT.timeSample * 0.001D);
/* 201 */     this.inputMAT.setTraceData(filtTrc.getTraceData());
/*     */     
/* 203 */     EnvelopeTraces envTraces = new EnvelopeTraces(this.inputMAT.getTraceData());
/* 204 */     this.inputMAT.setTraceData(envTraces.getTraceData());
/*     */     
/* 206 */     DiffTraces diffTraces = new DiffTraces(this.inputMAT.getTraceData());
/* 207 */     this.inputMAT.setTraceData(diffTraces.getTraceData());
/*     */     
/* 209 */     PhaseShiftTraces phaseShiftTraces = new PhaseShiftTraces(this.inputMAT.getTraceData());
/* 210 */     this.inputMAT.setTraceData(phaseShiftTraces.getTraceData());
/*     */     
/* 212 */     filtTrc = new BandPassTrace(this.inputMAT.getTraceData(), 
/* 213 */         filterSettings.BPLow, 
/* 214 */         filterSettings.BPHigh, 
/* 215 */         filterSettings.BPEdge, 
/* 216 */         1.0E-5D, 
/* 217 */         this.inputMAT.timeSample * 0.001D);
/* 218 */     this.inputMAT.setTraceData(filtTrc.getTraceData());
/*     */     
/* 220 */     MoveOutCorrect moveOutCorrectTraces = new MoveOutCorrect(this.inputMAT.getTraceData(), this.pickP, this.pickS, this.inputMAT.timeSample);
/* 221 */     this.inputMAT.setTraceData(moveOutCorrectTraces.getTraceData());
/*     */     
/* 223 */     envTraces = new EnvelopeTraces(this.inputMAT.getTraceData());
/* 224 */     this.inputMAT.setTraceData(envTraces.getTraceData());
/*     */     
/* 226 */     normTrace3C normTraces = new normTrace3C(this.inputMAT.getTraceData(), -0.85D, true);
/* 227 */     this.inputMAT.setTraceData(normTraces.getTraceData());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     ArrayList<XYTextAnnotation> pShape = new ArrayList<>();
/* 237 */     ArrayList<XYTextAnnotation> sShape = new ArrayList<>();
/* 238 */     ArrayList<XYTextAnnotation> ssShape = new ArrayList<>();
/*     */ 
/*     */     
/* 241 */     Color markerP = new Color(0, 100, 0);
/* 242 */     Color markerS = new Color(100, 0, 0);
/* 243 */     Color markerSS = new Color(0, 100, 0);
/*     */ 
/*     */     
/* 246 */     JFreeChart xylineChart = ChartFactory.createXYLineChart(
/* 247 */         "", "Time (ms)", "Receiver No.", 
/* 248 */         createDataset(this.inputMAT), PlotOrientation.VERTICAL, false, false, false);
/*     */     
/* 250 */     xylineChart.setBackgroundPaint(Color.white);
/* 251 */     xylineChart.setTitle(new TextTitle("QC Plot", 
/* 252 */           new Font("Avenir Book", 1, 14)));
/* 253 */     xylineChart.getTitle().setExpandToFitSpace(false);
/*     */     
/* 255 */     xylineChart.setAntiAlias(false);
/*     */ 
/*     */     
/* 258 */     XYPlot tracePlot = xylineChart.getXYPlot();
/* 259 */     tracePlot.setBackgroundPaint(Color.white);
/* 260 */     tracePlot.setDomainGridlinePaint(new Color(100, 100, 100));
/* 261 */     tracePlot.setRangeGridlinePaint(new Color(100, 100, 100));
/* 262 */     tracePlot.setDomainPannable(true);
/* 263 */     tracePlot.setRangePannable(true);
/* 264 */     tracePlot.setDomainCrosshairVisible(true);
/* 265 */     tracePlot.setDomainCrosshairLockedOnData(true);
/* 266 */     tracePlot.setRangeCrosshairVisible(true);
/* 267 */     tracePlot.setRangeCrosshairLockedOnData(true);
/*     */     
/* 269 */     NumberAxis xAxis = (NumberAxis)tracePlot.getDomainAxis();
/* 270 */     xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/* 271 */     xAxis.setLabelFont(new Font("Avenir Book", 1, 12));
/* 272 */     xAxis.setTickLabelFont(new Font("Avenir Book", 0, 10));
/* 273 */     xAxis.setAutoRange(true);
/*     */     
/* 275 */     NumberAxis yAxis = (NumberAxis)tracePlot.getRangeAxis();
/* 276 */     yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/* 277 */     yAxis.setInverted(true);
/* 278 */     yAxis.setAutoRangeIncludesZero(false);
/* 279 */     yAxis.setLabelFont(new Font("Avenir Book", 1, 12));
/* 280 */     yAxis.setTickLabelFont(new Font("Avenir Book", 0, 10));
/* 281 */     yAxis.setAutoRange(true);
/*     */     
/* 283 */     XYItemRenderer renderer = tracePlot.getRenderer();
/* 284 */     for (int i = 0; i < tracePlot.getSeriesCount(); i += 3) {
/*     */       
/* 286 */       renderer.setSeriesPaint(i, new Color(255, 96, 96));
/* 287 */       renderer.setSeriesPaint(i + 1, Color.LIGHT_GRAY);
/* 288 */       renderer.setSeriesPaint(i + 2, new Color(64, 64, 255));
/*     */     } 
/*     */     
/* 291 */     XYTextAnnotation temp = new XYTextAnnotation("x", moveOutCorrectTraces.pFlatten, 1.0D);
/* 292 */     temp.setFont(new Font("Avenir Book", 1, 20));
/* 293 */     temp.setPaint(markerP);
/* 294 */     tracePlot.getRenderer().addAnnotation((XYAnnotation)temp);
/*     */     
/* 296 */     temp = new XYTextAnnotation("x", moveOutCorrectTraces.sFlatten, 2.0D);
/* 297 */     temp.setFont(new Font("Avenir Book", 1, 20));
/* 298 */     temp.setPaint(markerP);
/* 299 */     tracePlot.getRenderer().addAnnotation((XYAnnotation)temp);
/*     */     
/* 301 */     temp = new XYTextAnnotation("x", moveOutCorrectTraces.sFlatten, 3.0D);
/* 302 */     temp.setFont(new Font("Avenir Book", 1, 20));
/* 303 */     temp.setPaint(markerP);
/* 304 */     tracePlot.getRenderer().addAnnotation((XYAnnotation)temp);
/*     */ 
/*     */     
/* 307 */     temp = new XYTextAnnotation("x", normTraces.maxPoints[0] * this.inputMAT.timeSample, 1.0D);
/* 308 */     temp.setFont(new Font("Avenir Book", 1, 20));
/* 309 */     temp.setPaint(markerS);
/* 310 */     tracePlot.getRenderer().addAnnotation((XYAnnotation)temp);
/*     */     
/* 312 */     temp = new XYTextAnnotation("x", normTraces.maxPoints[1] * this.inputMAT.timeSample, 2.0D);
/* 313 */     temp.setFont(new Font("Avenir Book", 1, 20));
/* 314 */     temp.setPaint(markerS);
/* 315 */     tracePlot.getRenderer().addAnnotation((XYAnnotation)temp);
/*     */     
/* 317 */     temp = new XYTextAnnotation("x", normTraces.maxPoints[2] * this.inputMAT.timeSample, 3.0D);
/* 318 */     temp.setFont(new Font("Avenir Book", 1, 20));
/* 319 */     temp.setPaint(markerS);
/* 320 */     tracePlot.getRenderer().addAnnotation((XYAnnotation)temp);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     ChartPanel chartPanel = new ChartPanel(xylineChart, false);
/* 330 */     chartPanel.setMaximumDrawHeight(8000);
/* 331 */     chartPanel.setMaximumDrawWidth(8000);
/* 332 */     chartPanel.setDisplayToolTips(false);
/* 333 */     chartPanel.setPopupMenu(null);
/*     */     
/* 335 */     chartPanel.setPreferredSize(new Dimension(this.graphPanel.getWidth() - 20, this.graphPanel.getHeight() - 40));
/* 336 */     chartPanel.setBackground(Color.white);
/* 337 */     chartPanel.setDomainZoomable(true);
/* 338 */     chartPanel.setRangeZoomable(true);
/* 339 */     chartPanel.setFocusable(true);
/* 340 */     chartPanel.setVisible(true);
/* 341 */     chartPanel.setAutoscrolls(true);
/* 342 */     chartPanel.setFillZoomRectangle(false);
/* 343 */     chartPanel.setZoomOutlinePaint(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/* 344 */     chartPanel.addMouseWheelListener(this);
/*     */     
/* 346 */     chartPanel.setZoomInFactor(0.9D);
/* 347 */     chartPanel.setZoomOutFactor(1.1D);
/*     */     
/* 349 */     chartPanel.addChartMouseListener(this);
/* 350 */     chartPanel.updateUI();
/*     */     
/* 352 */     this.graphPanel.add((Component)chartPanel);
/* 353 */     this.graphPanel.updateUI();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Container createPane() {
/* 361 */     Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();
/*     */     
/* 363 */     this.mainPanel = new JPanel();
/* 364 */     this.mainPanel.setLayout((LayoutManager)null);
/*     */ 
/*     */     
/* 367 */     this.graphPanel = new JPanel();
/* 368 */     this.graphPanel.setBackground(Color.WHITE);
/* 369 */     this.graphPanel.setSize((new Double(Size.getWidth() * 0.78D)).intValue(), (new Double(Size.getHeight() * 0.32D)).intValue());
/* 370 */     this.graphPanel.setLocation((new Double(Size.getWidth() * 0.01D)).intValue(), (new Double(Size.getHeight() * 0.01D)).intValue());
/*     */     
/* 372 */     GridBagConstraints c = new GridBagConstraints();
/* 373 */     c.gridx = 0; c.gridy = 0; c.gridwidth = 1; c.gridheight = 9;
/* 374 */     this.mainPanel.add(this.graphPanel);
/*     */     
/* 376 */     this.buttonsPanel = new JPanel();
/* 377 */     this.buttonsPanel.setSize((new Double(Size.getWidth() * 0.78D)).intValue(), (new Double(Size.getHeight() * 0.05D)).intValue());
/* 378 */     this.buttonsPanel.setLocation((new Double(Size.getWidth() * 0.01D)).intValue(), (new Double(Size.getHeight() * 0.34D)).intValue());
/* 379 */     this.mainPanel.add(this.buttonsPanel);
/*     */ 
/*     */     
/* 382 */     this.dialog.setContentPane(this.mainPanel);
/*     */     
/* 384 */     this.b1 = new JButton("Confirm");
/* 385 */     this.b1.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent event) {
/* 387 */             QCPicksWindow.this.confirm = true;
/* 388 */             QCPicksWindow.this.dialog.dispose();
/* 389 */             QCPicksWindow.this.close();
/*     */           }
/*     */         });
/*     */     
/* 393 */     this.b2 = new JButton("Repick");
/* 394 */     this.b2.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent event) {
/* 396 */             QCPicksWindow.this.dialog.dispose();
/* 397 */             QCPicksWindow.this.close();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 402 */     this.buttonsPanel.add(this.b1);
/* 403 */     this.buttonsPanel.add(this.b2);
/*     */     
/* 405 */     return this.mainPanel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void close() {
/* 410 */     this.dialog.dispose();
/* 411 */     this.dialog.setVisible(false);
/*     */   }
/*     */   
/*     */   public void setFrame(JFrame frame) {
/* 415 */     this.parentFrame = frame;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseWheelMoved(MouseWheelEvent e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chartMouseClicked(ChartMouseEvent arg0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chartMouseMoved(ChartMouseEvent arg0) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYDataset createDataset(readMAT MATContent) {
/* 441 */     double[][] nrmtraceData = transposeMatrix(MATContent.getTraceData());
/* 442 */     double[] sampleTimeArray = new double[(nrmtraceData[0]).length];
/*     */     
/* 444 */     for (int k = 0; k < sampleTimeArray.length; k++) {
/* 445 */       sampleTimeArray[k] = k * MATContent.timeSample;
/*     */     }
/* 447 */     DefaultXYDataset dataset = new DefaultXYDataset();
/*     */     
/* 449 */     for (int j = 0; j < nrmtraceData.length; j++) {
/*     */       
/* 451 */       double[][] data = { sampleTimeArray, nrmtraceData[j] };
/* 452 */       dataset.addSeries("Series " + Integer.toString(j), data);
/*     */     } 
/* 454 */     return (XYDataset)dataset;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] transposeMatrix(double[][] varName) {
/* 459 */     double[][] returnVar = new double[(varName[0]).length][varName.length];
/*     */     
/* 461 */     for (int i = 0; i < (varName[0]).length; i++) {
/*     */       
/* 463 */       for (int j = 0; j < varName.length; j++) {
/* 464 */         returnVar[i][j] = varName[j][i];
/*     */       }
/*     */     } 
/* 467 */     return returnVar;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/QCPicksWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */