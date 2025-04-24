import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Geyser extends Traps
{
    //private int activeTime = 0;
    //private boolean isActive = false;
    public Geyser(){
        super("fireActive","fireDeactive",180,15);
        attackSFX = new GreenfootSound("geyserSFX.mp3");
    }

    public void act()
    {
        super.act();
    }
    
    public void applyMyEffect(Fighter f){
        f.scorchFighter();
    }
    
    public String getDamageText(){
        return "SCORCHED!";
    }
}
