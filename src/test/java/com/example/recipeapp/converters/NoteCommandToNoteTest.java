package com.example.recipeapp.converters;

import com.example.recipeapp.commands.NoteCommand;
import com.example.recipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandToNoteTest {

    private static final Long ID_VALUE = 1L;
    private static final String NOTES = "notes";

    NoteCommandToNote converter;

    @BeforeEach
    void setUp() {
        converter = new NoteCommandToNote();
    }

    @Test
    void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(converter.convert(new NoteCommand()));
    }

    @Test
    void convert() {
        //given
        NoteCommand noteCommand = new NoteCommand();
        noteCommand.setId(ID_VALUE);
        noteCommand.setNotes(NOTES);
        
        //when
        Note note = converter.convert(noteCommand);

        //then
        assertNotNull(note);
        assertEquals(ID_VALUE, note.getId());
        assertEquals(NOTES, note.getNotes());
    }
}