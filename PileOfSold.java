import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PileOfSold here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PileOfSold extends Spawnables
{
    GreenfootImage img = new GreenfootImage("soldCoin.png");
    private int timer = 0;
    //private boolean touched = false;
    public PileOfSold(){
        setImage(img);
    }

    public void act()
    {
        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        super.fall();
        if (f != null) {
            GameWorld w  = (GameWorld)getWorld();
            w.removeGold(50);
            getWorld().addObject(new FadeText("-50 Gold", Color.RED), this.getX(), this.getY());
            getWorld().removeObject(this);
        } else {
            timer++;
            if (timer >= 120) { //120 frames = 2 seconds at 60 FPS
                getWorld().removeObject(this);
            }
        }
        
    }
}
