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

import model.User;
import model.UserSession;
import view.swing.message.MessageListView;
import view.swing.message.MessageReceivedListView;
import view.swing.post.PostListView;
import view.swing.post.PostUserListView;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    private final UserSession userSession;

	public MainView(UserSession userSession) {
		this.userSession = userSession;
        setTitle("Instagram CRUD - Swing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        // Menu Posts
        JMenu postMenu = new JMenu("Posts");
        // item: Todos os posts
        JMenuItem postListItem = new JMenuItem("Listar Posts");
        postListItem.addActionListener(e -> new PostListView(this,userSession).setVisible(true));
        postMenu.add(postListItem);
        menuBar.add(postMenu);
        
        // Item: Posts do usuário logado
        JMenuItem postUserItem = new JMenuItem("Meus Posts");
        postUserItem.addActionListener(e -> new PostUserListView(this, userSession).setVisible(true));
        postMenu.add(postUserItem);
        
        // Menu de Mensagens
        JMenu messageMenu = new JMenu("Mensagens");

        // Item: Mensagens Enviadas
        JMenuItem sentMessagesItem = new JMenuItem("Mensagens Enviadas");
        sentMessagesItem.addActionListener(e -> new MessageListView(this, userSession).setVisible(true));
        messageMenu.add(sentMessagesItem);

        // Item: Mensagens Recebidas
        JMenuItem receivedMessagesItem = new JMenuItem("Mensagens Recebidas");
        receivedMessagesItem.addActionListener(e -> new MessageReceivedListView(this, userSession).setVisible(true));
        messageMenu.add(receivedMessagesItem);

        // Adiciona o menu ao menuBar
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
            	User userLogged = login.getUserAuthenticated();
            	UserSession userSession = new UserSession(userLogged);
                MainView mainView = new MainView(userSession);
                mainView.setVisible(true);
                mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                System.exit(0);
            }
        });
    }
}
