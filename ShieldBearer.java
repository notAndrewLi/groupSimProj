import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ShieldBearer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ShieldBearer extends Fighter
{
    /**
     * Act - do whatever the ShieldBearer wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private int hpThresh;
    
    public ShieldBearer(int direction, String weaponType){
        super(direction, weaponType);
    }
    
    public void act()
    {
        super.act();
    }
        
    protected boolean useSpecialAbility(){
        //raise my shield
        if(!actOngoing){
            actOngoing = true;
            endAct = curAct + 60;
            hpThresh = health;
        }
        
        //set a threshold for health; shield bearer can't fall below this threshold
        //give ShieldBearer iFrames
        if(health < hpThresh){
            health = hpThresh;
        }
        
        if(curAct >= endAct){
            actOngoing = false;
            return false;
        }
        
        move(movementSpd/2 * -direction);
        
        return true;
    }
}
