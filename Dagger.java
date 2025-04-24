import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Dagger here.
 * 
 * @author (Andrew Li, Cyrus Yu) 
 * @version (a version number or a date)
 */
public class Dagger extends Weapon
{
    /**
     * Act - do whatever the Dagger wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    
    public Dagger(Fighter wielder){
        super(wielder,5,40,60,30,0,150);
        setMyImage("dagger", 70, 30);
        attackSFX = new GreenfootSound("daggerSFX.mp3");
    }
    
    public void act()
    {
        super.act();
    }
    
    protected void playAtkAnimation(){
        //charge forwards
        wielder.move(30 * wielder.getMyDirection());
    }
    
    protected void atkTelegraph(){
        wielder.move(3 * -wielder.getMyDirection());
    }
    
    protected void resetAnimation(){
        //doesn't do anything
    }
}
