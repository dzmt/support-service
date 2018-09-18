package by.smirnov.common.domain;

import by.smirnov.common.enumeration.Type;

import java.io.Serializable;

public class Message implements Serializable{
    private Type type;
    private String text;

    public Message(Type type, String text) {
        this.type = type;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (type != message.type) return false;
        return text.equals(message.text);
    }

}
