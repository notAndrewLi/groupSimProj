import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Armor here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Armor extends Actor
{
    /**
     * Act - do whatever the Armor wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    protected Fighter wearer;
    protected int dmgResist;
    
    public Armor(Fighter wearer,int dmgResist){
        this.wearer = wearer;
        this.dmgResist = dmgResist;
    }
    
    public void act()
    {
        moveMe(wearer);
    }
    
    public void moveMe(Fighter wielder){
        if (wielder != null && getWorld() != null){
            if (wielder.getWorld() != null){
                setLocation (wielder.getX(), wielder.getY() - 10);
            }else{
                getWorld().removeObject(this);
                return;
            }
        }    
    }
    
    public void setMyImage(String imgName){
        GreenfootImage img = new GreenfootImage(imgName + ".png");
        img.scale(40,40);
        
        if(wearer.getMyDirection() == -1){
            img.mirrorHorizontally();
        }
        
        setImage(img);
    }
    
    public int getDmgResist(){
        return dmgResist;
    }
}
