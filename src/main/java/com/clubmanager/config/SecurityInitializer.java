package com.clubmanager.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	//WebConfig �� getServletFilters()�� �̿��Ͽ� �߰��ѰͰ� ���� ����
	//AbstractSecurityWebApplicationInitializer Ŭ������ ���������� DelegatingFilterProxy �� �������� ���
	
	//DelegatingFilterProxy
	//������ ��ť��Ƽ�� ��� ���ø����̼� ��û�� ���ΰ� �ؼ� ��� ��û�� ������ ����ǰ� �ϴ� ���������̴�.
	//(������ �����ӿ�ũ���� ����) ������ �����ӿ�ũ ����� �� ���ø����̼ǿ��� ���� ���� ������ ����Ŭ�� 
	//������ ������ �� �������� ���� ���Ϳ� ���ε��ϴµ� ����Ѵ�.
}
