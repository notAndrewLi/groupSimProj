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
    public Spikes(){
        setImage(deactive);
    }
    
    public void act()
    {
        super.act();
    }
    
    public void activate(){
        setImage(active);
        super.activate();
    }
    
    public void deactivate(){
        //deactivate
        setImage(deactive);
        
    }
}
