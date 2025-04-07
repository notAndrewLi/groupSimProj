import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FireColumn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Geyser extends Traps
{
    GreenfootImage deactive = new GreenfootImage("geyserDeactive.png");
    GreenfootImage active = new GreenfootImage("geyserActive.png");
    public Geyser(){
        setImage(deactive);
    }
    public void act()
    {
        super.act();
    }
    
    public void activate(){
        setImage(active);
        super.activate();
    }
    
    public void deactivate(){
        setImage(deactive);
    }
}
