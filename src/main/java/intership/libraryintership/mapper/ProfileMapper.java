package intership.libraryintership.mapper;

import intership.libraryintership.dto.profile.ProfileCreateDTO;
import intership.libraryintership.entity.Profile;
import intership.libraryintership.repository.ProfileRepository;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileCreateDTO.ProfileResponse profileToProfileResponse(Profile profile);

    Profile profileCreateDTOtoProfile(ProfileCreateDTO profileCreateDTO);

}
