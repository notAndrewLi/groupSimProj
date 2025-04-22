import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MedArmor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MedArmor extends Armor
{
    /**
     * Act - do whatever the MedArmor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public MedArmor(Fighter wearer){
        super(wearer,2,1);
        setMyImage("medArmor");
    }
    
    public void act()
    {
        super.act();
    }
}
