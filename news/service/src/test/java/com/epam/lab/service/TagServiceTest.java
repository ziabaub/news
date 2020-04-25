package com.epam.lab.service;

import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.repository.hibernate.repository.impl.TagRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TagServiceTest {

    private static final String NAME = "1-800-GOT-JUNK";
    private static final int ID = 1;
    private TagService tagService;
    private TagRepository tagRepository;

    @Before
    public void setUp() {
        tagRepository = mock(TagRepository.class);
        tagService = new TagService(tagRepository);
    }

    @Test
    public void addItemTestShouldCheckInsertingIntoDatabase() throws ServiceException {
        Tag tag = new Tag();
        tag.setName(NAME);
        tag.setId(ID);

        tagService.save(tag);
        ArgumentCaptor<Tag> toDoArgument = ArgumentCaptor.forClass(Tag.class);
        verify(tagRepository, times(1)).save(toDoArgument.capture());
        verifyNoMoreInteractions(tagRepository);

    }

    @Test
    public void saveAll() throws ServiceException {
        Tag first = new Tag();
        first.setName(NAME);
        first.setId(ID);

        Tag second = new Tag();
        second.setName(NAME);
        second.setId(ID);
        List<Tag> tags = Arrays.asList(first, second);

        tagService.saveAll(tags);
        verify(tagRepository, times(1)).saveAll(tags);
        verifyNoMoreInteractions(tagRepository);
    }

    @Test
    public void getByIdItemsTestShouldCheckSelectingFromDatabaseById() throws ServiceException {
        Tag expected = new Tag();
        expected.setName(NAME);
        expected.setId(ID);
        when(tagRepository.findById(ID)).thenReturn(Optional.of(expected));

        Optional<Tag> actual = tagService.getById(ID);

        verify(tagRepository, times(1)).findById(ID);
        verifyNoMoreInteractions(tagRepository);

        actual.ifPresent(a -> Assert.assertEquals(a, expected));
    }

    @Test
    public void getAllItemsTestShouldCheckSelectingFromDatabase() throws ServiceException {
        List<Tag> tags = new ArrayList<>();
        when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> actual = tagService.getAll();

        verify(tagRepository, times(1)).findAll();
        verifyNoMoreInteractions(tagRepository);

        Assert.assertEquals(actual, tags);
    }

}