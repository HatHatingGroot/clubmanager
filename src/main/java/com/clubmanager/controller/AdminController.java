package com.clubmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clubmanager.domain.AnnVO;
import com.clubmanager.domain.Criteria;
import com.clubmanager.domain.pageDTO;
import com.clubmanager.service.AdminService;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
	
	@Setter(onMethod_= @Autowired)
	private AdminService adminService;
	
	@GetMapping("/announcements_list")
	public void goToAnnouncementsList(Criteria cri, Model model) {
		log.info("announcements_list.jsp cri :" + cri);
		
		model.addAttribute("cri", cri);
	}
	
	
	@PostMapping(value = "/annListByAjax", consumes="application/json", produces={ MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public List<AnnVO> getBoardListByAjax(@RequestBody Criteria cri) {
		log.info("listByAjax : " + cri);
		List<AnnVO> annList = adminService.getAnnList(cri);
		log.info("annList ......." + annList);
		return annList;
	}
	
	
	@PostMapping(value = "/getPaginator", consumes="application/json", produces={ MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public pageDTO getPaginator(@RequestBody Criteria cri) {
		log.info("getPaginator cri : " + cri);
		int total = adminService.getAnnTotalNum(cri);
		pageDTO pdto = new pageDTO(total, cri);
		log.info("pageDTO : " + pdto);
		
		return pdto;
	}
	
	@PostMapping(value = "/writeAction")
	public String writeAction(AnnVO annVO, RedirectAttributes rttr) {
		log.info("writeAction annVO : " + annVO);
		
		if(adminService.insert(annVO) ==1) {
			rttr.addFlashAttribute("writeResult", "공지사항 등록 완료");
		}else{
			rttr.addFlashAttribute("writeResult", "공지사항 등록 실패");
		};
		
		return "redirect:/admin/announcements_list";
	}
	
	@PostMapping(value = "/modify")
	public String modify(AnnVO annVO, RedirectAttributes rttr) {
		log.info("modify annVO : " + annVO);
		
		if(adminService.modify(annVO) ==1) {
			rttr.addFlashAttribute("modifyResult", "공지사항 수정 완료");
		}else{
			rttr.addFlashAttribute("modifyResult", "공지사항 수정 실패");
		};
		
		return "redirect:/admin/announcements_list";
	}
	
	@PostMapping(value = "/deleteAnn")
	public String deleteAnn(AnnVO annVO, RedirectAttributes rttr) {
		log.info("delete annVO : " + annVO);
		
		if(adminService.delete(annVO) ==1) {
			rttr.addFlashAttribute("deleteResult", "공지사항 삭제 완료");
		}else{
			rttr.addFlashAttribute("deleteResult", "공지사항 삭제 실패");
		};
		
		return "redirect:/admin/announcements_list";
	}
	
	
	@GetMapping("/clubs_list")
	public void goToClubsList() {
		log.info("clubs_list.jsp");
	}
	
	
}
