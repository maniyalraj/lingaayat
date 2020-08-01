package india.lingayat.we.payload;

import javax.validation.constraints.NotBlank;

public class AddCreditsPayload {

    @NotBlank
    private Long userId;

    @NotBlank
    private int creditsToAdd;

    @NotBlank
    private String reference;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCreditsToAdd() {
        return creditsToAdd;
    }

    public void setCreditsToAdd(int creditsToAdd) {
        this.creditsToAdd = creditsToAdd;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
