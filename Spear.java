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
        super(wielder,10,10,20,50,20,100);
        
        setMyImage("spear", 150, 20);
        attackSFX = new GreenfootSound("spearSFX.mp3");
    }
    
    public void act()
    {
        super.act();
    }
    
    protected void atkTelegraph(){
        //should modify depending on fighter's direction
        xOffset += -2 * wielder.getMyDirection();
    }
    
    protected void playAtkAnimation(){
        xOffset += 6 * wielder.getMyDirection();
    }
    
    protected void resetAnimation(){
        xOffset = 50;
    }
}
