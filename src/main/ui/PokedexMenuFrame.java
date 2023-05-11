package ui;

import exceptions.LogException;
import model.Database;
import model.Event;
import model.EventLog;
import model.Roster;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class PokedexMenuFrame extends JFrame implements ActionListener,LogPrinter {

    private JButton databaseBtn;
    private JButton rosterBtn;
    private JLabel questionTxt;
    private Roster roster;
    private Database database;
    private JsonWriter jsonWriterDatabase;
    private JsonWriter jsonWriterRoster;
    private JsonReader jsonReaderDatabase;
    private JsonReader jsonReaderRoster;
    private static final String JSON_STORE_ROSTER = "./data/roster.json";
    private static final String JSON_STORE_DATABASE = "./data/database.json";
    private EventLog eventLog;

    public PokedexMenuFrame() {
        super("Pokedex");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(500, 500));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new GridLayout(3,0));
        addDatabaseBtn();
        addRosterBtn();
        questionTxt = new JLabel("What do you want to do ?",SwingConstants.CENTER);
        add(questionTxt, CENTER_ALIGNMENT);
        add(databaseBtn);
        add(rosterBtn);

        addPrintLogWindowListener();
        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
        setResizable(false);

        initData();

    }

    private void addPrintLogWindowListener() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EventLog el = EventLog.getInstance();
                try {
                    printLog(el);
                    dispose();
                } catch (LogException ex) {
                    System.out.println("Could not print the log");
                    dispose();
                }
            }
        });
    }

    private void addRosterBtn() {
        rosterBtn = new JButton("Information About Roster");
        rosterBtn.setActionCommand("roster");
        rosterBtn.addActionListener(this);
    }

    private void addDatabaseBtn() {
        databaseBtn = new JButton("Information About Database");
        databaseBtn.setActionCommand("database");
        databaseBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("roster")) {
            int response = JOptionPane.showConfirmDialog(this,"Do you want to load the previous User Roster?",
                    "Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                RosterGUI panel = new RosterGUI(loadRoster());
                panel.createAndShowGUI();

            } else {
                dispose();
                new RosterGUI(new Roster());
            }
        } else {
            int response = JOptionPane.showConfirmDialog(this,"Do you want to load the previous Database?",
                    "Confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                dispose();
                DatabaseGUI panel = new DatabaseGUI(loadDatabase());
                panel.createAndShowGUI();
            } else {
                dispose();
                new DatabaseGUI(new Database());
            }

        }


    }

    private void initData() {
        roster = new Roster();
        database = new Database();
        jsonWriterDatabase = new JsonWriter(JSON_STORE_DATABASE);
        jsonWriterRoster = new JsonWriter(JSON_STORE_ROSTER);
        jsonReaderDatabase = new JsonReader(JSON_STORE_DATABASE);
        jsonReaderRoster = new JsonReader(JSON_STORE_ROSTER);
    }

    private Roster loadRoster() {
        try {
            roster = (Roster) jsonReaderRoster.read();
            System.out.println("Loaded user roster data from " + JSON_STORE_ROSTER);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_ROSTER);
        }
        return roster;
    }


    private Database loadDatabase() {
        try {
            database = (Database) jsonReaderDatabase.read();
            System.out.println("Loaded Pokedex database data from " + JSON_STORE_DATABASE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_DATABASE);
        }
        return database;
    }

    @Override
    public void printLog(EventLog el) throws LogException {
        for (Event next : el) {
            System.out.println(next);
        }


    }
}
