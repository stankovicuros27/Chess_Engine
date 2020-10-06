package chess;

import chess.board.Board;
import chess.gui.Table;

public class JChess {
    public static void main (String[] args) {
        Board board = Board.createStandardBoard();
        System.out.println(board);
        Table table = new Table();
    }
}
