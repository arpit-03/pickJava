/*     */ package org.jfree.data.statistics;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BoxAndWhiskerItem
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7329649623148167423L;
/*     */   private Number mean;
/*     */   private Number median;
/*     */   private Number q1;
/*     */   private Number q3;
/*     */   private Number minRegularValue;
/*     */   private Number maxRegularValue;
/*     */   private Number minOutlier;
/*     */   private Number maxOutlier;
/*     */   private List outliers;
/*     */   
/*     */   public BoxAndWhiskerItem(Number mean, Number median, Number q1, Number q3, Number minRegularValue, Number maxRegularValue, Number minOutlier, Number maxOutlier, List outliers) {
/* 114 */     this.mean = mean;
/* 115 */     this.median = median;
/* 116 */     this.q1 = q1;
/* 117 */     this.q3 = q3;
/* 118 */     this.minRegularValue = minRegularValue;
/* 119 */     this.maxRegularValue = maxRegularValue;
/* 120 */     this.minOutlier = minOutlier;
/* 121 */     this.maxOutlier = maxOutlier;
/* 122 */     this.outliers = outliers;
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
/*     */   public BoxAndWhiskerItem(double mean, double median, double q1, double q3, double minRegularValue, double maxRegularValue, double minOutlier, double maxOutlier, List outliers) {
/* 146 */     this(new Double(mean), new Double(median), new Double(q1), new Double(q3), new Double(minRegularValue), new Double(maxRegularValue), new Double(minOutlier), new Double(maxOutlier), outliers);
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
/*     */   public Number getMean() {
/* 159 */     return this.mean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMedian() {
/* 168 */     return this.median;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getQ1() {
/* 177 */     return this.q1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getQ3() {
/* 186 */     return this.q3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMinRegularValue() {
/* 195 */     return this.minRegularValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMaxRegularValue() {
/* 204 */     return this.maxRegularValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMinOutlier() {
/* 213 */     return this.minOutlier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getMaxOutlier() {
/* 222 */     return this.maxOutlier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List getOutliers() {
/* 231 */     if (this.outliers == null) {
/* 232 */       return null;
/*     */     }
/* 234 */     return Collections.unmodifiableList(this.outliers);
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
/* 245 */     return super.toString() + "[mean=" + this.mean + ",median=" + this.median + ",q1=" + this.q1 + ",q3=" + this.q3 + "]";
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
/*     */   public boolean equals(Object obj) {
/* 259 */     if (obj == this) {
/* 260 */       return true;
/*     */     }
/* 262 */     if (!(obj instanceof BoxAndWhiskerItem)) {
/* 263 */       return false;
/*     */     }
/* 265 */     BoxAndWhiskerItem that = (BoxAndWhiskerItem)obj;
/* 266 */     if (!ObjectUtilities.equal(this.mean, that.mean)) {
/* 267 */       return false;
/*     */     }
/* 269 */     if (!ObjectUtilities.equal(this.median, that.median)) {
/* 270 */       return false;
/*     */     }
/* 272 */     if (!ObjectUtilities.equal(this.q1, that.q1)) {
/* 273 */       return false;
/*     */     }
/* 275 */     if (!ObjectUtilities.equal(this.q3, that.q3)) {
/* 276 */       return false;
/*     */     }
/* 278 */     if (!ObjectUtilities.equal(this.minRegularValue, that.minRegularValue))
/*     */     {
/* 280 */       return false;
/*     */     }
/* 282 */     if (!ObjectUtilities.equal(this.maxRegularValue, that.maxRegularValue))
/*     */     {
/* 284 */       return false;
/*     */     }
/* 286 */     if (!ObjectUtilities.equal(this.minOutlier, that.minOutlier)) {
/* 287 */       return false;
/*     */     }
/* 289 */     if (!ObjectUtilities.equal(this.maxOutlier, that.maxOutlier)) {
/* 290 */       return false;
/*     */     }
/* 292 */     if (!ObjectUtilities.equal(this.outliers, that.outliers)) {
/* 293 */       return false;
/*     */     }
/* 295 */     return true;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/statistics/BoxAndWhiskerItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */