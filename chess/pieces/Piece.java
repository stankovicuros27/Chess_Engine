package chess.pieces;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;

import java.util.Collection;

public abstract class Piece {

    protected final boolean isFirstMove;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;

    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.piecePosition = piecePosition;
        this.pieceAlliance = pieceAlliance;
        this.isFirstMove = false;
    }

    public Alliance getPieceAlliance(){
        return pieceAlliance;
    }

    public boolean isFirstMove(){
        return isFirstMove;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
