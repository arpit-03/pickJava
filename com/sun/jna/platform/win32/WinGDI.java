/*     */ package com.sun.jna.platform.win32;
/*     */ 
/*     */ import com.sun.jna.NativeLong;
/*     */ import com.sun.jna.Pointer;
/*     */ import com.sun.jna.Structure;
/*     */ import java.util.List;
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
/*     */ public interface WinGDI
/*     */ {
/*     */   public static final int RDH_RECTANGLES = 1;
/*     */   
/*     */   public static class RGNDATAHEADER
/*     */     extends Structure
/*     */   {
/*  45 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dwSize", "iType", "nCount", "nRgnSize", "rcBound" });
/*  46 */     public int dwSize = size();
/*  47 */     public int iType = 1;
/*     */     
/*     */     public int nCount;
/*     */     public int nRgnSize;
/*     */     public WinDef.RECT rcBound;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  54 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RGNDATA extends Structure {
/*  59 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "rdh", "Buffer" });
/*     */     public WinGDI.RGNDATAHEADER rdh;
/*     */     public byte[] Buffer;
/*     */     
/*     */     public RGNDATA() {
/*  64 */       this(1);
/*     */     }
/*     */     public RGNDATA(int bufferSize) {
/*  67 */       this.Buffer = new byte[bufferSize];
/*  68 */       allocateMemory();
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<String> getFieldOrder() {
/*  73 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*  77 */   public static final WinNT.HANDLE HGDI_ERROR = new WinNT.HANDLE(Pointer.createConstant(-1));
/*     */   
/*     */   public static final int RGN_AND = 1;
/*     */   
/*     */   public static final int RGN_OR = 2;
/*     */   
/*     */   public static final int RGN_XOR = 3;
/*     */   public static final int RGN_DIFF = 4;
/*     */   public static final int RGN_COPY = 5;
/*     */   public static final int ERROR = 0;
/*     */   public static final int NULLREGION = 1;
/*     */   public static final int SIMPLEREGION = 2;
/*     */   public static final int COMPLEXREGION = 3;
/*     */   public static final int ALTERNATE = 1;
/*     */   public static final int WINDING = 2;
/*     */   public static final int BI_RGB = 0;
/*     */   public static final int BI_RLE8 = 1;
/*     */   public static final int BI_RLE4 = 2;
/*     */   public static final int BI_BITFIELDS = 3;
/*     */   public static final int BI_JPEG = 4;
/*     */   public static final int BI_PNG = 5;
/*     */   public static final int PFD_TYPE_RGBA = 0;
/*     */   public static final int PFD_TYPE_COLORINDEX = 1;
/*     */   public static final int PFD_MAIN_PLANE = 0;
/*     */   public static final int PFD_OVERLAY_PLANE = 1;
/*     */   public static final int PFD_UNDERLAY_PLANE = -1;
/*     */   public static final int PFD_DOUBLEBUFFER = 1;
/*     */   public static final int PFD_STEREO = 2;
/*     */   public static final int PFD_DRAW_TO_WINDOW = 4;
/*     */   public static final int PFD_DRAW_TO_BITMAP = 8;
/*     */   public static final int PFD_SUPPORT_GDI = 16;
/*     */   public static final int PFD_SUPPORT_OPENGL = 32;
/*     */   public static final int PFD_GENERIC_FORMAT = 64;
/*     */   public static final int PFD_NEED_PALETTE = 128;
/*     */   public static final int PFD_NEED_SYSTEM_PALETTE = 256;
/*     */   public static final int PFD_SWAP_EXCHANGE = 512;
/*     */   public static final int PFD_SWAP_COPY = 1024;
/*     */   public static final int PFD_SWAP_LAYER_BUFFERS = 2048;
/*     */   public static final int PFD_GENERIC_ACCELERATED = 4096;
/*     */   public static final int PFD_SUPPORT_DIRECTDRAW = 8192;
/*     */   public static final int DIB_RGB_COLORS = 0;
/*     */   public static final int DIB_PAL_COLORS = 1;
/*     */   
/*     */   public static class BITMAPINFOHEADER
/*     */     extends Structure
/*     */   {
/* 123 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "biSize", "biWidth", "biHeight", "biPlanes", "biBitCount", "biCompression", "biSizeImage", "biXPelsPerMeter", "biYPelsPerMeter", "biClrUsed", "biClrImportant" });
/*     */ 
/*     */ 
/*     */     
/* 127 */     public int biSize = size();
/*     */     
/*     */     public int biWidth;
/*     */     public int biHeight;
/*     */     public short biPlanes;
/*     */     public short biBitCount;
/*     */     public int biCompression;
/*     */     public int biSizeImage;
/*     */     public int biXPelsPerMeter;
/*     */     public int biYPelsPerMeter;
/*     */     public int biClrUsed;
/*     */     public int biClrImportant;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 141 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RGBQUAD extends Structure {
/* 146 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "rgbBlue", "rgbGreen", "rgbRed", "rgbReserved" });
/*     */     
/*     */     public byte rgbBlue;
/*     */     public byte rgbGreen;
/*     */     public byte rgbRed;
/* 151 */     public byte rgbReserved = 0;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 154 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BITMAPINFO extends Structure {
/* 159 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "bmiHeader", "bmiColors" });
/*     */     
/* 161 */     public WinGDI.BITMAPINFOHEADER bmiHeader = new WinGDI.BITMAPINFOHEADER();
/* 162 */     public WinGDI.RGBQUAD[] bmiColors = new WinGDI.RGBQUAD[1];
/*     */     public BITMAPINFO() {
/* 164 */       this(1);
/*     */     }
/*     */     public BITMAPINFO(int size) {
/* 167 */       this.bmiColors = new WinGDI.RGBQUAD[size];
/*     */     }
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 171 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class ICONINFO extends Structure {
/* 176 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "fIcon", "xHotspot", "yHotspot", "hbmMask", "hbmColor" });
/*     */     
/*     */     public boolean fIcon;
/*     */     public int xHotspot;
/*     */     public int yHotspot;
/*     */     public WinDef.HBITMAP hbmMask;
/*     */     public WinDef.HBITMAP hbmColor;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 185 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BITMAP extends Structure {
/* 190 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "bmType", "bmWidth", "bmHeight", "bmWidthBytes", "bmPlanes", "bmBitsPixel", "bmBits" });
/*     */     
/*     */     public NativeLong bmType;
/*     */     public NativeLong bmWidth;
/*     */     public NativeLong bmHeight;
/*     */     public NativeLong bmWidthBytes;
/*     */     public short bmPlanes;
/*     */     public short bmBitsPixel;
/*     */     public Pointer bmBits;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 201 */       return FIELDS;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DIBSECTION extends Structure {
/* 206 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "dsBm", "dsBmih", "dsBitfields", "dshSection", "dsOffset" });
/*     */     
/*     */     public WinGDI.BITMAP dsBm;
/*     */     public WinGDI.BITMAPINFOHEADER dsBmih;
/* 210 */     public int[] dsBitfields = new int[3];
/*     */     
/*     */     public WinNT.HANDLE dshSection;
/*     */     public int dsOffset;
/*     */     
/*     */     protected List<String> getFieldOrder() {
/* 216 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PIXELFORMATDESCRIPTOR
/*     */     extends Structure
/*     */   {
/* 227 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "nSize", "nVersion", "dwFlags", "iPixelType", "cColorBits", "cRedBits", "cRedShift", "cGreenBits", "cGreenShift", "cBlueBits", "cBlueShift", "cAlphaBits", "cAlphaShift", "cAccumBits", "cAccumRedBits", "cAccumGreenBits", "cAccumBlueBits", "cAccumAlphaBits", "cDepthBits", "cStencilBits", "cAuxBuffers", "iLayerType", "bReserved", "dwLayerMask", "dwVisibleMask", "dwDamageMask" }); public short nSize; public short nVersion; public int dwFlags; public byte iPixelType; public byte cColorBits; public byte cRedBits; public byte cRedShift; public byte cGreenBits; public byte cGreenShift; public byte cBlueBits;
/*     */     public byte cBlueShift;
/*     */     public byte cAlphaBits;
/*     */     public byte cAlphaShift;
/*     */     
/*     */     public PIXELFORMATDESCRIPTOR() {
/* 233 */       this.nSize = (short)size();
/*     */     }
/*     */     public byte cAccumBits; public byte cAccumRedBits; public byte cAccumGreenBits; public byte cAccumBlueBits; public byte cAccumAlphaBits; public byte cDepthBits; public byte cStencilBits; public byte cAuxBuffers; public byte iLayerType; public byte bReserved; public int dwLayerMask; public int dwVisibleMask; public int dwDamageMask;
/*     */     public PIXELFORMATDESCRIPTOR(Pointer memory) {
/* 237 */       super(memory);
/* 238 */       read();
/*     */     }
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
/*     */     public static class ByReference
/*     */       extends PIXELFORMATDESCRIPTOR
/*     */       implements Structure.ByReference {}
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
/*     */     protected List<String> getFieldOrder() {
/* 351 */       return FIELDS;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/win32/WinGDI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */