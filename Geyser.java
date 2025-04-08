import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class FireColumn here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Geyser extends Traps
{
    GreenfootImage deactive = new GreenfootImage("geyserDeactive.png");
    GreenfootImage active = new GreenfootImage("geyserActive.png");
    //private int activeTime = 0;
    //private boolean isActive = false;
    public Geyser(){
        setImage(deactive);
    }

    public void act()
    {
        super.act();
    }

    public void activate(){
        setImage(active);
        isActive = true;
        activeTime = 60; //1 second at 60 FPS

        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        if (f != null) {
            f.takeDamage(25);
        }
    }

    public void deactivate(){
        setImage(deactive);
        isActive = false;
    }
}
