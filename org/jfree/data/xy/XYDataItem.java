/*     */ package org.jfree.data.xy;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
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
/*     */ public class XYDataItem
/*     */   implements Cloneable, Comparable, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2751513470325494890L;
/*     */   private Number x;
/*     */   private Number y;
/*     */   
/*     */   public XYDataItem(Number x, Number y) {
/*  77 */     ParamChecks.nullNotPermitted(x, "x");
/*  78 */     this.x = x;
/*  79 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XYDataItem(double x, double y) {
/*  89 */     this(new Double(x), new Double(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getX() {
/*  98 */     return this.x;
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
/*     */   public double getXValue() {
/* 113 */     return this.x.doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Number getY() {
/* 122 */     return this.y;
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
/*     */   public double getYValue() {
/* 136 */     double result = Double.NaN;
/* 137 */     if (this.y != null) {
/* 138 */       result = this.y.doubleValue();
/*     */     }
/* 140 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(double y) {
/* 150 */     setY(new Double(y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setY(Number y) {
/* 160 */     this.y = y;
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
/*     */   public int compareTo(Object o1) {
/*     */     int result;
/* 182 */     if (o1 instanceof XYDataItem) {
/* 183 */       XYDataItem dataItem = (XYDataItem)o1;
/*     */       
/* 185 */       double compare = this.x.doubleValue() - dataItem.getX().doubleValue();
/* 186 */       if (compare > 0.0D) {
/* 187 */         result = 1;
/*     */       
/*     */       }
/* 190 */       else if (compare < 0.0D) {
/* 191 */         result = -1;
/*     */       } else {
/*     */         
/* 194 */         result = 0;
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 203 */       result = 1;
/*     */     } 
/*     */     
/* 206 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 217 */     Object clone = null;
/*     */     try {
/* 219 */       clone = super.clone();
/*     */     }
/* 221 */     catch (CloneNotSupportedException e) {
/* 222 */       e.printStackTrace();
/*     */     } 
/* 224 */     return clone;
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
/*     */   public boolean equals(Object obj) {
/* 237 */     if (obj == this) {
/* 238 */       return true;
/*     */     }
/* 240 */     if (!(obj instanceof XYDataItem)) {
/* 241 */       return false;
/*     */     }
/* 243 */     XYDataItem that = (XYDataItem)obj;
/* 244 */     if (!this.x.equals(that.x)) {
/* 245 */       return false;
/*     */     }
/* 247 */     if (!ObjectUtilities.equal(this.y, that.y)) {
/* 248 */       return false;
/*     */     }
/* 250 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 261 */     int result = this.x.hashCode();
/* 262 */     result = 29 * result + ((this.y != null) ? this.y.hashCode() : 0);
/* 263 */     return result;
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
/* 274 */     return "[" + getXValue() + ", " + getYValue() + "]";
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/xy/XYDataItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */