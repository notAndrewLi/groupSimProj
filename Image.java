import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Image here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Image extends ScreenGui
{
    /**
     * Act - do whatever the Image wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Image(int width, int height,String imageName, boolean mirror){
        theImage = new GreenfootImage(imageName + ".png");
        theImage.scale(width,height);
        if(mirror){
            theImage.mirrorHorizontally();
        }
        setImage(theImage);
    }
    
    public void act()
    {
        // Add your action code here.
    }
}
