package com.mechchess.engine.board;

import com.mechchess.engine.piece.Piece;

/**
 * Class representing a piece move.
 */
public abstract class Move {

    final Board board;
    final Piece piece;
    final int destinationCoordinate;

    private Move(final Board board, final Piece piece, final int destinationCoordinate) {
        this.board = board;
        this.piece = piece;
        this.destinationCoordinate = destinationCoordinate;
    }

    public static final class PieceMove extends Move {
        public PieceMove(final Board board, final Piece piece, final int destinationCoordinate) {
            super(board, piece, destinationCoordinate);
        }
    }

    public static final class PieceAttack extends Move {
        final Piece targetPiece;

        public PieceAttack(final Board board, final Piece piece, final int destinationCoordinate, final Piece targetPiece) {
            super(board, piece, destinationCoordinate);
            this.targetPiece = targetPiece;
        }
    }
}
