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
    private ArrayList<Image> arrowButtons;
    private ArrayList<Image> selectionImages;

    private static String fighterName;

    //the order: weapon, armor, fighter class, healing pots
    private static int[] customizationType = {0,0,0,0};

    //amount of options per customizable setting
    // -1 because 0 counts as one index
    private int[] customMax = {3,2,2,3};

    //index 2 and 3 should be armor and hp pots respectfully
    private String[] customizationString = {"Class","Weapon","",""};

    /*
     * ---CONTENTS---
     * Weapons: 0 = Sword, 1 = Spear
     * Classes: 0 = J Thrower, 1 = TwoHanded, 2 = ShieldBearer
     * Armor: 0 = ?, 1 = ?, 2 = ?
     * HP Potions: Self explanatory
     */

    private String[] fNames = {
            "Jordanius","Shamides","Slamocles","Meatios","Flexander","Pookus","Kickolas","Dingleberry", "Eugene","Egoletus","Tinosthenes"
        };
    private String[] titles = {
            " the Flatulent", " ,the Thumb of Olympus"," of Toe Level"," the Small-footed"," the Merciless"," the Brutish", " ,the Honored One",
            " Eugene"
        };

    private TextLabel goButton;

    public CustomizationScreen(boolean simStart)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1024, 768, 1); 

        arrowButtons = new ArrayList<>();
        selectionImages = new ArrayList<>();

        goButton = new TextLabel("Let's Rock!", 50); 
        if(simStart){
            //set starting weapon, fighter class, unique personality traits??

            fighterName = fNames[Greenfoot.getRandomNumber(fNames.length)] + titles[Greenfoot.getRandomNumber(titles.length)];

            //background
            addObject(new Frame(1024,768,new Color(255,0,0)),512,384);

            //image of fighter
            addObject(new Image(250,415,"placeholder",false),512,384);

            //fighter's name
            addObject(new TextLabel(fighterName,50),512,50);

            //start button
            addObject(goButton,512,700);

            int yPos = 200;

            for(int i = 0;i < 4;i++){
                //put on left side of screen
                if(i % 2 == 0 ){
                    /*Image rArrow = new Image(75,50,"selectArrow",false);
                    Image lArrow = new Image(75,50,"selectArrow",true);
                    Image theSelection = new Image(75,50,"anchor",false);

                    addObject(rArrow,512 - 200,yPos);
                    addObject(lArrow,512 - 450,yPos);
                    addObject(theSelection,512 - 325,yPos);

                    arrowButtons.add(lArrow);
                    arrowButtons.add(rArrow);
                    selectionImages.add(theSelection);
                    addObject(new TextLabel(customizationString[i],30),512 - 325,yPos - 70);*/
                    addUIElements(yPos,-1,i);
                    
                }else{//put on right side of screen
                    /*Image rArrow = new Image(75,50,"selectArrow",false);
                    Image lArrow = new Image(75,50,"selectArrow",true);
                    Image theSelection = new Image(75,50,"anchor",false);

                    addObject(lArrow,512 + 200,yPos);
                    addObject(rArrow,512 + 450,yPos);
                    addObject(theSelection,512 + 325,yPos);

                    arrowButtons.add(lArrow);
                    arrowButtons.add(rArrow);
                    selectionImages.add(theSelection);
                    addObject(new TextLabel(customizationString[i],30),512 + 325,yPos - 70);*/
                    //after 2 sets of arrows have been created, move down 300 pixels
                    
                    addUIElements(yPos,1,i);
                    
                    yPos += 300;
                }
            }

        }else{
            //upgrade screen
            addObject(new TextLabel(fighterName,50),512,50);
            addObject(goButton,512,700);
        }

        //stats like health, armor, damage etc can be modified later

        //on fighter death, return to this screen

    }

    public void act(){
        //which set of arrow buttons is the program referring to?
        //possible sets: 0 & 1, 2 & 3, 4 & 5, 6 & 7
        /*
         * 0 - 1: Class
         * 2 - 3: Weapon
         * 4 - 5: Armor
         * 6 - 7: Starting Health Potions
         */
        for(Image i: arrowButtons){
            if(Greenfoot.mouseClicked(i)){
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

                //set new image to display new selction
                GreenfootImage img = new GreenfootImage(customizationString[customIndex] + customizationType[customIndex] + ".png");
                
                //when modified,change below as well
                img.scale(75,75);
                selectionImages.get(customIndex).setImage(img);

                System.out.println(customizationString[customIndex] + " : " + customizationType[customIndex]);
            }
        }

        //confirm, start fight
        if(Greenfoot.mouseClicked(goButton)){
            //pass all of our fighter's information
            Greenfoot.setWorld(new GameWorld(customizationType[0],customizationType[1],customizationType[2],customizationType[3]));
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
    private void addUIElements(int yPos, int screenSide, int curIndex){
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
        Image theSelection = new Image(75,50,customizationString[curIndex] + "0",false);
        addObject(theSelection,512 + 325 * screenSide,yPos);
        selectionImages.add(theSelection);
        
        //text to indicate selection
        addObject(new TextLabel(customizationString[curIndex],30),512 + 325 * screenSide,yPos - 70);
    }
}
