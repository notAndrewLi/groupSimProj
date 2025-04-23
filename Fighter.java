import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Arrays;
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
    protected int maxHealth; //add to constructor later, have ability to choose
    protected int health;
    protected int direction;
    protected int movementSpd;
    protected int curAct;
    protected int endAct;

    protected int[] customizationType;
    protected Weapon myWeapon;
    protected Armor myArmor;
    
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

    //am i an opponent?
    protected boolean isOpponent;
    //animation stuff
    protected int curFrame;
    protected GreenfootImage[] jumpImgs = new GreenfootImage[5];
    protected GreenfootImage[] dieImgs = new GreenfootImage[6];
    protected GreenfootImage[] emoteImgs = new GreenfootImage[3];

    protected final int gravity = 1;
    protected int yVelocity;
    protected boolean canJump = false;
    protected GameWorld world;
    protected int floorY;
    protected int yOffset;

    //Status effect stuff
    private boolean isScorched = false;
    private int scorchTimer = 0;
    private int scorchDuration = 180; //3 seconds
    private int damageInterval = 60; //deal damage every second
    private int frozenTimer = 0;

    private boolean isBleeding = false;
    private int bleedTimer = 0;
    private int bleedDuration = 300; //5 seconds

    protected boolean isDead;
    protected boolean isFrozen;
    protected boolean isEmoting;
    
    protected String name = "Opponent";

    /**
     * Fighter constructor
     * only face one direction
     */
    public Fighter(int direction, int[] customizationType, int[] upgradeBonuses){
        this.direction = direction;
        this.customizationType = customizationType;

        actOngoing = false;

        changeMyState();

        if(upgradeBonuses != null){
            maxHealth = 100 + upgradeBonuses[0];
            //add one for damage too
            movementSpd = 4 + upgradeBonuses[2]/20;
        }else{
            maxHealth = 100;
            movementSpd = 4;
        }
        health = maxHealth;
        
        if(direction == 1){
            for(int i = 0; i < 5; i++){
                jumpImgs[i] = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                jumpImgs[i] = resize(jumpImgs[i], 2);
            }
        } else{
            for(int i = 0; i < 5; i++){
                GreenfootImage img = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                img.mirrorHorizontally();
                jumpImgs[i] = img;
                jumpImgs[i] = resize(jumpImgs[i], 2);
            } 
        }

        for(int i = 0; i < dieImgs.length; i++){
            dieImgs[i] = new GreenfootImage("images/dieAnimation/hurt" + i + ".png");
            dieImgs[i] = resize(dieImgs[i], 2);
        }
        for(int i = 0; i < emoteImgs.length; i++){
            emoteImgs[i] = new GreenfootImage("images/emoteAnimation/emote"+ i + ".png");
            emoteImgs[i] = resize(emoteImgs[i], 2);
        }
        setImage(jumpImgs[0]);
        
        yOffset = getImage().getHeight()/6;
    }

    protected abstract boolean useSpecialAbility();
    
    //moved ts up, before the act method because it should occur before the act method; the order pmo too much
    public void addedToWorld(World w){
        //System.out.println(w + "");
        if (w instanceof GameWorld) {
            //Cast the world to MyWorld and call the method
            world = (GameWorld) w;

            floorY = world.getFloorY() - yOffset;
            
            int weaponType = customizationType[1];
            int armorType = customizationType[2];
            
            ArrayList<Weapon> weaponTypes = new ArrayList<Weapon>(Arrays.asList(
                new Sword(this),
                new Spear(this),
                new Dagger(this)
            ));
            
            ArrayList<Armor> armorTypes = new ArrayList<Armor>(Arrays.asList(
                new LightArmor(this),
                new MedArmor(this),
                new HeavyArmor(this)
            ));
            
            myWeapon = weaponTypes.get(weaponType);
            w.addObject(myWeapon,getX(),getY());
            
            myArmor = armorTypes.get(armorType);
            w.addObject(myArmor,getX(),getY());
            movementSpd -= myArmor.getWeight();
            
            if(!isOpponent){
                name = world.getMCName();
            }
        }

        
        StatBar myStatBar = new StatBar(this, maxHealth, name);
        w.addObject(myStatBar, (w.getWidth()/2) - (direction * 300), 100);

    }
    
    public void act()
    {
        curAct++;
        frozenTimer--;

        if(isScorched){
            //deals damage every second
            if(scorchTimer % damageInterval == 0){
                takeDamage(5);
            }
            scorchTimer--;
            if(scorchTimer <= 0){
                isScorched = false;
            }
        }

        if(isBleeding){
            //deals damage every second
            if(bleedTimer % damageInterval == 0){
                takeDamage(2);
            }
            bleedTimer--;
            if(bleedTimer <= 0){
                isBleeding = false;
            }
        }

        if(iFrames && curAct >= iFramesEndAct){
            iFrames = false;
        }

        if(frozenTimer <= 0){
            isFrozen = false;
            //animation segment
            if(curAct % 4 == 0){//switch frames every 4 acts
                curFrame++;
                curFrame %= 5;
                setImage(jumpImgs[curFrame]);
            }
            //fighter behaviour
            myBehaviour();
            if(canJump && curAct % (7 + Greenfoot.getRandomNumber(10)) == 0){
                jump();
            }
        } else{
            if(isDead){
                die();
            } else if (isEmoting){
                emote();
            }
        }
        
        fall();
    }

    public void takeDamage(int damage){
        if(!iFrames){
            //if damage is greater than armor resistance
            if(damage - myArmor.getDmgResist() > 0){
                health -= (damage - myArmor.getDmgResist());
            }
            
            iFrames = true;
            iFramesEndAct = curAct + 60;
            int rand = 1 + Greenfoot.getRandomNumber(2);
            //offsets the damage pop up location
            int x = (rand == 2) ? this.getX() + (8 + Greenfoot.getRandomNumber(12)) : this.getX() - (8 + Greenfoot.getRandomNumber(12));
            int y = (rand == 1) ? this.getY() + (8 + Greenfoot.getRandomNumber(12)) : this.getY() - (8 + Greenfoot.getRandomNumber(12));
            getWorld().addObject(new FadeText(damage + "!"), x, y);
        }
        if(health <= 0 && !isDead){//perform death animation
            setCurFrame(0);
            die();
            ArrayList<Fighter> otherFighters = getOtherFighters();
            for(Fighter otherFighter : otherFighters){
                otherFighter.setCurFrame(0);
                otherFighter.emote();
                otherFighter.getWeapon().fallToGround();
                otherFighter.getArmor().fallToGround();
            }

            myWeapon.fallToGround();
            myArmor.fallToGround();
            if(isOpponent){//am i an enemy character?
                TextLabel VictoryPopUp = new TextLabel("Victory! Gold Got: " + world.getGold(), 50, new Color(237, 158, 109));
                world.addObject(VictoryPopUp, world.getWidth()/2, world.getHeight()/2 - 50);
            }
        }
    }

    public ArrayList<Fighter> getOtherFighters(){
        ArrayList<Fighter> fighters = (ArrayList)world.getObjects(Fighter.class);
        ArrayList<Fighter> otherFighters = new ArrayList<Fighter>();
        for (Fighter fighter : fighters){
            if(fighter != this){
                if(fighter.getHP() > 0){
                    otherFighters.add(fighter);
                }
            }
        }
        return otherFighters;
    }

    public void setCurFrame(int frameNum){
        curFrame = frameNum;
    }

    public void scorchFighter(){
        //scorch
        isScorched = true;
        scorchTimer = scorchDuration;
    }

    public void bleedFighter(){
        //bleed
        isBleeding = true;
        bleedTimer = bleedDuration;
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
            endAct = curAct + 30;
            timid = true;
        }else if(newState.equals("aggro")){
            aggro = true;
        }else if(newState.equals("usingSpecial")){
            usingSpecial = true;
        }
    }

    private void myBehaviour(){
        Fighter f = checkInRange();

        if(timid){

            //if two seconds have passed
            if(curAct >= endAct){
                changeMyState();
            }else{
                //retreat quickly
                move(movementSpd * 2 * -direction);
            }

        }else if(aggro){

            if(f != null){

                if(!actOngoing){
                    actOngoing = true;
                    myWeapon.attack();
                }else if(!myWeapon.isDangerous()){
                    //only changes once attack is done
                    actOngoing = false;
                    changeMyState();
                }

            }else if(isTouchingOpponentWall()){//is touching the opponents wall?
                changeMyState("timid");
            }else{//should move towards other fighter
                move(movementSpd * direction);
            }

        }else if(cautious){
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

            }else if(isTouchingOpponentWall()){//is touching the opponents wall?
                changeMyState("timid");
            }else{
                move(2 * direction);    
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

    public void setAsOpponent(){
        isOpponent = true;
    }
    
    public void setMovementSpd(int theSpd){
        movementSpd = theSpd;
    }
    
    public void setDirection(int newDir){
        direction = newDir;

        //reorient weapon
        myWeapon.setMyImage();
        myArmor.setMyImage();
        
        if(direction == 1){
            for(int i = 0; i < 5; i++){
                jumpImgs[i] = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                jumpImgs[i] = resize(jumpImgs[i], 2);
            }
        } else{
            for(int i = 0; i < 5; i++){
                GreenfootImage img = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                img.mirrorHorizontally();
                jumpImgs[i] = img;
                jumpImgs[i] = resize(jumpImgs[i], 2);
            } 
        }
    }

    public boolean isOpponent(){
        return isOpponent;
    }

    public Weapon getWeapon(){
        return myWeapon;
    }
    
    public Armor getArmor(){
        return myArmor;
    }

    public String getMyState(){
        if(myState.equals("usingSpecial")){
            return "cautious";
        }

        return myState;
    }

    public int getMyDirection(){
        return direction;
    }

    public int getYOffset(){
        return yOffset;
    }

    //getter for HP
    public int getHP(){
        return health;
    }

    public void freezeMe(int time){
        isFrozen = true;
        frozenTimer = time;
    }
    
    public boolean isFrozen(){
        return isFrozen; 
    }
    
    public boolean isScorched(){
        return isScorched;
    }
    
    public boolean isBleeding(){
        return isBleeding;
    }

    public void freezeMe(){
        freezeMe(Integer.MAX_VALUE);
    }
    
    public void unfreezeMe(){
        isFrozen = false;
    }
    
    //die method and animation
    public void die(){
        isDead = true;
        freezeMe();
        if(curFrame >= dieImgs.length){
            setImage(dieImgs[dieImgs.length - 1]);
            //add code for next round
        } else if(curAct % 4 == 0){
            setImage(dieImgs[curFrame]);
            curFrame++;
        }
    }
    //other fighter's emote method
    public void emote(){
        isEmoting = true;
        freezeMe();
        if(curAct % 6 == 0){
            setImage(emoteImgs[curFrame % 3]);
            curFrame++;
        }
        if(Greenfoot.getRandomNumber(600) == 0){//lazy way to change to customization screen
            Greenfoot.setWorld(new CustomizationScreen(isOpponent, world.getGold()));
        }
    }

    public GreenfootImage resize(GreenfootImage img, int multiplicationFactor){
        GreenfootImage newImg = img;
        newImg.scale(img.getWidth() * multiplicationFactor, img.getHeight() * multiplicationFactor);
        return(newImg);
    }

    public boolean isTouchingOpponentWall(){
        int opponentWallX = world.getWidth()/2 * (1 + direction);
        if(Math.abs(getX() - opponentWallX) <= 40){
            return true;
        }
        return false;
    }
}