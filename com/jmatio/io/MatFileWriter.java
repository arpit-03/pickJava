/*     */ package com.jmatio.io;
/*     */ 
/*     */ import com.jmatio.types.MLArray;
/*     */ import com.jmatio.types.MLSparse;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.WritableByteChannel;
/*     */ import java.util.Collection;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatFileWriter
/*     */ {
/*     */   public MatFileWriter() {}
/*     */   
/*     */   public MatFileWriter(String fileName, Collection<MLArray> data) throws IOException {
/*  73 */     this(new File(fileName), data);
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
/*     */   public MatFileWriter(File file, Collection<MLArray> data) throws IOException {
/*  85 */     this((new FileOutputStream(file)).getChannel(), data);
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
/*     */   public MatFileWriter(WritableByteChannel channel, Collection<MLArray> data) throws IOException {
/*  98 */     write(channel, data);
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
/*     */   public synchronized void write(String filepath, Collection<MLArray> data) throws IOException {
/* 115 */     write(new File(filepath), data);
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
/*     */   public synchronized void write(File file, Collection<MLArray> data) throws IOException {
/* 131 */     FileOutputStream fos = new FileOutputStream(file);
/*     */ 
/*     */     
/*     */     try {
/* 135 */       write(fos.getChannel(), data);
/*     */     }
/* 137 */     catch (IOException e) {
/*     */       
/* 139 */       throw e;
/*     */     }
/*     */     finally {
/*     */       
/* 143 */       fos.close();
/*     */     } 
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
/*     */   private synchronized void write(WritableByteChannel channel, Collection<MLArray> data) throws IOException {
/*     */     try {
/* 163 */       writeHeader(channel);
/*     */ 
/*     */       
/* 166 */       for (MLArray matrix : data)
/*     */       {
/*     */         
/* 169 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 170 */         DataOutputStream dos = new DataOutputStream(baos);
/*     */         
/* 172 */         writeMatrix(dos, matrix);
/*     */ 
/*     */         
/* 175 */         Deflater compresser = new Deflater();
/*     */         
/* 177 */         byte[] input = baos.toByteArray();
/*     */         
/* 179 */         ByteArrayOutputStream compressed = new ByteArrayOutputStream();
/* 180 */         DataOutputStream dout = new DataOutputStream(new DeflaterOutputStream(compressed, compresser));
/*     */         
/* 182 */         dout.write(input);
/*     */         
/* 184 */         dout.close();
/* 185 */         compressed.close();
/*     */ 
/*     */         
/* 188 */         byte[] compressedBytes = compressed.toByteArray();
/* 189 */         ByteBuffer buf = ByteBuffer.allocateDirect(8 + compressedBytes.length);
/* 190 */         buf.putInt(15);
/* 191 */         buf.putInt(compressedBytes.length);
/* 192 */         buf.put(compressedBytes);
/*     */         
/* 194 */         buf.flip();
/* 195 */         channel.write(buf);
/*     */       }
/*     */     
/* 198 */     } catch (IOException e) {
/*     */       
/* 200 */       throw e;
/*     */     }
/*     */     finally {
/*     */       
/* 204 */       channel.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeHeader(WritableByteChannel channel) throws IOException {
/* 216 */     MatFileHeader header = MatFileHeader.createHeader();
/* 217 */     char[] dest = new char[116];
/* 218 */     char[] src = header.getDescription().toCharArray();
/* 219 */     System.arraycopy(src, 0, dest, 0, src.length);
/*     */     
/* 221 */     byte[] endianIndicator = header.getEndianIndicator();
/*     */     
/* 223 */     ByteBuffer buf = ByteBuffer.allocateDirect(dest.length * 2 + 2 + endianIndicator.length);
/*     */     
/* 225 */     for (int i = 0; i < dest.length; i++)
/*     */     {
/* 227 */       buf.put((byte)dest[i]);
/*     */     }
/*     */     
/* 230 */     buf.position(buf.position() + 8);
/*     */ 
/*     */     
/* 233 */     int version = header.getVersion();
/* 234 */     buf.put((byte)(version >> 8));
/* 235 */     buf.put((byte)version);
/*     */     
/* 237 */     buf.put(endianIndicator);
/*     */     
/* 239 */     buf.flip();
/* 240 */     channel.write(buf);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMatrix(DataOutputStream output, MLArray array) throws IOException {
/*     */     // Byte code:
/*     */     //   0: new java/io/ByteArrayOutputStream
/*     */     //   3: dup
/*     */     //   4: invokespecial <init> : ()V
/*     */     //   7: astore #6
/*     */     //   9: new java/io/DataOutputStream
/*     */     //   12: dup
/*     */     //   13: aload #6
/*     */     //   15: invokespecial <init> : (Ljava/io/OutputStream;)V
/*     */     //   18: astore #7
/*     */     //   20: aload_0
/*     */     //   21: aload #7
/*     */     //   23: aload_2
/*     */     //   24: invokespecial writeFlags : (Ljava/io/DataOutputStream;Lcom/jmatio/types/MLArray;)V
/*     */     //   27: aload_0
/*     */     //   28: aload #7
/*     */     //   30: aload_2
/*     */     //   31: invokespecial writeDimensions : (Ljava/io/DataOutputStream;Lcom/jmatio/types/MLArray;)V
/*     */     //   34: aload_0
/*     */     //   35: aload #7
/*     */     //   37: aload_2
/*     */     //   38: invokespecial writeName : (Ljava/io/DataOutputStream;Lcom/jmatio/types/MLArray;)V
/*     */     //   41: aload_2
/*     */     //   42: invokevirtual getType : ()I
/*     */     //   45: tableswitch default -> 1100, 1 -> 706, 2 -> 611, 3 -> 1100, 4 -> 120, 5 -> 754, 6 -> 225, 7 -> 281, 8 -> 391, 9 -> 337, 10 -> 445, 11 -> 1100, 12 -> 1100, 13 -> 1100, 14 -> 499, 15 -> 555
/*     */     //   120: new java/io/ByteArrayOutputStream
/*     */     //   123: dup
/*     */     //   124: invokespecial <init> : ()V
/*     */     //   127: astore #4
/*     */     //   129: new java/io/DataOutputStream
/*     */     //   132: dup
/*     */     //   133: aload #4
/*     */     //   135: invokespecial <init> : (Ljava/io/OutputStream;)V
/*     */     //   138: astore #5
/*     */     //   140: aload_2
/*     */     //   141: checkcast com/jmatio/types/MLChar
/*     */     //   144: invokevirtual exportChar : ()[Ljava/lang/Character;
/*     */     //   147: astore #8
/*     */     //   149: iconst_0
/*     */     //   150: istore #9
/*     */     //   152: iload #9
/*     */     //   154: aload #8
/*     */     //   156: arraylength
/*     */     //   157: if_icmpge -> 201
/*     */     //   160: new java/lang/StringBuffer
/*     */     //   163: dup
/*     */     //   164: invokespecial <init> : ()V
/*     */     //   167: aload #8
/*     */     //   169: iload #9
/*     */     //   171: aaload
/*     */     //   172: invokevirtual charValue : ()C
/*     */     //   175: invokevirtual append : (C)Ljava/lang/StringBuffer;
/*     */     //   178: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   181: astore #10
/*     */     //   183: aload #5
/*     */     //   185: aload #10
/*     */     //   187: ldc 'UTF-8'
/*     */     //   189: invokevirtual getBytes : (Ljava/lang/String;)[B
/*     */     //   192: invokevirtual write : ([B)V
/*     */     //   195: iinc #9, 1
/*     */     //   198: goto -> 152
/*     */     //   201: new com/jmatio/io/OSArrayTag
/*     */     //   204: dup
/*     */     //   205: bipush #16
/*     */     //   207: aload #4
/*     */     //   209: invokevirtual toByteArray : ()[B
/*     */     //   212: invokespecial <init> : (I[B)V
/*     */     //   215: astore_3
/*     */     //   216: aload_3
/*     */     //   217: aload #7
/*     */     //   219: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   222: goto -> 1133
/*     */     //   225: new com/jmatio/io/OSArrayTag
/*     */     //   228: dup
/*     */     //   229: bipush #9
/*     */     //   231: aload_2
/*     */     //   232: checkcast com/jmatio/types/MLNumericArray
/*     */     //   235: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   238: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   241: astore_3
/*     */     //   242: aload_3
/*     */     //   243: aload #7
/*     */     //   245: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   248: aload_2
/*     */     //   249: invokevirtual isComplex : ()Z
/*     */     //   252: ifeq -> 1133
/*     */     //   255: new com/jmatio/io/OSArrayTag
/*     */     //   258: dup
/*     */     //   259: bipush #9
/*     */     //   261: aload_2
/*     */     //   262: checkcast com/jmatio/types/MLNumericArray
/*     */     //   265: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   268: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   271: astore_3
/*     */     //   272: aload_3
/*     */     //   273: aload #7
/*     */     //   275: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   278: goto -> 1133
/*     */     //   281: new com/jmatio/io/OSArrayTag
/*     */     //   284: dup
/*     */     //   285: bipush #7
/*     */     //   287: aload_2
/*     */     //   288: checkcast com/jmatio/types/MLNumericArray
/*     */     //   291: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   294: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   297: astore_3
/*     */     //   298: aload_3
/*     */     //   299: aload #7
/*     */     //   301: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   304: aload_2
/*     */     //   305: invokevirtual isComplex : ()Z
/*     */     //   308: ifeq -> 1133
/*     */     //   311: new com/jmatio/io/OSArrayTag
/*     */     //   314: dup
/*     */     //   315: bipush #7
/*     */     //   317: aload_2
/*     */     //   318: checkcast com/jmatio/types/MLNumericArray
/*     */     //   321: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   324: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   327: astore_3
/*     */     //   328: aload_3
/*     */     //   329: aload #7
/*     */     //   331: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   334: goto -> 1133
/*     */     //   337: new com/jmatio/io/OSArrayTag
/*     */     //   340: dup
/*     */     //   341: iconst_2
/*     */     //   342: aload_2
/*     */     //   343: checkcast com/jmatio/types/MLNumericArray
/*     */     //   346: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   349: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   352: astore_3
/*     */     //   353: aload_3
/*     */     //   354: aload #7
/*     */     //   356: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   359: aload_2
/*     */     //   360: invokevirtual isComplex : ()Z
/*     */     //   363: ifeq -> 1133
/*     */     //   366: new com/jmatio/io/OSArrayTag
/*     */     //   369: dup
/*     */     //   370: iconst_2
/*     */     //   371: aload_2
/*     */     //   372: checkcast com/jmatio/types/MLNumericArray
/*     */     //   375: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   378: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   381: astore_3
/*     */     //   382: aload_3
/*     */     //   383: aload #7
/*     */     //   385: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   388: goto -> 1133
/*     */     //   391: new com/jmatio/io/OSArrayTag
/*     */     //   394: dup
/*     */     //   395: iconst_1
/*     */     //   396: aload_2
/*     */     //   397: checkcast com/jmatio/types/MLNumericArray
/*     */     //   400: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   403: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   406: astore_3
/*     */     //   407: aload_3
/*     */     //   408: aload #7
/*     */     //   410: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   413: aload_2
/*     */     //   414: invokevirtual isComplex : ()Z
/*     */     //   417: ifeq -> 1133
/*     */     //   420: new com/jmatio/io/OSArrayTag
/*     */     //   423: dup
/*     */     //   424: iconst_1
/*     */     //   425: aload_2
/*     */     //   426: checkcast com/jmatio/types/MLNumericArray
/*     */     //   429: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   432: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   435: astore_3
/*     */     //   436: aload_3
/*     */     //   437: aload #7
/*     */     //   439: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   442: goto -> 1133
/*     */     //   445: new com/jmatio/io/OSArrayTag
/*     */     //   448: dup
/*     */     //   449: iconst_3
/*     */     //   450: aload_2
/*     */     //   451: checkcast com/jmatio/types/MLNumericArray
/*     */     //   454: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   457: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   460: astore_3
/*     */     //   461: aload_3
/*     */     //   462: aload #7
/*     */     //   464: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   467: aload_2
/*     */     //   468: invokevirtual isComplex : ()Z
/*     */     //   471: ifeq -> 1133
/*     */     //   474: new com/jmatio/io/OSArrayTag
/*     */     //   477: dup
/*     */     //   478: iconst_3
/*     */     //   479: aload_2
/*     */     //   480: checkcast com/jmatio/types/MLNumericArray
/*     */     //   483: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   486: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   489: astore_3
/*     */     //   490: aload_3
/*     */     //   491: aload #7
/*     */     //   493: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   496: goto -> 1133
/*     */     //   499: new com/jmatio/io/OSArrayTag
/*     */     //   502: dup
/*     */     //   503: bipush #12
/*     */     //   505: aload_2
/*     */     //   506: checkcast com/jmatio/types/MLNumericArray
/*     */     //   509: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   512: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   515: astore_3
/*     */     //   516: aload_3
/*     */     //   517: aload #7
/*     */     //   519: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   522: aload_2
/*     */     //   523: invokevirtual isComplex : ()Z
/*     */     //   526: ifeq -> 1133
/*     */     //   529: new com/jmatio/io/OSArrayTag
/*     */     //   532: dup
/*     */     //   533: bipush #12
/*     */     //   535: aload_2
/*     */     //   536: checkcast com/jmatio/types/MLNumericArray
/*     */     //   539: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   542: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   545: astore_3
/*     */     //   546: aload_3
/*     */     //   547: aload #7
/*     */     //   549: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   552: goto -> 1133
/*     */     //   555: new com/jmatio/io/OSArrayTag
/*     */     //   558: dup
/*     */     //   559: bipush #13
/*     */     //   561: aload_2
/*     */     //   562: checkcast com/jmatio/types/MLNumericArray
/*     */     //   565: invokevirtual getRealByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   568: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   571: astore_3
/*     */     //   572: aload_3
/*     */     //   573: aload #7
/*     */     //   575: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   578: aload_2
/*     */     //   579: invokevirtual isComplex : ()Z
/*     */     //   582: ifeq -> 1133
/*     */     //   585: new com/jmatio/io/OSArrayTag
/*     */     //   588: dup
/*     */     //   589: bipush #13
/*     */     //   591: aload_2
/*     */     //   592: checkcast com/jmatio/types/MLNumericArray
/*     */     //   595: invokevirtual getImaginaryByteBuffer : ()Ljava/nio/ByteBuffer;
/*     */     //   598: invokespecial <init> : (ILjava/nio/ByteBuffer;)V
/*     */     //   601: astore_3
/*     */     //   602: aload_3
/*     */     //   603: aload #7
/*     */     //   605: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   608: goto -> 1133
/*     */     //   611: ldc 262149
/*     */     //   613: istore #9
/*     */     //   615: aload #7
/*     */     //   617: iload #9
/*     */     //   619: invokevirtual writeInt : (I)V
/*     */     //   622: aload #7
/*     */     //   624: aload_2
/*     */     //   625: checkcast com/jmatio/types/MLStructure
/*     */     //   628: invokevirtual getMaxFieldLenth : ()I
/*     */     //   631: invokevirtual writeInt : (I)V
/*     */     //   634: new com/jmatio/io/OSArrayTag
/*     */     //   637: dup
/*     */     //   638: iconst_1
/*     */     //   639: aload_2
/*     */     //   640: checkcast com/jmatio/types/MLStructure
/*     */     //   643: invokevirtual getKeySetToByteArray : ()[B
/*     */     //   646: invokespecial <init> : (I[B)V
/*     */     //   649: astore_3
/*     */     //   650: aload_3
/*     */     //   651: aload #7
/*     */     //   653: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   656: aload_2
/*     */     //   657: checkcast com/jmatio/types/MLStructure
/*     */     //   660: invokevirtual getAllFields : ()Ljava/util/Collection;
/*     */     //   663: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */     //   668: astore #10
/*     */     //   670: aload #10
/*     */     //   672: invokeinterface hasNext : ()Z
/*     */     //   677: ifeq -> 703
/*     */     //   680: aload #10
/*     */     //   682: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   687: checkcast com/jmatio/types/MLArray
/*     */     //   690: astore #11
/*     */     //   692: aload_0
/*     */     //   693: aload #7
/*     */     //   695: aload #11
/*     */     //   697: invokespecial writeMatrix : (Ljava/io/DataOutputStream;Lcom/jmatio/types/MLArray;)V
/*     */     //   700: goto -> 670
/*     */     //   703: goto -> 1133
/*     */     //   706: aload_2
/*     */     //   707: checkcast com/jmatio/types/MLCell
/*     */     //   710: invokevirtual cells : ()Ljava/util/ArrayList;
/*     */     //   713: invokevirtual iterator : ()Ljava/util/Iterator;
/*     */     //   716: astore #10
/*     */     //   718: aload #10
/*     */     //   720: invokeinterface hasNext : ()Z
/*     */     //   725: ifeq -> 751
/*     */     //   728: aload #10
/*     */     //   730: invokeinterface next : ()Ljava/lang/Object;
/*     */     //   735: checkcast com/jmatio/types/MLArray
/*     */     //   738: astore #11
/*     */     //   740: aload_0
/*     */     //   741: aload #7
/*     */     //   743: aload #11
/*     */     //   745: invokespecial writeMatrix : (Ljava/io/DataOutputStream;Lcom/jmatio/types/MLArray;)V
/*     */     //   748: goto -> 718
/*     */     //   751: goto -> 1133
/*     */     //   754: new java/io/ByteArrayOutputStream
/*     */     //   757: dup
/*     */     //   758: invokespecial <init> : ()V
/*     */     //   761: astore #4
/*     */     //   763: new java/io/DataOutputStream
/*     */     //   766: dup
/*     */     //   767: aload #4
/*     */     //   769: invokespecial <init> : (Ljava/io/OutputStream;)V
/*     */     //   772: astore #5
/*     */     //   774: aload_2
/*     */     //   775: checkcast com/jmatio/types/MLSparse
/*     */     //   778: invokevirtual getIR : ()[I
/*     */     //   781: astore #10
/*     */     //   783: aload #10
/*     */     //   785: astore #11
/*     */     //   787: aload #11
/*     */     //   789: arraylength
/*     */     //   790: istore #12
/*     */     //   792: iconst_0
/*     */     //   793: istore #13
/*     */     //   795: iload #13
/*     */     //   797: iload #12
/*     */     //   799: if_icmpge -> 822
/*     */     //   802: aload #11
/*     */     //   804: iload #13
/*     */     //   806: iaload
/*     */     //   807: istore #14
/*     */     //   809: aload #5
/*     */     //   811: iload #14
/*     */     //   813: invokevirtual writeInt : (I)V
/*     */     //   816: iinc #13, 1
/*     */     //   819: goto -> 795
/*     */     //   822: new com/jmatio/io/OSArrayTag
/*     */     //   825: dup
/*     */     //   826: iconst_5
/*     */     //   827: aload #4
/*     */     //   829: invokevirtual toByteArray : ()[B
/*     */     //   832: invokespecial <init> : (I[B)V
/*     */     //   835: astore_3
/*     */     //   836: aload_3
/*     */     //   837: aload #7
/*     */     //   839: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   842: new java/io/ByteArrayOutputStream
/*     */     //   845: dup
/*     */     //   846: invokespecial <init> : ()V
/*     */     //   849: astore #4
/*     */     //   851: new java/io/DataOutputStream
/*     */     //   854: dup
/*     */     //   855: aload #4
/*     */     //   857: invokespecial <init> : (Ljava/io/OutputStream;)V
/*     */     //   860: astore #5
/*     */     //   862: aload_2
/*     */     //   863: checkcast com/jmatio/types/MLSparse
/*     */     //   866: invokevirtual getJC : ()[I
/*     */     //   869: astore #10
/*     */     //   871: aload #10
/*     */     //   873: astore #11
/*     */     //   875: aload #11
/*     */     //   877: arraylength
/*     */     //   878: istore #12
/*     */     //   880: iconst_0
/*     */     //   881: istore #13
/*     */     //   883: iload #13
/*     */     //   885: iload #12
/*     */     //   887: if_icmpge -> 910
/*     */     //   890: aload #11
/*     */     //   892: iload #13
/*     */     //   894: iaload
/*     */     //   895: istore #14
/*     */     //   897: aload #5
/*     */     //   899: iload #14
/*     */     //   901: invokevirtual writeInt : (I)V
/*     */     //   904: iinc #13, 1
/*     */     //   907: goto -> 883
/*     */     //   910: new com/jmatio/io/OSArrayTag
/*     */     //   913: dup
/*     */     //   914: iconst_5
/*     */     //   915: aload #4
/*     */     //   917: invokevirtual toByteArray : ()[B
/*     */     //   920: invokespecial <init> : (I[B)V
/*     */     //   923: astore_3
/*     */     //   924: aload_3
/*     */     //   925: aload #7
/*     */     //   927: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   930: new java/io/ByteArrayOutputStream
/*     */     //   933: dup
/*     */     //   934: invokespecial <init> : ()V
/*     */     //   937: astore #4
/*     */     //   939: new java/io/DataOutputStream
/*     */     //   942: dup
/*     */     //   943: aload #4
/*     */     //   945: invokespecial <init> : (Ljava/io/OutputStream;)V
/*     */     //   948: astore #5
/*     */     //   950: aload_2
/*     */     //   951: checkcast com/jmatio/types/MLSparse
/*     */     //   954: invokevirtual exportReal : ()[Ljava/lang/Double;
/*     */     //   957: astore #11
/*     */     //   959: iconst_0
/*     */     //   960: istore #12
/*     */     //   962: iload #12
/*     */     //   964: aload #11
/*     */     //   966: arraylength
/*     */     //   967: if_icmpge -> 989
/*     */     //   970: aload #5
/*     */     //   972: aload #11
/*     */     //   974: iload #12
/*     */     //   976: aaload
/*     */     //   977: invokevirtual doubleValue : ()D
/*     */     //   980: invokevirtual writeDouble : (D)V
/*     */     //   983: iinc #12, 1
/*     */     //   986: goto -> 962
/*     */     //   989: new com/jmatio/io/OSArrayTag
/*     */     //   992: dup
/*     */     //   993: bipush #9
/*     */     //   995: aload #4
/*     */     //   997: invokevirtual toByteArray : ()[B
/*     */     //   1000: invokespecial <init> : (I[B)V
/*     */     //   1003: astore_3
/*     */     //   1004: aload_3
/*     */     //   1005: aload #7
/*     */     //   1007: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   1010: aload_2
/*     */     //   1011: invokevirtual isComplex : ()Z
/*     */     //   1014: ifeq -> 1133
/*     */     //   1017: new java/io/ByteArrayOutputStream
/*     */     //   1020: dup
/*     */     //   1021: invokespecial <init> : ()V
/*     */     //   1024: astore #4
/*     */     //   1026: new java/io/DataOutputStream
/*     */     //   1029: dup
/*     */     //   1030: aload #4
/*     */     //   1032: invokespecial <init> : (Ljava/io/OutputStream;)V
/*     */     //   1035: astore #5
/*     */     //   1037: aload_2
/*     */     //   1038: checkcast com/jmatio/types/MLSparse
/*     */     //   1041: invokevirtual exportImaginary : ()[Ljava/lang/Double;
/*     */     //   1044: astore #11
/*     */     //   1046: iconst_0
/*     */     //   1047: istore #12
/*     */     //   1049: iload #12
/*     */     //   1051: aload #11
/*     */     //   1053: arraylength
/*     */     //   1054: if_icmpge -> 1076
/*     */     //   1057: aload #5
/*     */     //   1059: aload #11
/*     */     //   1061: iload #12
/*     */     //   1063: aaload
/*     */     //   1064: invokevirtual doubleValue : ()D
/*     */     //   1067: invokevirtual writeDouble : (D)V
/*     */     //   1070: iinc #12, 1
/*     */     //   1073: goto -> 1049
/*     */     //   1076: new com/jmatio/io/OSArrayTag
/*     */     //   1079: dup
/*     */     //   1080: bipush #9
/*     */     //   1082: aload #4
/*     */     //   1084: invokevirtual toByteArray : ()[B
/*     */     //   1087: invokespecial <init> : (I[B)V
/*     */     //   1090: astore_3
/*     */     //   1091: aload_3
/*     */     //   1092: aload #7
/*     */     //   1094: invokevirtual writeTo : (Ljava/io/DataOutputStream;)V
/*     */     //   1097: goto -> 1133
/*     */     //   1100: new com/jmatio/io/MatlabIOException
/*     */     //   1103: dup
/*     */     //   1104: new java/lang/StringBuilder
/*     */     //   1107: dup
/*     */     //   1108: invokespecial <init> : ()V
/*     */     //   1111: ldc 'Cannot write matrix of type: '
/*     */     //   1113: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   1116: aload_2
/*     */     //   1117: invokevirtual getType : ()I
/*     */     //   1120: invokestatic typeToString : (I)Ljava/lang/String;
/*     */     //   1123: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   1126: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   1129: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   1132: athrow
/*     */     //   1133: aload_1
/*     */     //   1134: bipush #14
/*     */     //   1136: invokevirtual writeInt : (I)V
/*     */     //   1139: aload_1
/*     */     //   1140: aload #6
/*     */     //   1142: invokevirtual size : ()I
/*     */     //   1145: invokevirtual writeInt : (I)V
/*     */     //   1148: aload_1
/*     */     //   1149: aload #6
/*     */     //   1151: invokevirtual toByteArray : ()[B
/*     */     //   1154: invokevirtual write : ([B)V
/*     */     //   1157: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #255	-> 0
/*     */     //   #256	-> 9
/*     */     //   #259	-> 20
/*     */     //   #262	-> 27
/*     */     //   #265	-> 34
/*     */     //   #267	-> 41
/*     */     //   #271	-> 120
/*     */     //   #272	-> 129
/*     */     //   #273	-> 140
/*     */     //   #274	-> 149
/*     */     //   #276	-> 160
/*     */     //   #277	-> 183
/*     */     //   #274	-> 195
/*     */     //   #279	-> 201
/*     */     //   #280	-> 216
/*     */     //   #282	-> 222
/*     */     //   #285	-> 225
/*     */     //   #287	-> 242
/*     */     //   #290	-> 248
/*     */     //   #292	-> 255
/*     */     //   #294	-> 272
/*     */     //   #299	-> 281
/*     */     //   #301	-> 298
/*     */     //   #304	-> 304
/*     */     //   #306	-> 311
/*     */     //   #308	-> 328
/*     */     //   #313	-> 337
/*     */     //   #315	-> 353
/*     */     //   #318	-> 359
/*     */     //   #320	-> 366
/*     */     //   #322	-> 382
/*     */     //   #327	-> 391
/*     */     //   #329	-> 407
/*     */     //   #332	-> 413
/*     */     //   #334	-> 420
/*     */     //   #336	-> 436
/*     */     //   #341	-> 445
/*     */     //   #343	-> 461
/*     */     //   #346	-> 467
/*     */     //   #348	-> 474
/*     */     //   #350	-> 490
/*     */     //   #355	-> 499
/*     */     //   #357	-> 516
/*     */     //   #360	-> 522
/*     */     //   #362	-> 529
/*     */     //   #364	-> 546
/*     */     //   #369	-> 555
/*     */     //   #371	-> 572
/*     */     //   #374	-> 578
/*     */     //   #376	-> 585
/*     */     //   #378	-> 602
/*     */     //   #383	-> 611
/*     */     //   #384	-> 615
/*     */     //   #385	-> 622
/*     */     //   #388	-> 634
/*     */     //   #389	-> 650
/*     */     //   #391	-> 656
/*     */     //   #393	-> 692
/*     */     //   #394	-> 700
/*     */     //   #395	-> 703
/*     */     //   #397	-> 706
/*     */     //   #399	-> 740
/*     */     //   #400	-> 748
/*     */     //   #401	-> 751
/*     */     //   #405	-> 754
/*     */     //   #406	-> 763
/*     */     //   #407	-> 774
/*     */     //   #408	-> 783
/*     */     //   #410	-> 809
/*     */     //   #408	-> 816
/*     */     //   #412	-> 822
/*     */     //   #413	-> 836
/*     */     //   #415	-> 842
/*     */     //   #416	-> 851
/*     */     //   #417	-> 862
/*     */     //   #418	-> 871
/*     */     //   #420	-> 897
/*     */     //   #418	-> 904
/*     */     //   #422	-> 910
/*     */     //   #423	-> 924
/*     */     //   #425	-> 930
/*     */     //   #426	-> 939
/*     */     //   #428	-> 950
/*     */     //   #430	-> 959
/*     */     //   #432	-> 970
/*     */     //   #430	-> 983
/*     */     //   #435	-> 989
/*     */     //   #436	-> 1004
/*     */     //   #438	-> 1010
/*     */     //   #440	-> 1017
/*     */     //   #441	-> 1026
/*     */     //   #442	-> 1037
/*     */     //   #443	-> 1046
/*     */     //   #445	-> 1057
/*     */     //   #443	-> 1070
/*     */     //   #447	-> 1076
/*     */     //   #448	-> 1091
/*     */     //   #452	-> 1100
/*     */     //   #458	-> 1133
/*     */     //   #459	-> 1139
/*     */     //   #460	-> 1148
/*     */     //   #461	-> 1157
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   183	12	10	temp	Ljava/lang/String;
/*     */     //   152	49	9	i	I
/*     */     //   692	8	11	a	Lcom/jmatio/types/MLArray;
/*     */     //   670	33	10	i$	Ljava/util/Iterator;
/*     */     //   740	8	11	a	Lcom/jmatio/types/MLArray;
/*     */     //   718	33	10	i$	Ljava/util/Iterator;
/*     */     //   809	7	14	i	I
/*     */     //   787	35	11	arr$	[I
/*     */     //   792	30	12	len$	I
/*     */     //   795	27	13	i$	I
/*     */     //   897	7	14	i	I
/*     */     //   875	35	11	arr$	[I
/*     */     //   880	30	12	len$	I
/*     */     //   883	27	13	i$	I
/*     */     //   962	27	12	i	I
/*     */     //   1049	27	12	i	I
/*     */     //   149	984	8	ac	[Ljava/lang/Character;
/*     */     //   615	518	9	itag	I
/*     */     //   783	350	10	ai	[I
/*     */     //   959	174	11	ad	[Ljava/lang/Double;
/*     */     //   0	1158	0	this	Lcom/jmatio/io/MatFileWriter;
/*     */     //   0	1158	1	output	Ljava/io/DataOutputStream;
/*     */     //   0	1158	2	array	Lcom/jmatio/types/MLArray;
/*     */     //   216	942	3	tag	Lcom/jmatio/io/OSArrayTag;
/*     */     //   129	1029	4	buffer	Ljava/io/ByteArrayOutputStream;
/*     */     //   140	1018	5	bufferDOS	Ljava/io/DataOutputStream;
/*     */     //   9	1149	6	baos	Ljava/io/ByteArrayOutputStream;
/*     */     //   20	1138	7	dos	Ljava/io/DataOutputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeFlags(DataOutputStream os, MLArray array) throws IOException {
/* 472 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 473 */     DataOutputStream bufferDOS = new DataOutputStream(buffer);
/*     */     
/* 475 */     bufferDOS.writeInt(array.getFlags());
/*     */     
/* 477 */     if (array.isSparse()) {
/*     */       
/* 479 */       bufferDOS.writeInt(((MLSparse)array).getMaxNZ());
/*     */     }
/*     */     else {
/*     */       
/* 483 */       bufferDOS.writeInt(0);
/*     */     } 
/* 485 */     OSArrayTag tag = new OSArrayTag(6, buffer.toByteArray());
/* 486 */     tag.writeTo(os);
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
/*     */   private void writeDimensions(DataOutputStream os, MLArray array) throws IOException {
/* 499 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 500 */     DataOutputStream bufferDOS = new DataOutputStream(buffer);
/*     */     
/* 502 */     int[] dims = array.getDimensions();
/* 503 */     for (int i = 0; i < dims.length; i++)
/*     */     {
/* 505 */       bufferDOS.writeInt(dims[i]);
/*     */     }
/* 507 */     OSArrayTag tag = new OSArrayTag(5, buffer.toByteArray());
/* 508 */     tag.writeTo(os);
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
/*     */   private void writeName(DataOutputStream os, MLArray array) throws IOException {
/* 521 */     ByteArrayOutputStream buffer = new ByteArrayOutputStream();
/* 522 */     DataOutputStream bufferDOS = new DataOutputStream(buffer);
/*     */     
/* 524 */     byte[] nameByteArray = array.getNameToByteArray();
/* 525 */     buffer = new ByteArrayOutputStream();
/* 526 */     bufferDOS = new DataOutputStream(buffer);
/* 527 */     bufferDOS.write(nameByteArray);
/* 528 */     OSArrayTag tag = new OSArrayTag(1, buffer.toByteArray());
/* 529 */     tag.writeTo(os);
/*     */   }
/*     */ }


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/jmatio/io/MatFileWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */