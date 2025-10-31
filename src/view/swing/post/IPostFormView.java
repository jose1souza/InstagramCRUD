package view.swing.post;

import model.Post;

public interface IPostFormView {
    Post getPostFromForm();
    void setPostInForm(Post post);
    void showInfoMessage(String msg);
    void showErrorMessage(String msg);
    void close();
}

