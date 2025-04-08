import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Traps here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Traps extends SuperSmoothMover
{
    protected int activeTime = 0;
    protected boolean isActive = false;
    public void act(){
        if (!isActive && getOneIntersectingObject(Fighter.class) != null){
            activate();
        }

        if (isActive) {
            activeTime--;
            if (activeTime <= 0) {
                deactivate();
            }
        }
    }
    
    public abstract void activate();
    public abstract void deactivate();
}
