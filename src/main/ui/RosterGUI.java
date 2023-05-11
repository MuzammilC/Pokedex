package ui;

import exceptions.InvalidPokemon;
import exceptions.NotCaught;
import model.Database;
import model.Pokemon;
import model.Roster;
import persistence.JsonReader;
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
import java.io.IOException;

// Represents a class that creates a GUI for functions related to Roster
public class RosterGUI extends JPanel implements ListSelectionListener {

    private JList rosterList;
    private DefaultListModel rosterListModel;
    private Database database;
    private Roster rosterGUI;
    private static final String JSON_STORE_DATABASE = "./data/database.json";
    private static final String JSON_STORE_ROSTER = "./data/roster.json";
    private JsonReader jsonReaderDatabase = new JsonReader(JSON_STORE_DATABASE);
    private JsonWriter jsonWriterRoster = new JsonWriter(JSON_STORE_ROSTER);

    private static final String removeString = "Remove Pokemon From roster";
    private static final String addString = "Add Pokemon";
    private static final String caughtString = "Show the Caught Pokemon";
    private static final String infoString = "Info Pokemon";
    private JButton removePokemonBtn;
    private JButton caughtPokemonBtn;
    private JButton infoPokemonBtn;
    private JTextField pokemonNameField;

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

    // Effects: Creates a JFrame for the RosterGUI
    public RosterGUI(Roster roster) {
        super(new BorderLayout());
        rosterGUI = roster;
        rosterListModel = new DefaultListModel();
        database = new Database();
        database = loadDatabase();
        addPokemonToRosterModel();

        //Create the list and put it in a scroll pane.
        rosterList = new JList(rosterListModel);
        rosterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rosterList.setSelectedIndex(0);
        rosterList.addListSelectionListener(this);
        rosterList.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(rosterList);

        JButton addPokemonBtn = new JButton(addString);
        AddPokemonListener addPokemonListener = new AddPokemonListener(addPokemonBtn);
        addPokemonBtn.setActionCommand(addString);
        addPokemonBtn.addActionListener(addPokemonListener);
        addPokemonBtn.setEnabled(false);

        addCaughtPokemonBtn();
        addInfoPokemonBtn();
        addRemovePokemonBtn();
        addPokemonNameTextField(addPokemonListener);
        createRosterPanel(listScrollPane, addPokemonBtn);
    }

    private void addPokemonNameTextField(AddPokemonListener addPokemonListener) {
        pokemonNameField = new JTextField(10);
        pokemonNameField.addActionListener(addPokemonListener);
        pokemonNameField.getDocument().addDocumentListener(addPokemonListener);
    }

    private void addInfoPokemonBtn() {
        infoPokemonBtn = new JButton(infoString);
        infoPokemonBtn.setActionCommand(infoString);
        infoPokemonBtn.addActionListener(new InfoListener());
    }

    private void addRemovePokemonBtn() {
        removePokemonBtn = new JButton(removeString);
        removePokemonBtn.setActionCommand(removeString);
        removePokemonBtn.addActionListener(new RemoveListener());
    }

    // Effects: add the caught pokeomn button to the JFrame that represents the Roster GUI
    private void addCaughtPokemonBtn() {
        caughtPokemonBtn = new JButton(caughtString);
        caughtPokemonBtn.setActionCommand(caughtString);
        caughtPokemonBtn.addActionListener(new CaughtPokemonListListener());
    }

    // Modifies: rosterListModel
    // Effects: Adds pokemon from the json roster file to the roster model
    private void addPokemonToRosterModel() {
        for (int i = 0; i < rosterGUI.size(); i++) {
            rosterListModel.addElement(rosterGUI.get(i).getName());

        }
    }

    //  Modifies: this
    // Effects: creates a JPanel with the list representation of Database with buttons at the bottom
    private void createRosterPanel(JScrollPane listScrollPane, JButton addPokemonBtn) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removePokemonBtn);
        buttonPane.add(caughtPokemonBtn);
        buttonPane.add(infoPokemonBtn);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(pokemonNameField);
        buttonPane.add(addPokemonBtn);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // Represents a class that displays the Information of the selected Pokemon
    class InfoListener extends JFrame implements ActionListener {

        JLabel pokemonNameLabel;
        JLabel pokemonIDLabel;
        JLabel pokemonExpLabel;
        JLabel pokemonHPLabel;
        JLabel movesLabel;
        JLabel typesLabel;
        JLabel attackLevelLabel;
        JLabel defenseLevelLabel;
        JLabel speedLabel;
        JLabel abilitiesLabel;
        JLabel evolutionLabel;
        JLabel caughtLabel;

        JLabel pokemonActualNameLabel;
        JLabel pokemonActualIDLabel;
        JLabel pokemonActualExpLabel;
        JLabel pokemonActualHPLabel;
        JLabel movesActualLabel;
        JLabel typesActualLabel;
        JLabel attackLevelActualLabel;
        JLabel defenseLevelActualLabel;
        JLabel speedActualLabel;
        JLabel abilitiesActualLabel;
        JLabel evolutionActualLabel;
        JLabel caughtActualLabel;


        // Modifies: rosterGUI, rosterListModel
        // Effects: removes the selected pokemon from the roster and the GUI representation of roster
        public void actionPerformed(ActionEvent e) {
            int index = rosterList.getSelectedIndex();
            Pokemon p1 = rosterGUI.get(index);
            createAndShowInfoGUI(p1);


            int size = rosterListModel.getSize();

            if (size == 0) {
                infoPokemonBtn.setEnabled(false);

            } else {
                if (index == rosterListModel.getSize()) {
                    index--;
                }

                rosterList.setSelectedIndex(index);
                rosterList.ensureIndexIsVisible(index);
            }
        }


        private void createAndShowInfoGUI(Pokemon pokemon) {
            setTitle("Pokemon Info");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setPreferredSize(new Dimension(400, 400));
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
            setLayout(new GridLayout(17,2));
            initLabel();
            initActualLabel(pokemon);
            addLabelField1();
            pack();
            setLocationRelativeTo(getParent());
            setVisible(true);
            setResizable(true);
        }

        // Effects: adds the fields to the JFrame of the Add pokemon form
        private void addLabelField1() {
            add(pokemonNameLabel);
            add(pokemonActualNameLabel);
            add(pokemonIDLabel);
            add(pokemonActualIDLabel);
            add(pokemonExpLabel);
            add(pokemonActualExpLabel);
            add(pokemonHPLabel);
            add(pokemonActualHPLabel);
            add(movesLabel);
            add(movesActualLabel);
            add(typesLabel);
            add(typesActualLabel);
            addLabelField2();
        }

        // Effects: adds the fields to the JFrame of the Add pokemon form
        private void addLabelField2() {
            add(attackLevelLabel);
            add(attackLevelActualLabel);
            add(defenseLevelLabel);
            add(defenseLevelActualLabel);
            add(speedLabel);
            add(speedActualLabel);
            add(abilitiesLabel);
            add(abilitiesActualLabel);
            add(evolutionLabel);
            add(evolutionActualLabel);
            add(caughtLabel);
            add(caughtActualLabel);
        }

        // Effects: Creates and assigns the labels in the JFrame that represents the Add Pokemon form
        private void initLabel() {
            pokemonNameLabel = new JLabel("Pokemon Name:");
            pokemonIDLabel = new JLabel("Pokemon ID:");
            pokemonExpLabel = new JLabel("Pokemon Exp:");
            pokemonHPLabel = new JLabel("Pokemon HP:");
            movesLabel = new JLabel("Moves: ");
            typesLabel = new JLabel("Types:");
            attackLevelLabel = new JLabel("Attack Level:");
            defenseLevelLabel = new JLabel("Defense Level:");
            speedLabel = new JLabel("Speed:");
            abilitiesLabel = new JLabel("Abilities:");
            evolutionLabel = new JLabel("Evolution:");
            caughtLabel = new JLabel("Caught (True or false):");
        }

        // Effects: Creates and assigns the text fields in the JFrame that represents the Add Pokemon form
        private void initActualLabel(Pokemon p1) {
            pokemonActualNameLabel = new JLabel(p1.getName());
            pokemonActualIDLabel = new JLabel(String.valueOf(p1.getId()));
            pokemonActualExpLabel = new JLabel(String.valueOf(p1.getExp()));
            pokemonActualHPLabel = new JLabel(String.valueOf(p1.getHp()));
            movesActualLabel = new JLabel(p1.printMoves());
            typesActualLabel = new JLabel(p1.printTypes());
            attackLevelActualLabel = new JLabel(String.valueOf(p1.getAttackLevel()));
            defenseLevelActualLabel = new JLabel(String.valueOf(p1.getDefenseLevel()));
            speedActualLabel = new JLabel(String.valueOf(p1.getSpeed()));
            abilitiesActualLabel = new JLabel(p1.getAbilities());
            evolutionActualLabel = new JLabel(p1.getEvolution());
            caughtActualLabel = new JLabel(String.valueOf(p1.isCaught()));
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
            for (int i = 0; i < database.size(); i++) {
                if (database.get(i).isCaught()) {
                    caughtPokemonListModel.addElement(database.get(i).getName());
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
            frame.setLocationRelativeTo(getParent());
            frame.setPreferredSize(new Dimension(400, 150));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(true);


            JComponent newContentPane = new ShowCaughtPokemon();
            newContentPane.setOpaque(true);
            frame.setContentPane(newContentPane);

            frame.pack();
            frame.setVisible(true);

        }
    }



    // Represents a class that wait for the remove button to be clicked to remove a pokemon from the roster
    class RemoveListener implements ActionListener {

        // Modifies: rosterGUI, rosterListModel
        // Effects: removes the selected pokemon from the roster and the GUI representation of roster
        public void actionPerformed(ActionEvent e) {
            int index = rosterList.getSelectedIndex();
            Pokemon p = rosterGUI.get(index);
            try {
                rosterGUI.removePokemonFromRoster(p);
            } catch (NotCaught ex) {
                System.out.println("Not caught the pokemon");
            }
            rosterListModel.remove(index);

            int size = rosterListModel.getSize();

            if (size == 0) {
                removePokemonBtn.setEnabled(false);

            } else {
                if (index == rosterListModel.getSize()) {
                    index--;
                }

                rosterList.setSelectedIndex(index);
                rosterList.ensureIndexIsVisible(index);
            }
        }
    }

    // Represents a class that adds a pokemon to the roster that when the add button is clicked
    class AddPokemonListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        // Effects: assigns the button to the add listener
        public AddPokemonListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = pokemonNameField.getText();

            int index = rosterList.getSelectedIndex();
            if (index == -1) {
                index = 0;
            } else {
                index++;
            }
            String pokemonName = pokemonNameField.getText();
            try {
                Pokemon p1 = database.getPokemon(pokemonName);
                rosterGUI.addPokemonToRoster(p1);
                rosterListModel.addElement(pokemonNameField.getText());

            } catch (InvalidPokemon ex) {
                System.out.println("Invalid name of Pokemon!!");
            } catch (NotCaught ex) {
                System.out.println("Pokemon not caught!!");
            }

            pokemonNameField.requestFocusInWindow();
            pokemonNameField.setText("");

            rosterList.setSelectedIndex(index);
            rosterList.ensureIndexIsVisible(index);
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

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {
            if (rosterList.getSelectedIndex() == -1) {
                removePokemonBtn.setEnabled(false);
            } else {
                removePokemonBtn.setEnabled(true);
            }
        }
    }



    // MOdifies: database
    // Effects: loads the json database file to the database
    private Database loadDatabase() {
        try {
            database = (Database) jsonReaderDatabase.read();
            System.out.println("Loaded Pokedex database data from " + JSON_STORE_DATABASE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_DATABASE);
        }
        return database;
    }

    // Modifies: this
    // Effects: creates and display the GUI for the roster
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Pokedex");
        frame.setLocationRelativeTo(getParent());
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(null,"Do you want to save the changes made to the roster?",
                        "Save",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    saveRoster();
                    new PokedexMenuFrame();
                    frame.dispose();

                } else {
                    frame.dispose();
                    System.out.println("Closed Application without saving Roster");

                }
            }
        });

        JComponent newContentPane = new RosterGUI(rosterGUI);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    // Effects: saves the rosterGUI to roster json file
    private void saveRoster() {
        try {
            jsonWriterRoster.open();
            jsonWriterRoster.write(rosterGUI);
            jsonWriterRoster.close();
            System.out.println("Saved the user Pokemon Roster to " + JSON_STORE_ROSTER);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE_ROSTER);
        }
    }


}
