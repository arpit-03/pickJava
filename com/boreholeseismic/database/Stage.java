/*    */ package com.boreholeseismic.database;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Stage
/*    */ {
/*    */   int id;
/*    */   String stage_name;
/*    */   String well_name;
/*    */   String display_name;
/*    */   String startTime;
/*    */   String endTime;
/*    */   
/*    */   public Stage(int id, String well_name, String stage_name, String startTime, String endTime) {
/* 21 */     this.id = id;
/* 22 */     this.well_name = well_name;
/* 23 */     this.stage_name = stage_name;
/* 24 */     this.startTime = startTime;
/* 25 */     this.endTime = endTime;
/* 26 */     this.display_name = String.valueOf(getWellName()) + " - " + getStageName();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 32 */     return this.display_name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStageName() {
/* 37 */     return this.stage_name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getWellName() {
/* 42 */     return this.well_name;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/Stage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */