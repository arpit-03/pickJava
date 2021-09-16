/*     */ package com.boreholeseismic.io;
/*     */ 
/*     */ import com.boreholeseismic.dsp.BandPassTrace;
/*     */ import com.boreholeseismic.dsp.Detrend;
/*     */ import com.boreholeseismic.dsp.FilterSettings;
/*     */ import com.boreholeseismic.dsp.RemoveNaN;
/*     */ import com.boreholeseismic.dsp.integrateTrace;
/*     */ import com.boreholeseismic.dsp.normTrace3C;
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Color;
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.LayoutManager;
/*     */ import java.awt.Rectangle;
/*     */ import java.io.File;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.jfree.chart.ChartFactory;
/*     */ import org.jfree.chart.ChartPanel;
/*     */ import org.jfree.chart.ChartUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.annotations.XYAnnotation;
/*     */ import org.jfree.chart.annotations.XYLineAnnotation;
/*     */ import org.jfree.chart.annotations.XYTextAnnotation;
/*     */ import org.jfree.chart.axis.NumberAxis;
/*     */ import org.jfree.chart.axis.ValueAxis;
/*     */ import org.jfree.chart.plot.Plot;
/*     */ import org.jfree.chart.plot.PlotOrientation;
/*     */ import org.jfree.chart.plot.XYPlot;
/*     */ import org.jfree.chart.renderer.LookupPaintScale;
/*     */ import org.jfree.chart.renderer.PaintScale;
/*     */ import org.jfree.chart.renderer.xy.XYBlockRenderer;
/*     */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*     */ import org.jfree.chart.title.TextTitle;
/*     */ import org.jfree.data.xy.DefaultXYDataset;
/*     */ import org.jfree.data.xy.DefaultXYZDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.ui.TextAnchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SavingTraces
/*     */ {
/*     */   public SavingTraces(String InputFolder, FilterSettings f, String DISPLAY_MODE, String NORMALIZATION_MODE) {
/*     */     try {
/*  66 */       saveTraces(InputFolder, f, DISPLAY_MODE, NORMALIZATION_MODE);
/*  67 */     } catch (Exception e) {
/*     */       
/*  69 */       JOptionPane.showMessageDialog(new JFrame("Save Read Error"), "Could not save previous file JPEG. Some Error.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveTraces(String fileName, FilterSettings f, String DISPLAY_MODE, String NORMALIZATION_MODE) throws Exception {
/*     */     XYPlot tracePlot;
/*  77 */     readMAT MATInfo = new readMAT(fileName);
/*     */     
/*  79 */     if (!MATInfo.errorFree) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     RemoveNaN rm1 = new RemoveNaN(MATInfo.getTraceData());
/*  87 */     MATInfo.setTraceData(rm1.getTraceData());
/*     */     
/*  89 */     if (f.DetrendFlag) {
/*     */       
/*  91 */       Detrend detrendTrc = new Detrend(MATInfo.getTraceData());
/*  92 */       MATInfo.setTraceData(detrendTrc.getTraceData());
/*     */     } 
/*     */ 
/*     */     
/*  96 */     if (f.IntFlag) {
/*  97 */       integrateTrace intTrc = new integrateTrace(MATInfo.getTraceData(), MATInfo.timeSample * 0.001D);
/*  98 */       MATInfo.setTraceData(intTrc.getTraceData());
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (f.BPFlag) {
/*     */       
/* 104 */       BandPassTrace filtTrc = new BandPassTrace(MATInfo.getTraceData(), 
/* 105 */           f.BPLow, 
/* 106 */           f.BPHigh, 
/* 107 */           f.BPEdge, 
/* 108 */           1.0E-5D, 
/* 109 */           MATInfo.timeSample * 0.001D);
/* 110 */       MATInfo.setTraceData(filtTrc.getTraceData());
/*     */     } 
/*     */     
/* 113 */     normTrace3C normTrc = new normTrace3C(MATInfo.getTraceData(), DISPLAY_MODE, NORMALIZATION_MODE);
/* 114 */     MATInfo.setTraceData(normTrc.getTraceData());
/*     */     
/* 116 */     JFreeChart xylineChart = ChartFactory.createXYLineChart(
/* 117 */         "", "Time (ms)", "Receiver Number", 
/* 118 */         createDataset(MATInfo), PlotOrientation.VERTICAL, false, false, false);
/*     */     
/* 120 */     File inputFile = new File(fileName);
/*     */ 
/*     */ 
/*     */     
/* 124 */     if (DISPLAY_MODE == "Wiggle") {
/*     */       
/* 126 */       xylineChart = ChartFactory.createXYLineChart(
/* 127 */           "", "Time (ms)", "Receiver Number", 
/* 128 */           createDataset(MATInfo), PlotOrientation.VERTICAL, false, false, false);
/*     */       
/* 130 */       xylineChart.setBackgroundPaint(Color.white);
/* 131 */       xylineChart.setTitle(new TextTitle("File: " + inputFile.getName(), 
/* 132 */             new Font("Avenir Book", 1, 30)));
/*     */ 
/*     */       
/* 135 */       tracePlot = xylineChart.getXYPlot();
/* 136 */       tracePlot.setBackgroundPaint(Color.white);
/* 137 */       tracePlot.setDomainGridlinePaint(new Color(100, 100, 100));
/* 138 */       tracePlot.setRangeGridlinePaint(new Color(100, 100, 100));
/* 139 */       tracePlot.setDomainPannable(true);
/* 140 */       tracePlot.setRangePannable(true);
/* 141 */       tracePlot.setDomainCrosshairVisible(false);
/* 142 */       tracePlot.setDomainCrosshairLockedOnData(true);
/* 143 */       tracePlot.setRangeCrosshairVisible(false);
/* 144 */       tracePlot.setRangeCrosshairLockedOnData(true);
/*     */ 
/*     */       
/* 147 */       NumberAxis xAxis = (NumberAxis)tracePlot.getDomainAxis();
/* 148 */       xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/* 149 */       xAxis.setLabelFont(new Font("Avenir Book", 1, 24));
/* 150 */       xAxis.setTickLabelFont(new Font("Avenir Book", 0, 21));
/* 151 */       xAxis.setAutoRange(true);
/*     */       
/* 153 */       NumberAxis yAxis = (NumberAxis)tracePlot.getRangeAxis();
/* 154 */       yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/* 155 */       yAxis.setInverted(true);
/* 156 */       yAxis.setAutoRangeIncludesZero(false);
/* 157 */       yAxis.setLabelFont(new Font("Avenir Book", 1, 24));
/* 158 */       yAxis.setTickLabelFont(new Font("Avenir Book", 0, 21));
/* 159 */       yAxis.setAutoRange(true);
/*     */       
/* 161 */       if (MATInfo.auxDataPresent) {
/* 162 */         yAxis.setRange(0.0D, MATInfo.noRec + 2.0D);
/*     */       } else {
/*     */         
/* 165 */         yAxis.setRange(0.0D, MATInfo.noRec + 1.0D);
/*     */       } 
/*     */       
/* 168 */       XYItemRenderer renderer = tracePlot.getRenderer();
/*     */       
/* 170 */       for (int j = 0; j < tracePlot.getSeriesCount(); j += 3)
/*     */       {
/* 172 */         renderer.setSeriesPaint(j, new Color(255, 96, 96));
/* 173 */         renderer.setSeriesPaint(j + 1, Color.LIGHT_GRAY);
/* 174 */         renderer.setSeriesPaint(j + 2, new Color(64, 64, 255));
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 180 */       DefaultXYZDataset dataset = new DefaultXYZDataset();
/*     */       
/* 182 */       double[][] traceData = MATInfo.getTraceData();
/*     */ 
/*     */       
/* 185 */       double minTrace = 0.0D, maxTrace = 0.0D;
/*     */       
/* 187 */       for (int j = 1; j <= MATInfo.noRec; j++) {
/*     */         
/* 189 */         double[] xvalues = new double[traceData.length];
/* 190 */         double[] yvalues = new double[traceData.length];
/* 191 */         double[] zvalues = new double[traceData.length];
/*     */         
/* 193 */         for (int m = 0; m < traceData.length; m++) {
/* 194 */           xvalues[m] = m * MATInfo.timeSample;
/* 195 */           yvalues[m] = j;
/* 196 */           zvalues[m] = (traceData[m][3 * j - 3] + traceData[m][3 * j - 2] + traceData[m][3 * j - 1]) / 3.0D;
/*     */         } 
/*     */         
/* 199 */         minTrace = getMin(zvalues, minTrace);
/* 200 */         maxTrace = getMax(zvalues, maxTrace);
/*     */         
/* 202 */         dataset.addSeries("Series" + Integer.toString(j + 1), new double[][] { xvalues, yvalues, zvalues });
/*     */       } 
/*     */       
/* 205 */       NumberAxis xAxis = new NumberAxis("Time (ms)");
/* 206 */       xAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
/* 207 */       xAxis.setLabelFont(new Font("Avenir Book", 1, 32));
/* 208 */       xAxis.setTickLabelFont(new Font("Avenir Book", 0, 24));
/* 209 */       xAxis.setAutoRange(true);
/* 210 */       xAxis.setRange(0.0D, 1.02D * MATInfo.noSample * MATInfo.timeSample);
/*     */       
/* 212 */       NumberAxis yAxis = new NumberAxis("Receiver number");
/* 213 */       yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
/* 214 */       yAxis.setInverted(true);
/* 215 */       yAxis.setAutoRangeIncludesZero(false);
/* 216 */       yAxis.setLabelFont(new Font("Avenir Book", 1, 32));
/* 217 */       yAxis.setTickLabelFont(new Font("Avenir Book", 0, 24));
/* 218 */       yAxis.setAutoRange(true);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 223 */       NumberAxis valueAxis1 = new NumberAxis("Marker");
/* 224 */       valueAxis1.setLowerMargin(0.0D);
/* 225 */       valueAxis1.setUpperMargin(0.0D);
/* 226 */       valueAxis1.setVisible(false);
/*     */ 
/*     */       
/* 229 */       LookupPaintScale paintScale = new LookupPaintScale(-1.0D, 1.0D, Color.black);
/*     */       
/* 231 */       for (int k = 0; k <= 51; k++) {
/* 232 */         double dataPoint = minTrace + k * (maxTrace - minTrace) / 51.0D;
/* 233 */         paintScale.add(dataPoint, new Color(4 * k, 4 * k, 4 * k));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 238 */       tracePlot = new XYPlot((XYDataset)dataset, (ValueAxis)xAxis, (ValueAxis)yAxis, (XYItemRenderer)new XYBlockRenderer());
/* 239 */       ((XYBlockRenderer)tracePlot.getRenderer()).setPaintScale((PaintScale)paintScale);
/*     */ 
/*     */       
/* 242 */       tracePlot.setDomainCrosshairVisible(true);
/* 243 */       tracePlot.setDomainCrosshairLockedOnData(true);
/* 244 */       tracePlot.setRangeCrosshairVisible(true);
/* 245 */       tracePlot.setRangeCrosshairLockedOnData(true);
/*     */       
/* 247 */       xylineChart = new JFreeChart(null, null, (Plot)tracePlot, false);
/* 248 */       xylineChart.setTitle(new TextTitle("Plot from File : " + inputFile.getName(), 
/* 249 */             new Font("Avenir Book", 1, 18)));
/*     */       
/* 251 */       xylineChart.getTitle().setExpandToFitSpace(false);
/*     */       
/* 253 */       tracePlot.setBackgroundPaint(Color.white);
/* 254 */       tracePlot.setDomainGridlinePaint(new Color(100, 100, 100));
/* 255 */       tracePlot.setRangeGridlinePaint(new Color(100, 100, 100));
/* 256 */       xylineChart.setBackgroundPaint(Color.white);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 263 */     String[] toolNamesArray = null;
/*     */     
/* 265 */     toolNamesArray = f.toolstringNames.replace("[", "").replace("]", "").split(",");
/*     */     
/*     */     try {
/*     */       double[] wellNoRec;
/*     */       
/* 270 */       String wellNoRecString = f.noOfRecsString;
/*     */ 
/*     */       
/* 273 */       if ((wellNoRecString.contentEquals("[]") | wellNoRecString.contentEquals("[,]") | wellNoRecString.contentEquals("")) != 0) {
/* 274 */         wellNoRec = new double[] { MATInfo.noRec };
/*     */       } else {
/* 276 */         wellNoRec = textToArray(wellNoRecString);
/*     */       } 
/*     */ 
/*     */       
/* 280 */       float y = 0.0F, previous = 0.0F;
/* 281 */       for (int n = 0; n < toolNamesArray.length; n++) {
/*     */         
/* 283 */         y = (float)(0.5D * wellNoRec[n]) + previous;
/* 284 */         previous = (float)(previous + wellNoRec[n]);
/*     */         
/* 286 */         XYTextAnnotation tempTemp = new XYTextAnnotation(toolNamesArray[n], 1.01D * MATInfo.noSample * MATInfo.timeSample, y);
/* 287 */         tempTemp.setRotationAngle(1.5707963267948966D);
/* 288 */         tempTemp.setTextAnchor(TextAnchor.BASELINE_CENTER);
/*     */         
/* 290 */         tempTemp.setFont(new Font("Avenir Book", 1, 25));
/* 291 */         tempTemp.setPaint(new Color(0, 100, 0));
/* 292 */         tracePlot.getRenderer().addAnnotation((XYAnnotation)tempTemp);
/*     */       } 
/*     */ 
/*     */       
/* 296 */       double[] plotLineY = new double[wellNoRec.length];
/* 297 */       plotLineY[0] = wellNoRec[0];
/*     */       int j;
/* 299 */       for (j = 1; j < wellNoRec.length; j++) {
/*     */         
/* 301 */         for (int k = 0; k <= j; k++)
/*     */         {
/* 303 */           plotLineY[j] = plotLineY[j] + wellNoRec[k];
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 308 */       for (j = 0; j < plotLineY.length; j++)
/*     */       {
/* 310 */         plotLineY[j] = plotLineY[j] + 0.5D;
/*     */       }
/*     */ 
/*     */       
/* 314 */       for (j = 0; j < plotLineY.length; j++) {
/* 315 */         tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(0.0D, plotLineY[j], MATInfo.noSample * MATInfo.timeSample, plotLineY[j], 
/* 316 */               new BasicStroke(2.0F, 1, 1), Color.black));
/*     */       
/*     */       }
/*     */     }
/* 320 */     catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     double[][] pickP = MATInfo.pickOutP;
/* 339 */     double[][] pickS = MATInfo.pickOutS;
/* 340 */     double[][] pickSS = MATInfo.pickOutSS;
/* 341 */     double[][] pickR1 = MATInfo.pickOutR1;
/* 342 */     double[][] pickR2 = MATInfo.pickOutR2;
/* 343 */     double[][] pickR3 = MATInfo.pickOutR3;
/*     */ 
/*     */ 
/*     */     
/* 347 */     Color lineP = Color.YELLOW;
/* 348 */     Color lineS = new Color(127, 255, 0);
/* 349 */     Color lineSS = Color.YELLOW;
/* 350 */     Color lineR1 = Color.ORANGE;
/* 351 */     Color lineR2 = Color.ORANGE;
/* 352 */     Color lineR3 = Color.ORANGE;
/* 353 */     Color markerP = new Color(0, 100, 0);
/* 354 */     Color markerS = new Color(0, 100, 0);
/* 355 */     Color markerSS = new Color(0, 100, 0);
/* 356 */     Color markerR1 = new Color(169, 169, 169);
/* 357 */     Color markerR2 = new Color(169, 169, 169);
/* 358 */     Color markerR3 = new Color(169, 169, 169);
/*     */     
/* 360 */     for (int i = 0; i < pickP.length; i++) {
/*     */       
/* 362 */       if (i < pickP.length - 1) {
/*     */ 
/*     */         
/* 365 */         xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(pickP[i][1], pickP[i][0], pickP[i + 1][1], pickP[i + 1][0], 
/* 366 */               new BasicStroke(1.5F, 1, 1), lineP));
/*     */         
/* 368 */         xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(pickS[i][1], pickS[i][0], pickS[i + 1][1], pickS[i + 1][0], 
/* 369 */               new BasicStroke(1.5F, 1, 1), lineS));
/*     */         
/* 371 */         xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(pickSS[i][1], pickSS[i][0], pickSS[i + 1][1], pickSS[i + 1][0], 
/* 372 */               new BasicStroke(1.5F, 1, 1), lineSS));
/*     */ 
/*     */         
/* 375 */         xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(pickR1[i][1], pickR1[i][0], pickR1[i + 1][1], pickR1[i + 1][0], 
/* 376 */               new BasicStroke(1.5F, 1, 1), lineR1));
/*     */         
/* 378 */         xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(pickR2[i][1], pickR2[i][0], pickR2[i + 1][1], pickR2[i + 1][0], 
/* 379 */               new BasicStroke(1.5F, 1, 1), lineR2));
/*     */         
/* 381 */         xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(pickR3[i][1], pickR3[i][0], pickR3[i + 1][1], pickR3[i + 1][0], 
/* 382 */               new BasicStroke(1.5F, 1, 1), lineR3));
/*     */       } 
/*     */ 
/*     */       
/* 386 */       XYTextAnnotation temp = new XYTextAnnotation("+", pickP[i][1], pickP[i][0]);
/* 387 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 388 */       temp.setPaint(markerP);
/* 389 */       xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)temp);
/*     */       
/* 391 */       temp = new XYTextAnnotation("x", pickS[i][1], pickS[i][0]);
/* 392 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 393 */       temp.setPaint(markerS);
/* 394 */       xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)temp);
/*     */       
/* 396 */       temp = new XYTextAnnotation("o", pickSS[i][1], pickSS[i][0]);
/* 397 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 398 */       temp.setPaint(markerSS);
/* 399 */       xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)temp);
/*     */       
/* 401 */       temp = new XYTextAnnotation("+", pickR1[i][1], pickR1[i][0]);
/* 402 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 403 */       temp.setPaint(markerR1);
/* 404 */       xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)temp);
/*     */       
/* 406 */       temp = new XYTextAnnotation("x", pickR2[i][1], pickR2[i][0]);
/* 407 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 408 */       temp.setPaint(markerR2);
/* 409 */       xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)temp);
/*     */       
/* 411 */       temp = new XYTextAnnotation("o", pickR3[i][1], pickR3[i][0]);
/* 412 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 413 */       temp.setPaint(markerR3);
/* 414 */       xylineChart.getXYPlot().getRenderer().addAnnotation((XYAnnotation)temp);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 421 */     Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
/*     */     
/* 423 */     JFrame SaveTracesFrame = new JFrame("Save");
/* 424 */     SaveTracesFrame.setBounds((int)(0.2D * r.width) + 10, 40, 
/* 425 */         (int)(0.8D * r.width) - 10 - 5, r.height - 40 - 5 - 20 - 20);
/*     */ 
/*     */     
/* 428 */     SaveTracesFrame.setBounds(0, 0, 
/* 429 */         r.width, r.height);
/*     */ 
/*     */     
/* 432 */     SaveTracesFrame.setLayout((LayoutManager)null);
/*     */ 
/*     */     
/* 435 */     ChartPanel chartPanel = new ChartPanel(xylineChart, false);
/* 436 */     SaveTracesFrame.setContentPane((Container)chartPanel);
/*     */ 
/*     */     
/* 439 */     chartPanel.setMaximumDrawHeight(8000);
/* 440 */     chartPanel.setMaximumDrawWidth(8000);
/* 441 */     chartPanel.setPopupMenu(null);
/* 442 */     chartPanel.setBackground(Color.white);
/* 443 */     chartPanel.setSize(SaveTracesFrame.getSize());
/* 444 */     chartPanel.setLocation(0, 0);
/*     */     
/* 446 */     File saveFileName = new File(fileName.replace("mat", "png"));
/*     */ 
/*     */     
/* 449 */     ChartUtilities.saveChartAsJPEG(saveFileName.getAbsoluteFile(), xylineChart, 1760, 876);
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
/*     */   public static double getMax(double[] inputArray, double predecidedMax) {
/* 466 */     for (int i = 1; i < inputArray.length; i++) {
/* 467 */       if (inputArray[i] > predecidedMax) {
/* 468 */         predecidedMax = inputArray[i];
/*     */       }
/*     */     } 
/* 471 */     return predecidedMax;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getMin(double[] inputArray, double predecidedMin) {
/* 477 */     for (int i = 1; i < inputArray.length; i++) {
/* 478 */       if (inputArray[i] < predecidedMin) {
/* 479 */         predecidedMin = inputArray[i];
/*     */       }
/*     */     } 
/* 482 */     return predecidedMin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYDataset createDataset(readMAT MATContent) {
/* 489 */     double[][] nrmtraceData = transposeMatrix(MATContent.getTraceData());
/* 490 */     double[] sampleTimeArray = new double[(nrmtraceData[0]).length];
/*     */     
/* 492 */     for (int k = 0; k < sampleTimeArray.length; k++) {
/* 493 */       sampleTimeArray[k] = k * MATContent.timeSample;
/*     */     }
/* 495 */     DefaultXYDataset dataset = new DefaultXYDataset();
/*     */     
/* 497 */     for (int j = 0; j < nrmtraceData.length; j++) {
/*     */       
/* 499 */       double[][] data = { sampleTimeArray, nrmtraceData[j] };
/* 500 */       dataset.addSeries("Series " + Integer.toString(j), data);
/*     */     } 
/* 502 */     return (XYDataset)dataset;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] transposeMatrix(double[][] varName) {
/* 507 */     double[][] returnVar = new double[(varName[0]).length][varName.length];
/*     */     
/* 509 */     for (int i = 0; i < (varName[0]).length; i++) {
/*     */       
/* 511 */       for (int j = 0; j < varName.length; j++) {
/* 512 */         returnVar[i][j] = varName[j][i];
/*     */       }
/*     */     } 
/* 515 */     return returnVar;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] textToArray(String wellNoRecString) {
/* 521 */     wellNoRecString = wellNoRecString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").replaceAll(" ", "");
/*     */     
/* 523 */     wellNoRecString = String.valueOf(wellNoRecString) + ",";
/*     */     
/* 525 */     String[] items = wellNoRecString.split(",");
/*     */     
/* 527 */     if (items.length == 0) {
/* 528 */       return null;
/*     */     }
/* 530 */     double[] returnInt = new double[items.length];
/*     */     
/* 532 */     int count = 0; int n;
/* 533 */     for (n = 0; n < items.length; n++) {
/*     */       
/* 535 */       if (items[n] != "") {
/*     */         
/* 537 */         count++;
/* 538 */         returnInt[n] = Double.parseDouble(items[n]);
/*     */       } 
/*     */     } 
/*     */     
/* 542 */     if (count != items.length) {
/*     */       
/* 544 */       returnInt = new double[count];
/* 545 */       count = -1;
/* 546 */       for (n = 0; n < items.length; n++) {
/*     */         
/* 548 */         if (items[n] != "") {
/*     */           
/* 550 */           count++;
/* 551 */           returnInt[count] = Double.parseDouble(items[n]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 556 */     return returnInt;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/io/SavingTraces.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */