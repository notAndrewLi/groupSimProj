import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Javelin here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Javelin extends SuperSmoothMover
{
    /**
     * Act - do whatever the Javelin wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private static int floorY;
    private static double gravity; 
    private GameWorld world;
    
    private Fighter target;
    private double xVelocity;
    private double yVelocity;
    private double angularVel;
    private int direction;

    public Javelin(double theXVel, double theYVel, int direction){
        gravity = 1;
        xVelocity = theXVel * direction;
        yVelocity = 5 + theYVel;
        angularVel = 2 * direction;
        this.direction = direction;
        
        GreenfootImage theImage = new GreenfootImage("javelin.png");
        theImage.scale(100,5);
        if(direction == 1){
            theImage.mirrorHorizontally();
        }
        setImage(theImage);
    }

    public void addedToWorld(World w){
        setRotation((yVelocity + 20) * -direction);
        
        if (w instanceof GameWorld) {
            //Cast the world to MyWorld and call the method
            world = (GameWorld) w;
            
            floorY = world.getFloorY();
        }
    }

    public void act()
    {
        // Add your action code here.

        //cool weapon idea maybe
        /*move(velocity);

        turn(velocity);

        velocity -= 1;*/

        //turn(velocity);
        
        move(xVelocity);
        
        fall();
        
    }
    public void fall(){
        setLocation(getX(), getY() - yVelocity);
        
        turn(angularVel);
        
        yVelocity -= gravity;
        if(getY() > floorY || Math.abs(floorY - getY()) <= 5){
            yVelocity = 0;
            
            xVelocity = 0;
            
            angularVel = 0;
            
            setLocation(getX(), floorY);

        } 
    }
}
