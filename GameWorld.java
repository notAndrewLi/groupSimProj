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

    
    private Fighter MC;
    private Fighter OPP;
    private int fighterHP;
    private static int gold = 0;

    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorld(int[] customizationType, int[] upgradeBonuses)
    {    

        super(1024, 768, 1, true);
        setBackground(bg);
        //min amnt of traps: 1
        //max amnt of traps: 3
        for(int i = 0; i < (1 + Greenfoot.getRandomNumber(3)); i++){
            spawnTraps();
        }
        
        MC = spawnHero(customizationType, upgradeBonuses);
        
        OPP = spawnOpponent();
        
        OPP.setAsOpponent();
    }
    
    public void act(){
        spawnSpawnables(0);
        spawnSpawnables(1);
    }
    
    public void spawnTraps(){
        //0,1,2
        int randomChance = Greenfoot.getRandomNumber(3);
        int randX = Greenfoot.getRandomNumber(getWidth());
        
        if(randomChance == 0){
            addObject(new Geyser(), randX, floorY - 10);
        }else if(randomChance == 1){
            addObject(new Spikes(), randX, floorY);
        }else{
            addObject(new Snare(), randX, floorY);
        }
    }
    
    public void spawnSpawnables(int type){
        int randomChance = (type == 0) ? Greenfoot.getRandomNumber(120) : Greenfoot.getRandomNumber(300);
        if(randomChance == 0){
            int randX = Greenfoot.getRandomNumber(getWidth());
            if(type == 0){
                addObject(new PileOfGold(), randX, floorY);
            }
            else if(type == 1){
                addObject(new PileOfSold(), randX, floorY);
            }
        }  
    }

    private Fighter spawnHero(int[] customizationType, int[] upgradeBonuses){
        //myWeapon gives us the index for the weapon that should be given to the fighter
        String[] weapons = {"sword","spear"};
        
        int myClass = customizationType[0];
        int myWeapon = customizationType[1];
        int myArmor = customizationType[2];
        int myPots = customizationType[3];
        
        //"Arrays.asList" taken from ChatGPT
        //myClass gives us the index for the appropriate class
        ArrayList<Fighter> fighterClasses = new ArrayList<Fighter>(Arrays.asList(
            new JavelinThrower(1,weapons[myWeapon],upgradeBonuses),
            new TwoHanded(1,weapons[myWeapon],upgradeBonuses),
            new ShieldBearer(1,weapons[myWeapon],upgradeBonuses)
        ));

        //do something with the potions and armor later on

        //main character goes on left side
        Fighter mc = fighterClasses.get(myClass);
        addObject(mc, 100, floorY);
        
        return mc;
    }

    private Fighter spawnOpponent(){
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
        Fighter opp = fighterClasses.get(myClass);
        addObject(opp, 924, floorY);
        
        return opp;
    }

    public int addGold(int goldAmount){
        this.gold += goldAmount;
        return gold;
    }
    
    public int removeGold(int goldAmount){
        this.gold -= goldAmount;
        return gold;
    }
    
    public int getGold(){
        return gold;
    }
    
    public int getFloorY(){
        return floorY;
    }
}
