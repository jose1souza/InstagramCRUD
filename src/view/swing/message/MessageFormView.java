package view.swing.message;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.MessageController;
import model.Message;
import model.User;
import model.UserSession;

public class MessageFormView extends JDialog implements IMessageFormView{
	private final JTextArea contentArea = new JTextArea(5, 20);
    private final JComboBox<User> authorComboBox = new JComboBox<>();
    private final JButton saveButton = new JButton("Salvar");
    private final JButton closeButton = new JButton("Fechar");
    private MessageController controller;
    private final boolean isNew;
    private final MessageListView parent;
    private Message message;

    public MessageFormView(MessageListView parent, Message message, MessageController controller) {
        super(parent, true);
        this.controller = controller;
        this.controller.setMessageFormView(this);
        this.parent = parent;
        this.message = message;
        this.isNew = (message == null);

        setTitle(isNew ? "Nova Mensagem" : "Editar Mensagem");
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        	gbc.gridx = 0; gbc.gridy = 0;
            add(new JLabel("Para:"), gbc);
            gbc.gridx = 1;
            add(authorComboBox, gbc);

            List<User> users = controller.getAllUsers();
            DefaultComboBoxModel<User> comboModel = new DefaultComboBoxModel<>();
            for (User u : users) {
            	if(u.getId() != UserSession.getInstance().getUser().getId())
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

        if (!isNew) setMessageInForm(message);

        saveButton.addActionListener(e -> controller.saveOrUpdate(isNew));
        closeButton.addActionListener(e -> close());
    }

    @Override
    public Message getMessageFromForm() {
        if (message == null) message = new Message(0);
        message.setContent(contentArea.getText());
        message.setUserReceiver((User) authorComboBox.getSelectedItem());
        message.setUserSend(UserSession.getInstance().getUser());
        return message;
    }

    @Override
    public void setMessageInForm(Message message) {
        contentArea.setText(message.getContent());
        if (message.getUserReceiver() != null) {
            boolean found = false;
            for (int i = 0; i < authorComboBox.getItemCount(); i++) {
                User u = authorComboBox.getItemAt(i);
                if (u.getId() == message.getUserReceiver().getId()) {
                    authorComboBox.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                authorComboBox.addItem(message.getUserReceiver());
                authorComboBox.setSelectedItem(message.getUserReceiver());
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
