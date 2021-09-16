/*    */ package com.boreholeseismic.database;
/*    */ 
/*    */ public class PickConfig
/*    */ {
/*    */   int id;
/*    */   String name;
/*    */   
/*    */   public PickConfig(int ip_id, String ip_name) {
/*  9 */     this.id = ip_id;
/* 10 */     this.name = ip_name;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 14 */     return "Pick Config Name - " + this.name + " id - " + String.valueOf(this.id);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 18 */     return this.name;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/PickConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */