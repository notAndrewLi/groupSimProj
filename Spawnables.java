import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spawnables here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Spawnables extends SuperSmoothMover
{
    protected int yVelocity = 0;
    protected int gravity = 1;
    protected int floorY;
    
    public void addedToWorld(World w){
        GameWorld gw = (GameWorld) w;
        
        floorY = gw.getFloorY();
    }
    
    public void fall() {
        setLocation(getX(), getY() + yVelocity);
        yVelocity += gravity;

        //stop falling if it hits the floor
        if (getY() >= floorY) {
            setLocation(getX(), floorY);
            yVelocity = 0;
        }
    }
}
