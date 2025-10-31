package view.swing.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.MessageController;
import model.Message;
import model.Post;

public class MessageListView extends JDialog implements IMessageListView {
	private MessageController controller;
	private final MessageTableModel tableModel = new MessageTableModel();
	private final JTable table = new JTable(tableModel);

	public MessageListView(JFrame parent) {
		super(parent, "Mensagens", true);
		this.controller = new MessageController();
		this.controller.setMessageListView(this);

		setSize(650, 400);
		setLocationRelativeTo(null);

		JScrollPane scrollPane = new JScrollPane(table);

		table.setRowHeight(36);
		table.setShowGrid(true);
		table.setGridColor(Color.LIGHT_GRAY);

		JButton addButton = new JButton("Adicionar Mensagem");
		addButton.addActionListener(e -> {
			MessageFormView form = new MessageFormView(this, null, controller);
			form.setVisible(true);
		});
		
		// Menu de contexto
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Message message = tableModel.getMessageAt(row);
                MessageFormView form = new MessageFormView(this, message, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Message message = tableModel.getMessageAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir mensagem?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirMessage(message);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadMessages();
	}

	@Override
	public void setMessageList(List<Message> messages) {
		tableModel.setPosts(messages);
	}

	@Override
	public void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	// Atualiza lista após cadastro/edição/exclusão
    public void refresh() {
        controller.loadMessages();
    }
    
 // Tabela de posts
    static class MessageTableModel extends AbstractTableModel {
        private final String[] columns = {"Conteúdo", "Autor"};
        private List<Message> messages = new ArrayList<>();

        public void setPosts(List<Message> messages) {
            this.messages = messages;
            fireTableDataChanged();
        }

        public Message getMessageAt(int row) {
			// TODO Auto-generated method stub
			return null;
		}

		public Message getPostAt(int row) {
            return messages.get(row);
        }

        @Override public int getRowCount() { return messages.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Message m = messages.get(row);
            switch (col) {
                case 0: return m.getContent();
                case 1: return m.getUserReceinder().getName();
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }

}
