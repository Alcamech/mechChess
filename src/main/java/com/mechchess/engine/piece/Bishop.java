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
 * Class representing the Bishop chess piece.
 */
public class Bishop extends Piece {

    private final static int[] POSSIBLE_MOVE_OFFSETS = {-9, -7, 7, 9};

    Bishop(int piecePosition, Side pieceSide) {
        super(piecePosition, pieceSide);
    }

    @Override
    public Set<Move> calculateLegalMoves(Board board) {
        final Set<Move> legalMoves = new HashSet<>();

        for (final int currentOffset : POSSIBLE_MOVE_OFFSETS) {
            int possibleDestinationCoordinate = this.piecePosition;

            while (BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)) {

                if (isFirstColumnExclusion(possibleDestinationCoordinate, currentOffset) ||
                        isEighthColumnExclusion(possibleDestinationCoordinate, currentOffset)) {
                    break;
                }

                possibleDestinationCoordinate += currentOffset;

                if (BoardUtils.isValidTileCoordinate(possibleDestinationCoordinate)) {
                    final Tile possibleDestinationTile = board.getTile(possibleDestinationCoordinate);

                    if (possibleDestinationTile.isTileOccupied()) {
                        legalMoves.add(new Move.PieceMove(board, this, possibleDestinationCoordinate)); // non attack move
                    } else { // is tile occupied by an enemy piece?
                        final Piece pieceAtDestination = possibleDestinationTile.getPiece();
                        final Side pieceSide = pieceAtDestination.getPieceSide();

                        if (this.pieceSide != pieceSide) {
                            legalMoves.add(new Move.PieceAttack(board, this, possibleDestinationCoordinate, pieceAtDestination)); // attack move
                        }
                        break; //stop looking for valid moves
                    }
                }
            }
        }
        return ImmutableSet.copyOf(legalMoves);
    }

    private static boolean isFirstColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (possibleOffset == -9 || possibleOffset == 7);
    }

    private static boolean isEighthColumnExclusion(final int currentPosition, final int possibleOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (possibleOffset == 9 || possibleOffset == -7);
    }

}
