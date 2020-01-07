package com.clubmanager.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PollStatusDTO {
	private int matchNo;
	private String userId;
	private String userName;
	private int pollType; //--1: participate , 2: MoM
	private int status; //-- 0: ����ǥ, 1:��ǥ or ����  2: ������ 
	private int picked; // MoM ��ǥ��
}
