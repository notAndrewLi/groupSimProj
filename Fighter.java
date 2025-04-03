import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Fighter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Fighter extends SuperSmoothMover
{
    /**
     * Act - do whatever the Fighter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    protected int health;
    protected int curAct;
    protected int direction;
    
    protected boolean isAggro;
    protected boolean isTimid;
    
    public Fighter(){
        //bagaga
        direction = 50;
        
        isAggro = true;
        isTimid = false;
    }
    
    public void act()
    {
       curAct++;
       
       if(isAggro){
           move(Math.abs(direction));
       }else if(isTimid){
           move(-Math.abs(direction));
       }else if(curAct % 120 == 0){
           move(getNewDirection());
       }
    }
    
    private int getNewDirection(){
        if(Greenfoot.getRandomNumber(2) == 0){
            return direction * -1;
        }
        return direction;
    }
}
