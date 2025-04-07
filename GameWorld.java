import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class GameWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GameWorld extends World
{
    GreenfootImage bg = new GreenfootImage("images/background.jpg");
    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorld()
    {    
        
        super(1024, 768, 1, true);
        setBackground(bg);
        Spikes spikeTrap = new Spikes();
        addObject(spikeTrap, 150, 700); 
        
        Geyser geyserTrap = new Geyser();
        addObject(geyserTrap, 900, 690);
        
        TwoHanded t = new TwoHanded(1);
        addObject(t, 768, 500);
        
        JavelinThrower j = new JavelinThrower(-1);
        addObject(j, 100, 500);

    }
}
