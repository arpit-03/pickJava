/*    */ package com.boreholeseismic.dsp;
/*    */ 
/*    */ public class FilterSettings
/*    */ {
/*  5 */   public int BPLow = 60; public int BPHigh = 360; public int BPEdge = 20; public boolean BPFlag = true;
/*    */   public boolean IntFlag = true;
/*  7 */   public String noOfWellsString = "1", noOfRecsString = "[,]"; public boolean DetrendFlag = false; public boolean LPFlag = true;
/*  8 */   public String toolstringNames = "[,]";
/*    */   
/* 10 */   public double LPWindow = 10.0D, LPExp = 8.0D, LPThresh = 0.65D;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFilterSettings(boolean BPFlagIn, int BPLowIn, int BPEdgeIn, int BPHighIn, boolean LPFlag, double LPWindow, double LPExp, double LPThresh, boolean IntFlagIn, boolean DetrendFlagIn, String noOfWellsString, String noOfRecsString, String toolstringNames) {
/* 21 */     this.BPLow = BPLowIn;
/* 22 */     this.BPHigh = BPHighIn;
/* 23 */     this.BPEdge = BPEdgeIn;
/* 24 */     this.BPFlag = BPFlagIn;
/*    */     
/* 26 */     this.LPFlag = LPFlag;
/* 27 */     this.LPExp = LPExp;
/* 28 */     this.LPThresh = LPThresh;
/* 29 */     this.LPWindow = LPWindow;
/*    */ 
/*    */     
/* 32 */     this.IntFlag = IntFlagIn;
/* 33 */     this.DetrendFlag = DetrendFlagIn;
/*    */     
/* 35 */     this.noOfWellsString = noOfWellsString;
/* 36 */     this.noOfRecsString = noOfRecsString;
/* 37 */     this.toolstringNames = toolstringNames;
/*    */   }
/*    */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/dsp/FilterSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */