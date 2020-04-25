package com.epam.lab.repository.hibernate;

import com.epam.lab.configuration.HibernateRepositoryConfiguration;
import com.epam.lab.model.entities.Tag;
import com.epam.lab.repository.Repository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateRepositoryConfiguration.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@ActiveProfiles("hibernate")
public class TagRepositoryTest {

    private static final String NAME = "1-800-GOT-JUNK";

    @Autowired
    private Repository<Tag> tagRepository;

    @Test
    public void selectById()  {
        Tag tag = new Tag();
        tag.setName(NAME);
        tagRepository.save(tag);
        Optional<Tag> actually = tagRepository.findById(tag.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a-> Assert.assertEquals(a,tag));
    }

    @Test
    public void selectAll() {
        Tag second = new Tag();
        second.setName(NAME);

        Tag third = new Tag();
        third.setName("1980 Olympics");

        List<Tag> expected = Arrays.asList(second, third);
        tagRepository.saveAll(expected);
        List<Tag> actually = tagRepository.findAll();
        Assert.assertTrue(actually.containsAll(expected));
    }

    @Test
    public void update() {
        Tag tag = new Tag();
        tag.setName(NAME);
        tagRepository.save(tag);

        tag.setName(NAME + "test");

        tagRepository.update(tag);

        Optional<Tag> actually = tagRepository.findById(tag.getId());
        Assert.assertTrue(actually.isPresent());
        actually.ifPresent(a-> Assert.assertEquals(a,tag));
    }

}