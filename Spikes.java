import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spikes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spikes extends Traps
{
    GreenfootImage deactive = new GreenfootImage("spikeTrapDeactive.png");
    GreenfootImage active = new GreenfootImage("spikeTrapActive.png");
    
    //private int activeTime = 0;
    //private boolean isActive = false;
    public Spikes(){
        setImage(deactive);
    }

    public void act()
    {
        super.act();
    }

    public void activate(){
        setImage(active);
        isActive = true;
        activeTime = 120; //2 seconds at 60 FPS

        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        if (f != null) {
            f.takeDamage(25);
            f.bleedFighter();
            int x = f.getX();
            int y = f.getY();
            getWorld().addObject(new FadeText("BLEEDING!"), x, y);
        }
    }

    public void deactivate(){
        setImage(deactive);
        isActive = false;
    }
}
