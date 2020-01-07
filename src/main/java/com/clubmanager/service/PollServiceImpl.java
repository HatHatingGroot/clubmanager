package com.clubmanager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubmanager.domain.PersonalRecordDTO;
import com.clubmanager.domain.PollPartVO;
import com.clubmanager.domain.PollStatusDTO;
import com.clubmanager.mapper.PollMapper;
import com.clubmanager.mapper.RecordMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class PollServiceImpl implements PollService {
	@Setter(onMethod_=@Autowired)
	private PollMapper pollMapper;
	
	@Setter(onMethod_=@Autowired)
	private RecordMapper recordMapper;
	
	@Override
	public List<PollPartVO> getPPList(String clubCode) {
		return pollMapper.getPPList(clubCode);
	}
	
	@Override
	public List<PollStatusDTO> getPSList(PollPartVO ppVO) {
		List<PollStatusDTO> psDTOList = new ArrayList<>();
		
		psDTOList = pollMapper.getPSList(ppVO);
		return psDTOList;
	}
	
	@Override
	public PollPartVO getPP(String clubCode, int matchNo) {
		return pollMapper.getPP(clubCode, matchNo);
	}

	@Transactional
	@Override
	public boolean vote(PollStatusDTO psDTO) {
		int result = pollMapper.modifyPS(psDTO);
		
		PersonalRecordDTO prDTO = new PersonalRecordDTO();
		prDTO.setMatchNo(psDTO.getMatchNo());
		prDTO.setUserId(psDTO.getUserId());
		if(psDTO.getStatus()==1) {//psDTO.getStatus == 1 => 참석자 명단 추가
			log.warn("insertPR  FROM vote..... prDTO : " + prDTO);
			recordMapper.insertPR(prDTO);
		}else if(psDTO.getStatus()==2) {//psDTO.getStatus == 2 => 참석자 명단 제거
			log.warn("deletePR  FROM vote..... prDTO : " + prDTO);
			recordMapper.deletePR(prDTO);
		}
		
		if(result == 1) return true;
		return false;
	}
	
	@Override
	public boolean modifyPP(PollPartVO ppVO) {
		int result = pollMapper.modifyPP(ppVO);
		if(result == 1) return true;
		return false;
	}
}
