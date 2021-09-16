/*    */ package com.boreholeseismic.database;
/*    */ 
/*    */ public class Project
/*    */ {
/*    */   int id;
/*    */   private String name;
/*    */   String fileStart;
/*    */   Stage[] stages;
/*    */   PickConfig[] pick_configs;
/*    */   
/*    */   public Project(int ip_id, String ip_name, String ip_fileStart) {
/* 12 */     this.id = ip_id;
/* 13 */     this.name = ip_name;
/* 14 */     this.fileStart = ip_fileStart;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 18 */     return "Name - " + getName() + " id - " + String.valueOf(this.id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 22 */     return String.valueOf(this.id);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 26 */     return this.name;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/Project.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */