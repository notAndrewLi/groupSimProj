import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StartWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StartWorld extends World
{

    /**
     * Constructor for objects of class StartWorld.
     * 
     */
    
    private Image startButton;
    private int textSpacing;
    private int topRowY;
    
    public StartWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        
        //create background
        addObject(new Image(1024,768,"bloodRed",false),512,384);
        
        //create text
        textSpacing = 160;
        topRowY = 130;
        addLetter(512 - textSpacing * 2,topRowY,"g");
        addLetter(512 - textSpacing,topRowY,"l");
        addLetter(512,topRowY,"a");
        addLetter(512 + textSpacing,topRowY,"d");
        addLetter(512 + textSpacing * 2,topRowY,"i");
        
        addLetter(512 - textSpacing - 80,topRowY + 150,"t");
        addLetter(512 - textSpacing/2,topRowY + 150,"o");
        addLetter(512 + textSpacing/2,topRowY + 150,"t");
        addLetter(512 + textSpacing + 80,topRowY + 150,"s");
        
        //addObject(new Image(350,350,"gLeft",false),150,600);
        //addObject(new Image(350,350,"gRight",false),1024 - 150,600);
        
        startButton = new Image(300,200,"goButton",false);
        
        addObject(startButton,512,500);
    }
    
    public void act(){
        if(Greenfoot.mouseClicked(startButton)){
            Greenfoot.setWorld(new CustomizationScreen(true,0));
        }
    }
    
    public void addLetter(int xPos, int yPos, String letterType){
        Image theLetter = new Image(300,200,letterType,false);
        
        addObject(theLetter, xPos, yPos);
    }
}
