package HAPPYREAD.Module;

/**
 * Created by  on 2016/7/8.
 */
public class RecommendBook extends Book {

    private String Recommender; //鎺ㄨ崘浜�
    private Plan defaultPlan;  //鎺ㄨ崘鐨勯粯璁ゆ湀搴﹁鍒�

    public RecommendBook(String title) {
        super(title);
    }

    public RecommendBook(String title, String author) {
        super(title, author);
    }
}
