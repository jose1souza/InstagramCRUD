package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.User;
import model.UserSession;
import model.auth.PasswordHash;
import model.data.DAOFactory;
import model.data.UserDAO;

public class LoginView extends JDialog {
	private boolean authenticated = false;
	private final JTextField emailField = new JTextField(20);
	private final JPasswordField passwordField = new JPasswordField(20);

	public LoginView() {
		setTitle("Instagram CRUD - Login");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel form = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		form.add(new JLabel("Email:"), gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		form.add(emailField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		form.add(new JLabel("Senha:"), gbc);
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		form.add(passwordField, gbc);

		JPanel buttons = new JPanel();
		JButton loginBtn = new JButton("Entrar");
		JButton cancelBtn = new JButton("Cancelar");
		JButton registerBtn = new JButton("Registrar");
		buttons.add(loginBtn);
		buttons.add(cancelBtn);
		buttons.add(registerBtn);

		loginBtn.addActionListener(e -> {
			String email = emailField.getText().trim();
			String password = new String(passwordField.getPassword()).trim();

			try {
				UserDAO userDAO = DAOFactory.createUserDAO();
				User user = userDAO.findByEmail(email);

				if (user == null) {
					JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (PasswordHash.checkPassword(password, user.getPasswordHash())) {
					authenticated = true;
					UserSession.create(user);
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Erro ao autenticar: " + ex.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				ex.printStackTrace();
			}
		});

		cancelBtn.addActionListener(e -> {
			authenticated = false;
			dispose();
		});

		registerBtn.addActionListener(e -> {
			SwingUtilities.invokeLater(() -> {
				RegisterView registerView = new RegisterView();
				registerView.setVisible(true);
			});
		});

		add(form, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(null);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

}
