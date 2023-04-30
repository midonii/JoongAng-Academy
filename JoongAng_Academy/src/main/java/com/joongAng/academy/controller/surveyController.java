package com.joongAng.academy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.joongAng.academy.service.CrclmInfoService;
import com.joongAng.academy.service.SurveyService;

@Controller
public class surveyController {

	@Autowired
	private SurveyService surveyService;
	@Autowired
	private CrclmInfoService crclmInfoService;
	
	//커리큘럼 select option 생성
	@GetMapping("survey")
	public ModelAndView survey() {
		ModelAndView mv = new ModelAndView("admin/survey");
		List<Map<String, Object>> list = crclmInfoService.crclmNameList();
		mv.addObject("crclmName", list);
		return mv;
	}
	
	//설문리스트 + 검색
	@ResponseBody
	@PostMapping(value = "/surveyListAjax", produces = "application/json;charset=UTF-8")
	public String surveyListAjax(@RequestParam Map<String, String> map) {
		JSONObject json = new JSONObject();
		List<Map<String, Object>> list = surveyService.list(map); 
		json.put("list", list);
		
		return json.toString();
	}
	
	//수정 + 리스트
	@ResponseBody
	@PostMapping(value = "/surveyUpdate", produces = "application/json;charset=UTF-8")
	public String surveyUpdate(@RequestParam Map<String, String> map) {
		JSONObject json = new JSONObject();
		int result = surveyService.update(map); 
		json.put("result", result); 
		//System.err.println("result:"+result); //1 (수정한값 있던없던 1)
		
		List<Map<String, Object>> list = surveyService.list(map); 
		json.put("list", list);
		//System.out.println("list:"+list);
		
		return json.toString();
	}
	
	//문항정보
	@ResponseBody
	@PostMapping(value = "/surveyDetail", produces = "application/json;charset=UTF-8")
	public String surveyDetail(@RequestParam Map<String, String> map) {
		JSONObject json = new JSONObject();
		List<Map<String, Object>> detaillist = surveyService.detaillist(map); 
		json.put("detaillist", detaillist);
		//System.out.println("detaillist:"+detaillist);//ok
		return json.toString();
	}
	
	//문항정보 신규
	@ResponseBody
	@PostMapping(value = "/ITEMCreate", produces = "application/json;charset=UTF-8")
	public String ITEMCreate(@RequestParam Map<String, Object> map,HttpServletRequest request){
		JSONObject json = new JSONObject();
		int result = surveyService.ITEMCreate(map); 
		//System.err.println(map.get("CRCLM_YEAR")); //ok
		json.put("result", result);
		//System.err.println(result); //1
	return json.toString();
	}
	
	//문항정보 수정
	@ResponseBody
	@PostMapping(value = "/ITEMUpdate", produces = "application/json;charset=UTF-8")
	public String ITEMUpdate(@RequestBody List<Map<String, Object>> updateData,HttpServletRequest request){
		JSONObject json = new JSONObject();
			int result = surveyService.ITEMUpdate(updateData); 
			//System.err.println(updateData.get(0).get("DGSTFN_CN")); //ok
			//System.err.println(updateData);//ok
			json.put("result", result);
			//System.err.println(result);//ok
		return json.toString();
	}
	
	//문항정보 삭제
	@ResponseBody
	@PostMapping(value = "/ITEMDelete", produces = "application/json;charset=UTF-8")
	public String codeDelete(@RequestBody List<Map<String, Object>> deleteData, HttpServletRequest request){
		    JSONObject json = new JSONObject();
		    int result = surveyService.ITEMdelete(deleteData);
		    System.err.println(deleteData.get(0).get("DGSTFN_CN"));//ok
		    System.err.println(deleteData); //[{CD_CLSF=1, CD=1, CD_NM=1, CD_USE_YN=, CD_EXPLN=1, CD_SORT_SN=1}, {CD_CLSF=1, CD=2, CD_NM=1, CD_USE_YN=, CD_EXPLN=1, CD_SORT_SN=1}]
		    json.put("result", result);
		    System.err.println("result:"+result);//삭제된 행 갯수
			
		    
		    
//			//삭제 저장후 다시 전체리스트 조회페이지로 가야함(전체조회+검색) 
//			String code_search = request.getParameter("code_search");
//			List<Map<String, Object>> list = codeService.list(code_search); 
//			JSONArray listJ = new JSONArray(list);
//			json.put("list", listJ);
		return json.toString();
	}
	
	//답변정보
	@ResponseBody
	@PostMapping(value = "/surveyANS", produces = "application/json;charset=UTF-8")
	public String surveyANS() {
		JSONObject json = new JSONObject();
		List<Map<String, Object>> anslist = surveyService.anslist(); 
			json.put("anslist", anslist);
			//System.out.println("객관");//
		return json.toString();
	}
	
	//참석자정보
	@ResponseBody
	@PostMapping(value = "/surveyStdnt", produces = "application/json;charset=UTF-8")
	public String surveyStdnt(@RequestParam Map<String, String> map) {
		JSONObject json = new JSONObject();
		List<Map<String, Object>> stdntlist = surveyService.stdntlist(map); 
		json.put("stdntlist", stdntlist);
		//System.out.println("객관");//
		return json.toString();
	}


}
