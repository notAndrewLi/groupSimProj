import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Traps here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Traps extends SuperSmoothMover
{

    protected int curAct = 0;

    //declared by subclass constructors
    protected GreenfootImage deactive;
    protected GreenfootImage active;
    protected int inactiveInterval;
    protected int myDamage;
    
    protected int activeTime;
    protected boolean canTouch = true;
    protected boolean isActive = false;
    
    protected GreenfootSound attackSFX;
    public Traps(String activeImage, String deactiveImage, int inactiveInterval,int myDamage){
        active = new GreenfootImage(activeImage + ".png");
        deactive = new GreenfootImage(deactiveImage + ".png");

        this.inactiveInterval = inactiveInterval;

        setImage(deactive);
    }

    public void act(){
        curAct++;
        
        //on an interval, activate me
        if(!isActive && curAct % inactiveInterval == 0){
            setImage(active);
            isActive = true;
            activeTime = 30;
            if(attackSFX != null){
                attackSFX.play();
            }
        }

        if (isActive) {
            activeTime--;

            //put functionality here
            Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
            if (f != null && canTouch) {
                canTouch = false;
                
                f.takeDamage(myDamage);
                applyMyEffect(f);
                int x = f.getX();
                int y = f.getY();
                getWorld().addObject(new FadeText(getDamageText()), x, y);
            }

            if (activeTime <= 0) {
                setImage(deactive);
                canTouch = true;
                isActive = false;   
            }
        }
    }
    
    public abstract void applyMyEffect(Fighter f);
    
    public abstract String getDamageText();
}
