package ChessGame;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import PieceAttributes.PieceColor;
import Pieces.*;

public class ChessGameGUI extends JFrame {
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8]; // Corrected typo
    private final ChessGame game = new ChessGame();
    private final Map<Class<? extends Piece>, String> pieceNameMap = new HashMap<>() {
        {
            put(Pawn.class, "P");  // "♙" for Unicode
            put(Rook.class, "R"); // "♖"
            put(Knight.class, "N"); // "♘"
            put(Bishop.class, "B"); // "♗"
            put(Queen.class, "Q"); // "♕"
            put(King.class, "K"); // "♔"
        }
    };
    
    public ChessGameGUI() { 
        setTitle("ChessGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8,8));
        initializeBoard();
        addGameResetOption();
        pack();
        setVisible(true);
    }
    private void resetGame() {
        game.resetGame();
        refreshBoard();
    }

    private void addGameResetOption() {
    JMenuBar menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenuItem resetItem = new JMenuItem("Reset");
    resetItem.addActionListener(e->resetGame());
    gameMenu.add(resetItem);
    menuBar.add(gameMenu);
    setJMenuBar(menuBar);
}


    private void initializeBoard() {
        for (int row = 0; row < squares.length; row++) {
            for (int col = 0; col < squares[row].length; col++) {
                final int finalRow = row;
                final int finalCol = col;
                ChessSquareComponent square = new ChessSquareComponent(row, col);
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    //Mouse clicked Method
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(finalRow, finalCol);
                                            }
                                        });
                                        add(square);
                                        squares[row][col] = square;
                                    }
                                }
                                refreshBoard();
                            }
/*This code is a method for refreshing the graphical representation of the chessboard in the GUI.
It ensures that the displayed chessboard reflects the current state of the underlying game logic by
iterating through each square and updating it based on the current position of pieces. */
private void refreshBoard() {
    ChessBoard board = game.getBoard(); // Fetch the current board state from the game
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = board.getPiece(row, col); // Get the piece at the current position
            if (piece != null) {
                // Get the name or symbol of the piece (e.g., "P" for Pawn)
                String pieceSymbol = pieceNameMap.get(piece.getClass());
                Color pieceColor = (piece.getColor() == PieceColor.WHITE) ? Color.WHITE : Color.BLACK;
                squares[row][col].setPieceSymbol(pieceSymbol, pieceColor); // Update the square with the piece
            } else {
                squares[row][col].clearPieceSymbol(); // Clear the square if no piece is present
            }
        }
    }
}


//when a piece is clicked this function determines 
private void handleSquareClick(int finalRow, int finalCol) {
boolean moveResult = game.handleSquareSelection(finalRow, finalCol);//handleSquareSelection takes care of the 
//if a piece is selecte, it attempts to move the piece to the selected position returns true if the move 
//made is valid 
clearHighlights();
if(moveResult)
{
    refreshBoard();
    checkGameState();
    checkGameOver();
}
}

//Checking the state of the game
private void checkGameState()
{
    PieceColor currentPlayer = game.getCurrentPlayerColor();
    boolean inCheck = game.isInCheck(currentPlayer);
    if(inCheck){
        JOptionPane.showMessageDialog(this , currentPlayer + " is in check");    
    }
}

private void checkGameOver()
{
    if(game.isCheckMate(game.getCurrentPlayerColor()))
    {
        int response = JOptionPane.showConfirmDialog(
    this,
    "Checkmate! Would you like to play again?", 
    "Game Over", 
    JOptionPane.YES_NO_OPTION
        );
    if (response == JOptionPane.YES_OPTION) {
        resetGame();
    } else {
        System.exit(0);
    }
}
}

private void clearHighlights() {
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            squares[row][col].setBackground((row + col) % 2 == 0 ? Color.LIGHT_GRAY : new Color(205, 133, 63));
        }
    }
}

    public static void main(String[] args) {
        /*Purpose: Ensures that the GUI (Graphical User Interface) is created and executed on the Event
        Dispatch Thread (EDT), which is the thread responsible for handling GUI-related events in Swing. \*/
        SwingUtilities.invokeLater(ChessGameGUI::new);
    }
};
                                
