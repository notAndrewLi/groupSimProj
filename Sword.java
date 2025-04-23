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
        super(wielder,15,10,20,20,0,60);
        
        //test weapon; one taps opponent
        //super(wielder,100,10,20,20,0,60);
        
        setMyImage("sword(1)", 100, 30);
    }
    
    public void act()
    {
        super.act();
    }
    
    protected void atkTelegraph(){
        //should modify depending on fighter's direction
        turn(-1 * wielder.getMyDirection());
    }
    
    protected void playAtkAnimation(){
        //System.out.println("sword attack");
        
        turn(9 * wielder.getMyDirection());
    }
    
    protected void resetAnimation(){
        setRotation(-20 * wielder.getMyDirection());
    }
}
