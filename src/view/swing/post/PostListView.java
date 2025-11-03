package view.swing.post;

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

import controller.PostController;
import model.Post;
import model.UserSession;

public class PostListView extends JDialog implements IPostListView {
    private PostController controller;
    private final PostTableModel tableModel = new PostTableModel();
    private final JTable table = new JTable(tableModel);
    private final UserSession userSession;

    public PostListView(JFrame parent,UserSession userSession) {
        super(parent, "Posts", true);
        this.userSession= userSession;
        this.controller = new PostController(userSession);
        this.controller.setPostListView(this);

        setSize(650, 400);
        setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(36);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        
        controller.loadPosts();

        /*JButton addButton = new JButton("Adicionar Post");
        addButton.addActionListener(e -> {
            PostFormView form = new PostFormView(this, null, controller);
            form.setVisible(true);
        });*/

        // Menu de contexto
        JPopupMenu popupMenu = new JPopupMenu();
        /*JMenuItem editItem = new JMenuItem("Editar");
        JMenuItem deleteItem = new JMenuItem("Excluir");
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);*/

        table.addMouseListener(new MouseAdapter() {
        	
            
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

        /*editItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Post post = tableModel.getPostAt(row);
                PostFormView form = new PostFormView(this, post, controller);
                form.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Post post = tableModel.getPostAt(row);
                int confirm = JOptionPane.showConfirmDialog(this, "Excluir post?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.excluirPost(post);
                }
            }
        });*/

        JPanel panel = new JPanel(new BorderLayout());
        //panel.add(addButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        //add(panel, BorderLayout.SOUTH);

    
    }

    @Override
    public void setPostList(List<Post> posts) {
        tableModel.setPosts(posts);
    }

    @Override
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    // Atualiza lista após cadastro/edição/exclusão
    public void refresh() {
        controller.loadPosts();
    }

    // Tabela de posts
    static class PostTableModel extends AbstractTableModel {
        private final String[] columns = {"Conteúdo", "Autor"};
        private List<Post> posts = new ArrayList<>();

        public void setPosts(List<Post> posts) {
            this.posts = posts;
            fireTableDataChanged();
        }

        public Post getPostAt(int row) {
            return posts.get(row);
        }

        @Override public int getRowCount() { return posts.size(); }

        @Override public int getColumnCount() { return columns.length; }

        @Override public String getColumnName(int col) { return columns[col]; }

        @Override
        public Object getValueAt(int row, int col) {
            Post p = posts.get(row);
            switch (col) {
                case 0: return p.getContent();
                case 1: return p.getUser().getName();
                default: return null;
            }
        }
        @Override public boolean isCellEditable(int row, int col) { return false; }
    }
}
