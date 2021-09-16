/*      */ package org.jfree.chart.axis;
/*      */ 
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.ValueAxisPlot;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.ui.RectangleEdge;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LogarithmicAxis
/*      */   extends NumberAxis
/*      */ {
/*      */   private static final long serialVersionUID = 2502918599004103054L;
/*  118 */   public static final double LOG10_VALUE = Math.log(10.0D);
/*      */ 
/*      */ 
/*      */   
/*      */   public static final double SMALL_LOG_VALUE = 1.0E-100D;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean allowNegativesFlag = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean strictValuesFlag = true;
/*      */ 
/*      */ 
/*      */   
/*  134 */   protected final NumberFormat numberFormatterObj = NumberFormat.getInstance();
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean expTickLabelsFlag = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean log10TickLabelsFlag = false;
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean autoRangeNextLogFlag = false;
/*      */ 
/*      */   
/*      */   protected boolean smallLogFlag = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public LogarithmicAxis(String label) {
/*  154 */     super(label);
/*  155 */     setupNumberFmtObj();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowNegativesFlag(boolean flgVal) {
/*  166 */     this.allowNegativesFlag = flgVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowNegativesFlag() {
/*  177 */     return this.allowNegativesFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrictValuesFlag(boolean flgVal) {
/*  189 */     this.strictValuesFlag = flgVal;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStrictValuesFlag() {
/*  201 */     return this.strictValuesFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExpTickLabelsFlag(boolean flgVal) {
/*  213 */     this.expTickLabelsFlag = flgVal;
/*  214 */     setupNumberFmtObj();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getExpTickLabelsFlag() {
/*  224 */     return this.expTickLabelsFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLog10TickLabelsFlag(boolean flag) {
/*  234 */     this.log10TickLabelsFlag = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getLog10TickLabelsFlag() {
/*  245 */     return this.log10TickLabelsFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoRangeNextLogFlag(boolean flag) {
/*  258 */     this.autoRangeNextLogFlag = flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoRangeNextLogFlag() {
/*  268 */     return this.autoRangeNextLogFlag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRange(Range range) {
/*  279 */     super.setRange(range);
/*  280 */     setupSmallLogFlag();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupSmallLogFlag() {
/*  290 */     double lowerVal = getRange().getLowerBound();
/*  291 */     this.smallLogFlag = (!this.allowNegativesFlag && lowerVal < 10.0D && lowerVal > 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setupNumberFmtObj() {
/*  300 */     if (this.numberFormatterObj instanceof DecimalFormat)
/*      */     {
/*      */       
/*  303 */       ((DecimalFormat)this.numberFormatterObj).applyPattern(this.expTickLabelsFlag ? "0E0" : "0.###");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double switchedLog10(double val) {
/*  322 */     return this.smallLogFlag ? (Math.log(val) / LOG10_VALUE) : 
/*  323 */       adjustedLog10(val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double switchedPow10(double val) {
/*  341 */     return this.smallLogFlag ? Math.pow(10.0D, val) : adjustedPow10(val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double adjustedLog10(double val) {
/*  359 */     boolean negFlag = (val < 0.0D);
/*  360 */     if (negFlag) {
/*  361 */       val = -val;
/*      */     }
/*  363 */     if (val < 10.0D) {
/*  364 */       val += (10.0D - val) / 10.0D;
/*      */     }
/*      */     
/*  367 */     double res = Math.log(val) / LOG10_VALUE;
/*  368 */     return negFlag ? -res : res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double adjustedPow10(double val) {
/*      */     double res;
/*  386 */     boolean negFlag = (val < 0.0D);
/*  387 */     if (negFlag) {
/*  388 */       val = -val;
/*      */     }
/*      */     
/*  391 */     if (val < 1.0D) {
/*  392 */       res = (Math.pow(10.0D, val + 1.0D) - 10.0D) / 9.0D;
/*      */     } else {
/*      */       
/*  395 */       res = Math.pow(10.0D, val);
/*      */     } 
/*  397 */     return negFlag ? -res : res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double computeLogFloor(double lower) {
/*      */     double logFloor;
/*  413 */     if (this.allowNegativesFlag) {
/*      */       
/*  415 */       if (lower > 10.0D)
/*      */       {
/*  417 */         logFloor = Math.log(lower) / LOG10_VALUE;
/*  418 */         logFloor = Math.floor(logFloor);
/*  419 */         logFloor = Math.pow(10.0D, logFloor);
/*      */       }
/*  421 */       else if (lower < -10.0D)
/*      */       {
/*  423 */         logFloor = Math.log(-lower) / LOG10_VALUE;
/*      */         
/*  425 */         logFloor = Math.floor(-logFloor);
/*      */         
/*  427 */         logFloor = -Math.pow(10.0D, -logFloor);
/*      */       }
/*      */       else
/*      */       {
/*  431 */         logFloor = Math.floor(lower);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  436 */     else if (lower > 0.0D) {
/*      */       
/*  438 */       logFloor = Math.log(lower) / LOG10_VALUE;
/*  439 */       logFloor = Math.floor(logFloor);
/*  440 */       logFloor = Math.pow(10.0D, logFloor);
/*      */     }
/*      */     else {
/*      */       
/*  444 */       logFloor = Math.floor(lower);
/*      */     } 
/*      */     
/*  447 */     return logFloor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double computeLogCeil(double upper) {
/*      */     double logCeil;
/*  463 */     if (this.allowNegativesFlag) {
/*      */       
/*  465 */       if (upper > 10.0D)
/*      */       {
/*      */         
/*  468 */         logCeil = Math.log(upper) / LOG10_VALUE;
/*  469 */         logCeil = Math.ceil(logCeil);
/*  470 */         logCeil = Math.pow(10.0D, logCeil);
/*      */       }
/*  472 */       else if (upper < -10.0D)
/*      */       {
/*      */         
/*  475 */         logCeil = Math.log(-upper) / LOG10_VALUE;
/*      */         
/*  477 */         logCeil = Math.ceil(-logCeil);
/*      */         
/*  479 */         logCeil = -Math.pow(10.0D, -logCeil);
/*      */       }
/*      */       else
/*      */       {
/*  483 */         logCeil = Math.ceil(upper);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  488 */     else if (upper > 0.0D) {
/*      */ 
/*      */       
/*  491 */       logCeil = Math.log(upper) / LOG10_VALUE;
/*  492 */       logCeil = Math.ceil(logCeil);
/*  493 */       logCeil = Math.pow(10.0D, logCeil);
/*      */     }
/*      */     else {
/*      */       
/*  497 */       logCeil = Math.ceil(upper);
/*      */     } 
/*      */     
/*  500 */     return logCeil;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void autoAdjustRange() {
/*  509 */     Plot plot = getPlot();
/*  510 */     if (plot == null) {
/*      */       return;
/*      */     }
/*      */     
/*  514 */     if (plot instanceof ValueAxisPlot) {
/*  515 */       double lower; ValueAxisPlot vap = (ValueAxisPlot)plot;
/*      */ 
/*      */       
/*  518 */       Range r = vap.getDataRange(this);
/*  519 */       if (r == null) {
/*      */         
/*  521 */         r = getDefaultAutoRange();
/*  522 */         lower = r.getLowerBound();
/*      */       }
/*      */       else {
/*      */         
/*  526 */         lower = r.getLowerBound();
/*  527 */         if (this.strictValuesFlag && !this.allowNegativesFlag && lower <= 0.0D)
/*      */         {
/*      */           
/*  530 */           throw new RuntimeException("Values less than or equal to zero not allowed with logarithmic axis");
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*      */       double lowerMargin;
/*      */       
/*  537 */       if (lower > 0.0D && (lowerMargin = getLowerMargin()) > 0.0D) {
/*      */         
/*  539 */         double logLower = Math.log(lower) / LOG10_VALUE;
/*      */         double logAbs;
/*  541 */         if ((logAbs = Math.abs(logLower)) < 1.0D) {
/*  542 */           logAbs = 1.0D;
/*      */         }
/*  544 */         lower = Math.pow(10.0D, logLower - logAbs * lowerMargin);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  549 */       if (this.autoRangeNextLogFlag) {
/*  550 */         lower = computeLogFloor(lower);
/*      */       }
/*      */       
/*  553 */       if (!this.allowNegativesFlag && lower >= 0.0D && lower < 1.0E-100D)
/*      */       {
/*      */         
/*  556 */         lower = r.getLowerBound();
/*      */       }
/*      */       
/*  559 */       double upper = r.getUpperBound();
/*      */       
/*      */       double upperMargin;
/*      */       
/*  563 */       if (upper > 0.0D && (upperMargin = getUpperMargin()) > 0.0D) {
/*      */         
/*  565 */         double logUpper = Math.log(upper) / LOG10_VALUE;
/*      */         double logAbs;
/*  567 */         if ((logAbs = Math.abs(logUpper)) < 1.0D) {
/*  568 */           logAbs = 1.0D;
/*      */         }
/*  570 */         upper = Math.pow(10.0D, logUpper + logAbs * upperMargin);
/*      */       } 
/*      */       
/*  573 */       if (!this.allowNegativesFlag && upper < 1.0D && upper > 0.0D && lower > 0.0D) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  578 */         double expVal = Math.log(upper) / LOG10_VALUE;
/*  579 */         expVal = Math.ceil(-expVal + 0.001D);
/*  580 */         expVal = Math.pow(10.0D, expVal);
/*      */ 
/*      */         
/*  583 */         upper = (expVal > 0.0D) ? (Math.ceil(upper * expVal) / expVal) : Math.ceil(upper);
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  590 */         upper = this.autoRangeNextLogFlag ? computeLogCeil(upper) : Math.ceil(upper);
/*      */       } 
/*      */       
/*  593 */       double minRange = getAutoRangeMinimumSize();
/*  594 */       if (upper - lower < minRange) {
/*  595 */         upper = (upper + lower + minRange) / 2.0D;
/*  596 */         lower = (upper + lower - minRange) / 2.0D;
/*      */ 
/*      */         
/*  599 */         if (upper - lower < minRange) {
/*  600 */           double absUpper = Math.abs(upper);
/*      */           
/*  602 */           double adjVal = (absUpper > 1.0E-100D) ? (absUpper / 100.0D) : 0.01D;
/*      */           
/*  604 */           upper = (upper + lower + adjVal) / 2.0D;
/*  605 */           lower = (upper + lower - adjVal) / 2.0D;
/*      */         } 
/*      */       } 
/*      */       
/*  609 */       setRange(new Range(lower, upper), false, false);
/*  610 */       setupSmallLogFlag();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double valueToJava2D(double value, Rectangle2D plotArea, RectangleEdge edge) {
/*  630 */     Range range = getRange();
/*  631 */     double axisMin = switchedLog10(range.getLowerBound());
/*  632 */     double axisMax = switchedLog10(range.getUpperBound());
/*      */     
/*  634 */     double min = 0.0D;
/*  635 */     double max = 0.0D;
/*  636 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  637 */       min = plotArea.getMinX();
/*  638 */       max = plotArea.getMaxX();
/*      */     }
/*  640 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  641 */       min = plotArea.getMaxY();
/*  642 */       max = plotArea.getMinY();
/*      */     } 
/*      */     
/*  645 */     value = switchedLog10(value);
/*      */     
/*  647 */     if (isInverted()) {
/*  648 */       return max - (value - axisMin) / (axisMax - axisMin) * (max - min);
/*      */     }
/*      */ 
/*      */     
/*  652 */     return min + (value - axisMin) / (axisMax - axisMin) * (max - min);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double java2DToValue(double java2DValue, Rectangle2D plotArea, RectangleEdge edge) {
/*  673 */     Range range = getRange();
/*  674 */     double axisMin = switchedLog10(range.getLowerBound());
/*  675 */     double axisMax = switchedLog10(range.getUpperBound());
/*      */     
/*  677 */     double plotMin = 0.0D;
/*  678 */     double plotMax = 0.0D;
/*  679 */     if (RectangleEdge.isTopOrBottom(edge)) {
/*  680 */       plotMin = plotArea.getX();
/*  681 */       plotMax = plotArea.getMaxX();
/*      */     }
/*  683 */     else if (RectangleEdge.isLeftOrRight(edge)) {
/*  684 */       plotMin = plotArea.getMaxY();
/*  685 */       plotMax = plotArea.getMinY();
/*      */     } 
/*      */     
/*  688 */     if (isInverted()) {
/*  689 */       return switchedPow10(axisMax - (java2DValue - plotMin) / (plotMax - plotMin) * (axisMax - axisMin));
/*      */     }
/*      */ 
/*      */     
/*  693 */     return switchedPow10(axisMin + (java2DValue - plotMin) / (plotMax - plotMin) * (axisMax - axisMin));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void zoomRange(double lowerPercent, double upperPercent) {
/*      */     Range adjusted;
/*  706 */     double startLog = switchedLog10(getRange().getLowerBound());
/*  707 */     double lengthLog = switchedLog10(getRange().getUpperBound()) - startLog;
/*      */ 
/*      */     
/*  710 */     if (isInverted()) {
/*      */ 
/*      */       
/*  713 */       adjusted = new Range(switchedPow10(startLog + lengthLog * (1.0D - upperPercent)), switchedPow10(startLog + lengthLog * (1.0D - lowerPercent)));
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  718 */       adjusted = new Range(switchedPow10(startLog + lengthLog * lowerPercent), switchedPow10(startLog + lengthLog * upperPercent));
/*      */     } 
/*      */     
/*  721 */     setRange(adjusted);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  738 */     List<Tick> ticks = new ArrayList();
/*  739 */     Range range = getRange();
/*      */ 
/*      */     
/*  742 */     double lowerBoundVal = range.getLowerBound();
/*      */ 
/*      */     
/*  745 */     if (this.smallLogFlag && lowerBoundVal < 1.0E-100D) {
/*  746 */       lowerBoundVal = 1.0E-100D;
/*      */     }
/*      */ 
/*      */     
/*  750 */     double upperBoundVal = range.getUpperBound();
/*      */ 
/*      */     
/*  753 */     int iBegCount = (int)Math.rint(switchedLog10(lowerBoundVal));
/*      */     
/*  755 */     int iEndCount = (int)Math.rint(switchedLog10(upperBoundVal));
/*      */     
/*  757 */     if (iBegCount == iEndCount && iBegCount > 0 && 
/*  758 */       Math.pow(10.0D, iBegCount) > lowerBoundVal)
/*      */     {
/*      */       
/*  761 */       iBegCount--;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  766 */     boolean zeroTickFlag = false;
/*  767 */     for (int i = iBegCount; i <= iEndCount; i++) {
/*      */       
/*  769 */       for (int j = 0; j < 10; j++) {
/*      */         double currentTickValue; String tickLabel;
/*  771 */         if (this.smallLogFlag) {
/*      */           
/*  773 */           currentTickValue = Math.pow(10.0D, i) + Math.pow(10.0D, i) * j;
/*  774 */           if (this.expTickLabelsFlag || (i < 0 && currentTickValue > 0.0D && currentTickValue < 1.0D)) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  779 */             if (j == 0 || (i > -4 && j < 2) || currentTickValue >= upperBoundVal) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  785 */               this.numberFormatterObj
/*  786 */                 .setMaximumFractionDigits(-i);
/*      */               
/*  788 */               tickLabel = makeTickLabel(currentTickValue, true);
/*      */             } else {
/*      */               
/*  791 */               tickLabel = "";
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */             
/*  800 */             tickLabel = (j < 1 || (i < 1 && j < 5) || j < 4 - i || currentTickValue >= upperBoundVal) ? makeTickLabel(currentTickValue) : "";
/*      */           } 
/*      */         } else {
/*      */           
/*  804 */           if (zeroTickFlag) {
/*  805 */             j--;
/*      */           }
/*      */ 
/*      */           
/*  809 */           currentTickValue = (i >= 0) ? (Math.pow(10.0D, i) + Math.pow(10.0D, i) * j) : -(Math.pow(10.0D, -i) - Math.pow(10.0D, (-i - 1)) * j);
/*  810 */           if (!zeroTickFlag) {
/*  811 */             if (Math.abs(currentTickValue - 1.0D) < 1.0E-4D && lowerBoundVal <= 0.0D && upperBoundVal >= 0.0D) {
/*      */ 
/*      */               
/*  814 */               currentTickValue = 0.0D;
/*  815 */               zeroTickFlag = true;
/*      */             } 
/*      */           } else {
/*      */             
/*  819 */             zeroTickFlag = false;
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  829 */           tickLabel = ((this.expTickLabelsFlag && j < 2) || j < 1 || (i < 1 && j < 5) || j < 4 - i || currentTickValue >= upperBoundVal) ? makeTickLabel(currentTickValue) : "";
/*      */         } 
/*      */         
/*  832 */         if (currentTickValue > upperBoundVal) {
/*  833 */           return ticks;
/*      */         }
/*      */ 
/*      */         
/*  837 */         if (currentTickValue >= lowerBoundVal - 1.0E-100D) {
/*      */           TextAnchor anchor, rotationAnchor;
/*      */ 
/*      */           
/*  841 */           double angle = 0.0D;
/*  842 */           if (isVerticalTickLabels()) {
/*  843 */             anchor = TextAnchor.CENTER_RIGHT;
/*  844 */             rotationAnchor = TextAnchor.CENTER_RIGHT;
/*  845 */             if (edge == RectangleEdge.TOP) {
/*  846 */               angle = 1.5707963267948966D;
/*      */             } else {
/*      */               
/*  849 */               angle = -1.5707963267948966D;
/*      */             }
/*      */           
/*      */           }
/*  853 */           else if (edge == RectangleEdge.TOP) {
/*  854 */             anchor = TextAnchor.BOTTOM_CENTER;
/*  855 */             rotationAnchor = TextAnchor.BOTTOM_CENTER;
/*      */           } else {
/*      */             
/*  858 */             anchor = TextAnchor.TOP_CENTER;
/*  859 */             rotationAnchor = TextAnchor.TOP_CENTER;
/*      */           } 
/*      */ 
/*      */           
/*  863 */           Tick tick = new NumberTick(new Double(currentTickValue), tickLabel, anchor, rotationAnchor, angle);
/*      */           
/*  865 */           ticks.add(tick);
/*      */         } 
/*      */       } 
/*      */     } 
/*  869 */     return ticks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, RectangleEdge edge) {
/*  887 */     List<NumberTick> ticks = new ArrayList();
/*      */ 
/*      */     
/*  890 */     double lowerBoundVal = getRange().getLowerBound();
/*      */ 
/*      */     
/*  893 */     if (this.smallLogFlag && lowerBoundVal < 1.0E-100D) {
/*  894 */       lowerBoundVal = 1.0E-100D;
/*      */     }
/*      */     
/*  897 */     double upperBoundVal = getRange().getUpperBound();
/*      */ 
/*      */     
/*  900 */     int iBegCount = (int)Math.rint(switchedLog10(lowerBoundVal));
/*      */     
/*  902 */     int iEndCount = (int)Math.rint(switchedLog10(upperBoundVal));
/*      */     
/*  904 */     if (iBegCount == iEndCount && iBegCount > 0 && 
/*  905 */       Math.pow(10.0D, iBegCount) > lowerBoundVal)
/*      */     {
/*      */       
/*  908 */       iBegCount--;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  913 */     boolean zeroTickFlag = false;
/*  914 */     for (int i = iBegCount; i <= iEndCount; i++) {
/*      */       
/*  916 */       int jEndCount = 10;
/*  917 */       if (i == iEndCount) {
/*  918 */         jEndCount = 1;
/*      */       }
/*      */       
/*  921 */       for (int j = 0; j < jEndCount; j++) {
/*      */         double tickVal; String tickLabel;
/*  923 */         if (this.smallLogFlag) {
/*      */           
/*  925 */           tickVal = Math.pow(10.0D, i) + Math.pow(10.0D, i) * j;
/*  926 */           if (j == 0) {
/*      */             
/*  928 */             if (this.log10TickLabelsFlag)
/*      */             {
/*  930 */               tickLabel = "10^" + i;
/*      */             
/*      */             }
/*  933 */             else if (this.expTickLabelsFlag)
/*      */             {
/*  935 */               tickLabel = "1e" + i;
/*      */             
/*      */             }
/*  938 */             else if (i >= 0)
/*      */             {
/*      */               
/*  941 */               NumberFormat format = getNumberFormatOverride();
/*  942 */               if (format != null) {
/*  943 */                 tickLabel = format.format(tickVal);
/*      */               } else {
/*      */                 
/*  946 */                 tickLabel = Long.toString(
/*  947 */                     (long)Math.rint(tickVal));
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else
/*      */             {
/*  954 */               this.numberFormatterObj
/*  955 */                 .setMaximumFractionDigits(-i);
/*      */               
/*  957 */               tickLabel = this.numberFormatterObj.format(tickVal);
/*      */             
/*      */             }
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  964 */             tickLabel = "";
/*      */           } 
/*      */         } else {
/*      */           
/*  968 */           if (zeroTickFlag) {
/*  969 */             j--;
/*      */           }
/*      */           
/*  972 */           tickVal = (i >= 0) ? (Math.pow(10.0D, i) + Math.pow(10.0D, i) * j) : -(Math.pow(10.0D, -i) - Math.pow(10.0D, (-i - 1)) * j);
/*  973 */           if (j == 0) {
/*  974 */             if (!zeroTickFlag) {
/*      */               
/*  976 */               if (i > iBegCount && i < iEndCount && 
/*  977 */                 Math.abs(tickVal - 1.0D) < 1.0E-4D)
/*      */               {
/*      */                 
/*  980 */                 tickVal = 0.0D;
/*  981 */                 zeroTickFlag = true;
/*  982 */                 tickLabel = "0";
/*      */ 
/*      */ 
/*      */               
/*      */               }
/*  987 */               else if (this.log10TickLabelsFlag)
/*      */               {
/*      */                 
/*  990 */                 tickLabel = ((i < 0) ? "-" : "") + "10^" + Math.abs(i);
/*      */               
/*      */               }
/*  993 */               else if (this.expTickLabelsFlag)
/*      */               {
/*      */                 
/*  996 */                 tickLabel = ((i < 0) ? "-" : "") + "1e" + Math.abs(i);
/*      */               }
/*      */               else
/*      */               {
/* 1000 */                 NumberFormat format = getNumberFormatOverride();
/* 1001 */                 if (format != null) {
/* 1002 */                   tickLabel = format.format(tickVal);
/*      */                 } else {
/*      */                   
/* 1005 */                   tickLabel = Long.toString(
/* 1006 */                       (long)Math.rint(tickVal));
/*      */                 }
/*      */               
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 1013 */               tickLabel = "";
/* 1014 */               zeroTickFlag = false;
/*      */             } 
/*      */           } else {
/*      */             
/* 1018 */             tickLabel = "";
/* 1019 */             zeroTickFlag = false;
/*      */           } 
/*      */         } 
/*      */         
/* 1023 */         if (tickVal > upperBoundVal) {
/* 1024 */           return ticks;
/*      */         }
/*      */         
/* 1027 */         if (tickVal >= lowerBoundVal - 1.0E-100D) {
/*      */           TextAnchor anchor, rotationAnchor;
/*      */ 
/*      */           
/* 1031 */           double angle = 0.0D;
/* 1032 */           if (isVerticalTickLabels()) {
/* 1033 */             if (edge == RectangleEdge.LEFT) {
/* 1034 */               anchor = TextAnchor.BOTTOM_CENTER;
/* 1035 */               rotationAnchor = TextAnchor.BOTTOM_CENTER;
/* 1036 */               angle = -1.5707963267948966D;
/*      */             } else {
/*      */               
/* 1039 */               anchor = TextAnchor.BOTTOM_CENTER;
/* 1040 */               rotationAnchor = TextAnchor.BOTTOM_CENTER;
/* 1041 */               angle = 1.5707963267948966D;
/*      */             }
/*      */           
/*      */           }
/* 1045 */           else if (edge == RectangleEdge.LEFT) {
/* 1046 */             anchor = TextAnchor.CENTER_RIGHT;
/* 1047 */             rotationAnchor = TextAnchor.CENTER_RIGHT;
/*      */           } else {
/*      */             
/* 1050 */             anchor = TextAnchor.CENTER_LEFT;
/* 1051 */             rotationAnchor = TextAnchor.CENTER_LEFT;
/*      */           } 
/*      */ 
/*      */           
/* 1055 */           ticks.add(new NumberTick(new Double(tickVal), tickLabel, anchor, rotationAnchor, angle));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1060 */     return ticks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String makeTickLabel(double val, boolean forceFmtFlag) {
/* 1073 */     if (this.expTickLabelsFlag || forceFmtFlag)
/*      */     {
/*      */       
/* 1076 */       return this.numberFormatterObj.format(val).toLowerCase();
/*      */     }
/* 1078 */     return getTickUnit().valueToString(val);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String makeTickLabel(double val) {
/* 1088 */     return makeTickLabel(val, false);
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/LogarithmicAxis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */