/** 
 * The game that uses a n x m board of tiles or cards.
 *  
 * Player chooses two random tiles from the board. The chosen tiles
 * are temporarily shown face up. If the tiles match, they are removed from board.
 * 
 * Play continues, matching two tiles at a time, until all tles have been matched.
 * 
 * @author PLTW
 * @version 2.0
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A game class to play concentration
 */
public class GameGUI
{
  private Board board;
  JFrame frame;
  JPanel buttonPanel;
  private int row1, col1;
  private int row2, col2;
  private int row, col;
  int turnNumber = 0;
  int scoreP1 = 0;
  int scoreP2 = 0;
  public void play() {
    board = new Board();

    frame = new JFrame("2D Button Grid");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1000, 1000);
    frame.setLayout(new BorderLayout()); 

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(Board.getRows(), Board.getCols(), 10, 10)); // Add gaps for spacing

    frame.add(buttonPanel, BorderLayout.CENTER); // Make buttonPanel fill the frame
    frame.getRootPane().setDefaultButton(null);
    
    frame.setVisible(true);
    SwingUtilities.invokeLater(() -> frame.requestFocusInWindow());

    showBoard();
    while (!board.allTilesMatch())
    {
      row1 = -1;
      col1 = -1;
      boolean validTile = false;
      while (!validTile)
      {
        System.out.print("");
        if(row != -1) {
          validTile = board.validateSelection(row1, col1);

          board.showValue(row, col);
          row1 = row;
          col1 = col;
          row = -1;
          col = -1;
        }
      }

      // display first tile
      board.showValue(row1, col1);
      showBoard();

      // get player's second selection, if not an integer, quit
      row2 = -1;
      col2 = -1;
      validTile = false;
      while (!validTile)
      {
        System.out.print("");
        if(row != -1) {
          row2 = row;
          col2 = col;
          row = -1;
          col = -1;
          validTile = board.validateSelection(row2, col2);
        }
      }
      

      // display second tile
      board.showValue(row2, col2);
      showBoard();

      String matched = board.checkForMatch(row1, col1, row2, col2);
      System.out.println(matched);
      if (matched.equals("Matched!")) {
        if (turnNumber % 2 == 0) scoreP1 ++;
        else scoreP2 ++;
      }
      turnNumber++;

      wait(2); 
      
    }

    showBoard();
    System.out.println("Game Over!");
  }

  private boolean getTile(boolean firstChoice)
  {
    int num1 = 0;
    int num2 = 0;


    if (!board.validateSelection(num1, num2))
    {
      System.out.print("Invalid input, please try again. ");
      wait(2);
      return false;
    }
    if (firstChoice)   
    {
      row1 = num1;
      col1 = num2;
    }
    else 
    {
      row2 = num1;
      col2 = num2;
    }
    return true;
  }

  /**
   * Clear the console and show the game board
   */
  public void showBoard() {
    buttonPanel.removeAll();
    Tile[][] gameboard = board.getGameboard();
    
    ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
              row = (int)sourceButton.getClientProperty("row");
              col = (int)sourceButton.getClientProperty("col");
          }
    };

    for (int i = 0; i < Board.getRows(); i++) {
      buttonPanel.setLayout(new GridLayout(Board.getRows(), Board.getCols()));
        for (int j = 0; j < Board.getCols(); j++) {
            String value;
            Tile tile = gameboard[i][j];
            if (gameboard[i][j].isShowingValue()) {
                value = tile.getValue();
              }
              else {
                value = tile.getHidden();
              }
            JButton button = new JButton(value);
            button.setMinimumSize(new Dimension(300, 300));
            button.addActionListener(buttonListener);
            button.putClientProperty("tile", tile);
            button.putClientProperty("row", i);
            button.putClientProperty("col", j);
            button.setFocusable(false);
            buttonPanel.add(button);
        }
    }
    buttonPanel.validate();

  }

  private void wait(int n)
  {
    try
    {
      Thread.sleep(n * 1000);
    } catch (InterruptedException e) { /* do nothing */ }
  }

  private void quitGame() 
  {
    System.out.println("Quit game!");
    System.exit(0);
  }
}
