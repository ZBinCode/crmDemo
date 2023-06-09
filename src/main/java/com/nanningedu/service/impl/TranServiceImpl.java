package com.nanningedu.service.impl;

import com.nanningedu.domain.TranVO;
import com.nanningedu.mapper.TranMapper;
import com.nanningedu.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    private TranMapper tranMapper;

    @Override
    public List<TranVO> queryByStage() {
        return tranMapper.selectByStage();
    }
}
