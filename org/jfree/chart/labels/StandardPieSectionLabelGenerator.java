/*     */ package org.jfree.chart.labels;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.text.AttributedString;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.jfree.data.general.PieDataset;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardPieSectionLabelGenerator
/*     */   extends AbstractPieItemLabelGenerator
/*     */   implements PieSectionLabelGenerator, Cloneable, PublicCloneable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 3064190563760203668L;
/*     */   public static final String DEFAULT_SECTION_LABEL_FORMAT = "{0}";
/*     */   private Map attributedLabels;
/*     */   
/*     */   public StandardPieSectionLabelGenerator() {
/*  95 */     this("{0}", NumberFormat.getNumberInstance(), 
/*  96 */         NumberFormat.getPercentInstance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardPieSectionLabelGenerator(Locale locale) {
/* 107 */     this("{0}", locale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardPieSectionLabelGenerator(String labelFormat) {
/* 117 */     this(labelFormat, NumberFormat.getNumberInstance(), 
/* 118 */         NumberFormat.getPercentInstance());
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
/*     */   public StandardPieSectionLabelGenerator(String labelFormat, Locale locale) {
/* 130 */     this(labelFormat, NumberFormat.getNumberInstance(locale), 
/* 131 */         NumberFormat.getPercentInstance(locale));
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
/*     */   public StandardPieSectionLabelGenerator(String labelFormat, NumberFormat numberFormat, NumberFormat percentFormat) {
/* 146 */     super(labelFormat, numberFormat, percentFormat);
/* 147 */     this.attributedLabels = new HashMap<Object, Object>();
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
/*     */   public AttributedString getAttributedLabel(int section) {
/* 159 */     return (AttributedString)this.attributedLabels.get(Integer.valueOf(section));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttributedLabel(int section, AttributedString label) {
/* 169 */     this.attributedLabels.put(Integer.valueOf(section), label);
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
/*     */   public String generateSectionLabel(PieDataset dataset, Comparable key) {
/* 182 */     return super.generateSectionLabel(dataset, key);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributedString generateAttributedSectionLabel(PieDataset dataset, Comparable key) {
/* 215 */     return getAttributedLabel(dataset.getIndex(key));
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
/* 227 */     if (obj == this) {
/* 228 */       return true;
/*     */     }
/* 230 */     if (!(obj instanceof StandardPieSectionLabelGenerator)) {
/* 231 */       return false;
/*     */     }
/* 233 */     StandardPieSectionLabelGenerator that = (StandardPieSectionLabelGenerator)obj;
/*     */     
/* 235 */     if (!this.attributedLabels.equals(that.attributedLabels)) {
/* 236 */       return false;
/*     */     }
/* 238 */     return super.equals(obj);
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
/* 251 */     StandardPieSectionLabelGenerator clone = (StandardPieSectionLabelGenerator)super.clone();
/* 252 */     clone.attributedLabels = new HashMap<Object, Object>();
/* 253 */     clone.attributedLabels.putAll(this.attributedLabels);
/* 254 */     return clone;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/StandardPieSectionLabelGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */