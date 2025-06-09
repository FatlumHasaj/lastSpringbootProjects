package com.notes.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.app.entity.notes;
import com.notes.app.service.noteservice;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    @Autowired
    private noteservice noteService;

    @GetMapping
    public List<notes> getNotes() {
        return noteService.getAllNotes();
    }

    @PostMapping
    public notes addNote(@RequestBody notes note) {
        return noteService.addNote(note);
    }

    @PutMapping
    public notes updateNote(@RequestBody notes note) {
        return noteService.updateNote(note);
    }

    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }
}

