package intership.libraryintership.mapper;

import intership.libraryintership.dto.author.AuthorCreateDTO;
import intership.libraryintership.entity.Author;
import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper {
    AuthorCreateDTO.AuthorStandardResponse authorToAuthorStandardResponse(Author author);
    Author authorCreateDTOToAuthor(AuthorCreateDTO authorCreateDTO);
}
