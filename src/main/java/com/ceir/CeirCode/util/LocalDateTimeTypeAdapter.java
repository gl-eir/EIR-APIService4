package com.ceir.CeirCode.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    private final DateTimeFormatter formatter;

    public LocalDateTimeTypeAdapter(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(formatter.format(value));
        }
    }

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.hasNext()) {
            String dateStr = in.nextString();
            return LocalDateTime.parse(dateStr, formatter);
        }
        return null;
    }
}
