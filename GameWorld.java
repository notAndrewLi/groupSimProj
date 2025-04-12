import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Arrays;
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
    public GameWorld(int myClass,int myWeapon, int myArmor, int myPotions)
    {    

        super(1024, 768, 1, true);
        setBackground(bg);
        for(int i = 0; i < 80; i++){
            spawnTraps(0);
            spawnTraps(1);
        }
        
        spawnHero(myClass,myWeapon,myArmor,myPotions);
        
        spawnOpponent();
        
        //Greenfoot.delay(120);
        
        /*TwoHanded t = new TwoHanded(-1, "sword");
        addObject(t, 924, floorY);

        JavelinThrower j = new JavelinThrower(1, "spear");
        addObject(j, 100, floorY);*/

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

    private void spawnHero(int myClass, int myWeapon,int myArmor, int myPotions){
        //myWeapon gives us the index for the weapon that should be given to the fighter
        String[] weapons = {"sword","spear"};

        //"Arrays.asList" taken from ChatGPT
        //myClass gives us the index for the appropriate class
        ArrayList<Fighter> fighterClasses = new ArrayList<Fighter>(Arrays.asList(
            new JavelinThrower(1,weapons[myWeapon]),
            new TwoHanded(1,weapons[myWeapon]),
            new ShieldBearer(1,weapons[myWeapon])
        ));

        //do something with the potions and armor later on

        //main character goes on left side
        addObject(fighterClasses.get(myClass), 100, floorY);
    }

    private void spawnOpponent(){
        int myWeapon;
        int myClass;
        
        String[] weapons = {"sword","spear"};
        myWeapon = Greenfoot.getRandomNumber(weapons.length);

        //"Arrays.asList" taken from ChatGPT
        ArrayList<Fighter> fighterClasses = new ArrayList<Fighter>(Arrays.asList(
            new JavelinThrower(-1,weapons[myWeapon]),
            new TwoHanded(-1,weapons[myWeapon]),
            new ShieldBearer(-1,weapons[myWeapon])
        ));
        myClass = Greenfoot.getRandomNumber(fighterClasses.size());
        
        //do something with the potions and armor later on

        //opponent on right
        addObject(fighterClasses.get(myClass), 924, floorY);
    }

    public int getFloorY(){
        return floorY;
    }
}
