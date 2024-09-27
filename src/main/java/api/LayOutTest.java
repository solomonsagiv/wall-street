package api;

import gui.MyGuiComps;
import locals.Themes;

import javax.swing.*;
import java.awt.*;

public class LayOutTest extends JFrame {

    public MyBoardPanel board;

    private LayOutTest() {

        Dimension panel = new Dimension(700, 25);
        Dimension field = new Dimension(50, 25);
        board = new MyBoardPanel(1, 15, panel, field);
        add(board);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                MyGuiComps.MyLabel myLabel = new MyGuiComps.MyLabel("Sagiv");

                LayOutTest layOutTest = new LayOutTest();
                layOutTest.board.setLabel(myLabel, 0, 12);
            }
        });
    }

    class MyBoardPanel extends JPanel {

        public Field[][] fields;

        int rows, cols;
        Dimension panelMinDimension, fieldsMinDimension;

        public MyBoardPanel(int rows, int cols, Dimension panelMinDimension, Dimension fieldsMinDimension) {
            this.rows = rows;
            this.cols = cols;
            this.panelMinDimension = panelMinDimension;
            this.fieldsMinDimension = fieldsMinDimension;

            fields = new Field[rows][cols];
            setLayout(new GridLayout(rows, cols));
            setMinimumSize(new Dimension(panelMinDimension));
            setPreferredSize(new Dimension(panelMinDimension));
            fillBoard();
        }

        private void fillBoard() {
            for (int i = 0; i < rows; ++i) {
                for (int j = 0; j < cols; ++j) {
                    fields[i][j] = new Field(fieldsMinDimension);
                    add(fields[i][j]);
                }
            }
        }

        public void setLabel(JLabel label, int row, int col) {
            fields[row][col].add(label);
        }
    }

    class Field extends JPanel {

        public Field(Dimension minDimension) {
            setBackground(Themes.getRamdomColor());
            setMinimumSize(minDimension);
            setPreferredSize(minDimension);
        }

    }
}
