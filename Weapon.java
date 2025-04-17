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

    protected int myRange;
    protected int weaponDir;
    protected int xOffset;
    protected int yOffset;

    protected GreenfootImage theImage;

    protected abstract void atkTelegraph();

    protected abstract void playAtkAnimation();

    protected abstract void resetAnimation();

    protected boolean fallen;

    public Weapon(Fighter wielder){
        this.wielder = wielder;

        weaponDir = wielder.getMyDirection();

        //should be defined in the subclasses
        //atkDuration = 7;

        isDangerous = true;
    }

    public void act()
    {
        if(!fallen){
            curAct++;
            moveMe(wielder);

            //While current act is less than the given value
            if(isDangerous && curAct < telegraphEnd){
                atkTelegraph();
            }else if(isDangerous && curAct < attackEnd){
                //do damage
                playAtkAnimation();

                Actor a = getOneIntersectingObject(Fighter.class);

                if(a != null){
                    Fighter f = (Fighter) a;
                    f.takeDamage(5);
                    //do damage do this
                }
            }else{
                isDangerous = false;
                resetAnimation();
            }
        }
    }

    public void attack(){
        telegraphEnd = curAct + 20;
        attackEnd = telegraphEnd + atkDuration;
        isDangerous = true;
    }

    public boolean isDangerous(){
        return isDangerous;
    }

    public int getRange(){
        return myRange;
    }

    public void moveMe(Fighter wielder){
        if (wielder != null && getWorld() != null){
            if (wielder.getWorld() != null){
                setLocation (wielder.getX() + xOffset * weaponDir, wielder.getY() + yOffset);
            }else{
                getWorld().removeObject(this);
                return;
            }
        }    
    }

    protected void setMyImage(String imageName, int x, int y){
        theImage = new GreenfootImage(imageName + ".png");
        theImage.scale(x,y);
        if(weaponDir == 1){
            theImage.mirrorHorizontally();
        }
        setImage(theImage);
    }

    protected void fallToGround() {
        GameWorld w = (GameWorld) getWorld();
        int floorY = w.getFloorY() + 15;
        turnTowards(getX(), getY() + (100 * weaponDir));//quick way of turning towards the ground
        setLocation(getX() + (25 * weaponDir), floorY - yOffset);
        fallen = true;
    }
}
