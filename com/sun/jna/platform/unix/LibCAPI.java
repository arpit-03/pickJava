package com.sun.jna.platform.unix;

public interface LibCAPI extends Reboot, Resource {
  public static final int HOST_NAME_MAX = 255;
  
  int getuid();
  
  int geteuid();
  
  int getgid();
  
  int getegid();
  
  int setuid(int paramInt);
  
  int seteuid(int paramInt);
  
  int setgid(int paramInt);
  
  int setegid(int paramInt);
  
  @Deprecated
  int gethostname(char[] paramArrayOfchar, int paramInt);
  
  @Deprecated
  int sethostname(char[] paramArrayOfchar, int paramInt);
  
  int gethostname(byte[] paramArrayOfbyte, int paramInt);
  
  int sethostname(String paramString, int paramInt);
  
  @Deprecated
  int getdomainname(char[] paramArrayOfchar, int paramInt);
  
  @Deprecated
  int setdomainname(char[] paramArrayOfchar, int paramInt);
  
  int getdomainname(byte[] paramArrayOfbyte, int paramInt);
  
  int setdomainname(String paramString, int paramInt);
  
  String getenv(String paramString);
  
  int setenv(String paramString1, String paramString2, int paramInt);
  
  int unsetenv(String paramString);
  
  int getloadavg(double[] paramArrayOfdouble, int paramInt);
}


/* Location:              /home/arpit/Downloads/Picking-Tool-6.5.2.jar!/com/sun/jna/platform/unix/LibCAPI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */