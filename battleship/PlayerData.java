package battleship;

import javax.swing.*;
import java.awt.*;


public class PlayerData extends JPanel{

    public String name;

    public PlayerData(String name) {
        this.name = name;
        Dimension size = getPreferredSize();
        size.height = 90;
        setPreferredSize(size);
        setBackground(Color.white);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(getPlayer() != null) {
            g.drawString("Number of own ships: " + getPlayer().sg.shipNumber, 0, 20);
            g.drawString("Number of own ships sunk: " + getPlayer().sg.ownSunk, 0, 40);
            g.drawString("Number of enemy ships sunk: " + getPlayer().sg.enemySunk, 0, 60);
            g.drawString("Current state: " + Game.getinstance().getState(), 0, 80);
        }

    }

    public PlayerScreen getPlayer() {
        if(this.name.equals("Player1")){
            return Game.getinstance().getPlayer1();
        }
        else{
            return Game.getinstance().getPlayer2();
        }
    }




}
