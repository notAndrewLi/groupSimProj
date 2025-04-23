import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FireColumn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Geyser extends Traps
{
    //private int activeTime = 0;
    //private boolean isActive = false;
    public Geyser(){
        super("fireActive","fireDeactive",180,15);
    }

    public void act()
    {
        super.act();
    }
    
    public void applyMyEffect(Fighter f){
        f.scorchFighter();
    }
    
    public String getDamageText(){
        return "SCORCHED!";
    }
}
