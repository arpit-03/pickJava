/*     */ package org.jfree.chart.plot;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PolarAxisLocation
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -3276922179323563410L;
/*  57 */   public static final PolarAxisLocation NORTH_LEFT = new PolarAxisLocation("PolarAxisLocation.NORTH_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  61 */   public static final PolarAxisLocation NORTH_RIGHT = new PolarAxisLocation("PolarAxisLocation.NORTH_RIGHT");
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final PolarAxisLocation SOUTH_LEFT = new PolarAxisLocation("PolarAxisLocation.SOUTH_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  69 */   public static final PolarAxisLocation SOUTH_RIGHT = new PolarAxisLocation("PolarAxisLocation.SOUTH_RIGHT");
/*     */ 
/*     */ 
/*     */   
/*  73 */   public static final PolarAxisLocation EAST_ABOVE = new PolarAxisLocation("PolarAxisLocation.EAST_ABOVE");
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final PolarAxisLocation EAST_BELOW = new PolarAxisLocation("PolarAxisLocation.EAST_BELOW");
/*     */ 
/*     */ 
/*     */   
/*  81 */   public static final PolarAxisLocation WEST_ABOVE = new PolarAxisLocation("PolarAxisLocation.WEST_ABOVE");
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final PolarAxisLocation WEST_BELOW = new PolarAxisLocation("PolarAxisLocation.WEST_BELOW");
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
/*     */   private PolarAxisLocation(String name) {
/*  97 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return this.name;
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
/* 120 */     if (obj == this) {
/* 121 */       return true;
/*     */     }
/* 123 */     if (!(obj instanceof PolarAxisLocation)) {
/* 124 */       return false;
/*     */     }
/* 126 */     PolarAxisLocation location = (PolarAxisLocation)obj;
/* 127 */     if (!this.name.equals(location.toString())) {
/* 128 */       return false;
/*     */     }
/* 130 */     return true;
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
/* 141 */     if (equals(NORTH_RIGHT)) {
/* 142 */       return NORTH_RIGHT;
/*     */     }
/* 144 */     if (equals(NORTH_LEFT)) {
/* 145 */       return NORTH_LEFT;
/*     */     }
/* 147 */     if (equals(SOUTH_RIGHT)) {
/* 148 */       return SOUTH_RIGHT;
/*     */     }
/* 150 */     if (equals(SOUTH_LEFT)) {
/* 151 */       return SOUTH_LEFT;
/*     */     }
/* 153 */     if (equals(EAST_ABOVE)) {
/* 154 */       return EAST_ABOVE;
/*     */     }
/* 156 */     if (equals(EAST_BELOW)) {
/* 157 */       return EAST_BELOW;
/*     */     }
/* 159 */     if (equals(WEST_ABOVE)) {
/* 160 */       return WEST_ABOVE;
/*     */     }
/* 162 */     if (equals(WEST_BELOW)) {
/* 163 */       return WEST_BELOW;
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/plot/PolarAxisLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */