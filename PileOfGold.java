import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class PileOfGold here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PileOfGold extends Spawnables
{
    GreenfootImage img = new GreenfootImage("gold.png");
    private int timer = 0;
    public PileOfGold(){
        setImage(img);
    }

    public void act()
    {
        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        if (f != null) {
            GameWorld w  = (GameWorld)getWorld();
            w.addGold(100);
            getWorld().addObject(new FadeText("+100 Gold", Color.YELLOW), this.getX(), this.getY());
            getWorld().removeObject(this);
        } else {
            timer++;
            if (timer >= 240) { //240 frames = 4 seconds at 60 FPS
                getWorld().removeObject(this);
            }
        }        
    }
}
