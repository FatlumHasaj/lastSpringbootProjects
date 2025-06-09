package com.notes.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notes.app.entity.notes;
import com.notes.app.repository.noterepo;
import java.util.List;


@Service
public class noteservice {

    @Autowired
    private noterepo noterepo1;

    // Add a note
    public notes addNote(notes note) {
    	note.setCreatedAt(LocalDateTime.now());
        return noterepo1.save(note);
    }

    // Update a note
    public notes updateNote(notes note) {
        note.setUpdatedAt(LocalDateTime.now());
        return noterepo1.save(note);
    }

    // Delete a note
    public void deleteNote(Long id) {
        noterepo1.deleteById(id);
    }

    // Get a note by ID
    public notes getNoteById(Long id) {
        return noterepo1.findById(id).orElse(null);
    }

    // Get all notes
    public List<notes> getAllNotes() {
        return noterepo1.findAll();
    }

}
