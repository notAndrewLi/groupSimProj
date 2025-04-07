import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Traps here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Traps extends SuperSmoothMover
{

    public void act()
    {
        if(getOneIntersectingObject(Fighter.class) != null){
            activate();
            this.sleepFor(10);
            deactivate();
        }
    }

    public void activate(){
        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        f.takeDamage(25);
    }

    public abstract void deactivate();
}
