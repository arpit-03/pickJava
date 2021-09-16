/*     */ package org.jfree.chart;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Paint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChartColor
/*     */   extends Color
/*     */ {
/*  59 */   public static final Color VERY_DARK_RED = new Color(128, 0, 0);
/*     */ 
/*     */   
/*  62 */   public static final Color DARK_RED = new Color(192, 0, 0);
/*     */ 
/*     */   
/*  65 */   public static final Color LIGHT_RED = new Color(255, 64, 64);
/*     */ 
/*     */   
/*  68 */   public static final Color VERY_LIGHT_RED = new Color(255, 128, 128);
/*     */ 
/*     */   
/*  71 */   public static final Color VERY_DARK_YELLOW = new Color(128, 128, 0);
/*     */ 
/*     */   
/*  74 */   public static final Color DARK_YELLOW = new Color(192, 192, 0);
/*     */ 
/*     */   
/*  77 */   public static final Color LIGHT_YELLOW = new Color(255, 255, 64);
/*     */ 
/*     */   
/*  80 */   public static final Color VERY_LIGHT_YELLOW = new Color(255, 255, 128);
/*     */ 
/*     */   
/*  83 */   public static final Color VERY_DARK_GREEN = new Color(0, 128, 0);
/*     */ 
/*     */   
/*  86 */   public static final Color DARK_GREEN = new Color(0, 192, 0);
/*     */ 
/*     */   
/*  89 */   public static final Color LIGHT_GREEN = new Color(64, 255, 64);
/*     */ 
/*     */   
/*  92 */   public static final Color VERY_LIGHT_GREEN = new Color(128, 255, 128);
/*     */ 
/*     */   
/*  95 */   public static final Color VERY_DARK_CYAN = new Color(0, 128, 128);
/*     */ 
/*     */   
/*  98 */   public static final Color DARK_CYAN = new Color(0, 192, 192);
/*     */ 
/*     */   
/* 101 */   public static final Color LIGHT_CYAN = new Color(64, 255, 255);
/*     */ 
/*     */   
/* 104 */   public static final Color VERY_LIGHT_CYAN = new Color(128, 255, 255);
/*     */ 
/*     */   
/* 107 */   public static final Color VERY_DARK_BLUE = new Color(0, 0, 128);
/*     */ 
/*     */   
/* 110 */   public static final Color DARK_BLUE = new Color(0, 0, 192);
/*     */ 
/*     */   
/* 113 */   public static final Color LIGHT_BLUE = new Color(64, 64, 255);
/*     */ 
/*     */   
/* 116 */   public static final Color VERY_LIGHT_BLUE = new Color(128, 128, 255);
/*     */ 
/*     */   
/* 119 */   public static final Color VERY_DARK_MAGENTA = new Color(128, 0, 128);
/*     */ 
/*     */   
/* 122 */   public static final Color DARK_MAGENTA = new Color(192, 0, 192);
/*     */ 
/*     */   
/* 125 */   public static final Color LIGHT_MAGENTA = new Color(255, 64, 255);
/*     */ 
/*     */   
/* 128 */   public static final Color VERY_LIGHT_MAGENTA = new Color(255, 128, 255);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChartColor(int r, int g, int b) {
/* 139 */     super(r, g, b);
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
/*     */   public static Paint[] createDefaultPaintArray() {
/* 151 */     return new Paint[] { new Color(255, 85, 85), new Color(85, 85, 255), new Color(85, 255, 85), new Color(255, 255, 85), new Color(255, 85, 255), new Color(85, 255, 255), Color.pink, Color.gray, DARK_RED, DARK_BLUE, DARK_GREEN, DARK_YELLOW, DARK_MAGENTA, DARK_CYAN, Color.darkGray, LIGHT_RED, LIGHT_BLUE, LIGHT_GREEN, LIGHT_YELLOW, LIGHT_MAGENTA, LIGHT_CYAN, Color.lightGray, VERY_DARK_RED, VERY_DARK_BLUE, VERY_DARK_GREEN, VERY_DARK_YELLOW, VERY_DARK_MAGENTA, VERY_DARK_CYAN, VERY_LIGHT_RED, VERY_LIGHT_BLUE, VERY_LIGHT_GREEN, VERY_LIGHT_YELLOW, VERY_LIGHT_MAGENTA, VERY_LIGHT_CYAN };
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/ChartColor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */