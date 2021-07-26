package com.ssafy.api.response;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceCategory;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.convert.ThreeTenBackPortConverters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ConferenceDetailRes")
public class ConferenceDetailRes {
	@ApiModelProperty(name="User ID")
	long id;
	String conference_category;
	int joinUsersNum; //
	String owner;
	LocalDateTime callstartTime;
	String thumbnail_url;
	String title;
	String description;
	boolean is_active;
	List<DetailUser> users = new ArrayList<>();
	
	public static ConferenceDetailRes of(Conference conference, Optional<List<UserConference>> userConference) {
		List<DetailUser> userDetail = getUser(userConference);
		ConferenceDetailRes res = new ConferenceDetailRes();
		res.set_active(conference.getIs_active());
		res.setCallstartTime(conference.getCallStartTime());
		res.setDescription(conference.getDescription());
		res.setId(conference.getId());
		res.setConference_category(conference.getConferenceCategory().getName());
		res.setThumbnail_url(conference.getThumbnail());
		res.setTitle(conference.getTitle());
		res.setUsers(userDetail);
		return res;
	}

	private static List<DetailUser> getUser(Optional<List<UserConference>> userConference) {
		List<DetailUser> users = new ArrayList<>();

		for(int i=0; i<userConference.get().size(); i++){
			DetailUser user = new DetailUser();
			user.setUserId(userConference.get().get(i).getUser().getId());
			user.setUserName(userConference.get().get(i).getUser().getName());
			users.add(user);
		}
		return users;
	}
}

@Getter
@Setter
class DetailUser{
	Long userId;
	String userName;
}
