package battleship;

import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class PlayerScreen extends JFrame {
    public Game bt = Game.getinstance();
    public SelfGrid sg;
    public AttackGrid ag;
    public PlayerData pd;
    public String name;
    public PlayerScreen(String name, boolean show) {
        super(name);
        if(name.equals("Player1")) {
            Game.getinstance().setPlayer1(this);
        }
        else{
            Game.getinstance().setPlayer2(this);
        }
        this.setLayout(new BorderLayout());
        this.name = name;
        this.sg = new SelfGrid(name);
        this.ag = new AttackGrid(name);
        this.pd = new PlayerData(name);
        this.add(sg, BorderLayout.EAST);
        this.add(ag, BorderLayout.WEST);
        this.add(new JLabel(name), BorderLayout.NORTH);
        this.add(pd, BorderLayout.SOUTH);
        JButton next = new JButton("next");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (bt.getState() == GameStates.PLAYER1_SETUP || bt.getState() == GameStates.PLAYER2_SETUP) {
                    if (sg.shipNumber == 5) {
                        hideScreen();
                        bt.getNextState();
                    }
                }
                else if (bt.getState() == GameStates.PLAYER1_ATTACK) {
                    if (bt.getPlayer1().ag.attacked) {
                        hideScreen();
                        bt.getNextState();
                    }
                }
                else if (bt.getState() == GameStates.PLAYER2_ATTACK) {
                    if (bt.getPlayer2().ag.attacked) {
                        hideScreen();
                        bt.getNextState();
                    }
                }

            }
        });
        this.add(next, BorderLayout.CENTER);
        this.pack();
        this.setVisible(show);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void hideScreen() {
        this.setVisible(false);
    }

    public void showScreen() {
        this.setVisible(true);
    }
}

