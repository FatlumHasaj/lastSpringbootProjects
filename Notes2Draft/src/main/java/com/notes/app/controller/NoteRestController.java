package com.notes.app.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notes.app.entity.notes;
import com.notes.app.repository.noterepo;
import com.notes.app.service.noteservice;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/notes")
public class NoteRestController {

    @Autowired
    private noteservice noteService;

    // Get notes for the logged-in user
    @GetMapping
    public ResponseEntity<List<notes>> getNotes(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        
        System.out.println("User ID from session: " + userId);
        
        if (userId == null) {
            // Not logged in, return unauthorized or an empty list
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.emptyList());
        }
        return ResponseEntity.ok(noteService.getAllNotesByUserId(userId));
    }

    // Add a note for the logged-in user
    @PostMapping
    public ResponseEntity<notes> addNote(@RequestBody notes note, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        notes newNote = noteService.addNote(note, userId);
        return ResponseEntity.ok(newNote);
    }

    // Update a note for the logged-in user
    @PutMapping
    public ResponseEntity<notes> updateNote(@RequestBody notes note, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            notes updatedNote = noteService.updateNote(note, userId);
            return ResponseEntity.ok(updatedNote);
        } catch (RuntimeException e) {
            // Catches errors like "Note not found" or "Unauthorized"
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
    
  //  noterepo noterepo1;
    
//    @PutMapping
//    public notes updateNote(notes note, Long userId) {
//        notes existingNote = noterepo1.findById(note.getId())
//            .orElseThrow(() -> new RuntimeException("Note not found"));
//
//        if (!existingNote.getUser().getId().equals(userId)) {
//            throw new RuntimeException("Unauthorized");
//        }
//
//        existingNote.setTitle(note.getTitle());
//        existingNote.setContent(note.getContent());
//        existingNote.setUpdatedAt(LocalDateTime.now());
//
//        return noterepo1.save(existingNote);
//    }

    
    // Delete a note for the logged-in user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            noteService.deleteNote(id, userId);
            return ResponseEntity.noContent().build(); // Standard for successful DELETE
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}