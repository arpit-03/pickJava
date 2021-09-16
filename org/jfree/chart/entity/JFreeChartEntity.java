/*     */ package org.jfree.chart.entity;
/*     */ 
/*     */ import java.awt.Shape;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.JFreeChart;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.io.SerialUtilities;
/*     */ import org.jfree.util.ObjectUtilities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JFreeChartEntity
/*     */   extends ChartEntity
/*     */ {
/*     */   private static final long serialVersionUID = -4445994133561919083L;
/*     */   private JFreeChart chart;
/*     */   
/*     */   public JFreeChartEntity(Shape area, JFreeChart chart) {
/*  77 */     this(area, chart, (String)null);
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
/*     */   public JFreeChartEntity(Shape area, JFreeChart chart, String toolTipText) {
/*  89 */     this(area, chart, toolTipText, (String)null);
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
/*     */   public JFreeChartEntity(Shape area, JFreeChart chart, String toolTipText, String urlText) {
/* 103 */     super(area, toolTipText, urlText);
/* 104 */     ParamChecks.nullNotPermitted(chart, "chart");
/* 105 */     this.chart = chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JFreeChart getChart() {
/* 114 */     return this.chart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 125 */     StringBuilder sb = new StringBuilder("JFreeChartEntity: ");
/* 126 */     sb.append("tooltip = ");
/* 127 */     sb.append(getToolTipText());
/* 128 */     return sb.toString();
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
/*     */   public boolean equals(Object obj) {
/* 140 */     if (obj == this) {
/* 141 */       return true;
/*     */     }
/* 143 */     if (!(obj instanceof JFreeChartEntity)) {
/* 144 */       return false;
/*     */     }
/* 146 */     JFreeChartEntity that = (JFreeChartEntity)obj;
/* 147 */     if (!getArea().equals(that.getArea())) {
/* 148 */       return false;
/*     */     }
/* 150 */     if (!ObjectUtilities.equal(getToolTipText(), that.getToolTipText())) {
/* 151 */       return false;
/*     */     }
/* 153 */     if (!ObjectUtilities.equal(getURLText(), that.getURLText())) {
/* 154 */       return false;
/*     */     }
/* 156 */     if (!this.chart.equals(that.chart)) {
/* 157 */       return false;
/*     */     }
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 169 */     int result = 39;
/* 170 */     result = HashUtilities.hashCode(result, getToolTipText());
/* 171 */     result = HashUtilities.hashCode(result, getURLText());
/* 172 */     return result;
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
/*     */   public Object clone() throws CloneNotSupportedException {
/* 185 */     return super.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObject(ObjectOutputStream stream) throws IOException {
/* 196 */     stream.defaultWriteObject();
/* 197 */     SerialUtilities.writeShape(getArea(), stream);
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
/*     */   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
/* 210 */     stream.defaultReadObject();
/* 211 */     setArea(SerialUtilities.readShape(stream));
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/entity/JFreeChartEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */