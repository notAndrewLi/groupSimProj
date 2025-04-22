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
    
    private Actor myShield;
    
    public ShieldBearer(int direction, int[] customizationType){
        super(direction, customizationType, null);
    }
    
    //with stats modified
    public ShieldBearer(int direction, int[] customizationType, int[] upgradeBonuses){
        super(direction, customizationType, upgradeBonuses);
    }
    
    /*public void addedToWorld(World w){
        myShield = new Image(25,25,"shield",false);
        
        w.addObject(myShield,getX(),getY());
    }*/
    
    public void act()
    {
        super.act();
        
        /*if (this != null && getWorld() != null){
            if (this.getWorld() != null){
                myShield.setLocation (this.getX(), this.getY());
            }else{
                getWorld().removeObject(myShield);
                return;
            }
        } */ 
    }
        
    protected boolean useSpecialAbility(){
        //for things that you want to happen only once per ability cast
        if(!actOngoing){
            actOngoing = true;
            boolean mirrorImage = false;
            if(direction == -1){
                mirrorImage = true;
            }
            
            myShield = new Image(100,100,"shield",mirrorImage);
            getWorld().addObject(myShield,getX(),getY());
            
            //shield bearer gets iFrames
            iFrames = true;
            iFramesEndAct = curAct + 180;
            endAct = iFramesEndAct;
        }
        
        if (this != null && getWorld() != null){
            if (this.getWorld() != null){
                myShield.setLocation (this.getX() + 20 * direction, this.getY());
            }else{
                getWorld().removeObject(myShield);
            }
        } 
        
        //end ability
        if(curAct >= endAct){
            getWorld().removeObject(myShield);
            actOngoing = false;
            return false;
        }
        
        move(1 * direction);
        
        return true;
    }
}
