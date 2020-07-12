package india.lingayat.we.payload;

import india.lingayat.we.models.SafeUserDetails;

import java.util.Set;

public class SafeUserPage {

    private long totalElements;
    private Set<SafeUserDetails> content;

    public SafeUserPage(long totalElements, Set<SafeUserDetails> content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public Set<SafeUserDetails> getContent() {
        return content;
    }

    public void setContent(Set<SafeUserDetails> content) {
        this.content = content;
    }
}
