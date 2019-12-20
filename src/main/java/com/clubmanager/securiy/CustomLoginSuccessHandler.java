package com.clubmanager.securiy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.warn("Login Success");

		// �α����� ����� ���� Ȯ��
		List<String> authNames = new ArrayList<>();
		authentication.getAuthorities().forEach(auth -> authNames.add(auth.getAuthority()));

		log.warn("Logined User's Auth : " + authNames);

		// ������ : �������� ���� �������� �̵�
		if (authNames.contains("ROLE_ADMIN")) {
			response.sendRedirect("/admin/announcements_list");
			return;
		}

		// ������,�Ŵ���,��� : ���� ��� ���� �������� �̵�
		if (authNames.contains("ROLE_MEMBER")) {

			// �Ҽ� ���� ���� ���� �ʿ�
//			request.setAttribute("clubCode", "123123");

			response.sendRedirect("/schedule/list");
			return;
		}

		// �Ҽӱ����� ���� �Ϲ� ����� : ���� �Ұ� �������� �̵�
			response.sendRedirect("/intro");
	}

}
