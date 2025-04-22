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
    
    private int fontSize;
    private Color textColor;
    
    //set bgColor = null for transparent bg
    public TextLabel(String text, int fontSize) {
        this.fontSize = fontSize;
        this.textColor = new Color(255,255,255);
        GreenfootImage img = new GreenfootImage(text, fontSize, textColor, null);
        setImage(img);
    }       
    
    //constructor with text color
    public TextLabel(String text, int fontSize,Color textColor/*Color bgColor*/) {
        this.fontSize = fontSize;
        this.textColor = textColor;
        GreenfootImage img = new GreenfootImage(text, fontSize, textColor, null);
        setImage(img);
    } 
    
    public int getFontSize(){
        return fontSize;
    }
    
    public Color getColor(){
        return textColor;
    }
}
