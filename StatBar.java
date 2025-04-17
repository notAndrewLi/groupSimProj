import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class StatBar extends Actor {
    private int HP;
    private int maxHP;
    private Fighter fighter;
    private int barWidth = 200;
    private int barHeight = 25;

    public StatBar(Fighter fighter, int maxHP){
        this.fighter = fighter;
        this.maxHP = maxHP;
        this.HP = maxHP;

        // Create the image with enough height for the bar and text
        GreenfootImage img = new GreenfootImage(barWidth + 2, barHeight + 25);
        setImage(img);
        updateBar(); // draw the initial bar
    }

    public void act() {
        updateBar(); // refresh bar every frame
    }

    public void updateBar() {
        HP = fighter.getHP(); // Get current HP
        if(HP < 0){
            HP = 0;
        }
        double hpPercent = (double) HP / maxHP;

        // Create a new image each time
        GreenfootImage img = new GreenfootImage(barWidth + 10, barHeight + 25);

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

        setImage(img);
    }
}
