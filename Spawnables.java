import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spawnables here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spawnables extends SuperSmoothMover
{
    protected int yVelocity = 0;
    protected int gravity = 1;
    protected int floorY = 600;
    /**
     * Act - do whatever the Spawnables wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        // Add your action code here.
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
