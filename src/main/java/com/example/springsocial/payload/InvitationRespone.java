package com.example.springsocial.payload;

import com.example.springsocial.model.Invitation;

public class InvitationRespone {
    private boolean success;
    // @Autowired
    private int code;

    private Invitation invitation;

    public InvitationRespone(boolean success, int code, Invitation invitation) {
        this.success = success;
        this.code = code;
        this.invitation = invitation;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }
}
