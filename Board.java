/** 
 * A game board of NxM board of tiles.
 * 
 *  @author PLTW
 * @version 2.0
 */

/** 
 * A Board class for concentration
 */
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
public class Board
{  
  private static String[] tileValues = {"lion", "lion",
                                        "penguin", "penguin",
                                        "dolphin", "dolphin",
                                        "fox", "fox",
                                        "monkey", "monkey",
                                        "turtle", "turtle",
                                        "Aster", "Aster",
                                        "Aarav", "Aarav",
                                        "wolf", "wolf",
                                        "tiger", "tiger",
                                        "donkey", "donkey",
                                        "rhino", "rhino",
                                        "chicken", "chicken",
                                        "dog", "dog",
                                        "cow", "cow"                                      
                                      }; 
  private static final int rows = 6;
  private static final int cols = 5;
  public static int getRows() {
    return rows;
  }

  public static int getCols() {
    return cols;
  }

  private Tile[][] gameboard = new Tile[rows][cols];

  public Tile[][] getGameboard() {
    return gameboard;
  }

  /**  
   * Constructor for the game. Creates the 2D gameboard
   * by populating it with card values
   * 
   */
  public Board()
  {
    int counter = 0;
    List<String> cool = Arrays.asList(tileValues);
    Collections.shuffle(cool);
    cool.toArray(tileValues);
    /* your code here */ 
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        gameboard[i][j] = new Tile(tileValues[counter]);
        counter++;
      }
    }

  }

 /** 
   * Returns a string representation of the board, getting the state of
   * each tile. If the tile is showing, displays its value, 
   * otherwise displays it as hidden.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return a string represetation of the board
   */
  public String toString()
  {
    String r = "";
    /* your code here */
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (gameboard[i][j].isShowingValue()) {
          r += gameboard[i][j].getValue();
        }
        else {
          r += gameboard[i][j].getHidden();
        }
        r+=" ";
      }
      r+="\n";
    }
    return r;
  }

  /** 
   * Determines if the board is full of tiles that have all been matched,
   * indicating the game is over.
   * 
   * Precondition: gameboard is populated with tiles
   * 
   * @return true if all tiles have been matched, false otherwse
   */
  public boolean allTilesMatch()
  {

    /* your code  here */
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if(!gameboard[i][j].matched()) return false;
      }
    }
    
    return true;
  }

  /** 
   * Sets the tile to show its value (like a playing card face up)
   * 
   * Preconditions:
   *   gameboard is populated with tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row the row value of Tile
   * @param column the column value of Tile
   */
  public void showValue (int row, int column)
  {
   
    /* your code here */
    Tile t = gameboard[row][column];
    t.show();
  }  

  /** 
   * Checks if the Tiles in the two locations match.
   * 
   * If Tiles match, show Tiles in matched state and return a "matched" message
   * If Tiles do not match, re-hide Tiles (turn face down).
   * 
   * Preconditions:
   *   gameboard is populated with Tiles,
   *   row values must be in the range of 0 to gameboard.length,
   *   column values must be in the range of 0 to gameboard[0].length
   * 
   * @param row1 the row value of Tile 1
   * @param col1 the column value of Tile 1
   * @param row2 the row value of Tile 2
   * @param col2 the column vlue of Tile 2
   * @return a message indicating whether or not a match occured
   */
  public String checkForMatch(int row1, int col1, int row2, int col2)
  {
    String msg = "";

     /* your code here */
     Tile tile1 = gameboard[row1][col1];
     Tile tile2 = gameboard[row2][col2];
     if (tile1.equals(tile2)) {
      tile1.foundMatch();
      tile2.foundMatch();
      msg = "Matched!";
     } else {
      tile1.hide();
      tile2.hide();
     }
    
     return msg;
  }

  /** 
   * Checks the provided values fall within the range of the gameboard's dimension
   * and that the tile has not been previously matched
   * 
   * @param rpw the row value of Tile
   * @param col the column value of Tile
   * @return true if row and col fall on the board and the row,col tile is unmatched, false otherwise
   */
  public boolean validateSelection(int row, int col)
  {

    /* your code here */
    if (row > rows - 1 || row < 0) {
      return false;
    }
    if (col > cols - 1 || col < 0) {
      return false;
    }
    if(gameboard[row][col].matched()) {
      return false;
    }
    return true;
  }
  public boolean isSelected(int row, int col) {
    if(gameboard[row][col].matched()) {
      return false;
    }
    if (gameboard[row][col].isShowingValue()) {
      return false;
    }
    return true;
  }

}
