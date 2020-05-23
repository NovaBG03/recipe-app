package com.example.recipeapp.converters;

import com.example.recipeapp.commands.NoteCommand;
import com.example.recipeapp.domain.Note;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {

    @Synchronized
    @Nullable
    @Override
    public Note convert(NoteCommand noteCommand) {

        if (noteCommand == null) {
            return null;
        }

        Note note = new Note();
        note.setId(noteCommand.getId());
        note.setNotes(noteCommand.getNotes());

        return note;
    }
}
