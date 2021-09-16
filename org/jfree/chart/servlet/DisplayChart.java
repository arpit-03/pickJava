/*     */ package org.jfree.chart.servlet;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ import javax.servlet.http.HttpSession;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DisplayChart
/*     */   extends HttpServlet
/*     */ {
/*     */   public void init() throws ServletException {}
/*     */   
/*     */   public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/* 106 */     HttpSession session = request.getSession();
/* 107 */     String filename = request.getParameter("filename");
/*     */     
/* 109 */     if (filename == null) {
/* 110 */       throw new ServletException("Parameter 'filename' must be supplied");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     filename = ServletUtilities.searchReplace(filename, "..", "");
/*     */ 
/*     */     
/* 118 */     File file = new File(System.getProperty("java.io.tmpdir"), filename);
/* 119 */     if (!file.exists()) {
/* 120 */       throw new ServletException("Unable to display the chart with the filename '" + filename + "'.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     boolean isChartInUserList = false;
/* 128 */     ChartDeleter chartDeleter = (ChartDeleter)session.getAttribute("JFreeChart_Deleter");
/*     */     
/* 130 */     if (chartDeleter != null) {
/* 131 */       isChartInUserList = chartDeleter.isChartAvailable(filename);
/*     */     }
/*     */     
/* 134 */     boolean isChartPublic = false;
/* 135 */     if (filename.length() >= 6 && 
/* 136 */       filename.substring(0, 6).equals("public")) {
/* 137 */       isChartPublic = true;
/*     */     }
/*     */ 
/*     */     
/* 141 */     boolean isOneTimeChart = false;
/* 142 */     if (filename.startsWith(ServletUtilities.getTempOneTimeFilePrefix())) {
/* 143 */       isOneTimeChart = true;
/*     */     }
/*     */     
/* 146 */     if (isChartInUserList || isChartPublic || isOneTimeChart) {
/*     */       
/* 148 */       ServletUtilities.sendTempFile(file, response);
/* 149 */       if (isOneTimeChart) {
/* 150 */         file.delete();
/*     */       }
/*     */     } else {
/*     */       
/* 154 */       throw new ServletException("Chart image not found");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/chart/servlet/DisplayChart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */