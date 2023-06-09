package com.nanningedu.service;

import com.nanningedu.domain.TranVO;

import java.util.List;

public interface TranService {

    List<TranVO> queryByStage();

}
