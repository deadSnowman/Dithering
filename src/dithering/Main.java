/*
 * Grayscale conversion and dithering program for multimedia class
 */
package dithering;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.activation.MimetypesFileTypeMap;

/**
 * Main class for testing Bitmap methods
 *
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
   *
   * @param args the commandline arguments
   */
  public static void run(String[] args) {

    if (Arrays.asList(args).contains("--help") || Arrays.asList(args).contains("-h")) {
      printUsage();
    } else if (Arrays.asList(args).contains("-prompt") || Arrays.asList(args).contains("-p")) {
      System.out.println("Running in prompt mode...");
      runPrompt();
    } else if (Arrays.asList(args).contains("-test") || Arrays.asList(args).contains("-t")) {
      System.out.println("Running in test mode...");
      runTest();
    } else {

      if (Arrays.asList(args).contains("-i")) { // if there in an input file flag
	String imagePath = args[Arrays.asList(args).indexOf("-i") + 1];
	File path = new File(args[Arrays.asList(args).indexOf("-i") + 1]);
	String mimetype = new MimetypesFileTypeMap().getContentType(path);

	if (path.exists() && !path.isDirectory() && mimetype.split("/")[0].equals("image")) { // if the file is real

	  Bitmap image = new Bitmap(imagePath);
	  goThroughOptions(image, args);

	} else {
	  System.out.println("You need to specify an image file after \"-i\"");
	}
      }
    }

  }

  /**
   * Look at commandline arguments for greyscale and dithering operations
   *
   * @param image the image that is being passed through standard input
   * @param args - arguments for performing the greyscale and dithering
   * operations
   */
  public static void goThroughOptions(Bitmap image, String[] args) {
    if (Arrays.asList(args).contains("-all") || Arrays.asList(args).contains("-a")) {
      performAllOperations(image);
    }
    if (Arrays.asList(args).contains("-g")) {
      // greyscale conversion
      image.convertToGrayscaleAverage();
      image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/greyscale.png");
    }
    if (Arrays.asList(args).contains("-gl")) {
      // greyscale conversion (luminosity)
      image.convertToGrayscaleLumosity();
      image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/greyscale_lumos.png");
    }
    if (Arrays.asList(args).contains("-d")) {
      // stein floyd dithering
      image.floydSteinbergDither(false);
      image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/dither.png");
    }
    if (Arrays.asList(args).contains("-dl")) {
      // stein floyd dithering (luminosity)
      image.floydSteinbergDither(true);
      image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/dither_lumos.png");
    }
    if (Arrays.asList(args).contains("-b")) {
      // bayer matrix dithering
      image.bayerDither(false);
      image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/bayerDither.png");
    }
    if (Arrays.asList(args).contains("-bl")) {
      // bayer matrix dithering (luminosity)
      image.bayerDither(true);
      image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/bayerDither_lumos.png");
    }

  }

  /**
   * Perform all greyscale and dithering operations on the bitmap image
   *
   * @param b - the bitmap image
   */
  public static void performAllOperations(Bitmap b) {
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
    System.out.println("1.) Add an image to the collection");
    System.out.println("2.) Default images");
    System.out.println("x.) Quit program");
  }

  public static String chooseFile() {
    return "";
  }

  public static void runPrompt() {
    /*--------------------------------------------------------------------------
     start load images
     --------------------------------------------------------------------------*/
    ArrayList<Bitmap> bitmaps = new ArrayList<>();

    // /res/bitmaps needs to be in the project directory, or in /dist
    String dir = "./res/bitmaps/";
    File path = new File(System.getProperty("user.dir"));
    if (path.getName().compareTo("Dithering") != 0) {
      dir = "../res/bitmaps/";
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
    input = "";
    boolean getInput = true;
    String decision = "";

    while (true) {
      if (getInput) {
	input = scan.nextLine();
      }
      if (input.compareTo("x") == 0) {
	System.out.println();
	System.out.println("Exiting program...");
	break;
      } else if (prompt == 1) {
	++prompt; //because next time through while loop we don't want to go through this switch case.
	switch (input) {
	  case "1":
	    if (!fileChosen) {
	      chooseFilePrompt("greyscale");
	      decision = "1";
	      getInput = true;
	    } else {//file is chosen and now need to perform greyscale action
	      for (Bitmap b : bitmaps) {
		b.convertToGrayscaleAverage();
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	  case "2":
	    if (!fileChosen) {
	      chooseFilePrompt("greyscale luminosity");
	      decision = "2";
	      getInput = true;
	    } else {//file is chosen and now need to perform greyscale luminosity action
	      for (Bitmap b : bitmaps) {
		b.convertToGrayscaleLumosity();
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale_lumos.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	  case "3":
	    if (!fileChosen) {
	      chooseFilePrompt("Stein Floyd dithering");
	      decision = "3";
	      getInput = true;
	    } else {//file is chosen and now need to perform Stein Floyd dithering action
	      for (Bitmap b : bitmaps) {
		b.floydSteinbergDither(false);
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	  case "4":
	    if (!fileChosen) {
	      chooseFilePrompt("Stein Floyd dithering (luminosity)");
	      decision = "4";
	      getInput = true;
	    } else {//file is chosen and now need to perform Stein Floyd dithering (luminosity) action
	      for (Bitmap b : bitmaps) {
		b.floydSteinbergDither(true);
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither_lumos.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	  case "5":
	    if (!fileChosen) {
	      chooseFilePrompt("Bayer dithering");
	      decision = "5";
	      getInput = true;
	    } else {//file is chosen and now need to perform Bayer dithering action
	      for (Bitmap b : bitmaps) {
		b.bayerDither(false);
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/bayerDither.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	  case "6":
	    if (!fileChosen) {
	      chooseFilePrompt("Bayer dithering (luminosity)");
	      decision = "6";
	      getInput = true;
	    } else {//file is chosen and now need to perform Bayer dithering (luminosity) action
	      for (Bitmap b : bitmaps) {
		b.bayerDither(true);
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/bayerDither_lumos.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	  case "7":
	    if (!fileChosen) {
	      chooseFilePrompt("all actions");
	      decision = "7";
	      getInput = true;
	    } else {//file is chosen and now need to perform all actions
	      for (Bitmap b : bitmaps) {
		b.convertToGrayscaleAverage();
		b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale.png");
	      }
	      System.out.println("Success!");
	      fileChosen = false; //restart menu settings
	      prompt = 1;
	      givePrompt();
	      getInput = true;
	    }
	    break;
	}

      } else if (prompt == 2) { //this is the second prompt, (after going through the original menu)
	switch (input) {
	  case "1": //user selects file location
	    prompt = 3; // go to next prompt
	    System.out.println();
	    System.out.println("Make sure picture is in its own separate folder.");
	    System.out.println("Please enter file (or x to exit):");
	    System.out.print(">> ");
	    getInput = true;
	    break;
	  case "2": //default
	    fileChosen = true;
	    prompt = 1;
	    getInput = false;
	    input = decision;
	    break;
	}
      } else if (prompt == 3) {
	File userInputPath = new File(dir + input);
	String mimetype = new MimetypesFileTypeMap().getContentType(userInputPath);
	
	if (path.exists() && mimetype.split("/")[0].equals("image")) {
	  Bitmap image = new Bitmap(dir + input); //user chosen image
	  bitmaps.add(image);

	  prompt = 1;
	  fileChosen = true;
	  input = decision;
	  getInput = false;
	} else {
	  System.out.println();
	  System.out.println("ERROR:  please fix path and try again.");
	  prompt = 3;
	  getInput = true;
	  System.out.println("Make sure picture is in its own separate folder.");
	  System.out.println("Please enter file (or x to exit):");
	  System.out.print(">> ");
	}
      }

    }
  }

  /**
   * Run all the operations on specific files for testing purposes
   */
  public static void runTest() {

    ArrayList<Bitmap> bitmaps = new ArrayList<>();

    // /res/bitmaps needs to be in the project directory, or in /dist
    String dir = "./res/bitmaps/";
    File path = new File(System.getProperty("user.dir"));
    if (path.getName().compareTo("Dithering") != 0) {
      dir = "../res/bitmaps/";
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
    for (Bitmap b : bitmaps) {
      performAllOperations(b);
      System.out.println();
    }
  }

  /**
   * Print usage for commandline arguments
   */
  public static void printUsage() {
    System.out.println("Usage: java -jar Dithering.jar [-i input_file] [-g] [-gl] [-d] [-dl] [-b] [-bl]");
    System.out.println("                               [-h usage]");
    System.out.println("                               [-i input_file] [-p]");
    System.out.println("                               [-i input_file] [-a]");
    System.out.println();
    System.out.println("\t -g\tConvert to greyscale");
    System.out.println("\t -gl\tConvert to greyscale (luminosity method)");
    System.out.println("\t -d\tDither with stein floyd algorithm");
    System.out.println("\t -dl\tDither with stein floyd algorithm (luminosity method)");
    System.out.println("\t -b\tDither with bayer matrix algorithm");
    System.out.println("\t -bl\tDither with bayer matrix algorithm (luminosity method)");
    System.out.println();
    System.out.println("\t -a\tPerform all operations");
    System.out.println("\t -p\tRun program in prompt mode");
  }

}
