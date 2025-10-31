package view.swing.post;

import controller.PostController;
import model.Post;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PostFormView extends JDialog implements IPostFormView {
    private final JTextArea contentArea = new JTextArea(5, 20);
    private final JComboBox<User> authorComboBox = new JComboBox<>();
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    private PostController controller;
    private final boolean isNew;
    private final PostListView parent;
    private Post post;

    public PostFormView(PostListView parent, Post post, PostController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setPostFormView(this);

        this.parent = parent;
        this.post = post;
        this.isNew = (post == null);

        setTitle(isNew ? "Novo Post" : "Editar Post");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adiciona ComboBox para autor
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        add(authorComboBox, gbc);

        // Preenche ComboBox com usuários
        List<User> users = controller.getAllUsers();
        DefaultComboBoxModel<User> comboModel = new DefaultComboBoxModel<>();
        for (User u : users) {
            comboModel.addElement(u);
        }
        authorComboBox.setModel(comboModel);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Conteúdo:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(contentArea), gbc);

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton);
        btnPanel.add(closeButton);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(btnPanel, gbc);

        if (!isNew) setPostInForm(post);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Post getPostFromForm() {
        if (post == null) post = new Post(0);
        post.setContent(contentArea.getText());
        // Atualiza o usuário do post sempre que o formulário é enviado
        post.setUser((User) authorComboBox.getSelectedItem());
        return post;
    }

    @Override
    public void setPostInForm(Post post) {
        contentArea.setText(post.getContent());
        // Atualiza o combobox para refletir o usuário do post
        if (post.getUser() != null) {
            boolean found = false;
            for (int i = 0; i < authorComboBox.getItemCount(); i++) {
                User u = authorComboBox.getItemAt(i);
                if (u.getId() == post.getUser().getId()) {
                    authorComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            // Se não encontrar, adiciona o usuário ao combobox e seleciona
            if (!found) {
                authorComboBox.addItem(post.getUser());
                authorComboBox.setSelectedItem(post.getUser());
            }
        }
    }

    @Override
    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void close() {
        parent.refresh();
        dispose();
    }
}
