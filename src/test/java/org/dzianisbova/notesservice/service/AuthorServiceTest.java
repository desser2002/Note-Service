package org.dzianisbova.notesservice.service;

import org.dzianisbova.notesservice.domain.model.Author;
import org.dzianisbova.notesservice.repository.AuthorRepository;
import org.dzianisbova.notesservice.web.dto.AuthorDto;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    @DisplayName("Create author should save and return DTO")
    void createAuthor_shouldSaveAndReturnDto() {
        String name ="John Doe";
        Long id = 1L;
        AuthorDto inputDto = new AuthorDto(null, name);
        Author savedAuthor = createAuthor(id, name);

        when(authorRepository.save(any(Author.class))).thenReturn(savedAuthor);

        AuthorDto result = authorService.createAuthor(inputDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(name);

        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("Get all authors should return list of DTOs")
    void getAllAuthors_shouldReturnListOfDto() {
        String name1="Author1";
        String name2="Author2";
        Author author1 = createAuthor(1L,name1 );
        Author author2 = createAuthor(2L, name2);

        when(authorRepository.findAll()).thenReturn(List.of(author1, author2));

        List<AuthorDto> result = authorService.getAllAuthors();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(name1);
        assertThat(result.get(1).getName()).isEqualTo(name2);

        verify(authorRepository).findAll();
    }

    @Test
    @DisplayName("Get author by existing ID should return DTO")
    void getAuthorById_existingId_shouldReturnDto() {
        Long id = 1L;
        String name = "John";
        Author author = createAuthor(id, name);

        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto result = authorService.getAuthorById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getName()).isEqualTo(name);

        verify(authorRepository).findById(id);
    }

    @Test
    @DisplayName("Get author by non-existing ID should throw RuntimeException")
    void getAuthorById_nonExistingId_shouldThrowException() {
        final Long nonExistingId = 100L;
        when(authorRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.getAuthorById(nonExistingId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Author not found with id: " + nonExistingId);

        verify(authorRepository).findById(nonExistingId);
    }

    private Author createAuthor(Long id, String name) {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        return author;
    }
}
