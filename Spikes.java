import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Spikes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Spikes extends Traps
{   
    //private int activeTime = 0;
    //private boolean isActive = false;
    public Spikes(){
        super("spikeTrapActive","spikeTrapDeactive",180,10);
    }

    public void act()
    {
        super.act();
    }
    
    public void applyMyEffect(Fighter f){
        f.bleedFighter();
    }
    
    public String getDamageText(){
        return "BLEEDING!";
    }
}
