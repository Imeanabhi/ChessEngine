package Pieces;
import PieceAttributes.*;

public class Knight extends Piece {
    public Knight(PieceColor color, Position position) {
        super(color, position);
    }

    @Override
    public boolean isValidMove(Position newPosition, Piece[][] board) {
        int rowDiff = Math.abs(this.position.getRow() - newPosition.getRow());
        int colDiff = Math.abs(this.position.getColumn() - newPosition.getColumn());

        boolean isValidLMove = (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);

        if (!isValidLMove) {
            return false;
        }

        Piece destinationPiece = board[newPosition.getRow()][newPosition.getColumn()];

        return destinationPiece == null || destinationPiece.getColor() != this.getColor();
    }
}
