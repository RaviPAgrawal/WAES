package com.myapp.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.bean.UserDiffDTO;
import com.myapp.myapp_service.DifferenceService;

/**
 * @author Ravi Bhai
 * Controller to handle diff requests 
 */
@Controller
@RequestMapping(value = "/v1/diff")
public class DifferenceController {
	
	 @Autowired
	 DifferenceService differenceService;

	/**
	 * @param userDiffDTO
	 * @param id
	 * @param request
	 * @return
	 * Mapping to handle create/update left data associated with particular id
	 */
	@RequestMapping(value = "/{id}/left", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveLeft(@RequestBody UserDiffDTO userDiffDTO, @PathVariable Integer id, HttpServletRequest request) {
        return differenceService.saveLeft(id, userDiffDTO);
    }

	/**
	 * @param userDiffDTO
	 * @param id
	 * @param request
	 * @return
	 * Mapping to handle create/update right data associated with particular id
	 */
	@RequestMapping(value = "/{id}/right", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveRight(@RequestBody UserDiffDTO userDiffDTO, @PathVariable Integer id, HttpServletRequest request) {
        return differenceService.saveRight(id, userDiffDTO);
    }

	/**
	 * @param id
	 * @param request
	 * @return
	 * Mapping to get difference in left and right data for a particular id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String, Object> getDifference(@PathVariable Integer id, HttpServletRequest request) {
        return differenceService.getDifference(id);
    }
}
