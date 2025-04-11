import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class StatBar here.
 * 
 * @author (Andrew Li) 
 * @version (a version number or a date)
 */
public class StatBar extends Actor {
    private int HP;
    private int maxHP;
    private GreenfootImage img;
    private Fighter fighter;

    public StatBar(Fighter fighter, int maxHP){
        this.maxHP = maxHP;
        this.fighter = fighter;
        HP = maxHP;
        img = new GreenfootImage(300, 50); // Set size instead of using text constructor
        setImage(img);
        updateBar(); // Set initial HP display
    }

    public void act() {
        updateBar(); // Keep updating if HP changes
    }

    public void updateBar() {
        HP = fighter.getHP(); // Get current HP

        // Clear the old image
        img.clear();

        // Draw new HP text
        img.setColor(Color.WHITE);
        img.setFont(new Font("Arial", true, false, 24));
        img.drawString("HP: " + HP + "/" + maxHP, 10, 35);

        setImage(img);
    }
}

