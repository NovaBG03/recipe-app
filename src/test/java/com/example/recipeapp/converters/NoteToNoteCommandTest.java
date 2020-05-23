package com.example.recipeapp.converters;

import com.example.recipeapp.commands.NoteCommand;
import com.example.recipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class NoteToNoteCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String NOTES = "notes";

    NoteToNoteCommand converter;

    @BeforeEach
    void setUp() {
        converter = new NoteToNoteCommand();
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new Note()));
    }

    @Test
    void convert() {
        //given
        Note note = new Note();
        note.setId(ID_VALUE);
        note.setNotes(NOTES);

        //when
        NoteCommand noteCommand = converter.convert(note);

        //then
        assertNotNull(noteCommand);
        assertEquals(ID_VALUE, noteCommand.getId());
        assertEquals(NOTES, noteCommand.getNotes());
    }
}