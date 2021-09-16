/*     */ package org.jfree;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.ResourceBundle;
/*     */ import org.jfree.base.BaseBoot;
/*     */ import org.jfree.base.Library;
/*     */ import org.jfree.ui.about.Contributor;
/*     */ import org.jfree.ui.about.Licences;
/*     */ import org.jfree.ui.about.ProjectInfo;
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
/*     */ public class JCommonInfo
/*     */   extends ProjectInfo
/*     */ {
/*     */   private static JCommonInfo singleton;
/*     */   
/*     */   public static synchronized JCommonInfo getInstance() {
/*  74 */     if (singleton == null) {
/*  75 */       singleton = new JCommonInfo();
/*     */     }
/*  77 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private JCommonInfo() {
/*  86 */     String baseResourceClass = "org.jfree.resources.JCommonResources";
/*  87 */     ResourceBundle resources = ResourceBundleWrapper.getBundle("org.jfree.resources.JCommonResources");
/*     */ 
/*     */     
/*  90 */     setName(resources.getString("project.name"));
/*  91 */     setVersion(resources.getString("project.version"));
/*  92 */     setInfo(resources.getString("project.info"));
/*  93 */     setCopyright(resources.getString("project.copyright"));
/*     */     
/*  95 */     setLicenceName("LGPL");
/*  96 */     setLicenceText(Licences.getInstance().getLGPL());
/*     */     
/*  98 */     setContributors(Arrays.asList(new Contributor[] { new Contributor("Anthony Boulestreau", "-"), new Contributor("Jeremy Bowman", "-"), new Contributor("J. David Eisenberg", "-"), new Contributor("Paul English", "-"), new Contributor("David Gilbert", "david.gilbert@object-refinery.com"), new Contributor("Hans-Jurgen Greiner", "-"), new Contributor("Arik Levin", "-"), new Contributor("Achilleus Mantzios", "-"), new Contributor("Thomas Meier", "-"), new Contributor("Aaron Metzger", "-"), new Contributor("Thomas Morgner", "-"), new Contributor("Krzysztof Paz", "-"), new Contributor("Nabuo Tamemasa", "-"), new Contributor("Mark Watson", "-"), new Contributor("Matthew Wright", "-"), new Contributor("Hari", "-"), new Contributor("Sam (oldman)", "-") }));
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
/* 121 */     addOptionalLibrary(new Library("JUnit", "3.8", "IBM Public Licence", "http://www.junit.org/"));
/*     */ 
/*     */     
/* 124 */     setBootClass(BaseBoot.class.getName());
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/jfree/JCommonInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */