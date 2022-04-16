package tictactoe;

public class Coordinate {
    public final int x;
    public final int y;

    Coordinate(int x, int y) throws CoordinateOutBoundException {
        if (x < 0 || x > 2 || y < 0 || y > 2) {
            throw new CoordinateOutBoundException();
        }
        this.x = x;
        this.y = y;
    }
}

