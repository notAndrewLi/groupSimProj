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
    protected int maxHealth = 100; //add to constructor later, have ability to choose
    protected int health;
    protected int curAct;
    protected int curFrame;
    protected int direction;
    protected int movementSpd;
    protected int endAct;

    protected boolean[] states;
    protected boolean aggro;
    protected boolean timid;
    protected boolean cautious;
    protected GreenfootImage[] testAnimationImgs = new GreenfootImage[5];
    protected String myState;
    /**
     * Fighter constructor
     * only face one direction
     */
    public Fighter(int direction){
        //bagaga
        this.direction = direction;

        aggro = true;
        timid = false;
        cautious = false;

        states = new boolean[]{aggro, timid, cautious};

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
        if(curAct % 4 == 0){//switch frames every 4 acts
            curFrame++;
            curFrame %= 5;
            setImage(testAnimationImgs[curFrame]);
        }
        myBehaviour();
    }

    public String getMyState(){
        return myState;
    }

    public void takeDamage(int damage){
        health -= damage;
    }

    public void heal(int healAmt){
        health += healAmt;
        if(health > 100){
            health = maxHealth;
        }
    }

    private void changeMyState(){

        int randInt = Greenfoot.getRandomNumber(100);

        //25% chance to be cautious
        if(Greenfoot.getRandomNumber(4) == 0){
            cautious = true;
            myState = "cautious";
        }else if(randInt <= 50){
            endAct = curAct + 120;
            timid = true;
            myState = "timid";
        }else if(randInt > 50){
            aggro = true;
            myState = "aggro";
        }
    }

    private void myBehaviour(){
        if(timid){

            //if two seconds have passed
            if(curAct % endAct == 0 && curAct != 0){
                changeMyState();
            }else{
                System.out.println("retreating");
                move(movementSpd * -direction);
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
                for (int i = 0; i < states.length; i++){
                    states[i] = false;
                }

                theirState = f.getMyState();

                //act accordingly
                if(theirState.equals("timid")){
                    aggro = true;
                    myState = "aggro";
                }else if(theirState.equals("aggro")){
                    timid = true;
                    myState = "timid";
                }else if(theirState.equals("cautious")){
                    //some other unique behaviour
                    System.out.println("throw a spear");
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

    private int getNewDirection(){
        if(Greenfoot.getRandomNumber(2) == 0){
            return direction * -1;
        }
        return direction;
    }
}