package controller;

import java.util.List;
import model.Post;
import model.ModelException;
import model.User;
import model.data.DAOFactory;
import model.data.PostDAO;
import view.swing.post.IPostFormView;
import view.swing.post.IPostListView;

public class PostController {
    private final PostDAO postDAO = DAOFactory.createPostDAO();
    private IPostListView postListView;
    private IPostFormView postFormView;

    // Listagem
    public void loadPosts() {
        try {
            List<Post> posts = postDAO.findAll();
            postListView.setPostList(posts);
        } catch (ModelException e) {
            postListView.showMessage("Erro ao carregar posts: " + e.getMessage());
        }
    }

    // Salvar ou atualizar
    public void saveOrUpdate(boolean isNew) {
        Post post = postFormView.getPostFromForm();

        try {
            post.validate();
        } catch (IllegalArgumentException e) {
            postFormView.showErrorMessage("Erro de validação: " + e.getMessage());
            return;
        }

        try {
            if (isNew) {
                postDAO.save(post);
            } else {
                postDAO.update(post);
            }
            postFormView.showInfoMessage("Post salvo com sucesso!");
            postFormView.close();
        } catch (ModelException e) {
            postFormView.showErrorMessage("Erro ao salvar: " + e.getMessage());
        }
    }

    // Excluir
    public void excluirPost(Post post) {
        try {
            postDAO.delete(post);
            postListView.showMessage("Post excluído!");
            loadPosts();
        } catch (ModelException e) {
            postListView.showMessage("Erro ao excluir post: " + e.getMessage());
        }
    }

    public void setPostFormView(IPostFormView postFormView) {
        this.postFormView = postFormView;
    }

    public void setPostListView(IPostListView postListView) {
        this.postListView = postListView;
    }

    public List<User> getAllUsers() {
        try {
            return DAOFactory.createUserDAO().findAll();
        } catch (ModelException e) {
            postFormView.showErrorMessage("Erro ao carregar usuários: " + e.getMessage());
            return List.of();
        }
    }
}
