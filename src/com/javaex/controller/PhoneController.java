package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//컨트롤러 테스트
		System.out.println("controller");

		// 파라미터 action 값을 읽어서
		String action = request.getParameter("action");
		System.out.println(action);
		
		// action List 이면 리스트 츨력관련

		if ("wform".equals(action)) {
			System.out.println("등록 폼 처리");
			
			WebUtil.forward(request, response, "./WEB-INF/WriteForm.jsp");
			
			
			
		} else if("insert".equals(action)) {
			System.out.println("전화번호 저장");

			// 파라미터 3개 값
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");

			// personVo 묶고
			PersonVo personVo = new PersonVo(name, hp, company);

			// new dao 저장

			PhoneDao phoneDao = new PhoneDao();
			phoneDao.personInsert(personVo);
			
			response.sendRedirect("./pbc?action=List");
		} else if("delete".equals(action)) {
			System.out.println("삭제");
			
			int PersonId = Integer.parseInt(request.getParameter("id"));
			
			PhoneDao phoneDao = new PhoneDao();
			phoneDao.PersonDelete(PersonId);
			
			response.sendRedirect("./pbc?action=List");
		} else if("uform".equals(action)) {
			System.out.println("수정 폼 처리");
			
			int ID = Integer.parseInt(request.getParameter("id"));
			
			PhoneDao pdao = new PhoneDao();
			PersonVo pvo = new PersonVo();
			
			pvo = pdao.getPerson(ID);
			
			//데이터 전달
			request.setAttribute("pvo", pvo);
			System.out.println(pvo);
			
			WebUtil.forward(request, response, "./WEB-INF/UpdateForm.jsp");

		} else if("update".equals(action)) {
			System.out.println("수정");
			
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			int Pid = Integer.parseInt(request.getParameter("id"));
			
			PersonVo pvo = new PersonVo(Pid, name, hp, company);
			
			PhoneDao pdao = new PhoneDao();
			pdao.personUpdate(pvo);
			
			response.sendRedirect("./pbc?action=List");
		} else /*("list".equals(action))*/ {
			System.out.println("리스트 처리");
			//리스트 출력 처리
			PhoneDao phoneDao = new PhoneDao();
			List<PersonVo> personList = phoneDao.getPersonList();

			// html 작성 엄청 복잡함

			// 데이터 전달
			request.setAttribute("pList", personList);

			// jsp에 포워드 시킨다.
			WebUtil.forward(request, response, "./WEB-INF/List.jsp");

	 		

		} 

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet(request, response);
	}

}
