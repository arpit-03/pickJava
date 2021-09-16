/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.Memory;
/*     */ import com.sun.jna.Native;
/*     */ import com.sun.jna.Pointer;
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ public class GDI32Util
/*     */ {
/*  54 */   private static final DirectColorModel SCREENSHOT_COLOR_MODEL = new DirectColorModel(24, 16711680, 65280, 255);
/*  55 */   private static final int[] SCREENSHOT_BAND_MASKS = new int[] { SCREENSHOT_COLOR_MODEL
/*  56 */       .getRedMask(), SCREENSHOT_COLOR_MODEL
/*  57 */       .getGreenMask(), SCREENSHOT_COLOR_MODEL
/*  58 */       .getBlueMask() };
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
/*     */   public static BufferedImage getScreenshot(WinDef.HWND target) {
/*  74 */     WinDef.RECT rect = new WinDef.RECT();
/*  75 */     if (!User32.INSTANCE.GetWindowRect(target, rect)) {
/*  76 */       throw new Win32Exception(Native.getLastError());
/*     */     }
/*  78 */     Rectangle jRectangle = rect.toRectangle();
/*  79 */     int windowWidth = jRectangle.width;
/*  80 */     int windowHeight = jRectangle.height;
/*     */     
/*  82 */     if (windowWidth == 0 || windowHeight == 0) {
/*  83 */       throw new IllegalStateException("Window width and/or height were 0 even though GetWindowRect did not appear to fail.");
/*     */     }
/*     */     
/*  86 */     WinDef.HDC hdcTarget = User32.INSTANCE.GetDC(target);
/*  87 */     if (hdcTarget == null) {
/*  88 */       throw new Win32Exception(Native.getLastError());
/*     */     }
/*     */     
/*  91 */     Win32Exception we = null;
/*     */ 
/*     */     
/*  94 */     WinDef.HDC hdcTargetMem = null;
/*     */ 
/*     */     
/*  97 */     WinDef.HBITMAP hBitmap = null;
/*     */ 
/*     */     
/* 100 */     WinNT.HANDLE hOriginal = null;
/*     */ 
/*     */     
/* 103 */     BufferedImage image = null;
/*     */     
/*     */     try {
/* 106 */       hdcTargetMem = GDI32.INSTANCE.CreateCompatibleDC(hdcTarget);
/* 107 */       if (hdcTargetMem == null) {
/* 108 */         throw new Win32Exception(Native.getLastError());
/*     */       }
/*     */       
/* 111 */       hBitmap = GDI32.INSTANCE.CreateCompatibleBitmap(hdcTarget, windowWidth, windowHeight);
/* 112 */       if (hBitmap == null) {
/* 113 */         throw new Win32Exception(Native.getLastError());
/*     */       }
/*     */       
/* 116 */       hOriginal = GDI32.INSTANCE.SelectObject(hdcTargetMem, hBitmap);
/* 117 */       if (hOriginal == null) {
/* 118 */         throw new Win32Exception(Native.getLastError());
/*     */       }
/*     */ 
/*     */       
/* 122 */       if (!GDI32.INSTANCE.BitBlt(hdcTargetMem, 0, 0, windowWidth, windowHeight, hdcTarget, 0, 0, 13369376)) {
/* 123 */         throw new Win32Exception(Native.getLastError());
/*     */       }
/*     */       
/* 126 */       WinGDI.BITMAPINFO bmi = new WinGDI.BITMAPINFO();
/* 127 */       bmi.bmiHeader.biWidth = windowWidth;
/* 128 */       bmi.bmiHeader.biHeight = -windowHeight;
/* 129 */       bmi.bmiHeader.biPlanes = 1;
/* 130 */       bmi.bmiHeader.biBitCount = 32;
/* 131 */       bmi.bmiHeader.biCompression = 0;
/*     */       
/* 133 */       Memory buffer = new Memory((windowWidth * windowHeight * 4));
/* 134 */       int resultOfDrawing = GDI32.INSTANCE.GetDIBits(hdcTarget, hBitmap, 0, windowHeight, (Pointer)buffer, bmi, 0);
/*     */       
/* 136 */       if (resultOfDrawing == 0 || resultOfDrawing == 87) {
/* 137 */         throw new Win32Exception(Native.getLastError());
/*     */       }
/*     */       
/* 140 */       int bufferSize = windowWidth * windowHeight;
/* 141 */       DataBuffer dataBuffer = new DataBufferInt(buffer.getIntArray(0L, bufferSize), bufferSize);
/* 142 */       WritableRaster raster = Raster.createPackedRaster(dataBuffer, windowWidth, windowHeight, windowWidth, SCREENSHOT_BAND_MASKS, (Point)null);
/*     */       
/* 144 */       image = new BufferedImage(SCREENSHOT_COLOR_MODEL, raster, false, null);
/*     */     }
/* 146 */     catch (Win32Exception e) {
/* 147 */       we = e;
/*     */     } finally {
/* 149 */       if (hOriginal != null) {
/*     */         
/* 151 */         WinNT.HANDLE result = GDI32.INSTANCE.SelectObject(hdcTargetMem, hOriginal);
/*     */         
/* 153 */         if (result == null || WinGDI.HGDI_ERROR.equals(result)) {
/* 154 */           Win32Exception ex = new Win32Exception(Native.getLastError());
/* 155 */           if (we != null) {
/* 156 */             ex.addSuppressedReflected((Throwable)we);
/*     */           }
/* 158 */           we = ex;
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       if (hBitmap != null && 
/* 163 */         !GDI32.INSTANCE.DeleteObject(hBitmap)) {
/* 164 */         Win32Exception ex = new Win32Exception(Native.getLastError());
/* 165 */         if (we != null) {
/* 166 */           ex.addSuppressedReflected((Throwable)we);
/*     */         }
/* 168 */         we = ex;
/*     */       } 
/*     */ 
/*     */       
/* 172 */       if (hdcTargetMem != null)
/*     */       {
/* 174 */         if (!GDI32.INSTANCE.DeleteDC(hdcTargetMem)) {
/* 175 */           Win32Exception ex = new Win32Exception(Native.getLastError());
/* 176 */           if (we != null) {
/* 177 */             ex.addSuppressedReflected((Throwable)we);
/*     */           }
/* 179 */           we = ex;
/*     */         } 
/*     */       }
/*     */       
/* 183 */       if (hdcTarget != null && 
/* 184 */         0 == User32.INSTANCE.ReleaseDC(target, hdcTarget)) {
/* 185 */         throw new IllegalStateException("Device context did not release properly.");
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 190 */     if (we != null) {
/* 191 */       throw we;
/*     */     }
/* 193 */     return image;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/GDI32Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */