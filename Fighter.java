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
    protected String weaponType;
    
    protected int curAct;
    protected int endAct;
    
    protected Weapon myWeapon;
    
    //states
    protected String myState;
    protected boolean aggro;
    protected boolean timid;
    protected boolean cautious;
    protected boolean usingSpecial;
    protected ArrayList<Integer> stateArray;
    
    protected boolean actOngoing;
    protected boolean iFrames;
    protected int iFramesEndAct;
    
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
    public Fighter(int direction, String weaponType/*,Weapon myWeapon*/){
        this.direction = direction;
        this.weaponType = weaponType;
        
        actOngoing = false;
        //stateArray = new ArrayList<>();
        
        //if value = 1, state is aggro
        //if value = 2, state is timid
        //fighter starts off having a higher chance of being aggro
        /*for(int i = 0;i < 9;i++){
            if(i % 3 == 0){
                stateArray.add(2);
            }else{
                stateArray.add(1);
            }
        }*/
        
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
    
    protected abstract boolean useSpecialAbility();
        
    public void act()
    {
        curAct++;
        
        if(iFrames && curAct >= iFramesEndAct){
            iFrames = false;
        }
        
        //animation segment
        if(curAct % 4 == 0){//switch frames every 4 acts
            curFrame++;
            curFrame %= 5;
            setImage(testAnimationImgs[curFrame]);
        }
        
        myBehaviour();
        fall();
        if(canJump && curAct % (7 + Greenfoot.getRandomNumber(10)) == 0){
            jump();
        }
    }
    
    public void addedToWorld(World w){
        //System.out.println(w + "");
        if (w instanceof GameWorld) {
            //Cast the world to MyWorld and call the method
            world = (GameWorld) w;
            
            floorY = world.getFloorY();
            
            if(weaponType.equalsIgnoreCase("sword")){
                myWeapon = new Sword(this);
            }else if(weaponType.equalsIgnoreCase("spear")){
                myWeapon = new Spear(this);
            }
            
            w.addObject(myWeapon,getX(),getY());
        }
    }

    public String getMyState(){
        if(myState.equals("usingSpecial")){
            return "timid";
        }
        
        return myState;
    }
    
    public int getMyDirection(){
        return direction;
    }
    
    public void takeDamage(int damage){
        if(!iFrames){
            health -= damage;
            iFrames = true;
            iFramesEndAct = curAct + 60;
        }
    }

    public void heal(int healAmnt){
        health += healAmnt;
        if(health > 100){
            health = maxHealth;
        }
    }

    private void changeMyState(){
        
        int missingHP = maxHealth - health;
        
        //as more hp is missing, chance to become timid increases
        int randInt = Greenfoot.getRandomNumber(40) + missingHP;

        //25% chance to be cautious
        if(Greenfoot.getRandomNumber(4) == 0){
            changeMyState("cautious");
        }else if(randInt > 60){
            changeMyState("timid");
        }else if(randInt <= 60){
            changeMyState("aggro");
        }
    }
    
    //specific changeMyState method
    private void changeMyState(String newState){
       //set all states to false
       cautious = false;
       timid = false;
       aggro = false;
       usingSpecial = false;
       
       myState = newState;
       
       if(newState.equals("cautious")){
           cautious = true;
       }else if(newState.equals("timid")){
           endAct = curAct + 10;
           timid = true;
       }else if(newState.equals("aggro")){
           aggro = true;
       }else if(newState.equals("usingSpecial")){
           usingSpecial = true;
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
                
                if(!actOngoing){
                    actOngoing = true;
                    myWeapon.attack();
                }else if(!myWeapon.isDangerous()){
                    actOngoing = false;
                    changeMyState();
                }
                
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
                    
                    changeMyState("usingSpecial");
                }

            }else{
                move(movementSpd/2 * direction);    
            }

        }else if(usingSpecial){
            //check for when ability is done
            
            //useSpecialAbility returns a boolean, true if still ongoing, false if done
            //if false, change my state
            if(!useSpecialAbility()){
                changeMyState("aggro");
            }
        }
        
        //useSpecialAbility();
    }

    private Fighter checkInRange(){

        ArrayList<Fighter> inRange = (ArrayList<Fighter>)getObjectsInRange(myWeapon.getRange(), Fighter.class);

        if(inRange.isEmpty()){
            return null;
        }else{
            return inRange.get(0);
        }
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