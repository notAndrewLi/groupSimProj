import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class LightArmor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class LightArmor extends Armor
{
    /**
     * Act - do whatever the LightArmor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public LightArmor(Fighter wearer){
        super(wearer,5);
        setMyImage("lightArmor");
    }
    
    public void act()
    {
        super.act();
    }
}
