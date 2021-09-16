/*     */ package org.jfree.data.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import org.jfree.data.Range;
/*     */ import org.jfree.data.RangeInfo;
/*     */ import org.jfree.data.xy.AbstractXYDataset;
/*     */ import org.jfree.data.xy.TableXYDataset;
/*     */ import org.jfree.data.xy.XYDataset;
/*     */ import org.jfree.util.Log;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBCXYDataset
/*     */   extends AbstractXYDataset
/*     */   implements XYDataset, TableXYDataset, RangeInfo
/*     */ {
/*     */   private transient Connection connection;
/* 109 */   private String[] columnNames = new String[0];
/*     */ 
/*     */   
/*     */   private ArrayList rows;
/*     */ 
/*     */   
/* 115 */   private double maxValue = 0.0D;
/*     */ 
/*     */   
/* 118 */   private double minValue = 0.0D;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTimeSeries = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JDBCXYDataset() {
/* 128 */     this.rows = new ArrayList();
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
/*     */   public JDBCXYDataset(String url, String driverName, String user, String password) throws SQLException, ClassNotFoundException {
/* 149 */     this();
/* 150 */     Class.forName(driverName);
/* 151 */     this.connection = DriverManager.getConnection(url, user, password);
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
/*     */   public JDBCXYDataset(Connection con) throws SQLException {
/* 163 */     this();
/* 164 */     this.connection = con;
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
/*     */   public JDBCXYDataset(Connection con, String query) throws SQLException {
/* 177 */     this(con);
/* 178 */     executeQuery(query);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTimeSeries() {
/* 188 */     return this.isTimeSeries;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTimeSeries(boolean timeSeries) {
/* 198 */     this.isTimeSeries = timeSeries;
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
/*     */   public void executeQuery(String query) throws SQLException {
/* 214 */     executeQuery(this.connection, query);
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
/*     */   public void executeQuery(Connection con, String query) throws SQLException {
/* 233 */     if (con == null) {
/* 234 */       throw new SQLException("There is no database to execute the query.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 239 */     ResultSet resultSet = null;
/* 240 */     Statement statement = null;
/*     */     try {
/* 242 */       statement = con.createStatement();
/* 243 */       resultSet = statement.executeQuery(query);
/* 244 */       ResultSetMetaData metaData = resultSet.getMetaData();
/*     */       
/* 246 */       int numberOfColumns = metaData.getColumnCount();
/* 247 */       int numberOfValidColumns = 0;
/* 248 */       int[] columnTypes = new int[numberOfColumns];
/* 249 */       for (int column = 0; column < numberOfColumns; column++) {
/*     */         try {
/* 251 */           int type = metaData.getColumnType(column + 1);
/* 252 */           switch (type) {
/*     */             
/*     */             case -7:
/*     */             case -5:
/*     */             case 2:
/*     */             case 3:
/*     */             case 4:
/*     */             case 5:
/*     */             case 6:
/*     */             case 7:
/*     */             case 8:
/*     */             case 91:
/*     */             case 92:
/*     */             case 93:
/* 266 */               numberOfValidColumns++;
/* 267 */               columnTypes[column] = type;
/*     */               break;
/*     */             default:
/* 270 */               Log.warn("Unable to load column " + column + " (" + type + "," + metaData
/*     */ 
/*     */                   
/* 273 */                   .getColumnClassName(column + 1) + ")");
/*     */ 
/*     */               
/* 276 */               columnTypes[column] = 0;
/*     */               break;
/*     */           } 
/*     */         
/* 280 */         } catch (SQLException e) {
/* 281 */           columnTypes[column] = 0;
/* 282 */           throw e;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 287 */       if (numberOfValidColumns <= 1) {
/* 288 */         throw new SQLException("Not enough valid columns where generated by query.");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 294 */       this.columnNames = new String[numberOfValidColumns - 1];
/*     */       
/* 296 */       int currentColumn = 0; int i;
/* 297 */       for (i = 1; i < numberOfColumns; i++) {
/* 298 */         if (columnTypes[i] != 0) {
/* 299 */           this.columnNames[currentColumn] = metaData
/* 300 */             .getColumnLabel(i + 1);
/* 301 */           currentColumn++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 306 */       if (this.rows != null) {
/* 307 */         for (i = 0; i < this.rows.size(); i++) {
/* 308 */           ArrayList row = this.rows.get(i);
/* 309 */           row.clear();
/*     */         } 
/* 311 */         this.rows.clear();
/*     */       } 
/*     */ 
/*     */       
/* 315 */       switch (columnTypes[0]) {
/*     */         case 91:
/*     */         case 92:
/*     */         case 93:
/* 319 */           this.isTimeSeries = true;
/*     */           break;
/*     */         default:
/* 322 */           this.isTimeSeries = false;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 328 */       while (resultSet.next()) {
/* 329 */         ArrayList<Object> newRow = new ArrayList();
/* 330 */         for (int j = 0; j < numberOfColumns; j++) {
/* 331 */           Object xObject = resultSet.getObject(j + 1);
/* 332 */           switch (columnTypes[j]) {
/*     */             case -5:
/*     */             case 2:
/*     */             case 3:
/*     */             case 4:
/*     */             case 5:
/*     */             case 6:
/*     */             case 7:
/*     */             case 8:
/* 341 */               newRow.add(xObject);
/*     */               break;
/*     */             
/*     */             case 91:
/*     */             case 92:
/*     */             case 93:
/* 347 */               newRow.add(new Long(((Date)xObject).getTime()));
/*     */               break;
/*     */             case 0:
/*     */               break;
/*     */             default:
/* 352 */               System.err.println("Unknown data");
/* 353 */               columnTypes[j] = 0;
/*     */               break;
/*     */           } 
/*     */         } 
/* 357 */         this.rows.add(newRow);
/*     */       } 
/*     */ 
/*     */       
/* 361 */       if (this.rows.isEmpty()) {
/* 362 */         ArrayList<Integer> newRow = new ArrayList();
/* 363 */         for (int j = 0; j < numberOfColumns; j++) {
/* 364 */           if (columnTypes[j] != 0) {
/* 365 */             newRow.add(new Integer(0));
/*     */           }
/*     */         } 
/* 368 */         this.rows.add(newRow);
/*     */       } 
/*     */ 
/*     */       
/* 372 */       if (this.rows.size() < 1) {
/* 373 */         this.maxValue = 0.0D;
/* 374 */         this.minValue = 0.0D;
/*     */       } else {
/*     */         
/* 377 */         this.maxValue = Double.NEGATIVE_INFINITY;
/* 378 */         this.minValue = Double.POSITIVE_INFINITY;
/* 379 */         for (int rowNum = 0; rowNum < this.rows.size(); rowNum++) {
/* 380 */           ArrayList row = this.rows.get(rowNum);
/* 381 */           for (int j = 1; j < numberOfColumns; j++) {
/* 382 */             Object testValue = row.get(j);
/* 383 */             if (testValue != null) {
/* 384 */               double test = ((Number)testValue).doubleValue();
/*     */               
/* 386 */               if (test < this.minValue) {
/* 387 */                 this.minValue = test;
/*     */               }
/* 389 */               if (test > this.maxValue) {
/* 390 */                 this.maxValue = test;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 397 */       fireDatasetChanged();
/*     */     } finally {
/*     */       
/* 400 */       if (resultSet != null) {
/*     */         try {
/* 402 */           resultSet.close();
/*     */         }
/* 404 */         catch (Exception e) {}
/*     */       }
/*     */ 
/*     */       
/* 408 */       if (statement != null) {
/*     */         try {
/* 410 */           statement.close();
/*     */         }
/* 412 */         catch (Exception e) {}
/*     */       }
/*     */     } 
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
/*     */   public Number getX(int seriesIndex, int itemIndex) {
/* 434 */     ArrayList<Number> row = this.rows.get(itemIndex);
/* 435 */     return row.get(0);
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
/*     */   public Number getY(int seriesIndex, int itemIndex) {
/* 450 */     ArrayList<Number> row = this.rows.get(itemIndex);
/* 451 */     return row.get(seriesIndex + 1);
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
/*     */   public int getItemCount(int seriesIndex) {
/* 465 */     return this.rows.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 476 */     return getItemCount(0);
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
/*     */   public int getSeriesCount() {
/* 489 */     return this.columnNames.length;
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
/*     */   public Comparable getSeriesKey(int seriesIndex) {
/* 505 */     if (seriesIndex < this.columnNames.length && this.columnNames[seriesIndex] != null)
/*     */     {
/* 507 */       return this.columnNames[seriesIndex];
/*     */     }
/*     */     
/* 510 */     return "";
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
/*     */   public int getLegendItemCount() {
/* 525 */     return getSeriesCount();
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
/*     */   public String[] getLegendItemLabels() {
/* 538 */     return this.columnNames;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 547 */       this.connection.close();
/*     */     }
/* 549 */     catch (Exception e) {
/* 550 */       System.err.println("JdbcXYDataset: swallowing exception.");
/*     */     } 
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
/*     */   public double getRangeLowerBound(boolean includeInterval) {
/* 565 */     return this.minValue;
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
/*     */   public double getRangeUpperBound(boolean includeInterval) {
/* 578 */     return this.maxValue;
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
/*     */   public Range getRangeBounds(boolean includeInterval) {
/* 591 */     return new Range(this.minValue, this.maxValue);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/jdbc/JDBCXYDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */