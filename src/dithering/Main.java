/*
 * Grayscale conversion and dithering program for multimedia class
 */
package dithering;

import java.util.ArrayList;

/**
 * Main class for testing Bitmap methods
 * @author seth
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    
    Bitmap image3 = new Bitmap("wings/mtg__gift_of_orzhova_by_algenpfleger-d5sj6ir.jpg"); // wings
    Bitmap image5 = new Bitmap("apocalyptic/oh_god_where_is_my_gun_by_alexiuss-d7smmkm.jpg"); // apocalyptic
    Bitmap image6 = new Bitmap("forest/Photo by Ray Bilcliff- (15).jpg"); // forest
    Bitmap image7 = new Bitmap("lenna/Lenna.png"); // lenna
    
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
<<<<<<< HEAD
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/out2.png");
=======
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/greyscale_lumos.png");
      b.floydSteinbergDither(false);
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither.png");
      b.floydSteinbergDither(true);
      b.writeBitmap(b.getFileName().substring(0, b.getFileName().lastIndexOf("/")) + "/dither_lumos.png");
      
>>>>>>> b7394e3de3d9cdf3e2dedfedd79a4ad85850c965
      System.out.println();
    }
    
    // Just test 1 image
    /*Bitmap image = new Bitmap("wings/mtg__gift_of_orzhova_by_algenpfleger-d5sj6ir.jpg"); // wings
    System.out.println("Width: " + image.getWidth() + ", " + "height: " + image.getHeight());      
    //image.convertToGrayscaleAverage();
    //image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/out.png");
    //image.convertToGrayscaleLumosity();
    //image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/out2.png");
    image.floydSteinbergDither(false);
    //image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/out3.png");
    
    image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/outTest.png");
    
    image.floydSteinbergDither(true);
    image.writeBitmap(image.getFileName().substring(0, image.getFileName().lastIndexOf("/")) + "/outTest2.png");*/
    
    
  }
  
}
