package com.clubmanager.mapper;

import com.clubmanager.domain.ClubVO;

public interface ClubMapper {
	public ClubVO get(String clubCode);
	
	public int insert(ClubVO clubVO);
}