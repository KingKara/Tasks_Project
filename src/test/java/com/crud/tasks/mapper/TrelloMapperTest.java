package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {
@Autowired
private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoard() {
        //Given
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("test", "testId", new ArrayList<>());
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("test2", "testId2", new ArrayList<>());
        List<TrelloBoardDto> trelloBoard = new ArrayList<>();
        trelloBoard.add(trelloBoardDto1);
        trelloBoard.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoard(trelloBoard);

        //Then
        Assert.assertEquals(2, trelloBoards.size());
        Assert.assertEquals("test", trelloBoards.get(0).getName());
        Assert.assertEquals("test2", trelloBoards.get(1).getName());
    }

    @Test
    public void shouldMapToBoardDto() {
        //Given
        TrelloBoard trelloBoard1 = new TrelloBoard("testId", "test", new ArrayList<>());
        TrelloBoard trelloBoard2 = new TrelloBoard("testId2", "test2", new ArrayList<>());
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardDto(trelloBoards);

        //Then
        Assert.assertEquals(2, trelloBoardDtos.size());
        Assert.assertEquals("test", trelloBoardDtos.get(0).getName());
        Assert.assertEquals("test2", trelloBoardDtos.get(1).getName());
    }

    @Test
    public void shouldMapToList() {
        //Given
        TrelloListDto trelloListDto1 = new TrelloListDto("testId", "test", true);
        TrelloListDto trelloListDto2 = new TrelloListDto("testId2", "test2", false);
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(trelloListDto1);
        trelloLists.add(trelloListDto2);

        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloLists);

        //Then
        Assert.assertEquals(2, trelloList.size());
        Assert.assertEquals("test", trelloList.get(0).getName());
        Assert.assertTrue(trelloList.get(0).isClosed());
        Assert.assertEquals("test2", trelloList.get(1).getName());
        Assert.assertFalse(trelloList.get(1).isClosed());
    }

    @Test
    public void shouldMapToListDto() {
        //Given
        TrelloList trelloList1 = new TrelloList("testId", "test", true);
        TrelloList trelloList2 = new TrelloList("testId2", "test2", false);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);

        //When
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloLists);

        //Then
        Assert.assertEquals(2, trelloListDtos.size());
        Assert.assertEquals("test", trelloListDtos.get(0).getName());
        Assert.assertTrue(trelloListDtos.get(0).isClosed());
        Assert.assertEquals("test2", trelloListDtos.get(1).getName());
        Assert.assertFalse(trelloListDtos.get(1).isClosed());
    }

    @Test
    public void shouldMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("testName", "testDesc", "testPos", "testListId");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        Assert.assertEquals("testName", trelloCardDto.getName());
        Assert.assertEquals("testDesc", trelloCardDto.getDescription());
        Assert.assertEquals("testPos", trelloCardDto.getPos());
        Assert.assertEquals("testListId", trelloCardDto.getListId());
    }

    @Test
    public void shouldMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("testName", "testDesc", "testPos", "testListId");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertEquals("testName", trelloCard.getName());
        Assert.assertEquals("testDesc", trelloCard.getDescription());
        Assert.assertEquals("testPos", trelloCard.getPos());
        Assert.assertEquals("testListId", trelloCard.getListId());
    }
}