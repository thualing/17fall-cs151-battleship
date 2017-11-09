package battleship;

public class Ship {
    public int x;
    public int y;
    public Boolean horizontal;
    public Boolean isSunk;


    public Ship(int x, int y, Boolean horizontal) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.isSunk = false;
    }

    public Boolean isBelongedToShip(int x, int y) {
        if (horizontal) {
            return this.x == x && (y - this.y) <= 2;
        }
        else {
            return this.y == y && (x - this.x) <= 2;
        }
    }



}
