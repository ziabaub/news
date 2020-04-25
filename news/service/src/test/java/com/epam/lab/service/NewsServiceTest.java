package com.epam.lab.service;

import com.epam.lab.exception.ServiceException;
import com.epam.lab.model.entities.Author;
import com.epam.lab.model.entities.News;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.repository.hibernate.repository.impl.AuthorRepository;
import com.epam.lab.repository.hibernate.repository.impl.NewsRepository;
import com.epam.lab.repository.hibernate.repository.impl.TagRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

public class NewsServiceTest {

    private static final String FULL_TEXT =  "Apple’s ‘Hungry Designers Wanted’ is an amazing idea. Making the the famous apple logo half eaten look like a person to represent the hungry job applicants is extremely playful. It presents a challenge to designers; Apple needs motivated individuals to work for them and they are encouraging them to send their CVs to the email provided.";
    private static final String AUTHOR_NAME = "ziad";
    private static final String SURNAME = "sarrih";
    private static final String TAGS_NAME = "1980 Olympics";
    private static final String TITLE = "Hungry Designers";
    private static final String SHORT_DATE = "Apple’s ‘Hungry Designers Wanted’ is an amazing idea.";
    private static final String CRE_DATE = "1999-01-08";
    private static final String MOD_DATE = "1999-01-28";



    private NewsService newsService;
    private NewsRepository newsRepository;
    private AuthorRepository authorRepository;
    private TagRepository tagRepository;

    @Captor
    private ArgumentCaptor<List<Tag>> tagsCaptor;

    @Captor
    private ArgumentCaptor<News> newsCaptor;

    @Captor
    private ArgumentCaptor<List<News>> newsListCaptor;

    @Captor
    private ArgumentCaptor<Author> authorCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        newsRepository = mock(NewsRepository.class);
        tagRepository = mock(TagRepository.class);
        authorRepository = mock(AuthorRepository.class);
        newsService = new NewsService(newsRepository, authorRepository, tagRepository);
    }

    @Test
    public void addItemTestShouldCheckInsertingIntoDatabase() throws ServiceException {
        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        author.setId(1);
        tag.setName(TAGS_NAME);
        tag.setId(2);
        news.setId(3);
        news.setTitle(TITLE);
        news.setShortText(SHORT_DATE);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));
        newsService.save(news);
        verify(newsRepository, times(1)).save(newsCaptor.capture());
        verify(authorRepository, times(1)).save(authorCaptor.capture());
        verify(tagRepository, times(1)).saveAll(tagsCaptor.capture());
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void saveAll() throws ServiceException {
        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        author.setId(1);
        tag.setName(TAGS_NAME);
        tag.setId(3);
        news.setId(5);
        news.setTitle(TITLE);
        news.setShortText(SHORT_DATE);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));

        Author author1 = new Author();
        News news1 = new News();
        Tag tag1 = new Tag();
        author1.setName(AUTHOR_NAME+1);
        author1.setSurname(SURNAME+1);
        author1.setId(2);
        tag1.setName(TAGS_NAME+1);
        tag1.setId(4);
        news1.setId(6);
        news1.setTitle(TITLE+1);
        news1.setShortText(SHORT_DATE+1);
        news1.setFullText(FULL_TEXT+1);
        news1.setCreationDate(CRE_DATE);
        news1.setAuthor(author1);
        news1.setModificationDate(MOD_DATE);
        news1.setTags(Collections.singletonList(tag1));

        List<News> newsLst = Arrays.asList(news1, news1);
        newsService.saveAll(newsLst);
        verify(newsRepository, times(1)).saveAll(newsListCaptor.capture());
        verify(authorRepository, times(2)).save(authorCaptor.capture());
        verify(tagRepository, times(2)).saveAll(tagsCaptor.capture());
        verifyNoMoreInteractions(newsRepository);
    }

    @Test
    public void getByIdItemsTestShouldCheckSelectingFromDatabaseById() throws ServiceException {
        Author author = new Author();
        News news = new News();
        Tag tag = new Tag();
        author.setName(AUTHOR_NAME);
        author.setSurname(SURNAME);
        author.setId(1);
        tag.setName(TAGS_NAME);
        tag.setId(2);
        news.setId(3);
        news.setTitle(TITLE);
        news.setShortText(SHORT_DATE);
        news.setFullText(FULL_TEXT);
        news.setCreationDate(CRE_DATE);
        news.setAuthor(author);
        news.setModificationDate(MOD_DATE);
        news.setTags(Collections.singletonList(tag));

        Optional<News> expected = Optional.of(news);
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));

        Optional<News> actual = newsService.getById(news.getId());

        ArgumentCaptor<Integer> integerCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(newsRepository, times(1)).findById(integerCaptor.capture());
        verifyNoMoreInteractions(newsRepository);

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void getAllItemsTestShouldCheckSelectingFromDatabase() throws ServiceException {
        List<News> news = new ArrayList<>();
        when(newsRepository.findAll()).thenReturn(news);

        List<News> actual = newsService.getAll();

        verify(newsRepository, times(1)).findAll();
        verifyNoMoreInteractions(newsRepository);

        Assert.assertEquals(actual, news);
    }

}