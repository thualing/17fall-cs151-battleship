package battleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashSet;

/**
Represents the player's own grid
*/
public class SelfGrid extends BattleGrid {

    public int[][] ships = new int[10][10]; // 0 if no ship, 1 if put ship, 2 if been attacked and missed, 3 if hit.
    public HashSet<Ship> ownShip = new HashSet<>();
    public int shipNumber = 0;
    public int ownSunk = 0;
    public int enemySunk = 0;
    public String name;

    public SelfGrid(String name) {
        super();
        this.name = name;
    }

    @Override
    protected JPanel getCell(int x,int y)
    {
        JPanel panel = panels[x][y];
        panel.setBackground(Color.black);
        panel.setBorder(BorderFactory.createLineBorder(Color.blue, 5));
        panel.setPreferredSize(new Dimension(20, 20)); // for demo purposes only
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (Game.getinstance().getState() == GameStates.PLAYER1_SETUP ||
                        Game.getinstance().getState() == GameStates.PLAYER2_SETUP) {
                    if (shipNumber < 5 && ships[x][y] == 0 && ships[x][y + 1] == 0 && ships[x][y + 2] == 0 && y < 8) {
                        shipNumber++;
                        ownShip.add(new Ship(x, y, true));
                        ships[x][y] = 1;
                        ships[x][y + 1] = 1;
                        ships[x][y + 2] = 1;
                        refresh();
                    }
                    else if (ships[x][y] == 1) {
                        for (Ship s : ownShip) {
                            if (s.isBelongedToShip(x, y)) {
                                if (canToggle(s)) {
                                    if (s.horizontal) {
                                        ships[s.x][s.y + 1] = 0;
                                        ships[s.x][s.y + 2] = 0;
                                        ships[s.x + 1][s.y] = 1;
                                        ships[s.x + 2][s.y] = 1;
                                        s.horizontal = false;
                                        refresh();
                                    }
                                    else {
                                        ships[s.x][s.y + 1] = 1;
                                        ships[s.x][s.y + 2] = 1;
                                        ships[s.x + 1][s.y] = 0;
                                        ships[s.x + 2][s.y] = 0;
                                        s.horizontal = true;
                                        refresh();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        return panel;
    }

    public void refresh() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                switch (ships[i][j]) {
                    case 0:
                        panels[i][j].setBackground(Color.black);
                        break;
                    case 1:
                        panels[i][j].setBackground(Color.white);
                        break;
                    case 2:
                        panels[i][j].setBackground(Color.green);
                        break;
                    case 3:
                        panels[i][j].setBackground(Color.orange);
                        break;
                }
            }
        }
        getPlayer().pd.repaint();
    }

    public PlayerScreen getPlayer() {
        if(this.name.equals("Player1")){
            return Game.getinstance().getPlayer1();
        }
        else{
            return Game.getinstance().getPlayer2();
        }
    }

    public boolean canToggle(Ship s) {
        if (s.horizontal) {
            return s.x < 8 && ships[s.x + 1][s.y] == 0 && ships[s.x + 2][s.y] == 0;
        }
        else {
            return s.y < 8 && ships[s.x][s.y + 1] == 0 && ships[s.x][s.y + 2] == 0;
        }
    }

}

