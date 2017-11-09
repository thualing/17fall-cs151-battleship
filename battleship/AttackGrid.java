package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
Represents the player's own grid
*/
public class AttackGrid extends BattleGrid{

    public int[][] enemyShip = new int[10][10]; // 0 if not attacked, 1 if missed, 2 if hit
    public String name;
    public boolean attacked;
    public AttackGrid(String name) {
        super();
        this.name = name;
    }

    @Override
    protected JPanel getCell(int x, int y)
    {
        JPanel panel = panels[x][y];
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.red, 5));
        panel.setPreferredSize(new Dimension(20, 20)); // for demo purposes only
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (enemyShip[x][y] == 0 && !attacked &&
                        (Game.getinstance().getState() == GameStates.PLAYER1_ATTACK ||
                                Game.getinstance().getState() == GameStates.PLAYER2_ATTACK)) {
                    Game.getinstance().attack(x, y);
                    attacked = true;
                }
            }
        });

        return panel;
    }


    public PlayerScreen getPlayer() {
        if(this.name.equals("Player1")){
            return Game.getinstance().getPlayer1();
        }
        else{
            return Game.getinstance().getPlayer2();
        }
    }


    public void refresh() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                switch (enemyShip[i][j]) {
                    case 0:
                        panels[i][j].setBackground(Color.white);
                        break;
                    case 1:
                        panels[i][j].setBackground(Color.green);
                        break;
                    case 2:
                        panels[i][j].setBackground(Color.orange);
                        break;
                }
            }
        }
        getPlayer().pd.repaint();
    }

}