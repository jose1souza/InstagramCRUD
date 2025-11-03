package view.swing.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import controller.UserController;
import model.User;

public class UserListView extends JDialog implements IUserListView {
    private UserController controller;
    private final UserTableModel tableModel = new UserTableModel();
    private final JTable table = new JTable(tableModel);

    public UserListView(JFrame parent) {
        super(parent, "Usuário", true);
        this.controller = new UserController();
        this.controller.setUserListView(this);

        setSize(650, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);

        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

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
                User user = tableModel.getUserAt(row);
                UserFormView form = new UserFormView(this, user, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                User user = tableModel.getUserAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirUsuario(user);
                    System.exit(0);
                }
            }
        });

        JPanel panel = new JPanel(new BorderLayout());

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        controller.loadUsers();
    }

    @Override
    public void setUser(User user) {
        tableModel.setUser(user);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void refresh() {
        controller.loadUsers();
    }

    static class UserTableModel extends AbstractTableModel {
        private final String[] columns = {"ID", "Nome", "Sexo", "Email"};
        private User user;

        public void setUser(User user) {
            this.user = user;
            fireTableDataChanged();
        }

        public User getUserAt(int row) {
            return user;
        }

        @Override
        public int getRowCount() {
            return user == null ? 0 : 1;
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }

        @Override
        public String getColumnName(int col) {
            return columns[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (user == null || row != 0) return null;
            switch (col) {
                case 0: return user.getId();
                case 1: return user.getName();
                case 2: return user.getGender();
                case 3: return user.getEmail();
                default: return null;
            }
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }

}
