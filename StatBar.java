import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StatBar extends Actor {
    private int HP;
    private int maxHP;
    private Fighter fighter;
    private int barWidth = 200;
    private int barHeight = 25;
    private boolean scorched;
    private boolean bleeding;
    private boolean snared;

    public StatBar(Fighter fighter, int maxHP){
        this.fighter = fighter;
        this.maxHP = maxHP;
        this.HP = maxHP;

        // Create the image with enough height for the bar and text
        GreenfootImage img = new GreenfootImage(barWidth + 2, barHeight + 100);
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
        GreenfootImage img = new GreenfootImage(barWidth + 10, barHeight + 100);

        // Draw border
        img.setColor(Color.YELLOW);
        img.drawRect(0, 0, barWidth, barHeight);

        // Draw background
        img.setColor(Color.GRAY);
        img.fillRect(1, 1, barWidth - 1, barHeight - 1);

        img.setColor(Color.RED);

        // Draw filled bar
        int fillWidth = (int)(hpPercent * (barWidth - 2));
        img.fillRect(1, 1, fillWidth, barHeight - 1);

        // Draw HP text below the bar
        img.setColor(Color.WHITE);
        img.setFont(new Font("Arial", false, false, 16));
        img.drawString("HP: " + HP + "/" + maxHP, 10, barHeight + 18);

        int statusYOffset = barHeight + 36;
        if (scorched) {
            img.setColor(Color.ORANGE);
            img.drawString("SCORCHED", 10, statusYOffset);
            statusYOffset += 18;
        }
        if (bleeding) {
            img.setColor(Color.RED);
            img.drawString("BLEEDING", 10, statusYOffset);
            statusYOffset += 18;
        }
        if (snared) {
            img.setColor(Color.CYAN);
            img.drawString("SNARED", 10, statusYOffset);
            statusYOffset += 18;
        }

        setImage(img);

    }
}
