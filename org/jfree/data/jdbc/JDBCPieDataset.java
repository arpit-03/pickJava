/*     */ package org.jfree.data.jdbc;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.sql.Timestamp;
/*     */ import org.jfree.data.general.DefaultPieDataset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBCPieDataset
/*     */   extends DefaultPieDataset
/*     */ {
/*     */   static final long serialVersionUID = -8753216855496746108L;
/*     */   private transient Connection connection;
/*     */   
/*     */   public JDBCPieDataset(String url, String driverName, String user, String password) throws SQLException, ClassNotFoundException {
/* 106 */     Class.forName(driverName);
/* 107 */     this.connection = DriverManager.getConnection(url, user, password);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JDBCPieDataset(Connection con) {
/* 118 */     if (con == null) {
/* 119 */       throw new NullPointerException("A connection must be supplied.");
/*     */     }
/* 121 */     this.connection = con;
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
/*     */   public JDBCPieDataset(Connection con, String query) throws SQLException {
/* 136 */     this(con);
/* 137 */     executeQuery(query);
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
/*     */   public void executeQuery(String query) throws SQLException {
/* 152 */     executeQuery(this.connection, query);
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
/*     */   public void executeQuery(Connection con, String query) throws SQLException {
/* 169 */     Statement statement = null;
/* 170 */     ResultSet resultSet = null;
/*     */     
/*     */     try {
/* 173 */       statement = con.createStatement();
/* 174 */       resultSet = statement.executeQuery(query);
/* 175 */       ResultSetMetaData metaData = resultSet.getMetaData();
/*     */       
/* 177 */       int columnCount = metaData.getColumnCount();
/* 178 */       if (columnCount != 2) {
/* 179 */         throw new SQLException("Invalid sql generated.  PieDataSet requires 2 columns only");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 184 */       int columnType = metaData.getColumnType(2);
/*     */       
/* 186 */       while (resultSet.next()) {
/* 187 */         double value; Timestamp date; Comparable key = resultSet.getString(1);
/* 188 */         switch (columnType) {
/*     */           case -5:
/*     */           case 2:
/*     */           case 3:
/*     */           case 4:
/*     */           case 6:
/*     */           case 7:
/*     */           case 8:
/* 196 */             value = resultSet.getDouble(2);
/* 197 */             setValue(key, value);
/*     */             continue;
/*     */           
/*     */           case 91:
/*     */           case 92:
/*     */           case 93:
/* 203 */             date = resultSet.getTimestamp(2);
/* 204 */             value = date.getTime();
/* 205 */             setValue(key, value);
/*     */             continue;
/*     */         } 
/*     */         
/* 209 */         System.err.println("JDBCPieDataset - unknown data type");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 215 */       fireDatasetChanged();
/*     */     }
/*     */     finally {
/*     */       
/* 219 */       if (resultSet != null) {
/*     */         try {
/* 221 */           resultSet.close();
/*     */         }
/* 223 */         catch (Exception e) {
/* 224 */           System.err.println("JDBCPieDataset: swallowing exception.");
/*     */         } 
/*     */       }
/* 227 */       if (statement != null) {
/*     */         try {
/* 229 */           statement.close();
/*     */         }
/* 231 */         catch (Exception e) {
/* 232 */           System.err.println("JDBCPieDataset: swallowing exception.");
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 244 */       this.connection.close();
/*     */     }
/* 246 */     catch (Exception e) {
/* 247 */       System.err.println("JdbcXYDataset: swallowing exception.");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/data/jdbc/JDBCPieDataset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */