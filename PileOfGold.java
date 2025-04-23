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
    public PileOfGold(){
        setImage(img);
    }

    public void act()
    {
        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        super.fall();
        if (f != null) {
            GameWorld w  = (GameWorld)getWorld();
            w.addGold(100);
            getWorld().addObject(new FadeText("+100 Gold", Color.YELLOW), this.getX(), this.getY());
            getWorld().removeObject(this);
        }
        
    }     
}
