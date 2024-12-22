package intership.libraryintership.mapper.profile;

import intership.libraryintership.dto.profile.ProfileCreateDTO;
import intership.libraryintership.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileCreateDTO.ProfileResponse profileToProfileResponse(Profile profile);

    Profile profileCreateDTOtoProfile(ProfileCreateDTO profileCreateDTO);

}
