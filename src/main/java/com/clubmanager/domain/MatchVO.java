package com.clubmanager.domain;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MatchVO {
	private int matchNo;
	private String apposingTeam;
	private Date matchDate;
	private String stadium;
	private int matchStatus; // 0: ��Ȯ���� ���   , 1:Ȯ���� ���, 2:����� ���
	private int matchRecordStatus; // 0:�̱��, 1:�����, 2:��� �Ϸ�
	private String clubCode;
	
	private int pollTime;
	
	private PollPartVO ppVO;
	
}
