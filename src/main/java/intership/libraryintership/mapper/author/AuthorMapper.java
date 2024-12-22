package intership.libraryintership.mapper.author;

import intership.libraryintership.dto.author.AuthorCreateDTO;
import intership.libraryintership.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorCreateDTO.AuthorStandardResponse authorToAuthorStandardResponse(Author author);
    Author authorCreateDTOToAuthor(AuthorCreateDTO authorCreateDTO);
}
