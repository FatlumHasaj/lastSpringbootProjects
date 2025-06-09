document.addEventListener('DOMContentLoaded', () => {
	const noteForm = document.getElementById('note-form');
	const noteTitleInput = document.getElementById('note-title');
	const noteContentInput = document.getElementById('note-content');
	const notesGrid = document.getElementById('notes-grid');
	const noteIdInput = document.getElementById('note-id');
	const addUpdateButton = document.getElementById('add-update-button');
	const cancelEditButton = document.getElementById('cancel-edit-button');

	let notes = [];
	let editingNoteId = null;

	// --- Helper Functions ---
	function formatTimestamp(dateString) {
		const date = new Date(dateString);
		const now = new Date();
		const seconds = Math.round((now - date) / 1000);
		const minutes = Math.round(seconds / 60);
		const hours = Math.round(minutes / 60);
		const days = Math.round(hours / 24);
		const weeks = Math.round(days / 7);

		if (seconds < 60) return `${seconds} seconds ago`;
		if (minutes < 60) return `${minutes} minutes ago`;
		if (hours < 24) return `${hours} hours ago`;
		if (days < 7) return `${days} days ago`;
		if (weeks < 52) return `${weeks} weeks ago`;
		return date.toLocaleDateString();
	}

	function escapeHTML(str) {
		const div = document.createElement('div');
		div.appendChild(document.createTextNode(str));
		return div.innerHTML;
	}

	// --- Fetch Notes from Backend ---
	async function fetchNotes() {
		try {
			const res = await fetch('/api/notes');
			if (!res.ok) throw new Error('Failed to fetch notes');
			notes = await res.json();
			renderNotes();
		} catch (err) {
			alert('Error loading notes: ' + err.message);
		}
	}

	async function createNote(title, content) {
		const noteData = {
			title: title,
			content: content
		};
		try {
			const res = await fetch('/api/notes', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(noteData)
			});
			if (!res.ok) throw new Error('Failed to create note');
			const newNote = await res.json();
			notes.push(newNote);
			renderNotes();
		} catch (err) {
			alert('Error adding note: ' + err.message);
		}
	}

	async function updateNote(id, title, content) {
		const updatedNote = { id, title, content };
		try {
			const res = await fetch('/api/notes', {
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify(updatedNote)
			});
			if (!res.ok) throw new Error('Failed to update note');
			const noteIndex = notes.findIndex(note => note.id === id);
			if (noteIndex > -1) {
				notes[noteIndex] = await res.json();
				renderNotes();
			}
		} catch (err) {
			alert('Error updating note: ' + err.message);
		}
	}

	async function deleteNote(id) {
		try {
			const res = await fetch(`/api/notes/${id}`, {
				method: 'DELETE'
			});
			if (!res.ok) throw new Error('Failed to delete note');
			notes = notes.filter(note => note.id !== id);
			renderNotes();
			if (editingNoteId === id) resetForm();
		} catch (err) {
			alert('Error deleting note: ' + err.message);
		}
	}

	// --- UI Functions ---
	function renderNotes() {
		notesGrid.innerHTML = '';
		if (notes.length === 0) {
			notesGrid.innerHTML = '<p style="color: var(--text-light);">No notes yet. Add one above!</p>';
			return;
		}

		notes.sort((a, b) => new Date(b.timestamp) - new Date(a.timestamp));

		notes.forEach(note => {
			const noteCard = document.createElement('div');
			noteCard.classList.add('note-card');
			noteCard.dataset.id = note.id;

			noteCard.innerHTML = `
                <h3>${escapeHTML(note.title)}</h3>
                <p>${escapeHTML(note.content)}</p>
                <div class="note-meta">
				<small>
				  <span>🟢 Created: ${formatTimestamp(note.createdAt)}</span><br>
				  <span>📝 Updated: ${formatTimestamp(note.updatedAt)}</span>
				</small>
                    <div class="note-actions">
                        <button class="edit-btn" title="Edit Note"><i class="fas fa-edit"></i></button>
                        <button class="delete-btn" title="Delete Note"><i class="fas fa-trash-alt"></i></button>
                    </div>
                </div>
            `;

			noteCard.querySelector('.edit-btn').addEventListener('click', () => handleEditNote(note.id));
			noteCard.querySelector('.delete-btn').addEventListener('click', () => handleDeleteNote(note.id));

			notesGrid.appendChild(noteCard);
		});
	}

	// --- Event Handlers ---
	noteForm.addEventListener('submit', (e) => {
		e.preventDefault();
		const title = noteTitleInput.value.trim();
		const content = noteContentInput.value.trim();

		if (!title || !content) {
			alert('Please fill in both title and content.');
			return;
		}

		if (editingNoteId) {
			updateNote(editingNoteId, title, content);
		} else {
			createNote(title, content);
		}
		resetForm();
	});

	function handleEditNote(id) {
		const note = notes.find(note => note.id === id);
		if (note) {
			editingNoteId = id;
			noteIdInput.value = id;
			noteTitleInput.value = note.title;
			noteContentInput.value = note.content;
			addUpdateButton.textContent = 'Update Note';
			cancelEditButton.classList.remove('hidden');
			noteTitleInput.focus();
		}
	}

	function handleDeleteNote(id) {
		if (confirm('Are you sure you want to delete this note?')) {
			deleteNote(id);
		}
	}

	cancelEditButton.addEventListener('click', () => {
		resetForm();
	});

	function resetForm() {
		noteForm.reset();
		editingNoteId = null;
		noteIdInput.value = '';
		addUpdateButton.textContent = 'Add Note';
		cancelEditButton.classList.add('hidden');
	}

	// --- Initial Load ---
	fetchNotes();
});
