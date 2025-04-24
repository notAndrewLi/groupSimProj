import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An abstract class representing a Fighter in the game.
 * This class serves as the base for all fighter characters, providing common functionality
 * for movement, combat, and status effects. Fighters have health, can attack with weapons,
 * wear armor, and are affected by various game mechanics like gravity and status effects.
 * 
 * @author (Andrew Li)
 * @version 1.0
 */
public abstract class Fighter extends SuperSmoothMover
{
    // Fighter stats
    protected int maxHealth;
    protected int health;
    protected int direction;// Direction the fighter is facing (1 for right, -1 for left)
    protected int movementSpd;
    protected int dmgBonus;
    protected int curAct;
    protected int endAct;// Act number when current state should end
    protected static int transTimer;// Timer for scene transitions
    
    protected int[] customizationType; // Array containing customization choices
    protected Weapon myWeapon;
    protected Armor myArmor;
    
    // State variables
    protected String myState;// Current state of the fighter
    protected boolean aggro;
    protected boolean timid;
    protected boolean cautious;
    protected boolean usingSpecial;
    protected ArrayList<Integer> stateArray;
    
    protected boolean actOngoing;
    protected boolean iFrames;
    protected int iFramesEndAct;
    
    // Identity variables
    protected boolean isOpponent;// Whether this fighter is an opponent
    
    // Animation variables
    protected int curFrame;
    protected GreenfootImage[] jumpImgs = new GreenfootImage[5];
    protected GreenfootImage[] dieImgs = new GreenfootImage[6];
    protected GreenfootImage[] emoteImgs = new GreenfootImage[3];
    
    // Physics variables
    protected final int gravity = 1;// Gravity constant
    protected int yVelocity;
    protected boolean canJump = false;
    protected GameWorld world;
    protected int floorY;// Y-coordinate of the floor
    protected int yOffset;// Vertical offset for positioning
    
    // Status effect variables
    private boolean isScorched = false;
    private int scorchTimer = 0;
    private int scorchDuration = 180;
    private int damageInterval = 60;
    private int frozenTimer = 0;        
    
    private boolean isBleeding = false; 
    private int bleedTimer = 0;         
    private int bleedDuration = 300;    
    
    // State flags
    protected boolean isDead;
    protected boolean isFrozen;
    protected boolean isEmoting;
    
    protected String name = "Opponent"; // Fighter's name
    
    // Sound effects
    protected GreenfootSound jumpSFX = new GreenfootSound("jumpSFX.mp3");
    protected GreenfootSound cheerSFX = new GreenfootSound("cheerSFX.mp3");
    protected GreenfootSound loseSFX = new GreenfootSound("loseSFX.mp3");
    
    /**
     * Constructs a Fighter with specified direction, customization, and upgrades.
     * 
     * @param direction The initial facing direction (1 for right, -1 for left)
     * @param customizationType Array containing customization choices
     * @param upgradeBonuses Array containing upgrade bonuses for health, damage, and speed
     */
    public Fighter(int direction, int[] customizationType, int[] upgradeBonuses) {
        this.direction = direction;
        this.customizationType = customizationType;
        actOngoing = false;
        changeMyState();
        
        // Apply upgrade bonuses (in increments of 20)
        if(upgradeBonuses != null) {
            maxHealth = 100 + upgradeBonuses[0];
            dmgBonus = upgradeBonuses[1]/5;
            movementSpd = 4 + upgradeBonuses[2]/20;
        } else {
            maxHealth = 100;
            dmgBonus = 0;
            movementSpd = 4;
        }
        health = maxHealth;
        
        // Load and prepare animation frames based on direction
        if(direction == 1) {
            for(int i = 0; i < 5; i++) {
                jumpImgs[i] = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                jumpImgs[i] = resize(jumpImgs[i], 2);
            }
        } else {
            for(int i = 0; i < 5; i++) {
                GreenfootImage img = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                img.mirrorHorizontally();
                jumpImgs[i] = img;
                jumpImgs[i] = resize(jumpImgs[i], 2);
            } 
        }

        // Load death and emote animations
        for(int i = 0; i < dieImgs.length; i++) {
            dieImgs[i] = new GreenfootImage("images/dieAnimation/hurt" + i + ".png");
            dieImgs[i] = resize(dieImgs[i], 2);
        }
        for(int i = 0; i < emoteImgs.length; i++) {
            emoteImgs[i] = new GreenfootImage("images/emoteAnimation/emote"+ i + ".png");
            emoteImgs[i] = resize(emoteImgs[i], 2);
        }
        setImage(jumpImgs[0]);
        
        yOffset = getImage().getHeight()/6;
    }

    /**
     * Abstract method for using the fighter's special ability.
     * Must be implemented by concrete subclasses.
     * 
     * @return true if the ability is still ongoing, false if completed
     */
    protected abstract boolean useSpecialAbility();
    
    /**
     * Called when the fighter is added to the world.
     * Initializes the fighter's equipment and stats.
     * 
     * @param w The world the fighter is being added to
     */
    public void addedToWorld(World w) {
        if (w instanceof GameWorld) {
            world = (GameWorld) w;
            floorY = world.getFloorY() - yOffset;
            
            int weaponType = customizationType[1];
            int armorType = customizationType[2];
            
            // Initialize weapon and armor based on customization
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
            w.addObject(myWeapon, getX(), getY());
            
            myArmor = armorTypes.get(armorType);
            w.addObject(myArmor, getX(), getY());
            movementSpd -= myArmor.getWeight();
            
            if(!isOpponent) {
                name = world.getMCName();
            }
        }

        // Add stat bar for the fighter
        StatBar myStatBar = new StatBar(this, maxHealth, name);
        w.addObject(myStatBar, (w.getWidth()/2) - (direction * 300), 100);
    }
    
    /**
     * The main act method called by Greenfoot.
     * Handles all fighter behavior, status effects, and animations.
     */
    public void act() {
        curAct++;
        frozenTimer--;

        // Handle scorch status effect
        if(isScorched) {
            if(scorchTimer % damageInterval == 0) {
                takeDamage(5);
            }
            scorchTimer--;
            if(scorchTimer <= 0) {
                isScorched = false;
            }
        }

        // Handle bleed status effect
        if(isBleeding) {
            if(bleedTimer % damageInterval == 0) {
                takeDamage(2);
            }
            bleedTimer--;
            if(bleedTimer <= 0) {
                isBleeding = false;
            }
        }

        // Handle invincibility frames
        if(iFrames && curAct >= iFramesEndAct) {
            iFrames = false;
        }

        // Handle frozen state
        if(frozenTimer <= 0) {
            isFrozen = false;
            // Animation handling
            if(curAct % 4 == 0) {
                curFrame++;
                curFrame %= 5;
                setImage(jumpImgs[curFrame]);
            }
            // Behavior logic
            myBehaviour();
            if(canJump && curAct % (7 + Greenfoot.getRandomNumber(10)) == 0) {
                jump();
            }
        } else {
            if(isDead) {
                die();
            } else if (isEmoting) {
                emote();
            }
        }
        
        fall();
    }

    /**
     * Causes the fighter to take damage, applying armor resistance.
     * Triggers invincibility frames and displays damage numbers.
     * 
     * @param damage The amount of damage to take before armor resistance
     */
    public void takeDamage(int damage) {
        if(!iFrames) {
            // Apply damage with armor resistance
            if(damage - myArmor.getDmgResist() > 0) {
                health -= (damage - myArmor.getDmgResist());
            }
            
            iFrames = true;
            iFramesEndAct = curAct + 60;
            // Display damage number
            int rand = 1 + Greenfoot.getRandomNumber(2);
            int x = (rand == 2) ? this.getX() + (8 + Greenfoot.getRandomNumber(12)) : this.getX() - (8 + Greenfoot.getRandomNumber(12));
            int y = (rand == 1) ? this.getY() + (8 + Greenfoot.getRandomNumber(12)) : this.getY() - (8 + Greenfoot.getRandomNumber(12));
            getWorld().addObject(new FadeText(damage + "!"), x, y);
        }
        
        // Handle death
        if(health <= 0 && !isDead) {
            // Collect all gold in the world
            ArrayList<PileOfGold> golds = (ArrayList<PileOfGold>) world.getObjects(PileOfGold.class);
            for(PileOfGold gold : golds) {
                world.addGold(100);
                world.removeObject(gold);
            }
            
            setCurFrame(0);
            die();
            ArrayList<Fighter> otherFighters = getOtherFighters();
            transTimer = curAct + 300;

            // Handle other fighters' reactions
            for(Fighter otherFighter : otherFighters) {
                otherFighter.setCurFrame(0);
                otherFighter.emote();
                otherFighter.getWeapon().fallToGround();
                otherFighter.getArmor().fallToGround();
            }

            myWeapon.fallToGround();
            myArmor.fallToGround();
            
            // Display victory/defeat message
            if(isOpponent) {
                TextLabel VictoryPopUp = new TextLabel("Victory! Gold Got: " + world.getGold(), 50, new Color(237, 158, 109));
                world.addObject(VictoryPopUp, world.getWidth()/2, world.getHeight()/2 - 50);
                cheerSFX.play();
            } else {
                loseSFX.play();
            }
        }
    }

    /**
     * Gets a list of all other fighters in the world.
     * 
     * @return ArrayList of other Fighter objects
     */
    public ArrayList<Fighter> getOtherFighters() {
        ArrayList<Fighter> fighters = (ArrayList)world.getObjects(Fighter.class);
        ArrayList<Fighter> otherFighters = new ArrayList<Fighter>();
        for (Fighter fighter : fighters) {
            if(fighter != this && fighter.getHP() > 0) {
                otherFighters.add(fighter);
            }
        }
        return otherFighters;
    }

    /**
     * Sets the current animation frame.
     * 
     * @param frameNum The frame number to set
     */
    public void setCurFrame(int frameNum) {
        curFrame = frameNum;
    }

    /**
     * Applies the scorch status effect to the fighter.
     */
    public void scorchFighter() {
        isScorched = true;
        scorchTimer = scorchDuration;
    }

    /**
     * Applies the bleed status effect to the fighter.
     */
    public void bleedFighter() {
        isBleeding = true;
        bleedTimer = bleedDuration;
    }

    /**
     * Heals the fighter by a specified amount, capping at max health.
     * 
     * @param healAmnt The amount to heal
     */
    public void heal(int healAmnt) {
        health += healAmnt;
        if(health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Randomly changes the fighter's state based on current health.
     */
    private void changeMyState() {
        int missingHP = maxHealth - health;
        int randInt = Greenfoot.getRandomNumber(40) + missingHP;

        if(Greenfoot.getRandomNumber(4) == 0) {
            changeMyState("cautious");
        } else if(randInt > 60) {
            changeMyState("timid");
        } else if(randInt <= 60) {
            changeMyState("aggro");
        }
    }

    /**
     * Changes the fighter's state to a specific state.
     * 
     * @param newState The new state to set ("cautious", "timid", "aggro", or "usingSpecial")
     */
    private void changeMyState(String newState) {
        // Reset all states
        cautious = false;
        timid = false;
        aggro = false;
        usingSpecial = false;

        myState = newState;

        // Set appropriate state flag
        if(newState.equals("cautious")) {
            cautious = true;
        } else if(newState.equals("timid")) {
            endAct = curAct + 30;
            timid = true;
        } else if(newState.equals("aggro")) {
            aggro = true;
        } else if(newState.equals("usingSpecial")) {
            usingSpecial = true;
        }
    }

    /**
     * Determines and executes the fighter's behavior based on current state.
     */
    private void myBehaviour() {
        Fighter f = checkInRange();

        if(timid) {
            if(curAct >= endAct) {
                changeMyState();
            } else {
                move(movementSpd * 2 * -direction);
            }
        } else if(aggro) {
            if(f != null) {
                if(!actOngoing) {
                    actOngoing = true;
                    myWeapon.attack();
                } else if(!myWeapon.isDangerous()) {
                    actOngoing = false;
                    changeMyState();
                }
            } else if(isTouchingOpponentWall()) {
                changeMyState("timid");
            } else {
                move(movementSpd * direction);
            }
        } else if(cautious) {
            if(f != null) {
                String theirState = f.getMyState();
                if(theirState.equals("timid")) {
                    changeMyState("aggro");
                } else if(theirState.equals("aggro")) {
                    changeMyState("timid");
                } else if(theirState.equals("cautious")) {
                    changeMyState("usingSpecial");
                }
            } else if(isTouchingOpponentWall()) {
                changeMyState("timid");
            } else {
                move(2 * direction);    
            }
        } else if(usingSpecial) {
            if(!useSpecialAbility()) {
                changeMyState("aggro");
            }
        }
    }

    /**
     * Checks if any other fighters are within attack range.
     * 
     * @return The first fighter in range, or null if none
     */
    private Fighter checkInRange() {
        ArrayList<Fighter> inRange = (ArrayList<Fighter>)getObjectsInRange(myWeapon.getRange(), Fighter.class);
        return inRange.isEmpty() ? null : inRange.get(0);
    }

    /**
     * Makes the fighter jump by applying upward velocity.
     */
    private void jump() {
        jumpSFX.play();
        yVelocity = 10;
        canJump = false;
    }

    /**
     * Handles the fighter's falling due to gravity.
     */
    private void fall() {
        setLocation(getX(), getY() - yVelocity);
        yVelocity -= gravity;
        if(getY() > floorY || Math.abs(floorY - getY()) <= 5) {
            yVelocity = 0;
            canJump = true;
            setLocation(getX(), floorY);
        }
    }

    /**
     * Marks this fighter as an opponent.
     */
    public void setAsOpponent() {
        isOpponent = true;
    }
    
    /**
     * Sets the fighter's movement speed.
     * 
     * @param theSpd The new movement speed
     */
    public void setMovementSpd(int theSpd) {
        movementSpd = theSpd;
    }
    
    /**
     * Sets the fighter's facing direction and updates animations accordingly.
     * 
     * @param newDir The new direction (1 for right, -1 for left)
     */
    public void setDirection(int newDir) {
        direction = newDir;
        myWeapon.setMyImage();
        myArmor.setMyImage();
        
        if(direction == 1) {
            for(int i = 0; i < 5; i++) {
                jumpImgs[i] = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                jumpImgs[i] = resize(jumpImgs[i], 2);
            }
        } else {
            for(int i = 0; i < 5; i++) {
                GreenfootImage img = new GreenfootImage("images/testJumpAnimation/testJump" + i + ".png");
                img.mirrorHorizontally();
                jumpImgs[i] = img;
                jumpImgs[i] = resize(jumpImgs[i], 2);
            } 
        }
    }

    /**
     * Checks if this fighter is an opponent.
     * 
     * @return true if this fighter is an opponent, false otherwise
     */
    public boolean isOpponent() {
        return isOpponent;
    }

    /**
     * Gets the fighter's equipped weapon.
     * 
     * @return The equipped Weapon object
     */
    public Weapon getWeapon() {
        return myWeapon;
    }
    
    /**
     * Gets the fighter's equipped armor.
     * 
     * @return The equipped Armor object
     */
    public Armor getArmor() {
        return myArmor;
    }

    /**
     * Gets the fighter's current state.
     * Returns "cautious" if state is "usingSpecial" to hide special ability usage.
     * 
     * @return The current state as a String
     */
    public String getMyState() {
        return myState.equals("usingSpecial") ? "cautious" : myState;
    }

    /**
     * Gets the fighter's facing direction.
     * 
     * @return 1 for right, -1 for left
     */
    public int getMyDirection() {
        return direction;
    }

    /**
     * Gets the vertical offset used for positioning.
     * 
     * @return The y-offset value
     */
    public int getYOffset() {
        return yOffset;
    }
    
    /**
     * Gets the fighter's damage bonus.
     * 
     * @return The damage bonus value
     */
    public int getDmgBonus() {
        return dmgBonus;
    }
    
    /**
     * Gets the fighter's current health.
     * 
     * @return The current health value
     */
    public int getHP() {
        return health;
    }

    /**
     * Freezes the fighter for a specified duration.
     * 
     * @param time The duration to freeze (in acts)
     */
    public void freezeMe(int time) {
        isFrozen = true;
        frozenTimer = time;
    }
    
    /**
     * Checks if the fighter is currently frozen.
     * 
     * @return true if frozen, false otherwise
     */
    public boolean isFrozen() {
        return isFrozen; 
    }
    
    /**
     * Checks if the fighter is currently scorched.
     * 
     * @return true if scorched, false otherwise
     */
    public boolean isScorched() {
        return isScorched;
    }
    
    /**
     * Checks if the fighter is currently bleeding.
     * 
     * @return true if bleeding, false otherwise
     */
    public boolean isBleeding() {
        return isBleeding;
    }

    /**
     * Freezes the fighter indefinitely.
     */
    public void freezeMe() {
        freezeMe(Integer.MAX_VALUE);
    }
    
    /**
     * Unfreezes the fighter.
     */
    public void unfreezeMe() {
        isFrozen = false;
    }
    
    /**
     * Plays the death animation and handles death state.
     */
    public void die() {
        isDead = true;
        freezeMe();
        if(curFrame >= dieImgs.length) {
            setImage(dieImgs[dieImgs.length - 1]);
        } else if(curAct % 4 == 0) {
            setImage(dieImgs[curFrame]);
            curFrame++;
        }
    }
    
    /**
     * Plays the emote animation and handles transition to next scene.
     */
    public void emote() {
        heal(maxHealth);
        isEmoting = true;
        freezeMe();
        if(curAct % 6 == 0) {
            setImage(emoteImgs[curFrame % 3]);
            curFrame++;
        }
        if(curAct >= transTimer) {
            world.stopMusic();
            Greenfoot.setWorld(new CustomizationScreen(isOpponent, world.getGold()));
        }
    }
    
    /**
     * Resizes a GreenfootImage by a multiplication factor.
     * 
     * @param img The image to resize
     * @param multiplicationFactor The scaling factor
     * @return The resized image
     */
    public GreenfootImage resize(GreenfootImage img, int multiplicationFactor) {
        GreenfootImage newImg = img;
        newImg.scale(img.getWidth() * multiplicationFactor, img.getHeight() * multiplicationFactor);
        return newImg;
    }

    /**
     * Checks if the fighter is touching the opponent's wall.
     * 
     * @return true if touching opponent's wall, false otherwise
     */
    public boolean isTouchingOpponentWall() {
        int opponentWallX = world.getWidth()/2 * (1 + direction);
        return Math.abs(getX() - opponentWallX) <= 40;
    }
    
    /**
     * Removes invincibility frames from the fighter.
     */
    public void removeIframes() {
        iFrames = false;
    }
}