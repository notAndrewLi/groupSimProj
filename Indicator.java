import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Indicator here.
 * 
 * @author (Andrew Li) 
 * @version (a version number or a date)
 */
public class Indicator extends Actor
{
    /**
     * Act - do whatever the Indicator wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private Fighter fighter;
    
    public Indicator(Fighter fighter){
        this.fighter = fighter;
        GreenfootImage img = new GreenfootImage("indicator.png");
        img.scale(50,25);
        setImage(img);
    }
    public void act()
    {
        // Add your action code here.
        track();
    }
    
    public void track(){
        setLocation(fighter.getX(), fighter.getY() - 100);
    }
}
