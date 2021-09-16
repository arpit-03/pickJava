/*     */ package com.boreholeseismic.database;
/*     */ 
/*     */ import java.awt.GridLayout;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URI;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.http.client.methods.CloseableHttpResponse;
/*     */ import org.apache.http.client.methods.HttpGet;
/*     */ import org.apache.http.client.methods.HttpUriRequest;
/*     */ import org.apache.http.client.utils.URIBuilder;
/*     */ import org.apache.http.impl.client.DefaultHttpClient;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ import org.json.JSONTokener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Authentication
/*     */ {
/*     */   boolean authenticated = false;
/*  32 */   User currentUser = null;
/*  33 */   Project currentProject = null;
/*  34 */   Stage currentStage = null;
/*  35 */   Design currentDesign = null;
/*  36 */   PickConfig currentPickConfig = null;
/*     */   
/*  38 */   String domainName = "https://boreholeseismic.biz";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public User getCurrentUser() {
/*  45 */     return this.currentUser;
/*     */   }
/*     */   
/*     */   public Project getCurrentProject() {
/*  49 */     return this.currentProject;
/*     */   }
/*     */   
/*     */   public Design getCurrentDesign() {
/*  53 */     return this.currentDesign;
/*     */   }
/*     */   
/*     */   public boolean isAuthenticated() {
/*  57 */     return this.authenticated;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void authenticate() {
/*  63 */     String message = JOptionPane.showInputDialog(null, "Enter Auth Token", "User Auth", 3);
/*     */     
/*  65 */     if (message == null) {
/*  66 */       message = "";
/*  67 */       this.currentUser = null;
/*  68 */       this.currentProject = null;
/*  69 */       this.currentStage = null;
/*  70 */       this.currentDesign = null;
/*  71 */       this.currentPickConfig = null;
/*     */     } 
/*     */     
/*  74 */     if (message.length() > 0) {
/*     */       
/*  76 */       HttpGet getRequest = new HttpGet(
/*  77 */           String.valueOf(this.domainName) + "/api/check_user");
/*     */ 
/*     */       
/*     */       try {
/*  81 */         URI uri = (new URIBuilder(getRequest.getURI())).addParameter("token", message).build();
/*  82 */         getRequest.setURI(uri);
/*  83 */         getRequest.addHeader("accept", "application/json");
/*     */         
/*  85 */         DefaultHttpClient httpClient = new DefaultHttpClient();
/*     */ 
/*     */         
/*  88 */         CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)getRequest);
/*  89 */         if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
/*  90 */           this.authenticated = false;
/*  91 */           JOptionPane.showMessageDialog(new JFrame("Error"), "User not authenticated. Please retry.");
/*     */           
/*     */           return;
/*     */         } 
/*  95 */         BufferedReader br = new BufferedReader(
/*  96 */             new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
/*     */         
/*  98 */         String output = IOUtils.toString(br);
/*     */         
/* 100 */         JSONObject userObject = new JSONObject(output);
/*     */         
/* 102 */         this.currentUser = new User(
/* 103 */             userObject.getInt("id"), 
/* 104 */             userObject.getString("firstname"), 
/* 105 */             userObject.getString("lastname"), 
/* 106 */             message);
/*     */ 
/*     */         
/* 109 */         this.authenticated = true;
/*     */       
/*     */       }
/* 112 */       catch (Exception e2) {
/* 113 */         this.authenticated = false;
/* 114 */         this.currentUser = null;
/* 115 */         this.currentProject = null;
/* 116 */         this.currentStage = null;
/* 117 */         this.currentPickConfig = null;
/* 118 */         this.currentDesign = null;
/* 119 */         JOptionPane.showMessageDialog(new JFrame("Error"), "Some error while authentication.");
/* 120 */         e2.printStackTrace();
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
/*     */   public void getProject() {
/* 136 */     if (this.currentUser == null) {
/* 137 */       JOptionPane.showMessageDialog(new JFrame("Error"), "Picker not authenticated yet");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 143 */       DefaultHttpClient httpClient = new DefaultHttpClient();
/* 144 */       HttpGet getRequest = new HttpGet(
/* 145 */           String.valueOf(this.domainName) + "/api/projects_info");
/*     */       
/* 147 */       URI uri = (new URIBuilder(getRequest.getURI())).addParameter("auth_token", this.currentUser.getToken()).build();
/* 148 */       getRequest.setURI(uri);
/*     */       
/* 150 */       getRequest.addHeader("accept", "application/json");
/* 151 */       getRequest.addHeader("Authorization", "Bearer " + this.currentUser.getToken());
/*     */       
/* 153 */       CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)getRequest);
/*     */       
/* 155 */       if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
/* 156 */         throw new RuntimeException("Failed : HTTP error code : " + 
/* 157 */             closeableHttpResponse.getStatusLine().getStatusCode());
/*     */       }
/*     */       
/* 160 */       BufferedReader br = new BufferedReader(
/* 161 */           new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
/*     */       
/* 163 */       String output = IOUtils.toString(br);
/*     */       
/* 165 */       JSONTokener json = new JSONTokener(output);
/*     */       
/* 167 */       JSONArray projectNames = new JSONArray(json);
/*     */       
/* 169 */       String[] list = new String[projectNames.length()];
/* 170 */       Project[] projects = new Project[projectNames.length()];
/*     */       
/* 172 */       for (int i = 0; i < projectNames.length(); i++) {
/* 173 */         projects[i] = new Project(
/* 174 */             projectNames.getJSONObject(i).getInt("id"), 
/* 175 */             projectNames.getJSONObject(i).getString("name"), 
/* 176 */             projectNames.getJSONObject(i).get("event_file_start").toString());
/*     */         
/* 178 */         list[i] = projects[i].getName();
/*     */       } 
/*     */       
/* 181 */       JComboBox<String> jcb = new JComboBox<>(list);
/*     */ 
/*     */       
/* 184 */       int option = JOptionPane.showConfirmDialog(null, jcb, "Select Project Name", 2, 3);
/*     */       
/* 186 */       if (option == 0)
/*     */       {
/* 188 */         for (int j = 0; j < projects.length; j++) {
/* 189 */           if (projects[j].getName() == jcb.getSelectedItem()) {
/* 190 */             this.currentProject = projects[j];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             break;
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
/* 214 */       httpClient.getConnectionManager().shutdown();
/*     */     }
/* 216 */     catch (Exception e) {
/*     */       
/* 218 */       this.currentProject = null;
/* 219 */       this.currentStage = null;
/* 220 */       this.currentDesign = null;
/* 221 */       this.currentPickConfig = null;
/*     */       
/* 223 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getProjectDetails() {
/* 233 */     if (this.currentUser == null) {
/* 234 */       JOptionPane.showMessageDialog(new JFrame("Error"), "Picker not authenticated yet");
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     if (this.currentProject == null) {
/* 239 */       JOptionPane.showMessageDialog(new JFrame("Error"), "No project selected.");
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 245 */       DefaultHttpClient httpClient = new DefaultHttpClient();
/* 246 */       HttpGet getRequest = new HttpGet(
/* 247 */           String.valueOf(this.domainName) + "/api/project_details");
/*     */       
/* 249 */       URI uri = (new URIBuilder(getRequest.getURI()))
/* 250 */         .addParameter("auth_token", this.currentUser.getToken())
/* 251 */         .addParameter("project_id", this.currentProject.getId())
/* 252 */         .build();
/* 253 */       getRequest.setURI(uri);
/* 254 */       getRequest.addHeader("accept", "application/json");
/* 255 */       getRequest.addHeader("Authorization", "Bearer " + this.currentUser.getToken());
/*     */       
/* 257 */       CloseableHttpResponse closeableHttpResponse = httpClient.execute((HttpUriRequest)getRequest);
/*     */       
/* 259 */       if (closeableHttpResponse.getStatusLine().getStatusCode() != 200) {
/* 260 */         throw new RuntimeException("Failed : HTTP error code : " + 
/* 261 */             closeableHttpResponse.getStatusLine().getStatusCode());
/*     */       }
/*     */       
/* 264 */       BufferedReader br = new BufferedReader(
/* 265 */           new InputStreamReader(closeableHttpResponse.getEntity().getContent()));
/*     */       
/* 267 */       String output = IOUtils.toString(br);
/*     */ 
/*     */ 
/*     */       
/* 271 */       JSONObject json = new JSONObject(new JSONTokener(output));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 278 */       JSONArray designs_json = json.getJSONArray("designs");
/*     */       
/* 280 */       Design[] designs = new Design[designs_json.length()];
/* 281 */       String[] designs_list = new String[designs_json.length()];
/*     */ 
/*     */       
/* 284 */       for (int i = 0; i < designs_json.length(); i++) {
/*     */         double[][][] rotationMatrix;
/* 286 */         JSONArray recArrayJSON = designs_json.getJSONObject(i).getJSONArray("rec_array");
/* 287 */         int[] recArray = new int[recArrayJSON.length()];
/*     */         
/* 289 */         for (int m = 0; m < recArrayJSON.length(); m++) {
/* 290 */           recArray[m] = recArrayJSON.getInt(m);
/*     */         }
/*     */         
/* 293 */         JSONArray recArrayNameJSON = designs_json.getJSONObject(i).getJSONArray("rec_array_name");
/* 294 */         String[] recNameArray = new String[recArrayNameJSON.length()];
/*     */         
/* 296 */         for (int n = 0; n < recArrayNameJSON.length(); n++) {
/* 297 */           recNameArray[n] = recArrayNameJSON.getString(n);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 306 */           JSONArray rotationMatrixJSON = designs_json.getJSONObject(i).getJSONArray("rotation_matrix");
/* 307 */           int count = rotationMatrixJSON.length();
/* 308 */           rotationMatrix = new double[3][3][count];
/*     */           
/* 310 */           for (int d3 = 0; d3 < count; d3++) {
/* 311 */             JSONArray rotationMatrixReceiver = rotationMatrixJSON.getJSONArray(d3);
/* 312 */             int rotMatCount = 0;
/* 313 */             for (int d2 = 0; d2 < 3; d2++) {
/* 314 */               for (int d1 = 0; d1 < 3; d1++) {
/* 315 */                 rotationMatrix[d2][d1][d3] = rotationMatrixReceiver.getDouble(rotMatCount);
/* 316 */                 rotMatCount++;
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           } 
/* 322 */         } catch (Exception e) {
/* 323 */           rotationMatrix = new double[3][3][0];
/* 324 */           System.out.println("Error while creating rotation matrix");
/* 325 */           e.printStackTrace();
/*     */         } 
/*     */         
/* 328 */         designs[i] = new Design(
/* 329 */             designs_json.getJSONObject(i).getInt("id"), 
/* 330 */             designs_json.getJSONObject(i).getString("name"), 
/* 331 */             designs_json.getJSONObject(i).getInt("no_rec"), 
/* 332 */             rotationMatrix, 
/* 333 */             designs_json.getJSONObject(i).getDouble("trc_pol_11"), 
/* 334 */             designs_json.getJSONObject(i).getDouble("trc_pol_12"), 
/* 335 */             designs_json.getJSONObject(i).getDouble("trc_pol_13"), 
/* 336 */             designs_json.getJSONObject(i).getDouble("trc_order_11"), 
/* 337 */             designs_json.getJSONObject(i).getDouble("trc_order_12"), 
/* 338 */             designs_json.getJSONObject(i).getDouble("trc_order_13"), 
/* 339 */             recArray, 
/* 340 */             recNameArray);
/*     */ 
/*     */         
/* 343 */         designs_list[i] = (designs[i]).name;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 353 */       JSONArray stages_json = json.getJSONArray("stages");
/*     */       
/* 355 */       Stage[] stages = new Stage[stages_json.length()];
/* 356 */       String[] stages_list = new String[stages_json.length()];
/*     */       
/* 358 */       for (int j = 0; j < stages_json.length(); j++) {
/*     */         
/* 360 */         stages[j] = new Stage(
/* 361 */             stages_json.getJSONObject(j).getInt("id"), 
/* 362 */             stages_json.getJSONObject(j).getString("well"), 
/* 363 */             stages_json.getJSONObject(j).getString("stage"), 
/* 364 */             stages_json.getJSONObject(j).getString("start_time"), 
/* 365 */             stages_json.getJSONObject(j).getString("end_time"));
/*     */         
/* 367 */         stages_list[j] = stages[j].getDisplayName();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 375 */       JSONArray pick_configs_json = json.getJSONArray("pick_configs");
/*     */       
/* 377 */       PickConfig[] pick_configs = new PickConfig[pick_configs_json.length()];
/* 378 */       String[] pick_configs_list = new String[pick_configs_json.length()];
/*     */       
/* 380 */       for (int k = 0; k < pick_configs_json.length(); k++) {
/* 381 */         pick_configs[k] = new PickConfig(pick_configs_json.getJSONObject(k).getInt("id"), pick_configs_json.getJSONObject(k).getString("name"));
/* 382 */         pick_configs_list[k] = (pick_configs[k]).name;
/*     */       } 
/*     */       
/* 385 */       httpClient.getConnectionManager().shutdown();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 393 */       JFrame frame = new JFrame("Select Project Details");
/*     */       
/* 395 */       JPanel pane = new JPanel();
/* 396 */       pane.setLayout(new GridLayout(0, 2, 2, 2));
/*     */       
/* 398 */       JComboBox<String> pickConfigField = new JComboBox<>(pick_configs_list);
/* 399 */       JComboBox<String> designField = new JComboBox<>(designs_list);
/* 400 */       JComboBox<String> stageField = new JComboBox<>(stages_list);
/*     */ 
/*     */       
/* 403 */       pane.add(new JLabel("Picking Config?"));
/* 404 */       pane.add(pickConfigField);
/*     */       
/* 406 */       pane.add(new JLabel("Design?"));
/* 407 */       pane.add(designField);
/*     */       
/* 409 */       pane.add(new JLabel("Stage #?"));
/* 410 */       pane.add(stageField);
/*     */ 
/*     */       
/* 413 */       int option = JOptionPane.showConfirmDialog(frame, pane, "Project Details", 2, 1);
/*     */       
/* 415 */       if (option == 0) {
/*     */         int m;
/* 417 */         for (m = 0; m < pick_configs.length; m++) {
/* 418 */           if ((pick_configs[m]).name == pickConfigField.getSelectedItem()) {
/* 419 */             this.currentPickConfig = pick_configs[m];
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 424 */         for (m = 0; m < designs.length; m++) {
/* 425 */           if ((designs[m]).name == designField.getSelectedItem()) {
/* 426 */             this.currentDesign = designs[m];
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 431 */         for (m = 0; m < stages.length; m++) {
/* 432 */           if ((stages[m]).display_name == stageField.getSelectedItem()) {
/* 433 */             this.currentStage = stages[m];
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 441 */     } catch (Exception e) {
/*     */       
/* 443 */       e.printStackTrace();
/*     */       
/* 445 */       this.currentStage = null;
/* 446 */       this.currentDesign = null;
/* 447 */       this.currentPickConfig = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PickConfig getCurrentPickConfig() {
/* 458 */     return this.currentPickConfig;
/*     */   }
/*     */ 
/*     */   
/*     */   public Stage getCurrentStage() {
/* 463 */     return this.currentStage;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/Authentication.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */