import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Potion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Potion extends Actor
{
    /**
     * Act - do whatever the Potion wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private boolean healOpponent;
    
    private int curAct;
    
    private int floorY;
    
    public Potion(boolean healOpponent){
        this.healOpponent = healOpponent;
        
        GreenfootImage img;
        
        if(healOpponent){
            //set img to green potion
            img = new GreenfootImage("greenPot.png");
        }else{
            //set img to red potion
            img = new GreenfootImage("pinkPot.png");
        }
        img.scale(50,50);
        setImage(img);
    }
    
    public void addedToWorld(World w){
        GameWorld gW = (GameWorld) w;
        
        floorY = gW.getFloorY();
    }
    
    public void act()
    {
        curAct++;
        
        //falling
        if(checkAirborne() && curAct % 2 == 0){
            setLocation(getX(),getY() + 15);
        }
        
        healOnContact();
    }
    
    private boolean checkAirborne(){
        if(getY() >= floorY){
            setLocation(getX(),floorY);
            return false;
        }
        
        return true;
    }
    
    private void healOnContact(){
        Fighter f = (Fighter)getOneIntersectingObject(Fighter.class);
        
        if(f != null){
            
            //if i'm supposed to heal the opponent and the fighter is an opponent, heal them
            if(healOpponent){
                if(f.isOpponent()){
                    f.heal(10);
                    getWorld().removeObject(this);
                }
            }else{ //if i'm supposed to heal the main character and the fighter is the main character
                if(!f.isOpponent()){
                    f.heal(10);
                    getWorld().removeObject(this);
                }
            }
        }  
    }
}
