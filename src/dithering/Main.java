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
    /*--------------------------------------------------------------------------
                                 start load images
    --------------------------------------------------------------------------*/
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
    /*--------------------------------------------------------------------------
                                 end load images                               
    --------------------------------------------------------------------------*/
    
    Scanner scan = new Scanner(System.in);
    String input;
    
    System.out.println("Welcome to the grayscale conversion and dithering program");
    System.out.println("=========================================================");
    givePrompt();
    int prompt = 1;
    boolean fileChosen = false;
    
    while(scan.hasNext()) {
      
      input = scan.nextLine();
      
      if(input.compareTo("x") == 0) {
        System.out.println();
	System.out.println("Exiting program...");
	break;
      }
      
      else if (prompt == 1) {
        ++prompt; //because next time through while loop we don't want to go through this switch case.
	switch(input) {
	  case "1":
              if(!fileChosen)
                chooseFilePrompt("greyscale");
              else {//file is chosen and now need to perform greyscale action
                for(Bitmap b : bitmaps) {   
                    b.convertToGrayscaleAverage();
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	  case "2":
              if(!fileChosen)
                chooseFilePrompt("greyscale luminosity");
              else {//file is chosen and now need to perform greyscale luminosity action
                for(Bitmap b : bitmaps) {   
                    b.convertToGrayscaleLumosity();
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale_lumos.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	  case "3":
              if(!fileChosen)
                chooseFilePrompt("Stein Floyd dithering");
              else {//file is chosen and now need to perform Stein Floyd dithering action
                for(Bitmap b : bitmaps) { 
                    b.floydSteinbergDither(false);
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	  case "4":
              if(!fileChosen)
                chooseFilePrompt("Stein Floyd dithering (luminosity)");
              else {//file is chosen and now need to perform Stein Floyd dithering (luminosity) action
                for(Bitmap b : bitmaps) { 
                    b.floydSteinbergDither(true);
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither_lumos.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	  case "5":
              if(!fileChosen)
                chooseFilePrompt("Bayer dithering");
              else {//file is chosen and now need to perform Bayer dithering action
                for(Bitmap b : bitmaps) { 
                    b.bayerDither(false);
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/bayerDither.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	  case "6":
              if(!fileChosen)
                chooseFilePrompt("Bayer dithering (luminosity)");
              else {//file is chosen and now need to perform Bayer dithering (luminosity) action
                for(Bitmap b : bitmaps) { 
                    b.bayerDither(true);
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/bayerDither_lumos.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	  case "7":
              if(!fileChosen)
                chooseFilePrompt("all actions");
              else {//file is chosen and now need to perform all actions
                for(Bitmap b : bitmaps) {   
                    b.convertToGrayscaleAverage();
                    b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale.png");
                }
                System.out.println("Success!");
                fileChosen = false; //restart menu settings
                prompt = 1;
                givePrompt();
              }
	    break;
	}
	  
      }
      else if(prompt == 2) { //this is the second prompt, (after going through the original menu)
          switch(input) {
              case "1": //user selects file location
                  prompt = 3; //go to 3rd prompt
                  break;
              case "2": //default
                  fileChosen = true;
                  prompt = 1;
                  break;
          }
      }
      else if(prompt == 3) {//ask user for the relative file location
          prompt = 4; // go to next prompt
          System.out.println();
          System.out.println("Make sure picture is in its own separate folder.");
          System.out.println("Please enter file (or x to exit):");
          System.out.print(">> ");
      }
      else {
          try {
              Bitmap image = new Bitmap(input); //user chosen image
              bitmaps.add(image);
              
              prompt = 1;
              fileChosen = true;
              
          } catch(Exception ex) {
              System.out.println();
              System.out.println("Please fix path and try again.");
              prompt = 4;
          }
      }
      
    }
  }
  
  public static void givePrompt() {
      System.out.println();
      System.out.println("What would you like to do?");
      System.out.println("1.) Convert to greyscale");
      System.out.println("2.) Convert to greyscale luminosity");
      System.out.println("3.) Dither using Stein Floyd dithering");
      System.out.println("4.) Dither using Stein Floyd dithering (luminosity)");
      System.out.println("5.) Dither with Bayer dithering");
      System.out.println("6.) Convert to Bayer dithering (luminosity)");
      System.out.println("7.) Do all");
      System.out.println("x.) Quit program");
  }
  
  public static void chooseFilePrompt(String action) {
      System.out.println();
      System.out.println("Perform " + action + " on:");
      System.out.println("1.) Enter an image's file location");
      System.out.println("2.) Default images");
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
