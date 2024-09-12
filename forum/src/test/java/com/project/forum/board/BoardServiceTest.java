package com.project.forum.board;

import com.project.forum.dto.FileDto;
import com.project.forum.dto.board.BoardDto;
import com.project.forum.entity.AppUser;
import com.project.forum.entity.Board;
import com.project.forum.entity.DummyImage;
import com.project.forum.entity.Tag;
import com.project.forum.exception.CustomException;
import com.project.forum.repository.*;
import com.project.forum.service.BoardService;
import com.project.forum.type.SortBoardTypeEnum;
import com.project.forum.util.FileStore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@ExtendWith({ MockitoExtension.class })
public class BoardServiceTest {
    @Mock
    private FileStore fileStore;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private DummyImageRepository dummyImageRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private BoardTagRepository boardTagRepository;
    @Mock
    private BoardImageRepository boardImageRepository;
    @InjectMocks
    private BoardService boardService;
    private MockMultipartFile mockMultipartFile;

    private final AppUser appUser = new AppUser();
    private final Tag tag = Tag.builder()
            .tagName("tag")
            .build();
    private final DummyImage dummyImage = DummyImage.builder()
            .imageSize(1024L)
            .imageType("image/jpeg")
            .imagePath("test.jpg")
            .build();

    private final Board board = new Board();

    @BeforeEach
    void setUp() {
        byte[] imageContent = new byte[]{(byte)0xFF, (byte)0xD8, (byte)0xFF, (byte)0xE0, 0x00, 0x10, 0x4A, 0x46, 0x49, 0x46, 0x00, 0x01};
        mockMultipartFile = new MockMultipartFile("thumbnail", "test.jpg", "image/jpeg", imageContent);

        appUser.setUserId(1L);
        appUser.setUserPw("encryptedPassword");
        appUser.setEmail("appUser@example.com");
        appUser.setProfilePath("/images/profile/appUser.jpg");
        appUser.setProfileSize(2048L);
        appUser.setProfileType("image/jpeg");
        appUser.setNickname("appUser");
        appUser.setDescription("This is a test user.");
        appUser.setBoardCount(10L);
        appUser.setNewsCount(5L);
        appUser.setCommentCount(20L);
        appUser.setLikeCount(50L);
        appUser.setFollowerCount(100L);
        appUser.setFollowingCount(150L);
        appUser.setDisableFollow(false);
        appUser.setPrivateAccount(false);
        appUser.setNoticeNotification(true);
        appUser.setCommentNotification(true);
        appUser.setLikeNotification(true);
        appUser.setRecommendNotification(true);
        appUser.setFollowNotification(true);
        appUser.setEventNotification(true);
        appUser.setDeleted(false);
        appUser.setDeletedTime(null);
        appUser.setCreatedTime(LocalDateTime.now());

        board.setBoardId(1L);
        board.setCategory('N');
        board.setTitle("title");
        board.setContent("content");
        board.setThumbnailPath("test.jpg");
        board.setThumbnailSize(1024L);
        board.setThumbnailType("image/jpeg");
        board.setUploader(appUser);
        board.setCreatedTime(LocalDateTime.now());

        tag.setTagCount(0L);
    }

    @Test
    @DisplayName("게시글 생성 실패 - 유저 없음")
    void createBoardFailedWithNotFound() {
        Mockito.when(appUserRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(CustomException.class, () -> {
            boardService.create(3L, new BoardDto.Request("N", "제목1", "내용1", List.of(1L), null, null));
        });

        Mockito.verify(boardRepository, Mockito.never()).save(ArgumentMatchers.any(Board.class));
    }

    @Test
    @DisplayName("게시글 생성 실패 - 태그 3개 초과")
    void createBoardFailedWithTag() {
        Mockito.when(appUserRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(appUser));
        Mockito.when(tagRepository.findAllByTagIdIn(Mockito.anyList()))
                .thenReturn(List.of(new Tag[]{ tag, tag, tag, tag }));

        Assertions.assertThrows(CustomException.class, () -> {
            boardService.create(1L, new BoardDto.Request("N", "제목1", "내용1", List.of(1L), null, null));
        });

        Mockito.verify(appUserRepository, Mockito.times(1)).findByUserId(Mockito.anyLong());
        Mockito.verify(boardRepository, Mockito.times(1)).save(ArgumentMatchers.any(Board.class));
    }

    @Test
    @DisplayName("게시글 생성 성공")
    @Rollback
    void createBoardSuccess() {
        Mockito.when(appUserRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(appUser));
        Mockito.when(boardRepository.save(Mockito.any()))
                .thenReturn(board);
        Mockito.when(tagRepository.findAllByTagIdIn(Mockito.anyList()))
                .thenReturn(List.of(new Tag[]{ tag }));
        Mockito.when(boardTagRepository.saveAll(Mockito.any()))
                .thenReturn(null);

        Long boardId = boardService.create(1L, new BoardDto.Request("N", "제목1", "내용1", List.of(1L), null, null));

        System.out.println("Board ID: " + boardId);
        Assertions.assertNotNull(boardId);

        Mockito.verify(boardRepository, Mockito.times(1)).save(ArgumentMatchers.any(Board.class));
        Mockito.verify(boardTagRepository, Mockito.times(1)).saveAll(ArgumentMatchers.any());
    }

    @Test
    @DisplayName("게시글 생성 및 썸네일 업로드 성공")
    @Rollback
    void createBoardSuccessWithThumbnail() {
        Mockito.when(fileStore.storeFile(Mockito.anyString(), Mockito.any(MultipartFile.class)))
                .thenReturn(FileDto.builder()
                        .storeFileName("test.jpg")
                        .originalFileName("test.jpg")
                        .storePath("test.jpg")
                        .size(1024L)
                        .extension("image/jpeg")
                        .build()
                );
        Mockito.when(appUserRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(appUser));
        Mockito.when(boardRepository.save(Mockito.any()))
                .thenReturn(board);
        Mockito.when(tagRepository.findAllByTagIdIn(Mockito.anyList()))
                .thenReturn(List.of(new Tag[]{ tag }));
        Mockito.when(boardTagRepository.saveAll(Mockito.any()))
                .thenReturn(null);

        Long boardId = boardService.create(1L, new BoardDto.Request("N", "제목1", "내용1", List.of(1L), mockMultipartFile, null));
        System.out.println("Board ID: " + boardId);

        Assertions.assertNotNull(boardId);
        Mockito.verify(fileStore, Mockito.times(1)).storeFile(Mockito.anyString(), Mockito.any(MultipartFile.class));
        Mockito.verify(boardRepository, Mockito.times(1)).save(ArgumentMatchers.any(Board.class));
    }

    @Test
    @DisplayName("게시글 생성 및 이미지 업로드 성공")
    @Rollback
    void createBoardSuccessWithImage() {
        Mockito.when(fileStore.storeFile(Mockito.anyString(), Mockito.any(MultipartFile.class)))
                .thenReturn(FileDto.builder()
                        .storeFileName("test.jpg")
                        .originalFileName("test.jpg")
                        .storePath("test.jpg")
                        .size(1024L)
                        .extension("image/jpeg")
                        .build()
                );
        Mockito.when(appUserRepository.findByUserId(Mockito.anyLong()))
                .thenReturn(Optional.of(appUser));
        Mockito.when(boardRepository.save(Mockito.any()))
                .thenReturn(board);
        Mockito.when(tagRepository.findAllByTagIdIn(Mockito.anyList()))
                .thenReturn(List.of(new Tag[]{ tag }));
        Mockito.when(boardTagRepository.saveAll(Mockito.any()))
                .thenReturn(null);
        Mockito.when(dummyImageRepository.findAllByImageIdIn(Mockito.anyList()))
                .thenReturn(List.of(new DummyImage[]{ dummyImage }));
        Mockito.when(boardImageRepository.saveAll(Mockito.any()))
                .thenReturn(null);
        Mockito.doNothing().when(dummyImageRepository).deleteAllInBatch(Mockito.anyList());

        Long boardId = boardService.create(1L, new BoardDto.Request("N", "제목1", "내용1", List.of(1L), mockMultipartFile, List.of(1L)));
        System.out.println("Board ID: " + boardId);

        Assertions.assertNotNull(boardId);
        Mockito.verify(fileStore, Mockito.times(1)).storeFile(Mockito.anyString(), Mockito.any(MultipartFile.class));
        Mockito.verify(boardRepository, Mockito.times(1)).save(ArgumentMatchers.any(Board.class));
        Mockito.verify(dummyImageRepository, Mockito.times(1)).deleteAllInBatch(Mockito.anyList());
    }
    
    @Test
    @DisplayName("게시글 가져오기 성공")
    void getBoardSuccess() {
        List<Board> board = List.of(new Board[]{ this.board });
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<Board> returnBoards = new PageImpl<>(board, pageable, board.size());

        Mockito.when(boardRepository.findAllByCategoryOrderByCreatedTimeDesc(Mockito.any(), Mockito.anyChar()))
                .thenReturn(returnBoards);

        BoardDto.Response.Boards boards = boardService.getRecentPosts(1, 5, SortBoardTypeEnum.B);

        System.out.println("First Board Id: " + boards.getBoards().getFirst().getBoardId());
        System.out.println("First Board Title: " + boards.getBoards().getFirst().getTitle());

        Assertions.assertNotNull(boards);
        Assertions.assertNotNull(boards.getBoards());
        Assertions.assertEquals(1, boards.getBoards().size());
        Assertions.assertEquals(1L, boards.getTotal());
        Assertions.assertEquals(1L, boards.getBoards().getFirst().getBoardId());
        Assertions.assertEquals("title", boards.getBoards().getFirst().getTitle());
    }

    @Test
    @DisplayName("게시글 검색 성공")
    void getBoardSearchSuccess() {
        List<Board> board = List.of(new Board[]{ this.board });
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<Board> returnBoards = new PageImpl<>(board, pageable, board.size());

        Mockito.when(boardRepository.findALlByTitleContainingOrderByCreatedTimeDesc(Mockito.any(), Mockito.anyString()))
                .thenReturn(returnBoards);

        BoardDto.Response.Boards boards = boardService.getSearchPosts(1, 5, "title");

        System.out.println("First Board Id: " + boards.getBoards().getFirst().getBoardId());
        System.out.println("First Board Title: " + boards.getBoards().getFirst().getTitle());

        Assertions.assertNotNull(boards);
        Assertions.assertNotNull(boards.getBoards());
        Assertions.assertEquals(1, boards.getBoards().size());
        Assertions.assertEquals(1L, boards.getTotal());
        Assertions.assertEquals(1L, boards.getBoards().getFirst().getBoardId());
        Assertions.assertEquals("title", boards.getBoards().getFirst().getTitle());
    }

    @Test
    @DisplayName("게시글 가져오기 성공 - 게시글 없음")
    void getBoardFailed() {
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<Board> returnBoards = new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(boardRepository.findAllByCategoryOrderByCreatedTimeDesc(Mockito.any(), Mockito.anyChar()))
                .thenReturn(returnBoards);

        BoardDto.Response.Boards boards = boardService.getRecentPosts(1, 5, SortBoardTypeEnum.B);

        Assertions.assertNotNull(boards);
        Assertions.assertNotNull(boards.getBoards());
        Assertions.assertEquals(0, boards.getBoards().size());
        Assertions.assertEquals(0L, boards.getTotal());
    }

    @Test
    @DisplayName("게시글 가져오기 성공 - 검색 결과 없음")
    void getBoardSearchFailed() {
        Pageable pageable = Pageable.ofSize(5).withPage(0);
        Page<Board> returnBoards = new PageImpl<>(List.of(), pageable, 0);

        Mockito.when(boardRepository.findALlByTitleContainingOrderByCreatedTimeDesc(Mockito.any(), Mockito.anyString()))
                .thenReturn(returnBoards);

        BoardDto.Response.Boards boards = boardService.getSearchPosts(1, 5, "title");

        Assertions.assertNotNull(boards);
        Assertions.assertNotNull(boards.getBoards());
        Assertions.assertEquals(0, boards.getBoards().size());
        Assertions.assertEquals(0L, boards.getTotal());
    }
}
