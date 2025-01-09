package ChessGame;
import java.util.ArrayList;
import java.util.List;

import PieceAttributes.*;
import Pieces.*;

public class ChessGame {
 private ChessBoard board;
 private Boolean whiteTurn = true;
 
 public ChessGame()
 {
    this.board = new ChessBoard();
 }
 public ChessBoard getBoard()
 {
    return this.board;
 }

 public void resetGame()
 {
    this.board = new ChessBoard();
    this.whiteTurn = true;
}

 public PieceColor getCurrentPlayerColor()
 {
    return whiteTurn?PieceColor.WHITE:PieceColor.BLACK;
 }

 public Position selectedPosition;
 
 public boolean isPieceSelected()
 {
    return selectedPosition != null;
 }


public boolean handleSquareSelection(int row, int col)
{
    if(selectedPosition == null)
    //No piece is currently selected
    {
    Piece selectedPiece = board.getPiece( row,col);
        if(selectedPiece!=null && selectedPiece.getColor()==(whiteTurn?PieceColor.WHITE:PieceColor.BLACK))
        {
            //Selected Position stores the position of the currently selected Piece
selectedPosition = new Position(row,col);
return false;//such that no move has still been made
        }
    }
    else{
        boolean moveMade = makeMove(selectedPosition, new Position(row,col));
        selectedPosition = null;
        return moveMade;
    }
    return false;
}
public boolean makeMove(Position start,Position end)
{
Piece movingPiece = board.getPiece(start.getRow(),start.getColumn());
if(movingPiece == null ||movingPiece.getColor()!=(whiteTurn?PieceColor.WHITE:PieceColor.BLACK))
{
    return false;
}
if(movingPiece.isValidMove(end,board.getBoard()))
{
    board.movePiece(start,end);
    whiteTurn = !whiteTurn;
    return true;
}
return false;
}

public boolean isInCheck(PieceColor KingColor)
{
    Position KingPosition = findKingPosition(KingColor);
    for (int row = 0; row < board.getBoard().length; row++) {
        for (int col = 0; col < board.getBoard()[row].length; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() != KingColor)
                {
                    if(piece.isValidMove(KingPosition,board.getBoard())){
                    return true;
                }
            }
        }
    }
        return false;
}
public Position findKingPosition(PieceColor color)
{
    for (int row = 0; row < board.getBoard().length; row++) {
        for (int col = 0; col < board.getBoard()[row].length; col++) {
            Piece piece = board.getPiece(row, col);
            if (piece instanceof King && piece.getColor() == color) {
                return new Position(row, col);
            }
        }
    }
    throw new RuntimeException("King not found, which should never happen.");
}

//Checking for CheckMate
public boolean isCheckMate(PieceColor KingColor)
{
if(!isInCheck(KingColor))
{
    return false;
}
Position KingPosition = findKingPosition(KingColor);
King king = (King) board.getPiece(KingPosition.getRow(),KingPosition.getColumn());
for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
    for (int colOffset = -1; colOffset <= 1; colOffset++) {
    if (rowOffset == 0 && colOffset == 0) {
        continue;
    }
Position newPosition = new Position(KingPosition.getRow()+rowOffset,KingPosition.getColumn()+colOffset);
if(isPositionOnBoard(newPosition) && king.isValidMove(newPosition, board.getBoard()) && !wouldBeInCheckMove(KingColor,KingPosition,newPosition))
{
return false;
}
    }
}
return true;
}

private boolean wouldBeInCheckMove(PieceColor kingColor, Position from , Position to) {
Piece temp = board.getPiece(to.getRow(),to.getColumn());
//move the piece from position to To position
board.setPiece(to.getRow(),to.getColumn(),board.getPiece(from.getRow(),from.getColumn()));
//set the current from position to null
board.setPiece(from.getRow(),from.getColumn(),null);
boolean inCheck = isInCheck(kingColor);
//Bring back from To position to From position
board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(), to.getColumn()));
board.setPiece(to.getRow(), to.getColumn(), temp);
return inCheck;
}

public List<Position>getAllLegalMovesForPieces(Position position)
{
    Piece selectedPiece = board.getPiece(position.getRow(),position.getColumn());
    if(selectedPiece == null)
    {
        return new ArrayList<>();
    }
    List<Position>legalMoves = new ArrayList<>();
    switch(selectedPiece.getClass().getName())
    {
        case "Pawn" :
        addPawnMoves(position , selectedPiece.getColor(),legalMoves);
        break;
        case "Rook" :
        addLineMoves(position ,new int[][] {{1,0},{-1,0},{0,1},{0,-1}},legalMoves);
                break;
        case "Queen":
        addLineMoves(position,new int[][] {{1,0},{-1,0},{0,-1},{0,1},{1,1},{-1,-1},{-1,1},{1,-1}},legalMoves);
                break;
        case "King" :
                addSingleMoves(position, new int[][] {{0,1},{0,-1},{1,0},{-1,0},{-1,-1},{1,1},{-1,1},{1,-1}},legalMoves);
                break;
        case "Knight":
                addSingleMoves(position, new int[][] { { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, 2 }, { -1, 2 },{ 1, -2 }, { -1, -2 } }, legalMoves);
                    break;
        case "Bishop" :
                addLineMoves(position, new int[][] { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } }, legalMoves);
                    break;
    }
    return legalMoves;
}

private void addSingleMoves(Position position,int[][] moves , List<Position>legalMoves)
{
    for(int move[] : moves )
    {
        Position newPos = new Position(position.getRow()+move[0],position.getColumn()+move[1]);
        if((isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(),newPos.getColumn()) == null) || board.getPiece(newPos.getRow(),newPos.getColumn()).getColor() != board.getPiece(position.getRow(),newPos.getColumn()).getColor() )
        {
            legalMoves.add(newPos);
        }
    }
}

        private void addLineMoves(Position position,int[][] directions , List<Position> legalMoves) {
for(int[] d : directions)
{
Position newPos = new Position(position.getRow()+d[0],position.getColumn()+d[1]);
while(isPositionOnBoard(newPos))
{
if(board.getPiece(newPos.getRow(),newPos.getColumn()) == null )
{
legalMoves.add(new Position(newPos.getRow(),newPos.getColumn()));
newPos = new Position(newPos.getRow()+d[0],newPos.getColumn()+d[1]);
}
else
{
    if(board.getPiece(newPos.getRow(),newPos.getColumn()).getColor()!=board.getPiece(position.getRow(),position.getColumn()).getColor())
    {
    legalMoves.add(newPos); 
    }
}
break;
}
}
}

private void addPawnMoves(Position position , PieceColor color, List<Position>legalMoves)
{
    int direction = (color == PieceColor.WHITE? -1 : 1);
    Position newPos = new Position(position.getRow()+direction,position.getColumn());
    if(isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn())==null)
    {
        legalMoves.add(newPos);
    }

    if((color == PieceColor.WHITE && position.getRow()==6) || (color ==PieceColor.BLACK && position.getColumn()==1))
    {
        newPos = new Position(position.getRow()+2*direction,position.getColumn());
        Position intermediatePos = new Position(position.getRow() + direction , position.getColumn());
        if (isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(), newPos.getColumn()) == null
        && board.getPiece(intermediatePos.getRow(), intermediatePos.getColumn()) == null) {
    legalMoves.add(newPos);
}
    }
int[] captureCols = {position.getColumn()-1,position.getColumn()+1};
for(int col : captureCols)
{
    newPos = new Position(position.getRow()+direction , col);
if(isPositionOnBoard(newPos) && board.getPiece(newPos.getRow(),newPos.getColumn())!=null && board.getPiece(newPos.getRow()
,newPos.getColumn()).getColor()!=color)
{
    legalMoves.add(newPos);
}
}
}


private boolean isPositionOnBoard(Position position)
{
    return (position.getRow()>= 0 && position.getColumn()<board.getBoard().length && position.getColumn()>=0 
    && position.getColumn()<board.getBoard()[0].length);
}
};
