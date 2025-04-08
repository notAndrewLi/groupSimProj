import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
/**
 * Write a description of class Fighter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Fighter extends SuperSmoothMover
{
    /**
     * Act - do whatever the Fighter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    //fighter stats
    protected int maxHealth = 100; //add to constructor later, have ability to choose
    protected int health;
    protected int direction;
    protected int movementSpd;
    
    protected int curAct;
    protected int endAct;
    
    //states
    protected String myState;
    protected boolean aggro;
    protected boolean timid;
    protected boolean cautious;
    
    //animation stuff
    protected int curFrame;
    protected GreenfootImage[] testAnimationImgs = new GreenfootImage[5];
    
    protected final int gravity = 1;
    protected int yVelocity;
    protected boolean canJump = false;
    protected GameWorld world;
    protected int floorY;

    /**
     * Fighter constructor
     * only face one direction
     */
    public Fighter(int direction){
        this.direction = direction;

        aggro = true;
        timid = false;
        cautious = false;

        changeMyState();

        health = 100;
        movementSpd = 4;
        
        if(direction == 1){
            for(int i = 0; i < 5; i++){
                testAnimationImgs[i] = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
            }
        } else{
           for(int i = 0; i < 5; i++){
                GreenfootImage img = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                img.mirrorHorizontally();
                testAnimationImgs[i] = img;
            } 
        }
        setImage(testAnimationImgs[0]);
    }

    public void act()
    {
        curAct++;
        
        //animation segment
        if(curAct % 4 == 0){//switch frames every 4 acts
            curFrame++;
            curFrame %= 5;
            setImage(testAnimationImgs[curFrame]);
        }
        
        myBehaviour();
        fall();
        if(canJump){
            jump();
        }
    }
    
    public void addedToWorld(World w){
        System.out.println(w + "");
        if (w instanceof GameWorld) {
            //Cast the world to MyWorld and call the method
            world = (GameWorld) w;
            floorY = world.getFloorY();
        }
    }

    public String getMyState(){
        return myState;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void heal(int healAmnt){
        health += healAmnt;
        if(health > 100){
            health = maxHealth;
        }
    }

    private void changeMyState(){

        int randInt = Greenfoot.getRandomNumber(100);

        //25% chance to be cautious
        if(Greenfoot.getRandomNumber(4) == 0){
            changeMyState("cautious");
        }else if(randInt <= 50){
            changeMyState("timid");
        }else if(randInt > 50){
            changeMyState("aggro");
        }
    }
    
    //specific changeMyState method
    private void changeMyState(String newState){
       //set all states to false
       cautious = false;
       timid = false;
       aggro = false;
        
       if(newState.equals("cautious")){
           myState = "cautious";
           cautious = true;
       }else if(newState.equals("timid")){
           endAct = curAct + 10;
           myState = "timid";
           timid = true;
       }else if(newState.equals("aggro")){
           myState = "aggro";
           aggro = true;
       }
    }
    
    private void myBehaviour(){
        if(timid){

            //if two seconds have passed
            if(curAct >= endAct){
                changeMyState();
            }else{
                //retreat quickly
                move(movementSpd * 2 * -direction);
            }

        }else if(aggro){

            if(checkInRange() != null){
                attack();
                changeMyState();
            }else{
                move(movementSpd * direction);
            }

        }else if(cautious){
            Fighter f = checkInRange();

            String theirState;

            if(f != null){
                theirState = f.getMyState();

                //act accordingly
                if(theirState.equals("timid")){
                    changeMyState("aggro");
                }else if(theirState.equals("aggro")){
                    changeMyState("timid");
                }else if(theirState.equals("cautious")){
                    //some other unique behaviour
                    //aggro is a placeholder
                    changeMyState("aggro");
                }

            }else{
                move(movementSpd/2 * direction);    
            }

        }
    }

    private Fighter checkInRange(){

        ArrayList<Fighter> inRange = (ArrayList<Fighter>)getObjectsInRange(100, Fighter.class);

        if(inRange.isEmpty()){
            return null;
        }else{
            return inRange.get(0);
        }
    }

    private void attack(){
        //attack
    }
    
    private void jump(){//give the fighter a velocity upwards
        yVelocity = 10;
        canJump = false;
    }
    
    private void fall(){
        setLocation(getX(), getY() - yVelocity);
        yVelocity -= gravity;
        if(getY() > floorY || Math.abs(floorY - getY()) <= 5){
            yVelocity = 0;
            canJump = true;
            setLocation(getX(), floorY);
        }
    }
}