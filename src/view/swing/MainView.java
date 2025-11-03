package view.swing;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import view.swing.message.MessageListView;
import view.swing.message.MessageReceivedListView;
import view.swing.post.PostListView;
import view.swing.post.PostUserListView;
import view.swing.user.UserListView;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainView() {
		setTitle("Instagram CRUD - Swing");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();

		JMenu postMenu = new JMenu("Posts");
		
		JMenuItem postListItem = new JMenuItem("Listar Posts");
		postListItem.addActionListener(e -> new PostListView(this).setVisible(true));
		postMenu.add(postListItem);
		menuBar.add(postMenu);

		JMenuItem postUserItem = new JMenuItem("Meus Posts");
		postUserItem.addActionListener(e -> new PostUserListView(this).setVisible(true));
		postMenu.add(postUserItem);

		JMenu messageMenu = new JMenu("Mensagens");

		JMenuItem sentMessagesItem = new JMenuItem("Mensagens Enviadas");
		sentMessagesItem.addActionListener(e -> new MessageListView(this).setVisible(true));
		messageMenu.add(sentMessagesItem);

		JMenuItem receivedMessagesItem = new JMenuItem("Mensagens Recebidas");
		receivedMessagesItem.addActionListener(e -> new MessageReceivedListView(this).setVisible(true));
		messageMenu.add(receivedMessagesItem);
		menuBar.add(messageMenu);
		
		JMenu userMenu = new JMenu("Sua conta");
		
		JMenuItem userListItem = new JMenuItem("Perfil");
		userListItem.addActionListener(e -> new UserListView(this).setVisible(true));
		userMenu.add(userListItem);

		menuBar.add(Box.createHorizontalGlue());
		menuBar.add(userMenu);

		JMenu menuSair = new JMenu("...");
		JMenuItem sairItem = new JMenuItem("Fechar o sistema");
		sairItem.addActionListener(e -> System.exit(0));
		JMenuItem loginItem = new JMenuItem("Tela de login");
		loginItem.addActionListener(e -> {dispose(); new LoginView().setVisible(true);});
		menuSair.add(sairItem);
		menuBar.add(menuSair);
		menuSair.add(loginItem);

		setJMenuBar(menuBar);

		JLabel label = new JLabel("Seja bem-vindo!", SwingConstants.CENTER);

		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(32, 32, 32, 32));
		contentPanel.add(label, BorderLayout.CENTER);

		setContentPane(contentPanel);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		SwingUtilities.invokeLater(() -> {
			LoginView login = new LoginView();
			login.setVisible(true);
			if (login.isAuthenticated()) {
				MainView mainView = new MainView();
				mainView.setVisible(true);
				mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else {
				System.exit(0);
			}
		});
	}
}
