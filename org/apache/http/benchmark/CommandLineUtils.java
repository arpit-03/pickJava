/*     */ package org.apache.http.benchmark;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.cli.CommandLine;
/*     */ import org.apache.commons.cli.HelpFormatter;
/*     */ import org.apache.commons.cli.Option;
/*     */ import org.apache.commons.cli.Options;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandLineUtils
/*     */ {
/*     */   public static Options getOptions() {
/*  41 */     Option iopt = new Option("i", false, "Do HEAD requests instead of GET (deprecated)");
/*  42 */     iopt.setRequired(false);
/*     */     
/*  44 */     Option oopt = new Option("o", false, "Use HTTP/S 1.0 instead of 1.1 (default)");
/*  45 */     oopt.setRequired(false);
/*     */     
/*  47 */     Option kopt = new Option("k", false, "Enable the HTTP KeepAlive feature, i.e., perform multiple requests within one HTTP session. Default is no KeepAlive");
/*     */ 
/*     */     
/*  50 */     kopt.setRequired(false);
/*     */     
/*  52 */     Option uopt = new Option("u", false, "Chunk entity. Default is false");
/*  53 */     uopt.setRequired(false);
/*     */     
/*  55 */     Option xopt = new Option("x", false, "Use Expect-Continue. Default is false");
/*  56 */     xopt.setRequired(false);
/*     */     
/*  58 */     Option gopt = new Option("g", false, "Accept GZip. Default is false");
/*  59 */     gopt.setRequired(false);
/*     */     
/*  61 */     Option nopt = new Option("n", true, "Number of requests to perform for the benchmarking session. The default is to just perform a single request which usually leads to non-representative benchmarking results");
/*     */ 
/*     */ 
/*     */     
/*  65 */     nopt.setRequired(false);
/*  66 */     nopt.setArgName("requests");
/*     */     
/*  68 */     Option copt = new Option("c", true, "Concurrency while performing the benchmarking session. The default is to just use a single thread/client");
/*     */     
/*  70 */     copt.setRequired(false);
/*  71 */     copt.setArgName("concurrency");
/*     */     
/*  73 */     Option popt = new Option("p", true, "File containing data to POST or PUT");
/*  74 */     popt.setRequired(false);
/*  75 */     popt.setArgName("Payload file");
/*     */     
/*  77 */     Option mopt = new Option("m", true, "HTTP Method. Default is POST. Possible options are GET, POST, PUT, DELETE, HEAD, OPTIONS, TRACE");
/*     */     
/*  79 */     mopt.setRequired(false);
/*  80 */     mopt.setArgName("HTTP method");
/*     */     
/*  82 */     Option Topt = new Option("T", true, "Content-type header to use for POST/PUT data");
/*  83 */     Topt.setRequired(false);
/*  84 */     Topt.setArgName("content-type");
/*     */     
/*  86 */     Option topt = new Option("t", true, "Client side socket timeout (in ms) - default 60 Secs");
/*  87 */     topt.setRequired(false);
/*  88 */     topt.setArgName("socket-Timeout");
/*     */     
/*  90 */     Option Hopt = new Option("H", true, "Add arbitrary header line, eg. 'Accept-Encoding: gzip' inserted after all normal header lines. (repeatable as -H \"h1: v1\",\"h2: v2\" etc)");
/*     */ 
/*     */     
/*  93 */     Hopt.setRequired(false);
/*  94 */     Hopt.setArgName("header");
/*     */     
/*  96 */     Option vopt = new Option("v", true, "Set verbosity level - 4 and above prints response content, 3 and above prints information on headers, 2 and above prints response codes (404, 200, etc.), 1 and above prints warnings and info");
/*     */ 
/*     */ 
/*     */     
/* 100 */     vopt.setRequired(false);
/* 101 */     vopt.setArgName("verbosity");
/*     */     
/* 103 */     Option hopt = new Option("h", false, "Display usage information");
/* 104 */     nopt.setRequired(false);
/*     */     
/* 106 */     Options options = new Options();
/* 107 */     options.addOption(iopt);
/* 108 */     options.addOption(mopt);
/* 109 */     options.addOption(uopt);
/* 110 */     options.addOption(xopt);
/* 111 */     options.addOption(gopt);
/* 112 */     options.addOption(kopt);
/* 113 */     options.addOption(nopt);
/* 114 */     options.addOption(copt);
/* 115 */     options.addOption(popt);
/* 116 */     options.addOption(Topt);
/* 117 */     options.addOption(vopt);
/* 118 */     options.addOption(Hopt);
/* 119 */     options.addOption(hopt);
/* 120 */     options.addOption(topt);
/* 121 */     options.addOption(oopt);
/* 122 */     return options;
/*     */   }
/*     */   
/*     */   public static void parseCommandLine(CommandLine cmd, Config config) {
/* 126 */     if (cmd.hasOption('v')) {
/* 127 */       String s = cmd.getOptionValue('v');
/*     */       try {
/* 129 */         config.setVerbosity(Integer.parseInt(s));
/* 130 */       } catch (NumberFormatException ex) {
/* 131 */         printError("Invalid verbosity level: " + s);
/*     */       } 
/*     */     } 
/*     */     
/* 135 */     if (cmd.hasOption('k')) {
/* 136 */       config.setKeepAlive(true);
/*     */     }
/*     */     
/* 139 */     if (cmd.hasOption('c')) {
/* 140 */       String s = cmd.getOptionValue('c');
/*     */       try {
/* 142 */         config.setThreads(Integer.parseInt(s));
/* 143 */       } catch (NumberFormatException ex) {
/* 144 */         printError("Invalid number for concurrency: " + s);
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     if (cmd.hasOption('n')) {
/* 149 */       String s = cmd.getOptionValue('n');
/*     */       try {
/* 151 */         config.setRequests(Integer.parseInt(s));
/* 152 */       } catch (NumberFormatException ex) {
/* 153 */         printError("Invalid number of requests: " + s);
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     if (cmd.hasOption('p')) {
/* 158 */       File file = new File(cmd.getOptionValue('p'));
/* 159 */       if (!file.exists()) {
/* 160 */         printError("File not found: " + file);
/*     */       }
/* 162 */       config.setPayloadFile(file);
/*     */     } 
/*     */     
/* 165 */     if (cmd.hasOption('T')) {
/* 166 */       config.setContentType(cmd.getOptionValue('T'));
/*     */     }
/*     */     
/* 169 */     if (cmd.hasOption('i')) {
/* 170 */       config.setHeadInsteadOfGet(true);
/*     */     }
/*     */     
/* 173 */     if (cmd.hasOption('H')) {
/* 174 */       String headerStr = cmd.getOptionValue('H');
/* 175 */       config.setHeaders(headerStr.split(","));
/*     */     } 
/*     */     
/* 178 */     if (cmd.hasOption('t')) {
/* 179 */       String t = cmd.getOptionValue('t');
/*     */       try {
/* 181 */         config.setSocketTimeout(Integer.parseInt(t));
/* 182 */       } catch (NumberFormatException ex) {
/* 183 */         printError("Invalid socket timeout: " + t);
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     if (cmd.hasOption('o')) {
/* 188 */       config.setUseHttp1_0(true);
/*     */     }
/*     */     
/* 191 */     if (cmd.hasOption('m')) {
/* 192 */       config.setMethod(cmd.getOptionValue('m'));
/* 193 */     } else if (cmd.hasOption('p')) {
/* 194 */       config.setMethod("POST");
/*     */     } 
/*     */     
/* 197 */     if (cmd.hasOption('u')) {
/* 198 */       config.setUseChunking(true);
/*     */     }
/*     */     
/* 201 */     if (cmd.hasOption('x')) {
/* 202 */       config.setUseExpectContinue(true);
/*     */     }
/*     */     
/* 205 */     if (cmd.hasOption('g')) {
/* 206 */       config.setUseAcceptGZip(true);
/*     */     }
/*     */     
/* 209 */     String[] cmdargs = cmd.getArgs();
/* 210 */     if (cmdargs.length > 0) {
/*     */       try {
/* 212 */         config.setUrl(new URL(cmdargs[0]));
/* 213 */       } catch (MalformedURLException e) {
/* 214 */         printError("Invalid request URL : " + cmdargs[0]);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static void showUsage(Options options) {
/* 220 */     HelpFormatter formatter = new HelpFormatter();
/* 221 */     formatter.printHelp("HttpBenchmark [options] [http://]hostname[:port]/path?query", options);
/*     */   }
/*     */   
/*     */   static void printError(String msg) {
/* 225 */     System.err.println(msg);
/* 226 */     showUsage(getOptions());
/* 227 */     System.exit(-1);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/org/apache/http/benchmark/CommandLineUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */