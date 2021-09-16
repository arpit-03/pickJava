/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.jfree.chart.HashUtilities;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.util.PublicCloneable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MultipleXYSeriesLabelGenerator
/*     */   implements XYSeriesLabelGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 138976236941898560L;
/*     */   public static final String DEFAULT_LABEL_FORMAT = "{0}";
/*     */   private String formatPattern;
/*     */   private String additionalFormatPattern;
/*     */   private Map seriesLabelLists;
/*     */   
/*     */   public MultipleXYSeriesLabelGenerator() {
/*  86 */     this("{0}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MultipleXYSeriesLabelGenerator(String format) {
/*  95 */     ParamChecks.nullNotPermitted(format, "format");
/*  96 */     this.formatPattern = format;
/*  97 */     this.additionalFormatPattern = "\n{0}";
/*  98 */     this.seriesLabelLists = new HashMap<Object, Object>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSeriesLabel(int series, String label) {
/* 108 */     Integer key = new Integer(series);
/* 109 */     List<String> labelList = (List)this.seriesLabelLists.get(key);
/* 110 */     if (labelList == null) {
/* 111 */       labelList = new ArrayList();
/* 112 */       this.seriesLabelLists.put(key, labelList);
/*     */     } 
/* 114 */     labelList.add(label);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSeriesLabels(int series) {
/* 123 */     Integer key = new Integer(series);
/* 124 */     this.seriesLabelLists.put(key, null);
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
/*     */   public String generateLabel(XYDataset dataset, int series) {
/* 138 */     ParamChecks.nullNotPermitted(dataset, "dataset");
/* 139 */     StringBuilder label = new StringBuilder();
/* 140 */     label.append(MessageFormat.format(this.formatPattern, 
/* 141 */           createItemArray(dataset, series)));
/* 142 */     Integer key = new Integer(series);
/* 143 */     List extraLabels = (List)this.seriesLabelLists.get(key);
/* 144 */     if (extraLabels != null) {
/* 145 */       Object[] temp = new Object[1];
/* 146 */       for (int i = 0; i < extraLabels.size(); i++) {
/* 147 */         temp[0] = extraLabels.get(i);
/* 148 */         String labelAddition = MessageFormat.format(this.additionalFormatPattern, temp);
/*     */         
/* 150 */         label.append(labelAddition);
/*     */       } 
/*     */     } 
/* 153 */     return label.toString();
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
/*     */   protected Object[] createItemArray(XYDataset dataset, int series) {
/* 166 */     Object[] result = new Object[1];
/* 167 */     result[0] = dataset.getSeriesKey(series).toString();
/* 168 */     return result;
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
/* 181 */     MultipleXYSeriesLabelGenerator clone = (MultipleXYSeriesLabelGenerator)super.clone();
/* 182 */     clone.seriesLabelLists = new HashMap<Object, Object>();
/* 183 */     Set keys = this.seriesLabelLists.keySet();
/* 184 */     Iterator iterator = keys.iterator();
/* 185 */     while (iterator.hasNext()) {
/* 186 */       Object key = iterator.next();
/* 187 */       Object entry = this.seriesLabelLists.get(key);
/* 188 */       Object toAdd = entry;
/* 189 */       if (entry instanceof PublicCloneable) {
/* 190 */         PublicCloneable pc = (PublicCloneable)entry;
/* 191 */         toAdd = pc.clone();
/*     */       } 
/* 193 */       clone.seriesLabelLists.put(key, toAdd);
/*     */     } 
/* 195 */     return clone;
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
/* 207 */     if (obj == this) {
/* 208 */       return true;
/*     */     }
/* 210 */     if (!(obj instanceof MultipleXYSeriesLabelGenerator)) {
/* 211 */       return false;
/*     */     }
/* 213 */     MultipleXYSeriesLabelGenerator that = (MultipleXYSeriesLabelGenerator)obj;
/*     */     
/* 215 */     if (!this.formatPattern.equals(that.formatPattern)) {
/* 216 */       return false;
/*     */     }
/* 218 */     if (!this.additionalFormatPattern.equals(that.additionalFormatPattern))
/*     */     {
/* 220 */       return false;
/*     */     }
/* 222 */     if (!this.seriesLabelLists.equals(that.seriesLabelLists)) {
/* 223 */       return false;
/*     */     }
/* 225 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 235 */     int result = 127;
/* 236 */     result = HashUtilities.hashCode(result, this.formatPattern);
/* 237 */     result = HashUtilities.hashCode(result, this.additionalFormatPattern);
/* 238 */     result = HashUtilities.hashCode(result, this.seriesLabelLists);
/* 239 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/MultipleXYSeriesLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */