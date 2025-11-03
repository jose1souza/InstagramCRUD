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
import view.swing.post.PostListView;
import view.swing.user.UserListView;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;

	public MainView() {
        setTitle("Instagram CRUD - Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Usuários
        JMenu menu = new JMenu("Usuários");
        JMenuItem userListItem = new JMenuItem("Listar Usuários");
        userListItem.addActionListener(e -> new UserListView(this).setVisible(true));
        menu.add(userListItem);
        menuBar.add(menu);

        // Menu Posts
        JMenu postMenu = new JMenu("Posts");
        JMenuItem postListItem = new JMenuItem("Listar Posts");
        postListItem.addActionListener(e -> new PostListView(this).setVisible(true));
        postMenu.add(postListItem);
        menuBar.add(postMenu);
        
        // Menu de Mensagens
        JMenu messageMenu = new JMenu("Mensagens");
        JMenuItem messageListItem = new JMenuItem("Listar Mensagens");
        messageListItem.addActionListener(e -> new MessageListView(this).setVisible(true));
        messageMenu.add(messageListItem);
        menuBar.add(messageMenu);

        // Adiciona um menu vazio para empurrar o próximo menu para a direita
        menuBar.add(Box.createHorizontalGlue());

        // Menu "Sair" alinhado à direita
        JMenu menuSair = new JMenu("...");
        JMenuItem sairItem = new JMenuItem("Fechar o sistema");
        sairItem.addActionListener(e -> System.exit(0));
        menuSair.add(sairItem);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);

        JLabel label = new JLabel("Seja bem-vindo!", SwingConstants.CENTER);

        // Painel com padding
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(32, 32, 32, 32)); // padding de 32px
        contentPanel.add(label, BorderLayout.CENTER);

        setContentPane(contentPanel);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Tela de login antes da tela principal
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
