import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Sword here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sword extends Weapon
{
    /**
     * Act - do whatever the Sword wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public Sword(Fighter wielder){
        super(wielder);
        
        atkDuration = 10;
        telegraphDuration = 20;
        
        setMyImage("sword(1)", 100, 30);
    }
    
    public void act()
    {
        super.act();
    }
    
    protected void atkTelegraph(){
        //should modify depending on fighter's direction
        turn(3);
    }
    
    protected void playAtkAnimation(){
        turn(-15);
    }
    
    protected void resetAnimation(){
        setRotation(20);
    }
}
