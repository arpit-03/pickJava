/*     */ package org.jfree.chart.imagemap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import org.jfree.chart.ChartRenderingInfo;
/*     */ import org.jfree.chart.entity.ChartEntity;
/*     */ import org.jfree.chart.entity.EntityCollection;
/*     */ import org.jfree.chart.util.ParamChecks;
/*     */ import org.jfree.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ImageMapUtilities
/*     */ {
/*     */   public static void writeImageMap(PrintWriter writer, String name, ChartRenderingInfo info) throws IOException {
/*  82 */     writeImageMap(writer, name, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
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
/*     */   public static void writeImageMap(PrintWriter writer, String name, ChartRenderingInfo info, boolean useOverLibForToolTips) throws IOException {
/*     */     ToolTipTagFragmentGenerator toolTipTagFragmentGenerator;
/* 104 */     if (useOverLibForToolTips) {
/* 105 */       toolTipTagFragmentGenerator = new OverLIBToolTipTagFragmentGenerator();
/*     */     }
/*     */     else {
/*     */       
/* 109 */       toolTipTagFragmentGenerator = new StandardToolTipTagFragmentGenerator();
/*     */     } 
/*     */     
/* 112 */     writeImageMap(writer, name, info, toolTipTagFragmentGenerator, new StandardURLTagFragmentGenerator());
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
/*     */   public static void writeImageMap(PrintWriter writer, String name, ChartRenderingInfo info, ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator) throws IOException {
/* 139 */     writer.println(getImageMap(name, info, toolTipTagFragmentGenerator, urlTagFragmentGenerator));
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
/*     */   public static String getImageMap(String name, ChartRenderingInfo info) {
/* 153 */     return getImageMap(name, info, new StandardToolTipTagFragmentGenerator(), new StandardURLTagFragmentGenerator());
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
/*     */   public static String getImageMap(String name, ChartRenderingInfo info, ToolTipTagFragmentGenerator toolTipTagFragmentGenerator, URLTagFragmentGenerator urlTagFragmentGenerator) {
/* 177 */     StringBuilder sb = new StringBuilder();
/* 178 */     sb.append("<map id=\"").append(htmlEscape(name));
/* 179 */     sb.append("\" name=\"").append(htmlEscape(name)).append("\">");
/* 180 */     sb.append(StringUtils.getLineSeparator());
/* 181 */     EntityCollection entities = info.getEntityCollection();
/* 182 */     if (entities != null) {
/* 183 */       int count = entities.getEntityCount();
/* 184 */       for (int i = count - 1; i >= 0; i--) {
/* 185 */         ChartEntity entity = entities.getEntity(i);
/* 186 */         if (entity.getToolTipText() != null || entity
/* 187 */           .getURLText() != null) {
/* 188 */           String area = entity.getImageMapAreaTag(toolTipTagFragmentGenerator, urlTagFragmentGenerator);
/*     */ 
/*     */           
/* 191 */           if (area.length() > 0) {
/* 192 */             sb.append(area);
/* 193 */             sb.append(StringUtils.getLineSeparator());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 198 */     sb.append("</map>");
/* 199 */     return sb.toString();
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
/*     */   public static String htmlEscape(String input) {
/* 214 */     ParamChecks.nullNotPermitted(input, "input");
/* 215 */     StringBuilder result = new StringBuilder();
/* 216 */     int length = input.length();
/* 217 */     for (int i = 0; i < length; i++) {
/* 218 */       char c = input.charAt(i);
/* 219 */       if (c == '&') {
/* 220 */         result.append("&amp;");
/*     */       }
/* 222 */       else if (c == '"') {
/* 223 */         result.append("&quot;");
/*     */       }
/* 225 */       else if (c == '<') {
/* 226 */         result.append("&lt;");
/*     */       }
/* 228 */       else if (c == '>') {
/* 229 */         result.append("&gt;");
/*     */       }
/* 231 */       else if (c == '\'') {
/* 232 */         result.append("&#39;");
/*     */       }
/* 234 */       else if (c == '\\') {
/* 235 */         result.append("&#092;");
/*     */       } else {
/*     */         
/* 238 */         result.append(c);
/*     */       } 
/*     */     } 
/* 241 */     return result.toString();
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
/*     */   public static String javascriptEscape(String input) {
/* 255 */     ParamChecks.nullNotPermitted(input, "input");
/* 256 */     StringBuilder result = new StringBuilder();
/* 257 */     int length = input.length();
/* 258 */     for (int i = 0; i < length; i++) {
/* 259 */       char c = input.charAt(i);
/* 260 */       if (c == '"') {
/* 261 */         result.append("\\\"");
/*     */       }
/* 263 */       else if (c == '\'') {
/* 264 */         result.append("\\'");
/*     */       }
/* 266 */       else if (c == '\\') {
/* 267 */         result.append("\\\\");
/*     */       } else {
/*     */         
/* 270 */         result.append(c);
/*     */       } 
/*     */     } 
/* 273 */     return result.toString();
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/imagemap/ImageMapUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */