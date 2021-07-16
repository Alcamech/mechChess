package com.mechchess.engine.piece;

import com.mechchess.engine.Side;
import com.mechchess.engine.board.Board;
import com.mechchess.engine.board.Move;

import java.util.Set;

/**
 * Abstract class representing a chess piece.
 */
public abstract class Piece {
    protected final int piecePosition;
    protected final Side pieceSide;

    Piece(final int piecePosition, final Side pieceSide) {
        this.piecePosition = piecePosition;
        this.pieceSide = pieceSide;
    }

    public Side getPieceSide() {
        return this.pieceSide;
    }

    // Given a chess board return all the legal moves available for each piece.
    public abstract Set<Move> calculateLegalMoves(final Board board);
}
