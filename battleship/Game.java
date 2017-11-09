package battleship;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {

    private static PlayerScreen player1;
    private static PlayerScreen player2;
    private static Game game;
    public static GameStates state = GameStates.PLAYER1_SETUP;

    private Game() {}

    public static Game getinstance() {
        if (game == null) {
            game = new Game();
        }
        return game;
    }

    public static PlayerScreen getPlayer1() {
        return player1;
    }

    public static PlayerScreen getPlayer2() {
        return player2;
    }

    public static void setPlayer1(PlayerScreen player1) {
        Game.player1 = player1;
    }

    public static void setPlayer2(PlayerScreen player2) {
        Game.player2 = player2;
    }

    public static GameStates getState() {
        return state;
    }

    public static void getNextState() {
        switch (state) {
            case PLAYER1_SETUP:
                getinstance().getPlayer2().showScreen();
                state = GameStates.PLAYER2_SETUP;
                break;
            case PLAYER2_SETUP:
                getinstance().getPlayer1().showScreen();
                state = GameStates.PLAYER1_ATTACK;
                break;
            case PLAYER1_ATTACK:
                if (getinstance().getPlayer1().sg.enemySunk == 5) {
                    state = GameStates.PLAYER1_WIN;
                }
                else {
                    getinstance().getPlayer1().ag.attacked = false;
                    getinstance().getPlayer2().showScreen();
                    state = GameStates.PLAYER2_ATTACK;
                }
                break;
            case PLAYER2_ATTACK:
                if (getinstance().getPlayer2().sg.enemySunk == 5) {
                    state = GameStates.PLAYER2_WIN;
                }
                else {
                    getinstance().getPlayer2().ag.attacked = false;
                    getinstance().getPlayer1().showScreen();
                    state = GameStates.PLAYER1_ATTACK;
                }
                break;
            case PLAYER1_WIN:
                break;
            case PLAYER2_WIN:
                break;
        }
    }


    public void attack(int x, int y) {
        int self; // this player's enemyship[x][y] in AttackGrid (1 missed, 2 hit)
        int enemy; // another player's ship[x][y] in SelfGrid (2 missed, 3 hit)
        int enemyState; //show 1 if the enemy put ship on this cell, show 0 if not
       if (getinstance().getState() == GameStates.PLAYER1_ATTACK) {
           enemyState = getinstance().getPlayer2().sg.ships[x][y];
           if (enemyState == 1) {
               self = 2;
               enemy = 3;
           }
           else {
               self = 1;
               enemy = 2;
           }
           getinstance().getPlayer1().ag.enemyShip[x][y] = self;
           getinstance().getPlayer2().sg.ships[x][y] = enemy;
       }
       else {
           enemyState = getinstance().getPlayer1().sg.ships[x][y];
           if (enemyState == 1) {
               self = 2;
               enemy = 3;
           }
           else {
               self = 1;
               enemy = 2;
           }
           getinstance().getPlayer2().ag.enemyShip[x][y] = self;
           getinstance().getPlayer1().sg.ships[x][y] = enemy;
       }
        judge();
       getinstance().getPlayer1().ag.refresh();
       getinstance().getPlayer1().sg.refresh();
       getinstance().getPlayer2().sg.refresh();
       getinstance().getPlayer2().ag.refresh();
    }

    public void judge() {
        int numberSunk = 0;
        if (getinstance().getState() == GameStates.PLAYER1_ATTACK) {
            for (Ship p : getinstance().getPlayer2().sg.ownShip) {
                if (p.horizontal && getinstance().getPlayer2().sg.ships[p.x][p.y] == 3 &&
                        getinstance().getPlayer2().sg.ships[p.x][p.y + 1] == 3 &&
                        getinstance().getPlayer2().sg.ships[p.x][p.y + 2] == 3) {
                    p.isSunk = true;
                    numberSunk++;
                }
                else if (!p.horizontal && getinstance().getPlayer2().sg.ships[p.x][p.y] == 3 &&
                        getinstance().getPlayer2().sg.ships[p.x + 1][p.y] == 3 &&
                        getinstance().getPlayer2().sg.ships[p.x + 2][p.y] == 3) {
                    p.isSunk = true;
                    numberSunk++;
                }
            }
            getinstance().getPlayer1().sg.enemySunk = numberSunk;
            getinstance().getPlayer2().sg.ownSunk = numberSunk;
            if (numberSunk == 5) {
                state = GameStates.PLAYER1_WIN;
            }
        }
        else {
            for (Ship p : getinstance().getPlayer1().sg.ownShip) {
                if (p.horizontal && getinstance().getPlayer1().sg.ships[p.x][p.y] == 3 &&
                        getinstance().getPlayer1().sg.ships[p.x][p.y + 1] == 3 &&
                        getinstance().getPlayer1().sg.ships[p.x][p.y + 2] == 3) {
                    p.isSunk = true;
                    numberSunk++;
                }
                else if (!p.horizontal && getinstance().getPlayer1().sg.ships[p.x][p.y] == 3 &&
                        getinstance().getPlayer1().sg.ships[p.x + 1][p.y] == 3 &&
                        getinstance().getPlayer1().sg.ships[p.x + 2][p.y] == 3) {
                    p.isSunk = true;
                    numberSunk++;
                }
            }
            getinstance().getPlayer2().sg.enemySunk = numberSunk;
            getinstance().getPlayer1().sg.ownSunk = numberSunk;
            if (numberSunk == 5) {
                state = GameStates.PLAYER2_WIN;
            }
        }
    }


}
