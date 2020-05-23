package com.example.recipeapp.converters;

import com.example.recipeapp.commands.NoteCommand;
import com.example.recipeapp.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {

    @Synchronized
    @Nullable
    @Override
    public NoteCommand convert(Note note) {

        if (note == null) {
            return null;
        }

        NoteCommand noteCommand = new NoteCommand();
        noteCommand.setId(note.getId());
        noteCommand.setNotes(note.getNotes());

        return noteCommand;
    }
}
