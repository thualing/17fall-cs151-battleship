package battleship;

public class BattleShip {

    public static void main(String[] args) {
        PlayerScreen player1 = new PlayerScreen("Player1", true);
        PlayerScreen player2 = new PlayerScreen("Player2", false);
        Game.getinstance().setPlayer1(player1);
        Game.getinstance().setPlayer2(player2);
    }

}