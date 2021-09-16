/*     */ package org.jfree.ui;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TextAnchor
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8219158940496719660L;
/*  64 */   public static final TextAnchor TOP_LEFT = new TextAnchor("TextAnchor.TOP_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final TextAnchor TOP_CENTER = new TextAnchor("TextAnchor.TOP_CENTER");
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final TextAnchor TOP_RIGHT = new TextAnchor("TextAnchor.TOP_RIGHT");
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final TextAnchor HALF_ASCENT_LEFT = new TextAnchor("TextAnchor.HALF_ASCENT_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final TextAnchor HALF_ASCENT_CENTER = new TextAnchor("TextAnchor.HALF_ASCENT_CENTER");
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final TextAnchor HALF_ASCENT_RIGHT = new TextAnchor("TextAnchor.HALF_ASCENT_RIGHT");
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final TextAnchor CENTER_LEFT = new TextAnchor("TextAnchor.CENTER_LEFT");
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final TextAnchor CENTER = new TextAnchor("TextAnchor.CENTER");
/*     */ 
/*     */   
/*  95 */   public static final TextAnchor CENTER_RIGHT = new TextAnchor("TextAnchor.CENTER_RIGHT");
/*     */ 
/*     */ 
/*     */   
/*  99 */   public static final TextAnchor BASELINE_LEFT = new TextAnchor("TextAnchor.BASELINE_LEFT");
/*     */ 
/*     */ 
/*     */   
/* 103 */   public static final TextAnchor BASELINE_CENTER = new TextAnchor("TextAnchor.BASELINE_CENTER");
/*     */ 
/*     */ 
/*     */   
/* 107 */   public static final TextAnchor BASELINE_RIGHT = new TextAnchor("TextAnchor.BASELINE_RIGHT");
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final TextAnchor BOTTOM_LEFT = new TextAnchor("TextAnchor.BOTTOM_LEFT");
/*     */ 
/*     */ 
/*     */   
/* 115 */   public static final TextAnchor BOTTOM_CENTER = new TextAnchor("TextAnchor.BOTTOM_CENTER");
/*     */ 
/*     */ 
/*     */   
/* 119 */   public static final TextAnchor BOTTOM_RIGHT = new TextAnchor("TextAnchor.BOTTOM_RIGHT");
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
/*     */   private TextAnchor(String name) {
/* 131 */     this.name = name;
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
/*     */   public boolean isLeft() {
/* 143 */     return (this == BASELINE_LEFT || this == BOTTOM_LEFT || this == CENTER_LEFT || this == HALF_ASCENT_LEFT || this == TOP_LEFT);
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
/*     */   public boolean isRight() {
/* 157 */     return (this == BASELINE_RIGHT || this == BOTTOM_RIGHT || this == CENTER_RIGHT || this == HALF_ASCENT_RIGHT || this == TOP_RIGHT);
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
/*     */   public boolean isHorizontalCenter() {
/* 171 */     return (this == BASELINE_CENTER || this == BOTTOM_CENTER || this == CENTER || this == HALF_ASCENT_CENTER || this == TOP_CENTER);
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
/*     */   public boolean isTop() {
/* 185 */     return (this == TOP_LEFT || this == TOP_CENTER || this == TOP_RIGHT);
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
/*     */   public boolean isBottom() {
/* 197 */     return (this == BOTTOM_LEFT || this == BOTTOM_CENTER || this == BOTTOM_RIGHT);
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
/*     */   public boolean isBaseline() {
/* 210 */     return (this == BASELINE_LEFT || this == BASELINE_CENTER || this == BASELINE_RIGHT);
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
/*     */   public boolean isHalfAscent() {
/* 223 */     return (this == HALF_ASCENT_LEFT || this == HALF_ASCENT_CENTER || this == HALF_ASCENT_RIGHT);
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
/*     */   public boolean isVerticalCenter() {
/* 236 */     return (this == CENTER_LEFT || this == CENTER || this == CENTER_RIGHT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 245 */     return this.name;
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
/*     */   public boolean equals(Object o) {
/* 258 */     if (this == o) {
/* 259 */       return true;
/*     */     }
/* 261 */     if (!(o instanceof TextAnchor)) {
/* 262 */       return false;
/*     */     }
/*     */     
/* 265 */     TextAnchor order = (TextAnchor)o;
/* 266 */     if (!this.name.equals(order.name)) {
/* 267 */       return false;
/*     */     }
/*     */     
/* 270 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 279 */     return this.name.hashCode();
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
/* 290 */     TextAnchor result = null;
/* 291 */     if (equals(TOP_LEFT)) {
/* 292 */       result = TOP_LEFT;
/*     */     }
/* 294 */     else if (equals(TOP_CENTER)) {
/* 295 */       result = TOP_CENTER;
/*     */     }
/* 297 */     else if (equals(TOP_RIGHT)) {
/* 298 */       result = TOP_RIGHT;
/*     */     }
/* 300 */     else if (equals(BOTTOM_LEFT)) {
/* 301 */       result = BOTTOM_LEFT;
/*     */     }
/* 303 */     else if (equals(BOTTOM_CENTER)) {
/* 304 */       result = BOTTOM_CENTER;
/*     */     }
/* 306 */     else if (equals(BOTTOM_RIGHT)) {
/* 307 */       result = BOTTOM_RIGHT;
/*     */     }
/* 309 */     else if (equals(BASELINE_LEFT)) {
/* 310 */       result = BASELINE_LEFT;
/*     */     }
/* 312 */     else if (equals(BASELINE_CENTER)) {
/* 313 */       result = BASELINE_CENTER;
/*     */     }
/* 315 */     else if (equals(BASELINE_RIGHT)) {
/* 316 */       result = BASELINE_RIGHT;
/*     */     }
/* 318 */     else if (equals(CENTER_LEFT)) {
/* 319 */       result = CENTER_LEFT;
/*     */     }
/* 321 */     else if (equals(CENTER)) {
/* 322 */       result = CENTER;
/*     */     }
/* 324 */     else if (equals(CENTER_RIGHT)) {
/* 325 */       result = CENTER_RIGHT;
/*     */     }
/* 327 */     else if (equals(HALF_ASCENT_LEFT)) {
/* 328 */       result = HALF_ASCENT_LEFT;
/*     */     }
/* 330 */     else if (equals(HALF_ASCENT_CENTER)) {
/* 331 */       result = HALF_ASCENT_CENTER;
/*     */     }
/* 333 */     else if (equals(HALF_ASCENT_RIGHT)) {
/* 334 */       result = HALF_ASCENT_RIGHT;
/*     */     } 
/* 336 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/TextAnchor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */