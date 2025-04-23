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
    GreenfootImage bg; //= new GreenfootImage("images/baldHead.png");
    private int floorY;//just do a constant for now

    private Fighter MC;
    private Fighter OPP;
    private int fighterHP;
    private int mcHealingCount = 0;
    private int oppHealingCount;
    private static int gold = 0;
    private int coinCeiling = 100;

    private String fighterName;
    /**
     * Constructor for objects of class GameWorld.
     * 
     */
    public GameWorld(int[] customizationType, int[] upgradeBonuses, String fighterName)
    {    
        super(1024, 768, 1, true);
        
        String[] bgImgs = {"carpet","baldHead","dinnerTable"};
        int[] floorVals = {480,470,495};
        
        int randInt = Greenfoot.getRandomNumber(bgImgs.length);
        bg = new GreenfootImage("images/" + bgImgs[randInt] + ".png");
        setBackground(bg);
        
        floorY = floorVals[randInt];
        
        //min amnt of traps: 3
        //max amnt of traps: 5
        for(int i = 0; i < (3 + Greenfoot.getRandomNumber(5)); i++){
            spawnTraps();
        }
        this.fighterName = fighterName;

        MC = spawnHero(customizationType, upgradeBonuses);

        OPP = spawnOpponent();

    }

    public void act(){
        spawnSpawnables(0);
        spawnSpawnables(1);

        //spawning health pots
        if(MC.getHP() <= 50 && mcHealingCount > 0){
            addObject(new Potion(false),MC.getX(),0);
            mcHealingCount -= 1;
        }

        if(OPP.getHP() <= 50 && oppHealingCount > 0){
            addObject(new Potion(true),OPP.getX(),0);
            oppHealingCount -= 1;
        }

        //checking if fighters are facing the right way
        if(MC.getX() > OPP.getX() && MC.getMyDirection() == 1){
            //set my direction = -1
            MC.setDirection(-1);

            //set opp direction = 1
            OPP.setDirection(1);
        }else if(MC.getX() < OPP.getX() && MC.getMyDirection() == -1){
            //set my direction = 1
            MC.setDirection(1);
            //set opp direction = -1
            OPP.setDirection(-1);
        }
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
        int randomChance = (type == 0) ? Greenfoot.getRandomNumber(300) : Greenfoot.getRandomNumber(700);
        if(randomChance == 0){
            int randX = Greenfoot.getRandomNumber(getWidth());
            if(type == 0){
                PileOfGold g = new PileOfGold();
                addObject(g, randX, coinCeiling);
            }
            else if(type == 1){
                PileOfSold s = new PileOfSold();
                addObject(s, randX, coinCeiling);
            }
        }  
    }

    private Fighter spawnHero(int[] customizationType, int[] upgradeBonuses){
        //myWeapon gives us the index for the weapon that should be given to the fighter

        int myClass = customizationType[0];
        mcHealingCount = customizationType[3] + 1;

        //"Arrays.asList" taken from ChatGPT
        //myClass gives us the index for the appropriate class
        ArrayList<Fighter> fighterClasses = new ArrayList<Fighter>(Arrays.asList(
                    new JavelinThrower(1,customizationType,upgradeBonuses),
                    new TwoHanded(1,customizationType,upgradeBonuses),
                    new ShieldBearer(1,customizationType,upgradeBonuses)
                ));

        //do something with the potions and armor later on

        //main character goes on left side
        Fighter mc = fighterClasses.get(myClass);
        addObject(mc, 100, floorY);
        Indicator ind = new Indicator(mc);
        addObject(ind, 0, 0);

        return mc;
    }

    private Fighter spawnOpponent(){
        int myWeapon;
        int myClass;

        //modify whenever new content added
        int[] customizationType = {0,Greenfoot.getRandomNumber(2),Greenfoot.getRandomNumber(2),Greenfoot.getRandomNumber(3)};
        oppHealingCount = customizationType[3] + 1;

        //"Arrays.asList" taken from ChatGPT
        ArrayList<Fighter> fighterClasses = new ArrayList<Fighter>(Arrays.asList(
                    new JavelinThrower(-1,customizationType),
                    new TwoHanded(-1,customizationType),
                    new ShieldBearer(-1,customizationType)
                ));
        myClass = Greenfoot.getRandomNumber(fighterClasses.size());

        //do something with the potions and armor later on

        //opponent on right
        Fighter opp = fighterClasses.get(myClass);
        opp.setAsOpponent();
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

    public String getMCName(){
        return fighterName;
    }
}
