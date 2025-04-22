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
    protected int weight;
    
    protected String imgName;
    
    public Armor(Fighter wearer,int dmgResist,int weight){
        this.wearer = wearer;
        this.dmgResist = dmgResist;
        this.weight = weight;
    }
    
    public void act()
    {
        moveMe(wearer);
    }
    
    public void moveMe(Fighter wielder){
        if (wielder != null && getWorld() != null){
            if (wielder.getWorld() != null){
                setLocation (wielder.getX(), wielder.getY() - 18);
            }else{
                getWorld().removeObject(this);;
                return;
            }
        }    
    }
    
    public void setMyImage(String imgName){
        this.imgName = imgName;
        GreenfootImage img = new GreenfootImage(imgName + ".png");
        img.scale(90,90);
        
        if(wearer.getMyDirection() == -1){
            img.mirrorHorizontally();
        }
        
        setImage(img);
    }
    
    //for re-orienting after a direction change
    public void setMyImage(){
        setMyImage(imgName);
    }
    
    public int getDmgResist(){
        return dmgResist;
    }
    
    public int getWeight(){
        return weight;
    }
}
