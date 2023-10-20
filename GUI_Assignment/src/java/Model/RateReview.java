package Model;

/**
 * @author LOH XIN JIE
 */
import DataAccess.DBModel;
import java.util.*;

public class RateReview extends DBModel {

    private int reviewId;
    private String reviewText;
    private int reviewRating;
    private Date reviewDate;
    private Product product;
    private Member member;
    private Orders orders;

    public RateReview(int reviewId, String reviewText, int reviewRating, Date reviewDate, Product product, Member member, Orders orders) {
        super("ratereview");
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.product = product;
        this.member = member;
        this.orders = orders;
    }

    public RateReview(String reviewText, int reviewRating, Date reviewDate, Product product, Member member, Orders orders) {
        super("ratereview");
        this.reviewText = reviewText;
        this.reviewRating = reviewRating;
        this.reviewDate = reviewDate;
        this.product = product;
        this.member = member;
        this.orders = orders;
    }

    public RateReview(int reviewId) {
        super("ratereview");
        this.reviewId = reviewId;
    }

    public RateReview() {
        super("ratereview");
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public Product getProduct() {
        return product;
    }

    public Member getMember() {
        return member;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }
}
