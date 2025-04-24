import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.ArrayList;

/**
 * Write a description of class CustomizationScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CustomizationScreen extends World
{

    /**
     * Constructor for objects of class CustomizationScreen.
     * 
     */

    private static int fights;
    private int fightsToWin = 4;
    
    private ArrayList<Image> arrowButtons;
    private ArrayList<Image> selectionImages;
    private ArrayList<Image> upgradeButtons;
    private ArrayList<TextLabel> upgradeCostLabels;

    private int selectionImgSize = 90;

    private static String fighterName;

    private static int[] customizationType;

    //amount of options per customizable setting
    // -1 because 0 counts as one index
    private int[] customMax = {3,3,3,3};

    //index 2 and 3 should be armor and hp pots respectfully
    private String[] customizationString = {"Class","Weapon","Armor","Potions"};

    /*
     * ---CONTENTS---
     * Classes: 0 = J Thrower, 1 = TwoHanded, 2 = ShieldBearer
     * Weapons: 0 = Sword, 1 = Spear
     * Armor: 0 = Light, 1 = Med, 2 = Heavy
     * HP Potions: Self explanatory
     */

    private String[] fNames = {
            "Jordanius","Shamides","Slamocles","Meatios","Flexander","Pookus","Kickolas","Dingleberry", "Eugene","Egoletus","Tinosthenes"
        };
    private String[] titles = {
            " the Flatulent", " ,the Thumb of Olympus"," of Toe Level"," the Small-footed"," the Merciless"," the Brutish", " ,the Honored One",
            " Eugene"
        };

    private int myGold;
    private TextLabel myGoldDisplay;

    String[] statArray = {"Upgrade Health", "Upgrade Damage", "Upgrade Speed"};
    private static int[] upgradeBonuses;
    private static int[] upgradeCostVals = new int[3];

    //modify according to number of upgrade buttons
    //all buttons are set to true once the switch to Game World is made
    private static boolean[] canBuy = {true,true,true};

    private TextLabel goButton;
    
    private GreenfootSound BGM = new GreenfootSound("customizationBGM.mp3");

    public CustomizationScreen(boolean simStart, int theGold)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 
        BGM.playLoop();
        //background
        addObject(new Image(1024,768,"bloodRed",false),512,384);

        //image of fighter
        //addObject(new Image(250,415,"placeholder",false),512,384);

        //start button

        //we need a "go" button no matter what
        goButton = new TextLabel("Let's Rock!", 50); 
        addObject(goButton,512,700);
        
        addObject(new Image(275,415,"theFighter",false),512,384);
        
        myGold = theGold;

        if(simStart){
            fights = 0;

            arrowButtons = new ArrayList<>();
            selectionImages = new ArrayList<>();

            //set all values to default on world start
            customizationType = new int[4];
            upgradeBonuses = new int[3];
            for(int i = 0; i < upgradeCostVals.length;i++){
                upgradeCostVals[i] = 200;
            }
            
            //image for fighter
            addObject(new Image(275,415,"theFighter",false),512,384);

            fighterName = fNames[Greenfoot.getRandomNumber(fNames.length)] + titles[Greenfoot.getRandomNumber(titles.length)];
            //fighter's name
            addObject(new TextLabel(fighterName,50),512,50);

            int yPos = 200;

            for(int i = 0;i < 4;i++){
                //put on left side of screen
                if(i % 2 == 0 ){
                    addCustomizationUI(yPos,-1,i);

                }else{//put on right side of screen
                    //after 2 sets of arrows have been created, move down 300 pixels

                    addCustomizationUI(yPos,1,i);

                    yPos += 300;
                }
            }

        }else{
            //upgrade screen

            //display myGold
            myGoldDisplay = new TextLabel("Gold to his name: " + myGold,25,new Color(255,255,255));
            addObject(myGoldDisplay,512,90);

            upgradeButtons = new ArrayList<>();

            upgradeCostLabels = new ArrayList<>();

            addObject(new TextLabel(fighterName,50),512,50);
            
            //fight counter
            int remainingFights = fightsToWin - fights;
            addObject(new TextLabel("Fights until Dominance:" + remainingFights, 24), 512, 650);
            
            //progress bar:
            //addObject(new Image(40,66,"placeholder",false),512,700 - (100 * fights));

            //addObject(new Image(500,500,"progressLine(1)",false),570,768/2);

            int yPos = 200;

            for(int i = 0;i < 3;i++){
                if(i % 2 == 0){
                    addUpgradeUI(yPos,1,i);
                }else{
                    addUpgradeUI(yPos,-1,i);
                    yPos += 300;
                }
            }

        }
    }
    
    public void started(){
        BGM.play();
    }
    
    public void stopped(){
        BGM.pause();
    }

    public void act(){
        //if we land on customization screen after the last fight, change to victory screen
        if(fights == fightsToWin){
            BGM.stop();
            Greenfoot.setWorld(new VictoryScreen());
        }
        
        //confirm, start fight
        if(Greenfoot.mouseClicked(goButton)){
            new GreenfootSound("buttonClickSFX.mp3").play(); // new instance plays immediately
            fights += 1;

            //set all upgrade buttons clickable again
            for(int i  = 0; i < canBuy.length;i++){
                canBuy[i] = true;
            }

            //pass all of our fighter's information
            Greenfoot.setWorld(new GameWorld(customizationType,upgradeBonuses, fighterName, fights));
            BGM.stop();
        }

        //which set of arrow buttons is the program referring to?
        //possible sets: 0 & 1, 2 & 3, 4 & 5, 6 & 7
        /*
         * 0 - 1: Class
         * 2 - 3: Weapon
         * 4 - 5: Armor
         * 6 - 7: Starting Health Potions
         */

        //for starting customization
        if(arrowButtons != null){
            for(Image i: arrowButtons){
                if(Greenfoot.mouseClicked(i)){
                    new GreenfootSound("buttonClickSFX.mp3").play(); // new instance plays immediately
                    //determine what i am customizing by finding the index of the specific button in the array, and determining which set it belongs to(see above)
                    int buttonIndex = arrowButtons.indexOf(i);
                    int customIndex = returnCustomizationType(buttonIndex);
                    int selector = 1;
                    int theInt;

                    //all the buttons on the left side of the "set" of arrow buttons has an even index in the arraylist 
                    if(arrowButtons.indexOf(i) % 2 == 0){
                        selector *= -1;
                    }

                    customizationType[customIndex] += selector;
                    theInt = customizationType[customIndex];
                    //make sure our current index is not greater than the amount of options we have
                    if(theInt > customMax[customIndex] - 1){
                        customizationType[customIndex] = 0; 
                    }else if(theInt < 0){
                        customizationType[customIndex] = customMax[customIndex] - 1; 
                    }

                    //set new image to display new selection
                    GreenfootImage img = new GreenfootImage(customizationString[customIndex] + customizationType[customIndex] + ".png");

                    //when modified,change below as well
                    img.scale(selectionImgSize,selectionImgSize);
                    selectionImages.get(customIndex).setImage(img);

                    //System.out.println(customizationString[customIndex] + " : " + customizationType[customIndex]);
                }
            }
        }

        //for upgrading character
        if(upgradeButtons != null){
            for(Image i: upgradeButtons){
                int theIndex = upgradeButtons.indexOf(i);
                if(Greenfoot.mouseClicked(i) && canBuy[theIndex] && myGold - upgradeCostVals[theIndex] >= 0){
                    new GreenfootSound("buttonClickSFX.mp3").play(); // new instance plays immediately
                    canBuy[theIndex] = false;
                    //subtract gold
                    //match up the index of the button in the arraylist with the array containing the cost of the upgrade
                    myGold -= upgradeCostVals[theIndex];

                    //modify the gold display
                    GreenfootImage newVal = new GreenfootImage("Gold to his name: " + myGold, myGoldDisplay.getFontSize(), myGoldDisplay.getColor(), null);
                    myGoldDisplay.setImage(newVal);

                    //apply the correct upgrade
                    //upgrades applied in increments of 20, modify accordingly
                    upgradeBonuses[theIndex] += 20;

                    //make the upgrade more pricey for next time
                    upgradeCostVals[theIndex] += 200;

                    //remove price tag
                    removeObject(upgradeCostLabels.get(theIndex));

                    GreenfootImage sold = new GreenfootImage("sold.png");
                    sold.scale(150,150);
                    i.setImage(sold);
                }
            }   
        }
    }

    //returns the index of the "thing" that is being customized
    private int returnCustomizationType (int buttonIndex){
        if(buttonIndex <= 1){
            return 0;
        }else if(buttonIndex <= 3){
            return 1;
        }else if(buttonIndex <= 5){
            return 2;
        }else if(buttonIndex <= 7){
            return 3;
        }else{
            //SHOULD NEVER RETURN 4!
            return 4;
        }
    }

    //only for initial customization screen
    //screenside should be set to positive for the right side, negative for the left side
    private void addCustomizationUI(int yPos, int screenSide, int curIndex){
        boolean mirrorImage = false;
        if(screenSide > 0){
            mirrorImage = true;
        }

        int xOffset = 200;

        for (int i = 0; i < 2; i++){
            Image arrow = new Image(75,75,"selectArrow",mirrorImage);
            addObject(arrow,512 + xOffset * screenSide,yPos);
            arrowButtons.add(arrow);
            mirrorImage = !mirrorImage;
            xOffset = 450;
        }

        //icon to indicate selection
        //set it to the default upon starting
        Image theSelection = new Image(selectionImgSize,selectionImgSize,customizationString[curIndex] + "0",false);
        addObject(theSelection,512 + 325 * screenSide,yPos);
        selectionImages.add(theSelection);

        //text to indicate selection
        addObject(new TextLabel(customizationString[curIndex],30),512 + 325 * screenSide,yPos - 70);
    }

    //on subsequent victories
    private void addUpgradeUI(int yPos, int screenSide, int curIndex){
        //button to press if upgrade is to be purchased
        Image uButton = new Image(150,150,"upgradeButton",false);
        addObject(uButton,512 + 325 * screenSide, yPos + 100);
        upgradeButtons.add(uButton);

        //type of upgrade
        addObject(new TextLabel(statArray[curIndex],30),512 + 325 * screenSide,yPos);

        //cost of upgrade
        TextLabel costLabel = new TextLabel(upgradeCostVals[curIndex] + "",30,new Color(0,0,0));
        addObject(costLabel,512 + 325 * screenSide,yPos + 115);
        upgradeCostLabels.add(costLabel);
    }
}
