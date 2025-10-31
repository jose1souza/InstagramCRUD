package view.swing.post;

import java.util.List;
import model.Post;

public interface IPostListView {
    void setPostList(List<Post> posts);
    void showMessage(String msg);
}

