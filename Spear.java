import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sword here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spear extends Weapon
{
    /**
     * Act - do whatever the Sword wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Spear(Fighter wielder){
        super(wielder);
        
        atkDuration = 10;
        telegraphDuration = 20;
        
        xOffset = 40;
        yOffset = 15;
        
        myRange = 100;
        
        setMyImage("spear", 100, 20);
    }
    
    public void act()
    {
        super.act();
    }
    
    protected void atkTelegraph(){
        //should modify depending on fighter's direction
        xOffset += -2 * weaponDir;
    }
    
    protected void playAtkAnimation(){
        xOffset += 6 * weaponDir;
    }
    
    protected void resetAnimation(){
        xOffset = 20;
    }
}
