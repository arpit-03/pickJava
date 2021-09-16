/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.ptr.IntByReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WinspoolUtil
/*     */ {
/*     */   public static Winspool.PRINTER_INFO_1[] getPrinterInfo1() {
/*  41 */     IntByReference pcbNeeded = new IntByReference();
/*  42 */     IntByReference pcReturned = new IntByReference();
/*  43 */     Winspool.INSTANCE.EnumPrinters(2, null, 1, null, 0, pcbNeeded, pcReturned);
/*     */     
/*  45 */     if (pcbNeeded.getValue() <= 0) {
/*  46 */       return new Winspool.PRINTER_INFO_1[0];
/*     */     }
/*     */     
/*  49 */     Winspool.PRINTER_INFO_1 pPrinterEnum = new Winspool.PRINTER_INFO_1(pcbNeeded.getValue());
/*  50 */     if (!Winspool.INSTANCE.EnumPrinters(2, null, 1, pPrinterEnum
/*  51 */         .getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/*  53 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/*  56 */     pPrinterEnum.read();
/*     */     
/*  58 */     return (Winspool.PRINTER_INFO_1[])pPrinterEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */   
/*     */   public static Winspool.PRINTER_INFO_2[] getPrinterInfo2() {
/*  62 */     return getPrinterInfo2(2);
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
/*     */   public static Winspool.PRINTER_INFO_2[] getAllPrinterInfo2() {
/*  75 */     return getPrinterInfo2(6);
/*     */   }
/*     */   
/*     */   private static Winspool.PRINTER_INFO_2[] getPrinterInfo2(int flags) {
/*  79 */     IntByReference pcbNeeded = new IntByReference();
/*  80 */     IntByReference pcReturned = new IntByReference();
/*  81 */     Winspool.INSTANCE.EnumPrinters(flags, null, 2, null, 0, pcbNeeded, pcReturned);
/*  82 */     if (pcbNeeded.getValue() <= 0) {
/*  83 */       return new Winspool.PRINTER_INFO_2[0];
/*     */     }
/*  85 */     Winspool.PRINTER_INFO_2 pPrinterEnum = new Winspool.PRINTER_INFO_2(pcbNeeded.getValue());
/*  86 */     if (!Winspool.INSTANCE.EnumPrinters(flags, null, 2, pPrinterEnum.getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/*  88 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*  90 */     pPrinterEnum.read();
/*  91 */     return (Winspool.PRINTER_INFO_2[])pPrinterEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */   
/*     */   public static Winspool.PRINTER_INFO_2 getPrinterInfo2(String printerName) {
/*  95 */     IntByReference pcbNeeded = new IntByReference();
/*  96 */     IntByReference pcReturned = new IntByReference();
/*  97 */     WinNT.HANDLEByReference pHandle = new WinNT.HANDLEByReference();
/*     */     
/*  99 */     if (!Winspool.INSTANCE.OpenPrinter(printerName, pHandle, null)) {
/* 100 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/* 102 */     Win32Exception we = null;
/* 103 */     Winspool.PRINTER_INFO_2 pinfo2 = null;
/*     */     
/*     */     try {
/* 106 */       Winspool.INSTANCE.GetPrinter(pHandle.getValue(), 2, null, 0, pcbNeeded);
/* 107 */       if (pcbNeeded.getValue() <= 0) {
/* 108 */         return new Winspool.PRINTER_INFO_2();
/*     */       }
/* 110 */       pinfo2 = new Winspool.PRINTER_INFO_2(pcbNeeded.getValue());
/* 111 */       if (!Winspool.INSTANCE.GetPrinter(pHandle.getValue(), 2, pinfo2.getPointer(), pcbNeeded.getValue(), pcReturned)) {
/* 112 */         throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */       }
/* 114 */       pinfo2.read();
/* 115 */     } catch (Win32Exception e) {
/* 116 */       we = e;
/*     */     } finally {
/* 118 */       if (!Winspool.INSTANCE.ClosePrinter(pHandle.getValue())) {
/* 119 */         Win32Exception ex = new Win32Exception(Kernel32.INSTANCE.GetLastError());
/* 120 */         if (we != null) {
/* 121 */           ex.addSuppressedReflected((Throwable)we);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     if (we != null) {
/* 127 */       throw we;
/*     */     }
/*     */     
/* 130 */     return pinfo2;
/*     */   }
/*     */   
/*     */   public static Winspool.PRINTER_INFO_4[] getPrinterInfo4() {
/* 134 */     IntByReference pcbNeeded = new IntByReference();
/* 135 */     IntByReference pcReturned = new IntByReference();
/* 136 */     Winspool.INSTANCE.EnumPrinters(2, null, 4, null, 0, pcbNeeded, pcReturned);
/*     */     
/* 138 */     if (pcbNeeded.getValue() <= 0) {
/* 139 */       return new Winspool.PRINTER_INFO_4[0];
/*     */     }
/*     */     
/* 142 */     Winspool.PRINTER_INFO_4 pPrinterEnum = new Winspool.PRINTER_INFO_4(pcbNeeded.getValue());
/* 143 */     if (!Winspool.INSTANCE.EnumPrinters(2, null, 4, pPrinterEnum
/* 144 */         .getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/* 146 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 149 */     pPrinterEnum.read();
/*     */     
/* 151 */     return (Winspool.PRINTER_INFO_4[])pPrinterEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */   
/*     */   public static Winspool.JOB_INFO_1[] getJobInfo1(WinNT.HANDLEByReference phPrinter) {
/* 155 */     IntByReference pcbNeeded = new IntByReference();
/* 156 */     IntByReference pcReturned = new IntByReference();
/* 157 */     Winspool.INSTANCE.EnumJobs(phPrinter.getValue(), 0, 255, 1, null, 0, pcbNeeded, pcReturned);
/*     */     
/* 159 */     if (pcbNeeded.getValue() <= 0) {
/* 160 */       return new Winspool.JOB_INFO_1[0];
/*     */     }
/*     */     
/* 163 */     Winspool.JOB_INFO_1 pJobEnum = new Winspool.JOB_INFO_1(pcbNeeded.getValue());
/* 164 */     if (!Winspool.INSTANCE.EnumJobs(phPrinter.getValue(), 0, 255, 1, pJobEnum
/* 165 */         .getPointer(), pcbNeeded.getValue(), pcbNeeded, pcReturned))
/*     */     {
/* 167 */       throw new Win32Exception(Kernel32.INSTANCE.GetLastError());
/*     */     }
/*     */     
/* 170 */     pJobEnum.read();
/*     */     
/* 172 */     return (Winspool.JOB_INFO_1[])pJobEnum.toArray(pcReturned.getValue());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinspoolUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */