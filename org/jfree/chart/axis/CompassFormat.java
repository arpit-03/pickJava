/*     */ package org.jfree.chart.axis;
/*     */ 
/*     */ import java.text.FieldPosition;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParsePosition;
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
/*     */ public class CompassFormat
/*     */   extends NumberFormat
/*     */ {
/*     */   public final String[] directions;
/*     */   
/*     */   public CompassFormat() {
/*  62 */     this("N", "E", "S", "W");
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
/*     */   public CompassFormat(String n, String e, String s, String w) {
/*  77 */     this(new String[] { n, n + n + e, n + e, e + n + e, e, e + s + e, s + e, s + s + e, s, s + s + w, s + w, w + s + w, w, w + n + w, n + w, n + n + w });
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
/*     */   public CompassFormat(String[] directions) {
/*  93 */     ParamChecks.nullNotPermitted(directions, "directions");
/*  94 */     if (directions.length != 16) {
/*  95 */       throw new IllegalArgumentException("The 'directions' array must contain exactly 16 elements");
/*     */     }
/*     */     
/*  98 */     this.directions = directions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDirectionCode(double direction) {
/* 109 */     direction %= 360.0D;
/* 110 */     if (direction < 0.0D) {
/* 111 */       direction += 360.0D;
/*     */     }
/* 113 */     int index = ((int)Math.floor(direction / 11.25D) + 1) / 2;
/* 114 */     return this.directions[index];
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
/*     */   public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
/* 129 */     return toAppendTo.append(getDirectionCode(number));
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
/*     */   public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
/* 144 */     return toAppendTo.append(getDirectionCode(number));
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
/*     */   public Number parse(String source, ParsePosition parsePosition) {
/* 158 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/axis/CompassFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */