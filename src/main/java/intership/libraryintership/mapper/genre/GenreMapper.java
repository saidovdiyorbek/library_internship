package intership.libraryintership.mapper.genre;

import intership.libraryintership.dto.genre.GenreCreateDTO;
import intership.libraryintership.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre genreCreateDTOtoGenre(GenreCreateDTO genreCreateDTO);
    GenreCreateDTO.GenreStandardResponse GenreToGenreStandardResponse(Genre genre);
}
