package chess.board;

import chess.pieces.Piece;

public abstract class Move {
    final Board board;
    final Piece piece;
    final int destinationCoordinate;

    private  Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.piece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board,
                          final Piece movedPiece,
                          final int destinationCoordinate,
                          Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attackedPiece = attackedPiece;
        }
    }
}
