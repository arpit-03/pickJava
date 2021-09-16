/*    */ package com.boreholeseismic.database;
/*    */ 
/*    */ 
/*    */ public class User
/*    */ {
/*    */   int id;
/*    */   String firstname;
/*    */   String lastname;
/*    */   String auth_token;
/*    */   
/*    */   public User(int ip_id, String ip_firstname, String ip_lastname, String ip_auth_token) {
/* 12 */     this.id = ip_id;
/* 13 */     this.firstname = ip_firstname;
/* 14 */     this.lastname = ip_lastname;
/* 15 */     this.auth_token = ip_auth_token;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 19 */     return String.valueOf(this.firstname) + " " + this.lastname;
/*    */   }
/*    */   
/*    */   public String getToken() {
/* 23 */     return this.auth_token;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/database/User.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */