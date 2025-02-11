import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUInew
{
    private Board board;
    JFrame frame;
    JPanel buttonPanel;
    private int row1, col1;
    private int row2, col2;
    int turnNumber = 0; 
    int scoreP1 = 0;
    int scoreP2 = 0;
    boolean hasSelectedFirst = false;
    boolean hasSelectedSecond = false;
    boolean toggle = false;
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

        showBoard();
        while (!board.allTilesMatch()){
            showBoard();
            while(!hasSelectedFirst) {
                System.out.print("");
                if(toggle) {
                    hasSelectedFirst = board.isSelected(row1, col1);
                    System.out.println(hasSelectedFirst);
                    board.showValue(row1, col1);
                    toggle = false;
                }
            }
            showBoard();
            while(!hasSelectedSecond) {
                System.out.print("");
                if(toggle) {
                    hasSelectedSecond = board.isSelected(row2, col2);
                    System.out.println(hasSelectedSecond);
                    board.showValue(row2, col2);
                    toggle = false;
                }
            }
            showBoard();
            wait(2);
            String matched = board.checkForMatch(row1, col1, row2, col2);
            if (matched.equals("Matched!")) {
                if (turnNumber % 2 == 0) scoreP1 ++;
                else scoreP2 ++;
            }
            turnNumber++;
            hasSelectedFirst = false;
            hasSelectedSecond = false;
        }
    }

    public void showBoard() {
        buttonPanel.removeAll();
        Tile[][] gameboard = board.getGameboard();
        
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton sourceButton = (JButton) e.getSource();
                    if(!hasSelectedFirst) 
                    {
                        row1 = (int)sourceButton.getClientProperty("row");
                        col1 = (int)sourceButton.getClientProperty("col");
                        toggle = true;
                    } else {
                        row2 = (int)sourceButton.getClientProperty("row");
                        col2 = (int)sourceButton.getClientProperty("col");
                        toggle = true;
                    }
              }
        };
    
        for (int i = 0; i < Board.getRows(); i++) {
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
        buttonPanel.revalidate();
        buttonPanel.repaint();
    
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