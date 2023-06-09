package com.nanningedu.controller;

import com.nanningedu.domain.TranVO;
import com.nanningedu.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workChart")
public class TranController {

    @Autowired
    private TranService tranService;

    @RequestMapping("/queryTranByChart.do")
    public List<TranVO> queryTranByChart(){
        return tranService.queryByStage();
    }

}
