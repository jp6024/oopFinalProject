package fitness;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class AddActivityFrame extends JFrame {

    private JPanel contentPane;
    private JTextField textField; 
    private JTextField textField_1; 
    private JTextField textField_2; 
    private JTextField textField_3;
    private JComboBox comboBox; 

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddActivityFrame frame = new AddActivityFrame();
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
    public AddActivityFrame() {
        setTitle("Add an Activity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 487, 351);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        setupFormFields();
        setupLabels();
        setupComboBox();

        JButton btnSubmit = new JButton("Submit");
        btnSubmit.setBounds(182, 280, 244, 23);
        btnSubmit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveToCSV();
            }
        });
        contentPane.add(btnSubmit);
    }

    private void setupFormFields() {
        textField = new JTextField();
        textField.setBounds(182, 10, 244, 20);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(182, 56, 244, 20);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(182, 154, 244, 20);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(182, 204, 244, 48);
        contentPane.add(textField_3);
        textField_3.setColumns(10);
    }

    private void setupLabels() {
        JLabel lblActivityName = new JLabel("Activity Name");
        lblActivityName.setBounds(35, 13, 113, 14);
        contentPane.add(lblActivityName);

        JLabel lblAddCollaborator = new JLabel("Add Collaborator");
        lblAddCollaborator.setBounds(35, 59, 137, 14);
        contentPane.add(lblAddCollaborator);

        JLabel lblWorkoutQuality = new JLabel("Workout Quality");
        lblWorkoutQuality.setBounds(35, 110, 97, 14);
        contentPane.add(lblWorkoutQuality);

        JLabel lblAddTime = new JLabel("Add Time");
        lblAddTime.setBounds(35, 157, 97, 14);
        contentPane.add(lblAddTime);

        JLabel lblAddNotes = new JLabel("Add Notes");
        lblAddNotes.setBounds(35, 204, 137, 14);
        contentPane.add(lblAddNotes);
    }


    private void setupComboBox() {
        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {":)", ":/", ":("}));
        comboBox.setBounds(182, 106, 244, 22);
        contentPane.add(comboBox);
    }

    private void saveToCSV() {
        String data = String.format("%s,%s,%s,%s,%s\n",
                textField.getText(),
                textField_1.getText(),
                comboBox.getSelectedItem().toString(),
                textField_2.getText(),
                textField_3.getText());

        try (FileWriter fw = new FileWriter("activities.csv", true)) {
            fw.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

