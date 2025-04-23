import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Frame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Frame extends ScreenGui
{
    /**
     * Act - do whatever the Frame wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Frame(int width,int height, Color theColor){       
       theImage = new GreenfootImage(width, height);
       
       theImage.setColor(theColor);
       
       theImage.fillRect(0,0,width, height);
       
       setImage(theImage);
    }
}
