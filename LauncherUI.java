import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale.Category;

import github.Github;
import github.Repository;
import github.Commit;

public class LauncherUI {
    private JFrame frame;
    private JTextArea outputConsole;
    private JProgressBar progressBar;
    private JLabel fetchingLabel;
    private JButton fetchButton;
    private JButton updateButton;
    private JButton playButton;
    private JCheckBox debugCheckbox;
    private JComboBox<String> versionList;

    private Repository cubyz;

    public void createAndShowGUI() {
        ThemeSetter.setTheme();
        initializeGithub();
        initializeComponents();
        setupLayout();
        setupActions();
        frame.setVisible(true);
    }

    private void initializeGithub() {
        cubyz = Github.getRepo("PixelGuys", "Cubyz");
    }

    private void initializeComponents() {
        frame = new JFrame("Cubyz Launcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        String fcommits[] = null;
        try {
            fcommits = cubyz.getCommits().stream().map(Commit::getShortSha).toArray(String[]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String commits[] = new String[fcommits.length + 1];
        commits[0] = "master";
        System.arraycopy(fcommits, 0, commits, 1, fcommits.length);

        versionList = new JComboBox<>(commits);
        
        outputConsole = new JTextArea();
        outputConsole.setEditable(false);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setForeground(new ColorUIResource(UIColors.TEXT));

        fetchingLabel = new JLabel("Ready");
        fetchingLabel.setHorizontalAlignment(SwingConstants.LEFT);
        fetchingLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        fetchButton = new JButton("Fetch newest");
        updateButton = new JButton("Update/Reinstall");
        playButton = new JButton("Play");
        playButton.setBackground(new Color(64, 160, 43));
        playButton.setForeground(new Color(0, 0, 0));
        debugCheckbox = new JCheckBox("Debug");
    }

    private void setupLayout() {
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(fetchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(versionList);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        frame.add(buttonPanel, gbc);

        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topRightPanel.add(playButton);
        topRightPanel.add(debugCheckbox);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        frame.add(topRightPanel, gbc);

        JScrollPane consoleScrollPane = new JScrollPane(outputConsole);
        consoleScrollPane.setBorder(BorderFactory.createTitledBorder("Output Console"));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(consoleScrollPane, gbc);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0, 30));
        bottomPanel.add(progressBar, BorderLayout.CENTER);
        bottomPanel.add(fetchingLabel, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.weighty = 0.0;
        frame.add(bottomPanel, gbc);
    }

    private void setupActions() {
        fetchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    try {
                        String commit = (String)(versionList.getSelectedItem());
                        String url = "https://github.com/PixelGuys/Cubyz/archive/" + commit + ".zip";
                        String destDir = "./.cubyz/fetched/";
                        Downloader.downloadAndUnzip(url, destDir, outputConsole, progressBar, fetchingLabel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        outputConsole.append("Error: " + ex.getMessage() + "\n");
                    }
                }).start();
            }
        });
    }
}
