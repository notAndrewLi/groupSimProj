import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class DeathScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DeathScreen extends World
{

    /**
     * Constructor for objects of class DeathScreen.
     * 
     */
    private int curAct;
    
    private TextLabel restartButton;
    
    private Frame blackScrn;
    
    private GreenfootSound BGM = new GreenfootSound("deathAmbience.mp3");
    
    public DeathScreen()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        
        BGM.play();
        
        int middleX = getWidth()/2;
        
        int middleY = getHeight()/2;
        
        //background
        addObject(new Frame(1024, 768, new Color(0,0,0)),middleX,middleY);
        
        addObject(new Image(200,200,"coffin",false),middleX,350);
        
        addObject( new TextLabel("My journey ends here... But maybe another...?",24),middleX,450);
        
        restartButton = new TextLabel("(Continue)",24);
        
        addObject(restartButton,middleX,500);
        
        blackScrn = new Frame(1024, 768, new Color(0,0,0));
        
        addObject(blackScrn,middleX,middleY);
    }
    
    public void started(){
        BGM.play();
    }
    
    public void stopped(){
        BGM.pause();
    }
    
    public void act(){
        curAct++;
        
        if(Greenfoot.mouseClicked(restartButton)){
            BGM.stop();
            
            Greenfoot.setWorld(new CustomizationScreen(true,0));
        }
        
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
