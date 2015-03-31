import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A class that represents a picture.  This class inherits from 
 * SimplePicture and allows the student to add functionality to
 * the Picture class.  
 * 
 * Copyright Georgia Institute of Technology 2004-2008
 * @author Barbara Ericson ericson@cc.gatech.edu
 * @author Jasper Bingham
 */
public class Picture extends SimplePicture { 

  ///////////////////// constructors //////////////////////////////////
  
  /**
   * Constructor that takes no arguments 
   */
  public Picture () {
    
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor 
     */
    super();  
  }
  
  /**
   * Constructor that takes a file name and creates the picture 
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName) {
    // let the parent class handle this fileName
    super(fileName);
  }
  
  /**
   * Constructor that takes the width and height
   * @param width the width of the desired picture
   * @param height the height of the desired picture
   */
  public Picture(int width, int height) {
    // let the parent class handle this width and height
    super(width,height);
  }
  
  /**
   * Constructor that takes a picture and creates a 
   * copy of that picture
   */
  public Picture(Picture copyPicture) {
    // let the parent class do the copy
    super(copyPicture);
  }
  
  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image) {
    super(image);
  }
  
  ////////////////////// methods ///////////////////////////////////////
  
  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString() {
    String output = "Picture, filename " + getFileName() + 
      " height " + getHeight() 
      + " width " + getWidth();
    return output;
    
  }
  
   /**
   * Class method to let the user pick a file name and then create the picture 
   * and show it
   * @return the picture object
   */
  public static Picture pickAndShow() {
    String fileName = FileChooser.pickAFile();
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * Class method to create a picture object from the passed file name and 
   * then show it
   * @param fileName the name of the file that has a picture in it
   * @return the picture object
   */
  public static Picture showNamed(String fileName) {
    Picture picture = new Picture(fileName);
    picture.show();
    return picture;
  }
  
  /**
   * A method create a copy of the current picture and return it
   * @return the copied picture
   */
  public Picture copy()
  {
    return new Picture(this);
  }
  
  /**
   * Method to increase the red in a picture.
   */
  public void increaseRed() {
    Pixel [] pixelArray = this.getPixels();
    for (Pixel pixelObj : pixelArray) {
      pixelObj.setRed(pixelObj.getRed()*2);
    }
  }
  
  /**
   * Method to negate a picture
   */
  public void negate() {
    Pixel [] pixelArray = this.getPixels();
    int red,green,blue;
    
    for (Pixel pixelObj : pixelArray) {
      red = pixelObj.getRed();
      green = pixelObj.getGreen();
      blue = pixelObj.getBlue();
      pixelObj.setColor(new Color(255-red, 255-green, 255-blue));
    }
  }
  
  /**
   * Method to flip a picture 
   */
  public Picture flip() {
    Pixel currPixel = null;
    Pixel targetPixel = null;
    Picture target = 
      new Picture(this.getWidth(),this.getHeight());
    
    for (int srcX = 0, trgX = getWidth()-1; 
         srcX < getWidth();
         srcX++, trgX--) {
      for (int srcY = 0, trgY = 0; 
           srcY < getHeight();
           srcY++, trgY++) {
        
        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);
        targetPixel = target.getPixel(trgX,trgY);
        
        // copy the color of currPixel into target
        targetPixel.setColor(currPixel.getColor());
      }
    }
    return target;
  }
  
  /**
   * Method to decrease the red by half in the current picture
   */
  public void decreaseRed() {
  
    Pixel pixel = null; // the current pixel
    int redValue = 0;       // the amount of red

    // get the array of pixels for this picture object
    Pixel[] pixels = this.getPixels();

    // start the index at 0
    int index = 0;

    // loop while the index is less than the length of the pixels array
    while (index < pixels.length) {

      // get the current pixel at this index
      pixel = pixels[index];
      // get the red value at the pixel
      redValue = pixel.getRed();
      // set the red value to half what it was
      redValue = (int) (redValue * 0.5);
      // set the red for this pixel to the new value
      pixel.setRed(redValue);
      // increment the index
      index++;
    }
  }
  
  /**
   * Method to decrease the red by an amount
   * @param amount the amount to change the red by
   */
  public void decreaseRed(double amount) {
 
    Pixel[] pixels = this.getPixels();
    Pixel p = null;
    int value = 0;

    // loop through all the pixels
    for (int i = 0; i < pixels.length; i++) {
 
      // get the current pixel
      p = pixels[i];
      // get the value
      value = p.getRed();
      // set the red value the passed amount time what it was
      p.setRed((int) (value * amount));
    }
  }
  
  /**
   * Method to compose (copy) this picture onto a target picture
   * at a given point.
   * @param target the picture onto which we copy this picture
   * @param x target X position to start at
   * @param y target Y position to start at
   */
  public void compose(Picture target, int x, int y) {
 
    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX = x; srcX < this.getWidth();
         srcX++, trgX++) {
  
      // loop through the rows
      for (int srcY=0, trgY=y; srcY < this.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* copy the color of currPixel into target,
         * but only if it'll fit.
         */
        if (trgX < target.getWidth() && trgY < target.getHeight()) {
          newPixel = target.getPixel(trgX,trgY);
          newPixel.setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to scale the picture by a factor, and return the result
   * @param factor the factor to scale by (1.0 stays the same,
   *    0.5 decreases each side by 0.5, 2.0 doubles each side)
   * @return the scaled picture
   */
  public Picture scale(double factor) {
    
    Pixel sourcePixel, targetPixel;
    Picture canvas = new Picture(
                                 (int) (factor*this.getWidth())+1,
                                 (int) (factor*this.getHeight())+1);
    // loop through the columns
    for (double x = 0;
         x < this.getWidth();
         x+=(1/factor), x++) {
      
      // loop through the rows
      for (double y=0;
           y < this.getHeight();
           y+=(1/factor), y++) {
        
        sourcePixel = this.getPixel((int) x,(int) y);
        targetPixel = canvas.getPixel((int) x, (int) y);
        targetPixel.setColor(sourcePixel.getColor());
      }
    }
    return canvas;
  }
  
  /**
   * Method to do chromakey using an input color for the background
   * and a point for the upper left corner of where to copy
   * @param target the picture onto which we chromakey this picture
   * @param bgColor the color to make transparent
   * @param threshold within this distance from bgColor, make transparent
   * @param x target X position to start at
   * @param y target Y position to start at
   */
  public void chromakey(Picture target, Color bgColor, int threshold,
                        int x, int y) {
 
    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX=x;
        srcX<getWidth() && trgX<target.getWidth();
        srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=y;
        srcY<getHeight() && trgY<target.getHeight();
        srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel is within threshold of
         * the input color, then don't copy the pixel
         */
        if (currPixel.colorDistance(bgColor)>threshold) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
    /**
   * Method to do chromakey assuming a blue background 
   * @param target the picture onto which we chromakey this picture
   * @param x target X position to start at
   * @param y target Y position to start at
   */
  public void blueScreen(Picture target,
                        int x, int y) {

    Pixel currPixel = null;
    Pixel newPixel = null;

    // loop through the columns
    for (int srcX=0, trgX=x;
         srcX<getWidth() && trgX<target.getWidth();
         srcX++, trgX++) {

      // loop through the rows
      for (int srcY=0, trgY=y;
           srcY<getHeight() && trgY<target.getHeight();
           srcY++, trgY++) {

        // get the current pixel
        currPixel = this.getPixel(srcX,srcY);

        /* if the color at the current pixel mostly blue (blue value is
         * greater than red and green combined), then don't copy pixel
         */
        if (currPixel.getRed() + currPixel.getGreen() > currPixel.getBlue()) {
          target.getPixel(trgX,trgY).setColor(currPixel.getColor());
        }
      }
    }
  }
  
  /**
   * Method to change the picture to gray scale with luminance
   */
  public void grayscaleWithLuminance()
  {
    Pixel[] pixelArray = this.getPixels();
    Pixel pixel = null;
    int luminance = 0;
    double redValue = 0;
    double greenValue = 0;
    double blueValue = 0;

    // loop through all the pixels
    for (int i = 0; i < pixelArray.length; i++)
    {
      // get the current pixel
      pixel = pixelArray[i];

      // get the corrected red, green, and blue values
      redValue = pixel.getRed() * 0.299;
      greenValue = pixel.getGreen() * 0.587;
      blueValue = pixel.getBlue() * 0.114;

      // compute the intensity of the pixel (average value)
      luminance = (int) (redValue + greenValue + blueValue);

      // set the pixel color to the new color
      pixel.setColor(new Color(luminance,luminance,luminance));

    }
  }
  
  /**
   * @author Jasper Bingham
   * NOTE: Extra credit matrices in comment after method body.
   * Method which performs a convolution on the image, using a passed kernel.
   * @param matrix the matrix of weights
   */
  public Picture convolve(float [][] matrix)
  {
    Pixel current = null;
    Picture result = this.copy();
    //looping through columns
    for(int k = 1; k < getWidth() - 1; k++)
    {
      //loop through rows
      for(int j = 1; j < getHeight() - 1; j++)
      {
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        //loop through pixels and matrix values at same time:
        for(int y = j - 1, m = 0; y <= j + 1 && m < 3; y++, m++)
          {
            for(int x = k - 1, p = 0; x <= k + 1 && p < 3; x++, p++)
            {
              //sum products for new R/G/B values
              redSum += (int)(matrix[m][p] * getPixel(x,y).getRed());
              greenSum += (int)(matrix[m][p] * getPixel(x,y).getGreen());
              blueSum += (int)(matrix[m][p] * getPixel(x,y).getBlue());
            }
          }
        current = result.getPixel(k, j);
        //correct values in case >255 or <0
        current.setRed(Pixel.correctValue(redSum));
        current.setGreen(Pixel.correctValue(greenSum));
        current.setBlue(Pixel.correctValue(blueSum));
        //set pixel to resulting color
        result.getPixel(k, j).setColor(current.getColor());
      }
    }
    return result;
  }
  
  /**
   * EXTRA CREDIT:
   * float [][] enhance = {{1.0f, -2.0f, 1.0f},
                          {-2.0f, 5.0f, -2.0f},
                          {1.0f, -2.0f, 1.0f}};
   * float [][] emboss = {{-2.0f, -1.0f, 0.0f},
   *                     {-1.0f, 1.0f, 1.0f}
   *                     {0.0f, 1.0f, 2.0f}};
   */
  
  /** 
   * Method to do an oil paint effect on a picture
   * @param dist the distance from the current pixel 
   * to use in the range
   * @return the new picture
   */
  public Picture oilPaint(int dist) {
    
    // create the picture to return
    Picture retPict = new Picture(this.getWidth(),this.getHeight());
    
    // declare pixels
    Pixel currPixel = null;
    Pixel retPixel = null;
    
    // loop through the pixels
    for (int x = 0; x < this.getWidth(); x++) {
      for (int y = 0; y < this.getHeight(); y++) {
        currPixel = this.getPixel(x,y);
        retPixel = retPict.getPixel(x,y);
        retPixel.setColor(currPixel.getMostCommonColorInRange(dist));
      }
    }
    return retPict;
  }
  
//METHODS FROM HERE DOWN ARE ONES REQUIRED FOR PROBLEM SET 1
  
  /*
   * Helper method which finds the closest color to a specified color out of an array list of color options.
   * @param color the specified color
   * @param colors the array list of color options
   * @return the closest color (out of the color options) to the specified color 
   */
  public Color findClosestColor(Color color, ArrayList<Color> colors)
  {
      double currentDistance = 0;
      double shortestDistance = 0;
      Color finalColor = null;
      for(int i = 0; i < colors.size(); i++)
      {
        currentDistance = Pixel.colorDistance(color, colors.get(i));
        if(i == 0) // on first run, current distance equals shortest distance
        {
          shortestDistance = currentDistance;
          finalColor = colors.get(i);
        }
        else if(currentDistance < shortestDistance) //compare following distances to find shortest
        {
          shortestDistance = currentDistance;
          finalColor = colors.get(i);
        }
      }
      return finalColor;
  }
  
  /**
   * Method which generates an array list of specified size of random colors.
   * @param size the specified size of the array list.
   * @return the array list of random colors
   */
  public static ArrayList<Color> generateRandomColorList(int size)
  {
    ArrayList<Color> randomList = new ArrayList<Color>();
    //make list with as many random colors as user specifies
    for(int i = 0; i < size; i++)
    {
      //make color with random R/G/B values between 0-255
      Color randomColor = new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
      randomList.add(randomColor);
    }
    return randomList;
  }

  /**
   * Method that returns a new picture where the color of each pixel 
   * in the original picture is replaced by the color in a specified
   * group of colors that is nearest to it.
   * @param colors the specified group of colors
   * @return the new, modified picture
   */ 
  public Picture mapToColorList(ArrayList<Color> colors)
  {
    Picture result = this.copy();
    Pixel [] pixelArray = result.getPixels(); 
    for(Pixel p : pixelArray) // iterating through all the pixels in the picture
    {
      //using helper method, sets pixel color to that of closest color in colors
      p.setColor(findClosestColor(p.getColor(), colors)); 
    }
    return result;
  }
  
  /**
   * Helper method which computes the centroids of clusters formed
   * given a list of colors.
   * @param colors the list of colors
   * @return an array list of centroid colors
   */
  public ArrayList<Color> computeCentroids(ArrayList<Color> colors)
  {
    Pixel[] pixelArray = this.getPixels(); //all pixels in picture
    ArrayList<ArrayList<Color>> clusters = new ArrayList<ArrayList<Color>>(); //holder for cluster array lists
    //fill "clusters" with array lists
    for(int i = 0; i < colors.size(); i++) 
    {
      ArrayList<Color> emptyList = new ArrayList<Color>();
      clusters.add(emptyList);
    }
    //will eventually hold the centroids
    ArrayList<Color> result = new ArrayList<Color>(colors.size());
    //put pixel colors into appropriate clusters
    for(Pixel p : pixelArray)
    {
      //determine which cluster pixel is in
      Color currColor = findClosestColor(p.getColor(), colors);
      //find correct cluster array list
      int clusterNumber = colors.indexOf(currColor);
      //put color in correct array list
      clusters.get(clusterNumber).add(p.getColor());
    }
    //this loop computes centroids for each cluster
    for(ArrayList<Color> a : clusters)
    {
      //ignore empty clusters
      if(a.size() != 0)
      {
        //average R/G/B values of all colors in cluster to make a centroid
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        for(Color c : a)
        {
          redSum += c.getRed();
          greenSum += c.getGreen();
          blueSum += c.getBlue();
        }
        int avgRed = redSum / a.size();
        int avgGreen = greenSum / a.size();
        int avgBlue = blueSum / a.size();
        //make the centroid using average R/G/B values
        Color centroid = new Color(avgRed, avgGreen, avgBlue);
        //put created centroid in array list to be returned at end of method
        result.add(centroid);
      }
    }
    return result;
  }
  
  /**
   * Method which finds a certain amount of unique pixels in a picture.  
   * Unique pixels are found starting at the top left of the picture.
   * The amount of pixels to be found is specified by the user.
   * @param number the number of unique pixels to be found
   * @return an array list containing the unique pixels
   */
  public ArrayList<Color> findUniquePixels(int number)
  {
    ArrayList<Color> result = new ArrayList<Color>(number);
    //loops through pixels in picture, while making sure result is of specified size
    for(int x = 0; x < this.getWidth() && result.size() != number; x++)
    {
      for(int y = 0; y < this.getHeight() && result.size() != number; y++)
      {
        Pixel current = this.getPixel(x, y);
        //tests if pixel color is unique
        if(!result.contains(current.getColor()))
        {
          result.add(current.getColor());
        }
      }
    }
    return result;
  }
  
  /**
   * Method which finds an ideal set of colors to represent a picture
   * using the k-means method.  User specifies the number of colors
   * to use.
   * @param number the number of colors to use
   * @return an array list with the ideal set of colors
   */
  public ArrayList<Color> computeColors(int number)
  {
    ArrayList<Color> finalCentroids = null; //this will hold the centroids computed by k-means
    //start by computing centroids of random list
    ArrayList<Color> currCentroids = this.computeCentroids(Picture.generateRandomColorList(number));
    //this array list has a slightly better estimation
    //of the colors by calling computeCentroids on the
    //centroids of the random list
    ArrayList<Color> nextCentroids = this.computeCentroids(currCentroids);
    //re-cluster and compute centroids until centroids do not change
    while(!currCentroids.equals(nextCentroids))
    {
      currCentroids = this.computeCentroids(nextCentroids);
      nextCentroids = this.computeCentroids(currCentroids);
    }
    finalCentroids = currCentroids;
    return finalCentroids;
  }  
  
  /**
   * Method which finds an ideal set of colors to represent a picture
   * using the k-means method.  User provides an array list of colors
   * with which to begin clustering.
   * @param initColors the array list of colors with which to begin clustering
   * @return an array list with the ideal set of colors
   */
  public ArrayList<Color> computeColors(ArrayList<Color> initColors)
  {
    ArrayList<Color> finalCentroids = null; //this will hold the centroids computed by k-means
    //start by computing centroids of provided array list
    ArrayList<Color> currCentroids = this.computeCentroids(initColors);
    //this array list has a slightly better estimation
    //of the colors by calling computeCentroids on the
    //centroids of the provided array list
    ArrayList<Color> nextCentroids = this.computeCentroids(currCentroids);
    //re-cluster and compute centroids until centroids do not change
    while(!currCentroids.equals(nextCentroids))
    {
      currCentroids = this.computeCentroids(nextCentroids);
      nextCentroids = this.computeCentroids(currCentroids);
    }
    finalCentroids = currCentroids;
    return finalCentroids;
  }  
  
  /**
   * Method which reduces the number of colors in a picture
   * to a certain number.  Colors are determined using the k-means
   * method.
   * @param number the number of colors to reduce the picture to
   * @return the new picture
   */
  public Picture reduceColors(int number)
  {
    return this.mapToColorList(computeColors(number));
  }
  
  public static void main(String[] args)
  {
    Picture p = 
      new Picture(FileChooser.pickAFile());
    p.explore();
    Picture q = p.oilPaint(5);
    q.explore();
  }
  
  
      
} // this is the end of class Picture, put all new methods before this
 