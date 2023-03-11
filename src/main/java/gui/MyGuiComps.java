package gui;

import api.Manifest;
import dataBase.mySql.MySql;
import locals.L;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyGuiComps {

    public static class Windows {

        public static ArrayList<MyGuiComps.MyFrame> frames = new ArrayList<>();

        public static void add_frame(MyFrame frame) {
            frames.add(frame);
            System.out.println(frames);
        }

        public static void remove_frame(MyFrame frame) {
            frames.remove(frame);
            System.out.println(frames);
        }
    }



    // ---------- JFrame ---------- //
    public static abstract class MyFrame extends JFrame {

        protected BASE_CLIENT_OBJECT client;

        public MyFrame(String title) throws HeadlessException {
            super(title);
            init();
            initialize();
            initListeners();
            packAndFinish();
            initOnClose();
            Windows.add_frame(this);
        }

        public MyFrame(String title, BASE_CLIENT_OBJECT client) throws HeadlessException {
            super(title);
            this.client = client;
            init();
            initialize();
            packAndFinish();
            initOnClose();
            Windows.add_frame(this);
        }

        private void packAndFinish() {
            pack();
            if (Manifest.DB) {
                loadBounds();
            }
            setVisible(true);
        }

        private void init() {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setBackground(Themes.GREY_LIGHT);
            getContentPane().setLayout(null);
            setLayout(null);
        }

        protected void loadBounds() {
            new Thread(() -> {
                try {
                    int width = 300, height = 300, x = 100, y = 100;

                    String query = String.format("SELECT * FROM sagiv.bounds WHERE stock_name = '%s' and item_name = '%s';", client.getId_name(), getTitle());
                    ResultSet rs = MySql.select(query);

                    while (rs.next()) {
                        x = rs.getInt("x");
                        y = rs.getInt("y");
                        width = rs.getInt("width");
                        height = rs.getInt("height");
                    }

                    setPreferredSize(new Dimension(width, height));
                    setBounds(x, y, width, height);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        public void insetOrUpdateBounds() {
            try {
                String query = String.format("SELECT sagiv.update_bounds('%s', '%s', %s, %s, %s, %s);", client.getId_name(), getTitle(), getX(), getY(), getWidth(), getHeight());
                MySql.select(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public void set_start_bounds(int pre_set) {
            try {
                String query = String.format("SELECT sagiv.update_start_bounds('%s', '%s', %s, %s, %s, %s, %s);", client.getId_name(), getTitle(), getX(), getY(), getWidth(), getHeight(), pre_set);
                System.out.println(query);
                MySql.select(query);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void load_start_bounds(int pre_set) {
            new Thread(() -> {
                try {
                    int width = 300, height = 300, x = 100, y = 100;

                    String query = String.format("SELECT * FROM sagiv.start_bounds WHERE stock_name = '%s' and item_name = '%s' and pre_set = %s;", client.getId_name(), getTitle(), pre_set);
                    ResultSet rs = MySql.select(query);

                    while (rs.next()) {
                        x = rs.getInt("x");
                        y = rs.getInt("y");
                        width = rs.getInt("width");
                        height = rs.getInt("height");
                    }

                    setPreferredSize(new Dimension(width, height));
                    setBounds(x, y, width, height);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        private void initOnClose() {
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    super.windowClosed(e);
                    onClose();
                }
            });
        }

        public void onClose() {
            if (Manifest.DB) {
                insetOrUpdateBounds();
            }
            Windows.remove_frame(this);
            dispose();

        }

        public void setXY(int x, int y) {
            setBounds(x, y, getWidth(), getHeight());
        }

        public void setWidth(int width) {
            setBounds(getX(), getY(), width, getHeight());
        }

        public void setHeight(int height) {
            setBounds(getX(), getY(), getWidth(), height);
        }

        public void setSize(int width, int height) {
            setPreferredSize(new Dimension(width, height));
        }

        public abstract void initListeners();

        public abstract void initialize();
    }

    // ---------- JPanel ---------- //
    public static class MyPanel extends JPanel {

        public MyPanel() {
            init();
        }

        protected void init() {
            setFont(Themes.VEDANA_12);
            setBackground(Themes.GREY_LIGHT);
            setBorder(null);
            setLayout(null);
        }

        public void setXY(int x, int y) {
            setBounds(x, y, getWidth(), getHeight());
        }

        public void setWidth(int width) {
            setBounds(getX(), getY(), width, getHeight());
        }

        public void setHeight(int height) {
            setBounds(getX(), getY(), getWidth(), height);
        }
    }

    // ---------- JTextField ---------- //
    public static class MyTextField extends JTextField {

        public MyTextField() {
            super();
            init();
        }

        public MyTextField(int columns) {
            super(columns);
            init();
        }

        public void setWidth(int width) {
            setBounds(getX(), getY(), width, getHeight());
        }

        public void setHeight(int height) {
            setBounds(getX(), getY(), getWidth(), height);
        }

        private void init() {
            setBounds(new Rectangle(65, 25));
            setFont(Themes.VEDANA_12);
            setForeground(Color.BLACK);
            setHorizontalAlignment(JTextField.CENTER);
            setBackground(Themes.GREY_VERY_LIGHT);
            setBorder(null);
//            setEnabled(false);
        }

        public void color_on_change(double val) {
            new Thread(() -> {
                try {

                    Color color;

                    if (val > L.dbl(getText())) {
                        color = Themes.OPEN_RACE;
                    } else if (val < L.dbl(getText())) {
                        color = Themes.BINANCE_RED;
                    } else {
                        color = getBackground();
                    }

                    // Get original color
                    Color original_color = getBackground();
                    setBackground(color);

                    // sleep
                    Thread.sleep(1000);

                    // Set original again
                    setBackground(original_color);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }

        public void setFontSize(int size) {
            setFont(getFont().deriveFont((float) size));
        }

        public void setXY(int x, int y) {
            setBounds(x, y, getWidth(), getHeight());
        }

        public void setText(double val, DecimalFormat format) {
            try {
                if (format != null) {
                    setText(format.format(val));
                } else {
                    setText(L.str(val));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void colorForge(double val, DecimalFormat format) {
            try {
                if (val >= 0) {
                    setForeground(Themes.GREEN);
                } else {
                    setForeground(Themes.RED);
                }

                setText(format.format(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void colorForge(double val, DecimalFormat format, String sign) {
            if (val >= 0) {
                setForeground(Themes.GREEN);
            } else {
                setForeground(Themes.RED);
            }

            setText(format.format(val) + sign);
        }

        public void make_bold(double val_to_bold) {
            double val = L.abs(L.dbl(getText()));
            if (val > val_to_bold) {
                setFont(getFont().deriveFont(Font.BOLD));
            } else {
                setFont(getFont().deriveFont(Font.PLAIN));
            }
        }

        public void colorForge(int val) {
            try {
                if (val >= 0) {
                    setForeground(Themes.GREEN);
                } else {
                    setForeground(Themes.RED);
                }
                setText(L.str(val));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void colorBack(double val, DecimalFormat format) {

            setForeground(Color.WHITE);
            setFont(getFont().deriveFont(Font.BOLD));

            if (val >= 0) {
                setBackground(Themes.GREEN);
            } else {
                setBackground(Themes.RED);
            }

            setText(format.format(val));
        }

        public void colorBack(double val, DecimalFormat format, String sign) {
            try {
                setForeground(Color.WHITE);
                setFont(getFont().deriveFont(Font.BOLD));

                if (val >= 0) {
                    setBackground(Themes.GREEN);
                } else {
                    setBackground(Themes.RED);
                }

                setText(format.format(val) + sign);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    // ---------- JLabel ---------- //
    public static class MyLabel extends JLabel {

        boolean bold = false;

        public MyLabel(String text) {
            super(text);
            init();
        }

        public MyLabel(String text, boolean bold) {
            super(text);
            this.bold = bold;
            init();
        }

        private void init() {

            setBounds(new Rectangle(60, 25));

            setForeground(Themes.BLUE);
            setHorizontalAlignment(JLabel.CENTER);
            setVerticalAlignment(JLabel.CENTER);

            if (bold) {
                setFont(Themes.VEDANA_12.deriveFont(Font.BOLD));
            } else {
                setFont(Themes.VEDANA_12);
            }

        }

        public void setWidth(int width) {
            setBounds(getX(), getY(), width, getHeight());
        }

        public void setHeight(int height) {
            setBounds(getX(), getY(), getWidth(), height);
        }

        public void setXY(int x, int y) {
            setBounds(x, y, getWidth(), getHeight());
        }

        public void setText(double val, DecimalFormat format) {
            if (format != null) {
                setText(format.format(val));
            } else {
                setText(L.str(val));
            }
        }

        public void colorForge(double val, DecimalFormat format) {
            if (val >= 0) {
                setForeground(Themes.GREEN);
            } else {
                setForeground(Themes.RED);
            }

            setText(format.format(val));
        }

        public void colorForge(int val) {
            if (val >= 0) {
                setForeground(Themes.GREEN);
            } else {
                setForeground(Themes.RED);
            }

            setText(L.coma(val));
        }


        public void colorForge(int val, Color green) {
            if (val >= 0) {
                setForeground(green);
            } else {
                setForeground(Themes.RED);
            }

            setText(L.coma(val));
        }

        public void colorBack(double val, DecimalFormat format) {
            if (val >= 0) {
                setBackground(Themes.GREEN);
            } else {
                setBackground(Themes.RED);
            }

            setText(format.format(val));
        }
    }

    // ---------- JButton ---------- //
    public static class MyButton extends JButton {

        public MyButton(String text) {
            super(text);

            init();
        }

        private void init() {
            setWidth(80);
            setHeight(25);
            setFont(Themes.VEDANA_12);
            setForeground(Themes.BLUE);
            setHorizontalAlignment(JLabel.CENTER);
            setBackground(Themes.GREY_LIGHT);
            setOpaque(true);
            addMouseListener(MyListeners.onOverMyButton(this));
        }

        public void complete() {
            Color backGroundColor = getBackground();
            setBackground(Themes.GREEN);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setBackground(backGroundColor);

        }

        public void setWidth(int width) {
            setBounds(getX(), getY(), width, getHeight());
        }

        public void setHeight(int height) {
            setBounds(getX(), getY(), getWidth(), height);
        }

        public void setSize(int width, int height) {
            setPreferredSize(new Dimension(width, height));
        }

        public void setXY(int x, int y) {
            setBounds(x, y, getWidth(), getHeight());
        }
    }


    // ---------- GridPanel ---------- //
    public static class MyBoardPanel extends JPanel {

        public Field[][] fields;

        int rows, cols;
        Dimension panelMinDimension, fieldsMinDimension;

        public MyBoardPanel(int rows, int cols, Dimension panelMinDimension, Dimension fieldsMinDimension) {
            this.rows = rows;
            this.cols = cols;
            fields = new Field[rows][cols];
            this.panelMinDimension = panelMinDimension;
            this.fieldsMinDimension = fieldsMinDimension;

            setLayout(new GridLayout(rows, cols));
            setMinimumSize(new Dimension(panelMinDimension));
            setPreferredSize(new Dimension(panelMinDimension));
            setBackground(Themes.GREY_LIGHT);
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

        class Field extends JPanel {

            public Field(Dimension minDimension) {
                setMinimumSize(minDimension);
                setPreferredSize(minDimension);
            }

        }
    }

    public static void color_on_complete(JButton button) {
        Color original = button.getBackground();
        Color complete = Themes.BLUE_LIGHT_2;

        new Thread(() -> {
            try {
                button.setBackground(complete);
                Thread.sleep(1000);
                button.setBackground(original);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}


abstract class AFrame extends JFrame {

    public AFrame(String title) throws HeadlessException {
        super(title);
    }

    public abstract void onClose();
}