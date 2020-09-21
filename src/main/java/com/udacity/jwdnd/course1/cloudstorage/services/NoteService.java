package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Note getNoteById(Integer noteid){
        return noteMapper.getNoteById(noteid);
    }

    public List<Note> getNotes(Integer userid ){
        return noteMapper.getNotes(userid);
    }

    public int deleteNote(Integer noteid){
        return noteMapper.delete(noteid);
    }

    public int updateNote(Note note){
        return noteMapper.update(note);
    }

    public int addNewNote(Note note){
        return noteMapper.insert(note);
    }
}
