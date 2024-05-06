package fitness;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private List<JButton> editButtons = new ArrayList<>();
    private List<JButton> deleteButtons = new ArrayList<>();

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
        scrollPane.setBounds(10, 11, 350, 300);  
        contentPane.add(scrollPane);
        
        loadActivities(textArea);
    }

    private void loadActivities(JTextArea textArea) {
        try {
            List<String> activities = DBManager.loadActivities();
            textArea.setText("");
            clearButtons();
            int lineNumber = 1;
            for (String activity : activities) {
                textArea.append("[" + lineNumber + "] " + activity + "\n");
                addEditDeleteButtons(lineNumber, activity, textArea);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Error loading activities from file.");
        }
    }

    private void clearButtons() {
        for (JButton button : editButtons) {
            contentPane.remove(button);
        }
        for (JButton button : deleteButtons) {
            contentPane.remove(button);
        }
        editButtons.clear();
        deleteButtons.clear();
        contentPane.repaint();
    }

    private void addEditDeleteButtons(int lineNumber, String activity, JTextArea textArea) {
        JButton btnEdit = new JButton("Edit");   
        btnEdit.setBounds(370, (lineNumber - 1) * 20 + 10, 80, 20);
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String editedActivity = JOptionPane.showInputDialog("Edit Activity:", activity);
                if (editedActivity != null && !editedActivity.isEmpty()) {
                    try {
                        DBManager.updateActivity(lineNumber, editedActivity);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    loadActivities(textArea);
                }
            }
        });
        contentPane.add(btnEdit);
        editButtons.add(btnEdit);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(460, (lineNumber - 1) * 20 + 10, 80, 20);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this activity?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        DBManager.deleteActivity(lineNumber);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    loadActivities(textArea);
                }
            }
        });
        contentPane.add(btnDelete);
        deleteButtons.add(btnDelete);
    }

}

