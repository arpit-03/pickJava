/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AxisLocation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3276922179323563410L;
/*  63 */   public static final AxisLocation TOP_OR_LEFT = new AxisLocation("AxisLocation.TOP_OR_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  67 */   public static final AxisLocation TOP_OR_RIGHT = new AxisLocation("AxisLocation.TOP_OR_RIGHT");
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final AxisLocation BOTTOM_OR_LEFT = new AxisLocation("AxisLocation.BOTTOM_OR_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final AxisLocation BOTTOM_OR_RIGHT = new AxisLocation("AxisLocation.BOTTOM_OR_RIGHT");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AxisLocation(String name) {
/*  87 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisLocation getOpposite() {
/*  98 */     return getOpposite(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return this.name;
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
/* 121 */     if (this == obj) {
/* 122 */       return true;
/*     */     }
/* 124 */     if (!(obj instanceof AxisLocation)) {
/* 125 */       return false;
/*     */     }
/* 127 */     AxisLocation location = (AxisLocation)obj;
/* 128 */     if (!this.name.equals(location.toString())) {
/* 129 */       return false;
/*     */     }
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 141 */     int hash = 5;
/* 142 */     hash = 83 * hash + this.name.hashCode();
/* 143 */     return hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AxisLocation getOpposite(AxisLocation location) {
/* 154 */     ParamChecks.nullNotPermitted(location, "location");
/* 155 */     AxisLocation result = null;
/* 156 */     if (location == TOP_OR_LEFT) {
/* 157 */       result = BOTTOM_OR_RIGHT;
/*     */     }
/* 159 */     else if (location == TOP_OR_RIGHT) {
/* 160 */       result = BOTTOM_OR_LEFT;
/*     */     }
/* 162 */     else if (location == BOTTOM_OR_LEFT) {
/* 163 */       result = TOP_OR_RIGHT;
/*     */     }
/* 165 */     else if (location == BOTTOM_OR_RIGHT) {
/* 166 */       result = TOP_OR_LEFT;
/*     */     } else {
/*     */       
/* 169 */       throw new IllegalStateException("AxisLocation not recognised.");
/*     */     } 
/* 171 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object readResolve() throws ObjectStreamException {
/* 182 */     if (equals(TOP_OR_RIGHT)) {
/* 183 */       return TOP_OR_RIGHT;
/*     */     }
/* 185 */     if (equals(BOTTOM_OR_RIGHT)) {
/* 186 */       return BOTTOM_OR_RIGHT;
/*     */     }
/* 188 */     if (equals(TOP_OR_LEFT)) {
/* 189 */       return TOP_OR_LEFT;
/*     */     }
/* 191 */     if (equals(BOTTOM_OR_LEFT)) {
/* 192 */       return BOTTOM_OR_LEFT;
/*     */     }
/* 194 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/AxisLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */