import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TextLabel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TextLabel extends ScreenGui
{
    /**
     * Act - do whatever the TextLabel wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //set bgColor = null for transparent bg
    public TextLabel(String text, int fontSize/*, Color textColor, Color bgColor*/) {
        GreenfootImage img = new GreenfootImage(text, fontSize, Color.WHITE, null);
        setImage(img);
    }       
    
    public void act()
    {
        // Add your action code here.
    }
}
