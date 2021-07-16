package com.mechchess.engine.board;

import com.google.common.collect.ImmutableMap;
import com.mechchess.engine.piece.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract immutable class for a single chess tile. Cannot be instantiated.
 */
public abstract class Tile {
    // only accessible by its subclasses and able to be set once.
    protected final int tileCoordinate;

    // create all possible and valid empty tiles upfront.
    private static final Map<Integer, EmptyTile> EMPTY_TILE_MAP = createValidEmptyTiles();

    private static Map<Integer, EmptyTile> createValidEmptyTiles() {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for (int i =0; i < BoardUtils.TOTAL_NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    // accessible method for creating an occupied tile or getting a pre-made empty tile.
    public static Tile createTile(final int tileCoordinate, final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE_MAP.get(tileCoordinate);
    }

    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    public abstract boolean isTileOccupied();
    public abstract Piece getPiece();

    /**
     *  Subclass representing the state of an
     *  empty tile. Can be instantiated.
     */
    public static final class EmptyTile extends Tile {
        private EmptyTile(final int coordinate) {
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     *  Subclass representing the state of an
     *  occupied tile. Can be instantiated.
     */
    public static final class OccupiedTile extends Tile {
        // piece can not be referenced outside of OccupiedTile
        private final Piece piece;

        private OccupiedTile(final int coordinate,final Piece piece) {
            super(coordinate);
            this.piece = piece;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.piece;
        }
    }
}
