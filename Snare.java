import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Snare here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Snare extends Traps
{
    /**
     * Act - do whatever the Snare wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Snare(){
        super("snareActive","snareDeactive",180,5);
    }
    
    public void act()
    {
        super.act();
    }
    
    public void applyMyEffect(Fighter f){
        
    }
    
    public String getDamageText(){
        return "CAUGHT!";
    }
}
