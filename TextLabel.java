import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
//import java.awt.Font;
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
        this(text, fontSize, new Color(255,255,255));
    }       
    
    //constructor with text color
    public TextLabel(String text, int fontSize,Color textColor) {
        this.fontSize = fontSize;
        this.textColor = textColor;
        
        /*int x = fontSize/2  * text.length();
        int y = fontSize * 2;
        
        GreenfootImage img = new GreenfootImage(x,y);
        greenfoot.Font customFont = new greenfoot.Font("DejaVu Sans Mono",false,false,fontSize);
        
        img.setFont(customFont);
        img.setColor(textColor);
        img.drawString(text,0,y/2);*/
        
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
