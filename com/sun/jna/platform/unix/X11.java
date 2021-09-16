/*      */ package com.sun.jna.platform.unix;
/*      */ 
/*      */ import com.sun.jna.Callback;
/*      */ import com.sun.jna.FromNativeContext;
/*      */ import com.sun.jna.Library;
/*      */ import com.sun.jna.Native;
/*      */ import com.sun.jna.NativeLong;
/*      */ import com.sun.jna.Pointer;
/*      */ import com.sun.jna.PointerType;
/*      */ import com.sun.jna.Structure;
/*      */ import com.sun.jna.Union;
/*      */ import com.sun.jna.ptr.ByReference;
/*      */ import com.sun.jna.ptr.IntByReference;
/*      */ import com.sun.jna.ptr.NativeLongByReference;
/*      */ import com.sun.jna.ptr.PointerByReference;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface X11
/*      */   extends Library
/*      */ {
/*      */   public static class VisualID
/*      */     extends NativeLong
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*   48 */     public static final VisualID None = null;
/*   49 */     public VisualID() { this(0L); } public VisualID(long value) {
/*   50 */       super(value, true);
/*      */     } protected boolean isNone(Object o) {
/*   52 */       return (o == null || (o instanceof Number && ((Number)o)
/*      */         
/*   54 */         .longValue() == 0L));
/*      */     }
/*      */     
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*   58 */       if (isNone(nativeValue))
/*   59 */         return None; 
/*   60 */       return new VisualID(((Number)nativeValue).longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XID extends NativeLong {
/*      */     private static final long serialVersionUID = 1L;
/*   66 */     public static final XID None = null;
/*   67 */     public XID() { this(0L); } public XID(long id) {
/*   68 */       super(id, true);
/*      */     } protected boolean isNone(Object o) {
/*   70 */       return (o == null || (o instanceof Number && ((Number)o)
/*      */         
/*   72 */         .longValue() == 0L));
/*      */     }
/*      */     
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*   76 */       if (isNone(nativeValue))
/*   77 */         return None; 
/*   78 */       return new XID(((Number)nativeValue).longValue());
/*      */     }
/*      */     
/*      */     public String toString() {
/*   82 */       return "0x" + Long.toHexString(longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Atom extends XID { private static final long serialVersionUID = 1L;
/*   87 */     public static final Atom None = null; public Atom() {}
/*      */     public Atom(long id) {
/*   89 */       super(id);
/*      */     }
/*      */     
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*   93 */       long value = ((Number)nativeValue).longValue();
/*   94 */       if (value <= 2147483647L) {
/*   95 */         switch ((int)value) { case 0:
/*   96 */             return None;
/*   97 */           case 1: return X11.XA_PRIMARY;
/*   98 */           case 2: return X11.XA_SECONDARY;
/*   99 */           case 3: return X11.XA_ARC;
/*  100 */           case 4: return X11.XA_ATOM;
/*  101 */           case 5: return X11.XA_BITMAP;
/*  102 */           case 6: return X11.XA_CARDINAL;
/*  103 */           case 7: return X11.XA_COLORMAP;
/*  104 */           case 8: return X11.XA_CURSOR;
/*  105 */           case 9: return X11.XA_CUT_BUFFER0;
/*  106 */           case 10: return X11.XA_CUT_BUFFER1;
/*  107 */           case 11: return X11.XA_CUT_BUFFER2;
/*  108 */           case 12: return X11.XA_CUT_BUFFER3;
/*  109 */           case 13: return X11.XA_CUT_BUFFER4;
/*  110 */           case 14: return X11.XA_CUT_BUFFER5;
/*  111 */           case 15: return X11.XA_CUT_BUFFER6;
/*  112 */           case 16: return X11.XA_CUT_BUFFER7;
/*  113 */           case 17: return X11.XA_DRAWABLE;
/*  114 */           case 18: return X11.XA_FONT;
/*  115 */           case 19: return X11.XA_INTEGER;
/*  116 */           case 20: return X11.XA_PIXMAP;
/*  117 */           case 21: return X11.XA_POINT;
/*  118 */           case 22: return X11.XA_RECTANGLE;
/*  119 */           case 23: return X11.XA_RESOURCE_MANAGER;
/*  120 */           case 24: return X11.XA_RGB_COLOR_MAP;
/*  121 */           case 25: return X11.XA_RGB_BEST_MAP;
/*  122 */           case 26: return X11.XA_RGB_BLUE_MAP;
/*  123 */           case 27: return X11.XA_RGB_DEFAULT_MAP;
/*  124 */           case 28: return X11.XA_RGB_GRAY_MAP;
/*  125 */           case 29: return X11.XA_RGB_GREEN_MAP;
/*  126 */           case 30: return X11.XA_RGB_RED_MAP;
/*  127 */           case 31: return X11.XA_STRING;
/*  128 */           case 32: return X11.XA_VISUALID;
/*  129 */           case 33: return X11.XA_WINDOW;
/*  130 */           case 34: return X11.XA_WM_COMMAND;
/*  131 */           case 35: return X11.XA_WM_HINTS;
/*  132 */           case 36: return X11.XA_WM_CLIENT_MACHINE;
/*  133 */           case 37: return X11.XA_WM_ICON_NAME;
/*  134 */           case 38: return X11.XA_WM_ICON_SIZE;
/*  135 */           case 39: return X11.XA_WM_NAME;
/*  136 */           case 40: return X11.XA_WM_NORMAL_HINTS;
/*  137 */           case 41: return X11.XA_WM_SIZE_HINTS;
/*  138 */           case 42: return X11.XA_WM_ZOOM_HINTS;
/*  139 */           case 43: return X11.XA_MIN_SPACE;
/*  140 */           case 44: return X11.XA_NORM_SPACE;
/*  141 */           case 45: return X11.XA_MAX_SPACE;
/*  142 */           case 46: return X11.XA_END_SPACE;
/*  143 */           case 47: return X11.XA_SUPERSCRIPT_X;
/*  144 */           case 48: return X11.XA_SUPERSCRIPT_Y;
/*  145 */           case 49: return X11.XA_SUBSCRIPT_X;
/*  146 */           case 50: return X11.XA_SUBSCRIPT_Y;
/*  147 */           case 51: return X11.XA_UNDERLINE_POSITION;
/*  148 */           case 52: return X11.XA_UNDERLINE_THICKNESS;
/*  149 */           case 53: return X11.XA_STRIKEOUT_ASCENT;
/*  150 */           case 54: return X11.XA_STRIKEOUT_DESCENT;
/*  151 */           case 55: return X11.XA_ITALIC_ANGLE;
/*  152 */           case 56: return X11.XA_X_HEIGHT;
/*  153 */           case 57: return X11.XA_QUAD_WIDTH;
/*  154 */           case 58: return X11.XA_WEIGHT;
/*  155 */           case 59: return X11.XA_POINT_SIZE;
/*  156 */           case 60: return X11.XA_RESOLUTION;
/*  157 */           case 61: return X11.XA_COPYRIGHT;
/*  158 */           case 62: return X11.XA_NOTICE;
/*  159 */           case 63: return X11.XA_FONT_NAME;
/*  160 */           case 64: return X11.XA_FAMILY_NAME;
/*  161 */           case 65: return X11.XA_FULL_NAME;
/*  162 */           case 66: return X11.XA_CAP_HEIGHT;
/*  163 */           case 67: return X11.XA_WM_CLASS;
/*  164 */           case 68: return X11.XA_WM_TRANSIENT_FOR; }
/*      */ 
/*      */       
/*      */       }
/*  168 */       return new Atom(value);
/*      */     } }
/*      */   
/*      */   public static class AtomByReference extends ByReference { public AtomByReference() {
/*  172 */       super(X11.XID.SIZE);
/*      */     } public X11.Atom getValue() {
/*  174 */       NativeLong value = getPointer().getNativeLong(0L);
/*  175 */       return (X11.Atom)(new X11.Atom()).fromNative(value, null);
/*      */     } }
/*      */   
/*      */   public static class Colormap extends XID {
/*      */     private static final long serialVersionUID = 1L;
/*  180 */     public static final Colormap None = null; public Colormap() {}
/*      */     public Colormap(long id) {
/*  182 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  185 */       if (isNone(nativeValue))
/*  186 */         return None; 
/*  187 */       return new Colormap(((Number)nativeValue).longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Font extends XID { private static final long serialVersionUID = 1L;
/*  192 */     public static final Font None = null; public Font() {}
/*      */     public Font(long id) {
/*  194 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  197 */       if (isNone(nativeValue))
/*  198 */         return None; 
/*  199 */       return new Font(((Number)nativeValue).longValue());
/*      */     } }
/*      */   
/*      */   public static class Cursor extends XID {
/*      */     private static final long serialVersionUID = 1L;
/*  204 */     public static final Cursor None = null; public Cursor() {}
/*      */     public Cursor(long id) {
/*  206 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  209 */       if (isNone(nativeValue))
/*  210 */         return None; 
/*  211 */       return new Cursor(((Number)nativeValue).longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class KeySym extends XID { private static final long serialVersionUID = 1L;
/*  216 */     public static final KeySym None = null; public KeySym() {}
/*      */     public KeySym(long id) {
/*  218 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  221 */       if (isNone(nativeValue))
/*  222 */         return None; 
/*  223 */       return new KeySym(((Number)nativeValue).longValue());
/*      */     } }
/*      */   
/*      */   public static class Drawable extends XID {
/*      */     private static final long serialVersionUID = 1L;
/*  228 */     public static final Drawable None = null; public Drawable() {}
/*      */     public Drawable(long id) {
/*  230 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  233 */       if (isNone(nativeValue))
/*  234 */         return None; 
/*  235 */       return new Drawable(((Number)nativeValue).longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Window extends Drawable { private static final long serialVersionUID = 1L;
/*  240 */     public static final Window None = null; public Window() {}
/*      */     public Window(long id) {
/*  242 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  245 */       if (isNone(nativeValue))
/*  246 */         return None; 
/*  247 */       return new Window(((Number)nativeValue).longValue());
/*      */     } }
/*      */   
/*      */   public static class WindowByReference extends ByReference { public WindowByReference() {
/*  251 */       super(X11.XID.SIZE);
/*      */     } public X11.Window getValue() {
/*  253 */       NativeLong value = getPointer().getNativeLong(0L);
/*  254 */       return (value.longValue() == 0L) ? X11.Window.None : new X11.Window(value.longValue());
/*      */     } }
/*      */   
/*      */   public static class Pixmap extends Drawable {
/*      */     private static final long serialVersionUID = 1L;
/*  259 */     public static final Pixmap None = null; public Pixmap() {}
/*      */     public Pixmap(long id) {
/*  261 */       super(id);
/*      */     }
/*      */     public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  264 */       if (isNone(nativeValue))
/*  265 */         return None; 
/*  266 */       return new Pixmap(((Number)nativeValue).longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Display extends PointerType {}
/*      */   
/*      */   public static class Visual extends PointerType {
/*      */     public X11.VisualID getVisualID() {
/*  274 */       if (getPointer() != null)
/*  275 */         return new X11.VisualID(getPointer().getNativeLong(Native.POINTER_SIZE).longValue()); 
/*  276 */       throw new IllegalStateException("Attempting to retrieve VisualID from a null Visual");
/*      */     }
/*      */     
/*      */     public String toString() {
/*  280 */       return "Visual: VisualID=0x" + Long.toHexString(getVisualID().longValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static class Screen
/*      */     extends PointerType {}
/*      */   
/*      */   public static class GC extends PointerType {}
/*      */   
/*      */   public static class XImage extends PointerType {}
/*      */   
/*      */   public static interface Xext extends Library {
/*  292 */     public static final Xext INSTANCE = (Xext)Native.loadLibrary("Xext", Xext.class);
/*      */     
/*      */     public static final int ShapeBounding = 0;
/*      */     
/*      */     public static final int ShapeClip = 1;
/*      */     public static final int ShapeInput = 2;
/*      */     public static final int ShapeSet = 0;
/*      */     public static final int ShapeUnion = 1;
/*      */     public static final int ShapeIntersect = 2;
/*      */     public static final int ShapeSubtract = 3;
/*      */     public static final int ShapeInvert = 4;
/*      */     
/*      */     void XShapeCombineMask(X11.Display param1Display, X11.Window param1Window, int param1Int1, int param1Int2, int param1Int3, X11.Pixmap param1Pixmap, int param1Int4);
/*      */   }
/*      */   
/*      */   public static interface Xrender
/*      */     extends Library
/*      */   {
/*  310 */     public static final Xrender INSTANCE = (Xrender)Native.loadLibrary("Xrender", Xrender.class); public static final int PictTypeIndexed = 0; public static final int PictTypeDirect = 1;
/*      */     XRenderPictFormat XRenderFindVisualFormat(X11.Display param1Display, X11.Visual param1Visual);
/*      */     
/*  313 */     public static class XRenderDirectFormat extends Structure { public static final List<String> FIELDS = createFieldsOrder(new String[] { "red", "redMask", "green", "greenMask", "blue", "blueMask", "alpha", "alphaMask" }); public short red; public short redMask; public short green;
/*      */       public short greenMask;
/*      */       public short blue;
/*      */       public short blueMask;
/*      */       public short alpha;
/*      */       public short alphaMask;
/*      */       
/*      */       protected List<String> getFieldOrder() {
/*  321 */         return FIELDS;
/*      */       } }
/*      */ 
/*      */     
/*      */     public static class PictFormat extends X11.XID {
/*      */       private static final long serialVersionUID = 1L;
/*  327 */       public static final PictFormat None = null;
/*  328 */       public PictFormat(long value) { super(value); } public PictFormat() {
/*  329 */         this(0L);
/*      */       }
/*      */       public Object fromNative(Object nativeValue, FromNativeContext context) {
/*  332 */         if (isNone(nativeValue))
/*  333 */           return None; 
/*  334 */         return new PictFormat(((Number)nativeValue).longValue());
/*      */       } }
/*      */     
/*      */     public static class XRenderPictFormat extends Structure {
/*  338 */       public static final List<String> FIELDS = createFieldsOrder(new String[] { "id", "type", "depth", "direct", "colormap" });
/*      */       
/*      */       public X11.Xrender.PictFormat id;
/*      */       public int type;
/*      */       public int depth;
/*      */       public X11.Xrender.XRenderDirectFormat direct;
/*      */       public X11.Colormap colormap;
/*      */       
/*      */       protected List<String> getFieldOrder() {
/*  347 */         return FIELDS;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface Xevie
/*      */     extends Library
/*      */   {
/*  358 */     public static final Xevie INSTANCE = (Xevie)Native.loadLibrary("Xevie", Xevie.class);
/*      */     public static final int XEVIE_UNMODIFIED = 0;
/*      */     public static final int XEVIE_MODIFIED = 1;
/*      */     
/*      */     boolean XevieQueryVersion(X11.Display param1Display, IntByReference param1IntByReference1, IntByReference param1IntByReference2);
/*      */     
/*      */     int XevieStart(X11.Display param1Display);
/*      */     
/*      */     int XevieEnd(X11.Display param1Display);
/*      */     
/*      */     int XevieSendEvent(X11.Display param1Display, X11.XEvent param1XEvent, int param1Int);
/*      */     
/*      */     int XevieSelectInput(X11.Display param1Display, NativeLong param1NativeLong);
/*      */   }
/*      */   
/*      */   public static interface XTest
/*      */     extends Library {
/*  375 */     public static final XTest INSTANCE = (XTest)Native.loadLibrary("Xtst", XTest.class);
/*      */     boolean XTestQueryExtension(X11.Display param1Display, IntByReference param1IntByReference1, IntByReference param1IntByReference2, IntByReference param1IntByReference3, IntByReference param1IntByReference4);
/*      */     boolean XTestCompareCursorWithWindow(X11.Display param1Display, X11.Window param1Window, X11.Cursor param1Cursor);
/*      */     boolean XTestCompareCurrentCursorWithWindow(X11.Display param1Display, X11.Window param1Window);
/*      */     int XTestFakeKeyEvent(X11.Display param1Display, int param1Int, boolean param1Boolean, NativeLong param1NativeLong);
/*      */     int XTestFakeButtonEvent(X11.Display param1Display, int param1Int, boolean param1Boolean, NativeLong param1NativeLong);
/*      */     int XTestFakeMotionEvent(X11.Display param1Display, int param1Int1, int param1Int2, int param1Int3, NativeLong param1NativeLong);
/*      */     int XTestFakeRelativeMotionEvent(X11.Display param1Display, int param1Int1, int param1Int2, NativeLong param1NativeLong);
/*      */     int XTestFakeDeviceKeyEvent(X11.Display param1Display, X11.XDeviceByReference param1XDeviceByReference, int param1Int1, boolean param1Boolean, IntByReference param1IntByReference, int param1Int2, NativeLong param1NativeLong);
/*      */     int XTestFakeDeviceButtonEvent(X11.Display param1Display, X11.XDeviceByReference param1XDeviceByReference, int param1Int1, boolean param1Boolean, IntByReference param1IntByReference, int param1Int2, NativeLong param1NativeLong);
/*      */     int XTestFakeProximityEvent(X11.Display param1Display, X11.XDeviceByReference param1XDeviceByReference, boolean param1Boolean, IntByReference param1IntByReference, int param1Int, NativeLong param1NativeLong);
/*      */     
/*      */     int XTestFakeDeviceMotionEvent(X11.Display param1Display, X11.XDeviceByReference param1XDeviceByReference, boolean param1Boolean, int param1Int1, IntByReference param1IntByReference, int param1Int2, NativeLong param1NativeLong);
/*      */     
/*      */     int XTestGrabControl(X11.Display param1Display, boolean param1Boolean);
/*      */     
/*      */     void XTestSetVisualIDOfVisual(X11.Visual param1Visual, X11.VisualID param1VisualID);
/*      */     
/*      */     int XTestDiscard(X11.Display param1Display); }
/*      */   
/*  395 */   public static class XInputClassInfoByReference extends Structure implements Structure.ByReference { public static final List<String> FIELDS = createFieldsOrder(new String[] { "input_class", "event_type_base" });
/*      */     
/*      */     public byte input_class;
/*      */     public byte event_type_base;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  401 */       return FIELDS;
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class XDeviceByReference extends Structure implements Structure.ByReference {
/*  406 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "device_id", "num_classes", "classes" });
/*      */     
/*      */     public X11.XID device_id;
/*      */     public int num_classes;
/*      */     public X11.XInputClassInfoByReference classes;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  413 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*  417 */   public static final X11 INSTANCE = (X11)Native.loadLibrary("X11", X11.class); public static final int XK_0 = 48; public static final int XK_9 = 57; public static final int XK_A = 65; public static final int XK_Z = 90; public static final int XK_a = 97; public static final int XK_z = 122; public static final int XK_Shift_L = 65505; public static final int XK_Shift_R = 65505; public static final int XK_Control_L = 65507; public static final int XK_Control_R = 65508; public static final int XK_CapsLock = 65509; public static final int XK_ShiftLock = 65510;
/*      */   public static final int XK_Meta_L = 65511;
/*      */   public static final int XK_Meta_R = 65512;
/*      */   public static final int XK_Alt_L = 65513;
/*      */   public static final int XK_Alt_R = 65514;
/*      */   public static final int VisualNoMask = 0;
/*      */   public static final int VisualIDMask = 1;
/*      */   public static final int VisualScreenMask = 2;
/*      */   public static final int VisualDepthMask = 4;
/*      */   public static final int VisualClassMask = 8;
/*      */   public static final int VisualRedMaskMask = 16;
/*      */   public static final int VisualGreenMaskMask = 32;
/*      */   public static final int VisualBlueMaskMask = 64;
/*      */   public static final int VisualColormapSizeMask = 128;
/*      */   public static final int VisualBitsPerRGBMask = 256;
/*      */   public static final int VisualAllMask = 511;
/*      */   
/*  434 */   public static class XWMHints extends Structure { public static final List<String> FIELDS = createFieldsOrder(new String[] { "flags", "input", "initial_state", "icon_pixmap", "icon_window", "icon_x", "icon_y", "icon_mask", "window_group" });
/*      */     
/*      */     public NativeLong flags;
/*      */     public boolean input;
/*      */     public int initial_state;
/*      */     public X11.Pixmap icon_pixmap;
/*      */     public X11.Window icon_window;
/*      */     public int icon_x;
/*      */     public int icon_y;
/*      */     public X11.Pixmap icon_mask;
/*      */     public X11.XID window_group;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  447 */       return FIELDS;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XTextProperty
/*      */     extends Structure
/*      */   {
/*  460 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "value", "encoding", "format", "nitems" });
/*      */     
/*      */     public String value;
/*      */     public X11.Atom encoding;
/*      */     public int format;
/*      */     public NativeLong nitems;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  468 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XSizeHints
/*      */     extends Structure
/*      */   {
/*      */     public static class Aspect
/*      */       extends Structure
/*      */     {
/*  490 */       public static final List<String> FIELDS = createFieldsOrder(new String[] { "x", "y" });
/*      */       public int x;
/*      */       public int y;
/*      */       
/*      */       protected List<String> getFieldOrder() {
/*  495 */         return FIELDS;
/*      */       }
/*      */     }
/*      */     
/*  499 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "flags", "x", "y", "width", "height", "min_width", "min_height", "max_width", "max_height", "width_inc", "height_inc", "min_aspect", "max_aspect", "base_width", "base_height", "win_gravity" });
/*      */     
/*      */     public NativeLong flags;
/*      */     
/*      */     public int x;
/*      */     
/*      */     public int y;
/*      */     
/*      */     public int width;
/*      */     public int height;
/*      */     public int min_width;
/*      */     public int min_height;
/*      */     public int max_width;
/*      */     public int max_height;
/*      */     public int width_inc;
/*      */     public int height_inc;
/*      */     public Aspect min_aspect;
/*      */     public Aspect max_aspect;
/*      */     public int base_width;
/*      */     public int base_height;
/*      */     public int win_gravity;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  522 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XWindowAttributes
/*      */     extends Structure
/*      */   {
/*  556 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "x", "y", "width", "height", "border_width", "depth", "visual", "root", "c_class", "bit_gravity", "win_gravity", "backing_store", "backing_planes", "backing_pixel", "save_under", "colormap", "map_installed", "map_state", "all_event_masks", "your_event_mask", "do_not_propagate_mask", "override_redirect", "screen" });
/*      */     
/*      */     public int x;
/*      */     
/*      */     public int y;
/*      */     
/*      */     public int width;
/*      */     
/*      */     public int height;
/*      */     
/*      */     public int border_width;
/*      */     
/*      */     public int depth;
/*      */     
/*      */     public X11.Visual visual;
/*      */     
/*      */     public X11.Window root;
/*      */     
/*      */     public int c_class;
/*      */     public int bit_gravity;
/*      */     public int win_gravity;
/*      */     public int backing_store;
/*      */     public NativeLong backing_planes;
/*      */     public NativeLong backing_pixel;
/*      */     public boolean save_under;
/*      */     public X11.Colormap colormap;
/*      */     public boolean map_installed;
/*      */     public int map_state;
/*      */     public NativeLong all_event_masks;
/*      */     public NativeLong your_event_mask;
/*      */     public NativeLong do_not_propagate_mask;
/*      */     public boolean override_redirect;
/*      */     public X11.Screen screen;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  591 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XSetWindowAttributes
/*      */     extends Structure
/*      */   {
/*  615 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "background_pixmap", "background_pixel", "border_pixmap", "border_pixel", "bit_gravity", "win_gravity", "backing_store", "backing_planes", "backing_pixel", "save_under", "event_mask", "do_not_propagate_mask", "override_redirect", "colormap", "cursor" });
/*      */     
/*      */     public X11.Pixmap background_pixmap;
/*      */     
/*      */     public NativeLong background_pixel;
/*      */     
/*      */     public X11.Pixmap border_pixmap;
/*      */     
/*      */     public NativeLong border_pixel;
/*      */     
/*      */     public int bit_gravity;
/*      */     
/*      */     public int win_gravity;
/*      */     
/*      */     public int backing_store;
/*      */     
/*      */     public NativeLong backing_planes;
/*      */     public NativeLong backing_pixel;
/*      */     public boolean save_under;
/*      */     public NativeLong event_mask;
/*      */     public NativeLong do_not_propagate_mask;
/*      */     public boolean override_redirect;
/*      */     public X11.Colormap colormap;
/*      */     public X11.Cursor cursor;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  641 */       return FIELDS;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XVisualInfo
/*      */     extends Structure
/*      */   {
/*  675 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "visual", "visualid", "screen", "depth", "c_class", "red_mask", "green_mask", "blue_mask", "colormap_size", "bits_per_rgb" });
/*      */     
/*      */     public X11.Visual visual;
/*      */     
/*      */     public X11.VisualID visualid;
/*      */     public int screen;
/*      */     public int depth;
/*      */     public int c_class;
/*      */     public NativeLong red_mask;
/*      */     public NativeLong green_mask;
/*      */     public NativeLong blue_mask;
/*      */     public int colormap_size;
/*      */     public int bits_per_rgb;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  690 */       return FIELDS;
/*      */     }
/*      */   }
/*      */   
/*  694 */   public static class XPoint extends Structure { public static final List<String> FIELDS = createFieldsOrder(new String[] { "x", "y" }); public short x;
/*      */     public short y;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  698 */       return FIELDS;
/*      */     } public XPoint() {
/*  700 */       this((short)0, (short)0);
/*      */     } public XPoint(short x, short y) {
/*  702 */       this.x = x;
/*  703 */       this.y = y;
/*      */     } }
/*      */   
/*      */   public static class XRectangle extends Structure {
/*  707 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "x", "y", "width", "height" }); public short x;
/*      */     public short y;
/*      */     public short width;
/*      */     public short height;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  713 */       return FIELDS;
/*      */     } public XRectangle() {
/*  715 */       this((short)0, (short)0, (short)0, (short)0);
/*      */     } public XRectangle(short x, short y, short width, short height) {
/*  717 */       this.x = x; this.y = y;
/*  718 */       this.width = width; this.height = height;
/*      */     }
/*      */   }
/*      */   
/*  722 */   public static final Atom XA_PRIMARY = new Atom(1L);
/*  723 */   public static final Atom XA_SECONDARY = new Atom(2L);
/*  724 */   public static final Atom XA_ARC = new Atom(3L);
/*  725 */   public static final Atom XA_ATOM = new Atom(4L);
/*  726 */   public static final Atom XA_BITMAP = new Atom(5L);
/*  727 */   public static final Atom XA_CARDINAL = new Atom(6L);
/*  728 */   public static final Atom XA_COLORMAP = new Atom(7L);
/*  729 */   public static final Atom XA_CURSOR = new Atom(8L);
/*  730 */   public static final Atom XA_CUT_BUFFER0 = new Atom(9L);
/*  731 */   public static final Atom XA_CUT_BUFFER1 = new Atom(10L);
/*  732 */   public static final Atom XA_CUT_BUFFER2 = new Atom(11L);
/*  733 */   public static final Atom XA_CUT_BUFFER3 = new Atom(12L);
/*  734 */   public static final Atom XA_CUT_BUFFER4 = new Atom(13L);
/*  735 */   public static final Atom XA_CUT_BUFFER5 = new Atom(14L);
/*  736 */   public static final Atom XA_CUT_BUFFER6 = new Atom(15L);
/*  737 */   public static final Atom XA_CUT_BUFFER7 = new Atom(16L);
/*  738 */   public static final Atom XA_DRAWABLE = new Atom(17L);
/*  739 */   public static final Atom XA_FONT = new Atom(18L);
/*  740 */   public static final Atom XA_INTEGER = new Atom(19L);
/*  741 */   public static final Atom XA_PIXMAP = new Atom(20L);
/*  742 */   public static final Atom XA_POINT = new Atom(21L);
/*  743 */   public static final Atom XA_RECTANGLE = new Atom(22L);
/*  744 */   public static final Atom XA_RESOURCE_MANAGER = new Atom(23L);
/*  745 */   public static final Atom XA_RGB_COLOR_MAP = new Atom(24L);
/*  746 */   public static final Atom XA_RGB_BEST_MAP = new Atom(25L);
/*  747 */   public static final Atom XA_RGB_BLUE_MAP = new Atom(26L);
/*  748 */   public static final Atom XA_RGB_DEFAULT_MAP = new Atom(27L);
/*  749 */   public static final Atom XA_RGB_GRAY_MAP = new Atom(28L);
/*  750 */   public static final Atom XA_RGB_GREEN_MAP = new Atom(29L);
/*  751 */   public static final Atom XA_RGB_RED_MAP = new Atom(30L);
/*  752 */   public static final Atom XA_STRING = new Atom(31L);
/*  753 */   public static final Atom XA_VISUALID = new Atom(32L);
/*  754 */   public static final Atom XA_WINDOW = new Atom(33L);
/*  755 */   public static final Atom XA_WM_COMMAND = new Atom(34L);
/*  756 */   public static final Atom XA_WM_HINTS = new Atom(35L);
/*  757 */   public static final Atom XA_WM_CLIENT_MACHINE = new Atom(36L);
/*  758 */   public static final Atom XA_WM_ICON_NAME = new Atom(37L);
/*  759 */   public static final Atom XA_WM_ICON_SIZE = new Atom(38L);
/*  760 */   public static final Atom XA_WM_NAME = new Atom(39L);
/*  761 */   public static final Atom XA_WM_NORMAL_HINTS = new Atom(40L);
/*  762 */   public static final Atom XA_WM_SIZE_HINTS = new Atom(41L);
/*  763 */   public static final Atom XA_WM_ZOOM_HINTS = new Atom(42L);
/*  764 */   public static final Atom XA_MIN_SPACE = new Atom(43L);
/*  765 */   public static final Atom XA_NORM_SPACE = new Atom(44L);
/*  766 */   public static final Atom XA_MAX_SPACE = new Atom(45L);
/*  767 */   public static final Atom XA_END_SPACE = new Atom(46L);
/*  768 */   public static final Atom XA_SUPERSCRIPT_X = new Atom(47L);
/*  769 */   public static final Atom XA_SUPERSCRIPT_Y = new Atom(48L);
/*  770 */   public static final Atom XA_SUBSCRIPT_X = new Atom(49L);
/*  771 */   public static final Atom XA_SUBSCRIPT_Y = new Atom(50L);
/*  772 */   public static final Atom XA_UNDERLINE_POSITION = new Atom(51L);
/*  773 */   public static final Atom XA_UNDERLINE_THICKNESS = new Atom(52L);
/*  774 */   public static final Atom XA_STRIKEOUT_ASCENT = new Atom(53L);
/*  775 */   public static final Atom XA_STRIKEOUT_DESCENT = new Atom(54L);
/*  776 */   public static final Atom XA_ITALIC_ANGLE = new Atom(55L);
/*  777 */   public static final Atom XA_X_HEIGHT = new Atom(56L);
/*  778 */   public static final Atom XA_QUAD_WIDTH = new Atom(57L);
/*  779 */   public static final Atom XA_WEIGHT = new Atom(58L);
/*  780 */   public static final Atom XA_POINT_SIZE = new Atom(59L);
/*  781 */   public static final Atom XA_RESOLUTION = new Atom(60L);
/*  782 */   public static final Atom XA_COPYRIGHT = new Atom(61L);
/*  783 */   public static final Atom XA_NOTICE = new Atom(62L);
/*  784 */   public static final Atom XA_FONT_NAME = new Atom(63L);
/*  785 */   public static final Atom XA_FAMILY_NAME = new Atom(64L);
/*  786 */   public static final Atom XA_FULL_NAME = new Atom(65L);
/*  787 */   public static final Atom XA_CAP_HEIGHT = new Atom(66L);
/*  788 */   public static final Atom XA_WM_CLASS = new Atom(67L);
/*  789 */   public static final Atom XA_WM_TRANSIENT_FOR = new Atom(68L); public static final int None = 0; public static final int ParentRelative = 1; public static final int CopyFromParent = 0; public static final int PointerWindow = 0; public static final int InputFocus = 1; public static final int PointerRoot = 1; public static final int AnyPropertyType = 0; public static final int AnyKey = 0; public static final int AnyButton = 0; public static final int AllTemporary = 0; public static final int CurrentTime = 0; public static final int NoSymbol = 0; public static final int NoEventMask = 0; public static final int KeyPressMask = 1; public static final int KeyReleaseMask = 2; public static final int ButtonPressMask = 4; public static final int ButtonReleaseMask = 8; public static final int EnterWindowMask = 16; public static final int LeaveWindowMask = 32; public static final int PointerMotionMask = 64; public static final int PointerMotionHintMask = 128; public static final int Button1MotionMask = 256; public static final int Button2MotionMask = 512; public static final int Button3MotionMask = 1024; public static final int Button4MotionMask = 2048; public static final int Button5MotionMask = 4096; public static final int ButtonMotionMask = 8192; public static final int KeymapStateMask = 16384; public static final int ExposureMask = 32768; public static final int VisibilityChangeMask = 65536; public static final int StructureNotifyMask = 131072; public static final int ResizeRedirectMask = 262144; public static final int SubstructureNotifyMask = 524288; public static final int SubstructureRedirectMask = 1048576; public static final int FocusChangeMask = 2097152; public static final int PropertyChangeMask = 4194304; public static final int ColormapChangeMask = 8388608; public static final int OwnerGrabButtonMask = 16777216; public static final int KeyPress = 2; public static final int KeyRelease = 3; public static final int ButtonPress = 4; public static final int ButtonRelease = 5; public static final int MotionNotify = 6; public static final int EnterNotify = 7; public static final int LeaveNotify = 8; public static final int FocusIn = 9; public static final int FocusOut = 10; public static final int KeymapNotify = 11; public static final int Expose = 12; public static final int GraphicsExpose = 13; public static final int NoExpose = 14; public static final int VisibilityNotify = 15; public static final int CreateNotify = 16; public static final int DestroyNotify = 17; public static final int UnmapNotify = 18; public static final int MapNotify = 19; public static final int MapRequest = 20; public static final int ReparentNotify = 21; public static final int ConfigureNotify = 22; public static final int ConfigureRequest = 23; public static final int GravityNotify = 24; public static final int ResizeRequest = 25; public static final int CirculateNotify = 26; public static final int CirculateRequest = 27; public static final int PropertyNotify = 28; public static final int SelectionClear = 29; public static final int SelectionRequest = 30; public static final int SelectionNotify = 31; public static final int ColormapNotify = 32; public static final int ClientMessage = 33; public static final int MappingNotify = 34; public static final int LASTEvent = 35; public static final int ShiftMask = 1; public static final int LockMask = 2; public static final int ControlMask = 4; public static final int Mod1Mask = 8; public static final int Mod2Mask = 16; public static final int Mod3Mask = 32; public static final int Mod4Mask = 64; public static final int Mod5Mask = 128; public static final int ShiftMapIndex = 0; public static final int LockMapIndex = 1; public static final int ControlMapIndex = 2; public static final int Mod1MapIndex = 3; public static final int Mod2MapIndex = 4; public static final int Mod3MapIndex = 5; public static final int Mod4MapIndex = 6; public static final int Mod5MapIndex = 7; public static final int Button1Mask = 256; public static final int Button2Mask = 512; public static final int Button3Mask = 1024; public static final int Button4Mask = 2048; public static final int Button5Mask = 4096; public static final int AnyModifier = 32768; public static final int Button1 = 1; public static final int Button2 = 2; public static final int Button3 = 3; public static final int Button4 = 4; public static final int Button5 = 5; public static final int NotifyNormal = 0; public static final int NotifyGrab = 1; public static final int NotifyUngrab = 2; public static final int NotifyWhileGrabbed = 3; public static final int NotifyHint = 1; public static final int NotifyAncestor = 0; public static final int NotifyVirtual = 1; public static final int NotifyInferior = 2; public static final int NotifyNonlinear = 3; public static final int NotifyNonlinearVirtual = 4; public static final int NotifyPointer = 5; public static final int NotifyPointerRoot = 6; public static final int NotifyDetailNone = 7; public static final int VisibilityUnobscured = 0; public static final int VisibilityPartiallyObscured = 1; public static final int VisibilityFullyObscured = 2; public static final int PlaceOnTop = 0; public static final int PlaceOnBottom = 1; public static final int FamilyInternet = 0; public static final int FamilyDECnet = 1; public static final int FamilyChaos = 2; public static final int FamilyInternet6 = 6; public static final int FamilyServerInterpreted = 5; public static final int PropertyNewValue = 0; public static final int PropertyDelete = 1; public static final int ColormapUninstalled = 0; public static final int ColormapInstalled = 1; public static final int GrabModeSync = 0; public static final int GrabModeAsync = 1; public static final int GrabSuccess = 0; public static final int AlreadyGrabbed = 1; public static final int GrabInvalidTime = 2; public static final int GrabNotViewable = 3; public static final int GrabFrozen = 4; public static final int AsyncPointer = 0; public static final int SyncPointer = 1; public static final int ReplayPointer = 2; public static final int AsyncKeyboard = 3; public static final int SyncKeyboard = 4; public static final int ReplayKeyboard = 5; public static final int AsyncBoth = 6; public static final int SyncBoth = 7; public static final int RevertToNone = 0; public static final int RevertToPointerRoot = 1; public static final int RevertToParent = 2; public static final int Success = 0; public static final int BadRequest = 1; public static final int BadValue = 2; public static final int BadWindow = 3; public static final int BadPixmap = 4; public static final int BadAtom = 5; public static final int BadCursor = 6; public static final int BadFont = 7; public static final int BadMatch = 8; public static final int BadDrawable = 9; public static final int BadAccess = 10; public static final int BadAlloc = 11; public static final int BadColor = 12; public static final int BadGC = 13; public static final int BadIDChoice = 14; public static final int BadName = 15; public static final int BadLength = 16; public static final int BadImplementation = 17; public static final int FirstExtensionError = 128; public static final int LastExtensionError = 255; public static final int InputOutput = 1; public static final int InputOnly = 2; public static final int CWBackPixmap = 1; public static final int CWBackPixel = 2; public static final int CWBorderPixmap = 4; public static final int CWBorderPixel = 8; public static final int CWBitGravity = 16; public static final int CWWinGravity = 32; public static final int CWBackingStore = 64; public static final int CWBackingPlanes = 128; public static final int CWBackingPixel = 256; public static final int CWOverrideRedirect = 512; public static final int CWSaveUnder = 1024; public static final int CWEventMask = 2048; public static final int CWDontPropagate = 4096; public static final int CWColormap = 8192; public static final int CWCursor = 16384; public static final int CWX = 1; public static final int CWY = 2; public static final int CWWidth = 4; public static final int CWHeight = 8; public static final int CWBorderWidth = 16; public static final int CWSibling = 32; public static final int CWStackMode = 64; public static final int ForgetGravity = 0; public static final int NorthWestGravity = 1; public static final int NorthGravity = 2; public static final int NorthEastGravity = 3; public static final int WestGravity = 4; public static final int CenterGravity = 5; public static final int EastGravity = 6; public static final int SouthWestGravity = 7; public static final int SouthGravity = 8; public static final int SouthEastGravity = 9; public static final int StaticGravity = 10; public static final int UnmapGravity = 0; public static final int NotUseful = 0; public static final int WhenMapped = 1; public static final int Always = 2; public static final int IsUnmapped = 0; public static final int IsUnviewable = 1; public static final int IsViewable = 2; public static final int SetModeInsert = 0; public static final int SetModeDelete = 1; public static final int DestroyAll = 0; public static final int RetainPermanent = 1; public static final int RetainTemporary = 2; public static final int Above = 0; public static final int Below = 1; public static final int TopIf = 2; public static final int BottomIf = 3; public static final int Opposite = 4; public static final int RaiseLowest = 0; public static final int LowerHighest = 1; public static final int PropModeReplace = 0; public static final int PropModePrepend = 1; public static final int PropModeAppend = 2;
/*  790 */   public static final Atom XA_LAST_PREDEFINED = XA_WM_TRANSIENT_FOR; public static final int GXclear = 0; public static final int GXand = 1; public static final int GXandReverse = 2; public static final int GXcopy = 3; public static final int GXandInverted = 4; public static final int GXnoop = 5; public static final int GXxor = 6; public static final int GXor = 7; public static final int GXnor = 8; public static final int GXequiv = 9; public static final int GXinvert = 10; public static final int GXorReverse = 11; public static final int GXcopyInverted = 12; public static final int GXorInverted = 13; public static final int GXnand = 14; public static final int GXset = 15; public static final int LineSolid = 0; public static final int LineOnOffDash = 1; public static final int LineDoubleDash = 2; public static final int CapNotLast = 0; public static final int CapButt = 1; public static final int CapRound = 2; public static final int CapProjecting = 3; public static final int JoinMiter = 0; public static final int JoinRound = 1; public static final int JoinBevel = 2; public static final int FillSolid = 0; public static final int FillTiled = 1; public static final int FillStippled = 2; public static final int FillOpaqueStippled = 3; public static final int EvenOddRule = 0; public static final int WindingRule = 1; public static final int ClipByChildren = 0; public static final int IncludeInferiors = 1; public static final int Unsorted = 0; public static final int YSorted = 1; public static final int YXSorted = 2; public static final int YXBanded = 3; public static final int CoordModeOrigin = 0; public static final int CoordModePrevious = 1; public static final int Complex = 0; public static final int Nonconvex = 1; public static final int Convex = 2; public static final int ArcChord = 0; public static final int ArcPieSlice = 1; public static final int GCFunction = 1; public static final int GCPlaneMask = 2; public static final int GCForeground = 4; public static final int GCBackground = 8; public static final int GCLineWidth = 16; public static final int GCLineStyle = 32; public static final int GCCapStyle = 64; public static final int GCJoinStyle = 128; public static final int GCFillStyle = 256; public static final int GCFillRule = 512; public static final int GCTile = 1024; public static final int GCStipple = 2048; public static final int GCTileStipXOrigin = 4096; public static final int GCTileStipYOrigin = 8192; public static final int GCFont = 16384; public static final int GCSubwindowMode = 32768; public static final int GCGraphicsExposures = 65536; public static final int GCClipXOrigin = 131072; public static final int GCClipYOrigin = 262144; public static final int GCClipMask = 524288; public static final int GCDashOffset = 1048576; public static final int GCDashList = 2097152; public static final int GCArcMode = 4194304; public static final int GCLastBit = 22; public static final int FontLeftToRight = 0; public static final int FontRightToLeft = 1; public static final int FontChange = 255; public static final int XYBitmap = 0; public static final int XYPixmap = 1; public static final int ZPixmap = 2; public static final int AllocNone = 0; public static final int AllocAll = 1; public static final int DoRed = 1; public static final int DoGreen = 2; public static final int DoBlue = 4; public static final int CursorShape = 0; public static final int TileShape = 1; public static final int StippleShape = 2; public static final int AutoRepeatModeOff = 0; public static final int AutoRepeatModeOn = 1; public static final int AutoRepeatModeDefault = 2; public static final int LedModeOff = 0; public static final int LedModeOn = 1; public static final int KBKeyClickPercent = 1; public static final int KBBellPercent = 2; public static final int KBBellPitch = 4; public static final int KBBellDuration = 8; public static final int KBLed = 16; public static final int KBLedMode = 32; public static final int KBKey = 64; public static final int KBAutoRepeatMode = 128; public static final int MappingSuccess = 0; public static final int MappingBusy = 1; public static final int MappingFailed = 2; public static final int MappingModifier = 0; public static final int MappingKeyboard = 1; public static final int MappingPointer = 2; public static final int DontPreferBlanking = 0; public static final int PreferBlanking = 1; public static final int DefaultBlanking = 2; public static final int DisableScreenSaver = 0; public static final int DisableScreenInterval = 0; public static final int DontAllowExposures = 0; public static final int AllowExposures = 1; public static final int DefaultExposures = 2; public static final int ScreenSaverReset = 0; public static final int ScreenSaverActive = 1; public static final int HostInsert = 0; public static final int HostDelete = 1; public static final int EnableAccess = 1; public static final int DisableAccess = 0; public static final int StaticGray = 0; public static final int GrayScale = 1; public static final int StaticColor = 2; public static final int PseudoColor = 3; public static final int TrueColor = 4; public static final int DirectColor = 5; public static final int LSBFirst = 0; public static final int MSBFirst = 1; Display XOpenDisplay(String paramString); int XGetErrorText(Display paramDisplay, int paramInt1, byte[] paramArrayOfbyte, int paramInt2); int XDefaultScreen(Display paramDisplay); Screen DefaultScreenOfDisplay(Display paramDisplay);
/*      */   Visual XDefaultVisual(Display paramDisplay, int paramInt);
/*      */   Colormap XDefaultColormap(Display paramDisplay, int paramInt);
/*      */   int XDisplayWidth(Display paramDisplay, int paramInt);
/*      */   int XDisplayHeight(Display paramDisplay, int paramInt);
/*      */   Window XDefaultRootWindow(Display paramDisplay);
/*      */   Window XRootWindow(Display paramDisplay, int paramInt);
/*      */   int XAllocNamedColor(Display paramDisplay, int paramInt, String paramString, Pointer paramPointer1, Pointer paramPointer2);
/*      */   XSizeHints XAllocSizeHints();
/*      */   void XSetWMProperties(Display paramDisplay, Window paramWindow, String paramString1, String paramString2, String[] paramArrayOfString, int paramInt, XSizeHints paramXSizeHints, Pointer paramPointer1, Pointer paramPointer2);
/*      */   int XSetWMProtocols(Display paramDisplay, Window paramWindow, Atom[] paramArrayOfAtom, int paramInt);
/*      */   int XGetWMProtocols(Display paramDisplay, Window paramWindow, PointerByReference paramPointerByReference, IntByReference paramIntByReference);
/*      */   int XFree(Pointer paramPointer);
/*      */   Window XCreateSimpleWindow(Display paramDisplay, Window paramWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*      */   Pixmap XCreateBitmapFromData(Display paramDisplay, Window paramWindow, Pointer paramPointer, int paramInt1, int paramInt2);
/*      */   int XMapWindow(Display paramDisplay, Window paramWindow);
/*      */   int XMapRaised(Display paramDisplay, Window paramWindow);
/*      */   int XMapSubwindows(Display paramDisplay, Window paramWindow);
/*      */   int XFlush(Display paramDisplay);
/*      */   int XSync(Display paramDisplay, boolean paramBoolean);
/*      */   int XEventsQueued(Display paramDisplay, int paramInt);
/*      */   int XPending(Display paramDisplay);
/*      */   int XUnmapWindow(Display paramDisplay, Window paramWindow);
/*      */   int XDestroyWindow(Display paramDisplay, Window paramWindow);
/*      */   int XCloseDisplay(Display paramDisplay);
/*      */   int XClearWindow(Display paramDisplay, Window paramWindow);
/*      */   int XClearArea(Display paramDisplay, Window paramWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*      */   Pixmap XCreatePixmap(Display paramDisplay, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3);
/*      */   int XFreePixmap(Display paramDisplay, Pixmap paramPixmap);
/*      */   GC XCreateGC(Display paramDisplay, Drawable paramDrawable, NativeLong paramNativeLong, XGCValues paramXGCValues);
/*      */   int XSetFillRule(Display paramDisplay, GC paramGC, int paramInt);
/*      */   int XFreeGC(Display paramDisplay, GC paramGC);
/*      */   int XDrawPoint(Display paramDisplay, Drawable paramDrawable, GC paramGC, int paramInt1, int paramInt2);
/*      */   int XDrawPoints(Display paramDisplay, Drawable paramDrawable, GC paramGC, XPoint[] paramArrayOfXPoint, int paramInt1, int paramInt2);
/*      */   int XFillRectangle(Display paramDisplay, Drawable paramDrawable, GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*      */   int XFillRectangles(Display paramDisplay, Drawable paramDrawable, GC paramGC, XRectangle[] paramArrayOfXRectangle, int paramInt);
/*      */   int XSetForeground(Display paramDisplay, GC paramGC, NativeLong paramNativeLong);
/*      */   int XSetBackground(Display paramDisplay, GC paramGC, NativeLong paramNativeLong);
/*      */   int XFillArc(Display paramDisplay, Drawable paramDrawable, GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*      */   int XFillPolygon(Display paramDisplay, Drawable paramDrawable, GC paramGC, XPoint[] paramArrayOfXPoint, int paramInt1, int paramInt2, int paramInt3);
/*      */   int XQueryTree(Display paramDisplay, Window paramWindow, WindowByReference paramWindowByReference1, WindowByReference paramWindowByReference2, PointerByReference paramPointerByReference, IntByReference paramIntByReference);
/*      */   boolean XQueryPointer(Display paramDisplay, Window paramWindow, WindowByReference paramWindowByReference1, WindowByReference paramWindowByReference2, IntByReference paramIntByReference1, IntByReference paramIntByReference2, IntByReference paramIntByReference3, IntByReference paramIntByReference4, IntByReference paramIntByReference5);
/*      */   int XGetWindowAttributes(Display paramDisplay, Window paramWindow, XWindowAttributes paramXWindowAttributes);
/*      */   int XChangeWindowAttributes(Display paramDisplay, Window paramWindow, NativeLong paramNativeLong, XSetWindowAttributes paramXSetWindowAttributes);
/*      */   int XGetGeometry(Display paramDisplay, Drawable paramDrawable, WindowByReference paramWindowByReference, IntByReference paramIntByReference1, IntByReference paramIntByReference2, IntByReference paramIntByReference3, IntByReference paramIntByReference4, IntByReference paramIntByReference5, IntByReference paramIntByReference6);
/*      */   boolean XTranslateCoordinates(Display paramDisplay, Window paramWindow1, Window paramWindow2, int paramInt1, int paramInt2, IntByReference paramIntByReference1, IntByReference paramIntByReference2, WindowByReference paramWindowByReference);
/*      */   int XSelectInput(Display paramDisplay, Window paramWindow, NativeLong paramNativeLong);
/*      */   int XSendEvent(Display paramDisplay, Window paramWindow, int paramInt, NativeLong paramNativeLong, XEvent paramXEvent);
/*      */   int XNextEvent(Display paramDisplay, XEvent paramXEvent);
/*      */   int XPeekEvent(Display paramDisplay, XEvent paramXEvent);
/*      */   int XWindowEvent(Display paramDisplay, Window paramWindow, NativeLong paramNativeLong, XEvent paramXEvent);
/*      */   boolean XCheckWindowEvent(Display paramDisplay, Window paramWindow, NativeLong paramNativeLong, XEvent paramXEvent);
/*      */   int XMaskEvent(Display paramDisplay, NativeLong paramNativeLong, XEvent paramXEvent);
/*      */   boolean XCheckMaskEvent(Display paramDisplay, NativeLong paramNativeLong, XEvent paramXEvent);
/*      */   boolean XCheckTypedEvent(Display paramDisplay, int paramInt, XEvent paramXEvent);
/*      */   boolean XCheckTypedWindowEvent(Display paramDisplay, Window paramWindow, int paramInt, XEvent paramXEvent);
/*      */   XWMHints XGetWMHints(Display paramDisplay, Window paramWindow);
/*      */   int XGetWMName(Display paramDisplay, Window paramWindow, XTextProperty paramXTextProperty);
/*      */   XVisualInfo XGetVisualInfo(Display paramDisplay, NativeLong paramNativeLong, XVisualInfo paramXVisualInfo, IntByReference paramIntByReference);
/*      */   Colormap XCreateColormap(Display paramDisplay, Window paramWindow, Visual paramVisual, int paramInt);
/*      */   int XGetWindowProperty(Display paramDisplay, Window paramWindow, Atom paramAtom1, NativeLong paramNativeLong1, NativeLong paramNativeLong2, boolean paramBoolean, Atom paramAtom2, AtomByReference paramAtomByReference, IntByReference paramIntByReference, NativeLongByReference paramNativeLongByReference1, NativeLongByReference paramNativeLongByReference2, PointerByReference paramPointerByReference);
/*      */   int XChangeProperty(Display paramDisplay, Window paramWindow, Atom paramAtom1, Atom paramAtom2, int paramInt1, int paramInt2, Pointer paramPointer, int paramInt3);
/*      */   int XDeleteProperty(Display paramDisplay, Window paramWindow, Atom paramAtom);
/*      */   Atom XInternAtom(Display paramDisplay, String paramString, boolean paramBoolean);
/*      */   String XGetAtomName(Display paramDisplay, Atom paramAtom);
/*      */   int XCopyArea(Display paramDisplay, Drawable paramDrawable1, Drawable paramDrawable2, GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*      */   XImage XCreateImage(Display paramDisplay, Visual paramVisual, int paramInt1, int paramInt2, int paramInt3, Pointer paramPointer, int paramInt4, int paramInt5, int paramInt6, int paramInt7);
/*      */   int XPutImage(Display paramDisplay, Drawable paramDrawable, GC paramGC, XImage paramXImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*      */   int XDestroyImage(XImage paramXImage);
/*      */   XErrorHandler XSetErrorHandler(XErrorHandler paramXErrorHandler);
/*      */   String XKeysymToString(KeySym paramKeySym);
/*      */   KeySym XStringToKeysym(String paramString);
/*      */   byte XKeysymToKeycode(Display paramDisplay, KeySym paramKeySym);
/*      */   KeySym XKeycodeToKeysym(Display paramDisplay, byte paramByte, int paramInt);
/*      */   int XGrabKey(Display paramDisplay, int paramInt1, int paramInt2, Window paramWindow, int paramInt3, int paramInt4, int paramInt5);
/*      */   int XUngrabKey(Display paramDisplay, int paramInt1, int paramInt2, Window paramWindow);
/*      */   int XGrabKeyboard(Display paramDisplay, Window paramWindow, int paramInt1, int paramInt2, int paramInt3, NativeLong paramNativeLong);
/*      */   int XUngrabKeyboard(Display paramDisplay, NativeLong paramNativeLong);
/*      */   int XFetchName(Display paramDisplay, Window paramWindow, PointerByReference paramPointerByReference);
/*      */   int XChangeKeyboardMapping(Display paramDisplay, int paramInt1, int paramInt2, KeySym[] paramArrayOfKeySym, int paramInt3);
/*      */   KeySym XGetKeyboardMapping(Display paramDisplay, byte paramByte, int paramInt, IntByReference paramIntByReference);
/*      */   int XDisplayKeycodes(Display paramDisplay, IntByReference paramIntByReference1, IntByReference paramIntByReference2);
/*      */   int XSetModifierMapping(Display paramDisplay, XModifierKeymapRef paramXModifierKeymapRef);
/*      */   XModifierKeymapRef XGetModifierMapping(Display paramDisplay);
/*      */   XModifierKeymapRef XNewModifiermap(int paramInt);
/*      */   XModifierKeymapRef XInsertModifiermapEntry(XModifierKeymapRef paramXModifierKeymapRef, byte paramByte, int paramInt);
/*      */   XModifierKeymapRef XDeleteModifiermapEntry(XModifierKeymapRef paramXModifierKeymapRef, byte paramByte, int paramInt);
/*      */   int XFreeModifiermap(XModifierKeymapRef paramXModifierKeymapRef);
/*      */   int XChangeKeyboardControl(Display paramDisplay, NativeLong paramNativeLong, XKeyboardControlRef paramXKeyboardControlRef);
/*      */   int XGetKeyboardControl(Display paramDisplay, XKeyboardStateRef paramXKeyboardStateRef);
/*      */   int XAutoRepeatOn(Display paramDisplay);
/*      */   int XAutoRepeatOff(Display paramDisplay);
/*      */   int XBell(Display paramDisplay, int paramInt);
/*      */   int XQueryKeymap(Display paramDisplay, byte[] paramArrayOfbyte);
/*  884 */   public static class XGCValues extends Structure { public static final List<String> FIELDS = createFieldsOrder(new String[] { "function", "plane_mask", "foreground", "background", "line_width", "line_style", "cap_style", "join_style", "fill_style", "fill_rule", "arc_mode", "tile", "stipple", "ts_x_origin", "ts_y_origin", "font", "subwindow_mode", "graphics_exposures", "clip_x_origin", "clip_y_origin", "clip_mask", "dash_offset", "dashes" });
/*      */     
/*      */     public int function;
/*      */     
/*      */     public NativeLong plane_mask;
/*      */     
/*      */     public NativeLong foreground;
/*      */     
/*      */     public NativeLong background;
/*      */     
/*      */     public int line_width;
/*      */     
/*      */     public int line_style;
/*      */     
/*      */     public int cap_style;
/*      */     
/*      */     public int join_style;
/*      */     
/*      */     public int fill_style;
/*      */     
/*      */     public int fill_rule;
/*      */     
/*      */     public int arc_mode;
/*      */     public X11.Pixmap tile;
/*      */     public X11.Pixmap stipple;
/*      */     public int ts_x_origin;
/*      */     public int ts_y_origin;
/*      */     public X11.Font font;
/*      */     public int subwindow_mode;
/*      */     public boolean graphics_exposures;
/*      */     public int clip_x_origin;
/*      */     public int clip_y_origin;
/*      */     public X11.Pixmap clip_mask;
/*      */     public int dash_offset;
/*      */     public byte dashes;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/*  921 */       return FIELDS;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XEvent
/*      */     extends Union
/*      */   {
/*      */     public int type;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XAnyEvent xany;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XKeyEvent xkey;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XButtonEvent xbutton;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XMotionEvent xmotion;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XCrossingEvent xcrossing;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XFocusChangeEvent xfocus;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XExposeEvent xexpose;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XGraphicsExposeEvent xgraphicsexpose;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XNoExposeEvent xnoexpose;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XVisibilityEvent xvisibility;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XCreateWindowEvent xcreatewindow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XDestroyWindowEvent xdestroywindow;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XUnmapEvent xunmap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XMapEvent xmap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XMapRequestEvent xmaprequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XReparentEvent xreparent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XConfigureEvent xconfigure;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XGravityEvent xgravity;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XResizeRequestEvent xresizerequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XConfigureRequestEvent xconfigurerequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XCirculateEvent xcirculate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XCirculateRequestEvent xcirculaterequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XPropertyEvent xproperty;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XSelectionClearEvent xselectionclear;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XSelectionRequestEvent xselectionrequest;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XSelectionEvent xselection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XColormapEvent xcolormap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XClientMessageEvent xclient;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XMappingEvent xmapping;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XErrorEvent xerror;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public X11.XKeymapEvent xkeymap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1539 */     public NativeLong[] pad = new NativeLong[24];
/*      */   }
/*      */   
/*      */   public static class XAnyEvent extends Structure {
/* 1543 */     public static final List<String> FIELDS = createFieldsOrder(new String[] { "type", "serial", "send_event", "display", "window" });
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     
/*      */     protected List<String> getFieldOrder() {
/* 1551 */       return FIELDS;
/*      */     } }
/*      */   
/*      */   public static class XKeyEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Window root;
/*      */     public X11.Window subwindow;
/*      */     public NativeLong time;
/*      */     public int x;
/*      */     public int y;
/*      */     public int x_root;
/*      */     public int y_root;
/*      */     public int state;
/*      */     public int keycode;
/*      */     public int same_screen;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1571 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "root", "subwindow", "time", "x", "y", "x_root", "y_root", "state", "keycode", "same_screen" });
/*      */     } }
/*      */   
/*      */   public static class XButtonEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Window root;
/*      */     public X11.Window subwindow;
/*      */     public NativeLong time;
/*      */     public int x;
/*      */     public int y;
/*      */     public int x_root;
/*      */     public int y_root;
/*      */     public int state;
/*      */     public int button;
/*      */     public int same_screen;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1591 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "root", "subwindow", "time", "x", "y", "x_root", "y_root", "state", "button", "same_screen" });
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class XButtonPressedEvent
/*      */     extends XButtonEvent {}
/*      */   
/*      */   public static class XButtonReleasedEvent
/*      */     extends XButtonEvent {}
/*      */   
/*      */   public static class XClientMessageEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Atom message_type;
/*      */     public int format;
/*      */     public Data data;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1612 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "message_type", "format", "data" });
/*      */     }
/*      */     
/*      */     public static class Data extends Union {
/* 1616 */       public byte[] b = new byte[20];
/* 1617 */       public short[] s = new short[10];
/* 1618 */       public NativeLong[] l = new NativeLong[5]; } }
/*      */   
/*      */   public static class XMotionEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Window root;
/*      */     public X11.Window subwindow;
/*      */     public NativeLong time;
/*      */     public int x;
/*      */     public int y;
/*      */     public int x_root;
/*      */     public int y_root;
/*      */     public int state;
/*      */     public byte is_hint;
/*      */     public int same_screen;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1638 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "root", "subwindow", "time", "x", "y", "x_root", "y_root", "state", "is_hint", "same_screen" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XPointerMovedEvent
/*      */     extends XMotionEvent {}
/*      */   
/*      */   public static class XCrossingEvent
/*      */     extends Structure
/*      */   {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Window root;
/*      */     public X11.Window subwindow;
/*      */     public NativeLong time;
/*      */     public int x;
/*      */     public int y;
/*      */     public int x_root;
/*      */     public int y_root;
/*      */     public int mode;
/*      */     public int detail;
/*      */     public int same_screen;
/*      */     public int focus;
/*      */     public int state;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1667 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "root", "subwindow", "time", "x", "y", "x_root", "y_root", "mode", "detail", "same_screen", "focus", "state" });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class XEnterWindowEvent
/*      */     extends XCrossingEvent {}
/*      */ 
/*      */   
/*      */   public static class XLeaveWindowEvent
/*      */     extends XCrossingEvent {}
/*      */ 
/*      */   
/*      */   public static class XFocusChangeEvent
/*      */     extends Structure
/*      */   {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public int mode;
/*      */     public int detail;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1692 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "mode", "detail" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XFocusInEvent extends XFocusChangeEvent {}
/*      */   
/*      */   public static class XFocusOutEvent extends XFocusChangeEvent {}
/*      */   
/*      */   public static class XExposeEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public int x;
/*      */     public int y;
/*      */     public int width;
/*      */     public int height;
/*      */     public int count;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1713 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "x", "y", "width", "height", "count" });
/*      */     } }
/*      */   
/*      */   public static class XGraphicsExposeEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Drawable drawable;
/*      */     public int x;
/*      */     public int y;
/*      */     public int width;
/*      */     public int height;
/*      */     public int count;
/*      */     public int major_code;
/*      */     public int minor_code;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1730 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "drawable", "x", "y", "width", "height", "count", "major_code", "minor_code" });
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class XNoExposeEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Drawable drawable;
/*      */     public int major_code;
/*      */     public int minor_code;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1744 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "drawable", "major_code", "minor_code" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XVisibilityEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public int state;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1757 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "state" });
/*      */     } }
/*      */   
/*      */   public static class XCreateWindowEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window parent;
/*      */     public X11.Window window;
/*      */     public int x;
/*      */     public int y;
/*      */     public int width;
/*      */     public int height;
/*      */     public int border_width;
/*      */     public int override_redirect;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1774 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "parent", "window", "x", "y", "width", "height", "border_width", "override_redirect" });
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class XDestroyWindowEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1787 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window" });
/*      */     } }
/*      */   
/*      */   public static class XUnmapEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     public int from_configure;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1800 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window", "from_configure" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XMapEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     public int override_redirect;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1814 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window", "override_redirect" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XMapRequestEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window parent;
/*      */     public X11.Window window;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1827 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "parent", "window" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XReparentEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     public X11.Window parent;
/*      */     public int x;
/*      */     public int y;
/*      */     public int override_redirect;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1843 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window", "parent", "x", "y", "override_redirect" });
/*      */     } }
/*      */   
/*      */   public static class XConfigureEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     public int x;
/*      */     public int y;
/*      */     public int width;
/*      */     public int height;
/*      */     public int border_width;
/*      */     public X11.Window above;
/*      */     public int override_redirect;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1861 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window", "x", "y", "width", "height", "border_width", "above", "override_redirect" });
/*      */     } }
/*      */   
/*      */   public static class XGravityEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     public int x;
/*      */     public int y;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1875 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window", "x", "y" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XResizeRequestEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public int width;
/*      */     public int height;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1888 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "width", "height" });
/*      */     } }
/*      */   
/*      */   public static class XConfigureRequestEvent extends Structure { public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window parent;
/*      */     public X11.Window window;
/*      */     public int x;
/*      */     public int y;
/*      */     public int width;
/*      */     public int height;
/*      */     public int border_width;
/*      */     public X11.Window above;
/*      */     public int detail;
/*      */     public NativeLong value_mask;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1907 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "parent", "window", "x", "y", "width", "height", "border_width", "above", "detail", "value_mask" });
/*      */     } }
/*      */ 
/*      */   
/*      */   public static class XCirculateEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window event;
/*      */     public X11.Window window;
/*      */     public int place;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1921 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "event", "window", "place" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XCirculateRequestEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window parent;
/*      */     public X11.Window window;
/*      */     public int place;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1935 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "parent", "window", "place" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XPropertyEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Atom atom;
/*      */     public NativeLong time;
/*      */     public int state;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1950 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "atom", "time", "state" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XSelectionClearEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Atom selection;
/*      */     public NativeLong time;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1964 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "selection", "time" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XSelectionRequestEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window owner;
/*      */     public X11.Window requestor;
/*      */     public X11.Atom selection;
/*      */     public X11.Atom target;
/*      */     public X11.Atom property;
/*      */     public NativeLong time;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1981 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "owner", "requestor", "selection", "target", "property", "time" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XSelectionEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window requestor;
/*      */     public X11.Atom selection;
/*      */     public X11.Atom target;
/*      */     public X11.Atom property;
/*      */     public NativeLong time;
/*      */     
/*      */     protected List getFieldOrder() {
/* 1997 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "requestor", "selection", "target", "property", "time" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XColormapEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public X11.Colormap colormap;
/*      */     public int c_new;
/*      */     public int state;
/*      */     
/*      */     protected List getFieldOrder() {
/* 2012 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "colormap", "c_new", "state" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XMappingEvent extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/*      */     public int request;
/*      */     public int first_keycode;
/*      */     public int count;
/*      */     
/*      */     protected List getFieldOrder() {
/* 2027 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "request", "first_keycode", "count" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XErrorEvent extends Structure {
/*      */     public int type;
/*      */     public X11.Display display;
/*      */     public NativeLong serial;
/*      */     public byte error_code;
/*      */     public byte request_code;
/*      */     public byte minor_code;
/*      */     public X11.XID resourceid;
/*      */     
/*      */     protected List getFieldOrder() {
/* 2041 */       return Arrays.asList(new String[] { "type", "display", "serial", "error_code", "request_code", "minor_code", "resourceid" });
/*      */     }
/*      */   }
/*      */   
/*      */   public static class XKeymapEvent
/*      */     extends Structure {
/*      */     public int type;
/*      */     public NativeLong serial;
/*      */     public int send_event;
/*      */     public X11.Display display;
/*      */     public X11.Window window;
/* 2052 */     public byte[] key_vector = new byte[32];
/*      */     
/*      */     protected List getFieldOrder() {
/* 2055 */       return Arrays.asList(new String[] { "type", "serial", "send_event", "display", "window", "key_vector" });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static interface XErrorHandler
/*      */     extends Callback
/*      */   {
/*      */     int apply(X11.Display param1Display, X11.XErrorEvent param1XErrorEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XModifierKeymapRef
/*      */     extends Structure
/*      */     implements Structure.ByReference
/*      */   {
/*      */     public int max_keypermod;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Pointer modifiermap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected List getFieldOrder() {
/* 2365 */       return Arrays.asList(new String[] { "max_keypermod", "modifiermap" });
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class XKeyboardControlRef
/*      */     extends Structure
/*      */     implements Structure.ByReference
/*      */   {
/*      */     public int key_click_percent;
/*      */     
/*      */     public int bell_percent;
/*      */     
/*      */     public int bell_pitch;
/*      */     
/*      */     public int bell_duration;
/*      */     
/*      */     public int led;
/*      */     
/*      */     public int led_mode;
/*      */     public int key;
/*      */     public int auto_repeat_mode;
/*      */     
/*      */     protected List getFieldOrder() {
/* 2389 */       return Arrays.asList(new String[] { "key_click_percent", "bell_percent", "bell_pitch", "bell_duration", "led", "led_mode", "key", "auto_repeat_mode" });
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2394 */       return "XKeyboardControlByReference{key_click_percent=" + this.key_click_percent + ", bell_percent=" + this.bell_percent + ", bell_pitch=" + this.bell_pitch + ", bell_duration=" + this.bell_duration + ", led=" + this.led + ", led_mode=" + this.led_mode + ", key=" + this.key + ", auto_repeat_mode=" + this.auto_repeat_mode + '}';
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class XKeyboardStateRef
/*      */     extends Structure
/*      */     implements Structure.ByReference
/*      */   {
/*      */     public int key_click_percent;
/*      */ 
/*      */     
/*      */     public int bell_percent;
/*      */ 
/*      */     
/*      */     public int bell_pitch;
/*      */ 
/*      */     
/*      */     public int bell_duration;
/*      */ 
/*      */     
/*      */     public NativeLong led_mask;
/*      */ 
/*      */     
/*      */     public int global_auto_repeat;
/*      */     
/* 2421 */     public byte[] auto_repeats = new byte[32];
/*      */ 
/*      */     
/*      */     protected List getFieldOrder() {
/* 2425 */       return Arrays.asList(new String[] { "key_click_percent", "bell_percent", "bell_pitch", "bell_duration", "led_mask", "global_auto_repeat", "auto_repeats" });
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 2430 */       return "XKeyboardStateByReference{key_click_percent=" + this.key_click_percent + ", bell_percent=" + this.bell_percent + ", bell_pitch=" + this.bell_pitch + ", bell_duration=" + this.bell_duration + ", led_mask=" + this.led_mask + ", global_auto_repeat=" + this.global_auto_repeat + ", auto_repeats=" + this.auto_repeats + '}';
/*      */     }
/*      */   }
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/unix/X11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */