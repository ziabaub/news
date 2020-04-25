package com.epam.lab.model.entities;

import java.util.List;
import java.util.Objects;

public class TagRelation {
    private int id;
    private List<Tag> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TagRelation(int newsId, List<Tag> tags) {
        setId(newsId);
        this.tags = tags;
    }
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TagRelation tagRelation = (TagRelation) o;
        return Objects.equals(tags, tagRelation.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tags);
    }

    @Override
    public String toString() {
        return "OwnedTags{" +
                super.toString() +
                "tags=" + tags +
                '}';
    }
}
