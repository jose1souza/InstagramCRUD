package view.swing.message;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.MessageController;
import model.Message;

public class MessageReceivedListView extends JDialog implements IMessageListView {
    private final MessageController controller;
    private final MessageTableModel tableModel = new MessageTableModel();
    private final JTable table = new JTable(tableModel);

    public MessageReceivedListView(JFrame parent) {
        super(parent, "Mensagens Recebidas", true);
        this.controller = new MessageController();
        this.controller.setMessageListView(this);

        setSize(650, 400);
        setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JPanel panel = new JPanel(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadMessagesReceiver();
    }

    @Override
    public void setMessageList(List<Message> messages) {
        tableModel.setMessages(messages);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    static class MessageTableModel extends AbstractTableModel {
        private final String[] columns = {"Remetente", "Conteúdo", "Data"};
        private List<Message> messages = new ArrayList<>();

        public void setMessages(List<Message> messages) {
            this.messages = messages;
            fireTableDataChanged();
        }

        public Message getMessageAt(int row) {
            return messages.get(row);
        }

        @Override public int getRowCount() { return messages.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Message m = messages.get(row);
            switch (col) {
                case 0: return m.getUserSend().getName();
                case 1: return m.getContent();
                case 2: return m.getDate();
                default: return null;
            }
        }

        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}
