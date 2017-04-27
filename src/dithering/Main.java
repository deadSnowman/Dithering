/*
 * Grayscale conversion and dithering program for multimedia class
 */
package dithering;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class for testing Bitmap methods
 * @author seth & anna
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    run(args);

  }
  
  /**
   * Run the program using commandline arguments as different operations
   * @param args the commandline arguments
   */
  public static void run(String[] args) {
    
    if(Arrays.asList(args).contains("--help") || Arrays.asList(args).contains("-h")) {
      printUsage();
    }
    else if(Arrays.asList(args).contains("-prompt") || Arrays.asList(args).contains("-p")) {
      System.out.println("Running in prompt mode...");
      runPrompt();
    }
    else if(Arrays.asList(args).contains("-test") || Arrays.asList(args).contains("-t")) {
      System.out.println("Running in test mode...");
      runTest();
    }
    else if(Arrays.asList(args).contains("-all")) {
      System.out.println("Contains \"-all\"");
      // run all methods
      // if directory is specified, write there, otherwise use current directory
    }
    else {
      if(Arrays.asList(args).contains("-i")) { // if there in an input file flag
	String image = args[Arrays.asList(args).indexOf("-g")+1];
	File path = new File(args[Arrays.asList(args).indexOf("-g")+1]);
	
	if(path.exists() && !path.isDirectory()) { // if the file is real
	
	  if(Arrays.asList(args).contains("-g")) {
	    // greyscale conversion
	  }
	  if(Arrays.asList(args).contains("-gl")) {
	    // greyscale conversion (luminosity)
	  }
	  if(Arrays.asList(args).contains("-d")) {
	    // stein floyd dithering
	  }
	  if(Arrays.asList(args).contains("-dl")) {
	    // stein floyd dithering (luminosity)
	  }
	  if(Arrays.asList(args).contains("-b")) {
	    // bayer matrix dithering
	  }
	  if(Arrays.asList(args).contains("-bl")) {
	    // bayer matrix dithering (luminosity)
	  }
	}
	else {
	  System.out.println("You need to specify an image file after \"-i\"");
	}
      }
    }
    
  }
  
  public static void runPrompt() {
    
    Scanner scan = new Scanner(System.in);
    String input;
    
    System.out.println("Welcom to the grayscale conversion and dithering program");
    System.out.println("==============================AYYYYEEELAMOOO============");
    givePromptMessage();
    
    while(scan.hasNext()) {
      givePromptMessage();
      
      input = scan.nextLine();
      
      if(input.compareTo("x") == 0) {
	System.out.println("Exiting program...");
	break;
      }
      else {
      
	switch(input) {
	  case "1":
	    System.out.println("greyscale");
	    break;
	  case "2":
	    System.out.println("greyscale");
	    break;
	}
	  
      }
      
    }
  }
  
  public static void givePromptMessage() {
    System.out.println("What would you like to do?");
      System.out.println("1.) Convert to greyscale");
      System.out.println("2.) Convert to greyscale luminosity");
      System.out.println("3.) Dither using Stein Floyd dithering");
      System.out.println("4.) Dither using Stein Floyd dithering (luminosity)");
      System.out.println("5.) Dither with ");
      System.out.println("6.) Convert to greyscale");
      System.out.println("x.) Quit program");
  }
  
  public static void runTest() {
    
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    
    // /res/bitmaps needs to be in the project directory, or in /dist
    String dir = "./res/bitmaps/";
    File path = new File(System.getProperty("user.dir"));
    System.out.println(path.getName());
    if(path.getName().compareTo("Dithering") != 0) {
      dir = "../res/bitmaps/";
      System.out.println("here");
    }
    
    Bitmap image3 = new Bitmap(dir + "wings/mtg__gift_of_orzhova_by_algenpfleger-d5sj6ir.jpg"); // wings
    Bitmap image5 = new Bitmap(dir + "apocalyptic/oh_god_where_is_my_gun_by_alexiuss-d7smmkm.jpg"); // apocalyptic
    Bitmap image6 = new Bitmap(dir + "forest/Photo by Ray Bilcliff- (15).jpg"); // forest
    Bitmap image7 = new Bitmap(dir + "lenna/Lenna.png"); // lenna
    
    bitmaps.add(image3);
    bitmaps.add(image5);
    bitmaps.add(image6);
    bitmaps.add(image7);

    // Iterate through bitmaps list, convert, and write converted grayscale images to their associated folders
    for(Bitmap b : bitmaps) {
      System.out.println("Width: " + b.getWidth() + ", " + "height: " + b.getHeight());      
      b.convertToGrayscaleAverage();
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale.png");
      b.convertToGrayscaleLumosity();
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale_lumos.png");
      b.floydSteinbergDither(false);
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither.png");
      b.floydSteinbergDither(true);
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither_lumos.png");
      b.bayerDither(false);
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/bayerDither.png");
      b.bayerDither(true);
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/bayerDither_lumos.png");
      
      System.out.println();
    }
  }
  
  /**
   * Print usage for commandline arguments
   */
  public static void printUsage() {
      System.out.println("Usage: java -jar Dithering.jar [-i input_file] [-g] [-gl] [-d] [-dl] [-b] [-bl] [-o output_dir]");
      System.out.println("                               [-h usage]");
      System.out.println("                               [-i input_file] [-p]");
      System.out.println("                               [-i input_file] [-a] [-o output_dir]");
      System.out.println();
      System.out.println("\t -g\tConvert to greyscale");
      System.out.println("\t -gl\tConvert to greyscale (luminosity method)");
      System.out.println("\t -d\tDither with stein floyd algorithm");
      System.out.println("\t -dl\tDither with stein floyd algorithm (luminosity method)");
      System.out.println("\t -b\tDither with bayer matrix algorithm");
      System.out.println("\t -bl\tDither with bayer matrix algorithm (luminosity method)");
      System.out.println();
      System.out.println("\t -p\tRun program in prompt mode");
  }
  
}
