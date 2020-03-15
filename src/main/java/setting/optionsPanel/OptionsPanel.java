package setting.optionsPanel;

import gui.MyGuiComps;
import locals.Themes;
import options.Options;
import options.OptionsEnum;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.RacesPanel;
import setting.TwsPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends MyGuiComps.MyPanel {

    // Variables
    BASE_CLIENT_OBJECT client;
    public static Options options;

    JComboBox comboBox;
    RacesPanel racesPanel;
    PropsPanel propsPanel;
    ExecutorsPanel executorsPanel;
    TwsPanel twsPanel;

    // Constructor
    public OptionsPanel(BASE_CLIENT_OBJECT client) {
        this.client = client;
        initialize();
        initListeners();
    }

    private void initListeners() {
        // Combo
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    switch (comboBox.getSelectedItem().toString()) {
                        case "WEEK":
                            options = client.getOptionsHandler().getOptions(OptionsEnum.WEEK);
                        case "MONTH":
                            options = client.getOptionsHandler().getOptions(OptionsEnum.MONTH);
                        case "QUARTER":
                            options = client.getOptionsHandler().getOptions(OptionsEnum.QUARTER);
                        case "MAIN":
                            options = client.getOptionsHandler().getMainOptions();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void initialize() {

        // This
        setSize(640, 150);

        TitledBorder titledBorder = BorderFactory.createTitledBorder("Options");
        titledBorder.setTitleColor(Themes.BLUE_DARK);
        setBorder(titledBorder);

        // Races
        racesPanel = new RacesPanel(client);
        racesPanel.setXY(5, 20);
        add(racesPanel);

        // Props
        propsPanel = new PropsPanel(client);
        propsPanel.setXY(racesPanel.getX() + racesPanel.getWidth() + 1, 20);
        add(propsPanel);

        // Executors
        executorsPanel = new ExecutorsPanel(client);
        executorsPanel.setXY(propsPanel.getX() + propsPanel.getWidth() + 1, propsPanel.getY());
        add(executorsPanel);

        // Tws
        twsPanel = new TwsPanel(client);
        twsPanel.setXY(executorsPanel.getX() + executorsPanel.getWidth() + 1, executorsPanel.getY());
        add(twsPanel);

        // Combo
        comboBox = new JComboBox(getOptionsArrayString());
        comboBox.setBackground(Themes.BLUE);
        comboBox.setForeground(Themes.GREY_VERY_LIGHT);
        comboBox.setBounds(twsPanel.getX() + twsPanel.getWidth() + 5, twsPanel.getY(), 120, 25);
        add(comboBox);

    }

    public String[] getOptionsArrayString() {
        String[] optionsTypes = new String[client.getOptionsHandler().getOptionsList().size()];
        int i = 0;
        for (Options options : client.getOptionsHandler().getOptionsList()) {
            optionsTypes[i] = options.getType().toString();
            i++;
        }
        return optionsTypes;
    }


}