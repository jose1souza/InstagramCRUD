package view.swing;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import controller.UserController;
import model.ModelException;
import model.User;
import model.UserGender;
import model.auth.RegisterAuthenticator;

public class RegisterView extends JDialog {
	private boolean authenticated = false;

	private final JTextField nameField = new JTextField(20);
	private final JTextField emailField = new JTextField(20);
	private final JPasswordField passwordField = new JPasswordField(20);
	private final JComboBox<UserGender> genderComboBox = new JComboBox<>(UserGender.values());

	public RegisterView() {
		setTitle("Instagram CRUD - Registro");
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		// Formulário
		JPanel form = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.anchor = GridBagConstraints.EAST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		form.add(new JLabel("Nome:"), gbc);
		gbc.gridx = 1;
		form.add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		form.add(new JLabel("Email:"), gbc);
		gbc.gridx = 1;
		form.add(emailField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		form.add(new JLabel("Sexo:"), gbc);
		gbc.gridx = 1;
		form.add(genderComboBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		form.add(new JLabel("Senha:"), gbc);
		gbc.gridx = 1;
		form.add(passwordField, gbc);

		// Botões
		JPanel buttons = new JPanel();
		JButton registerBtn = new JButton("Cadastrar");
		JButton loginBtn = new JButton("Fazer login");
		buttons.add(registerBtn);
		buttons.add(loginBtn);

		registerBtn.addActionListener(e -> {
			String nome = nameField.getText().trim();
			String email = emailField.getText().trim();
			UserGender sexo = (UserGender) genderComboBox.getSelectedItem();
			String senha = new String(passwordField.getPassword()).trim();

			if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}

			User novoUsuario = new User(0);
			novoUsuario.setName(nome);
			novoUsuario.setEmail(email);
			novoUsuario.setGender(sexo);
			novoUsuario.setPassword(senha);

			RegisterAuthenticator authenticator = new RegisterAuthenticator();
			try {
				if (!authenticator.autheticathor(novoUsuario)) {
					UserController controller = new UserController();
					controller.save(novoUsuario);
					JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
					authenticated = true;
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Email já está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} catch (ModelException ex) {
				JOptionPane.showMessageDialog(this, "Erro ao cadastrar: " + ex.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		loginBtn.addActionListener(e -> {
			dispose();
			SwingUtilities.invokeLater(() -> {
				LoginView loginView = new LoginView();
				loginView.setVisible(true);
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
