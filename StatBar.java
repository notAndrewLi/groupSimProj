import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Write a description of class Armor here.
 * 
 * @author (Andrew Li, Dinneth De Silva) 
 * @version (a version number or a date)
 */
public class StatBar extends Actor {
    private int HP;
    private int maxHP;
    private Fighter fighter;

    
    private boolean scorched;
    private boolean bleeding;
    private boolean snared;

    private int barWidth = 270;
    private int barHeight = 70;
    private String name;


    public StatBar(Fighter fighter, int maxHP, String name){
        this.fighter = fighter;
        this.maxHP = maxHP;
        this.name = name;
        this.HP = maxHP;
        // Create the image with enough height for the bar and text

        GreenfootImage img = new GreenfootImage(barWidth, barHeight);

        setImage(img);
        updateBar(); // draw the initial bar
    }

    public void act() { 
        updateBar(); // refresh bar every frame
    }

    public void updateBar() {
        HP = fighter.getHP(); // Get current HP
        scorched = fighter.isScorched();
        bleeding = fighter.isBleeding();
        snared = fighter.isFrozen();
        if(HP < 0){
            HP = 0;
        }
        double hpPercent = (double) HP / maxHP;

        // Create a new image each time

        GreenfootImage img = new GreenfootImage(barWidth, barHeight + 54);

        // Draw border
        img.setColor(Color.YELLOW);
        img.drawRect(0, 20, barWidth, 25);

        // Draw background
        img.setColor(Color.GRAY);
        img.fillRect(1, 21, barWidth - 1, 24);

        img.setColor(Color.RED);

        // Draw filled bar
        int fillWidth = (int)(hpPercent * (barWidth - 2));
        img.fillRect(1, 21, fillWidth, 24);

        // Draw HP text below the bar
        img.setColor(Color.WHITE);
        img.setFont(new Font("Arial", true, false, 16));
        img.drawString("HP: " + HP + "/" + maxHP, 0, barHeight);

        img.setColor(new Color(227, 129, 68));
        img.setFont(new Font("Arial", true, false, 16));
        img.drawString(name, 0, 17);

        int statusYOffset = barHeight + 18;
        if (scorched) {
            img.setColor(Color.ORANGE);
            img.drawString("SCORCHED", 0, statusYOffset);
            statusYOffset += 18;
        }
        if (bleeding) {
            img.setColor(Color.RED);
            img.drawString("BLEEDING", 0, statusYOffset);
            statusYOffset += 18;
        }
        if (snared) {
            img.setColor(Color.CYAN);
            img.drawString("SNARED", 0, statusYOffset);
            statusYOffset += 18;
        }

        setImage(img);

    }
}
