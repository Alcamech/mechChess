package com.mechchess.engine.piece;

import com.google.common.collect.ImmutableSet;
import com.mechchess.engine.Side;
import com.mechchess.engine.board.Board;
import com.mechchess.engine.board.BoardUtils;
import com.mechchess.engine.board.Move;
import com.mechchess.engine.board.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing the Knight chess piece.
 */
public class Knight extends Piece {

    private final static int[] POSSIBLE_MOVE_OFFSETS = {-17, -15, -10, -6, 6, 10, 15, 17};

    Knight(int piecePosition, Side pieceSide) {
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
                        isSecondColumnExclusion(this.piecePosition, currentOffset) ||
                        isSeventhColumnExclusion(this.piecePosition, currentOffset) ||
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

        return ImmutableSet.copyOf(legalMoves);
    }

    // edge cases for POSSIBLE_MOVE_OFFSETS where the traditional offsets for calculating a move position
    // do not work.
    private static boolean isFirstColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && ((possibleOffset == -17) || (possibleOffset == -10) ||
                (possibleOffset == 6) || (possibleOffset == 15));
    }

    private static boolean isSecondColumnExclusion(final int currentPositon, final int possibleOffset) {
        return BoardUtils.SECOND_COLUMN[currentPositon] && ((possibleOffset == -10) || (possibleOffset == 6));
    }

    private static boolean isSeventhColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((possibleOffset == -6) || (possibleOffset == 10));
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((possibleOffset == -15) || (possibleOffset == -6) ||
                (possibleOffset == 10) || (possibleOffset == 17));
    }

}
