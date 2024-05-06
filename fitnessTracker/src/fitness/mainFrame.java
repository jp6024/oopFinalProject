package fitness;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class mainFrame extends JFrame {

	private JPanel contentPane;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public mainFrame() {
		setTitle("FITNESS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 513, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		addActivity(panel);

        browseActivity(panel);
    }
	
	public void addActivity(JPanel panel) {
		JButton btnAddActivity = new JButton("Add Activity");
        btnAddActivity.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddActivityFrame addActivityFrame = new AddActivityFrame();
                addActivityFrame.setVisible(true);
            }
        });
        panel.add(btnAddActivity);
	}
	
	public void browseActivity(JPanel panel) {
		JButton btnBrowseActivities = new JButton("Browse Activities");
        btnBrowseActivities.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 BrowseActivityFrame browseActivityFrame = new BrowseActivityFrame();
                 browseActivityFrame.setVisible(true);
            }
        });
        panel.add(btnBrowseActivities);
	}
}
