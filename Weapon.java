import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Weapon here.
 * 
 * @author (Andrew Li, Cyrus Yu) 
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
    protected int damage;
    //protected int weaponDir;
    protected int xOffset;
    protected int yOffset;

    protected String imageName;
    protected int imgX;
    protected int imgY;
    protected GreenfootImage theImage;

    protected GreenfootSound attackSFX;

    protected abstract void atkTelegraph();

    protected abstract void playAtkAnimation();

    protected abstract void resetAnimation();

    protected boolean fallen;

    public Weapon(Fighter wielder, int damage, int atkDuration, int telegraphDuration, int xOffset, int yOffset, int myRange){
        this.wielder = wielder;

        this.damage = damage;
        this.atkDuration = atkDuration;
        this.telegraphDuration = telegraphDuration;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.myRange = myRange;

        isDangerous = false;
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
                if(attackSFX != null){
                    attackSFX.play();
                }
                //do damage
                playAtkAnimation();

                Actor a = getOneIntersectingObject(Fighter.class);

                if(a != null){
                    Fighter f = (Fighter) a;
                    f.takeDamage(damage + wielder.getDmgBonus());
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
                setLocation (wielder.getX() + xOffset * wielder.getMyDirection(), wielder.getY() + yOffset);
            }else{
                getWorld().removeObject(this);
                return;
            }
        }    
    }

    public void setMyImage(String imageName, int x, int y){
        this.imageName = imageName;
        imgX = x;
        imgY = y;
        theImage = new GreenfootImage(imageName + ".png");
        theImage.scale(x,y);
        if(wielder.getMyDirection() == 1){
            theImage.mirrorHorizontally();
        }
        setImage(theImage);
    }

    //no parameters, for mirroring image when modifying direction
    public void setMyImage(){
        setMyImage(imageName,imgX,imgY);
    }

    protected void fallToGround() {
        GameWorld w = (GameWorld) getWorld();
        int floorY = w.getFloorY() + 15;
        turnTowards(getX(), getY() + (100 * wielder.getMyDirection()));//quick way of turning towards the ground
        setLocation(getX() + (25 * wielder.getMyDirection()), floorY - yOffset);
        fallen = true;
    }

    public int getWeaponLength(){
        return theImage.getWidth();
    }
}
