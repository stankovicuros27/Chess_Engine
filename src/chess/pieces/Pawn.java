package chess.pieces;

import chess.Alliance;
import chess.board.Board;
import chess.board.BoardUtils;
import chess.board.Move;
import chess.board.Move.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Pawn extends Piece {

    private static final int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    public Pawn(Alliance pieceAlliance, final int piecePosition) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, true);
    }

    public Pawn(Alliance pieceAlliance, final int piecePosition, boolean isFirstMove) {
        super(PieceType.PAWN, piecePosition, pieceAlliance, isFirstMove);
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = piecePosition + currentCandidateOffset * getPieceAlliance().getDirection();
            if (!BoardUtils.isValidCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                if (pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                    legalMoves.add(new PawnPromotion(new Move.PawnMove(board, this, candidateDestinationCoordinate)));
                } else {
                    legalMoves.add(new Move.PawnMove(board, this, candidateDestinationCoordinate));
                }

            } else if (currentCandidateOffset == 16 && this.isFirstMove() &&
                    ((BoardUtils.SEVENTH_RANK[piecePosition] && this.getPieceAlliance().isBlack()) ||
                            (BoardUtils.SECOND_RANK[piecePosition]) && this.getPieceAlliance().isWhite())) {
                final int behindCandidateDestinationCoordinate = piecePosition + getPieceAlliance().getDirection() * 8;
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                        !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.PawnJump(board, this, candidateDestinationCoordinate));
                }
            } else if (currentCandidateOffset == 7 &&
                    !((BoardUtils.EIGHTH_COLUMN[piecePosition] && getPieceAlliance().isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[piecePosition] && getPieceAlliance().isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        if (pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        } else {
                            legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if(board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (piecePosition + pieceAlliance.getOppositeDirection())) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            } else if (currentCandidateOffset == 9 &&
                    !((BoardUtils.FIRST_COLUMN[piecePosition] && getPieceAlliance().isWhite()) ||
                            (BoardUtils.EIGHTH_COLUMN[piecePosition] && getPieceAlliance().isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.getPieceAlliance() != pieceOnCandidate.getPieceAlliance()) {
                        if (pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
                            legalMoves.add(new PawnPromotion(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate)));
                        } else {
                            legalMoves.add(new Move.PawnAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                } else if(board.getEnPassantPawn() != null) {
                    if (board.getEnPassantPawn().getPiecePosition() == (piecePosition - pieceAlliance.getOppositeDirection())) {
                        final Piece pieceOnCandidate = board.getEnPassantPawn();
                        if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
                            legalMoves.add(new Move.PawnEnPassantAttackMove(board, this, candidateDestinationCoordinate, pieceOnCandidate));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public Pawn movePiece(Move move) {
        return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    public Piece getPromotionPiece() {
        return new Queen(this.pieceAlliance, this.piecePosition, false);
    }
}
