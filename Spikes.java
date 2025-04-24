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
        super("spikeActive","spikeDeactive",180,10);
        attackSFX = new GreenfootSound("spikeSFX.mp3");
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
