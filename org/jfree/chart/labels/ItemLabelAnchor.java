/*     */ package org.jfree.chart.labels;
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
/*     */ public final class ItemLabelAnchor
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -1233101616128695658L;
/*  60 */   public static final ItemLabelAnchor CENTER = new ItemLabelAnchor("ItemLabelAnchor.CENTER");
/*     */ 
/*     */ 
/*     */   
/*  64 */   public static final ItemLabelAnchor INSIDE1 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE1");
/*     */ 
/*     */ 
/*     */   
/*  68 */   public static final ItemLabelAnchor INSIDE2 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE2");
/*     */ 
/*     */ 
/*     */   
/*  72 */   public static final ItemLabelAnchor INSIDE3 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE3");
/*     */ 
/*     */ 
/*     */   
/*  76 */   public static final ItemLabelAnchor INSIDE4 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE4");
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final ItemLabelAnchor INSIDE5 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE5");
/*     */ 
/*     */ 
/*     */   
/*  84 */   public static final ItemLabelAnchor INSIDE6 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE6");
/*     */ 
/*     */ 
/*     */   
/*  88 */   public static final ItemLabelAnchor INSIDE7 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE7");
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static final ItemLabelAnchor INSIDE8 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE8");
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static final ItemLabelAnchor INSIDE9 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE9");
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final ItemLabelAnchor INSIDE10 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE10");
/*     */ 
/*     */ 
/*     */   
/* 104 */   public static final ItemLabelAnchor INSIDE11 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE11");
/*     */ 
/*     */ 
/*     */   
/* 108 */   public static final ItemLabelAnchor INSIDE12 = new ItemLabelAnchor("ItemLabelAnchor.INSIDE12");
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static final ItemLabelAnchor OUTSIDE1 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE1");
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final ItemLabelAnchor OUTSIDE2 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE2");
/*     */ 
/*     */ 
/*     */   
/* 120 */   public static final ItemLabelAnchor OUTSIDE3 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE3");
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static final ItemLabelAnchor OUTSIDE4 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE4");
/*     */ 
/*     */ 
/*     */   
/* 128 */   public static final ItemLabelAnchor OUTSIDE5 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE5");
/*     */ 
/*     */ 
/*     */   
/* 132 */   public static final ItemLabelAnchor OUTSIDE6 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE6");
/*     */ 
/*     */ 
/*     */   
/* 136 */   public static final ItemLabelAnchor OUTSIDE7 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE7");
/*     */ 
/*     */ 
/*     */   
/* 140 */   public static final ItemLabelAnchor OUTSIDE8 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE8");
/*     */ 
/*     */ 
/*     */   
/* 144 */   public static final ItemLabelAnchor OUTSIDE9 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE9");
/*     */ 
/*     */ 
/*     */   
/* 148 */   public static final ItemLabelAnchor OUTSIDE10 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE10");
/*     */ 
/*     */ 
/*     */   
/* 152 */   public static final ItemLabelAnchor OUTSIDE11 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE11");
/*     */ 
/*     */ 
/*     */   
/* 156 */   public static final ItemLabelAnchor OUTSIDE12 = new ItemLabelAnchor("ItemLabelAnchor.OUTSIDE12");
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
/*     */   private ItemLabelAnchor(String name) {
/* 168 */     this.name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 178 */     return this.name;
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
/* 191 */     if (this == obj) {
/* 192 */       return true;
/*     */     }
/* 194 */     if (!(obj instanceof ItemLabelAnchor)) {
/* 195 */       return false;
/*     */     }
/* 197 */     ItemLabelAnchor that = (ItemLabelAnchor)obj;
/* 198 */     if (!this.name.equals(that.toString())) {
/* 199 */       return false;
/*     */     }
/* 201 */     return true;
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
/* 212 */     ItemLabelAnchor result = null;
/* 213 */     if (equals(CENTER)) {
/* 214 */       result = CENTER;
/*     */     }
/* 216 */     else if (equals(INSIDE1)) {
/* 217 */       result = INSIDE1;
/*     */     }
/* 219 */     else if (equals(INSIDE2)) {
/* 220 */       result = INSIDE2;
/*     */     }
/* 222 */     else if (equals(INSIDE3)) {
/* 223 */       result = INSIDE3;
/*     */     }
/* 225 */     else if (equals(INSIDE4)) {
/* 226 */       result = INSIDE4;
/*     */     }
/* 228 */     else if (equals(INSIDE5)) {
/* 229 */       result = INSIDE5;
/*     */     }
/* 231 */     else if (equals(INSIDE6)) {
/* 232 */       result = INSIDE6;
/*     */     }
/* 234 */     else if (equals(INSIDE7)) {
/* 235 */       result = INSIDE7;
/*     */     }
/* 237 */     else if (equals(INSIDE8)) {
/* 238 */       result = INSIDE8;
/*     */     }
/* 240 */     else if (equals(INSIDE9)) {
/* 241 */       result = INSIDE9;
/*     */     }
/* 243 */     else if (equals(INSIDE10)) {
/* 244 */       result = INSIDE10;
/*     */     }
/* 246 */     else if (equals(INSIDE11)) {
/* 247 */       result = INSIDE11;
/*     */     }
/* 249 */     else if (equals(INSIDE12)) {
/* 250 */       result = INSIDE12;
/*     */     }
/* 252 */     else if (equals(OUTSIDE1)) {
/* 253 */       result = OUTSIDE1;
/*     */     }
/* 255 */     else if (equals(OUTSIDE2)) {
/* 256 */       result = OUTSIDE2;
/*     */     }
/* 258 */     else if (equals(OUTSIDE3)) {
/* 259 */       result = OUTSIDE3;
/*     */     }
/* 261 */     else if (equals(OUTSIDE4)) {
/* 262 */       result = OUTSIDE4;
/*     */     }
/* 264 */     else if (equals(OUTSIDE5)) {
/* 265 */       result = OUTSIDE5;
/*     */     }
/* 267 */     else if (equals(OUTSIDE6)) {
/* 268 */       result = OUTSIDE6;
/*     */     }
/* 270 */     else if (equals(OUTSIDE7)) {
/* 271 */       result = OUTSIDE7;
/*     */     }
/* 273 */     else if (equals(OUTSIDE8)) {
/* 274 */       result = OUTSIDE8;
/*     */     }
/* 276 */     else if (equals(OUTSIDE9)) {
/* 277 */       result = OUTSIDE9;
/*     */     }
/* 279 */     else if (equals(OUTSIDE10)) {
/* 280 */       result = OUTSIDE10;
/*     */     }
/* 282 */     else if (equals(OUTSIDE11)) {
/* 283 */       result = OUTSIDE11;
/*     */     }
/* 285 */     else if (equals(OUTSIDE12)) {
/* 286 */       result = OUTSIDE12;
/*     */     } 
/* 288 */     return result;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/labels/ItemLabelAnchor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */