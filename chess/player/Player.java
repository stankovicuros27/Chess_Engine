package chess.player;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;
import chess.pieces.King;
import chess.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public abstract class Player {

    protected final Board board;
    protected final King playerKing;
    protected final Collection<Move> legalMoves;
    private final boolean isInCheck;

    Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
        this.board = board;
        this.playerKing = establishKing();
        this.legalMoves = legalMoves;
        this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
    }

    private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (piecePosition == move.getDestinationCoordinate()) {
                attackMoves.add(move);
            }
        }
        return Collections.unmodifiableList(attackMoves);
    }

    protected King establishKing() {
        for (final Piece piece : getActivePieces()) {
            if (piece.getPieceType().isKing()) {
                return (King) piece;
            }
        }
        throw new RuntimeException("Should not reach here! Not a valid board!");
    }

    public King getPlayerKing() {
        return playerKing;
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public Collection<Move> getLegalMoves() {
        return legalMoves;
    }

    public boolean isInCheck(){
        return isInCheck;
    }

    public boolean isInCheckMate() {
        return isInCheck && !hasEscapeMoves();
    }

    public boolean isInStaleMate() {
        return !isInCheck && !hasEscapeMoves();
    }

    public boolean isCastled() {
        return false;
    }

    protected boolean hasEscapeMoves() {
        for (final Move move : legalMoves) {
            final MoveTransition transition = makeMove(move);
            if (transition.getMoveStatus().isDone()) {
                return true;
            }
        }
        return false;
    }

    public MoveTransition makeMove(final Move move){
        if (!isMoveLegal(move)) {
            return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
        }
        final Board transitionBoard = move.execute();
        final Collection<Move> kingAttacks =
                Player.calculateAttacksOnTile(transitionBoard.getCurrentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
                        transitionBoard.getCurrentPlayer().getLegalMoves());

        if (!kingAttacks.isEmpty()) {
            return new MoveTransition(board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
        }
        return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
    }

    public abstract Collection<Piece> getActivePieces();
    public abstract Alliance getAlliance();
    public abstract Player getOpponent();
}
