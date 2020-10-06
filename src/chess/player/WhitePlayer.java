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

public class WhitePlayer extends Player {

    public WhitePlayer(Board board, Collection<Move> whiteStandardLegalMoves, Collection<Move> blackStandardLegalMoves) {
        super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return board.getBlackPlayer();
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegalMoves, Collection<Move> opponentsLegalMoves) {
        final List<Move> kingCastles = new ArrayList<>();

        if (playerKing.isFirstMove() && !isInCheck()) {
            if (!board.getTile(61).isTileOccupied() && !board.getTile(62).isTileOccupied()) {
                final Tile rookTile = board.getTile(63);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(61, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(62, opponentsLegalMoves).isEmpty() &&
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.KingSideCastleMove(board, playerKing, 62,
                                        (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 61));
                    }
                }
            }

            if (!board.getTile(59).isTileOccupied() && !board.getTile(58).isTileOccupied() && !board.getTile(57).isTileOccupied()) {
                final Tile rookTile = board.getTile(56);
                if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(59, opponentsLegalMoves).isEmpty() &&
                            Player.calculateAttacksOnTile(58, opponentsLegalMoves).isEmpty() &&
                            /*Player.calculateAttacksOnTile(57, opponentsLegalMoves).isEmpty() &&*/
                            rookTile.getPiece().getPieceType().isRook()) {
                        kingCastles.add(new Move.QueenSideCastleMove(board, playerKing, 58,
                                        (Rook) rookTile.getPiece(), rookTile.getTileCoordinate(), 59));
                    }
                }
            }
        }

        return Collections.unmodifiableList(kingCastles);
    }
}
