import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TwoHanded here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TwoHanded extends Fighter
{
    /**
     * Act - do whatever the TwoHanded wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    protected int swingCount;
    
    public TwoHanded(int direction, String weaponType){
        super(direction, weaponType, null);
    }
    
    //with stats modified
    public TwoHanded(int direction, String weaponType, int[] upgradeBonuses){
        super(direction, weaponType, upgradeBonuses);
    }
    
    public void act()
    {
        super.act();
    }
        
    protected boolean useSpecialAbility(){
        //swing three times while moving forward
        
        move(movementSpd/2 * direction);
        
        if(!myWeapon.isDangerous()){
            myWeapon.attack();
            swingCount++;
        }
        
        if(swingCount >= 3){
            return false;
        }
        
        return true;
    }
}
