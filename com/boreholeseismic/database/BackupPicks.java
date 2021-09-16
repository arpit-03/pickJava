/*     */ package com.boreholeseismic.database;
/*     */ 
/*     */ import java.io.File;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.apache.http.HttpEntity;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpPost;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.entity.ContentType;
/*     */ import org.apache.http.entity.mime.MultipartEntityBuilder;
/*     */ import org.apache.http.impl.client.CloseableHttpClient;
/*     */ import org.apache.http.impl.client.HttpClients;
/*     */ import org.apache.http.util.EntityUtils;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.ReadableInstant;
/*     */ import org.joda.time.format.DateTimeFormat;
/*     */ import org.joda.time.format.DateTimeFormatter;
/*     */ import us.hebi.matlab.mat.format.Mat5;
/*     */ import us.hebi.matlab.mat.format.Mat5File;
/*     */ import us.hebi.matlab.mat.types.Matrix;
/*     */ import us.hebi.matlab.mat.types.Source;
/*     */ import us.hebi.matlab.mat.types.Sources;
/*     */ import us.hebi.matlab.mat.types.Struct;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BackupPicks
/*     */ {
/*     */   public BackupPicks(String currentFile, Authentication auth) {
/*  31 */     if (auth.currentUser == null) {
/*     */       return;
/*     */     }
/*     */     
/*  35 */     if (auth.currentProject == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  41 */       CloseableHttpClient client = HttpClients.createDefault();
/*  42 */       HttpPost httpPost = new HttpPost("https://boreholeseismic.biz/api/save_event");
/*  43 */       httpPost.addHeader("Authorization", "Bearer " + auth.currentUser.getToken());
/*     */       
/*  45 */       MultipartEntityBuilder builder = MultipartEntityBuilder.create();
/*     */       
/*  47 */       builder.addTextBody("name", (new File(currentFile)).getName());
/*     */       
/*  49 */       Source source = Sources.openFile(currentFile);
/*  50 */       Mat5File mat5File = Mat5.newReader(source).readMat();
/*  51 */       Struct headerData = mat5File.getStruct("headerData");
/*  52 */       String acqTime = String.valueOf(headerData.getStruct("acquisition").getChar("date").getString()) + headerData.getStruct("acquisition").getChar("time").getString() + headerData.getStruct("acquisition").getChar("secondFraction").getString();
/*  53 */       DateTimeFormatter formatter = DateTimeFormat.forPattern("MM/dd/yyyyHH:mm:ssSSS");
/*  54 */       DateTime acqDateTime = formatter.parseDateTime(acqTime);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  60 */       if (auth.currentProject != null) {
/*     */         
/*  62 */         System.out.println("auth.currentProject.fileStart");
/*  63 */         System.out.println(auth.currentProject.fileStart);
/*     */         
/*  65 */         if (auth.currentProject.fileStart == "null" || auth.currentProject.fileStart.length() == 0 || auth.currentProject.fileStart == "") {
/*  66 */           JOptionPane.showMessageDialog(new JFrame("Just checking.."), "Please set the file initials on the project menu in the portal - for QC purposes");
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  73 */         if (!(new File(currentFile)).getName().startsWith(auth.currentProject.fileStart)) {
/*  74 */           String message = "File name - " + (new File(currentFile)).getName() + " does not start with " + auth.currentProject.fileStart + " as specified on the portal. Are you sure its the right project?";
/*  75 */           int reply = JOptionPane.showConfirmDialog(null, message, "Just checking..", 0);
/*  76 */           if (reply == 1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       if (auth.getCurrentPickConfig() == null) {
/*     */         
/*  89 */         JOptionPane.showMessageDialog(new JFrame("Just checking.."), "No picking config selected. File will not be saved to the portal");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       if (auth.currentDesign != null) {
/*     */         
/* 104 */         double[][] traceData = getDoubleArray(mat5File.getMatrix("traceData"));
/* 105 */         double noRec = Math.ceil(((traceData[0]).length / 3));
/*     */         
/* 107 */         if (noRec != auth.currentDesign.noRec) {
/* 108 */           String message = "No of receivers do not match the design as specified on the portal - #" + String.valueOf(auth.currentDesign.noRec) + ". Are you sure its the right design?";
/* 109 */           int reply = JOptionPane.showConfirmDialog(null, message, "Just checking..", 0);
/* 110 */           if (reply == 1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (auth.currentStage != null) {
/*     */         
/* 123 */         DateTimeFormatter stageFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS ");
/* 124 */         DateTime startingStageTime = stageFormatter.parseDateTime(auth.currentStage.startTime.replace("T", " ").replace("Z", " "));
/* 125 */         DateTime endStageTime = stageFormatter.parseDateTime(auth.currentStage.endTime.replace("T", " ").replace("Z", " "));
/* 126 */         if (acqDateTime.isBefore((ReadableInstant)startingStageTime) || acqDateTime.isAfter((ReadableInstant)endStageTime)) {
/* 127 */           String message = "Discrepancy noticed between acquisition time & stage times from the portal. Are you sure you've selected the right stage?";
/* 128 */           int reply = JOptionPane.showConfirmDialog(null, message, "Just checking..", 0);
/* 129 */           if (reply == 1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       builder.addTextBody("acq_time", acqDateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS")));
/* 145 */       builder.addTextBody("pick_time", String.valueOf(mat5File.getMatrix("pickTime").getDouble(0, 0)));
/*     */       
/* 147 */       builder.addTextBody("ppick", getDoubleArrayString(mat5File.getMatrix("pickOutP")));
/* 148 */       builder.addTextBody("spick", getDoubleArrayString(mat5File.getMatrix("pickOutS")));
/* 149 */       builder.addTextBody("sspick", getDoubleArrayString(mat5File.getMatrix("pickOutSS")));
/* 150 */       builder.addTextBody("r1pick", getDoubleArrayString(mat5File.getMatrix("pickOutR1")));
/* 151 */       builder.addTextBody("r2pick", getDoubleArrayString(mat5File.getMatrix("pickOutR2")));
/* 152 */       builder.addTextBody("r3pick", getDoubleArrayString(mat5File.getMatrix("pickOutR3")));
/*     */       
/* 154 */       builder.addTextBody("auth_token", auth.currentUser.getToken());
/* 155 */       builder.addTextBody("user_id", String.valueOf(auth.currentUser.id));
/* 156 */       builder.addTextBody("project_id", String.valueOf(auth.currentProject.id));
/*     */       
/* 158 */       if (auth.currentDesign != null) {
/* 159 */         builder.addTextBody("design_id", String.valueOf(auth.currentDesign.id));
/*     */       }
/*     */       
/* 162 */       if (auth.currentStage != null) {
/* 163 */         builder.addTextBody("stage_id", String.valueOf(auth.currentStage.id));
/*     */       }
/*     */       
/* 166 */       if (auth.getCurrentPickConfig() != null) {
/* 167 */         builder.addTextBody("pick_config_id", String.valueOf((auth.getCurrentPickConfig()).id));
/*     */       }
/*     */ 
/*     */       
/* 171 */       File saveFileName = new File(currentFile.replace("mat", "png"));
/*     */       
/* 173 */       builder.addBinaryBody(
/* 174 */           "image", 
/* 175 */           saveFileName, 
/* 176 */           ContentType.APPLICATION_OCTET_STREAM, 
/* 177 */           saveFileName.getName());
/*     */ 
/*     */       
/* 180 */       HttpEntity multipart = builder.build();
/* 181 */       httpPost.setEntity(multipart);
/*     */       
/* 183 */       CloseableHttpResponse response = client.execute((HttpUriRequest)httpPost);
/* 184 */       System.out.println("Response code - ");
/* 185 */       System.out.println(response.getStatusLine().getStatusCode());
/*     */       
/* 187 */       if (response.getStatusLine().getStatusCode() != 200) {
/* 188 */         JOptionPane.showMessageDialog(new JFrame("File Backup Error"), "Could not save previous file to the portal. Some Error.");
/*     */       }
/*     */ 
/*     */       
/* 192 */       HttpEntity respEntity = response.getEntity();
/*     */       
/* 194 */       if (respEntity != null) {
/*     */         
/* 196 */         String content = EntityUtils.toString(respEntity);
/* 197 */         System.out.println(content);
/*     */       } 
/*     */ 
/*     */       
/* 201 */       client.close();
/*     */     }
/* 203 */     catch (Exception e) {
/* 204 */       JOptionPane.showMessageDialog(new JFrame("File Backup Error"), "Could not save previous file to the portal. Some Error.");
/*     */       
/* 206 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getDoubleArrayString(Matrix matrix) {
/* 213 */     String output = "{";
/*     */     
/* 215 */     for (int i = 0; i < matrix.getNumRows(); i++) {
/* 216 */       output = String.valueOf(output) + String.valueOf(matrix.getDouble(i, 1));
/* 217 */       if (i != matrix.getNumRows() - 1) {
/* 218 */         output = String.valueOf(output) + ",";
/*     */       }
/*     */     } 
/*     */     
/* 222 */     output = String.valueOf(output) + "}";
/*     */     
/* 224 */     return output;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[][] getDoubleArray(Matrix matrix) {
/* 229 */     double[][] output = new double[matrix.getNumRows()][matrix.getNumCols()];
/*     */     
/* 231 */     for (int i = 0; i < output.length; i++) {
/* 232 */       for (int j = 0; j < (output[0]).length; j++) {
/* 233 */         output[i][j] = matrix.getDouble(i, j);
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return output;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/BackupPicks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */