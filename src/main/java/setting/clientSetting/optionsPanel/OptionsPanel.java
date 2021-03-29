package setting.clientSetting.optionsPanel;

import exp.Exp;
import exp.ExpStrings;
import gui.MyGuiComps;
import locals.Themes;
import serverObjects.BASE_CLIENT_OBJECT;
import setting.clientSetting.RacesPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OptionsPanel extends MyGuiComps.MyPanel {

    public static Exp exp;
    // Variables
    BASE_CLIENT_OBJECT client;
    JComboBox comboBox;
    RacesPanel racesPanel;
    PropsPanel propsPanel;
    ExecutorsPanel executorsPanel;

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
                        case "week":
                            exp = client.getExps().getExp(ExpStrings.week);
                            break;
                        case "month":
                            exp = client.getExps().getExp(ExpStrings.month);
                            break;
                        case "e1":
                            exp = client.getExps().getExp(ExpStrings.q1);
                            break;
                        case "e2":
                            exp = client.getExps().getExp(ExpStrings.q2);
                            break;
                        case "MAIN":
                            exp = client.getExps().getMainExp();
                            break;
                        default:
                            break;
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

        // Combo options
        comboBox = new JComboBox(getExpsNames());
        comboBox.setBounds(executorsPanel.getX() + executorsPanel.getWidth() + 5, executorsPanel.getY(), 60, 25);
        add(comboBox);
    }

    private String[] getExpsNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Exp exp : client.getExps().getExpList()) {
            names.add(exp.getName());
        }
        return names.toArray(String[]::new);
    }


}