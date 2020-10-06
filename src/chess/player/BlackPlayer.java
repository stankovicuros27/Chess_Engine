package chess.player;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;
import chess.board.Tile;
import chess.pieces.Piece;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BlackPlayer extends Player {

    public BlackPlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return board.getWhitePlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if (playerKing.isFirstMove() && !isInCheck()) {
            if (!board.getTile(5).isTileOccupied() && !board.getTile(6).isTileOccupied()) {
                final Tile rookTile = board.getTile(7);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(5, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(6, opponentsLegalMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(board, playerKing, 6,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 5));
                    }
                }
            }

            if (!board.getTile(1).isTileOccupied() && !board.getTile(2).isTileOccupied() && !board.getTile(3).isTileOccupied()) {
                final Tile rookTile = board.getTile(0);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (/*Player.calculateAttacksOnTile(1, opponentsLegalMoves).isEmpty() &&*/
                            Player.calculateAttacksOnTile(2, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(3, opponentsLegalMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.QueenSideCastleMove(board, playerKing, 2,
                                (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 3));
                    }
                }
            }
        }

        return Collections.unmodifiableList(kingCastles);    }
}
