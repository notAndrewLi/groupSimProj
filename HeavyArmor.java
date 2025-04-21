import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class HeavyArmor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HeavyArmor extends Armor
{
    /**
     * Act - do whatever the HeavyArmor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public HeavyArmor(Fighter wearer){
        super(wearer,3,2);
        setMyImage("heavyArmor");
    }
    
    public void act()
    {
        super.act();
    }
}
