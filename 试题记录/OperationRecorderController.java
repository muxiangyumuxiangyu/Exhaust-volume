package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Recorder;
import com.model.Page;
import com.service.OperationRecorderService;

@Controller
@RequestMapping("recorder")
public class OperationRecorderController {
	@Resource
	private OperationRecorderService operationRecorderService;
	
	/**
	 * 查找所有的记录
	 * @param reqeust
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("findAllRecorder")
	public String findAllRecorder(HttpServletRequest request,@RequestParam(name="pageNum", defaultValue="1") int pageNum){
		Page page = operationRecorderService.FindRecorderAllList(pageNum,2);
		request.setAttribute("page",page);
		request.setAttribute("choose","none");
		return "record/OperationRecorder";
	}
	/**
	 * 按照老师的id查找 
	 * @param request
	 * @param pageNum
	 * @param t_id
	 * @return
	 */
	@RequestMapping("findRecorderByTeacher")
	public String findRecorderByTeahcer(HttpServletRequest request,
			@RequestParam(name="pageNum",defaultValue="1") int pageNum,
			@RequestParam(name="t_id") String teacher_id){
		Integer t_id = null;
		try{
			t_id = new Integer(teacher_id);
		}catch(Exception e){
			t_id = -1;
		}
		
		Page page = operationRecorderService.findTeacherRecorder(pageNum,5,t_id);
		request.setAttribute("choose","t_id");
		if(t_id != -1){
			request.setAttribute("t_id",t_id);
		}else{
			request.setAttribute("t_id",teacher_id);
		}
		request.setAttribute("page",page);
		return "record/OperationRecorder";
	}
	
	/**
	 * 按照操作的类型进行查询
	 * @param request
	 * @param pageNum
	 * @param operationName
	 * @return
	 */
	@RequestMapping("findRecorderByOperationType")
	public String findRecorderByOperationType(HttpServletRequest request,
			@RequestParam(name="pageNum",defaultValue="1") int pageNum,
			@RequestParam(name="operationName") String operationName){
		try {
			operationName = new String(operationName.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("操作的类型是："+operationName);
		Page page = operationRecorderService.findRecorderByOperationType(pageNum,2,operationName);
		request.setAttribute("choose","operationType");
		request.setAttribute("operationName",operationName);
		request.setAttribute("page",page);
		return "record/OperationRecorder";
	}

	/**
	 * 按照老师的姓名进行
	 * @param request
	 * @param pageNum
	 * @param TeacherName
	 * @return
	 */
	@RequestMapping("findRecorderByTeacherName")
	public String findRecorderByTeacherName(HttpServletRequest request,
			@RequestParam(name="pageNum",defaultValue="1") int pageNum,
			@RequestParam(name="TeacherName") String TeacherName){
		System.out.println("zhixing lelelel");
		Page page = operationRecorderService.findRecorderByTeacherName(pageNum,5,TeacherName);
		request.setAttribute("choose","TeacherName");
		request.setAttribute("TeacherName",TeacherName);
		request.setAttribute("page",page);
		return "record/OperationRecorder";
	}
}
