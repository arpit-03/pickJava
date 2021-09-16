/*     */ package org.jfree.ui.about;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Image;
/*     */ import java.util.List;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.border.Border;
/*     */ import org.jfree.util.ResourceBundleWrapper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AboutFrame
/*     */   extends JFrame
/*     */ {
/*  78 */   public static final Dimension PREFERRED_SIZE = new Dimension(560, 360);
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final Border STANDARD_BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceBundle resources;
/*     */ 
/*     */ 
/*     */   
/*     */   private String application;
/*     */ 
/*     */ 
/*     */   
/*     */   private String version;
/*     */ 
/*     */ 
/*     */   
/*     */   private String copyright;
/*     */ 
/*     */ 
/*     */   
/*     */   private String info;
/*     */ 
/*     */   
/*     */   private Image logo;
/*     */ 
/*     */   
/*     */   private List contributors;
/*     */ 
/*     */   
/*     */   private String licence;
/*     */ 
/*     */ 
/*     */   
/*     */   public AboutFrame(String title, ProjectInfo project) {
/* 116 */     this(title, project
/* 117 */         .getName(), "Version " + project
/* 118 */         .getVersion(), project
/* 119 */         .getInfo(), project
/* 120 */         .getLogo(), project
/* 121 */         .getCopyright(), project
/* 122 */         .getLicenceText(), project
/* 123 */         .getContributors(), project);
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
/*     */   public AboutFrame(String title, String application, String version, String info, Image logo, String copyright, String licence, List contributors, ProjectInfo project) {
/* 149 */     super(title);
/*     */     
/* 151 */     this.application = application;
/* 152 */     this.version = version;
/* 153 */     this.copyright = copyright;
/* 154 */     this.info = info;
/* 155 */     this.logo = logo;
/* 156 */     this.contributors = contributors;
/* 157 */     this.licence = licence;
/*     */     
/* 159 */     String baseName = "org.jfree.ui.about.resources.AboutResources";
/* 160 */     this.resources = ResourceBundleWrapper.getBundle("org.jfree.ui.about.resources.AboutResources");
/*     */     
/* 162 */     JPanel content = new JPanel(new BorderLayout());
/* 163 */     content.setBorder(STANDARD_BORDER);
/*     */     
/* 165 */     JTabbedPane tabs = createTabs(project);
/* 166 */     content.add(tabs);
/* 167 */     setContentPane(content);
/*     */     
/* 169 */     pack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize() {
/* 179 */     return PREFERRED_SIZE;
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
/*     */   private JTabbedPane createTabs(ProjectInfo project) {
/* 191 */     JTabbedPane tabs = new JTabbedPane();
/*     */     
/* 193 */     JPanel aboutPanel = createAboutPanel(project);
/* 194 */     aboutPanel.setBorder(STANDARD_BORDER);
/* 195 */     String aboutTab = this.resources.getString("about-frame.tab.about");
/*     */     
/* 197 */     tabs.add(aboutTab, aboutPanel);
/*     */     
/* 199 */     JPanel systemPanel = new SystemPropertiesPanel();
/* 200 */     systemPanel.setBorder(STANDARD_BORDER);
/* 201 */     String systemTab = this.resources.getString("about-frame.tab.system");
/*     */     
/* 203 */     tabs.add(systemTab, systemPanel);
/*     */     
/* 205 */     return tabs;
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
/*     */ 
/*     */   
/*     */   private JPanel createAboutPanel(ProjectInfo project) {
/* 220 */     JPanel about = new JPanel(new BorderLayout());
/*     */     
/* 222 */     JPanel details = new AboutPanel(this.application, this.version, this.copyright, this.info, this.logo);
/*     */ 
/*     */     
/* 225 */     boolean includetabs = false;
/* 226 */     JTabbedPane tabs = new JTabbedPane();
/*     */     
/* 228 */     if (this.contributors != null) {
/* 229 */       JPanel contributorsPanel = new ContributorsPanel(this.contributors);
/*     */       
/* 231 */       contributorsPanel.setBorder(STANDARD_BORDER);
/* 232 */       String contributorsTab = this.resources.getString("about-frame.tab.contributors");
/*     */       
/* 234 */       tabs.add(contributorsTab, contributorsPanel);
/* 235 */       includetabs = true;
/*     */     } 
/*     */     
/* 238 */     if (this.licence != null) {
/* 239 */       JPanel licencePanel = createLicencePanel();
/* 240 */       licencePanel.setBorder(STANDARD_BORDER);
/* 241 */       String licenceTab = this.resources.getString("about-frame.tab.licence");
/*     */       
/* 243 */       tabs.add(licenceTab, licencePanel);
/* 244 */       includetabs = true;
/*     */     } 
/*     */     
/* 247 */     if (project != null) {
/* 248 */       JPanel librariesPanel = new LibraryPanel(project);
/* 249 */       librariesPanel.setBorder(STANDARD_BORDER);
/* 250 */       String librariesTab = this.resources.getString("about-frame.tab.libraries");
/*     */       
/* 252 */       tabs.add(librariesTab, librariesPanel);
/* 253 */       includetabs = true;
/*     */     } 
/*     */     
/* 256 */     about.add(details, "North");
/* 257 */     if (includetabs) {
/* 258 */       about.add(tabs);
/*     */     }
/*     */     
/* 261 */     return about;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JPanel createLicencePanel() {
/* 272 */     JPanel licencePanel = new JPanel(new BorderLayout());
/* 273 */     JTextArea area = new JTextArea(this.licence);
/* 274 */     area.setLineWrap(true);
/* 275 */     area.setWrapStyleWord(true);
/* 276 */     area.setCaretPosition(0);
/* 277 */     area.setEditable(false);
/* 278 */     licencePanel.add(new JScrollPane(area));
/* 279 */     return licencePanel;
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/ui/about/AboutFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */