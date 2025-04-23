import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class VictoryScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VictoryScreen extends World
{

    /**
     * Constructor for objects of class VictoryScreen.
     * 
     */
    
    private int curAct;
    
    private Frame blackScrn;
    
    public VictoryScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        
        addObject(new Image(1024,768,"endScreen",false),1024/2,768/2);
        
        addObject(new TextLabel("The End.",24,new Color(0,0,0)),1024/2,200);
        
        blackScrn = new Frame(1024, 768, new Color(0,0,0));
        
        addObject(blackScrn,1024/2,768/2);
    }
    
    public void act(){
        curAct++;
        
        if(blackScrn != null){
            fadeAway(blackScrn);
        }
    }
    
    public void fadeAway(ScreenGui object){
        if(object.getImage().getTransparency() > 0){
            GreenfootImage fadeImg = object.getImage();
            fadeImg.setTransparency(object.getImage().getTransparency() - 1);
            object.setImage(fadeImg);
        }else{
            removeObject(object);
        }
    }
}
