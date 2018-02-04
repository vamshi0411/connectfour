/**
 * Represents the playing field (or stand) in the game of Connect Four. This
 * class encapsulates all the game logic, including turn-based plays, winning,
 * stalemating and illegal moves.
 */
public class PlayingStand {
    private static final int MAX_ROWS = 6;
    private static final int MAX_COLUMNS = 7;

    private final Chip[][] stand = new Chip[MAX_COLUMNS][MAX_ROWS];
    private boolean gameOver;
    private Chip winner = null;
    private Chip lastPlayed;
    private WinningPlacement winningPlacement;

    public void dropRed(int columnNumber) throws GameOverException {
        dropChip(Chip.RED, columnNumber);
    }

    public void dropBlack(int columnNumber) throws GameOverException {
        dropChip(Chip.BLACK, columnNumber);
    }

    private void dropChip(Chip player, int columnNumber) {
        if (gameOver) {
            throw new GameOverException();
        }

        if (lastPlayed == player) {
            throw new OutOfTurnException();
        }

        if (columnNumber < 0 || columnNumber >= MAX_COLUMNS) {
            throw new InvalidColumnException();
        }

        if (stand[columnNumber][MAX_ROWS - 1] != null) {
            throw new FullColumnException();
        }

        Chip[] column = stand[columnNumber];
        for (int i = 0; i < column.length; i++) {
            Chip nextChip = column[i];
            if (nextChip == null) {
                column[i] = player;
                break;
            }
        }

        lastPlayed = player;
        gameOver = areFourConnected() || isFull();
    }

    public boolean isFull() {

        for (int nextColumnNum = 0; nextColumnNum < stand.length; nextColumnNum++) {
            if (stand[nextColumnNum][MAX_ROWS - 1] == null) {
                return false;
            }
        }

        return true;
    }

    public boolean areFourConnected() {
        for (int i = 0; i < stand.length; i++) {
            Chip[] column = stand[i];
            for (int j = 0; j < column.length; j++) {
                Chip nextCell = column[j];
                if (nextCell != null && (hasVerticalMatch(i, j) ||
                        hasUpwardDiagonalMatch(i, j) ||
                        hasDownwardDiagonalMatch(i, j) ||
                        hasHorizontalMatch(i, j))) {
                    winner = nextCell;
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasHorizontalMatch(int column, int row) {
        Chip chip = stand[column][row];
        winningPlacement = new WinningPlacement(column, row, Direction.HORIZONTAL);

        try {
            return stand[column + 1][row] == chip && stand[column + 2][row] == chip && stand[column + 3][row] == chip;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasUpwardDiagonalMatch(int column, int row) {
        Chip chip = stand[column][row];
        winningPlacement = new WinningPlacement(column, row, Direction.UPWARD_DIAGONAL);
        try {
            return stand[column + 1][row + 1] == chip && stand[column + 2][row + 2] == chip &&
                    stand[column + 3][row + 3] == chip;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasDownwardDiagonalMatch(int column, int row) {
        Chip chip = stand[column][row];
        winningPlacement = new WinningPlacement(column, row, Direction.DOWNWARD_DIAGONAL);
        try {
            return stand[column + 1][row - 1] == chip && stand[column + 2][row - 2] == chip &&
                    stand[column + 3][row - 3] == chip;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean hasVerticalMatch(int column, int row) {
        Chip chip = stand[column][row];
        winningPlacement = new WinningPlacement(column, row, Direction.VERTICAL);
        try {
            return stand[column][row + 1] == chip && stand[column][row + 2] == chip && stand[column][row + 3] == chip;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    public Chip getWinner() {
        return winner;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public WinningPlacement getWinningPlacement() {
        if (!gameOver) {
            throw new GameNotOverException();
        }
        if (isFull() && !areFourConnected()) {
            throw new StalemateException();
        }

        return winningPlacement;
    }

    public class WinningPlacement {
        private final Direction direction;
        private final Cell startingCell;

        public WinningPlacement(int column, int row, Direction direction) {
            startingCell = new Cell(column, row);
            this.direction = direction;
        }

        public Cell getStartingCell() {
            return startingCell;
        }

        public Direction getDirection() {
            return direction;
        }
    }


}
