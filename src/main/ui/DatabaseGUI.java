package ui;

import model.Database;
import model.Pokemon;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

// Represents a class that creates a GUI for functions related to Database
public class DatabaseGUI extends JPanel implements ListSelectionListener {

    private JList databaseList;
    private DefaultListModel databaseListModel;
    private Database databaseGUI;
    private static final String JSON_STORE_DATABASE = "./data/database.json";
    private JsonWriter jsonWriterDatabase = new JsonWriter(JSON_STORE_DATABASE);

    private static final String catchString = "Catch Pokemon From Database";
    private static final String addString = "Add Pokemon";
    private static final String caughtString = "Show the Caught Pokemon";
    private JButton catchPokemonBtn;
    private JTextField pokemonNameField;
    private JButton caughtPokemonListBtn;

    private String name;
    private int id;
    private int exp;
    private int hp;
    private String[] moves;
    private String[] types;
    private int attackLevel;
    private int defenseLevel;
    private int speed;
    private String abilities;
    private String evolution;
    private boolean caught;

    // Effects: Creates a JFrame for the DatabaseGUI
    public DatabaseGUI(Database database) {
        super(new BorderLayout());
        databaseGUI = database;
        databaseListModel = new DefaultListModel();
        addPokemonToDatabaseModel();

        //Create the list and put it in a scroll pane.
        databaseList = new JList(databaseListModel);
        databaseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        databaseList.setSelectedIndex(0);
        databaseList.addListSelectionListener(this);
        databaseList.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(databaseList);

        JButton addPokemonBtn = new JButton(addString);
        AddPokemonListener addPokemonListener = new AddPokemonListener(addPokemonBtn);
        addPokemonBtn.setActionCommand(addString);
        addPokemonBtn.addActionListener(addPokemonListener);
        addPokemonBtn.setEnabled(false);

        catchPokemonBtn = new JButton(catchString);
        catchPokemonBtn.setActionCommand(catchString);
        catchPokemonBtn.addActionListener(new CatchListener());

        addCaughtPokemonBtn();

        pokemonNameField = new JTextField(10);
        pokemonNameField.addActionListener(addPokemonListener);
        pokemonNameField.getDocument().addDocumentListener(addPokemonListener);

        createDatabasePanel(listScrollPane, addPokemonBtn);
    }

    // Effects: add the caught pokeomn button to the JFrame that represents the Database GUI
    private void addCaughtPokemonBtn() {
        caughtPokemonListBtn = new JButton(caughtString);
        caughtPokemonListBtn.setActionCommand(caughtString);
        caughtPokemonListBtn.addActionListener(new CaughtPokemonListListener());
    }

    // Effects: adds pokemon from the database to the GUI
    private void addPokemonToDatabaseModel() {
        for (int i = 0; i < databaseGUI.size(); i++) {
            databaseListModel.addElement(databaseGUI.get(i).getName());

        }
    }

    // Effects: creates a JPanel with the list representation of Database with buttons at the bottom
    private void createDatabasePanel(JScrollPane listScrollPane, JButton addPokemonBtn) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(catchPokemonBtn);
        buttonPane.add(caughtPokemonListBtn);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(pokemonNameField);
        buttonPane.add(addPokemonBtn);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }


    // Catches a Pokemon when the button is pressed
    class CatchListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int index = databaseList.getSelectedIndex();
            databaseGUI.get(index).catchPokemon();


            int size = databaseListModel.getSize();

            if (size == 0) {
                catchPokemonBtn.setEnabled(false);

            } else {
                if (index == databaseListModel.getSize()) {
                    index--;
                }

                databaseList.setSelectedIndex(index);
                databaseList.ensureIndexIsVisible(index);
            }
        }
    }

    // Represents a class that displays the different pokemon that are caught when the button is pressed
    class CaughtPokemonListListener extends JPanel implements ActionListener {

        // Effects: When the button is pressed it displays the pokemon that have been caught
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowCaughtPokemon caughtPokemonPanel = new ShowCaughtPokemon();
            caughtPokemonPanel.createAndShowCaughtPokemon();


        }
    }

    // Represents a class that displays the pokemon that are caught
    class ShowCaughtPokemon extends JPanel {
        private JList caughtPokemonList;
        private DefaultListModel caughtPokemonListModel;

        // Effects: checks which pokemon are caught and adds them to the GUI
        public ShowCaughtPokemon() {
            super(new BorderLayout());
            caughtPokemonListModel = new DefaultListModel<>();
            for (int i = 0; i < databaseGUI.size(); i++) {
                if (databaseGUI.get(i).isCaught()) {
                    caughtPokemonListModel.addElement(databaseGUI.get(i).getName());
                }
            }
            caughtPokemonList = new JList(caughtPokemonListModel);
            caughtPokemonList.setVisibleRowCount(5);
            JScrollPane caughtPokemonlistScrollPane = new JScrollPane(caughtPokemonList);
            add(caughtPokemonlistScrollPane);
        }

        // Effects: creates a scroll pane GUI to represent the caught Pokemon
        public void createAndShowCaughtPokemon() {

            JFrame frame = new JFrame("Caught Pokemon");
            frame.setLocationRelativeTo(this);
            frame.setPreferredSize(new Dimension(400,150));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(true);


            JComponent newContentPane = new ShowCaughtPokemon();
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);

            frame.pack();
            frame.setVisible(true);

        }





    }



    // Represents an action listener for the add button
    class AddPokemonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // Effects: creates the button as user defined Listener
        public AddPokemonListener(JButton button) {
            this.button = button;
        }

        // Modifies: databaseGUI
        // Effects: adds a pokemon to the databaseGUI
        public void actionPerformed(ActionEvent e) {
            String name = pokemonNameField.getText();

            int index = databaseList.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }

            databaseListModel.addElement(pokemonNameField.getText());
            new AddPokemonToDatabaseFrame();



            pokemonNameField.requestFocusInWindow();
            pokemonNameField.setText("");

            databaseList.setSelectedIndex(index);
            databaseList.ensureIndexIsVisible(index);
        }



        //Required by DocumentListener.
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    // Effect: checks to see if the pokemon selected has changed
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (databaseList.getSelectedIndex() == -1) {
                catchPokemonBtn.setEnabled(false);

            } else {
                catchPokemonBtn.setEnabled(true);
            }
        }
    }



    // Effects: creates the JFrame for the databaseGUI
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Pokedex");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Do you want to save the changes "
                        + "made to the Database?", "Save",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    saveDatabase();
                    System.out.println("Saved the Database !!");
                    new PokedexMenuFrame();
                    frame.dispose();

                } else {
                    frame.dispose();
                }
            }
        });

        JComponent newContentPane = new DatabaseGUI(databaseGUI);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    // Effects: saves the database to its json file
    private void saveDatabase() {
        try {
            jsonWriterDatabase.open();
            jsonWriterDatabase.write(databaseGUI);
            jsonWriterDatabase.close();
            System.out.println("Saved the updated Pokemon Database to " + JSON_STORE_DATABASE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_DATABASE);
        }
    }

    // Represents a class that makes a form so that we can add the details of a Pokemon
    public class AddPokemonToDatabaseFrame extends JFrame implements ActionListener {

        JLabel pokemonNameLabel;
        JLabel pokemonIDLabel;
        JLabel pokemonExpLabel;
        JLabel pokemonHPLabel;
        JLabel move1Label;
        JLabel move2Label;
        JLabel move3Label;
        JLabel move4Label;
        JLabel type1Label;
        JLabel type2Label;
        JLabel attackLevelLabel;
        JLabel defenseLevelLabel;
        JLabel speedLabel;
        JLabel abilitiesLabel;
        JLabel evolutionLabel;
        JLabel caughtLabel;

        JTextField textFieldName;
        JTextField textFieldID;
        JTextField textFieldExp;
        JTextField textFieldHP;
        JTextField textFieldMove1;
        JTextField textFieldMOve2;
        JTextField textFieldMove3;
        JTextField textFieldMove4;
        JTextField textFieldType1;
        JTextField textFieldType2;
        JTextField textFieldAttack;
        JTextField textFieldDefense;
        JTextField textFieldSpeed;
        JTextField textFieldAbilities;
        JTextField textFieldEvolution;
        JTextField textFieldCaught;

        JButton addBtnTDatabase;

        // Effects: creates the GUI for the Add pokemon form
        public AddPokemonToDatabaseFrame() {
            super("Add Pokemon form");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new GridLayout(17,2));
            addBtnTDatabase = new JButton("Add Pokemon To Database");
            addBtnTDatabase.setActionCommand("add");
            addBtnTDatabase.addActionListener(this);
            initLabel();
            initTextFields();
            addLabelField1();
            add(addBtnTDatabase);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
            setResizable(false);
        }

        // Effects: adds the fields to the JFrame of the Add pokemon form
        private void addLabelField1() {
            add(pokemonNameLabel);
            add(textFieldName);
            add(pokemonIDLabel);
            add(textFieldID);
            add(pokemonExpLabel);
            add(textFieldExp);
            add(pokemonHPLabel);
            add(textFieldHP);
            add(move1Label);
            add(textFieldMove1);
            add(move2Label);
            add(textFieldMOve2);
            add(move3Label);
            add(textFieldMove3);
            add(move4Label);
            add(textFieldMove4);
            addLabelField2();
        }

        // Effects: adds the fields to the JFrame of the Add pokemon form
        private void addLabelField2() {
            add(type1Label);
            add(textFieldType1);
            add(type2Label);
            add(textFieldType2);
            add(attackLevelLabel);
            add(textFieldAttack);
            add(defenseLevelLabel);
            add(textFieldDefense);
            add(speedLabel);
            add(textFieldSpeed);
            add(abilitiesLabel);
            add(textFieldAbilities);
            add(evolutionLabel);
            add(textFieldEvolution);
            add(caughtLabel);
            add(textFieldCaught);
        }

        // Effects: Creates and assigns the labels in the JFrame that represents the Add Pokemon form
        private void initLabel() {

            pokemonNameLabel = new JLabel("Pokemon Name:");
            pokemonIDLabel = new JLabel("Pokemon ID:");
            pokemonExpLabel = new JLabel("Pokemon Exp:");
            pokemonHPLabel = new JLabel("Pokemon HP:");
            move1Label = new JLabel("Move 1:");
            move2Label = new JLabel("Move 2:");
            move3Label = new JLabel("Move 3:");
            move4Label = new JLabel("Move 4:");
            type1Label = new JLabel("Type 1:");
            type2Label = new JLabel("Type 2:");
            attackLevelLabel = new JLabel("Attack Level:");
            defenseLevelLabel = new JLabel("Defense Level:");
            speedLabel = new JLabel("Speed:");
            abilitiesLabel = new JLabel("Abilities:");
            evolutionLabel = new JLabel("Evolution:");
            caughtLabel = new JLabel("Caught (True or false):");

        }


        // Effects: Creates and assigns the text fields in the JFrame that represents the Add Pokemon form
        public void initTextFields() {
            textFieldName = new JTextField(10);
            textFieldID = new JTextField(10);
            textFieldExp = new JTextField(10);
            textFieldHP = new JTextField(10);
            textFieldMove1 = new JTextField(10);
            textFieldMOve2 = new JTextField(10);
            textFieldMove3 = new JTextField(10);
            textFieldMove4 = new JTextField(10);
            textFieldType1 = new JTextField(10);
            textFieldType2 = new JTextField(10);
            textFieldAttack = new JTextField(10);
            textFieldDefense = new JTextField(10);
            textFieldSpeed = new JTextField(10);
            textFieldAbilities = new JTextField(10);
            textFieldEvolution = new JTextField(10);
            textFieldCaught = new JTextField(10);

        }

        @Override
        // Effects: takes the information from the text fields and creates a new pokemon with that info
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("add")) {
                name = textFieldName.getText();
                id = Integer.parseInt(textFieldID.getText());
                exp = Integer.parseInt(textFieldExp.getText());
                hp = Integer.parseInt(textFieldHP.getText());
                moves = new String[] {textFieldMove1.getText(),textFieldMOve2.getText(),textFieldMove3.getText(),
                        textFieldMove4.getText()};
                types = new String[] {textFieldType1.getText(),textFieldType2.getText()};
                attackLevel = Integer.parseInt(textFieldAttack.getText());
                defenseLevel = Integer.parseInt(textFieldDefense.getText());
                speed = Integer.parseInt(textFieldSpeed.getText());
                abilities = textFieldAbilities.getText();
                evolution = textFieldEvolution.getText();
                caught = Boolean.parseBoolean(textFieldCaught.getText());
                databaseGUI.addPokemonToDatabase(new Pokemon(name,id,exp,hp,moves,types,attackLevel,
                        defenseLevel,speed,abilities, evolution, false));
                dispose();



            }
        }
    }






}


