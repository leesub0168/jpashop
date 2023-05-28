package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 상품_등록() throws Exception {
        //given
        Album album = new Album();
        album.setName("albumA");

        //when
        itemService.saveItem(album);
        Long albumId = album.getId();
        
        //then
        assertEquals(album, itemRepository.findOne(albumId));
    }

    @Test
    public void 상품_단건_조회() throws Exception {
        //given
        Book book = new Book();
        book.setName("book");

        itemService.saveItem(book);

        //when
        Item one = itemService.findOne(book.getId());

        //then
        assertEquals(one, book);
    }

    @Test
    public void 상품_전체_조회() throws Exception {
        //given
        Album album = new Album();
        album.setName("album");

        Movie movie = new Movie();
        movie.setName("movie");

        itemService.saveItem(album);
        itemService.saveItem(movie);

        //when
        List<Item> items = itemService.findItems();

        //then
        assertEquals(2, items.size());
    }
}