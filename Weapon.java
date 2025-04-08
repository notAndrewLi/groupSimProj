import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Weapon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Weapon extends Actor
{
    /**
     * Act - do whatever the Weapon wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    protected Fighter wielder;
    
    protected boolean isDangerous;
    
    protected int curAct;
    
    protected int telegraphEnd;
    protected int attackEnd;
    protected int atkDuration;
    protected int telegraphDuration;
    
    protected abstract void atkTelegraph();
    
    protected abstract void playAtkAnimation();
    
    protected abstract void resetAnimation();
    
    public Weapon(Fighter wielder){
        this.wielder = wielder;
        
        //should be defined in the subclasses
        //atkDuration = 7;
        
        isDangerous = true;
    }
    
    public void act()
    {
        curAct++;
        
        moveMe(this.wielder);
        
        if(curAct % 120 == 0){
            //resetAnimation();
            attack();
        }
        
        //While current act is less than the given value
        if(isDangerous && curAct < telegraphEnd){
            atkTelegraph();
        }else if(isDangerous && curAct < attackEnd){
            //do damage
            playAtkAnimation();
            
            Actor a = getOneIntersectingObject(Fighter.class);
            
            if(a != null){
                Fighter f = (Fighter) a;
                f.takeDamage(10);
                //do damage do this
            }
        }else{
            isDangerous = false;
            resetAnimation();
        }
    }
    
    public void attack(){
        telegraphEnd = curAct + 20;
        attackEnd = telegraphEnd + atkDuration;
        isDangerous = true;
    }
    
    public void moveMe(SuperSmoothMover wielder){
        if (wielder != null && getWorld() != null){
            if (wielder.getWorld() != null){
                setLocation (wielder.getX() - 30, wielder.getY() - 20);
            }else{
                getWorld().removeObject(this);
                return;
            }
        }    
    }
    
    protected void setMyImage(String imageName, int x, int y){
        GreenfootImage theImage = new GreenfootImage(imageName + ".png");
        theImage.scale(x,y);
        setImage(theImage);
    }
}
