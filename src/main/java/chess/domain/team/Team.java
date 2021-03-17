package chess.domain.team;

import chess.domain.Position;
import chess.domain.piece.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Team {
    protected final Map<Position, Piece> piecePosition;

    public Team() {
        piecePosition = new HashMap<>();
    }

    protected void initializePiece(final int pawnColumn, final int pieceColumn) {
        initializePawn(pawnColumn);
        piecePosition.put(new Position( 0, pieceColumn), new Rook(""));
        piecePosition.put(new Position( 1, pieceColumn), new Knight(""));
        piecePosition.put(new Position( 2, pieceColumn), new Bishop(""));
        piecePosition.put(new Position( 3, pieceColumn), new Queen(""));
        piecePosition.put(new Position( 4, pieceColumn), new King(""));
        piecePosition.put(new Position( 5, pieceColumn), new Bishop(""));
        piecePosition.put(new Position( 6, pieceColumn), new Knight(""));
        piecePosition.put(new Position( 7, pieceColumn), new Rook(""));
    }

    private void initializePawn(final int pawnColumn) {
        for (int i = 0; i < 8; i++) {
            piecePosition.put(new Position(i, pawnColumn), new Pawn(""));
        }
    }

    public Map<Position, Piece> getPiecePosition() {
        return Collections.unmodifiableMap(piecePosition);
    }
}