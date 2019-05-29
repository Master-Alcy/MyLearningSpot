package basic;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Frame2 {

	private JFrame frame;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textFieldAns;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame2 window = new Frame2();
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
	public Frame2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField1 = new JTextField();
		textField1.setBounds(10, 11, 200, 26);
		frame.getContentPane().add(textField1);
		textField1.setColumns(10);
		
		textField2 = new JTextField();
		textField2.setBounds(224, 11, 200, 26);
		frame.getContentPane().add(textField2);
		textField2.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num1, num2, ans;		
				try {
					num1 = Integer.parseInt(textField1.getText());
					num2 = Integer.parseInt(textField2.getText());	
					ans = num1 + num2;
					textFieldAns.setText(Integer.toString(ans));
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Number: " + ex);
				}
			}
		});
		btnNewButton.setBounds(10, 48, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Minus");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num1, num2, ans;		
				try {
					num1 = Integer.parseInt(textField1.getText());
					num2 = Integer.parseInt(textField2.getText());	
					ans = num1 - num2;
					textFieldAns.setText(Integer.toString(ans));
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Number: " + ex);
				}
			}
		});
		btnNewButton_1.setBounds(335, 48, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		textFieldAns = new JTextField();
		textFieldAns.setBounds(177, 91, 174, 26);
		frame.getContentPane().add(textFieldAns);
		textFieldAns.setColumns(10);
		
		JLabel lblAnswer = new JLabel("Answer:");
		lblAnswer.setBounds(113, 93, 54, 23);
		frame.getContentPane().add(lblAnswer);
	}
}
