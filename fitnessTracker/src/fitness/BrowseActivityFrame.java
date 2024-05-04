package fitness;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class BrowseActivityFrame extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BrowseActivityFrame frame = new BrowseActivityFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public BrowseActivityFrame() {
        setTitle("Browse Activities");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(10, 11, 564, 300);
        contentPane.add(scrollPane);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(10, 322, 89, 23);
        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadActivities(textArea);
            }
        });
        contentPane.add(btnRefresh);

        loadActivities(textArea); 
    }

    private void loadActivities(JTextArea textArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("activities.csv"))) {
            textArea.setText(""); 
            List<String> activities = new ArrayList<>();

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                activities.add(line);
                textArea.append("[" + lineNumber + "] " + line + "\n");
                addEditDeleteButtons(lineNumber, line, textArea);
                lineNumber++;
            }

        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Error loading activities from file.");
        }
    }

    private void addEditDeleteButtons(int lineNumber, String activity, JTextArea textArea) {
        JButton btnEdit = new JButton("Edit");
        btnEdit.setBounds(450, (lineNumber - 1) * 20, 70, 20);
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String editedActivity = JOptionPane.showInputDialog("Edit Activity:", activity);
                if (editedActivity != null && !editedActivity.isEmpty()) {
                    updateActivityInFile(lineNumber, editedActivity);
                    loadActivities(textArea); // Refresh activities after edit
                }
            }
        });
        contentPane.add(btnEdit);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(530, (lineNumber - 1) * 20, 70, 20);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement delete functionality here
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this activity?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    deleteActivityFromFile(lineNumber);
                    loadActivities(textArea); // Refresh activities after delete
                }
            }
        });
        contentPane.add(btnDelete);
    }

    private void updateActivityInFile(int lineNumber, String updatedActivity) {
        try (BufferedReader reader = new BufferedReader(new FileReader("activities.csv"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("activities_temp.csv"))) {

            String line;
            int currentLine = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNumber) {
                    writer.write(updatedActivity);
                } else {
                    writer.write(line);
                }
                writer.newLine();
                currentLine++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace the original file with the updated file
        try {
            Files.move(Paths.get("activities_temp.csv"), Paths.get("activities.csv"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteActivityFromFile(int lineNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader("activities.csv"));
             BufferedWriter writer = new BufferedWriter(new FileWriter("activities_temp.csv"))) {

            String line;
            int currentLine = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLine != lineNumber) {
                    writer.write(line);
                    writer.newLine();
                }
                currentLine++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace the original file with the updated file (without the deleted line)
        try {
            Files.move(Paths.get("activities_temp.csv"), Paths.get("activities.csv"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
