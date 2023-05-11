package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

// Class represents the start of the GUI of the Pokedex
public class MainFrame extends JFrame implements ActionListener {

    private JLabel wlcmLabel;
    private JLabel startLabel;
    private JSeparator horizontalSep;

    // Creates the first frame of the Application
    public MainFrame() {
        super("Pokedex");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 400));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new GridLayout(4, 0));
        JButton startBtn = new JButton("Start");
        startBtn.setActionCommand("start");
        startBtn.addActionListener(this);
        wlcmLabel = new JLabel("Welcome to the Pokedex!", SwingConstants.CENTER);
        startLabel = new JLabel("Press the Start button to Start", SwingConstants.CENTER);
        horizontalSep = new JSeparator(SwingConstants.HORIZONTAL);
        add(wlcmLabel);
        add(startLabel);
        add(startBtn);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {
            dispose();
            new PokedexMenuFrame();


        }
    }


    static class PokedexSplashScreen {

        JFrame frame;
        URL image = MainFrame.class.getClassLoader().getResource("Pokedex logo.png");
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        JProgressBar progressBar = new JProgressBar();
        JLabel loadingMessage = new JLabel();

        private PokedexSplashScreen() {

            frame = new JFrame();
            frame.getContentPane().setLayout(null);
            frame.setUndecorated(true);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(Color.red);
            frame.setVisible(true);
            imageLabel.setSize(400, 400);
            frame.add(imageLabel,CENTER_ALIGNMENT);

            addLoadingMessage();
            addProgressBar();
            progressBarLoading();
        }

        public void addProgressBar() {
            progressBar.setBounds(100,300,200,30);
            progressBar.setBorderPainted(true);
            progressBar.setStringPainted(true);
            progressBar.setBackground(Color.WHITE);
            progressBar.setForeground(Color.BLACK);
            progressBar.setValue(0);
            frame.add(progressBar);
        }

        private void progressBarLoading() {
            int i = 0;

            while (i <= 100) {
                try {
                    Thread.sleep(50);
                    progressBar.setValue(i);
                    loadingMessage.setText("LOADING... ");
                    i++;
                    if (i == 100) {
                        frame.dispose();
                        new MainFrame();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void addLoadingMessage() {
            loadingMessage.setBounds(100, 250, 200, 40);
            loadingMessage.setForeground(Color.black);
            loadingMessage.setFont(new Font("arial", Font.BOLD, 15));
            frame.add(loadingMessage);
        }
    }

    public static void main(String[] args) {
        new PokedexSplashScreen();
    }
}
