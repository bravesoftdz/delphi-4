package edu.mq.comp.delphi.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {
  public static void closeQuietly(InputStream in) {
	if (in != null) {
	  try {
		in.close();
	  } catch (IOException ex) {
		// ignore
	  }
	}
  }
  
  public static void closeQuietly(OutputStream out) {
	if (out != null) {
	  try {
		out.close();
	  } catch (IOException ex) {
		// ignore
	  }
	}
  }
  
  public static void closeQuietly(Reader reader) {
	if (reader != null) {
	  try {
		reader.close();
	  } catch (IOException ex) {
		// ignore
	  }
	}
  }
  
  public static void closeQuietly(Writer writer) {
	if (writer != null) {
	  try {
		writer.close();
	  } catch (IOException ex) {
		// ignore
	  }
	}
  }
  
  // private constructor to prevent instantiation
  private IOUtil() {
  }
}
