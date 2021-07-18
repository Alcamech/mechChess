package com.mechchess.engine.piece;

import com.mechchess.engine.Side;
import com.mechchess.engine.board.Board;
import com.mechchess.engine.board.BoardUtils;
import com.mechchess.engine.board.Move;
import com.mechchess.engine.board.Tile;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Queen extends Piece{

    private final static int[] POSSIBLE_MOVE_OFFSETS = {-9, -7, -8, -1, 1, 7, 8, 9};

    Queen(int piecePosition, Side pieceSide) {
        super(piecePosition, pieceSide);
    }

    @Override
    public Set<Move> calculateLegalMoves(final Board board) {
        Set<Move> legalMoves = new HashSet<>();

        for (final int currentOffset : POSSIBLE_MOVE_OFFSETS) {
            final int possibleDestinationCoordinate = this.piecePosition + currentOffset;

            if (BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)) {
                final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                if (isFirstColumnExclusion(this.piecePosition, currentOffset) ||
                        isEighthColumnExclusion(this.piecePosition, currentOffset)) {
                    continue;
                }

                if (possibleDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.PieceMove(board, this, possibleDestinationCoordinate)); // non attack move
                } else { // is tile occupied by an enemy piece?
                    final Piece pieceAtDestination = possibleDestinationTile.getPiece();
                    final Side pieceSide = pieceAtDestination.getPieceSide();

                    if (this.pieceSide != pieceSide) {
                        legalMoves.add(new Move.PieceAttack(board, this, possibleDestinationCoordinate, pieceAtDestination)); // attack move
                    }
                }
            }
        }

        return Collections.unmodifiableSet(legalMoves);
    }

    // edge cases for POSSIBLE_MOVE_OFFSETS where the traditional offsets for calculating a move position
    // do not work.
    private static boolean isFirstColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (possibleOffset == -1 || possibleOffset == -9 || possibleOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (possibleOffset == 1 || possibleOffset == 9 || possibleOffset == -7);
    }
}
