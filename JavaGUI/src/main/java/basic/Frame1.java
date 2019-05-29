package basic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Frame1 {

	private JFrame frame;
	private JLabel lblMessage; // declare it here
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame1 window = new Frame1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Frame1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 452, 302);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnButton = new JButton("Refresh");
		btnButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null, "Hello Demo1 ...");
				lblMessage.setText("Hello Demo1 ...");
			}
		});
		btnButton.setForeground(Color.RED);
		btnButton.setBackground(Color.LIGHT_GRAY);
		btnButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
		btnButton.setBounds(351, 11, 73, 23);
		frame.getContentPane().add(btnButton);
		
		lblMessage = new JLabel("Message"); // remove declare here
		lblMessage.setBounds(10, 11, 124, 14);
		frame.getContentPane().add(lblMessage);
	}
}
