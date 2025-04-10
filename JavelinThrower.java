import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Write a description of class JavelinThrower here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JavelinThrower extends Fighter
{
    /**
     * Act - do whatever the SpearThrower wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private int javelinCount;
    
    public JavelinThrower(int direction, String weaponType){
        super(direction, weaponType);
    }
    
    public void act()
    {
        super.act();
    }
    
    protected boolean useSpecialAbility(){
           
        Javelin j;
        Fighter opp;
        double distance;
        
        ArrayList<Fighter> opponent = (ArrayList<Fighter>)getObjectsInRange(700, Fighter.class);
        
        //no fighters? Chuck as hard as possible
        if(opponent.isEmpty()){
           j = new Javelin(10,15,direction); 
        }else{
           opp = opponent.get(0);
           
           distance = Math.abs(getX() - opp.getX());
           
           j = new Javelin((int)(distance/45),15, direction);
           
        }
        getWorld().addObject(j,getX(),getY());
        
        return false;
    }
}
