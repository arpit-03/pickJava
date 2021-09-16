/*      */ package com.boreholeseismic.gui;
/*      */ 
/*      */ import Catalano.Math.Matrix;
/*      */ import Catalano.Math.Transforms.FourierTransform;
/*      */ import Catalano.Math.Transforms.HilbertTransform;
/*      */ import com.boreholeseismic.database.Authentication;
/*      */ import com.boreholeseismic.dsp.BandPassTrace;
/*      */ import com.boreholeseismic.dsp.CannyEdgeDetector;
/*      */ import com.boreholeseismic.dsp.Detrend;
/*      */ import com.boreholeseismic.dsp.FilterSettings;
/*      */ import com.boreholeseismic.dsp.LinearityFilter;
/*      */ import com.boreholeseismic.dsp.RemoveNaN;
/*      */ import com.boreholeseismic.dsp.RotTraces;
/*      */ import com.boreholeseismic.dsp.SWSCheck;
/*      */ import com.boreholeseismic.dsp.SortTraces;
/*      */ import com.boreholeseismic.dsp.integrateTrace;
/*      */ import com.boreholeseismic.dsp.normTrace3C;
/*      */ import com.boreholeseismic.io.ExtensionFileFilter;
/*      */ import com.boreholeseismic.io.readMAT;
/*      */ import com.jmatio.io.MatFileReader;
/*      */ import com.jmatio.types.MLArray;
/*      */ import com.jmatio.types.MLChar;
/*      */ import com.jmatio.types.MLDouble;
/*      */ import edu.mines.jtk.interp.CubicInterpolator;
/*      */ import java.awt.BasicStroke;
/*      */ import java.awt.Color;
/*      */ import java.awt.Component;
/*      */ import java.awt.Cursor;
/*      */ import java.awt.Dimension;
/*      */ import java.awt.Font;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.Image;
/*      */ import java.awt.LayoutManager;
/*      */ import java.awt.Point;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Toolkit;
/*      */ import java.awt.datatransfer.DataFlavor;
/*      */ import java.awt.datatransfer.Transferable;
/*      */ import java.awt.dnd.DragGestureEvent;
/*      */ import java.awt.dnd.DragGestureListener;
/*      */ import java.awt.dnd.DragGestureRecognizer;
/*      */ import java.awt.dnd.DragSource;
/*      */ import java.awt.dnd.DragSourceDragEvent;
/*      */ import java.awt.dnd.DragSourceDropEvent;
/*      */ import java.awt.dnd.DragSourceEvent;
/*      */ import java.awt.dnd.DragSourceListener;
/*      */ import java.awt.dnd.DropTarget;
/*      */ import java.awt.dnd.DropTargetContext;
/*      */ import java.awt.dnd.DropTargetDragEvent;
/*      */ import java.awt.dnd.DropTargetDropEvent;
/*      */ import java.awt.dnd.DropTargetEvent;
/*      */ import java.awt.dnd.DropTargetListener;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import java.awt.event.KeyEvent;
/*      */ import java.awt.event.KeyListener;
/*      */ import java.awt.event.MouseAdapter;
/*      */ import java.awt.event.MouseEvent;
/*      */ import java.awt.event.MouseWheelEvent;
/*      */ import java.awt.event.MouseWheelListener;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.MemoryImageSource;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.IOException;
/*      */ import java.net.URL;
/*      */ import java.nio.file.CopyOption;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.Paths;
/*      */ import java.nio.file.StandardCopyOption;
/*      */ import java.text.NumberFormat;
/*      */ import java.time.LocalTime;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Enumeration;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.Executors;
/*      */ import javax.swing.BorderFactory;
/*      */ import javax.swing.ButtonGroup;
/*      */ import javax.swing.Icon;
/*      */ import javax.swing.ImageIcon;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JCheckBox;
/*      */ import javax.swing.JCheckBoxMenuItem;
/*      */ import javax.swing.JFrame;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenu;
/*      */ import javax.swing.JMenuBar;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JOptionPane;
/*      */ import javax.swing.JPanel;
/*      */ import javax.swing.JPopupMenu;
/*      */ import javax.swing.JProgressBar;
/*      */ import javax.swing.JScrollPane;
/*      */ import javax.swing.JSeparator;
/*      */ import javax.swing.JTabbedPane;
/*      */ import javax.swing.JTable;
/*      */ import javax.swing.JTextField;
/*      */ import javax.swing.JToolBar;
/*      */ import javax.swing.JTree;
/*      */ import javax.swing.KeyStroke;
/*      */ import javax.swing.SwingUtilities;
/*      */ import javax.swing.Timer;
/*      */ import javax.swing.border.Border;
/*      */ import javax.swing.border.TitledBorder;
/*      */ import javax.swing.event.TreeSelectionEvent;
/*      */ import javax.swing.event.TreeSelectionListener;
/*      */ import javax.swing.table.DefaultTableCellRenderer;
/*      */ import javax.swing.table.TableColumnModel;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import javax.swing.tree.DefaultTreeCellRenderer;
/*      */ import javax.swing.tree.DefaultTreeModel;
/*      */ import javax.swing.tree.TreeNode;
/*      */ import javax.swing.tree.TreePath;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
/*      */ import org.apache.commons.math3.analysis.interpolation.NevilleInterpolator;
/*      */ import org.apache.commons.math3.analysis.interpolation.PiecewiseBicubicSplineInterpolatingFunction;
/*      */ import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
/*      */ import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
/*      */ import org.jfree.chart.ChartFactory;
/*      */ import org.jfree.chart.ChartMouseEvent;
/*      */ import org.jfree.chart.ChartMouseListener;
/*      */ import org.jfree.chart.ChartPanel;
/*      */ import org.jfree.chart.JFreeChart;
/*      */ import org.jfree.chart.annotations.XYAnnotation;
/*      */ import org.jfree.chart.annotations.XYLineAnnotation;
/*      */ import org.jfree.chart.annotations.XYTextAnnotation;
/*      */ import org.jfree.chart.axis.NumberAxis;
/*      */ import org.jfree.chart.axis.ValueAxis;
/*      */ import org.jfree.chart.entity.ChartEntity;
/*      */ import org.jfree.chart.entity.XYItemEntity;
/*      */ import org.jfree.chart.event.ChartProgressEvent;
/*      */ import org.jfree.chart.event.ChartProgressListener;
/*      */ import org.jfree.chart.plot.Plot;
/*      */ import org.jfree.chart.plot.PlotOrientation;
/*      */ import org.jfree.chart.plot.XYPlot;
/*      */ import org.jfree.chart.renderer.LookupPaintScale;
/*      */ import org.jfree.chart.renderer.PaintScale;
/*      */ import org.jfree.chart.renderer.xy.XYBlockRenderer;
/*      */ import org.jfree.chart.renderer.xy.XYItemRenderer;
/*      */ import org.jfree.chart.title.TextTitle;
/*      */ import org.jfree.data.Range;
/*      */ import org.jfree.data.xy.DefaultXYDataset;
/*      */ import org.jfree.data.xy.DefaultXYZDataset;
/*      */ import org.jfree.data.xy.XYDataset;
/*      */ import org.jfree.ui.TextAnchor;
/*      */ import us.hebi.matlab.mat.format.Mat5;
/*      */ import us.hebi.matlab.mat.format.Mat5File;
/*      */ import us.hebi.matlab.mat.types.Array;
/*      */ import us.hebi.matlab.mat.types.MatFile;
/*      */ import us.hebi.matlab.mat.types.MatlabType;
/*      */ import us.hebi.matlab.mat.types.Matrix;
/*      */ import us.hebi.matlab.mat.types.Source;
/*      */ import us.hebi.matlab.mat.types.Sources;
/*      */ import us.hebi.matlab.mat.types.Struct;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PickingWindow
/*      */   extends JPanel
/*      */   implements ActionListener, TreeSelectionListener, DragSourceListener, DragGestureListener, DropTargetListener, MouseWheelListener, ChartProgressListener, ChartMouseListener, KeyListener
/*      */ {
/*      */   boolean Windows7 = true;
/*      */   boolean showAutoPicks = true;
/*      */   boolean rotateTracesFlag = true;
/*      */   JFrame MasterFrame;
/*      */   JPanel FileNumberPanel;
/*      */   JPanel ToolBarPanel;
/*      */   JPanel FileHeaderPanel;
/*      */   JPanel TimePicksPanel;
/*      */   JPanel AutoPickViewPanel;
/*      */   JPanel AutoPickQCPanel;
/*      */   JLabel FileNumberLabel;
/*      */   JLabel StopWatch;
/*      */   JLabel PickerLabel;
/*      */   JLabel ProjectLabel;
/*      */   volatile JLabel SoftwareStatusLabel;
/*      */   JToolBar ToolBar;
/*      */   JMenuBar MenuBar;
/*      */   JMenuItem SaveFile;
/*      */   JMenuItem ZoomIn;
/*      */   JMenuItem ZoomOut;
/*      */   JMenuItem ZoomAll;
/*      */   JMenuItem PrevFile;
/*      */   JMenuItem NextFile;
/*      */   JMenuItem Undo;
/*      */   JMenuItem Renormalize;
/*      */   JMenuItem PickPDelete;
/*      */   JMenuItem PickSDelete;
/*      */   JMenuItem PickSSDelete;
/*      */   JMenuItem PickR1Delete;
/*      */   JMenuItem PickR2Delete;
/*      */   JMenuItem PickR3Delete;
/*      */   JMenuItem PickArrivalsDelete;
/*      */   JMenuItem PickReflectionsDelete;
/*      */   JMenuItem TrimFileButton;
/*      */   JMenuItem DiffLocSettings;
/*      */   JMenuItem StackRecSettings;
/*      */   JMenuItem MergeFileButton;
/*      */   JCheckBoxMenuItem showAutoPicksItem;
/*      */   JCheckBoxMenuItem TraceNormalization;
/*      */   JCheckBoxMenuItem ReceiverNormalization;
/*      */   JCheckBoxMenuItem WiggleDisplay;
/*      */   JCheckBoxMenuItem VariableDensityDisplay;
/*      */   JCheckBoxMenuItem PickPMenuItem;
/*      */   JCheckBoxMenuItem PickSMenuItem;
/*      */   JCheckBoxMenuItem PickSSMenuItem;
/*      */   JCheckBoxMenuItem PickR1MenuItem;
/*      */   JCheckBoxMenuItem PickR2MenuItem;
/*      */   JCheckBoxMenuItem PickR3MenuItem;
/*      */   JCheckBoxMenuItem HideArrivals;
/*      */   JCheckBoxMenuItem HideReflections;
/*      */   JCheckBoxMenuItem savePicksToDB;
/*      */   JCheckBoxMenuItem SaveTraces;
/*      */   JCheckBoxMenuItem RepickingButton;
/*      */   JCheckBoxMenuItem ZMs500Button;
/*      */   JCheckBoxMenuItem ZMs750Button;
/*      */   JCheckBoxMenuItem ZMs1000Button;
/*      */   JCheckBoxMenuItem Ms500Button;
/*      */   JCheckBoxMenuItem Ms750Button;
/*      */   JCheckBoxMenuItem Ms1000Button;
/*      */   JCheckBoxMenuItem ClippingMode;
/*      */   JCheckBoxMenuItem LinearInterp;
/*      */   JCheckBoxMenuItem SplineInterp;
/*      */   JCheckBoxMenuItem QCPicksMode;
/*      */   JCheckBoxMenuItem AutoPickMode;
/*      */   JCheckBoxMenuItem RotateTracesMode;
/*      */   JMenuItem Comp1;
/*      */   JMenuItem Comp2;
/*      */   JMenuItem Comp3;
/*      */   boolean Comp1Flag = true;
/*      */   boolean Comp2Flag = true;
/*      */   boolean Comp3Flag = true;
/*      */   ButtonGroup NormalizationGroup;
/*      */   ButtonGroup IconGroup;
/*      */   ButtonGroup MenuGroup;
/*      */   ButtonGroup ClipMsGroup;
/*      */   ButtonGroup InterpGroup;
/*      */   ButtonGroup CustomZoom;
/*      */   ButtonGroup DisplayGroup;
/*      */   JButton ZoomInButton;
/*      */   JButton RefreshTreeButton;
/*      */   JButton SavingButton;
/*      */   JButton ZoomOutButton;
/*      */   JButton ZoomAllButton;
/*      */   JButton BackButton;
/*      */   JButton ForwardButton;
/*      */   JButton TrimButton;
/*      */   JCheckBox c1;
/*      */   JCheckBox c2;
/*      */   JCheckBox c3;
/*      */   JCheckBox PickPButton;
/*      */   JCheckBox PickSButton;
/*      */   JCheckBox PickSSButton;
/*      */   JCheckBox PickR1Button;
/*      */   JCheckBox PickR2Button;
/*      */   JCheckBox PickR3Button;
/*      */   static JTree fileTree;
/*      */   static JScrollPane fileTreeScrollPane;
/*      */   static String InputFolderPath;
/*      */   Timer SimpleTimer;
/*      */   Timer StatusTimer;
/*  286 */   int noSeconds = 0; FilterSettings filterSettings; volatile boolean pickPFlag = false, pickSFlag = false, pickSSFlag = false, pickR1Flag = false, pickR2Flag = false, pickR3Flag = false, backupMode = true; volatile boolean HideArrivalsFlag = false; volatile boolean HideReflectionsFlag = false; volatile boolean saveCheck = false; volatile boolean TrimFileMode = false; volatile boolean RepickingMode = false; volatile boolean DiffLocFlag = false; volatile boolean QCPicksFlag = false; volatile boolean AutoPickFlag = true;
/*      */   boolean MasterClippingMode = false;
/*      */   boolean clickZoomMode = false;
/*      */   boolean renormalizeFlag = false;
/*      */   FileInputStream InputFileStream;
/*      */   Properties InputProp;
/*      */   Properties outputProperties;
/*      */   FileOutputStream outputFileStream;
/*  294 */   volatile String PickerNameStr = ""; final JProgressBar progressBar;
/*      */   RoundedPanel TracePanel;
/*      */   RoundedPanel FilePanel;
/*      */   RoundedPanel PicksPanel;
/*  298 */   int clipFileDuration = 1000; int customZoomX = 500; Cursor defaultCursor; List<Integer> startingClipPoints; List<Integer> endingClipPoints;
/*      */   double[] wellNoRec;
/*      */   double[] plotLineY;
/*      */   int lowerLimitNorm;
/*      */   int upperLimitNorm;
/*  303 */   String DISPLAY_MODE = "Wiggle"; String NORMALIZATION_MODE = "Per Receiver";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   Authentication pickingAuth;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String inputFolderSaving;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String outputFolderSaving;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String stackedReceivers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String repititionReceiver;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   String receiverCountStr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setUserProjectDetails() {
/* 1251 */     String projectText = "Project : ";
/*      */     
/* 1253 */     if (this.pickingAuth.getCurrentProject() != null) {
/* 1254 */       projectText = String.valueOf(projectText) + this.pickingAuth.getCurrentProject().getName();
/*      */     }
/*      */     
/* 1257 */     if (this.pickingAuth.getCurrentPickConfig() != null) {
/* 1258 */       projectText = String.valueOf(projectText) + " - " + this.pickingAuth.getCurrentPickConfig().getName();
/*      */     }
/*      */     
/* 1261 */     this.ProjectLabel.setText(projectText);
/* 1262 */     this.ProjectLabel.updateUI();
/*      */     
/* 1264 */     if (this.pickingAuth.getCurrentUser() != null) {
/* 1265 */       this.PickerLabel.setText("Picker: " + this.pickingAuth.getCurrentUser().getName());
/*      */     } else {
/*      */       
/* 1268 */       this.PickerLabel.setText("Picker: ");
/*      */     } 
/*      */     
/* 1271 */     if (this.pickingAuth.getCurrentDesign() != null) {
/*      */ 
/*      */       
/* 1274 */       String noOfRecsString = "[";
/* 1275 */       for (int r = 0; r < (this.pickingAuth.getCurrentDesign().getRecInfo()).length; r++) {
/* 1276 */         noOfRecsString = String.valueOf(noOfRecsString) + Integer.toString(this.pickingAuth.getCurrentDesign().getRecInfo()[r]);
/* 1277 */         if (r < (this.pickingAuth.getCurrentDesign().getRecInfo()).length - 1) {
/* 1278 */           noOfRecsString = String.valueOf(noOfRecsString) + ",";
/*      */         }
/*      */       } 
/* 1281 */       noOfRecsString = String.valueOf(noOfRecsString) + "]";
/*      */       
/* 1283 */       if ((this.pickingAuth.getCurrentDesign().getRecInfo()).length > 0) {
/* 1284 */         this.filterSettings.noOfWellsString = Integer.toString((this.pickingAuth.getCurrentDesign().getRecInfo()).length);
/* 1285 */         this.filterSettings.noOfRecsString = Arrays.toString(this.pickingAuth.getCurrentDesign().getRecInfo());
/*      */ 
/*      */ 
/*      */         
/* 1289 */         String toolStringName = "[";
/* 1290 */         System.out.println((this.pickingAuth.getCurrentDesign().getToolstringInfo()).length);
/* 1291 */         for (int i = 0; i < (this.pickingAuth.getCurrentDesign().getToolstringInfo()).length; i++) {
/* 1292 */           toolStringName = String.valueOf(toolStringName) + this.pickingAuth.getCurrentDesign().getToolstringInfo()[i];
/* 1293 */           if (i < (this.pickingAuth.getCurrentDesign().getToolstringInfo()).length - 1) {
/* 1294 */             toolStringName = String.valueOf(toolStringName) + ",";
/*      */           }
/*      */         } 
/* 1297 */         toolStringName = String.valueOf(toolStringName) + "]";
/*      */         
/* 1299 */         this.filterSettings.toolstringNames = toolStringName;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1306 */     this.PickerLabel.updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadPropertiesFile() {
/* 1312 */     String PropInputFile = "";
/* 1313 */     InputFolderPath = "";
/*      */     
/*      */     try {
/* 1316 */       this.InputProp = new Properties();
/* 1317 */       this.InputFileStream = null;
/* 1318 */       Path p1 = Paths.get(System.getProperty("user.dir"), new String[0]);
/* 1319 */       Path p2 = Paths.get("PickingHistory.properties", new String[0]);
/* 1320 */       File InputFile = new File(p1.toString(), p2.toString());
/* 1321 */       if (InputFile.exists())
/*      */       {
/* 1323 */         this.InputFileStream = new FileInputStream(InputFile.toString());
/* 1324 */         this.InputProp.load(this.InputFileStream);
/* 1325 */         this.InputFileStream.close();
/*      */         
/* 1327 */         InputFolderPath = this.InputProp.getProperty("CurrentFolder");
/* 1328 */         PropInputFile = this.InputProp.getProperty("CurrentFile");
/*      */ 
/*      */ 
/*      */         
/* 1332 */         this.filterSettings.BPFlag = Boolean.parseBoolean(this.InputProp.getProperty("BP"));
/* 1333 */         this.filterSettings.BPLow = Integer.parseInt(this.InputProp.getProperty("BP Low"));
/* 1334 */         this.filterSettings.BPHigh = Integer.parseInt(this.InputProp.getProperty("BP High"));
/* 1335 */         this.filterSettings.BPEdge = Integer.parseInt(this.InputProp.getProperty("BP Edge"));
/*      */         
/* 1337 */         this.filterSettings.LPFlag = Boolean.parseBoolean(this.InputProp.getProperty("LP"));
/* 1338 */         this.filterSettings.LPExp = Double.parseDouble(this.InputProp.getProperty("LPExp"));
/* 1339 */         this.filterSettings.LPThresh = Double.parseDouble(this.InputProp.getProperty("LPThresh"));
/* 1340 */         this.filterSettings.LPWindow = Double.parseDouble(this.InputProp.getProperty("LPWindow"));
/*      */ 
/*      */         
/* 1343 */         this.inputFolderSaving = this.InputProp.getProperty("inputFolderSaving");
/* 1344 */         this.outputFolderSaving = this.InputProp.getProperty("outputFolderSaving");
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1349 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1355 */     if (InputFolderPath != "" && PropInputFile != "") {
/*      */       
/* 1357 */       createFileTree();
/* 1358 */       selectNode(PropInputFile);
/* 1359 */       fileTree.grabFocus();
/* 1360 */       fileTree.setFocusable(true);
/* 1361 */       currentFilePath = (new File(InputFolderPath, PropInputFile)).toString();
/*      */     } 
/*      */   }
/*      */   
/*      */   public PickingWindow()
/*      */   {
/* 1367 */     this.inputFolderSaving = ""; this.outputFolderSaving = ""; this.stackedReceivers = ""; this.repititionReceiver = ""; this.receiverCountStr = "";
/*      */ 
/*      */     
/* 1370 */     this.stackReceiverCountInput = 0;
/* 1371 */     this.stackMode = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2219 */     this.deleteNode = new DefaultMutableTreeNode("00_Deleted Files");
/* 2220 */     this.skipNode = new DefaultMutableTreeNode("01_Skipped Files");
/* 2221 */     this.filterNode = new DefaultMutableTreeNode("02_Filtered Files");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2695 */     this.clipParentFilePath = "x";
/* 2696 */     this.drawCount = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2796 */     this.aDown = false; this.ctrlDown = false; this.shiftDown = false; this.returnDown = false; this.escDown = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2802 */     this.prevWaveType = " ";
/*      */     
/* 2804 */     this.lineP = Color.YELLOW;
/* 2805 */     this.lineS = new Color(127, 255, 0);
/* 2806 */     this.lineSS = Color.YELLOW;
/* 2807 */     this.lineR1 = Color.ORANGE;
/* 2808 */     this.lineR2 = Color.ORANGE;
/* 2809 */     this.lineR3 = Color.ORANGE;
/* 2810 */     this.lineInterp = Color.GREEN;
/* 2811 */     this.markerP = new Color(0, 100, 0);
/* 2812 */     this.markerS = new Color(0, 100, 0);
/* 2813 */     this.markerSS = new Color(0, 100, 0);
/* 2814 */     this.markerR1 = new Color(169, 169, 169);
/* 2815 */     this.markerR2 = new Color(169, 169, 169);
/* 2816 */     this.markerR3 = new Color(169, 169, 169);
/* 2817 */     this.markerInterp = Color.BLACK;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3771 */     this.longArray = new ArrayList<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4258 */     this.infill = 0.0D; this.ID_counter = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5904 */     this.savingPreviousFile = false;
/*      */     
/* 5906 */     this.currentSavingFile = "";
/* 5907 */     this.copySavingFile = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6978 */     this.trimReturnDown = false; this.pickingAuth = new Authentication(); Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds(); this.MasterFrame = new JFrame("Picking Tool - Borehole Seismic LLC"); this.MasterFrame.setDefaultCloseOperation(3); this.MasterFrame.setResizable(false); this.MasterFrame.setSize(r.width, r.height); this.MasterFrame.setLocation(0, 0); this.MasterFrame.setLayout((LayoutManager)null); Color FrameColor = new Color(220, 221, 220); this.MasterFrame.getContentPane().setBackground(FrameColor); int ToolBarPanelHeight = 35; this.FilePanel = new RoundedPanel(); this.FilePanel.setBounds(5, ToolBarPanelHeight + 5, (int)(0.2D * r.width), (int)(0.5D * r.height)); this.FilePanel.setBackground(Color.WHITE); TitledBorder fileExplorerTitle = new TitledBorder(new RoundedBorder(), "File Explorer"); fileExplorerTitle.setTitleJustification(2); fileExplorerTitle.setTitleFont(new Font("Avenir Book", 0, 12)); this.FilePanel.setBorder(BorderFactory.createCompoundBorder(fileExplorerTitle, BorderFactory.createEmptyBorder(10, 10, 10, 10))); this.MasterFrame.add(this.FilePanel); this.PicksPanel = new RoundedPanel(); this.PicksPanel.setBounds(5, ToolBarPanelHeight + 5 + (int)(0.5D * r.height) + 5, (int)(0.2D * r.width), r.height - ToolBarPanelHeight + 5 + (int)(0.5D * r.height) + 5 - 5 - 20 - 20); this.PicksPanel.setBackground(Color.white); this.MasterFrame.add(this.PicksPanel); this.FileHeaderPanel = new JPanel(); this.FileHeaderPanel.setBackground(Color.white); JLabel HeaderLabel = new JLabel("No info to display."); HeaderLabel.setFont(new Font("Avenir Book", 0, 11)); this.FileHeaderPanel.add(HeaderLabel); this.TimePicksPanel = new JPanel(); this.TimePicksPanel.setBackground(Color.white); JLabel TimePickLabel = new JLabel("No info to display."); TimePickLabel.setFont(new Font("Avenir Book", 0, 11)); this.TimePicksPanel.add(TimePickLabel); JTabbedPane tabbedPane = new JTabbedPane(); tabbedPane.setBackground(Color.white); tabbedPane.setFont(new Font("Avenir Book", 0, 11)); tabbedPane.insertTab("Header", (Icon)null, this.FileHeaderPanel, "Header", 0); tabbedPane.insertTab("Time Picks", (Icon)null, this.TimePicksPanel, "Time Picks", 1); tabbedPane.setPreferredSize(this.PicksPanel.getSize()); this.PicksPanel.add(tabbedPane); this.PicksPanel.setForeground(Color.white); this.TracePanel = new RoundedPanel(); this.TracePanel.setBounds((int)(0.2D * r.width) + 10, ToolBarPanelHeight + 5, (int)(0.8D * r.width) - 10 - 5, r.height - ToolBarPanelHeight + 5 - 5 - 20 - 20); this.TracePanel.setBackground(Color.white); TitledBorder traceTitle = new TitledBorder(new RoundedBorder(), "Trace View"); traceTitle.setTitleJustification(2); traceTitle.setTitleFont(new Font("Avenir Book", 0, 12)); this.TracePanel.setBorder(BorderFactory.createCompoundBorder(traceTitle, BorderFactory.createEmptyBorder(10, 10, 10, 10))); this.MasterFrame.add(this.TracePanel); this.AutoPickQCPanel = new RoundedPanel(); this.AutoPickQCPanel.setBounds((int)(0.2D * r.width) + 10, (int)(r.height * 0.95D) - 5 - 20 - 20 + 5, (int)(0.4D * r.width) - 10 - 5, (int)(r.height * 0.25D) - 5); this.AutoPickQCPanel.setBackground(Color.white); TitledBorder autoPickTitle = new TitledBorder(new RoundedBorder(), "Picks QC"); autoPickTitle.setTitleJustification(2); autoPickTitle.setTitleFont(new Font("Avenir Book", 0, 12)); this.AutoPickQCPanel.setBorder(BorderFactory.createCompoundBorder(autoPickTitle, BorderFactory.createEmptyBorder(10, 10, 10, 10))); this.AutoPickViewPanel = new RoundedPanel(); this.AutoPickViewPanel.setBounds((int)(0.6D * r.width), ToolBarPanelHeight + 5, (int)(0.4D * r.width) - 5, r.height * 1 - ToolBarPanelHeight + 5 - 5 - 20 - 20); this.AutoPickViewPanel.setBackground(Color.white); TitledBorder autoPickViewTitle = new TitledBorder(new RoundedBorder(), "Auto Picks"); autoPickViewTitle.setTitleJustification(2); autoPickViewTitle.setTitleFont(new Font("Avenir Book", 0, 12)); this.AutoPickViewPanel.setBorder(BorderFactory.createCompoundBorder(autoPickViewTitle, BorderFactory.createEmptyBorder(10, 10, 10, 10))); this.FileNumberPanel = new JPanel(); this.FileNumberPanel = new RoundedPanel(); this.FileNumberPanel.setBounds(5, r.height - 41, r.width - 10, 14); this.FileNumberPanel.setLayout((LayoutManager)null); this.FileNumberPanel.setBackground(FrameColor); this.MasterFrame.add(this.FileNumberPanel); this.progressBar = new JProgressBar(); this.progressBar.setMinimum(0); this.progressBar.setMaximum(25); this.progressBar.setValue(0); this.progressBar.setStringPainted(true); this.progressBar.setBounds(this.FileNumberPanel.getWidth() - 170, 0, 100, this.FileNumberPanel.getHeight()); this.progressBar.setVisible(false); this.progressBar.setIndeterminate(true); this.FileNumberPanel.add(this.progressBar); this.FileNumberLabel = new JLabel("Lets pick some files today!"); this.FileNumberLabel.setBounds(0, 0, this.FileNumberPanel.getWidth() / 2, this.FileNumberPanel.getHeight()); this.FileNumberLabel.setFont(new Font("Avenir Book", 0, 11)); this.FileNumberPanel.add(this.FileNumberLabel, "West"); this.FileNumberPanel.updateUI(); this.SoftwareStatusLabel = new JLabel("Ready!"); this.SoftwareStatusLabel.setBounds(this.FileNumberPanel.getWidth() - 250, 0, 250, this.FileNumberPanel.getHeight()); this.SoftwareStatusLabel.setFont(new Font("Avenir Book", 0, 11)); this.SoftwareStatusLabel.setHorizontalAlignment(11); this.FileNumberPanel.add(this.SoftwareStatusLabel, "West"); this.FileNumberPanel.updateUI(); this.ToolBarPanel = new JPanel(); this.ToolBarPanel.setPreferredSize(new Dimension(this.MasterFrame.getWidth(), ToolBarPanelHeight)); this.ToolBarPanel.setLocation(0, 0); this.ToolBarPanel.setLayout((LayoutManager)null); this.ToolBarPanel.setSize(this.MasterFrame.getWidth(), ToolBarPanelHeight); this.ToolBarPanel.setBackground(new Color(244, 244, 244)); this.MasterFrame.add(this.ToolBarPanel); this.ToolBar = new JToolBar(); this.ToolBar.setBounds(this.ToolBarPanel.getBounds()); this.ToolBar.setFloatable(false); this.ToolBar.setBackground(new Color(244, 244, 244)); URL imgURL1 = PickingRebuild.class.getResource("opendir.png"); ImageIcon OpenDirIcon = new ImageIcon(imgURL1); Image OpenDirImg = OpenDirIcon.getImage(); Image NewOpenDirImg = OpenDirImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); OpenDirIcon = new ImageIcon(NewOpenDirImg); JButton OpenDirButton = new JButton(OpenDirIcon); OpenDirButton.setToolTipText("Import Folder"); OpenDirButton.setActionCommand("OpenDirIcon"); OpenDirButton.setBorderPainted(false); OpenDirButton.addActionListener(this); URL imgURL2 = PickingRebuild.class.getResource("closedir.png"); ImageIcon CloseDirIcon = new ImageIcon(imgURL2); Image CloseDirImg = CloseDirIcon.getImage(); Image NewCloseDirImg = CloseDirImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); CloseDirIcon = new ImageIcon(NewCloseDirImg); JButton CloseDirButton = new JButton(CloseDirIcon); CloseDirButton.setToolTipText("Close Folder"); CloseDirButton.setBorderPainted(false); CloseDirButton.setActionCommand("CloseDirIcon"); CloseDirButton.addActionListener(this); URL imgURL14 = PickingRebuild.class.getResource("refresh.png"); ImageIcon RefreshTreeIcon = new ImageIcon(imgURL14); Image RefreshTreeImg = RefreshTreeIcon.getImage(); Image NewRefreshTreeImg = RefreshTreeImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); RefreshTreeIcon = new ImageIcon(NewRefreshTreeImg); this.RefreshTreeButton = new JButton(RefreshTreeIcon); this.RefreshTreeButton.setToolTipText("Refresh Directory Tree"); this.RefreshTreeButton.setBorderPainted(false); this.RefreshTreeButton.setActionCommand("RefreshTreeIcon"); this.RefreshTreeButton.addActionListener(this); URL imgURL3 = PickingRebuild.class.getResource("save.png"); ImageIcon SaveIcon = new ImageIcon(imgURL3); Image SaveImg = SaveIcon.getImage(); Image NewSaveImg = SaveImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); SaveIcon = new ImageIcon(NewSaveImg); this.SavingButton = new JButton(SaveIcon); this.SavingButton.setToolTipText("Save Picks"); this.SavingButton.setBorderPainted(false); this.SavingButton.setActionCommand("Save Icon"); this.SavingButton.addActionListener(this); URL imgURL4 = PickingRebuild.class.getResource("zoom_in.png"); ImageIcon ZoomInIcon = new ImageIcon(imgURL4); Image ZoomInImg = ZoomInIcon.getImage(); Image NewZoomInImg = ZoomInImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); ZoomInIcon = new ImageIcon(NewZoomInImg); this.ZoomInButton = new JButton(ZoomInIcon); this.ZoomInButton.setToolTipText("Zoom In"); this.ZoomInButton.setBorderPainted(false); this.ZoomInButton.addActionListener(this); this.ZoomInButton.setActionCommand("ZoomInButton"); URL imgURL5 = PickingRebuild.class.getResource("zoom_out.png"); ImageIcon ZoomOutIcon = new ImageIcon(imgURL5); Image ZoomOutImg = ZoomOutIcon.getImage(); Image NewZoomOutImg = ZoomOutImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); ZoomOutIcon = new ImageIcon(NewZoomOutImg); this.ZoomOutButton = new JButton(ZoomOutIcon); this.ZoomOutButton.setToolTipText("Zoom Out"); this.ZoomOutButton.setBorderPainted(false); this.ZoomOutButton.addActionListener(this); this.ZoomOutButton.setActionCommand("ZoomOutButton"); URL imgURL6 = PickingRebuild.class.getResource("zoom_fit.png"); ImageIcon ZoomAllIcon = new ImageIcon(imgURL6); Image ZoomAllImg = ZoomAllIcon.getImage(); Image NewZoomAllImg = ZoomAllImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); ZoomAllIcon = new ImageIcon(NewZoomAllImg); this.ZoomAllButton = new JButton(ZoomAllIcon); this.ZoomAllButton.setToolTipText("Zoom All"); this.ZoomAllButton.addActionListener(this); this.ZoomAllButton.setBorderPainted(false); this.ZoomAllButton.setActionCommand("ZoomAllButton"); JLabel PickLabel = new JLabel("Pick"); PickLabel.setFont(new Font("Avenir Book", 0, 12)); this.IconGroup = new ButtonGroup(); this.PickPButton = new JCheckBox("P"); this.PickPButton.addActionListener(this); this.IconGroup.add(this.PickPButton); this.PickSButton = new JCheckBox("S"); this.PickSButton.addActionListener(this); this.IconGroup.add(this.PickSButton); this.PickSSButton = new JCheckBox("SS"); this.PickSSButton.addActionListener(this); this.IconGroup.add(this.PickSSButton); this.PickR1Button = new JCheckBox("R1"); this.PickR1Button.addActionListener(this); this.IconGroup.add(this.PickR1Button); this.PickR2Button = new JCheckBox("R2"); this.PickR2Button.addActionListener(this); this.IconGroup.add(this.PickR2Button); this.PickR3Button = new JCheckBox("R3"); this.PickR3Button.addActionListener(this); this.IconGroup.add(this.PickR3Button); this.IconGroup.clearSelection(); JLabel CompLabel = new JLabel("Comp View"); CompLabel.setFont(new Font("Avenir Book", 0, 12)); this.c1 = new JCheckBox("1", true); this.c1.addActionListener(this); this.c2 = new JCheckBox("2", true); this.c2.addActionListener(this); this.c3 = new JCheckBox("3", true); this.c3.addActionListener(this); URL imgURL7 = PickingRebuild.class.getResource("back.png"); ImageIcon BackIcon = new ImageIcon(imgURL7); Image BackImg = BackIcon.getImage(); Image NewBackImg = BackImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); BackIcon = new ImageIcon(NewBackImg); this.BackButton = new JButton(BackIcon); this.BackButton.setToolTipText("Previous File"); this.BackButton.setBorderPainted(false); this.BackButton.setActionCommand("Previous File"); this.BackButton.addActionListener(this); URL imgURL8 = PickingRebuild.class.getResource("forward.png"); ImageIcon ForwardIcon = new ImageIcon(imgURL8); Image ForwardImg = ForwardIcon.getImage(); Image NewForwardImg = ForwardImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); ForwardIcon = new ImageIcon(NewForwardImg); this.ForwardButton = new JButton(ForwardIcon); this.ForwardButton.setToolTipText("Next File"); this.ForwardButton.setBorderPainted(false); this.ForwardButton.setActionCommand("Next File"); this.ForwardButton.addActionListener(this); URL imgURL12 = PickingRebuild.class.getResource("cut.png"); ImageIcon TrimIcon = new ImageIcon(imgURL12); Image TrimImg = TrimIcon.getImage(); Image NewTrimImg = TrimImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); TrimIcon = new ImageIcon(NewTrimImg); this.TrimButton = new JButton(TrimIcon); this.TrimButton.setToolTipText("Trim File"); this.TrimButton.setBorderPainted(false); this.TrimButton.setActionCommand("Trim File"); this.TrimButton.addActionListener(this); URL imgURL13 = PickingRebuild.class.getResource("settings.png"); ImageIcon FilterSettingsIcon = new ImageIcon(imgURL13); Image FilterSettingsImg = FilterSettingsIcon.getImage(); Image NewFilterSettingsImg = FilterSettingsImg.getScaledInstance((int)(this.ToolBar.getHeight() * 0.5D), (int)(this.ToolBar.getHeight() * 0.5D), 4); FilterSettingsIcon = new ImageIcon(NewFilterSettingsImg); JButton FilterSettingsButton = new JButton(FilterSettingsIcon); FilterSettingsButton.setToolTipText("Filter Settings"); FilterSettingsButton.setBorderPainted(false); FilterSettingsButton.setActionCommand("Filter Settings Icon"); FilterSettingsButton.addActionListener(this); this.PickerLabel = new JLabel("Picker: " + this.PickerNameStr); this.ProjectLabel = new JLabel("Project: "); this.StopWatch = new JLabel(""); this.SimpleTimer = new Timer(1000, new ActionListener() { public void actionPerformed(ActionEvent e) { DateTimeFormatter timerFormat = DateTimeFormatter.ofPattern("HH:mm:ss"); PickingWindow.this.noSeconds++; LocalTime timeOfDay = LocalTime.ofSecondOfDay(PickingWindow.this.noSeconds); PickingWindow.this.StopWatch.setText(timeOfDay.format(timerFormat)); PickingWindow.this.StopWatch.updateUI(); } }
/*      */       ); this.ToolBar.addSeparator(); this.ToolBar.add(OpenDirButton); this.ToolBar.add(CloseDirButton); this.ToolBar.add(this.RefreshTreeButton); this.ToolBar.addSeparator(); this.ToolBar.add(this.SavingButton); this.ToolBar.addSeparator(); this.ToolBar.add(this.ZoomInButton); this.ToolBar.add(this.ZoomOutButton); this.ToolBar.add(this.ZoomAllButton); this.ToolBar.addSeparator(); this.ToolBar.add(PickLabel); this.ToolBar.add(this.PickPButton); this.ToolBar.add(this.PickSButton); this.ToolBar.add(this.PickSSButton); this.ToolBar.addSeparator(); this.ToolBar.add(this.PickR1Button); this.ToolBar.add(this.PickR2Button); this.ToolBar.add(this.PickR3Button); this.ToolBar.addSeparator(); this.ToolBar.add(CompLabel); this.ToolBar.add(this.c1); this.ToolBar.add(this.c2); this.ToolBar.add(this.c3); this.ToolBar.addSeparator(); this.ToolBar.add(this.BackButton); this.ToolBar.add(this.ForwardButton); this.ToolBar.addSeparator(); this.ToolBar.add(this.TrimButton); this.ToolBar.addSeparator(); this.ToolBar.add(FilterSettingsButton); this.ToolBar.addSeparator(); this.ToolBar.add(this.PickerLabel); this.ToolBar.addSeparator(); this.ToolBar.add(this.ProjectLabel); this.ToolBar.addSeparator(); this.ToolBar.add(this.StopWatch); this.ToolBarPanel.add(this.ToolBar, "First"); this.MasterFrame.add(this.ToolBarPanel, "First"); this.MenuBar = new JMenuBar(); JMenu File = new JMenu("File"); JMenuItem ImportFolder = new JMenuItem("Import Folder"); ImportFolder.setAccelerator(KeyStroke.getKeyStroke(73, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); ImportFolder.addActionListener(this); JMenuItem CloseFolder = new JMenuItem("Close Folder"); CloseFolder.addActionListener(this); this.SaveFile = new JMenuItem("Save"); this.SaveFile.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.SaveFile.addActionListener(this); JMenuItem PickerName = new JMenuItem("Picker.."); PickerName.addActionListener(this); JMenuItem ModifyProject = new JMenuItem("Change Project"); ModifyProject.addActionListener(this); JMenuItem ModifyProjectDetails = new JMenuItem("Change Design/Stage"); ModifyProjectDetails.addActionListener(this); JMenuItem Exit = new JMenuItem("Exit"); Exit.addActionListener(this); File.add(ImportFolder); File.add(CloseFolder); File.add(new JSeparator()); File.add(this.SaveFile); File.add(new JSeparator()); File.add(PickerName); File.add(ModifyProject); File.add(ModifyProjectDetails); File.add(new JSeparator()); File.add(Exit); JMenu View = new JMenu("View"); this.ZoomIn = new JMenuItem("Zoom In"); this.ZoomIn.setAccelerator(KeyStroke.getKeyStroke(61, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.ZoomIn.addActionListener(this); this.ZoomOut = new JMenuItem("Zoom Out"); this.ZoomOut.setAccelerator(KeyStroke.getKeyStroke(45, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.ZoomOut.addActionListener(this); this.ZoomAll = new JMenuItem("Zoom All"); this.ZoomAll.setAccelerator(KeyStroke.getKeyStroke(48, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.ZoomAll.addActionListener(this); this.Comp1 = new JMenuItem("Toggle Comp 1"); this.Comp1.setAccelerator(KeyStroke.getKeyStroke(49, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.Comp1.addActionListener(this); this.Comp2 = new JMenuItem("Toggle Comp 2"); this.Comp2.setAccelerator(KeyStroke.getKeyStroke(50, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.Comp2.addActionListener(this); this.Comp3 = new JMenuItem("Toggle Comp 3"); this.Comp3.setAccelerator(KeyStroke.getKeyStroke(51, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.Comp3.addActionListener(this); this.PrevFile = new JMenuItem("Prev File.."); this.PrevFile.setAccelerator(KeyStroke.getKeyStroke(44, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.PrevFile.addActionListener(this); this.NextFile = new JMenuItem("Next File.."); this.NextFile.setAccelerator(KeyStroke.getKeyStroke(46, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.NextFile.addActionListener(this); View.add(new JSeparator()); View.add(this.ZoomIn); View.add(this.ZoomOut); View.add(this.ZoomAll); View.add(new JSeparator()); View.add(this.Comp1); View.add(this.Comp2); View.add(this.Comp3); View.add(new JSeparator()); View.add(this.NextFile); View.add(this.PrevFile); JMenu PickMenu = new JMenu("Actions"); this.MenuGroup = new ButtonGroup(); this.PickPMenuItem = new JCheckBoxMenuItem("Pick P"); this.PickPMenuItem.setAccelerator(KeyStroke.getKeyStroke(49, 8)); this.PickPMenuItem.addActionListener(this); this.MenuGroup.add(this.PickPMenuItem); this.PickSMenuItem = new JCheckBoxMenuItem("Pick S"); this.PickSMenuItem.setAccelerator(KeyStroke.getKeyStroke(50, 8)); this.PickSMenuItem.addActionListener(this); this.MenuGroup.add(this.PickSMenuItem); this.PickSSMenuItem = new JCheckBoxMenuItem("Pick SS"); this.PickSSMenuItem.setAccelerator(KeyStroke.getKeyStroke(51, 8)); this.PickSSMenuItem.addActionListener(this); this.MenuGroup.add(this.PickSSMenuItem); this.PickR1MenuItem = new JCheckBoxMenuItem("Pick R1"); this.PickR1MenuItem.setAccelerator(KeyStroke.getKeyStroke(52, 8)); this.PickR1MenuItem.addActionListener(this); this.MenuGroup.add(this.PickR1MenuItem); this.PickR2MenuItem = new JCheckBoxMenuItem("Pick R2"); this.PickR2MenuItem.setAccelerator(KeyStroke.getKeyStroke(53, 8)); this.PickR2MenuItem.addActionListener(this); this.MenuGroup.add(this.PickR2MenuItem); this.PickR3MenuItem = new JCheckBoxMenuItem("Pick R3"); this.PickR3MenuItem.setAccelerator(KeyStroke.getKeyStroke(54, 8)); this.PickR3MenuItem.addActionListener(this); this.MenuGroup.add(this.PickR3MenuItem); this.HideArrivals = new JCheckBoxMenuItem("Hide Arrival Picks"); this.HideArrivals.addActionListener(this); this.HideReflections = new JCheckBoxMenuItem("Hide Reflection Picks"); this.HideReflections.addActionListener(this); this.Undo = new JMenuItem("Undo"); this.Undo.setAccelerator(KeyStroke.getKeyStroke(90, 8)); this.Undo.addActionListener(this); this.Renormalize = new JMenuItem("Renormalize"); this.Renormalize.addActionListener(this); this.PickPDelete = new JMenuItem("Delete P Picks"); this.PickPDelete.setAccelerator(KeyStroke.getKeyStroke(49, 1)); this.PickPDelete.addActionListener(this); this.PickSDelete = new JMenuItem("Delete S Picks"); this.PickSDelete.setAccelerator(KeyStroke.getKeyStroke(50, 1)); this.PickSDelete.addActionListener(this); this.PickSSDelete = new JMenuItem("Delete SS Picks"); this.PickSSDelete.setAccelerator(KeyStroke.getKeyStroke(51, 1)); this.PickSSDelete.addActionListener(this); this.PickArrivalsDelete = new JMenuItem("Delete All Arrivals"); this.PickArrivalsDelete.setAccelerator(KeyStroke.getKeyStroke(52, 1)); this.PickArrivalsDelete.addActionListener(this); this.PickR1Delete = new JMenuItem("Delete R1 Picks"); this.PickR1Delete.setAccelerator(KeyStroke.getKeyStroke(53, 1)); this.PickR1Delete.addActionListener(this); this.PickR2Delete = new JMenuItem("Delete R2 Picks"); this.PickR2Delete.setAccelerator(KeyStroke.getKeyStroke(54, 1)); this.PickR2Delete.addActionListener(this); this.PickR3Delete = new JMenuItem("Delete R3 Picks"); this.PickR3Delete.setAccelerator(KeyStroke.getKeyStroke(55, 1)); this.PickR3Delete.addActionListener(this); this.PickReflectionsDelete = new JMenuItem("Delete All Reflectionss"); this.PickReflectionsDelete.setAccelerator(KeyStroke.getKeyStroke(56, 1)); this.PickReflectionsDelete.addActionListener(this); this.TrimFileButton = new JMenuItem("Clip File.."); this.TrimFileButton.setAccelerator(KeyStroke.getKeyStroke(88, 8)); this.TrimFileButton.addActionListener(this); this.MergeFileButton = new JMenuItem("Merge"); this.MergeFileButton.setAccelerator(KeyStroke.getKeyStroke(77, 8)); this.MergeFileButton.addActionListener(this); PickMenu.add(this.PickPMenuItem); PickMenu.add(this.PickSMenuItem); PickMenu.add(this.PickSSMenuItem); PickMenu.add(new JSeparator()); PickMenu.add(this.PickR1MenuItem); PickMenu.add(this.PickR2MenuItem); PickMenu.add(this.PickR3MenuItem); PickMenu.add(new JSeparator()); PickMenu.add(this.Renormalize); PickMenu.add(new JSeparator()); PickMenu.add(this.Undo); PickMenu.add(new JSeparator()); PickMenu.add(this.HideArrivals); PickMenu.add(this.HideReflections); PickMenu.add(new JSeparator()); PickMenu.add(this.PickPDelete); PickMenu.add(this.PickSDelete); PickMenu.add(this.PickSSDelete); PickMenu.add(this.PickArrivalsDelete); PickMenu.add(new JSeparator()); PickMenu.add(this.PickR1Delete); PickMenu.add(this.PickR2Delete); PickMenu.add(this.PickR3Delete); PickMenu.add(this.PickReflectionsDelete); PickMenu.add(new JSeparator()); PickMenu.add(this.TrimFileButton); PickMenu.add(new JSeparator()); PickMenu.add(this.MergeFileButton); JMenu Settings = new JMenu("Settings"); JMenuItem TraceFilterSettings = new JMenuItem("Filter Settings"); TraceFilterSettings.setAccelerator(KeyStroke.getKeyStroke(70, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); TraceFilterSettings.addActionListener(this); JMenuItem TraceDisplaySettings = new JMenu("Trace Display"); this.DisplayGroup = new ButtonGroup(); this.WiggleDisplay = new JCheckBoxMenuItem("Wiggle"); this.WiggleDisplay.setSelected(true); this.WiggleDisplay.addActionListener(this); this.DisplayGroup.add(this.WiggleDisplay); this.VariableDensityDisplay = new JCheckBoxMenuItem("Variable Density"); this.VariableDensityDisplay.addActionListener(this); this.DisplayGroup.add(this.VariableDensityDisplay); JMenuItem NormalizationSettings = new JMenu("Normalization"); this.NormalizationGroup = new ButtonGroup(); this.TraceNormalization = new JCheckBoxMenuItem("Entire Trace"); this.TraceNormalization.addActionListener(this); this.NormalizationGroup.add(this.TraceNormalization); this.ReceiverNormalization = new JCheckBoxMenuItem("Per Receiver"); this.ReceiverNormalization.setSelected(true); this.ReceiverNormalization.addActionListener(this); this.NormalizationGroup.add(this.ReceiverNormalization); JMenuItem InterpSettings = new JMenu("Interpolation Settings"); this.InterpGroup = new ButtonGroup(); this.LinearInterp = new JCheckBoxMenuItem("Linear"); this.LinearInterp.setAccelerator(KeyStroke.getKeyStroke(56, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.LinearInterp.addActionListener(this); this.InterpGroup.add(this.LinearInterp); this.SplineInterp = new JCheckBoxMenuItem("Spline"); this.SplineInterp.setAccelerator(KeyStroke.getKeyStroke(57, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(), false)); this.SplineInterp.addActionListener(this); this.SplineInterp.setSelected(true); this.InterpGroup.add(this.SplineInterp); JMenu CustomZoomButton = new JMenu("Click Zoom Settings"); this.CustomZoom = new ButtonGroup(); this.ZMs500Button = new JCheckBoxMenuItem("Z - 500 ms"); this.ZMs500Button.addActionListener(this); this.ZMs500Button.setSelected(true); this.CustomZoom.add(this.ZMs500Button); this.ZMs750Button = new JCheckBoxMenuItem("Z - 750 ms"); this.ZMs750Button.addActionListener(this); this.CustomZoom.add(this.ZMs750Button); this.ZMs1000Button = new JCheckBoxMenuItem("Z - 1000 ms"); this.ZMs1000Button.addActionListener(this); this.CustomZoom.add(this.ZMs1000Button); JMenu ClipFileDurButton = new JMenu("Clip File Duration"); this.ClipMsGroup = new ButtonGroup(); this.Ms500Button = new JCheckBoxMenuItem("500 ms"); this.Ms500Button.addActionListener(this); this.ClipMsGroup.add(this.Ms500Button); this.Ms750Button = new JCheckBoxMenuItem("750 ms"); this.Ms750Button.addActionListener(this); this.ClipMsGroup.add(this.Ms750Button); this.Ms1000Button = new JCheckBoxMenuItem("1000 ms"); this.Ms1000Button.setSelected(true); this.Ms1000Button.addActionListener(this); this.ClipMsGroup.add(this.Ms1000Button); this.ClippingMode = new JCheckBoxMenuItem("Clipping Mode.."); this.ClippingMode.addActionListener(this); this.QCPicksMode = new JCheckBoxMenuItem("QC Picks"); this.QCPicksMode.setSelected(this.QCPicksFlag); this.QCPicksMode.addActionListener(this); this.AutoPickMode = new JCheckBoxMenuItem("Auto Pick Mode"); this.AutoPickMode.setSelected(this.AutoPickFlag); this.AutoPickMode.addActionListener(this); this.SaveTraces = new JCheckBoxMenuItem("Save Traces"); this.SaveTraces.addActionListener(this); this.SaveTraces.setSelected(false); JMenuItem ExportTraces = new JMenuItem("Export Traces"); ExportTraces.addActionListener(this); JMenuItem ExportCurrentTrace = new JMenuItem("Export Current Trace"); ExportCurrentTrace.addActionListener(this); this.RepickingButton = new JCheckBoxMenuItem("Repicking"); this.RepickingButton.addActionListener(this); this.RepickingButton.setSelected(false); JMenuItem inputFilter = new JMenuItem("Filter Input Files"); inputFilter.addActionListener(this); this.DiffLocSettings = new JMenuItem("Change Save Folder"); this.DiffLocSettings.addActionListener(this); this.StackRecSettings = new JMenuItem("Stack Receiver"); this.StackRecSettings.addActionListener(this); this.showAutoPicksItem = new JCheckBoxMenuItem("Show Auto Picks"); this.showAutoPicksItem.addActionListener(this); this.showAutoPicksItem.setSelected(this.showAutoPicks); this.savePicksToDB = new JCheckBoxMenuItem("Backup Picks"); this.savePicksToDB.addActionListener(this); this.savePicksToDB.setSelected(this.backupMode); this.RotateTracesMode = new JCheckBoxMenuItem("Rotate Traces"); this.RotateTracesMode.addActionListener(this); this.RotateTracesMode.setSelected(this.rotateTracesFlag); Settings.add(TraceFilterSettings); Settings.add(TraceDisplaySettings); TraceDisplaySettings.add(this.WiggleDisplay); TraceDisplaySettings.add(this.VariableDensityDisplay); Settings.add(NormalizationSettings); NormalizationSettings.add(this.TraceNormalization); NormalizationSettings.add(this.ReceiverNormalization); Settings.add(InterpSettings); InterpSettings.add(this.LinearInterp); InterpSettings.add(this.SplineInterp); Settings.add(new JSeparator()); Settings.add(CustomZoomButton); CustomZoomButton.add(this.ZMs500Button); CustomZoomButton.add(this.ZMs750Button); CustomZoomButton.add(this.ZMs1000Button); Settings.add(new JSeparator()); Settings.add(ClipFileDurButton); ClipFileDurButton.add(this.Ms500Button); ClipFileDurButton.add(this.Ms750Button); ClipFileDurButton.add(this.Ms1000Button); Settings.add(this.ClippingMode); Settings.add(this.QCPicksMode); Settings.add(new JSeparator()); Settings.add(this.SaveTraces); Settings.add(ExportCurrentTrace); Settings.add(ExportTraces); Settings.add(new JSeparator()); Settings.add(this.RepickingButton); Settings.add(new JSeparator()); Settings.add(this.StackRecSettings); Settings.add(new JSeparator()); Settings.add(inputFilter); Settings.add(this.DiffLocSettings); Settings.add(new JSeparator()); Settings.add(this.savePicksToDB); Settings.add(this.RotateTracesMode); Settings.add(this.showAutoPicksItem); JMenu Help = new JMenu("Help"); JMenuItem Tips = new JMenuItem("Tips and Tricks.."); JMenuItem AboutUS = new JMenuItem("About us"); Help.add(Tips); Help.add(new JSeparator()); Help.add(AboutUS); this.MenuBar.add(File); this.MenuBar.add(View); this.MenuBar.add(PickMenu); this.MenuBar.add(Settings); this.MenuBar.add(Help); this.MasterFrame.setJMenuBar(this.MenuBar); this.MasterFrame.setVisible(true); this.filterSettings = new FilterSettings(); traceButtonsState(false); loadPropertiesFile(); if (!this.pickingAuth.isAuthenticated()) { this.pickingAuth.authenticate(); if (this.pickingAuth.isAuthenticated()) { this.pickingAuth.getProject(); this.pickingAuth.getProjectDetails(); }  }  setUserProjectDetails(); this.DiffLocSettings.doClick(); FilterSettingsButton.doClick(); } static String minDepth = ""; static String maxDepth = ""; static String minMisfit = ""; static String maxMisfit = ""; static String minEasting = ""; static String minNorthing = ""; static String maxEasting = ""; static String maxNorthing = ""; int[] revMappingArray; int[] mappingArray; int stackReceiverCountInput; boolean stackMode; DefaultMutableTreeNode deleteNode; DefaultMutableTreeNode skipNode; DefaultMutableTreeNode filterNode; DragSource source; DragGestureRecognizer recognizer; TransferableTreeNode transferable; DropTarget target; volatile DefaultMutableTreeNode[] oldNode; public void actionPerformed(ActionEvent e) { if (e.getActionCommand() == "Show Auto Picks") this.showAutoPicks = !this.showAutoPicks;  if (e.getActionCommand() == "Entire Trace" || e.getActionCommand() == "Per Receiver") this.NORMALIZATION_MODE = e.getActionCommand();  if (e.getActionCommand() == "Wiggle" || e.getActionCommand() == "Variable Density") this.DISPLAY_MODE = e.getActionCommand();  if (e.getActionCommand() == "Backup Picks") { this.backupMode = !this.backupMode; this.savePicksToDB.setSelected(this.backupMode); }  if (e.getActionCommand() == "Stack Receiver") { JTextField field1 = new JTextField(this.stackedReceivers); JTextField field2 = new JTextField(this.repititionReceiver); JTextField field3 = new JTextField(this.receiverCountStr); Object[] message = { "Stacked Receivers?", field1, "No of times?", field2, "Total no of Receivers?", field3 }; int option = JOptionPane.showConfirmDialog(null, message, "Enter Stacked Receivers Info", 2); this.stackedReceivers = field1.getText(); this.stackedReceivers = this.stackedReceivers.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").replaceAll(" ", ""); this.stackedReceivers = String.valueOf(this.stackedReceivers) + ","; this.repititionReceiver = field2.getText(); this.repititionReceiver = this.repititionReceiver.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").replaceAll(" ", ""); this.repititionReceiver = String.valueOf(this.repititionReceiver) + ","; String[] receivers = this.stackedReceivers.split(","); String[] repeats = this.repititionReceiver.split(","); this.receiverCountStr = field3.getText(); if (!this.receiverCountStr.isEmpty()) this.stackReceiverCountInput = Integer.parseInt(this.receiverCountStr);  if ((((receivers.length == 0) ? 1 : 0) | ((repeats.length == 0) ? 1 : 0) | ((this.stackReceiverCountInput > 0) ? 0 : 1)) != 0) { JOptionPane.showMessageDialog(new JFrame(), "Input Issue"); this.stackMode = false; return; }  if (receivers.length != repeats.length) { JOptionPane.showMessageDialog(new JFrame(), "Something wrong with stack receivers input -- diff number of receivers & repitition"); this.stackMode = false; return; }  int[] receiverNo = new int[receivers.length]; for (int i = 0; i < receiverNo.length; i++) receiverNo[i] = Integer.parseInt(receivers[i]);  int[] receiverRep = new int[receivers.length]; int rec2IgnoreArrayLength = 0; int j; for (j = 0; j < receiverRep.length; j++) { receiverRep[j] = Integer.parseInt(repeats[j]); rec2IgnoreArrayLength += receiverRep[j]; }  for (j = 0; j < receiverRep.length - 1; j++) { if (receiverNo[j] + receiverRep[j] > receiverNo[j + 1]) { JOptionPane.showMessageDialog(new JFrame(), "Something wrong with no. receivers input --> overlap"); this.stackMode = false; return; }  }  rec2IgnoreArrayLength -= receiverRep.length; int[] rec2Ignore = new int[rec2IgnoreArrayLength]; int count = 0; int k; for (k = 0; k < receiverNo.length; k++) { for (int m = 1; m <= receiverRep[k] - 1; m++) { rec2Ignore[count] = receiverNo[k] + m - 1; count++; }  }  this.mappingArray = new int[this.stackReceiverCountInput - rec2Ignore.length]; count = 0; for (k = 0; k < this.stackReceiverCountInput; k++) { boolean condition = true; for (int m = 0; m < rec2Ignore.length; m++) { if (k == rec2Ignore[m]) condition = false;  }  if (condition) { this.mappingArray[count] = k; count++; }  }  this.revMappingArray = new int[this.stackReceiverCountInput]; for (k = 0; k < this.stackReceiverCountInput; k++) this.revMappingArray[k] = k;  for (k = 0; k < receiverNo.length; k++) { count = 0; for (int m = 0; m < receiverRep[k]; m++) { this.revMappingArray[receiverNo[k] + count - 1] = this.revMappingArray[receiverNo[k] - 1]; count++; }  }  for (k = 0; k < this.revMappingArray.length; k++) { for (int m = 0; m < this.mappingArray.length; m++) { if (this.revMappingArray[k] == this.mappingArray[m]) { this.revMappingArray[k] = m; break; }  }  }  this.stackMode = true; }  if (e.getActionCommand() == "Rotate Traces") { this.rotateTracesFlag = !this.rotateTracesFlag; this.RotateTracesMode.setSelected(this.rotateTracesFlag); }  if (e.getActionCommand() == "Filter Input Files") { JTextField minMisfitField = new JTextField(minMisfit); JTextField maxMisfitField = new JTextField(maxMisfit); JTextField minDepthField = new JTextField(minDepth); JTextField maxDepthField = new JTextField(maxDepth); JTextField mineastingField = new JTextField(minEasting); JTextField maxeastingField = new JTextField(maxEasting); JTextField minnorthingField = new JTextField(minNorthing); JTextField maxnorthingField = new JTextField(maxNorthing); Object[] message = { 
/*      */           "Min Misfit:", minMisfitField, "Max Misfit:", maxMisfitField, "Min Depth:", minDepthField, "Max Depth:", maxDepthField, "Min Easting:", mineastingField, 
/*      */           "Max Easting:", maxeastingField, "Min Northing:", minnorthingField, "Max Northing:", maxnorthingField }; JOptionPane.showConfirmDialog(null, message, "Enter File Filter Parameters", 2); if ((minMisfitField.getText().toString().isEmpty() & maxMisfitField.getText().toString().isEmpty() & minDepthField.getText().toString().isEmpty() & maxDepthField.getText().toString().isEmpty() & mineastingField.getText().toString().isEmpty() & minnorthingField.getText().toString().isEmpty() & maxeastingField.getText().toString().isEmpty() & maxnorthingField.getText().toString().isEmpty()) != 0) { minMisfit = ""; maxMisfit = ""; minDepth = ""; maxDepth = ""; minEasting = ""; minNorthing = ""; maxEasting = ""; maxNorthing = ""; return; }  try { double test0 = Double.parseDouble(minMisfitField.getText().toString()); double test1 = Double.parseDouble(maxMisfitField.getText().toString()); double test2 = Double.parseDouble(minDepthField.getText().toString()); double test3 = Double.parseDouble(maxDepthField.getText().toString()); double test4 = Double.parseDouble(mineastingField.getText().toString()); double test5 = Double.parseDouble(maxeastingField.getText().toString()); double test6 = Double.parseDouble(minnorthingField.getText().toString()); double d1 = Double.parseDouble(maxnorthingField.getText().toString()); } catch (Exception e2) { JOptionPane.showMessageDialog(new JFrame("Error"), "Cannot convert to double. Double check inputs please."); return; }  if (Double.parseDouble(minMisfitField.getText().toString()) > Double.parseDouble(maxMisfitField.getText().toString())) { JOptionPane.showMessageDialog(new JFrame("Error"), "Minimum misfit cannot be greater than maximum misfit."); return; }  if (Double.parseDouble(minDepthField.getText().toString()) > Double.parseDouble(maxDepthField.getText().toString())) { JOptionPane.showMessageDialog(new JFrame("Error"), "Minimum depth cannot be greater than maximum depth."); return; }  minMisfit = minMisfitField.getText().toString(); maxMisfit = maxMisfitField.getText().toString(); minDepth = minDepthField.getText().toString(); maxDepth = maxDepthField.getText().toString(); minEasting = mineastingField.getText().toString(); maxEasting = maxeastingField.getText().toString(); minNorthing = minnorthingField.getText().toString(); maxNorthing = maxnorthingField.getText().toString(); }  if (e.getActionCommand() == "Change Save Folder") { JTextField field1 = new JTextField(this.inputFolderSaving); JTextField field2 = new JTextField(this.outputFolderSaving); Object[] message = { "Input Folder:", field1, "Output Folder:", field2 }; JOptionPane.showConfirmDialog(null, message, "Enter Input, Output Folder Location", 2); this.inputFolderSaving = (new File(field1.getText())).toString(); this.outputFolderSaving = (new File(field2.getText())).toString(); if ((this.inputFolderSaving.isEmpty() & this.outputFolderSaving.isEmpty()) != 0) return;  if ((((new File(this.inputFolderSaving)).exists() ? 0 : 1) | ((new File(this.outputFolderSaving)).exists() ? 0 : 1)) != 0) { JOptionPane.showMessageDialog(new JFrame("Error"), "Either input or output saving folder does not exist. Resetting to default."); this.inputFolderSaving = ""; this.outputFolderSaving = ""; }  }  if (e.getActionCommand() == "Clipping Mode..") { this.MasterClippingMode = this.ClippingMode.isSelected(); this.drawCount = 0; }  if (e.getActionCommand() == "QC Picks") this.QCPicksFlag = this.QCPicksMode.isSelected();  if (e.getActionCommand() == "Auto Pick Mode") { this.AutoPickFlag = this.AutoPickMode.isSelected(); this.TrimFileMode = false; }  if (e.getActionCommand() == "500 ms") { this.clipFileDuration = 500; if (this.TrimFileMode) setMouse(500);  }  if (e.getActionCommand() == "750 ms") { this.clipFileDuration = 750; if (this.TrimFileMode) setMouse(750);  }  if (e.getActionCommand() == "1000 ms") { this.clipFileDuration = 1000; if (this.TrimFileMode)
/*      */         setMouse(1000);  }  if (e.getActionCommand() == "Z - 500 ms")
/*      */       this.customZoomX = 500;  if (e.getActionCommand() == "Z - 750 ms")
/*      */       this.customZoomX = 750;  if (e.getActionCommand() == "Z - 1000 ms")
/*      */       this.customZoomX = 1000;  if (e.getActionCommand() == "Undo")
/*      */       undoPick();  if (e.getActionCommand() == "Renormalize") { this.lowerLimitNorm = (int)Math.ceil(this.chartPanel.getChart().getXYPlot().getDomainAxis().getRange().getLowerBound() / this.MATInfo.timeSample); this.upperLimitNorm = (int)Math.floor(this.chartPanel.getChart().getXYPlot().getDomainAxis().getRange().getUpperBound() / this.MATInfo.timeSample); if (this.lowerLimitNorm < 1) { JOptionPane.showMessageDialog(new JFrame(), "Lower limit < 1; Cannot normalize. Shift Window"); return; }  if (this.upperLimitNorm > this.MATInfo.noSample) { JOptionPane.showMessageDialog(new JFrame(), "Upper limit exceeds no of samples; Cannot normalize. Shift Window"); return; }  this.renormalizeFlag = true; DrawTracePickPanel(0); }  if (e.getActionCommand() == "Export Current Trace") { Runnable r = new Runnable() { public void run() { final File currentFile = new File(PickingWindow.currentFilePath); if (currentFile.exists() && currentFile.isFile()) { SwingUtilities.invokeLater(new Runnable() { public void run() {} }
/*      */                 ); return; }  } }
/*      */         ; ExecutorService executor = Executors.newCachedThreadPool(); executor.submit(r); }  if (e.getActionCommand() == "Export Traces") { FolderBrowser folderBrowser = new FolderBrowser(); if (folderBrowser.getReturnValue() == 1) { final File selectFile = folderBrowser.getSelectedFile(); if (selectFile.exists() && selectFile.isFile()) { SwingUtilities.invokeLater(new Runnable() { public void run() {} }
/*      */             ); return; }  File[] FileNames = null; if (selectFile.exists() && selectFile.isDirectory()) { FileNames = selectFile.listFiles((FilenameFilter)new ExtensionFileFilter("mat")); if (FileNames.length < 1)
/*      */             return;  final File[] ipFileNames = FileNames; this.progressBar.setMinimum(0); this.progressBar.setMaximum(ipFileNames.length); this.progressBar.setVisible(true); this.progressBar.setIndeterminate(false); for (int k = 0; k < ipFileNames.length; k++) { final int temp = k + 1; SwingUtilities.invokeLater(new Runnable() { public void run() { PickingWindow.this.progressBar.setValue(temp); Rectangle progressRect = PickingWindow.this.progressBar.getBounds(); progressRect.x = 0; progressRect.y = 0; PickingWindow.this.progressBar.paintImmediately(progressRect); if (temp == ipFileNames.length)
/*      */                       PickingWindow.this.progressBar.setVisible(false);  } }
/*      */               ); }  }  }  }  if (e.getActionCommand() == "Picker..") { this.pickingAuth.authenticate(); this.pickingAuth.getProject(); this.pickingAuth.getProjectDetails(); setUserProjectDetails(); }  if (e.getActionCommand() == "Change Project") { this.pickingAuth.getProject(); this.pickingAuth.getProjectDetails(); setUserProjectDetails(); }  if (e.getActionCommand() == "Change Design/Stage") { this.pickingAuth.getProjectDetails(); setUserProjectDetails(); }  if (e.getActionCommand() == "Repicking")
/*      */       this.RepickingMode = this.RepickingButton.isSelected();  if ((((e.getActionCommand() == "Trim File") ? 1 : 0) | ((e.getActionCommand() == "Clip File..") ? 1 : 0)) != 0) { saveFileDialog(); this.chartPanel.restoreAutoBounds(); this.TrimFileMode = true; setMouse(this.clipFileDuration); trimModetraceButtonsState(false); this.chartPanel.setMouseZoomable(false); resetTimer(); }  if ((((e.getActionCommand() == "OpenDirIcon") ? 1 : 0) | ((e.getActionCommand() == "Import Folder") ? 1 : 0)) != 0) { saveFileDialog(); this.clipParentFilePath = "x"; FolderBrowser folderBrowser = new FolderBrowser(); if (folderBrowser.getReturnValue() == 1) { this.drawCount = 0; resetTimer(); emptyPanels(); InputFolderPath = folderBrowser.getSelectedFile().toString(); createFileTree(); }  }  if ((((e.getActionCommand() == "CloseDirIcon") ? 1 : 0) | ((e.getActionCommand() == "Close Folder") ? 1 : 0)) != 0) { this.drawCount = 0; saveFileDialog(); emptyPanels(); resetCounter(); stopTimer(); traceButtonsState(false); }  if ((((e.getActionCommand() == "Next File") ? 1 : 0) | ((e.getActionCommand() == "Next File..") ? 1 : 0)) != 0)
/*      */       DrawTracePickPanel(1);  if ((((e.getActionCommand() == "Previous File") ? 1 : 0) | ((e.getActionCommand() == "Prev File..") ? 1 : 0)) != 0)
/*      */       DrawTracePickPanel(-1);  if ((((e.getActionCommand() == "Filter Settings") ? 1 : 0) | ((e.getActionCommand() == "Filter Settings Icon") ? 1 : 0)) != 0); if ((((e.getActionCommand() == "Zoom In") ? 1 : 0) | ((e.getActionCommand() == "ZoomInButton") ? 1 : 0)) != 0)
/*      */       zoomChartAxis(this.chartPanel, true);  if ((((e.getActionCommand() == "Zoom Out") ? 1 : 0) | ((e.getActionCommand() == "ZoomOutButton") ? 1 : 0)) != 0)
/*      */       zoomChartAxis(this.chartPanel, false);  if ((((e.getActionCommand() == "Zoom All") ? 1 : 0) | ((e.getActionCommand() == "ZoomAllButton") ? 1 : 0)) != 0)
/*      */       this.chartPanel.restoreAutoBounds();  if (e.getActionCommand() == "Delete P Picks") { for (int i = 0; i < this.pickP.length; ) { this.pickP[i][1] = Double.NaN; i++; }  updatePPicks(); clearInterp(); }  if (e.getActionCommand() == "Delete S Picks") { for (int i = 0; i < this.pickS.length; ) { this.pickS[i][1] = Double.NaN; i++; }  updateSPicks(); clearInterp(); }  if (e.getActionCommand() == "Delete SS Picks") { for (int i = 0; i < this.pickS.length; ) { this.pickSS[i][1] = Double.NaN; i++; }  updateSSPicks(); clearInterp(); }  if (e.getActionCommand() == "Delete R1 Picks") { for (int i = 0; i < this.pickS.length; ) { this.pickR1[i][1] = Double.NaN; i++; }  updateR1Picks(); clearInterp(); }  if (e.getActionCommand() == "Delete R2 Picks") { for (int i = 0; i < this.pickS.length; ) { this.pickR2[i][1] = Double.NaN; i++; }  updateR2Picks(); clearInterp(); }  if (e.getActionCommand() == "Delete R3 Picks") { for (int i = 0; i < this.pickS.length; ) { this.pickR3[i][1] = Double.NaN; i++; }  updateR3Picks(); clearInterp(); }  if (e.getActionCommand() == "Delete All Arrivals") { int i; for (i = 0; i < this.pickP.length; ) { this.pickP[i][1] = Double.NaN; i++; }  for (i = 0; i < this.pickS.length; ) { this.pickS[i][1] = Double.NaN; i++; }  for (i = 0; i < this.pickSS.length; ) { this.pickSS[i][1] = Double.NaN; i++; }  updatePPicks(); updateSPicks(); updateSSPicks(); clearInterp(); }  if (e.getActionCommand() == "Delete All Reflections") { int i; for (i = 0; i < this.pickR1.length; ) { this.pickR1[i][1] = Double.NaN; i++; }  for (i = 0; i < this.pickR2.length; ) { this.pickR2[i][1] = Double.NaN; i++; }  for (i = 0; i < this.pickR3.length; ) { this.pickR3[i][1] = Double.NaN; i++; }  updateR1Picks(); updateR2Picks(); updateR3Picks(); clearInterp(); }  if (e.getActionCommand() == "1") { this.Comp1Flag = this.c1.isSelected(); for (int i = 0; i < this.tracePlot.getSeriesCount(); i += 3)
/*      */         this.tracePlot.getRenderer().setSeriesVisible(i, Boolean.valueOf(this.c1.isSelected()));  }  if (e.getActionCommand() == "Toggle Comp 1") { this.Comp1Flag = !this.Comp1Flag; this.c1.setSelected(this.Comp1Flag); for (int i = 0; i < this.tracePlot.getSeriesCount(); i += 3)
/*      */         this.tracePlot.getRenderer().setSeriesVisible(i, Boolean.valueOf(this.c1.isSelected()));  }  if (e.getActionCommand() == "2") { this.Comp2Flag = this.c2.isSelected(); for (int i = 1; i < this.tracePlot.getSeriesCount(); i += 3)
/*      */         this.tracePlot.getRenderer().setSeriesVisible(i, Boolean.valueOf(this.c2.isSelected()));  }  if (e.getActionCommand() == "Toggle Comp 2") { this.Comp2Flag = !this.Comp2Flag; this.c2.setSelected(this.Comp2Flag); for (int i = 1; i < this.tracePlot.getSeriesCount(); i += 3)
/*      */         this.tracePlot.getRenderer().setSeriesVisible(i, Boolean.valueOf(this.c2.isSelected()));  }  if (e.getActionCommand() == "3") { this.Comp3Flag = this.c3.isSelected(); for (int i = 2; i < this.tracePlot.getSeriesCount(); i += 3)
/*      */         this.tracePlot.getRenderer().setSeriesVisible(i, Boolean.valueOf(this.c3.isSelected()));  }  if (e.getActionCommand() == "Toggle Comp 3") { this.Comp3Flag = !this.Comp3Flag; this.c3.setSelected(this.Comp3Flag); for (int i = 2; i < this.tracePlot.getSeriesCount(); i += 3)
/*      */         this.tracePlot.getRenderer().setSeriesVisible(i, Boolean.valueOf(this.c3.isSelected()));  }  if ((((e.getActionCommand() == "Pick P") ? 1 : 0) | ((e.getActionCommand() == "Pick S") ? 1 : 0) | ((e.getActionCommand() == "Pick SS") ? 1 : 0) | ((e.getActionCommand() == "Pick R1") ? 1 : 0) | ((e.getActionCommand() == "Pick R2") ? 1 : 0) | ((e.getActionCommand() == "Pick R3") ? 1 : 0)) != 0) { this.PickPButton.setSelected(this.PickPMenuItem.isSelected()); this.PickSButton.setSelected(this.PickSMenuItem.isSelected()); this.PickSSButton.setSelected(this.PickSSMenuItem.isSelected()); this.PickR1Button.setSelected(this.PickR1MenuItem.isSelected()); this.PickR2Button.setSelected(this.PickR2MenuItem.isSelected()); this.PickR3Button.setSelected(this.PickR3MenuItem.isSelected()); this.pickPFlag = this.PickPButton.isSelected(); this.pickSFlag = this.PickSButton.isSelected(); this.pickSSFlag = this.PickSSButton.isSelected(); this.pickR1Flag = this.PickR1Button.isSelected(); this.pickR2Flag = this.PickR2Button.isSelected(); this.pickR3Flag = this.PickR3Button.isSelected(); clearInterp(); if (this.AutoPickFlag)
/*      */         this.TracePanel.setFocusable(true);  }  if (e.getActionCommand() == "Hide Arrival Picks"); this.HideArrivalsFlag = this.HideArrivals.isSelected(); if (e.getActionCommand() == "Hide Reflection Picks"); this.HideReflectionsFlag = this.HideReflections.isSelected(); if ((((e.getActionCommand() == "P") ? 1 : 0) | ((e.getActionCommand() == "S") ? 1 : 0) | ((e.getActionCommand() == "SS") ? 1 : 0) | ((e.getActionCommand() == "R1") ? 1 : 0) | ((e.getActionCommand() == "R2") ? 1 : 0) | ((e.getActionCommand() == "R3") ? 1 : 0)) != 0) { this.PickPMenuItem.setSelected(this.PickPButton.isSelected()); this.PickSMenuItem.setSelected(this.PickSButton.isSelected()); this.PickSSMenuItem.setSelected(this.PickSSButton.isSelected()); this.PickR1MenuItem.setSelected(this.PickR1Button.isSelected()); this.PickR2MenuItem.setSelected(this.PickR2Button.isSelected()); this.PickR3MenuItem.setSelected(this.PickR3Button.isSelected()); this.pickPFlag = this.PickPButton.isSelected(); this.pickSFlag = this.PickSButton.isSelected(); this.pickSSFlag = this.PickSSButton.isSelected(); this.pickR1Flag = this.PickR1Button.isSelected(); this.pickR2Flag = this.PickR2Button.isSelected(); this.pickR3Flag = this.PickR3Button.isSelected(); clearInterp(); }  if (e.getActionCommand() == "Exit") { saveFileDialog(); System.exit(0); }  if (e.getActionCommand() == "RefreshTreeIcon")
/*      */       updateTree();  if (e.getActionCommand() == "Save") { saveFile(currentFilePath); this.saveCheck = false; }  if (e.getActionCommand() == "Merge") { TreePath[] paths = fileTree.getSelectionPaths(); System.out.print("Paths - "); System.out.print(paths.toString()); String[] filePaths = new String[paths.length]; String mergingOrder = "Merging Order:\n"; for (int i = 0; i < paths.length; i++) { filePaths[i] = createFilePath(paths[i]); if (!filePaths[i].endsWith("mat")) { JOptionPane.showMessageDialog(new JFrame("Error"), "Select MAT files only & try again."); return; }  mergingOrder = String.valueOf(mergingOrder) + Integer.toString(i + 1) + ". " + filePaths[i] + "\n"; }  mergingOrder = String.valueOf(mergingOrder) + "Proceed?"; int response = JOptionPane.showConfirmDialog(null, mergingOrder, "Merge QC", 0, 3); if (response == 0) { this.SoftwareStatusLabel.setText("Merging"); if (filePaths.length > 2)
/*      */           JOptionPane.showMessageDialog(null, "Merging more than 2 files is not recommended.", "Warning", 2);  this.SoftwareStatusLabel.setText(""); } else { if (response == 1) { this.saveCheck = false; return; }  if (response == -1)
/*      */           return;  }  if (paths.length < 1) { JOptionPane.showMessageDialog(new JFrame("Error"), "No files selected."); return; }  }  } private void setMouse(int i) { int w = (int)this.tracePlot.getDomainAxis().lengthToJava2D((i + 1), this.chartPanel.getScreenDataArea(), this.tracePlot.getDomainAxisEdge()); int h = (int)this.tracePlot.getRangeAxis().lengthToJava2D(this.tracePlot.getRangeAxis().getRange().getLength() / 1.33D, this.chartPanel.getScreenDataArea(), this.tracePlot.getRangeAxisEdge()); int[] pix = new int[w * h]; int index = 0; for (int y = 0; y < h; y++) { if ((((y < 3) ? 1 : 0) | ((y > h - 3) ? 1 : 0)) != 0) { for (int x = 0; x < w; x++)
/*      */           pix[index++] = 1684275300;  } else { for (int x = 0; x < w; x++) { if ((((x < 3) ? 1 : 0) | ((x > w - 3) ? 1 : 0)) != 0) { pix[index++] = 1684275300; } else { index++; }  }  }  }  Image img = createImage(new MemoryImageSource(w, h, pix, 0, w)); Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "transparentCursor"); this.chartPanel.setCursor(transparentCursor); } private void undoPick() { if (this.prevWaveType == "P") { this.pickP = this.prevP; updatePPicks(); } else if (this.prevWaveType == "S") { this.pickS = this.prevS; updateSPicks(); } else if (this.prevWaveType == "SS") { this.pickSS = this.prevSS; updateSSPicks(); } else if (this.prevWaveType == "R1") { this.pickR1 = this.prevR1; updateR1Picks(); } else if (this.prevWaveType == "R2") { this.pickR2 = this.prevR2; updateR2Picks(); } else if (this.prevWaveType == "R3") { this.pickR3 = this.prevR3; updateR3Picks(); }  } private void createFileTree() { DefaultMutableTreeNode root = new DefaultMutableTreeNode(); getList(root, new File(InputFolderPath)); fileTree = new JTree(root); fileTree.setRootVisible(false); fileTree.setBackground(Color.white); fileTree.getSelectionModel().setSelectionMode(2); fileTree.addMouseListener(new MouseAdapter() { public void mouseClicked(MouseEvent e) { PickingWindow.fileTree.grabFocus(); PickingWindow.fileTree.setFocusable(true); if ((((e.getButton() == 1) ? 1 : 0) & ((e.getClickCount() == 2) ? 1 : 0)) != 0)
/*      */               PickingWindow.this.DrawTracePickPanel(0);  if (e.getButton() == 3) { TreePath path = PickingWindow.fileTree.getPathForLocation(e.getX(), e.getY()); Rectangle pathBounds = PickingWindow.fileTree.getUI().getPathBounds(PickingWindow.fileTree, path); if (pathBounds != null && pathBounds.contains(e.getX(), e.getY())) { if (!PickingWindow.fileTree.getSelectionModel().isPathSelected(path))
/*      */                   PickingWindow.fileTree.getSelectionModel().setSelectionPath(path);  final TreePath[] selectedPaths = PickingWindow.fileTree.getSelectionPaths(); final DefaultMutableTreeNode[] node = new DefaultMutableTreeNode[selectedPaths.length]; JPopupMenu menu = new JPopupMenu(); JMenuItem Delete = new JMenuItem("Delete.."); Delete.setFont(new Font("Avenir Book", 0, 11)); Delete.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { File currentFile = new File(PickingWindow.currentFilePath); DefaultMutableTreeNode nextNode = null; for (int i = 0; i < node.length; i++) { node[i] = (DefaultMutableTreeNode)selectedPaths[i].getLastPathComponent(); if (node[i].toString().equals(currentFile.getName())) { (PickingWindow.null.access$0(PickingWindow.null.this)).saveCheck = false; PickingWindow.null.access$0(PickingWindow.null.this).emptyTracePickPanels(); }  String oldPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); Boolean existDeleteNode = Boolean.valueOf(false); nextNode = node[i].getNextNode(); DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node[i].getParent(); parentNode.remove(parentNode.getIndex(node[i])); Enumeration<DefaultMutableTreeNode> parentNodeEnum = (Enumeration)parentNode.children(); while (parentNodeEnum.hasMoreElements()) { DefaultMutableTreeNode expNode = parentNodeEnum.nextElement(); if (expNode.toString().equalsIgnoreCase("00_Deleted Files")) { existDeleteNode = Boolean.valueOf(true); (PickingWindow.null.access$0(PickingWindow.null.this)).deleteNode = expNode; }  }  if (!existDeleteNode.booleanValue())
/*      */                             parentNode.insert((PickingWindow.null.access$0(PickingWindow.null.this)).deleteNode, 0);  File dir = new File(PickingWindow.createFilePath(PickingWindow.getPath((PickingWindow.null.access$0(PickingWindow.null.this)).deleteNode))); if (!dir.exists())
/*      */                             dir.mkdir();  (PickingWindow.null.access$0(PickingWindow.null.this)).deleteNode.insert(node[i], 0); String newPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); try { System.out.println(oldPath.toString()); System.out.println(newPath.toString()); Files.move((new File(oldPath)).toPath(), (new File(newPath)).toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }); } catch (Exception e1) { e1.printStackTrace(); JOptionPane.showMessageDialog(new JFrame("Error"), "Something went wrong with File Tree Sync. Updating JTree."); }  }  PickingWindow.fileTree.setSelectionPath(PickingWindow.getPath(nextNode)); PickingWindow.updateTree(); PickingWindow.null.access$0(PickingWindow.null.this).DrawTracePickPanel(0); } }
/*      */                   ); menu.add(Delete); JMenuItem Skip = new JMenuItem("Skip.."); Skip.setFont(new Font("Avenir Book", 0, 11)); Skip.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { DefaultMutableTreeNode nextNode = null; File currentFile = new File(PickingWindow.currentFilePath); for (int i = 0; i < node.length; i++) { node[i] = (DefaultMutableTreeNode)selectedPaths[i].getLastPathComponent(); if (node[i].toString().equals(currentFile.getName())) { (PickingWindow.null.access$0(PickingWindow.null.this)).saveCheck = false; PickingWindow.null.access$0(PickingWindow.null.this).emptyTracePickPanels(); }  String oldPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); Boolean existSkipNode = Boolean.valueOf(false); nextNode = node[i].getNextNode(); DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node[i].getParent(); parentNode.remove(parentNode.getIndex(node[i])); Enumeration<DefaultMutableTreeNode> parentNodeEnum = (Enumeration)parentNode.children(); while (parentNodeEnum.hasMoreElements()) { DefaultMutableTreeNode expNode = parentNodeEnum.nextElement(); if (expNode.toString().equalsIgnoreCase("01_Skipped Files")) { existSkipNode = Boolean.valueOf(true); (PickingWindow.null.access$0(PickingWindow.null.this)).skipNode = expNode; }  }  if (!existSkipNode.booleanValue())
/*      */                             parentNode.insert((PickingWindow.null.access$0(PickingWindow.null.this)).skipNode, 0);  File dir = new File(PickingWindow.createFilePath(PickingWindow.getPath((PickingWindow.null.access$0(PickingWindow.null.this)).skipNode))); if (!dir.exists())
/*      */                             dir.mkdir();  (PickingWindow.null.access$0(PickingWindow.null.this)).skipNode.insert(node[i], 0); String newPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); try { Files.move((new File(oldPath)).toPath(), (new File(newPath)).toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }); } catch (Exception e1) { JOptionPane.showMessageDialog(new JFrame("Error"), "Something went wrong with File Tree Sync. Updating JTree."); PickingWindow.updateTree(); }  }  PickingWindow.fileTree.setSelectionPath(PickingWindow.getPath(nextNode)); PickingWindow.updateTree(); PickingWindow.null.access$0(PickingWindow.null.this).DrawTracePickPanel(0); } }
/*      */                   ); menu.add(Skip); JMenuItem filterFile = new JMenuItem("Filter.."); filterFile.setFont(new Font("Avenir Book", 0, 11)); filterFile.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { File currentFile = new File(PickingWindow.currentFilePath); DefaultMutableTreeNode nextNode = null; for (int i = 0; i < node.length; i++) { node[i] = (DefaultMutableTreeNode)selectedPaths[i].getLastPathComponent(); if (node[i].toString().equals(currentFile.getName())) { PickingWindow.null.access$0(PickingWindow.null.this).saveFileDialog(); (PickingWindow.null.access$0(PickingWindow.null.this)).saveCheck = false; PickingWindow.null.access$0(PickingWindow.null.this).emptyTracePickPanels(); }  String oldPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); Boolean existDeleteNode = Boolean.valueOf(false); nextNode = node[i].getNextNode(); DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode)node[i].getParent(); parentNode.remove(parentNode.getIndex(node[i])); Enumeration<DefaultMutableTreeNode> parentNodeEnum = (Enumeration)parentNode.children(); while (parentNodeEnum.hasMoreElements()) { DefaultMutableTreeNode expNode = parentNodeEnum.nextElement(); if (expNode.toString().equalsIgnoreCase("02_Filtered")) { existDeleteNode = Boolean.valueOf(true); (PickingWindow.null.access$0(PickingWindow.null.this)).filterNode = expNode; }  }  if (!existDeleteNode.booleanValue())
/*      */                             parentNode.insert((PickingWindow.null.access$0(PickingWindow.null.this)).filterNode, 0);  File dir = new File(PickingWindow.createFilePath(PickingWindow.getPath((PickingWindow.null.access$0(PickingWindow.null.this)).filterNode))); if (!dir.exists())
/*      */                             dir.mkdir();  (PickingWindow.null.access$0(PickingWindow.null.this)).filterNode.insert(node[i], 0); String newPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); try { Files.move((new File(oldPath)).toPath(), (new File(newPath)).toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }); } catch (Exception e1) { JOptionPane.showMessageDialog(new JFrame("Error"), "Something went wrong with File Tree Sync. Updating JTree."); }  }  PickingWindow.fileTree.setSelectionPath(PickingWindow.getPath(nextNode)); PickingWindow.updateTree(); PickingWindow.null.access$0(PickingWindow.null.this).DrawTracePickPanel(0); } }
/*      */                   ); menu.add(filterFile); JMenuItem Duplicate = new JMenuItem("Duplicate.."); Duplicate.setFont(new Font("Avenir Book", 0, 11)); Duplicate.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { File currentFile = new File(PickingWindow.currentFilePath); DefaultMutableTreeNode nextNode = null; for (int i = 0; i < node.length; i++) { node[i] = (DefaultMutableTreeNode)selectedPaths[i].getLastPathComponent(); nextNode = node[i]; if (node[i].toString().equals(currentFile.getName())) { (PickingWindow.null.access$0(PickingWindow.null.this)).saveCheck = false; PickingWindow.null.access$0(PickingWindow.null.this).emptyTracePickPanels(); }  String oldPath = PickingWindow.createFilePath(PickingWindow.getPath(node[i])); String newPath = oldPath; int count = 1; newPath = oldPath.replace(".mat", "_" + String.valueOf(count) + ".mat"); while ((new File(newPath)).exists())
/*      */                             count++;  try { Source source = Sources.openFile(oldPath); Mat5File mat5File = Mat5.newReader(source).readMat(); source.close(); Struct headerData = mat5File.getStruct("headerData"); headerData.set("fileName", (Array)Mat5.newString((new File(newPath)).getName())); Mat5.writeToFile((MatFile)mat5File, newPath); } catch (Exception e1) { e1.printStackTrace(); JOptionPane.showMessageDialog(new JFrame("Error"), "Something went wrong with File Tree Sync. Updating JTree."); }  }  PickingWindow.fileTree.setSelectionPath(PickingWindow.getPath(nextNode)); PickingWindow.updateTree(); PickingWindow.null.access$0(PickingWindow.null.this).DrawTracePickPanel(0); } }
/*      */                   ); menu.add(Duplicate); menu.setSize(30, 10); menu.show(PickingWindow.fileTree, e.getX(), e.getY()); menu.setVisible(true); PickingWindow.fileTree.setComponentPopupMenu(menu); }  }  } public void mousePressed(MouseEvent e) {} public void mouseReleased(MouseEvent e) {} public void mouseEntered(MouseEvent e) {} public void mouseExited(MouseEvent e) {} }
/*      */       ); fileTree.addKeyListener(new KeyListener() { public void keyTyped(KeyEvent e) { PickingWindow.fileTree.grabFocus(); PickingWindow.fileTree.setFocusable(true); PickingWindow.this.TracePanel.setFocusable(false); }
/*      */           public void keyPressed(KeyEvent e) { PickingWindow.fileTree.grabFocus(); PickingWindow.fileTree.setFocusable(true); PickingWindow.this.TracePanel.setFocusable(false); if (e.getKeyCode() == 10)
/*      */               PickingWindow.this.DrawTracePickPanel(0);  }
/*      */           public void keyReleased(KeyEvent e) { PickingWindow.fileTree.grabFocus(); PickingWindow.fileTree.setFocusable(true); PickingWindow.this.TracePanel.setFocusable(false); } }
/*      */       ); fileTree.addTreeSelectionListener(this); fileTreeScrollPane = new JScrollPane(fileTree); fileTreeScrollPane.setPreferredSize(new Dimension(this.FilePanel.getWidth() - 20, this.FilePanel.getHeight() - 40)); fileTreeScrollPane.setBorder((Border)null); this.FilePanel.removeAll(); this.FilePanel.add(fileTreeScrollPane, "Center"); this.FilePanel.updateUI(); }
/*      */   static volatile String currentFilePath = ""; String clipParentFilePath; int drawCount; readMAT MATInfo; CubicInterpolator.Method LINEAR; volatile boolean aDown; volatile boolean ctrlDown; volatile boolean shiftDown; volatile boolean returnDown; volatile boolean escDown; JFreeChart xylineChart; XYPlot tracePlot; volatile double[][] pickP; volatile double[][] pickS; volatile double[][] pickSS; volatile double[][] pickR1; volatile double[][] pickR2; volatile double[][] pickR3; volatile double[][] computeP; volatile double[][] computeS; volatile double[][] computeSS; volatile double[][] computeR1; volatile double[][] computeR2; volatile double[][] computeR3; volatile double[][] prevP; volatile double[][] prevS; volatile double[][] prevSS; volatile double[][] prevR1; volatile double[][] prevR2; volatile double[][] prevR3; String prevWaveType; final Color lineP; final Color lineS; final Color lineSS; final Color lineR1; final Color lineR2; final Color lineR3; final Color lineInterp; final Color markerP; final Color markerS; final Color markerSS; final Color markerR1; final Color markerR2; final Color markerR3; final Color markerInterp; List<XYLineAnnotation> pAnnot; List<XYLineAnnotation> sAnnot; List<XYLineAnnotation> ssAnnot; List<XYLineAnnotation> r1Annot; List<XYLineAnnotation> r2Annot; List<XYLineAnnotation> r3Annot; List<XYLineAnnotation> autoEdgesAnnotation; List<XYTextAnnotation> pShape; List<XYTextAnnotation> sShape; List<XYTextAnnotation> ssShape; List<XYTextAnnotation> r1Shape; List<XYTextAnnotation> r2Shape; List<XYTextAnnotation> r3Shape;
/* 7029 */   public void keyPressed(KeyEvent e) { if (e.getKeyCode() == 65 && this.AutoPickFlag) {
/* 7030 */       this.aDown = true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7035 */     if (e.getKeyCode() == 27) {
/*      */       
/* 7037 */       System.out.println(this.AutoPickFlag);
/* 7038 */       if (this.TrimFileMode) {
/*      */         
/* 7040 */         trimModetraceButtonsState(true);
/* 7041 */         this.SoftwareStatusLabel.setText("Ready");
/* 7042 */         this.TrimFileMode = false;
/* 7043 */         startTimer();
/* 7044 */         this.chartPanel.setCursor(this.defaultCursor);
/* 7045 */         this.chartPanel.setMouseZoomable(true);
/* 7046 */         this.chartPanel.setFillZoomRectangle(false);
/* 7047 */         this.chartPanel.setZoomOutlinePaint(new Color(0.0F, 0.0F, 0.0F, 0.0F));
/*      */       } else {
/*      */         
/* 7050 */         clearInterp();
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7067 */     if (e.isControlDown())
/*      */     {
/* 7069 */       this.ctrlDown = true;
/*      */     }
/*      */     
/* 7072 */     if (e.isShiftDown()) {
/* 7073 */       this.shiftDown = true;
/*      */     }
/* 7075 */     if (e.getKeyCode() == 10)
/* 7076 */     { System.out.println("Enter pressed!");
/* 7077 */       this.returnDown = true; }  }
/*      */   List<XYTextAnnotation> InterpShape;
/*      */   List<XYTextAnnotation> AutoPickValues; List<Float> toInterpPX; List<Float> toInterpPY; JTable table; public synchronized void saveFileDialog() { if (this.saveCheck) { int response = JOptionPane.showConfirmDialog(null, "Save File?", "Save..", 0, 3); if (response == 0) { this.SoftwareStatusLabel.setText("Saving.."); saveFile(currentFilePath); } else if (response == 1) { this.saveCheck = false; } else if (response == -1) { return; }  }  } private synchronized void DrawTracePickPanel(int option) { if (this.MasterClippingMode) this.drawCount++;  saveFileDialog(); this.TrimFileMode = false; if (this.MasterClippingMode && this.drawCount > 1) { File tempCurrentFile = new File(currentFilePath); File tempCurrentFolder = tempCurrentFile.getParentFile(); File parentFolder = new File(tempCurrentFolder.getAbsolutePath(), "02_Parent File"); if (!parentFolder.exists()) parentFolder.mkdir();  File newFilePath = new File(parentFolder.getPath(), tempCurrentFile.getName()); try { Files.move(tempCurrentFile.toPath(), newFilePath.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING }); } catch (Exception exception) {} }  DefaultMutableTreeNode node = (DefaultMutableTreeNode)fileTree.getLastSelectedPathComponent(); if ((((node == null) ? 1 : 0) | (node.toString().endsWith("mat") ? 0 : 1)) != 0) return;  if (option == -1) { DefaultMutableTreeNode PrevNode = node.getPreviousSibling(); if (PrevNode == null) return;  if (!PrevNode.isLeaf()) return;  if (!PrevNode.toString().endsWith("mat")) return;  TreePath previous = getPath(PrevNode); currentFilePath = createNodePath(node.getPreviousSibling().getPath()); fileTree.clearSelection(); fileTree.setSelectionPath(previous); fileTree.scrollPathToVisible(previous); } else if (option == 0) { currentFilePath = createFilePath(fileTree.getSelectionPath()); } else if (option == 1) { DefaultMutableTreeNode NextNode = node.getNextSibling(); if (NextNode == null) return;  if (!NextNode.isLeaf()) return;  if (!NextNode.toString().endsWith("mat")) return;  TreePath Next = getPath(NextNode); currentFilePath = createNodePath(node.getNextSibling().getPath()); fileTree.clearSelection(); fileTree.setSelectionPath(Next); fileTree.scrollPathToVisible(Next); }  if (option == 0) currentFilePath = createFilePath(fileTree.getSelectionPath());  if (currentFilePath.equalsIgnoreCase(this.currentSavingFile) && this.savingPreviousFile) { JOptionPane.showMessageDialog(new JFrame("Error"), "Saving Previous file. Attempt again sometime."); return; }  emptyTracePickPanels(); resetTimer(); this.SoftwareStatusLabel.setText("Plotting"); SwingUtilities.invokeLater(new Runnable() {
/*      */           public void run() { PickingWindow.this.plotSeismogram(); PickingWindow.this.SoftwareStatusLabel.setText("Ready"); }
/*      */         }); generatePropertiesFile(); } static int count = 0; ChartPanel chartPanel; readMAT rawMATInfo; JTree headerTree; double InitialYAxisLimits; ArrayList<Long> longArray; JFreeChart xylineChartAuto; XYPlot tracePlotAuto; ChartPanel chartPanelAuto; public synchronized void plotSeismogram() { try { this.MATInfo = new readMAT(currentFilePath); if (!this.MATInfo.errorFree) { JOptionPane.showMessageDialog(new JFrame("File Read Error"), "MAT File Formatting Issue."); return; }  if (this.stackMode) if ((((this.MATInfo.noRec != this.revMappingArray.length) ? 1 : 0) | ((this.MATInfo.noRec != this.stackReceiverCountInput) ? 1 : 0)) != 0) { this.stackMode = false; JOptionPane.showMessageDialog(new JFrame(""), "Receiver Input in Stack Mode Flawed or some programming error. Stacking mode off"); }   if (this.stackMode) { this.MATInfo.noRec = this.mappingArray.length; this.MATInfo.noTrace = (this.mappingArray.length * 3); double[][] traceData = new double[(this.MATInfo.getTraceData()).length][this.mappingArray.length * 3]; for (int i1 = 0; i1 < traceData.length; i1++) { for (int i3 = 0; i3 < (traceData[0]).length; i3++) traceData[i1][i3] = this.MATInfo.getTraceData()[i1][3 * this.mappingArray[i3 / 3] + Math.floorMod(i3, 3)];  }  this.MATInfo.setTraceData(traceData); double[][] pickOutP = new double[this.mappingArray.length][2]; double[][] pickOutS = new double[this.mappingArray.length][2]; double[][] pickOutSS = new double[this.mappingArray.length][2]; double[][] pickOutR1 = new double[this.mappingArray.length][2]; double[][] pickOutR2 = new double[this.mappingArray.length][2]; double[][] pickOutR3 = new double[this.mappingArray.length][2]; double[][] computeP = new double[this.mappingArray.length][2]; double[][] computeS = new double[this.mappingArray.length][2]; double[][] computeSS = new double[this.mappingArray.length][2]; int i2; for (i2 = 0; i2 < this.mappingArray.length; i2++) { pickOutP[i2][0] = (i2 + 1); pickOutP[i2][1] = this.MATInfo.pickOutP[this.mappingArray[i2]][1]; pickOutS[i2][0] = (i2 + 1); pickOutS[i2][1] = this.MATInfo.pickOutS[this.mappingArray[i2]][1]; pickOutSS[i2][0] = (i2 + 1); pickOutSS[i2][1] = this.MATInfo.pickOutSS[this.mappingArray[i2]][1]; pickOutR1[i2][0] = (i2 + 1); pickOutR1[i2][1] = this.MATInfo.pickOutR1[this.mappingArray[i2]][1]; pickOutR2[i2][0] = (i2 + 1); pickOutR2[i2][1] = this.MATInfo.pickOutR2[this.mappingArray[i2]][1]; pickOutR3[i2][0] = (i2 + 1); pickOutR3[i2][1] = this.MATInfo.pickOutR3[this.mappingArray[i2]][1]; computeP[i2][0] = (i2 + 1); computeP[i2][1] = this.MATInfo.computeP[this.mappingArray[i2]][1]; computeS[i2][0] = (i2 + 1); computeS[i2][1] = this.MATInfo.computeS[this.mappingArray[i2]][1]; computeSS[i2][0] = (i2 + 1); computeSS[i2][1] = this.MATInfo.computeSS[this.mappingArray[i2]][1]; }  this.MATInfo.pickOutP = new double[this.mappingArray.length][2]; this.MATInfo.pickOutS = new double[this.mappingArray.length][2]; this.MATInfo.pickOutSS = new double[this.mappingArray.length][2]; this.MATInfo.pickOutR1 = new double[this.mappingArray.length][2]; this.MATInfo.pickOutR2 = new double[this.mappingArray.length][2]; this.MATInfo.pickOutR3 = new double[this.mappingArray.length][2]; this.MATInfo.computeP = new double[this.mappingArray.length][2]; this.MATInfo.computeS = new double[this.mappingArray.length][2]; this.MATInfo.computeSS = new double[this.mappingArray.length][2]; for (i2 = 0; i2 < this.mappingArray.length; i2++) { this.MATInfo.pickOutP[i2][0] = (i2 + 1); this.MATInfo.pickOutP[i2][1] = pickOutP[i2][1]; this.MATInfo.pickOutS[i2][0] = (i2 + 1); this.MATInfo.pickOutS[i2][1] = pickOutS[i2][1]; this.MATInfo.pickOutSS[i2][0] = (i2 + 1); this.MATInfo.pickOutSS[i2][1] = pickOutSS[i2][1]; this.MATInfo.pickOutR1[i2][0] = (i2 + 1); this.MATInfo.pickOutR1[i2][1] = pickOutR1[i2][1]; this.MATInfo.pickOutR2[i2][0] = (i2 + 1); this.MATInfo.pickOutR2[i2][1] = pickOutR2[i2][1]; this.MATInfo.pickOutR3[i2][0] = (i2 + 1); this.MATInfo.pickOutR3[i2][1] = pickOutR3[i2][1]; this.MATInfo.computeP[i2][0] = (i2 + 1); this.MATInfo.computeP[i2][1] = computeP[i2][1]; this.MATInfo.computeS[i2][0] = (i2 + 1); this.MATInfo.computeS[i2][1] = computeS[i2][1]; this.MATInfo.computeSS[i2][0] = (i2 + 1); this.MATInfo.computeSS[i2][1] = computeSS[i2][1]; }  }  RemoveNaN rm1 = new RemoveNaN(this.MATInfo.getTraceData()); this.MATInfo.setTraceData(rm1.getTraceData()); if (this.pickingAuth.getCurrentDesign() != null) { SortTraces srtTrc = new SortTraces(this.MATInfo.getTraceData(), this.pickingAuth.getCurrentDesign().getTracePolarity(), this.pickingAuth.getCurrentDesign().getTraceOrder()); this.MATInfo.setTraceData(srtTrc.getTraceData()); if ((this.pickingAuth.getCurrentDesign().getRotationMatrix()[0][0]).length > 0) { RotTraces rotTrc = new RotTraces(this.MATInfo.getTraceData(), this.pickingAuth.getCurrentDesign().getRotationMatrix(), this.MATInfo.noRec); this.MATInfo.setTraceData(rotTrc.getTraceData()); }  }  if (this.filterSettings.DetrendFlag) { Detrend detrendTrc = new Detrend(this.MATInfo.getTraceData()); this.MATInfo.setTraceData(detrendTrc.getTraceData()); }  if (this.filterSettings.IntFlag) { integrateTrace intTrc = new integrateTrace(this.MATInfo.getTraceData(), this.MATInfo.timeSample * 0.001D); this.MATInfo.setTraceData(intTrc.getTraceData()); }  if (this.filterSettings.BPFlag) { BandPassTrace filtTrc = new BandPassTrace(this.MATInfo.getTraceData(), this.filterSettings.BPLow, this.filterSettings.BPHigh, this.filterSettings.BPEdge, 1.0E-5D, this.MATInfo.timeSample * 0.001D); this.MATInfo.setTraceData(filtTrc.getTraceData()); this.MATInfo.setTraceData(filtTrc.getTraceData()); }  if (this.filterSettings.LPFlag) { LinearityFilter filtTrc = new LinearityFilter(this.MATInfo.getTraceData(), this.filterSettings); this.MATInfo.setTraceData(filtTrc.getTraceData()); }  if (this.renormalizeFlag) { normTrace3C normTrc = new normTrace3C(this.MATInfo.getTraceData(), this.lowerLimitNorm, this.upperLimitNorm); this.MATInfo.setTraceData(normTrc.getTraceData()); this.renormalizeFlag = false; } else { normTrace3C normTrc = new normTrace3C(this.MATInfo.getTraceData(), this.DISPLAY_MODE, this.NORMALIZATION_MODE); this.MATInfo.setTraceData(normTrc.getTraceData()); }  String wellNoRecString = this.filterSettings.noOfRecsString; if ((wellNoRecString.contentEquals("[]") | wellNoRecString.contentEquals("[,]") | wellNoRecString.contentEquals("")) != 0) { this.wellNoRec = new double[] { this.MATInfo.noRec }; } else { this.wellNoRec = textToArray(wellNoRecString); }  int noWells = 1; if (this.filterSettings.noOfWellsString != "" || this.filterSettings.noOfWellsString != "0") noWells = Integer.parseInt(this.filterSettings.noOfWellsString);  if (this.wellNoRec.length != noWells) { JOptionPane.showMessageDialog(this.MasterFrame, "Length of receiver array not equal to no. of wells in settings."); return; }  int noRecSum = 0; int m; for (m = 0; m < this.wellNoRec.length; m++) noRecSum = (int)(noRecSum + this.wellNoRec[m]);  if (noRecSum != this.MATInfo.noRec) { JOptionPane.showMessageDialog(new JFrame("Well Input Error"), "No of Receivers in well array not equal to MAT File."); return; }  this.plotLineY = new double[this.wellNoRec.length]; this.plotLineY[0] = this.wellNoRec[0]; for (m = 1; m < this.wellNoRec.length; m++) { for (int i1 = 0; i1 <= m; i1++) this.plotLineY[m] = this.plotLineY[m] + this.wellNoRec[i1];  }  for (m = 0; m < this.plotLineY.length; m++) this.plotLineY[m] = this.plotLineY[m] + 0.5D;  } catch (Exception e) { System.out.println(e); JOptionPane.showMessageDialog(new JFrame("File Read Error"), "File Content or File Location Issue."); return; }  File x = new File(currentFilePath); String chartTitle = "Plot from File : " + x.getName(); if (this.pickingAuth.getCurrentDesign() != null) chartTitle = String.valueOf(chartTitle) + ", Design - " + this.pickingAuth.getCurrentDesign().getName();  if (this.pickingAuth.getCurrentStage() != null) chartTitle = String.valueOf(chartTitle) + ", " + this.pickingAuth.getCurrentStage().getWellName() + " Stage " + this.pickingAuth.getCurrentStage().getStageName();  if (this.DISPLAY_MODE == "Wiggle") { this.xylineChart = ChartFactory.createXYLineChart("", "Time (ms)", "Receiver No.", createDataset(this.MATInfo), PlotOrientation.VERTICAL, false, false, false); this.xylineChart.setBackgroundPaint(Color.white); this.xylineChart.setTitle(new TextTitle(chartTitle, new Font("Avenir Book", 1, 14))); this.xylineChart.getTitle().setExpandToFitSpace(false); this.tracePlot = this.xylineChart.getXYPlot(); this.tracePlot.setBackgroundPaint(Color.white); this.tracePlot.setDomainGridlinePaint(new Color(100, 100, 100)); this.tracePlot.setRangeGridlinePaint(new Color(100, 100, 100)); this.tracePlot.setDomainPannable(true); this.tracePlot.setRangePannable(true); this.tracePlot.setDomainCrosshairVisible(false); this.tracePlot.setDomainCrosshairLockedOnData(true); this.tracePlot.setRangeCrosshairVisible(false); this.tracePlot.setRangeCrosshairLockedOnData(true); NumberAxis xAxis = (NumberAxis)this.tracePlot.getDomainAxis(); xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); xAxis.setLabelFont(new Font("Avenir Book", 1, 12)); xAxis.setTickLabelFont(new Font("Avenir Book", 0, 10)); xAxis.setAutoRange(true); NumberAxis yAxis = (NumberAxis)this.tracePlot.getRangeAxis(); yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); yAxis.setInverted(true); yAxis.setAutoRangeIncludesZero(false); yAxis.setLabelFont(new Font("Avenir Book", 1, 12)); yAxis.setTickLabelFont(new Font("Avenir Book", 0, 10)); yAxis.setAutoRange(true); XYItemRenderer renderer = this.tracePlot.getRenderer(); for (int m = 0; m < this.tracePlot.getSeriesCount(); m += 3) { renderer.setSeriesPaint(m, new Color(255, 96, 96)); renderer.setSeriesPaint(m + 1, Color.LIGHT_GRAY); renderer.setSeriesPaint(m + 2, new Color(64, 64, 255)); }  } else { DefaultXYZDataset dataset = new DefaultXYZDataset(); double[][] traceData = this.MATInfo.getTraceData(); double minTrace = 0.0D, maxTrace = 0.0D; for (int m = 1; m <= this.MATInfo.noRec; m++) { double[] xvalues = new double[traceData.length]; double[] yvalues = new double[traceData.length]; double[] zvalues = new double[traceData.length]; for (int i2 = 0; i2 < traceData.length; i2++) { xvalues[i2] = i2 * this.MATInfo.timeSample; yvalues[i2] = m; zvalues[i2] = (traceData[i2][3 * m - 3] + traceData[i2][3 * m - 2] + traceData[i2][3 * m - 1]) / 3.0D; }  minTrace = getMin(zvalues, minTrace); maxTrace = getMax(zvalues, maxTrace); dataset.addSeries("Series" + Integer.toString(m + 1), new double[][] { xvalues, yvalues, zvalues }); }  NumberAxis xAxis = new NumberAxis("Time (ms)"); xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); xAxis.setLabelFont(new Font("Avenir Book", 1, 12)); xAxis.setTickLabelFont(new Font("Avenir Book", 0, 10)); xAxis.setAutoRange(true); NumberAxis yAxis = new NumberAxis("Receiver No."); yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); yAxis.setInverted(true); yAxis.setAutoRangeIncludesZero(false); yAxis.setLabelFont(new Font("Avenir Book", 1, 12)); yAxis.setTickLabelFont(new Font("Avenir Book", 0, 10)); yAxis.setAutoRange(true); NumberAxis valueAxis1 = new NumberAxis("Marker"); valueAxis1.setLowerMargin(0.0D); valueAxis1.setUpperMargin(0.0D); valueAxis1.setVisible(false); LookupPaintScale paintScale = new LookupPaintScale(-1.0D, 1.0D, Color.black); for (int i1 = 0; i1 <= 51; i1++) { double dataPoint = minTrace + i1 * (maxTrace - minTrace) / 51.0D; paintScale.add(dataPoint, new Color(4 * i1, 4 * i1, 4 * i1)); }  this.tracePlot = new XYPlot((XYDataset)dataset, (ValueAxis)xAxis, (ValueAxis)yAxis, (XYItemRenderer)new XYBlockRenderer()); ((XYBlockRenderer)this.tracePlot.getRenderer()).setPaintScale((PaintScale)paintScale); this.tracePlot.setDomainCrosshairVisible(true); this.tracePlot.setDomainCrosshairLockedOnData(true); this.tracePlot.setRangeCrosshairVisible(true); this.tracePlot.setRangeCrosshairLockedOnData(true); this.xylineChart = new JFreeChart(null, null, (Plot)this.tracePlot, false); this.xylineChart.setTitle(new TextTitle(chartTitle, new Font("Avenir Book", 1, 14))); this.xylineChart.getTitle().setExpandToFitSpace(false); this.tracePlot.setBackgroundPaint(Color.white); this.tracePlot.setDomainGridlinePaint(new Color(100, 100, 100)); this.tracePlot.setRangeGridlinePaint(new Color(100, 100, 100)); this.xylineChart.setBackgroundPaint(Color.white); }  this.pAnnot = new ArrayList<>(); this.sAnnot = new ArrayList<>(); this.ssAnnot = new ArrayList<>(); this.r1Annot = new ArrayList<>(); this.r2Annot = new ArrayList<>(); this.r3Annot = new ArrayList<>(); this.pShape = new ArrayList<>(); this.sShape = new ArrayList<>(); this.ssShape = new ArrayList<>(); this.r1Shape = new ArrayList<>(); this.r2Shape = new ArrayList<>(); this.r3Shape = new ArrayList<>(); this.pickP = this.MATInfo.pickOutP; this.pickS = this.MATInfo.pickOutS; this.pickSS = this.MATInfo.pickOutSS; this.pickR1 = this.MATInfo.pickOutR1; this.pickR2 = this.MATInfo.pickOutR2; this.pickR3 = this.MATInfo.pickOutR3; this.computeP = this.MATInfo.computeP; this.computeS = this.MATInfo.computeS; this.computeSS = this.MATInfo.computeSS; this.computeR1 = this.MATInfo.computeR1; this.computeR2 = this.MATInfo.computeR2; this.computeR3 = this.MATInfo.computeR3; this.toInterpPX = new ArrayList<>(); this.toInterpPY = new ArrayList<>(); for (int i = 0; i < this.plotLineY.length; i++) this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(0.0D, this.plotLineY[i], this.MATInfo.noSample * this.MATInfo.timeSample, this.plotLineY[i], new BasicStroke(2.0F, 1, 1), Color.black));  String[] toolNamesArray = null; toolNamesArray = this.filterSettings.toolstringNames.replace("[", "").replace("]", "").split(","); float y = 0.0F, previous = 0.0F; for (int n = 0; n < toolNamesArray.length; n++) { y = (float)(0.5D * this.wellNoRec[n]) + previous; previous = (float)(previous + this.wellNoRec[n]); XYTextAnnotation tempTemp = new XYTextAnnotation(toolNamesArray[n], 1.01D * this.MATInfo.noSample * this.MATInfo.timeSample, y); tempTemp.setRotationAngle(1.5707963267948966D); tempTemp.setTextAnchor(TextAnchor.BASELINE_CENTER); tempTemp.setFont(new Font("Avenir Book", 1, 16)); tempTemp.setPaint(this.markerSS); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)tempTemp); }  if (!this.stackMode && this.MATInfo.edgesOut.length > 0 && this.AutoPickFlag && this.showAutoPicks) { this.autoEdgesAnnotation = new ArrayList<>(); for (int m = 0; m < (this.MATInfo.edgesOut[0]).length; m++) { Color edgeColor = new Color((int)(Math.random() * 1.6777216E7D)); for (int i1 = 0; i1 < this.MATInfo.edgesOut.length - 1; i1++) { if (this.MATInfo.edgesOut[i1][m] > 0.0D && this.MATInfo.edgesOut[i1 + 1][m] > 0.0D) { XYLineAnnotation tempAnnot = new XYLineAnnotation(this.MATInfo.edgesOut[i1][m], (i1 + 1), this.MATInfo.edgesOut[i1 + 1][m], (i1 + 2), new BasicStroke(5.0F, 2, 0), edgeColor); tempAnnot.setToolTipText(Integer.toString(m)); tempAnnot.setURL(Integer.toString(m)); this.autoEdgesAnnotation.add(tempAnnot); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.autoEdgesAnnotation.get(this.autoEdgesAnnotation.size() - 1)); }  }  }  }  for (int j = 0; j < this.pickP.length; j++) { if (j < this.pickP.length - 1) { if (!this.HideArrivalsFlag) { this.pAnnot.add(j, new XYLineAnnotation(this.pickP[j][1], this.pickP[j][0], this.pickP[j + 1][1], this.pickP[j + 1][0], new BasicStroke(1.5F, 1, 1), this.lineP)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.pAnnot.get(j)); this.sAnnot.add(j, new XYLineAnnotation(this.pickS[j][1], this.pickS[j][0], this.pickS[j + 1][1], this.pickS[j + 1][0], new BasicStroke(1.5F, 1, 1), this.lineS)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.sAnnot.get(j)); this.ssAnnot.add(j, new XYLineAnnotation(this.pickSS[j][1], this.pickSS[j][0], this.pickSS[j + 1][1], this.pickSS[j + 1][0], new BasicStroke(1.5F, 1, 1), this.lineSS)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.ssAnnot.get(j)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.computeP[j][1], this.computeP[j][0], this.computeP[j + 1][1], this.computeP[j + 1][0], new BasicStroke(0.75F, 1, 1, 0.5F, new float[] { 3.0F, 3.0F }, 0.0F), new Color(100, 0, 0))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.computeS[j][1], this.computeS[j][0], this.computeS[j + 1][1], this.computeS[j + 1][0], new BasicStroke(0.75F, 1, 1, 0.5F, new float[] { 3.0F, 3.0F }, 0.0F), new Color(100, 0, 0))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.computeSS[j][1], this.computeSS[j][0], this.computeSS[j + 1][1], this.computeSS[j + 1][0], new BasicStroke(0.75F, 1, 1, 0.5F, new float[] { 3.0F, 3.0F }, 0.0F), new Color(100, 0, 0))); }  if (!this.HideReflectionsFlag) { this.r1Annot.add(j, new XYLineAnnotation(this.pickR1[j][1], this.pickR1[j][0], this.pickR1[j + 1][1], this.pickR1[j + 1][0], new BasicStroke(1.5F, 1, 1), this.lineR1)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r1Annot.get(j)); this.r2Annot.add(j, new XYLineAnnotation(this.pickR2[j][1], this.pickR2[j][0], this.pickR2[j + 1][1], this.pickR2[j + 1][0], new BasicStroke(1.5F, 1, 1), this.lineR2)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r2Annot.get(j)); this.r3Annot.add(j, new XYLineAnnotation(this.pickR3[j][1], this.pickR3[j][0], this.pickR3[j + 1][1], this.pickR3[j + 1][0], new BasicStroke(1.5F, 1, 1), this.lineR3)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r3Annot.get(j)); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.computeR1[j][1], this.computeR1[j][0], this.computeR1[j + 1][1], this.computeR1[j + 1][0], new BasicStroke(0.75F, 1, 1, 0.5F, new float[] { 3.0F, 3.0F }, 0.0F), new Color(100, 0, 0))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.computeR2[j][1], this.computeR2[j][0], this.computeR2[j + 1][1], this.computeR2[j + 1][0], new BasicStroke(0.75F, 1, 1, 0.5F, new float[] { 3.0F, 3.0F }, 0.0F), new Color(100, 0, 0))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.computeR3[j][1], this.computeR3[j][0], this.computeR3[j + 1][1], this.computeR3[j + 1][0], new BasicStroke(0.75F, 1, 1, 0.5F, new float[] { 3.0F, 3.0F }, 0.0F), new Color(100, 0, 0))); }  if (this.RepickingMode) { if (!this.HideArrivalsFlag) { this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.pickP[j][1], this.pickP[j][0], this.pickP[j + 1][1], this.pickP[j + 1][0], new BasicStroke(0.75F, 1, 1), new Color(100, 100, 100))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.pickS[j][1], this.pickS[j][0], this.pickS[j + 1][1], this.pickS[j + 1][0], new BasicStroke(0.75F, 1, 1), new Color(100, 100, 100))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.pickSS[j][1], this.pickSS[j][0], this.pickSS[j + 1][1], this.pickSS[j + 1][0], new BasicStroke(0.75F, 1, 1), new Color(100, 100, 100))); }  if (!this.HideReflectionsFlag) { this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.pickR1[j][1], this.pickR1[j][0], this.pickR1[j + 1][1], this.pickR1[j + 1][0], new BasicStroke(0.75F, 1, 1), new Color(100, 100, 100))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.pickR2[j][1], this.pickR2[j][0], this.pickR2[j + 1][1], this.pickR2[j + 1][0], new BasicStroke(0.75F, 1, 1), new Color(100, 100, 100))); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)new XYLineAnnotation(this.pickR3[j][1], this.pickR3[j][0], this.pickR3[j + 1][1], this.pickR3[j + 1][0], new BasicStroke(0.75F, 1, 1), new Color(100, 100, 100))); }  }  }  if (!this.HideArrivalsFlag) { XYTextAnnotation temp = new XYTextAnnotation("+", this.pickP[j][1], this.pickP[j][0]); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerP); this.pShape.add(j, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.pShape.get(j)); temp = new XYTextAnnotation("x", this.pickS[j][1], this.pickS[j][0]); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerS); this.sShape.add(j, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.sShape.get(j)); temp = new XYTextAnnotation("o", this.pickSS[j][1], this.pickSS[j][0]); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerSS); this.ssShape.add(j, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.ssShape.get(j)); }  if (!this.HideReflectionsFlag) { XYTextAnnotation temp = new XYTextAnnotation("+", this.pickR1[j][1], this.pickR1[j][0]); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerR1); this.r1Shape.add(j, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r1Shape.get(j)); temp = new XYTextAnnotation("x", this.pickR2[j][1], this.pickR2[j][0]); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerR2); this.r2Shape.add(j, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r2Shape.get(j)); temp = new XYTextAnnotation("o", this.pickR3[j][1], this.pickR3[j][0]); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerR3); this.r3Shape.add(j, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r3Shape.get(j)); }  }  this.chartPanel = new ChartPanel(this.xylineChart, false); this.chartPanel.setMaximumDrawHeight(8000); this.chartPanel.setMaximumDrawWidth(8000); this.chartPanel.setDisplayToolTips(false); this.chartPanel.setPopupMenu(null); this.chartPanel.setPreferredSize(new Dimension(this.TracePanel.getWidth() - 20, this.TracePanel.getHeight() - 40)); this.chartPanel.setBackground(Color.white); this.chartPanel.setDomainZoomable(true); this.chartPanel.setRangeZoomable(true); this.chartPanel.setFocusable(true); this.chartPanel.setVisible(true); this.chartPanel.setAutoscrolls(true); this.chartPanel.setFillZoomRectangle(false); this.chartPanel.setZoomOutlinePaint(new Color(0.0F, 0.0F, 0.0F, 0.0F)); this.chartPanel.addMouseWheelListener(this); this.chartPanel.setZoomInFactor(0.9D); this.chartPanel.setZoomOutFactor(1.1D); this.chartPanel.getChart().addProgressListener(this); this.chartPanel.addChartMouseListener(this); this.chartPanel.updateUI(); this.chartPanel.addKeyListener(this); this.TracePanel.addKeyListener(this); this.TracePanel.add((Component)this.chartPanel); this.TracePanel.updateUI(); this.InitialYAxisLimits = this.chartPanel.getChart().getXYPlot().getRangeAxis().getUpperBound() - this.chartPanel.getChart().getXYPlot().getRangeAxis().getLowerBound(); NumberFormat nf = NumberFormat.getInstance(); nf.setMaximumFractionDigits(2); String[] columnNames = { "Rec #", "P - wave", "S - wave", "SS - wave", "R1 - wave", "R2 - wave", "R3 - wave" }; String[][] dataValues = new String[this.pickP.length][7]; for (int k = 0; k < this.pickP.length; k++) { dataValues[k][0] = String.format("%.0f", new Object[] { Double.valueOf(this.pickP[k][0]) }); dataValues[k][1] = String.format("%.2f", new Object[] { Double.valueOf(this.pickP[k][1]) }); dataValues[k][2] = String.format("%.2f", new Object[] { Double.valueOf(this.pickS[k][1]) }); dataValues[k][3] = String.format("%.2f", new Object[] { Double.valueOf(this.pickSS[k][1]) }); dataValues[k][4] = String.format("%.2f", new Object[] { Double.valueOf(this.pickR1[k][1]) }); dataValues[k][5] = String.format("%.2f", new Object[] { Double.valueOf(this.pickR2[k][1]) }); dataValues[k][6] = String.format("%.2f", new Object[] { Double.valueOf(this.pickR3[k][1]) }); }  this.table = new JTable((Object[][])dataValues, (Object[])columnNames); this.table.getTableHeader().setFont(new Font("Avenir Book", 1, 12)); this.table.getTableHeader().setAlignmentX(0.0F); this.table.setAutoResizeMode(0); this.table.setAutoscrolls(false); this.table.setDragEnabled(false); this.table.setShowGrid(true); this.table.setGridColor(Color.LIGHT_GRAY); this.table.setFont(new Font("Avenir Book", 0, 12)); this.table.setCellSelectionEnabled(false); this.table.setEnabled(false); this.table.setVisible(true); JScrollPane scrollpane = new JScrollPane(this.table); scrollpane.setPreferredSize(new Dimension(this.TimePicksPanel.getWidth() - 4, this.TimePicksPanel.getHeight() - 4)); DefaultTableCellRenderer tableHeaderRenderer = (DefaultTableCellRenderer)this.table.getTableHeader().getDefaultRenderer(); tableHeaderRenderer.setHorizontalAlignment(0); DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer(); int align = 0; tableRenderer.setHorizontalAlignment(align); TableColumnModel columnModel = this.table.getColumnModel(); this.TimePicksPanel.add(scrollpane); for (int col = 0; col < columnModel.getColumnCount(); col++) { columnModel.getColumn(col).setCellRenderer(tableRenderer); columnModel.getColumn(col).setResizable(true); columnModel.getColumn(col).setPreferredWidth((this.TimePicksPanel.getWidth() - 20) / 4); columnModel.getColumn(col).setMaxWidth((this.TimePicksPanel.getWidth() - 10) / 4); columnModel.getColumn(col).setMinWidth((this.TimePicksPanel.getWidth() - 30) / 4); }  this.TimePicksPanel.setOpaque(true); this.TimePicksPanel.updateUI(); createHeaderTree(this.MATInfo.FileArray); this.defaultCursor = this.chartPanel.getCursor(); traceButtonsState(true); startTimer(); this.mouseWheelCount = 0; selectComponents(); this.prevWaveType = " "; this.startingClipPoints = new ArrayList<>(); this.endingClipPoints = new ArrayList<>(); this.TracePanel.setFocusable(true); this.chartPanel.setFocusable(true); this.chartPanel.grabFocus(); SwingUtilities.invokeLater(new Runnable() {
/*      */           public void run() { if (PickingWindow.this.MasterClippingMode) { PickingWindow.this.TrimFileButton.doClick(); PickingWindow.updateTree(); }  }
/*      */         }); } private void createHeaderTree(Struct[] fileArray) { DefaultMutableTreeNode root = new DefaultMutableTreeNode(); for (int i = 0; i < fileArray.length; i++) { if (i == 0) { getHeaderList(root, (Array)fileArray[i], "headerData"); } else { getHeaderList(root, (Array)fileArray[i], "invBHS"); }  }  this.headerTree = new JTree(root); DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)this.headerTree.getCellRenderer(); renderer.setLeafIcon(null); renderer.setClosedIcon(null); renderer.setOpenIcon(null); this.headerTree.setRootVisible(false); this.headerTree.setBackground(Color.white); this.headerTree.setScrollsOnExpand(true); expandAll(this.headerTree); JScrollPane headerTreeScrollPane = new JScrollPane(this.headerTree); headerTreeScrollPane.setPreferredSize(new Dimension(this.FileHeaderPanel.getWidth() - 5, this.FileHeaderPanel.getHeight() - 10)); headerTreeScrollPane.setBorder((Border)null); this.FileHeaderPanel.removeAll(); this.FileHeaderPanel.add(headerTreeScrollPane, "Center"); } private void getHeaderList(DefaultMutableTreeNode root, Array headerData, String name) { if (headerData.getType() == MatlabType.Structure) { Struct temp = (Struct)headerData; DefaultMutableTreeNode child = new DefaultMutableTreeNode(name); root.add(child); Object[] z = temp.getFieldNames().toArray(); for (int j = 0; j < z.length; j++) getHeaderList(child, temp.get((String)z[j]), (String)z[j]);  } else { String tree = headerData.toString(); tree = tree.replace('@', ' '); tree = tree.replace(name, ""); root.add(new DefaultMutableTreeNode(String.valueOf(name) + '\t' + tree)); }  } private double[][] getDoubleArray(Matrix matrix) { double[][] output = new double[matrix.getNumRows()][matrix.getNumCols()]; for (int i = 0; i < output.length; i++) { for (int j = 0; j < (output[0]).length; j++) output[i][j] = matrix.getDouble(i, j);  }  return output; } public void expandAll(JTree tree) { TreeNode root = (TreeNode)tree.getModel().getRoot(); expandAll(tree, new TreePath(root)); } private void expandAll(JTree tree, TreePath parent) { TreeNode node = (TreeNode)parent.getLastPathComponent(); if (node.getChildCount() >= 0) for (Enumeration<? extends TreeNode> e = node.children(); e.hasMoreElements(); ) { TreeNode n = e.nextElement(); TreePath path = parent.pathByAddingChild(n); expandAll(tree, path); }   tree.expandPath(parent); } private void selectComponents() { this.c1.setSelected(true); this.Comp1.setSelected(true); this.c2.setSelected(true); this.Comp2.setSelected(true); this.c3.setSelected(true); this.Comp3.setSelected(true); } public XYDataset createDataset(readMAT MATContent) { double[][] nrmtraceData = transposeMatrix(MATContent.getTraceData()); double[] sampleTimeArray = new double[(nrmtraceData[0]).length]; for (int k = 0; k < sampleTimeArray.length; k++) sampleTimeArray[k] = k * MATContent.timeSample;  DefaultXYDataset dataset = new DefaultXYDataset(); for (int j = 0; j < nrmtraceData.length; j++) { double[][] data = { sampleTimeArray, nrmtraceData[j] }; dataset.addSeries("Series " + Integer.toString(j), data); }  return (XYDataset)dataset; } private double[][] transposeMatrix(double[][] varName) { double[][] returnVar = new double[(varName[0]).length][varName.length]; for (int i = 0; i < (varName[0]).length; i++) { for (int j = 0; j < varName.length; j++) returnVar[i][j] = varName[j][i];  }  return returnVar; } private void zoomChartAxis(ChartPanel chartP, boolean increase) { int width = chartP.getMaximumDrawWidth() - chartP.getMinimumDrawWidth(); int height = chartP.getMaximumDrawHeight() - chartP.getMinimumDrawWidth(); if (increase) chartP.zoomInBoth((width / 2), (height / 2));  if (!increase) if (chartP.getChart().getXYPlot().getRangeAxis().getUpperBound() - chartP.getChart().getXYPlot().getRangeAxis().getLowerBound() < this.InitialYAxisLimits) chartP.zoomOutBoth((width / 2), (height / 2));   } public void keyReleased(KeyEvent e) { this.aDown = false; this.ctrlDown = false; this.shiftDown = false; this.escDown = false; this.clickZoomMode = false; e.getKeyCode(); if (e.getKeyCode() == 10) if (this.returnDown) { this.returnDown = false; if (this.TrimFileMode) { this.SoftwareStatusLabel.setText("Cutting File"); this.trimReturnDown = true; double minRange = this.lastchartX; double lowerBound = 0.0D; if (lowerBound > minRange) { this.SoftwareStatusLabel.setText("Bound Issues! Cannot cut file"); return; }  final int lowerSampleNo = (int)Math.round(minRange / this.MATInfo.timeSample); double maxRange = this.lastchartX + this.clipFileDuration; double upperBound = this.MATInfo.noSample * this.MATInfo.timeSample; if (upperBound < maxRange) { this.SoftwareStatusLabel.setText("Bound Issues! Cannot cut file"); return; }  final int upperSampleNo = (int)Math.round(maxRange / this.MATInfo.timeSample); boolean overLappingFlag = false; for (int i = 0; i < this.startingClipPoints.size(); i++) { if ((((lowerSampleNo >= ((Integer)this.endingClipPoints.get(i)).intValue()) ? 1 : 0) | ((upperSampleNo <= ((Integer)this.startingClipPoints.get(i)).intValue()) ? 1 : 0)) == 0) { overLappingFlag = true; break; }  }  final boolean overLap = overLappingFlag; final boolean nonoverLappingFlag = !overLappingFlag; int reply = 0; SwingUtilities.invokeLater(new Runnable() {
/*      */                 public void run() { if (overLap) { int reply = JOptionPane.showConfirmDialog(PickingWindow.this.MasterFrame, "Over Lapping Traces. Continue?", "Overlap Conf.", 0); JOptionPane.getRootFrame().dispose(); }  PickingWindow.this.startingClipPoints.add(Integer.valueOf(lowerSampleNo)); PickingWindow.this.endingClipPoints.add(Integer.valueOf(upperSampleNo)); PickingWindow.updateTree(); PickingWindow.this.SoftwareStatusLabel.setText("Ready"); }
/* 7085 */               }); } else { ArrayList<Integer> deleteList = new ArrayList<>(); System.out.println("toInterpPX.size()"); System.out.println(this.toInterpPX.size()); if (this.toInterpPX.size() < 2) return;  int j; for (j = 0; j < this.toInterpPY.size() - 1; j++) { for (int m = j + 1; m < this.toInterpPY.size(); m++) { if (((Float)this.toInterpPY.get(m)).equals(this.toInterpPY.get(j))) { deleteList.add(Integer.valueOf(j)); break; }  }  }  for (j = deleteList.size() - 1; j >= 0; j--) { this.toInterpPY.remove(((Integer)deleteList.get(j)).intValue()); this.toInterpPX.remove(((Integer)deleteList.get(j)).intValue()); }  double[] YData = new double[this.toInterpPY.size()]; for (int i = 0; i < this.toInterpPY.size(); ) { YData[i] = ((Float)this.toInterpPY.get(i)).floatValue(); i++; }  double[] XData = new double[this.toInterpPX.size()]; for (int k = 0; k < this.toInterpPX.size(); ) { XData[k] = ((Float)this.toInterpPX.get(k)).floatValue(); k++; }  clearInterp(); if (this.LinearInterp.isSelected()) { double[] sortedYData = new double[YData.length]; double[] tempYData = new double[YData.length]; double[] tempXData = new double[XData.length]; for (int ind = 0; ind < YData.length; ind++) { sortedYData[ind] = YData[ind]; tempYData[ind] = YData[ind]; tempXData[ind] = XData[ind]; }  Arrays.sort(sortedYData); for (int m = 0; m < sortedYData.length; m++) { for (int i2 = 0; i2 < tempYData.length; i2++) { if (tempYData[i2] == sortedYData[m]) { XData[m] = tempXData[i2]; YData[m] = tempYData[i2]; }  }  }  LinearInterpolator li = new LinearInterpolator(); PolynomialSplineFunction polySplineF = li.interpolate(YData, XData); double minY = YData[0], maxY = YData[0]; for (int n = 1; n < YData.length; n++) { if (minY > YData[n]) minY = YData[n];  if (maxY < YData[n]) maxY = YData[n];  }  double[] newY = new double[(int)(maxY - minY + 1.0D)]; int i1; for (i1 = 0; i1 < newY.length; i1++) newY[i1] = minY + i1;  if (this.pickPFlag) for (i1 = 0; i1 < newY.length; i1++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] >= 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] < 0.0D) { this.pickP[(int)newY[i1] - 1][1] = polySplineF.value(newY[i1]); updatePPicks(); } else { this.pickP[(int)newY[i1] - 1][1] = Double.NaN; updatePPicks(); }  }   if (this.pickSFlag) for (i1 = 0; i1 < newY.length; i1++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] < 0.0D) { this.pickS[(int)newY[i1] - 1][1] = polySplineF.value(newY[i1]); updateSPicks(); } else { this.pickS[(int)newY[i1] - 1][1] = Double.NaN; updateSPicks(); }  }   if (this.pickSSFlag) for (i1 = 0; i1 < newY.length; i1++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] < 0.0D) { this.pickSS[(int)newY[i1] - 1][1] = polySplineF.value(newY[i1]); updateSSPicks(); } else { this.pickSS[(int)newY[i1] - 1][1] = Double.NaN; updateSSPicks(); }  }   if (this.pickR1Flag) for (i1 = 0; i1 < newY.length; i1++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] < 0.0D) { this.pickR1[(int)newY[i1] - 1][1] = polySplineF.value(newY[i1]); updateR1Picks(); } else { this.pickR1[(int)newY[i1] - 1][1] = Double.NaN; updateR1Picks(); }  }   if (this.pickR2Flag) for (i1 = 0; i1 < newY.length; i1++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] < 0.0D) { this.pickR2[(int)newY[i1] - 1][1] = polySplineF.value(newY[i1]); updateR2Picks(); } else { this.pickR2[(int)newY[i1] - 1][1] = Double.NaN; updateR2Picks(); }  }   if (this.pickR3Flag) for (i1 = 0; i1 < newY.length; i1++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[i1] - 1.0D))] < 0.0D) { this.pickR3[(int)newY[i1] - 1][1] = polySplineF.value(newY[i1]); updateR3Picks(); } else { this.pickR3[(int)newY[i1] - 1][1] = Double.NaN; updateR3Picks(); }  }   } else { NevilleInterpolator splineInterp = new NevilleInterpolator(); PolynomialFunctionLagrangeForm polySplineF = splineInterp.interpolate(YData, XData); double minY = YData[0], maxY = YData[0]; for (int m = 1; m < YData.length; m++) { if (minY > YData[m]) minY = YData[m];  if (maxY < YData[m]) maxY = YData[m];  }  double[] newY = new double[(int)(maxY - minY + 1.0D)]; int n; for (n = 0; n < newY.length; n++) newY[n] = minY + n;  if (this.pickPFlag) for (n = 0; n < newY.length; n++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] < 0.0D) { this.pickP[(int)newY[n] - 1][1] = polySplineF.value(newY[n]); updatePPicks(); } else { this.pickP[(int)newY[n] - 1][1] = Double.NaN; updatePPicks(); }  }   if (this.pickSFlag) for (n = 0; n < newY.length; n++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] < 0.0D) { this.pickS[(int)newY[n] - 1][1] = polySplineF.value(newY[n]); updateSPicks(); } else { this.pickS[(int)newY[n] - 1][1] = Double.NaN; updateSPicks(); }  }   if (this.pickSSFlag) for (n = 0; n < newY.length; n++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] < 0.0D) { this.pickSS[(int)newY[n] - 1][1] = polySplineF.value(newY[n]); updateSSPicks(); } else { this.pickSS[(int)newY[n] - 1][1] = Double.NaN; updateSSPicks(); }  }   if (this.pickR1Flag) for (n = 0; n < newY.length; n++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] < 0.0D) { this.pickR1[(int)newY[n] - 1][1] = polySplineF.value(newY[n]); updateR1Picks(); } else { this.pickR1[(int)newY[n] - 1][1] = Double.NaN; updateR1Picks(); }  }   if (this.pickR2Flag) for (n = 0; n < newY.length; n++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] < 0.0D) { this.pickR2[(int)newY[n] - 1][1] = polySplineF.value(newY[n]); updateR2Picks(); } else { this.pickR2[(int)newY[n] - 1][1] = Double.NaN; updateR2Picks(); }  }   if (this.pickR3Flag) for (n = 0; n < newY.length; n++) { if (this.DISPLAY_MODE != "Wiggle" || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] > 0.0D || this.MATInfo.getTraceData()[0][(int)(3.0D * (newY[n] - 1.0D))] < 0.0D) { this.pickR3[(int)newY[n] - 1][1] = polySplineF.value(newY[n]); updateR3Picks(); } else { this.pickR3[(int)newY[n] - 1][1] = Double.NaN; updateR3Picks(); }  }   }  }  }   } public static void displayntraces(double[][] traceIn, int n) { System.out.println("--- Displaying records ---"); System.out.println(); for (int i = 0; i < n; i++) { for (int j = 0; j < n; j++) System.out.print(String.valueOf(traceIn[i][j]) + "\t");  System.out.println(); }  System.out.println(); System.out.println("--- Displayed records ---"); System.out.println(); } private static int wndE = 2; private static int wndAGC = 300; private static int noEdgeSelect = 20; private static double cannyUp = 0.375D; private double infill; private double ID_counter; private ArrayList<ArrayList<double[]>> edges; private ArrayList<ArrayList<XYLineAnnotation>> edgesAnnotation; private ArrayList<ArrayList<XYLineAnnotation>> selectedPAnnotation; private ArrayList<ArrayList<XYLineAnnotation>> selectedSAnnotation; private ArrayList<ArrayList<XYLineAnnotation>> selectedSSAnnotation; boolean savingPreviousFile; String currentSavingFile; public void resetTimer() { if (this.SimpleTimer.isRunning())
/* 7086 */       this.SimpleTimer.stop(); 
/* 7087 */     this.StopWatch.setText("00:00:00"); } String copySavingFile; double[][] stackPickP; double[][] stackPickS; double[][] stackPickSS; double[][] stackPickR1; double[][] stackPickR2; double[][] stackPickR3; int mouseWheelCount; boolean mouseClicked; double lastchartX; boolean trimReturnDown; private void DrawAutoPickPanel(String filePath, final int lowerSampleNo, int upperSampleNo) { double startTimeMaster = System.currentTimeMillis(); this.SoftwareStatusLabel.setText("Computing Picks"); System.out.println(filePath); if (upperSampleNo - lowerSampleNo < 300) { wndAGC = upperSampleNo - lowerSampleNo; } else { wndAGC = 300; }  this.infill = 0.0D; readMAT autoMATInfo = null; try { autoMATInfo = new readMAT(filePath); } catch (FileNotFoundException e1) { e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }  double[][] traceGathers = autoMATInfo.getTraceDataOnly(); double[][] trimmedTraceGathers = Matrix.getRows(traceGathers, lowerSampleNo, upperSampleNo); int t_r = trimmedTraceGathers.length; int t_c = (trimmedTraceGathers[0]).length; double[] A = { 1.0D, -3.9308045893116D, 8.4449518498129D, -12.8736829637162D, 14.6928054389875D, -12.966167191264D, 8.9240395616017D, -4.6734406199523D, 1.7863789902029D, -0.4525918854287D, 0.0572679132456D }; double[] B = { 0.0539924864187D, -0.0163623116246D, 0.0508330896893D, -0.0928053470789D, 0.0202836417753D, -0.0731799975667D, 0.0512961026978D, -0.002054881733D, 0.0248531289073D, -0.0061399701363D, -0.0050471924725D }; double[] Z = { 0.5933833111996D, -1.9349621334628D, 3.4812622164679D, -4.760043213373D, 4.7314397851655D, -3.5893630447634D, 2.1365480817071D, -0.8868693855229D, 0.2447360092009D, -0.0421210534878D }; BandPassTrace filtTrc = new BandPassTrace(traceGathers, A, B, Z); double[][] trimmedFilterGathers = null; if (this.filterSettings.LPFlag) { LinearityFilter filtTrc2 = new LinearityFilter(filtTrc.getTraceData(), this.filterSettings); trimmedFilterGathers = filtTrc2.getTraceData(); } else { trimmedFilterGathers = filtTrc.getTraceData(); }  long startTime = System.currentTimeMillis(); double[][] denergy = calculateDEnergy(trimmedFilterGathers, 3); long endTime = System.currentTimeMillis(); System.out.println("Denergy took " + (endTime - startTime) + " milliseconds"); startTime = System.currentTimeMillis(); double[][] agcDenergy = calculateAGC(denergy); endTime = System.currentTimeMillis(); System.out.println("agcDenergy took " + (endTime - startTime) + " milliseconds"); startTime = System.currentTimeMillis(); double[][] envelope = calculateEnvelope(agcDenergy); endTime = System.currentTimeMillis(); System.out.println("envelope took " + (endTime - startTime) + " milliseconds"); envelope = Matrix.getRows(envelope, lowerSampleNo, upperSampleNo); List<double[][]> smoothImgList = (List)new ArrayList<>(); List<double[]> x1OutList = (List)new ArrayList<>(), x2OutList = (List)new ArrayList<>(); double[] divisions = divideInteger(t_r, this.wellNoRec); for (int well = 0; well < this.wellNoRec.length; well++) { double t_sub_c = divisions[2 * well + 1] - divisions[2 * well] + 1.0D; int startingRec = 0, endingRec = 0; for (int subwell = 0; subwell <= well; subwell++) { if (subwell > 0) startingRec = endingRec + 1;  endingRec = (int)(startingRec + this.wellNoRec[subwell] - 1.0D); }  double[] Y1in_Sub = new double[t_r]; double[] Y2in_Sub = new double[(int)this.wellNoRec[well]]; for (int j = 0; j < t_r; j++) Y1in_Sub[j] = j * autoMATInfo.timeSample;  for (int k = 0; k < this.wellNoRec[well]; k++) Y2in_Sub[k] = (startingRec + k + 1);  double[][] traceInInterp = Matrix.Submatrix(envelope, 0, envelope.length - 1, startingRec, endingRec); PiecewiseBicubicSplineInterpolatingFunction interpF_Sub = new PiecewiseBicubicSplineInterpolatingFunction(Y1in_Sub, Y2in_Sub, traceInInterp); this.infill = Math.max(this.infill, Math.floor(t_r / this.wellNoRec[well])); double[] X1outTemp = new double[t_r]; double[] X2outTemp = new double[(int)t_sub_c]; int m; for (m = 0; m < X1outTemp.length; m++) X1outTemp[m] = m * autoMATInfo.timeSample;  for (m = 0; m < X2outTemp.length; m++) X2outTemp[m] = (1 + startingRec) + m * (this.wellNoRec[well] - 1.0D) / (X2outTemp.length - 1);  double[][] smoothImgTemp = new double[t_r][(int)t_sub_c]; double[][] smoothImgTemp2 = new double[t_r][(int)t_sub_c]; int i1; for (i1 = 0; i1 < t_r; i1++) { for (int i2 = 0; i2 < (int)t_sub_c; i2++) smoothImgTemp[i1][i2] = interpF_Sub.value(X1outTemp[i1], X2outTemp[i2]);  }  startTime = System.currentTimeMillis(); endTime = System.currentTimeMillis(); System.out.println("Interpolation Parallel Sub Block took - " + (endTime - startTime) + " milliseconds"); startTime = System.currentTimeMillis(); for (i1 = 0; i1 < 3; i1++) { smoothImgTemp = smoothdata(smoothImgTemp, 1, this.infill); smoothImgTemp = smoothdata(smoothImgTemp, 2, this.infill); }  endTime = System.currentTimeMillis(); System.out.println("smoothdata Sub Block took - " + (endTime - startTime) + " milliseconds"); smoothImgList.add(smoothImgTemp); x1OutList.add(X1outTemp); x2OutList.add(X2outTemp); }  double[] X1out = mergeListofArrays(x1OutList); double[] X2out = mergeListofArrays(x2OutList); double[][] smoothImg = mergeListof2DArrays(smoothImgList); this.xylineChartAuto = ChartFactory.createXYLineChart("", "Time (ms)", "Receiver No.", null, PlotOrientation.VERTICAL, false, false, false); this.xylineChartAuto.setBackgroundPaint(Color.white); this.xylineChartAuto.setTitle(new TextTitle("Automatic Plots", new Font("Avenir Book", 1, 14))); this.xylineChartAuto.getTitle().setExpandToFitSpace(false); this.tracePlotAuto = this.xylineChartAuto.getXYPlot(); this.tracePlotAuto.setDomainGridlinesVisible(false); this.tracePlotAuto.setRangeGridlinesVisible(false); this.tracePlotAuto.setDomainPannable(false); this.tracePlotAuto.setRangePannable(false); this.tracePlotAuto.setDomainCrosshairVisible(false); this.tracePlotAuto.setDomainCrosshairLockedOnData(false); this.tracePlotAuto.setRangeCrosshairVisible(false); this.tracePlotAuto.setRangeCrosshairLockedOnData(false); NumberAxis xAxis = (NumberAxis)this.tracePlotAuto.getDomainAxis(); xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); xAxis.setLabelFont(new Font("Avenir Book", 1, 12)); xAxis.setTickLabelFont(new Font("Avenir Book", 0, 10)); xAxis.setRange(new Range(X1out[0], X1out[X1out.length - 1])); xAxis.setAutoRange(false); NumberAxis xAxis1 = (NumberAxis)this.tracePlot.getDomainAxis(); xAxis1.setRange(new Range(lowerSampleNo * autoMATInfo.timeSample + X1out[0], lowerSampleNo * autoMATInfo.timeSample + X1out[X1out.length - 1])); xAxis1.setAutoRange(false); NumberAxis yAxis = (NumberAxis)this.tracePlotAuto.getRangeAxis(); yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits()); yAxis.setInverted(true); yAxis.setAutoRangeIncludesZero(false); yAxis.setLabelFont(new Font("Avenir Book", 1, 12)); yAxis.setTickLabelFont(new Font("Avenir Book", 0, 10)); yAxis.setAutoRange(false); yAxis.setRange(new Range(X2out[0], X2out[X2out.length - 1])); NumberAxis yAxis1 = (NumberAxis)this.tracePlot.getRangeAxis(); yAxis1.setAutoRange(false); yAxis1.setRange(new Range(X2out[0], X2out[X2out.length - 1])); this.chartPanelAuto = new ChartPanel(this.xylineChartAuto, false); this.chartPanelAuto.setMaximumDrawHeight(8000); this.chartPanelAuto.setMaximumDrawWidth(8000); this.chartPanelAuto.setDisplayToolTips(false); this.chartPanelAuto.setPopupMenu(null); this.chartPanelAuto.setPreferredSize(new Dimension(this.AutoPickViewPanel.getWidth() - 20, this.AutoPickViewPanel.getHeight() - 40)); this.chartPanelAuto.setDomainZoomable(false); this.chartPanelAuto.setRangeZoomable(false); this.chartPanelAuto.setFocusable(true); this.chartPanelAuto.setVisible(true); this.chartPanelAuto.setAutoscrolls(false); this.chartPanelAuto.setFillZoomRectangle(false); this.chartPanelAuto.setZoomOutlinePaint(new Color(0.0F, 0.0F, 0.0F, 0.0F)); this.chartPanelAuto.addMouseWheelListener(this); this.chartPanelAuto.setZoomInFactor(0.9D); this.chartPanelAuto.setZoomOutFactor(1.1D); this.chartPanelAuto.getChart().addProgressListener(this); this.chartPanelAuto.updateUI(); this.chartPanelAuto.addKeyListener(this); BufferedImage image = new BufferedImage((smoothImg[0]).length, smoothImg.length, 1); double[][] image_matrix = smoothImg; double min_smooth = Matrix.Min(image_matrix); Matrix.Subtract(image_matrix, min_smooth); double max_smooth = Matrix.Max(image_matrix); for (int i = 0; i < image_matrix.length; i++) { for (int j = 0; j < (image_matrix[0]).length; j++) { int a = (int)Math.round(255.0D * image_matrix[i][j] / max_smooth); a = 255 - a; Color newColor = new Color(a, a, a); image.setRGB(i, j, newColor.getRGB()); }  }  BufferedImage outputCombined = new BufferedImage(smoothImg.length, (smoothImg[0]).length, 1); List<BufferedImage> outputs = new ArrayList<>(); int startingX = 0, startingY = 0; for (int l = 0; l < smoothImgList.size(); l++) { System.out.println("Starting X\t" + startingX); System.out.println("X Length\t" + ((double[][])smoothImgList.get(l)).length); System.out.println("Y Length\t" + (((double[][])smoothImgList.get(l))[0]).length); System.out.println("Image X\t" + image.getWidth()); System.out.println("Image Y\t" + image.getHeight()); outputs.add(image.getSubimage(0, startingX, ((double[][])smoothImgList.get(l)).length - 1, (((double[][])smoothImgList.get(l))[0]).length)); CannyEdgeDetector detector = new CannyEdgeDetector(); detector.setSourceImage(outputs.get(l)); detector.setLowThreshold(0.5F); detector.setHighThreshold(1.0F); detector.process(); BufferedImage outputProcessed = detector.getEdgesImage(); for (int row = 0; row < outputProcessed.getWidth(); row++) { for (int col = 0; col < outputProcessed.getHeight(); col++) outputCombined.setRGB(row, startingX + col, outputProcessed.getRGB(row, col));  }  startingX += (((double[][])smoothImgList.get(l))[0]).length; }  if (this.filterSettings.IntFlag) { this.xylineChart.setBackgroundImage(outputCombined); this.xylineChart.getPlot().setBackgroundImage(outputCombined); this.xylineChart.setBackgroundImageAlpha(0.0F); this.xylineChart.setBackgroundImageAlignment(15); }  this.xylineChartAuto.setBackgroundImage(image); this.xylineChartAuto.getPlot().setBackgroundImage(image); this.xylineChartAuto.setBackgroundImageAlpha(0.0F); this.xylineChartAuto.setBackgroundImageAlignment(15); this.AutoPickViewPanel.addKeyListener(this); this.AutoPickViewPanel.add((Component)this.chartPanelAuto); this.AutoPickViewPanel.updateUI(); this.ID_counter = 1.0D; List<double[][]> connectedEdgesList = (List)new ArrayList<>(); int startingCol = 0; for (int s = 0; s < smoothImgList.size(); s++) { double[][] smoothImgTemp = smoothImgList.get(s); startTime = System.currentTimeMillis(); for (int m = 0; m < (smoothImgTemp[0]).length; m++) { for (int k = 0; k < smoothImgTemp.length; k++) smoothImgTemp[k][m] = (outputCombined.getRGB(k, startingCol + m) >> 16 & 0xFF);  }  startingCol += (smoothImgTemp[0]).length; endTime = System.currentTimeMillis(); System.out.println("Canny Block took - " + (endTime - startTime) + " milliseconds"); int j; for (j = 0; j < 1; j++) { smoothImgTemp = smoothdata(smoothImgTemp, 1, 3.0D); smoothImgTemp = smoothdata(smoothImgTemp, 2, 3.0D); }  for (j = 0; j < smoothImgTemp.length; j++) { for (int k = 0; k < (smoothImgTemp[0]).length; k++) { if (smoothImgTemp[j][k] != 0.0D) smoothImgTemp[j][k] = 1.0D;  }  }  startTime = System.currentTimeMillis(); double[][] conEdgeTemp = findConnected(smoothImgTemp); endTime = System.currentTimeMillis(); System.out.println("findConnected took - " + (endTime - startTime) + " milliseconds"); connectedEdgesList.add(conEdgeTemp); }  double[][] conEdge = mergeListof2DArrays(connectedEdgesList); displaySizeOf(conEdge); double noEdge = Matrix.Max(conEdge); double[] volSeg = Matrix.CreateMatrix1D((int)noEdge, 0.0D); for (int iedge = 0; iedge < noEdge; iedge++) { double tempVal = 0.0D; for (int r = 0; r < conEdge.length; r++) { for (int c = 0; c < (conEdge[0]).length; c++) { if (conEdge[r][c] == (iedge + 1)) tempVal++;  }  }  volSeg[iedge] = tempVal; }  Arrays.sort(volSeg); if (volSeg.length > 0) Matrix.Reverse(volSeg);  this.edgesAnnotation = new ArrayList<>(); this.selectedPAnnotation = new ArrayList<>(); this.selectedSAnnotation = new ArrayList<>(); this.selectedSSAnnotation = new ArrayList<>(); this.edges = new ArrayList<>(); for (int n = 0; n < volSeg.length; n++) { if (n > volSeg.length - 1) break;  ArrayList<double[]> tempEdges = (ArrayList)new ArrayList<>(); ArrayList<XYLineAnnotation> tempAnnotList = new ArrayList<>(); for (int c = 0; c < (conEdge[0]).length; c++) { double tempVar = 0.0D; for (int r = 0; r < conEdge.length; r++) { if (conEdge[r][c] == (n + 1)) tempVar = Math.max(tempVar, X1out[r]);  }  if (tempVar != 0.0D) tempEdges.add(new double[] { tempVar, X2out[c] });  }  if (tempEdges.size() > 1) { this.edges.add(tempEdges); tempAnnotList = new ArrayList<>(); int j; for (j = 1; j < tempEdges.size(); j++) { XYLineAnnotation tempAnnot = new XYLineAnnotation(((double[])tempEdges.get(j))[0], ((double[])tempEdges.get(j))[1], ((double[])tempEdges.get(j - 1))[0], ((double[])tempEdges.get(j - 1))[1], new BasicStroke(3.0F, 1, 1), Color.YELLOW); tempAnnot.setURL("Init" + String.valueOf(this.edges.size())); tempAnnotList.add(tempAnnot); this.tracePlotAuto.getRenderer().addAnnotation((XYAnnotation)tempAnnot); }  this.edgesAnnotation.add(tempAnnotList); tempAnnotList = new ArrayList<>(); for (j = 1; j < tempEdges.size(); j++) { XYLineAnnotation tempAnnot = new XYLineAnnotation(((double[])tempEdges.get(j))[0], ((double[])tempEdges.get(j))[1], ((double[])tempEdges.get(j - 1))[0], ((double[])tempEdges.get(j - 1))[1], new BasicStroke(3.0F, 1, 1), Color.RED); tempAnnot.setURL("P-Pick" + String.valueOf(this.edges.size())); tempAnnotList.add(tempAnnot); }  this.selectedPAnnotation.add(tempAnnotList); tempAnnotList = new ArrayList<>(); for (j = 1; j < tempEdges.size(); j++) { XYLineAnnotation tempAnnot = new XYLineAnnotation(((double[])tempEdges.get(j))[0], ((double[])tempEdges.get(j))[1], ((double[])tempEdges.get(j - 1))[0], ((double[])tempEdges.get(j - 1))[1], new BasicStroke(3.0F, 1, 1), Color.GREEN); tempAnnot.setURL("S-Pick" + String.valueOf(this.edges.size())); tempAnnotList.add(tempAnnot); }  this.selectedSAnnotation.add(tempAnnotList); tempAnnotList = new ArrayList<>(); for (j = 1; j < tempEdges.size(); j++) { XYLineAnnotation tempAnnot = new XYLineAnnotation(((double[])tempEdges.get(j))[0], ((double[])tempEdges.get(j))[1], ((double[])tempEdges.get(j - 1))[0], ((double[])tempEdges.get(j - 1))[1], new BasicStroke(3.0F, 1, 1), Color.ORANGE); tempAnnot.setURL("SS-Pick" + String.valueOf(this.edges.size())); tempAnnotList.add(tempAnnot); }  this.selectedSSAnnotation.add(tempAnnotList); }  }  this.chartPanelAuto.addChartMouseListener(new ChartMouseListener() { public void chartMouseClicked(ChartMouseEvent cme) { ChartEntity ce = cme.getEntity(); if (ce instanceof org.jfree.chart.entity.XYAnnotationEntity) for (int i = 0; i < PickingWindow.this.edgesAnnotation.size(); i++) { for (int j = 0; j < ((ArrayList)PickingWindow.this.edgesAnnotation.get(i)).size(); j++) { if (PickingWindow.this.pickPFlag || PickingWindow.this.pickSFlag || PickingWindow.this.pickSSFlag) if (((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.edgesAnnotation.get(i)).get(j)).getURL().equalsIgnoreCase(ce.getURLText())) { PickingWindow.this.tracePlotAuto.getRenderer().removeAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.edgesAnnotation.get(i)).get(j)); if (PickingWindow.this.pickPFlag) PickingWindow.this.tracePlotAuto.getRenderer().addAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.selectedPAnnotation.get(i)).get(j));  if (PickingWindow.this.pickSFlag) PickingWindow.this.tracePlotAuto.getRenderer().addAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.selectedSAnnotation.get(i)).get(j));  if (PickingWindow.this.pickSSFlag) PickingWindow.this.tracePlotAuto.getRenderer().addAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.selectedSSAnnotation.get(i)).get(j));  }   if (PickingWindow.this.pickPFlag && ((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.selectedPAnnotation.get(i)).get(j)).getURL().equalsIgnoreCase(ce.getURLText())) { PickingWindow.this.tracePlotAuto.getRenderer().removeAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.selectedPAnnotation.get(i)).get(j)); PickingWindow.this.tracePlotAuto.getRenderer().addAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.edgesAnnotation.get(i)).get(j)); }  if (PickingWindow.this.pickSFlag && ((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.selectedSAnnotation.get(i)).get(j)).getURL().equalsIgnoreCase(ce.getURLText())) { PickingWindow.this.tracePlotAuto.getRenderer().removeAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.selectedSAnnotation.get(i)).get(j)); PickingWindow.this.tracePlotAuto.getRenderer().addAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.edgesAnnotation.get(i)).get(j)); }  if (PickingWindow.this.pickSSFlag && ((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.selectedSSAnnotation.get(i)).get(j)).getURL().equalsIgnoreCase(ce.getURLText())) { PickingWindow.this.tracePlotAuto.getRenderer().removeAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.selectedSSAnnotation.get(i)).get(j)); PickingWindow.this.tracePlotAuto.getRenderer().addAnnotation(((ArrayList<XYAnnotation>)PickingWindow.this.edgesAnnotation.get(i)).get(j)); }  }  if (PickingWindow.this.pickPFlag && ((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.selectedPAnnotation.get(i)).get(0)).getURL().equalsIgnoreCase(ce.getURLText())) { for (int r_p = 1; r_p < PickingWindow.this.pickP.length + 1; r_p++) { double minDifference = 1.0D / PickingWindow.this.infill; for (int e = 0; e < ((ArrayList)PickingWindow.this.edges.get(i)).size(); e++) { if (Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p) < minDifference) { PickingWindow.this.pickP[r_p - 1][1] = Double.NaN; minDifference = Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p); }  }  }  PickingWindow.this.updatePPicks(); }  if (PickingWindow.this.pickSFlag && ((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.selectedSAnnotation.get(i)).get(0)).getURL().equalsIgnoreCase(ce.getURLText())) { for (int r_p = 1; r_p < PickingWindow.this.pickP.length + 1; r_p++) { double minDifference = 1.0D / PickingWindow.this.infill; for (int e = 0; e < ((ArrayList)PickingWindow.this.edges.get(i)).size(); e++) { if (Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p) < minDifference) { PickingWindow.this.pickS[r_p - 1][1] = Double.NaN; minDifference = Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p); }  }  }  PickingWindow.this.updateSPicks(); }  if (PickingWindow.this.pickSSFlag && ((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.selectedSSAnnotation.get(i)).get(0)).getURL().equalsIgnoreCase(ce.getURLText())) { for (int r_p = 1; r_p < PickingWindow.this.pickP.length + 1; r_p++) { double minDifference = 1.0D / PickingWindow.this.infill; for (int e = 0; e < ((ArrayList)PickingWindow.this.edges.get(i)).size(); e++) { if (Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p) < minDifference) { PickingWindow.this.pickSS[r_p - 1][1] = Double.NaN; minDifference = Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p); }  }  }  PickingWindow.this.updateSSPicks(); }  if (((XYLineAnnotation)((ArrayList<XYLineAnnotation>)PickingWindow.this.edgesAnnotation.get(i)).get(0)).getURL().equalsIgnoreCase(ce.getURLText())) { if (PickingWindow.this.pickPFlag) { for (int r_p = 1; r_p < PickingWindow.this.pickP.length + 1; r_p++) { double minDifference = 1.0D / PickingWindow.this.infill; for (int e = 0; e < ((ArrayList)PickingWindow.this.edges.get(i)).size(); e++) { if (Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p) < minDifference) { PickingWindow.this.pickP[r_p - 1][1] = ((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[0] + lowerSampleNo * PickingWindow.this.MATInfo.timeSample; minDifference = Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p); }  }  }  PickingWindow.this.updatePPicks(); }  if (PickingWindow.this.pickSFlag) { for (int r_p = 1; r_p < PickingWindow.this.pickS.length + 1; r_p++) { double minDifference = 1.0D / PickingWindow.this.infill; for (int e = 0; e < ((ArrayList)PickingWindow.this.edges.get(i)).size(); e++) { if (Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p) < minDifference) { PickingWindow.this.pickS[r_p - 1][1] = ((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[0] + lowerSampleNo * PickingWindow.this.MATInfo.timeSample; minDifference = Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p); }  }  }  PickingWindow.this.updateSPicks(); }  if (PickingWindow.this.pickSSFlag) { for (int r_p = 1; r_p < PickingWindow.this.pickSS.length + 1; r_p++) { double minDifference = 1.0D / PickingWindow.this.infill; for (int e = 0; e < ((ArrayList)PickingWindow.this.edges.get(i)).size(); e++) { if (Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p) < minDifference) { PickingWindow.this.pickSS[r_p - 1][1] = ((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[0] + lowerSampleNo * PickingWindow.this.MATInfo.timeSample; minDifference = Math.abs(((double[])((ArrayList)PickingWindow.this.edges.get(i)).get(e))[1] - r_p); }  }  }  PickingWindow.this.updateSSPicks(); }  }  }   } public void chartMouseMoved(ChartMouseEvent arg0) {} }
/*      */       ); this.SoftwareStatusLabel.setText("Ready!"); double endTimeMaster = System.currentTimeMillis(); System.out.println("Master Function took - " + (endTimeMaster - startTimeMaster) + " milliseconds"); } private void fixAreaForConnectingEdges(double[][] input) { for (int i = 0; i < input.length; i++) { double previousNo = input[i][0]; for (int j = 1; j < (input[0]).length; j++) { if (input[i][j] != 0.0D) if (input[i][j] != previousNo) { previousNo = input[i][j]; } else { input[i][j] = 0.0D; }   }  }  } private double[][] mergeListof2DArrays(List<double[][]> smoothImgList) { int r = 0, c = 0; for (int l = 0; l < smoothImgList.size(); l++) { r = ((double[][])smoothImgList.get(l)).length; c += (((double[][])smoothImgList.get(l))[0]).length; }  double[][] output = new double[r][c]; int counter_c = 0; for (int i = 0; i < smoothImgList.size(); i++) { for (int j = 0; j < (((double[][])smoothImgList.get(i))[0]).length; j++) { for (int k = 0; k < ((double[][])smoothImgList.get(i)).length; k++) output[k][counter_c] = ((double[][])smoothImgList.get(i))[k][j];  counter_c++; }  }  return output; } private double[] mergeListofArrays(List<double[]> x1OutList) { int size = 0; for (int l = 0; l < x1OutList.size(); l++) size += ((double[])x1OutList.get(l)).length;  double[] output = new double[size]; int counter = 0; for (int i = 0; i < x1OutList.size(); i++) { for (int j = 0; j < ((double[])x1OutList.get(i)).length; j++) output[counter++] = ((double[])x1OutList.get(i))[j];  }  return output; } private double[] divideInteger(double n, double[] wellNoRec) { double totalRecs = 0.0D; for (int i = 0; i < wellNoRec.length; i++) totalRecs += wellNoRec[i];  double split = n / totalRecs; double[] startingRow = new double[wellNoRec.length]; double[] endingRow = new double[wellNoRec.length]; double[] output = new double[2 * wellNoRec.length]; totalRecs = 0.0D; startingRow[0] = 1.0D; for (int j = 0; j < wellNoRec.length; j++) { totalRecs += wellNoRec[j]; if (j > 0) startingRow[j] = endingRow[j - 1] + 1.0D;  endingRow[j] = Math.round(split * totalRecs); }  endingRow[wellNoRec.length - 1] = n; int count = 0; for (int k = 0; k < startingRow.length; k++) { output[count++] = startingRow[k]; output[count++] = endingRow[k]; }  return output; } private double[][] findConnected(double[][] input) { int rows = input.length, cols = (input[0]).length; List<int[]> stack = (List)new ArrayList<>(); boolean[][] visited = Matrix.CreateMatrix2D(rows, cols, false); double[][] B = Matrix.CreateMatrix2D(rows, cols, 0.0D); for (int row = 0; row < rows; row++) { for (int col = 0; col < cols; col++) { if (input[row][col] == 0.0D) { visited[row][col] = true; } else if (!visited[row][col]) { stack.add(new int[] { row, col }); while (!stack.isEmpty()) { int[] loc = stack.get(0); stack.remove(0); if (visited[loc[0]][loc[1]]) continue;  visited[loc[0]][loc[1]] = true; B[loc[0]][loc[1]] = this.ID_counter; List<int[]> locs = (List)new ArrayList<>(); for (int x = -2; x <= 2; x++) { for (int y = -2; y <= 2; y++) { locs.add(new int[] { loc[0] + x, loc[1] + y }); }  }  for (int l = 0; l < locs.size(); l++) { int[] gridElement = locs.get(l); if (gridElement[0] >= 0 && gridElement[0] <= rows - 1 && gridElement[1] >= 0 && gridElement[1] <= cols - 1) if (!visited[gridElement[0]][gridElement[1]]) if (input[gridElement[0]][gridElement[1]] != 0.0D) stack.add(gridElement);    }  }  this.ID_counter++; }  }  }  return B; } private double[][] findConnected(double[][] traceIn, double[] divisions) { double ID_counter = 1.0D; List<double[][]> outputArrays = (List)new ArrayList<>(); for (int well = 0; well < this.wellNoRec.length; well++) { int startingRec = 0, endingRec = 0; for (int subwell = 0; subwell <= well; subwell++) { if (subwell > 0) startingRec = endingRec + 1;  endingRec = (int)(startingRec + this.wellNoRec[subwell] - 1.0D); }  double[][] input = Matrix.Submatrix(traceIn, 0, traceIn.length - 1, startingRec, endingRec); int rows = input.length; int cols = (input[0]).length; List<int[]> stack = (List)new ArrayList<>(); boolean[][] visited = Matrix.CreateMatrix2D(rows, cols, false); double[][] B = Matrix.CreateMatrix2D(rows, cols, 0.0D); for (int row = 0; row < rows; row++) { for (int col = 0; col < cols; col++) { if (input[row][col] == 0.0D) { visited[row][col] = true; } else if (!visited[row][col]) { stack.add(new int[] { row, col }); while (!stack.isEmpty()) { int[] loc = stack.get(0); stack.remove(0); if (visited[loc[0]][loc[1]]) continue;  visited[loc[0]][loc[1]] = true; B[loc[0]][loc[1]] = ID_counter; List<int[]> locs = (List)new ArrayList<>(); locs.add(new int[] { loc[0] - 1, loc[1] - 1 }); locs.add(new int[] { loc[0] + 0, loc[1] - 1 }); locs.add(new int[] { loc[0] + 1, loc[1] - 1 }); locs.add(new int[] { loc[0] - 1, loc[1] - 0 }); locs.add(new int[] { loc[0] + 0, loc[1] - 0 }); locs.add(new int[] { loc[0] + 1, loc[1] - 0 }); locs.add(new int[] { loc[0] - 1, loc[1] + 1 }); locs.add(new int[] { loc[0] + 0, loc[1] + 1 }); locs.add(new int[] { loc[0] + 1, loc[1] + 1 }); for (int l = 0; l < locs.size(); l++) { int[] gridElement = locs.get(l); if (gridElement[0] >= 0 && gridElement[0] <= rows - 1 && gridElement[1] >= 0 && gridElement[1] <= cols - 1) if (!visited[gridElement[0]][gridElement[1]]) if (input[gridElement[0]][gridElement[1]] != 0.0D) stack.add(gridElement);    }  }  ID_counter++; }  }  }  outputArrays.add(B); }  double[][] output = mergeListof2DArrays(outputArrays); return output; } private double[][] canny(double[][] img, double T_High) { double T_Low = 0.05D; double imgMin = Matrix.Min(img); double imgMax = Matrix.Max(img); Matrix.Subtract(img, imgMin); img = Matrix.Multiply(img, 255.0D / Math.abs(imgMax)); double[][] B = { { 2.0D, 4.0D, 5.0D, 4.0D, 2.0D }, { 4.0D, 9.0D, 12.0D, 9.0D, 4.0D }, { 5.0D, 12.0D, 15.0D, 12.0D, 5.0D }, { 4.0D, 9.0D, 12.0D, 9.0D, 4.0D }, { 2.0D, 4.0D, 5.0D, 4.0D, 2.0D } }; Matrix.Divide(B, 159.0D); double[][] A = conv2Same(img, B); double[][] KGx = { { -1.0D, 0.0D, 1.0D }, { -2.0D, 0.0D, 2.0D }, { -1.0D, 0.0D, 1.0D } }; double[][] KGy = { { 1.0D, 2.0D, 1.0D }, { 0.0D, 0.0D, 0.0D }, { -1.0D, -2.0D, -1.0D } }; double[][] Filtered_X = conv2Same(A, KGx); double[][] Filtered_Y = conv2Same(A, KGy); double[][] arah = new double[Filtered_X.length][(Filtered_X[0]).length]; double[][] arah2 = new double[Filtered_X.length][(Filtered_X[0]).length]; for (int i = 0; i < arah.length; i++) { for (int m = 0; m < (arah[0]).length; m++) { arah[i][m] = Math.atan2(Filtered_Y[i][m], Filtered_X[i][m]); arah[i][m] = arah[i][m] * 180.0D / Math.PI; if (arah[i][m] < 0.0D) arah[i][m] = 360.0D + arah[i][m];  if ((arah[i][m] >= 0.0D && arah[i][m] < 22.5D) || (arah[i][m] >= 157.5D && arah[i][m] < 202.5D) || (arah[i][m] >= 337.5D && arah[i][m] <= 360.0D)) { arah2[i][m] = 0.0D; } else if ((arah[i][m] >= 22.5D && arah[i][m] < 67.5D) || (arah[i][m] >= 202.5D && arah[i][m] < 247.5D)) { arah2[i][m] = 45.0D; } else if ((arah[i][m] >= 67.5D && arah[i][m] < 112.5D) || (arah[i][m] >= 247.5D && arah[i][m] < 292.5D)) { arah2[i][m] = 90.0D; } else if ((arah[i][m] >= 112.5D && arah[i][m] < 157.5D) || (arah[i][m] >= 292.5D && arah[i][m] < 337.5D)) { arah2[i][m] = 135.0D; }  }  }  double[][] magnitude = new double[Filtered_X.length][(Filtered_X[0]).length]; double[][] magnitude2 = new double[Filtered_X.length][(Filtered_X[0]).length]; double[][] BW = new double[Filtered_X.length][(Filtered_X[0]).length]; int j; for (j = 0; j < magnitude.length; j++) { for (int m = 0; m < (magnitude[0]).length; m++) { magnitude[j][m] = Math.pow(Filtered_X[j][m], 2.0D) + Math.pow(Filtered_Y[j][m], 2.0D); magnitude2[j][m] = Math.sqrt(magnitude[j][m]); }  }  for (j = 0; j < magnitude.length; j++) { for (int m = 0; m < (magnitude[0]).length; m++) { if (j > 1 && j < magnitude.length - 1 && m > 1 && m < (magnitude[0]).length - 1) if (arah2[j][m] == 0.0D) { if (magnitude2[j][m] == Matrix.Max(new double[] { magnitude2[j][m], magnitude2[j][m + 1], magnitude2[j][m - 1] })) BW[j][m] = 1.0D;  } else if (arah2[j][m] == 45.0D) { if (magnitude2[j][m] == Matrix.Max(new double[] { magnitude2[j][m], magnitude2[j + 1][m - 1], magnitude2[j - 1][m + 1] })) BW[j][m] = 1.0D;  } else if (arah2[j][m] == 90.0D) { if (magnitude2[j][m] == Matrix.Max(new double[] { magnitude2[j][m], magnitude2[j + 1][m], magnitude2[j - 1][m] })) BW[j][m] = 1.0D;  } else if (arah2[j][m] == 135.0D && magnitude2[j][m] == Matrix.Max(new double[] { magnitude2[j][m], magnitude2[j + 1][m + 1], magnitude2[j - 1][m - 1] })) { BW[j][m] = 1.0D; }   BW[j][m] = BW[j][m] * magnitude2[j][m]; }  }  T_Low *= Matrix.Max(BW); T_High *= Matrix.Max(BW); double[][] T_res = new double[BW.length][(BW[0]).length]; for (int k = 0; k < BW.length; k++) { for (int m = 0; m < (BW[0]).length; m++) { if (BW[k][m] > T_High) T_res[k][m] = 1.0D;  }  }  return T_res; } private double[][] smoothdata(double[][] smoothImg, int dimension, double window) { if (window % 2.0D == 0.0D) window--;  double n = window; double[][] input = smoothImg; return smoothImg; } private double[][] smoothdata1(double[][] smoothImg, int dimension, double window) { if (window % 2.0D == 0.0D) window--;  if (dimension == 1) { for (int i = 0; i < (smoothImg[0]).length; i++) { double[] temp = smooth1DArray(Matrix.getColumn(smoothImg, i), window); for (int j = 0; j < smoothImg.length; j++) smoothImg[j][i] = temp[j];  }  } else { for (int i = 0; i < smoothImg.length; i++) { double[] temp = smooth1DArray(Matrix.getRow(smoothImg, i), window); for (int j = 0; j < (smoothImg[0]).length; j++) smoothImg[i][j] = temp[j];  }  }  return smoothImg; } private double[] smooth1DArray(double[] rawData, double window) { int halfSpace = (int)Math.round((window - 1.0D) / 2.0D); double[] start1 = Matrix.CreateMatrix1D(halfSpace + 1, 1.0D); double[] start2 = Matrix.CreateMatrix1D(rawData.length - halfSpace - 1, 0.0D); for (int i = 0; i < start2.length; i++) start2[i] = (i + 2);  double[] startMerged = merge(start1, start2); double[] stop1 = Matrix.CreateMatrix1D(rawData.length - halfSpace, 0.0D); for (int j = 0; j < stop1.length; j++) stop1[j] = (j + halfSpace + 1);  double[] stop2 = Matrix.CreateMatrix1D(halfSpace, rawData.length); double[] stopMerged = merge(stop1, stop2); double[] divide = Matrix.Subtract(stopMerged, startMerged); Matrix.Add(divide, 1.0D); double[] cumulativeSum = new double[rawData.length]; for (int k = 0; k < rawData.length; k++) { for (int n = 0; n < k + 1; n++) cumulativeSum[k] = cumulativeSum[k] + rawData[n];  }  double[] tempSum = new double[rawData.length]; double[] result = new double[rawData.length]; for (int m = 0; m < tempSum.length; m++) { tempSum[m] = cumulativeSum[(int)(stopMerged[m] - 1.0D)] - cumulativeSum[(int)Math.max(0.0D, startMerged[m] - 2.0D)]; if (startMerged[m] == 1.0D) tempSum[m] = tempSum[m] + rawData[0];  result[m] = tempSum[m] / divide[m]; }  return result; } private double[][] calculateEnvelope(double[][] agcOutput) { double[][] envelopeOutput = new double[agcOutput.length][(agcOutput[0]).length]; for (int j = 0; j < (agcOutput[0]).length; j++) { double[] y = Matrix.getColumn(agcOutput, j); double xmean = 0.0D; for (int i = 0; i < y.length; i++) xmean += y[i];  xmean /= y.length; double[] xcentered = new double[y.length]; for (int k = 0; k < y.length; k++) xcentered[k] = y[k] - xmean;  HilbertTransform.FHTAbsolute(xcentered, FourierTransform.Direction.Forward); double[] xupper = new double[y.length]; double[] xlower = new double[y.length]; int m; for (m = 0; m < y.length; m++) { xupper[m] = xmean + xcentered[m]; xlower[m] = xmean - xcentered[m]; }  for (m = 0; m < y.length; m++) envelopeOutput[m][j] = xmean + xcentered[m];  }  return envelopeOutput; } private double[][] calculateAGC(double[][] denergy) { double[][] data = Matrix.Transpose(denergy); int np = wndAGC; int nt = (data[0]).length; double[][] temp1 = Matrix.Submatrix(data, 0, data.length - 1, 0, np - 1); double[][] output = flipLTR(temp1); output = merge(output, data); double[][] temp = Matrix.Submatrix(data, 0, data.length - 1, nt - np - 1, nt - 1); double[][] temp2 = flipLTR(temp); output = merge(output, temp2); output = matrixRaisedTo(output, 2.0D); double[][] f = Matrix.CreateMatrix2D(np, 1, 1.0D / np); double[][] convOutput = conv2Full(output, Matrix.Transpose(f)); int start = (int)Math.round(1.5D * np); int endd = start + nt - 1; double[][] out = Matrix.Submatrix(convOutput, 0, convOutput.length - 1, start - 1, endd - 1); for (int i = 0; i < data.length; i++) { for (int j = 0; j < (data[0]).length; j++) data[i][j] = data[i][j] / (Math.sqrt(out[i][j]) + 1.0E-6D);  }  double[][] agcOutput = Matrix.Transpose(data); return agcOutput; } public double[][] matrixRaisedTo(double[][] traceIn, double power) { double[][] traceOut = new double[traceIn.length][(traceIn[0]).length]; for (int i = 0; i < traceIn.length; i++) { for (int j = 0; j < (traceIn[0]).length; j++) traceOut[i][j] = Math.pow(traceIn[i][j], power);  }  return traceOut; } public double[][] flipLTR(double[][] traceIn) { double[][] traceOut = new double[traceIn.length][(traceIn[0]).length]; for (int i = 0; i < traceIn.length; i++) { for (int j = 0; j < (traceIn[0]).length; j++) traceOut[i][j] = traceIn[i][(traceIn[0]).length - 1 - j];  }  return traceOut; } public double[] merge(double[] traceA, double[] traceB) { double[] traceOut = new double[traceA.length + traceB.length]; int j; for (j = 0; j < traceA.length; j++) traceOut[j] = traceA[j];  for (j = 0; j < traceB.length; j++) traceOut[traceA.length + j] = traceB[j];  return traceOut; } public double[][] merge(double[][] traceA, double[][] traceB) { if (traceA.length != traceB.length) JOptionPane.showMessageDialog(null, "Cannot merge traces. Unequal length");  double[][] traceOut = new double[traceA.length][(traceA[0]).length + (traceB[0]).length]; for (int i = 0; i < traceA.length; i++) { int j; for (j = 0; j < (traceA[0]).length; j++) traceOut[i][j] = traceA[i][j];  for (j = 0; j < (traceB[0]).length; j++) traceOut[i][(traceA[0]).length + j] = traceB[i][j];  }  return traceOut; } public static double[][] conv2Full(double[][] traceIn, double[][] kernel) { int m = traceIn.length; int n = (traceIn[0]).length; int m1 = kernel.length; int n1 = (kernel[0]).length; int m0 = m + 2 * m1 - 2; int n0 = n + 2 * n1 - 2; double[][] a0 = Matrix.CreateMatrix2D(m0, n0, 0.0D); for (int i = 0; i < traceIn.length; i++) { for (int j = 0; j < (traceIn[0]).length; j++) a0[m1 + i - 1][n1 + j - 1] = traceIn[i][j];  }  double[][] b1 = kernel; double[] b2 = Matrix.Reshape(b1); double[][] output = Matrix.CreateMatrix2D(m1 + m - 1, n1 + n - 1, 0.0D); for (int ii = 0; ii < m0 - m1 + 1; ii++) { for (int jj = 0; jj < n0 - n1 + 1; jj++) { double[][] x = Matrix.Submatrix(a0, ii, ii + m1 - 1, jj, jj + n1 - 1); output[ii][jj] = Matrix.InnerProduct(Matrix.Reshape(x), b2); }  }  return output; } public static double[][] conv2Same(double[][] traceIn, double[][] kernel) { int m = traceIn.length; int n = (traceIn[0]).length; int m1 = kernel.length; int n1 = (kernel[0]).length; int m0 = m + 2 * m1 - 2; int n0 = n + 2 * n1 - 2; double[][] a0 = Matrix.CreateMatrix2D(m0, n0, 0.0D); for (int i = 0; i < traceIn.length; i++) { for (int j = 0; j < (traceIn[0]).length; j++) a0[m1 + i - 1][n1 + j - 1] = traceIn[i][j];  }  double[][] b1 = kernel; rotateMatrix(b1); double[] b2 = Matrix.Reshape(b1); double[][] output = Matrix.CreateMatrix2D(m1 + m - 1, n1 + n - 1, 0.0D); for (int ii = 0; ii < m0 - m1 + 1; ii++) { for (int jj = 0; jj < n0 - n1 + 1; jj++) { double[][] x = Matrix.Submatrix(a0, ii, ii + m1 - 1, jj, jj + n1 - 1); output[ii][jj] = Matrix.InnerProduct(Matrix.Reshape(x), b2); }  }  int startingRow = (int)Math.floor((kernel.length / 2)); int endingRow = (int)(Math.floor((kernel.length / 2)) + traceIn.length - 1.0D); int startingCol = (int)Math.floor(((kernel[0]).length / 2)); int endingCol = (int)(Math.floor(((kernel[0]).length / 2)) + (traceIn[0]).length - 1.0D); output = Matrix.Submatrix(output, startingRow, endingRow, startingCol, endingCol); return output; } public static void rotateMatrix(double[][] mat) { int N = mat.length; for (int i = 0; i < N / 2; i++) { for (int j = 0; j < N; j++) { double temp = mat[i][j]; mat[i][j] = mat[N - i - 1][N - j - 1]; mat[N - i - 1][N - j - 1] = temp; }  }  if (N % 2 == 1) for (int j = 0; j < N / 2; j++) { double temp = mat[N / 2][j]; mat[N / 2][j] = mat[N / 2][N - j - 1]; mat[N / 2][N - j - 1] = temp; }   } private double[][] calculateDEnergy(double[][] trimmedFilterGathers, int noComp) { int noRec = (trimmedFilterGathers[0]).length / noComp; int noSample = trimmedFilterGathers.length; double[][] energy = new double[noSample][noRec]; double[][] denergy = new double[noSample][noRec]; for (int irec = 0; irec < noRec; irec++) { for (int iwnd = wndE; iwnd < noSample - wndE; iwnd++) { for (int icomp = 0; icomp < noComp; icomp++) { for (int row = iwnd - wndE; row < iwnd + wndE + 1; row++) energy[iwnd][irec] = energy[iwnd][irec] + Math.pow(trimmedFilterGathers[row][3 * irec + icomp], 2.0D);  }  }  int i; for (i = 1; i < energy.length - 1; i++) denergy[i][irec] = (energy[i + 1][irec] - energy[i - 1][irec]) / 2.0D;  denergy[0][irec] = (-3.0D * energy[0][irec] + 4.0D * energy[1][irec] - energy[2][irec]) / 2.0D; denergy[energy.length - 1][irec] = (3.0D * energy[energy.length - 1][irec] - 4.0D * energy[energy.length - 2][irec] + energy[energy.length - 3][irec]) / 2.0D; for (i = 0; i < 2 * wndE + 1; i++) denergy[i][irec] = 0.0D;  }  return denergy; } public void saveFile(final String currentFile) { SWSCheck qc = new SWSCheck(this.pickP, this.pickS, this.pickSS); if (!this.inputFolderSaving.isEmpty()) if (currentFile.contains(this.inputFolderSaving)) { this.copySavingFile = currentFile.replaceFirst(this.inputFolderSaving, this.outputFolderSaving); } else { JOptionPane.showMessageDialog(new JFrame("Warning!!"), "Current file not from the input saving folder. Cannot save to another location as specified."); this.copySavingFile = ""; }   try { this.rawMATInfo = new readMAT(currentFilePath); } catch (FileNotFoundException e1) { e1.printStackTrace(); } catch (IOException e1) { e1.printStackTrace(); }  if (this.stackMode) { double[][] bkupP = new double[this.pickP.length][(this.pickP[0]).length]; double[][] bkupS = new double[this.pickS.length][(this.pickS[0]).length]; double[][] bkupSS = new double[this.pickSS.length][(this.pickSS[0]).length]; double[][] bkupR1 = new double[this.pickP.length][(this.pickP[0]).length]; double[][] bkupR2 = new double[this.pickS.length][(this.pickS[0]).length]; double[][] bkupR3 = new double[this.pickSS.length][(this.pickSS[0]).length]; int i; for (i = 0; i < this.pickP.length; i++) { for (int j = 0; j < (this.pickP[0]).length; j++) { bkupP[i][j] = this.pickP[i][j]; bkupS[i][j] = this.pickS[i][j]; bkupSS[i][j] = this.pickSS[i][j]; bkupR1[i][j] = this.pickR1[i][j]; bkupR2[i][j] = this.pickR2[i][j]; bkupR3[i][j] = this.pickR3[i][j]; }  }  this.stackPickP = new double[this.revMappingArray.length][2]; this.stackPickS = new double[this.revMappingArray.length][2]; this.stackPickSS = new double[this.revMappingArray.length][2]; this.stackPickR1 = new double[this.revMappingArray.length][2]; this.stackPickR2 = new double[this.revMappingArray.length][2]; this.stackPickR3 = new double[this.revMappingArray.length][2]; for (i = 0; i < this.stackPickP.length; i++) { this.stackPickP[i][0] = (i + 1); this.stackPickP[i][1] = bkupP[this.revMappingArray[i]][1]; this.stackPickS[i][0] = (i + 1); this.stackPickS[i][1] = bkupS[this.revMappingArray[i]][1]; this.stackPickSS[i][0] = (i + 1); this.stackPickSS[i][1] = bkupSS[this.revMappingArray[i]][1]; this.stackPickR1[i][0] = (i + 1); this.stackPickR1[i][1] = bkupSS[this.revMappingArray[i]][1]; this.stackPickR2[i][0] = (i + 1); this.stackPickR2[i][1] = bkupSS[this.revMappingArray[i]][1]; this.stackPickR3[i][0] = (i + 1); this.stackPickR3[i][1] = bkupSS[this.revMappingArray[i]][1]; }  }  if (this.QCPicksFlag) { QCPicksWindow qcWindow; this.SoftwareStatusLabel.setText("Waiting for QC Window.."); this.SoftwareStatusLabel.repaint(); this.SoftwareStatusLabel.revalidate(); Rectangle progressRect = this.SoftwareStatusLabel.getBounds(); progressRect.x = 0; progressRect.y = 0; this.SoftwareStatusLabel.paintImmediately(progressRect); if (this.stackMode) { qcWindow = new QCPicksWindow(this.rawMATInfo, this.stackPickP, this.stackPickS, this.stackPickSS); } else { qcWindow = new QCPicksWindow(this.rawMATInfo, this.pickP, this.pickS, this.pickSS); }  if (!qcWindow.confirm) { this.SoftwareStatusLabel.setText("Ready!"); return; }  this.SoftwareStatusLabel.setText("Saving.."); }  this.saveCheck = false; this.savingPreviousFile = true; this.currentSavingFile = currentFile; if (this.Windows7) { try { MatFileReader mfr = new MatFileReader(currentFile); Map<String, MLArray> mlArrayRetrived = mfr.getContent(); MLDouble pickOutP = new MLDouble("pickOutP", this.pickP); MLDouble pickOutS = new MLDouble("pickOutS", this.pickS); MLDouble pickOutSS = new MLDouble("pickOutSS", this.pickSS); MLDouble pickOutR1 = new MLDouble("pickOutR1", this.pickR1); MLDouble pickOutR2 = new MLDouble("pickOutR2", this.pickR2); MLDouble pickOutR3 = new MLDouble("pickOutR3", this.pickR3); if (this.stackMode) { pickOutP = new MLDouble("pickOutP", this.stackPickP); pickOutS = new MLDouble("pickOutS", this.stackPickS); pickOutSS = new MLDouble("pickOutSS", this.stackPickSS); pickOutR1 = new MLDouble("pickOutR1", this.stackPickR1); pickOutR2 = new MLDouble("pickOutR2", this.stackPickR2); pickOutR3 = new MLDouble("pickOutR3", this.stackPickR3); }  if (this.pickingAuth.getCurrentUser() == null) JOptionPane.showMessageDialog(new JFrame("Not authenticated!"), "Picks will not be saved to the database. Please check.");  if (this.pickingAuth.getCurrentUser() != null) { MLChar pickerName = new MLChar("pickerName", this.pickingAuth.getCurrentUser().getName()); mlArrayRetrived.put("pickerName", pickerName); }  double[][] noSecondsMatrix = new double[1][1]; noSecondsMatrix[0][0] = this.noSeconds; MLDouble pickerTime = new MLDouble("pickTime", noSecondsMatrix); mlArrayRetrived.put("pickTime", pickerTime); mlArrayRetrived.put("pickOutP", pickOutP); mlArrayRetrived.put("pickOutS", pickOutS); mlArrayRetrived.put("pickOutSS", pickOutSS); mlArrayRetrived.put("pickOutR1", pickOutR1); mlArrayRetrived.put("pickOutR2", pickOutR2); mlArrayRetrived.put("pickOutR3", pickOutR3); ArrayList list = new ArrayList(); for (String key : mlArrayRetrived.keySet()) list.add(mlArrayRetrived.get(key));  try { try { if (!this.copySavingFile.isEmpty()) { if (!(new File(this.copySavingFile)).getParentFile().exists()) if (!(new File(this.copySavingFile)).getParentFile().mkdirs()) JOptionPane.showMessageDialog(new JFrame("Cannot make directory"), "Could not make the new saving directory.");   if ((new File(this.copySavingFile)).exists()) (new File(this.copySavingFile)).delete();  FileUtils.copyFile(new File(currentFile), new File(this.copySavingFile)); }  } catch (Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(new JFrame("Copy file error"), "Could not copy previous file to save folder location."); }  } catch (IOException e) { e.printStackTrace(); JOptionPane.showMessageDialog(new JFrame("Save Read Error"), "Could not save previous file. Some Error."); }  if (this.backupMode || this.SaveTraces.isSelected()) if (this.Windows7) { try { this.saveCheck = false; if (this.backupMode && this.pickingAuth.getCurrentUser() != null && this.pickingAuth.getCurrentProject() != null); } catch (Exception e) { e.printStackTrace(); this.saveCheck = false; }  } else { SwingUtilities.invokeLater(new Runnable() { public void run() { try { PickingWindow.this.saveCheck = false; if (PickingWindow.this.backupMode); } catch (Exception e) { e.printStackTrace(); PickingWindow.this.saveCheck = false; }  } }
/*      */               ); }   } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame("Save Read Error"), "Cannot save previous file. Some Error."); e.printStackTrace(); return; }  this.savingPreviousFile = false; this.currentSavingFile = ""; } else { Runnable r = new Runnable() { public void run() { try { MatFileReader mfr = new MatFileReader(currentFile); Map<String, MLArray> mlArrayRetrived = mfr.getContent(); MLDouble pickOutP = new MLDouble("pickOutP", PickingWindow.this.pickP); MLDouble pickOutS = new MLDouble("pickOutS", PickingWindow.this.pickS); MLDouble pickOutSS = new MLDouble("pickOutSS", PickingWindow.this.pickSS); MLDouble pickOutR1 = new MLDouble("pickOutR1", PickingWindow.this.pickR1); MLDouble pickOutR2 = new MLDouble("pickOutR2", PickingWindow.this.pickR2); MLDouble pickOutR3 = new MLDouble("pickOutR3", PickingWindow.this.pickR3); if (PickingWindow.this.stackMode) { pickOutP = new MLDouble("pickOutP", PickingWindow.this.stackPickP); pickOutS = new MLDouble("pickOutS", PickingWindow.this.stackPickS); pickOutSS = new MLDouble("pickOutSS", PickingWindow.this.stackPickSS); pickOutR1 = new MLDouble("pickOutR1", PickingWindow.this.stackPickR1); pickOutR2 = new MLDouble("pickOutR2", PickingWindow.this.stackPickR2); pickOutR3 = new MLDouble("pickOutR3", PickingWindow.this.stackPickR3); }  if (PickingWindow.this.pickingAuth.getCurrentUser() == null) JOptionPane.showMessageDialog(new JFrame("Not authenticated!"), "Picks will not be saved to the database. Please check.");  if (PickingWindow.this.pickingAuth.getCurrentUser() != null) { MLChar pickerName = new MLChar("pickerName", PickingWindow.this.pickingAuth.getCurrentUser().getName()); mlArrayRetrived.put("pickerName", pickerName); }  double[][] noSecondsMatrix = new double[1][1]; noSecondsMatrix[0][0] = PickingWindow.this.noSeconds; MLDouble pickerTime = new MLDouble("pickTime", noSecondsMatrix); mlArrayRetrived.put("pickTime", pickerTime); mlArrayRetrived.put("pickOutP", pickOutP); mlArrayRetrived.put("pickOutS", pickOutS); mlArrayRetrived.put("pickOutSS", pickOutSS); mlArrayRetrived.put("pickOutR1", pickOutR1); mlArrayRetrived.put("pickOutR2", pickOutR2); mlArrayRetrived.put("pickOutR3", pickOutR3); ArrayList list = new ArrayList(); for (String key : mlArrayRetrived.keySet()) list.add(mlArrayRetrived.get(key));  try { try { if (!PickingWindow.this.copySavingFile.isEmpty()) { if (!(new File(PickingWindow.this.copySavingFile)).getParentFile().exists()) if (!(new File(PickingWindow.this.copySavingFile)).getParentFile().mkdirs()) JOptionPane.showMessageDialog(new JFrame("Cannot make directory"), "Could not make the new saving directory.");   if ((new File(PickingWindow.this.copySavingFile)).exists()) (new File(PickingWindow.this.copySavingFile)).delete();  FileUtils.copyFile(new File(currentFile), new File(PickingWindow.this.copySavingFile)); }  } catch (Exception e) { e.printStackTrace(); JOptionPane.showMessageDialog(new JFrame("Copy file error"), "Could not copy previous file to save folder location."); }  } catch (IOException e) { e.printStackTrace(); JOptionPane.showMessageDialog(new JFrame("Save Read Error"), "Could not save previous file. Some Error."); }  if (PickingWindow.this.backupMode || PickingWindow.this.SaveTraces.isSelected()) if (PickingWindow.this.Windows7) { try { PickingWindow.this.saveCheck = false; if (PickingWindow.this.backupMode && PickingWindow.this.pickingAuth.getCurrentUser() != null && PickingWindow.this.pickingAuth.getCurrentProject() != null); } catch (Exception e) { e.printStackTrace(); PickingWindow.this.saveCheck = false; }  } else { SwingUtilities.invokeLater(new Runnable() {
/*      */                         public void run() { try { (PickingWindow.null.access$0(PickingWindow.null.this)).saveCheck = false; if ((PickingWindow.null.access$0(PickingWindow.null.this)).backupMode); } catch (Exception e) { e.printStackTrace(); (PickingWindow.null.access$0(PickingWindow.null.this)).saveCheck = false; }  }
/*      */                       }); }   } catch (Exception e) { JOptionPane.showMessageDialog(new JFrame("Save Read Error"), "Cannot save previous file. Some Error."); e.printStackTrace(); return; }  PickingWindow.this.savingPreviousFile = false; PickingWindow.this.currentSavingFile = ""; } }
/* 7092 */         ; ExecutorService executor = Executors.newCachedThreadPool(); executor.submit(r); }  } private static void displaySingleArray(double[] x2out) { System.out.println("--- Displaying single array ---"); System.out.println(); for (int i = 0; i < x2out.length; i++) System.out.print(String.valueOf(x2out[i]) + "\t");  System.out.println("\n\n-- Displayed single array ---"); } private Matrix getDoubleMatrix(double[][] matrix) { Matrix output = Mat5.newMatrix(matrix.length, (matrix[0]).length); for (int i = 0; i < matrix.length; i++) { for (int j = 0; j < (matrix[0]).length; j++) output.setDouble(i, j, matrix[i][j]);  }  return output; } private String getDoubleMatrixString(double[][] matrix) { String output = "{"; for (int i = 0; i < matrix.length; i++) { output = String.valueOf(output) + String.valueOf(matrix[i][1]); if (i != matrix.length - 1) output = String.valueOf(output) + ",";  }  output = String.valueOf(output) + "}"; return output; } private static void displaySizeOf(double[][] output) { System.out.println(" --- Size is ----\n"); System.out.println("Rows - " + output.length); System.out.println("Columns - " + (output[0]).length); System.out.println("\n--- End of Size Block ----"); } public void mouseWheelMoved(MouseWheelEvent e) { if (this.TrimFileMode) return;  this.mouseWheelCount++; if (e.getScrollType() != 0) return;  if (Math.floorMod(this.mouseWheelCount, 2) == 0) if (e.getWheelRotation() < 0) { zoomChartAxis(this.chartPanel, true); } else { zoomChartAxis(this.chartPanel, false); }   } public void chartMouseClicked(ChartMouseEvent mouseChartEvent) { this.mouseClicked = true; if (!this.AutoPickFlag) this.saveCheck = true;  if (mouseChartEvent.getTrigger().getButton() != 1) this.clickZoomMode = true;  if (this.DISPLAY_MODE == "Wiggle") return;  ChartEntity ce = mouseChartEvent.getEntity(); if (ce != null && ce instanceof XYItemEntity) { XYItemEntity item = (XYItemEntity)ce; double time = item.getDataset().getXValue(item.getSeriesIndex(), item.getItem()); double noRec = (item.getSeriesIndex() + 1); System.out.println("Time - " + Double.toString(time) + "; Rec - " + Double.toString(noRec)); if (this.clickZoomMode && this.mouseClicked) { double chartX = item.getDataset().getXValue(item.getSeriesIndex(), item.getItem()); NumberAxis domain = (NumberAxis)this.chartPanel.getChart().getXYPlot().getDomainAxis(); domain.setRange(chartX, chartX + this.customZoomX - 1.0D); this.mouseClicked = false; this.clickZoomMode = false; return; }  if (((((this.pickPFlag | this.pickSFlag | this.pickSSFlag) != 0 && this.HideArrivalsFlag) ? 1 : 0) | (((this.pickR1Flag | this.pickR2Flag | this.pickR3Flag) != 0 && this.HideReflectionsFlag) ? 1 : 0)) != 0) return;  if (!this.clickZoomMode && this.mouseClicked && (this.pickPFlag | this.pickSFlag | this.pickSSFlag | this.pickR1Flag | this.pickR2Flag | this.pickR3Flag) != 0 && !this.TrimFileMode) { double chartY = noRec; double chartX = time; chartY = Math.round(chartY); this.toInterpPX.add(Float.valueOf((float)chartX)); this.toInterpPY.add(Float.valueOf((float)chartY)); String markerInterpType = ""; if (this.pickPFlag) markerInterpType = "+";  if (this.pickSFlag) markerInterpType = "x";  if (this.pickSSFlag) markerInterpType = "o";  if (this.pickR1Flag) markerInterpType = "+";  if (this.pickR2Flag) markerInterpType = "x";  if (this.pickR3Flag) markerInterpType = "o";  XYTextAnnotation temp = new XYTextAnnotation(markerInterpType, chartX, chartY); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerInterp); this.InterpShape.add(count, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.InterpShape.get(count)); count++; this.mouseClicked = false; }  }  ce instanceof org.jfree.chart.entity.XYAnnotationEntity; } public void chartProgress(ChartProgressEvent cpe) { if (this.DISPLAY_MODE != "Wiggle") return;  cpe.getType(); if (cpe.getType() == 2 && this.clickZoomMode && this.mouseClicked) { double chartX = this.chartPanel.getChart().getXYPlot().getDomainCrosshairValue(); NumberAxis domain = (NumberAxis)this.chartPanel.getChart().getXYPlot().getDomainAxis(); domain.setRange(chartX, chartX + this.customZoomX - 1.0D); this.mouseClicked = false; this.clickZoomMode = false; return; }  if (cpe.getType() != 2 || this.mouseClicked); if (((((this.pickPFlag | this.pickSFlag | this.pickSSFlag) != 0 && this.HideArrivalsFlag) ? 1 : 0) | (((this.pickR1Flag | this.pickR2Flag | this.pickR3Flag) != 0 && this.HideReflectionsFlag) ? 1 : 0)) != 0) return;  if (cpe.getType() == 2 && !this.clickZoomMode && this.mouseClicked && (this.pickPFlag | this.pickSFlag | this.pickSSFlag | this.pickR1Flag | this.pickR2Flag | this.pickR3Flag) != 0 && !this.TrimFileMode) { double chartY = this.chartPanel.getChart().getXYPlot().getRangeCrosshairValue(); double chartX = this.chartPanel.getChart().getXYPlot().getDomainCrosshairValue(); chartY = Math.round(chartY); this.toInterpPX.add(Float.valueOf((float)chartX)); this.toInterpPY.add(Float.valueOf((float)chartY)); String markerInterpType = ""; if (this.pickPFlag) markerInterpType = "+";  if (this.pickSFlag) markerInterpType = "x";  if (this.pickSSFlag) markerInterpType = "o";  if (this.pickR1Flag) markerInterpType = "+";  if (this.pickR2Flag) markerInterpType = "x";  if (this.pickR3Flag) markerInterpType = "o";  XYTextAnnotation temp = new XYTextAnnotation(markerInterpType, chartX, chartY); temp.setFont(new Font("Avenir Book", 1, 20)); temp.setPaint(this.markerInterp); this.InterpShape.add(count, temp); this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.InterpShape.get(count)); count++; this.mouseClicked = false; }  } public void chartMouseMoved(ChartMouseEvent mouseChartEvent) { if (((((this.pickPFlag | this.pickSFlag | this.pickSSFlag) != 0 && this.HideArrivalsFlag) ? 1 : 0) | (((this.pickR1Flag | this.pickR2Flag | this.pickR3Flag) != 0 && this.HideReflectionsFlag) ? 1 : 0)) != 0) return;  for (int i = 0; i < this.ToolBar.getComponentCount(); i++) this.ToolBar.getComponent(i).setFocusable(false);  this.ToolBar.setFocusable(false); this.ToolBarPanel.setFocusable(false); fileTree.setFocusable(false); this.headerTree.setFocusable(false); this.TracePanel.setFocusable(true); this.chartPanel.setFocusable(true); ChartEntity ce = mouseChartEvent.getEntity(); if (ce instanceof org.jfree.chart.entity.XYAnnotationEntity && this.aDown && this.AutoPickFlag) for (int j = 0; j < this.autoEdgesAnnotation.size(); j++) { if (this.pickPFlag || this.pickSFlag || this.pickSSFlag || this.pickR1Flag || this.pickR2Flag || this.pickR3Flag) if (((XYLineAnnotation)this.autoEdgesAnnotation.get(j)).getURL().equalsIgnoreCase(ce.getURLText())) { int index = Integer.parseInt(ce.getURLText()); if (this.pickPFlag) { for (int l = 0; l < this.pickP.length; l++) { if (this.MATInfo.edgesOut[l][index] > 0.0D) this.pickP[l][1] = this.MATInfo.edgesOut[l][index];  }  updatePPicks(); }  if (this.pickSFlag) { for (int l = 0; l < this.pickS.length; l++) { if (this.MATInfo.edgesOut[l][index] > 0.0D) this.pickS[l][1] = this.MATInfo.edgesOut[l][index];  }  updateSPicks(); }  if (this.pickSSFlag) { for (int l = 0; l < this.pickSS.length; l++) { if (this.MATInfo.edgesOut[l][index] > 0.0D) this.pickSS[l][1] = this.MATInfo.edgesOut[l][index];  }  updateSSPicks(); }  if (this.pickR1Flag) { for (int l = 0; l < this.pickR1.length; l++) { if (this.MATInfo.edgesOut[l][index] > 0.0D) this.pickR1[l][1] = this.MATInfo.edgesOut[l][index];  }  updateR1Picks(); }  if (this.pickR2Flag) { for (int l = 0; l < this.pickR2.length; l++) { if (this.MATInfo.edgesOut[l][index] > 0.0D) this.pickR2[l][1] = this.MATInfo.edgesOut[l][index];  }  updateR2Picks(); }  if (this.pickR3Flag) { for (int l = 0; l < this.pickR3.length; l++) { if (this.MATInfo.edgesOut[l][index] > 0.0D) this.pickR3[l][1] = this.MATInfo.edgesOut[l][index];  }  updateR3Picks(); }  this.saveCheck = true; this.mouseClicked = false; break; }   }   ChartEntity entity = mouseChartEvent.getEntity(); if (entity != null && entity instanceof XYItemEntity) { XYItemEntity item = (XYItemEntity)entity; double chartY = 0.0D; if (this.DISPLAY_MODE == "Wiggle") { chartY = (1 + item.getSeriesIndex() / 3); } else { chartY = (1 + item.getSeriesIndex()); }  double chartX = Double.NaN; if (this.ctrlDown) { chartX = item.getItem() * this.MATInfo.timeSample; } else if (this.shiftDown) { chartX = Double.NaN; } else { this.lastchartX = item.getItem() * this.MATInfo.timeSample; return; }  if (this.pickPFlag) { this.pickP[(int)(chartY - 1.0D)][1] = chartX; updatePPicks(); }  if (this.pickSFlag) { this.pickS[(int)(chartY - 1.0D)][1] = chartX; updateSPicks(); }  if (this.pickSSFlag) { this.pickSS[(int)(chartY - 1.0D)][1] = chartX; updateSSPicks(); }  if (this.pickR1Flag) { this.pickR1[(int)(chartY - 1.0D)][1] = chartX; updateR1Picks(); }  if (this.pickR2Flag) { this.pickR2[(int)(chartY - 1.0D)][1] = chartX; updateR2Picks(); }  if (this.pickR3Flag) { this.pickR3[(int)(chartY - 1.0D)][1] = chartX; updateR3Picks(); }  this.saveCheck = true; }  } private void clearInterp() { for (int i = 0; i < count; i++) this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.InterpShape.get(i));  this.toInterpPX = new ArrayList<>(); this.toInterpPY = new ArrayList<>(); this.InterpShape = new ArrayList<>(); count = 0; } public void startTimer() { this.StopWatch.setText("00:00:00");
/* 7093 */     this.noSeconds = 0;
/* 7094 */     this.SimpleTimer.start(); }
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopTimer() {
/* 7099 */     this.SimpleTimer.stop();
/* 7100 */     this.noSeconds = 0;
/* 7101 */     this.StopWatch.setText("00:00:00");
/*      */   }
/*      */ 
/*      */   
/*      */   private double[] textToArray(String wellNoRecString) {
/* 7106 */     wellNoRecString = wellNoRecString.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").replaceAll(" ", "");
/*      */     
/* 7108 */     wellNoRecString = String.valueOf(wellNoRecString) + ",";
/*      */     
/* 7110 */     String[] items = wellNoRecString.split(",");
/*      */     
/* 7112 */     if (items.length == 0) {
/* 7113 */       return null;
/*      */     }
/* 7115 */     double[] returnInt = new double[items.length];
/*      */     
/* 7117 */     int count = 0; int n;
/* 7118 */     for (n = 0; n < items.length; n++) {
/*      */       
/* 7120 */       if (items[n] != "") {
/*      */         
/* 7122 */         count++;
/* 7123 */         returnInt[n] = Double.parseDouble(items[n]);
/*      */       } 
/*      */     } 
/*      */     
/* 7127 */     if (count != items.length) {
/*      */       
/* 7129 */       returnInt = new double[count];
/* 7130 */       count = -1;
/* 7131 */       for (n = 0; n < items.length; n++) {
/*      */         
/* 7133 */         if (items[n] != "") {
/*      */           
/* 7135 */           count++;
/* 7136 */           returnInt[count] = Double.parseDouble(items[n]);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 7141 */     return returnInt;
/*      */   }
/*      */ 
/*      */   
/*      */   public void traceButtonsState(boolean enableFlag) {
/* 7146 */     this.Renormalize.setEnabled(enableFlag);
/* 7147 */     this.ZoomInButton.setEnabled(enableFlag);
/* 7148 */     this.SavingButton.setEnabled(enableFlag);
/* 7149 */     this.ZoomOutButton.setEnabled(enableFlag);
/* 7150 */     this.ZoomAllButton.setEnabled(enableFlag);
/* 7151 */     this.c1.setEnabled(enableFlag);
/* 7152 */     this.c2.setEnabled(enableFlag);
/* 7153 */     this.c3.setEnabled(enableFlag);
/* 7154 */     this.PickPButton.setEnabled(enableFlag);
/* 7155 */     this.PickSButton.setEnabled(enableFlag);
/* 7156 */     this.PickSSButton.setEnabled(enableFlag);
/* 7157 */     this.PickR1Button.setEnabled(enableFlag);
/* 7158 */     this.PickR2Button.setEnabled(enableFlag);
/* 7159 */     this.PickR3Button.setEnabled(enableFlag);
/* 7160 */     if (!enableFlag) {
/*      */       
/* 7162 */       this.IconGroup.clearSelection();
/* 7163 */       this.MenuGroup.clearSelection();
/*      */     } 
/* 7165 */     this.BackButton.setEnabled(enableFlag);
/* 7166 */     this.ForwardButton.setEnabled(enableFlag);
/* 7167 */     this.TrimButton.setEnabled(enableFlag);
/* 7168 */     this.SaveFile.setEnabled(enableFlag);
/* 7169 */     this.ZoomIn.setEnabled(enableFlag);
/* 7170 */     this.ZoomOut.setEnabled(enableFlag);
/* 7171 */     this.ZoomAll.setEnabled(enableFlag);
/* 7172 */     this.Comp1.setEnabled(enableFlag);
/* 7173 */     this.Comp2.setEnabled(enableFlag);
/* 7174 */     this.Comp3.setEnabled(enableFlag);
/* 7175 */     this.PickPMenuItem.setEnabled(enableFlag);
/* 7176 */     this.PickSMenuItem.setEnabled(enableFlag);
/* 7177 */     this.PickSSMenuItem.setEnabled(enableFlag);
/* 7178 */     this.PickR1MenuItem.setEnabled(enableFlag);
/* 7179 */     this.PickR2MenuItem.setEnabled(enableFlag);
/* 7180 */     this.PickR3MenuItem.setEnabled(enableFlag);
/* 7181 */     this.PickPDelete.setEnabled(enableFlag);
/* 7182 */     this.PickSDelete.setEnabled(enableFlag);
/* 7183 */     this.PickSSDelete.setEnabled(enableFlag);
/* 7184 */     this.PickArrivalsDelete.setEnabled(enableFlag);
/* 7185 */     this.PickR1Delete.setEnabled(enableFlag);
/* 7186 */     this.PickR2Delete.setEnabled(enableFlag);
/* 7187 */     this.PickR3Delete.setEnabled(enableFlag);
/* 7188 */     this.PickReflectionsDelete.setEnabled(enableFlag);
/* 7189 */     this.NextFile.setEnabled(enableFlag);
/* 7190 */     this.PrevFile.setEnabled(enableFlag);
/* 7191 */     this.Undo.setEnabled(enableFlag);
/* 7192 */     this.TrimFileButton.setEnabled(enableFlag);
/* 7193 */     this.HideArrivals.setEnabled(enableFlag);
/* 7194 */     this.HideReflections.setEnabled(enableFlag);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void trimModetraceButtonsState(boolean enableFlag) {
/* 7200 */     this.Renormalize.setEnabled(enableFlag);
/* 7201 */     this.ZoomInButton.setEnabled(enableFlag);
/* 7202 */     this.SavingButton.setEnabled(enableFlag);
/* 7203 */     this.ZoomOutButton.setEnabled(enableFlag);
/* 7204 */     this.ZoomAllButton.setEnabled(enableFlag);
/* 7205 */     this.c1.setEnabled(enableFlag);
/* 7206 */     this.c2.setEnabled(enableFlag);
/* 7207 */     this.c3.setEnabled(enableFlag);
/* 7208 */     this.PickPButton.setEnabled(enableFlag);
/* 7209 */     this.PickSButton.setEnabled(enableFlag);
/* 7210 */     this.PickSSButton.setEnabled(enableFlag);
/* 7211 */     this.PickR1Button.setEnabled(enableFlag);
/* 7212 */     this.PickR2Button.setEnabled(enableFlag);
/* 7213 */     this.PickR3Button.setEnabled(enableFlag);
/* 7214 */     this.MergeFileButton.setEnabled(enableFlag);
/* 7215 */     if (!enableFlag) {
/*      */       
/* 7217 */       this.IconGroup.clearSelection();
/* 7218 */       this.MenuGroup.clearSelection();
/*      */     } 
/* 7220 */     this.TrimButton.setEnabled(enableFlag);
/* 7221 */     this.SaveFile.setEnabled(enableFlag);
/* 7222 */     this.ZoomIn.setEnabled(enableFlag);
/* 7223 */     this.ZoomOut.setEnabled(enableFlag);
/* 7224 */     this.ZoomAll.setEnabled(enableFlag);
/* 7225 */     this.Comp1.setEnabled(enableFlag);
/* 7226 */     this.Comp2.setEnabled(enableFlag);
/* 7227 */     this.Comp3.setEnabled(enableFlag);
/* 7228 */     this.PickPMenuItem.setEnabled(enableFlag);
/* 7229 */     this.PickSMenuItem.setEnabled(enableFlag);
/* 7230 */     this.PickSSMenuItem.setEnabled(enableFlag);
/* 7231 */     this.PickR1MenuItem.setEnabled(enableFlag);
/* 7232 */     this.PickR2MenuItem.setEnabled(enableFlag);
/* 7233 */     this.PickR3MenuItem.setEnabled(enableFlag);
/* 7234 */     this.PickPDelete.setEnabled(enableFlag);
/* 7235 */     this.PickSDelete.setEnabled(enableFlag);
/* 7236 */     this.PickSSDelete.setEnabled(enableFlag);
/* 7237 */     this.PickArrivalsDelete.setEnabled(enableFlag);
/* 7238 */     this.PickR1Delete.setEnabled(enableFlag);
/* 7239 */     this.PickR2Delete.setEnabled(enableFlag);
/* 7240 */     this.PickR3Delete.setEnabled(enableFlag);
/* 7241 */     this.PickReflectionsDelete.setEnabled(enableFlag);
/* 7242 */     this.Undo.setEnabled(enableFlag);
/* 7243 */     this.TrimFileButton.setEnabled(enableFlag);
/* 7244 */     this.DiffLocSettings.setEnabled(enableFlag);
/* 7245 */     this.HideArrivals.setEnabled(enableFlag);
/* 7246 */     this.HideReflections.setEnabled(enableFlag);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updatePPicks() {
/* 7252 */     this.saveCheck = true;
/*      */     
/* 7254 */     this.prevP = new double[this.pickP.length][(this.pickP[0]).length];
/*      */     int i;
/* 7256 */     for (i = 0; i < this.pickP.length; i++) {
/*      */       
/* 7258 */       this.prevP[i][0] = (i + 1);
/* 7259 */       this.prevP[i][1] = ((XYTextAnnotation)this.pShape.get(i)).getX();
/*      */     } 
/* 7261 */     this.prevWaveType = "P";
/*      */     
/* 7263 */     for (i = 0; i < this.pickP.length; i++) {
/* 7264 */       if (i < this.pickP.length - 1) {
/* 7265 */         this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.pAnnot.get(i));
/* 7266 */         this.pAnnot.set(i, new XYLineAnnotation(this.pickP[i][1], this.pickP[i][0], this.pickP[i + 1][1], this.pickP[i + 1][0], 
/* 7267 */               new BasicStroke(1.5F, 1, 1), this.lineP));
/* 7268 */         this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.pAnnot.get(i));
/*      */       } 
/*      */       
/* 7271 */       this.table.getModel().setValueAt(String.format("%.2f", new Object[] { Double.valueOf(this.pickP[i][1]) }), i, 1);
/*      */       
/* 7273 */       this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.pShape.get(i));
/*      */       
/* 7275 */       XYTextAnnotation temp = new XYTextAnnotation("+", this.pickP[i][1], this.pickP[i][0]);
/* 7276 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 7277 */       temp.setPaint(this.markerP);
/* 7278 */       this.pShape.set(i, temp);
/*      */       
/* 7280 */       this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.pShape.get(i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSPicks() {
/* 7287 */     this.prevS = new double[this.pickS.length][(this.pickS[0]).length];
/*      */     int i;
/* 7289 */     for (i = 0; i < this.pickS.length; i++) {
/*      */       
/* 7291 */       this.prevS[i][0] = (i + 1);
/* 7292 */       this.prevS[i][1] = ((XYTextAnnotation)this.sShape.get(i)).getX();
/*      */     } 
/* 7294 */     this.prevWaveType = "S";
/*      */     
/* 7296 */     this.saveCheck = true;
/* 7297 */     for (i = 0; i < this.pickS.length; i++) {
/*      */       
/* 7299 */       if (i < this.pickS.length - 1) {
/*      */         
/* 7301 */         this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.sAnnot.get(i));
/* 7302 */         this.sAnnot.set(i, new XYLineAnnotation(this.pickS[i][1], this.pickS[i][0], this.pickS[i + 1][1], this.pickS[i + 1][0], 
/* 7303 */               new BasicStroke(1.5F, 1, 1), this.lineS));
/* 7304 */         this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.sAnnot.get(i));
/*      */       } 
/*      */       
/* 7307 */       this.table.getModel().setValueAt(String.format("%.2f", new Object[] { Double.valueOf(this.pickS[i][1]) }), i, 2);
/*      */       
/* 7309 */       this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.sShape.get(i));
/*      */       
/* 7311 */       XYTextAnnotation temp = new XYTextAnnotation("x", this.pickS[i][1], this.pickS[i][0]);
/* 7312 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 7313 */       temp.setPaint(this.markerS);
/* 7314 */       this.sShape.set(i, temp);
/*      */       
/* 7316 */       this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.sShape.get(i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSSPicks() {
/* 7323 */     this.prevSS = new double[this.pickSS.length][(this.pickSS[0]).length];
/*      */     int i;
/* 7325 */     for (i = 0; i < this.pickSS.length; i++) {
/*      */       
/* 7327 */       this.prevSS[i][0] = (i + 1);
/* 7328 */       this.prevSS[i][1] = ((XYTextAnnotation)this.ssShape.get(i)).getX();
/*      */     } 
/* 7330 */     this.prevWaveType = "SS";
/*      */ 
/*      */     
/* 7333 */     this.saveCheck = true;
/* 7334 */     for (i = 0; i < this.pickSS.length; i++) {
/*      */       
/* 7336 */       if (i < this.pickSS.length - 1) {
/*      */         
/* 7338 */         this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.ssAnnot.get(i));
/* 7339 */         this.ssAnnot.set(i, new XYLineAnnotation(this.pickSS[i][1], this.pickSS[i][0], this.pickSS[i + 1][1], this.pickSS[i + 1][0], 
/* 7340 */               new BasicStroke(1.5F, 1, 1), this.lineSS));
/* 7341 */         this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.ssAnnot.get(i));
/*      */       } 
/*      */       
/* 7344 */       this.table.getModel().setValueAt(String.format("%.2f", new Object[] { Double.valueOf(this.pickSS[i][1]) }), i, 3);
/*      */       
/* 7346 */       this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.ssShape.get(i));
/*      */       
/* 7348 */       XYTextAnnotation temp = new XYTextAnnotation("o", this.pickSS[i][1], this.pickSS[i][0]);
/* 7349 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 7350 */       temp.setPaint(this.markerSS);
/*      */       
/* 7352 */       this.ssShape.set(i, temp);
/*      */       
/* 7354 */       this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.ssShape.get(i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateR1Picks() {
/* 7361 */     this.prevR1 = new double[this.pickR1.length][(this.pickR1[0]).length];
/*      */     int i;
/* 7363 */     for (i = 0; i < this.pickR1.length; i++) {
/*      */       
/* 7365 */       this.prevR1[i][0] = (i + 1);
/* 7366 */       this.prevR1[i][1] = ((XYTextAnnotation)this.r1Shape.get(i)).getX();
/*      */     } 
/* 7368 */     this.prevWaveType = "R1";
/*      */     
/* 7370 */     this.saveCheck = true;
/* 7371 */     for (i = 0; i < this.pickR1.length; i++) {
/*      */       
/* 7373 */       if (i < this.pickR1.length - 1) {
/*      */         
/* 7375 */         this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.r1Annot.get(i));
/* 7376 */         this.r1Annot.set(i, new XYLineAnnotation(this.pickR1[i][1], this.pickR1[i][0], this.pickR1[i + 1][1], this.pickR1[i + 1][0], 
/* 7377 */               new BasicStroke(1.5F, 1, 1), this.lineR1));
/* 7378 */         this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r1Annot.get(i));
/*      */       } 
/*      */       
/* 7381 */       this.table.getModel().setValueAt(String.format("%.2f", new Object[] { Double.valueOf(this.pickR1[i][1]) }), i, 4);
/*      */       
/* 7383 */       this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.r1Shape.get(i));
/*      */       
/* 7385 */       XYTextAnnotation temp = new XYTextAnnotation("x", this.pickR1[i][1], this.pickR1[i][0]);
/* 7386 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 7387 */       temp.setPaint(this.markerR1);
/* 7388 */       this.r1Shape.set(i, temp);
/*      */       
/* 7390 */       this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r1Shape.get(i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateR2Picks() {
/* 7397 */     this.prevR2 = new double[this.pickR2.length][(this.pickR2[0]).length];
/*      */     int i;
/* 7399 */     for (i = 0; i < this.pickR2.length; i++) {
/*      */       
/* 7401 */       this.prevR2[i][0] = (i + 1);
/* 7402 */       this.prevR2[i][1] = ((XYTextAnnotation)this.r2Shape.get(i)).getX();
/*      */     } 
/* 7404 */     this.prevWaveType = "R2";
/*      */     
/* 7406 */     this.saveCheck = true;
/* 7407 */     for (i = 0; i < this.pickR2.length; i++) {
/*      */       
/* 7409 */       if (i < this.pickR2.length - 1) {
/*      */         
/* 7411 */         this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.r2Annot.get(i));
/* 7412 */         this.r2Annot.set(i, new XYLineAnnotation(this.pickR2[i][1], this.pickR2[i][0], this.pickR2[i + 1][1], this.pickR2[i + 1][0], 
/* 7413 */               new BasicStroke(1.5F, 1, 1), this.lineR2));
/* 7414 */         this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r2Annot.get(i));
/*      */       } 
/*      */       
/* 7417 */       this.table.getModel().setValueAt(String.format("%.2f", new Object[] { Double.valueOf(this.pickR2[i][1]) }), i, 5);
/*      */       
/* 7419 */       this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.r2Shape.get(i));
/*      */       
/* 7421 */       XYTextAnnotation temp = new XYTextAnnotation("x", this.pickR2[i][1], this.pickR2[i][0]);
/* 7422 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 7423 */       temp.setPaint(this.markerR2);
/* 7424 */       this.r2Shape.set(i, temp);
/*      */       
/* 7426 */       this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r2Shape.get(i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateR3Picks() {
/* 7433 */     this.prevR3 = new double[this.pickR3.length][(this.pickR3[0]).length];
/*      */     int i;
/* 7435 */     for (i = 0; i < this.pickR3.length; i++) {
/*      */       
/* 7437 */       this.prevR3[i][0] = (i + 1);
/* 7438 */       this.prevR3[i][1] = ((XYTextAnnotation)this.r3Shape.get(i)).getX();
/*      */     } 
/* 7440 */     this.prevWaveType = "R3";
/*      */     
/* 7442 */     this.saveCheck = true;
/* 7443 */     for (i = 0; i < this.pickR3.length; i++) {
/*      */       
/* 7445 */       if (i < this.pickR3.length - 1) {
/*      */         
/* 7447 */         this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.r3Annot.get(i));
/* 7448 */         this.r3Annot.set(i, new XYLineAnnotation(this.pickR3[i][1], this.pickR3[i][0], this.pickR3[i + 1][1], this.pickR3[i + 1][0], 
/* 7449 */               new BasicStroke(1.5F, 1, 1), this.lineR3));
/* 7450 */         this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r3Annot.get(i));
/*      */       } 
/*      */       
/* 7453 */       this.table.getModel().setValueAt(String.format("%.2f", new Object[] { Double.valueOf(this.pickR3[i][1]) }), i, 6);
/*      */       
/* 7455 */       this.tracePlot.getRenderer().removeAnnotation((XYAnnotation)this.r3Shape.get(i));
/*      */       
/* 7457 */       XYTextAnnotation temp = new XYTextAnnotation("x", this.pickR3[i][1], this.pickR3[i][0]);
/* 7458 */       temp.setFont(new Font("Avenir Book", 1, 20));
/* 7459 */       temp.setPaint(this.markerR3);
/* 7460 */       this.r3Shape.set(i, temp);
/* 7461 */       this.tracePlot.getRenderer().addAnnotation((XYAnnotation)this.r3Shape.get(i));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String createNodePath(TreeNode[] treePath) {
/* 7468 */     StringBuilder sb = new StringBuilder();
/* 7469 */     sb.append(InputFolderPath);
/* 7470 */     TreeNode[] arrayOfTreeNode = treePath;
/*      */     
/* 7472 */     for (int i = 0; i < arrayOfTreeNode.length; i++) {
/*      */       
/* 7474 */       if (i > 1)
/* 7475 */         sb.append(File.separatorChar).append(arrayOfTreeNode[i].toString()); 
/*      */     } 
/* 7477 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateTree() {
/* 7483 */     String currentSelection = "";
/*      */ 
/*      */     
/*      */     try {
/* 7487 */       currentSelection = fileTree.getLastSelectedPathComponent().toString();
/*      */     }
/* 7489 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7495 */     DefaultMutableTreeNode root1 = new DefaultMutableTreeNode();
/* 7496 */     getList(root1, new File(InputFolderPath));
/*      */     
/* 7498 */     fileTree.removeAll();
/*      */     
/* 7500 */     DefaultTreeModel model = (DefaultTreeModel)fileTree.getModel();
/* 7501 */     model.setRoot(root1);
/* 7502 */     model.reload(root1);
/* 7503 */     selectNode(currentSelection);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void selectNode(String nodeStr) {
/* 7509 */     DefaultMutableTreeNode node = null;
/* 7510 */     DefaultMutableTreeNode m_rootNode = (DefaultMutableTreeNode)fileTree.getModel().getRoot();
/* 7511 */     Enumeration<DefaultMutableTreeNode> e = (Enumeration)m_rootNode.depthFirstEnumeration();
/* 7512 */     while (e.hasMoreElements()) {
/*      */       
/* 7514 */       node = e.nextElement();
/* 7515 */       if (node.toString().equalsIgnoreCase(nodeStr)) {
/*      */         
/* 7517 */         fileTree.clearSelection();
/* 7518 */         fileTree.setSelectionPath(getPath(node));
/* 7519 */         fileTree.scrollPathToVisible(getPath(node));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double getMax(double[] inputArray, double predecidedMax) {
/* 7527 */     for (int i = 1; i < inputArray.length; i++) {
/* 7528 */       if (inputArray[i] > predecidedMax) {
/* 7529 */         predecidedMax = inputArray[i];
/*      */       }
/*      */     } 
/* 7532 */     return predecidedMax;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double getMin(double[] inputArray, double predecidedMin) {
/* 7538 */     for (int i = 1; i < inputArray.length; i++) {
/* 7539 */       if (inputArray[i] < predecidedMin) {
/* 7540 */         predecidedMin = inputArray[i];
/*      */       }
/*      */     } 
/* 7543 */     return predecidedMin;
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
/*      */   private void generatePropertiesFile() {
/* 7555 */     File temp = new File(currentFilePath);
/* 7556 */     this.outputProperties = new Properties();
/* 7557 */     this.outputProperties.setProperty("CurrentFolder", InputFolderPath);
/* 7558 */     this.outputProperties.setProperty("CurrentFile", temp.getName());
/*      */     
/* 7560 */     this.outputProperties.setProperty("inputFolderSaving", this.inputFolderSaving);
/* 7561 */     this.outputProperties.setProperty("outputFolderSaving", this.outputFolderSaving);
/*      */     
/* 7563 */     this.outputProperties.setProperty("BP", String.valueOf(this.filterSettings.BPFlag));
/* 7564 */     this.outputProperties.setProperty("BP Low", String.valueOf(this.filterSettings.BPLow));
/* 7565 */     this.outputProperties.setProperty("BP High", String.valueOf(this.filterSettings.BPHigh));
/* 7566 */     this.outputProperties.setProperty("BP Edge", String.valueOf(this.filterSettings.BPEdge));
/*      */     
/* 7568 */     this.outputProperties.setProperty("LP", String.valueOf(this.filterSettings.LPFlag));
/* 7569 */     this.outputProperties.setProperty("LPExp", String.valueOf(this.filterSettings.LPExp));
/* 7570 */     this.outputProperties.setProperty("LPThresh", String.valueOf(this.filterSettings.LPThresh));
/* 7571 */     this.outputProperties.setProperty("LPWindow", String.valueOf(this.filterSettings.LPWindow));
/*      */ 
/*      */     
/*      */     try {
/* 7575 */       String location = System.getProperty("user.dir");
/* 7576 */       Path p1 = Paths.get(location, new String[0]);
/* 7577 */       Path p2 = Paths.get("PickingHistory.properties", new String[0]);
/*      */       
/* 7579 */       this.outputFileStream = new FileOutputStream((new File(p1.toString(), p2.toString())).toString());
/* 7580 */       this.outputProperties.store(this.outputFileStream, "Properties file generated from Java program");
/* 7581 */       this.outputFileStream.close();
/*      */     }
/* 7583 */     catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void deleteNode(DefaultMutableTreeNode m_rootNode, String nodeStr) {
/* 7591 */     DefaultMutableTreeNode node = null;
/*      */     
/* 7593 */     Enumeration<DefaultMutableTreeNode> e = (Enumeration)m_rootNode.children();
/* 7594 */     while (e.hasMoreElements()) {
/*      */       
/* 7596 */       node = e.nextElement();
/* 7597 */       if (node.toString().equalsIgnoreCase(nodeStr))
/*      */       {
/* 7599 */         node.removeFromParent();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static TreePath getPath(TreeNode treeNode) {
/* 7605 */     List<Object> nodes = new ArrayList();
/* 7606 */     if (treeNode != null) {
/* 7607 */       nodes.add(treeNode);
/* 7608 */       treeNode = treeNode.getParent();
/* 7609 */       while (treeNode != null) {
/* 7610 */         nodes.add(0, treeNode);
/* 7611 */         treeNode = treeNode.getParent();
/*      */       } 
/*      */     } 
/*      */     
/* 7615 */     return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
/*      */   }
/*      */ 
/*      */   
/*      */   private static void getList(DefaultMutableTreeNode node, File f) {
/* 7620 */     String[] exts = { "mat" };
/*      */     
/* 7622 */     if (!f.isHidden())
/*      */     {
/* 7624 */       if (!f.isDirectory()) {
/*      */         
/* 7626 */         if (f.getName().endsWith("mat")) {
/*      */           try
/*      */           {
/*      */ 
/*      */             
/* 7631 */             if ((((minMisfit != "") ? 1 : 0) | ((maxMisfit != "") ? 1 : 0) | ((minDepth != "") ? 1 : 0) | ((maxDepth != "") ? 1 : 0) | ((minEasting != "") ? 1 : 0) | ((minNorthing != "") ? 1 : 0) | ((maxEasting != "") ? 1 : 0) | ((maxNorthing != "") ? 1 : 0)) != 0)
/*      */             {
/*      */               
/* 7634 */               readMAT MATInfo = new readMAT(f.getAbsolutePath());
/*      */               
/* 7636 */               if (MATInfo.inverted)
/*      */               {
/* 7638 */                 if (MATInfo.misfit > Double.parseDouble(minMisfit) && MATInfo.misfit < Double.parseDouble(maxMisfit) && 
/* 7639 */                   MATInfo.depth > Double.parseDouble(minDepth) && MATInfo.depth < Double.parseDouble(maxDepth) && 
/* 7640 */                   MATInfo.Easting > Double.parseDouble(minEasting) && MATInfo.Easting < Double.parseDouble(maxEasting) && 
/* 7641 */                   MATInfo.Northing > Double.parseDouble(minNorthing) && MATInfo.Northing > Double.parseDouble(maxNorthing))
/*      */                 {
/* 7643 */                   DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.getName());
/* 7644 */                   node.add(child);
/*      */                 
/*      */                 }
/*      */ 
/*      */               
/*      */               }
/*      */             }
/*      */             else
/*      */             {
/* 7653 */               DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.getName());
/* 7654 */               node.add(child);
/*      */             }
/*      */           
/*      */           }
/* 7658 */           catch (IOException e)
/*      */           {
/* 7660 */             e.printStackTrace();
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 7670 */         DefaultMutableTreeNode child = new DefaultMutableTreeNode(f.getName());
/* 7671 */         FilenameFilter fileNameFilter = new FilenameFilter()
/*      */           {
/*      */             
/*      */             public boolean accept(File dir, String name)
/*      */             {
/* 7676 */               return true;
/*      */             }
/*      */           };
/*      */ 
/*      */ 
/*      */         
/* 7682 */         File[] fList = f.listFiles(fileNameFilter);
/* 7683 */         Arrays.sort((Object[])fList);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 7688 */         node.add(child);
/*      */         
/* 7690 */         for (int i = 0; i < fList.length; i++) {
/* 7691 */           getList(child, fList[i]);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void emptyPanels() {
/* 7699 */     this.FilePanel.removeAll(); this.FilePanel.updateUI();
/* 7700 */     this.TracePanel.removeAll(); this.TracePanel.updateUI();
/*      */     
/* 7702 */     JLabel HeaderLabel = new JLabel("No info to display.");
/* 7703 */     HeaderLabel.setFont(new Font("Avenir Book", 0, 11));
/*      */     
/* 7705 */     this.TimePicksPanel.removeAll();
/* 7706 */     this.TimePicksPanel.add(HeaderLabel);
/* 7707 */     this.TimePicksPanel.updateUI();
/*      */     
/* 7709 */     this.FileHeaderPanel.removeAll();
/* 7710 */     this.FileHeaderPanel.add(HeaderLabel);
/* 7711 */     this.FileHeaderPanel.updateUI();
/*      */     
/* 7713 */     this.AutoPickViewPanel.removeAll();
/* 7714 */     this.AutoPickViewPanel.updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void emptyTracePickPanels() {
/* 7721 */     this.TracePanel.removeAll(); this.TracePanel.updateUI();
/*      */     
/* 7723 */     JLabel HeaderLabel = new JLabel("No info to display.");
/* 7724 */     HeaderLabel.setFont(new Font("Avenir Book", 0, 11));
/*      */     
/* 7726 */     this.TimePicksPanel.removeAll();
/* 7727 */     this.TimePicksPanel.add(HeaderLabel);
/* 7728 */     this.TimePicksPanel.updateUI();
/*      */     
/* 7730 */     this.FileHeaderPanel.removeAll();
/* 7731 */     this.FileHeaderPanel.add(HeaderLabel);
/* 7732 */     this.FileHeaderPanel.updateUI();
/*      */     
/* 7734 */     this.AutoPickViewPanel.removeAll();
/* 7735 */     this.AutoPickViewPanel.updateUI();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void valueChanged(TreeSelectionEvent e) {
/*      */     try {
/* 7744 */       TreePath x = e.getNewLeadSelectionPath();
/* 7745 */       if (x != null) {
/*      */         
/* 7747 */         DefaultMutableTreeNode node = (DefaultMutableTreeNode)x.getLastPathComponent();
/*      */         
/* 7749 */         if (node.isLeaf())
/*      */         {
/* 7751 */           this.FileNumberLabel.setText("File " + Integer.toString(node.getParent().getIndex(node) + 1) + 
/* 7752 */               " out of " + Integer.toString(node.getParent().getChildCount()));
/* 7753 */           this.FileNumberLabel.updateUI();
/*      */         }
/*      */         else
/*      */         {
/* 7757 */           this.FileNumberLabel.setText("");
/* 7758 */           this.FileNumberLabel.updateUI();
/*      */         }
/*      */       
/*      */       } 
/* 7762 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetCounter() {
/* 7770 */     this.FileNumberLabel.setText("Lets pick some files today!");
/* 7771 */     this.FileNumberLabel.updateUI();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String createFilePath(TreePath treePath) {
/* 7776 */     StringBuilder sb = new StringBuilder();
/* 7777 */     sb.append(InputFolderPath);
/* 7778 */     Object[] nodes = treePath.getPath();
/*      */     
/* 7780 */     for (int i = 0; i < nodes.length; i++) {
/*      */       
/* 7782 */       if (i > 1)
/* 7783 */         sb.append(File.separatorChar).append(nodes[i].toString()); 
/*      */     } 
/* 7785 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dragGestureRecognized(DragGestureEvent dge) {
/* 7795 */     if (fileTree.getSelectionCount() < 1) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/* 7800 */       this.oldNode = new DefaultMutableTreeNode[fileTree.getSelectionCount()];
/*      */       
/* 7802 */       TreePath[] tempPath = fileTree.getSelectionPaths();
/*      */       int j;
/* 7804 */       for (j = 0; j < this.oldNode.length; j++) {
/* 7805 */         this.oldNode[j] = (DefaultMutableTreeNode)tempPath[j].getLastPathComponent();
/*      */       }
/*      */       
/* 7808 */       for (j = 0; j < this.oldNode.length; j++) {
/*      */         
/* 7810 */         if (this.oldNode[j].getParent().toString() != this.oldNode[0].getParent().toString()) {
/*      */           return;
/*      */         }
/*      */       } 
/* 7814 */     } catch (Exception e) {
/*      */       
/* 7816 */       JOptionPane.showMessageDialog(new JFrame("Error"), "Something went wrong with File Tree Sync. Updating JTree.");
/* 7817 */       updateTree();
/*      */     } 
/*      */     
/* 7820 */     TreePath[] path = fileTree.getSelectionPaths();
/*      */     
/* 7822 */     for (int i = 0; i < path.length; i++) {
/*      */       
/* 7824 */       this.transferable = new TransferableTreeNode(path[i]);
/*      */ 
/*      */       
/*      */       try {
/* 7828 */         this.source.startDrag(dge, DragSource.DefaultMoveNoDrop, this.transferable, this);
/*      */       }
/* 7830 */       catch (Exception e) {
/*      */         
/* 7832 */         this.source = new DragSource();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dragEnter(DragSourceDragEvent dsde) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dragExit(DragSourceEvent dse) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dragOver(DragSourceDragEvent dsde) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dropActionChanged(DragSourceDragEvent dsde) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dragDropEnd(DragSourceDropEvent dsde) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TreeNode getNodeForEvent(DropTargetDragEvent dtde) {
/* 7874 */     Point p = dtde.getLocation();
/* 7875 */     DropTargetContext dtc = dtde.getDropTargetContext();
/* 7876 */     JTree tree = (JTree)dtc.getComponent();
/* 7877 */     TreePath path = tree.getClosestPathForLocation(p.x, p.y);
/* 7878 */     tree.getSelectionModel().setSelectionPath(path);
/* 7879 */     return (TreeNode)path.getLastPathComponent();
/*      */   }
/*      */ 
/*      */   
/*      */   public void dragEnter(DropTargetDragEvent dtde) {
/* 7884 */     TreeNode node = getNodeForEvent(dtde);
/* 7885 */     DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode)node;
/*      */     
/* 7887 */     if ((node.toString().endsWith("mat") | (((tempNode.getUserObjectPath()).length > (this.oldNode[0].getUserObjectPath()).length) ? 1 : 0) | (
/* 7888 */       (tempNode.toString() == this.oldNode[0].toString()) ? 1 : 0)) != 0) {
/* 7889 */       dtde.rejectDrag();
/*      */     }
/*      */     else {
/*      */       
/* 7893 */       dtde.acceptDrag(dtde.getDropAction());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dragOver(DropTargetDragEvent dtde) {
/* 7900 */     TreeNode node = getNodeForEvent(dtde);
/* 7901 */     DefaultMutableTreeNode tempNode = (DefaultMutableTreeNode)node;
/*      */     
/* 7903 */     if ((node.toString().endsWith("mat") | (
/* 7904 */       ((tempNode.getUserObjectPath()).length > (this.oldNode[0].getUserObjectPath()).length) ? 1 : 0) | (
/* 7905 */       (tempNode.toString() == this.oldNode[0].toString()) ? 1 : 0)) != 0) {
/*      */       
/* 7907 */       dtde.rejectDrag();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 7912 */       dtde.acceptDrag(dtde.getDropAction());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void dragExit(DropTargetEvent dte) {}
/*      */ 
/*      */   
/*      */   public void dropActionChanged(DropTargetDragEvent dtde) {}
/*      */ 
/*      */   
/*      */   public void drop(DropTargetDropEvent dtde) {
/* 7924 */     Point pt = dtde.getLocation();
/* 7925 */     DropTargetContext dtc = dtde.getDropTargetContext();
/* 7926 */     TreePath parentpath = fileTree.getClosestPathForLocation(pt.x, pt.y);
/* 7927 */     DefaultMutableTreeNode parent = (DefaultMutableTreeNode)parentpath.getLastPathComponent();
/*      */     
/* 7929 */     if ((parent.toString().endsWith("mat") | (
/* 7930 */       ((parent.getUserObjectPath()).length > (this.oldNode[0].getUserObjectPath()).length) ? 1 : 0) | (
/* 7931 */       (parent.toString() == this.oldNode[0].toString()) ? 1 : 0)) != 0) {
/*      */       
/* 7933 */       dtde.rejectDrop();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*      */     try {
/* 7939 */       Transferable tr = dtde.getTransferable();
/* 7940 */       DataFlavor[] flavors = tr.getTransferDataFlavors();
/* 7941 */       DefaultTreeModel model = (DefaultTreeModel)fileTree.getModel();
/* 7942 */       DefaultMutableTreeNode x1 = (DefaultMutableTreeNode)model.getRoot();
/*      */       
/* 7944 */       for (int i = 0; i < flavors.length; i++) {
/*      */         
/* 7946 */         if (tr.isDataFlavorSupported(flavors[i])) {
/*      */ 
/*      */           
/* 7949 */           dtde.acceptDrop(dtde.getDropAction());
/*      */           
/* 7951 */           TreePath p = (TreePath)tr.getTransferData(flavors[i]);
/*      */ 
/*      */           
/* 7954 */           for (int j = 0; j < this.oldNode.length; j++) {
/*      */             
/* 7956 */             File oldFile = new File(createNodePath(this.oldNode[j].getPath()));
/* 7957 */             File newFile = new File(createNodePath(parent.getPath()), oldFile.getName());
/* 7958 */             File newFilePath = new File(newFile.getAbsolutePath().substring(0, newFile.getAbsolutePath().lastIndexOf(File.separator)));
/*      */             
/* 7960 */             if (!newFilePath.exists()) {
/* 7961 */               newFilePath.mkdir();
/*      */             }
/*      */             
/* 7964 */             if (newFile.exists()) {
/*      */               
/* 7966 */               deleteNode(parent, oldFile.getName());
/* 7967 */               if ((newFile.listFiles()).length > 0)
/*      */               {
/* 7969 */                 for (int k = 0; k < (newFile.listFiles()).length; i++) {
/*      */                   
/* 7971 */                   newFile.listFiles()[k].setWritable(true);
/* 7972 */                   newFile.listFiles()[k].delete();
/*      */                 } 
/*      */               }
/* 7975 */               newFile.setWritable(true);
/* 7976 */               newFile.delete();
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 7982 */             TreePath p1 = getPath(this.oldNode[j]);
/* 7983 */             DefaultMutableTreeNode oldNode0 = (DefaultMutableTreeNode)p1.getLastPathComponent();
/* 7984 */             model.insertNodeInto(oldNode0, parent, 0);
/*      */ 
/*      */             
/*      */             try {
/* 7988 */               if (oldFile.isDirectory()) {
/* 7989 */                 FileUtils.moveDirectory(oldFile, newFile);
/*      */               } else {
/* 7991 */                 Files.move(oldFile.toPath(), newFile.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
/*      */               }
/*      */             
/* 7994 */             } catch (Exception e1) {
/*      */ 
/*      */               
/* 7997 */               JOptionPane.showMessageDialog(new JFrame("Error"), "Something went wrong with File Tree Sync. Updating JTree.");
/* 7998 */               fileTree.setSelectionPath(getPath(this.oldNode[0].getParent()));
/* 7999 */               updateTree();
/* 8000 */               dtde.rejectDrop();
/*      */             } 
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 8006 */           dtde.dropComplete(true);
/*      */ 
/*      */ 
/*      */           
/* 8010 */           updateTree();
/* 8011 */           selectNode(this.oldNode[0].getParent().toString());
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 8016 */     } catch (Exception exception) {}
/*      */   }
/*      */   
/*      */   public void keyTyped(KeyEvent e) {}
/*      */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/boreholeseismic/gui/PickingWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */