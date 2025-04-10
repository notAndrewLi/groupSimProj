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
    private int floorY = 700;//just do a constant for now
    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorld()
    {    

        super(1024, 768, 1, true);
        setBackground(bg);

        for(int i = 0; i < 70; i++){
            spawnTraps(0);
            spawnTraps(1);
        }

        TwoHanded t = new TwoHanded(-1);
        addObject(t, 768, 715);

        JavelinThrower j = new JavelinThrower(1);
        addObject(j, 100, 715);

    }

    public void spawnTraps(int type){
        int randomChance = Greenfoot.getRandomNumber(60);
        if(randomChance == 0){
            int randX = Greenfoot.getRandomNumber(getWidth());
            if(type == 0){
                addObject(new Geyser(), randX, floorY - 10);
            }
            else if(type == 1){
                addObject(new Spikes(), randX, floorY);
            }
        }  
    }

    public int getFloorY(){
        return floorY;
    }
}
