package com.fabianospdev.android.colabora_la.model;

public class Feed {
    private int id = 0;
    private String title = "";
    private String picture = "";
    private String message = "";
    private String createdAt = "";
    private String updatedAt = "";

    private int idCom;
    private String nameCom;
    private String commentCom;
    private String createdAtCom;
    private String updatedAtCom;

    public Feed ( ) {
    }

    public Feed ( int id , String title , String picture , String message , String createdAt , String updatedAt ) {
        this.id = id;
        this.title = title;
        this.picture = picture;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId ( ) {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getTitle ( ) {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getPicture ( ) {
        return picture;
    }

    public void setPicture ( String picture ) {
        this.picture = picture;
    }

    public String getMessage ( ) {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public String getCreatedAt ( ) {
        return createdAt;
    }

    public void setCreatedAt ( String createdAt ) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt ( ) {
        return updatedAt;
    }

    public void setUpdatedAt ( String updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public int getIdCom ( ) {
        return idCom;
    }

    public void setIdCom ( int idCom ) {
        this.idCom = idCom;
    }

    public String getNameCom ( ) {
        return nameCom;
    }

    public void setNameCom ( String nameCom ) {
        this.nameCom = nameCom;
    }

    public String getCommentCom ( ) {
        return commentCom;
    }

    public void setCommentCom ( String commentCom ) {
        this.commentCom = commentCom;
    }

    public String getCreatedAtCom ( ) {
        return createdAtCom;
    }

    public void setCreatedAtCom ( String createdAtCom ) {
        this.createdAtCom = createdAtCom;
    }

    public String getUpdatedAtCom ( ) {
        return updatedAtCom;
    }

    public void setUpdatedAtCom ( String updatedAtCom ) {
        this.updatedAtCom = updatedAtCom;
    }
}