import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameWorld extends World
{
    GreenfootImage bg = new GreenfootImage("images/background(3).png");
    private int floorY = 600;//just do a constant for now
    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorld()
    {    
        
        super(1024, 768, 1, true);
        setBackground(bg);
        Spikes spikeTrap = new Spikes();
        addObject(spikeTrap, 150, floorY); 
        
        Geyser geyserTrap = new Geyser();
        addObject(geyserTrap, 900, floorY - 10);
                
        TwoHanded t = new TwoHanded(-1, "sword");
        addObject(t, 924, floorY);
        
        JavelinThrower j = new JavelinThrower(1, "spear");
        addObject(j, 100, floorY);
    }
    
    public int getFloorY(){
        return floorY;
    }
}
