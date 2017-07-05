package ryanc.cc.mynote.bean;

import java.io.Serializable;

/**
 * Created by RYAN0UP on 2017/7/3.
 * 便签信息实体类
 */

public class NoteBean implements Serializable {
    private String noteTitle;
    private String noteDate;
    private String noteContent;

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }
}
