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
	private int matchStatus; // 0: ����� ���  , 1:Ȯ���� ���, 2:��Ȯ���� ��� 
	private int matchRecordStatus; // 0:��� �Ϸ�, 1:�����, 2:�̱��
	private String clubCode;
	
	private int pollTime;
}
