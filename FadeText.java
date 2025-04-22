import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class FadeText extends Actor
{
    private int timer = 50; 
    private GreenfootImage image;
    public FadeText(String text) {
        //default constructor
        image = new GreenfootImage(text, 18, Color.RED, new Color(0, 0, 0, 0));
        setImage(image);
    }
    
    public FadeText(String text, Color textColor){
        //color customizable version
        image = new GreenfootImage(text, 18, textColor, new Color(0, 0, 0, 0));
        setImage(image);
    }
    
    public void act()
    {
        //timer
        if (timer > 0) {
            timer--;
            //changes transparency over time
            int transparency = (int) (255 * ((double) timer / 100)); 
            image.setTransparency(transparency);
            setImage(image);
        } else {
            getWorld().removeObject(this);
        }
    }
}
