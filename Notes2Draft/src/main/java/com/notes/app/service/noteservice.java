package com.notes.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- IMPORT THIS

import com.notes.app.entity.notes;
import com.notes.app.entity.user;
import com.notes.app.repository.noterepo;
import com.notes.app.repository.usersrepo;
import java.util.List;

@Service
public class noteservice {

    @Autowired
    private noterepo noterepo1;

    @Autowired
    private usersrepo userRepo;

    // Use readOnly for fetching data, it's more efficient
    @Transactional(readOnly = true)
    public List<notes> getAllNotesByUserId(Long userId) {
        return noterepo1.findByusers_IdOrderByUpdatedAtDesc(userId);
    }

    // Add a note for a specific user
    @Transactional // <-- THIS IS THE CRITICAL FIX
    public notes addNote(notes note, Long userId) {
        user currentUser = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        note.setUser(currentUser);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noterepo1.save(note);
    }

    // Update a note, ensuring it belongs to the user
    @Transactional // <-- ADD THIS HERE AS WELL
    public notes updateNote(notes updatedNote, Long userId) {
        notes existingNote = noterepo1.findById(updatedNote.getId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check: ensure the note belongs to the logged-in user
        if (!existingNote.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only edit your own notes.");
        }

        existingNote.setTitle(updatedNote.getTitle());
        existingNote.setContent(updatedNote.getContent());
        existingNote.setUpdatedAt(LocalDateTime.now());
        return noterepo1.save(existingNote);
    }

    // Delete a note, ensuring it belongs to the user
    @Transactional // <-- AND HERE FOR CONSISTENCY
    public void deleteNote(Long noteId, Long userId) {
        notes noteToDelete = noterepo1.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Security check
        if (!noteToDelete.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own notes.");
        }
        
        noterepo1.deleteById(noteId);
    }
}